package Account;

import java.util.*;
//TODO: Update accounts to a HashTable, we can do lookups by email in O(N) time by matching the key to the account value.
public class AccountTable {
	private ArrayList<Account> accounts;
	private int accountIndex;
	
	public AccountTable(ArrayList<Account> m_Accounts)
	{
		accounts = new ArrayList<Account>();
		setAccountList(m_Accounts);
		accountIndex = accounts.size();
	}
	
	public AccountTable()
	{
		accounts = new ArrayList<Account>();
	}
	
	public ArrayList<Account> getAccounts()
	{
		return accounts;
	}
	
	private void setAccountList(ArrayList<Account> ac)
	{
		for(int i = 0;i<=ac.size()-1;i++)
		{
			accounts.add(ac.get(i));
		}
	}
	
	public int getAccountsIndex()
	{
		return accountIndex;
	}
	
	public void addAccount(Account a)
	{
		accounts.add(a);
	}
	
	public void removeAccount(Account a)
	{
		accounts.remove(a);
	}
	
	public boolean checkAccount(Account tmp)
	{
		boolean isUser=false;
		Account a = tmp;
		Account b = getAccount(a);
		if(b.equals(a))
		{
			isUser = true;
		}
		return isUser;
	}
	
	public boolean validateActiveAccount(String user, String pw)
	{
		boolean isActive = false;
		
		return isActive;
	}
	
	public Account getAccount(Account a)
	{
		Account retVal = null;
		for(Account b : accounts)
		{
			if(a.equals(b))
			{
				retVal = b;
			}
		}
		return retVal;
	}
	
	public Account getAccount(int i)
	{
		Account tmp = accounts.get(i);
		return tmp;
	}
	
	public Account getAccount(String em)
	{
		Account ac = null;
		for(int i=0;i<=accounts.size()-1;i++)
		{
			Account tmp = accounts.get(i);
			if(tmp.getEmail().matches(em))
			{
				ac = tmp;
			}
		}
		return ac;
	}
}
