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
package org.alexandria.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.LinkedList;

import org.alexandria.model.daos.ContentDao;
import org.alexandria.model.helpers.DaoHelper;
import org.alexandria.model.objects.ContentModel;
import org.alexandria.test.helpers.ContentModelFactory;
import org.alexandria.test.helpers.ObjectFactory;
import org.alexandria.test.helpers.TestConstants;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Andrew Wolfe
 * @category Test
 * @since Sat Oct 12 2013
 * @version 1.0.0
 * 
 * Test class in order to run tests against the Content DAO.
 */
public class ContentDaoTest extends DaoTestAbstract{
	private static ContentDao contentDAO;
	
	/*
	 * Setups the model factory for sub objects, and the in memory database
	 */
    @BeforeClass
    public static void setUp() throws Exception {
    	contentDAO = (ContentDao) DaoHelper.setupDAO(ContentDao.class, new TestDaoModule());
        Class.forName(TestConstants.DRIVER_CLASS);
        modelFactory = new ObjectFactory(new ContentModelFactory());
        setupDatabaseConnection();
    }

    /*
     * Tests the insert method on the content dao.
     */
	@Test
	public void testInsert() {
        ContentModel contentModel = (ContentModel) modelFactory.createObject();
        contentDAO.insert(contentModel);
        ContentModel contentModel2 = contentDAO.get(contentModel.getId());
        assertEquals(contentModel2.getId(), contentModel.getId());
	}
	
    /*
     * Tests the delete method on the content dao.
     */
	@Test
	public void testDelete() {
		ContentModel contentModel = (ContentModel) modelFactory.createObject();
        contentDAO.insert(contentModel);
        contentDAO.delete(contentModel);
        ContentModel contentModel2 = contentDAO.get(contentModel.getId());
        assertNull(contentModel2);
	}
	
    /*
     * Tests the get on the content dao.
     */
	@Test
	public void testRetrival() {
		ContentModel contentModel = (ContentModel) modelFactory.createObject();
        contentDAO.insert(contentModel);
        ContentModel contentModel2 = contentDAO.get(contentModel.getId());
        assertEquals(contentModel2.getContent(), contentModel.getContent());
	}
	
    /*
     * Tests the insert collection on the content dao.
     */
	@Test
	public void testInsertList() {
		ContentModel contentModel = (ContentModel) modelFactory.createObject();   
        ContentModel contentModel2 = (ContentModel) modelFactory.createObject();            
        Collection<ContentModel> collection = new LinkedList<ContentModel>();
        collection.add(contentModel);
        collection.add(contentModel2);
        contentDAO.insertCollection(collection);
 
        //test that the second one was inserted, would mean both were
        ContentModel contentModel3 = contentDAO.get(contentModel2.getId());
        assertEquals(contentModel2.getContent(), contentModel3.getContent());
	}
	
    /*
     * Tests the getAll function on the content dao.
     */
	@Test
	public void testGetAll() {
        Collection<ContentModel> collection = new LinkedList<ContentModel>();
        collection = contentDAO.getAll();
        assertNotNull(collection);
	}
	
	
    /*
     * Tests the get by field and value function on the content dao.
     */
	@Test
	public void testGetByFieldAndValue(){
        ContentModel contentModel = (ContentModel) modelFactory.createObject();   
        contentDAO.insert(contentModel);
        Collection<ContentModel> collection = contentDAO.getByFieldNameAndValue(TestConstants.INTERNAL_ID_FIELD,TestConstants.INTERNAL_ID_VALUE);
        for (ContentModel contentModels : collection) {
        	assertEquals(contentModels.getInternalId(), contentModel.getInternalId());	
        }

	}


}
