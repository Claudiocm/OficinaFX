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
@Table(name = "produtofornecedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produtofornecedor.findAll", query = "SELECT p FROM Produtofornecedor p"),
    @NamedQuery(name = "Produtofornecedor.findByProfId", query = "SELECT p FROM Produtofornecedor p WHERE p.profId = :profId"),
    @NamedQuery(name = "Produtofornecedor.findByProfPreco", query = "SELECT p FROM Produtofornecedor p WHERE p.profPreco = :profPreco")})
public class Produtofornecedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "prof_id")
    private Integer profId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "prof_preco")
    private Double profPreco;
    @OneToMany(mappedBy = "serProduto")
    private List<Servicos> servicosList;
    @JoinColumn(name = "prof_fornecedor", referencedColumnName = "for_id")
    @ManyToOne(optional = false)
    private Fornecedor profFornecedor;
    @JoinColumn(name = "prof_produto", referencedColumnName = "pro_id")
    @ManyToOne(optional = false)
    private Produto profProduto;

    public Produtofornecedor() {
    }

    public Produtofornecedor(Integer profId) {
        this.profId = profId;
    }

    public Integer getProfId() {
        return profId;
    }

    public void setProfId(Integer profId) {
        this.profId = profId;
    }

    public Double getProfPreco() {
        return profPreco;
    }

    public void setProfPreco(Double profPreco) {
        this.profPreco = profPreco;
    }

    @XmlTransient
    public List<Servicos> getServicosList() {
        return servicosList;
    }

    public void setServicosList(List<Servicos> servicosList) {
        this.servicosList = servicosList;
    }

    public Fornecedor getProfFornecedor() {
        return profFornecedor;
    }

    public void setProfFornecedor(Fornecedor profFornecedor) {
        this.profFornecedor = profFornecedor;
    }

    public Produto getProfProduto() {
        return profProduto;
    }

    public void setProfProduto(Produto profProduto) {
        this.profProduto = profProduto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (profId != null ? profId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produtofornecedor)) {
            return false;
        }
        Produtofornecedor other = (Produtofornecedor) object;
        if ((this.profId == null && other.profId != null) || (this.profId != null && !this.profId.equals(other.profId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return profProduto + " ";
    }
    
}
