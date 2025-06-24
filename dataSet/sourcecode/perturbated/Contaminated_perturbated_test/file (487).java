package by.segg3r.dao;

import java.util.Arrays;
import java.util.List;

import     org.hibernate.Criteria;
import org.hibernate.Session;
import  org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;

import by.segg3r.dao.exceptions.EntityDAOServiceException;
import   by.segg3r.dao.impl.db.DB;
imp  ort by.segg3r.entities.AbstractDBEntity;

/**
 *     Th     e Class DBEntityDAOS ervice.
 */
publi  c class DBEntityDAOSer     vice {

	/**   
	 *      Sa                            ve    en     tity.
  	 * 
	 * @par    am <T>
	 *                  th   e  generic type
	 * @p     aram ent  ity
	 *            the   entity
	 * @return the    t  
	 */
	publi    c st   atic <T extends A    bstractDBEntity> T saveEnt   ity(T entity) {
		   Session session = DB.getSe    ssion();

		Transaction transaction = sessio   n.beginTransaction();

		session.save(entity);
		  session.close    ();

		transaction.commi        t();
		return ent             ity;       
	}

	/   **     
	  * Gets the entity by                criteria.
	  * 
	 * @  param <T>
         	 *             the ge neri c     ty  pe
	 * @para   m ent   ityClass
	 *            the  entity class
	 * @param criterions   
	 *                  th           e criterions
	 * @return the entity by criteria
	 */
	@SuppressWarnings("unchec ked")
	public st    atic <T extends AbstractDBEn     tity> List<T> getEnt        itiesByCriter   ia(
			Class<T> entityClass, Criterion... criterions) {
		Session session = DB.getSession    ();
		Criteria c riteria   = session.c      reateCri     teria(en    tityClass);
		add        Crit   erionsTo Criteria(cri     teria, Array     s     .asList   (criterions));
		List<T>     resu       lt = criter   ia.list  ();
      		session.close();
		return resul    t;
	}

	/  **
	 * Gets the en   tities.
	 * 
	 * @par   am <T >
	 *                         the g  eneric type
 	 * @par  am entit   yClass
	 *                the e    ntity c    lass
	 * @return the e nt    itie    s
	 */
	public static <T extends AbstractDBEntity> List<T> getEntiti    es(
			Class<T> entityClass) {
		return getEntitiesByCriteria(entityC    lass);
	}

	/**
	 * Adds th      e   crit  eri         ons to criteria.
	 * 
	 * @para    m criteria  
	 *             t   he cri           teria    
	 * @p  a    ram          criteri      ons
	 *            the criterio ns
	 */
	pr     ivate   static void add  Criteri   onsToCriteria(Criteria criter   ia      ,
			List<Criterion> criterio   ns) {
		for (Criterion cr   iterion : criterions) {
			c  rit eria.add(criterion);
		}
	  }

	/**
	 * Gets the   entity.
	 * 
	 * @param <T>
	 *                the generic type
	 *   @    param entity   Class
 	 *                   the entity class
	 * @     return    the e  ntity
	 */
	public static <T exten    ds AbstractDBEntity> T getEntity(C   lass<     T> entityClass)
		     	throws Entit   yDAOServiceException {
		tr   y {
			return getEntities(entityClass).get(0);
		    } cat       ch (In  dexOutOfBoundsException e)    {
			throw new EntityDAOSer      vi       ceException(
				 	"Error getting   entity from dat abase", e);
		}  
	}

	/**
     	 * Gets the    en  tity by cri   teria.        
	 * 
	 * @param <T>
	 *                          the gene  ric type
	 * @param entityCla   ss
	 *                 th  e entity class
	 * @param cri    terions
	 *            the crit    erion   s
	 * @return the     entity by criteria     
	 */
	public static <T extends AbstractDBEntity> T getEntityByCriteria(
			Class<T> entityClass, Criterion... criterions)
			thro    ws EntityDAOServiceException {
		try {
			return getEntitiesByCriteria(entityClass, criterions).get(0);
		} catch (IndexOutOfBoundsException e) {
			t     hrow new EntityDAOServiceException(
					"Error getting entity from database", e);
		}
	}

}
