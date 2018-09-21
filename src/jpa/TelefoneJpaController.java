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
import modelo.Cliente;
import modelo.Mecanico;
import modelo.Telefone;

/**
 *
 * @author estagio
 */
public class TelefoneJpaController implements Serializable {

    public TelefoneJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Telefone telefone) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente telClienteId = telefone.getTelClienteId();
            if (telClienteId != null) {
                telClienteId = em.getReference(telClienteId.getClass(), telClienteId.getCliId());
                telefone.setTelClienteId(telClienteId);
            }
            Mecanico telMecanicoId = telefone.getTelMecanicoId();
            if (telMecanicoId != null) {
                telMecanicoId = em.getReference(telMecanicoId.getClass(), telMecanicoId.getMecId());
                telefone.setTelMecanicoId(telMecanicoId);
            }
            em.persist(telefone);
            if (telClienteId != null) {
                telClienteId.getTelefoneList().add(telefone);
                telClienteId = em.merge(telClienteId);
            }
            if (telMecanicoId != null) {
                telMecanicoId.getTelefoneList().add(telefone);
                telMecanicoId = em.merge(telMecanicoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Telefone telefone) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Telefone persistentTelefone = em.find(Telefone.class, telefone.getTelId());
            Cliente telClienteIdOld = persistentTelefone.getTelClienteId();
            Cliente telClienteIdNew = telefone.getTelClienteId();
            Mecanico telMecanicoIdOld = persistentTelefone.getTelMecanicoId();
            Mecanico telMecanicoIdNew = telefone.getTelMecanicoId();
            if (telClienteIdNew != null) {
                telClienteIdNew = em.getReference(telClienteIdNew.getClass(), telClienteIdNew.getCliId());
                telefone.setTelClienteId(telClienteIdNew);
            }
            if (telMecanicoIdNew != null) {
                telMecanicoIdNew = em.getReference(telMecanicoIdNew.getClass(), telMecanicoIdNew.getMecId());
                telefone.setTelMecanicoId(telMecanicoIdNew);
            }
            telefone = em.merge(telefone);
            if (telClienteIdOld != null && !telClienteIdOld.equals(telClienteIdNew)) {
                telClienteIdOld.getTelefoneList().remove(telefone);
                telClienteIdOld = em.merge(telClienteIdOld);
            }
            if (telClienteIdNew != null && !telClienteIdNew.equals(telClienteIdOld)) {
                telClienteIdNew.getTelefoneList().add(telefone);
                telClienteIdNew = em.merge(telClienteIdNew);
            }
            if (telMecanicoIdOld != null && !telMecanicoIdOld.equals(telMecanicoIdNew)) {
                telMecanicoIdOld.getTelefoneList().remove(telefone);
                telMecanicoIdOld = em.merge(telMecanicoIdOld);
            }
            if (telMecanicoIdNew != null && !telMecanicoIdNew.equals(telMecanicoIdOld)) {
                telMecanicoIdNew.getTelefoneList().add(telefone);
                telMecanicoIdNew = em.merge(telMecanicoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = telefone.getTelId();
                if (findTelefone(id) == null) {
                    throw new NonexistentEntityException("The telefone with id " + id + " no longer exists.");
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
            Telefone telefone;
            try {
                telefone = em.getReference(Telefone.class, id);
                telefone.getTelId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telefone with id " + id + " no longer exists.", enfe);
            }
            Cliente telClienteId = telefone.getTelClienteId();
            if (telClienteId != null) {
                telClienteId.getTelefoneList().remove(telefone);
                telClienteId = em.merge(telClienteId);
            }
            Mecanico telMecanicoId = telefone.getTelMecanicoId();
            if (telMecanicoId != null) {
                telMecanicoId.getTelefoneList().remove(telefone);
                telMecanicoId = em.merge(telMecanicoId);
            }
            em.remove(telefone);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Telefone> findTelefoneEntities() {
        return findTelefoneEntities(true, -1, -1);
    }

    public List<Telefone> findTelefoneEntities(int maxResults, int firstResult) {
        return findTelefoneEntities(false, maxResults, firstResult);
    }

    private List<Telefone> findTelefoneEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Telefone.class));
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

    public Telefone findTelefone(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Telefone.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelefoneCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Telefone> rt = cq.from(Telefone.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
