package framework.model;

import java.util.ArrayList;
import java.util.List;

import framework.helper.IAction;
/**
 * Data model to manage records   
 **/
public class DataModel extends IDataModel {
    private static List<Customer> customers; //customers record list
    
    public DataModel(){
    	customers = new ArrayList<Customer>();
    }
	@Override
	public void insertEntry(int accNumber, Entry entry) {
		// TODO might use a generic dynamic functor to find custoemr uniquely
		for (Customer customer: customers){
			 Account account = customer.getAccount(accNumber);
			 //add entry to the account
			 if(account != null){
				account.addEntry(entry);
			 }
		}		
	}

	//inserting account to the accounts list of a customer
	@Override
	public void insertAccount(int cusNumber, Account account) {
		// TODO Auto-generated method stub
	   	Customer customer = getCustomer(cusNumber);
	   	if(customer != null)
	   		customer.addAccount(account);
	}
    //insert the customer
	@Override
	public void insertCustomer(Customer customer) {
		// TODO Auto-generated method stub
		try {
			customers.add(customer);
			//notify view observers
			setChanged();
	        notifyObservers(customers);
		}
		catch(Exception e){
			System.out.println("Error "+e);
		}

	}
    //get the customer by customer number
	@Override
	public Customer getCustomer(int cusNumber) {
		 for (Customer customer :customers){
			  if(customer.getCustomerNumber() == cusNumber){
				 return customer;
			  }
		 }
		 
		return null;
	}
    //get customer by name
	Customer getCustomer(String accName) {
		 for (Customer customer :customers){
			  if(customer.getName().equals(accName)){
				 return customer;
			  }
		 }
		 
		return null;
	}
	
    //return the list of customers
	@Override
	public List<Customer> allCustomers() {
		return customers;
	}
     
	//return all customer accounts
	//parameters include action for sorting, and other operations
	@Override
	public List<Account> allCustomersAccounts(IAction action) {
		List<Account> accounts = null;
        if(action != null){
        	
        }
        else{       	
        	for (Customer customer: customers){
                 accounts.addAll(customer.getAccounts());
        	}        		
        }
		return accounts;
	}
    
	//return list of a customer, 
	//parameters are customer number and functor action 
	@Override
	public List<Account> customerAccounts(int custNumber, IAction action) {
		List<Account> accounts = null;
        if(action != null){
        	return null;
        }
        else{
        	//find customer by number
        	return getCustomer(custNumber).getAccounts();       	    		
        }
	}
	@Override
	public void insertEntry(String accName, Entry entry) {
	   try{
		Customer customer = getCustomer(accName);
		 System.out.println("insert entry name"+accName);
		if(customer != null){
		   List<Account> accounts = customer.getAccounts();
		   System.out.println("insert entry size"+accounts.size());
		   if(!accounts.isEmpty()){
			   Account account = accounts.get(0);
			   account.addEntry(entry);
			 //notify view observers
				setChanged();
		        notifyObservers(customers);
		   }
		}
	   }
	   catch(Exception e){
		   System.out.println("Error "+e);
	   }
	}

}
