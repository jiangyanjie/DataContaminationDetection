package cookmaster.server;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.apphosting.api.DatastorePb.QueryResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.logging.Logger;


import cookmaster.client.CookmasterService;
import cookmaster.client.CookmasterServiceAsync;
import cookmaster.client.TableWithCells.Recipe;

import cookmaster.server.DBInterface;

public class CookmasterServiceImpl extends RemoteServiceServlet implements CookmasterService{
	private int count = 0;
	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	public CookmasterServiceAsync.RecipeRResponceData getContacts(String cursor)
	{
		FetchOptions fo = FetchOptions.Builder.withLimit(10);
		if (cursor != null && !cursor.isEmpty()){
			fo.startCursor(Cursor.fromWebSafeString(cursor));
		}
		Query query = new Query(DBInterface.RecipeKey);
		PreparedQuery pq = datastore.prepare(query);
		List<Recipe> recipes = new ArrayList<Recipe>();
		QueryResultList<Entity> result = pq.asQueryResultList(fo); 
		for (Entity item: result)
		{
			String name = (String) item.getProperty(DBInterface.captionProp);
			String manual = ((Text) item.getProperty(DBInterface.manualProp)).getValue();
			String url = (String) item.getProperty(DBInterface.urlProp);
			recipes.add( new Recipe(name, manual, url) );		
		};
		String nextCursor = result.getCursor().toWebSafeString();
		
		Recipe []array = new Recipe[ recipes.size()];
		array = recipes.toArray(array);
		Logger.getLogger(this.getClass().getName()).info("Array len is " +  array.length );
		
		return new CookmasterServiceAsync.RecipeRResponceData(array, nextCursor);
	}
	public Boolean addContact()
	{
		return true;
	}
}
