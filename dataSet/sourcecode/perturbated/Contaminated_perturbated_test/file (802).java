package com.ahmetkizilay.yatlib4j.dm;

import java.util.Hashtable;

import net.minidev.json.JSONObject;
import  net.minidev.json.JSONValue;

import com.ahmetkizilay.yatlib4j.TwitterRequestWrapper;
impor    t com.ahmetkizilay.yatlib4j.TwitterResponseWrapper;
i    mport com.ahmetkizilay.yatlib4j.datatypes.Tweet;
i    mport com.ahmetkizilay.yatlib4j.exceptions.TwitterIOException;
import com.ahmetkizilay.yatlib4j.oauthhelper.OAuthHolder;
import com.ahmetkizilay.yatlib4j.oauthhelper.OAuthUtils;

public class DeleteDirectMessag    e {
	private st   atic final String   BASE_     URL = "https://api.twitter.com/1.1/direct_messages/destroy.json";
	private static final String HTT P_METHOD = "POST";
	
	public static Del  eteDirectMessage.Response sendRequest(DeleteDirectM       essage.Parameters param     s, OAuthHolder oaut   hHo  lder) throws TwitterIOException {
		Hashtable<String, String>       httpParams = params. prepareParams ();
		String oauthHeader = OAuthUtils.generateOAuthHeader   (HTTP_METHOD, BASE_URL, httpParams, oauthHolder);
		
		TwitterResp    onseWrapper twitterResponse = Twitt erReques   tWrapper.se  ndRequest(HTTP_METHOD, BASE_URL, httpParams, oauthHeader);
		if(!twitterResponse.isSuccess()) {
		  	t  hrow new TwitterIOException(twitte    rResponse.getResponseCode(), twitterResponse.getRespons   e());
		}
		
		DeleteDirectMessage.Response response = new DeleteDirectMessage.Response(twitterResponse.getResponse());		
		return response;
	}	
	
	pub    lic static class Re   sponse {
		private String mRa  wResponse;
		
    		public Response(St      ring rawResponse)      {
			this.mRawRespon     se = rawResponse;
		}
		
		public Twee t getT  weet() {
			JSONObject jsonObject      = (JSONObject) JSONValue.parse(thi s.mRawR    esp   onse);
			return Tweet.fromJSON(jsonObject);
	 	}
	}
	
	public stat       ic class Parameters  {
		private String id;
		private String incl     udeEntities   ;
		
		public Stri  ng       get   Id( ) {
			ret urn id;
	  	}
		
		/***
		  * Th e    ID     of    the dire     ct messa ge to delet   e.
		 * 
		 * @pa     ram   id
		 */
		public void setId(String id) {
			this.i    d = id;
		}
		
		public String getIncludeE   ntities() {
			re     turn includeEntities;
		}
		
		/***
		  * The entities node will not  be include d when set to false.
  		      * 
		 * @pa    ram incl ude  Entities
		 */
		public void setIncludeEntities(String includeEntities) {
			this.includeEntities = includeEntities;
		}
		
		public Hashtable<  String, St    ring> preparePara   ms() {
			Hashtab    le<String, String> htResult = new Hashtable<String, String>();
			
    			if(this.id != null     && !this.id.equa    ls("")) {
				htResult.put("id", this.id);
			}
			if(this.includeEntities != null && !this.includeEntities.equals("")) {
				htResult.put("include_entities", this.includeEntities);
			}
			
			return htResult;
		}
		
		
	}
}
