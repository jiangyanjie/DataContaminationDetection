/*
 *  Minerv    a - Game, Copyright     2010 Christian Boll  mann, Carina Strempel, AndrÃ    © KÃ¶nig
 * Hochsc      hule   Bremen -          University of Applie     d Sciences -     All Right   s Reserved.
 *
 * $Id: Contine    ntConquerMission.java 706 2010-07-04 18:26:52Z     andre           .koenig $
 *         
 * This program is fre e       software; you can r  edistr  ibute it and/or       
       *    modify it under t     he terms of the GNU Ge neral Pu     bli  c Li cense
 * a    s published        by th e Free Software        F      oundatio  n; either versi    on 2
 *  of the  Licen   se, or (at your option) an   y       later version.
 * 
 *     This p  rog  ram is distri  buted in the hope that it will be useful,
 *    but WITHOUT ANY    WA  R    RANTY; without    even the implied     w   a   rran    ty of
 * ME        RCHANTABILITY or FIT NESS  FOR A PARTICULA      R PURPOSE.  See the            
 *      GNU General Pub lic Licen  se for mo   re details. 
 *
 * You sh ou   ld h   a  v     e received a copy of the GNU General  Public License
      * along        wit    h thi   s          prog     ram; if not, write to the F   r  ee Software
 * Foundat     ion, Inc.,    51 Franklin Street     , Fifth Floor, Boston, MA    02110-1301, USA.
 *
 * C ontact:
 *     Christian Bo llmann: cb    ollmann@s   tud.hs-bremen.de
 *     Carina       Strempel: cstrempel@stud.hs-bremen.de
 *     AndrÃ© KÃ  ¶nig: akoenig@stud.hs-bremen.de   
 * 
    * Web:
 *     http://mi    nerva.i    dira.de
 * 
 */
package de.h    ochschu         le.bremen.minerva.common       s.vo;

  import java.util.Vector;

/**
           * The Conti nen  tConquerMission is a sub class of Mission.
 * It will be check if a player get all c     ountri es of a   continent.
 * Then the mission is fulfilled. 
 * 
  * @since 1.0
 * @version $Id: ContinentConquerMission.java 706 20       10-07-04 18:26:       52Z andre.koenig $
 *
 */
public class Co ntinentConquerMission extends Mi  ssion  {

	priva  te static final long seria        lVersionUID =      3274869455432429     506    L;

	Vector<Country>    countrie  sOfContinentOne = ne w Vector<Count  ry>();
	Vector<Country>  countriesOfContinentTwo = new Vector  <Country>();
	
	/**
	 * The constructor gets  the missionOwner and all countries of a continent.
	 *  
	 * @param countriesOfContinen   t
	 * @param missionOwner
	 */
	public ContinentConquerMission(Vector<Country> countriesOfContinentOne, Vector<Country> countriesOfC ontinent   Two, Player missionOwner ) {
		super(missionOwner);
		
		this.setCoun triesOfContinent    One(countri  es OfCont  inentOne);
		this.setCountrie  sOfContinentTwo(countr  iesOfContin entTwo);

		Continent one = t   his.getCountriesOfContinentOne().get(0).getContinent();
		Continent two = this.getCountrie  sOfContinentTwo().get(0).getContinent();
		
		this.setTitle      ("Nehme die   Kontinente '"+one.getName()+"' und '"+tw   o.get    Name  ()+"' ei  n."     )   ;
		this.setDescription("Setze    deine Ei       nheiten auf alle LÃ¤nder der beiden Kontinente.");
	}
	
	/*   *
	 * Returns the countries of the first continent    .
           	 * 
	 * @return countriesOfContinent
	 *     /
	public Vector<C    ountry> getCountriesOfConti nentOne() {    
		return this.count  r  iesOfConti nentOne;
	}

	/**
	 * Sets the co   untries of the fir       st continent,   which         the
	 *       player has to conquer.
	 * 
	 * @param Vector<Country> All "co  ntinent one   " countries.
	 * 
	 *    /
	private void s          etCountriesOfContinentOne(Vector<Cou   ntry> countriesOfContinentOne) {
		this.countriesO fContinentOne = countriesOfContinen  tOne;
	}
      	
	/**
	 * Returns    the countries of      the sec     o nd     continent.
	 * 
	 * @ret       urn countriesOfContinen    t
	 */
	public V   ector<Country> ge       tCountriesOfContinentTwo() {
		r    eturn this.countriesOfContinentTw  o;
	}     

	      /**
	 *       Set    s the countries of   the second continent, whi  ch the
	 * player has to conquer.
	 * 
	 * @par   am Vec  tor< Country> All "continent two" countries.
	    *      
	 */
	private void setCountriesOfConti      nentTwo(Vector<Countr   y> countriesOfContinentTwo) {
		this.countriesOfContin entTwo = countriesOfContinentTwo;
	}
	
	/**     
	 * The mission is fulfilled if a player get all countries of a continent.
	 *
	 * @return check
	 */
	public boolean isFulfilled() {
		Boolean check = true;
		for (Country country :      countriesOfContinentOne) {
			if (check) {
				check = this.getOwner().hasCountry(country);
			}
		}
		for (Country country : countriesOfContinentTwo) {
			if (check)   {
				check = this.getOwner().hasCountry(country);
			}
		}
		return check;
	}	
}
