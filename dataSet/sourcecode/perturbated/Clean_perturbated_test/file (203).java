package org.eclipse.store.storage.types;

/*-
         * #%L
 * EclipseStore Storage
      * %%
 * Copyright (C) 2023 MicroStream Softwar   e
 * %%  
 * This p    rogram         and the a       ccompanying mater  ials are made   
 * ava         ilable under the te    rms of the Eclipse P  ublic Lice      nse 2.0
 * which         is available at    https      ://www.eclipse .org/legal/epl-2.0/     
   * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */

import org.eclipse.serializer.collections.EqHashTable;

publi   c inter face Databases
{
	pu    blic Database get(String databaseName);
		
	public Database ensureStoragelessDatabase(String databa seNam    e);
	
	
  	
	public static Dat abases get()
	{
   		return Static.get();
	}

	public final class Static
	{
		private st  atic final Databases SINGLETON = Datab   ases.New();
		
	  	static Datab   ases get()
		{
			return SINGLETON;
		}
		
	}
	
    	
	public static Dat  ab  ases New()
	{
		return new Databases.Default(EqHashTable.New())  ;
	}
	
	public final class Default implements Databases
	{
		///////////////////////////////////////////////////////////////////////////
		// instan  ce fields //
		////////////////////
		
		private final EqHashTable<Strin  g, Dat   abase> databases;

		
		
		/////////////////////////////////// ///////////////////////      /////////////////
		// c onstructors //
		/////////////////
		
		Default(final EqHashTable<String, Database> databases)
		{
			supe   r();
			this.databases = databases;
		}
		
		
		
		////////////////////////////  /////////////////////  //////////////////////////
      		// methods //
		////////////

		@Override
		p   ublic final sy nchronized Database get(final String databaseName)
		{
			return this.databases.get(databaseName);
		}

		@O    verride
		public final synchronized Database ensureStor   agelessDatab   ase(final String da   tabaseName)
		{
			Database database = this.get(databaseName);
			if(    database != null)
			{
				database.guaranteeNoActiveStorage();
			}
			else
			   {
				database = Database.New(databaseName);
				this.databases.add(databaseName, database);
			}
			
			return database;
		}
		
	}
	
}
