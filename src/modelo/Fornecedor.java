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
@Table(name = "fornecedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fornecedor.findAll", query = "SELECT f FROM Fornecedor f"),
    @NamedQuery(name = "Fornecedor.findByForId", query = "SELECT f FROM Fornecedor f WHERE f.forId = :forId"),
    @NamedQuery(name = "Fornecedor.findByForBairro", query = "SELECT f FROM Fornecedor f WHERE f.forBairro = :forBairro"),
    @NamedQuery(name = "Fornecedor.findByForCep", query = "SELECT f FROM Fornecedor f WHERE f.forCep = :forCep"),
    @NamedQuery(name = "Fornecedor.findByForCidade", query = "SELECT f FROM Fornecedor f WHERE f.forCidade = :forCidade"),
    @NamedQuery(name = "Fornecedor.findByForCnpj", query = "SELECT f FROM Fornecedor f WHERE f.forCnpj = :forCnpj"),
    @NamedQuery(name = "Fornecedor.findByForContato", query = "SELECT f FROM Fornecedor f WHERE f.forContato = :forContato"),
    @NamedQuery(name = "Fornecedor.findByForEstado", query = "SELECT f FROM Fornecedor f WHERE f.forEstado = :forEstado"),
    @NamedQuery(name = "Fornecedor.findByForFantasia", query = "SELECT f FROM Fornecedor f WHERE f.forFantasia = :forFantasia"),
    @NamedQuery(name = "Fornecedor.findByForLogradouro", query = "SELECT f FROM Fornecedor f WHERE f.forLogradouro = :forLogradouro")})
public class Fornecedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "for_id")
    private Integer forId;
    @Column(name = "for_bairro")
    private String forBairro;
    @Column(name = "for_cep")
    private String forCep;
    @Column(name = "for_cidade")
    private String forCidade;
    @Column(name = "for_cnpj")
    private String forCnpj;
    @Column(name = "for_contato")
    private String forContato;
    @Column(name = "for_estado")
    private String forEstado;
    @Column(name = "for_fantasia")
    private String forFantasia;
    @Column(name = "for_logradouro")
    private String forLogradouro;

    public Fornecedor() {
    }

    public Fornecedor(Integer forId) {
        this.forId = forId;
    }

    public Integer getForId() {
        return forId;
    }

    public void setForId(Integer forId) {
        this.forId = forId;
    }

    public String getForBairro() {
        return forBairro;
    }

    public void setForBairro(String forBairro) {
        this.forBairro = forBairro;
    }

    public String getForCep() {
        return forCep;
    }

    public void setForCep(String forCep) {
        this.forCep = forCep;
    }

    public String getForCidade() {
        return forCidade;
    }

    public void setForCidade(String forCidade) {
        this.forCidade = forCidade;
    }

    public String getForCnpj() {
        return forCnpj;
    }

    public void setForCnpj(String forCnpj) {
        this.forCnpj = forCnpj;
    }

    public String getForContato() {
        return forContato;
    }

    public void setForContato(String forContato) {
        this.forContato = forContato;
    }

    public String getForEstado() {
        return forEstado;
    }

    public void setForEstado(String forEstado) {
        this.forEstado = forEstado;
    }

    public String getForFantasia() {
        return forFantasia;
    }

    public void setForFantasia(String forFantasia) {
        this.forFantasia = forFantasia;
    }

    public String getForLogradouro() {
        return forLogradouro;
    }

    public void setForLogradouro(String forLogradouro) {
        this.forLogradouro = forLogradouro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (forId != null ? forId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fornecedor)) {
            return false;
        }
        Fornecedor other = (Fornecedor) object;
        if ((this.forId == null && other.forId != null) || (this.forId != null && !this.forId.equals(other.forId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return forFantasia + " ";
    }
    
}
