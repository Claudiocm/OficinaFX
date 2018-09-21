package dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.TelefoneJpaController;
import jpa.exceptions.NonexistentEntityException;
import modelo.Telefone;

public class TelefoneDAO implements InterfaceDAO<Telefone> {

    private final TelefoneJpaController jpa;
    private final EntityManagerFactory emf;

    public TelefoneDAO() {
        emf = Persistence.createEntityManagerFactory("oficinaFXPU");
        jpa = new TelefoneJpaController(emf);
    }

    @Override
    public void salvar(Telefone t) {
        jpa.create(t);
    }

    @Override
    public void atualizar(Telefone t) {
        try {
            jpa.edit(t);
        } catch (Exception ex) {
            Logger.getLogger(TelefoneDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void apagar(int id) {
        try {
            jpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TelefoneDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Telefone buscarId(int id) {
        return jpa.findTelefone(id);
    }

    @Override
    public List<Telefone> buscarTodos() {
        return jpa.findTelefoneEntities();
    }

}
