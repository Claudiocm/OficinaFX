package dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.ProdutoJpaController;
import jpa.exceptions.NonexistentEntityException;
import modelo.Fornecedor;
import modelo.Produto;

public class ProdutoDAO implements InterfaceDAO<Produto> {

    private final ProdutoJpaController jpa;
    private final EntityManagerFactory emf;

    public ProdutoDAO() {
        emf = Persistence.createEntityManagerFactory("oficinaFXPU");
        jpa = new ProdutoJpaController(emf);
    }

    @Override
    public void salvar(Produto t) {
        jpa.create(t);
    }

    @Override
    public void atualizar(Produto t) {
        try {
            jpa.edit(t);
        } catch (Exception ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void apagar(int id) {
        try {
            jpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Produto buscarId(int id) {
        return jpa.findProduto(id);
    }

    @Override
    public List<Produto> buscarTodos() {
        return jpa.findProdutoEntities();
    }

    public List<Produto> listarPorFornecedor(Fornecedor nome) {
        Stream<Produto> streamProduto = this.buscarTodos().stream();
        return streamProduto.filter(m -> !m.getProNome().trim().isEmpty() && m.getProNome().trim().toLowerCase().contains(nome.getForFantasia())).collect(Collectors.toList());

    }
}
