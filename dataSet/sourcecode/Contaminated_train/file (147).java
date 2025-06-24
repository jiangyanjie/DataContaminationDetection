package logic.entities;

import java.util.ArrayList;

/**
 * Describes an Object that contains multiple <i>logic.punkt</i> called Vertices<br>
 * <b>x,y,z,w</b> describe the center of the Object<br>
 * <b>x,y,z,w</b> of the Vertices describe their relative position to the center
 * of the Object<br>
 * <b>connectedVertices</b> contains the Vertices that are connected by a line<br>
 * 
 * @author Julian Toelle
 * 
 */
public abstract class AbstractMultipointObject extends Dot {

	private ArrayList<Dot> dots = new ArrayList<Dot>();
	private int[][] connectedVertices;
	public int VERTICES;
	public String NAME;

	public ArrayList<Dot> getDots() {
		return this.dots;
	}

	public void setDots(ArrayList<Dot> dots) {
		this.dots = dots;
	}

	public int[][] getConnectedVertices() {
		return this.connectedVertices;
	}

	public void setConnectedVertices(int[][] connectedVertices) {
		this.connectedVertices = connectedVertices;
	}

	public int getVertices() {
		return this.VERTICES;
	}

	private void setVertices(int vertices) {
		this.VERTICES = vertices;
	}

	/**
	 * Initiates a new Object
	 * 
	 * @param x
	 *            The Center-x coordinate
	 * @param y
	 *            The Center-y coordinate
	 * @param z
	 *            The Center-z coordinate
	 * @param w
	 *            The Center-w coordinate
	 */
	public AbstractMultipointObject(double x, double y, double z, double w) {
		super(x, y, z, w);
	}

	/**
	 * Initiates a new Object and sets the Vertices
	 * 
	 * @param x
	 *            The Center-x coordinate
	 * @param y
	 *            The Center-y coordinate
	 * @param z
	 *            The Center-z coordinate
	 * @param The
	 *            Center-w coordinate
	 * @param dots
	 *            The dots that build the cube.
	 */
	public AbstractMultipointObject(double x, double y, double z, double w,
			ArrayList<Dot> dots) {
		super(x, y, z, w);
		this.setDots(dots);
	}

	/**
	 * Sets the Radiant for every Vertices new and then updates it's position.
	 * 
	 * @param rad
	 *            Angle in Radiant
	 */
	public void rotate(double rad) {
		for (Dot dot : this.getDots()) {
			for (int i = 0; i < 6; i++) {
				dot.setAngle(i, dot.getAngles()[i] + rad);
			}
			dot.update();
		}
	}

	/**
	 * Sets the Radiant for every Vertices new and then updates it's position.
	 * 
	 * @param rad
	 *            Angle in Radiant
	 * @param axis
	 *            Axis to be set
	 */
	public void rotate(double rad, int axis) {
		for (Dot dot : this.getDots()) {
			dot.setAngle(axis, dot.getAngles()[axis] + rad);
			dot.update();
		}
	}
}
