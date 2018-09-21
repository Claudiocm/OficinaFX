package dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.ItensservicosJpaController;
import jpa.exceptions.NonexistentEntityException;
import modelo.Itensservicos;
import modelo.Servicos;

/**
 *
 * @author estagio
 */
public class ItensDAO implements InterfaceDAO<Itensservicos> {

    private final ItensservicosJpaController jpa;
    private final EntityManagerFactory emf;

    public ItensDAO() {
        emf = Persistence.createEntityManagerFactory("oficinaFXPU");
        jpa = new ItensservicosJpaController(emf);
    }

    @Override
    public void salvar(Itensservicos t) {
        jpa.create(t);
    }

    @Override
    public void atualizar(Itensservicos t) {
        try {
            jpa.edit(t);
        } catch (Exception ex) {
            Logger.getLogger(ItensDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void apagar(int id) {
        try {
            jpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ItensDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Itensservicos buscarId(int id) {
        return jpa.findItensservicos(id);
    }

    @Override
    public List<Itensservicos> buscarTodos() {
        return jpa.findItensservicosEntities();
    }
    
    public List<Itensservicos> listarServicos(Servicos servicos){
        return jpa.findItensservicos(servicos);
    }

}
