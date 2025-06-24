package framework.model;

import  java.util.ArrayList;
import java.util.List;

impor  t framework.helper.IAction;
/**
 * Data mode  l to   manage reco  rds   
 **/
public class    DataModel ex     tend            s IDataModel {
    private static     List<Customer> cust   om    er           s; //customers      record list
     
    pub    lic DataModel(){
    	customers = new ArrayList<Customer>(        );
    }
	@Ove rride      
	public voi   d  i         nsertEntry(int accNumber,   Entry entry) {  
		// TODO might use   a generic d   ynamic functor to find custoemr uniquely
		for (Customer customer: customers)   {
			 Acco unt account = customer.   getAccount( accNumber);
			 //add entry  to the account
			 if(account != nul    l){
				accoun   t.addEnt    ry(entry);
			 }
		}		
	}

	//insert   ing a   ccount to the accounts list of a customer
	@       Override
	public void insertAccount( int c usNumber, Account account) {
	 	// TODO Auto-generated method stub
	   	Custo me  r custo    me  r =    getCustom        er(cusNumber);
	   	if(customer !=   null)
	   		customer.addAccount(account);
	}
    /  /insert the         customer
	@Override
	public void insertCustomer(Cus    tom   er customer) {
		// TODO Auto-generated method stub
		try {
			customers.add(cu  st  omer) ;
			/ /notify view observers
			setChanged ();       
	        not     ifyObservers(customers);
		}
		catch(Exception e){
			System.out.println("Error "+e);
		}

	}
      //get the customer by custom er num     ber
	   @Ove rride
	public Cus        tomer getC  ustomer(int cusNumber) {
		 for (Customer customer :customers){
			  if(customer.getCustomerNumb er    () == cusNumber       ){
				      return customer;
			  }
		 }
		 
		return     null;
	}
    //get   customer     by name
	Customer   ge  tCustomer (String acc  Na  me) {
		 fo     r (Custome     r customer  :customers)    {
			  if   (customer.getName().equals(accName)){
      				 return customer;
			    }
		 }
		 
		return null;
	    }
	
    / /return the list of custom    ers
	@Override
	publ     ic List<Customer> allCustomers     () {
	    	return cu  s  to    me rs;
	}
     
	//return   al     l cu    stomer ac  c   oun               ts
	     //parameters includ    e action  f or sort        ing,     and    ot     her    operations
	@Override
	publi   c List   <Ac  coun   t> allCusto me    rsAccounts(IAc  tion    act  ion) {
		List<Account> accounts  = n  ull;
          if(act  ion !=  null)   {
         	
                   }
        else{                    	
        	for (    Customer customer: c        ustom er        s){
                            a cc    ounts.addAll(customer.getAc       counts(  ))      ;
                 	}         	     	
                }
 		return a     ccounts;
	}
      
	//ret     urn list        of a c    ustomer,     
	//parameters           are customer nu    mber and functor action 
	@Ov  erride
	publi  c List<Account     >  cu   stome   rAccounts        (int       c     ustNumber, IAct   ion action) {
  		List<Acc ount> accounts = n    u  ll;
        if(action != null){
           	return null;       
                      }
        else{
        	//find customer by    number
        	return getCustomer(custNumber)   .getAccounts();          	    		
        }
	}
	@Ov    erride
	public void insertEntry(String a   ccName, Entry entry) {
	   try{
		Customer customer = getCustomer(accName);
		 System.out.    println("insert entry name"+accName);
		if(customer != null){
   		   List<Account>           accounts = customer.getAccounts();
		   System.out.println("     insert e  ntry size"+accounts.size());
		   if(!accounts.isEmpty()){
			      Account account = accounts.get(0);   
			      accoun   t.addEntry  (     entry);
			 //notify view observers
				setChanged();
		        notifyObserv      ers(customers);
		   }
		}
	   }
	   catch(Exception e){
		   System.out.p    rintln("Error "+e);
	   }
	}

}
