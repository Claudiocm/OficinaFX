package dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.MecanicoJpaController;
import jpa.exceptions.NonexistentEntityException;
import modelo.Mecanico;

/**
 *
 * @author estagio
 */
public class MecanicoDAO implements InterfaceDAO<Mecanico> {

    private final MecanicoJpaController jpa;
    private final EntityManagerFactory emf;

    public MecanicoDAO() {
        emf = Persistence.createEntityManagerFactory("oficinaFXPU");
        jpa = new MecanicoJpaController(emf);
    }

    @Override
    public void salvar(Mecanico t) {
        jpa.create(t);

    }

    @Override
    public void atualizar(Mecanico t) {
        try {
            jpa.edit(t);
        } catch (Exception ex) {
            Logger.getLogger(MecanicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void apagar(int id) {
        try {
            jpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(MecanicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Mecanico buscarId(int id) {
        return jpa.findMecanico(id);
    }

    @Override
    public List<Mecanico> buscarTodos() {
        return jpa.findMecanicoEntities();
    }

}
