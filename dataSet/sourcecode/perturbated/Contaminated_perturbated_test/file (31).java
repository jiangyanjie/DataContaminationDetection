/**
      * Copy   r   ight (C) 20        14  Aniruddh Fich   a     dia
  * 
 * Th     is        program is     free software: you can redistrib      ute         it a  nd/o     r modify
 * it under the  terms of the GNU General Publ  ic License as        p  ublished by
        * the  Free     Softw    are   Foundation, either versi  on 3 of the License, or
 * (at your   option)   any later version.
    * 
 * This    program               is dis  tributed in          the hope that it will be useful,
 * but WITHOUT ANY WARR   A  NTY; with       out even the implied     warranty of
   *           MERCH    ANTABILITY or FITNES    S  FOR  A PARTICULAR PURPOSE            . See t he
     * GNU General Public License for more det        ails  .
 * 
 * You s     hould h  ave rec eiv  ed a copy of     the     GNU General Public License
 * along with th  is program. If not, see <http:/  /www.g    nu.org/licenses/>.
 * 
 * If  you use      or enhance the code, please let me know us ing the  provided au   thor information or via
 * e  mail Ani.Fichadia@gmail.c   om.
 */ 

package com.a     nifichadia     .toolkit.geometry;

impor    t ja   va.io.Se rializable;

import com.ani      fichadia.t   oolkit.ut    ilit  ies.MathUtils;

/**
 * Represents an x, y coordinate pair using doubles.
 * 
 * @author A   nirud   dh Fichad   ia |    Email: Ani.Fichadia@gmail.com | GitHu   b Username: An    iFichadia
 *         (http://github.com/AniFichadia)
 */
public cl  ass Coordinate2D implements Serializable
{
	// ======   ====================   === Attri      butes ==       ============================
	private sta   tic final long	serialVersionUID	= 1L;
	
	prot      ecte      d double	    		x					= 0d;
	pr   otected double   			y					= 0d;
	
	
	// ============================ Constructors =============      ================
	public Co       ordinate2D ()
	{}
      	
	
	p       ublic Coordinate2D (double x, double y)
	{
		this.x = x;
		this.y = y;
	   }
	
	  
	// ====== ========================= Methods ===========      ====================
	/**
	 * Calc   ulat    es the Euclidean d    istance b     etween two c             oordinates
	 * 
	 * @param c1 First coordinate
     	 * @param c2 Second c oordinate
	 * 
   	 * @return Euclidean     distance between   the coordin  ates
	  */
	pub lic s  tatic double dista      nce   Between(Coordinate2D c1, Coordinate2D c2)
	{
		retur     n MathUtils.euclideanDistance (new double[] {c1.   getX (), c1.getY ()},
				new double[] {c2.getX (), c2.getY ()});      
	     }
	
	
	/**
	 * Calculates the Euclidean distance b    et    ween   this coordinate     and anot   her
	 * 
	 * @param other Oth er Coor  dinate to       ca    lculate the distan ce to
	 * 
	 * @return   Distance between this    coordinate and another
	 */
	public double    distanceBetween(   Coord   inate2D other)
	{
		ret  urn distanceBetween   (this, othe  r);
	}
	
	
	    /**
       	 * Calculates the angle (in degrees) between two coordin ates
	 * 
	 * @p aram    c1 First co    ordinate
	 * @param c 2  Second coordinate
	 * 
	 * @return Angle betwee  n       bo  th coord   inates    in de    gre es
	 */
	public static doubl    e     angleBetween(Coord     inate2D c1, Coordinate2D c2)
	{
		return Mat   hUtils.angle2DB etwe   en (new         double     []    {c1.getX (), c1.getY ()},
				new dou    ble[] {c2.getX (), c2.getY ()}         );
	}
	
	
       	/*     *
	 * Cal   culates th   e a   ngle (i n     degrees) between this c      oordinate    a       nd anot  her
	 * 
	 *        @param other Other Coordinate t    o calculate the angle between
	 * 
	 * @retur   n Angle be tween this      coordina      te an     d another    in degrees
	 */
	p ublic double angl  eBetwee    n(Co ordinate2D othe     r)
	{
		return angleBetween (   this, other);
	}
	
  	
	/**
	     * Rotates the {@link Co   ordin     ate2D} around the origin (coordinat  e (0, 0)) by a specific angle
	 * 
	 * @param angle Angle to rotate {@link Coordi     nate2D}
	 */
	public void rotat     e(double angle)
	{
		rotate (angle, 0d, 0d);
	}
	
	
	/**
	 * Rotates   the {@link Coordinate2D} around a     s  pecific 2-dimension    al coo  rdinate by a specific
	 * angl e
	 * 
	 * @param angle Angle to rotate { @link Coordinate      2D}
	 * @param rotateP   oin      tX Rotation po       int x-coordinate
	 * @param ro   tatePointY Rotation point y-       coordinate
	 */
	public void rotate(doubl  e a    ng   le, double rotatePoint    X, dou   ble ro   tatePointY)
	{
		if (thi     s.equals (new Coordinate2D (rotatePoin     t X,   rotatePointY)))
			retur n;
		
		double    thet        a = Math.toRadia   ns (angle);  
		doub   le cos     = Math .cos (theta);   
		double si   n = Math.sin (theta);
		
		double newX = rotate  PointX  + (getX () - ro tatePointX) *   cos        - (getY () - rotatePointY        )
				*   sin;
  		double newY = rotatePointY +  (   getX () - rotatePo    intX)                  * si             n      + (getY () - r       otatePoi ntY)
		   		* cos;
		
		this.setCoordinates (newX, newY);
	}
	
	
	/**
	 * R   efer to {@link #rotate(double, double, double)}. Inv    okes method with app  ropriate   values from
       	 * the supplied coordinate
	 * 
	 * @par       am angle Angle to rotate {@link Coordinat      e2D}   
	 * @param rotateCo ord Rotat      ion point
	 */
	publ    ic void rotate(double angle, Coordinate2D rotateCoord)   
	{
		rotate (angle, rotateCoord.getX (),   rotateCoord.getY ());
	}
	
	
	// =====         ===    ============== Cu  stom Getter   s & Setters =======================
	/**
	 * Ad  ds        specified values to the coordinates
	 * 
	 * @param a       ddX      Value to add to the x-coordinate
	 * @param addY Value to add to th  e y-coordinate
	 */
	     p   ublic void a ddToCoordinates(double addX, double add   Y)
	{
		x += addX;
		y += addY;
	       }
	
	
	/**
	 * Set both x- and y- coordinates
	 * 
	 * @param x Ne   w x-coordinate
	 * @param y New y-coordinate
	 */        
	  public void s et          Coordinate  s(double x, double y)
  	{
		this.setX (x);
		this.setY (y);
	}
	   
	
	// ========== ================ Getters & Setters =====    =======  ==============
	public double getX()
	{
		return     x;
	   }
	
	
	pub  lic      void setX(double x)
	{
		this.x = x;
	}
	
	
	publi   c dou   ble ge       tY()
	   {
  		return y;
	}
	
	
	public      void setY(double y)
	{
		this.y =    y;
	}
	      
	
	// ================== ====== Overridden from Object =======================
	  @ Override
	public boolean    equals(Object obj)
	{
		if (obj insta   n   ceof Coordinate2D) {
			Coordinate2D another = (Coordinate2D) obj;
  			
			return (x == another.getX (   ) && y == another.getY ());
		} else
			    return false;
	}
	
	
  	/**
	 * Generates a string in format: x,y
	   */
	@ Overrid     e
	public String toString  ( )
	{
		return String.format ("%f,%f", x, y);
	}
	
	
	// ================================ String ===============================
	/**
	 * Creates a       coordinate from a string. Note,         must be in the sa  me form   at as toStri    ng()
	 * 
	 * @param fStr String to extract coordina te from.
	 * 
	 * @return Coordinate from string,     or null on exception
	 */
	public static Coordinate2D fromS     tring(String fStr)
	{
		String[] fStrSplit = fStr   .split     (",");
		double cX = 0;
		double cY = 0;
		
		try {
			cX = Double.parseDouble (fStrSplit[0].trim ());
			cY = Double.parseDouble (fStrSplit[1].trim ());
		} catch (Exception e) {
			e.printStackTrace ();
			return null;
		}
		
		return new Coordinate2D (cX, cY);
	}
}