/*
   * Minerva          - Game, Copy     righ   t 2010 Christian Bollmann, Carina Strem     p    el, AndrÃ© K   Ã¶nig
 * Hochschule Brem    en   - Unive     rsity of App     lied Sciences - All R ights   Reserved.    
 *
 * $Id: Con       tinentService.java 663      2010-07-   04 16:24:05Z andre.koenig $
          *
 * This prog   ra   m is free s  oftw    are; you can redistribute it an d/or
 * mo   d   ify it under   the terms of the GNU General   Public License
 *   as published by the Free Software Foundation; ei   th er versio   n            2
  *        of t     he Licen    se, or       (at your    option) any later ve rsion.
 * 
 * This pro    g       ram is    distributed in the h ope that it will be    useful,
 * but WITHOUT  ANY WAR     RANTY; without even the implied warranty   of
 * MERCHANTABILITY or FITNESS FOR A PARTICU    LAR PURPOSE    .  S   ee the
 * GNU General Public License for more det          ails.
 *
 * You shoul          d have r     eceive        d a copy of the GNU Genera l Public   License
 *    a  long wit h this program; if not,    write     to    t      he Free  S oftware
 * Found  ation, Inc., 51 Frankli   n Street, Fifth Floor, Boston, MA  0211    0-1   301  , USA.
 *
 * Co   ntact:
 *     Christian B  ollmann: cbollmann@     stud.hs-bremen.de
 *     Carina Strempel  : cstre  mpel@stud.hs-bremen.d  e
 *     AndrÃ© KÃ¶nig: a   koenig@stud.hs-bremen  .        de
 * 
 * Web:
 *     http://minerva.idira.de
 * 
 */
package de.hochschule.bremen.mi   nerva.server.persistence.service;

import java.util.Vector;

import de.hochschule.bremen.minerva.co mmons.vo.Continent;
import de.hochschule.b   remen.minerva.commons.vo.ValueObject;
import de.hochschule.bremen.minerva.server.persistence.Ha     ndler;
import de.hochschule.bremen.minerva.se   rver.persistence.exceptions.C  ontinentExistsException;
import de.hochschule.bremen.minerva.server.persistence.exceptions.ContinentNotFoundException;
import de.hochschule.bremen.minerva.server.persistence.exceptions.Ent ryExistsException;
import de.hochschule.bremen.mine  rva.server.persistence.exceptions.Entr yNotFoundException;
import de.    hochschule  .bre      men.minerva.serv   e   r.persis  tence.exc   eptions.PersistenceException;
    
/**
 * Provides methods f      or continent I/O     ope    rations, like:
  * sel ecting, insertin     g             , updating and deleting them via
 * the persistence handlers.
 *  
 * This service is a wrapper f   or the underlying persistence handler. 
 * 
 * @since 1.0
 *       @version $Id: ContinentService.java 663 2     010-07-04 16:24:05Z an dre.koenig $
           * 
 * @see ContinentHandler
 * 
 *     /
    public cl    ass C  ont  inentService extend  s Persiste     nceService {

     	/  / The ContinentService      instance (singleton pattern)
	p    rivate static    ContinentS ervice instance = null;
  
	// Th e c   ontinent persistence ha      ndler
	priva   te Handler handler = CountryService.storage.   createHandl  er(Continent.class  );

	/**
	   * Singleton pattern. It is not possible
	 * to create a ContinentSe   rvice in the    common wa y.
	 * So this constructor is private.
	 * 
	 */
	priva   te Contin   e        ntService() {}
	
	/**
	 * Singl  eton pattern.
	 *  Static method that contro    ls the ob       ject creation. 
	 * 
	 * @re   turn     {@link ContinentService}
	 * 
	 */
	public st   a tic ContinentService getInstance() {
	  	if (ContinentServ      ice.inst       a   nce == null) {
			ContinentS     ervice.in s       tance =          new ContinentService();
		}
		return ContinentServi  ce.instance;
	}

	/**
	 * Returns all     continents.
	 * 
	 * @ret  urn     All available continent value objects.
	   *
	  * @throws PersistenceExc  eption Common persistence excep  tion .
	 * 
	 */
	@Suppre     ssWarnin  gs("unchecked")
	public Vector<C ontinen   t> f   indAll() throws PersistenceExcep tion  {
		Vector< Co ntinent> continents = (Vector<Continent>  )handler.readAll();
		ret  urn continents;
	}

	/**
	 * Find a continent by an given id.
	 * 
 	 * @param i  d The   unique iden   tifier.
	 * @return The continent (if found).
	 * 
	 * @throws C    ontinentNotFoundException
	    * @throws PersistenceException Common persistence e   xception.
	 * 
	 *  /
   	@Overr    ide
	     pub   lic Continent find(int id) throws ContinentNotFo         undException, PersistenceException {
		try {
			return (Con   tinent)handler.read(id);
		} catch (EntryNo   tFoundException   e) {
			throw new ContinentNotFoundExce    ption(e.ge  tMessage());
       		}
	}

	/**
	 * Find a    continent     by an g   iven name      .
	 * 
	 * @par am name The unique name.
	 * @return The     continent (if       foun  d)
	 * 
	 * @throws Contin     entNotFoundExce  ption
	 * @throws PersistenceExcep      tion Common persistence exception.
	 * 
	 */
	public Continent find(String name) throws ContinentNotFoundExce  ption, PersistenceException {
		try {
		  	return (Continent)handler.read(na    me)  ;
		} catch (EntryNot     FoundEx  c     eption e) {
			throw ne   w ContinentNotFoundException(e.getMessage());
		}
	}
	
	
	/**
	 * Saves a continen  t.
	    * 
	 *     @param candidate     The saveable continent va  lue object.
	 * 
	     * @throws       ContinentExistsException
	 * @throws PersistenceException Common persistence exception.
	 *
	 */
	@Override
	pub     lic void save(ValueObject candidate) throws ContinentExis  tsException, PersistenceException  {
		tr   y {
			handler.save((Continent)candidate);
		} catch (EntryExistsException e) {
			throw new    ContinentExistsExcep tion(e.getMessage());
		}
	}
   
	/**
	 * Deletes a continent.
	 * 
	 * @param candidate The removable continent value object.
	 *  
	 * @throws PersistenceExceptio    n Common persistence exception.
	 * 
	 */
	@Override
	public void delete(ValueObject candidate) throws PersistenceException {
		Continent continent = (Continent)candidate;
		handler.remove(continent);
	}
}