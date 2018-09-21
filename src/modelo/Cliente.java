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
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByCliId", query = "SELECT c FROM Cliente c WHERE c.cliId = :cliId"),
    @NamedQuery(name = "Cliente.findByCliBairro", query = "SELECT c FROM Cliente c WHERE c.cliBairro = :cliBairro"),
    @NamedQuery(name = "Cliente.findByCliCep", query = "SELECT c FROM Cliente c WHERE c.cliCep = :cliCep"),
    @NamedQuery(name = "Cliente.findByCliCidade", query = "SELECT c FROM Cliente c WHERE c.cliCidade = :cliCidade"),
    @NamedQuery(name = "Cliente.findByCliCnpj", query = "SELECT c FROM Cliente c WHERE c.cliCnpj = :cliCnpj"),
    @NamedQuery(name = "Cliente.findByCliCpf", query = "SELECT c FROM Cliente c WHERE c.cliCpf = :cliCpf"),
    @NamedQuery(name = "Cliente.findByCliDatacadastro", query = "SELECT c FROM Cliente c WHERE c.cliDatacadastro = :cliDatacadastro"),
    @NamedQuery(name = "Cliente.findByCliEstado", query = "SELECT c FROM Cliente c WHERE c.cliEstado = :cliEstado"),
    @NamedQuery(name = "Cliente.findByCliLogradouro", query = "SELECT c FROM Cliente c WHERE c.cliLogradouro = :cliLogradouro"),
    @NamedQuery(name = "Cliente.findByCliNome", query = "SELECT c FROM Cliente c WHERE c.cliNome = :cliNome")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cli_id")
    private Integer cliId;
    @Column(name = "cli_bairro")
    private String cliBairro;
    @Column(name = "cli_cep")
    private String cliCep;
    @Column(name = "cli_cidade")
    private String cliCidade;
    @Column(name = "cli_cnpj")
    private String cliCnpj;
    @Column(name = "cli_cpf")
    private String cliCpf;
    @Column(name = "cli_datacadastro")
    @Temporal(TemporalType.DATE)
    private Date cliDatacadastro;
    @Column(name = "cli_estado")
    private String cliEstado;
    @Column(name = "cli_logradouro")
    private String cliLogradouro;
    @Column(name = "cli_nome")
    private String cliNome;
    @OneToMany(mappedBy = "carCliente")
    private List<Carro> carroList;
    @OneToMany(mappedBy = "telClienteId")
    private List<Telefone> telefoneList;
    @OneToMany(mappedBy = "ordCliente")
    private List<Ordemservico> ordemservicoList;

    public Cliente() {
    }

    public Cliente(Integer cliId) {
        this.cliId = cliId;
    }

    public Integer getCliId() {
        return cliId;
    }

    public void setCliId(Integer cliId) {
        this.cliId = cliId;
    }

    public String getCliBairro() {
        return cliBairro;
    }

    public void setCliBairro(String cliBairro) {
        this.cliBairro = cliBairro;
    }

    public String getCliCep() {
        return cliCep;
    }

    public void setCliCep(String cliCep) {
        this.cliCep = cliCep;
    }

    public String getCliCidade() {
        return cliCidade;
    }

    public void setCliCidade(String cliCidade) {
        this.cliCidade = cliCidade;
    }

    public String getCliCnpj() {
        return cliCnpj;
    }

    public void setCliCnpj(String cliCnpj) {
        this.cliCnpj = cliCnpj;
    }

    public String getCliCpf() {
        return cliCpf;
    }

    public void setCliCpf(String cliCpf) {
        this.cliCpf = cliCpf;
    }

    public Date getCliDatacadastro() {
        return cliDatacadastro;
    }

    public void setCliDatacadastro(Date cliDatacadastro) {
        this.cliDatacadastro = cliDatacadastro;
    }

    public String getCliEstado() {
        return cliEstado;
    }

    public void setCliEstado(String cliEstado) {
        this.cliEstado = cliEstado;
    }

    public String getCliLogradouro() {
        return cliLogradouro;
    }

    public void setCliLogradouro(String cliLogradouro) {
        this.cliLogradouro = cliLogradouro;
    }

    public String getCliNome() {
        return cliNome;
    }

    public void setCliNome(String cliNome) {
        this.cliNome = cliNome;
    }

    @XmlTransient
    public List<Carro> getCarroList() {
        return carroList;
    }

    public void setCarroList(List<Carro> carroList) {
        this.carroList = carroList;
    }

    @XmlTransient
    public List<Telefone> getTelefoneList() {
        return telefoneList;
    }

    public void setTelefoneList(List<Telefone> telefoneList) {
        this.telefoneList = telefoneList;
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
        hash += (cliId != null ? cliId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.cliId == null && other.cliId != null) || (this.cliId != null && !this.cliId.equals(other.cliId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return cliNome + " ";
    }
    
}
