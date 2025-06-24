package estefan;

import java.util.StringTokenizer;



public class Createur {
	
	
	private static String forme;
	private static String socket[];
	private static String str;
	private static int i;
	private static int registre;
	
	public Createur(){
		
		forme= "";
		
				
	}
	
	public static void setCreateur(String str){
		
		forme = str;
		
		
	}
	
	public static Forme getForme(String forme){
		
		Forme forme1 =  null;
		
		 StringTokenizer str = new StringTokenizer(forme);
		 socket = new String[str.countTokens()];
		 
	     while (str.hasMoreTokens()) {
	         
	         socket[i]= str.nextToken();
	         i++;
	         
	     }
	 
       
      	 registre = Integer.parseInt( socket [0]);
        	 
	     if (socket[1].equals("<CARRE>"))
	     {
	    	forme1= new Carre(Integer.parseInt( socket [2]),Integer.parseInt( socket [3]),Integer.parseInt( socket [4]),Integer.parseInt( socket [5])); 
	    	System.out.println("Creation de carre");	
	     }
	     
	     else if (socket[1].equals("<RECTANGLE>"))
	     {
	    	forme1= new Rectangle(Integer.parseInt( socket [2]),Integer.parseInt( socket [3]),Integer.parseInt( socket [4]),Integer.parseInt( socket [5]));
	    	System.out.println("Creation de rectangle");
	     }
	     
	     else if (socket[1].equals("<CERCLE>"))
	     {
	    	forme1= new Cercle(Integer.parseInt( socket [2]),Integer.parseInt( socket [3]),Integer.parseInt( socket [4]));
	    	System.out.println("Creation de cercle");
	     }
	     
	     else if (socket[1].equals("<OVAL>"))
	     {
	    	 forme1= new Oval(Integer.parseInt( socket [2]),Integer.parseInt( socket [3]),Integer.parseInt( socket [4]),Integer.parseInt( socket [5]));
	    	 System.out.println("Creation de oval");
	     }  else if (socket[1].equals("<LIGNE>"))
	     {
	    	 forme1= new Ligne(Integer.parseInt( socket [2]),Integer.parseInt( socket [3]),Integer.parseInt( socket [4]),Integer.parseInt( socket [5]));
	    	 System.out.println("Creation de ligne");
	     }

	     else {i=0;}
           
	
	     
	     
      return forme1;
      
		
	     
	
	
			
	}
}
	
	


