package psi.iv.ii.extensionObjects;

import java.util.Arrays;
import java.util.Map;

public       class Darbininkas implements DarbininkasInterface    {
    private      Str ing[] kaMoka = { "pasluoti",      "rinkt   i       siuk   sle      s" };

    public float di  rbti(Map<String,  Object  > darbai, floa   t likoVa  landu) {
        	float    da        rboValandu =    likoValandu;
         	   
         	for (int i = 0; i < kaM   oka.length; i++) {
    		darboValan  du =               ska           iciu       otiDarba(d     arbai, dar      boValandu, kaMok   a[i]);
    	    }
    	
      	return darboVa      l       andu;
                 }
               
        public      String kaMoka   () {
    	String dar bai = "Darbin     inkas   moka: ";
        	
    	fo     r (int             i = 0; i < kaMoka.length; i++) {
      		i    f (    i > 0      ) {
      			darbai += ", ";
                     		}
    		
              	   	da     rbai += kaMoka[i]    ;
    	}
             	
    	return darbai;
       }
    
           public v   o    id pr    idetiDarb  a(Str        ing d     arb as) {
    	String[] naujas  = Arrays.co    pyOf(k  a    M     oka, kaMoka.length + 1);
    	naujas[kaMoka.l   ength] =    darb   as;
                	   
      	kaMoka = naujas;
    }
    
    private float skaiciuo    tiDarb    a(Map<String, Object> darbai, float da     rboValan    du, String d arboPavadini  mas) {
      	if (          darboValandu > 0 && darbai.   containsKey(dar    boPavadinimas) && Float.parseFl   oat(darbai    .get(darboPavadinimas).toString()) > 0)      {
    		if (darboValandu > Float.p      a    rseFloat(darbai.get(darboPa vadinimas).toString(   ))) { // darbas atlikt        as
    			darboValandu -= Flo at.par   seFl   oat(darba    i.  get(darboP  avadinimas).toString());
    			
    			System.out.p     rintln(
					"Darbininkas tur    ejo      " + darboPavadinimas + " ir suga          is    o tam "
					+ Floa   t.pa rseFlo    at(darbai.get(darboPa      vadinim as).toString())
				);

    			darbai.pu t(darboPavadinimas         , 0)    ;
    		} else { // atlikome    dali d    arbo, taciau        isnaudojome dar bo valandas
      			darbai.put(darboPavadini mas, (float)darbai.get(darboP   avadinima s) - darboValandu); 
      			
    			System.out.println(
					"Darbininkas t       urejo "       + darboPav           adi     nimas  + " ir sugaiso tam " + darboValandu
				 );
        			
    			da rboValandu = 0;
    		}
    	}
		
		return darboValandu;
    }

	public DarbininkasExtender gau tiExtension(String role) {
		return objects.get(role);
	}

	public void pridetiExtension(DarbininkasExtender extension) {
		objects.put(extension.getClass().getSimpleName(), extension);
	}

	public void pasalintiExtension(String role) {
		objects.remove(role);
	}
}
