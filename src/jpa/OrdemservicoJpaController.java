package jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Carro;
import modelo.Cliente;
import modelo.Mecanico;
import modelo.Caixa;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpa.exceptions.NonexistentEntityException;
import modelo.Itensservicos;
import modelo.Ordemservico;

/**
 *
 * @author estagio
 */
public class OrdemservicoJpaController implements Serializable {

    public OrdemservicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ordemservico ordemservico) {
        if (ordemservico.getCaixaList() == null) {
            ordemservico.setCaixaList(new ArrayList<Caixa>());
        }
        if (ordemservico.getItensservicosList() == null) {
            ordemservico.setItensservicosList(new ArrayList<Itensservicos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carro ordCarro = ordemservico.getOrdCarro();
            if (ordCarro != null) {
                ordCarro = em.getReference(ordCarro.getClass(), ordCarro.getCarId());
                ordemservico.setOrdCarro(ordCarro);
            }
            Cliente ordCliente = ordemservico.getOrdCliente();
            if (ordCliente != null) {
                ordCliente = em.getReference(ordCliente.getClass(), ordCliente.getCliId());
                ordemservico.setOrdCliente(ordCliente);
            }
            Mecanico ordMecanico = ordemservico.getOrdMecanico();
            if (ordMecanico != null) {
                ordMecanico = em.getReference(ordMecanico.getClass(), ordMecanico.getMecId());
                ordemservico.setOrdMecanico(ordMecanico);
            }
            List<Caixa> attachedCaixaList = new ArrayList<Caixa>();
            for (Caixa caixaListCaixaToAttach : ordemservico.getCaixaList()) {
                caixaListCaixaToAttach = em.getReference(caixaListCaixaToAttach.getClass(), caixaListCaixaToAttach.getCaId());
                attachedCaixaList.add(caixaListCaixaToAttach);
            }
            ordemservico.setCaixaList(attachedCaixaList);
            List<Itensservicos> attachedItensservicosList = new ArrayList<Itensservicos>();
            for (Itensservicos itensservicosListItensservicosToAttach : ordemservico.getItensservicosList()) {
                itensservicosListItensservicosToAttach = em.getReference(itensservicosListItensservicosToAttach.getClass(), itensservicosListItensservicosToAttach.getItesId());
                attachedItensservicosList.add(itensservicosListItensservicosToAttach);
            }
            ordemservico.setItensservicosList(attachedItensservicosList);
            em.persist(ordemservico);
            if (ordCarro != null) {
                ordCarro.getOrdemservicoList().add(ordemservico);
                ordCarro = em.merge(ordCarro);
            }
            if (ordCliente != null) {
                ordCliente.getOrdemservicoList().add(ordemservico);
                ordCliente = em.merge(ordCliente);
            }
            if (ordMecanico != null) {
                ordMecanico.getOrdemservicoList().add(ordemservico);
                ordMecanico = em.merge(ordMecanico);
            }
            for (Caixa caixaListCaixa : ordemservico.getCaixaList()) {
                Ordemservico oldCaOrdemservicoOfCaixaListCaixa = caixaListCaixa.getCaOrdemservico();
                caixaListCaixa.setCaOrdemservico(ordemservico);
                caixaListCaixa = em.merge(caixaListCaixa);
                if (oldCaOrdemservicoOfCaixaListCaixa != null) {
                    oldCaOrdemservicoOfCaixaListCaixa.getCaixaList().remove(caixaListCaixa);
                    oldCaOrdemservicoOfCaixaListCaixa = em.merge(oldCaOrdemservicoOfCaixaListCaixa);
                }
            }
            for (Itensservicos itensservicosListItensservicos : ordemservico.getItensservicosList()) {
                Ordemservico oldItesOrdemservicoOfItensservicosListItensservicos = itensservicosListItensservicos.getItesOrdemservico();
                itensservicosListItensservicos.setItesOrdemservico(ordemservico);
                itensservicosListItensservicos = em.merge(itensservicosListItensservicos);
                if (oldItesOrdemservicoOfItensservicosListItensservicos != null) {
                    oldItesOrdemservicoOfItensservicosListItensservicos.getItensservicosList().remove(itensservicosListItensservicos);
                    oldItesOrdemservicoOfItensservicosListItensservicos = em.merge(oldItesOrdemservicoOfItensservicosListItensservicos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ordemservico ordemservico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ordemservico persistentOrdemservico = em.find(Ordemservico.class, ordemservico.getOrdId());
            Carro ordCarroOld = persistentOrdemservico.getOrdCarro();
            Carro ordCarroNew = ordemservico.getOrdCarro();
            Cliente ordClienteOld = persistentOrdemservico.getOrdCliente();
            Cliente ordClienteNew = ordemservico.getOrdCliente();
            Mecanico ordMecanicoOld = persistentOrdemservico.getOrdMecanico();
            Mecanico ordMecanicoNew = ordemservico.getOrdMecanico();
            List<Caixa> caixaListOld = persistentOrdemservico.getCaixaList();
            List<Caixa> caixaListNew = ordemservico.getCaixaList();
            List<Itensservicos> itensservicosListOld = persistentOrdemservico.getItensservicosList();
            List<Itensservicos> itensservicosListNew = ordemservico.getItensservicosList();
            if (ordCarroNew != null) {
                ordCarroNew = em.getReference(ordCarroNew.getClass(), ordCarroNew.getCarId());
                ordemservico.setOrdCarro(ordCarroNew);
            }
            if (ordClienteNew != null) {
                ordClienteNew = em.getReference(ordClienteNew.getClass(), ordClienteNew.getCliId());
                ordemservico.setOrdCliente(ordClienteNew);
            }
            if (ordMecanicoNew != null) {
                ordMecanicoNew = em.getReference(ordMecanicoNew.getClass(), ordMecanicoNew.getMecId());
                ordemservico.setOrdMecanico(ordMecanicoNew);
            }
            List<Caixa> attachedCaixaListNew = new ArrayList<Caixa>();
            for (Caixa caixaListNewCaixaToAttach : caixaListNew) {
                caixaListNewCaixaToAttach = em.getReference(caixaListNewCaixaToAttach.getClass(), caixaListNewCaixaToAttach.getCaId());
                attachedCaixaListNew.add(caixaListNewCaixaToAttach);
            }
            caixaListNew = attachedCaixaListNew;
            ordemservico.setCaixaList(caixaListNew);
            List<Itensservicos> attachedItensservicosListNew = new ArrayList<Itensservicos>();
            for (Itensservicos itensservicosListNewItensservicosToAttach : itensservicosListNew) {
                itensservicosListNewItensservicosToAttach = em.getReference(itensservicosListNewItensservicosToAttach.getClass(), itensservicosListNewItensservicosToAttach.getItesId());
                attachedItensservicosListNew.add(itensservicosListNewItensservicosToAttach);
            }
            itensservicosListNew = attachedItensservicosListNew;
            ordemservico.setItensservicosList(itensservicosListNew);
            ordemservico = em.merge(ordemservico);
            if (ordCarroOld != null && !ordCarroOld.equals(ordCarroNew)) {
                ordCarroOld.getOrdemservicoList().remove(ordemservico);
                ordCarroOld = em.merge(ordCarroOld);
            }
            if (ordCarroNew != null && !ordCarroNew.equals(ordCarroOld)) {
                ordCarroNew.getOrdemservicoList().add(ordemservico);
                ordCarroNew = em.merge(ordCarroNew);
            }
            if (ordClienteOld != null && !ordClienteOld.equals(ordClienteNew)) {
                ordClienteOld.getOrdemservicoList().remove(ordemservico);
                ordClienteOld = em.merge(ordClienteOld);
            }
            if (ordClienteNew != null && !ordClienteNew.equals(ordClienteOld)) {
                ordClienteNew.getOrdemservicoList().add(ordemservico);
                ordClienteNew = em.merge(ordClienteNew);
            }
            if (ordMecanicoOld != null && !ordMecanicoOld.equals(ordMecanicoNew)) {
                ordMecanicoOld.getOrdemservicoList().remove(ordemservico);
                ordMecanicoOld = em.merge(ordMecanicoOld);
            }
            if (ordMecanicoNew != null && !ordMecanicoNew.equals(ordMecanicoOld)) {
                ordMecanicoNew.getOrdemservicoList().add(ordemservico);
                ordMecanicoNew = em.merge(ordMecanicoNew);
            }
            for (Caixa caixaListOldCaixa : caixaListOld) {
                if (!caixaListNew.contains(caixaListOldCaixa)) {
                    caixaListOldCaixa.setCaOrdemservico(null);
                    caixaListOldCaixa = em.merge(caixaListOldCaixa);
                }
            }
            for (Caixa caixaListNewCaixa : caixaListNew) {
                if (!caixaListOld.contains(caixaListNewCaixa)) {
                    Ordemservico oldCaOrdemservicoOfCaixaListNewCaixa = caixaListNewCaixa.getCaOrdemservico();
                    caixaListNewCaixa.setCaOrdemservico(ordemservico);
                    caixaListNewCaixa = em.merge(caixaListNewCaixa);
                    if (oldCaOrdemservicoOfCaixaListNewCaixa != null && !oldCaOrdemservicoOfCaixaListNewCaixa.equals(ordemservico)) {
                        oldCaOrdemservicoOfCaixaListNewCaixa.getCaixaList().remove(caixaListNewCaixa);
                        oldCaOrdemservicoOfCaixaListNewCaixa = em.merge(oldCaOrdemservicoOfCaixaListNewCaixa);
                    }
                }
            }
            for (Itensservicos itensservicosListOldItensservicos : itensservicosListOld) {
                if (!itensservicosListNew.contains(itensservicosListOldItensservicos)) {
                    itensservicosListOldItensservicos.setItesOrdemservico(null);
                    itensservicosListOldItensservicos = em.merge(itensservicosListOldItensservicos);
                }
            }
            for (Itensservicos itensservicosListNewItensservicos : itensservicosListNew) {
                if (!itensservicosListOld.contains(itensservicosListNewItensservicos)) {
                    Ordemservico oldItesOrdemservicoOfItensservicosListNewItensservicos = itensservicosListNewItensservicos.getItesOrdemservico();
                    itensservicosListNewItensservicos.setItesOrdemservico(ordemservico);
                    itensservicosListNewItensservicos = em.merge(itensservicosListNewItensservicos);
                    if (oldItesOrdemservicoOfItensservicosListNewItensservicos != null && !oldItesOrdemservicoOfItensservicosListNewItensservicos.equals(ordemservico)) {
                        oldItesOrdemservicoOfItensservicosListNewItensservicos.getItensservicosList().remove(itensservicosListNewItensservicos);
                        oldItesOrdemservicoOfItensservicosListNewItensservicos = em.merge(oldItesOrdemservicoOfItensservicosListNewItensservicos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ordemservico.getOrdId();
                if (findOrdemservico(id) == null) {
                    throw new NonexistentEntityException("The ordemservico with id " + id + " no longer exists.");
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
            Ordemservico ordemservico;
            try {
                ordemservico = em.getReference(Ordemservico.class, id);
                ordemservico.getOrdId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ordemservico with id " + id + " no longer exists.", enfe);
            }
            Carro ordCarro = ordemservico.getOrdCarro();
            if (ordCarro != null) {
                ordCarro.getOrdemservicoList().remove(ordemservico);
                ordCarro = em.merge(ordCarro);
            }
            Cliente ordCliente = ordemservico.getOrdCliente();
            if (ordCliente != null) {
                ordCliente.getOrdemservicoList().remove(ordemservico);
                ordCliente = em.merge(ordCliente);
            }
            Mecanico ordMecanico = ordemservico.getOrdMecanico();
            if (ordMecanico != null) {
                ordMecanico.getOrdemservicoList().remove(ordemservico);
                ordMecanico = em.merge(ordMecanico);
            }
            List<Caixa> caixaList = ordemservico.getCaixaList();
            for (Caixa caixaListCaixa : caixaList) {
                caixaListCaixa.setCaOrdemservico(null);
                caixaListCaixa = em.merge(caixaListCaixa);
            }
            List<Itensservicos> itensservicosList = ordemservico.getItensservicosList();
            for (Itensservicos itensservicosListItensservicos : itensservicosList) {
                itensservicosListItensservicos.setItesOrdemservico(null);
                itensservicosListItensservicos = em.merge(itensservicosListItensservicos);
            }
            em.remove(ordemservico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ordemservico> findOrdemservicoEntities() {
        return findOrdemservicoEntities(true, -1, -1);
    }

    public List<Ordemservico> findOrdemservicoEntities(int maxResults, int firstResult) {
        return findOrdemservicoEntities(false, maxResults, firstResult);
    }

    private List<Ordemservico> findOrdemservicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ordemservico.class));
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

    public Ordemservico findOrdemservico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ordemservico.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdemservicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ordemservico> rt = cq.from(Ordemservico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public int getOrdemservicoContaNumero() {
        EntityManager em = getEntityManager();
        try {
            String query = "Select MAX(ord_id) from ordemservico";
            //CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Query q = em.createQuery(query);
            return ((Integer) q.getSingleResult());
        } finally {
            em.close();
        }
    }
}
