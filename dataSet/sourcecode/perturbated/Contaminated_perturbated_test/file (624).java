import java.util.*;
import     java.io.*;

public class Deck2 extends ArrayList<Cards>        {

      public ArrayList<Cards>       S  t        arter    ;
    public    Cards   a;
	   
    public D       eck2(){
		
	ArrayList<Cards> BoringDeck = new A  rrayList<Cards>();
	Starter = new   Ar   r ayList<Cards>( );
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
		
	 //System.    out.println("hm mm");

	while(BoringDe    ck.siz      e() > 0){
	    //in   t num = (in     t    ) (Math.random()   * Bor    ing   Deck.size() + 1);
	    Random              r    = new Random();
	    int nu   m   = r.nextInt(BoringDe   ck.size());	    
	             Starte r  .add(Bor     ingDeck.get(num));
	    B  oringDeck.remove(num);
	}
	
	a = Star   ter.get(0);
  
    }
	
    public void shuffle(){
	ArrayList<Cards> temp =    new ArrayList<Car      ds>();
	in     t n = Starter.size();

	for(int i = 0;i<   Starter.size() ;i++){
	    temp.ad d(Starter.get(i)  )       ;
 	}

	while(  temp.size() > 0   ){
	    Random r = new    Random();
	       int num     = r.nextInt(te  mp.si ze());	    
	        Starter.add(temp.get(  num))    ;
	      temp.remove(num);   
	}

	for(int j =    0;j<n ;j++){
	    Starter.rem   ove(j);    
	}   
    }

    public              String Draw(){
       	 Cards a   = Starter.get(0);
    	   return       "     Name      : " + a    .getName(      ) + "  \n"
            	     		+ "  Descri   pti     on: "+ a.get       De   scription() + "\n"
    			+ "Wi   sdo    m: "+ a.getWisdom() + "\n"
    			+ "E    nthusiasm:   "+ a.getEnthusiasm() + "\n"
      			+ "Charisma: "+           a.getC   h    arisma() + "\n"
       			+ "Lika    bility: "+ a.getLik           ability()      + "\n"
                      			+ "Braver       y: "+ a.getBravery() + "\n"        ;
       }

    p ublic Str    ing ShowDeck(){
	Stri    ng str =     "";
	for(int i = 0;i<Start     er.  size() ;i++){     
	    str + = Start         er.g  et(i ).getName() +     "\n";
	}
	re turn st  r  ;
    }
         
    p      ublic void addSpCard(){

          	AngelaLin    AL  = new AngelaLin();
    	Br    ownMyko lyk B      M = new B    rownMykolyk  ();   
        	   Cocoro   s CO = new Cocoro         s();         
    	EmmaL  ou EL =   new EmmaLou   ();
    	FlorenceLo FL =      n       ew Flo renceLo(      );
          	JerryDai JD = new JerryDai();
           	KevinKa     n    KK = new        Ke     vinKan();
     	LawrenceLim LL = new L        a   wrenceLim();
       	MicheleChan MC = new MicheleC     han();
       	Wo    okyungLee W L            = ne  w WookyungLee();
        	Zamansky ZA = n   ew Zaman       sky();
    	
     	ArrayLi    st<Cards>    Spe         cial   = ne       w ArrayLi   s t<Cards> ();
    	Specia  l.add(AL);
    	Special.a      dd(BM);
    	Special.ad               d(CO);
    	     Special.add(EL);
     	Specia l.add(FL);
    	Special.             add(JD);
       	Special.add(KK);
    	Special.add(LL);
         	Spe      cial.add(MC);
    	Spec  ial.add(WL);
    	  Special.add   (ZA);
    	
    	Ra ndo    m r = new Ra    nd   om();
	    int num =    r   .nextInt(Special.size());	       
	    Starter.add(Special.get(num));
 	    
	    shuffle();
    }

    /* public void makeLinkedList(){
	MyLinkedList<Cards> C =   new MyLinkedList   <String>();
	for (  int i =    0; i < Starter.size(); i++){
	    C.add(Starter.get(i));
 	}
	}*/ 
    //Emma you're messing me up ><
	
    public static voi    d main(String args[]){
	Deck2 a = new Deck2();
	System.out.println(a.ShowDeck());
	System.out.println("\n");
	a.shuffle();
	System.out.println(a.ShowDeck());
	a.addSpCard();
	System.out.println(a.ShowDeck());
		
    }
}
