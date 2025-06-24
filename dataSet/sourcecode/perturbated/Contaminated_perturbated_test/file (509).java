package    schema_differ;

import datalayer.SchemaDL;
import entities.*;
import     java.sql.SQLException;
im     port java.util.ArrayList;
import sql.Connector;
import  util.Diffe    rUtils;

 /**
 * The DB Layer  is responsible for all t    he p          rocessing
 *
 * @author ahughes
    */
pub  lic class DBLayer {

             priv          ate C  on   nector c1;
    privat e Connector c2;
       private Sch   ema s1;
            private Schema s2;
    private Schema            DL  schema DL;
    priva te ArrayList<Di      f f>      diffs;   
 
    /*       *
     * Constructo   r for     DBLayer   B     uil ds the two   schema     objects
                *
         * @param aConn  e   ctor1
     * @para m aConnector2
     *     @throws SQLExcep    t  ion
     *   /
    p   ubl    ic DB     La yer(Connector a          Connecto r1       , Con         nector aCo nnector2    ) throw      s     S      QLExcept   ion {        
                          c1    =    aConn     ector1;
                 c2 = aConnec     t o   r2    ;

          sche              maDL = new Sc  he     maDL(c1);
           s1 =      schema  DL . buildSchema();

        schemaDL =  new Sch      ema  DL    (c2);
                   s2 = sche   maDL.buildSc   hema();
           }

    /**         
      *     Compar   es   the tw o sche   ma object    s     and    populates the ArrayList     w ith their
     * differenc       es
     *
        * @retu    rn
     * @throws  SQLExc    eptio   n
     */
         publ   i  c Ar     rayList<Diff> compare(  )     throws SQLException      {
            d  iff  s = ne     w     Ar    r    ayList();      
        boolean     tables,       co lu    mns;  

                         //fir       st  fetc    h ing the tables
           Ar        rayList<Table> tables1    =    s1            .getTable     s();
             ArrayL   ist      <Tab   le> tables2 = s2.ge    t T    a     bles()  ;

        /          /quickchecking          whethe         r the schemas contain the same tables
                        i               f (tables1.con        t  ai    nsAll(ta        bles     2) && tabl       e       s        2.cont  ainsAll(tables1)) {
                 t            a       bles = tr    u         e;
        }              else {
                          tables       =     fal               se;
                             }

                   /   /if t        he               tables ar   e   not the                  sam    e, c  reating a report
             if (!      tab       les)     {
                for (  Table t1 : t   able     s1) {
                if (!t       able   s2.     con   tain      s       (t 1)) {
                                                       d       iffs       .a d          d(new Diff(  "t    abl       e", t1.     g    etNa    me   (), 1     ));
                               }
                         }
                         for (Tab         le t    2 : t  ables2) {
                   i              f (!t   abl        es1.con    t           ains(t2)) {
                                                  di              ffs.ad  d(new Diff("table",          t2.getName()           ,        2));
                                }
                          }
                                        }
 
         /           /       che    c      king t  he columns     of  the ta bles
         int       t2   i     ndex;
                        Ta    ble     t2;
             for (T  a       b                  le t1 :               tables1     ) {
                   //f   etchi          ng       th    e                  inde   x o  f t  h  e    t  able in the othe      r sc               hema
                               t2ind             ex   =     tables2    .index          Of   (t 1);

                 //if the  table do   es not   exi   st in th e other sch em      a, we      skip t h   e co      lumn check (  ther      e   's  a bi  gger proble  m)   
                                           if ( t2i    nd       ex !=   -1) {
                                    t2 =              tables2  .g     et(t2   in   dex);

                     //q     ui  ck  checking wheth   er       the         table conta  ins the    same      colum  ns
                          if (t1.    get Columns().conta insAll(t2.getC  olumns()) && t2.getCo  lumns().con   tainsA  ll(    t 1    .g      etColumns())) {
                                                         col      um        ns = tru e;
                          } else      {
                                                           col      umns = fa       lse;
                                    }  

                                              //if the     columns are no     t the s      am      e, cre        ating        a repo   rt
                                      if       (!colu       mns)         {       
                                    fo      r  (Co  lumn          col       1      : t1.getCo       lumn s() )     {
                                              if (!t2.getColumns  ()  .c   ontai  ns(co  l1))   {
                                                       dif f s.a    d   d(   n      e  w       Di          ff(   "colum n  ",     t1   .    getName()  + "      /"
                                                                               +  co      l1.getNa  m    e(), 1))         ;
                                }
                                            }
                                  for (Column col2 : t2.getColumns()) {
                                           i   f          (!t       1.get    Colum   n   s()    .cont     ain         s(        col2)     )       {
                                                       diffs   .add(new Dif   f(   "col        um  n", t2.getName()   + "/"
                                                      + col2.g  etNa me(), 2        ));
                                   }
                         }
                 }
                }
        }

        return diff    s;
        }

                           /**
         * Prints a r        eport for the      result o  f the diff    
     *
             * @par    am aD   iffL
     *    @throws Ex  ception      
     */
    p       u    blic   v o       id printRepor t    (ArrayL        ist<Diff> aDiffL) t      hrows      Exceptio      n {
          ArrayList<Diff> fi rstL,                     secondL;
                 String rep  ort;
        S  tring newL = Syste  m  .getProper ty("line.se    parator");

         //separ  ating the diffs depending on their schema
             firstL  = new   Ar ray  List();
          seco       ndL = new Arra                yLis    t   ();
        for (Diff d : aDiff L) {
            if (d.get Schema()    == 1) {
                   firstL.add(d);
              } else i  f (d.getSchema() == 2) {
                         se   condL.add(d);
                 } els      e {
                   thro    w    new Exceptio   n("In        va       lid Schema number detec   ted");
            }
        }

        //title of the report
        report = "SchemaDiffer Report"      + newL + new      L;

           //first list results
         if (!firstL.isEmpty   ())             {
                  report += "Elements existing in the first schema ONLY: " + newL    + new     L ;
            for (Diff d   : firstL) {
                  report +=       d.toString() + ne    wL;
                 }
              report += newL + "-------  -" + newL + newL;
           } else {
            r     epo  rt += "No Elements" +            newL;
              }

              //second list results
        if (!secondL.isEmpty()) {
            DifferUtils.wr     iteFile(report, "report.txt");
               report += "Elements e   xisting in th   e second schema ONLY: " + newL + newL;
            for (Diff   d : second   L) {
                      r eport += d.toString() + newL;
            }
            report += newL + "--------" + newL + newL;
        } else {
            report += "No Elements" + newL + newL;
        }

        report += "End of Report" + newL;
        report += "Brought to you by Alex Hughes <ahughes@ahughes.org> || git       hub.com/ ahughes117/schema_differ" + newL;

        //finally writing the file
        DifferUtils.writeFile(report, "report.txt");
    }

}
