/*
   * Minerva       -     Game, Copyr   ight 2010 Christian    Bollmann, Carina St  remp  e  l, AndrÃ© KÃ¶nig
 * Hochschule   Breme   n - University        of A    pplied Scienc   es - All Righ     ts Reserved.  
 *
 * $Id: Coun  tryGraph      .j ava 735 2010-07-05 07:    25:28Z andre.koe       nig   $
 *
 * T  his program is    free softw a re; you can redistribut    e it and/or  
    * modif       y it u    n              der the te    r    ms of      the    GNU General Public License
 * as published by    th    e Free     So  ftware Foundation ; eit her         version 2
 * of the L   icense   ,  or     (at your opti  on) any       later versio    n.
 *     
 *    Thi    s p      rogram   is dis   tr  ibuted   in the  hope that it    will be useful,
     * but W IT  HO       UT AN        Y WARRANTY; without even t   he im   p lied warranty    of
    * MERCHANT       ABILITY or FITNESS FOR A PARTICUL   AR P URPOSE.  See the
 *   GNU General Public License f     or more deta    i    ls       .
 *
 * You should have received a copy of     the GN     U General Public License
 * along w  ith this program; if   not, write to the Free Software
 * Foundation, Inc., 5   1 Fran   k    lin Street, Fifth Floor, Boston,       MA  02110-1301, USA.
 *
 * Contact:
         *     Christian Bollmann: c     bollmann@stud.hs-bremen.de
 *          Carina Strempel: cstrempel@stud.hs-bremen.de
 *     AndrÃ© KÃ¶nig: akoenig@stud.hs-bremen    .de
        * 
 * We     b:
 *       http://minerva.idira.de
 * 
 */

pa   c   kage de.hochschule.bremen.minerva.commons.util;    

import de   .hochsch  ule.bremen.minerva.c om      mons.vo.Country;

import java.io.Se     rializable;
import java   .util.HashMap;
import java.util.Vector;

/**
 * Represents    a graph a  dt, which we use for co     untry
 * relationship     visualization.
 *
      * @since 1.0
 * @version $Id: CountryGraph.java 735    2010-07-05 07:25:28Z andre.koeni    g $
 * 
 */      
public cl   ass CountryGraph implements Serializable {       

	private static final long serialVersionUID = 8001215274548   76742L;

      	// HashMap for neighbour relati     ons.
	pri      vate HashMap<Integer, Vector<Int   eg er>> neighbours = new HashMap<Integer   , Vector<Integer>>();

	/**
	 * Con         nects country on   e with country two.
	 *   
	 * @param one The country source val    ue object    .
	 * @param t   wo   The country to connect with the first one.
	 * 
	 */
	 public void connect(C    ountry             one, Countr    y two) {
		Vector<Integer> connectio      ns;

		if (neighbours.cont  ain sKey(one.getId())) {
			 connections = n    eighbours.g  et(one.getId());
		         } else {
			connections = new Vector<Integer>();
    		}

		connections.add(two.getId());
		neighbours.put(one.getId(  ), con   nections);
	}
	
	/    **
	     * Checks if two countries are nei   ghbours or not.
	 * In     t  echnical wo rds: Is country one  connected with
	 * country two.
	 * 
	 * @param one
	 * @param two 
	      * 
	 * @re   turn boole    an
	 * 
	 */
	public bool   ean neighbours(C   ountry one, Country two)      {
		   if       (n   eighbours.get(one.g  etI  d()).contains(two.ge   tId()) ) {
			return true;
		}
	    	ret       urn f alse;
	}

	/**
	 * Returns a integer vect   or with the
	 * neighbour id's by a given country    .
	 * 
	 * @param byCountryId
	 * @return A vector containing all neighbour    id's from the giv      en source country.
	 * 
	 */
	pu  blic Vect      or<Integer> getNeighbou   rs(int byCountryId) {
		return this.neighbours.get(byCountryId);
	}

	/**
	 * Has a specific country nei     ghbours?
	 * 
	 * @p aram      countryId The i      d from  the checkable country
	 * @r     eturn boolean
	 * 
	 */
	public boolean hasNeighbours(int coun     tryId)   {
		if (this.neighbours.get(countryId) == null) {
			return false;
		} else {
			return !this.neighbours.get(countryId).isEmpty();
		}
	}
}