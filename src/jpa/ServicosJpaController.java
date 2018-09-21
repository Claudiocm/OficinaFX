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
import modelo.Produtofornecedor;
import modelo.Servicos;

/**
 *
 * @author estagio
 */
public class ServicosJpaController implements Serializable {

    public ServicosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Servicos servicos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produtofornecedor serProduto = servicos.getSerProduto();
            if (serProduto != null) {
                serProduto = em.getReference(serProduto.getClass(), serProduto.getProfId());
                servicos.setSerProduto(serProduto);
            }
            em.persist(servicos);
            if (serProduto != null) {
                serProduto.getServicosList().add(servicos);
                serProduto = em.merge(serProduto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Servicos servicos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servicos persistentServicos = em.find(Servicos.class, servicos.getSerCodigo());
            Produtofornecedor serProdutoOld = persistentServicos.getSerProduto();
            Produtofornecedor serProdutoNew = servicos.getSerProduto();
            if (serProdutoNew != null) {
                serProdutoNew = em.getReference(serProdutoNew.getClass(), serProdutoNew.getProfId());
                servicos.setSerProduto(serProdutoNew);
            }
            servicos = em.merge(servicos);
            if (serProdutoOld != null && !serProdutoOld.equals(serProdutoNew)) {
                serProdutoOld.getServicosList().remove(servicos);
                serProdutoOld = em.merge(serProdutoOld);
            }
            if (serProdutoNew != null && !serProdutoNew.equals(serProdutoOld)) {
                serProdutoNew.getServicosList().add(servicos);
                serProdutoNew = em.merge(serProdutoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = servicos.getSerCodigo();
                if (findServicos(id) == null) {
                    throw new NonexistentEntityException("The servicos with id " + id + " no longer exists.");
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
            Servicos servicos;
            try {
                servicos = em.getReference(Servicos.class, id);
                servicos.getSerCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The servicos with id " + id + " no longer exists.", enfe);
            }
            Produtofornecedor serProduto = servicos.getSerProduto();
            if (serProduto != null) {
                serProduto.getServicosList().remove(servicos);
                serProduto = em.merge(serProduto);
            }
            em.remove(servicos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Servicos> findServicosEntities() {
        return findServicosEntities(true, -1, -1);
    }

    public List<Servicos> findServicosEntities(int maxResults, int firstResult) {
        return findServicosEntities(false, maxResults, firstResult);
    }

    private List<Servicos> findServicosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Servicos.class));
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

    public Servicos findServicos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Servicos.class, id);
        } finally {
            em.close();
        }
    }

    public int getServicosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Servicos> rt = cq.from(Servicos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
