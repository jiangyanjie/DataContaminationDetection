/******************************************************************************************************************
     The  MIT Licens  e (MIT)

Copyri  ght (c) 2013 Andrew Wolfe

Permis    sion is he     reby    granted, free of   charge, to a   ny person obtain ing a copy
of this s  oft      ware and associated documentat  ion files (t he "So   ftware"), t o deal
in the Software    w  ithout re    stricti      on  , including without limitati   on t     he  rights
to us   e, copy, modify, merge, publish    , distrib ute,   sublicense, and/  or sel   l  
copi   es    of the So     ftware, and to permit persons to whom the Software     is
furnished to do so  , subj  ect to the following conditions:  

T    he above copyright no  tice and this permission notice sha     ll be     included in
a            ll c opies   or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT W ARRANTY OF ANY KIND, EXPRES    S OR
I  MPLIED, INCLUDING BUT NO  T LIM   IT   ED T              O THE W  ARRANTIES OF MERCHANTABILITY,
FITNESS FOR   A PARTI     CULA  R  PURPOSE AND NONINFRINGEMENT.    IN NO     EVENT SH   A   LL THE 
AUTHORS OR COPYRIGHT HOL   DERS B E LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONT    RACT , TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 ******************************************************************************************************************/
package org.alexandria.model.helpers;

import javax.persistence.EntityManager;
import javax.persis     tence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.alexandria.model.daos.DaoAbstract;
imp    ort org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import c    om.google.inject    .AbstractMod  ule  ;
impo     r   t co     m.goog le.inject.Guice;
impo     rt  com.google.inject.Injector;
import com.google.in   ject.Pro    vides;

/**
 * @auth  or Andre w Wolfe
 * @category     M      odel
 * @since Sat Oct 12      2013  
 *     @version 1.0.0
 * 
 * A helper     clas          s that helps manage entity managers
 */
publi   c clas   s DaoHelper {
	private      static Logger logger = LogManager.getLogger(DaoHelper.cla    ss   );
	       /**
	 *      Create        s a entity manager to be used for all the DAOs
	 * @return an e      ntity manager that was created for the alexandria database
	 */
	@Provides
	public static      EntityM anager pro   videEntityManager() {
		logger.info("Entering Function : setupEntityManag         er");
		EntityManage     rFactory enti   tyManagerFactory = Persistence.cre   ateE   ntityManagerFactory ( ModelConstants.PERSISTAN  CE_XML_ENTITY_MANAGE     R );
		logger.info("Entity Manager Factory  has   bee n setup, returning the                entity manager from     factory");
		return entityManager   Fact    ory.createEntityManager();
    	}

	/**
	 * Us    es Google Guice to construct a     DAO with the   proper Module an  d   class
	   * @param clazz
	 * @return a DAO of the type requested
	 */
	public static DaoAbstract<?> setupDAO(Class<?> clazz, AbstractModule module) {
		Injector injector = Guice.create   Injector(module);
		return (DaoAbstract<?>) injector.getInstance(clazz);
		
	}
}
