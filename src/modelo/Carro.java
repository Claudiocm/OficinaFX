
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author estagio
 */
@Entity
@Table(name = "carro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Carro.findAll", query = "SELECT c FROM Carro c"),
    @NamedQuery(name = "Carro.findByCarId", query = "SELECT c FROM Carro c WHERE c.carId = :carId"),
    @NamedQuery(name = "Carro.findByCarAno", query = "SELECT c FROM Carro c WHERE c.carAno = :carAno"),
    @NamedQuery(name = "Carro.findByCarChassi", query = "SELECT c FROM Carro c WHERE c.carChassi = :carChassi"),
    @NamedQuery(name = "Carro.findByCarMarca", query = "SELECT c FROM Carro c WHERE c.carMarca = :carMarca"),
    @NamedQuery(name = "Carro.findByCarModelo", query = "SELECT c FROM Carro c WHERE c.carModelo = :carModelo"),
    @NamedQuery(name = "Carro.findByCarPlaca", query = "SELECT c FROM Carro c WHERE c.carPlaca = :carPlaca")})
public class Carro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "car_id")
    private Integer carId;
    @Column(name = "car_ano")
    private Integer carAno;
    @Column(name = "car_chassi")
    private String carChassi;
    @Column(name = "car_marca")
    private String carMarca;
    @Column(name = "car_modelo")
    private String carModelo;
    @Column(name = "car_placa")
    private String carPlaca;
    @JoinColumn(name = "car_cliente", referencedColumnName = "cli_id")
    @ManyToOne
    private Cliente carCliente;
    @OneToMany(mappedBy = "ordCarro")
    private List<Ordemservico> ordemservicoList;

    public Carro() {
    }

    public Carro(Integer carId) {
        this.carId = carId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getCarAno() {
        return carAno;
    }

    public void setCarAno(Integer carAno) {
        this.carAno = carAno;
    }

    public String getCarChassi() {
        return carChassi;
    }

    public void setCarChassi(String carChassi) {
        this.carChassi = carChassi;
    }

    public String getCarMarca() {
        return carMarca;
    }

    public void setCarMarca(String carMarca) {
        this.carMarca = carMarca;
    }

    public String getCarModelo() {
        return carModelo;
    }

    public void setCarModelo(String carModelo) {
        this.carModelo = carModelo;
    }

    public String getCarPlaca() {
        return carPlaca;
    }

    public void setCarPlaca(String carPlaca) {
        this.carPlaca = carPlaca;
    }

    public Cliente getCarCliente() {
        return carCliente;
    }

    public void setCarCliente(Cliente carCliente) {
        this.carCliente = carCliente;
    }

    @XmlTransient
    public List<Ordemservico> getOrdemservicoList() {
        return ordemservicoList;
    }

    public void setOrdemservicoList(List<Ordemservico> ordemservicoList) {
        this.ordemservicoList = ordemservicoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (carId != null ? carId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Carro)) {
            return false;
        }
        Carro other = (Carro) object;
        if ((this.carId == null && other.carId != null) || (this.carId != null && !this.carId.equals(other.carId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return carModelo + " ";
    }
    
}
