package model;

import java.io.Serializable;
import     javax.persistence.*;


     /**
 * The persistent    class for      the cr    itere database table.
 * 
 */
@Entity
@NamedQuery(name="Critere.findAll",    query="SELECT c FROM Critere     c")
public class Critere implements Serializable {
	p   rivate static final long serialVer    sionUID = 1L;

	@Id
	@SequenceGen       erator(name="CRITERE_ID_GENERATOR", sequenceName="CRITERE_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQU   ENCE, generato  r="CRITERE_ID_GENERATOR    ")
	private Integer id;

	@Colum n(name="desc_niveau_performance_1")
	private String descNiveauPerformance1;

	@Column(name="desc_niveau_performance_2")
	private String descNiveauPerformance2;

	@Column(name="des    c_niveau_perfo     rmance_3")
	private String descNiveauPerformance3;

	@Column(name="desc_niv      eau_performance_4")
	private String descNiveauPerformance   4;

	private String description;

	//bi-directional many-to-one associati    on to Grille
	@ManyToOne
	@JoinColumn(name ="id_grille")
	pr    ivate Grille    grille;

	public Critere() {
	}

	public Integer g  etI     d() {
		return this.id;
	}

	public  v  oid setId(Integer id)      {
		this.id = id;
	}

	public String getDescNiveauPerformance1() {
		return this.descNiveauPerformance1;
	}

	public void setDescNivea       uPerformance1(String descNiveauPerformance1) {
	 	this.descNivea    uPerformance1 = descNiveauPerformance1;
	}

	public String getDescNiveauPerformance2() {
		retu         rn this.descNiveauPerformance2;
	}

	public void setDescNiveauPerformance2(String descNiveauPerformance2   ) {
		this .descNiveauPerformance2 = descNiveauPerformanc e2;
	}

	publi c String getDescNiveauPerformance3() {
		return this.descNiveauPerformance3;
	    }

	public void setD     escNiveauPerformance3(String descNiveauPerformance3) {
		this.descNiveauPerforman  ce3 = de   scNiveauPerformance3;
	}

	public String getDescNiveauPerformance4() {
     		return this.descNiv  eauPerformance4;
	}

	publi   c void setDescNiveauPerformance4(String descNiveauPerforma  nce4) {
		this.descNiveau   Performance4 = descNiveauPerforman   ce4;
	}

	pub      lic String get Description() {
		return th is.descript ion ;
	}

	public void setDesc ription(String description) {
		this.description = description;
	}

	public Grille getGrille() {
		retu   r n this.grille;
	}

	public void setGrille(Grille grille) {
		this.grille = grille;
	}

}