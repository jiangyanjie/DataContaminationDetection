package moteur;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;

import Main.Script;
import affichage.Etiquette;
import affichage.PanelGraph;
import interpreteur.Forme;

public class CourbeDeBezier extends Chemin {
	private Point p1, p2, p3, p4;
	private boolean dessiner;
	private boolean remplir;
	private boolean etiqueter;
	private Color remplissage;
	public CourbeDeBezier(Point p1, Point p2, Point p3, Point p4) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
	}
	public CourbeDeBezier() {
	}
	public Point getDebut() {
		return p1;
	}
	public void setDebut(Point d) {
		this.p1 = d;
	}
	
	public Point getPoint2() {
		return p2;
	}
	public void setPoint2(Point d) {
		this.p2 = d;
	}
	
	public Point getPoint3() {
		return p3;
	}
	public void setPoint3(Point d) {
		this.p3 = d;
	}
	
	public Point getFin() {
		return p4;
	}
	public void setFin(Point d) {
		this.p4 = d;
	}
	
	public boolean isDessiner() {
		return dessiner;
	}
	@Override
	public void dessiner() {
		this.dessiner = true;
	}
	public boolean isRemplir() {
		return remplir;
	}	
	public void remplir() {
		this.remplir = true;
	}
	@Override
	public void paintComponent(Graphics g, PanelGraph panelGraph, Crayon trace) {
		CubicCurve2D.Double curve = new CubicCurve2D.Double(p1.abscisse(), p1.ordonnee(), p2.abscisse(), p2.ordonnee(), 
				p3.abscisse(), p3.ordonnee(), p4.abscisse(), p4.ordonnee());
		Graphics2D gra = (Graphics2D) g;
		if (dessiner) {
			gra.setColor(trace.getCouleur());
			gra.setStroke(new BasicStroke((float)trace.getEpaisseur()));
			gra.draw(curve);
		}
		if (remplir) {
			gra.setColor(this.remplissage);
			gra.fill(curve);
		}
		if (etiqueter){
			//Point p = new Point(centre.abscisse(), centre.ordonnee()-rayon);
			Etiquette E = new Etiquette("Courbe", p1);
			Script.getEtiquettes().add(E);
		}
	}
	@Override
	public void remplir(Color col) throws Exception {
		this.remplir = true;
		this.remplissage = col;
	}
	@Override
	public void inserer(Forme fig) {
		// TODO Auto-generated method stub
	}
	@Override
	public void etiqueter() {
		// TODO Auto-generated method stub
		this.etiqueter = true;
	}
	@Override
	public Forme creer(Crayon sh) {
		Dessin dess = new Dessin(this, sh);
		Script.getDess().add(dess);
		return this;
	}		



	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub
		return null;
	}

	public static CourbeDeBezier Bezier(Point p, Point r, Point s, Point l, float largeurCray) {
		CourbeDeBezier cr = new CourbeDeBezier(p, r, s, l);
		Crayon cray = new Crayon((int)largeurCray, Color.black);
		Script.creer(cr, cray);
		return cr;
		
	}
	
	public CourbeDeBezier creer(Point p, Point r, Point s, Point l) {
		return new CourbeDeBezier(p, r, s, l);
	}
	@Override
	public boolean executer() {
		Script.creer(this);
		return true;
		
	}


	

}
