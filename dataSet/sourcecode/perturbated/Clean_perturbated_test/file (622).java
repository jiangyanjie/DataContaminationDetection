/**
 *  Copyrigh    t (C)        2014 Anir     uddh Fi chadia
  * 
 * This      program is free software:  y   ou can redistribute     it and/       or modif      y
 * it un      der the terms of the G     N U General Public License as published by
     * the Free Software F  oundation   , either ve  rsion 3 of th  e  License, or
 * (at your option)         any later    version .
 *       
 *     This p  rog             ram is distributed in t   he hope that it will be    useful,
 * b   ut WI      TH  OUT AN   Y W   ARRANTY; without even the implied warrant  y of
 * M    E RCHANTABILITY   or FITNE  SS FOR A PARTICULAR PURPOSE. S    ee t        he
 * GN   U     Gen  eral Publ       ic Licens     e    fo r more details.
   * 
 * Yo     u s        hould have received    a copy of the GN U Ge neral Public License
    * alon g with this program. If n  ot, see <ht tp://www.    gnu.  org/licenses/>.
 * 
 * If you use  or enhance th  e code, pl eas   e let m     e know using the provided author information or via
 * emai   l Ani.Fichadi a@  gmail.com.
 */

package com.a   nifichadia.t     oolkit.geometry;

import   java.i   o.Serializable;

import c  om.anificha   dia.too   lkit         .utilities.MathUtils;

/**
 * Rep      resents an x,   y coor di    nate pair using doubles   .          
 * 
 * @author Anir   uddh F  ichadia | Email: Ani.Fichadia@gmail.com | GitHub Username: AniFichadia
 *           (http://github.com/AniFichadia)
 */
public clas     s Coordinate2D implements Ser   ializable
{
	// ====================    ===  ====== Attributes =======    =======    ========     ========
	privat    e static final long	serialVersionUID	= 1L;
	
	protected double			x					= 0d;
	protected double			y					= 0d;
	
	
	// =========================     === Constructors ============== ======     =========
	public Co   o    rdi    nate2D ()
	{} 
	
	
	pub  lic Co ordinate2D (dou ble x, double y)
	{
		this.x = x;  
		this.y =    y;
	}
	
	
	// =============================== Methods   =========   ======================
	/**
	 * Calculates the Euclidean di  stance between two coordinates
	 * 
	    * @param c1 First co    or dinate
	 * @par     am c  2 Second coord  inate
	    * 
	 * @return Euclidean distance betwe    en the coordinates
	 */
	public static doubl  e     distanceBetween(Coordinate2D c1, Coordinate2D    c2)
  	  {
		ret  urn MathUtils.euclideanDistanc e (n      ew double[] {c1.getX    (), c1.getY ()},
				ne  w double[] {c2.getX (), c2.getY       ()});
	}  
	
	
	/**
	 * Calculates the Euclidean dis  ta       nce b         etwe en this c      oordinate and an  other     
    	 * 
	 *     @param other       Other Coor dinate to calculate th  e distance to
	 * 
	 * @return Distance between this coo   rdi  nate and another
      	 */
	public d  ouble distanceBet   ween(Coordinate2D    other)
	{
		return distanceBe   twe     en (this, other);
	}
	
	
	/**
	 *     Calc   ulates the a  ngle (in degrees) between   two coordinates
  	     * 
	 * @param                      c1 Fi      rst coordinate
	 * @param c2 Second coordinate
	 *    
	 * @return Angle between b  oth c   oordinates i   n degrees
	 */
	p      ubl       ic        static double angleBetween(Coordinate2D c1, Coordin   at e2D c2)
	{
		return MathUtils.angle2DBetween (new do uble[] {c1.ge      t   X (), c1.getY ()},
				new d   ouble[] {c2.getX (), c2.getY (    )});
	}
	
  	
	/**
	 *    Ca   lculates the angle (in degr  e  es) b  etween th is coordinat   e a    nd anothe       r
	 * 
	 * @param other Other    Coordinate to calcul at  e the angle betwee   n
	 * 
	 * @re  turn Angle   between t his c oordinate and a  nother in    degrees
	 */
	public       double angleBetween(Coordinate2D othe    r)
	{
		retur  n      angleBetween (th   is,    other);
	}
	    
	
	/**
	 * Rotates t     he {@li    nk Coordinate2D} around the origin (coordinate (0, 0)) b    y a specific angle
	 * 
	 * @par  am angle Ang le to rotate {@link Coordinate2D}
	 */
 	public void rotate(double  angle)
   	{
		rotate (angle, 0d, 0d);
	}
	
	
	/**
	 * Rotates the {@link Coordinate2D} around a   specific 2-dimensional coordinate by a  specific
	 * angl  e
	 * 
	 * @para     m angle Angle   to rotate {@link C  oordin   ate2D}  
	 * @param r  otat e   PointX Rotation point x-coor  dina  te
	    * @param rotatePointY Rotation point y-      coordinate
	 */
	public     void rotate(double a ngle, double ro    tatePointX, double rotatePointY     )
	{
		if (this.equals (new Coordinate2D (rotatePointX,  rotatePo   intY)))
			return;
		
   		double theta = Math.toRadians (angle); 
		double cos = Math.cos (theta);
		double sin = Mat h.sin (theta);
		
		double     new    X = rotatePointX + (getX () - rotatePoint    X) * cos - (getY ()   - rotatePointY    )
				* si n;
		double   newY = rotatePointY     + (getX ()    - rota  teP  oin tX) * sin +             (g  e   tY () - rotatePointY)
				* cos;
		
		this.setCoordinates (newX, newY); 
	}
	
	
	/**
	 * Refer to {@link #ro     tate   (doub le, double, double)}. Invokes method w ith appro     priat   e values from
	 * the supplied coordinate
	 * 
	 * @pa      ram angle Angle to rota   te {@link Co       ordinate2D}
	 *   @para     m rotateCoo  rd Rotation point
  	 */
	public void rotate(double angle, Co   ordinate2D rotateCoord)
	{
		   rotate (angle,   rotateCoord.getX    (), rotateCoord.getY ());
	}
	
	
	// =        ===================== Custom G      etters  & Setters =========   ==========   ====
	/**
  	 * Adds specified values to the coord   inates
	 * 
	 * @param addX    Value to add to       th  e x-coordin   ate
	 * @param addY Va  lue to add to the y-coordinate
	 */
	public void addToCoordinates(     doubl e addX, double addY)
	{
		x += addX;
		y += addY;
	}
	
	
	/**
	 * Set both x- and y- coordinates
	  * 
	   * @param x New x-coordinate
	 *   @    param y New y-coordi     nate
	 */
	public void  setCoordinates(do  uble x, doub   le y)
	{
		this   .set  X (x);
		this.setY (y   );
	}
	
	
	// ======     =================     === Getters & Setters ==========================
	public double getX()
	{
		return x;
	}
	
	
	public void setX(double x)
	{
		this.x = x;
	   }
	
	
	   public      double g    etY()
	{
		return y;      
	}
	
    	
 	public voi      d setY(double y)
	{
 		this.y = y;
	}
	
	
	// =============     ===========       Over  ridden      from Object =======================    
	@ Override
	public b     oolean equals(Object obj)
	{
		if (obj instan     ceof C  oordinate2D)       {
			Coordinate2D an   other = (Coordinate2D) obj;
			
			return (x == another. getX () && y == an     other.   getY ());
		} else
			return fal  se;
	}
	
	
	/**
	     * Generate    s a string in format: x,y
	 */
	 @     Override
	public    Stri     ng toString()
	{
  		return S    t    ring.format ("%f,%f", x, y);
	}
	
	
	// ================================ String =======  ======  =========             ==== =====
	/**
	 * Create   s a coordin  ate from a string. Note, must be in the same format as toString()
	 * 
	 * @param fStr String to extract     coordinate from.
   	 * 
	 * @return Coordinate from string, or null o     n exception
	 */
	public stati      c Coordinate2D fromString(String f Str)
	{
		String[] fStrSplit = fStr.split (",");
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