/* 
 * Framework controller class
 * */
package creditcard.controller;
import framework.view.*;
import framework.helper.FwFactory;
import framework.helper.IAction;
import framework.model.*;
import framework.controller.*;

import java.util.ArrayList;
import java.util.List;

public class CreditCardController extends FwController {
	  //facade data model object
    public CreditCardController(ViewPane view) {
    	super(view);
    }
    
    public CreditCardController(ViewPane view, FwFactory creator) {
    	super(view, creator);
    } 

    /**
     *  action to Personal customer to the data model
     */
    public void addPersonalAccount(String name, String street, String city,  String state,
    		                        String zip, String email, String birthdate, String accType) 
    {
        Customer customer =  creator.createPersonalCustomer(name.trim(), street, city, state, zip, email, birthdate);
    	customer.addAccount(creator.createAccount(accType));
        addCustomer(customer);
    }
    
    /**
     *  action to Comapny customer to the data model
     */
    public void addCompanyAccount(String name, String street, String city, String state,
    		                       String zip, String email, int numOfEmps,String accType) 
    {
    	Customer  customer = creator.createCompanyCustomer(name.trim(), street, city, state, zip, email, numOfEmps);
     	customer.addAccount(creator.createAccount(accType));    	
    	super.addCustomer(customer);
    }
 
    /**
     *  action to CC customer to the data model
     */
    public void addCCAccount(String name, String street, String city,  String state,
    		                        String zip, String email, String ccNUmber,String Expdate, String accType) 
    {
        Customer customer =  creator.createCCardCustomer(name.trim(), street, city, state, zip, email, ccNUmber,Expdate,accType);
    	customer.addAccount(creator.createAccount(accType));
        addCustomer(customer);
    }
    
    /* 
     * add entry to Saving account
     *  */
    public void addSavingAccount(int custNumber,Account account) {
    	//super.addCustomerAccount(custNumber, account);
    }
    
//    public ArrayList<Customer> getCustomer()
//    {
//    	return super.getCustomer();
//    }
    
    /* 
     * add deposit to customer account
     *  */
    public void makeDepositTransacation(String accNum,double amount) {
       String accountName = (!accNum.isEmpty())? accNum : view.getSelectedCustomer().toString();
       super.makeTransacation(accountName.trim(), creator.createEntry(amount));
    }
    
    /* 
     * add withdrawal to customer account
     *  */
    public void makeWithdrawalTransacation(String accNum,double amount) {   	 
    	String accountName = (!accNum.isEmpty())? accNum : view.getSelectedCustomer().toString() ;
    	double wAmount = -1 * amount ; //withdrawal is  
        super.makeTransacation(accountName.trim(), creator.createEntry(wAmount));
    }
    
}
