/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa.exceptions.NonexistentEntityException;
import modelo.Caixa;
import modelo.Ordemservico;

/**
 *
 * @author estagio
 */
public class CaixaJpaController implements Serializable {

    public CaixaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Caixa caixa) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ordemservico caOrdemservico = caixa.getCaOrdemservico();
            if (caOrdemservico != null) {
                caOrdemservico = em.getReference(caOrdemservico.getClass(), caOrdemservico.getOrdId());
                caixa.setCaOrdemservico(caOrdemservico);
            }
            em.persist(caixa);
            if (caOrdemservico != null) {
                caOrdemservico.getCaixaList().add(caixa);
                caOrdemservico = em.merge(caOrdemservico);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Caixa caixa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Caixa persistentCaixa = em.find(Caixa.class, caixa.getCaId());
            Ordemservico caOrdemservicoOld = persistentCaixa.getCaOrdemservico();
            Ordemservico caOrdemservicoNew = caixa.getCaOrdemservico();
            if (caOrdemservicoNew != null) {
                caOrdemservicoNew = em.getReference(caOrdemservicoNew.getClass(), caOrdemservicoNew.getOrdId());
                caixa.setCaOrdemservico(caOrdemservicoNew);
            }
            caixa = em.merge(caixa);
            if (caOrdemservicoOld != null && !caOrdemservicoOld.equals(caOrdemservicoNew)) {
                caOrdemservicoOld.getCaixaList().remove(caixa);
                caOrdemservicoOld = em.merge(caOrdemservicoOld);
            }
            if (caOrdemservicoNew != null && !caOrdemservicoNew.equals(caOrdemservicoOld)) {
                caOrdemservicoNew.getCaixaList().add(caixa);
                caOrdemservicoNew = em.merge(caOrdemservicoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = caixa.getCaId();
                if (findCaixa(id) == null) {
                    throw new NonexistentEntityException("The caixa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Caixa caixa;
            try {
                caixa = em.getReference(Caixa.class, id);
                caixa.getCaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The caixa with id " + id + " no longer exists.", enfe);
            }
            Ordemservico caOrdemservico = caixa.getCaOrdemservico();
            if (caOrdemservico != null) {
                caOrdemservico.getCaixaList().remove(caixa);
                caOrdemservico = em.merge(caOrdemservico);
            }
            em.remove(caixa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Caixa> findCaixaEntities() {
        return findCaixaEntities(true, -1, -1);
    }

    public List<Caixa> findCaixaEntities(int maxResults, int firstResult) {
        return findCaixaEntities(false, maxResults, firstResult);
    }

    private List<Caixa> findCaixaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Caixa.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Caixa findCaixa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Caixa.class, id);
        } finally {
            em.close();
        }
    }

    public int getCaixaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Caixa> rt = cq.from(Caixa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
