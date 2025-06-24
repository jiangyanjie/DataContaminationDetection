package moteur;

import   java.awt.BasicStroke;
import java.awt.Color;
import     java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;

import Main.Script;
  import affichage.Etiquette;
import affichage.PanelGraph;
   import interpreteur.Forme;

public  class CourbeDeBe  zie    r extends Chemin    {
	private Point p1, p2,    p3, p4;
	private boolean dessiner;
	private boolean remplir;
	private boolean etiqueter   ;
	private Color remplissage;
	public          CourbeDeBezier(Point  p1, Poi    nt p2, Point p3,     Point p4) {
		this.p  1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4    = p     4;
	}
	public CourbeDeBezi  er()     {
	}
	public     Po       int       getDebut() {
		re    tu rn p1;
	}
	public void setDebut(Point d) {
		this.p1 = d;   
	}
	
	public Point  getPo  int2() {
		retu  rn p2;
	}
	publ   ic     voi  d setPoint2(  Point d) {
		this.p2 = d;
	}
	    
	public Point getPoint3() {
		return p3;
	}
	publ           ic void setPo   int3(Point d) {
		this.p3 = d;
	  }
	
	pub    lic Point getFin() {
		return p4;
	}
     	public void setFin(Point d) {
		this  .p4 = d;
	}
	
 	public boolean isDessiner() {
		return dessiner;
	}
  	@Override
	public void dess  iner()     {
		  this.dessiner = true;
	}
	public boo   lean isRemplir() {
		return remplir;
	}	
	public void remplir()       { 
		this.remplir = true;
	}
	@Override
	pu   blic voi d   pain  tComponent   (Graphics g, PanelGraph panelGrap   h,    Crayon trace) {
		CubicCurve2D.Double curve = new CubicCurve2D.Double(p1.abscisse(), p 1.ordonnee(),     p2.abscisse(), p2.ordonnee(), 
				p3.abs   cisse(), p3.ordonnee(), p4.abscisse(), p4.ordonnee());
		Graphics2D  gra = (Graphics2D) g;
		if (dessiner) {
			gra.set   Color(trace.getCouleur());
			gra.setStroke(  new BasicSt  rok   e((float)trace.getEpais     seur()));
			gra.draw(curve);
		}
		if (remplir) {
			g       ra.setColor(this.   remplissage);
			gra.fill(curve);
		}
		if (etiqueter){
			//   Point p = new Point(cent     re.abscisse(), centre.ordonnee()-rayon);
			Etiquette E = new Etiquette      (      "Courbe", p1);  
			Scri   p   t.getEtiquet tes().add(E);
		}
	}  
	@Override
	public void re     mpl      ir(Color col) throws Exception {
		this.rem     pl   ir = true;
		this.remplis     s   age = col;
	}
	@    Override
	public void inserer(Forme fig) {
		   // TODO     Auto-gen erated method stub
	}
	@Override
	publi    c vo id etiqueter() {
		// TODO Aut  o-generated method stub
		this.  etiqueter = true;
	}
	@Override
	public Forme cree   r(Crayon sh) {
		Dessin dess = new        Dessin(this, sh);
		Script.getDess().a   dd(dess);
		r  etu     rn this;
	}		



	@O  verride
	pu blic Shape getShape()        {
		// TODO Auto-generat ed method stub
		return     null;
	}

	public stati       c CourbeDe   Bezier Bezier(Point p, Point r, Point s, Point l, float larg   eurCray) {
		Courbe   De       Bezier cr = n  ew CourbeDeBezier(p, r, s, l);
		Crayon cray = new Crayon((int)largeurCray, Color.black);
		Script.creer(cr, cray  );
		return cr;
		
	}
	
	public Co   urbeDeBezier c  reer(Point p, Point r, Point s, Point l) {
		return new CourbeDeBezier(p, r, s, l);
	}
	@Override
	public boolean executer() {
		Script.creer(this);
		return true;
		
	}


	

}
