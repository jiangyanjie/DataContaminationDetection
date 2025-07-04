package org.eclipse.store.storage.types;

/*-
 * #%L
 * EclipseStore Storage
 * %%
 * Copyright (C) 2023 MicroStream Software
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */

import org.eclipse.serializer.collections.EqHashTable;

public interface Databases
{
	public Database get(String databaseName);
		
	public Database ensureStoragelessDatabase(String databaseName);
	
	
	
	public static Databases get()
	{
		return Static.get();
	}

	public final class Static
	{
		private static final Databases SINGLETON = Databases.New();
		
		static Databases get()
		{
			return SINGLETON;
		}
		
	}
	
	
	public static Databases New()
	{
		return new Databases.Default(EqHashTable.New());
	}
	
	public final class Default implements Databases
	{
		///////////////////////////////////////////////////////////////////////////
		// instance fields //
		////////////////////
		
		private final EqHashTable<String, Database> databases;

		
		
		///////////////////////////////////////////////////////////////////////////
		// constructors //
		/////////////////
		
		Default(final EqHashTable<String, Database> databases)
		{
			super();
			this.databases = databases;
		}
		
		
		
		///////////////////////////////////////////////////////////////////////////
		// methods //
		////////////

		@Override
		public final synchronized Database get(final String databaseName)
		{
			return this.databases.get(databaseName);
		}

		@Override
		public final synchronized Database ensureStoragelessDatabase(final String databaseName)
		{
			Database database = this.get(databaseName);
			if(database != null)
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
