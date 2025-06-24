package za.co.andrewrobinson.forandrew.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import   java.util.Map;
import java.util.Set;

impo   rt za.co.andrewrobinson.forandrew.domain.Domain;
i  mport za.co.andrewrobinson.forandrew.domain.LoginResult;
import za.co.andrewrobinson.forandrew.domain.NotificationLink;

public class APIStubIm    pl implements APIStub     {
	
	//These con    trol stub behaviour
	boo   lean loginShouldSucceed = true    ;
	
   	//These contain data to ret   urn
	List<Domain> domains;
	Map<Integer, List<N otificationLink>> notificatio   nsPerDomain;
	
	
	public APIStubImpl() {
		
		//populates domains and notificationsPerDomain
		setUpTestData();      
		
	}

	
	public LoginResu  lt doLogin(String username, String password   ) {
		
		//System.out.println("doLogin,   username:"+userna      me+", password:"+passw   ord);
		
		LoginResult lr = new LoginResult();

		if (loginShouldSucceed) {
			
			lr.setSuccess (true);
			lr.setDomains(domains);
			
		} else {
			
			lr.setSuccess(fals   e);
			lr.setErrorMessag  e("Login Failed. Please try again");
			
		}
		
		return lr;
		
	}

	
	public Map<Integer, List<NotificationLink>> getNotificationsForUserO   nDomains(String   username, Set<String> domainIds) {
		             
		
		Map<Integer, List<NotificationLink>> relevantNotificatio       ns = new HashMap<Integer, List<NotificationL  ink>>()  ;
		
		for (String domainId: domainIds) {
			
			Integer domainIdAsInteger = Integer.parseInt(domainId);
			
	  		relevantNotifications.put(domainIdAsInteger, notificationsPerDomain.get(domainIdAsInteger));
			
		}

		retur      n relevantNotifications;
		
	}


	public vo    id setLoginShoul   dSucceed(boolean loginShouldSucceed) {
		thi               s.loginShouldSucceed = loginShouldSucceed;
	}
	
	private void setUpTestData()  {
		//set up the list of      D     omains
		domains = new ArrayList<Do    main>();
		
		domains.add(new Domain(1,"Doma   in One"));
		domains.add(new Domai   n(2,"Domain Two"));
		  domains.add(new Dom      ain(   3,"Domain Three"));
		domains.add(new Domain(4,"Domain Four"));
		domai    ns.add(new Domain(5,"Domain  Five"));
		
		
		//Set up the List of notificationsPerDoma       in
		notificationsPerDomain = new HashMap<Integer, List<Notifi  cationLink>>();
		
    		List<NotificationLink> noti   ficationsF     or1 = new ArrayList<NotificationLink     >();
		List<Notifi   cationLink> no   tificationsFor2 = ne    w ArrayList<No  tificationLink>();
		List<NotificationLink> notificati  onsFor3 = new ArrayList<NotificationLink>();
		List<NotificationLink> notificationsFor4 = ne   w ArrayList<NotificationLink>();
		List<Noti    ficationLink> notificationsFor5 = new ArrayList<NotificationLink>();
		
		notificationsFor1.add(new      NotificationLink("ht  tp://www.google.co.za/search?q=domai      n1-1", "H   ere is notification 1 for Domain1!"));
		notificationsFor1.add(new Notificati     onLi      nk("http://www.google.co.za/search? q      =domain1-2",   "Here is notification 2 for Domain1!"));

		notificationsFor2.add(new Noti  ficatio    nLink("http://www.goo  gle.co.za/search?q=domain2-1", "Here is notification 1 for Domain2!"));
		//notificationsFor    2.add(new NotificationLink  ("http://www.google.co.za/search?q=doma       in2-2", "Here is notification 2 for Domain2!"));

		notificationsFor 3.add(new NotificationLink("http://www.googl    e.co.za/search?q=domain3-1", "Here is n      otification 1 for Domain3!"));
		//notificationsFor3.add(new NotificationLink("h  ttp://www.google.co.za/search?q=domain3-2",     "Here is notification 2 for Domain3!"));

		//noti  ficationsFor4.add(new NotificationLink(   "http://www.google.co.za/search?q=domain4-1", "Here   is notification 1 for Domain4!"));
		//noti   ficationsFor4.add(new Notifica    tionLink("http    ://www.google.c      o.za/search?q=domain4-2", "Here is notification 2 for Do      main4!"));

		notificationsFor5.add(new NotificationLink("http://www.goog le.co.     za/search?q=do main  5-1", "Here is notification 1 for Domain5!"));
		//notificationsFor5.add(new NotificationLink("http://www.google.co.za/search?q=domain5-2",  "Here is notification 2 for Domain5!"));
		
		notificationsPerDomain.put(1, notificationsFor1);
		notificationsPerDomain.put(2, notificationsFor2);    
		notificationsPerDomain.put(3, notificationsFor3);
		notificationsPerDomain.put(4, notificationsFor4);
		notificationsPerDomain.put(5, notificationsFor5);
	}
	
	
}
