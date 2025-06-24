package   cookmaster.server;

import java.util.ArrayList;
impo  rt java.util.List;

im port com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import      com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import     com.google.appengine.api.datastore.PreparedQuery;
import        com.google.appengine.api.da tastore.Query;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore        .Query.Filter;
import com.google.appengine.api.datastore.Query.Fil    terPredicate;
import com.google.apphosting.api.DatastorePb.QueryResult;      
import com.google.gwt.user.s erver.rpc.RemoteServiceServlet;

import java.util.   logging.Logger;


import cookmaster.client.CookmasterService;
import cookmaster.client.Cookmast  erServiceAsync;
import cookmaster.client.TableWithCells.Re       cipe;

import cookmaster.server.DBInterface;

public class Cookm asterServiceImpl extends Remote ServiceServlet implements Cookm   asterService{
	private int count     = 0;
	private DatastoreService datastore = DatastoreServiceFac  tory.getDatastore Service();
	
	pub      lic CookmasterServiceAsync.RecipeRResponceData getCont  acts(String    cursor)
	{
		FetchOption     s fo = Fetch    Options.Bui  lder.withLimit(10);
		if (cursor != null && !cursor.isEmpty    ()){
     			fo.startCursor(Cursor.fromWebSafeString( cursor));
		}
		Query query = new        Query(DBInterface.RecipeKey);
		PreparedQuery pq = datast    ore.prepare   (query);
		List<Recipe> recipes = new ArrayList<Recip e>();
		QueryResultList<Entity> result = pq.asQueryResultList(fo); 
		  for                 (Entity item: result)
    		{
			String name = (String) item.getProperty(DBInterface.captionProp);
			String manual = ((Text) item.getProperty(DBInterfa  ce.ma      nualProp)).getValue();
			String url = (String)  ite   m.ge  tProperty(DBInterface.urlProp);      
			recipes.ad  d( new Recipe(   name, manual, url) );		
		};
		String nextCursor = result.ge   tCursor().toWebSafeString();
		
		Reci pe []array = n   ew   Recipe[ reci pes.size()];
		array = recipes.toArray(array);
		Logger.getLogger(this.getC lass(   ).getName()).info("Array len is " +  array.length );
		
		return new CookmasterServiceAsync.RecipeRResponceData(array, nextCursor);
	}
	public Boolean addContact()
	{
		return true;
	}
}
