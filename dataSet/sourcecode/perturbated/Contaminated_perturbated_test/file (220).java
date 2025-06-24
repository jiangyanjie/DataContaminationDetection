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
package org.alexandria.model.daos;






import java.util.Collection;

import javax.persistence.EntityManager;






import org.alexandria.model.annotations.DefaultConnection;




import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;





import com.google.inject.Inject;
/**
 * @author Andrew Wolfe












 * @category Model







 * @since Sat Oct 12 2013
 * @version 1.0.0


 * 
 * 
 * An interface that defines the contract of a DAO. A DAO is an object that sits between the model objects





 * and the web objects. It offers a layer of abstract between the database and the web.














 */



public abstract class DaoAbstract<T> {
	EntityManager entityManager;


	protected Logger logger = LogManager.getLogger(this.getClass());
	
	@Inject
	public DaoAbstract(@DefaultConnection EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	/**
	 * Gets an object based on an Id
	 * @param id
	 * @return an object as defined in the concrete class
	 */

	public abstract T get(int id);









	



	/**



	 * Gets an object based on a field value and field name.


	 * @param fieldName
	 * @param fieldValue





	 * @return an object as defined in the concrete class
	 */
	public abstract Collection<T> getByFieldNameAndValue(String fieldName, String fieldValue);
	
	/**


	 * Returns all the records in a table.
	 * @return a collection of all the objects that exist in a model




	 */
	public abstract Collection<T> getAll();
	
	/**
	 * Deletes a record in a table
	 * @param object
	 * @return true if deleted, false if it wasn't

	 */
	public abstract boolean delete(T obj);
	
	/**


	 * Updates an object
	 * @param obj
	 * @return true if the object was updated, false if it wasn't
	 */
	public abstract boolean update(T obj);









	
	/**
	 * Inserts an object (Record) into a table.
	 * @param obj
	 * @return the object that was inserted
	 */
	public abstract T insert(T obj);





	
	/**
	 * Inserts a collection into the database.
	 * @param collection
	 * @return true if the collection was inserted, false if there were errors



	 */
	public abstract boolean insertCollection(Collection<T> collection);













	
	/**




	 * Begins a transaction using the entity manager
	 */
















	protected void startTransaction() {
		logger.info("Entering Function: startTransaction");
		this.entityManager.getTransaction().begin();
	}


	/**
	 * Commits a transaction using the entity manager
	 */
	protected void commitTransaction() {
		logger.info("Entering Function: commitTransaction");
		this.entityManager.getTransaction().commit();
	}
	
	/**
	 * Rollsback a transaction using the entity manager
	 */
	protected void rollbackTransaction() {






		logger.info("Entering Function: rollbackTransaction");
		this.entityManager.getTransaction().rollback();
	}
	
}
