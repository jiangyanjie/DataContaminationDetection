package    ru.arlen.dao;

import ru.arlen.entity.LocationInfo;
import ru.arlen.entity.User;

import java.math.BigDecimal;
imp  ort java.sql.*;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

pub  lic class DAOImpl implements D  AO {
      private    static String JDBC_PROP = "jdb     c";
    private static String        URL    = "url";
    priva  te     static S   tring USER = "user";
    private static String PASSWORD =    "pass";

    private   Resou r    c    eBundle propFile;
      private Propertie   s      pro p;
    private  Conn       ection c   onnect;

          pu     b           lic DA     O   Im  pl() {  
                 init(      );
            }     

    p           r    ivate        void i  n it(    ) {
        try {
              propFi   le = R  esou  rce     Bundle.get    Bun             d le(JD  BC _PROP     ,      Locale.ENGLISH);
                                            connec t = DriverMa  nager.getConnection(            
                                                   prop File.getString(URL),
                                   p      ropFil  e .g   etStrin   g(USER),   
                                    pr     opF   il          e.getSt   ring(PAS   SW O   RD));
            } c  atch (SQLExcep      tion       e)  {
                   e.pr  i     ntStackTrac     e()    ;  
        }
       }

                    @Override
        public boolea    n isAuth(User us      er) throws S   QLException {
         Pre     pa   redStatement prepSt     = null;
           ResultSet      rs = nul   l;

              t  ry    {
//              p     repSt = conne      ct.  p   repareSta    tem    ent("      sel        e   ct    id from m       y      table           w   here i     d    =?");      
                   p   re    pSt = connect.prepareStatemen   t( "se lec              t d             istinct                app_id fro m T_GT  BT_US            AGE_TRACK ING wher  e a pp      _id =?"   );
                           prepS      t.setInt          (1, us e     r.get   Stid());

                 rs = prep  S   t.  executeQuer      y();
                         return        rs.next();
                } finally { 
            try {
                                     if (pr  epSt != null)  {
                                       prepSt.  close();
                                   }
               } catc       h (SQLException   sqle) {
                   //         NO         P
              }
                                        rs                      =        null;
                  prepSt     = nul      l   ;
               }
         }

          @Ove     rride   
       public          int            g    etAppId(Use    r user  ) t     hrows SQLExcepti   on   {
                 Pre   paredS    ta tement      prepSt = n ull      ;
             Re            sultSet r  s = null;

          t           ry     {
//            prepSt =  co    n  nect.prep    areStatem   e    nt(  "select id      from myta  b  le where id=?"    );
            prepSt = conne    c    t.  pr   e        p      areStatement("sel   ect dist  inct app_id             from    T_GT BT_U  S  AGE_ TRACKIN           G where    app_id =?")    ;
               prepSt.setInt(1,      us      er.getStid    ());
           
                          rs = prepSt.executeQuery();
                            ret    urn rs       .getInt(1);
        }    fin       ally {
                         t           r  y {
                                    if       (  prepS  t  != null) {       
                                         prepSt  .clo  se();
                        }
                  }     catch (SQ   LEx  ception sqle) {
                     //NOP
                      }
               rs    =   null;
              pre       pSt = null;
             }
    }
 
       @Override
    p       ublic Locati o   nInfo getLocationInfo (int recordI d)          {
                               Prepa  redState  m ent pre pSt = null;
          Re           s        ultS  et rs =         null;
        Locat  ion   Inf               o loca       tionInfo = new Location I    nfo();    
                      String query = "select tr           acki ng_id, record_numbe     r, latitud        e, longitude   ,  s  peed, heading, elevation,         a         ged  _status,    ehpe  f   rom t_  vtm_  locatio  n_info         where  RECORD_N  UMBER = ?";
        t  r         y {    
              prep      S    t = connect      .prepareSt     ate         ment(quer         y        );
                         prep  S t.set       Int(1    , recordI           d);
                       rs          = prepSt.e        xec      u   te   Que  ry(   )     ;

                                       if   (rs.next()) {      
                            locationInfo.setTrackingId(rs.getBigDecimal(         1));
                      l  ocat  ionIn      fo    .setRecord      N    umber(   r   s.getInt(2)    );
                       loc   ationInfo.setLat       itu       de(      rs.getInt(3));
                     l    ocat     ionI  nfo.setLongitude(rs.getInt(4    ));
                      loca     tionInfo.setSp   eed(rs  .getInt(5));
                  l              ocationInfo.       s   e tH      ead  ing(rs.  ge           t   Int(6));
                               locationInfo.setElevatio             n(rs.getInt(7));
                                                      locationInfo.setA  gedSta   tus(rs.getSt      rin  g(8));
                     locationInfo.se                tEhpe(      rs.g   etI  nt(9    ))        ;
                      }
                          /*System.out.p    rintln("      Row = " + rs.g   etRow());
                 System.ou t.println("tracki ng_id               =" + rs.getBigDe   cimal(1));
                   S   y    stem.out.println(" record_   number=   " + rs    .getI nt(2));
                  Sy   stem.out.p     ri        ntl   n("latitude  =" +        rs .getInt(3)   );
            Sy     stem.      out.pri   n tln("longitude=" +       r  s.getInt(4));
                    Sy          s te  m.out.prin      t    ln(" spe  ed="       + rs.g   etInt(5));
                    System        .out.println       ("heading=" + rs.g         etI       nt(6));
                             S   yst     em.out.print  l                   n( "elevation=" + rs.g    etInt(7)   );
                System. o  ut.   p    r  in tln("ag     ed_status=  " + rs.getString(8) );
            S        ystem.out          .p        ri   ntln(  "      ehpe=" + rs.get                Int(9));
            System.out.printl n();*/     
               } c     a tch (SQLE   xceptio  n sqle)      {        
                    //N             OP
                    }      fina   lly {     
                                   try  {
                     i f  (    prepSt !=    null)     {
                              prepS               t.close  ();
                       }
            } catch (S QLExcept ion     sqle) {       
                      //NOP
                                             }
                                     rs = null;
            prep  St =     nu   ll   ;
           }
        return loc  ati        onInfo;   
      }
}

/*class w          {

     p  rivate static String JDBC_PROP = "   jdbc";
         private      static    Strin    g URL = "url";
    priva    te          sta  tic    String U    SER = "      user";
    priv  ate sta    tic String   PASS    WORD =      "pass";

    private st  atic Re  sou         rceBund     l   e p    ropFile;
    p  riva        te static Pro per    tie  s    prop   ;
            priv     ate static Co   nnection    connect; 

    public static v   oid main(S   tri   ng[] args)   {


              try {
                 propFile = Resourc eBun   dl  e  .ge  tBu   ndle(JDBC_PROP, Loc   ale.ENGLISH);
                conne  ct = DriverManager   .getConnectio     n(
                      propFil     e.g     etStri    ng(    URL),
                                    propFile.getString(USER),
                     pro  pFile.getString(PASSWORD));
                               } catch  (SQLE   xception e) {
            e.pri    ntStackTrace();
        }

           PreparedStatement pre pSt               = null;
           ResultSet rs = nul   l;
          S tring query =         "select tracking_       id     ,   record_number, latitud e,longitu  de,   speed,head    ing,elevation,aged_status, ehpe      from t_vtm_location_info where tracking_id      = ?";

        try {
              prepSt = connect.prepareSt  atement(  query);
                 prepSt.setBigD  ecimal (1,         ne      w BigDecimal(5837 +         "0   0  000         0000000   0000"));
                    rs = prepS  t.executeQuery  ();

            while (rs.  n       ext())   {
                       System.  out.print      ln("Row = " + rs.ge    tRow());
                 System.out.println("tracking_id=" + rs.getBigDecimal(1));
                System.out.p   rintln(    "record_num      ber=" + rs.getInt(2)                );
                System.out.println("lat      itude=" + rs.getInt(3));
                        System.ou   t  .println(      "longitude=" + rs.getInt(   4));
                Sys    tem.out.    println("speed=" + rs.getInt(5));  
                System.out.println ("heading=" + rs.getInt(6));
                   System.out.println("elevation=" + rs.getInt(7)  ) ;
                  System.out.println("aged_status=" + rs.getString(8));    
                System.out.p      rintl     n("ehpe=" + rs.getInt(9));
                System.out.pri     ntln();
            }
        } catch (SQLException sqle) {
            //NOP
            } finally {
                try {
                if (prepSt   != null) {
                       prepSt.close();
                   }
            } catch (SQLException sqle) {
                //NOP
            }
            rs = null;
            prepSt = null;
        }
    }
}*/


