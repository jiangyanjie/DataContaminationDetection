/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alexandra
 */
@Entity
@Table(name = "consults")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Consults.findAll", query = "SELECT c FROM Consults c"),
	@NamedQuery(name = "Consults.findById", query = "SELECT c FROM Consults c WHERE c.id = :id"),
	@NamedQuery(name = "Consults.findByConsDate", query = "SELECT c FROM Consults c WHERE c.consDate = :consDate")})
public class Consults implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
	private Integer id;
	@Basic(optional = false)
    @NotNull
    @Column(name = "cons_date")
    @Temporal(TemporalType.DATE)
	private Date consDate;
	@Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "diagnostic")
	private String diagnostic;
	@Lob
    @Size(max = 65535)
    @Column(name = "observations")
	private String observations;
	@JoinColumn(name = "id_patient", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private Patients idPatient;

	public Consults() {
	}

	public Consults(Integer id) {
		this.id = id;
	}

	public Consults(Integer id, Date consDate, String diagnostic) {
		this.id = id;
		this.consDate = consDate;
		this.diagnostic = diagnostic;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getConsDate() {
		return consDate;
	}

	public void setConsDate(Date consDate) {
		this.consDate = consDate;
	}

	public String getDiagnostic() {
		return diagnostic;
	}

	public void setDiagnostic(String diagnostic) {
		this.diagnostic = diagnostic;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Patients getIdPatient() {
		return idPatient;
	}

	public void setIdPatient(Patients idPatient) {
		this.idPatient = idPatient;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Consults)) {
			return false;
		}
		Consults other = (Consults) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "model.Consults[ id=" + id + " ]";
	}
	
}
