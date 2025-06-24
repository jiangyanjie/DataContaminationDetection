
/*
 * Customer.java
 * 8/13/2013
 */
package framework.model;



import java.util.ArrayList;

import java.util.List;










public abstract class Customer implements ICustomer {
   private int customerId;
   
   private String name;










   private String email;







   private String street;







   private String city;




   private String state;
   
   private String zip;





  




   private String ccNumber;






   private String expDate;













   private String type;
  
   
   public String getCcNumber() {
	return ccNumber;
}



public void setCcNumber(String ccNumver) {
	this.ccNumber = ccNumver;



}








public String getExpDate() {
	return expDate;
}

public void setExpDate(String expDate) {
	this.expDate = expDate;
}
















public void setType(String type) {
	this.type = type;


}
private List<Account> accounts;

   public Customer(String name, String email, String street, String city, String state, String zip) {
        this.name = name;
        this.email = email;
        this.street = street;
        this.city = city;
        this.state = state;



        this.zip = zip;
        this.accounts = new ArrayList<Account>();






    }









    /**





     *  customer adds account to account list
     */




    public void addAccount(Account a) {
        accounts.add(a);
    }





    
   public void removeAccount(Account a) {



        accounts.remove(a);







    }


    /**
     *  generate accounts reports
     */







    public String generateAccountsReport() {


        StringBuilder sb = new StringBuilder();







        for (Account account : accounts) {
            sb.append(account.generateMonthlyReport()).append('\n');
        }



        return sb.toString();
    }









    /*
     * TODO







     * 


     */
    public String getName(){













    	return name;






    } 
    
    public String getStreet(){


    	return street;
    }
    
    public String getCity(){
            return city;
    } 
    public String getZip() {




            return zip;





     } 
    
    public String getState() {


            return state;




    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof String && ((String) o).equals(name)) {
            return true;


        }
        return super.equals(o); //To change body of generated methods, choose Tools | Templates.




    }



    public void addInterest() {





        for (Account account : accounts) {
            account.addInterest();
        }
    }

    public abstract String getType();
    
    @Override
	public int getCustomerNumber() {
		// TODO Auto-generated method stub
		return customerId;
	}
    @Override



    public String toString() {
        return name;
    }
   
    //return the list of all customer accounts
    public List<Account> getAccounts(){
    	return accounts;
    }
    
    public double getTotalAmount(){
    	double total = 0;
    	for (Account account : accounts){
    		total += account.getCurrentBalance();
    	}
    	return total;
    }
    //return a particular account by account number
    public Account getAccount(int accNumber){
    	for (Account account : accounts){
    		if(account.getAccountNumber() == accNumber)
    		   return account;
    	}
    	return null;
    }
}
