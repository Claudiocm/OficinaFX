package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class OficinaDAO {

    private final EntityManager em;
    private final EntityManagerFactory emf;

    public OficinaDAO() {
        emf = Persistence.createEntityManagerFactory("oficinaFXPU");
        em = emf.createEntityManager();
    }

    public void close() {
        em.close();
    }
    
    public void open(){
        em.getTransaction();
    }

}
