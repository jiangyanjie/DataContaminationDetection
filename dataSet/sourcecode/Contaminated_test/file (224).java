/******************************************************************************************************************
 The MIT License (MIT)

Copyright (c) 2013 Andrew Wolfe

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 ******************************************************************************************************************/
package org.alexandria.model.helpers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.alexandria.model.daos.DaoAbstract;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;

/**
 * @author Andrew Wolfe
 * @category Model
 * @since Sat Oct 12 2013
 * @version 1.0.0
 * 
 * A helper class that helps manage entity managers
 */
public class DaoHelper {
	private static Logger logger = LogManager.getLogger(DaoHelper.class);
	/**
	 * Creates a entity manager to be used for all the DAOs
	 * @return an entity manager that was created for the alexandria database
	 */
	@Provides
	public static EntityManager provideEntityManager() {
		logger.info("Entering Function : setupEntityManager");
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( ModelConstants.PERSISTANCE_XML_ENTITY_MANAGER );
		logger.info("Entity Manager Factory has been setup, returning the entity manager from factory");
		return entityManagerFactory.createEntityManager();
	}

	/**
	 * Uses Google Guice to construct a DAO with the proper Module and class
	 * @param clazz
	 * @return a DAO of the type requested
	 */
	public static DaoAbstract<?> setupDAO(Class<?> clazz, AbstractModule module) {
		Injector injector = Guice.createInjector(module);
		return (DaoAbstract<?>) injector.getInstance(clazz);
		
	}
}
