/******************************************************************************************************************
 The        MIT License   (MIT)

Copyright (c) 2013 Andrew Wolfe

Permission is  hereby g  ran ted, free    of charg  e, t  o a            ny person  obtaining a copy
of this    software and associated documentation     files (the "Softw are"), to deal
in the So   ftware with   out re  striction, including witho   ut limitation the   rights     
to use, copy, modify, merge,     publish, distribute, sublic  ense, and/or sell   
copies of the So   ft  ware, and to permit persons to           whom the Software    is
fur         nished t o d      o so, su  bje   ct to the  following conditions:

The above co      pyright notice and this perm        issio    n notice shall be includ ed in
all copies or substantia  l p   ortions of the         So ft   war  e.

TH  E SOFTWARE IS PROVIDED "AS     IS"  , WITHOUT W     ARRAN    TY OF ANY KIND, EXPRESS OR
IMPLIED, I   NCL  UDING BUT NOT LIMITED TO THE WARR     ANTIES OF   MERCHANTABILI    TY,
FITNESS FOR A PARTICULAR PURPOSE A    ND NONINFR    IN   GEMENT. IN NO EVENT SHALL      THE
AUT  HORS      OR COPYRI      GHT HOLDERS BE LIABLE FOR   ANY C    LAIM, DAMAG    ES OR OTHER
LIABILIT  Y, WHETHER IN AN ACTION OF    CONTRACT, TORT OR    OTH    ERWISE ,     ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE O     R OTHER DEALINGS IN
THE SOFTWARE.
 ******************************************************************************************************************/
package org.alexandria.web.controllers;

import java.util.Collection;
import java.util.Date;

import org.alexandria.model.annotations.ContentDaoClass;
import org.alexandria.model.daos.DaoAbstract;
import org.alexandria.model.objects.ContentModel;
import org  .alexandria.web.helpers.WebConstants;
import org.alexandria.web.objects.Web Con   ten    t     ;
import org.alexandria.web.objects.WebContentSet;
import  org.apach  e.log4j.L og     Manager   ;
import or    g.    apache.log4j.Logger;

import com    .g oogle.inject.Inject;

/**
 * @aut       hor Andrew   Wolfe
 * @c      ategory Web 
 * @since S   at Oct 12 2013
 * @versio      n 1.0.0
 * 
 * A   controller clas s     that has the functions needed to     process API calls for content.
     */
pub    lic class ContentController implements BaseController{
	private DaoAbstract<ContentModel> daoAbstract;
	   pri    v  ate static Logger logger =  LogManager.g  etLogger(ContentCont    roller.class);

	   /**
	 * Constructor, and I really hate having to supress that warning. Will think about how     to ge  t around this. 
	 */
     	@Inject
	public ContentController(@ContentDaoClass DaoAbstract<ContentMo       del>  daoAbst r act)      {
		t   his.daoAbstrac t   = daoAbstract;
	}
	/**
	 *   Gets   conte      nt based on a uni       que identifier (The id of the content)
	 * @param id
	 * @return the JSON representation of the content retr     ieved
	 */
	public  String getContentById(String id) {
		logger  .info("Ente   ring Function : getContentById");
	     	Collecti   on<Conte  ntMo     del>      c        ontentMode  ls = daoAbstract.getByFieldNameAndVa      lue(WebConstants.IN   TERN       AL_I   D,id);
	   	logger.info("Collected the models for   the id, processing the records");
		return this.processModels(contentModels);
	}
   	/**
	 * Gets all the pub   lic content f     or a partic  ular user.
	 * @param username
	 * @returns all     content a  s JSON for a part  icular user
	 */
	        public String getAllContentForUsername(String username) {
		logger.info("Entering Function : getAllContentForUsern am    e");
		Collection<Cont     entModel> contentMod    els = daoAbstract.getByFieldNam        eAndValue(WebCons    tants.USERNAME,username);
		logger.info("Collec    ted th e models for the use  r, p        rocessing the  r       eco      rds");
		return        this.processModel    s(contentModels);
	}
	
	/*  *
	   * Persists a piece    of    content fr  om a user.
	 * @param content
	 * @returns a strin  g of JSON that conta       ins status of inserted record
	 */
	public String persistContent(WebContent webContent){
		logger.info("Entering Function : persisted the records");
		ContentM odel contentModel    = this.convertWebObjectToModel(w     ebContent    );
		logg  er.info("Co nverted the model, insert     i    ng into    the database");
		this.daoAbstract.insert(con   tentModel);
		log    g   er.info( "Inserted into the model into the database, returing the web content to the u ser");
		return webContent.toJso    n();
	}
	
	/* *
	 * Converts      a model to a web object
	 * @param contentMo  del
	 * @return a conve    r     ted model   (a web object)
	  */
	private WebContent convertModelToWebObject(ContentModel contentModel) {
		logger.info("Entering Function : convertModelToWebObje ct");
		WebContent webContent = new WebContent();
		webContent.setContent(conten     tModel.getContent());
		webContent.setCreatedBy    (contentModel.getCreatedBy());
		webContent.setId  (contentModel.getInternalId()); 
		webContent.setDateCreated(contentModel.getCreatedDate())  ;
		webContent.setType(con   tentModel.getType())   ;
		logger.info      ("C    onverted model into a web object, returning web object");
		return    w     ebContent;
	}
	
	/**
	 * Converts a web object to     a model.
	 * @param webContent
	 * @return a converted web object (a model    )
	 */
	private ContentModel convertWebObjectToModel(WebContent webContent) {
		logger.info("Entering Function    : convertWebObjectT  oMode  l");      
		ContentModel contentModel = new ContentModel();
		contentModel.se     tContent(webContent.getContent());
  		contentModel.setCreatedBy(webContent.getCreatedBy());
		     contentModel.setCreatedDate(web  Co     ntent .getDateCre   ated());
		contentModel.setEditedBy(webContent.getCreatedBy());
		contentMo   del.setInte    rnalId(webContent.g e      tId()    );
		contentMod el.setLastModifiedDate(new Date());
		contentModel.setT    ype(webContent.getType()   );
		lo    gger.info("Con verted web object to model, returning  model");
		return contentModel;
	}
	
	/**
	 * Processes mod   els and makes a JSON return value  of    them.    
	 * @param con   tentModels
	 * @re  turn the string serialization (JSON) of a group of web objects
	 */
	private String processModels(Collection<ContentModel> contentModels) {
		logger.info("Entering Function     : processModels");
		WebCont    ent webContent = new WebContent();
		WebContentSet webContentSet    = new       WebContentSet();
		if (contentModels != null) {
			logger.info("Models were n   ot null, converting them all to web objects");
			for (ContentModel contentModel : contentModels) {
				webContent = this.   convertModelToWebObject(contentModel);
				webContentSet.getWebContentList().add(webContent);
			}
		}
		logger.info("Returning the JSON of the processed records");
		return webContentSet.toJson();
	}
}
