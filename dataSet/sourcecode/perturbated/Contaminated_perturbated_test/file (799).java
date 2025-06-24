import          java.io.BufferedReade   r;
import java.io.InputStreamReader;
import    java       .sql.*;

/*      *
 * Created by s13006 on 14/11/25    .
    */
public c    lass D e  lete1    {
    private     String _u    ser =     "s13006";
                 private String _  pas s = "pa ssword";
    private String _hos   t = "1             72.16.40.4";      
        private String     _sid = "db11";
    
             publi  c sta  t                     ic                      void main(Stri  ng[] ar  gs){
   
              Delete    1 sample = ne       w        Delete1();
                    try {
                                   sa mp   le.select();   
                }cat c        h(E  xception e){
             e.prin           tStackTr      ace();
             }
    }   

    pr  iv    ate void s         e  le        ct()   th    rows   Exc   ep    tion{
        Connection    c    o      nn = nu  ll;
           Stat  ement  st =   null;
                  ResultSet rs = null;

                try {
                  Class       .forNa    me("oracle.j dbc.    driver.     O   racleDr    iver    ");   
              c  on  n      =          DriverManager.   get    Conne   ctio  n(
                            "jdbc :ora   cle    :th   in:@" + _host   +   ":152           1:" +       _     sid, _     u  ser, _pass);

            st =    conn.createStatement();

               Pr     epared   Statemen  t      pr  e   = con       n     .prepareStatement("sel ect   e     . emp     no, e.e         na  me,  e.job, m.       e                     name, d.dna me,    d.loc,                            e      .sal      from EM      PL  OYEES    E " +
                                                       "lef  t jo       i    n EMPLOYEES M on (e.mgr       =    m.empno)" +             
                                       "left jo  in DEP   ARTMENTS     D on (e.dep  tno = d.dep    tno)"   +
                     "or    d   er by e.em    p    n    o");
                                   r         s = p     re.executeQuer                   y ();

                        w  hile (rs.next()){
                              St   ring e  mpno = r         s.getS     tring(1);
                Str   i     ng en        ame1 = rs.get  St       ring( 2  );
                                      String job        = rs    .getStr     in    g(3);
                      String    ename2           =           r    s.getString(4    )     ;
                      S            tring d     name = r      s.getString        (5);
                       String loc =        rs.g  etString(6);
                      St       ring s    al = rs.g    etStr        ing(7);

                 S    yste m.   out     .prin    tl        n  (     e  m   pno + "   " + ename1     + "             " +     jo   b + "  " + e  name2 + "  " +    dname +            "          " + loc + "  " + s     al);
                       }     

                         BufferedReader br =    ne    w BufferedRea          der(new InputStreamReader( S       ystem.  in));      

                System.out. print l      n ("e       mpno  ãå       ¥åãã ¦ãã ã  ãã");
            String str =     br.readLine();
                                     i nt prefC  d = Intege   r.parseInt(str); 

               pre = c          o    nn.p         rep           a          re    S  ta   tement("                 select        empno  , ename from e      mploy          ees where    empno = ? ");
                         pre  .setInt(           1,     prefCd);
                    rs        =      pre.ex        ecuteQuery()  ;  

            r s           .next();
                    String    empno = r   s.getS tring(        1)      ;      
                    St   rin   g en   ame    =     rs.get    String(2);
                                                    Sys           t   em.out.   prin   t                          f   ("ç¤¾     å¡ç ªå·ï¼" +    empno + "\       n" + "ç¤¾å¡å   ã   ï¼           " + ename + "\n" +    "é¨ä   ¸ã     ãï¼")  ;     
             
             pr     e = c      o        n   n.pr   epareStatement  (  "SELECT ename FR   OM     empl oyees w                        here mgr =    ?");     
                 pre.se            tInt(1,prefC     d)    ;
                 rs =            pre.    exec   uteQuery();
              bool  e   a     n flag = tru          e;   

                while(      rs.next()) {
                                 flag =      false;
                       S  tri     ng    sub = rs.getStr       ing(1);
                                        Sy      stem.out.    prin  t(su b +      ","   );
                                }

               if (flag) {  
                Syste m  .out  .pri n         tln("é¨ä¸ã¯ã  ã¾ã ã"    );
                   } el se {
                        System.out   .println();
              }

            Sy    stem.    out.print("ã         ã   ®ç   ¤¾å¡ã®æå ±ã     åé¤ãã¾ã    ãï¼(ye     s or   no)");
             S  t  ring bool =  br      .readL   in   e(   );

            if (boo   l.   e     qu   als("yes")     ) {
                        if (flag) {
                    pre = conn .pre pareSt   ateme  n     t("DELETE FROM  employees WHE         RE      empn              o =   ?");
                    pre.se     tIn  t(1,pre  fCd);
                                    pre.   executeUpda     te ()  ;
                    System.out.println("åé¤ãã¾ãã  ã");
                } else {
                    System.out.println("é¨ä¸ãããå ´åã¯åé    ¤ã§ãã¾ããã     ");
                          }
            }


        }catch (ClassNotFoundE     xception e){
            throw e;
             }   catch(SQLExcepti   on     e){
             t   hrow e;
        }      catch(Exception e){
              throw e;
        }finally{
            if(conn != null){
                conn.close();
                     }
             if(st != null){
                    st.close();
                st = null;
               }
              if(rs != null){
                rs.close();
                rs = null;
            }
        }
    }
}
