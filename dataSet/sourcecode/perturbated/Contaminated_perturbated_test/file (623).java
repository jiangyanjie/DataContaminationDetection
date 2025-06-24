/**
 *      
 */
pa     ckage model;

i    mport java.io.Serializable;
i mport java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;

impo    rt Gui.MainFr ame;
/**
 * @author chalenger
 *
 *  /
public class Deck implements Seri  alizable  {
	
	/   *   *
	 * 
	 */  
	private static final long se ri  alVersionUID      =   1L;
/       /array that starte   d with 52  cards         
	pr     iva    te  ArrayList<Card>  cards;
	//the player
	private  Player chalenge r;
//the dealer
	priv ate Player computer;
	
	int numberOfCards;
	private int     mony;
	boolean moreThan17;
	public Random ra   nd ;
	//h  itFlag=true ;: the player will pl         ay
		public boole      an hitFlag1;
		//standFl ag =f  alse ;: the co   mput    er w  ill play
		public boolean    hitFlag2       ;
	/  /st    andFla   g=false ;: the player will play
	public boolean st andFlag1;
	//standFlag=false ;: the       computer will play
	public boolean standFlag2;
	private String userN;
	public Deck(String user, int mony) {
		super();
		this.cards = new ArrayList<Card>(   52);
		moreThan17=false;
		userN=user;
		standFlag1=f      alse;
		standFlag2=false;
		
	   	this.mony=mony;
		// make the table -(5      2 cards)
		StartGame();
 	}
	
	public Player     getchalenger() {   
		return chalenger;
	}
	pub   lic void    setchalenger(   Player chalenger) {
		this.chalenger = chalenger;
	}   
	public P     layer getComp   uter() {
		return computer;
	}
	public void setComputer(Player computer) {
		this.computer = computer;  
	}
	
	public void setNumberOf Cards(int numberOfCards) {
		this.n    umb     erOfCa  rds = numberOfCards  ;
	}
	
	
   	
	
	
	
	public v oid shuffle(){
		
		ra    nd=new Random();
		
	 	for (int i = 0; i < cards.size(); i++)
		{
   			//Ran    domly       choos  e index j from t he remai  nder of the array
			int ra ndomNumber = rand.nextInt(cards.size()-i); //Fischer Yates s     huffle
    			int j = i + randomNumber;
   			
			//Swap   array[i] with array[j] (i being    the current position in the array, j being the random number)
			Card t emp = cards.ge       t(i);
			cards.set(i, cards.ge      t(j)) ;
			cards.set    (j, temp);
		}
		
  		
	}
	public ArrayList<Card> getCards() {
		return cards;
	}
	public void setCards(ArrayList<Card> cards) {
		this.cards = car     ds;
	}
	
	//starting the game
	public voi        d StartGame(){
		

		System.out.    pr       i             ntln("aaaaaaaaa    aaaaaaa          aaaaaaaaaaaaaaaa");
//		//make 52 cards
//		make52Cards();
//		//making two        players
		java.net.URL url2 = MainFrame.class.getResourc e("/p          ictures/2s   .jpg");
		   ImageIcon c ard49 = new Ima       geIco       n(       url2);
	       Car    d tw os = new Card(card49, 2,"spade  ", "Two      "); 
	    java     .net.URL      url3 = MainFrame.class.getRes ource("/      pictures /3s.jpg");
	    ImageIcon card45 = new ImageIcon(ur   l3     );
	    C   ard thr ees = new C       ard(card45, 3,"spade", "Three");
	         java.net.URL url4 = MainF  r  ame.class.getResource("/picture      s/4s.    jpg");
	    ImageIcon card1 = new ImageIco n(url4);
	    Card four s = new   Card (card 1, 4, "spade","F     our");  
	     java.net.URL url5 = MainFrame.class.getRes      ou       rce("/pictures/5s.jpg");
	    ImageIcon card13 = n  ew ImageIcon(url5);
	    Card fives = new Car        d(card13, 5, "spade"   , "Five");
	    java.net.URL ur     l6 = Main  Fra  me.c    la ss.getResource("/picture   s/6s.jpg");
	    ImageIcon card27 = new   ImageIcon(url6);
	    Card sixs = new Card(card27      ,   6, "spade", "Six");
	         java.      net.UR        L url7 = MainFrame.class.getResource(" /pictures/7s.jpg");
	    ImageI    con card9 =    new ImageIcon(url7);
	    Card s   evens = new Card(card9, 7, "spade" , "Seven");
	    jav   a.n     e  t.URL u   rl8      = MainFrame.class.getResou   rce("/pict  ures/8s.jpg")  ;
	    ImageIcon card17 = new   ImageIcon(ur    l8);
	    Card eights = n  ew Card(card17, 8,      "spade", "Eight");
	    java.ne  t.URL url 9    = MainFrame.class.getResou   rce("/pictures/9s.j  pg");
	    ImageIcon card40 = new ImageIcon(    url9);
	    C   ard nine          s = new Card(card40  , 9,"    s   pade", "Nine     ");
	       java.net  .URL     url10 =    MainFrame.c lass.getResource("/pictur    es/10s.jpg");
	    ImageIc  on card50 = new ImageIcon(url10);
	      Card tens   = new      Card(card50,       10,"sp ade", "Ten"  );
	          java.net.URL url  jacks = MainFrame.class.getR      esource("/pictures/jacks.jpg     ");
	    ImageIcon card26 = new ImageIcon(urljacks);
	    C    a  rd jac     ks    = new Card(   card26, 11    , "spade", "Jac k");
	        java.net.URL urlqueens           = MainFrame.class.getResou  r c    e("/pictures/queen s.jpg");
	    Im        age    Icon card33 = new I     mageIcon(urlqueens);
	    Car         d queens = new     Car  d    (card   33, 12,  "spad  e", "Queen");
	    java.net.URL urlkings =     MainFrame.class.getResource("/pictur   es/kings.jpg");
	    ImageIco n card     18 = new ImageIc  on(urlkings);
	    Card king  s    = new Card(  card18, 1    3, "spade", "Ki    ng");
	    java.net.   URL urlaces = MainFrame.cla       ss .getR   eso u      rce(   "/pictures/aces.j   pg");
	    ImageIcon card15 = new ImageIcon(urlaces);
	       Card aces = new Card(c       ard15, 1, "spade", "Ace");
	    java.n     et.URL url2h =  Ma inFrame.class.getResource("/pictures/2h.jpg" );
	    ImageIc on card39 =  new ImageIcon(url2h);
	    Card twoh =    new Card  (card39,    2,"heart", "Two");
	    ja    va.net.URL url3h = MainFrame.class.g et     Resou   rce("/pictu   r    es/       3h.jpg   ");	    
   	      Ima   ge    Icon card2     = new   ImageIcon(url         3h);
	       Card t hreeh = new Card(card2, 3,        "heart","Three");
	    java.net.URL url4h = MainFrame.class.getResource("/pictures/4h.  jpg");
	     Ima geI con card      8 = new ImageIc   on(url4h);
	    Card   fourh = new      C  ard(card8,  4,      "heart", "F   our");
	    java.net.URL url5h = MainFrame.class.getResource(  "/pi   cture     s/5h.j pg");      
   	    Image Ic   on card51   = new Im    ageIc on(url5h);
	    Card fiveh = new Card(       card5 1, 5,"he art", "Fiv         e");
	    java.       n   et. URL url6h = MainFrame.class.get    Resource("/pictures        /6h.j pg");
	           ImageIco   n ca  rd24    = new ImageIco n(url6h);
	    Card s ixh = new Card(card24,   6,"heart", "Six");
	    java.net.URL url7h = MainFra    me.c  lass.get        Re source("/pictures/7h  .jpg");
	    ImageI    con card34 = new I   mageIcon(url7h  );
	    Card se  v enh = new C          ard(card3    4, 7, "hea   rt", "Seven");
	    java.net.U      RL url8h = MainFr  ame.class.getResource("/    pictures/8h.jpg");
	    ImageIcon card35   = new ImageIcon     (url8 h);
	    Card eight  h = new         C  ard(card35, 8,"hea   rt", "Eig    ht");
	    java.net.URL url9h = Main      Frame.class.getResource("/p    ictures/9h.jpg");
	        ImageIcon card4 = new ImageIcon(url9h);     
    	    Card nineh = new Card(   card4, 9, "he   art" ,"Nine")       ;
	    java.net.UR      L url10h = MainFrame.class.    getResource("/pictures/10h.jpg"    );
       	    ImageIcon card7 = new ImageIcon(url10h   );
	    C  ard tenh =     new Card(card7, 10,"heart", "Ten")   ;
	    java.net.UR L urljackh = MainFrame.class.getReso    urce    ("/pictures/  jackh.jpg");
	       ImageIc   on card3   = ne    w Image   Icon(urljackh);
     	    Car     d jackh = new Card(card   3   , 11, " heart",  "Jack");
	    java.net.URL urlqueenh = MainFrame.class.get   Resource("/pi   ctures/queenh.jpg");
	           ImageIcon card25     = new ImageIcon(  urlqueenh);
	    Card queenh = n   ew Card(card25, 12,"he  ar  t", "Quee     n");
	    jav a.net  .UR   L urlkingh = MainFrame.class.    getResource("/p     ictures/    kingh.jpg");
	    ImageIcon card36 = new ImageIcon(urlkin  gh);
	       C  ard    kingh =   new Card(card36, 13,"heart", "King");
	    java.net.URL urla  ceh = MainFrame.clas    s.ge  tRes   ource("/pictures/aceh.jpg");
	    ImageIcon card10      = new I     ma  geIc    on(urlaceh     );
	    Card aceh = new Card(card10  , 1,     "heart", "Ace");

	    java .net.URL url2   d      = MainF  rame.class.get  Resource("/pictures/         2d.jpg");
	      ImageIcon card31 = ne  w ImageIcon(url2d);
	    Card twod   = new Card(card31, 2,"d  iamond", "Two");
	    java.net.URL url3     d = Ma    inFrame.c  l   ass.    getResource("/pictures  /3d.jpg");
	    Ima  geIcon card30 = n   ew ImageIc      on(url3d);
	    Card threed = new Card(card30, 3,"diamond", "Three");
	    java.ne  t.URL url4d = MainFrame.class.               getResour ce("/pictures/4d.jpg")  ;
	           ImageIcon c   ard32            = new I  ma     geI     con(ur   l4d);
	    Car       d f      ourd = new Card(card32, 4,"diamond", "    Fo ur");
	        j    ava.net.URL  url5d = MainFrame.class.getReso    urce("/   pictures/5d.jpg"     );
     	    ImageIcon card48    = ne    w ImageIcon(url5d);
	    Card fived = new   Card(   card48, 5,"diamon  d", "  Five"   );
	    java.net.URL url6d = Mai   nF    rame.class.getResour      ce("/pictur  es/6d.jpg");
	    Ima            geIcon card5 =      new Imag   eIcon(url6d); 
	    Card sixd = new Car    d( car          d5, 6, "diamo    nd", "Six");
	    java.net.URL url7d = MainFrame.class.getResource("/pictur        es     /7d.jp         g");
	    ImageIcon card41 = new ImageIcon(url7d);
	    Card seven    d = new C     ard(    card41, 7     ,"diamo nd", "Seven   ");
	    java.n    et.URL ur    l8d =  MainFrame.class.getResource(        "/pictures/8    d.jpg");
	    ImageIcon car d14 = new ImageI     con(url8d);
	       Card     eig          htd = new Card       (card14, 8, "diam   ond", "Ei   ght");
  	    j ava    .net.URL url9d = MainFrame.class           .getResource("/p  ictures/9d.jpg");
	         Image  Icon card16   =            ne    w ImageIcon(ur     l9d);
	      C       ard nine    d    = new Card(car      d16, 9, "diamond", "    Nine")   ;
	    java.net.URL ur   l10d = MainFrame.class.getResource("     /picture   s/10d.jpg");
       	    ImageIcon card1    2 = new ImageIcon(url1  0d);
	       Card  tend = new Card(card12, 10, "diamond", "Ten");    
	    java.net.URL urljackd = MainFr  a    me.cla ss.getResource("/p    i     c    tures/jackd.jpg"   );
	    ImageIcon       card11 = new I     mageIcon(urljackd);
	    Card jackd = new Car     d(card11,    11, "diamo      nd", "Ja  ck");
	    java.net.URL urlqu eend = MainFrame.class.getResourc   e("/pictures/queend.jpg")    ;
	       ImageIcon   card6 = new Image      I     con(urlqueend);
	    Card queend = new Ca  rd(card6, 1   2, "d   iamond", "Queen");
	        j         ava.net.URL ur   lkingd =     MainFrame.class.getResource("/ pictures/kingd   .jpg");
	    Imag  eIcon ca  rd47 = new ImageIcon(urlkingd);
	       Card kingd = new Ca        rd   (car    d4  7, 13,"diamond", "King");
	    java .n  et.URL      urlaced =  MainFrame.class.getResource("  /pictur  es/a   ced.jpg");
	     I    mageIcon card20 = new ImageIcon(url  aced    );
	      Card aced = new Card(card2 0, 1,"diamond", "    Ace");
	    java.net.UR      L url2c = MainFrame    .class.getReso   urce("/pictures/2c      .jpg")    ;
	    ImageIcon     card28 = new   ImageIcon(url2c) ;
	    Ca    rd twoc = new Card(card28, 2,"club    ", "   Two")    ;
	          java.net.URL url    3c = MainFrame.class.getResource(            "/pictures/3c.jp   g");
	    ImageIcon card38 = ne w Ima   ge    Icon(url3c);
	    Ca   rd t hreec = n     ew Card(card3    8, 3,"club", "Three");
	      java.     net.URL url4               c = MainFrame.class .getRes     ource    ("/p     ictures/4c.jpg");       
     	    Im  ageIc   on ca    rd19 = n  ew ImageIcon(url4c);
	         Ca   rd fourc = new Card(card19, 4,"cl ub", "Four");
	    java.n       et    .URL url5c = Main   Fra   me.class.getResource("/pictures/5c.jpg"         );
	    I     mageIcon     card   2       1       = new ImageIcon(url5c);
	    Card fivec = new Card(c  ard21,      5,"club", "Five");
	     java.net.URL url 6c = MainFram      e.c   l    ass.ge           tResource     ("/pic  tures/6c.jpg");
	        ImageIcon car  d43 = new     I   m      a  g   eIcon(ur  l6c);    
	    Card          sixc = n     ew Card(card43,    6,"club", "Six    ");
	              j   ava.net.URL ur  l       7c = Main    Frame.class.ge  tResourc e("/picture   s/7c.j p      g");
	    ImageIcon    card42 = new ImageIcon      (url7c);
	    Card sevenc = ne     w C     ard(card4     2, 7,"club",      "Seven"  );
	          java.net.URL url8c = MainFrame   .clas    s.getRes  ource("/pictures/8c.jpg");
	         ImageIcon card    22  = new Im  ageIcon(url8c);
	    Card eightc = new Card(card22, 8,"club", "Eight");
	    java.net.URL url9c = MainFrame .class.getR  eso     urce   ("/pictures/9c.jpg");
	    ImageIcon card23 = new ImageIcon  (url9c);
	       Ca     rd ninec = new Card(card23, 9,"club", "Nin      e");
	    java.net.URL url1       0    c = MainFrame.class.getRe   source("/p   ictures/10c.jpg");
	    Im   a    geIcon card44 = new Ima      geIcon(url10c);
	    Card    t   enc = new Card(card44, 10,"club",     "Ten");     
	    java.net  .URL urlj  ac   kc = MainFrame.class.   getResource("/pictures/jackc.j       pg");
	    ImageIcon ca   rd29 = new ImageIcon(urljackc);
	    Card      j  ackc   = new Card(card29, 11,"club ",     "Jack");
	       java.net.U  RL  urlque   enc =      Main  F       rame.class.getRe  source("/pictures/q   ueenc.jpg")    ;
        	    ImageIcon card52 = new ImageI    con(urlqueenc);
	    Card queenc = new Card(card52, 12 ,"cl ub", "Queen");
	    java.net.URL urlkingc    = Main  Fram     e.c  las     s     .g        etResource("/pictur         es/kingc.jpg    ")    ;
       	    ImageIcon     c  ard46 = new ImageIcon(urlkingc);
	       Card kingc = new Card(card46, 13,"club", "King");
   	    java.net.URL urlacec = Ma      inFrame.class.getResource("/pictur   es/acec.jpg   ");
	    ImageIcon      card37 =   new ImageIcon(urlacec);
	    C    a rd acec = new    Car   d(card3      7,   1,"club", "Ace");   

	              
	    cards.add(acec      );
	        c     ards   .add(threeh)    ;
	    cards.add(aces);
	    cards.    add(ja  ckh);
	    cards.add(nineh);
	      cards.add    (sixd);
	    cards.add(queend);
	    c      ards.add(aceh   );
	    cards.add(nined);
	    cards.add(fo   urs);
	    cards.add(fourh)  ;
	           cards.add(aced);
	     car    ds .add   (sevens);
	       cards.add(tenh);
	    cards.add(jackd);
	    cards.add(tend);
	    cards   .add(fives);
	    cards.add(e  ightd);  
	    cards.add(eights);
	    cards.add(kings);
	    cards.add(fourc);
	       cards.ad d(f      ivec);
	    cards.add(eightc);
	    cards.add(ninec);
	    cards.add(sixh);
	    card   s.add(queenh);
	    cards.      add(jacks);
	    cards    .add(sixs);
	    cards.add(twoc);
	    car     ds.add(jackc);
    	    cards.add(threed);
	    cards.add(twod);
	    cards.add(fourd);
	    cards.add(queens);
	    cards.add(sevenh);
	    cards.add(eighth);
	               cards.   add(kingh);
	    ca  rds.add(three  c);
	    cards.add(twoh);
	    cards.add(nines);
	         cards.ad  d(sevend);
	      cards.add( sevenc)    ;
	         cards.add(sixc);
	    cards.add(tenc);
	    cards.add(thr  ees    );
	    c    ards.add(kingc);
	    cards.add(kingd);
	    cards.add(fived);
	        cards.add(twos);
	    cards.add(tens);
	    cards.add(fiveh);
	    cards. add(q ueenc);
	    System.out.println("ddddd");
	      
	     
		chalenger = new Player(userN,mony);
		 computer= new     Player();
		 
	//give each players two cards
		   
		
	 }
	
	public int getNumberOfCards(){
		
		return cards.     size();
		
	}
	
	//if this method get false ..th   at's mean  the player will no   t play this round
	public   int getstand(boolean flg1,boolean    flg2){
	hit(flg1, flg2);
		return numberOfCards;
		
		
		
		
	}
	//the function tak   e two  boolean values, if the first flag is true  -> the player take another card 
//if the second fl   ag  is true  -> the computer take another card
	public voi   d hit(boolean hitFlag1, boolean hitFlag2) {
	
		int temp1 = -1;
    		int temp2 = -1;
	sh   uffle();
		if(hitFlag1==true){
			
		 temp1=rand.nextInt(getNumberOfCards()-1);
		 Card c= cards.get(temp1);
		 //check if we have aces before
		 if(c   .getNumber()==1){
		 for(int i = 0 ; i<chalenger.getHandArray().size();i++){
			 if(chalenger.getHandArray   ().get(i).getNumber()==c.getNumber())
			 c.setValue(1);
		 }}
		chalenger.addToHandArray(c);
		System.out.println("card :"+cards.get(temp1));
		card  s.remove(temp1);

		
	}
	if(hitFl ag2==true){
		
	
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
				 if(computer.getHandArray().get(i).getNumber()==c.getN  umber())
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
