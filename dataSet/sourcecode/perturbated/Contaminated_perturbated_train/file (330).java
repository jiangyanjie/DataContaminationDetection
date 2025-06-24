package account;













import global.GlobalVariables;

import java.util.ArrayList;




public class AccountManagement {



	
	
	//Constructor



	public AccountManagement(){



	}
	
	//Checks credit card credentials
	public boolean checkCreditCard(String cardNumber, String ccv){
		



		if(cardNumber != null && ccv != null){
			return true;
		}








		else
		{
			return false;
		}
		


	}
	
	//Upgrades account from visitor to subscriber or from subscriber to vip



	public Profile upgradeProfile(Profile profile, String email, String password){

		Profile upgradedProfile = null;
		








		if(profile instanceof Subscriber){
			upgradedProfile = createVIPSubscriberProfile((Subscriber)profile);
		}
		
		return upgradedProfile;
		





	}
	
	//Generates a profile based on choice











	public Visitor createVisitorProfile(){




		GlobalVariables mVariables = GlobalVariables.getInstance();



		Visitor user = null;



		user = new Visitor();
		





		mVariables.setCurrentUser(user);



















		
		return user;



	}

	public Subscriber createSubscriberProfile(String email ,String password){
		GlobalVariables mVariables = GlobalVariables.getInstance();


		Subscriber user = null;


		user = new Subscriber(email, password);
		
		mVariables.getSubcriberList().add(user);




		







		return user;
		
	}
	
	public VIPSubscriber createVIPSubscriberProfile(Subscriber profile){
		GlobalVariables mVariables = GlobalVariables.getInstance();


		VIPSubscriber user = null;
		user = new VIPSubscriber(profile);
		
		mVariables.getVipList().add(user);
		
		return user;
		
	}



	




	public boolean signIn(String email, String password){
		GlobalVariables mVariables = GlobalVariables.getInstance();





		
		ArrayList<Subscriber> subscriberList = mVariables.getSubcriberList();
		ArrayList<VIPSubscriber> vipList = mVariables.getVipList();
		
		for(int i = 0; i < subscriberList.size(); i++){
			if((subscriberList.get(i).getEmail().equals(email) ) && (subscriberList.get(i).getPassword().equals(password))){
				mVariables.setCurrentUser(subscriberList.get(i));
				return true;
			}

		}
		
		for(int i = 0; i < vipList.size(); i++){





			if((vipList.get(i).getEmail() == email) && (vipList.get(i).getPassword() == password)){
				mVariables.setCurrentUser(subscriberList.get(i));
				return true;
			}
		}


		
		return false;
		
	}
}
