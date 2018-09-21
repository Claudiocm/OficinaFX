package modelo;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author estagio
 */
@Entity
@Table(name = "itensservicos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Itensservicos.findAll", query = "SELECT i FROM Itensservicos i"),
    @NamedQuery(name = "Itensservicos.findByItesId", query = "SELECT i FROM Itensservicos i WHERE i.itesId = :itesId"),
    @NamedQuery(name = "Itensservicos.findByItesQuantidade", query = "SELECT i FROM Itensservicos i WHERE i.itesQuantidade = :itesQuantidade"),
    @NamedQuery(name = "Itensservicos.findByItesValor", query = "SELECT i FROM Itensservicos i WHERE i.itesValor = :itesValor")})
public class Itensservicos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ites_id")
    private Integer itesId;
    @Column(name = "ites_quantidade")
    private Integer itesQuantidade;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ites_valor")
    private Double itesValor;
    @JoinColumn(name = "ites_ordemservico", referencedColumnName = "ord_id")
    @ManyToOne
    private Ordemservico itesOrdemservico;
    @JoinColumn(name = "ites_servico", referencedColumnName = "ser_codigo")
    @ManyToOne
    private Servicos itesServico;

    public Itensservicos() {
    }

    public Itensservicos(Integer itesId) {
        this.itesId = itesId;
    }

    public Integer getItesId() {
        return itesId;
    }

    public void setItesId(Integer itesId) {
        this.itesId = itesId;
    }

    public Integer getItesQuantidade() {
         return itesQuantidade;
    }

    public void setItesQuantidade(Integer itesQuantidade) {
        this.itesQuantidade = itesQuantidade;
    }

    public Double getItesValor() {
        itesValor = itesServico.getSerPreco();
        return  itesValor;
    }

    public void setItesValor(Double itesValor) {
        this.itesValor = itesValor;
    }

    public Ordemservico getItesOrdemservico() {
        return itesOrdemservico;
    }

    public void setItesOrdemservico(Ordemservico itesOrdemservico) {
        this.itesOrdemservico = itesOrdemservico;
    }

    public Servicos getItesServico() {
        return itesServico;
    }

    public void setItesServico(Servicos itesServico) {
        this.itesServico = itesServico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itesId != null ? itesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Itensservicos)) {
            return false;
        }
        Itensservicos other = (Itensservicos) object;
        if ((this.itesId == null && other.itesId != null) || (this.itesId != null && !this.itesId.equals(other.itesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return itesId + " "+itesServico;
    }
    
}
