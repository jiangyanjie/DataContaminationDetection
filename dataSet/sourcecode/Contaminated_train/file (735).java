package model;

public class Constructible extends Carte {
	//attributs
	private int coutPose; //cout de la carte pour la poser >=0

	//cout pour activer une carte : 
	//[1]cout en argent >=0 ; 
	//[2]cout en carte {"Aucun","choix","bleu"}
	private String [] coutActivation= new String [2];

	private int pointsVictoirePose; // point de victoire acquis lors de la pose de la carte >=0

	//gains lors de l'activation de la carte :
	//[1] gain en argent >=0
	//[2] gain en points de victoire >=0
	//[3] gain en points de pauvret�
	private int [] gainAcivation= new int[3];

	private String pouvoirIlli; //pouvoir persisatant de la carte
	private String pouvoirActiv;//pouvoir lors de l'activation de la carte
	private boolean aRetourne; //true si la carte est a retourn� apr�s activation sinon false
	private boolean activable ;//true si la carte peu �tre activ� sinon false

	public Constructible(String nom, String couleur, String categorie) {
		super(nom, couleur, categorie,"");

		// TODO Auto-generated constructor stub
	}

	public Constructible(String nom, String couleur, String categorie,
			int coutPose, String[] coutActivation, int pointsVictoirePose,
			int[] gainAcivation, String pouvoirIlli, String pouvoirActiv,
			boolean aRetourne, boolean activable) {
		super(nom, couleur, categorie,"");
		this.coutPose = coutPose;
		this.coutActivation = coutActivation;
		this.pointsVictoirePose = pointsVictoirePose;
		this.gainAcivation = gainAcivation;
		this.pouvoirIlli = pouvoirIlli;
		this.pouvoirActiv = pouvoirActiv;
		this.aRetourne = aRetourne;
		this.activable = activable;
	}

	public int getCoutPose() {
		return coutPose;
	}

	public void setCoutPose(int coutPose) {
		this.coutPose = coutPose;
	}

	public String[] getCoutActivation() {
		return coutActivation;
	}

	public void setCoutActivation(String[] coutActivation) {
		this.coutActivation = coutActivation;
	}

	public int getPointsVictoirePose() {
		return pointsVictoirePose;
	}

	public void setPointsVictoirePose(int pointsVictoirePose) {
		this.pointsVictoirePose = pointsVictoirePose;
	}

	public int[] getGainAcivation() {
		return gainAcivation;
	}

	public void setGainAcivation(int[] gainAcivation) {
		this.gainAcivation = gainAcivation;
	}

	public String getPouvoirIlli() {
		return pouvoirIlli;
	}

	public void setPouvoirIlli(String pouvoirIlli) {
		this.pouvoirIlli = pouvoirIlli;
	}

	public String getPouvoirActiv() {
		return pouvoirActiv;
	}

	public void setPouvoirActiv(String pouvoirActiv) {
		this.pouvoirActiv = pouvoirActiv;
	}

	public boolean isARetourne() {
		return aRetourne;
	}

	public void setARetourne(boolean aRetourne) {
		this.aRetourne = aRetourne;
	}

	public boolean isActivable() {
		return activable;
	}

	public void setActivable(boolean activable) {
		this.activable = activable;
	}

	@Override
	public void jouerCarte() {
		// TODO Auto-generated method stub

	}

}
