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
@Table(name = "servicos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Servicos.findAll", query = "SELECT s FROM Servicos s"),
    @NamedQuery(name = "Servicos.findBySerCodigo", query = "SELECT s FROM Servicos s WHERE s.serCodigo = :serCodigo"),
    @NamedQuery(name = "Servicos.findBySerNome", query = "SELECT s FROM Servicos s WHERE s.serNome = :serNome"),
    @NamedQuery(name = "Servicos.findBySerPreco", query = "SELECT s FROM Servicos s WHERE s.serPreco = :serPreco")})
public class Servicos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ser_codigo")
    private Integer serCodigo;
    @Column(name = "ser_nome")
    private String serNome;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ser_preco")
    private Double serPreco;
    @JoinColumn(name = "ser_produto", referencedColumnName = "prof_id")
    @ManyToOne
    private Produtofornecedor serProduto;

    public Servicos() {
    }

    public Servicos(Integer serCodigo) {
        this.serCodigo = serCodigo;
    }

    public Integer getSerCodigo() {
        return serCodigo;
    }

    public void setSerCodigo(Integer serCodigo) {
        this.serCodigo = serCodigo;
    }

    public String getSerNome() {
        return serNome;
    }

    public void setSerNome(String serNome) {
        this.serNome = serNome;
    }

    public Double getSerPreco() {
        return serPreco;
    }
    
    public Double getPreco(){
        return serPreco;
    }

    public void setSerPreco(Double serPreco) {
        this.serPreco = serPreco;
    }

    public Produtofornecedor getSerProduto() {
        return serProduto;
    }

    public void setSerProduto(Produtofornecedor serProduto) {
        this.serProduto = serProduto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serCodigo != null ? serCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servicos)) {
            return false;
        }
        Servicos other = (Servicos) object;
        if ((this.serCodigo == null && other.serCodigo != null) || (this.serCodigo != null && !this.serCodigo.equals(other.serCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return serNome + " ";
    }
    
}
