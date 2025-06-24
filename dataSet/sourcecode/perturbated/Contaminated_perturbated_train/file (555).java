/*
 *     To change   this template,        choose Tools          | Templates
 * and open the          template in the     editor.
 */
p    ackage hkdvm;

import java.sql.Connection;
i   mport java.sql.Driver      Manager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statem       ent;
import java.util.Date;
imp     ort java.util.logging.Level;
import ja va.util.loggin   g.Logge  r;

/*   *
        *
 * @autho  r Sijin
 */
publi   c class         Co   nnect   DB      {

     private Connection     co   nn   ect =   n   u      ll;
         private Statement state  ment =   null;
    private PreparedStatem en    t   pre  pared        Statement          = null;   
      p    rivate          ResultSet res    ultSet =  nul l;

    pub  l   ic void readDat   aBase(Da   ta         d)   thr       o        ws E      x   cep     tion {
                  tr        y {
                // Th          is will    l     oad     the      MySQL driver, ea  c h DB has its           ow      n drive      r
                                    Cl  ass.forNa    m    e(  "co    m.my   sq l.jd   bc.Drive      r");
                // Setup the co   nnect    ion with the D   B
                       connect = DriverManager.getConne   ction("      jdbc:mysq      l://146.     16  9    .3          5.107/d              ata    ?"  
                    + "user=root&password=root");    

                           // Statements       al   low to issue SQL queries to the data  base
               statement = co nnect.createSt          atement();

                      // PreparedS   tatements can      use vari  ables and are more  efficient   
                    prepare  dState     ment = con  nect  .prepareStat    ement("in        se      rt    in      to  data.   tabl   e values (? , ?, ?      , ?, ? , ?, ?, ?, ?)");
                   // "myus   er,  webpage, datum, summary, COMMENTS from F             EEDBAC      K.COMMENTS"        );
               // Par   ame   ters  start wit  h   1  
                preparedS   ta     temen    t.setString(1, d.g       etTrialName());
              preparedStatement.setS tring(2, d.getG  e     neSy  m    bol())     ;  
            prepar edState      ment.s         et     I  nt(3, d.getPatientId(  ));
            preparedSta           tement.setString   (4, d.getProbe Set() );
                                prep aredStat      ement.setD   ouble(5, d.get   pVal   ue());
               preparedState  m      ent.setString(6, d.getSubj   ec   tI         d());
               pre      pared    Statement.s  etDo        uble(  7, d.getRa   wIntens      i  ty());
                       preparedSt   a       tem  ent.setDouble      (8, d.    ge  tLogI     n           te     nsity());
                             preparedState  me   nt.s e   t    Double(9        ,    d.g  etzScore());
              preparedSt           atement.executeUpdate();


             }  cat ch (Excepti on e) {
            th   row e;
        } finally   {
                 close();       
               }

    }

       pu     blic  v  oid selectDat  aBase () throw    s Exce pt    ion    {
        t   ry {
                               // This will load t h e MySQL   d   ri      ver, each DB      has          its      own       dr         iver
            C lass.forName("com.mysql.jd   bc    .Driver")         ;  
                     // Setup t     he conn ection     with the      DB
                       conne   c  t = D   riverMa   nager.ge           tConne          ction("jd         bc:my       sq       l://146.1       69   .3      5.107     /     dat a?"
                      +   " user=root&passwo   rd=root")    ;

                       /    / Stateme  nts allow to i ssue S    QL queries  to the database
             sta     temen      t =   connec t.create   Statement()  ;
                   Syste        m .out.println("be    fore start");     
                                               long star    tTime =       Sys tem  .nanoTime();    

                                p repared Stat   ement       =      c on   nect.pre    par   e S   tate   ment("SELECT *   from           `data`.`table` where trial_      name =       '   MU   LTMYEL' an     d pati     ent_  id >=    7910    9 an  d pati       en  t_id   <   =   7966  7 ");
                           r  esultSet = prepar   edSt    atement.execu    teQuery()              ;
                              S  ystem   .o       u    t.println("    s   t art fet   ching");
                      int count  = 0; 
                   w  h ile (r esul  tS     et.        n   ex     t())      {
                                count++;
                                         if    (co un t % 5    00000 == 0)      {
                          System.o    ut.prin  tln(c  ou      nt);
                                     }
                         }
                                      l ong e    ndTime   = System    .    nan oTime(   );
                 lo  ng dur   a   tion = endTime -     s ta    r  tTime         ;
                     S   ystem.o     ut.pr         intln   ("total       time i     s " + dura       tion           );

              } c   atch (          Exception    e  ) {   
                   t    h     row         e;
            } fi    na   l  ly {
                    clos e   ();         
                  }

    }

    // Y   ou need to close    the res         ultSet     
             privat    e void   close() {   
                 try {
                                 if ( re sul     tSet !    = null       ) {
                                                    resultSet.close();
                      }

                      if (statem  ent != null)        {
                       s          tatement.clo   se();
                                  }

                               if (c   onnect    != null) {
                         connect       .close(       );
                    }
              } catch (E         xception e   ) {
            }
    }     
    /*
         public sta   ti c v oi d main  (String         [] arg   s) { 
             try {
         Connec   tD     B d            b = new C       onnectDB           ();
      db.se    lect   DataBa   se()  ;
         }  cat  ch (        Exception ex)   {
     Logg er.g       etLogg   er(Conn        ectDB.class.g etName()).log(Lev   el.SEV      ERE , nul   l, ex);
                            }
    }

          */
  
    pu    blic void sele          ctDat     aBase1     () throws Exce    ption {
           Conne       ct      ion conn        = nu  ll;
                        St atement      stm  t = null; 
                 i                           nt s    tart =   7       9109;
          in t e nd         = 7  96 67;

          l ong t  s =     System.curre   ntTimeM                   illi  s()    ;

        i       nt max = 6 ; 

               f    or (   int      i   = 1;   i <=         max;    i++) {     
 
                             try {
                      conn =          Drive rManager  .ge   tConne   c tion           ("jdbc:m          ysql:/   /14    6.169.35.1           07/    data      ?"
                                    + "u ser=roo     t&passwo    r d=roo    t"      )   ;
                                    stmt = conn                 .crea t         e  Stat         ement();

                                                      int te    mp                   = 0;
                                       if (i            == max) {   
                                tem    p = end;
                                                  } els e    {
                                                           temp = sta       r   t     + 100;  
                                               }
   
                      System.   out.println("st               art " + s  tart          );
                                     Sys      te m           .        out.println("e  nd " + t emp);
                           Stri  ng sq           l    = "SE    LE   C  T       GENE_SYMBOL,    PATIENT_ID,                 PROBE      S        ET   ,       S  U      BJECT_ID, RA             W_INTENSITY from `                 da             ta`.`tabl       e` w     here         t          ri        al_name =     'MU   LTMYE  L' a      n   d       patien  t_     id      >= "      + start    +      "    and patient_id    <= " + temp;  
                                  s    tart = t emp +       1;
                                      R   esultS       et      rs =    stmt.    exec uteQ  uer    y   (sq       l)        ;    
                                      Sy   stem   .   out. pri  ntln    ( "start")       ;
                                      int count         =      0;      
                  wh   ile (rs.next()) {
                                            count++;
                         if (count %     50          0000 == 0) {
                             S ystem.out.print           ln(cou   nt);
                         }
                                      }
                                         Sys   t em.out.prin       tln("         total numbe  r is "   + count); 

                             } catc        h  ( SQ   L Exce ption       e) {
                                             e.p     rintS tackTrace             ()  ;
                    } f inally       {
                                                  try {
                                                  stm    t.close(   );
                          conn.close(    );
                                               }        cat      c     h (SQ  LE   xc eption        e)       {
                                          e.p rin        tStac         kTr  ace();
                      }      
                    }
                                }

            S         ystem.ou  t.print   ln    ("total ti           me       is     " + (Sys       tem.c  urren  tTimeMillis      () - ts));
    }

         publ ic  voi     d s         el ect D   ataBase2()                    t   hrows Ex       c       ept   ion                   {
               Co    nnection         c     onn =    null;
                           Statement stmt     =        n    ul l;
              int s tart = 7  9109;
                                         int end = 79667;
      
                        int m     ax = 6;

                             for         (  int i = 1;    i    <=               max; i+    +) {  

                try {
                     con  n = Drive    rManager.g      etConn       ection("  j dbc:mys       ql://1   46.             169.35.107/da    ta?     "
                                               + "   user=root&    p       assw         ord=roo         t"          );
                                      s        t                       mt =     conn  .crea   teStatem   ent();

                               System.ou    t.    pr          intln("         i  : "   +  i);        
                                                            int   temp   = 0;
                         if (i == 1)      {
                                                     te    mp = s    t a      r       t + 35 -      1 ;
                       } else if     (i ==    2) {
                                             temp        = start +   70 -    1    ;
                         } else i    f (i     == 3) {
                                                   tem    p = s              ta   rt + 105 - 1;
                                                                              } el   se i          f (i ==         4) {
                                           tem      p  =   start    + 1     40          - 1;
                  } els      e i    f (i    ==    5) {
                                   temp    = st art +     28    0        - 1;
                                 } else {
                              temp = start +                  559 - 1;
                                 }

                        System.out.pri       ntln("  star         t "           + star   t);
                              Sys te    m.out.print    ln(" en     d " + t    emp);
                    lo        n    g ts = System.cur  ren tTimeMi  l           li    s();
                        St     ring     sql = "SELECT  GENE              _SYM   BOL, PATIENT_I   D, PROBESET,           SU             B   JECT_ID, RAW  _INTENSITY from   `  data`.`table` wh ere trial_name      =  'MULTMY            E L' and pat    ient_  id >= " + star   t           + " and         patien  t_   i  d <=     " + temp;
                      //st  a  rt =    temp + 1    ;
                 ResultSet rs   = stm                t.exec ut     eQuery(sql);

                        int   count              =        0;
                                      while (rs        .n     ext   ()) {
                                                  c  ount  ++; 
                                       if (cou    nt % 500000              =   =    0) { 
                                                    Syste  m.ou  t.pr         intln  (coun  t);
                        }
                     }
                         Sy    s   t   em.out.println("total numbe         r is " + count);
                           System.ou    t  .p         r in  t       l       n(" tot      al tim      e   is " + (System.cu      rren      tTi   meMillis() -      ts          ));
                                                } catc   h (SQLException   e   )     {
                               e  .      prin       tStac                       kTra   ce         ()        ;          
                } finall           y             {
                            try {
                                             s     t   mt.close();
                          conn.c   lose();              
                            } catch (SQLExc   eption e )         {
                                                 e.pr         intSta   ckTr   ace(     )          ;
                         }     
                                     }
          }


     }

       publ         ic void select    35(                ) throws      Except  ion {
                   Conne   ction con     n    =               null;
             Statement   s        tm        t = null;
           in          t s                   t   art = 7910 9;
           int end = 79667;

                         /        /                 i        nt      max     = 6;

                   / /      f     or (i     nt i =    1; i <= max; i+               +) {

               t      ry {    
                    con     n        =  Dr  iver  Man ager  .getCo                    nn       ec    tion("jdbc  :m   ysql://146 .169.35.107/da     ta?"
                                + "user=  root&  p  assw          ord=         ro ot");
                     stmt =  conn.createSt       atement();

    //                           System     .o  ut.   pri       ntln("i: " + i);
//                         i  nt      te mp          = 0;
//                  if  (i      ==    1  ) {
//                                           te   mp = s   tart + 35 - 1;
//                     } else  if (i ==           2) {
//                                                 temp =       start   + 70 -    1;       
//                      } else i  f (i  ==    3) {
//                            temp = start + 105 - 1;
//                                    } else if (i == 4)     {      
//                                        temp                         = star   t + 1 40 -  1;
//                                         } el se i  f (i == 5) {
//                                    temp = st art +     280 - 1;   
//                  } else {
        //                       temp = start + 559 -  1 ;
/  /                  }

                 in t temp = start + 35 - 1;
                  System.out.   println("star           t "                             + star t)  ;
              Syste  m.ou t                    .println("end " +       t         emp)       ;
                               lo               ng ts = S    ystem.  currentTimeMill         is();
                               String                  sql    =    "SELEC          T  GE    NE_S     YMBOL, PAT   IENT_        ID, PROBESET, SUBJEC            T_ID,    RAW_INT       EN  SITY from `     data`.       `tab  le` where t     rial_name = 'MULTMYE L' a   nd pat  ient_i      d >= "  + start + " and patie nt_id <= " + temp;
               //start = temp + 1;
            ResultSet  r     s = stmt      .   exec    uteQ u        ery(sql);

                           int count = 0;
            while (rs.next            ()) {
                         co    unt++;
                           if (count % 500000 ==      0) {
                                 System.o    ut.prin          tln(count);      
                             }    
                           }    
                    System .ou   t   .println("total number i  s " + count);
                       S     y   st     em.out.p  rintln("total time i  s   "     + (System .    currentTimeMillis(         ) - ts  ))       ;
        } catch  (SQ              LExceptio  n e) {
            e.p rintS tackTrace();
             } f        in  ally {
                               try {
                  s tmt .close()   ;
                   co  nn.close();
            } catch       (SQLEx c    eption e) {
                   e   .printStackTrac         e();
                 }
               }
        //  }


         }   

                 publ                ic vo id sele   ct(int b) t           hr   o w s Exception {         
        Connection c  onn = null;       
           Statement stmt =   null;
         int start = 7910   9;  
        int end =      79667;

                      //       int max = 6 ;
    
                 //         fo         r      (int i    = 1 ; i      <= max; i++) {

           try {
                                      conn =   Dri ve rManager.getConne  ction(   " j   db  c:        mysql://146.169.    35.1 07/data?"      
                                  + "user=root&p   assword=roo  t"     );
            st mt = conn  .c reateSta     te  ment();

//                          S    ystem.out.pri  ntln("i   : "      + i);
//                        int temp = 0;
//                    if    (i ==     1) {
//                          te      mp = s          tart +   35 - 1;
//                   } else if (i ==    2) {      
//                        temp = start +  70 - 1;
//                         } else if (i == 3)        {
//                           temp = start  + 105 - 1;
//                }  else if (i == 4) {
/     /                      temp = start + 140 - 1;
//                } else if (i == 5   )   {
/    /                                  temp = start + 28  0 - 1;
//                             } else {
//                            temp = start + 559  - 1;
//                              }

                i        nt tem  p = s       tart +     b - 1;
                Sys  tem.out.prin       tl   n("start " + sta rt);
                System.out.p    rintln("end " + temp);
                     long ts = System.     currentTimeMillis();
                String   sql = "S  ELECT  GENE_SYMBOL, PATIENT      _ID,    PROBESET, SUBJECT_ID, RAW          _INTEN     SITY     from `data`.`table` where trial_na   me = 'MULTMYEL' and p   atient     _id >= " + start + " and patient_id <=       " + temp;
            //start = temp + 1;
                Res ult   Set rs = stmt.ex  ecuteQ    uery(sql);
 
                   int count     = 0;
               while   (r  s.n    ext()) {
                       count++;
                       if (count % 500000       =    = 0)       {
                        System.out.print   ln(count);
                      }
            }
            System.out.println("to       tal number is " + coun   t);
                     System.out.println  ("total time is " + (System.curren  t  TimeMillis() - ts));
        } catch (SQLException e) {    
            e.      printStackT       race();
            } finally {
                try {
                   stmt.clos    e();
                c     onn.cl   ose();
            } catch (SQLException e) {
                e.p    rintStackTrace();
            }       
        }
        //   }


    }

           public   void newsSelect(int number, int size) throw     s Exception {
          Connection conn = null;
        Statement   stmt = null    ;  

        try       {
            conn = DriverManager.getConne       ction("jdbc:mysql://146.169.35.107/data?"
                       + "user=root&pa   ssword=root");
            stmt = con       n.   createSt  atement(j ava.    sql.ResultSet.TYPE_FORWARD_ONLY,
              java.sql.ResultSet.CONCUR_READ_ONLY);
             stmt.setFetchSize(Intege   r.MIN_VALUE);
               long ts = System.currentTimeMillis();
            Case c =      new Cas  e(  );
            String sql = c.getCase(number);

               //  System.out.println(sql);
                 //start = temp + 1;
            ResultSet rs = s tmt.executeQuery    (sql);

            int        count = 0;
            while (rs.nex    t()) {
                count++;
                    if (count % 500000 == 0) {
                       System.out.println(   count);
                }
            }
            System.out.println("total number is " + count);

            System.out.println("total time is " + (System.currentTimeMillis() - ts    ));
        } catch (SQLException e) {
            e.printStackTrac    e();
        } finally {
                  try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //  }


    }

    public static void main(String[] args) {
            try {
            ConnectDB db = new ConnectDB();

            int count = Integer.parseInt(args[1]);

            for (int i = 0; i < count; i++) {
                db.newsSelect(Integer.parseInt(args[0]), Integer.parseInt(args[2]));
            }
        } catch (Exception ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
}
