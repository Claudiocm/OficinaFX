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
import modelo.Telefone;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpa.exceptions.NonexistentEntityException;
import modelo.Mecanico;
import modelo.Ordemservico;

/**
 *
 * @author estagio
 */
public class MecanicoJpaController implements Serializable {

    public MecanicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mecanico mecanico) {
        if (mecanico.getTelefoneList() == null) {
            mecanico.setTelefoneList(new ArrayList<Telefone>());
        }
        if (mecanico.getOrdemservicoList() == null) {
            mecanico.setOrdemservicoList(new ArrayList<Ordemservico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Telefone> attachedTelefoneList = new ArrayList<Telefone>();
            for (Telefone telefoneListTelefoneToAttach : mecanico.getTelefoneList()) {
                telefoneListTelefoneToAttach = em.getReference(telefoneListTelefoneToAttach.getClass(), telefoneListTelefoneToAttach.getTelId());
                attachedTelefoneList.add(telefoneListTelefoneToAttach);
            }
            mecanico.setTelefoneList(attachedTelefoneList);
            List<Ordemservico> attachedOrdemservicoList = new ArrayList<Ordemservico>();
            for (Ordemservico ordemservicoListOrdemservicoToAttach : mecanico.getOrdemservicoList()) {
                ordemservicoListOrdemservicoToAttach = em.getReference(ordemservicoListOrdemservicoToAttach.getClass(), ordemservicoListOrdemservicoToAttach.getOrdId());
                attachedOrdemservicoList.add(ordemservicoListOrdemservicoToAttach);
            }
            mecanico.setOrdemservicoList(attachedOrdemservicoList);
            em.persist(mecanico);
            for (Telefone telefoneListTelefone : mecanico.getTelefoneList()) {
                Mecanico oldTelMecanicoIdOfTelefoneListTelefone = telefoneListTelefone.getTelMecanicoId();
                telefoneListTelefone.setTelMecanicoId(mecanico);
                telefoneListTelefone = em.merge(telefoneListTelefone);
                if (oldTelMecanicoIdOfTelefoneListTelefone != null) {
                    oldTelMecanicoIdOfTelefoneListTelefone.getTelefoneList().remove(telefoneListTelefone);
                    oldTelMecanicoIdOfTelefoneListTelefone = em.merge(oldTelMecanicoIdOfTelefoneListTelefone);
                }
            }
            for (Ordemservico ordemservicoListOrdemservico : mecanico.getOrdemservicoList()) {
                Mecanico oldOrdMecanicoOfOrdemservicoListOrdemservico = ordemservicoListOrdemservico.getOrdMecanico();
                ordemservicoListOrdemservico.setOrdMecanico(mecanico);
                ordemservicoListOrdemservico = em.merge(ordemservicoListOrdemservico);
                if (oldOrdMecanicoOfOrdemservicoListOrdemservico != null) {
                    oldOrdMecanicoOfOrdemservicoListOrdemservico.getOrdemservicoList().remove(ordemservicoListOrdemservico);
                    oldOrdMecanicoOfOrdemservicoListOrdemservico = em.merge(oldOrdMecanicoOfOrdemservicoListOrdemservico);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mecanico mecanico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mecanico persistentMecanico = em.find(Mecanico.class, mecanico.getMecId());
            List<Telefone> telefoneListOld = persistentMecanico.getTelefoneList();
            List<Telefone> telefoneListNew = mecanico.getTelefoneList();
            List<Ordemservico> ordemservicoListOld = persistentMecanico.getOrdemservicoList();
            List<Ordemservico> ordemservicoListNew = mecanico.getOrdemservicoList();
            List<Telefone> attachedTelefoneListNew = new ArrayList<Telefone>();
            for (Telefone telefoneListNewTelefoneToAttach : telefoneListNew) {
                telefoneListNewTelefoneToAttach = em.getReference(telefoneListNewTelefoneToAttach.getClass(), telefoneListNewTelefoneToAttach.getTelId());
                attachedTelefoneListNew.add(telefoneListNewTelefoneToAttach);
            }
            telefoneListNew = attachedTelefoneListNew;
            mecanico.setTelefoneList(telefoneListNew);
            List<Ordemservico> attachedOrdemservicoListNew = new ArrayList<Ordemservico>();
            for (Ordemservico ordemservicoListNewOrdemservicoToAttach : ordemservicoListNew) {
                ordemservicoListNewOrdemservicoToAttach = em.getReference(ordemservicoListNewOrdemservicoToAttach.getClass(), ordemservicoListNewOrdemservicoToAttach.getOrdId());
                attachedOrdemservicoListNew.add(ordemservicoListNewOrdemservicoToAttach);
            }
            ordemservicoListNew = attachedOrdemservicoListNew;
            mecanico.setOrdemservicoList(ordemservicoListNew);
            mecanico = em.merge(mecanico);
            for (Telefone telefoneListOldTelefone : telefoneListOld) {
                if (!telefoneListNew.contains(telefoneListOldTelefone)) {
                    telefoneListOldTelefone.setTelMecanicoId(null);
                    telefoneListOldTelefone = em.merge(telefoneListOldTelefone);
                }
            }
            for (Telefone telefoneListNewTelefone : telefoneListNew) {
                if (!telefoneListOld.contains(telefoneListNewTelefone)) {
                    Mecanico oldTelMecanicoIdOfTelefoneListNewTelefone = telefoneListNewTelefone.getTelMecanicoId();
                    telefoneListNewTelefone.setTelMecanicoId(mecanico);
                    telefoneListNewTelefone = em.merge(telefoneListNewTelefone);
                    if (oldTelMecanicoIdOfTelefoneListNewTelefone != null && !oldTelMecanicoIdOfTelefoneListNewTelefone.equals(mecanico)) {
                        oldTelMecanicoIdOfTelefoneListNewTelefone.getTelefoneList().remove(telefoneListNewTelefone);
                        oldTelMecanicoIdOfTelefoneListNewTelefone = em.merge(oldTelMecanicoIdOfTelefoneListNewTelefone);
                    }
                }
            }
            for (Ordemservico ordemservicoListOldOrdemservico : ordemservicoListOld) {
                if (!ordemservicoListNew.contains(ordemservicoListOldOrdemservico)) {
                    ordemservicoListOldOrdemservico.setOrdMecanico(null);
                    ordemservicoListOldOrdemservico = em.merge(ordemservicoListOldOrdemservico);
                }
            }
            for (Ordemservico ordemservicoListNewOrdemservico : ordemservicoListNew) {
                if (!ordemservicoListOld.contains(ordemservicoListNewOrdemservico)) {
                    Mecanico oldOrdMecanicoOfOrdemservicoListNewOrdemservico = ordemservicoListNewOrdemservico.getOrdMecanico();
                    ordemservicoListNewOrdemservico.setOrdMecanico(mecanico);
                    ordemservicoListNewOrdemservico = em.merge(ordemservicoListNewOrdemservico);
                    if (oldOrdMecanicoOfOrdemservicoListNewOrdemservico != null && !oldOrdMecanicoOfOrdemservicoListNewOrdemservico.equals(mecanico)) {
                        oldOrdMecanicoOfOrdemservicoListNewOrdemservico.getOrdemservicoList().remove(ordemservicoListNewOrdemservico);
                        oldOrdMecanicoOfOrdemservicoListNewOrdemservico = em.merge(oldOrdMecanicoOfOrdemservicoListNewOrdemservico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mecanico.getMecId();
                if (findMecanico(id) == null) {
                    throw new NonexistentEntityException("The mecanico with id " + id + " no longer exists.");
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
            Mecanico mecanico;
            try {
                mecanico = em.getReference(Mecanico.class, id);
                mecanico.getMecId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mecanico with id " + id + " no longer exists.", enfe);
            }
            List<Telefone> telefoneList = mecanico.getTelefoneList();
            for (Telefone telefoneListTelefone : telefoneList) {
                telefoneListTelefone.setTelMecanicoId(null);
                telefoneListTelefone = em.merge(telefoneListTelefone);
            }
            List<Ordemservico> ordemservicoList = mecanico.getOrdemservicoList();
            for (Ordemservico ordemservicoListOrdemservico : ordemservicoList) {
                ordemservicoListOrdemservico.setOrdMecanico(null);
                ordemservicoListOrdemservico = em.merge(ordemservicoListOrdemservico);
            }
            em.remove(mecanico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mecanico> findMecanicoEntities() {
        return findMecanicoEntities(true, -1, -1);
    }

    public List<Mecanico> findMecanicoEntities(int maxResults, int firstResult) {
        return findMecanicoEntities(false, maxResults, firstResult);
    }

    private List<Mecanico> findMecanicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mecanico.class));
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

    public Mecanico findMecanico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mecanico.class, id);
        } finally {
            em.close();
        }
    }

    public int getMecanicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mecanico> rt = cq.from(Mecanico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
