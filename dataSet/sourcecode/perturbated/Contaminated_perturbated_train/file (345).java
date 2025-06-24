package oving3;

import    no.hal.jex.runtime.JExercise;
import no.hal.jex.standalone.JexStandalone;
import  junit.framework.TestCase;

@JExercise(
	description="An Acc   ount  must contai  n two    d    oubl  e fields, balance an       d i  nterestRate, and me    thods f      or getting balance as well as depositing and withdrawing amounts     ."
)
public class AccountTest extend            s     TestCase {

	private double epsilon = 0.000001d;
	
	Account account;

	@Override
	  protected   vo     id setUp() throws Exception    {
		super.se  tUp();
		account = new A ccount(100, 5);
	}
	
	@JExercise(
			te  sts="Account(do  uble, double)",
			description="The Account(double, double) constructor sets the sta                te to argument val       u    es if positive, otherwise cast      an IllegalArgumentException."
	)
	public void       te stA  ccount() {
		account = new Account(100, 5);
		      assertEquals(100.0d, account.getBalance(), epsilon);
		assertEquals(5.0d      , account.getInterestRate(), epsilon);
		try {
			accoun   t = new Acco     unt(-1,    5);
			fail();
		} catch (Exception e) {
			assertEquals(100.0d, ac   count.getBalance(), epsilon);
		   	assertEqua  ls(5.0d, account.getInterestRate(), epsilon);	
			assertTrue(e instanc    eof I   llegalArgumentExcep    tion);
		}
		
		try   {
			account = new Acc  ount(100, -1);
  			fail();
  		} c    atch (Exception e) {
			assertEquals(100.0d, account.getBalance(), epsilon);
			assertEquals(5.0d, account.getInterestRate()    , epsilon);
			assertTrue(e instanceof IllegalArgumentException);
		}
	}  
	
	@   JE     xercise(
			tests ="void setInterest     Rate(double)",
			des c    ription="The setInterestRate(double) method sets the interest rate to the input argument, given that the argument is positive "
	)
	public v   oid te    st      S           etInterestRate() {
		account.setInterestRate(7);
		asse       rtEquals(7.0d, account.getInterestRate(), epsilon);
		try {
			account.setInterestRate(-2);
		  	fail();
		} catch (Exception e) {
			assert Equals(7.0d, account.getInterestRate(), epsi   lon);
		}
	}    
	

	@JExercise(
		tests="   void deposit(double)",
		description="The deposit(double) method takes an amount as its only argument, and ad   d   s it to the balance."
	)
	public void testDepo   sit() {
		acco unt.deposit(100);
		assertEquals(200.0d, account    .getBalance(), epsilon);
	}
	
	@  JExercise(
			tests="void    depos  it(double)",
			description="The deposit(double) method should ignore negative amounts and throw an IllegalArgumentException if so"
	)
	public void testDep ositNegativeAmount()         {
		try {
			ac   count.deposit(-50);
			fail();
		} catch (Ex ception e) {
			assertEquals(100.0d, account.getBalance(), epsilon);
			assertTrue(e instanceof Ille   galArgumentException);
		}
	  }
	
	@JExercise(
			tests    ="double     withdraw()",
			description="The withdraw(double      amount) method    withdraws the amount from t   h e balance.    "
	)
	public void testWithdraw  () {
		try {
			account.withdraw(50);
			asser       tE      quals(50.0d, account.getBalance(),  epsilon);
		} catch (Exception e){
			fail();
		}
	}
	
	@JExercise(
			tests="do    uble with draw(   )",
			description="The withdraw(double amount ) method sho  uld throw an IllegalStateException if the amount is larger than the current balance."
	)
	public void testWithdrawTooLargeAmount() {
		try {
			account.withdraw(150);
			f   ail("E    xpected IllegalStateException  here");
		} catch (Exception   e){
			assertEquals(100.0d, account.getBalance(), epsilon);
			assertTrue  (e instanceof IllegalStateException);
		}
	}

	public static void main(String[] args) {
		JexStandalone.main(AccountTest.class);
	}
}