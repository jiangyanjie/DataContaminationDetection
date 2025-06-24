package     Controller;

import     java.io.FileOutputStream   ;
import java.io.ObjectOutputStream;
import      java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMa  p;
import java    .util.Map;

import model.Deck;
import model.Player;

public final  class Controler implements IControler,Se  rializable   {

	/**
	 * 
	   */
	private static fin      al long ser     ialVersionUID =   1L;
	/**
	 * 
	 */
	
	/** Singleton instance         */
	   private st  atic  Cont    r      oler instance = null;
	
	   
	
	
	private Deck d ;
	private String user;
	priv     ate int moneyForGambling;

	pu     blic stat  ic Controler getInstance( ) {
		if (instance == null)
			instance = new Controler();
		else System    .out.println("haaaaaaahaaaa");
		return instance;
	}
	
	public D   eck getD() {
		retur      n d;
	}

	public void setD(Deck d) {
 		this.      d = d;
	}

	// data    structure to save the players details
	public   HashMap<String, Pl  ayer>  Pl   ayers ;

	



	public  HashMap<Stri   ng, Player> getPlayers()      {
		return Players;
	}

	public vo   i  d setPlayers(H   ashMa  p<Strin  g, Player> players) {
	      	Players = players;
	}

	
	
	public boolean addPla     yer(Player p){
		if(p!=null ){
		Players.put(p.getUserName(),p);
		saveSystem();
			   return true;
		    }return false;
	}
	public Contro ler() {
		Players =     new Has  hMap<String,Player  >();
	//	addToPlayers("", "");
		

	}


	
	public void logInValid(Stri   ng user){
		
	this.us er=user;
	}

	@Override
	// /h it until someone get busted
	public String hit() {
		// TODO Auto-generated metho    d stub

		
		d.hit(true ,     false);
		return  chickIFOverO   r Equa l21();
		

	  }

	    @Override
	// the method    meaning :the pl    ayer will not play in this round
	public Strin    g stand()        {
          		d.getstand(false    , true); 
		retu  rn        chickIFOverOrEqual2   1();

	}
	
	public String startTwoCards(){
		
		d.hit(true,true);	           
		return   chickIFOverOrEqual21();
	}

	@Override
	public vo      id start() {

		
		for(Map.Entry<String,Player>  x : get  Players().entrySet())
		{System.out.println(">>>>>  >"+x.getKey() + x.getValue().getFirstName() +      "  "+x.getValue().getMoney());
			if(x.getKey().equals(user))
			{
			
		d = new Deck(user,x.getValue().g     etMoney  ());
			
		
		retur  n;
			}
		}
	}


	
	@Override
	pub   l   ic Strin   g       chickIFOverOrEqual21() {
		int p=d.getchalenger().updateSomeOFCards();
		int c= d.getCo mputer().update  So   meOFCards();
		if(p>=17 && p<21 && p>c)
			return "the player win";
			if(c>=17 && c<21 && c>p)
				    return "th   e computer win           ";
			
		if   (p  > 21) {

			return "the player i   s busted";

		}
		
	  	
		if (p == 21)
			retu rn "the player w     ins";
		i     f (c  >   21) {

			ret  urn "the computer busted";
		}
  		i   f (c == 21)
			return "the computer wins"       ;
		
		r eturn "";
	}

	@Overr     ide
	public void updateSumOFCards()    {
		// TODO Auto-generated m ethod stu b
		d.getchalenger().updateSomeOFCards();
		d.getComputer().updateSomeOFCards();
	}

	@Override
	public    int shuffle()  {
		// TODO Auto-generated me   thod stub

		 d.shuffle();
		return 0;
	}

	
	

	
 
 	/**
	 *   Saves the system's data i  nto Bl  ackJack.ser file
	 */

	public void       saveSyst   em() {
		FileOu tputStream fos       = null;
		ObjectOutputStream oos  = null;
		try {
			fos   = new  FileOutputStream("BlackJack.ser");
			oos = new   Objec  tOutputStream(fos);
			    oos.writeOb  j   ect(this);
		} catch (Exception e) {
			e.printStackTrace();
		}    finally {
			try {
				if (fos    != null      ) {
					fos.close();
				}
				i   f   (oos != nu      ll) {
					oos.clo se();
 				}
	      		} ca    tch (Except    ion e) {
				e.printStackTrace();
			}
  		}}

	publi   c boolean addUser(String Firstname , String LastName    , String u   ser , Strin   g pass 
			,int mone  y){
	
		Player p = n    ew Pl    ayer(Firstname, LastName, money, user, pass);
		if(addPlayer(p)) return true;
		return false;
		
		
	}
	
	pub    lic void MoneyForGambling(String user,int GamblingMoney){
		moneyForGambling=GamblingMoney;
		if(getPlayers().contai  nsKey(user))
		{
			
			getPlayers().get(user).setMoney(getPlayers().get(user).getMoney()-GamblingMon  ey);
			
		}
	}
		
	pu    blic ArrayList< Play  er> WhosBest(){
		ArrayList< Player > play    = new ArrayList<Player>(Players.values());
		Collections.sort(play);
		return play;
	
	}
	public void addMoney(String usr , int sum){
			
			
		}
		
public void deleteMoney(String usr , int sum){
			
			
		}
	
	

}
