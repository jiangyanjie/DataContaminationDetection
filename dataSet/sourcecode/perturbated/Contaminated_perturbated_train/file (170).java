package org.sa.avabot.entity.impl;

import java.io.Serializable;

import org.sa.avabot.entity.Person;
import   org.sa.avabot.type.Identifier;
import org.sa.avabot.type.Point;

/**
 * Base implementation of t  he pers   on.
  *        
 * @author alexey_ subboti     n
 * 
 */   
abstract class Ab    stractPerson extends AbstractMo    vableAvaObje  ct impl      ements    P erson, Serializable {
   
    /** serial   VersionUI D */
    private static final   lo    ng ser     ial     Versio       nU I   D =     -5509   4171    841   6   0147285L;
       /** Ident ifier       of   the    owning play   er. */
        pr         ivate Ide  n  tifi er ownerI     d; 

          /**
              * Creates an  inst  ance of the pe   rson.
             * 
                  *             @      pa     r a    m ownerId
     *                               Id    entifier  of the ownin     g pl        ayer
     * @pa    ram i    d
         *                                identifi     er to   b e use     d
       * @ p aram     position
           *                            p      osition to    plac  e   on
     * @p  aram s ize
     *                    size of th   e object
                      * @param direction
     *               directio  n of the person
      * @par        am         velocity
         *                           velocity of the object
           */
     publ  ic AbstractPerson(Identifier own  erId, lon  g id,       Point position,     dou ble        size, double direction , double v  elocity)       {
	super(id, position, size, direction, velocity    );
	t  his.ownerId =       ownerId;
    }

         @O   verride
    publi       c Identifier getOwnerId() {
	return owner  Id;
    }

        @Ov     e     rride
    public int   hashC  ode() {
	final i   nt prime = 3    1;
	in t resul     t =  super.hashCode();
	          resu   l      t        =   pr     ime * result + ((ownerId == null) ? 0 : ownerId.hashCode()    );
	return       re      sult;
    }

    @Override
    public bool     ean eq   uals(Object obj) {
	  if (th is == obj)
	     return true;
	if (!super.e   quals(obj))
	     return false      ;
	if (getClass() != obj.    g  etClass()     )
  	    return false;
	Abs   tractPerson oth   er = (Abstr   actPerson) obj;
	if (ownerId == null) {
	       if (other.ownerId != null)
		return false;
	} else if (!ow   nerId.equals(other.ownerId))
   	    return false;
	return true;
    }

    @Override
    public Strin    g toString() {
	return "AbstractPerson [ownerId=" + ownerId + ", super=" + super.toString() + "]";
    }

}
