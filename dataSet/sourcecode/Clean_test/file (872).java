package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the critere database table.
 * 
 */
@Entity
@NamedQuery(name="Critere.findAll", query="SELECT c FROM Critere c")
public class Critere implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CRITERE_ID_GENERATOR", sequenceName="CRITERE_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CRITERE_ID_GENERATOR")
	private Integer id;

	@Column(name="desc_niveau_performance_1")
	private String descNiveauPerformance1;

	@Column(name="desc_niveau_performance_2")
	private String descNiveauPerformance2;

	@Column(name="desc_niveau_performance_3")
	private String descNiveauPerformance3;

	@Column(name="desc_niveau_performance_4")
	private String descNiveauPerformance4;

	private String description;

	//bi-directional many-to-one association to Grille
	@ManyToOne
	@JoinColumn(name="id_grille")
	private Grille grille;

	public Critere() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescNiveauPerformance1() {
		return this.descNiveauPerformance1;
	}

	public void setDescNiveauPerformance1(String descNiveauPerformance1) {
		this.descNiveauPerformance1 = descNiveauPerformance1;
	}

	public String getDescNiveauPerformance2() {
		return this.descNiveauPerformance2;
	}

	public void setDescNiveauPerformance2(String descNiveauPerformance2) {
		this.descNiveauPerformance2 = descNiveauPerformance2;
	}

	public String getDescNiveauPerformance3() {
		return this.descNiveauPerformance3;
	}

	public void setDescNiveauPerformance3(String descNiveauPerformance3) {
		this.descNiveauPerformance3 = descNiveauPerformance3;
	}

	public String getDescNiveauPerformance4() {
		return this.descNiveauPerformance4;
	}

	public void setDescNiveauPerformance4(String descNiveauPerformance4) {
		this.descNiveauPerformance4 = descNiveauPerformance4;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Grille getGrille() {
		return this.grille;
	}

	public void setGrille(Grille grille) {
		this.grille = grille;
	}

}