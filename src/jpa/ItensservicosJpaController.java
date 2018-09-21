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
import modelo.Itensservicos;
import modelo.Ordemservico;
import modelo.Servicos;

/**
 *
 * @author estagio
 */
public class ItensservicosJpaController implements Serializable {

    public ItensservicosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Itensservicos itensservicos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ordemservico itesOrdemservico = itensservicos.getItesOrdemservico();
            if (itesOrdemservico != null) {
                itesOrdemservico = em.getReference(itesOrdemservico.getClass(), itesOrdemservico.getOrdId());
                itensservicos.setItesOrdemservico(itesOrdemservico);
            }
            em.persist(itensservicos);
            if (itesOrdemservico != null) {
                itesOrdemservico.getItensservicosList().add(itensservicos);
                itesOrdemservico = em.merge(itesOrdemservico);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Itensservicos itensservicos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Itensservicos persistentItensservicos = em.find(Itensservicos.class, itensservicos.getItesId());
            Ordemservico itesOrdemservicoOld = persistentItensservicos.getItesOrdemservico();
            Ordemservico itesOrdemservicoNew = itensservicos.getItesOrdemservico();
            if (itesOrdemservicoNew != null) {
                itesOrdemservicoNew = em.getReference(itesOrdemservicoNew.getClass(), itesOrdemservicoNew.getOrdId());
                itensservicos.setItesOrdemservico(itesOrdemservicoNew);
            }
            itensservicos = em.merge(itensservicos);
            if (itesOrdemservicoOld != null && !itesOrdemservicoOld.equals(itesOrdemservicoNew)) {
                itesOrdemservicoOld.getItensservicosList().remove(itensservicos);
                itesOrdemservicoOld = em.merge(itesOrdemservicoOld);
            }
            if (itesOrdemservicoNew != null && !itesOrdemservicoNew.equals(itesOrdemservicoOld)) {
                itesOrdemservicoNew.getItensservicosList().add(itensservicos);
                itesOrdemservicoNew = em.merge(itesOrdemservicoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = itensservicos.getItesId();
                if (findItensservicos(id) == null) {
                    throw new NonexistentEntityException("The itensservicos with id " + id + " no longer exists.");
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
            Itensservicos itensservicos;
            try {
                itensservicos = em.getReference(Itensservicos.class, id);
                itensservicos.getItesId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itensservicos with id " + id + " no longer exists.", enfe);
            }
            Ordemservico itesOrdemservico = itensservicos.getItesOrdemservico();
            if (itesOrdemservico != null) {
                itesOrdemservico.getItensservicosList().remove(itensservicos);
                itesOrdemservico = em.merge(itesOrdemservico);
            }
            em.remove(itensservicos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Itensservicos> findItensservicosEntities() {
        return findItensservicosEntities(true, -1, -1);
    }

    public List<Itensservicos> findItensservicosEntities(int maxResults, int firstResult) {
        return findItensservicosEntities(false, maxResults, firstResult);
    }

    private List<Itensservicos> findItensservicosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Itensservicos.class));
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

    public Itensservicos findItensservicos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Itensservicos.class, id);
        } finally {
            em.close();
        }
    }

    public List<Itensservicos> findItensservicos(Servicos servico) {
        EntityManager em = getEntityManager();
        try {
           return em.createQuery("Select it from Itensservicos where it.servico.ites_id = :ites_id")
				.setParameter("ites_id", servico.getSerCodigo()).getResultList();
        } finally {
            em.close();
        }
        
        
    }
    
    public int getItensservicosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Itensservicos> rt = cq.from(Itensservicos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
