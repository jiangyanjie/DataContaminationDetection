/**
 * 
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;

import Gui.MainFrame;
/**
 * @author chalenger
 *
 */
public class Deck implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//array that started with 52 cards 
	private  ArrayList<Card>  cards;
	//the player
	private  Player chalenger;
//the dealer
	private Player computer;
	
	int numberOfCards;
	private int mony;
	boolean moreThan17;
	public Random rand ;
	//hitFlag=true ;: the player will play
		public boolean hitFlag1;
		//standFlag=false ;: the computer will play
		public boolean hitFlag2;
	//standFlag=false ;: the player will play
	public boolean standFlag1;
	//standFlag=false ;: the computer will play
	public boolean standFlag2;
	private String userN;
	public Deck(String user, int mony) {
		super();
		this.cards = new ArrayList<Card>(52);
		moreThan17=false;
		userN=user;
		standFlag1=false;
		standFlag2=false;
		
		this.mony=mony;
		// make the table -(52 cards)
		StartGame();
	}
	
	public Player getchalenger() {
		return chalenger;
	}
	public void setchalenger(Player chalenger) {
		this.chalenger = chalenger;
	}
	public Player getComputer() {
		return computer;
	}
	public void setComputer(Player computer) {
		this.computer = computer;
	}
	
	public void setNumberOfCards(int numberOfCards) {
		this.numberOfCards = numberOfCards;
	}
	
	
	
	
	
	
	public void shuffle(){
		
		rand=new Random();
		
		for (int i = 0; i < cards.size(); i++)
		{
			//Randomly choose index j from the remainder of the array
			int randomNumber = rand.nextInt(cards.size()-i); //Fischer Yates shuffle
			int j = i + randomNumber;
			
			//Swap array[i] with array[j] (i being the current position in the array, j being the random number)
			Card temp = cards.get(i);
			cards.set(i, cards.get(j)) ;
			cards.set(j, temp);
		}
		
		
	}
	public ArrayList<Card> getCards() {
		return cards;
	}
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
	
	//starting the game
	public void StartGame(){
		

		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//		//make 52 cards
//		make52Cards();
//		//making two players
		java.net.URL url2 = MainFrame.class.getResource("/pictures/2s.jpg");
		ImageIcon card49 = new ImageIcon(url2);
	    Card twos = new Card(card49, 2,"spade", "Two"); 
	    java.net.URL url3 = MainFrame.class.getResource("/pictures/3s.jpg");
	    ImageIcon card45 = new ImageIcon(url3);
	    Card threes = new Card(card45, 3,"spade", "Three");
	    java.net.URL url4 = MainFrame.class.getResource("/pictures/4s.jpg");
	    ImageIcon card1 = new ImageIcon(url4);
	    Card fours = new Card(card1, 4, "spade","Four");  
	    java.net.URL url5 = MainFrame.class.getResource("/pictures/5s.jpg");
	    ImageIcon card13 = new ImageIcon(url5);
	    Card fives = new Card(card13, 5, "spade", "Five");
	    java.net.URL url6 = MainFrame.class.getResource("/pictures/6s.jpg");
	    ImageIcon card27 = new ImageIcon(url6);
	    Card sixs = new Card(card27, 6, "spade", "Six");
	    java.net.URL url7 = MainFrame.class.getResource("/pictures/7s.jpg");
	    ImageIcon card9 = new ImageIcon(url7);
	    Card sevens = new Card(card9, 7, "spade" , "Seven");
	    java.net.URL url8 = MainFrame.class.getResource("/pictures/8s.jpg");
	    ImageIcon card17 = new ImageIcon(url8);
	    Card eights = new Card(card17, 8, "spade", "Eight");
	    java.net.URL url9 = MainFrame.class.getResource("/pictures/9s.jpg");
	    ImageIcon card40 = new ImageIcon(url9);
	    Card nines = new Card(card40, 9,"spade", "Nine");
	    java.net.URL url10 = MainFrame.class.getResource("/pictures/10s.jpg");
	    ImageIcon card50 = new ImageIcon(url10);
	    Card tens = new Card(card50, 10,"spade", "Ten");
	    java.net.URL urljacks = MainFrame.class.getResource("/pictures/jacks.jpg");
	    ImageIcon card26 = new ImageIcon(urljacks);
	    Card jacks = new Card(card26, 11, "spade", "Jack");
	    java.net.URL urlqueens = MainFrame.class.getResource("/pictures/queens.jpg");
	    ImageIcon card33 = new ImageIcon(urlqueens);
	    Card queens = new Card(card33, 12,"spade", "Queen");
	    java.net.URL urlkings = MainFrame.class.getResource("/pictures/kings.jpg");
	    ImageIcon card18 = new ImageIcon(urlkings);
	    Card kings = new Card(card18, 13, "spade", "King");
	    java.net.URL urlaces = MainFrame.class.getResource("/pictures/aces.jpg");
	    ImageIcon card15 = new ImageIcon(urlaces);
	    Card aces = new Card(card15, 1, "spade", "Ace");
	    java.net.URL url2h = MainFrame.class.getResource("/pictures/2h.jpg");
	    ImageIcon card39 = new ImageIcon(url2h);
	    Card twoh = new Card(card39, 2,"heart", "Two");
	    java.net.URL url3h = MainFrame.class.getResource("/pictures/3h.jpg");	    
	    ImageIcon card2 = new ImageIcon(url3h);
	    Card threeh = new Card(card2, 3, "heart","Three");
	    java.net.URL url4h = MainFrame.class.getResource("/pictures/4h.jpg");
	    ImageIcon card8 = new ImageIcon(url4h);
	    Card fourh = new Card(card8, 4, "heart", "Four");
	    java.net.URL url5h = MainFrame.class.getResource("/pictures/5h.jpg");
	    ImageIcon card51 = new ImageIcon(url5h);
	    Card fiveh = new Card(card51, 5,"heart", "Five");
	    java.net.URL url6h = MainFrame.class.getResource("/pictures/6h.jpg");
	    ImageIcon card24 = new ImageIcon(url6h);
	    Card sixh = new Card(card24, 6,"heart", "Six");
	    java.net.URL url7h = MainFrame.class.getResource("/pictures/7h.jpg");
	    ImageIcon card34 = new ImageIcon(url7h);
	    Card sevenh = new Card(card34, 7,"heart", "Seven");
	    java.net.URL url8h = MainFrame.class.getResource("/pictures/8h.jpg");
	    ImageIcon card35 = new ImageIcon(url8h);
	    Card eighth = new Card(card35, 8,"heart", "Eight");
	    java.net.URL url9h = MainFrame.class.getResource("/pictures/9h.jpg");
	    ImageIcon card4 = new ImageIcon(url9h);
	    Card nineh = new Card(card4, 9, "heart","Nine");
	    java.net.URL url10h = MainFrame.class.getResource("/pictures/10h.jpg");
	    ImageIcon card7 = new ImageIcon(url10h);
	    Card tenh = new Card(card7, 10,"heart", "Ten");
	    java.net.URL urljackh = MainFrame.class.getResource("/pictures/jackh.jpg");
	    ImageIcon card3 = new ImageIcon(urljackh);
	    Card jackh = new Card(card3, 11, "heart","Jack");
	    java.net.URL urlqueenh = MainFrame.class.getResource("/pictures/queenh.jpg");
	    ImageIcon card25 = new ImageIcon(urlqueenh);
	    Card queenh = new Card(card25, 12,"heart", "Queen");
	    java.net.URL urlkingh = MainFrame.class.getResource("/pictures/kingh.jpg");
	    ImageIcon card36 = new ImageIcon(urlkingh);
	    Card kingh = new Card(card36, 13,"heart", "King");
	    java.net.URL urlaceh = MainFrame.class.getResource("/pictures/aceh.jpg");
	    ImageIcon card10 = new ImageIcon(urlaceh);
	    Card aceh = new Card(card10, 1, "heart", "Ace");

	    java.net.URL url2d = MainFrame.class.getResource("/pictures/2d.jpg");
	    ImageIcon card31 = new ImageIcon(url2d);
	    Card twod = new Card(card31, 2,"diamond", "Two");
	    java.net.URL url3d = MainFrame.class.getResource("/pictures/3d.jpg");
	    ImageIcon card30 = new ImageIcon(url3d);
	    Card threed = new Card(card30, 3,"diamond", "Three");
	    java.net.URL url4d = MainFrame.class.getResource("/pictures/4d.jpg");
	    ImageIcon card32 = new ImageIcon(url4d);
	    Card fourd = new Card(card32, 4,"diamond", "Four");
	    java.net.URL url5d = MainFrame.class.getResource("/pictures/5d.jpg");
	    ImageIcon card48 = new ImageIcon(url5d);
	    Card fived = new Card(card48, 5,"diamond", "Five");
	    java.net.URL url6d = MainFrame.class.getResource("/pictures/6d.jpg");
	    ImageIcon card5 = new ImageIcon(url6d);
	    Card sixd = new Card(card5, 6, "diamond", "Six");
	    java.net.URL url7d = MainFrame.class.getResource("/pictures/7d.jpg");
	    ImageIcon card41 = new ImageIcon(url7d);
	    Card sevend = new Card(card41, 7,"diamond", "Seven ");
	    java.net.URL url8d = MainFrame.class.getResource("/pictures/8d.jpg");
	    ImageIcon card14 = new ImageIcon(url8d);
	    Card eightd = new Card(card14, 8, "diamond", "Eight");
	    java.net.URL url9d = MainFrame.class.getResource("/pictures/9d.jpg");
	    ImageIcon card16 = new ImageIcon(url9d);
	    Card nined = new Card(card16, 9, "diamond", "Nine");
	    java.net.URL url10d = MainFrame.class.getResource("/pictures/10d.jpg");
	    ImageIcon card12 = new ImageIcon(url10d);
	    Card tend = new Card(card12, 10, "diamond", "Ten");    
	    java.net.URL urljackd = MainFrame.class.getResource("/pictures/jackd.jpg");
	    ImageIcon card11 = new ImageIcon(urljackd);
	    Card jackd = new Card(card11, 11, "diamond", "Jack");
	    java.net.URL urlqueend = MainFrame.class.getResource("/pictures/queend.jpg");
	    ImageIcon card6 = new ImageIcon(urlqueend);
	    Card queend = new Card(card6, 12, "diamond", "Queen");
	    java.net.URL urlkingd = MainFrame.class.getResource("/pictures/kingd.jpg");
	    ImageIcon card47 = new ImageIcon(urlkingd);
	    Card kingd = new Card(card47, 13,"diamond", "King");
	    java.net.URL urlaced = MainFrame.class.getResource("/pictures/aced.jpg");
	    ImageIcon card20 = new ImageIcon(urlaced);
	    Card aced = new Card(card20, 1,"diamond", "Ace");
	    java.net.URL url2c = MainFrame.class.getResource("/pictures/2c.jpg");
	    ImageIcon card28 = new ImageIcon(url2c);
	    Card twoc = new Card(card28, 2,"club", "Two");
	    java.net.URL url3c = MainFrame.class.getResource("/pictures/3c.jpg");
	    ImageIcon card38 = new ImageIcon(url3c);
	    Card threec = new Card(card38, 3,"club", "Three");
	    java.net.URL url4c = MainFrame.class.getResource("/pictures/4c.jpg");
	    ImageIcon card19 = new ImageIcon(url4c);
	    Card fourc = new Card(card19, 4,"club", "Four");
	    java.net.URL url5c = MainFrame.class.getResource("/pictures/5c.jpg");
	    ImageIcon card21 = new ImageIcon(url5c);
	    Card fivec = new Card(card21, 5,"club", "Five");
	    java.net.URL url6c = MainFrame.class.getResource("/pictures/6c.jpg");
	    ImageIcon card43 = new ImageIcon(url6c);
	    Card sixc = new Card(card43, 6,"club", "Six");
	    java.net.URL url7c = MainFrame.class.getResource("/pictures/7c.jpg");
	    ImageIcon card42 = new ImageIcon(url7c);
	    Card sevenc = new Card(card42, 7,"club", "Seven");
	    java.net.URL url8c = MainFrame.class.getResource("/pictures/8c.jpg");
	    ImageIcon card22 = new ImageIcon(url8c);
	    Card eightc = new Card(card22, 8,"club", "Eight");
	    java.net.URL url9c = MainFrame.class.getResource("/pictures/9c.jpg");
	    ImageIcon card23 = new ImageIcon(url9c);
	    Card ninec = new Card(card23, 9,"club", "Nine");
	    java.net.URL url10c = MainFrame.class.getResource("/pictures/10c.jpg");
	    ImageIcon card44 = new ImageIcon(url10c);
	    Card tenc = new Card(card44, 10,"club", "Ten");   
	    java.net.URL urljackc = MainFrame.class.getResource("/pictures/jackc.jpg");
	    ImageIcon card29 = new ImageIcon(urljackc);
	    Card jackc = new Card(card29, 11,"club", "Jack");
	    java.net.URL urlqueenc = MainFrame.class.getResource("/pictures/queenc.jpg");
	    ImageIcon card52 = new ImageIcon(urlqueenc);
	    Card queenc = new Card(card52, 12,"club", "Queen");
	    java.net.URL urlkingc = MainFrame.class.getResource("/pictures/kingc.jpg");
	    ImageIcon card46 = new ImageIcon(urlkingc);
	    Card kingc = new Card(card46, 13,"club", "King");
	    java.net.URL urlacec = MainFrame.class.getResource("/pictures/acec.jpg");
	    ImageIcon card37 = new ImageIcon(urlacec);
	    Card acec = new Card(card37, 1,"club", "Ace");

	  
	    cards.add(acec);
	    cards.add(threeh);
	    cards.add(aces);
	    cards.add(jackh);
	    cards.add(nineh);
	    cards.add(sixd);
	    cards.add(queend);
	    cards.add(aceh);
	    cards.add(nined);
	    cards.add(fours);
	    cards.add(fourh);
	    cards.add(aced);
	    cards.add(sevens);
	    cards.add(tenh);
	    cards.add(jackd);
	    cards.add(tend);
	    cards.add(fives);
	    cards.add(eightd);
	    cards.add(eights);
	    cards.add(kings);
	    cards.add(fourc);
	    cards.add(fivec);
	    cards.add(eightc);
	    cards.add(ninec);
	    cards.add(sixh);
	    cards.add(queenh);
	    cards.add(jacks);
	    cards.add(sixs);
	    cards.add(twoc);
	    cards.add(jackc);
	    cards.add(threed);
	    cards.add(twod);
	    cards.add(fourd);
	    cards.add(queens);
	    cards.add(sevenh);
	    cards.add(eighth);
	    cards.add(kingh);
	    cards.add(threec);
	    cards.add(twoh);
	    cards.add(nines);
	    cards.add(sevend);
	    cards.add(sevenc);
	    cards.add(sixc);
	    cards.add(tenc);
	    cards.add(threes);
	    cards.add(kingc);
	    cards.add(kingd);
	    cards.add(fived);
	    cards.add(twos);
	    cards.add(tens);
	    cards.add(fiveh);
	    cards.add(queenc);
	    System.out.println("ddddd");
	    
	    
		chalenger = new Player(userN,mony);
		 computer= new Player();
		 
	//give each players two cards
		
		
	}
	
	public int getNumberOfCards(){
		
		return cards.size();
		
	}
	
	//if this method get false ..that's mean  the player will not play this round
	public int getstand(boolean flg1,boolean flg2){
	hit(flg1, flg2);
		return numberOfCards;
		
		
		
		
	}
	//the function take two  boolean values, if the first flag is true  -> the player take another card 
//if the second flag  is true  -> the computer take another card
	public void hit(boolean hitFlag1, boolean hitFlag2) {
	
		int temp1 = -1;
		int temp2 = -1;
	shuffle();
		if(hitFlag1==true){
			
		 temp1=rand.nextInt(getNumberOfCards()-1);
		 Card c= cards.get(temp1);
		 //check if we have aces before
		 if(c.getNumber()==1){
		 for(int i = 0 ; i<chalenger.getHandArray().size();i++){
			 if(chalenger.getHandArray().get(i).getNumber()==c.getNumber())
			 c.setValue(1);
		 }}
		chalenger.addToHandArray(c);
		System.out.println("card :"+cards.get(temp1));
		cards.remove(temp1);

		
	}
	if(hitFlag2==true){
		
	
		temp2=rand.nextInt(getNumberOfCards()-1);
		 Card c= cards.get(temp2);
		 if(c.getNumber()==1 && computer.getSumOfCards()+11>21)
		 {
			
					 c.setValue(1);
					 computer.addToHandArray(cards.get(temp2));
						System.out.println("card :"+cards.get(temp2));
						cards.remove(temp2);
						return;
				 
			
		 }
		 
		 if(c.getNumber()==1 )
		 {
			 for(int i = 0 ; i<computer.getHandArray().size();i++){
				 if(computer.getHandArray().get(i).getNumber()==c.getNumber())
				 {
					 c.setValue(1);
					 computer.addToHandArray(cards.get(temp2));
						System.out.println("card :"+cards.get(temp2));
						cards.remove(temp2);
						return;
				 }
			 }
			
		 }
		 computer.addToHandArray(cards.get(temp2));
			System.out.println("card :"+cards.get(temp2));
			cards.remove(temp2);
			return;	
	}
	
	
	

	

}





}
