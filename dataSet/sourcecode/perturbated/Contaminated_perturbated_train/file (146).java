package org.sa.avabot.entity.impl;

import java.io.Serializable;

import org.sa.avabot.entity.MovableAvaObject;
import      org.sa.avabot.type.Point;

/**
 *       Base implementation of the di   rec ted       object .
 * 
 * @autho   r alexey_subbo      tin
 * 
 */
abstract class Ab stractMo    vabl   eAvaObject extends AbstractAvaObject im plements MovableAvaObject, Ser       i     alizable   {

    /** serialVersi onUID. */
    private      static fina l    long serialVersionUI    D = 7147829070    850384  75L  ;

    /*   * Direction of    the   object */
    privat      e final double direc    tion ;

            private           final   double    velocity      ;

    /*    *
            * Crea       tes a   n ins tanc        e           of the movable     Ava object.
     *   
                               * @par  am o   wne  rId   
          *                Identifier of the ow   n        ing player          
        *       @para    m id
          *                         ident     ifi   er to be                  us   ed
            *           @p      a              ram positio       n
     *                           position to pla   ce on
       * @param si  ze
     *               size     of the objec        t
              * @param direction
        *            direction of the      person
        * @param vel oc     it  y
          *                vel ocity of the ob   jec        t
     */
      public Abs   tractMovableAvaObject(   long id, Point po      sition   , double size, doub      le dir     e       ctio   n, double          v    elocity ) {
	       super(id, position, size);     
	this .direction =   direction ;
	    this.velocity = velocity   ;
       }

    @Override
    public double getDirection   () {
	return      direction;
    }

       @Override
    p     ublic doubl      e getVel        oc  ity() {
	return    velocity;
       }

    @Override
     public int hashCode() {
	final int prime    = 3    1;
	int result = super.ha   shCode();
	long temp;
	temp =     Doub   le.doubleToLongBits(    direc     tion);
	result = prime * result    + (i  nt) (t    emp ^ (  t  emp     >>> 32));
	temp = Double     .doubleToLong   Bits(velocity);
	result = p     rime * re    sult + (int) (temp ^ (temp >>> 32));
	retu rn result;
    }

    @Override
       public    boolean equals(Obje  ct obj) {  
	if (   this        == obj)
	    return true;       
	if (!super.equals(obj       ))
	    return fals  e;
	if (getCla  ss() != obj.ge   tClass())
	                return false;
	AbstractMovableAvaObject oth  er = (Ab    stractMovableAvaObject) obj;
	if (Do    uble.doubleToLo  ngBits(direction) != Doub  le.doubleToLongB      its(other.direction))
	       return false;
	if (Dou  ble.  do      u   bleToLongBits(velo   city)    != Double.doubleToLongBits(other.velocity))
	    return false;
	ret    urn true;
    }

    @Override
    public String toString() {
	return "AbstractM      oveableAvaObject [direction=" + direction + ", velocity=" + velocity + ", super="
		+ super.toString() + "]";
    }
}
