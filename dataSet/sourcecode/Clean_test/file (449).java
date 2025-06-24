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
package org.alexandria.web.controllers;

import java.util.Collection;
import java.util.Date;

import org.alexandria.model.annotations.ContentDaoClass;
import org.alexandria.model.daos.DaoAbstract;
import org.alexandria.model.objects.ContentModel;
import org.alexandria.web.helpers.WebConstants;
import org.alexandria.web.objects.WebContent;
import org.alexandria.web.objects.WebContentSet;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.inject.Inject;

/**
 * @author Andrew Wolfe
 * @category Web
 * @since Sat Oct 12 2013
 * @version 1.0.0
 * 
 * A controller class that has the functions needed to process API calls for content.
 */
public class ContentController implements BaseController{
	private DaoAbstract<ContentModel> daoAbstract;
	private static Logger logger = LogManager.getLogger(ContentController.class);

	/**
	 * Constructor, and I really hate having to supress that warning. Will think about how to get around this. 
	 */
	@Inject
	public ContentController(@ContentDaoClass DaoAbstract<ContentModel> daoAbstract) {
		this.daoAbstract = daoAbstract;
	}
	/**
	 * Gets content based on a unique identifier (The id of the content)
	 * @param id
	 * @return the JSON representation of the content retrieved
	 */
	public String getContentById(String id) {
		logger.info("Entering Function : getContentById");
		Collection<ContentModel> contentModels = daoAbstract.getByFieldNameAndValue(WebConstants.INTERNAL_ID,id);
		logger.info("Collected the models for the id, processing the records");
		return this.processModels(contentModels);
	}
	/**
	 * Gets all the public content for a particular user.
	 * @param username
	 * @returns all content as JSON for a particular user
	 */
	public String getAllContentForUsername(String username) {
		logger.info("Entering Function : getAllContentForUsername");
		Collection<ContentModel> contentModels = daoAbstract.getByFieldNameAndValue(WebConstants.USERNAME,username);
		logger.info("Collected the models for the user, processing the records");
		return this.processModels(contentModels);
	}
	
	/**
	 * Persists a piece of content from a user.
	 * @param content
	 * @returns a string of JSON that contains status of inserted record
	 */
	public String persistContent(WebContent webContent){
		logger.info("Entering Function : persisted the records");
		ContentModel contentModel = this.convertWebObjectToModel(webContent);
		logger.info("Converted the model, inserting into the database");
		this.daoAbstract.insert(contentModel);
		logger.info("Inserted into the model into the database, returing the web content to the user");
		return webContent.toJson();
	}
	
	/**
	 * Converts a model to a web object
	 * @param contentModel
	 * @return a converted model (a web object)
	 */
	private WebContent convertModelToWebObject(ContentModel contentModel) {
		logger.info("Entering Function : convertModelToWebObject");
		WebContent webContent = new WebContent();
		webContent.setContent(contentModel.getContent());
		webContent.setCreatedBy(contentModel.getCreatedBy());
		webContent.setId(contentModel.getInternalId());
		webContent.setDateCreated(contentModel.getCreatedDate());
		webContent.setType(contentModel.getType());
		logger.info("Converted model into a web object, returning web object");
		return webContent;
	}
	
	/**
	 * Converts a web object to a model.
	 * @param webContent
	 * @return a converted web object (a model)
	 */
	private ContentModel convertWebObjectToModel(WebContent webContent) {
		logger.info("Entering Function : convertWebObjectToModel");
		ContentModel contentModel = new ContentModel();
		contentModel.setContent(webContent.getContent());
		contentModel.setCreatedBy(webContent.getCreatedBy());
		contentModel.setCreatedDate(webContent.getDateCreated());
		contentModel.setEditedBy(webContent.getCreatedBy());
		contentModel.setInternalId(webContent.getId());
		contentModel.setLastModifiedDate(new Date());
		contentModel.setType(webContent.getType());
		logger.info("Converted web object to model, returning model");
		return contentModel;
	}
	
	/**
	 * Processes models and makes a JSON return value of them.
	 * @param contentModels
	 * @return the string serialization (JSON) of a group of web objects
	 */
	private String processModels(Collection<ContentModel> contentModels) {
		logger.info("Entering Function : processModels");
		WebContent webContent = new WebContent();
		WebContentSet webContentSet = new WebContentSet();
		if (contentModels != null) {
			logger.info("Models were not null, converting them all to web objects");
			for (ContentModel contentModel : contentModels) {
				webContent = this.convertModelToWebObject(contentModel);
				webContentSet.getWebContentList().add(webContent);
			}
		}
		logger.info("Returning the JSON of the processed records");
		return webContentSet.toJson();
	}
}
