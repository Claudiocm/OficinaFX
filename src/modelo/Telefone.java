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
@Table(name = "telefone")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Telefone.findAll", query = "SELECT t FROM Telefone t"),
    @NamedQuery(name = "Telefone.findByTelId", query = "SELECT t FROM Telefone t WHERE t.telId = :telId"),
    @NamedQuery(name = "Telefone.findByTelNumero", query = "SELECT t FROM Telefone t WHERE t.telNumero = :telNumero"),
    @NamedQuery(name = "Telefone.findByTelTipo", query = "SELECT t FROM Telefone t WHERE t.telTipo = :telTipo")})
public class Telefone implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tel_id")
    private Integer telId;
    @Column(name = "tel_numero")
    private String telNumero;
    @Column(name = "tel_tipo")
    private String telTipo;
    @JoinColumn(name = "tel_cliente_id", referencedColumnName = "cli_id")
    @ManyToOne
    private Cliente telClienteId;
    @JoinColumn(name = "tel_fornecedor_id", referencedColumnName = "for_id")
    @ManyToOne
    private Fornecedor telFornecedorId;
    @JoinColumn(name = "tel_mecanico_id", referencedColumnName = "mec_id")
    @ManyToOne
    private Mecanico telMecanicoId;

    public Telefone() {
    }

    public Telefone(Integer telId) {
        this.telId = telId;
    }

    public Integer getTelId() {
        return telId;
    }

    public void setTelId(Integer telId) {
        this.telId = telId;
    }

    public String getTelNumero() {
        return telNumero;
    }

    public void setTelNumero(String telNumero) {
        this.telNumero = telNumero;
    }

    public String getTelTipo() {
        return telTipo;
    }

    public void setTelTipo(String telTipo) {
        this.telTipo = telTipo;
    }

    public Cliente getTelClienteId() {
        return telClienteId;
    }

    public void setTelClienteId(Cliente telClienteId) {
        this.telClienteId = telClienteId;
    }

    public Fornecedor getTelFornecedorId() {
        return telFornecedorId;
    }

    public void setTelFornecedorId(Fornecedor telFornecedorId) {
        this.telFornecedorId = telFornecedorId;
    }

    public Mecanico getTelMecanicoId() {
        return telMecanicoId;
    }

    public void setTelMecanicoId(Mecanico telMecanicoId) {
        this.telMecanicoId = telMecanicoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (telId != null ? telId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Telefone)) {
            return false;
        }
        Telefone other = (Telefone) object;
        if ((this.telId == null && other.telId != null) || (this.telId != null && !this.telId.equals(other.telId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return " " + telNumero + " " + telTipo;
    }

}
