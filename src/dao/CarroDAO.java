package dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.CarroJpaController;
import jpa.exceptions.NonexistentEntityException;
import modelo.Carro;

/**
 *
 * @author estagio
 */
public class CarroDAO implements InterfaceDAO<Carro> {

    private final CarroJpaController jpa;
    private final EntityManagerFactory emf;

    public CarroDAO() {
        emf = Persistence.createEntityManagerFactory("oficinaFXPU");
        jpa = new CarroJpaController(emf);
    }

    @Override
    public void salvar(Carro t) {
        jpa.create(t);
    }

    @Override
    public void atualizar(Carro t) {
        try {
            jpa.edit(t);
        } catch (Exception ex) {
            Logger.getLogger(CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void apagar(int id) {
        try {
            jpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Carro buscarId(int id) {
        return jpa.findCarro(id);
    }

    public Carro buscarIdCarro(Carro carro){
        return jpa.findCarro(carro);
    }
    
    @Override
    public List<Carro> buscarTodos() {
        return jpa.findCarroEntities();
    }
    
    public List<Carro> listarPorPlaca(String nome) {
        Stream<Carro> streamCarro = this.buscarTodos().stream();
        return streamCarro.filter(m -> !m.getCarPlaca().trim().isEmpty() && m.getCarPlaca().trim().toLowerCase().contains(nome.toLowerCase())).collect(Collectors.toList());

    }

}
