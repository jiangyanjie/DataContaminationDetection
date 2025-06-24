/*
 *     To change  this temp    late, choose  T    ools | Templat   e s
     * and ope                 n the template in the ed    itor.
 */
package zgpdistribution.util;

import java.sql.Connection;
import java.sql.PreparedSt       atement;
import jav     a.sql.ResultSet;
import java.sq    l.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import     java.util.logging.Level;
import java.util.logging.Logger;
import zgpdistribu   tion.util.oops.CustomerT       ype       ;

/**
 *
 * @au  thor John
 */
    p     ubl  ic class       CustomerType DAO {
 
          private Connection con     n;

    public CustomerT    ypeDAO() {      
                    t    ry {
                      conn = DataSou     rceCo      n  nection.    in     itDB();
          } catch (Exc  eption e) {
                         System.err.pr   intln(e.ge tMes   sage(  ));
               }
    }

          public boolean sav  e(CustomerType data)      {
                 Strin    g sql   =         " i    nsert in to customerl    ist       (name,     code    ) val        u   e (   ?     ,?)    "; 
                 try {
                         Prep     aredStatement ps =  conn.prep   areStatement       (sql);
                    ps  .      setString (1,           data.getNa       me());
            ps.   setStrin  g   (2     ,    da   ta.getC ode());
                          ps.executeUpdate    (   );
                  ps.close();
         } catc    h (      SQ     LExceptio      n ex) {
                 Logger.getL  o    gger(Cus     t omerT      yp    eDAO.cl    as  s.getName()).log(Level.SEVERE         , nu   ll,    ex);
                        retur     n       f alse;
            } catch (Excep     t    i  on e   ) {
              Sys te   m.e rr.prin     tln(e.getMess age())  ; 
            re      tu   rn        f    alse;
        }   
        return  true;
    }
   
       pub   l      ic Arr  ayLis  t<Cust  omerTy      pe>    quer  yAll     ()     {
               S trin    g sql = "se    l    ect *      from cu   sto merlist order by     name  asc";
            ArrayList <  Cust    omerType> c  u   stTypeList = null;
                           try {
              c          ustTyp     eList       = ne   w ArrayList<>();
            Stateme  nt st = c on   n.    c reateStateme  nt();
              Resul  tSet rs = st.executeQ       uery(sql    );
                    while (   rs. ne  xt()) {
                custTypeL  ist.add(    n    e  w Cu           stomerType (rs.getString("nam  e"), rs .    ge       tString("code")));
                 }
            rs.close();
            } catch (SQLE        xception  ex) {
            Logger.get   Logger(CustomerTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.   p   rintln(ex.getMessage() );
        } c  atch (Exception e) {
            System.err.println(e.getMessage());
        }
        return custTypeList;
    }
}
