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
import modelo.Carro;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpa.exceptions.NonexistentEntityException;
import modelo.Cliente;
import modelo.Telefone;
import modelo.Ordemservico;

/**
 *
 * @author estagio
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getCarroList() == null) {
            cliente.setCarroList(new ArrayList<Carro>());
        }
        if (cliente.getTelefoneList() == null) {
            cliente.setTelefoneList(new ArrayList<Telefone>());
        }
        if (cliente.getOrdemservicoList() == null) {
            cliente.setOrdemservicoList(new ArrayList<Ordemservico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Carro> attachedCarroList = new ArrayList<Carro>();
            for (Carro carroListCarroToAttach : cliente.getCarroList()) {
                carroListCarroToAttach = em.getReference(carroListCarroToAttach.getClass(), carroListCarroToAttach.getCarId());
                attachedCarroList.add(carroListCarroToAttach);
            }
            cliente.setCarroList(attachedCarroList);
            List<Telefone> attachedTelefoneList = new ArrayList<Telefone>();
            for (Telefone telefoneListTelefoneToAttach : cliente.getTelefoneList()) {
                telefoneListTelefoneToAttach = em.getReference(telefoneListTelefoneToAttach.getClass(), telefoneListTelefoneToAttach.getTelId());
                attachedTelefoneList.add(telefoneListTelefoneToAttach);
            }
            cliente.setTelefoneList(attachedTelefoneList);
            List<Ordemservico> attachedOrdemservicoList = new ArrayList<Ordemservico>();
            for (Ordemservico ordemservicoListOrdemservicoToAttach : cliente.getOrdemservicoList()) {
                ordemservicoListOrdemservicoToAttach = em.getReference(ordemservicoListOrdemservicoToAttach.getClass(), ordemservicoListOrdemservicoToAttach.getOrdId());
                attachedOrdemservicoList.add(ordemservicoListOrdemservicoToAttach);
            }
            cliente.setOrdemservicoList(attachedOrdemservicoList);
            em.persist(cliente);
            for (Carro carroListCarro : cliente.getCarroList()) {
                Cliente oldCarClienteOfCarroListCarro = carroListCarro.getCarCliente();
                carroListCarro.setCarCliente(cliente);
                carroListCarro = em.merge(carroListCarro);
                if (oldCarClienteOfCarroListCarro != null) {
                    oldCarClienteOfCarroListCarro.getCarroList().remove(carroListCarro);
                    oldCarClienteOfCarroListCarro = em.merge(oldCarClienteOfCarroListCarro);
                }
            }
            for (Telefone telefoneListTelefone : cliente.getTelefoneList()) {
                Cliente oldTelClienteIdOfTelefoneListTelefone = telefoneListTelefone.getTelClienteId();
                telefoneListTelefone.setTelClienteId(cliente);
                telefoneListTelefone = em.merge(telefoneListTelefone);
                if (oldTelClienteIdOfTelefoneListTelefone != null) {
                    oldTelClienteIdOfTelefoneListTelefone.getTelefoneList().remove(telefoneListTelefone);
                    oldTelClienteIdOfTelefoneListTelefone = em.merge(oldTelClienteIdOfTelefoneListTelefone);
                }
            }
            for (Ordemservico ordemservicoListOrdemservico : cliente.getOrdemservicoList()) {
                Cliente oldOrdClienteOfOrdemservicoListOrdemservico = ordemservicoListOrdemservico.getOrdCliente();
                ordemservicoListOrdemservico.setOrdCliente(cliente);
                ordemservicoListOrdemservico = em.merge(ordemservicoListOrdemservico);
                if (oldOrdClienteOfOrdemservicoListOrdemservico != null) {
                    oldOrdClienteOfOrdemservicoListOrdemservico.getOrdemservicoList().remove(ordemservicoListOrdemservico);
                    oldOrdClienteOfOrdemservicoListOrdemservico = em.merge(oldOrdClienteOfOrdemservicoListOrdemservico);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getCliId());
            List<Carro> carroListOld = persistentCliente.getCarroList();
            List<Carro> carroListNew = cliente.getCarroList();
            List<Telefone> telefoneListOld = persistentCliente.getTelefoneList();
            List<Telefone> telefoneListNew = cliente.getTelefoneList();
            List<Ordemservico> ordemservicoListOld = persistentCliente.getOrdemservicoList();
            List<Ordemservico> ordemservicoListNew = cliente.getOrdemservicoList();
            List<Carro> attachedCarroListNew = new ArrayList<Carro>();
            for (Carro carroListNewCarroToAttach : carroListNew) {
                carroListNewCarroToAttach = em.getReference(carroListNewCarroToAttach.getClass(), carroListNewCarroToAttach.getCarId());
                attachedCarroListNew.add(carroListNewCarroToAttach);
            }
            carroListNew = attachedCarroListNew;
            cliente.setCarroList(carroListNew);
            List<Telefone> attachedTelefoneListNew = new ArrayList<Telefone>();
            for (Telefone telefoneListNewTelefoneToAttach : telefoneListNew) {
                telefoneListNewTelefoneToAttach = em.getReference(telefoneListNewTelefoneToAttach.getClass(), telefoneListNewTelefoneToAttach.getTelId());
                attachedTelefoneListNew.add(telefoneListNewTelefoneToAttach);
            }
            telefoneListNew = attachedTelefoneListNew;
            cliente.setTelefoneList(telefoneListNew);
            List<Ordemservico> attachedOrdemservicoListNew = new ArrayList<Ordemservico>();
            for (Ordemservico ordemservicoListNewOrdemservicoToAttach : ordemservicoListNew) {
                ordemservicoListNewOrdemservicoToAttach = em.getReference(ordemservicoListNewOrdemservicoToAttach.getClass(), ordemservicoListNewOrdemservicoToAttach.getOrdId());
                attachedOrdemservicoListNew.add(ordemservicoListNewOrdemservicoToAttach);
            }
            ordemservicoListNew = attachedOrdemservicoListNew;
            cliente.setOrdemservicoList(ordemservicoListNew);
            cliente = em.merge(cliente);
            for (Carro carroListOldCarro : carroListOld) {
                if (!carroListNew.contains(carroListOldCarro)) {
                    carroListOldCarro.setCarCliente(null);
                    carroListOldCarro = em.merge(carroListOldCarro);
                }
            }
            for (Carro carroListNewCarro : carroListNew) {
                if (!carroListOld.contains(carroListNewCarro)) {
                    Cliente oldCarClienteOfCarroListNewCarro = carroListNewCarro.getCarCliente();
                    carroListNewCarro.setCarCliente(cliente);
                    carroListNewCarro = em.merge(carroListNewCarro);
                    if (oldCarClienteOfCarroListNewCarro != null && !oldCarClienteOfCarroListNewCarro.equals(cliente)) {
                        oldCarClienteOfCarroListNewCarro.getCarroList().remove(carroListNewCarro);
                        oldCarClienteOfCarroListNewCarro = em.merge(oldCarClienteOfCarroListNewCarro);
                    }
                }
            }
            for (Telefone telefoneListOldTelefone : telefoneListOld) {
                if (!telefoneListNew.contains(telefoneListOldTelefone)) {
                    telefoneListOldTelefone.setTelClienteId(null);
                    telefoneListOldTelefone = em.merge(telefoneListOldTelefone);
                }
            }
            for (Telefone telefoneListNewTelefone : telefoneListNew) {
                if (!telefoneListOld.contains(telefoneListNewTelefone)) {
                    Cliente oldTelClienteIdOfTelefoneListNewTelefone = telefoneListNewTelefone.getTelClienteId();
                    telefoneListNewTelefone.setTelClienteId(cliente);
                    telefoneListNewTelefone = em.merge(telefoneListNewTelefone);
                    if (oldTelClienteIdOfTelefoneListNewTelefone != null && !oldTelClienteIdOfTelefoneListNewTelefone.equals(cliente)) {
                        oldTelClienteIdOfTelefoneListNewTelefone.getTelefoneList().remove(telefoneListNewTelefone);
                        oldTelClienteIdOfTelefoneListNewTelefone = em.merge(oldTelClienteIdOfTelefoneListNewTelefone);
                    }
                }
            }
            for (Ordemservico ordemservicoListOldOrdemservico : ordemservicoListOld) {
                if (!ordemservicoListNew.contains(ordemservicoListOldOrdemservico)) {
                    ordemservicoListOldOrdemservico.setOrdCliente(null);
                    ordemservicoListOldOrdemservico = em.merge(ordemservicoListOldOrdemservico);
                }
            }
            for (Ordemservico ordemservicoListNewOrdemservico : ordemservicoListNew) {
                if (!ordemservicoListOld.contains(ordemservicoListNewOrdemservico)) {
                    Cliente oldOrdClienteOfOrdemservicoListNewOrdemservico = ordemservicoListNewOrdemservico.getOrdCliente();
                    ordemservicoListNewOrdemservico.setOrdCliente(cliente);
                    ordemservicoListNewOrdemservico = em.merge(ordemservicoListNewOrdemservico);
                    if (oldOrdClienteOfOrdemservicoListNewOrdemservico != null && !oldOrdClienteOfOrdemservicoListNewOrdemservico.equals(cliente)) {
                        oldOrdClienteOfOrdemservicoListNewOrdemservico.getOrdemservicoList().remove(ordemservicoListNewOrdemservico);
                        oldOrdClienteOfOrdemservicoListNewOrdemservico = em.merge(oldOrdClienteOfOrdemservicoListNewOrdemservico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getCliId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getCliId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<Carro> carroList = cliente.getCarroList();
            for (Carro carroListCarro : carroList) {
                carroListCarro.setCarCliente(null);
                carroListCarro = em.merge(carroListCarro);
            }
            List<Telefone> telefoneList = cliente.getTelefoneList();
            for (Telefone telefoneListTelefone : telefoneList) {
                telefoneListTelefone.setTelClienteId(null);
                telefoneListTelefone = em.merge(telefoneListTelefone);
            }
            List<Ordemservico> ordemservicoList = cliente.getOrdemservicoList();
            for (Ordemservico ordemservicoListOrdemservico : ordemservicoList) {
                ordemservicoListOrdemservico.setOrdCliente(null);
                ordemservicoListOrdemservico = em.merge(ordemservicoListOrdemservico);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
