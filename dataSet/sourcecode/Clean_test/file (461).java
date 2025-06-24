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
package org.alexandria.web.routes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.alexandria.web.controllers.ContentController;
import org.alexandria.web.controllers.ContentControllerModule;
import org.alexandria.web.helpers.ControllerHelper;
import org.alexandria.web.helpers.WebConstants;
import org.alexandria.web.objects.WebContent;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Andrew Wolfe
 * @category Web
 * @since Sat Oct 12 2013
 * @version 1.0.0
 * 
 * Class where routes are being defined for the content web services.
 */
@Path(WebConstants.API)
public class ContentWebRoutes {
	private ContentController contentController;
	private static Logger logger = LogManager.getLogger(ContentWebRoutes.class);
	
	/**
	 * Constructor
	 */
	public ContentWebRoutes() {
		contentController = (ContentController) ControllerHelper.getController(ContentController.class, new ContentControllerModule());
	}
	
	/**
	 * A HTTP GET route for getting content based on an ID specified in the URL.
	 * @annotation GET
	 * @annotation Path
	 * @annotation Produces
	 * @param id
	 * @return String representation of content retrieved by id
	 */
	@GET
	@Path(WebConstants.CONTENT_BY_ID)
	@Produces(MediaType.APPLICATION_JSON)
	public String getContentById(@PathParam(WebConstants.PATH_ID) String id) {
		logger.info("Entering Function : getContentById");
		return contentController.getContentById(id);
	}
	
	/**
	 * A HTTP GET route for getting all the content created by a user based on an username specified in the URL.
	 * @annotation GET
	 * @annotation Path
	 * @annotation Produces
	 * @param id
	 * @return String representation of content retrieved by the username
	 */
	@POST
	@Path(WebConstants.ALL_CONTENT_FOR_USERNAME)
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllContentForUsername(@PathParam(WebConstants.PATH_USERNAME) String username) {
		logger.info("Entering Function : getAllContentForUsername");
		return contentController.getAllContentForUsername(username);
	}
	
	/**
	 * A HTTP POST route for persisting content created by a user.
	 * @annotation POST
	 * @annotation Path
	 * @annotation Produces
	 * @param id
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
