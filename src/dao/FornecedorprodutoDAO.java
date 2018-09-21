package dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.ProdutofornecedorJpaController;
import jpa.exceptions.NonexistentEntityException;

import modelo.Produtofornecedor;

/**
 *
 * @author estagio
 */
public class FornecedorprodutoDAO implements InterfaceDAO<Produtofornecedor> {

    private final ProdutofornecedorJpaController jpa;
    private final EntityManagerFactory emf;

    public FornecedorprodutoDAO() {
        emf = Persistence.createEntityManagerFactory("oficinaFXPU");
        jpa = new ProdutofornecedorJpaController(emf);
    }

    @Override
    public void salvar(Produtofornecedor t) {
        try {
            jpa.create(t);
        } catch (Exception ex) {
            Logger.getLogger(FornecedorprodutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void atualizar(Produtofornecedor t) {
        try {
            jpa.edit(t);
        } catch (Exception ex) {
            Logger.getLogger(FornecedorprodutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void apagar(int id) {
        try {
            jpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(FornecedorprodutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Produtofornecedor buscarId(int id) {
        return jpa.findProdutofornecedor(id);
    }

    @Override
    public List<Produtofornecedor> buscarTodos() {
        return jpa.findProdutofornecedorEntities();
    }

}
