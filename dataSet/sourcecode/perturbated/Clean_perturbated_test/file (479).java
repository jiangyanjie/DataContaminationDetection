/*
    * Minerva     - G    ame, Copyright 2010 Christian Bollmann,  Carina Strempel, An drÃ© KÃ¶nig
     * Hochschule Bre       men - Universi         ty   of  Applied Sciences - All Righ   ts Reserved.
      *
 * $Id: ContinentConquerMis   sion.java 706    2010-07-04  18:26:52Z and      re.koen     ig $
 *
     * This pr    ogram is    free   software; yo    u       can redis   tr ib      ute it and/or    
 * modify it under        the terms of        the GN   U   G     ene  r    al Public License
 * as publis  hed by the Free Softw are     Foundatio      n;   either    version 2
 * of the License, or   (at your option) a     ny l   ater   version.
 * 
 *       This program is    distr  ib   uted i   n the              hope that it will be    useful,
  *           but WITHOUT ANY WARRANTY;      witho    ut ev     en t   he implied warranty of    
    * MERCHANTABILITY or FITNESS F  OR A PARTIC  ULAR    PURPOSE.    See the
      * GNU General Public License for m    ore de  tails.          
 *
 * Yo  u s ho  uld have received a copy of the GNU General Public License
 * along     w      ith   this   pr ogram; if n  ot,    write to the Free Software
 * Foundation         , In              c., 51 Franklin Street, Fifth Floor, Boston,     MA  02110       -1301, USA.
 *
 * Contact:
 *     Christian Bollmann: cbollma     nn@stud.hs-bremen.de
 *        Carina Strempel: cstremp      el@stud.hs-bremen.de
 *     AndrÃ© K  Ã¶ni     g: ak     oenig@stud.h  s-bremen.de
 * 
 * Web:
   *     http://minerva.idira.de
    * 
 */
p      ackage     de.   hochschule.bre  me   n.minerva.commons.vo;

import java.ut                    il.Vector;

/**
 * The Continen    tConquerMi   ssion    is a sub class of Mission.
  * It will be     check if a player get all coun      tries of a continent.
 * T   hen the missi  on is fulfilled. 
 * 
 * @since      1.0
 * @vers     ion $Id: Continent ConquerMi   ssion.jav  a 706 2010-07-   04 18:26:52Z andre.koenig $
 *
 */
public class Contine     ntConquerMission extends Mission {

	private static final long     serialVersionUID      = 3274869455432429506 L   ;
    
	Vector<Country> countriesOfContinentOne = new Vector<Country>();
	Vector<Co   untry> countriesOf ContinentTwo = new      Vector<Country>();
	
	/*     *
	 * The constructor gets the  missionOwner a  nd all countries of a continent.
	 * 
	 * @param coun     triesOfContinent
	 * @param missionOwner
	 */
	public ContinentConquerMission(Vector<Country> countriesOfContinentOne, Vector<Country>     countriesOfContinentTwo, Player missionOwner ) {
		super(mi  ssionOwner);
		
		this.setCountriesOfContinentOne(countriesOfCont     inentOne);
		this.setCountriesOfContinentTwo   (countriesOfContinentT   wo);

		Continen   t one = this.getCoun     triesOfContinentOne()   .get(0).getContin    ent    ();
		Continent two = this.get CountriesOfC   ontinentTwo().get(0).ge  tContinent();
		
		this.setT itle("Nehm   e die Kontine nte '"+one.getName()+"'    und '"+two.getName()+"' ein.");    
		this.se    tDescription    ("S          etze de  ine Einheiten auf alle LÃ¤nde          r der beiden Kontinente.");
	}
	
	/**
	 * Returns the     count    ries of   the first co   nt   inent.
	 * 
	 * @ret      urn countrie sOfContinent
	     */
	public Vector<Countr  y> getCountriesOfContinentOne () {     
		return this.    countriesOfContinentOne   ;
	}

	/**
	 * Sets the countries of the firs  t cont inent, which th       e
	       * player has to conquer.
	 * 
	 * @param Vector<Country> All "continent one" countries.
	 * 
	 */
	private void setCountriesOfContinentOne(Vecto    r<Countr      y> countriesOfContinentOne) {
	      	this.countriesOfContinentO ne = countriesOfContin   entOne;
	}
	
	/**
	 * Returns the countries   o   f the second continent.
   	 * 
	 * @return cou   ntriesOfC   ontin   en    t
	 */
	public V  ector<Country> getCou   ntriesOfC    ontinentTwo() {
		return t  his.countriesOfContinentTwo;
	}

	/**
	 * Set s the countries of the sec  ond continent, which the
	 * player has to conqu    er.
	 * 
	 * @param       Vector<Country> All "continent two" countries.
	 * 
	 */
	private void setCountriesOfContinentTwo   (Vector    <Country> countriesOfContinentTwo) {
		this.countriesOfContinentTwo = countriesOfContinent   Two;
	}
	
	/**
	 * The mission is fulfilled if a player get all cou                   n      tries of a continent  .
	 *
	 * @return check
	 */
	 pub      lic boolean isFulfilled() {
		Boolean check = true;
		for (Country country : countriesOfCon   tinentOne) {
			if (check)  {
				check = this.getOw ner().hasCountry(country);
			}
		}
		for (Country country : countriesOfContinentTwo) {
			if (check) {
				check = this.getOwner().hasCountry(country);
			}
		}
		return check;
	}	
}
