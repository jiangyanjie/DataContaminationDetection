package SyslogCDR;

import     java.sql.Connection;
im       port java.sql.DriverManager;
import     java.sql.PreparedStatement;
import    java.sql.SQLExcept  ion;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Le     vel;
import java.util.logging.Logger;

public      class DBInsert implements Runna  ble {

    private  final  String CallingPeerAddress, CalledPeerAddress,      SetupTime, ConnectTime, Disconne        ctTime,DisconnectCause;
        private     fin   al boo   l     e    an IsMiss   ed;  
        privat    e fin    al int Type     Appel;
    privat   e Connection connect = null;
    private P   reparedStat     ement preparedStatement = null;

     public     DBIns    e        rt(Str   ing CallingPeerAddress,          St  ring CalledPeer     Address ,
              String Se   tupTime, String ConnectTime, String Di   sconnectTime, boolean IsMissed, int TypeAppel,String Disco   nnectCause)     {
                this.C     allingPeerAddress = Ca    llingPeerAddre ss;
                 t      his.Calle   dP eerAddress = C  alledPeerAddress;
        this   .SetupTime    = SetupTime;
              t       his.    Conne  ct Time =      Co    nnectTim   e; 
        thi  s.DisconnectTim       e = Disconne      ctTi       me;
                 this.IsM  i  ssed =   IsM    issed;   
        this.TypeAppel = TypeApp  e   l;
             this.Dis    conn    ectCause = Discon   nectCause;
      }

             @Overrid e
    public    void r       un() {
             try {
                   /       /System.err   .println("DB       Thr   ead Runs"   ); 
            Class.forName("com.mysql.jdbc.Driver");
            connect = Driver    M     ana  ger.getConnectio  n("j  dbc:mysql://localhost/cdr_da t a?us e r=r oot&password=root");
                    prepar edStatement =  connect   .prepareStatement("   insert into  cd   r_inbound "
                    + "(Call    ingPeerAddr         ess,Ca   lledPee     rAddress,Setup     Time,C    onnectTime,   "
                               + "Disc onnectT   im    e,IsMiss     ed,Date   Ad         ded,T   ypeAppe     l,Di  sconnectCause) values (?, ?, TIME  _FORMAT(?, '%H:%i:%s.% f'    ), T  I  ME_FORMA T   (?, '%H:  %i:%s.%f')     , TIME_FORMAT(?, '%H:%i:%s.%f') ,?       ,D   ATE_FORMA    T(?,    '%Y-%c-%d      %H:%i:%s.%f') ,?,?    )");
              preparedSta  teme  n  t.setString         (1, CallingP     e erAddress);
                         preparedStateme nt.s    e       t String(2, Ca  lle  dPee rAd   dres       s);
                  prepar         edSta te     ment.s   etString(3  , Set   upTi   me);
                preparedStateme      nt .setString      (4, ConnectTime)            ;
            preparedStatem ent.set     String(5, Disconn  ect   Tim   e)     ;
                               preparedStatement.setBoolean( 6, Is  Missed    );
                Si  mpleDateFormat sd  f = new SimpleDat                eF   ormat("yyyy-MM-      dd HH:mm:ss.SSS");
                  Date     dte = new D    ate();
              preparedStat            ement.setString(7  , sdf.format(dte));
                      preparedStatement.s     etInt(8, TypeAppel);
            preparedStatement.setInt(9, Integer.parseInt(Disconnec  tCause  ,16));
                  preparedS  tatement.executeUpdate    ();
                  
        }   catch (SQLException e) {
            Logger.getLogger(ConcurrentDataInsertTest.class.ge    tNam    e()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBInsert.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
