package    logic.entities;

import java.util.ArrayList;

/**
     * Descr    ibes an  Object that contains multiple <i>logic.punkt</i> called Vertices    <br>
 * <b>x,y,z,w</b> des   cribe t  h   e cent  er of th e Object<br>
 * <b>      x   ,y,z,w</b> o   f the Vertices describe their         relative pos    ition to th    e center
 *       of the Object<br>
 * <b>co    nnectedVertices</b> contains t    he Ve      rtices that ar     e connected by a line<br>
 *   
 * @author Julian Toelle
 * 
 */
p  ublic abstract class Abstr       actMultipoin   tObject extends Dot   {

   	private   ArrayList<Dot>   dots = new ArrayList<Dot>();
	private int[][] c onnec    tedV ertices;
	public int VERTICES;
	public String NAME;

	public ArrayList<Dot> getDots() {
		return this.dots;
	  }

	pub lic void setD   ots(ArrayList   <Dot> dots) {
		this.dots = dots;
	}

	pub      lic int[][] get ConnectedVertices() {
		return t   his.connectedVertices;
	}

	public voi    d setConnectedVertices(int[][] connectedVertices) {
		this.connect ed     Vertices = connectedVertices;
	}

	public int ge   tVertices(   ) {
		return      this.VERTI     CES;
  	}

	private void    setVertices(   int vertices) {
		t       his. VERTIC           ES = v   e  rtice         s;
	}

	/**
	 * I                nitiate    s       a                  new Object
	    * 
	 * @param x
	   *            The Center-x coo    r dinate
	    * @  p   aram y
	 *            The     Cent    er-y coordin    ate
	 * @pa       ram z
	 *            The     Center-z coo     rdinate
	 *           @param w
	 *             T   he Center-w c                oordinate
	 *   /
	p   ublic A bstractMulti   pointObject(  doub      le x, double     y, double z, d  ouble   w) {
		su per(x, y, z,   w      );  
	}

	/*    *    
	 *        I    nitiates     a new O   bjec        t and sets the Vertice s
	 * 
	  * @p        aram   x
	 *            The Center-x coordinat        e
	    *         @param       y
	  *               The Cente    r    -y coordina    te
	 * @param z 
  	 *                    The Ce n   ter-z coordin                      ate
	              *       @param The
	 *            Center-w coordinate
	 * @      param dots
	 *             The dots t  hat build the cube.
	 */  
	p    ublic Abstra        ctMultipointOb  jec  t(double x, do ub    le y, do    uble z, dou  ble w,
			ArrayList<Dot> dots)    {    
		super          (x, y, z, w);
	         	this.se    tDots(dots   );
	}

	/**
	 * Sets the Radiant for ev     ery Vertice   s new   and then updates it's position.
	 *    
	     *   @param r       ad
  	 *                 Ang   le in Radiant
	 */   
	public void ro     tate(doub         le rad) {
		for (Dot dot : this.getDots()) {
			f   or (int i  = 0; i < 6; i+   +) {
			    	dot.s etAngle(i, dot.getAngles()[i]        + rad);   
			}
			    dot   .update();
		}
	}

	/**
	 * Sets the Radiant f or    every   Vertices new and then updates it's position.
	 * 
	 * @param     rad
   	 *                  Angle in Radiant
	 * @param axis
	 *            Axis to be set
	 */
	public void rotate(double rad,   int axis) {
		for (Dot dot : this.getDots()) {
			dot.setAngle(axis, dot.getAngles()[axis] + rad);
			dot.update();
		}
	}
}
