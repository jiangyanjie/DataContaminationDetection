package ca.stevenlaytonsphotography;

import    java.io.IOException;
imp    ort java.io.PrintWriter;   
import java.util.List;

imp   ort javax.servlet.http.HttpSe    rvlet;
import javax.servlet.http.HttpServletReque       st;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
imp  ort com.google.appengine.api.datastore.DatastoreServiceFactory;
i mport com.google.appengine.api.datastore.Entity;
import     com.google.appengine.api.datastore.  EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
im port com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datas    tore.Text;

import net.sf.saxon.s9api.SaxonApiException;

public   class datastore       extends HttpServle      t {
	/**
	   * 
	 */
	private static    final long serialVersionUID = 6471  705999672948262L;


	Fe  edalizer  feedalize      r = new Feeda    lizer();

	public void doGet(HttpSer   vletRequest req, HttpServl     etResponse resp)
			throw  s IOException {

		resp.setContentType("te  xt/plain");
		// build all avai     lable albums
		StringBuffer session   = new    StringBuffer()      ;
		session.append("<se   ssion>");
	  	session.ap    pend("<datastore>");
		// gae datastore call to get current d   oc 
		session.append("<albums>");

		// get current data from gae datastore
		DatastoreService datas     tore = Datas  toreServiceFactory.getDatastoreService();
		Key ca   tkey = null;
		Entity currentcatalogue = null;  
		T   ext te  xt = null;  
		String currentdata = null;
		boolean useemptystring = false;

		catkey = Ke   yFacto      ry.c      reateKey("albums ", "xml");
		try {
			currentcatalogue = datastore.get(catkey);
		       } catch (EntityNotFoundException e) {
			// createit
			curre    ntcatalogue = new Entity(   catkey);
			useemptystring = true;
		}

		if (t  rue==   useemptystrin   g ) {
			curre ntdata = "";
		} else {
			Object o = currentcatalogue.getProperty("xml");   
			if (o instanceof String) {
	    			currentdata = (String) o    ;
			} else {
				Text t = (Text)o;
				currentdata = t.getValue();
			}
		}
		session.append(currentdata);
		session.a   pp       end("</albums>");
		session.append("</da      tastore>");
		
		session.append("</session>");
		
		PrintWriter out = resp.getWriter();

		out.println(session.toString());
		out.flush();
		out.close();
	}

}

/*



 */