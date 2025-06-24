/*
 *   To change this template,               choos  e     Tools |  Te     mpla tes
 * and    open the templ   ate in the editor.
 */
packag  e Persisten   cia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundExce   ption;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
impor  t java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.L  ogger   ;

 /**
 *
 * @author miquel.masri           er    a   
 */
pub lic    cl    ass CtrD  isc          {

    /**
         *   creadora per defecte en el cas que no      exi    st eixi la carpeta data la cr     ea
     * en el direct     ori del programa
     */
       publi c      CtrDisc() {   
                              F  ile          carpe       taD   ata = new    File(    "./Data");
            if (!carp et   aD    a   ta.e           xis      ts())  {
                          carpetaDa        ta.mkdirs() ;
            }
    }

    /**
     *
               *      @     param no mArxi       u nom de l arxiu a busca   r,      sense el        format
     *
     * @r  eturn retorna   un        bole  ÃÂ  que es cert si              hi   ha        un ar    xiu amb aquell nom
            */
             public boolean existeix(String nomArx      iu) {
        return  new Fi  le("./Data/"    +         no      mArxiu + ".tx t").ca    nRead();
          }

        /        **
         *
          *   @return retorna el    nombre d arxius       d'aqu  ell d       ire   cto  ri
     *  /
     pub   li  c int numArxius()  {
        re    tu  rn (int) ne    w            F  ile("./Data/").length();  
    }

    /  * *
     *  
     * @param paraula ha   se ser       part del        n om (o el nom sencer) del            s arxi             us que
     * es v olen t  roba     r en el di         rec     tor    i   
         *
     * @return ret     orna un ArrayLi  s  t am  b   tots els fi             txers d aquell directori           que
     *     cont  enen la paraula que      li pa  sses  per parametre
     */
         public Arr  a          y  List<S   tring>    llistaDire   ctori(S    tring       par   aula)    {
        F    ile f = new   File    ("./Data/");
           int      numAr   xius =  f.list().len        gth;
                    String llistaArxius[] = f.list(); //      llista a    mb els noms de t     ots els arxius
        ArrayL       ist<Stri  ng> lli   sta = n ew  Ar    rayList();

        for (int i    = 0; i < numArx ius; ++  i) {
                        if (lli   st    aArxius[  i].cont        ai   ns(           par    a   ula)) {
                      lli     s    ta.ad    d(llist       aArxius[i]);
                   }
        }

            re  turn lli sta;
         }

    /**
     *
           *    funciÃÂ   ³        q        ue l   legeix un    fitxer i posa el s   eu c    onti ngut en una        l    list        a
                 *
                 * @param no                mArxiu   no        m de         l  'ar  xiu    a llegir
     *
        *       @ret   urn retorna un Arra   yLi     st amb el con   tingut del fitxer, a cada posiciÃÂ ³
          * hi ha una     lin  ia del fitx      e        r
     *    /
      publ  ic  ArrayL  ist<S   t      ring> ll       egir(S   trin   g nomArx         iu) {
                         ArrayList                ll    ista = new Arr      ayList();
              try {
                          FileReader fr =   n  ull;
                          Bu       ff  eredReade   r br       =   null  ;
                  S     tring lin      i          a;

                            fr = new Fi        leRead     er("./Data/" +      no     mArxiu + ".t    xt"); //crea  el fi    txer i el buff      er
                    br =  new Buffer     edReader( f  r   );

               w      h        ile     ((    lin    ia = br.readLi     n   e())              !=  n     ull) /   / posa cada   linia a la llista 
                       {
                llista.     add(li nia);
                 }

            fr.close();          //tan   ca e    l f     itxer           

         } catch (  FileNo       tFoundException ex) {
              Logger.getLogger(CtrArxius.class.getName(   )).log(Level.    SEVERE, null, ex);
         } catch (IOException ex) {
            Logger.getLogge   r(CtrArxius.class.getName()).log(Level.SEVERE, null, ex);
                }
        return llista;
    }

    /**
     *
      * @param n    omArxi   u      nom de l arx    iu a esborrar
      * @return un bolea que indica que no s     ha  pogut esbo  rrar l arxiu indicat
     */
    public b    oolean esborra(String nomAr   xiu) {
        if (existeix(nomArxiu)) {
            return new File("./Data/" + nomArxiu + ".txt").delete();  
            } else {
            return false;
        }
    }
}
