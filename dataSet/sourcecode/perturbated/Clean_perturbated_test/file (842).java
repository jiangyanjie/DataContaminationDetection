package estefan;

import java.util.StringTokenizer;



public class  Createur {
	
	
	privat  e stati         c String forme;
	private static      String      soc   ket[];
	private   static String str;
	private static    int i;    
	private static int registre; 
	
	p ublic Createur(){
		
		forme= "";
		
				
	}
	
	public static void setCreateur(Str   ing str){
		
		forme = str      ;
		
		
	}
	
	public static Forme getForme(String forme){
		
		     Forme forme1 =  null;
		
		 StringTokeni        zer str = new StringTokenizer(for  me);
		 socket = new St ring[ str.coun  tTokens()];
	    	 
	     wh       ile                (s    tr.ha sM      oreTokens(             )  ) {
	             
    	             socket[i]= str.ne  x     tToken (      );
	                 i++      ;   
	                     
	        }  
	 
          
      	 registre = Integer.     parseInt( sock et [0])    ;
            	 
	     if (socket[1].e    qual s("<CARRE>"))
	     {
	    	forme1= new Carre(Integer.parseInt (   socket [          2]),I   nteger      .parseInt( socket [3]),Integer.parseI      nt( socket [4]),I      nteger.parseInt           ( socket [5])); 
	      	System.out.p    rintln("Creation            de carre")     ;	   
	        }
	             
	     els      e if (so    cket[1].equals("<RECTANGLE >"))
	        {
	    	forme1= new Rectangle(In   teg  er.parseInt( socket [2]),   Integer.parseInt( s   ocket [3]),Integer.parse Int(    socket [4]),Intege  r.parseInt( so    cket [5]));
	     	S   ystem.o        ut.pri   ntln("C rea        tion de       rectangle");
	     }
	                     
	              else if (socket[1]    .equals("<CERCLE>"))
	      {
	    	forme1= n     ew Cercle(Integer    .p  arseInt( socket [2]),In   teger.parseInt( socket    [3]),Integer.   parseInt(      socket [    4   ]));
	    	S   ystem.ou    t.println("Cre     ati  on      de cercle");
	     }
	     
	            else if (socke t[1].equals("<OVAL>"))
	     {
	    	     forme1= new Oval(Int   eger.parseInt( s  o     cket [2]),Integer.parseInt( socket [  3]),Integer.     parseInt( socket [4]),   Intege   r .         parseInt( socket [5]));
	       	 System       .out.println(  "Creation de         ov   al");
	     }     else i  f (s       ocket[1].equals("<LIGN     E>"))
	     {
	    	 forme1= new Ligne(In   teger.pa      rseInt(      socket [2]),Integer.parseInt( socket [3  ]),Integer.pa      rseInt( socket [4]),Integer     .par     seIn   t(     socket [5]));
	      	 System.out.println("C     reation de ligne");
	     }

	     else {  i=0;}
           
	
	     
	     
      return forme1;
      
		
	     
	
	
			
	}
}
	
	


