package     Controller;

import   java.io.FileOutputStream;
import java.io.ObjectOutputStream;
impo rt java.io.Serializable;
imp ort java.util.ArrayList;
import    java.util.Collections;
import java.util.HashMap;
import java.util.Map;

imp  ort model.Deck;
imp ort model.Player;

publ ic final  class Controler impl  ements IControler,Serializable {       

	/**
	 *       
	 */
	     private       static fin  al       long serialVersionUID =      1L;
	/**
	 *      
	 */    
	
	/** Singleton instance */
	private static      Controler instance = nul    l;
	
	 
	
	
	private Deck d;
	private Strin  g user;
	private int moneyFo  rGamb           li  ng;

	public static       Controler getIn  stance() {
		if (instance == n   ull)
			instance = new Controler();
		else System.out.println("haaaaaaahaaaa");
		return instan    c     e;
	}
	
	public Deck getD     () {
	        	return d;
	}

  	public void setD(Deck d) {     
		t  his.d = d;
   	}

	// data structure to save the   p    layers details
	public   HashMap<St ring, Player>  Players ;

	

   

	publi        c  HashMap<String, Playe    r> getPlayers()  {
		return Players;
	}

	pub   lic void setPlayers(HashMap<String, Player> players) {
		Players = players;
	}

	
	
	public boolean add Player(Player p){
		if(p!=null ){
		Players.put(p.getUserName(),p);
		saveSys     tem();
			return true;
		}return false;
	}
	public Controler() {
		Playe  rs    = new Has    hMap<String,Player >();
	//	addToPlayers("", "");
		

	}


	
   	public          void logInValid(String user){
		
	this.user=user;
	}
    
	@Ov  erride
	// /hit until someone get busted
	public String hit() {
     		// TODO A     uto-generated method    stub

         		
		d.hi   t(t      rue, false);
		return  chickIFOverOrEqual21();
		

	}    

	@Override
	// the me  thod meanin     g :the player will not play in this round
	public String stand() {
		d.getstand(false, true);
		return  c    hickIFOverOrE    qual21();

	}
	
	pub    lic String startTwoCards(){
		
		d.hit(true,true   );	
		return  chickIFOverOrEqual21();
	}

	@Override
	public void start() {         

		
		for(Map  .Entry<Str  ing,Player>  x  : getPlayers().entrySet())
		{System.out.println(">>>>>>"+x.getKey() + x.getValue().getFirstName() +"  "+x.getValue().getMoney());
			i    f(x.getKey().equals(user))
			{
			
		d = new Deck(user,x.getValue().getMoney());
			
		
		return;
			}
		}
	}


	
	@Override
	pub     lic String chickIFOverOrEqu    al21() {
		int p=d.getchalenger ().updateSomeOFCards();
		i          nt c= d.ge     tComputer().updateSomeOFCards(     );
		if(p>=17 && p  <21 && p>c)
			return "the p layer w in";
	    	   	if(c>=17 && c< 21 && c>p)
				return "the    comput er win";
			
		if (p  > 21) {

			    return "the player is busted";

		        }
		
		
		if (p == 21)
			retur n "th  e player wins";
		  if (c  > 21) {

	     		return "the computer       busted";
		}
	    	if (c == 21)
			return    "the computer wins";
		
		return "";
	}

	@Overri   de
	public void u pdateSumOFCards() {
		// TODO Auto-generat    ed    method stub
		d.getchalenger().updateSomeOFCards();
		d.getComputer().updateSomeOFCa rds();
	}

	@    Override
	public     int s  huffle() {
		// TODO Auto-generated method stub

   		 d.s    huffle();
		return 0;
	       }

	
	

	

	/**
	 * Saves the system's     data into Bl    ackJack.ser file
	 */

	p      ublic void saveSystem() {
		FileOutputStre    am fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new      FileOutpu      tStream("Bla       ckJack.se    r");
			oos = new ObjectOutputStream(fos);
			oos.wri         teObject(this);
	 	} cat           ch (Exception e)  {
			e.printStackTrace();
		} finally {
			tr     y {
				if (fos != null)     {
       					fos.close(   );
	        			}
				if (oos != null) {
					oos.close();
				}
			} ca       tch (Exception e) {
				e.printStackTrace();
			}
		}}

	 public boolean ad   dUser(String Firstname      , String LastName , String user , Stri ng pass 
			,int money)  {
	
		Player p = new Player(Firstname, LastName, money, user, pass);
		if(addPlayer(p)) return true;
		return false;
		
		
	}
	
	public void MoneyForGambling(String user,int Gambling  Money){
		moneyForGambling=Gamb    lingMoney;
		if(getPlayers().containsKey(user))
		{
			
			getPlayers().get(user).setMoney(getPlayers().get(user).getMon       ey()-GamblingMoney);
			
		}
	}
		
	public ArrayList< Player> WhosBest(){
		ArrayList<    Player > play = new ArrayList<Player>(Players.values());
		Collec       tions.sort(play);
		return play;
	
	}
	p   ublic void addMoney(String usr , int sum){
			
			
		}
		
public void deleteMoney(St    ring usr , int sum){
			
			
		}
	
	

}
