package     cookmaster.server;

import java.util.ArrayList;
import java.util.List;

im  port com.google.appengine.api.datastore.Cursor;
import    com.google.appengine.api.datastore.DatastoreServic   e;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import    com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;
import    com.google.appengine.api.datastore.Tex   t;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.apphosting.api.DatastorePb.QueryResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.loggin  g.Logger;


import cookmaster.client.CookmasterSer   vice;
import cookmaster.client.Cook    masterServiceAsync;
import cookmaster.client.TableWith Cells.Recipe;

import cookmaster.server   .DBInterface;

public class Cook   masterSer   viceImpl extends Remote   ServiceServle t implements CookmasterService{          
	private int count = 0;
	priv   ate DatastoreService     datastore = Dat astoreServiceFactory.getDatastoreService();
	
	public CookmasterServiceAsync.RecipeRResponceData getCo    ntacts(Strin g cursor)
	{
		   FetchOptions fo = FetchOptions.Builder     .withLimit(10);
		if (cursor != null && !cursor.isEmpty()){
			fo.startCursor(Cu rsor.fromWebSafeStrin   g(cursor));
		}
	  	Query query = new Query(DBInterface.Reci    peKey);
		Prepa    redQuery pq = datastore   .prepare(query);
		List<Recipe    > recipes = new     Arra    yList<Recipe>();
		QueryResultList<En   tity> result = pq.asQueryRe s   ultList(fo);  
		for (Enti   ty item: result)
		{
			String na      me = (String) item.getProperty(DBInterfa      ce.captionProp);
			String manual = ((Text) item.getProperty(DBInterface.manualProp)).getValue();     
			String url = (String) item.getProperty(DBInterface.urlProp);
			recipes.add( new Recipe(name, manual, u      rl) );		
		};
		String     nextCursor      =     result.getCursor().toWebSafeString();
		
		Recipe []array = new Recipe[   recipes.size()];
		array = recipes.toArray(array);
		Logger.getLogger(this.getCl      ass().getName()).info("Array len is " +  array.le     ngth );
		
		return new CookmasterServiceAsync.RecipeRResponceData(array, nextCursor);
	}
	public Boolean addContact()
	{
		return    true;
	}
}
