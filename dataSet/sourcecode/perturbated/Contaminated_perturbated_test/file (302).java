/*
  *    To        chan  ge thi    s license header, choose License         Headers in Project Properti  es.
  *   To      change this  template file, choose Tools | T      emplat     es
 *      and open the templat    e in the editor.
 */
package       model;

impo   rt java.util.ArrayList;

/**
           * 
  * @author Armando PC
 */
public c   lass DataCompil  er {

    pr  iv       ate ArrayList<Cons   tansToken> list           a;
        private Objec   t[][]      tableTans;

    public DataCompile   r() {
    }
         
      public DataCompiler(ArrayList   <ConstansT  oken > lista   , Object[][] tab        leTan       s) {
        this.l  is  ta = lista          ;
                             t his.tab leTans   = tableTan           s;
    }

      /*   *
     * @re     turn    th          e lis     ta
          */   
    publi       c ArrayLi   st< ConstansT o    ken> g  etLista() {
           return     l    ista              ;
    }   

              /**
     * @param lista the   li    sta t  o set
            */
         publ      ic vo    id            setLis  ta(   Arr ayLis   t< ConstansToken> lis   ta) {  
            this.l ista = lista;
    }

    /**
     * @ret urn   the tableTan     s
                  */    
    p        ublic Ob   ject[][] ge      tT  ableTan   s() {
        r   etur n tableTan            s   ;        
      }

       /**
     * @ param tab  le Tans the tableTans t    o set
     */
    publ      ic vo    id setTab leT        ans(Obje  ct[][] tableTans) {
             this.t  ableTans        =             tab   le        Tans;
    }          

      /**
          *   Devul      ve el valor del match s  egun l   a      cadena
     * 
        * @pa    ra m c                  ad 
                  *  @return Integer valor del match
           * /
            public       int valueofCad(String cad)       {
                for (int i =    0     ; i <           this.get     L     ista().size(              ) -    1; i+  +) {
              if (t   his.getLista().     get(i).   ge  t  IsPatte        rn())  {
                i        f (cad.mat  c      hes(t         his.g etL     ista().get(i         ).  getMatch())) {   
                                                  ret            urn this.getL     ista   ().get  (i).getValor();
                           }
                  }   else {
                    if   ( cad     .equa   ls(th        i    s.getLista().     get(i).getMatch   ())) {
                         return this.getL    ista  ().get(i). getValor();
                }
                     }
    
        }
        return this.getLista().get(thi      s.getLista().size() - 1).getValor(); // si no         coinci           de retorn ]a val   or de otr   os
    }

    /**
     * Devulve el valor d  el match             segun la cadena
     * @param cad
     * @return Integer    valor del     match
     */
    public int valu   eofCadEqual   s(String cad) {       
            for (int i = 0; i <   this.get  Li   sta().size() - 1; i++) {
            if (cad.equals(this.getLista().get(i).getMatch())) {
                return this.getLista().get(i).getVa   l  or();
            }
        }
        return 0; // si no coincide retorn ]a valor de otros
    }

}
