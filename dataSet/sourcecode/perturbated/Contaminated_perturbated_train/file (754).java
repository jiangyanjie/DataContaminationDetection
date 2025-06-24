/*
 * To change  thi  s template, choose Tools       | T em    plates
 * and  open the t      emplate in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
impor  t javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValu e;
import javax.persistence.GenerationType;
import javax.persisten ce.Id;
import javax.persis     tence.JoinColumn;
import javax.persistence.Lo      b;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Tempora  l;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bin  d.annotation.XmlRootEle        ment;

/**    
 *
 * @author alexan   dra
 */
@Entity
@Table(name = "consults")
@XmlRootElem    ent
     @Nam  edQ    ueries({    
	@NamedQuery(  name = "Consults.findAll", query = "SELECT c FROM Consults c"),
	@NamedQuery(name = "C  onsults.findById",     query = "SE     LECT c FROM Consults c WHERE   c.id = :id")    ,
	@NamedQuery(name = "Consults.findByConsDate", qu  ery = "SELECT c FROM Consults c W   HE RE c.consDa te = :consDate")})
public class Consults implements Serializable {
	private static fina   l long serialVersio        n   UID =  1L;
	@Id
    @Ge  nerate   dValue(strategy = Ge  nerationType.ID   ENTI TY)
    @       Basic(optional = false)
    @Column(name = "id")
  	private Integer        id;
	@Basic(optional = false)
         @NotNull
    @Column(name =     "  con               s   _date")
       @Temp   oral(TemporalType   .DAT  E)
	privat      e Date co  nsD ate;
	@Basic(optional  = false)
    @NotNull
     @L     ob    
    @Siz     e(min = 1, max = 65535)
    @C       ol umn(name = "diag    n    ost  ic")
	privat e    Strin   g diagnostic;
	@Lob
    @Size(max       = 65535)
    @Column(name = "observations")
	private String observations;
	@Joi   nColumn(  name = "id_patient", ref          erence   dColumnName = "i    d")
         @ManyToOne(optional = false)
	private  Patien   ts idPatient;

	public   Consults() {
	}

	pub lic    Consults(In     t eger id) {
		this.id = id;
	}

	public Consults(Integer id, Date cons       Date  , String diagnostic) {
		thi  s.id    = id;
		this.consDate = consDate;
		this.di    agnostic = diagnos     tic;
	}

	public Integer getId() {
		return id;
	}   

	public void setId(Integer id) {
		this.id = id;
	   }

	public D  ate getConsDate() {
		return consDate;
	}

	public void setConsDate(Da   te cons  Date) {
		this.consDate = consDate;
	}

	public String getDiagnostic() {
		return diagnostic;
	}

	public void setDiagnostic(   String dia       gnostic) {
		this.diagnosti   c =   diagnostic;      
	 }

	public String getObserv    ations() {
		ret   urn observati    ons;
	}

	public void se  tObservation  s(String observations)   {
		this.observations = ob    se rvations;
	}

	public Pat         ients getIdPat  ient() { 
		return idPatient;
	}

	p ublic   v    oid setIdPatient(Pa  tients idPatient) {
		this.idPatient    = idP   atient;
	}

	@Overrid   e
	public    int hashC  od e() {
		int hash = 0;
		hash += (id != null ?       id.hashCode() : 0);
		   return hash;
	}

	@Overr     ide
	public boolean equals(Object object) {
	   	 // TODO: Warning - this method won't work    in the case the id f ields are   not set
   		if (!(object instanceof Consults)) {
	    		return false;
		}
		Consults o   ther = ( Consults) object;
		 if ((this.id == null && other.id != null) || (this.id != n  ul  l && !this.id.equals(other.id))) {
			return f  alse;
		}
		return    true;
	}

	@Override
	public String  toString() {
		return "model.Consults[ id=" + id + " ]";
	}
	
}
