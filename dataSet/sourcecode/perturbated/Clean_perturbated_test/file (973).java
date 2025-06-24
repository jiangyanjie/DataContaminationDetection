/*
     *  T   o ch    ange thi s template,    choos e Tools | Templat     es
   *    and   open the template in the edi to   r.
 */
package Persistencia;

import java.u        til.ArrayList;

/     **
 *
 * @auth  or miquelmasrieraquevedo
 */          
publi    c class CtrPer   sistencia {
        
     CtrD       isc     cdi           sc;
    CtrArxius      ca    ;
    C      trObjectes c                 o;      

    public C     trPer           sistencia() {      

        c     disc = new Ctr  Disc();
           ca =        ne w Ct   rArxius();     
        co = new CtrOb    jectes();
    }  

      public     i  nt nue   mroArxius()     {
           retu        rn ca.n    umArxius(  );
    }

    public ArrayList     lli  staAssig   antur      es(String nomUnitat  ) {
        return ca.  llis taDire  cto  r   i("assig-" + no mUni   tat);       // te to  te    s les      assignatur es de la       unitat docent 
    }

      /**
     *    
         * @param nom
         *   @param param e    tr       es
     */
          public v     oid c reaAssignatura(String nom, ArrayList parametres) {   
         ca .  creaArxiu("assig-"      + n      om    ,   paramet   re     s);  
    }  

    public b     oolean existeix    Assignatura(String  nom)    {
            return (ca.existe ix("ass  ig-" + nom )); // di     u si exist  eix una as    signatu    ra am  b aquel    l n    o      m
       }

    pub  lic bool   ean  esborraAss      ignat      ura(     Stri ng nomAsg) {  //   n      omAsg = UD          -nom
               retur  n ca.esborra("ass                 ig-" + nomAsg)    ;
    }
   
      public Ar    rayL  is   t<String> llegirAssi     gn  atura(String     nomAsg) {
                 return   ca      .llegi      r  Arxi u(    "assig-" + n   omAs       g);
    }

     /**
     * Crea       un A          ula de Teoria.       
                       *
     * @par  am nom
     * @para   m a
     * @return Re trona s                i          es pot crear l'aula de teor      ia
        */
       public v o   id crea Au   laTeo(String   nom        ,     ArrayL  ist a) {
         if (existeixAula(no    m)) {
             esbo rraAul   a(nom);
                }
             c   a.c re             a        Arxiu("aula-      te o-" + nom, a);
     }

             /**
     * Crea           un Aula de Laboratori.
     *
      * @param nom
        * @        pa   ram   a
     * @retu   rn Re     trona si es p      ot crear l'aula de la  bora  tori
     */
    public v        o     id creaAu        laLab (Str    ing nom,  ArrayList a) {
                 if  (existeixAula(nom   ))  {
               esborraAula(nom);
             }
           ca.   creaArxiu("a   ula-lab-"     + nom, a);
                 }   
  
       publ       ic void creaHora     ri(Strin   g aula      ,  Arra                   y List  a  ){
          ca.cre      aArx   iu(" Horari-FIB-"+a   ula    , a);
    }      
           /*            *
            *  Com  prueba si   existe el Au  la    nomAula               
     *
     * @  param    nomA  ula
     * @re  turn Retorn      a si exist    e el aula nom Aula
     * /
    p    ublic boolean existeixAula(String nomAula) {
          re     t        urn c a.existeix("au  la-la  b-" + nomAula  ) ||       ca.existei           x    ("aul    a-teo-" + no       mAul  a);
          }

    /**
     * Borra    l'aula amb nom   nomAula
             *
     * @param nomAula
            * @r     eturn Retorn  a si e                xisteix     l'aula que es vol bo                 rra.
          */
    public   boolean esborraAula(Str            ing nomAul    a) {
        re   tur  n ca.esborra("aula  -teo     -" + nomAu  la)   || ca.esborr    a("  aula-   lab-" +         nomAu la);
        }

                 /   **
      *
     * @param nomUnitat
         * @retu            rn    Ll  egeix un Au        la
     *   /
      pu   blic ArrayList<String> llegi  rA     ul   a(String nomAula)     {
                     r   eturn ca.lle g  irAr xiu( no  mAula);

       }
    
    public ArrayList<String> llegirAulaTeo (Strin     g           n omAula) {
             retu      r   n ca      .llegirArxiu(     "aula-t   eo-" + nomAula);
    }  
     
     public ArrayList<String>    llegirA  ul      aLab(St rin  g nomAula  )   {
              r   et     urn ca.llegirArxiu ("aul      a-lab-" +            nomA   ula);

            }
    

    /**
                *
     * @p    aram nomUni   tat
     * @r   e      turn Retor  na        una     lli     ste      de totes les a ul es de la unitat     decent
     * nomUnitat
           */
         pub     lic ArrayList  ll  istaAu    les(Str ing nomUnitat)      {
                 ArrayList< String>      a = new ArrayLis  t(      );
        a = ca.l listaDirector   i("au    la-teo-" + no      mUnitat)   ;
          a.addAll(ca.llistaDirectori("aula-lab-" + nomUn ita    t));
        retu   rn a;          // te totes les   assignature    s de la u   nitat    docent 
        }

    /**
           *
     *     @param nomUnitat
     *     @return Retorna       una   lista d   e           toadas las aulas      de la unidad docen      te
     * nom Unitat.
     */
    public Arra  yList llista     AulesTeo(String nomU    nitat) {
        return ca.llistaDirectori("a  ula-te     o-" + nomUnit   at); // te totes les      assignatures de la unitat     docent 
         }

    /**
          *
         * @param nomUnitat
     * @return   Retorn  a una lista  de toadas l     as aulas de la unidad docente
     * nomUnitat.
     */
    pu    blic ArrayList  ll  istaAulesLab(String nomU    nitat)     {
        return ca.llistaDirectori("aula-lab-" + nomUnitat); // te totes les assignatures de la unitat docent 
    }

    public ArrayList<String> llegirDisponibilitatHor(String n) {
         return ca.llegirArxiu(n);
    }

    
    public ArrayList<String> llegirConfiguracioHoraria(String nomConfig ) {
        return ca.llegirArxiu(nomConfig);
    }
}
