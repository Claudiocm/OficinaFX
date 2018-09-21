package dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.ServicosJpaController;
import jpa.exceptions.NonexistentEntityException;
import modelo.Servicos;


public class ServicoDAO implements InterfaceDAO<Servicos>{
private final ServicosJpaController jpa;
private final EntityManagerFactory emf;

    public ServicoDAO() {
        emf = Persistence.createEntityManagerFactory("oficinaFXPU");
        jpa = new ServicosJpaController(emf);
    }

    @Override
    public void salvar(Servicos t) {
        jpa.create(t);
    }
    
    @Override
    public void atualizar(Servicos t) {
    try {
        jpa.edit(t);
    } catch (Exception ex) {
        Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    @Override
    public void apagar(int id) {
    try {
        jpa.destroy(id);
    } catch (NonexistentEntityException ex) {
        Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    @Override
    public Servicos buscarId(int id) {
        return jpa.findServicos(id);
    }

    @Override
    public List<Servicos> buscarTodos() {
        return jpa.findServicosEntities();
    }
    
}
