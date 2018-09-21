/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@Table(name = "caixa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Caixa.findAll", query = "SELECT c FROM Caixa c"),
    @NamedQuery(name = "Caixa.findByCaId", query = "SELECT c FROM Caixa c WHERE c.caId = :caId"),
    @NamedQuery(name = "Caixa.findByCaFormapagamento", query = "SELECT c FROM Caixa c WHERE c.caFormapagamento = :caFormapagamento"),
    @NamedQuery(name = "Caixa.findByCaQtdeparcelas", query = "SELECT c FROM Caixa c WHERE c.caQtdeparcelas = :caQtdeparcelas")})
public class Caixa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ca_id")
    private Integer caId;
    @Column(name = "ca_formapagamento")
    private String caFormapagamento;
    @Column(name = "ca_qtdeparcelas")
    private Integer caQtdeparcelas;
    @JoinColumn(name = "ca_ordemservico", referencedColumnName = "ord_id")
    @ManyToOne
    private Ordemservico caOrdemservico;

    public Caixa() {
    }

    public Caixa(Integer caId) {
        this.caId = caId;
    }

    public Integer getCaId() {
        return caId;
    }

    public void setCaId(Integer caId) {
        this.caId = caId;
    }

    public String getCaFormapagamento() {
        return caFormapagamento;
    }

    public void setCaFormapagamento(String caFormapagamento) {
        this.caFormapagamento = caFormapagamento;
    }

    public Integer getCaQtdeparcelas() {
        return caQtdeparcelas;
    }

    public void setCaQtdeparcelas(Integer caQtdeparcelas) {
        this.caQtdeparcelas = caQtdeparcelas;
    }

    public Ordemservico getCaOrdemservico() {
        return caOrdemservico;
    }

    public void setCaOrdemservico(Ordemservico caOrdemservico) {
        this.caOrdemservico = caOrdemservico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (caId != null ? caId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caixa)) {
            return false;
        }
        Caixa other = (Caixa) object;
        if ((this.caId == null && other.caId != null) || (this.caId != null && !this.caId.equals(other.caId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Caixa[ caId=" + caId + " ]";
    }
    
}
