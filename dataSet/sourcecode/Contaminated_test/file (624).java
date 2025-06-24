import java.util.*;
import java.io.*;

public class Deck2 extends ArrayList<Cards> {

    public ArrayList<Cards> Starter;
    public Cards a;
	
    public Deck2(){
		
	ArrayList<Cards> BoringDeck = new ArrayList<Cards>();
	Starter = new ArrayList<Cards>();
	Bob b = new Bob();
	Dave d = new Dave();

	//System.out.println("Checking");
	//System.out.println(b.getName());

	BoringDeck.add(b);
	BoringDeck.add(b);
	BoringDeck.add(b);
	BoringDeck.add(b);
	BoringDeck.add(b);
	BoringDeck.add(d);
	BoringDeck.add(d);
	BoringDeck.add(d);
	BoringDeck.add(d);
	BoringDeck.add(d);
		
	//System.out.println("hmmm");

	while(BoringDeck.size() > 0){
	    //int num = (int) (Math.random() * BoringDeck.size() + 1);
	    Random r = new Random();
	    int num = r.nextInt(BoringDeck.size());	    
	    Starter.add(BoringDeck.get(num));
	    BoringDeck.remove(num);
	}
	
	a = Starter.get(0);

    }
	
    public void shuffle(){
	ArrayList<Cards> temp = new ArrayList<Cards>();
	int n = Starter.size();

	for(int i = 0;i<Starter.size() ;i++){
	    temp.add(Starter.get(i));
	}

	while(temp.size() > 0){
	    Random r = new Random();
	    int num = r.nextInt(temp.size());	    
	    Starter.add(temp.get(num));
	    temp.remove(num);
	}

	for(int j = 0;j<n ;j++){
	    Starter.remove(j);
	}
    }

    public String Draw(){
    	Cards a = Starter.get(0);
    	return "Name: " + a.getName() + "\n"
    			+ "Description: "+ a.getDescription() + "\n"
    			+ "Wisdom: "+ a.getWisdom() + "\n"
    			+ "Enthusiasm: "+ a.getEnthusiasm() + "\n"
    			+ "Charisma: "+ a.getCharisma() + "\n"
    			+ "Likability: "+ a.getLikability() + "\n"
    			+ "Bravery: "+ a.getBravery() + "\n";
    }

    public String ShowDeck(){
	String str = "";
	for(int i = 0;i<Starter.size() ;i++){
	    str += Starter.get(i).getName() + "\n";
	}
	return str;
    }
    
    public void addSpCard(){

    	AngelaLin AL = new AngelaLin();
    	BrownMykolyk BM = new BrownMykolyk();
    	Cocoros CO = new Cocoros();
    	EmmaLou EL = new EmmaLou();
    	FlorenceLo FL = new FlorenceLo();
    	JerryDai JD = new JerryDai();
    	KevinKan KK = new KevinKan();
    	LawrenceLim LL = new LawrenceLim();
    	MicheleChan MC = new MicheleChan();
    	WookyungLee WL = new WookyungLee();
    	Zamansky ZA = new Zamansky();
    	
    	ArrayList<Cards> Special = new ArrayList<Cards>();
    	Special.add(AL);
    	Special.add(BM);
    	Special.add(CO);
    	Special.add(EL);
    	Special.add(FL);
    	Special.add(JD);
    	Special.add(KK);
    	Special.add(LL);
    	Special.add(MC);
    	Special.add(WL);
    	Special.add(ZA);
    	
    	Random r = new Random();
	    int num = r.nextInt(Special.size());	    
	    Starter.add(Special.get(num));
	    
	    shuffle();
    }

    /* public void makeLinkedList(){
	MyLinkedList<Cards> C = new MyLinkedList<String>();
	for (int i = 0; i < Starter.size(); i++){
	    C.add(Starter.get(i));
	}
	}*/ 
    //Emma you're messing me up ><
	
    public static void main(String args[]){
	Deck2 a = new Deck2();
	System.out.println(a.ShowDeck());
	System.out.println("\n");
	a.shuffle();
	System.out.println(a.ShowDeck());
	a.addSpCard();
	System.out.println(a.ShowDeck());
		
    }
}
