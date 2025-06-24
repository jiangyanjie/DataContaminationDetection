package Game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

class EvolveData {
	private int id;
	private int level;
	private String img;
	private int req;
	
	public EvolveData(int id, int level, String img, int req)
	{
		this.id = id;
		this.level = level;
		this.img = img;
		this.req = req;
	}
}

public class Damagochi {
	static public int hunger = 100;
	static public int boredom = 100;
	static public int thirst = 100;
	static public int health = 100;
	static public int ev_level = 1;
	static public int ev_exp = 0;
	
	static public int hunger_time = 15;
	static public int hunger_delay = 150;
	static public int boredom_time = 10;
	static public int boredom_delay = 100;
	static public int thirst_time = 5;
	static public int thirst_delay = 50;
	static public int health_time = 10;
	static public int health_delay = 200;
	
	static public int money = 1000;
	
	static public int festival_delay = 100;
	static public int festival_time = 100;
	static public int score = 10;
	
	static public int status = 1; 
	// 1. normal
	// 2. bored
	// 3. hunger
	// 4. sick
	// ---
	// 0. sleep
	// else= ded;
	
	public boolean canAfford(int amount){
		return (money <= amount);
	}
	
	public void feedFood(int index)
	{
		FoodObject food = Food.getFood(index);
		
		if (!canAfford(food.getPrice()))
			return;
		
		hunger += food.getAmount();
		// money -= food.getMoney();
		// "Purchased" + food.getName();
		
		// TODO Add Food on the screen.
		// TODO adding hunger is placeholder
	}
	
	public void feedDrink(int index)
	{
		DrinkObject drink = Drink.getDrink(index);
		
		if (!canAfford(drink.getPrice()))
			return;
		
		thirst += drink.getAmount();
		// money -= food.getMoney();
		// "Purchased" + food.getName();
		
		// TODO Add Food on the screen.
		// TODO adding hunger is placeholder
	}
	
	public void evolveDama()
	{
	}
	
	static int dmgStack = 0;
	public static void damaThink(GameContainer c, StateBasedGame s, int d)
	{
		
		if (hunger_time < GameScreen.time)
		{
			hunger -= 1;
			if (hunger < 0)
				hunger = 0;
			
			hunger_time = GameScreen.time + hunger_delay;
		}

		if (boredom_time < GameScreen.time)
		{
			boredom -= 1;
			if (boredom < 0)
				boredom = 0;
			
			boredom_time = GameScreen.time + boredom_delay;
		}
		
		if (thirst_time < GameScreen.time)
		{
			thirst -= 1;
			if (thirst < 0)
				thirst = 0;
			
			thirst_time = GameScreen.time + thirst_delay;
		}
		
		dmgStack = 0;
		status = 1;
		
		if (boredom == 0)
			status = 2;
			//dmgStack += 2;
			// TODO add some legit effect for boredom.
		
		if (thirst == 0)
		{
			dmgStack += 2;
			status = 3;
		}
		
		if (hunger == 0)
		{
			dmgStack += 2;
			status = 3;
		}
		
		if (health_time < GameScreen.time)
		{
			health -= dmgStack;
			
			if (health < 0)
			{
				s.enterState(-1);
				health = 0;
			}

			if (health <= 50)
			{
				status = 4; // sick
			}
			health_time = GameScreen.time + health_delay;
		}
		
		// if game time is near night
		// status = 5;

	}
	
}
