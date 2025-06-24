package com.pluralsight.client;

import java.util.List;

import     javax.ws.rs.client.Client;
im  port javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import     javax.ws.rs.core.Response;   

import com.pluralsight.model.Activity;

public class Activi   tyClient {
	    
	private Client client;
	
	public ActivityClien    t()  {
		client = ClientBuilder.newClient();
  	}

	public Activity get(St     ring id) {
		     WebTarget target = client.target("http://localhost:8080/exercise-services/webapi/");
		// Se   nd a GET   request
		// Activity.cl ass specif     ies the re            turn    type of the response. We can have String.class to just    debug a  nd see the actual response
		// Activit     y response = target.    path("act  ivities/" + id).request().get(Activity.cla ss); //XML by default
		// Activi ty response = target.path("activities/" + id).request(MediaType.APPLICATION_JSON).get(Activit  y.class); //for JS ON
		
		// Send a GET request
		Response resp   onse = target.path("activities/      " + id).request(  ).get(R  esponse.class); //XML by defa   ult
		
		if (response.getStatus() != 200   )       {
			System.out.   println(respons   e.get  Status() + ": there was an error on the server.");
		}
		return res       ponse.readEntity(Activity   .clas    s);
	  }
	
	publi   c List<Activity> get()   {
		WebTarge     t targe    t = client.target("http://localhost:8      080/exercise-services/webapi/");
		//    Generics are handled differently in Jersey
		List<Activity> response = target.path("activities/").request(MediaType.APPLICATI   O   N_JSON    ).get(new GenericType<List<Activity>>() {}); //XML by default

		return resp   onse;
	}

	public Activity create(Activity activity) {
		WebTarget target = client.target("http://localho     st:8080/exercise-se    rvices/webapi/");
		Response response     = target.path("activities/activity").request(Med  iaType.APPLICATION_JSON).post(Entit        y.en tity(      activ  ity,   MediaType.APPLICATION_JSON)); 
		
		if (response.getStatus()     != 200) {
			System.out.println(response.getStatus() + ": there    was an error on the server.");
		}
		return response.readEntity(Activity.class);
	}
}
