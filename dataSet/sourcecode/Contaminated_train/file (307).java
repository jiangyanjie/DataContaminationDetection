
package framework.model;

import java.util.*;

/**
 *  Account class
 */
public abstract class Account  implements IAccount {
    private int accountNumber;
    
    protected Customer customer;
   
    private List<Entry> entries = new ArrayList<Entry>();
   
    private double currentBalance;
    
    public double interest_rate;
   
    public Account(){
    	currentBalance =0; 
    }
    public Account(Customer customer) {
        this.customer = customer;
      
        Random r = new Random();
        this.accountNumber = r.nextInt();
    }

    @Override
    public double getCurrentBalance() {
        return currentBalance;
    }

    public void addEntry(Entry entry) {
        entries.add(entry);
        currentBalance += entry.getAmount();
    }

    //TODO
    public abstract String generateMonthlyReport();

    /**
     *
     */
    public void addInterest() {
        double newBalance = currentBalance + (getInterestRate() * currentBalance);
        currentBalance = newBalance;
    }

    /**
     *
     */
    public abstract double getInterestRate();

    
    public abstract String getType();

    /**
     *
     */
    public void setCustomer(Customer cust) {
        this.customer = cust;
    }

    public String getField(String key) {
        return null;
    }
    //return the account number
    @Override
    public int getAccountNumber(){
    	return accountNumber;
    }
   
    @Override
    public String toString() {
        return getType();
    }   
}
