/******************************************************************************************************************
  Th   e MIT Licens  e (MIT)

Copyright        (c) 2013 Andrew Wolfe

Perm  iss   ion is he   reby     granted, free    of ch     arge, to an   y     pers  on obt    aining a copy
of this software and              associated documentation    files (the "Software"), to deal
in the Software without  restriction, including without limita  tion the rights
t  o use, copy, modify,   mer ge     , publish, distribute, sublicense     , and/or    se  ll 
copie s of the Software,   an    d to permit pe rsons to wh      om the Software is
furnishe   d to do so, subject to the following con   ditions:

The above cop    yright notice and this permission notice  shall    b       e included in
all copies or substantial portions of the     Software.

  THE S     O    FTW  ARE IS PROVIDE    D "AS IS", WITHOUT       WARR       ANTY OF ANY KIND, EXPRESS    OR
IMPLI  ED, INCLUDING BUT NOT LIMITED TO    THE WARRANTI      E   S OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE     AND N  ONINFRINGEMENT. IN NO EVENT    SHALL      THE
AUTHORS OR COPYRIGHT HOLDERS BE         LIABLE FOR AN       Y CLAIM,   DAMAGES   OR OTHER
LIABIL    ITY, WHETHER IN AN ACTI ON    OF CONTRACT, TORT OR OTHERWI     SE, ARISING  FROM, 
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 ******************************************************************************************************************/
package org.alexandria.web.routes;

import    javax.ws.rs.GET;
import javax.ws.rs.POST    ;
import java    x.ws.rs.Path;
import ja       vax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.alexandria.web.controllers.ContentController;
import org.alexandria.web.controllers.ContentControllerModule;
import org.alexandria   .web.he   lpers.ControllerHelpe   r;
import org.alexand ria.we b.help    ers.WebConstan    ts;
import org.alexandr ia.web.objects.WebContent;
import org.apache.log4j.LogManager     ;
import org.   apac     he   .log4   j.Logger;

/**
 * @author Andrew Wolfe  
 * @category Web
 * @since Sa t Oct 12 2  013
 * @      v      ersion 1.0.0
 * 
 * Class where routes ar          e be     ing defined for the content web services.
 */
@  Path(WebConstants.API   )
public class ContentWebRoutes     {
	private ContentContr  oller contentController;
	private static Logger logger = LogMan    ager.getLogger(Conte     ntWebRoutes.class);
	
	       /**
	 * Constructor
	 */
	public ContentWebRout es() {
		contentController =   (ContentController)    Control         ler   He  lper.getCo    ntrol    ler(ContentContr     oller.class, new Content   ControllerMo     dule());
	}
	
	/**
 	 * A HTTP   GET r out e for      getting content based on       an ID specifie  d in the URL.
	 * @annotation GET
	 * @annotation Pa th    
	 *    @annotation Produc   es
	 * @param id
	 * @return String  representation of content retrieved by i      d
	 */
	@GET
	@Path(We   bConstants.CONTENT_BY_ID)
	@Produces(Med iaType.A     PPLIC      ATION_JSON)
	public    String getContentById(@PathPar   am(We  bConstants.PATH_I          D) String id) {
		logger.  info( "Entering    Functi    on : getCon  tentById");
		   r eturn contentController.getContentById(i             d);
	}
	
	/**
	 * A HTTP GET route for gettin  g all the content created by a user based     on an usernam    e specified in the URL.
	 * @annotation GET
	 * @annotation Path
	 * @annotation Produces
	 * @param id
	 * @return Stri   ng repr es   entation of content retrieved    b   y the username
	 */
	@POST
	@P   ath(WebConstants.ALL_CONTENT    _FOR_USERNAM    E)
	@ Produ    ces(MediaType.AP     PLICATION_JSON)
	public String getAllConten  tForUserna       me(@PathParam(   WebConstants.PATH_USERNAME) String username) {
		logger.info("Entering Function :   getAllContentFo     rUsername");
		return cont       entContro    ller     .getAllContentForUsername(u   sername);
	}
	
	/**
	 * A HTTP POST route for persisting content created by a user.
	 * @annotation POST
	 * @annotation Path
	 * @annotation Produces
  	 * @p  aram id
	 * @return json that specifies the results of persisting the records.
	 */
	@POST
	@Path(WebConstants.PERSIST_CONTENT)
	@Produces(MediaType.APPLICATION_JSON)
	public String saveContent(WebContent content) {
		logger.info("Entering Function : saveContent");
		return contentController.persistContent(content);
	}
}
