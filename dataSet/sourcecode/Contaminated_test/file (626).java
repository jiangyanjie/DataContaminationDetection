package co.whitejack.api;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author gabizou
 * 
 */
public class DeckArrayManager extends Deck {

	public DeckArrayManager(int n) {
		super(n);
		// TODO Auto-generated constructor stub
	}

	private static DeckArrayManager instance;
	private boolean cardIsPlayable = true;
	private Card[] card;
	private ArrayList<Card> stack = new ArrayList<Card>();
	private ArrayList<Card> heap = new ArrayList<Card>();
	private ArrayList<Card> recycleBin = new ArrayList<Card>();
	private static Logger log = Logger.getLogger("WhiteJackAPI");

	/**
	 * This method is accessed from outside the class by deck =
	 * DeckArrayManager.getDeckArrayManager();
	 */

	public int getCount() {
		return count;
	}

	public void setCount(int c) {
		count = c;
	}

	public boolean needShuffle() {
		if (count == 50)
			return true;
		else
			return false;
	}

	public static DeckArrayManager getDeckArrayManager() {
		if (instance == null) {
			instance = new DeckArrayManager(1);
			log.debug("[DeckArrayManager] inside getter() method");
		}
		return instance;
	}

	/**
	 * Push a card into the array <list>
	 * 
	 * @param list
	 * @param card
	 */
	public void push(ArrayList<Card> list, int card) {
		try {
			list.add(this.card[card]);
		} catch (Exception ex) {
			// if arrayList becomes empty handle exception here...
		}
	}

	public boolean isStackEmpty() // return true if needShuffle == true;
	{
		return false;
	}

	/**
	 * Pull a card from the array <list>
	 * 
	 * @param list
	 * @param card
	 */
	public void pull(ArrayList<Card> list, int card) {
		try {
			list.remove(this.card[card]);
		} finally {
		}
	}

	public ArrayList<Card> getRecycleBin() {
		return recycleBin;
	}

	public void setRecycleBin(ArrayList<Card> recycleBin) {
		this.recycleBin = recycleBin;
	}

	public ArrayList<Card> getStack() {
		return stack;
	}

	public void setStack(ArrayList<Card> stack) {
		this.stack = stack;
	}

	public ArrayList<Card> getHeap() {
		return heap;
	}

	public void setHeap(ArrayList<Card> heap) {
		this.heap = heap;
	}

	/**
	 * Getter for Card
	 */
	public void getCard() {
	}

	/**
	 * Setter for Card
	 */
	public void setCard() {
	}

	public boolean isCardIsPlayable() {
		return cardIsPlayable;
	}

	public void setCardIsPlayable(boolean cardIsPlayable) {
		this.cardIsPlayable = cardIsPlayable;
	}
}