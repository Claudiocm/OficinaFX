package dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.ClienteJpaController;
import jpa.exceptions.NonexistentEntityException;
import modelo.Cliente;

/**
 *
 * @author estagio
 */
public class ClienteDAO implements InterfaceDAO<Cliente> {

    private final ClienteJpaController jpa;
    private final EntityManagerFactory emf;

    public ClienteDAO() {
        emf = Persistence.createEntityManagerFactory("oficinaFXPU");
        jpa = new ClienteJpaController(emf);
    }

    @Override
    public void salvar(Cliente t) {
        jpa.create(t);
    }

    @Override
    public void atualizar(Cliente t) {
        try {
            jpa.edit(t);
        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void apagar(int id) {
        try {
            jpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Cliente buscarId(int id) {
        return jpa.findCliente(id);
    }

    @Override
    public List<Cliente> buscarTodos() {
        return jpa.findClienteEntities();
    }

}
