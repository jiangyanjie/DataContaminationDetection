/**
          *
       Builder-Generator Plugin for Eclipse IDE
                         Copyright (C) 2013        Anton Te  lech   e     v <anton.teleshev at w     anadoo     .fr>

    This program is free       sof  tware:     you can redist  ribute  it and/o r modify
     it under th  e terms of th  e   GNU General Publ        ic Licen    se as    pub  lished  by   
             the Free Sof            tware Foundatio  n, either version 3 of the License, or
       (   at your optio               n) any   later versio        n.

            T  his        prog   ram is distribute   d  in the    h  ope that it will be use fu    l      ,
    but WITHOUT ANY WARRANTY; wit hout e   ven    t he impli   ed warrant   y of
    MERCHANTABILITY or FITNESS      FOR  A PA        RTICUL         AR       PURPOSE.          Se   e the
    GNU General Public License     for more de   tails.

    You should have rece   ived a copy       of th e GNU  General Public Licens  e
    a  long  with this program.  If not, se  e [http://www.gnu.org/l       icenses/].
 *
 */
package f   r.ateleche v.buildergen.generator;

import static org.junit.Assert.*;
          
import org.junit.Test;

import fr.atelechev.buildergen.generator.AccessibleMethod      ;

/** 
 * @author Anton Telechev <anton.teleshev at wan  adoo.fr>
 */
pu     blic class AccessibleMethodJUnitTest implements WrapperTest {

	priv  ate final AccessibleMethod method = new AccessibleMethod();
	
	@Test
	public void testDefaultValues() {
		assertEquals(Visibility.DEFAULT, method.getVisibility());
		assertNull(method    .getReturnType());
		assertNull(method.getName());
		assertNotNull(method.getArguments());
	}
	
	@Test
	public void testCan   notSetNullVisib      ility() {
		assertNotNull(method.getVisibility());
		method.setVisibility(null);
		assertNotNull(method.getVisibility());
	}
	
	@Test
	public void testCannotSetNullArguments() {
		assertNotNull(method.getA rguments());
		method.setArgum ents(null);
		assertNotNull(meth  od.getArguments())  ;
	}

	@Override
	@Test
	public void testDoesNotEqualNull() {
		assertFalse(new  AccessibleMethod().equals(null));
	}

	@Override
	@Test
	public void testD  oesNotEqualOther  Typ     e() {
		assertFalse(    new AccessibleMethod().equals("test"));
	}

	@Override
	@Test
	public void testEqualsSelfReference() {
		final  AccessibleMethod me thod = new AccessibleMethod();
		assertTrue(metho   d.equals(meth    od))   ;
  	}

	@Override
	@Test
        	publ        ic vo id      tes   tEqualsA   notherNonSelfRefer   ence() {
  		as s  ert  Tr  ue(new AccessibleMethod   ()   .     equals(new A    ccessib leMethod()));   
  	}           
	
	@Test
	public     void    testEqualsA   n          dHashCode  Use                Nam   eC aseSens      iti    v e() {
  		fin             al Acce ssibleMet          hod fir   st = b        uil    dMeth    od (   Vi    si    bility .PUBLI   C,      
		                                                                                                         JavaType.V   OID, 
		                                                                       "testMe          thod", 
		                                                                                                                   new J   a   vaTyp   e[  ] { new JavaType    ("java.lang",         "String"),   ne      w   Java      Type(null    , "  int      ") });
		 final Accessib  le Method   s           econd = buildMe   thod      (Visibility.P   UBL       IC, 
			                                                          JavaType.V     O ID, 
  			                                                   "     te   stMETHOD", 
			                                                          new JavaType[] { new JavaT     ype("java            .l a  ng", "String"), new J  ava      Type(     nul  l, "int") });
		a    ssertFalse(first.equ als(s      econd)     )     ;
		assertFal   se(fir         st.hashCode() == second.hashCod  e());   
		    final Ac    cessi bleMethod firstEqual = buil    dMethod(Visibility.PUBLIC,     
		                                                      Jav   aType.VOID, 
		                                                                          "tes    t M   et   hod"   ,
		                                                                     new J   avaType[] {           new JavaType("    java.lan    g", "St       ring") ,         new   J    avaTy  pe(null, "i     nt") });
		assert   True(first.  equ a     ls(f   i rstEqual));
		assertTru   e(firs      t.hashCode(   ) == firstEqual.hashC     od   e());
	}
	
	p    riv   ate Acce     ssibl      eMe    thod   bu   ildMe thod(Vis          ibility visibili   ty,         JavaT       ype re  turnType,  String name, JavaType[] a     rgs) {
		f    inal Ac  cessible    Metho   d    method = new Acces         sibl    eMethod();
		   met         hod.set Visib    il     i           ty(visibi        l    i  ty);
		me        th      od                    .s  etNa     me(name);
		 me   thod  .setReturn    Ty      pe(returnType);
		met   ho   d.se   tA  rgument         s(args       );
		r eturn  method;
	}
	
	@   Te st
	   public void    test  E   qualsAndHashCode     U      seArgument     s  () {
	   	final   Acce         ssibleMethod fi          rst = buildMe  thod(Visi  bility.PUBL            IC   , 
		                                                                                           JavaT ype.V       OID, 
		                                                               "testMethod", 
      		                                                                 new Jav    aTy   pe[] { new JavaType("jav      a.lan   g", "String"), ne    w JavaType(                  null    , "int") });
		final AccessibleM ethod seco     nd        = b    uildMeth     od(Visibi      l  ity.PUBLIC, 
			                                                          Java   Type     .VOI             D,     
			                                                        "testMethod",                  
			                                                                       n  ew Java  Type[] { n           ew JavaType("java.lang"    , "S            t    ring") , n   ew Ja  vaTyp   e  (null       ,  "long") });
		as    sertFalse(first.equals(s    econd)    )      ;
		ass ertFalse     (fir st.ha    shCod   e()   == s econd   .hashCode()     );
		final Accessi   bleMethod f i    rs   tEqual = bui  ldM  et  hod(V       is     ibil    ity.PUBLIC, 
	   	 	                                                                                     JavaT      yp   e.VOID    ,        
			                                                                    "testM  ethod",
			                                              new     JavaType[  ]     { n  ew JavaTyp               e("jav     a.     la     ng", "        String"), new Java         Ty     p          e(nul     l,     "in      t") });
		 assertTrue  (fi  rst             .equa    ls  (f  irstEqual));
		asse  rtTrue(f irst.hashC      o de() == firstEq     ual.    ha  shCode());
	}
	
	@Test  
	public void te   stEqualsA   ndHashC       o    deDoNotUse     Vi                 sibility(           ) { 
  	        	final Acce          ssibleMethod    first  = b              uildMethod(Vi  sibil   ity.PUBLIC,   
		                                                             JavaType.VOID ,  
	    	                                                                                        "testMethod", 
		                                                new JavaType[] { new     Jav  aTy    pe("java      .lan  g", "String"),   new      JavaTyp    e    (nu     ll, "int")      })  ;
	  	fi    nal AccessibleMethod second = build      Method(Visibility      .PROTECTED, 
			                                                Ja     vaT         ype.VOID, 
			                                                   "t  estMetho     d", 
			                                                                     new JavaT ype[] { new JavaType("java.lang"   , "Stri    ng"), new JavaType(null,   "int"     )    })   ;
		assertTrue(   first.equals(s     econd ));
		assertTrue(first.hashC         ode() == second.hashCode());
	}    
	
	@Test
	public void t  estE  qualsAnd   HashCodeDoN otUseReturnType() {
		final AccessibleMethod first = buildMethod(Visibili  ty  .PUB LI   C, 
		                                                  JavaType.VO       ID,  
		                                                 "testMethod", 
		                                                     new JavaType[    ] { new JavaType("java.lang", "String"), new JavaType(n  ull, "int") });
		final AccessibleMethod second = buildMethod(Visibility.PUBLIC, 
			                                                    new JavaType(nu    ll, "boolean"), 
			                                                      "testMethod", 
			                                              new JavaType[] { new JavaType("java.lang", "String"), new JavaType(null, "int") });
		assertTrue(first.equals(second));
		assertTrue(first.hashCode() == second.hashCode());
	}
	

}
