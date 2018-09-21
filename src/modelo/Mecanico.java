package modelo;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author estagio
 */
@Entity
@Table(name = "mecanico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mecanico.findAll", query = "SELECT m FROM Mecanico m"),
    @NamedQuery(name = "Mecanico.findByMecId", query = "SELECT m FROM Mecanico m WHERE m.mecId = :mecId"),
    @NamedQuery(name = "Mecanico.findByMecLogin", query = "SELECT m FROM Mecanico m WHERE m.mecLogin = :mecLogin"),
    @NamedQuery(name = "Mecanico.findByMecNome", query = "SELECT m FROM Mecanico m WHERE m.mecNome = :mecNome"),
    @NamedQuery(name = "Mecanico.findByMecSenha", query = "SELECT m FROM Mecanico m WHERE m.mecSenha = :mecSenha")})
public class Mecanico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mec_id")
    private Integer mecId;
    @Column(name = "mec_login")
    private String mecLogin;
    @Column(name = "mec_nome")
    private String mecNome;
    @Column(name = "mec_senha")
    private String mecSenha;
    @OneToMany(mappedBy = "telMecanicoId")
    private List<Telefone> telefoneList;
    @OneToMany(mappedBy = "ordMecanico")
    private List<Ordemservico> ordemservicoList;

    public Mecanico() {
    }

    public Mecanico(Integer mecId) {
        this.mecId = mecId;
    }

    public Integer getMecId() {
        return mecId;
    }

    public void setMecId(Integer mecId) {
        this.mecId = mecId;
    }

    public String getMecLogin() {
        return mecLogin;
    }

    public void setMecLogin(String mecLogin) {
        this.mecLogin = mecLogin;
    }

    public String getMecNome() {
        return mecNome;
    }

    public void setMecNome(String mecNome) {
        this.mecNome = mecNome;
    }

    public String getMecSenha() {
        return mecSenha;
    }

    public void setMecSenha(String mecSenha) {
        this.mecSenha = mecSenha;
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
        hash += (mecId != null ? mecId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mecanico)) {
            return false;
        }
        Mecanico other = (Mecanico) object;
        if ((this.mecId == null && other.mecId != null) || (this.mecId != null && !this.mecId.equals(other.mecId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return mecNome + " ";
    }
    
}
