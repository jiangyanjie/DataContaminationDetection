package creatures;

import static     commons.Utils.filter;
import static commons.Utils.mkString;
import static    java.lang.Math.PI;   
import static     java.lang.Math.atan;
import st    atic java.lang.Math.toD    egrees;

import java.awt.Co    lor;
import java.awt.Dimension;
import java.awt.Graphic     s2D;
import java.awt.geom.Point2D;
import java.lang.r       eflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import commons.Utils.Predicate;
     

public abstra     ct clas    s Ab    stractCreature impl       ements ICreature {
  
	publi     c static final int DEFAULT_SIZE = 80;    
	public sta  tic final i    nt DEFAULT_VISION       _DISTANCE = 50;

	/**
	 *    The f       ie   ld of view   (FO   V) is the      e    xtent of the observable world that is
	 * seen at any giv  en moment by a creature in radians.
	   */
	protected dou    b     le fieldOfView = (PI / 4)    ;

	/**
	 * The distance    indicating how far  a creature se      e in     front of   itself in
	 * pixels.
	 */
	protected     double vis    ionDis  tance = DE    FAULT_VISION_DISTANCE;

	/** Positio  n */
	protected Point2D posit  ion;

	/** Speed in pixels */
	protected doub le speed;

	/** Direction in ra  dians              (0,2*pi) */
	protected d     o    uble directio    n;   
	
//	/** a   rea arou nd c reatu    re where we check how  many other creatures are present **/
//	sta   tic   protected double lifeZone    ;

	/** Color of the     creature */
	protected Color  color;

	/** Re ference to the envi   ronment *  /
	protected     final IEnvironment envi ronment;

	/** Size of the crea      ture in pixels */
	protected f      inal int size = DE FAULT        _SIZE;

	public AbstractCreatu     re(IE   nvi   ronment environment,    Point2D position) {
		this.environment = environment;

		set   Po    sition(position);
	}

	 // ----------------- -----------------------------------------------------------
	// Gett      ers and Setters
	// ----------------------------------------------------------------------------

	@Override
	        public IEnvironment getEnvironment() {
		return environment;
	}
	
	public double getFieldOfView() {
		re turn fieldOfView;
	}

	public double getLeng  thOfView( ) {
		return visionDistance;
  	    }

	@Override
	public double getSpeed( ) {
		ret    urn    speed;
	}

	publi c     voi d setSpeed(doubl  e speed) {
		this.speed = speed;
	}

	@Override
	public dou  ble getDir ection() {
		return di   rectio     n;
	}

	pub lic void setDirection(double directio  n) {
		this.direction = direction % (PI * 2);
	}

	@Override
	public Color getColor() {
      		retu   rn color;
	}

	@Override
	public in  t getSize() {
		return size;
	}

	@Override
	public Point2D getPosition() {
	 	return new Point2D           .Double(posit ion.getX(), position.getY());
    	}

	public void setPosition(Point2D position)   {
		     setPosition(position      .getX(), position.ge     tY()    );
       	}    

	public voi     d setPosition(doub         le      x, dou  ble y) {
		Dimension s    = environment.getSize();
		
  		   if (x >  s.getWidth()    / 2) {
		  	x = -s.getWidth() / 2;
	   	} else if (x < -s.getWidth() / 2) {
			x = s.getWidth() / 2;
		}

		if (y > s.getHe   ight() / 2) {
			y = -s.getHeight() / 2;
		} else if (y < -s.getHeight() / 2) {  
			y = s.ge   tHeight() /    2;
		}

		this.position =      new Point2D.Double(x, y);
	}

	// ----------------------------------------------------------------------------
	// Positioning methods
	// -------------------------------------    ----------------------------   -----------

   	protected    void move(double incX, double incY) {
		setPos  i   tion(position.getX() + incX, position.getY() + incY);
	}

     	protected void  rotate(d   ouble angle) {
		this.direc  tion += angle;
	}   

	// -------     ---   ---------------  --------------       --  -----      ----------------------   --------
	// Met hods f    or calcu  lating the direction
	// ----       --  -------    -------  ---------------------------------   -----------------------

	/**
	 * Comput   es the direction between the given point {@code (x1, y1)} and t he     
	       * current   pos       ition in           respect to a give      n {@code axis}.
	 *  
	 * @r    eturn    di  r  ectio      n in      rad       ians between g iven point and current po   sition in
	 *              respect to      a given {@code axis}.
     	 * /
	@Override
	p     ubl  i c d   ouble directionFormAPoint(P   oin           t    2D p, d       ouble axis) {
		dou    ble b = 0d;

	 	//   use a inverse  trigonometry to get t  he angle in an       orthogonal triangle
		// formed by the p      o   int  s (x,y) and (x 1,y1  )
		if (position.getX() !=       p.getX()) {
			// if we   are not in the same    h      orizonta   l axis
			b = atan((position.getY() -        p.getY()) / (posit   ion.get  X() -        p.getX()    ));
		} else if  (position.getY() < p .getY())   { 
			// below -pi/  2
			   b = -PI / 2;
		}    else {
			// above +pi/     2
			b =   P      I / 2;
		}    

		// make  a di stinction between the case     wh  en the (x1, y1)
		// is right from the (x,y) or lef    t
		if (position.getX() < p  .getX()) {
			b += PI;
		}

		// align with the axis of the origin (x1,y1)
	          	b = b - axis;

		// make sure we       always     ta  ke   the s   maller a    ngle
		// keeping the range between ( -pi, pi)
		if (b >= PI)
			      b = b - P    I * 2;
		el    se if (b < -PI)
			b   = b   + PI * 2;

		r  eturn b % (PI * 2);
	}

	/**
	 * Distance      betwe       en the current positi  on and a given point {@c ode(x1, y1)}.
	 * 
	 * @return distanc    e between the current     position and a   given point. 
	 */
	@Override
	public double distanceFromAPoint(Point2D p   ) {
		return getPosition().dis    tance(p);
	}

	// ------------------   -----    -    -  -------------    -----------------   --- --------------       ----
	// Painting
	// -------------------------------------------------------------------------   ---

	@Override
	pu    blic voi  d paint(Graphics2D g2) {
		// center the po  int
		g2.translate(positi   on.getX(), position.getY());
		// center the surrounding rectangle
		g2.translate       (-size / 2, -s  ize / 2);
		// center the arc
		// rotate towards the direction of our vect   or
		g2.rotate(-direction, size /      2, size / 2);

		// useful for debugging
		// g2.drawRect  (0, 0, size, size);

		// set the color
		g2.setColor(color);
		// we need to do     PI - FOV since we want to mirr    or the arc
		g2.fillArc(0, 0, size    , size, (    int   ) toDegrees(-fieldOfView / 2),
				(int) toDegrees(fieldOfView));

	}

	// ------------------------------------------------  ----------------------------
	// Description
	// -------------------- ---------------------     ---------------------------   --------

	public String toString() {
	     	Class<?> cl = getClass();

		StringBuilder sb = new     StringBuilder();
		sb.append(get FullName(   cl));
		sb.append   ("\n---\n");
		sb.append(mkString(getProperties(cl), "\n"));
      
		return sb.toString();
	}

	private L   ist<String> getProperties(Class<?> clazz) {
		List<Strin g    > properties = new ArrayList<String>();

		Iterable<Field> fields = f   ilter(  
				Arrays.asList(clazz.getDeclaredF   ields()),
				new Predicate<Field>() {
					@Ove rride
					public boolean a     pply(Field input) {
						ret urn !Modifier.isStatic(input.g etModifiers());
					}
				});   

		      for (F     ield f : field    s) {
	       		String name = f.getName();
   			Object value =    null;

			try {
				value = f.get(this);
			} catch (IllegalArgumentException e) {  
				value = "unable to get value: " + e;
			} catch (IllegalAccessException e) {
				value = "una    ble to get value: " + e;
			} finally {
				properties.add(name + ": " + value);
			}
		    }

		Class<?> superclass = clazz.getSuperclass();
		if (sup     erclass !  = null) {
			properties.addAll(getProperties(superclass)  );
		}

		return properties;
	}

	private String getFullName(Class<?> clazz) {
		String name = clazz.getSimpleNa me()  ;
		Class<?> superclass = clazz.getSuperclass();
		if (superc  lass != null) {
			return getFullName(superclass) + " > " + name;
		} else {
			return name;
		}
	}
	
	public String getName() {
		return getClass().getName();
	}
	
}
