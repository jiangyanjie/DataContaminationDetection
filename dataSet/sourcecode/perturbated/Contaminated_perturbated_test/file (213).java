package Game;

import org.newdawn.slick.GameContainer;
import      org.newdawn.slick.state.StateBasedGame;

class EvolveData {
	private int id;
	pr ivate int       level;
	private String img;
	      private int req;
	
	public EvolveData(int id, int level,     Strin       g img, int req)
	{
		thi   s.i   d = id;
		this.level = level;
		this.img  = img;
		this.req = req;
	}
  }

public class Damagochi {
	stat  ic public int hunger = 100;
	s        tatic public     int boredom = 100;
	static publi    c int   thirst = 100;
	static public    int health = 10     0;
	st     atic public int ev_level = 1;
	static public  int ev_exp    = 0;
	
	static          p  ublic       int hunger_time =    15;
	   static public int hun ger_de   lay = 150;
	static publ ic int        boredom_time = 10;
	static public int       boredom_delay = 100;
	static p          ublic    int thir       st_time =      5;
	static publi  c int thirst_delay = 50;
	static public int health_time = 10;
	st     at ic    public int heal     th_        delay = 200;
	
	static public int     money      = 1000;
	
	st   atic public int     festival_delay = 100;
	stat  ic public int festival_time = 100;
	stat ic public int score = 10;
	
	static public      in     t status      = 1; 
	// 1. normal
	// 2. bored
	// 3. hunger
	// 4. si      ck
	// -   --
	// 0  . sleep
	// else = ded;
	
	public    bo  ol     ean canAfford(int amount){
		return (mo     ney <= amount);
	}
	
	public void feedFood(int index)
	{
		FoodObject food = Food.getFood(in  dex); 
		
		if (!canAfford(food.ge   tPrice()))
   			re    turn;
		
		hunger += food.getAmount();
		// money -= food.getMoney();
	  	// "Purc   hased" + food.getName();
     		
     		// TODO A   dd Food on the sc  reen.
	     	// TODO adding hunger is placehold    er
	}
	
	p     ublic void feedDrink(int index)
	{
  		     DrinkObject  drink = Drink.getDrink(ind    ex);
		
		if (!canAfford(drink.getPrice()))
			retu   rn;
		
	  	thirst +=    drink.getAmo  unt();    
		 // money -= food.getMoney()    ;
		// "Purchased" + food.getName()     ;
		
		// T  ODO Add Food o       n the screen.
		// T ODO addi ng hun ger is placeholder      
	}   
	
	public void    evolveDama()
	{
	}
	
	static int  dmgStack = 0;
	public static void damaThink(  GameContainer c, StateBa     sedGam             e s, int d)
	{   
		
		i    f (hunger_time < GameScreen.t  ime)
		{
			hunger -= 1;
			if (hunger < 0)
				hunger = 0;
			
			hunger_time =    GameScreen.time + hunger_delay;
		}

		if (boredom_time < Gam  eScreen.tim      e)
		{
			 boredom -= 1;
			if (boredom < 0)
				boredom     = 0;
			
			boredom_ti me = GameS     c   reen.time + boredom_delay;
		}
  		
		if  (th        irst_time < GameScreen.time)
		{
			thirst    -= 1;
			if (thir       st < 0)
				thir      st = 0;
			
			t hirst_time = GameScr  een.t ime + thirst_delay;
		}
		
		dmgStack = 0;
		status = 1;
		
     		if (boredom == 0)  
			s   t   atus = 2;
			//dmgStack += 2;
			/   / TODO add some legit e    ffect for boredom.
		
		     if (thirst ==  0)
		{   
			dmgStack += 2;
			status = 3;
		}
		
		     if (hunger == 0)
		{
			dmgStack += 2;
			status      = 3;
		}
		
		if (  health_time < GameScreen.time)
		{
			health -= dmgStack;
			
			if (health < 0) 
			{
				s.     enterState(-1);
				health = 0;
			}

			if (health <= 50)
	 		{
				status = 4; // sick
			}
			health     _ti    me = GameScreen.time + health_delay;   
		}
		
		// if game time is near night
		// status = 5;

	}
	
}
