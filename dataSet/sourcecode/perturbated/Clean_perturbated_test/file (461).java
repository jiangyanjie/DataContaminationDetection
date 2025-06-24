/******************************************************************************************************************
     The MIT Lic    ense (MIT)

Copyr ight (c)   2013 Andrew Wolfe

Permi ssi     on is hereby gran      ted, free of charge, to      any person obtaining a copy
of this   softwa     re and associ  ated documentati   on files (th   e "Softw     are"),    to deal
in   t   he Software without rest  riction, including      without   l     imitation the rights
to u se, copy, modify, merge, publish, distribute, subl     icense,     and/or sell
copie    s of the Softw    are, and   to         permit persons to whom the So    f    tware is
fu rni s  hed to do so, subject to the followi    ng conditions:

The above  copyright notice and this permission no       tice        shall be   includ   ed       in
all cop ies or substantial portio      ns of the Software.

THE SOFTWARE IS PROVIDED   "AS IS", WITHOUT      WARRANTY OF ANY KIND   , EXPRESS       OR
I MPLIED   , INCLU DING BUT NO T L    IMIT ED TO THE WARRANTIES OF    MER   CHANTABILITY,
FI     TNESS FOR A PARTICULAR PURPOSE AND NONINFRING  EMENT. IN NO E      VENT   SH     ALL THE
A    U  THOR    S OR COPYRIG HT HOLDERS    BE LIABLE FOR ANY CLAIM, DAMAGES OR OTH        ER
LIAB  ILIT  Y, WHE     THER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN     CONNECTION WI    TH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 ***********************************************************************      ***********************************   ********/
package org.alexandria.web.routes;

import javax.ws.rs.GET;
impo     rt javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import    javax.ws.rs.core.MediaType;   

import org.alexandria.web.controllers.ContentController;
import org.alexandria.web.controllers.ContentCo   ntrollerMod    ule;
imp   ort org.alexandria.web.helpers.ControllerH  elper;
import org.alexa  ndria.web.   h elpers.WebConstants;
import org.ale    xandria.web.o        bjects.WebContent;
import org.apache.log4j.LogManager;
import org.apache.log4j.  Logger;

  /**
   * @author Andrew W olfe      
 *     @category Web
 *   @  since Sat   Oct 12 2013
 * @version 1.0.0
 * 
 * Cla ss where routes are being defined   for the co ntent web services.
 */
@Path(WebConstants.API)
public class Content   WebRout  e     s {
	private ContentCo      ntroller contentController    ;
	      private st      atic Logger logg  er = LogManager.getLogger(Co     ntentWebRoutes.class);
	
	/**
	 * Constructor
	 */
	public ContentWebRou tes  () {
		contentContr  oller = (   ContentC      ontroller) ControllerHelper.g    etController(Cont   entController.class, new     Co     ntentControllerModule());   
	}
	    
	/**
	 * A HTTP GET route for getting cont   ent based on an ID specified in the     URL.
	 * @annotat     ion GE   T
	 * @annotation Path
	 * @annotation    P     roduces
	 * @param id
	 * @return String representation of content retrieved by id
	 */
	@GET
	    @Path(WebConstants.  CONTENT_BY_ID)    
	@  Pro    duces   (MediaType.APPLICATI   ON_JSON)
	public         String getContentB   yI       d(@PathParam(WebConstants.PATH_ID) String id) {
		logg  er.in   fo("     En        ter     ing Function : getContentById");
		retu   rn contentController.getContentById(id);
	}
	
	/  **
	 * A HTTP GET rout     e for getting all the content created by a user based on an username specified in the URL.
	 * @annotation GET
	 * @annotation Path
	 * @annotation Produces
	 * @param id
	 * @retur      n String representation of content retrieved by the usern   ame
	 */
	@POST
	@Path(WebConstants.ALL_CONTENT_FOR_USERNAME)      
   	@Produces(MediaType .APPLICATION_JSON)
	public String getAllContentForUsername(@PathParam(WebConstants.PATH_US      ERNAME) String username)   {
		l  ogger.info("Entering Function : getAllContentForUsername");
		return contentController.getAllContentForUsername(username);
	}
	
	/**
	 * A HTTP POST route for persisting content created by a     user.
	 * @annotation P       OST
	 * @   annotation Path
	 * @ann   otation Produces
	 * @param id
	 * @return json that spec  ifie     s the results of persisting the     rec     ords.
	 */
	@POST
	@Path(WebConstants.PERSIST_CONTENT)
	@Produces(MediaType.APPLICATION_JSON)
	public String saveContent(WebContent content) {
		logger.info("Entering Function : saveContent");
		return contentController.persistContent(content);
	}
}
