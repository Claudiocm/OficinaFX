package dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.FornecedorJpaController;
import jpa.exceptions.NonexistentEntityException;
import modelo.Fornecedor;
import modelo.Produto;

/**
 *
 * @author estagio
 */
public class FornecedorDAO implements InterfaceDAO<Fornecedor> {

    private final FornecedorJpaController jpa;
    private final EntityManagerFactory emf;
    
    public FornecedorDAO() {
        emf = Persistence.createEntityManagerFactory("oficinaFXPU");
        jpa = new FornecedorJpaController(emf);
    }
    
    @Override
    public void salvar(Fornecedor t) {
        try {
            jpa.edit(t);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void atualizar(Fornecedor t) {
        try {
            jpa.edit(t);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void apagar(int id) {
        try {
            jpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Fornecedor buscarId(int id) {
        return jpa.findFornecedor(id);
    }
    
    @Override
    public List<Fornecedor> buscarTodos() {
        return jpa.findFornecedorEntities();
    }
    
    public List<Fornecedor> listarPorFornecedor(String nome) {
        Stream<Fornecedor> streamProduto = this.buscarTodos().stream();
        return streamProduto.filter(m -> !m.getForFantasia().trim().isEmpty() && m.getForFantasia().trim().toLowerCase().contains(nome.toLowerCase())).collect(Collectors.toList());

    }
    
}
