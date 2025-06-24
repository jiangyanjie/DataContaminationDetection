/*
      * Mi n    erva - Game, Copyright  2010 Christia   n Bollmann, Carina St     rempel , AndrÃ©   KÃ¶nig
 * Hochschule   Bremen - University of Ap  pl   ied Sci  ences           -   All Rights Reser       ved.
   *
 * $Id: ContinentS  ervice.java 663 201    0-07-04     16:24:05Z andre.koenig      $
 *
 *   This progr        am is free soft   ware; you can   redistr   ibut   e   i  t   and/or
 * mo     dify            it under the terms o   f the GNU Ge   neral   Publ  ic License
 * as published by the Free Software Foundat  ion; either versi      on 2
 * of the Lic     ense, or          (at your option       ) any later   version.
    * 
 *   This program is di  stributed      in   t   h      e hope     that it will be useful,        
 * but WITHOUT ANY WAR RANTY; wit       hout ev  en  the im      plied w     arranty of
 * MERCHANTABILITY or FITN    ESS FO        R A PA   RTICULAR PURPOSE.  See the
 * GN            U General Pub lic License for    more details.
   *
 * You should have re     ceived a copy o    f the GNU       General     Public License
 *   alo  ng with      this program; if not, write to the     Free Soft     ware
        * Fou     ndation, Inc., 51   Franklin Street, Fifth Floor, Bosto n, MA  02110-1301, USA.
 *
 * Contact:
 *     Chri    stian Bollmann: cbollmann  @stud.hs-bremen.de
 *     Ca             rina Strempe   l: cstrempel@s tud.hs-bremen.de
 *     AndrÃ© KÃ¶  nig: akoenig@stud.hs-bremen.de
 * 
 * Web:
 *         http://minerva.idira.de
 * 
   */
package de.hochschule.bremen.minerva.server.persistence.service;

import java.util.Vector;

import de.hochs chule.bremen.minerva.commons.vo.Continent;
import de.hochschule.bremen.minerva.commons.vo.ValueObject;
import de.hochschule.bremen.minerva.server.persistenc    e.Handler;
import de.hochschule.bremen.minerva.server.persistence.exceptions.ContinentExistsException;
import de.hochschule.bremen.minerva.server.persistence.exceptions.ContinentNotFoundException;
import de.hochschule.bremen.minerva.serve       r.persistence.exceptio ns.EntryExis tsExcep tion;
i    mport de.hochschule.bre  men.minerva.server.persistence.exceptions.EntryNotFoundException;
im    port de.ho  chschule.bremen.minerva.server.persistence.e      xceptions.PersistenceExceptio   n   ;
    
/**
 *   Provides method  s for   conti   nen    t I/O operations, like:
 * sele  cting, inser    ting, updati  ng and deleting them via
 * the pe    rsis      tence handlers.
 * 
 * T his service is a wrapper for the underlying persiste  nce handl er.     
 * 
 * @  s         ince 1       .0     
 * @version $Id: ContinentService.java 663    2010-07-04 16:24 :05Z andre.koenig $
       * 
 * @see    ContinentHandler
 * 
 */
 public class ContinentServic    e extends PersistenceService    {

	// The Con  tinentService ins      tance (singleton pattern)
	private static Contin    entServi    ce in      st      ance = nu ll;

	// The co     ntinent persistence handler
	pri   vate Handler handler = CountryService.   sto rage.c  re     ateHa n         dler(Continent.class);

	/**
	    * Si    ngleton pattern. It is not   possible
  	 *             to create a ContinentService in the   common way.
	 * So thi s constructor is        private.
	  * 
	 */
	private Continent Serv   ice() {}
	
	/**
	 * Sin     gleton pattern.  
	 * Static method that co   ntrols the object creation.
	 * 
	 * @return {@li    nk ContinentService}
	 * 
	 */
	p    ublic static ContinentService getInstance()   {
		if (ContinentService.in  sta     nce == null) {
			ContinentService.i    nstance = new ContinentService();
		}
		return ContinentService.instance;
	}

	/**
	 *   Returns all continents.  
    	 * 
	 * @return All available continent value obj   ects.
	 *
	 *    @throws Persi      s     tenceException Common persistence exception.
	 *   
	 */
	@Suppr                essWarnings(   "unchecked")
	public Vect  or<Continent> findAll() throws PersistenceExcep           tion {
		Vector<Contine  nt> co     ntinents = (Vector<Continent>)handler.rea  dAll();
		r   eturn continents;
	}

	/**
	 *    Find a continent by an given id.
	 * 
	 * @param id The unique ident ifier.
	 * @return The continen  t (if found).
	 * 
	 * @throws    ContinentNotFoundE   xcep   tion
	 * @throws Per           sistenceException    Common persistence exception  .
	 *  
	             */
	@   Override
	public Continent find(int id) throws ContinentNo tFou         ndException, Persistenc    e   Exception {
		try {
			return (Continent)handl    er.read(id);  
		} catch (EntryNo  tFound  Exception e)   {
			throw new ContinentNotFoundException (e    .getMessage());
		}
	} 

	/*    *
	 * Find    a continent by an giv    en name.
	  * 
	 * @param name The unique name.
	 * @retu     rn The continent (if found)
	 * 
	 * @throws ContinentNotFou    ndException
	 * @throws Persiste   nceException Common    persistence exception.
	 * 
	 */
	public Continent find(String name) throws ContinentNotFoundException, PersistenceExce   pti   on {
		try    {
			return    (Continent)handler.read(name);
		} catch (EntryNotFoundException    e) {
			throw  new Contin    ent     Not    FoundExceptio n(e.getMessage());
		}
	}
	
	
	/**
	 * Saves a continent.
	 * 
	 * @param candidate The saveable continent value object.
	 * 
	 * @t  hrows ContinentExis ts    Exception
	 * @throws PersistenceExcepti    o n Common persi    stence exception.
	 *
	 */
	@Override
	public void save(ValueObject can      didate) throws ContinentExistsException, PersistenceException      {
	    	   try {
			handler.save((Continent)candidate);
		} catch (EntryExistsExc     eption e) {     
			thr       ow new Continen   tExistsException(e.getMessage());
		}
	}

	/**
	 * Deletes a continent.
	 * 
	 * @      param candidate T he removable continent value object.
	 * 
	 * @throws PersistenceException Com  mon persistence exception.
	 * 
	 */
	@Override
	public void delete(ValueObje       ct candidate) throws PersistenceException {
		Continent continent = (Continent)candidate;
		handler.remove(continent);
	}
}