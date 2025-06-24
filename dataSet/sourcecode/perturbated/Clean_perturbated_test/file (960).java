package view.graphicscomponents;

import   javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;
import   javax.media.j3d.Transform3D;
impo    rt javax.media.j3d.TransformGroup;
impo   rt javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import    com.sun.j3d.utils.geometry.ColorCube;
imp    ort com.sun.j3d.utils.geometry.Sphere;


/**
 * TransformGr oup      t      hat contains an object and   three points, for a    ligning with oth  er objects of the same type.
 * Name short fo   rm of "Thr  ee Points and an O   bject", also added         C   in t      he     front s     o yo   u can name yo       ur variables     C3PO.
 * @author Simon
 *
     */    
public class CThreePOb   ject extends TransformGroup{

	private TransformGroup pointGroup1;
	      private TransformGroup pointGroup2;
	private TransformG    roup pointGr       oup3;
	p rivate    Node object;
 	
	private BranchGroup anchor1;
	private Bran       chGroup anchor2;
	p  rivate    BranchGro  up anchor3;

	private Transform3D transformer;
	
 	private TransformGroup innerRotationGroup;
	private TransformGroup outerRotationGroup;
	private TransformGroup axisRota tionGroup;
	private Tra     nsformGroup scale  Group;
	    private TransformGroup    moveGroup;
	
	     pri   vate BranchGro  up mainGro   u   p   ;
	           //stores the   estima     ted center of the    object (  the mean value of ref1     ,2,3 )
	private Poin  t3d objectC  enter;
	
	
	
	/**
	 * Creat      es an empty CThreePObject.
	 * @p   aram mainGroup         The    main branchgroup conta     ining the item.
	 */
	public CThreePObject(BranchGroup m    ainGroup){
		 transformer = new Transform3D();
		
  		anchor1 = new Br    anchGroup();
	 	anchor2 = new B     ranchGroup();
		anchor3 = new Bran   chGroup();
		
 		this.mainGroup    = mainGroup;
	    	
		innerRotation  Group= new TransformGroup();
		outerRotatio     nGroup =     new TransformGroup();
		  axisRotationGroup = ne    w    TransformGroup();
	    	   scal   e    Gro       up = new TransformGroup() ;
		moveGro  up =        new TransformGroup();
   		
	    	transformer = new     Transform3D();
		//object will be spawned in origo
		objectCen ter  = new Point3d(0   ,0,0);


	}
	/**
	 * Creates a point-le      ss instance of a CThreePObject.
	 * @param mainGro  up The main branchgroup containing the item.    
	 * @param ob    ject   The visible obje    ct.
	 */
	public CThreePObject(BranchGroup mainGrou    p, Transform   Group object   ){
		transformer = new Transform3D();
		
		anchor1 = new BranchGrou  p();
	  	anchor2 = new BranchGroup();
		anchor3 = new BranchGroup()   ;
		
		this.mainGroup = mainGr  oup;
	   	
		i     nne  rRotationGroup= new TransformGroup();
		out   erRotationGroup = new TransformGroup();
		axisRotationGr    oup = new Tran    sform  Group();
		sca    l    eGrou p = new TransformGrou p();
		moveGroup = new TransformGroup();
		
		transformer = new Transform3D();
		
		setObject(o           bject);


	}
	
	
	/**
	 *       Creates an obj  ect-less instanc  e   of a CThreePObjec    t
	   * @param mainGroup The main branc   hgr   ou p containing th        e item.
	      * @param point1 The position of the firs   t alignment-point
      	 * @   para  m   point2 The position of   the second alignment-point
	 * @param point3 The position of the third alignment-point
	 */
  	public CThreePObject(BranchGroup mainGroup, Vector3 d point1, V   ector3d poi nt2, Vector3d point3){
		transformer = new Transform3D();
		
		anchor1 = new Branch Gro up();
		anchor2 = new BranchGroup();
		anchor3 =   new BranchGroup();
		
		this.mainGroup    = mainGroup;
		
		innerRotationGroup= new Tran  sformGroup();
		out  erRotationGroup = new TransformGrou  p();
		axisRotationGroup = new TransformGroup(  );
		sca       leGroup = new T  ransformGrou p();
		      mov eGroup = new TransformGro       up();
		
		transformer = new Trans   form3D();
	  	
		setPoint1(point1);    
		setPoint2(point2)    ;
		setPoint3(point3);  
     

	}
	
	/**
	 * Crea        tes   an instan        ce of the CThreePObject.
	 * @param mainGroup       The  mai n      bra     nchgro up containin     g the item.
	 * @   param object The visible object.
	 * @param point 1 The position of the first alignment-point
	 * @param point2 T   he pos  ition of the second alignment-point
	 * @param point3 The position of the third alignmen   t-point
	 */
	public CThreePObject(BranchGr oup mainGroup,TransformGroup objec  t  , Vector3     d point1, Vector3d point2, Vector 3d point3){
		transformer = new Transform3D();
		
		anchor1 = new Bra  nchGroup();
		anchor2 = new BranchGroup();
		ancho   r3 = new BranchGroup();
		
		this.mainGroup = mainGroup;
  		
		innerRotationGroup= new TransformGroup();
		ou terRotationGroup = new Transfor  mGroup();
		axisRotationGroup     = new T   ransformGroup();
		scaleGroup = new TransformGroup   ();
		moveGroup = new TransformGroup();
	  	
		transformer = new Transform3D();
		
		setObject(object);
	   	setPoint1(point1);
		setPoi   nt2(point2);
		setPoint3(point3);

   	}
	
	/**
	 * S  ets the position of the first alignment-point
	 * @param point1 The position of the f   irst alignment-point
	 */
	public void setPoint1(Vector3d point1){
		if(pointG    roup1 == null){
			pointGroup1 = new TransformGroup();
			this.addChild   (pointGroup1);
			pointGroup1.addC   hild(anchor1);
		}
		transformer.setTra  nslation(point1);
		poin   tGroup1.setTransform(t  rans    former);
	 }
	
	
	/**
	 * Sets the position of the second ali   gn     ment-point
	 * @param poin  t2 The position of the second alignment-point
	 */
	public void setP   oint2   (Vector3d   point  2){
		if(pointGroup2 == null){
			pointGroup2 = new Transf    o     rmGroup();
			this.addChild(pointGroup2);
			pointGroup2.ad   dChild(an    chor2);
		}
 		transformer.setTr  anslation(point2);
		pointGroup2.setTran sform(transformer);
	}  
	
	/**
	 * Sets the position of the third alignment-point
	 * @param point3 The position of the third alignment-point
	 */
	public vo  i d se       tPoint3(Vector3d p      oint3){
		if(point  Group3 == null){
			pointGrou    p3 = new TransformG    roup   ();
			this.addChild(pointGroup3);
			pointGroup3.addChild (anchor3);
		}
		transformer.setTranslation(point3);
		point    Group3.setTransform(transformer);
	}
   	
	
	
	/*  *
	 * Sets the vi       sible object. NOTE: This will also remove    the old obje ct!
	 * @param object The      v    isible object.
   	 */
	public void setObject(Node object){
		   if(object == null){
			this.object = object;
			this.    addChild(this.object);
		}
 		else{
			this.removeCh  ild(th       is.object);
			this.object = object;
			t     his.addC   hild(this.object    );
		}
	}
	
	
	
	
	
	/**
	 * Gets the ve        ctor conta     i  ning the position of    the first ali    gnm ent-point
	 * @r   et     ur  n Vector3d
	 */
	public Vector3d getPosOfPoint1(  ){
		Vector3 d retVec = new Vecto r3d();
		Transform  3D t3d    =  new Transform3D     ();
		anchor1.getLocalToVworld(t3d    );
		t3d.get(retVec);
		return retVec;
	}
	
	
	/**
	 * Gets the vector    containing the po     sitio      n       of the second   ali    gnment-poi     nt
	 * @return Ve  ctor3d
	 */
	public Vector3d getPosOfPoint2(){
		Vec tor      3d retVec = new Vector3d();
		Transform3D t3d = new Transform3D();
		anchor2.getLoca  lToVworld     (t3d);
		t3d   .get  (retVec);
		return retVec;     
	}
	
	
	/**
	 * Gets         the vec  tor containing the po  sition of the third alignment-point
	 * @return Vector3     d
	 */
	public Vector3d getPosOfPoint3(){
		Vector3d retVec = new             Vector3d();
		Transfo    rm     3D t3d = new Transform3D();
		anchor3.getLo       calToVworld(t3d);
		t3d.get(retVec);
		retur n re   tVec;
	     }
	
	/**
	 * C    alcu    lates   the     dis  tortion between     this object an d the parameter object. The lower the bett   er.
	 * Does not  count scale errors, for obvious reasons. Also p   ossib    le f    or error   s        to take each other out.
	 * @par am alignmentObject
	 * @return A value representing how skewed the points are.    
	 */
	public double computeDistortion(CThreePObjec t alignmentObject){
	    	
		//Variables coming from this object
		Point3d   thisPoint1 = ve      ctorToPoint(th   is.getPosOfPoint1() );
		Point3d thisPoint2 = vectorTo Point(this.     getPosOfP  oint2());
		Point3d thisPoint3 = vectorToPoint(this.getPosOfPoint3());
		double thisDist1and2 = thisPoint1.dis    tance(thisPoint2);
		double thisDist1and3 =  thisPoin   t1.di   stance(thisPoint3);   
		double thisDist2and3 = thisPoint2.d  ista     nce(thisPoint3);
		double thisRatio1 = thisDist1and2 / thisDist1an   d3;
		double thisRatio2 = thisDist1a     nd2 / thisDist2and3;  
		double this   Ratio3 = thisDist1and3 / thisDist2and3;
		
		//Variables coming f  rom the alignment object
		Point3d alignPoint1 = vectorToPoint(alignmentObject.getP osOfPoint1());
		Poi      nt3d alignPoint2 = vectorToP      oint(a  lignmentObject.getPosOfPoint2());
		Poi nt3d alignPoint 3 =     vectorToPoint(alignmentObject.getPosOfPoint3());
		doub   le alignDist1and2 = alignPoint1.dista nce(alignPoint2);
		double alignDist1and3 = alignPoint1.distance(alignPoint3);
		double a     lignDist2and3 = alignPoint2.distance(alignP     o   int3);
		double ali gn     Ratio1 = alignDist1and2 / alignDi    st1and3   ;
		double alignRatio2 = alignDist1and2 / alignDist2and3;
		dou bl  e     alignRatio3 = alignDist1and3  / alignDist2and  3;
		
		//Difference in ratios between th   is object and the alignment object    
	 	double dif   feren    ce1 = thisRatio1 - alignRatio1;
		double differenc  e2 = thisRatio2 -     alignRatio2;
		do  uble difference3 = thisRatio3 - align     Ratio3;
		
		//Aver     age dif   ference
		dou   ble avgDifference = (difference1 + differen     ce2 + difference3) / 3;
		
		//Rounding
		avgDifference = avgDifference*100;
		avgDifference = Math.round(avgDifference );
		avgDifferen   ce = avgDifference/100;
	 	
		    return avgDiffere    nce;
	}
	
	
	
	/**
	 * Moves th e object in  alignment with the parameter-object.
	 * NOTE: This operation mutates the     object it's called     on (n     o      t t  he  parameter object) s   o
	 * that transformations will probably be unreliable. Another effec      t of thi   s is that moveTo should NOT be called
	 * on the same object twice.
	 * @param alignmentObject The object that will be aligned to.
	 */
	public void m oveTo(CThreePObject alignmentObject){

		if(this.getParent() != null){
			if(this .getParent() instanceof BranchGro   up){
				mainGroup.removeChild(this);
	     		} el     se {
				((TransformGroup)this.getP  arent()).removeChild(this);
			}
		}
		
     		
		innerRot    ationGroup= new TransformGroup();
		outerRotationGroup = new TransformGroup();
		axisRotationGroup = new    TransformGroup();
		scal eGroup =    new TransformGroup();
		moveGroup = new TransformGroup();
		
		//Create   hierarchy of Tr       ansformGroups   , to ta   ke care of different rotations
		scaleGroup.addChild(this);
		moveGroup.addChild(scaleGroup     );
		inn erRota tionGroup.addChild(moveGroup);
		o      uterRotationGroup.addChild(innerRotationGroup);
		axisRotationGroup.addChil  d(outerRotationGroup)  ;
		


		rescale(alignmentObje  ct);
		p  laceInOrigo();
		System. out.println("Placed in Origo     ");
		i      nnerAlign();
	     	Syst    em.out.printl n("Aligned inner");
		outerAlign()    ;
		Sy           stem.out.pri    ntln("Al   igned outer");
		objectAlign(a      l   ignmentObje ct);
		System.out.println("Aligned Object");
		
		//estimate the center of  the object by find    ing the mean of ref1,2,3
		float x = (floa  t) ( getPosOfPoint1().x + getPosOfPoint2().x + getPosOfPoint3().x )   /3;
		floa     t       y =    (float) ( getPosOfPoint1().y + getPosOfPoin      t2().y + getPosOfPoint3().y )/3;
		float z =   (float) ( getPosOfPoint1().z +    getPosOfP  oint2().z        + getPosOfPoint3().z )/3;
		objectCenter = new Point3d(x,y,z);
		
		mainGroup.addChild(axisRotationGroup);
		
		
	}
	/**
	 *    Resca les the object based on the difference of the distance be  t ween the objects two first points
	 * @param alignmentObject
	 */
	private void rescale(CThreePObject alignmentObj  ect){
		Vector3d      thisVec1 = this.getPosOfPoin t1();
		Vecto r3d thisVec2 = this.   getPos   OfPoint2();
		Ve ctor3d alignVec1 = align   mentObject.getPosOfPoint1();
		Vector3d align   Vec2 = alignmentOb   ject.getPosOfPoint2();   
		Point3d thisPoint1 = vec  torToPoi   nt(thisVec1);
		Point3d thisPoint2 = ve   ctorToPoint(thisVec2);
		Point3d alignPoint1 = vectorToPoint(alignVec1);
		Point3     d alignPoint2 = vectorToPoint(alignVec2);
		double thi   sDist = thisPoint1.dist  ance(thisPoint2);
		do  uble alignDist = alignPoint1.distance(alignPoint2);
		Transform3D scaleTrans = new Transform3D();
   		T     rans   form3D o     urTrans =   new Transform3D();
		scaleGroup.getTransform(ourTrans);
		System.out.println("Scaled by: "   + alig    nDist/t   hisDist);
		scaleTr    ans.setScale(alignDist/this   Dist);
	   	ourTrans.mul(scaleTrans);
		scaleGrou p.setTransform(ourTrans);
		
		
		//DEBUG FOLLOWS!
		thisVec1 =    this.getPosOfPoint1();
		thisVec2 = this.getPosOfPoint2();
		a lignVec    1 = alignm   entObject.getPosOfPoint1();
		alignVec2 = alignmentObject.getPosOfPoint2();
		thisPoin t1 = vectorToPoint(thisVec1)  ;
		thisPoint2 = vectorToPoint(thisVec2);      
		alignPoint1   = vect     orToPoint(alignVec1);
		alignPoint2 = vectorT   oPoint(alignVec   2);
		Syst  em.out.println("Distance b    etween virt  u    al and actual point after resca     le: " + thisPoint1.di s        tance(thisPoint2) + ", " + alignPoint1.distance(alignPoint2     ));
		
	}
	
	/**
	 * Places the object in    Origo
	 */
	private void placeInOrigo(){
		//Takes a zero-vector and su       btract the vector of the first point.
		/    /Moving to      this point with the entire TransformGroup will then result in p     oint1 appearing at origo
		Tra nsform3D tf3d = new Transform3D();
		Vector3d zeroVec = new Vect    or3d(0,0,0);
//		System.o ut.println(this.getPosOfPoint1());
		zeroVec.sub(this.getPosOfPoint1());
		         tf3d.setTranslation(zeroVec);
//		System.out.pri    ntln(zeroVec);
		moveGroup.setTra     nsform(t   f3d);
	}
	
	
	/**
	 * Aligns the         X-coor   dinates wrt the Z axis and the center of the universe.
	 */
	private void innerAlign() {
		Vector3d point1V   ec =  this.getPo  sOfPoint1();
		Vec  tor3d point2Vec = this.getPosOfPoint2()  ;
//		System.out.println(po     int       1Ve  c.toString() + "\n" + point2Vec.toS  tr ing());
		
		     // The rotationFac   tor     SHOULD b        e enough   to rotate the TG to where we   want it.
		// However, since I'm not sure about dir ections etc we have the rest of this     class
		// which basically is a trial-and-error    ro tation untill we'  re satisfied.
		double    rotationFactor =    Mat   h.atan2(point2Vec.x , point2Vec.z);
		Transform3D rotati         onTransform = new Transform3D(); 
		Transform3    D tpaacTransform = new Transform   3D();
     		double point1x =    point1Vec.x;
		double    point2x = point2Vec.x;
		doubl   e point1xOld = point1Vec.x;
		double point2xOld = p    oint2V   ec.x;
		boolean rotpositive = true;
//		if(point2x > 0){
//		  	rotpositive = true;
//		} else      {
//			  r       otpositive =    false;
     //		}
		while(true){
			
			if((point1x == point2      x) || (Math.abs(point1x - point2x) <= core.model.Constants.ACCURACYVALUE)){
				break;
  			}
			if(rotpositive){
				rotationT     ransform.rotY(rotationF  actor);
			} else {
				rotationTransform.rotY(-rotationFactor);
			}
			innerRotationGroup.getTransform(tpaacTransform);
    			tpaacTr     ansform.mul(rotationTra  nsform);
			innerR  otationGroup.setTransfo  rm(tpaacTransform);
			
			poin     t1Vec =  this.get   Pos OfPo i    nt1();
			point      2Vec = this.getPosOfPoint2();
			poi     nt1x = poin    t1Vec.x;
			poi     nt2x = point2Vec.x;
			
			//I've forgotten what the   purpose of this is, bu   t it     works, usually.
			// Shouldn't use this any way, see the commen    t above the RotationFactor
			     if(  M   ath.abs(point1x - point2x) ==   Math .abs(point1xOld - point2xO   ld)){
				rota           tionFactor = rota  tionFactor / 2;
				point1xOld = poi nt1x;
				point2xOld = point2x;
			} else if(point1x - point2x > point1   xOld - point2xOld){
				rotationFactor = rotationFactor /       2;
				rotpositiv      e = !rotpositive;
				poi      nt1xOl d =    poi nt1x;
				point2xOld = point2x;
			}
//			System.err.pr   intl   n(point1Vec.toString() + "\n" + point2Vec.     toString());

		}
      //		System.out.println("   Bomf!");
		
	}
	
	
	/**
	 * Al  igns the Y-coordinates wrt the Z axis and the       center of t   he universe.
	 */
	private voi  d outerAlign()   {
		Vector3d    point1Vec = this.getPosOfPoint1();
		Vector3d point2Vec = this .ge  tPosOfPoin      t2();
//		 System.out.println(po    int1Vec.toString() + "\n" + point2Ve      c.toS     tring());
	       	
		/ / The rotationFactor      SHOULD be enough to ro  tate the TG    to    whe   re we want it.
		// Howev    er, since I'm not sure about directions etc we have th    e rest of this class
		// w   hich basi      cally is a trial-and-error rotation until l we're satisfied.
		double rotationFactor = Math.atan2(point2Vec.y , point2Vec.z);
		Trans    form3D rot   ationTransform = new Transform3D();
		Transform3D tpaacTr  ansform = new Tran      sform3D();
		do   uble point1x = point1Vec.   y;
		double point2x    = point2Vec.y;
	     	double p   oint1xOld = point1Vec      .y;
		double poin           t2xOld = point2Vec.y;
		boolean rotpositive =    true;
//		if(point2x > 0){
//			rotposi  tive = true;
//		} else {
//		   	rotpositive   = fa   lse;
/   /		}
 		wh  ile(true){
			
			if((point1x == point2x) || (    Math.abs(point1x - point2x) <= core   .model.Co nstan    ts  .ACCURACYVALUE)){
				bre  ak;
			}
  			if(rotpositive){
				rotationTransform.rotX(rotationFactor);
			} else {
				rotationTransfor  m.rotX(-rotationFactor);
			}
			outerRotationGroup.getTransform(tpaacTransform);
			tpaac      Transform.mul(rotationTransform);
	  		outerRotationGroup.set  Tra nsform(tpaacT ransform);
			
			point1Vec = this.getPosOfPoint1();
			point2Vec = this.getPosOfPoint2();
			point1x = point1Vec.y;
			point2x = p   oint2Vec.y;
			
			//I'v         e forgott      en what the pur pose of this is, but it works, usually.
			// Shouldn't    use this any way, see the comment above the  RotationFactor
			i      f(Math.abs(point1x    -         point2x) == Math.abs(point1xOld - point2xOld)){
	  			rotationFactor = rotationFactor / 2;
				point1xOld = point1x;
				   point2xOld = poi nt2x;
			}      else if(point2x > point2xOld)   {
				rotationFactor = rotationFactor     / 2   ;
				rotp    ositive = !rotpositiv     e;
				point1xOld = point1x;
				point2xOld = point2x;
			}
//	 		System.ou     t.println   (point1Vec.toString() + "\n" + point2Vec.toSt   ring   ());

		   }
		
//      		System.out.println("Bomf   !");
		
		
  		
	}
	
	
	/**
	 * Moves the object to the alignment-obje     ct, and rotat   es it int     o posi tion. innerAli  gn() and outerAlign should be called
	 * be fore this     one.
	 * @param alignmentOb jec    t  The object that will be aligned to.
	 */
	private void objectAlign(CTh      reePObject alignmentObject){
		Vec       tor3d aObjPoint1 = alig      nmentObject.getPosOfPoint1();
		Vector3d aObjPoint2 = alignmentObject.getPosOfPoint2();
		
		Point3d posPo   int = new Point3d();
	   	Point3d lookPoin   t      = new Point3d();
		
		posPoint.x = aObjPo     int1.x;
	   	posPoint.   y = aObjPoint1.y;
		pos   Poi   nt.z = aObjPoint     1.z;
		System.out.println(posPoint);

		
		lookPoint    .   x = aObjPoint  2.x;
		   lookPoint.y = aObjPoint2.y;
		lookPoint.z = aObjP oint    2.z;
		System.out.println(lookPoint);
		 
		//Positions the object a t        point1 of the other object, an   d     looks at the other objects point 2.
		transformer.look   At(posPoint, lookPoint, new Vector3d(0,1,0));
		transformer.invert();
		Transform3D rotTrans = new Transform3D();

		
		//Spins it 180ï¿½ sin ce the alignment cl         asses are off by exactly this. I    t'  s a bit lik     e using .invert after lookAt
		//Note to self: if we get a  bug later on where it sometimes is mis-aligned by 180      ï¿½, make the rotation late r, after    
		//checking if the second points align. 
		rotTrans.rotY(Math.PI);
		transformer.mu   l(r  otTra   ns);
	  	
		axisRotationGroup.setTransform(transfor      mer);
		System.out.println(this.g   etPosOfPoint1() + ", " + this.getP  osOfPoint2())    ;
	
		
		Vector 3d th    isVec3 = this.getPosOfPoint3();
		Vector3d       alignVec3 = alignmentObject.getPosOfPoint3();
		Point3d t  hisCompPoint = vectorToPoint(thisVec3);
		Point3d ali   gnCompPoint = vectorToPoint(alig    nVec3);
		Point3d thisCompPointOld = thisCompPoint;
		Point3d alignCompPointOld = alignCompPoint;
		double rotFactor = 0.00001;
		rotTrans = new Transform3D();
		Transform3D tempTrans = new Tr    ansform3D();
		boolean rotpositive = true;
		boolean ha sChosenRotationDi     r    ect   i    on = false;
		
		
		//Rotates       slo wly untill the two third points are aligned or as close to e ach other    a   s possible.
		while(true){
			
			//Check if they are the same or very close.
			if(thisVec3.equals(alignVec3) |    | (thisCompPoint.distance(alig   nCompPoint) < core.model  .Con   sta  nts.ACCURACYVALUE)){
				r     eturn;
			}
			
			//Rotate one way or the other?
			if(rotpositive){
				rotTrans.rotZ(rotFactor);
			    } else {
				rotTrans.rotZ(-r    otFactor);  
			}
			
			//Do the transform
			axisRotationGroup.getTransform(tempTrans)      ;
			tempTrans.mu           l(rot  Trans); 
			axisRotationGroup.setTransform(tempTrans);
			
			thisVec3 = this.getPosOfPoint3();
			alignVec3 = alignmentObject.getPosOfPoint3();
			thisCompPoint = vectorToPoint(thisVec3);
			alignComp   P   oint = vectorToPoint(alignVec3);
			
			//Check if we've come as close as we can
			if(thisCompPoint.distance(alignCompPoint) > thisCompPointOld.distance(alignCompPointOld)){
				if(hasChosenRotationDirection){
					return;
				}    else {
					hasChosenR     otationDire  ction = !hasChosenRotat   ionDirection;
					rotpositive = !  rotpositiv   e;
				}

				
			}
			
			thisCompPointOld = thisCompPoint;
			alignCompPointOld = alignCompPoint;
//			System.out.println(thisCompPoint.toString() + "\n" + alignCompPoint.toString());
  //			break;

			
			
		}
		
		
		
		
		
		
		
		
		
	}
	
	
	/**
	 * Help-method that translates the coordinates of a vector into a point3d
	 * @param vector The vector to be translated
	 * @return point The translated point
	 */
	private Point3d vectorToPoint(Vector3d vector){
		Point3d retval = new    Point3d();
		retval.x = vector.x;
		retval.y = vector.y;
		retval.z = vector.z;
		return retval;
	}

	/**
	 * returns the estimated center point of the object
	 */
	public Point3d getObjectCenter() {
		return objectCenter;
	}
	
	/*         *
	 * sets the estimated center point of the object
	 */
	public void setObjectCenter(Point3d objectCenter) {
		this.objectCenter = objectCenter;
	}
	
	

}
