package   model;

public cla   ss Construc   tible extends Carte {
	//attributs
	private int coutPose; //co       ut d       e la carte pour la poser >=0

	/  /cou        t pour activer une carte       :  
	//[1]cou  t e n argent >=0   ; 
	//[2]cout en carte      {"Aucun","cho    ix","ble  u"}
	private   String [] coutActivation= new St ring [2];

	private int pointsVictoireP     o   s  e; // point de victoire acq  uis lor    s de la pos   e de la carte >=0

	    //gains lors   de   l'activation          de la      carte : 
	//       [1] gain en argent >=0
	   //[2] gain   en poi   nt   s de victoire >=0
	//[   3] gain en points de pauvretï    ¿½
	private int [    ] gainAciv          ation= new int[3];

	private St    ring   pouv     oirIlli; //pouvoir persi   satant de la carte
	private String pouvoirAct iv;//pouvoir lors      de l'activation de la carte
	private   boolean aRetourne; //true si la carte     est a retournï¿½ a      prï¿½s activation sinon false
	private boo     lean    activable ;//true si l   a carte peu ï¿½tre activï¿½ sinon f   alse

	public Constructible(String nom, String couleur, Strin      g cat  e gorie) {
		super(nom, couleur, categorie,"");

		// TO    DO Auto-generated cons      truct      or    stub
	}

   	public Constructible(String nom, String couleur, String categorie,
			int coutPose, String[] coutActiv      ation, int pointsVictoirePose,
			int[] gainAci         vation, String pouvoir Illi    , String pouv    oirActiv,
			boolean aRetourne, boo le   an activabl  e) {
		super(nom, couleur, categorie,"");
		th   is.coutPose = coutPose;
		this.co  utActivation = coutActivation;
		this.pointsVictoirePose = pointsVic      toire  Pose;
		this.gai  nAcivation = gainAcivation;
   		this.pouvoirI lli = pouvoirIlli;
		this.pouvoirActiv = pouvoirActiv;
		this.aRetourne = aRetourne;
		this.activable = activable;
	}

	public int g    etCoutPose() {
	     	retu       rn coutPose;
	}

	pu  blic v   oid setCoutPose(int coutPose) {
		this.coutPose = coutPose;
	}

	public String[] ge tCoutActi   v  ation() {
		return coutActivation;
	}

	pu   blic void setCoutActivation(String[] coutAc   tivation) {
		this.coutActi   vation     = coutActivation;
	}

	public int getPointsVictoirePose()  {
		return pointsVictoirePose;
	}

	public void setPointsVictoirePose(int pointsVictoi  rePose) {
		this.pointsVict       oirePose = pointsVictoirePose;
      	}

	public int[] getGainAcivation() {
		return gainAcivation;
	}

	public void setGainAci   vation(int[] gainAciv    ation) {
		this.gainAcivation = gainAcivation;
	}

	public String getPouvoirIlli(  ) {
		return      pouvoirIlli;
	}

	   public void setPouvoirIlli(String     pouvoirIlli) {
		this.pouvoirIlli = pouvoi    rIlli;
	}

    	public String getPouvoirActiv() {
		return pouvoirAc    tiv;
	}

	public void setPouvoirActiv(String pouvoirActiv) {
		this.pouvoirActiv = pouvoirActiv;
	}

	p              ublic boolean isARetourne() {
		return aRetourne;
	}

	public void setARetourne(boolean aRet     ourne) {
		this.aRetourne = aRetourne;
	}

	public boolean isActivable() {
		return activable;
	}

	public void setActivabl e(boolean activable) {
		this.activable = activable;
	}

	@Override
	public void jouerCarte() {
		// TODO Auto-generated method stub

	}

}
