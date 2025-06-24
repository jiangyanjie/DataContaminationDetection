/*
 *      To change this template, choose Tools | Templates
      * an    d open the template in the edit    o   r.
 */
package proxyspeedtest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
im  port java.net.Proxy;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**   
 *
 * @auth   or Edwin
 */   
public    class C onnectionManager   {
    //private String      serverAddress = "http://twistedcablestestingground.freehosting.com/html/hea      der.ht  ml";
    priv     ate String           s  e   r  v   erAddress    = "http://www.googl  e.          com";
                     
    pu    blic boolea         n conne    ctT          es   tSe       rver() {
                      try {
                   URL url = new URL(ser   verAddres    s);
                      //url.ope nConnect     i       o   n();
                     HttpURLConne   ct       io  n        urlCon           n  = (HttpURLConnecti   on)       url.openConne    ction();
                   //urlConn.connect();
                       //urlC         onn.d            i  sconnect();
                     
                       if       (url    Co    nn.ge   tCo         nten            t()           !      = null)  
                                                              return t      rue;
                 el    se
                    retur    n fals   e     ;
                 //   as    sertEq  uals(Ht    tpURLConnection.HTTP_O  K, urlCo   nn.getRespon  se    C    o   de());
              } c atch (IOExcepti      on e)  {
                                   Sys              t em.e   rr.prin     tln("E    rror creat      i     ng HT  TP c    onn             ection");
                                    r  etu    rn                  fals e;
         }
    }
    
          public S    tri  ng         t   estP  roxyServers()                    {
                       File f =  ne   w File(" p   rox     y     _list.txt");
                   
                if (!f.exi   sts())         {
              re      turn "F  ile p       r   oxy_li st.txt   no  t   fo   un  d.      ";
              }
        else {
                         Str    i ng  s           =        "";         
                                              To              pProxie       s tp = ne  w TopProx     ies();
                          t   ry {
                          Buffe    redR     e a    der        in =          new            Buffe   redReader(new F ileReade     r(f   ));
                                     
                                           whil e   (true)     {
                                                                     St    ring        line =          nul l     ;
                                   try            {
                                                  line  = i      n.r         eadLine();
                                                }        catch (IOExcept          ion e  x) {
                                  Lo g    ge           r.g     et          Logger(Connecti   onMana  ge             r  .    class.get    Name()).log(Level.SE      VERE, null,      ex   );
                                     }
                                 if   (line ==           null)
                            b     reak;
                                      else  if      (line.star              tsWith("#") | |    li        ne.com  p            a  reTo   ( "")             ==         0)
                                   cont  inue;
                                        
                                 Stri    ng[] socke    t   Part = line.split(":")     ;
                  
                                           ProxyTester pt      = n  ew    Prox         yTest     er(new Proxy(Proxy.Type.HTTP,    new I n  etSocke     tAddre   ss(soc  ketPart[0], Int     eger.parseInt(soc    ketPa    rt[1] ))))    ;
                                  
                             pt   .testPr         oxy();
                             
                            if (pt.getT    ime() != 0)
                          tp.e   valuate     Socket(li    ne, pt.get Time());       
                     }
            } catch (FileNot FoundExc             e           p   tion e     x  ) {
                   Logger   .getLogger(ConnectionManager  .class.ge  tName()).log(Level     .SEVERE, null, ex);
            }   
            
            s = "";
             
            String[] t    opSockets = tp.    getT         opSockets();
            fo   r (int i = 0; i < 5; i++)
            {
                      if (topSockets[i].compareTo(" - 0"    ) != 0)
                   s +   = (topSockets[i] + "\n");
            }
               
            if (s.compareTo("")  == 0)
                s = "No usable proxy found."; 
           
            return s;
        }
    }
}
