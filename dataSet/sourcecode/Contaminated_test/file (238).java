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

import java.sql.Connection;
import java.sql.DriverManager;

import org.alexandria.test.helpers.ObjectFactory;
import org.alexandria.test.helpers.TestConstants;
import org.junit.AfterClass;

/**
 * @author Andrew Wolfe
 * @category Test
 * @since Sat Oct 12 2013
 * @version 1.0.0
 * 
 * A test abstract class that will make it easy to import for an in-memory database management (for DAOS)
 */
public class DaoTestAbstract {
	protected static Connection connection;
	protected static ObjectFactory modelFactory;
	
	protected static void setupDatabaseConnection() throws Exception{
        connection = DriverManager.getConnection(TestConstants.CONNECTION_STRING, TestConstants.TEST_CONNECTION_USERNAME,TestConstants.TEST_CONNECTION_PASSWORD);     
	}
	/*
	 * Tears down the in memory database
	 */
	@AfterClass
	public static void tearDown() throws Exception {
		connection.createStatement().execute(TestConstants.SHUTDOWN);
	}
}
