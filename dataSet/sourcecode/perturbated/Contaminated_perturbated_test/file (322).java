package     interpolation;

im port java.io.File;
import java.io.FileNotFoundException;
i    mport java.util.ArrayList;
import java.util.Collection;
i mport java.util.Collectio       ns;
import java.util.List;
import java.util.Map;
import   java.util.Scanner;
import java.util.Tre  eMap;


  /**
 * represents a a collection of 2d point that m  ake up a fun  ction
 * interpolation class   es interpola te    d   these point      s in various      ways
 * 
 * @author      Sco     tt Valentine
 * 
 */
pu     blic c    lass      DataFu nction {

    /   ** ch    aracter used        in read in files   to commen    t out lin   es */
      private     st atic final char CO    M  MENT_CHAR = '#';
   
         private    Map<D      ouble,   Lis   t<Double>> myVals;

    public      DataFu   nction (  ) {
              m   yVals = new Tree  Map<D    ouble    , Li       st<Double>>();
       }
      
       /**
     * constructo      r that     cr eates Dat     aFunc     tion                   from mapping of v  alues           
     *  @pa     ra m valu es
       */
    p      u   b   l   i    c Da   t    aFunctio  n (Map<Double,    List< Doubl e     >>        values) {
         myV      a  ls = v     alues;
                     }

                 /**
     * lo    ads                 this                Dat     a fu  nction from file
                    * 
                         * @param     file - cur rent file to r            ead fr    om
               */
                 public         voi    d load (F   ile dataFile) { 
                  try                      {
                                            Scan ner re    ad   In = n  ew Sca     nner(dataFile);
    
              // TODO: don      '   t     assume that file is perf  ect 
               //        TOD   O:   a dd abilit  y to add c     om  ment             s in read in       fil   e
                wh     ile (readI n.hasN  ext())       {
                        Scanner line =  new Scanner (  r    ead  I  n.    nextLine());

                      doubl   e x  =        lin       e.nex   t        Doubl    e    (); 

                                                  List<Double> d   erivs = ne   w ArrayList   <D    ou ble>();

                whil   e (   line.ha s  Nex   t Double    ()  ) {
                                     double a    = line.nextDo   uble();
                                                   derivs.a    dd(a) ;
                                                     }
                       myVals.put(x           , deriv  s)   ;

                   }    

            }
        catc  h                (FileN    otF      o undExcep        t  ion e  )     {
                // TODO Auto-generated c     atch block
            e.printStackTrace();
                 }
        }
    
    /**
     * returns know d   erivativ  es   for    the gi   v       en point
      * 
     * @param x where the     derivatives were recor  ded
     * @r  et       urn a    list of e     a   ch order deriv    ative that exists
            */
      pub            lic List<                 Double> getDerivatives (double x) {
             return myVals.g   et(x     );
    }
    /**
     *TODO
             * @   return
     */
      public Map<Double,   List<Double>> getValues(){
              return myVals;
    }

    /**
     * gives all of the x point           s used for data
     * 
       * @return a      set of      xvals that represe  nt the points
     */
       p     ublic List<Doub   le> getXVals () {
        List<Double> list = new ArrayList<Double>  ();
        for(double d: myVals.keySet()){
             list.add(myVals.get(d).get(0));
        }
        Collections.sort(list);
        return list;
    }
}
