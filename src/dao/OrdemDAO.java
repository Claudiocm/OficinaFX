package dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.OrdemservicoJpaController;
import jpa.exceptions.NonexistentEntityException;
import modelo.Ordemservico;

/**
 *
 * @author estagio
 */
public class OrdemDAO implements InterfaceDAO<Ordemservico> {

    private final OrdemservicoJpaController jpa;
    private final EntityManagerFactory emf;

    public OrdemDAO() {
        emf = Persistence.createEntityManagerFactory("oficinaFXPU");
        jpa = new OrdemservicoJpaController(emf);
    }

    @Override
    public void salvar(Ordemservico t) {
        jpa.create(t);
    }

    @Override
    public void atualizar(Ordemservico t) {
        try {
            jpa.edit(t);
        } catch (Exception ex) {
            Logger.getLogger(OrdemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void apagar(int id) {
        try {
            jpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(OrdemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Ordemservico buscarId(int id) {
        return jpa.findOrdemservico(id);
    }

    @Override
    public List<Ordemservico> buscarTodos() {
        return jpa.findOrdemservicoEntities();
    }
    
    public int buscaNumero(){
        return jpa.getOrdemservicoContaNumero()+1;
    }

}
