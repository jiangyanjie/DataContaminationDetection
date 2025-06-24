package   Account;

import     java.util.*;
//TODO: Update accounts         to a HashTable,      we can d o lookups by  email in O(N) time b y matchin   g th e key to the account valu       e.
public cla   ss AccountTable      {
	private ArrayList<Account> accounts;
	pr  ivate int accountIndex;
	
	pu  blic AccountTable(ArrayList<Account> m_Accounts)
	{
		accounts = new ArrayList<Account>();
		setAccou      ntList(m_Accounts);
		accountIndex = accounts.size();
	}
	
	public    AccountTable()
	{
		accou    nts = new ArrayList<Account>();
	}    
	
	public ArrayList<    Account> getAccounts()
	{  
		return accounts;
	}
	
	priv  ate void setAccountList(ArrayList<Account> ac)
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
	
	public    boolean che ckAccount(Account     tmp)
	{
		boolean isUser=false;
		Account a = tmp;
		Acco     unt b = g  etAccount(a);
		i     f(b.equals(a))
		{
			isUser = true;
		}
		return isUser;
	}
	
	public boolean validateActiveAccount(Strin  g user, S    tring pw)
	{
		b   ool       ean isActiv      e = false;
		
		return isActive;
	}
	
	publ ic   Account getAccount(Account a)
	{
		Account retV al = null;
		for(Account b : accounts)
		{
			if(a.equals(b))
			{
				retVal = b;
			}
		}    
		return retVal;
	}
     	
	public  Ac  count getAccount(int i)
	{
		Acco    unt tmp = accounts.get(i     );
		return tmp;
	}
	
       	public Account getAccount   (String em)
	{
		Account ac = null;
		for(int i=0;i<=acc  ounts.size()-1;i++)
		{
			Account tm p = accounts.get(i);
			if(tmp.getEmail().matches(em))
			{
				ac = tmp;
			}
		}
		return ac;
	}
}
