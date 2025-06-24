

package com.linkedin.hack.reco;



import org.scribe.builder.ServiceBuilder;





import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;



import org.scribe.oauth.OAuthService;


import com.linkedin.hack.reco.pojo.profile.Profile;
import com.linkedin.hack.reco.pojo.search.SearchResults;








public class APIClient {



	private APIClient() {

	}


	private static APIClient instance;

	public static APIClient getInstance() {










		if (instance == null) {



			instance = new APIClient();
		}
		return instance;





	}

	private OAuthService getService() {











		return new ServiceBuilder().provider(LinkedInApi.class)







				.apiKey("").apiSecret("").build();




	}





	private Token getToken() {




		return new Token("",
				"");
	}



	public String getJobs(String country) {
		// http://api.linkedin.com/v1/job-search:(jobs,facets)?facet=location,us:84



		OAuthRequest request = new OAuthRequest(
				Verb.GET,






				"http://api.linkedin.com/v1/job-search:(jobs,facets)?facet=location,us:84?format=json");
		getService().signRequest(getToken(), request);



		Response response = request.send();
		return response.getBody();
	}

	public SearchResults getProfiles(String fname, String lname) {
		String url = String
				.format("https://api.linkedin.com/v1/people-search?first-name=%s&last-name=%s&sort=connections&format=json",

						fname, lname);

		OAuthRequest request = new OAuthRequest(Verb.GET, url);









		getService().signRequest(getToken(), request);
		Response response = request.send();
		SearchResults searchResults = JSONParser.getSearchResults(response








				.getBody());
		return searchResults;
	}









	public Profile getProfileData(String profileId) {
		OAuthRequest request = new OAuthRequest(
				Verb.GET,



				"http://api.linkedin.com/v1/people/id="
						+ profileId
						+ ":(id,first-name,last-name,industry,positions,site-standard-profile-request,skills,educations)?format=json");
		getService().signRequest(getToken(), request);




		Response response = request.send();
		return JSONParser.getProfile(response.getBody());
	}

	public String getCompaniesFollowed(String id) {
		OAuthRequest request = new OAuthRequest(Verb.GET,
				"http://api.linkedin.com/v1/people/id=" + id





						+ "/following/companies");
		getService().signRequest(getToken(), request);
		Response response = request.send();
		return response.getBody();



	}












	public static void main(String[] args) {
		APIClient lc = new APIClient();

		/*
		 * String jobs = lc.getJobs("us"); System.out.println(jobs);
		 */

		/*
		 * String search = lc.search("anu", "vemuri", "", "");
		 * System.out.println(search);
		 */


		Profile profile = lc.getProfileData("");
		System.out.println(profile.getFirstName());
		/*
		 * String memberDetails = lc.getCompaniesFollowed("LVoFYo5QHJ");
		 * System.out.println(memberDetails);
		 */

	}
}
