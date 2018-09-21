package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author estagio
 */
@Entity
@Table(name = "produto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produto.findAll", query = "SELECT p FROM Produto p"),
    @NamedQuery(name = "Produto.findByProId", query = "SELECT p FROM Produto p WHERE p.proId = :proId"),
    @NamedQuery(name = "Produto.findByProMarca", query = "SELECT p FROM Produto p WHERE p.proMarca = :proMarca"),
    @NamedQuery(name = "Produto.findByProModelo", query = "SELECT p FROM Produto p WHERE p.proModelo = :proModelo"),
    @NamedQuery(name = "Produto.findByProNome", query = "SELECT p FROM Produto p WHERE p.proNome = :proNome"),
    @NamedQuery(name = "Produto.findByProQuantidade", query = "SELECT p FROM Produto p WHERE p.proQuantidade = :proQuantidade"),
    @NamedQuery(name = "Produto.findByProUnidade", query = "SELECT p FROM Produto p WHERE p.proUnidade = :proUnidade")})
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pro_id")
    private Integer proId;
    @Column(name = "pro_marca")
    private String proMarca;
    @Column(name = "pro_modelo")
    private String proModelo;
    @Column(name = "pro_nome")
    private String proNome;
    @Column(name = "pro_quantidade")
    private Integer proQuantidade;
    @Column(name = "pro_unidade")
    private String proUnidade;

    public Produto() {
    }

    public Produto(Integer proId) {
        this.proId = proId;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public String getProMarca() {
        return proMarca;
    }

    public void setProMarca(String proMarca) {
        this.proMarca = proMarca;
    }

    public String getProModelo() {
        return proModelo;
    }

    public void setProModelo(String proModelo) {
        this.proModelo = proModelo;
    }

    public String getProNome() {
        return proNome;
    }

    public void setProNome(String proNome) {
        this.proNome = proNome;
    }

    public Integer getProQuantidade() {
        return proQuantidade;
    }

    public void setProQuantidade(Integer proQuantidade) {
        this.proQuantidade = proQuantidade;
    }

    public String getProUnidade() {
        return proUnidade;
    }

    public void setProUnidade(String proUnidade) {
        this.proUnidade = proUnidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proId != null ? proId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        if ((this.proId == null && other.proId != null) || (this.proId != null && !this.proId.equals(other.proId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return proNome + " ";
    }
    
}
