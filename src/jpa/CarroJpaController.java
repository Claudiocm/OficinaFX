/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Cliente;
import modelo.Ordemservico;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpa.exceptions.NonexistentEntityException;
import modelo.Carro;

/**
 *
 * @author estagio
 */
public class CarroJpaController implements Serializable {

    public CarroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Carro carro) {
        if (carro.getOrdemservicoList() == null) {
            carro.setOrdemservicoList(new ArrayList<Ordemservico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente carCliente = carro.getCarCliente();
            if (carCliente != null) {
                carCliente = em.getReference(carCliente.getClass(), carCliente.getCliId());
                carro.setCarCliente(carCliente);
            }
            List<Ordemservico> attachedOrdemservicoList = new ArrayList<Ordemservico>();
            for (Ordemservico ordemservicoListOrdemservicoToAttach : carro.getOrdemservicoList()) {
                ordemservicoListOrdemservicoToAttach = em.getReference(ordemservicoListOrdemservicoToAttach.getClass(), ordemservicoListOrdemservicoToAttach.getOrdId());
                attachedOrdemservicoList.add(ordemservicoListOrdemservicoToAttach);
            }
            carro.setOrdemservicoList(attachedOrdemservicoList);
            em.persist(carro);
            if (carCliente != null) {
                carCliente.getCarroList().add(carro);
                carCliente = em.merge(carCliente);
            }
            for (Ordemservico ordemservicoListOrdemservico : carro.getOrdemservicoList()) {
                Carro oldOrdCarroOfOrdemservicoListOrdemservico = ordemservicoListOrdemservico.getOrdCarro();
                ordemservicoListOrdemservico.setOrdCarro(carro);
                ordemservicoListOrdemservico = em.merge(ordemservicoListOrdemservico);
                if (oldOrdCarroOfOrdemservicoListOrdemservico != null) {
                    oldOrdCarroOfOrdemservicoListOrdemservico.getOrdemservicoList().remove(ordemservicoListOrdemservico);
                    oldOrdCarroOfOrdemservicoListOrdemservico = em.merge(oldOrdCarroOfOrdemservicoListOrdemservico);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Carro carro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carro persistentCarro = em.find(Carro.class, carro.getCarId());
            Cliente carClienteOld = persistentCarro.getCarCliente();
            Cliente carClienteNew = carro.getCarCliente();
            List<Ordemservico> ordemservicoListOld = persistentCarro.getOrdemservicoList();
            List<Ordemservico> ordemservicoListNew = carro.getOrdemservicoList();
            if (carClienteNew != null) {
                carClienteNew = em.getReference(carClienteNew.getClass(), carClienteNew.getCliId());
                carro.setCarCliente(carClienteNew);
            }
            List<Ordemservico> attachedOrdemservicoListNew = new ArrayList<Ordemservico>();
            for (Ordemservico ordemservicoListNewOrdemservicoToAttach : ordemservicoListNew) {
                ordemservicoListNewOrdemservicoToAttach = em.getReference(ordemservicoListNewOrdemservicoToAttach.getClass(), ordemservicoListNewOrdemservicoToAttach.getOrdId());
                attachedOrdemservicoListNew.add(ordemservicoListNewOrdemservicoToAttach);
            }
            ordemservicoListNew = attachedOrdemservicoListNew;
            carro.setOrdemservicoList(ordemservicoListNew);
            carro = em.merge(carro);
            if (carClienteOld != null && !carClienteOld.equals(carClienteNew)) {
                carClienteOld.getCarroList().remove(carro);
                carClienteOld = em.merge(carClienteOld);
            }
            if (carClienteNew != null && !carClienteNew.equals(carClienteOld)) {
                carClienteNew.getCarroList().add(carro);
                carClienteNew = em.merge(carClienteNew);
            }
            for (Ordemservico ordemservicoListOldOrdemservico : ordemservicoListOld) {
                if (!ordemservicoListNew.contains(ordemservicoListOldOrdemservico)) {
                    ordemservicoListOldOrdemservico.setOrdCarro(null);
                    ordemservicoListOldOrdemservico = em.merge(ordemservicoListOldOrdemservico);
                }
            }
            for (Ordemservico ordemservicoListNewOrdemservico : ordemservicoListNew) {
                if (!ordemservicoListOld.contains(ordemservicoListNewOrdemservico)) {
                    Carro oldOrdCarroOfOrdemservicoListNewOrdemservico = ordemservicoListNewOrdemservico.getOrdCarro();
                    ordemservicoListNewOrdemservico.setOrdCarro(carro);
                    ordemservicoListNewOrdemservico = em.merge(ordemservicoListNewOrdemservico);
                    if (oldOrdCarroOfOrdemservicoListNewOrdemservico != null && !oldOrdCarroOfOrdemservicoListNewOrdemservico.equals(carro)) {
                        oldOrdCarroOfOrdemservicoListNewOrdemservico.getOrdemservicoList().remove(ordemservicoListNewOrdemservico);
                        oldOrdCarroOfOrdemservicoListNewOrdemservico = em.merge(oldOrdCarroOfOrdemservicoListNewOrdemservico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = carro.getCarId();
                if (findCarro(id) == null) {
                    throw new NonexistentEntityException("The carro with id " + id + " no longer exists.");
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
            Carro carro;
            try {
                carro = em.getReference(Carro.class, id);
                carro.getCarId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carro with id " + id + " no longer exists.", enfe);
            }
            Cliente carCliente = carro.getCarCliente();
            if (carCliente != null) {
                carCliente.getCarroList().remove(carro);
                carCliente = em.merge(carCliente);
            }
            List<Ordemservico> ordemservicoList = carro.getOrdemservicoList();
            for (Ordemservico ordemservicoListOrdemservico : ordemservicoList) {
                ordemservicoListOrdemservico.setOrdCarro(null);
                ordemservicoListOrdemservico = em.merge(ordemservicoListOrdemservico);
            }
            em.remove(carro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Carro> findCarroEntities() {
        return findCarroEntities(true, -1, -1);
    }

    public List<Carro> findCarroEntities(int maxResults, int firstResult) {
        return findCarroEntities(false, maxResults, firstResult);
    }

    private List<Carro> findCarroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Carro.class));
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

    public Carro findCarro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Carro.class, id);
        } finally {
            em.close();
        }
    }
    
    public Carro findCarro(Carro c){
        EntityManager em = getEntityManager();
        try{
            return em.find(Carro.class, c);
        }finally{
            em.close();
        }
    }

    public int getCarroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Carro> rt = cq.from(Carro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
