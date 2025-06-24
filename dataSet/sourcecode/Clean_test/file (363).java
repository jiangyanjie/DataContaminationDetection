package za.co.andrewrobinson.forandrew.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import za.co.andrewrobinson.forandrew.domain.Domain;
import za.co.andrewrobinson.forandrew.domain.LoginResult;
import za.co.andrewrobinson.forandrew.domain.NotificationLink;

public class APIStubImpl implements APIStub {
	
	//These control stub behaviour
	boolean loginShouldSucceed = true;
	
	//These contain data to return
	List<Domain> domains;
	Map<Integer, List<NotificationLink>> notificationsPerDomain;
	
	
	public APIStubImpl() {
		
		//populates domains and notificationsPerDomain
		setUpTestData();
		
	}

	
	public LoginResult doLogin(String username, String password) {
		
		//System.out.println("doLogin, username:"+username+", password:"+password);
		
		LoginResult lr = new LoginResult();

		if (loginShouldSucceed) {
			
			lr.setSuccess(true);
			lr.setDomains(domains);
			
		} else {
			
			lr.setSuccess(false);
			lr.setErrorMessage("Login Failed. Please try again");
			
		}
		
		return lr;
		
	}

	
	public Map<Integer, List<NotificationLink>> getNotificationsForUserOnDomains(String username, Set<String> domainIds) {
		
		
		Map<Integer, List<NotificationLink>> relevantNotifications = new HashMap<Integer, List<NotificationLink>>();
		
		for (String domainId: domainIds) {
			
			Integer domainIdAsInteger = Integer.parseInt(domainId);
			
			relevantNotifications.put(domainIdAsInteger, notificationsPerDomain.get(domainIdAsInteger));
			
		}

		return relevantNotifications;
		
	}


	public void setLoginShouldSucceed(boolean loginShouldSucceed) {
		this.loginShouldSucceed = loginShouldSucceed;
	}
	
	private void setUpTestData() {
		//set up the list of Domains
		domains = new ArrayList<Domain>();
		
		domains.add(new Domain(1,"Domain One"));
		domains.add(new Domain(2,"Domain Two"));
		domains.add(new Domain(3,"Domain Three"));
		domains.add(new Domain(4,"Domain Four"));
		domains.add(new Domain(5,"Domain Five"));
		
		
		//Set up the List of notificationsPerDomain
		notificationsPerDomain = new HashMap<Integer, List<NotificationLink>>();
		
		List<NotificationLink> notificationsFor1 = new ArrayList<NotificationLink>();
		List<NotificationLink> notificationsFor2 = new ArrayList<NotificationLink>();
		List<NotificationLink> notificationsFor3 = new ArrayList<NotificationLink>();
		List<NotificationLink> notificationsFor4 = new ArrayList<NotificationLink>();
		List<NotificationLink> notificationsFor5 = new ArrayList<NotificationLink>();
		
		notificationsFor1.add(new NotificationLink("http://www.google.co.za/search?q=domain1-1", "Here is notification 1 for Domain1!"));
		notificationsFor1.add(new NotificationLink("http://www.google.co.za/search?q=domain1-2", "Here is notification 2 for Domain1!"));

		notificationsFor2.add(new NotificationLink("http://www.google.co.za/search?q=domain2-1", "Here is notification 1 for Domain2!"));
		//notificationsFor2.add(new NotificationLink("http://www.google.co.za/search?q=domain2-2", "Here is notification 2 for Domain2!"));

		notificationsFor3.add(new NotificationLink("http://www.google.co.za/search?q=domain3-1", "Here is notification 1 for Domain3!"));
		//notificationsFor3.add(new NotificationLink("http://www.google.co.za/search?q=domain3-2", "Here is notification 2 for Domain3!"));

		//notificationsFor4.add(new NotificationLink("http://www.google.co.za/search?q=domain4-1", "Here is notification 1 for Domain4!"));
		//notificationsFor4.add(new NotificationLink("http://www.google.co.za/search?q=domain4-2", "Here is notification 2 for Domain4!"));

		notificationsFor5.add(new NotificationLink("http://www.google.co.za/search?q=domain5-1", "Here is notification 1 for Domain5!"));
		//notificationsFor5.add(new NotificationLink("http://www.google.co.za/search?q=domain5-2", "Here is notification 2 for Domain5!"));
		
		notificationsPerDomain.put(1, notificationsFor1);
		notificationsPerDomain.put(2, notificationsFor2);
		notificationsPerDomain.put(3, notificationsFor3);
		notificationsPerDomain.put(4, notificationsFor4);
		notificationsPerDomain.put(5, notificationsFor5);
	}
	
	
}
