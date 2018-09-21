package modelo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author estagio
 */
@Entity
@Table(name = "ordemservico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ordemservico.findAll", query = "SELECT o FROM Ordemservico o"),
    @NamedQuery(name = "Ordemservico.findByOrdId", query = "SELECT o FROM Ordemservico o WHERE o.ordId = :ordId"),
    @NamedQuery(name = "Ordemservico.findByOrdData", query = "SELECT o FROM Ordemservico o WHERE o.ordData = :ordData"),
    @NamedQuery(name = "Ordemservico.findByOrdEntrega", query = "SELECT o FROM Ordemservico o WHERE o.ordEntrega = :ordEntrega"),
    @NamedQuery(name = "Ordemservico.findByOrdStatus", query = "SELECT o FROM Ordemservico o WHERE o.ordStatus = :ordStatus"),
    @NamedQuery(name = "Ordemservico.findByOrdTotal", query = "SELECT o FROM Ordemservico o WHERE o.ordTotal = :ordTotal")})
public class Ordemservico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ord_id")
    private Integer ordId;
    @Column(name = "ord_data")
    @Temporal(TemporalType.DATE)
    private Date ordData;
    @Column(name = "ord_entrega")
    @Temporal(TemporalType.DATE)
    private Date ordEntrega;
    @Column(name = "ord_status")
    private Character ordStatus;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ord_total")
    private Double ordTotal;
    @OneToMany(mappedBy = "caOrdemservico")
    private List<Caixa> caixaList;
    @JoinColumn(name = "ord_carro", referencedColumnName = "car_id")
    @ManyToOne
    private Carro ordCarro;
    @JoinColumn(name = "ord_cliente", referencedColumnName = "cli_id")
    @ManyToOne
    private Cliente ordCliente;
    @JoinColumn(name = "ord_mecanico", referencedColumnName = "mec_id")
    @ManyToOne
    private Mecanico ordMecanico;
    @OneToMany(mappedBy = "itesOrdemservico")
    private List<Itensservicos> itensservicosList;

    public Ordemservico() {
    }

    public Ordemservico(Integer ordId) {
        this.ordId = ordId;
    }

    public Integer getOrdId() {
        return ordId;
    }

    public void setOrdId(Integer ordId) {
        this.ordId = ordId;
    }

    public Date getOrdData() {
        return ordData;
    }

    public void setOrdData(Date ordData) {
        this.ordData = ordData;
    }

    public Date getOrdEntrega() {
        return ordEntrega;
    }

    public void setOrdEntrega(Date ordEntrega) {
        this.ordEntrega = ordEntrega;
    }

    public Character getOrdStatus() {
        return ordStatus;
    }

    public void setOrdStatus(Character ordStatus) {
        this.ordStatus = ordStatus;
    }

    public Double getOrdTotal() {
        double total= 0;
        for(Itensservicos i: itensservicosList){
            total = total + i.getItesValor();
            ordTotal  = total;
        }
        return ordTotal;
    }

    public void setOrdTotal(Double ordTotal) {
        this.ordTotal = ordTotal;
    }

    @XmlTransient
    public List<Caixa> getCaixaList() {
        return caixaList;
    }

    public void setCaixaList(List<Caixa> caixaList) {
        this.caixaList = caixaList;
    }

    public Carro getOrdCarro() {
        return ordCarro;
    }

    public void setOrdCarro(Carro ordCarro) {
        this.ordCarro = ordCarro;
    }

    public Cliente getOrdCliente() {
        return ordCliente;
    }

    public void setOrdCliente(Cliente ordCliente) {
        this.ordCliente = ordCliente;
    }

    public Mecanico getOrdMecanico() {
        return ordMecanico;
    }

    public void setOrdMecanico(Mecanico ordMecanico) {
        this.ordMecanico = ordMecanico;
    }

    @XmlTransient
    public List<Itensservicos> getItensservicosList() {
        return itensservicosList;
    }

    public void setItensservicosList(List<Itensservicos> itensservicosList) {
        this.itensservicosList = itensservicosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ordId != null ? ordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ordemservico)) {
            return false;
        }
        Ordemservico other = (Ordemservico) object;
        if ((this.ordId == null && other.ordId != null) || (this.ordId != null && !this.ordId.equals(other.ordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ordId + " ";
    }
    
}
