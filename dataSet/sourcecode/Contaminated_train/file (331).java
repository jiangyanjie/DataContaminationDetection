package com.core.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.core.exception.AccountExisitException;

public class AccountManager {

	private String mAMName;
	private double mTotalAmount;
	private Map<String, Account> mAccountList;
	
	public AccountManager(String AMName){
		mAccountList = new HashMap<String, Account>();
		mAMName = AMName;
		mTotalAmount = 0;
	}
	
	public void addAccount(String accountName, int AccountType, double Amount) 
			throws AccountExisitException{
		
		if (mAccountList.containsKey(accountName))
			throw new AccountExisitException();
		
		mAccountList.put(accountName, new Account(accountName, AccountType, Amount));
		calculateTotalAmount();
	}
	
	public void removeAccount(String accountName){
		mAccountList.remove(accountName);
		calculateTotalAmount();
	}
	
	public Set<String> getAccountList(){
		return mAccountList.keySet();
	}
	
	public void SetAMName(String AMName){
		mAMName = AMName;
	}
	
	public String GetAMName(){
		return mAMName;
	}
	
	public double getTotalAmount(){
		return mTotalAmount;
	}
	
	public String AddTransaction(Transaction tran){
		String transID = "";
		
		return transID;
	}
	
	public void EditTransaction(String transID, Transaction tran){
		
	}
	
	public void RemoveTransaction(String transID){
		
	}
	
	private double calculateTotalAmount(){
		double dRet = 0;
		
		for (Account account : mAccountList.values()){
			dRet += account.getAmount();
		}

		mTotalAmount = dRet;
		return dRet;
	}
}
