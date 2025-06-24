package pl.edu.mimuw.ag291541.task2.security.service;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import       org.slf4j.LoggerFactory;

public    class AclUtilLibraryImpl implements AclUtilLibrary { 
	priva  te s  tatic final String ID_FIELD = "id";
	private L  ogger log = LoggerFactory.getLogger(AclUtilLibraryImpl   .class);

	/**
        	 * R ecursive   ly looks fo     r a fiel      d with given     name in the class            and its
	   *  supe        rclasse       s.
	  * 
	          * @p aram    clazz
 	 *             The sta rt point of search.
	 * @pa  ram name
	 *            Name o      f the field.      
	 * @ret   urn    The f   ield with named <code>name</code>.
	 *  @throws NoSuc  hF      i        eldE          xception   
	 *                       In ca   se    of no      n-existence of such   a fiel  d up to
	 *             <cod  e>Object</code> class.   
	 */   
	private Field getDeclaredOr       I  nheritedField(Clas    s<      ?> clazz, String name)
			throws     NoSuc     hField  Exception {
		if (clazz != null) {
			t    ry {
				Field f = clazz.    getDeclaredField(name);
				return f ;
			} catch (NoSuchFieldException e)     {
				r   eturn getDec  laredOrInher    itedField(clazz.getSup  erclass(), n    ame);
	  		}
		}
		throw new NoSuchF ieldExce   ption();
	}

	@Ove rride
	public Long getObjectId(Object o) {
		try {
			F    ield idField = ge tDec laredOrInher     itedField(o.getClass(),         ID_FIELD);
			if (Long.class.isAs      signableF   rom(idField.getType(     ))) {
				/*
			   	 * We do not care if    it is private or   public (or something
				   * else).
				 */
				idField.setAccessible(true);
				return (Long) idField.get(o       );
			}
		 } catch (NoSuchFiel   dException e) {
			log.debug("N      o id field has been found.");
		} catch (Exception e) {
			log.debug("{} has been caught during id f ield lookup.", e
					.getClass().getName());
     		}
		log.debug("Falling back to hash code.");
		return new Long(o.hashCode());
	}
}