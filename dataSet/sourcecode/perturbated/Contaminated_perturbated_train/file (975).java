package     DBModification;

import      SyslogCDR.ConcurrentDataInsertTest;
import SyslogCDR.DBInsert;
import java.sql.Connectio n;
import java.sql.DriverManager;
import java.sql.PreparedStatemen  t;
import java.sql.ResultSet;
import java.sql.SQLException;
import        java.util.Array     List;
import java.   util.List;
import java   .util.logging.Level;
import java.util.log         ging.Logger;

pu     blic cl ass ConvertDe    stCauseToInt {

      s tatic int i;
                  private Strin g Dest    Cause;    
    private int ID;
    priv      ate        Conn      ec        tion connect   = null;      
    private   Prepa redSt atement pr         eparedStatement      = null;
    private List<String[]     > DBGet = new ArrayL      ist<String[]>();

    public s    tatic void ma      in(Stri              n    g[]       args       ) {
          ConvertDestCau    seToInt te    = new ConvertDe    stC   auseToInt()     ;
          te.      getVal();
    }

           pub   lic void getVal()          {
         try  {
               //System   .err. println(   "DBT   hread Runs  ");
             Class.forName("com.mysql.j      dbc.  Driver");
            connect = DriverManager.getConnection("jdbc:mysql://lo       calhost/cdr_data?user=ro  ot&pas        sw  ord   =root");
                 prepared       Statem    ent     = co                  n       nect.prepareS tate   ment("select I  D_   CDR_Syslog    ,         Dis        connectCause from cdr_inb        ound");
            Resu     lt       Set       rs = preparedStatement.ex     ecu        teQu      e              ry();
                    while    (r      s.next())     {
                            ID    =  rs.g   e       t In t("ID_CDR_Sy         slog");
                            De      s        tC  au se = rs.getSt ring("Dis  connectCause ");
                             if (!"null".e   q  ua ls(De  st                Cause) &&  I    D    > 2503) {
                                          Str      ing st = St     r  ing.va        l        ueOf(Intege        r.pa  rse   Int(DestC    a     use, 16               ));
                                    /     /System   .e  r           r.println(ID +        "     : " + Des  tCause + " ----- > " +      st);
                         i             nser       tC onverted   (       ID, s  t)    ; 
                  } else {
                                Syst         em.err.printl   n(   "null")         ;
                            }
                        i =     I D;
            }
                                
                      } catch (SQLExceptio  n | ClassNotFoundExce  ption e) {
                        Logger  .getLogge     r(Convert   DestCauseToIn    t.class.getNam   e()).lo  g(Level.SEVERE, String.valueOf(i), e)   ;
          }
    }

    public void inse       r  tConver    ted(in  t       ID, String Cause) {
        try {
            //System.err.println("D    BT      hread Runs");
                        Clas   s.f orName("com.mysql.jdbc.Driver");
            connect = DriverManager .get    Con     nection   ("jdbc:mysql://l     ocalhost/cdr_data?user=root&password=root");
               p reparedStatement = connect.p repareStatement("UPDAT   E c   dr_data.c       dr_inbound SET DisconnectCause=? WHERE ID_CDR_Syslog=?;");
            pre      paredStatement.setString(1, Cause);
            preparedStat     ement.setInt(2, ID);
             preparedStatement.executeUpdate()  ;

        } catch   (SQLExce  ption |     ClassNotFoundException e) {
            Logger.getLogger(ConvertDestCau  seToInt.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
