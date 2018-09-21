package jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Servicos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpa.exceptions.NonexistentEntityException;
import modelo.Fornecedor;
import modelo.Produtofornecedor;

/**
 *
 * @author estagio
 */
public class ProdutofornecedorJpaController implements Serializable {

    public ProdutofornecedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Produtofornecedor produtofornecedor) {
        if (produtofornecedor.getServicosList() == null) {
            produtofornecedor.setServicosList(new ArrayList<Servicos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Servicos> attachedServicosList = new ArrayList<Servicos>();
            for (Servicos servicosListServicosToAttach : produtofornecedor.getServicosList()) {
                servicosListServicosToAttach = em.getReference(servicosListServicosToAttach.getClass(), servicosListServicosToAttach.getSerCodigo());
                attachedServicosList.add(servicosListServicosToAttach);
            }
            produtofornecedor.setServicosList(attachedServicosList);
            em.persist(produtofornecedor);
            for (Servicos servicosListServicos : produtofornecedor.getServicosList()) {
                Produtofornecedor oldSerProdutoOfServicosListServicos = servicosListServicos.getSerProduto();
                servicosListServicos.setSerProduto(produtofornecedor);
                servicosListServicos = em.merge(servicosListServicos);
                if (oldSerProdutoOfServicosListServicos != null) {
                    oldSerProdutoOfServicosListServicos.getServicosList().remove(servicosListServicos);
                    oldSerProdutoOfServicosListServicos = em.merge(oldSerProdutoOfServicosListServicos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Produtofornecedor produtofornecedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produtofornecedor persistentProdutofornecedor = em.find(Produtofornecedor.class, produtofornecedor.getProfId());
            List<Servicos> servicosListOld = persistentProdutofornecedor.getServicosList();
            List<Servicos> servicosListNew = produtofornecedor.getServicosList();
            List<Servicos> attachedServicosListNew = new ArrayList<Servicos>();
            for (Servicos servicosListNewServicosToAttach : servicosListNew) {
                servicosListNewServicosToAttach = em.getReference(servicosListNewServicosToAttach.getClass(), servicosListNewServicosToAttach.getSerCodigo());
                attachedServicosListNew.add(servicosListNewServicosToAttach);
            }
            servicosListNew = attachedServicosListNew;
            produtofornecedor.setServicosList(servicosListNew);
            produtofornecedor = em.merge(produtofornecedor);
            for (Servicos servicosListOldServicos : servicosListOld) {
                if (!servicosListNew.contains(servicosListOldServicos)) {
                    servicosListOldServicos.setSerProduto(null);
                    servicosListOldServicos = em.merge(servicosListOldServicos);
                }
            }
            for (Servicos servicosListNewServicos : servicosListNew) {
                if (!servicosListOld.contains(servicosListNewServicos)) {
                    Produtofornecedor oldSerProdutoOfServicosListNewServicos = servicosListNewServicos.getSerProduto();
                    servicosListNewServicos.setSerProduto(produtofornecedor);
                    servicosListNewServicos = em.merge(servicosListNewServicos);
                    if (oldSerProdutoOfServicosListNewServicos != null && !oldSerProdutoOfServicosListNewServicos.equals(produtofornecedor)) {
                        oldSerProdutoOfServicosListNewServicos.getServicosList().remove(servicosListNewServicos);
                        oldSerProdutoOfServicosListNewServicos = em.merge(oldSerProdutoOfServicosListNewServicos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = produtofornecedor.getProfId();
                if (findProdutofornecedor(id) == null) {
                    throw new NonexistentEntityException("The produtofornecedor with id " + id + " no longer exists.");
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
            Produtofornecedor produtofornecedor;
            try {
                produtofornecedor = em.getReference(Produtofornecedor.class, id);
                produtofornecedor.getProfId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The produtofornecedor with id " + id + " no longer exists.", enfe);
            }
            List<Servicos> servicosList = produtofornecedor.getServicosList();
            for (Servicos servicosListServicos : servicosList) {
                servicosListServicos.setSerProduto(null);
                servicosListServicos = em.merge(servicosListServicos);
            }
            em.remove(produtofornecedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Produtofornecedor> findProdutofornecedorEntities() {
        return findProdutofornecedorEntities(true, -1, -1);
    }

    public List<Produtofornecedor> findProdutofornecedorEntities(int maxResults, int firstResult) {
        return findProdutofornecedorEntities(false, maxResults, firstResult);
    }

    private List<Produtofornecedor> findProdutofornecedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Produtofornecedor.class));
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

    public Produtofornecedor findProdutofornecedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Produtofornecedor.class, id);
        } finally {
            em.close();
        }
    }
    
    public List<Produtofornecedor> findProdutofornecedor(Fornecedor f) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("Select f from Fornecedor where f.for_id = f.pro_id").setParameter("for_id", f.getForId()).getResultList();
        } finally {
            em.close();
        }
    }

    public int getProdutofornecedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Produtofornecedor> rt = cq.from(Produtofornecedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
