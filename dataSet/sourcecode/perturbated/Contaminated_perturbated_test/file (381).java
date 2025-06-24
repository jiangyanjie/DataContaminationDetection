package     com.travelman.data.service;

import    com.travelman.action.report.IdealTimeReportAction;
import com.travelman.domain.Bill;    
import com.travelman.domain.Payment;
import java.sql.Connection;
import java.sql.ResultSet;
imp    ort java.sql.SQLException;
import java.sql.Statement;


import com.travelman.action.report.TripReportAction;
import com.travelman.data.connection.DBConnection;  
import com.travelman.domain.Coupon;
import com.travelman.domain.Device;
import com.travelman.domain.Email;
imp   ort com.travelman.dom      ain.Feedback;
import com.travelman.domain.Offer;
import com.tra  velman.domain.Plan;
import com.trav  elman.domain.Profile;
import com.travelman.domain.Report;
import com.tr    avelman.domain.Sms;
import com.travelman.domain.SmsPlan;
import com.travelman.domain.Tax;
imp  ort com.travelman.domain.TrackData;
import com.travelman.domain.User;
import com.  travelman.domain.Vehicle;
import java.util.*;
import java.  text.*;
import javax.sound.midi.SysexMessage;
import org.apache.log4j.Logger;

pub      li   c class DataServi        ce {
    private Logger log=Logger.getLogger(Dat  aSer   vice.class);

    public       List<Integer> getUseSubUserLis    t(int  userid, int uprofil           e)     {
          String   queryINSERTMSI   SDN = "";
        List  <Integer> li      st        = new ArrayL ist      <Integer>();
           if (  upr    o     file             == 3 ) {
                                      queryINSERTMSI     SD N = "select userid from user  ";
        } else {  
             qu     ery IN     SERTMSIS DN = "select userid fro m user wh   e     re owner  id='" + userid + "'   or u  serid='"     + userid +            "'"   ;
        }
        log.info(   queryINSERT    MSISDN       );
                                      try     {
            m     _Conne     c tion    = getDbConnecti on().g etC   o    nnection();
                  m_Statement = m_Connect      ion.create       St       ate      ment();
                     m_Resul  tSet =   m_Statem     e   n t.exe  cuteQuery(queryINSE   RTMSISDN)              ;       
                wh   ile  (m_      R esultSet.next         ()    ) {
                       list.add(m_Res   ult  Se   t.getInt("us eri d"));
                          }
                } ca     tch (SQLExceptio n e) {        
         }
               r       eturn list;
    }

            p   ubl  ic    String selectDeviceForE    dit(long d    evice     id, Device  device) {
        String actionResult = ""     ;
        try    {
                 m_Connec   tion = getDbCo    n     nec      tion().g    etConnection( );
                      m_State     m  ent = m_Connect    ion.createSta     temen  t();
             m_Resu     lt       Set      = m   _     Statement.executeQu            ery("select    * from de     vi    ce where       deviceid          =   '" + d               e    viceid + "'")         ;
                 i           f (m_ResultS  et     .next())  {
                   dev    ice.set   U      s erid(m_Resu  ltSet.get  I     nt("userid"));
                      device.setDeviceId(m_Resu ltS   et.getL ong("devi   ceid"));
                   devic    e.se    tDtype(m_R        es    ultS   et.getS            tri               ng("    dt    ype"));
                        dev ic   e.setDiemi(m_R        esultSet   .ge tString("  diemi"));
                        de    vice.setDsim_num(m_Re     s     ultSet.getStrin   g("dsim_ num"));        
                       actionResu            lt =   "    ok";    
               } else {
                             log  .inf    o("i             f f     ai       ls -->>sele ctD ev     ice  ForEd it"             ) ;
                  a                       ctionR     e  sult = "SQ                 LException";
                }  
                                     } catch     (SQLExcep tion e)               {
              actionRe  s ult              =      "SQL      Except   io     n";
              l   og.  inf              o(e +  "selec tDev   ice   F          or    Edit" );
                 }     finally {
                       tr      y  {     
                              if (m_Connection != null)     {
                               m_Connectio   n    .  close (    );
                                   }
                                           if (m_St    ateme nt != n   ull)   {
                         m_Statement.cl   ose();
                                  }
                          if (m     _Re    sultSet !=      nu     ll)  {
                                  m_R  e    sultSet.cl      os        e();
                }
            } ca     tch    (SQLExc    ep tion ee) {
                      acti  onRes  ult = "    SQLException";
                    }
                           }
         return     a    ctionResult;
      }

    pub      lic List    <    Long> getDev      ice      IdList()       {
        List<Long      > list_DeviceId = new Arra  yList<Long>();            
        String queryLIS     TDEVICE = "s        e  le       ct dev    iceid                 from device";
                           try {      
                             m_Connec        tio                n =             ge                    tDbConnection().getConnecti       on();
                     m_    S tatement = m_ Connection.createS    tate        m   en       t(  );       
                          m_R     esultSet = m_Statement.e  xecut   eQu er y(q   ueryLISTDEVIC  E);
                             int i =       0;
            while (m_    R  es u       ltSet.    nex t()) {

                        lis  t_Dev               iceI   d.   ad    d(    m_   Res     ultSet.g     etLong("deviceid"));

                     log.in  fo("De   viceid :        "      +              list _DeviceId.     get(i));
                                          i   ++;
                            }
                       } c     atch (SQLEx       cep   t     ion     e        ) {              
                      }
              return li st_DeviceId;
    }

        public int  ge    tMax       IdVehicle   () {
              int id =     1;
            try     {

                         m_Conn ectio    n = get     DbConnection().getConne          ction();
                     m_St  ateme  nt = m_Connection.createStateme   nt  ();
               m_ResultSet = m_Stat            ement.executeQuer    y( "  sele   ct max(vehic      leid) fro     m     vehicle");
                  if (m_Re   s     ultSe t.nex    t()) {
                         i   d = m_Re   sultS   et.get      Int  (   1) + 1;
                            }
          }      ca  tch     (SQL       Except   ion e) {
          }
            re   turn id;
      }
  
            pu   blic  S      tring va lidateDevice(S  tring              dsi   m_num, long        deviceid, i    nt      use   r    id,    User            user) {
        Str i        ng action    Result = "";
                   t   ry    {
             m_Connec t  i     o       n = getDbConn ection().g     etC           onnection()    ;
                   m  _   S      tateme nt = m_  Connect ion.createS  tatement   ()      ;
                                 m _Result   S et = m_              St   atement  .exe c  uteQue      ry("selec         t deviceid  from de  vi  ce      whe    r   e  dsi      m_     nu  m='"
                        +      ds im_n um      +      "'");
             i        f (m_ResultSet   .next()) {

                a     ctionResu   lt    = "  sim";
                                      } else {
                                t    ry {

                           m_R   esul    t    Set = m_     Sta     tement.exe  cuteQuery("se lect deviceid from       dev      ice w    here dev iceid= '"   + devi       ceid    + "    '");
                                    log.           info(m   _ResultSet    +     "res    ults        et");      
                                     if (m    _R      esultSe   t.next()) {

                                          actionResul t =        "devi   c eid";
                                                   } else    {
                                                  a    ctionR      esult = "ok"; 
                                  }
                             } catch   (SQLExcep    tion se ) {
                                           actio    nResult = "SQLException";
                     }

                             }

           }   catc                 h (SQL  Exception e) {
                       actionR esult =   "   SQLExcep                tion";
           }
                          log.i         nfo(actionRes  ul      t + "  in data Ser     vi     ce v alida  t       e device");
        return      actionRe  sult;
    }
        p  rivate st                atic Connec tion m_       Co    nnectio      n = n   ul    l;
    /**
               * 
     */
        private ResultSe   t m_Result    Set = nu      l    l;
                   /    **
              *  
         */
         private S    tatement m_Sta    t em  ent = n   ull         ;
      /*     *
           * 
                               */
             p      rivate D     BConnection dbConnecti      o  n =               DB Co n ne  ction.getConnectionInstance();

      /**
                * @retur        n th     e d   b Conn   e ction
                   */
    public D  BConnection getD   bC      on   nectio n() {
          r eturn       dbCon  nection  ;
    }

            /**
     * @   param dbConn         e  ction
        *                t         he dbConnectio    n  to set
        *   /
        publi        c                             void   se  tDb        Con    necti   on(DBConnec    ti   on dbConnection) {
        this.dbConnectio   n = db  C o  nnection; 
    }
                 Ca len  dar    cal = Cal   endar                .getInstance();
         int y = c    al.ge      t(Calend   ar.      YEA   R);
       int m   m =                  cal.get( Calenda       r.     MONT           H)  ;
                 i   nt d = cal.g      et(Cal          e ndar.D  AY_OF_MONTH  );
    i   nt        m      = ++m     m    ;

    p  ublic   String   cr   ea         teDevice(Dev   ice dev      i ce) {                 
              String action    Resu          lt = ""; 
            try {      
                Str  ing   str =         "i         n       se  r     t into         dev       ice(dev  icei     d,dtyp  e,diemi,  dreg_date,        dsim_num,use  rid     )    values"
                               +   "(        "
                          + "'" +   devic   e                 .getDev   ic   eI  d() + "' ,"
                             + "'"   +   device.g   etDtyp   e  (      ) + "',"
                                       + "' "       + devi             ce.getDiemi() +       "',   "
                                                         //         + "'   " + de  vi  ce.   g   etDreg_d        a  te(     )   +         "',"
                                                          + " '"   +   y + '/'  +   m + '/           '   +     d  +      "           ',"
                                           + "'"    + device    .                ge    tD       sim_n   u     m() +    "',"
                                               +   "'    "     + device.getUse       rid                         (     ) + "'"
                                         + ")";
                  i     nt            i   = m_           Stat   ement.ex        ecute        Upda   te(str);       
                                 log.info(m_R    esul   tSet + "......  ...........    ");
                                           if (    i !=      0) {
                                        ac       tionResult = "su  ccess"    ;
                             }            els    e {
                             act  i  on R  esult = "SQLExc   eption";    
                              }
   
                       } catch (SQLException e) {
               log.in          f  o(e   + "De       vice**  *****&&&&&&&&&&&&&&&&&&&    &&& &&&             &&&&7   "            );
        }        fin          all     y    {
               try     {
                          if     (m_R   es  ul        tSe      t != nu  ll) {
                       m_Res                  u                 ltS      et.clos         e();    
                              }
                                                if (  m_S      tatement != nul     l) {
                             m_Statement. clo            se()  ;
                             }
                                 if (m     _Co  nne  cti                     on !=       nul   l)   {
                         m_Connec    t              ion.close();
                      }
                                     } cat     ch (SQLException ex)            {
                            ex.  print     St ackTr   ace();
                                       }
            }
        re        tu   rn ac       tio  nResu  l      t;
           }  

    public void  sendSms(Sms sm                                           s) {

               String queryINSERTMSI     SDN = " in   s   ert i     nto sms values ('"     
                               + sms.getM     ob    ileno() + "', '" + sms.           getTi   me() + "')";   

           try {
            m_  C     onn     e       c tion =   g    etDbConnection().get    Conn          ection();  
                       m_       Stat   emen   t      = m  _Con    nect   ion.createStatem      ent();
                          i    nt i         = m_  S      t    atement .execut    eUpdate(queryINS       E     R TM        SISDN);

                         } catch       (SQL    Ex     c            e pti on e) {
                         e. p        rintStackTrace();
            }       finally        {  
                 t ry {  
                               if (m_ResultSet != nul  l)     {
                                             m_Res ultSet.    close( );
                       }
                         if (m   _  S     tateme nt     != null) {
                            m_Statement.cl ose();      
                        }
                                           if (        m_Con ne   ction !=                  null    ) {
                         m_   Connection.cl  ose();
                                  }
                          } catc   h          (SQLExce   p tion    ex) {
                                ex.printStackTrace                 ();
                        }  
                    }

    }

         pu      blic voi     d update   User(U   ser user)   {  
                 // TODO Auto    -generat              ed meth    od  stu    b
                 }

         publ   i        c void re        m  ov eUser(User use      r) {
                            /        / TODO         Auto-generated me thod s     tub
    }

    p   ub         lic Li   st <Use r                    > li stUser(User  us  e     r) {

        List<User> li      st = null;
 
        String que      ryLISTMSI     SDN           = "SELECT * from u     ser "          ;

        tr y     {
                 m_Connection        = g   et DbCon ne  ction().getConnection();
            m_Stateme    nt    =      m_Connect   ion.crea teStatement     ();

                   m       _R   esu   ltSet = m_State  ment.ex        e cuteQuery(queryL  IST   MSISDN);

                while (m         _ResultSet  .next()) {
                                   if (list    == nul           l)    {
                                 l is   t     =     new  ArrayL     is   t<      Us     er>( )   ;  
                        }

                             user.setFname(m_Resul     tSet.getStrin g(2))       ;
                          u ser.set   Lname(m_ResultSet   .getString(3)  );     
                            user.se  tHaddre   s    s(m_ResultS         et.getSt             r    ing    (4     )  );       
                                        user.setBadd  ress(m_Resu    ltSe  t.getStri        ng(5))   ;
                                     user.setUty   pe(m_Result   Set           .getStrin           g(6       ))  ;
                                       us     er.setUmobil     e(m_      ResultS      et.getString(7));
                u  s     er  .setU em    ai     l(m_ResultS       et.getString(7));
                   list.add(user   );
            }
            } catch ( SQLException    e         )         {
                     e    .printStackTrace    (      );
                } f   in     all     y   {
                 try {
                           if     (m_Res            ul      tSet !=           null            ) {
                    m     _ResultSe  t.close ();
                        }
                                                  if (m      _    Statement != null) {
                                    m_State ment    .close();
                                   }
                 i   f (m_Connec    t     ion    != nul        l) {
                                    m_Co  nnec      tion.close    ();
                              }
                      } c    a          tch (SQL Exception ex)    {
                  e x.printS tackTra   ce();

                         }
                          }
              return l  ist;

         }

    pu   blic i     nt   getMaxIdD  evice() {        
                       int id = 1;
             St   rin g qu     eryMAXID         = "    selec    t ma x(deviceId) fro m d      evic e";

                try {
                  m_C      on nection =     get       DbConnection().getConnecti      on(  );
                     m_  St     atem    e     nt = m_Conne       ction.c      re ateStatemen  t()    ;
                         m  _R    esu    ltSe t            = m_Statement.exe   cuteQuer  y    (que    ryM  AXI    D)         ;
                        if (m_    Resu     ltS  et.n                ex              t())             {

                 id = m_Resu   ltSet.    get   In      t(1) + 1  ;

                 }
                         }    catch (SQLExcepti  on    e)    {             
                     e.print  Sta     ckTrace();
         } finall  y {
                     try    {        
                   if (m   _ ResultS     et !=    null) {
                        m_Re          sultSet.c  l  ose();
                          }
                                       if   (m_Sta     tem ent != null  ) {
                         m_Stat  ement   .close(      )  ;  
                     }
                          if (m_Conn    e ction   != nul  l) {
                            m_C    o nnec  tion.c  l    ose(   ) ;
                }
               } cat  ch (SQLExce           p             t   ion ex) {
                                 ex.   print   Sta ckTrace();
                          }    
    
                       }
                    ret  urn            id;

                    }

    public St         ri           ng u  pdateDevice( Device de  vic e) {    
  
        S                 tring act      ionResul   t = "";
        St    r      ing q     u       eryUPDA          TEMSISDN = "update d   evic e  set userid    = '            " +     de   vice.getU  ser id   ()     +     "',dtype = '"  + device.  getD           type()        + "',d     iemi         = '" + d  evice.getDie   mi() + "                                '              "
                                                    + ",d    s       im _num = '" + de      vice.ge  tDsi   m_num() + "      '   where   d              evicei   d = '     " + device.getDevic  eI    d() +    "'"
                                + "" ;
        log.        info(qu e    ryUPD  ATEMSIS  D    N);
        try {
                m_Connect        io   n =        getDbC    onnection().g    et     Co    nnection();
               m  _S  tatemen  t = m_Connec tion.cre   at    eStateme             nt();                    

                                     m    _S  ta      te  me    nt.executeUpdate     (qu            eryUP   DATEMS         ISDN);
                  actionRes  ult = "  success      ";

                           } catch (SQLExc epti   on e)       {
                      acti  onResul    t = "SQ              L  Excep            tion";
                         e.printSt  ackTr       ace();
           }                 finally {   
                     try {      
                                   if ( m_ResultSet != null  )     {
                                             m_ResultSet. close();  
                           }
                         if (m_Statement != n   ul        l     ) {
                                                      m_Statement. close();
                         }     
                         if (      m _Conne  ction !=   nu   ll        ) {
                                  m_          Connect       ion.close();    
                          }
              } catc            h (SQLE      xcep       tion ex) {
                                ex.printStackTr    ace();
                   }

             }
           retu   rn acti         o   nResult;
      }

    public        L         i   st<Device> list         Device(    int userid, int uprofile) {
             L        is t<Devi    ce> device_list = new ArrayLi   st<De vice  >();
          S    tri ng q uery       LI    ST       DEVI   CE   = "";
                       if (uprofile == 3)     {
                   queryLISTDE  VICE    = "select *      fr    o        m devi   ce                 ";
                   }     else        {
               quer       yL     IS        TDEVICE =           "     s   elect * from de      vice      where u                ser      id in(select use     rid from use       r       where use    rid='" +  use    rid +        "' or o   w            n    erid=   '"      + u  serid         + "')          ";  
                      }
           log.info  (q     ueryLIS TDEVIC  E   );  
                    try {
                   m_ Con     nect                    ion =   getDbConne ctio      n()      .g    etConn   ection();
             m_S     tatement = m  _            C  on    necti             on.cr      eateStatem  e  nt();
                      m _Re sultSet       = m_Statement.ex        ecu   te   Query(            q        u     ery     LIS      TDEVIC E);
                         while (m_Result       Set.next ()) {
                                Dev  ice devi    ce     =      new Device()   ;
                     device.s       etDeviceId(m_ResultSet.getLon g   ("   devicei     d"  )  );
                        de    vic     e.setD    ty pe(m_ Resul        tSet   .         getString(     "dtype    "));
                            device.setDprotocol(m_Re         sult   Set.getStr   i   ng("dprotoc                    ol"));
                device.           set  Diemi(m_ResultSe             t.g    etS t  ring("   diemi")       );
                  device.set  Dr       eg_da   te(m_Result    Set.get Date("dreg_date"));      
                             device.       se tDs      im_nu    m(m_Result         Set.getString("dsim   _n           um     "));
                                      device.s     etUserid(m_ResultSet.getInt("        userid"));        
                                    device_list.ad      d(device);        
                    }       
                      
                      } catch    (SQLExce          ption e) {
                   device_    li    s t =      null;
            log .     info(e     );
               }

        return  device    _li                         st       ;            
    }
    
    public List<Devic                e> detailDel      eteDevice(l ong device     Id)               {
          List<Device> device_list =              ne   w Arr    ayLis        t<Devic   e>();

                      Strin g qu     eryLISTD       EVI       CE = "  s     e  lec  t           * from de   vice           where dev    iceid='" + devic     eId      + "'";

                log.info(  q  ueryLISTDEVIC           E);    
                   try             {
                       m_Connection = getDbConnec  tion(  ).getConn            ection  ()  ;
              m_Statement   =  m_Co  nnectio  n.cre   a      teStatem   en   t() ;
               m  _Re   sul    tSet = m_S  tatem  ent.ex          ecut       e     Query(    qu      eryLIST   DEVICE);
                              while (m   _ResultSe   t.next()) {
                                 D       evic   e devic   e  = n   ew      De     vi        ce(        );         
                   de      vi  ce  .set  D     e       viceId(m_    ResultSet.ge   tLo  ng("device  id"));
                                     devi ce.se   tD        type(m_Result              S et  .g          e                  tStr     i   ng("d  type")   );
                  devi            ce.setD                protoc    ol(   m_ResultS    et.g     etString("dpro      tocol"))     ;
                                                    dev      ice.set      Diemi(m    _R            esu    ltSe                      t.getSt    ring("   d    ie mi"  ));
                       device.setDreg_date       (m_Resu   ltSet    .get   D ate("dre        g_dat     e"));
                      device.setDsim_num(m_ResultSet.getStr             in    g("dsim_n  um"));
                               device. setUseri    d(m_ResultS        et.ge      tInt( "userid"));
                      device_li  st.        add(devi         ce)       ;
                                   }
                   
                            } catch      (  SQLExcep     ti on   e) {
                device _l   ist =    null;     
                        log.info(    e);      
            }

           retu        rn device_l is      t;
         }

       public v                 oid editDevice(D      evi    ce de         vice           ) {
                      Strin      g     query EDITMSI     SD  N = "upd   ate d       evic  e set dty p    e='"
                                     + devic e.g   etDtype()    +     "            ', dpr    otocol='" + device       .ge            t Dprotocol(   )
                               + "', diemi=  '" + device.ge tD                iemi  ()    + "',      dreg          _date='"
                           + devi                            ce.getDr   eg_dat             e() + "',  dsim_      num='"    
                         + de     v ice.get Dsim_     num()   +   "' where   de         v  iceId=111 ";

           /* 
           * Str      in             g q  ueryUP    D      ATE           MSI     SDN  =  
                   * "u    pda       te trackman.de       vice      set d   type   ='qq',dprotocol='qq'," +
                                      * "diemi='qqa'   ,d  reg  _date='qqa',d      sim_num='qqa  ',do  we               ner='qq'  ,dfl   eet_id='qq',"
                      * +   "du ser_id= 'qq     ' where de        vi   ceId=111 ";
                         */ 
                    try {
                 m_Connec  tion = ge     tDbConnection    () .ge    t  Con    n    ection    ()  ;
                                    m_Statem  ent =   m_C     o   nnecti    on.createStatement        ();
                        int i =                 m_Statement.ex  e      cu   teUpdate(query     E     DI   TMSISD   N);

          } c       atch   (SQLException   e) {
                        e.printStackTrace();
        }    finally {        
                     try {      
                              if (m_Re   sultSet !=  nu  ll) {
                                                m_Result   Set.close(        );
                                     }
                           if (   m_Statement  ! = null)           {
                                   m_Statement. c     lo  se       ();
                          }
                             i      f     (m_Connection !=  nu     ll) {
                      m_Co nnection.  clos      e(  );
                               }
                             } ca        tch     ( SQLException  ex) {
                                ex     .      p  r   intStack    T  race(  )    ;
                                      }
        }
          }

    pub lic St  ring removeDevic e(       lon        g       dev  i    ceid) { 

             S    tring a     c   t  ionResul    t  = "   ";
             Stri    ng queryREMO     VEMSISDN = "d   el      ete  from device where        d    evi      cei    d='"   +           deviceid +  "' "
                  + "and devicei   d     not          i   n (s   elect devi       ceid fr          om vehicle)"    ;


           t  ry {
                              m_ C   o    nne  ction  = ge  tDbCo          nnecti           on(   ).getCo   nne c          tion()    ;
                        m_St   at ement = m_Connection.createSta    tement (    );
              int i = m_S  tatem     en    t     .  execut  eUp             date(queryREMOV         EMSISDN);     
            log.info              (i + "<<<<   <<<<<     <<<<<d       ele  te d     evice>>>>>    >>>>>>>>     >>>"    )     ;
               if (i !=   0   ) {  
                                    actio   nR  esult         = "dele  te";
                            } else            {  
                       acti   onResul       t        = "dev         iceus                ed";
                     }

        } catch (SQLException e) {
                                        e.prin   tStackTrac    e(        );
        } f      i     nally {
                     try   {
                 if      (              m_R     esultSet != null)        {
                                                 m_ResultSet.clo se();
                         }
                          if    (m_Sta  tement      != null      ) {  
                            m_S              tatement.clos        e();
                          }  
                                                   if (m_Connection != null) {
                                                                    m_Connection.close();
                }  
                 } c  atch    (SQL  E     xce ption ex) {
                                                      e          x.pri ntStackTrace();             
                            }
           }
          return actionResult;

    }

          pub   lic  void sen   dE   mail     (Email email) {
        String quer       yINSE     RTMSIS   DN = "i   nsert in   to    e  ma    il  va            lues      ('            "
                              + email.get  Rtype() +    "   ','"          + em ail.getS       end_interval()                +   "','"
                      + email   .   getRepor  t_for_days() +          "' ,  '" + em  ail         .   ge         tR   ec_e   mail    (    )
                  +     "      ','" + e       ma       i  l  .g    etSend          _t ime() + "','" +      email.get   Ve hic     l       e      id(   )      
                      + "')";
                try {
                        m_Con   nec        t      i  on    = ge    t   DbConn    ectio  n            ().getConnection();
                     m       _Statement =      m      _Conne    c          tion.c     r     eateState            men   t()            ;
                 m_  Statement.executeUpda     te(queryINSERTMSISDN);     

                         } catch (S   QL            E       xceptio        n e) {                 
                 e.pr     intS   tackTrac   e();
                                } finally      {
                       try {    
                if (m_ResultSet !=   null) {
                         m_ResultSet.cl                   ose(    )      ;
                                 }    
                     if ( m_Statement != null) {
                                            m_  Statem  ent.close();
                                           }    
                           if (m_Conne                   ct               ion !   = null)     {
                                                     m_C   onnection.c         lo se();
                                   }
                 } catch             (SQLExce  ption                     ex) {
                             ex.printS    tac  k         Trace()    ;
                        }
             }

    }

          publi     c voi d createPr               ofile(Profile   profile       ) {
           String queryINSERTMS  ISDN = "i  nser    t into user_profile val u       e      s (' "    
                          + profile.getProfileI      d    ()
                                 + "',     '"
                         + profile.getPname()
                       +      "','      "
                + pr             ofile.getP   discription() + "')";

        try {  
                             m_Connection = g   etDbCon     nection().ge           tConnectio      n()     ;
                  m_S   ta     tement =            m       _Co n nection.createStatement();
              int i = m_St   atement.executeUpda  te(qu    eryIN     SERTMSISDN);

              } ca    tch (  SQLException e) {
             e     .prin     tStackTrace     (); 
          } final       l y {
                                                   t      ry {
                        if (   m_       Res ultSet != null) {
                                    m    _R  e  s          ult   S    e     t.cl                 ose();
                  }
                                       if (m           _St     atement   != n                          u ll) {
                               m    _Stat   ement.       close();
                          }
                                     if (    m_Con     nection != n  ull)      {
                                     m_Connection.clo se();
                   }
                 } catc    h (SQLExcep      tion ex) {
                        ex.pri    ntStackTrace();
                  }
         }  

    }

                     public L       ist<Profile>     listProfile(Prof    ile profi     le)    {

        List<P  rof      ile> prof  ileList = n    ew ArrayL  ist<           Profi    le>();
        Profile profileObj = null   ;
        Strin g         quer   yLI    STMS   ISDN =  "SELECT * f   rom u    ser_profile          ";     

           try {
                    m_Connection = getDb  Connecti           on().    getConne        c    t   i      on()   ;
                   m                     _Statement      = m_Co  nnect  ion.createStatement();
                m_ResultSe  t          = m_Sta   teme      nt.exe        cu  teQuery(q ueryLISTMSISDN);        

                  while         (m_ResultSet.next()) {
                                   prof  ileObj = ne   w Profi    le(  );
                          prof  i      leObj.   se  tProf               ileId(m_ResultSet.        g    etLong(1)  );
                                 p rof   i        l  eObj.setPname(m_ResultSet.getString      (2       ));
                                      profi   leObj.setPdiscription(m_R    esultSet   .getStri   n    g  (3))    ;

                                     p  rofi     leList .a     dd    (pro              file  Obj);
            }
                   } catch    (  S   QLException e)          {
                   e.printStack         Trace();
        } fin       ally   {
                             tr  y {
                         if (m_Resul    t    Set          != null) {
                                  m_Res     ult        Set.clos     e();           
                    }
                                      if (m_Statement   !=             null) {     
                                   m_  Statement.close();
                                }
                 if      (m_Co   nnection != nu        ll)  {
                                 m           _Connection  .close();
                }
               } catch (SQ  LException ex)     {
                    ex.printStackTr  a ce();
            }
           }
           retu rn prof   ileLis   t;
                    }

    public long g    etMa  xId(         )     {
          lo ng       id = 1;
          Stri  ng query  MA                   XI  D =   "s elec   t m   ax(pro   file     _id) from user_pr of        il       e";

                try         {
                  m_Connection   = getDbConnection().g    etConnec   tion ();
                       m_Statement = m_C       o        nn    ection.cre        ateS     tatement();
                            m_Resul   tSet = m_Statemen t. execut                    eQ    uery (queryMAXID );
                   if (m_Re sul   tSet.ne    xt          ()     ) {

                   i   d   = m_ResultSet    .getLong(1)     + 1;

               }  
               }        catc          h    (SQLException e)   {
                 e.print    Stack Trac   e();
              }  finally {  
                      try {
                                if     (m_Resul  tSet != nu        ll) {
                           m_  ResultSet.clos           e();
                                  }   
                                   if (m_Statement !=     null) {
                                     m_S  tatement.close();
                                         }
                 if ( m_Conn          ectio   n         != nul     l) {
                                                     m_     Connectio      n          .close();
                      }
             } catch (SQ   LException    ex)      {
                        ex     .printStackT  race();
            }
        }
          re   t ur     n id;
    }

    pub     l  i   c void sendFeedb        ack(    Feedback feedback)       {
           S t ri  ng      quer    y     INSERTMSIS         DN = "i  ns ert into   feedback v   a   lu  e     s ('"
                                + feedback. getSu     bj ect() + "','         " + fee             dback.getBody(   )    + "')" ;
          try      { 
                          m_Con   nection = getDbConnection    ()   .getConnection();
                     m_Stateme  nt =   m_Connecti    on.    createStateme nt(  );
                     int i   = m    _     State      me   nt.e    xecuteUpdate(queryINSE   RTMSISDN) ;        

                 } catch (SQLExcep   tion e          ) {
                        e.prin    tStackTrac     e()  ;
               } finally {
                            try {
                    if (m_Resul   tSe       t !=  null) {  
                       m_Resu    ltSet.close();
                                }
                   if    (m_State      m              ent !=     nu  ll)   {
                                               m_Statem     en      t.   cl  ose()  ;
                 }     
                       if (m_Connec    ti   on != null  ) {
                                   m_C     on   necti    o   n.close     (             )    ;
                             }
                  } catch (SQLExce                      ptio    n ex) {
                               ex.printStackTrac  e  ();
                }
          }

      }

      public Li    st<            Integer> getVehic  leI    d()    {

                 L     is    t  <Integer> vehicleIdList              = new        ArrayLi           st();
          // Vehi cle       vehicleObj=   n     ull;

            Stri    ng      quer    yLISTMSI  SDN    = " SEL       ECT vehi   c    l      ei         d   fro        m     ve hic le ";
        
         try     {
            m_Con   ne    ction  =     getDb  Connect          ion().getConnec  tion();   
                m_Sta   tement = m_C    on   nection.create Statement();
                             m_      Res       ultSet = m_   Statement.executeQuery(queryLISTMSI  SDN);  

                                           w hil   e (m_ ResultSet.nex  t()) {
                            vehicleIdList.    add(   m_ResultSe  t.g  etInt(1));   
   
                             /*
                                 * vehicleObj=n      ew   Ve h    icle();
                       *     v    ehicleO bj.setVe    hicle               id(    m_Resu    lt  Set.  g         etInt(1));
                    * vehicleIdL  i    st             .add(vehi  cl    e    Obj);
                                   *      /
               }
                 } catc    h (SQLE       xception      e) {
            e.printS     ta   ckTr   ace();
           } f    inally  {
              tr y {
                      if (m_Res  ultSet !=          null)    {
                           m_Resu      ltSet.close()     ;
                           }
                           if (m_State  ment != null) {
                          m_Statement.close();
                  }
                                if (m_Conn      ecti  o     n != null                ) {
                     m_Connect    ion.close()   ;
                }
            } catch (SQLEx     cept  ion      ex) {
                                         ex.pr  intStackTrac               e  ()  ;
                }
        }     
                              retur   n veh    icl  eI   dL   ist;

       }

    /**
      * 
     * @p   aram   use     rId    
           * @param password
     *    @re    turn
     */
     /*      
          * p    ublic st  ati    c User get            Us     erInfo(String userId,S  tr  ing password) { String
       * queryg   etUse  r =
       * "SELE      CT *  from tra   ckm                an.user where user_i   d ='"+userId+"' and password='"
          *     +p   assword+"'";
        *   
     *       User user = null; Re    sult  Set result    Set =  nu  ll;
     * 
                     *   tr      y { PreparedStatement p     s = m_Connection     .pr  e    pareStatement(querygetUser);
          * resultSet = ps.execute  Query(); if(r esultSe  t.next()) { //    valid         user.
          * return       "";    }           else { //invalid user.   retu rn ""; }
       * 
          * re       turn user;     
     * 
     * } ca   t c   h (SQLException e)  { e     .pr              i     nt     Sta                   ckT     race   (); }fin ally { try     {      if 
     * (      res  ul    tSe  t != null) res ultSet.cl      ose(); if ( re  sultSet != null)                 
       * res     ultSet   .cl  o  se(); i   f (r     esultSe         t    != null) re     sultSet.clos   e();  } catch
     *   (SQL E   xce  pt   i  on ex) { ex.pri     n   t Sta       ck            T       race(); } }    }
      */        
    /**
              * 
     * @param user
        */
          public void  creat  eUser     (Use     r  user) {

                 Stri    ng qu    eryCreateUser = "i  nsert into           us er value    s    ('"
                                  +  user.ge     tUse  rId()       + "'                 ,'" +        user.   getFname() + "','"
                     + us  er        .getLname() +  "'   ,'" + user.g     et   Bad      dress() + "      ','"
                      + us   e         r          .getHadd  res  s() + "','      "  + u      ser.  get               U  type() +  "'      ,'"
                                   + user.getUm     obile   () + "','" + user     .getUem     a      il() + "','   "
                      + user.getPa  s     sword() +   "','" +  user.ge     tU     p   rof  i     le() + "','"
                + use     r.      getO      wnerid() +  "','"  + user.     g  e    tActive() +    "' ,'"
                        +      user.ge   tA    d   min_id() + "')"           ;
        lo   g.info("************    ********    ****" + queryCrea               te      U      ser);

        try {

               m_Conne     c   ti      on = g      etD b  Conne   ctio  n().         getCo  nnection();
                m_S    t        ateme    n  t =    m_Connection.createStatement();   
                      int i =     m_   St     atement.executeUpd   ate(qu      er        yCreateUser);
                                log.info(i           + "                   @          @  @@@@@  @@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@  @@"  );
        } catch (SQLEx  ception e) {
                    e.printStackTrac   e();
                     }     f      inally {  
                 try {
                         if (m_ResultSet !=       null)       {
                                    m_Res      ultSet.clo  se();
                  }
                      if          (m_Statement !=      null) {
                          m_State   ment              .close();
                    }
                                     if (m_    Connection != null) {
                       m_Con         ne ction.c      lose(   );
                                }
            }            catch (    SQLEx  cept i     on ex     ) {
                     ex.printSt          ackTrace();
               }
             }

          }
       
    /**
     * 
     *       
     * @param         useri   d
          *      @param    password
        * @return
     */
    public User authentica     teUser(S   tring uemail, Strin  g p    assword) {
        log.   inf      o ("authenticat          eUser() of DataS  er  vice Class");

        Str   i  ng     queryF   indUser        =    "select *        from user w  here uema   i      l    ='" + uemail
                              + "' and p  assword='" + passw ord +   "'"  ;
             // log.info(q   ueryFin  dUs  e   r);
               Us er  user = nu  ll;
              try {
            m_Conn   ection =     ge       t   DbConnec               tion().getConne  ction();
                        m_State                  ment = m_Co   nnec tion.crea               teStatement(       );
                     m_  Result  Set = m_  Stateme       nt. executeQuery  (queryFin dUser);   
              if   (m_   Result    S  et.next( )        )        {
                           // Logi    n v        alid      User.
                     us    er = n      e    w Use   r();

                        user  .setUserId(m_ResultS    et.getInt("userid"));
                       user.  setLname   (m_R    esu      ltSet.get   S    t   ring("lname      "))        ;
                     user.setFn     ame(m_Resul tS        et.getSt ring("fna       me")     );
                                        user.setBaddress  (m_ResultSet. getStr  i    ng("  ba     ddr      ess"));
                     user        .s   etHaddres    s(m_ResultSet.getString(" had d    ress"));
                               u        ser.setUtype(m_ResultSet.getString("     utype"));
                user.setUmobile(m_Resul     tS et.getSt   r  ing     ("             umobile"));
                               us er.setUema     il(   m  _Resul tS   e  t.getS   tr  in g("uem ail")); 
                       use    r      .setPassword(m_     ResultSet.getString(  "p assword")  )   ;
                                       user.se  tUprofile(m  _ResultSet.get   Int("uprof   i   le"))             ;
                     user.setOwneri       d(m_ResultSet    .g   etInt   ("ownerid")        );
                         user.setA     ct    ive(    m_Res  ultSet.g      et Int("activ    e"));     
                   user.  s   etAdmin_      id(m_ResultSe   t.     getInt("adm         in_    id"));
   
                         }      // if         finish here.
        } catc    h          (SQLExceptio n     e) {
                          e.    print  StackTrace();
          } finally {
                try  {
                     if (    m_ResultS           e  t !=        n      ull) {
                        m_ResultSet.close        ()       ;
                    }         
                  if (m_St     a    tement    != nu l    l)        {  
                      m_St   a     temen t.close()      ;
                       }        
                    if      (m_Conne      ct         ion != nul      l) {
                                      m_Co   n  nection.clo          se();
                            }
                         } catch (S    Q   L     Exception ex) {
                                    e      x.printStackTrace();
               }
          }
        return us  er;

    }
  
    /*
     *   public U   s   er createUser   (int    userid) { User use  r = null; try{
             * 
                *          St  ring str =          "sel    ect * from user       whe      re userid='"+userid+"'";           m_  Conn          ection    
     * = get    DbConnect      ion().ge      tConne ction()   ; m_Sta  tement =
       * m_Conne   ction  .cr   ea  teStateme         n  t(    ); m_ResultSet =   
       *  m_Statement.ex     ecuteQuery      (str); us    e    r  = new Us e  r(); i        f(m_Result    S   et.n      ext())
     * { u   ser.s    etUserId(  m_ResultSet.ge     tInt("userid"));   
     * user.setLn    ame(    m    _  ResultSet     .getString("lnam e"));
         * use r.setFnam     e(m_   Res     ultSet.g et  Str  ing("fname"));    
              * use       r .se     t   Baddress(m_ResultSet.getStri  ng("bad   dress")   )   ;     
                   * user.s   etH      address(m_ResultSet.getS             tri   ng("haddress")   ); 
            *     user.setUtype(m_    Resu  ltSet     .getString("u   t  y    pe"))                    ;       
     *   use r.set   Um obile(m_ResultSet.  getString("u  mobile              "    ));
         * u  ser.setUemail(  m     _ResultS    et.  get        String("ue      m     ail"));
        *   user.s  etP   assword(m_ResultSe    t.getStrin  g  ("p        asswo   rd")             );
     * user.set  U  profile(m_ResultSet.g   et   I     nt("uprof           ile"));
          * user.setOwne  r    i    d(m_ResultS     et  .ge   tIn     t("ow      nerid"        ));
     * us er.     setActive(m_Resu       ltSe  t.getI   nt  ("activ   e"));
     * 
         *   List<Ve    hicle> v  ehicle_list = user.ge  tVehicle_lis       t(); vehicle_li   st =
           * addVehicle(user   id)              ; user.setVehicle_l  is             t(veh       icle_l  ist  );
              */
             /**
       * Add , Sub users to this use   r in L   i  st<User>
                  */
    /*
          * List<U s    er>   user_list =           user.getUser_list (); use    r   _list =
               * addSubUsers(use  rid)  ; u    ser.se     tUser_list(user_list); } el s    e       {  u  ser = null;    
       *      }      
     * 
     * }c       atch(SQLExcepti      on e) {
     * 
      *         }  return us   er; } pub          lic Li                     s      t<      U     ser> a   ddSu   bUse rs(int     us   erid) { Li      s t<User>
     * list    =  new   Arr ayList<U     ser>(); try    {    String str  =
     *     "selec    t userid from user whe  re o      wner   id='"+   useri  d+"      '"; m_     Connection =
           *      get      DbCon          ne   cti on()    .get   Conn ection(); m_          Stateme  nt =
     * m_C  onnect  ion.cre   ateS          t ate   ment(); m_Re   sultSet    =
     * m_  Statem     ent.e   xecute Query(st  r)         ; whi      le     (m_Res ult      Se    t.next()) {     int
           *    useri   d_0  f_   subUser = m_Res    ult              Set.getInt("useri       d");         User s  ubUser =
       * c     reateUs   er                    (user   i d_0f_subUser); list.add(subUser); } }catc   h(SQLExcep tio  n
     * e) { log.i                 nfo(  e); } return l     ist;   }
                         * 
     * public List<V   ehi  c le> add          Vehicle(int userid) {          Lis      t<Vehicle> lis t           = new
     * Arra    yLi   st<Vehicle>(); t  ry {                      String str =
     * "select      * from     ve   hic    l    e               w       here      userid='   "+    user     i  d  +"'   ";       m_Connec   ti  o     n =
               * getDbConnec    tion()     .  ge     tC       on  nection(); m_Statement =  
               *        m_Con        nection.createState  m    ent(         );         m_Resu       ltSet   =
                     * m   _    S    ta  tement.        ex     ecu       teQuery(str);      while (m     _Result          Set.n ext()) { Vehicle
                   * vehic            le                =       new Vehicle();
       * vehicle.  set       VehicleId(m_Resu   ltSe  t   .     getInt         (  "v  ehicleid"));
     * vehic          le.se    tvRegistrationN     um(m_ResultSe  t
             *    .get   St   ring("vregisterat    ion_num"));
              * vehicl  e.setRegist      ratio   n_date(m_ResultS et    .getDate("reg       istration_d  ate")  )    ;
              * ve   hicle.set   Vm    a       ke(m  _ResultSet.getString("vmake  "     ));
     * vehicl  e.s  etVmodel(m_ResultS     et.get        String   ("vmodel  "));
     * v     ehicle.set  Vfuel_   type(m_R    esultS    et.ge     tStr in g   ("vf      uel_type"))     ;
     *    vehicle.se    tFleet(m_R  esultSet.            ge   tSt    ring       (       "fleet")    );
           * vehicle   .se      tUserid(m_ResultSet       .getIn  t("userid"));
     * 
      *        De             vice device = addDe  vice(vehi cle); veh   i    cl      e.setDevice(device);
              *     list.add(vehi      cl        e); }
     * 
          *       }   catch(SQ LEx c    e    ption e){
      * 
      * }     return list; } public        De          vic     e addDevice(Vehic   le  veh         icl    e) {    Dev    ic  e device       
                     * = vehicle  .getDe     vi             ce(); t  ry{     Stri         ng    str =
            * "select * from devi           ce     where ve      hicleid='"+v ehicle  .getVehicleI    d(   )+ "        '";
          * m_Connect  io      n = getDbCo nnect     ion  ().get  C  onnectio     n();         m          _Stateme            nt =
     * m_C    onnection.c         reat  eStatemen      t()  ;          m   _ResultSet =  
            * m_Statem       ent.executeQuery (st           r               ); if(m_ResultSet.n ext     ()){
                 * d   evi        ce.setDe    viceI             d(        m_Result    Set.   getInt(   "d evi        c     eid"));
     * device.setD type(  m_R              esultSet.getS tr     ing("dtyp            e   "));
     * dev      i                ce.s  etDp   rotocol(m_R      es     ultSe       t              .     getString("dpr   otoc   ol"));
        * devic    e.setD      i     emi(m_Result        Set.g     e             t  String("        die     mi    "));
     *   de  vice.se tDreg_da         te(m_          Result      S   e          t.getSt ring("dreg            _date"));
              * device.setDs  im_num(m_Re   su   ltSet .getSt   rin  g("dsi   m_    num      "));
     * device.setUserid(    m_ResultS           et.ge   tI nt("user    i  d"));   
     *  dev          ice             .setVe   hiclei   d(      m      _ResultSet.getInt("veh  ic       leid"));      }
      *    }cat      c     h(S     QLException e) {
           * 
      * } r   etu rn devi    ce; }
     *   /
          publ    ic    List   <Tr          ackData> getLiveLo  cation         (U  se   r user) {

         List<            TrackData>     list = new     ArrayList<TrackData>(    ) ; 
      
        list = ge   t    T     rackData Li       s   t_L   ive(user,   list);

           return li       st;

    }

        p        ublic Li      s   t<       Trac  kData> getTrackD    ataList_Li  ve(User     u    ser,
                          Li   st    <TrackData>    list_trac   k_data) {
             Lis  t        <L     ong>       lis      t_devic         eI       D = new ArrayLi st        <Long> ()               ;
                    List<Long > lis     t_ veh     icleID = ne   w ArrayLi     st<Long>(                 );        
                 Strin       g colle ct_v ehicle   IDs =       "";
         //Str  ing        c oll                  ect_DeviceIDs="";
          i f   (use r.g   etUprofile  () == 3          ) {
                       collec        t_vehicleID s   = " select vehicleid    from ve    hi  cl   e";
                         }   else {
                                        co   lle         ct_     v      ehi   cleID  s =  "selec t   vehicleid fro     m user_vehicle wh    ere userid in     (selec     t   userid      from     user wher      e own erid = '"
                       + us          er.getU      serId()    
                                                                                    +    "'           or us        e   rid =   '"
                                                +         us      er.ge  tUs       erId(   )
                                         + "'  )";
        }  
        l  og.info("v  ehicle ids--->>       >              " + coll        ect_vehicleIDs);
                tr    y        {

                             m_Co  nnec       tio             n = getDbC   onn         ection(    ).getCon                       ne     ction();
                            m   _Statement = m_C   o    nnection       .create   S    tat         ement();

                                     m    _      Re      sultSet =   m_S        t  atement.e        x   ecuteQ u ery    (collect_vehicleIDs); 
                w        hile ( m_Resu  ltSet.n   ext             ()) {        
                                                                 list_veh icl   eID.add(m_ResultSet. g      etLo    n  g("vehicleid"))   ;
                      }      
                 It  erator <Lon  g> ve    hicleid_Iterator = lis         t_v     ehicle     ID.iterator();
            String dev  ic  eIds = ""      ;            
                    Long      j = 0L;
                   while            ( veh   i  cleid_Iterator.hasNext()) {
                          j  = vehic   leid_Iterator.           n  e              xt   ();
                 if (deviceIds.equals   (""))     {   
                         deviceI   ds += "select    d e        vi      ceid fr     om veh icle       wh  ere vehi     cleid=      '" + j + "    '";
                             }     else {
                                      deviceIds       += " u    nio  n   "     + "se   lec    t    device  id from vehicle wher  e vehic leid='"     + j  + "'";
                            }
              }
                  log.in        fo(   "   deviceIds----    >>>"    +     de   vic            eIds);
              m_ResultSet   = m_Statem      ent.exec      u  te Quer  y (d    e                 viceI ds);
                                 whi   l    e (m_ResultSet.next      ()) {              
                     list_de   v    ice ID.add(m_ResultSet.getLong  ("d eviceid "));            
            }

                              It         erator<Long      > d                            e vic           eid_Iterator   = li   st_deviceID.it   erator();

                      Stri   ng str = "";
                              Long i =   0  L;
             while (deviceid_Iterato   r.    h     as     Next       ()) {
                i =         dev  icei      d_I   t  era      tor.ne      xt();
                              if (              str .equals("")) {
                                                                 str +=  "     s e           lect vreg i           ste  ratio     n_n   um, Ph        one    _No,  cv,   L      atitude,Longitude,S     peed,A      cc_Status,location      f         rom          livemessagedat  a jo   in veh      icl    e on            livemessage  da   ta.         Ph one_No=vehi    cle.dev  iceid where cv" 
                                     + "     in( "
                                        + " s   elect max(cv     ) f   r     om liv        emes   sagedata where     P                   hone_             No         = '"          + i + "' "
                                     + "                           ) "
                                      + " an         d "  
                                    +       " Phone_   N        o = '" +       i + "'";
                       } els     e {
                       str += "uni on "
                                                 + "select vr      egister  ation_n           u    m, Phone_No  ,cv ,Lati  tud e,Longitude,Speed,Ac  c_S  ta              t   us,locati      o      n from live   messagedat  a j    oi n  vehicl e on      l   iv     e    message   d ata  .  Phon                    e_  N o=vehic     le  .devi  ce       id    where  cv"
                                                 + "   in( "
                                                  +      "      selec   t max(c     v) from livemessagedata where    Ph      one_No = '" + i     +    "' "
                                      +        "            ) "
                                           + "    and    "
                                             + "    Phone_No   = '"  +          i + "'";
                              }  
                   }
                  log.info (str);   
                                    Sim     pleDateForma  t df =        n        ew Si       m    ple     Dat      eF  ormat("dd-MM  -yyyy H      H:mm:ss") ;   

                    m  _Result  Set      = m_Statement.execut eQ    u                ery(s   tr);
                            wh i le (m_Resu ltSet.next())     {

                                    Tr   ackData       trackDat  a =    new Track   Data(); 
                                        trackDa                    ta.setVregisteration_num(      m_Re   sultSet.getStr   ing(  "vregisterati   on_num   "));
                   tr ackDat      a  .s     e  tDe  viceid(m_Res  ul    tSet.   getS tring("P   hon              e   _No")            );
                                             Date d ate    = m   _Resul     t Se         t.g   et    Timestamp(  "cv");
                      l      og     .info("date -->>>         " + da  te)  ;

                        t  ra ck Dat      a.setDate(df.fo  rmat(date ));

                      if (t    ra ckData.getLat itudeLi   st().equals("")) {
                           trackDat    a.setLatitu   de   List(m_ResultSet.getS        tring("La   t  itude" ));
                       }  else   {       
                                            trackDa     ta.            setL             atitudeList(    t ra                 c     kD     ata.get  L       atit u deList()     + ","   + m_Resul  tS     et.getString         ("Lat   itude")     );
                                     }

                              if (trackD       ata.getLon  gitud          eList ().equa          ls("")) {
                                 tra    ckData.set          Longitude               L  ist(m_      ResultSet.   ge   t                     Strin                             g("   Lon  g    itude"))    ;      
                        } else      {
                               trackD          ata.setLon   gitudeList(trackD               at    a.              getLongitude      List()    + "," + m_Re   sul   tSet. getString("Long    i  tude"));
                                     }
  

                               trac  kD   at   a.setLocati  on(m_ResultSet.getStr   in              g("   locatio     n"));

                        trac    kData     .setSpeed(D     oubl  e.   pa    rseDouble(m  _ResultSet   .getStri     ng(    "Spe         ed   "    )));
                           int x = I          nteger.parseInt(m_ResultSet.getString("A     cc_Status"));
                                if (   x == 0) {
                                         tr    ac       kDat                 a.setAcc_status("O     FF");
                             }    else {
                           trac  kData.setAcc_      status("ON");
                                  }

                            li     st_t      rack_data.add(trackDat a);
                   }
                   } catch (SQLExcepti         on sqe) {     
                    lo     g.info       (sqe);
                                   } catch (  Exception e) {
            log         .info(e);
                   }         fi       nally {
            try    {    
                   i        f (m_Re    su    ltSet     != null) {
                            m_ResultSet.clo se(     );
                              }
                     if  (m_St at     em     ent !   =     null) {
                                m_Statement   .cl   ose(   );
                                        }
                                 if (m_Connec      tion != null) {
                              m_Con   ne  ction.c        lose() ;
                                    }
                   } c  atch (SQLExc   eption ex ) {
                          ex  .p ri     ntS   ta ckTrace();
                                   }
                  }
           //log.info  ( "Valu         e of  : " + trackDa  ta    .g    etLat   itudeList());
                           // log.info (   "Value of  : "          + trackDa     ta.getLon       gi     tudeList())    ;
                   ret     urn li    st_track_data;  
       }

     p   ublic  List<TrackD                   ata>     getHistoryLoca      tions(S   tring s   tart   Date, S   tring     endDate,   S t    ring vregi     s   t      er   ation_     nu  m) {
  
              List<TrackData   > lis    t         =      new Ar      ra yL   i       st<Trac         kData  >();

                      l  ist    = getTr    ackDataList_H   is      t      ory(list,        startDa      te, end   D   ate, vregiste ration_num);

           return list;
    }

        public List<TrackData> getT         ra    ckD  ataLi      st_His          tory    (
                        List<           TrackDa     ta> list_track_   data,               S  tring s  tartDate,   Stri ng     endDate, String vregisteratio    n_num) {

        try {

                       m_Connect   ion = get Db Connection  ().g  et    Conn           ecti on();
              m_  Statement = m_C onnection.cr        ea                       teState  m   ent      ();
                          Stri             ng  m_Q  uery   =               "select  Pho    ne_No,  cv,   Lati               tude,Longi          tude,Speed,Acc_Status,location fr      om l    ivemess         a gedat  a where Phone_No in    "
                                   + "( "  
                                     + " se        lect deviceid from v             ehic    le wher  e vregistera  tio   n_nu m = '" + vr    egi  st         eration_num + "   ' "
                                  + ") "
                                                        + "a nd "
                       + "cv b   etween "
                        +     "'"   + star  tDate +   "'         "
                             + "and       "
                      + "'" + endDa te + "'";

                                       SimpleDat   eFormat df =     new                SimpleDateFor      mat("dd-MMM        -yyy   y h   h:mm   :ss");
                        m_ResultSet = m_Stateme           nt  .ex   ecut  eQuery(       m_Query);
                           lo   g.info(       m _Quer y);

              wh             ile (m_R   esultSet.next  ()) {

                             TrackData tra       ckData = ne       w  TrackData     (      );
                           tr   ac  kData.setDev  iceid(m_Res        u   ltSet .getS     tring( "Pho   ne_ No"));
                         trackDat   a.setDate(df.form         at(m     _ResultSet.getTimestamp("c v")));   

                            if (track      Data.g   et   L    ati tudeList().equals("")    )       {
                         t    rac       kDa  t  a.s       etLatitude    List(    m_ResultSet.getSt        r            ing("  Latitu   de"));
                        }  else { 
                                   trackData.setLatitudeLi      st(trackDa    ta.getLat   itudeList() +   " ,     " + m_R       e   s    ultSet  .g     etStrin   g("Latitude"));              
                           }

                 if    (trackData.g  et LongitudeList().equals("  ")     ) {
                                        tra   ckD ata.setLongitudeLis    t(m_R esult      Set.getSt rin           g  (   "Lo              ngitude"))      ;                        
                         }     el   se {
                                                tra                       c     kData.setLongi    tudeLi  st(trackDa     ta    .    ge t         LongitudeList()       + "," + m_Resul    tSet.       getStri                ng(       "Longitu   de"));
                        }     

                                      tr     ac        kData.setLocati  on(m_R                                esultS    et.getString  ("l  oc   ati         on")  );

                                                        t rac  k   Data.setSpeed(Do uble.      p a   rseDouble(m_R   esul       tSet.getString("S  peed")  )  );
                  i    nt x = I          nteger.pa  rse   Int(m_Result            Set.  get           S t      ring   ("Acc_St   a      t   us"));
                       if (x =   = 0) {
                                             trac  kData.set Acc  _status("OF        F");            
                         } else {  
                         trackD   ata.setAc         c_status  ("ON");
                       }

                list       _track_da  ta  . add(tr     ackDa  ta);    
            }

        } c    a   tch    (SQLException    s   qe    ) {
                      lo    g.i     nfo(sqe)         ;
               }      finally      {
                             t               r    y {
                            if        (m_R  esul   tS    et      != null)     {
                                    m_ResultS           e       t.cl ose();   
                }
                 if        (m     _Stat  ement != null) {
                                   m_St   ate      ment.clos    e();
                                         }
                if    (m_Connec  tion !=  n  ull) {
                             m   _Connection.clos    e()       ;
                      }  
            } catch (SQLE   xc             ept  ion ex) {
                                 ex.pr  int  StackTrace  ();
             }       
                               }      
        retu rn li st_track_da ta;
    }

    public  void        c  reateVehic  le(     Ve  hicle     v ehicl       e)     {

              String qu      e ryInsert =    "inser         t i n to veh   icle values('     "   + v          e          hi cle.    getVehicle    Id()  +     "'         ,'" + vehic   le.   g  etVn()                +      "       ','"  + vehicle.ge  tRe    g            istration_date()   + "','" +                veh    icle.getVm    a     k               e() +       "','" + vehi cle     .getVmo    de  l() +  "',      '" + vehicl           e.getVfue l   _type() +           "','"    + vehicle.getFleet(  ) + "','" +          ve h     icle.ge tUs    erid()           +   "','" + vehicle.g   etDev       iceid() +  "','" +              ve   hic    le.getActive() + "'          )";  
        log.info(queryInsert + "***********            *****   ****  ***** ****");         
               try {                 

            m  _Connection  = getDbConnection  ()   .  getCon   necti   on()   ;
                 m_ Sta   tement = m_Connecti  on.crea  teStat           ement();
                       int i = m_Sta  t e    m         ent.e      xecut eU  pdate( queryI nser t);
                        lo   g.i         nfo(i +    "          @@@@@@@    @@@@     @  @@ @@@@@@@@@@@     @@              @       @@@ @@@@@@@@@@")       ;
         }    catch (          SQLExce           pti     on        e) {
                        e.pr     intStackTrace();
                                } fi      nally {          
                        try {
                            if (m_Resu ltSet   != null) {
                               m_ResultSet.close(   );
                  }
                               i         f   (m_State                  ment     != null)              {   
                       m  _S  tatem ent.c    lose              ();
                                     }  
                          if (m_C      onn        ection      !    = null) {
                                       m_      Connection.close();
                                                          }               
                                } catch (SQLExcep     tion ex)            {
                                 ex.pr  in   tStack      Tra ce(   );
                             }    
           }
    }

          publi   c           Lis             t<Vehicle> l  ist      Vehicle(int u   seri     d, int u          profil    e) {
              L i      st<Vehicle      >    ve hicle            _lis       t = new Ar   ray           List<Vehicle   > ();    
           Stri  ng query     List = ""   ;
          if (u     profile == 3) {
                    queryLis    t = "select    * from vehicle";  
           }    e     lse {
                query       List = "select *      from        vehicle where vehi  cl    ei d      in"    
                        + "(        s         ele ct   veh  i   c     l       eid fro   m                    use      r  _ve hi     cle w    here userid i   n"  
                                     +  "(s           elect userid from us er whe     r  e u    serid='" + userid +      "  ' or owner   id='" +       us   erid + "'))"   ;
           }
                 tr        y {
  
              m_Conne          ction = getDbConnect  ion().getConnection();
                       m_Statem    en         t = m    _C     onnec       ti    o       n.crea      teS    ta   teme      nt();
                               m_     ResultSet =   m_                                   Sta     tement.e            xec     uteQuery   (query       List  );

                               while (m_       ResultSet.ne      xt(   )) {
                                      V  ehicle vehicle = new      Ve    h                icle   (       );
                        v        ehicle.setVe   hi     cleI      d(         m_Result            S et.getLong("ve hiclei    d"));
                                       v     e   hicle. set  V  n(m_Re     sult    S  et.getSt    ring("vre   gisteration_n  um"));
                          vehic   le.setRegi   s             tra     tion_date(m_ResultSet.g   e  tStrin  g("      regist  rat    io n   _d             ate         "));  
                            vehicle.se   tVmak   e(m_Result      Set.getString("      vm  ake")) ;
                              vehicl        e.        set   Vmodel(m_Res  ultSet.ge  tStri     ng("vm   od     el"));
                                      vehic    l   e.setVfue                   l_type(m_R  esultSet.ge             tString("vf   uel  _type"));
                                     vehicl            e.setFleet(m   _       ResultSe    t.get     Long("fle               et"))      ;
                      vehicle.setUs            erid(m_ResultSet.getLon g("    user id"));
                                ve   hicle  .     s et  Device     id(m_  Resul      tSet.getLon   g    ("d   evice      i  d"                 ))    ;
                                vehicle.s   etActiv  e(m_ResultSe   t.getInt("acti        ve         "))      ;
                         vehicle_  lis t  .add(vehicle);
                      }     
                 }     catc   h (         S QLExcepti     on e) {
                         l        og.in      f                o   (     e)            ;
                      }
              return vehicle_list;
      }

          public L ist<Strin    g>      listUse   r(in t useri   d) {
                                   List<Str   ing> user_list =       new ArrayList   <String>();
            try       { 

                     m_   Connection = get   DbConnec     tion().g     etConn  ect   ion();
                        m_St   atement = m   _Co     nne      ction            .create    S   tateme   nt();
                 m _ResultSet = m        _Statement  .e  xecuteQuery("sel        e             c t fn      ame,lna    me,u        email   from user where u     serid                 =     ' "
                                         + user  id
                                      + "'        or  ownerid = '"
                                            +  userid
                                        + "' and       act          ive = 1;");
                      while (m_ResultSet.next()   )    {      
                        St ri  ng fname  = m_ResultSet.getStri     ng("fname");
                         Str ing l     nam     e  = m_ResultSet.ge tS        tri      n   g(         "lnam     e");
                        St  r   in             g          email = m_  ResultS        et  .getString        ("ue  ma   i         l      ");
                                   use   r_list    .               add("Mr. " +     f n      ame + " " + lname + "," + e     mail);                 
                           }
             } c    atch (SQLE       xception e) {
            log.i  n  fo(e);
                      }
             retu    rn use   r_list ;
       }
       
    public St  rin    g    add  E  xistingVe hicle(lon      g vehicleid, int  use  rid)              {
          S    tring ac       ti  onReport = "";
                                try        {
                 m_  Co   nne      ct           ion = g      e  tDbC onnection().g     etConnectio  n();  
                m_St              ate         ment = m_Co  n   nection           .cr   ea    t    eState  ment()   ;
                  in               t      i = m_Statem     ent.executeUpdate("i    n s e  rt     into user_     vehicle values (  '" + userid + "','"    + vehicl    eid +        "   '  );") ;
                               if (   i      != 0) {
                             actionRep    ort   =    " group";
                             } els   e     {
                    act      i     on     Report = "input";
                               }
                       } ca    t          ch (SQLE xce ption e) { 
              a      ctionReport =   "input"    ;
          }
               return actio      nRepor   t;
                 }

       pub           lic L      ist<Long>      get      De  v      iceId(    lo   ng      u  seri         d,      int upr   o               file) {

                                 List<Long> list = new ArrayLis           t();
                Str  ing queryLIS       TMSISD   N = "";
           if    (u   profil   e == 3)   {
                                   queryLISTMSISD       N = "sel  e              ct deviceid from dev ice where  us               e           rid in"        
                          +         "(sel      ec      t      userid from    user)    "
                                        +   " an             d devi ce         id not in(s  elect devicei        d f  r   om vehic  le )";
        } else {
            query  LI             STMSI  SD  N =    "   select deviceid     from device   whe  r e  use    r    i    d in"   
                                                         +         "(select      userid from user w  here  own     eri   d ='" + u    se rid +     "'  or use   r   id ='"  + useri    d + "'   )"
                                             + " and de  viceid   not     in(select dev              ice      id from     vehicl e )";    
           }
        log. i        nfo (q      ueryL         ISTMSISDN);
           try              {
            m    _Conn        ecti  on =    get            DbConnection (  ).get     Conne                 ction();
                        m_St      at ement = m_Conne ction.createStateme   nt();                
            m_Re        sultSet = m_Statemen    t.execu        teQuery    (queryLIST              MSI   S   DN);

                             wh  ile (m   _R es        ultSe     t.     next    (    )) {
                           list                       .add(   m_Resu ltSet.ge   tLong("      de    vi          cei   d" )  );

               }
                              } cat                     ch (SQ   LExce  ption e) {
                                              e        .pr  intSta  c               k  T   race();
            } f   inally {
                      t  ry {
                                                if (m  _Resu   ltS    et != null) {
                               m_ResultSet.close   ()     ;
                  }
                                i  f (m   _Sta                 tement !    =  nu        ll ) {
                                   m_Statemen     t.clos   e  (   );  
                   }
                                         if (      m     _Connection != n    ull) {
                                                       m_Co     nn  ect      ion.close         ();
                }
                       }      catch (SQLException  ex) {
                                          ex.print     St      ackTr     ace();
                     }
             }

                     r  et urn l  ist;
    }

    pub lic      int g     etMaxVehi    cleI  d() {
                            i         nt id       = 1;
        St   ri         ng que      r         yMAXID                =   " s  e     lect max(v  ehicl      eid          )   fr     om  v      ehicle";

        try {
                      m_Connec       t  ion =    get D         bConnection      (     ).getCo   n  necti          on()          ;
                  m_State   me   nt = m_Conne     ction.c   reateStat  eme   n  t();
                     m_R       esultSe                  t = m_Sta  te     me  nt.e    xe       c       u    t     eQuery(que  ryMAXID         )    ;
                                          if (m_ResultS et.next     ()) { 

                         id =   m_Re  sult  Set.getInt(    1) + 1   ;

                    }
             }    catch (SQLExcep      tion    e) {
                        e.p    ri      ntSta    ckT   race   ();
                             }    fin   ally       {
                  try {    
                        if (m_Resu  l     t     Set      ! =    null) {  
                                      m_ResultSet.close  (  );
                          }
                  if         (m_S     t  atem ent != n       ull) {
                                               m                 _Sta          t          em    en             t.close();
                                            }
                                if (      m_Conne ction                != null) {
                       m_Co      nnect    io      n. c   lose();
                         }
                    } ca        tch         (  SQLException ex) {
                                e  x.printStackTrace();
                      }

        }
            return id;
          }

     pu    blic              List  <I   nteger> ge    tUserIdList(  int    u    serI  d, in      t uprofil   e)       {
        Lis   t<   I n    t  eger  > list      = new        A    r rayList() ;       
          Str  i       ng queryLISTMSISD    N = "     ";
                    if     (upr    ofile == 3) {       
                    qu         eryLISTMSIS     DN      =                  "se  le  ct           u    serid f  rom user"  ;    
            }                   e  lse {
                         q       ueryLIS   TMSIS  DN = "select     userid from user where    us    e    rid='" + use  rId          + "' o      r                  o   wnerid    ='              "    +   u     se    r   Id +    "'";
            }
                     l   o   g.i   nfo(queryLI  STMSISDN)   ;
                   try {
            m_Connectio   n    = ge         tDbConnection       ().     get      Connect       ion()    ;
                  m_State            ment = m_Con   nectio  n.createState   m     e     nt();
                      m_Resu   l     tSet =  m_Stat    e    ment  .executeQ             u          e   ry  (q ueryL     I STMS  IS        DN);

                w     hi  le ( m_ResultSet.       next()) {
                list.           ad   d(m_Resu    ltSet.getInt("user  i  d"))  ;
      
                }
          } ca     tch     (SQ    LExcep       tion e) {    
                      e.printStackTrace     ();
            }   f      inally {
                try          {
                   if   (m_Re    sultS       e    t                 != n     ull)    {
                              m_R  esultSe   t.cl   ose ( );
                   }   
                                    if (m_Statemen    t !    =    null) {
                                m_Sta    teme    nt.clo  s   e();
                          }     
                                  if (       m_Co   n nection != null)     {
                                       m   _Connection.clo         se();
                         }          
                                            } c      atch    (SQLExce  pti       on ex) {
                          ex.print  S  tackT   race ();
                         }
            }

                return li   st;
      }   
 
      pu  bl              ic Vehi             cle ge         tVeh         ic  l      e(long vehi  cl  eId) {
                String q         ueryLISTM    SI   S    D     N           = "sel  ect *          from                        v  ehicle where ve        hi     cleid=' " +         vehicleId +     " ' ";

             lo     g  .info         (que  ry  LIS    TMSIS  DN    + "qqqqqqqqqqqqqqqqqqqqqq");

             V      ehicle ve      hicle     = ne  w Vehicle()   ;
        try {
                 m_Connection    =   get      DbCo nne     ct      i   on().getCon nection();
                      m_Stat ement = m_Connecti   on.c              reateStatement();

                      m_ResultSet =            m_Sta            tement. e    xecuteQuer      y(q      ueryLIST   MSIS       DN     );  
 
                 whil  e     (m_ Re s     u   ltSet.n ext())                 {  

                                  v ehic        le.setVehi    cleId(m_Res        u        l tSet.g   e       tLo              ng("veh            icleid"));
                                 vehicl    e.set Vn(m_R es    ultS  et.get          String(    "vre    gis teratio     n_num"))     ;
                 veh                 icle.    setReg  is     tr      atio     n_d     a   te(m  _Resu  ltSet.getStrin  g("re  gistration_date "  )      );
                                                            vehi   cle.setVmake(m_Res    ul tSet.getString("vmake"     ));
                     vehicl  e    .set V      model(m_ResultSet.getS   t        r    i    ng("     v         model"));
                                             ve      hicle.                           s  etV f  u el_  typ     e(m_R  esultSet.getStr    i      n  g("vf            uel_ty  pe"))   ;
                                     ve     h  icle.se   tFleet(m_       Resu                   ltSe     t.   getL  o ng(      "fleet"));
                                       veh i cle.setUseri  d(m_Res ultSet.getLong("userid")    ); 
                                                                  vehi c    le.setDevicei      d(m_ResultSet      .getLong("deviceid")) ;  
                         v     ehicl      e.setA        ctiv    e                          (m   _Result    Set   .                    getInt("a              ctive"))   ;


                    } 
             } ca         tc  h (SQLException e)           {
                                 e   .printS tack            T   race();   
                                  } finally {
                             try {
                    i  f (m_ResultSe  t != null) {
                                    m_Re   s   ultSe         t.close();
                     }
                                if (m_S       tate me    nt   !   = nu     ll) {
                                                 m_S   t        atement.close();
                                       }
                                    if (        m_Connec  tion !=   null)      {
                                      m           _Connection  .c  lose();                
                       }
                  } c   atch (SQLE        xc     eption   e x) {
                                                   ex.printStackT race();

                     }
            }

                          retur        n    vehicl                             e   ;

    }
  
    public      Strin         g val  idateDeviceIn         Veh   icle(long deviceid, lo             ng vehicleId  )    {
                    St       rin  g   a    ctionR      esult =      "";
                        try    {
            m_Connection = getDbCon   n    ectio            n    ().g     etConnection();
               m_     Statemen  t          =         m_  Co   nnection   .createStateme   nt()   ;   
                            m_ResultSet = m_Statement.execu     teQuer   y   ("         select dev iceid   f rom vehi   cle w  here d                 eviceid ='" + deviceid
                       + "' an    d vehicl    eid='"        +      vehicleId +    "'");
                        l   og.info(m_Re sultSet + "Re  su          ltSet");
                   if (m_Res                   ultSet.nex    t(    ))      {
                                                        ac     tionResult = "ok";   
               } el     se      {
                               try {
                                 m_Result Se  t =          m_Statem       e  nt    .exe                  c u teQu   er  y("selec   t devi    ceid fro    m vehicl  e     where deviceid='" + deviceid     + "'");
                                      if    (m_ResultSet.next()) {
                                           actionRes    ul t =    "deviceidExist";
                       }      else {
                                                                   act  ionResult = "ok";
                               }    
                                        }            catch (SQ     LE xc   e          ption se)  {
                              s     e.printStackTrac  e();
                                           } final   ly   {   
                         t ry         {
                                                     if (m_Re                sul       tSet !=   null)             {    
                                                                                  m_Res       ul tSe   t.clo    se();
                                          }
                             if (m          _Stateme  n  t !=         null) {
                                                                 m_Statement.clo  se();
                                        }
                                             if (m   _Con  n    ection    !=      null   ) { 
                                    m_Con    nec  tion  .cl       o    s    e(  );
                             }
                            }     ca tch (  SQLE   xce ption  ex) {
                              ex.printStack Trace();
                     }
                                                  }     
                     }

                   }          catch (SQ  LExc  ept              i  on             e) {           
                         e.    printS   tackT  rac    e(       );
                       }
           r       eturn actionR  es                ult;

    }
      
    public String updat    eVehicle(Veh   ic   l  e vehicle) {
             St  ring         acti      o    nResul  t = "";
           Stri    ng qu     eryE           DITMSI    SDN = "upd     a   te vehicle set de   vice     id='"   + veh            icle.g   etDevice   i   d () + "'       ,v registeratio       n_num='"
                                     +    ve     hicle   .getVn()    + "',vma  ke='     " +     vehicle  .ge    tV      m  a     ke() +             "',vmo    del ='"   + vehicle.ge  tVmodel(                  ) + "',vfu     el_t   ype='"
                               +      vehi   c      le.ge         t    Vfu  el_type(     )   +          "'           wh       ere vehicleid='" +          ve    hicle.getVehi   cl  eId() + "' "       ;      

           log.info         (q   uery        EDITMS     ISDN              + "!!   !!  !!!!!!!!!  !!    !!!!!!!                     ")    ;
              try {
                         m_Con  nection = get   DbC              onne     ction().getConne             ct     ion(            ) ;
                                   m_Statement = m_Co     n nection.createSt     a      tement(   );
                             i          nt i      =  m_Statement.exe              cuteUpdate(qu     eryEDI  T        MS ISDN);
                  if      (i   !=   0)   {
                        actionR  esult = "  updated"    ;
                } else {
                                        actionR    es ult    = " notUp   dated";
                             }

              }    catch (SQLExceptio       n      e) {
                 e.pri     n  t St    ackT race()       ;              
             }  fin   a   lly {
                  t ry {
                             if  (m_Re   sultS  et != n        u ll) {
                           m_Result Set  .close();
                    }       
                               if  (m_Statement !=                    null) {
                                m_State ment.close();
                      }
                                            i    f (m_Con            n     ect  io    n             !                   = n  ull) {
                                 m_Connection.close(   );
                                   }       
                                    } catch (SQL  Exception   ex) {
                              ex .       pr   int    S     tackTrace()        ;
                  }  
              } 
                 retu  rn acti     onResult;
             }

             publ     ic       v oi  d        delete     Vehi  cle(lo    n g   veh   icleI      d    )    {

                    Strin  g queryREMOVEMSI   S            DN = "de   let                e   from ve             hi cl  e w he  r        e vehi   cleid='" + vehicleId + "'";
                   
        try {
            m_Connectio  n =     getDbConne  ction().getC      onnection();
               m          _State      m ent   = m_    C onn ecti  o     n   .createStatement   ();
                  int i = m_State       ment.exe        cuteU pd ate(qu   er         yREMOVEMSISDN);

             }        catch    (SQLExc    eption e) {
              e.p         rintSt  ackTrace(   );
              } fin   ally {
                                             tr    y {
                            if (m_Resul    tSet   != null) {
                         m_Res                         ultSet.                 close();
                                        }    
                              if (m_ S                         tateme nt != null) {
                         m_       S     t   atemen           t  . clo  se();
                                                            }
                          if (m   _Co          n   n    ectio          n !=  null) {      
                                              m_Connecti      o    n.cl     ose(    );
                          }
                      } ca        tch ( S  QLE                    xception ex) {
                     ex.      print    StackT        ra             ce   ( );
            }
                  }
     
    }

    public     List<Integer> getVehic  l   e      IdLis    t(i  nt userId        , int   u   profile) {
        Lis                         t<Integer> lis   t = new Arra     yList();
          String qu        e           ryLIS   TMSISDN =    "";
                  if        (uprof     ile ==       3)     {
                        queryLI    STMSI    SDN =       "select  vehicl             eid from veh icle";
        } el   se   {
                  quer     yL    ISTMSI       SDN       =      "select     ve    hicleid fro     m vehi   cle where ve hic  leid          in"
                                + "(selec t      vehic       leid    fr         om user_v  ehi   cl    e w      here userid in"
                                +        "(sel     ect us   erid from use  r         w  here owne  rid ='        "                     +   userId          + "      ' o   r    use   rid ='" + user     Id + "'))";
                      }
        log.       i     nfo(   qu      eryL ISTMSIS    DN   );
               try     {
                          m_  Con  nec t  ion =      getDb    Co      n    nect     ion   (          ).getConnec      ti on();
                                      m_Stat    emen     t =   m_Connect i                   on.c                  reat   eSta       tement(  );
              m_Resu     ltSet = m _Statement.exe       cute    Query(queryLISTM    SI       SDN);

                 while (m_R  esultSet.ne    xt()) {
                                      list.add(m_R  esultSet.  getInt   (     "vehi   cleid"));

                           }
                                 }     catch ( SQL  Excep      tion e)    {
            e   .pri   n      tStackTrace    ();
                    } finally {
                      t  ry {
                         if (m_ResultSe    t !=      n  ull)    {
                                    m_Resul    t           Set.c l     os  e ();
                                 }
                                     if (m_              St  atement !   =      n  ull) {
                                  m         _ Sta  temen t.clo   se();
                                 }
                                 if     (m_Conne c tion    != null) {
                                                           m_ Con  necti  on.          close();
                         }
                 } cat            ch   (SQLExcepti  on      e      x) {
                 e        x.pri    ntStackT        race();
                  }
         }

        re   turn lis  t;
    }

                       pu  blic   List<      S  trin  g>    getVe    hicl  eList(User user) {
            List<String> list = new   ArrayList<Stri ng      >();
          S        tri ng str     = "";
                          i   f             (us er.getUprofil         e()      == 3) {
                     str      = "select vr   egister   a   tion  _num   from ve   hicle ";
        }  else {  
                                    str = "select               vregiste     ration_       num f            rom v     e   h                ic   le where vehicleid in"   
                                                                 + "(s elect vehic lei    d fro  m user    _veh   ic          le w      here userid in" 
                    + "(select user       id          fr om user w   her    e ow      nerid ='" +     user.getU   se   r Id()   + "'     or use       rid         ='"   + user.getU serId() +         "  '))";
                     }
                  l og.i    nfo   ("vreg               istra       ti    on_ num " + s tr);
        try {
                                   m_Con        nection     =  getD bC  o      nnec             tion( ).               getConne  ction() ;
                             m_Sta    tement = m_      C   on    nect   ion   .   createSt             a        tement(  )    ;
                                         m_Re      sultS         et =              m_Statemen t.executeQ   u           ery(st  r );
                whi   le     (m_ResultS    et.nex  t ())       {
                                list   .add(m_Res                 u        ltSe  t.  getString(1));
                           }
                   }      c  a         tc      h (SQLException e) {
                         log.inf   o(e)       ;
                 }
           return   li    st;
     }

    public Lis  t<Report> createTri   p(      Us er user  , Stri    ng st   artD    a         te,
                            S          tri    ng           endD                            a  te, S    tri  ng vre     gistrat  i           on_num                 ) { /  / meth  od
        /    / her        e.
          Li st<Rep         o  rt>     list =      ne      w A rrayL    is   t<R   e     port>();

           Li      st<Inte    g     er    > acc_s    tatu     s_List = n      e   w Arr   ay      List<Inte       ger>(     );
        List<In     teger>  messag       e_id_L  is     t_of_T  ri          p_Re      port   = new Arra  yLis   t<Intege    r>       ();
        List<    Inte  ge   r> me     ssage_id_L    ist = new               Ar    rayLis   t  <   In  teger>();

                       Str    ing trip_Query = "sel     ect    Messag    e_ID,Ac  c_    Status f  r            om          "
                         +    "liveme     ssa                      geda ta           w  here cv        b    etween '" +       startDat  e
                +    " '             and '" +    end          Date + "   '  "
                    + "and P  hone            _     No in( selec    t de       viceid from vehicle w  here  "
                         + " vregis     tera   t    i   on        _num       = '" +     v   registrati on_n  um       + "'    )";
          log.i   nfo(trip_Query );
                 try {
                    m_   Connec    t  io     n = get    DbConnect   ion     ().getCon   n   ection();
                    m_S    tatement   = m   _Conn  ecti      on.cr    ea   teStatement();
              m_R       esultSet = m_    Statement. executeQuery( tri p_Query)     ;
                while (m_R        esultS   et.n  ext()) {
                                         me   ssa  ge_i   d_List         .ad  d(m_Resu   l    tSet.ge          tI    nt("Message_ID"));
                               acc_st   atus_Li  st.ad    d(   In    teger.parseInt(m_R       es               ultSet.   getS      tr  ing("A   cc_Status"))  );
                 }
                   for (int m : messag  e_id_List) {
                                         /   / log.inf     o( "         ##         ##   #       "     + m   );
               }      
                     for ( int m  :  acc_sta    tus_List) {
                                //  log.inf           o   ("       #        ####" +   m);      
                             }
                    int   flag = 0;
                  for (int k = 0; k <     ac  c_sta   tu          s_List.s     ize() - 1; k++)                     {
                                     if (acc_    sta    tus_Lis     t    .    get(0) ==    0)     {
                      flag = 1;
                        }
                            if (acc_status_List.get  (0) == 1)           {
                         Trip      ReportAction.s   etStar        t_location        _flag(1)  ;
                          }

                   if ((acc   _status_Li    s t.get   (k) == 0 && ac        c_status         _L   ist  .get(k + 1) =   =          1)) {
                       //if(flag =       = 0  )
                    //flag = 1; // if fl     a    g =       1 , 
                                       m                      essa ge_id_List_         of_Trip      _Report.add(messa              ge_id_L        i       st.get(k + 1   ));   
                           }   
                   i       f ((               acc_st      atus_List.g   et(    k)   == 1 &&      acc_sta  tus_List.g   e     t(k + 1) == 0)   ) {
                            mess      age_        id_Lis  t_o   f_Trip         _Re   port.add(mes         s     ag  e_id           _Li      st.get       (   k +       1));
                                                            }
                }
                for (int  m : message_id_List_ of_Tr     ip_Repor            t)   {   
                                        Syste    m.  out.pr    int("* ***       :" +     m) ;
                                     }

               S tring SQL_trip   = "  "     ;
                     Iterat       o        r         <I                     nte      ger>      tr    ip_ro w_n    um = messag   e_id_List_o     f_              Trip_R    e         port.  iterat  or();
                   whi   le (tri  p_row_num        .ha  sNe                xt())       {
                          if (SQL_   t    rip.equ    a ls("")) {
                                                S   QL   _   t   rip += "select cv,La       t   itude,  L  ongit   ude f      ro         m     live            mess  a gedat a wh    ere Message           _ID           ='"
                                         + trip_row_num.n  e   xt()        + "'";    
                                         } el  se {
                                         SQL    _  t              rip += "o      r Mes    sage       _ID          ='"     + trip_r       o    w_num .nex  t() +   "'";     
                                }
             }
            log.in fo(S         QL_trip);   
                     m_Resu  ltSet =      m  _Statement.ex  e          c   ut       eQue          ry(SQL_trip);
                  Sim      pleDat  eF      ormat          df =         n ew      Sim  pl   eDateF    ormat   ("dd-MM     M-yyyy hh   :mm:ss");
                    int f        lag_fl a     g = 0;
   
            while (m_     Resu   l        t  Set      .ne xt   ())   {
                            j       av  a.util.Date st  art_d = null;
                   jav    a.util.Da             t  e end_d = null;   
                     Report repo   rt =      ne            w Rep      ort();
                     l    og.i  nfo("       h     ere i n rep     ort..");
                         i  f (rep    or      t.        getLatitudeLi  st      () != null) {
                        log.   info("lat      i   tute    is no nu     ll..") ;
     
                                  if (re  por       t.get      Latitu       deList  ()      .    equals("     " )) {
                                            r      eport.setLati  tudeList(      m_Resu        ltSet.   getS         tring("Latitude        "));
                             } else {
                               repo              rt.setLat  itudeList(report.g            e  tLatit  udeL      i    st    () + "   ,"    + m  _ResultSe t.get   String("Latitude    ")) ;
                                }
                     } else  {
                       log.i nfo ("       latitude is             null   ...");
                                     }   
                          i f (report  .ge                   tLongitu                deLi st()    .    equals(""    )   ) {
                                report         .s    et  Longitude   List      (m   _   ResultSet.ge  tSt  r  ing  ("Long  itude")        );  
                                    }     else {       
                                         report.s     e  tLongitudeLi       st( report.ge           tL   o n   gi    tudeList         ()  +   "      ," + m_ResultSet.  ge   t   Strin          g("Long    itude"));
                                  }
                          l    og.info("sta    rting flag");      
                                         if (flag_flag == 0)        {
                                 f lag_f    l       a    g = 1;
                                      if (      f   lag == 1) {
                         star      t_d = m_ResultSet.g     etTimestamp    ("cv");   
                                                                     report.s         e   t St      artDate(df.  format(star         t_  d))    ;

                              if           (m_Resul   tSet       .next()) {
                                                               end_d    = m_Re  su l tSet  .getT  imest    amp("cv");
                                              report.setE   ndDa  te(df.format(end   _d)             );
                                                      if (report       .ge  tLati  tu    deList().equal  s(" "))  {
                                                                                 report  .setLatit    udeList(m_Resul  tSe   t.getStr   ing("Latitu      de")               )   ;
                                   }   e                  ls  e     { 
                                                   r  eport.setLa   tit     udeLis              t(report.getLatitudeL  is  t()    +  "," +     m _R   esult Set.ge                   t     Str    ing("Latitu    de"))   ;
                                                            }

                                         if (report.          getLon   gitude  List().eq             u   als("     ")) {
                                        r     eport.se tLong   itudeList(m_Res  u ltSet.getString(   "L    ongitu   de"));
                                                       } el           se {         
                                         report.setL               ongitud eL  ist(report.g      etLong  itud  eList()     + ", " +  m_ResultSet.getSt   ring("Longi      tu      d    e")   );
                                }
                              }
                             } else     {
                                              e nd  _d = m_ResultSet.getTimes   t a    mp(       "cv    ");
                                           repo  rt.          setEndDate(  d           f.format(end_d));
                                              }
                    i   f (   start_d != null) {
                                                                  report        .setDuration(       du  rat    ion     (start   _d,     end_d       ));
                            }      else {
                           r    ep     or     t.setDu   r  at  ion("Can not    fi      nd"     );
                         }
                                  }       else {
                         star    t        _d =   m_ResultSe t.get     Tim  es       t       amp("cv   ")    ;
                              report.setStartDate(df.format(  start_        d))     ;

                                       if (m_Resu          ltSet.next()) {
                                                                   end_d = m_Re                            s         ultSet.get    Timestam               p("c          v");
                                     re  por  t.setE   n    d       D        ate(df.  form at(en         d_d) );
                           i      f (r     eport.    getLati      tu     deL  ist().equals(""))   {
                                         r   eport     .setLatitu      deList(m_ ResultSet.getString("L     atitude")) ;
                                                                             } else {
                                                repor  t               .setLatit   udeList(report.ge   tLati      tudeLi  st    () + "," + m_ResultSet.getString("         L atit   ud e"));
                                         }

                                             if (    r         ep ort       .getLongi  t u  deList()     .e            quals("")) {    
                                               r    e    port       .s             etLon gitu  deList(m_Res   ultS   et.getS       tring("Lon   gitude"));
                                                  } els      e {  
                                                                                re     po         r   t.setLongitude      L  ist(rep        ort.        getL       ongitudeLi st() +   ","    +             m_Re   sultSet.ge          tStri           ng    ("Longitude"));
                                  }
                                            }
                         i  f   (end_d !=         n        u        ll)     {
                                      repor   t.set D ura   t  ion   (dur    ation(start_d, e  nd_d));
                            } el  se {
                                         report   .setD           urat   ion("Can not   f     ind");

                             }
                          log.info("before repor         t");
                      }
                                        list.add(repo   rt);
                      }



             }      catch    (  SQ         LE       xc     ept  ion e) {
               log.info(    e);
                  } cat   ch (NoSuch      ElementException ne ) {
                 l    o g.info(" #   ############## E     XCEPT ION.. :     " + ne);    
                }

                re  turn   li  st     ;
         } // crea   teTr i                 p() E   nds here     .

    pu   blic   L  ist<Report> createS             toppage(Us   er user,  S    tri  n   g sta  r  tDate, Strin  g      endDate     ,  
                  St       ring v  re     gi             str   a     tion_num) {
                            List   <Report> list = n   e    w A           rrayLi    st<Re p    or  t>() ;   

                        String hi     story_Query        = "select     cv,Latitude,Long      i  tude,S      peed       ,M  ileage_He  x   a     ,Acc_Status,    Availabi    lity_G  PS from "
                                 + "l       ivemessa           ge  d a                             ta where c  v   be  tween '"      + s        t   art                Date          
                         + "' and '    "            +      endDate + "' "     
                     + "an       d      Phon  e_  No             in        ( select device  id from           vehic     le          where"
                          + " vregiste ration_num = '"     +                          vr  egis    tration    _num + "')   an      d s          peed  =0.00                   "    ;

        lo g.info("**************    *" + history   _Q       uer  y     );
              t   ry {
                         m_C   o  nnection = getDbConn   ectio         n().getConn     ection() ;
                                m_State  m  ent =   m_Connection.createS      tateme    nt()     ;
            m_ResultSet       = m_S      tatement.ex      ecuteQu   er y(history  _  Query);
              SimpleDat   e   Format df = new SimpleDateFormat("          dd-MMM-yyyy          h    h:mm:ss");
                     while              (m_ResultSet.n   ex    t()) {

                         Re   p         ort report     =           new Rep  ort(); 

                      report    .s   et    S     tartDat e(d      f.for         m   at(m_ResultSet    .g   et       Timestamp       ("c     v"    )    ));
                       if (   report.ge    tLatitudeLi       st().equals("")) {
                                     repo r  t.setL     atitu    deLis     t(m_ResultSet.         getString("Latitu      de  "));   
                              } else {        
                               re por  t.se t   Lat  i   tu  de       L        ist(report     .getLat itu      d                    eLis     t() + "," + m_R      esultSet.getString("Latitude"))             ;
                                }

                       i f      (repor          t.             getLo ngitudeList().   eq  ual   s("")) {
                                             report  .           setLo ng    itu         deList         (m_Resu      ltS   et.getString("Longitude"));
                           }    else {    
                              re  port.     set      Longi    t    udeList  (repor           t.g   etLon    gitudeList() +     "," + m   _Resu ltSet.getStri   ng("Longitude               "));                
                                                   }

                       report.setSp   eed(m_   ResultSet.g  etStri ng("Speed"));
                               report.setDistanc    e(m     _R               e               s                 ultS    et.                  getSt     ri    ng("Mil      eage  _Hexa")   );
                           i   nt ac   c_s   tat   us = I    nteger.parseInt(m_   ResultSet.get  String   ("Acc  _Statu          s"));
                if (ac  c_statu          s == 1) {
                             report.    set  Igni      tion("   ON");   
                      }    else {
                             report.setIgnitio   n    ("OF    F");
                }             
                                  String g     ps = m_ResultSet.getStri   n         g(  "Av  ail   abil        ity_GPS");
                                 if (gps.equ  als("A")) {
                       report.se    tGPS(                  "Av       ailable"       );
                                               } else            { 
                                          re   port.setGPS("Not Av  ailabl    e") ;
                              }
                l         i   st.ad   d(re    port);
                         }
         
              } catc h (S     QLExc  eption e) {
                               log.  info(e + "*    *** ***   **  *******   **  ****")      ;
                     } finally        {
              t     ry {  
                     if (m   _ResultSet != null)       {
                                m_   R             esultSet.clo   se();
                      }             
                               if (m_       Statement       != null       ) {
                               m_       State       m     e  nt.c   lose();
                    }  
                    if (m_Co   nne c          tion != n   ull) {
                                    m _    Connecti   o     n.clos e  (         );         
                          }  
                           } catch (SQ      LExc    ept      ion ex)     {
                 ex.pr intSt      ackTrace();
                        }
    
        }

                  r           etur n   l ist;
              }

    //RIT        E    SH
       public    String du  ration     (Date date1, Date     date      2      ) {
                  Strin     g to    tal        _dur    ation = null;
          tr                     y {     
                       SimpleDateFo       rmat       sdf = new Si    mpleDat    e  For    ma  t("dd  -MMM-yyy dd:m m:ss"  );
              St       r  ing  date_S1 = sd  f.form    at(d   ate1)   ;
                          String    date_S2 =     sd      f.form   at    (date2);
                          D   ate d1 = sdf.p ar        s  e(d            ate_S1); 
            Date d2  = sdf   .par   se(d    ate_S 2);
                       lon     g  d  iff = d2.get                Time() - d1.getTime();
                   long     sec =   di        ff / 1000;
                       long y    ea          r =            se c / (60 * 6    0      *    2  4 * 365);
                    l ong month     = (sec - year * 60 *    60 * 24 *    365) / (60     * 60 * 2       4 * 30);
            lo     ng day =      (sec - year * 6  0 * 60     * 24 * 36    5 - mon       th * 60   * 60 * 24 * 30)
                                  / (60    *     60                * 24);   
              lon g hou               r = (s ec - year * 60 *       60             * 2          4     * 365   - month   * 60  *   6                        0 * 24     
                               * 3 0 - day * 60 *      60 *                          24)
                           / (60 * 60    );
                        lon   g min =    (sec - year             * 60 *    60      * 2  4 *             365 -      month * 60       * 60 * 24 * 30
                                         - day    * 60 * 60 * 24   - h o      ur * 60 * 60)    / (60);
                        long sec       onds = (          sec   - year *      60 * 60 *        24     * 365 - mont     h * 60 *   60 *     2   4
                          *        30 - day * 60       * 60 * 24   - ho    u   r * 60 *    60 - mi       n *           60);

                  to     tal_duration =          y  ea  r + " YEAR "     + month    + "   MONTHS               " +    day + " DAYS   "
                                            + hou    r + " H      OUR " +           min +          " MI    N " + seco         nds +   " SEC ";
             }   catch (    E           x    ception e) {
                log.info(        e);
                  }
         r     et urn total_dura             ti   on;
        }

           public   Lis              t<Report> cr e   a      teIdeal         T   i     me(User user,   St    ring startDat   e ,
                    S tring e       ndDate   , S   tring vr                egistration_n   u m) {
 
           Map<  Integer,      j    ava.        util.Date> date_List = ne    w    Ha  shMap<      I nteg er,                  jav        a.util.Date>(); 

              List<Report> list     = new      A     rra   y    List<Report>();

                 Lis         t<Doub       le> speed      _Lis t = ne  w  ArrayList<Doubl    e>();  
                List   <Integer> acc_status_List = new     A       rrayList  <       In  te   ger>  ();
             Li st<I  n     teger> m    essage_id_List_ of_Id           eal _     Report = new Arr   ayList<     Integ     er>();
            List<     Integer>                mes  sage_i   d_List = new Array     List<Int  eger>();
   
                Stri      n   g    trip_Query =      "    select  M  essage        _ID,cv,Speed,Acc_   St      atus fro   m "
                                             + "livemessagedat     a where cv be   twee  n '"   + startDate
                              + "' and          '"     +     e      ndDate     +      "'    "
                     + "an    d Phone_No       in( select deviceid from    vehicle    where   "
                         +               " vreg        isterati   on_    num = '" +      vregistration_n     um    + "   '    )";
             log.info    ("   Idel time r    e port--->"               + tri    p_Qu       ery);
                       t    ry {
                           m_Conn   ection = g      etDbCo      nnection().get Co          nnection();
                           m       _S        tateme        nt =    m_Con            nect   ion     .createStatement  (                 );
              m_     Resu          ltSet = m  _  Stat ement  .     executeQ     u       ery(  trip_Qu   ery);
                        while (m          _      ResultS   et.next())              {
                int id       = m_ResultSet.getInt("Mes     sage_ID");
                mes     sage_    id_List.ad  d(        id);
                   date_      Lis          t.put(id,    m_R   es   u   l  tSet   .getTime     sta mp      ("cv"));

                 // log.     info    (da   te_L                      ist.get(id));
                      speed_Li    st.add(Double. par  seDo uble(m_Res     ul   tSet.getString("S      peed       ")));
                              acc_sta    tus_                List.  add   (Intege  r.    parse   I    nt(m_Res  u            ltSet.getString("Acc_ Sta   tus")));    
                            }  
            for   (in    t m           :      message_id_Lis      t) {
                          / / log.   info("#####" + m)  ;  
              }
                        f     or (doubl  e m  :   spe     ed_List) {
                         /  /   log   .in         fo(      "speed   --> " +               m);
            }
                                                  for (    int      m      : a c  c_stat  us_L   ist)    {
                  //           lo  g.info      ("#####" +      m);
            }
                int f lag      = 0;
                           int flagDu              m  my = 0;//use                    t  o ge           t start and end point location
                int start_M     es      sage_ID       =    0       ;
                        in      t end_Me  ss age_ID =  0  ;

              for      (int                 k = 0;             k   < speed           _List.size  () -   1; k++) {
                                                log.info("v    alue         of itration --->" + k);

                                 if  (speed_Li     s         t. get(k)   >      0.00 &&   (spe ed_L    ist.g  e   t(k + 1) == 0.00        && acc_status_List.ge   t(k + 1 ) =   = 1)) {   
                                           // log    .info("hell  o in if");      
                               log.i  nfo("   value of k       in       i       f cond it     i         on -->" + k);
                                                          log.info(  "sp        eed          -->"       + speed_List  .get(k ));
                              // log.  info    ("acc_status      -->" + ac         c_st      atus_L  i       st.get(k   + 1));
                                    /  *      *
                                           * Make it sta  r       t   poi      nt.
                         */
                                           sta rt_Message_I      D = mes s    age_id_List.get(k + 1)   ;
                               log.info("start msg id  -->" +              s  t          a    r    t  _Message        _ID);
                                     fla gD         ummy = 1;
                                 fl   ag    =  1;      / /             Repr esent start      point.
                                   I  dealT     imeRep      ort   A  ction   .se   tSt         art_locati      on_flag(1      );  
                   }// if clo    s           e he       re       ..
                       els      e {    
                                    if    (flag   Dum                 my ==  1)    {
                              if (speed_List.get(k)    =    = 0 &&  acc_s     tatus_Li           st.get(k) == 1) {
                                                         c           ont   inue;  
                                       //Her    e , we       go    t   contino   u   s ideal    c   onditio    n. So ,   make it  contin   ue for fi   nd ou    t    end         point of ideal session.
                              } els   e   { 
                                                      end_Message_ID     = message_id_List.get(k);
                                log.info("v    alue of k  in els      e condit    io   n -->"      + k);
                                              log.inf    o("End msg       Id -    ->    " +              end_Message      _ ID );
                                        flagDummy    = 0;  
                                                             /   /   This  is the en d po  int of ideal se  ssion.
                                                           }
                                   }    else     {
                                                       cont  inue;
                                              }
                           }
                          /*
                               Now check for Id         eal d    ura  tion s ould be   grea        ter      than    2 minutes.
                           */
                                try       {
                             //log.info(start_Message_ID            + " End /" + end_Me   ssage_ID)   ;
                                             lo        ng t1 = 0L           ;
                          lo ng                          t2    =      0L;
  
                            if (start_Message_ID       != 0 &  &   end           _Message_ID != 0  )                 {
                                                      log.info     (s   tart_Message_I      D + "   End /" +      end_Me  ssa   g         e_ID);
                                                  log.inf   o(  "   here if start a     n   d end m   sg  id     n   ot o");
                                                    t1 = date_  Li     st.get(end_Me        ssag e_ID).getTi me();
                                  l    og  .in   fo("        val           u   e of t1 -->" + t1);
                         //        .g   et  Time();
                                 //   log.info("there");
                                            } els       e {
                              t1     = 0    L;
                                                   }
                                      if (                star t_Me  ssage_ ID != 0    &&         end_Messa    g  e_ID    != 0   ) {
                                   t    2 =          d     ate_List.get (st    a      rt_Message_I     D).get   Time   ();
                                                 log       .in  fo("value of          t2-->  "    + t2   );
                                 } else {
                              t2 = 0L;   
                              }
                          if (start_       Mes   s       a               ge_ID !=               0 &&       end_Message_ID     != 0) {
                              l    o    g.info("value of time           difference"     + (t1 - t2   ));
                                             lo        g.       info("value      of 2 min   " + (2    * 60 * 10     00));
                                                  if ((  t1 - t2) > (2 * 60 *  1000)) {
                                          message_id_     List         _of_Id  eal_Report    .add( start_M    essage_ID);
                                                             message  _  id_   L     ist_of_Ide  al_Report    .ad     d(end_Messa           ge_ID);   
                                        start_Me     ssage_                 ID = 0;
                               end_Message _ID = 0;        
                                                          log.   info("     in t 2 - t2-  --->>    >");
                                                          for (int m :       messag           e_id_Li  st_   of_Ideal     _Report)   {
                                               log            .info("   message_id_List_of_I   deal_Repo    r    t-->" + m);
                                 }   

                                 } else   {
                                        log.info(    "clean the    value of start    an d end     msg_id");
                                                          start_      Message_ID =     0;
                                            end_Message_ID = 0            ;
                                                           }
                           }     
                         }    catc   h (Nu         l lPointerEx ception e) {
                     e.pr      intStac   kTrac             e();
                      log.info             (   e +  " : T    h        i       s         is the reason.");
                                  break;
                                  }
                   // s       to    p 2 minc   alculation   l        og     i      c
              }

            Strin  g SQL_trip = ""   ;
                  Iter ator<I    n      teger> trip_row_num    = me ssage_   id   _   List_of_Ideal_R       eport.iterator();
                 while     (trip_row_nu  m.hasNext())  {
                           i   f (SQL_trip.eq       uals(""))      {
                                               S      QL_trip += "select cv     ,Latit     u de,   Longi     tud                    e fr    om livemessa  gedata where Me      ssage_ID='"
                                             +    trip          _ row  _n                  um.nex   t(     ) +                    "'"    ;
                } else {
                        SQL_tr     ip    +=        "or Me         ssage_ID=   '  " + t    rip_row_num.next() + "'";
                                   }
                     }
                  l   o   g.info("SQL     -Trip----->    " + SQL_trip);
                          m   _ResultSet = m_St atemen    t.executeQuer    y(SQL_     trip);
                   Sim pleDateFo    rm at df = new Simp                 leD  a   teF  ormat("dd-MMM-     yyyy hh:mm:s  s");

                    whi   le (m_R    esul       tSet.n     ext(     )) {
                       j ava.uti l.D           ate st      art_d = nu    ll;
                      ja     va.uti     l.Date end_d         = nu ll;
                    Rep ort report = new Report();        
                          if     (re           p  o   r   t.get   Latit     ud    eL  ist().e qual     s(          ""                  ))      {
                    repo rt.setLati  tudeList(m_Result   S  et.getSt ring("Lat   itude")); 
                       } els           e {
                                     report.setLat   itude List(repo rt.getLatitudeList( ) + "," + m_ResultSet.g et    Strin  g("L   atitud     e"      ));    
                   }    
      
                           if (    report.get     Lo     n    g    itudeList(    )      .equals("")) {
                           report.set      Longi   tudeList(    m_Res   ultSet.get  String ("L      ongitude")  );
                                             } else {
                        repor  t.set      LongitudeList(r  epor     t. ge   tLongitudeLis  t()       + "," + m_ResultSet       .g       etString("Long  itude")      )    ;
                               }
                                        start_d                         = m_Resul tSet.getTimestamp("c                      v");
                 re    port       .setSta      rtDate(df.forma       t(start_d));

                  if (m   _Re    s    ultSet.next( )) {
                               e  nd _d     = m_Re sultS   et.getTimes tamp("c      v");
                                    re por    t.setEndD       ate(df.format             (end_d));
                         }
                           if (s   tart_d !=  n         u    ll  )      {
                                  re          p ort.setDuration(dur        at          ion(s  tart_ d, end_d)    );
                     } els   e {
                                    rep or   t.s     etDura      tion("Can n    ot find      ");
                         }
                       lis   t.add(repor    t); 
                        }
            } ca           tch   (SQ   LExcep    tion e) {
            log        .in f  o(e    ) ; 
        } catch    (NoSu    chElementExcept ion ne) {
                   lo   g.i  n    f o("      ######### #####     # EX CEPTION.  . :    " + ne);
           } 
        retu  rn list;
    }

    public     List<  Repor  t> creat   eHistory(Us   e   r      us         er,    St ring startDate,
             S   tring            endDate, String   v      regist   ratio  n_  n     um) {


        L   ist<Repor  t> l      i st = new ArrayLis    t       <Repo  rt>();

                String    h         istory   _Query = "selec   t cv,L     at  i     tu   de, Longitude,Speed ,       Mileag  e_Hexa,    Acc_Status  ,Ava  ilability _GPS from "
                          +  "liv  emessagedata where cv betwee    n '"  +              startDate
                         + "' and '" + endDa     te + "    '       "
                      + "and Phone_No in    "    
                       + "(select de             v    icei       d from vehicle    where vregist era     tio     n_num =     '" + vregistr         ation_n        um           + "        ')";

                 l      og.info("*** *******   *****" + h   istor    y_Qu  ery);
                t   ry {
                m_C  onne  ction = getDbConne  c   tion().ge          t  Connectio  n();
                         m_Statement = m_Con   necti  on.cr       eateStatement(        );
                      m_ResultSet =     m_Stat      e ment.execu t eQuery(histor  y           _Query);
                  SimpleD at   eForma          t df = new SimpleDate    Format("dd-MMM  -    yyyy     hh:mm:s s ");
            whil   e      (m_    ResultSet.next()) {

                     Report r    e    port = new       Report();

                                  r   eport.setStar   tDat    e(df.format  (m_ResultSe t.getTime      stamp    ("cv        "                     )));
                                         if      (rep   or   t.getLatitudeList()  .equals("      ")) {
                          report.setLatitudeList(m_  R   esul     tSet.getStri             ng ("Latitude")) ;
                                       }      else {
                          report   .setLatitu deLis    t(   report.get LatitudeList() + "," + m_ResultSet.ge    tStrin  g("Latitude")) ;
                  }       

                    if (report.getLong              itu       d eList().equals     ("   ")) {
                                    report.s   et      LongitudeL            ist(m_  Result    Set.getString("Longi    tu      de"));   
                     } else {
                                report.setLongi  tudeL    ist(r    e    port.getLongitudeList() +      ","  + m_Res       ultSet.getString   ("Longitude"));   
                        }
                        report.set Speed(m_     Result  Set.g  etSt    ring("Spe       ed"));   
                    repor       t.s   etDistance(m_R  es  ultS  et.    getString("Mi   lea   ge_Hexa"));
                          int  a              c        c             _sta    tus       =        Integer.pa        r        seInt   (m_ResultSet.  get      Str       ing("  Ac     c_Sta    tus"));
                                    if      (ac   c_st atus ==       1) {    
                                     repor          t.setIg  nition("ON     ");
                      } els   e {
                           report.setI       gnition("   OFF");
                }
                     String gps    = m_     ResultSet.getString("Av           ailability _GPS");
                        if       (gps.equa   ls("A")) {
                       report.setGPS("Available");
                      } else {
                                           report.setGPS("N      ot A   va ilable");
                                        }
                          list.add(repor    t);
            }

        }      ca   tch (SQLException e) {
                  log.info   (e + "**********************"    );  
           } finally {
                   try {
                           i   f     (m_    ResultSet != null) {
                                          m_R   esu   l                  tSet.clo  se();
                           }
                    if (m_   Sta       tement !=     null)   {
                      m_Sta    tem     ent.close();
                                  }         
                        if (m_Connection != n  ull) {
                            m_Connect    ion.c    lose();
                       }
            } c   atch   (S        QLEx   c     ep       tion ex      ) {       
                 ex.pr    intSta ckTr ace();
                          }

           }
         return lis       t;
    }

    public Lis       t<Rep      ort>   create   O        verSpeed(User    u    ser     , String sta   rtDate, Stri  ng endDa  te,  String vregist    ration_nu    m, i nt over    Sp  ee                   d        )     {

            L   ist<Re  port> l ist     = new ArrayList    <Re     port>()  ;
        St ring overSpeedStr       ing = "  select  cv,loca       tion,Speed,Mile age_  He  xa,Acc_Status,A vailability_GPS fro m livemessagedata where cv between '"   + startDate + "' and '2        012/04 /28 01 :52:17    ' and  Phone_No        in     (selec t  deviceid from ve    hi    cle where v  re              gistera            tio  n_num = '1')";
            log.info(   "overSpe   edSt   ring : "+o   v     e        r     SpeedS  tring);    
             try {      
            m_Connection = getD    bConnectio      n(). getConnection();
                m_Statement   = m_Connection   .createStatement();
               m     _Resul   tSet = m_Statement.executeQ uery(     overSpeedStri     ng     );    
                          SimpleDateFo    rmat df = new         SimpleDateFo   rmat("d   d-M   MM-yyyy hh:mm:ss");  
                          w    hile (m_Re          su ltSet.nex  t()) {
                                    //log     .info   ("*       *****"   +        overSp       eed + "spe   ed       : "   + Float.pars       eFloat(m_ResultSet.getString("sp   e    ed")));
 
                  //ch   ecking condition in which sp       eed id gr ea  ter t       han over speed
                    if (Float.p   arse             Flo a  t(m_ResultSet.getString("speed")) >= overSpee    d   ) {
                          Report report =      n  ew  R  eport();
                    repo   r    t.se           t   StartDate(df.format(m_Resul    tSet.      getTimes    tamp("cv")));
                                             report.s et   Location(m_R         esultSet.get     St   ring("location"));
                      report.      setSp   eed(m_ResultSet.getString("speed") )  ;

                       int acc_sta  tus = In         teger.parseInt       (m_Res  ultSet.get            String("Acc_Status"));
                        float spe  ed  = Float.par                  se    Float(m_           Resul   tS e     t.getString  ("speed    ")); 
                      /    /For sett   i  ng values of status   field
                     if (a  cc_s    tatus         == 1 &       & speed == 0) {
                                 rep   ort.se          tIgn    iti      on("D    ormant");//vehi  le                is s       t  art          but n    o   t runni   n     g(ig     nition on but  speed   =   = 0 )
                                                  //l     og.i           nfo   ("            dormant   ") ;
                            } else {    
                                  if          (acc_status == 1 &&   speed > 0) {
                                         report.s              etIgnition("In Motion");//vehile in runn    in     g (speed >  o)
                                       // l  og.info(   "in motion");
                                           } else {
                                            i     f (acc    _st     a tus   =    = 0)  {
                                                                     report   .s     etIgnition("Stop  ");//ignition    off
                                        / / l og.info("st     op"     );
                                                    }
                                             }
                                         }

//                                             if (   acc_stat  us       == 1   ) {
//                                   repor    t.setIgnition("     ON   "  );
//                                 } els  e {
//                            report.setIgnition("OF      F") ;
   //                          }
                         String g   ps = m_Res             ultS    et.    getString("A  vailability_GPS");
                    if (gps.   equals("A"))       {
                                             report.setGPS( "  Available");
                         }     else {
                                     r   e  po          rt.setGPS("Not Available" );
                        }


                             l        ist.a  dd(report);
                      }
                  }
            }      catch (Exception e) {
            e.  printStackTrace  ()       ;   
        } finall      y {
                try {
                           if (m_Re   su   l  tSet !=          nu  ll) {
                                 m_ResultSet.close()   ;
                       }
                     if (  m_Statement != null ) {           
                      m              _  Statement.close();  
                   }
                                 if (m  _  Connection     != null)        {
                          m_Conn     ection.  close();
                }
             } catc    h (SQLExcep    tion ex    ) {
                 ex.printStac   kTra  ce();  
                }

                            }
           return list;     
    }

         pu   b       l     ic L        ist<User> list ViewUser(int uprofile, int userI  d)        {

        List  <User> list    = n    ul l;

        S  tring queryLI STMSISDN = "";

                if (up        rofile == 3)       {
                               queryLIST   MSISDN =   "select userid,fname,ln      am   e,badd    ress,haddr       es  s   ,utype,umobile,  uemail,active f       rom us   er                ";
          } els    e {
            qu          eryLISTMS       ISDN = "select use       rid,fname,     lname,baddress,haddress,ut     ype,um      obi         le,        uemail        ,act     ive from user     where"
                        + "     use         rid='"  + userI d + "' or uprofile='" + userId       + "'";
               }
            try     {
            m_      Connection        = g    etDbConnection    ().getCon    nection();
             m_Statement =     m_Connecti    on.createStatement();

            m_ResultSe           t = m_Statem     e    nt.e  xecute Query(queryLISTMSISDN);

               while (m_ResultSe t.next()) {
                  if   (li st ==   null)   {
                            l  is   t = n ew Array  Lis         t<User>();
                    }
                     User user = new U     ser();
                     user.se tUserId( m_Re  sultS et. getInt(1   ));
                           user.s       etFname(m_Resul    tS      e t.getString(2));
                           user.setLname(                       m_Result   Set.getString(3));
                    user.s   etBad d    ress      ( m_ResultSet.getSt ring(4                     )              );
                   user.    setHaddress(m_    ResultSet.getString   (5));
                          user.setUtype(m_R     esult  Set.getString(6      ))        ;  
                us     er  .setUmobile(m_ResultSe    t .   getString(7));
                user.setUemail(m_ResultS    et.getString(8))      ;
                   user.s     e   t  Active(m_ResultSet. getInt(  9  ));
                      list.   add(u          ser);
            }
           } catch (   S QLException e)  {
              e.printStackTrace();
          } f    inally {
                        try {    
                   if     (m_ResultSe      t != n       ul    l) {
                    m_ResultSet.cl  ose();
                   }
                            if (m_Statement    != null)   {
                        m_Statement    .c   lose();    
                          }
                       if (m_Co   nnectio     n        != null) {
                                                m_Connec   tion.close();
                }
                     } catch (      S   QLE xception    ex)   { 
                   ex.printStackTrace( )    ;

              }     
            }
            return li  st;  

    }

                 public int getMaxUserId () {

                     i      nt  id = 1;
              S     tring queryMAXID = " se    lect max(userid) fro  m u     ser";

          try {
            m_Connection = getDbC   onnec   tion().getConnection      ();
               m_S  tatement = m_Co   nnection  .  create  Stat    eme  nt()  ;
                                 m_Re    sultS  et = m_Sta   temen t.execut        eQu   ery(que        ry MAXID);
                     if (m_Resu       l    tSet   .n ext   ()) {

                      id = m_R  esultSet.getInt(    1) + 1;

            }
        } catc     h (SQLException e )   {
             e.prin         t   StackTrace(  );
           } finally {
                           try {
                    if (    m_R      esultSet !=    null     ) {
                    m_R esul   tSe   t.c     lose();
                                }
                   if ( m_Statem   ent != null) {   
                      m_Statement.close();
                           }  
                    if  (m_C           onnectio  n != null) {
                      m     _Co  nnectio       n.close();
                          }
                    } catch (SQL   Exception    ex     ) {
                 ex.printStack Trace();
                      }

            }
            retu  rn i      d;
           }
    // use to get u   sers email list  

    public  List<Str    ing> getUserId(i nt userId, int uprofile) {

         Str    i             ng queryLI STMSISDN = "";
                List<String> userI  dList = new      Arra  yList();
            if (uprofile == 3) {    
                        queryLI         ST       MSISDN     =  "se   lec      t uemail   from       user;        ";
                 } else {
                      queryL    IST   MSISDN = "SELECT uemai   l fr   om us  er     whe       re ow      nerid= '"     + userId +  "'   or userid='" + userId + "' ";
          }
           log.info(queryLISTMSI     SDN);
            try     {
                   m_Connecti     on = g etDbConnect  ion      ().getCo     nnection();
            m_Stat  ement       =  m_Co   nnect i  on.crea  teSt   atement();
              m_ResultSet = m_Statement.execu    teQue    ry(quer  yLIST  MSISDN);
    
            whi le (m_ResultSe t.n     ext(      )) {
                     userIdLis   t.ad    d(m_Re  sultSet.g    etString("u email"));


                          }
        } ca  tch (SQL    Exception e)     {
            e.p  rint  Stac        kTrace();
        }             finally {
                    try {
                                  if (m_Re  sultSet != n    ul   l) {
                                       m_ResultSet.        close();    
                             }
                         if (m_Statement != nu        ll) {
                                         m_Statement.close();
                }
                                      if (m_Co   nnecti     on  != null) {
                    m_Connecti    on.close ()   ;
                          }
                           } c   atch (SQLException ex) {
                      ex.prin     tStackTra   ce();
                    }
           }
               r eturn userIdList;
       }       

      pu    blic U   s     er getUser(S    tring uemail, int userId _Owner) {
              St    ring //queryLIST      MS      ISD  N        = "s   elect      useri   d, fname,ln    ame,badd ress,haddress,utype,u   mobile,uemail,passwor   d,uprofile,active     from     tra  ck man.user wh     ere userid=  '"+   userId    +"' and    own      erid='"+ u    s       erId_Owner +"' ";
                             query    LI   STMSISDN        = "        s   elect * from u   ser w    he    re uemail=    '" + u      e   mail + "' ";

        log.info(q     ueryLISTM   SI  SDN +   "qqqqqqqqqqqqqqqqqqqqqq");

              User user = new U      ser(  );
               try {
            m_Con nection            =     g   etDbConnecti     o    n()  .getConn ection();
                   m_Sta tement = m_Conne ction.  createStatement();

                  m_Resu     ltSet = m     _Statement.execut     eQuery(queryLI STMSISDN);

            w             hile         (m_ResultSe t.ne     xt()) {
                      user.setUserId(m   _      Resu  ltSet.getInt   ("userid"));
                         user.setFna             me(m_Resul   tSe  t.getSt   ri    ng("fname"));
                         user.setLnam  e(m_ResultSet.get          String( "l         name"));
                user.setB    ad d ress(m_Resu ltSet.getString("baddress"));
                user.setHadd  re ss(m_R    e   su    ltSet.getString("haddress"));
                  user.set    Utype(m     _ResultSet.getString("utype"));
                       u     s     er.set   U   mobile(                     m_ResultS      et.getString("   umobile"   ));
                      user.setUemail(   m_    Re   s ultSe       t.getString("uemail"));
                          user.setPass     wor  d(m_ResultSe t.  g       et      String("password"));
                             user.setUprof      ile(m_Resu   lt         S et.getIn     t("uprofi    le"));
                        user.setOwnerid(m_ResultSet.    getI  nt("ownerid"));
                  user.setActive (m_ResultSet.getInt(" active"));
                        user.s   etAdmin_id(m_ResultSet .getInt("admin_id"));
            }
        } catch (SQ     L  E    xception e) {
            e.pr     intStackTra       ce(   );
        } f   inal           ly {
            try {
                   if  (m_Resu  ltSet   != null) {
                            m_ResultSet.close();
                    }
                       if (m_Statement != null) {
                            m _Statement.close();
                     }
                       if (m_C   onnection != null) {    
                         m_C   onnection. close();
                }
              }   catch (SQLException        ex) {
                            ex.printStac  kTrac   e();

                }
                }

          return user;
          }

    public v  oid upd ateUser(User user, int  u  s      erId_Owner) {
     
        String query   ED  ITMSISDN =      "upda  te user s     et fna  me='" + user.getFname() + "', lname='" + user.getL            name() + "', badd         ress='" + user.ge    tBaddress() + "',   "
                + "had       dress='" + user.ge   tHaddr       es s    () + "', ut     yp   e='"   + us  er.   getUt    ype() + "', umobile='" + u   ser.getU     mobile()    +               "',uemail='"  + user.getUemail() + "',"
                       + "pas  sword='" + us  er.getPa    ssword()  +  "' ,   u  profile='"       +   user.getUp    rofile() + "',a   ctive='" + user    .get    Active  (  ) +      "' where userid='" + user.ge   tUs      erId() +      "' ";

        log     .info(qu   ery    EDITMSISDN +   "!!!!!     !!!!!!!!!     !!!  !!!!!");

        try {
            m_Connecti  on = getDbCo      n necti on()  .  getConne    ction();         
            m_Sta     tement =   m_Connection.    cr     eateStatement();
                 int i  = m_State  ment.e    xecuteUpda    te(queryEDITMSISDN);

        }  catc    h ( SQLException e) {
            e.pr  intStackTrace(      );
            } finally {
                    try {
                   if (          m_Re  sultSet != null) {
                            m_ResultSet.cl   ose();
                          }
                    if (m_Statem       ent != null) {
                             m_Statemen  t.clos    e();
                       }
                       if (m_Connecti    o  n !=      null) {    
                         m_Co nnectio   n.close();
                }
                } catch (   SQLException ex) {
                   ex.pr intStackTrace();
              }
        }
    }

     p   ublic Strin                 g updatePass   wo   rd(i nt userId,      Strin    g password, String newPassword) {

        String querySELECTMSISDN = "      sel   ect password fr      o m use    r where passw  ord='"       + pa ssword + "' and us   erid='" +      u      serId + "'";
               lo        g.info(         qu erySELECTMSISDN + "     <<-----");  
         Strin     g queryUPD  ATE     = " UPDATE user       SET pa     s swo     rd=' " + newPas      sword + "'         WHERE userid='" + userId + "'";
            log.info(      "update  pa   ssword-->" + queryUPDAT   E);
        Str     ing result    = "      ";
        t   ry {
                            m_Connection     = get     DbConnectio n().getCon   nection();
               m_S       tatement = m_Connectio     n.createStatement();
                m_Resu  ltSet = m_State     men    t.executeQuery(querySEL    ECTMSISDN  );
            if (m_ResultSet.next ()) {
                    int i = m_Sta    tement.executeUpdate(query   UPD  ATE);
                     result = "updated";
                }    else {
                   result = "psdInput";
                }
     
               } c  atc h (SQLE    xception     e) {
              e.printStackTrac e(     );
              } f   inally {
                            try {
                i f (m_ResultSet != n ull) {
                            m_Res     ult Set.close();
                   }
                if (m_St    ate    ment != null) {
                           m_Statement.cl    ose   ();
                }
                                                           if (m_Connection != n  ull)   {
                               m_Conne  ction.close();
                             }
            } cat        ch (SQLException ex) {
                                  ex.printStackTrace();
                  }
        }
        return result;
    }

    publ     ic List<Long> get DeviceIdForDe  v   ice(int   u    ser   id, int up               rofile) {
                List<Long>       list    = new Ar   rayList<L    ong> (   );
        String     ins   ert    Query = "";
        if              (upr     ofile =  = 3) {
            insertQ   uery   = "select     d e    vi        ceid from device where     useri d in (select userid from user )";
                     } else {
             insertQuery = "sel  ect devi c   eid from device    where userid in"
                                + "("
                             + " select userid fr   om user   where ownerid =     '" + userid + "' o  r us    erid = '" + us     erid + "'"
                    + ")"  
                        + "";
                log.i  nfo("list of devices"    + i      nsertQuery);
        }
        try { 
            m_Connection = ge   tDbC    onne    ction().getCon     nection  ();
               m_Stateme    nt = m_Connectio n.createStateme    nt();
            m_ResultSet = m_St         atement.executeQu  ery(insertQuery);
              while (m_ResultS  et .n     ext()) {
                       list.add(m_ResultSet.getLong("dev   ice id"));
                       }
         } cat  ch (SQLException    e) {
             log.info("Ed  it   MgtDevi   ceAc tion  : " + e);
        }
          return list;
    }

       public Li   st<Ve   hic       le> search    Vehicle(String vn, int user     Id) {
          List<Vehicle  > ve hicl e_list = new ArrayList<Vehi       cle>();
        String str = "select * f   rom vehicl      e wh  ere vregisterat  io  n_num='" + vn + "'";
           log.info("Search v      ehicle    --->>>"     +       str)  ;
           try {
                m_Connection = getDbCon        nection().getConnection();
            m_Statement = m_Connection.createStatemen   t();
            m_ResultSet = m_Statement.ex   ecut     eQue ry(str) ;

            while (m_ResultSet.next()) {
                     Vehi   cle veh   icle = new Vehicle();
                          vehicle.setVe hicleI   d(m_R  esultSet.g   etLong("vehic    leid"));
                      v  ehicle.setVn(m_ResultS et.getString("   vregisteration_num"));
                    vehicle.setRegistration_date(m_ResultS     et.getString("registr at           ion_ d  at    e"));
                     vehicle.setVmake      (m_ResultSet.g   e    tStri ng("vma  ke"));
                vehicl  e.set Vm      odel(   m     _Res      ultSet.getString("vmodel      "));
                       vehi      c  le         .s   etVfuel_type(m_ResultSet.     getString("vfuel_type")    );
                vehicle.s  etFleet(m_ResultSet.getLong("fl   eet"));
                    vehicle.setUserid(m_      ResultSet.getLong(   "userid"));
                  veh icle.setDeviceid(m_ResultSet.getLong("deviceid"));
                             vehicle.se  tA  ctive(m_Resu      l       tSet.getInt("active" ));
                        vehicle_list.add(vehicle);
             }
        } cat   ch (SQLEx     ception e) {
              log.info(e);
        }
             ret    urn vehicle_list;
    }

    p  ublic Li st<Inte ger>  g      etD  eviceListPayment    (int a          ccNo) {

           List<Integer    > list = new Arr   ayLi     st();
  
        String queryLISTMSIS DN      = "selec     t deviceid from de     vice where user    id='" + accNo     + "'";


        lo    g.info(que  ryLISTMSISDN);
        t ry {
                   m_Connec       tion = ge     tDbConnection()  .getConnection   ();
              m_Statement = m_Connection.createSt    atement();
            m_ResultSet =     m_ Sta    tement.executeQuery(qu   eryL     ISTMSI  S DN);

                while (  m_ResultSet.next(  )) {
                  list.add(m_ResultSet.getIn  t("de  viceid"))   ;

              }
        } catch (SQLE xception        e) {
                  e.pri  ntStackTrace();
        } finall  y {
            try {
                       if (m_ResultSet                      != null) {
                               m_Re    sultSet.close();        
                   }      
                if (m_Statement != n      ull)  {
                             m_Statement.c       lose();
                }
                                            if (m_Connec   tion !=    null) {
                    m_Connection.close(   );
                }
                             } cat         ch (SQL     Exception ex) {
                ex.prin  tStackTrace();
              }
          }

        return li      st;

    }

    public void savePay ment(Payment payment) {
             // throw new Unsupported    Operat  ionExcepti    on("Not ye   t implementedin    sert into    p  ayment             values(1,1,350    ,'cash',09/02/2011,350,12121212,'pnb','noida','payof        fer',0,0,0,'',0)    ;");
        String quer     yINSER TMSISDN = "insert into paym   en    t values('" + pa   y ment.    ge  tAccNo() + "','" +    paymen   t.getDeviceId   ()
                + "','" + payment.getNetAmoun   t() +    "','" + pay    ment.getPaymentMode() +     "','" + payment.getPaymentDate(  )
                   + "','" + payment.ge    tReceivedAmt() +          "','"  + payment. getChqDdNo() + "','" + payment.getBankName()
                + "','" + payment.getBranchName() + "','" +     payment.getO  fferName() +      "','" + payment.    getOfferAmt()    + "','" + payment.getAdj    usment()
                + "'         ,'" + payment.     getDiscount() + "','" + pa    ym     ent.getCouponNo()  + "','" + payment.getCouponDiscount() + "'   )";

                      log.info(queryINSERTMSISDN);
        try {
                          m_Conne     ction = ge   tDbConnection()   .getConnection();
                   m_  Statement = m_Connection.createStat      ement();
                  int i = m_Statement.executeUpdate(queryI  NSERTMSISD    N);

             } c      atch (SQL   Excep      tion e) {
              e.printStackTrace();
        } finally {
            try      {
                 if (m_ResultSet != null) {
                        m_ResultSet       .close();
                }
                i   f (m_Statemen     t !=      n  ull) {
                                 m_Statement.close();    
                        }
                  if (m_Connectio   n != null) {
                                   m_Connect  io  n.close();
                  }
              } catch (SQ      LException ex) {
                       e  x.printStackTrace();
               }
        }

    }

                   public void saveOff  er(Offer ofr) {
                  // th   row new Unsuppor     tedOperat       ionExce       ption("N    ot ye   t impl  ementedin    s   ert   into   pay    ment value    s(     1,1,350,'cash',09/02/2011,350,12121212,'pnb','noida','payoffer',0,     0,0,'',0);       "     );
        String queryINSERTMSISDN = "insert into defining_offer(offer_name,offer_a    mount,billing_cycle,assignto)   v  alue     s('" + ofr.getOfferName() + "'," + of   r.getOfferAmount() + "," + ofr.getBillingCycle() + ","     + ofr.   getAssignTo() +     ")"   ;


           log.info(queryINS       ERTMSISDN);
        try {        
            m_C o  nne   ction = getDbCo   nnection().getConnection();
            m      _St    atement = m_Connection.createStatement();
            // System.err.println("Problem");
            int i = m_S    tatement.executeUpdate(quer yINSERTMSISDN);
              S  ystem.err.pri  ntln("Offer sav            ed Successfully.."     + i);
 
        } catch (SQLException e) {
                  e.   printStackT        race();
            } finally {
            try           {   
                       if (m_ResultSet != nul          l) {
                    m_ResultSet.close();
                }
                     if (m_St     atement != null) {
                          m_Statement.close();
                     }
                i  f (m_Connection != null) {
                       m_Connection.close(   );
                        }
                    } ca  tch (SQLExcep     tio    n ex) {
                ex.printStackTrace(); 
                          }
           }

      }

    public List<Long> getUser  IdLi  st() {
        List<Long> list = n  e   w Arr      ayList();

        St     ring queryLI     STMSISDN =          "select userid from user w    her e uprofile=" + '2';
           log.info(queryLI  STMSISDN   );
           try {
                   m_Connection =    getDbConnection().getConnection();
            m_Statement = m_Connection.createState       ment();
                m_ResultSet = m_Stateme        nt.executeQuery(que   ryLISTMSISDN);

             while (m_Res  ul    tSet.next         ())             {
                list.add    (m_ResultS et.getLong("userid"));

             }
            } catc   h (SQLException e) {
            e.pri ntStackTrace();
        } finally {
            try {
                    if (m_ResultSet != null) {
                      m_Result  Set.cl    ose();
                   }
                     if (m_  Statement != null) {
                        m_Statement.close();
                 }
                if (m_Connec        tion !=   null) {
                       m_Connection.close(  );
                   }
            } ca  tch (SQLExc   eption ex) {
                               ex.printStac     kTrace();
            }
        }

         return list;
    }

     public List    <O   ffer> li   stOffer(lon    g userid, long              uprofile)     { 

             List<Offer> list = null;  
        String listOffer;
        if  (uprofile == 3) {
                   listOffer = "select offer_name,offer_amount,billing_cycle f rom trackman.defini    ng  _ offer";
        } e lse {
             listOffer = "select off    er_name,   offer_amount,b    illing_cycle from trackman.defining_off   er where assignto='" +  useri  d + "'";
        }
        try {
                   m      _Connection = getDbConnection().getCo     nnection();
                    m_Statement = m_Connection.creat   eStatement();

                    m_R   esu ltSet =  m_Sta  tement.executeQuery(listOffer)   ;

                    while (m             _ResultSet.next()) {
                   if   (list ==   null)   {
                    list = new ArrayList<Offer>();
                   }
                Offer offer    = new Offer();
                offer.setOff    erName(m_ResultSe       t.getStr ing(1));
                     offer.setOfferAm     ount(m_Resu     ltSet.getFloat(2));
                   offer      .setBill  ingCycl     e(m_Resu    ltSet.getString(3));


                list.add(offer);
            }
          } catch (SQLException e) {
            e.printStackTrace()  ;
        } finally {
            try {
                  if (m_R     esultSet != null    ) {
                        m_ResultSet.close();
                     }
                if (m    _Statement != null) {
                              m_Statement.close();
                   }
                   if    (m_Connection !=       null) {
                        m_Conn   ection.close();
                      }
            } catch (SQL  Exception ex) {
                       ex.printStackTrace(    );

               }  
        }
              ret urn list;

       }

    public void sav     eCoupons(Coupon coup  on   , St  ring str) {
        String queryINSERTMSISD       N = nu  ll;
         if (str.e  qual   s("definin        gpreassigned_     coupons")) {
              queryINSERTMSIS   DN = "insert into " +  str +    " values('" + cou    pon .getCouponId() + "','"
                       + coupon.getCouponName() + "','" + coupon.getNumberOfCoupons()  + "','" + coupo n.getDiscount() + "' ,'"
                            + 3   + "','" + coupon.getAssign() + "','" + coupon.getValidTill() +  "', '" + 0 + "       ','" +  0 + "'" + ")"         ;

        } else {
             queryINSERTMSISDN =   "in sert into " + str + " values('" + coupon.get           CouponI   d() + "','"
                       + c        oupon.getCo    uponName() + "',  '" + coupon  .getNum     berOfCoupo     ns()    + "','"    +             coupon.get  Discount() + "','    "
                                         + 1 + "','"      + coupon.getAssign() + "','" + coupon.getValidTill(   )   + "','"       + 0 + "','" + 0 + "'" + ")";
        }


        log. in  fo(queryINSERTMSI       SDN      +  "--------------------" + coupon. getAs  sign());
        try   {
                  m_Connection = getDbConnec   tion().getConnection();
            m_Statement = m_Connection.createStatement();

                      int i = m_Statement.exec   uteUpdate(q   ueryINSERTM SISDN);  

                     Sy stem.err.println(" C   oupons saved    Successfully.." + i);
     
        } catch (    SQLException e)     {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                        m_Resul  tSet.close();
                 }
                if (      m_Statemen      t != null) {
                     m_    Statement.close(    );
                 }
                if (m_Connection != null) {   
                        m_Connecti  on.close();
                }
                } catch (SQLExcep    ti        on ex) {
                 ex.print StackTrace();
                   }
        }
    }

      public void preAssignedCoupon () {
        String querySELECTMSISDN = "select coupon_id from definingpreassigned_coupons";
        String querySE LECTSISDN1 = "select deviceid,dreg_  date from devi    ce order by dreg_date";



        l   og.info(  querySELECTMSISDN);
          lo    g.info( quer         ySELECTSISDN1);
        try {

            m_Connection = get         DbConnection().getConnec tion();
            m_Statement = m_Connec     tion.cre   ateStatement();
            m_Resul    tSet = m_Statement.e   xecuteQuery(querySELECTMSISDN);

            System.   err.println("Que   r    y1 exec      ution Successfully....");
            System.err.println("Quer  y2 execution S  ucces   sfully....");
            List<String> list1 =    new    ArrayList<String>();
               Lis     t<Lon     g> list2 = new ArrayLi   st<Long>();
            List<S   tring> lis       t3   = new Arra yL   ist<S  t   ri     ng>();

                   while (m_R  esultSet.next()) {
                   list1.add(m_Re  sultSet.getStr  ing(1))   ;

            }

            Statement m_S tatem     ent1 = m_Connecti  on.createStat      eme    nt();
            ResultSet m_ResultSet1 = m_Statement.executeQ    uery(query SELECTSISDN1);
                 log.info      ("Li      st1 size " + list1.size());
            whil  e (m_ResultSet1.next  ()) {
                 lis t2.add(m_ResultSet1.getLong(1));
                list3.add(m_ResultSet1.   getString(2));

            }
            log.info("L    ist2 siz e "           + list2.size());
            log.info("List3 size " + list    3.si ze());
                int i = 0;
                     int j = list1.size();

             Stri ng DATE_FORMAT =     "yyy  y-MM  -dd hh:mm:ss";
            Simp    leDa teFormat sdf =
                           new Si   mp   leDateFormat(DATE_FORMAT);
                 Calendar   c1 = Calendar.getInstance();             // to  day
            //    log.info("Today      is " + sdf.forma     t(c1.getTime()));

            //  String queryINSE  RTM     SISDN =  "insert into as        sign_co  upon values('" + list1.get(i) + "'   ,'" + list2.get(i) + "','" + list3.si   ze() + "'," + sdf.format(c1.getTime()) + "      )";

                     while (j > 0) {
                    String queryINSERTMSISDN = "insert into assign_coupon_de  vice values('" + l   ist1.get(i) + "','" + list2.get(i) + "',  '" + list 3.get(i) + "','" + sdf.format(c1  .getTime()) + "')";
                  log.in  fo(queryINSERTMSISDN);
                     m    _Statement .ex   ecuteUpdate(queryI     NSERTMSISDN);
                    i++;
                j--;
            }

            String queryINSERTMSISDN1 = "update defini   ngpreassigned_coupons set STATUS_CO UPON=" + 1;

//int l=m_Statement.executeUpdate(queryINSERTMSIS      DN1);
             log  .info("Numb  er of rows updated " + 1);
             } catch (SQLException e) {
                e.printStackTrace();
        } finally {
                    t   ry {
                   if (m_R   esultSet != nul    l) {
                          m_ResultSet.cl  ose(  );
                          }   
                         if (m_Statement != nul     l) {
                    m_Statement.close();
                  }
                if (m_Connection != null) {
                            m_C onnec         tion.close();
                  }
                } catch (SQLException ex      ) {
                         ex.printStackT   race();
             }
             }

    }

    public void createPlan(Plan plan) {

        try {
  
            String s = "insert into defining_plan (PLAN_NAME,RENTAL,BILLING_CYCL  E,FACILITIES,ASSIGNTO) values('" + plan.getP          lanname() + "','"      + plan.getRe     ntal() + "','"    + plan.getBillingcycle() + "','" +    pla   n.getFaciliti      es() + "','" + plan.getAssignto() + "    ')";
            //log.info(queryINSERTMSISDN);


              m_Con   nection = getDbCon  nection().getCon   nection();
                  m_Statement     = m_Connectio   n.createStat   ement ();
            m_Statement.executeUpdate(s);

        } catch (SQLExceptio    n e) {
            e.printStackTrace();
            } fi        nally {
            try {
                    if (m_ResultSet != null) {
                    m_ResultSet.close()    ;
                         }
                       if (m_Stateme   nt != null) {
                            m_St      atement.close();
                }
                   if (m_Connection != null) {
                    m_Connection.close();
                     }
            } catch (SQLException ex) {   
                ex.printStackTrace();
                }
        }



    }

    public List<Plan> listPl   an() {
        List<Plan> list = new Array   List<Plan>();
        String          queryLISTDEVICE = "select * from defining_plan   ";
        log.info(    q ueryLISTDEVICE);
           try    {
                  m_Connectio   n = getDbConnection()          .getConnectio n();
            m_Statement = m_Connection.createStatement();
                 m_R es ultSet = m_Statement.executeQuery(queryLISTDEVICE);
            whi   le (m_ResultSet.next()) {  
                  P lan plan = new    Plan();
                plan.setPlanna   me(m_ResultSet.getString(2));
                  plan  .  setRental(m_ResultSet.getFloat(3));
                  plan.setBillingcy     cle(m_ResultSet.getString(4));
                    plan.setFacilities(m_ResultSet.getStri  ng(7));
                plan.setAssi    gnto(m_ResultSet.getInt(8));
                     list.add(plan);

            }

        } catch (SQLException e) {
            list = null;
            log.info(e);
           }

        retu  rn list;
    }

    public List<Plan> listPlans(int userID, int u      profile) {
        List<Plan> list = new Arr  ayList<Plan   >();
             S trin  g queryLISTDEVICE;
        if (uprof   ile == 3) {
            queryLISTDEVI    CE = "sel   ect *   from defining   _plan";
        } else {
            queryL ISTDEVICE = "select * from defining_plan where   as    signto =" + userID;
                  }
                l  og.info(   query    LISTDEVICE);
        try      {
                  m_Conn  ection = getDbConnection().getConnecti  on();
            m_Statement = m_Connection.createStateme  nt();
            m_ResultSet = m_     Statement.executeQuery(queryLISTDEVICE);
                while (m_ResultSet.next()) {
                Plan     plan = new Plan();  
                plan.setPlannam    e(m_Res   ultS    et.getString(2));
                plan.setRental(m_ResultSet.getFloat(3    )    );
                pl an.setBillingcycle(m_Res  ult Set.getString(4));
                    plan.setFacilities(m_ResultSet.get   String(7));   
                plan.setAssignto(m_Resu    ltSet.getInt(8));
                list.add(plan);

            }

          } catch (SQLExcepti on e) {
            l   ist = null;
                 log.info(e);
             }

        return list;
    }

    public void    createTax(Tax tax) {

        try {
            String st = "truncate table define_service_text";

            String s = "insert into def    ine_servi ce_text(SERVICE_TEX,EDU_CESS,S_H_EDU_CESS,OTHER_TEX) values('" + tax.getServiceTax(   ) + "','" + tax.getEducationCess() + "','"    + tax.getSheCess() +     "','" + tax.   getOtherTax() + "')";



            m_Conne    ction = get   DbCon  nection().getConnection();
             m_St    ate ment = m_Connection.create   Statement();
                 m_Statement.executeQuery(st);
                int i =     m_Statement.executeUpdat     e(s);
            log.info("@@@@@@@@@@@@@@  @@@@@" + i);

        } catch     (SQLException e) {
            e.pr  intStackTrace();
        } finally {
            try {
                         if (m_ResultSet !     = null)    {
                    m_R     esultS   et.close();
                }
                if        (m_Statement != null) {
                       m_Statement.close();
                  }
                      if (m_C     onnection != null) {
                        m_Connection.c      lose();
                  }
            }       catch (SQLException ex) {
                ex.printStackTrace();
            }
          }



         }
  
    public List<Tax> updateTax()    {
        List<Tax>         list = new ArrayList<   Tax>();
        Str in       g queryL  ISTDEVICE;

        queryLISTDEVICE      = "select * from define_service_text";

           log.info(queryLISTDEVICE);
              try {
                 m_Co     nnection = getDbConnection(   ).getConnection();
            m_Statement = m_Connec  tion.createStatement();
            m_ResultSet = m_Statement. executeQuery(queryLISTDEVICE);
            while (m_ResultSet.next()) {
                                  Tax tax = new Tax();
                tax.setServic   eTax(m_ResultSet.g  etFloat(2));
                tax.se    tEducationCess(m   _    ResultSet.getFloat(3)   );
                tax.setSheCess(m_ResultSet.getFloat(4));
                     tax.setOtherTax(m_Result    Set.getFloat(5));
                list.add(tax);

             }

        } catch (SQLException e) {
            list = null;
            log.info(e);
        }

        return list;
    }

    public vo id ed   itTax(Tax tax) {
           String queryEDITMSISDN =    "update define_service_te       xt set SERVICE_TEX='"
                       + tax.getServiceTax() + "', EDU_CESS='" + tax.getE ducationCes s()
                + "', S_H_EDU_CESS='" + tax.getSheCess() + "'    , OTHER_TEX='"
                 + tax.getOtherTax() + "' where SERV    ICEID=1 ";

        log.info(queryEDITMSISDN + "----------- --");
        try {
              m_Con   nection = getDbConnection().getConnection();
            m_State ment = m_Connecti  on.createStatement     ();
             int i = m_Statement.executeUpdate(queryEDI     TMSISDN);
            log.info(i + "-------------");
        } catch (SQLException e) {
            e.printStackTrace();
             } finally {
                  try {
                    if    (m_ResultSe    t != null) {
                    m_ResultSet.close    ();
                }
                if (    m_Statement != nu  ll) {
                    m_  Sta        tement.close();
                }
                if (m_C   onnection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printSt   ackTrace();
                   }
        }
    }

    public List<Long    > getDeviceId(in t userID, int uprofile) {
        List<Long> list = new ArrayList<Long>();
        String queryLISTDEVICE = null;
        if  (uprofile == 3) {
            queryLISTDEVICE = "select DEVICEID    from device";
        } else {
            queryLISTDEVICE = "select  DEVICEID from device where userid in(select        userid from user where userid='" + userID + "' or ownerid='  " + u   serID + "')";

        }
        log.info("Device Id List : " + queryLISTDEVICE);

         try {
               m_Connection = getD  bCo      nnection().getConnec   tion();
            m_Stateme  nt = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryLISTD  EVICE);
            while (m_ResultSet.next()) {


                lis       t.add(m_ResultSe  t.getLong("DEVICEID"));
            }  

          } catch (SQLException e) {
            list = null;
            log.info(e);
        }

           return l    ist;
    }

    public List<String> getPlanName(int userID    , int u profile) {

             List<S    tring> list = new ArrayList<String>();
        String queryLISTDEVICE = null;;
           if (uprofile == 3) {

            queryLISTDEVICE = "select PLAN_NAME from    def    ining_pl  an";
        } el se {
            queryLISTDE VICE = "select PLAN_NAME fro   m defining_plan where ASS   IG      NTO='" + userID + "'";

            } 

        // queryLISTDEVICE="select PLAN_NAME from defining_plan where ASSIGNTO =+'"+11+"'+";

 
          log.info(qu        eryLISTDEVI      CE);
        try    {
            m_Connection = getDbConnection().getConnection();
               m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statemen    t.executeQuery(queryLISTDEVICE);

            log.info(qu    eryLIS TDEVICE + "8888888888888*************");
            while     (m_ResultSet.next()) {

                  list.add(m_ResultSet.getString("PLAN_NAME"));
                  }

        } ca  tch (SQLException e)           {
            list = null;
            log.info(e);
        }

        return list;
    }

    public   List<String> getSmsPlan() {
        List<String> l   ist = new ArrayList<String>();
        S      tring   queryLISTDEVICE;

           queryLISTDEVICE = "select SMS_PLAN from sms_plan";

        log.info(queryLISTDEVICE);
        tr  y {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.creat    eStatement();
            m_ResultSet = m_Statement.executeQuery(queryLISTDEVICE);
            while (m_ResultSet.next()) {

                list    .add(m_ResultSet.getString("SMS_  PLAN"));

            }

        } catch (SQ  LException e)    {
            list = null;
            log.info(e);
        }

        return list;
    }

    public void     assignPlan(long deviceId, Strin    g  plan, String smsPlan) {
        try {

            String s = "insert into assign_plan(DEVICEID,PLAN_NAME,SMS_PLAN)values('" + deviceId + "','" + plan + "','" + smsP    lan + "')";
               log.info(s);
            m_Connection = getDbConnection().getConnection();
            m_Statemen   t =    m_Conn        ection.createStatement();

            int i = m_Stateme    nt   .executeUpdate(s);
            log.info("Assign P   lan" + i);

          } catch      (SQLException e) {
            e.pri        ntStackTrace();
        } finally {
            try {
                if (m        _ResultSe  t != null) {
                    m_ResultSet.close();
                    }
                if (m_Statement !=    null) {
                    m_Statement.close();
                }
                if (m_Connection    != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.pr    i    ntStackTrace();
            }
        }



    }

    pub  lic List<Integer> getUserIdPlan(int userId) {

        String queryLISTMSISDN = "";
        List<Integer> userIdList = new ArrayList();

        que   ryLISTMSISDN = "sel   ect userid from user where uprofile=2";

        log.info(queryLISTMSISDN);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();
            m_ResultSet = m_Statement.executeQuery(queryLISTMSISDN);

            while (m_ResultSet.next()) {
                userIdList.add(m_ResultSet.getInt(1));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }    finally {
            try {  
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement !  = null) {
                       m_Statement.close();
                  }
                    if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLExcep    tion ex) {
                ex.printStackTrace();
            }
        }
         return userIdList;
    }
//work for bill generation

    public Plan getPlanInfo(long deviceid) {
              Plan plan = n  ew Plan();
        String queryLISTMSIS DN = "select * from defining_plan where pla     n_name in"
                + "(select plan_name from assign_plan where deviceid='" + deviceid + "')     ";
        log.info("select plan details--->" + queryLISTMS  ISDN);
                tr    y {
            m_Connection  = g   etDbConnection().getConnection();
            m_Statement = m_Connection.cre   ateSta   tement();

            m_Resul   tSet = m_Statement.executeQuery(queryLISTMSISDN);

            wh ile (m_Re    sultSet.next()) {

                     plan .setPlanname(m_ResultSet     .getString("PLAN_NAME"));
                  plan.setRental(m_ResultSet.getLong("RENTAL"));
                plan.setBillingcycle(m_ResultSet.get   String("BILLING_CYCLE"));
                  plan.  setDiscount(m_ResultSet.ge   tLong("DISCOUNT"));
                plan.setTatalrental(m_ResultSet.getLong("TOTAL_RENTAL"));
                plan.setFacilities(m_ResultSet.getString("FACILITIES"));
                    plan.setAssignto(m_ResultSet.getInt("ASSIGNTO"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                       m_ResultSet.close();
                }
                if (m_Stateme      nt != null) {
                    m_Statement.close();
                }
                if (m_Connection != nu   ll) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return plan;
    }
  
    public Bill ge  tPreviousBillInfo(long deviceid) {
        Bill bill = new Bill();
          String queryLISTMSISDN = "select AMT_BEFORE_DUE_DATE from bill_g    eneration where deviceid='" + deviceid + "'";
        log.info("Previous Bill deta     ils--->" + queryLISTMSISDN);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.crea teState    ment();

            m_Resul       tSet = m_Statement.executeQuery(quer  yLISTMSISDN);

            while (m_  ResultSet.next()) {

                bill.setAmtBeforeDueDate(m_ResultSet.getFloat("AMT_BEFORE_DUE_DATE"));

            }
        } catch (SQLException e) {
              e.printStackTrace();
        } finally {
            try {
                  if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return bill;
    }
//getting      smsPlan information

    public SmsPlan getSmsPlanInfo(long deviceid) {
        SmsPlan sm  sPlan = new SmsPlan();
        String queryLISTMSISDN = "select sms_amount from sms_plan where sms_plan in"
                + "(select sms_plan from assign_plan where DEVICEID='" + deviceid + "')";
        log.info("Sms Plan Details--->" + qu    eryLISTMSISDN);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Conne    ction.createStatement();

            m_ResultSet = m_Statement.executeQuery(queryLISTMSISDN);

            while (m_ResultSet.next()) {

                smsPlan.setSmsAmount(m_ResultSet.getFloat("SMS_AMOUNT"));

            }
        } catch (SQLException e) {
                 e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                      m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return smsPlan;
    }

    public Payment getPaymentInfo(long deviceid) {
        Payment payment = new Payment();
        String queryLISTMSISDN = "select RECEIVED_AMOUNT,DISCOUNT,COUPON_DISCOUNT from payment "
                + "where PAYMENT_ID='" + deviceid + "'";
        log.info("Payment Details Details--->" + queryLISTMSISDN);
        try {
            m_Connection = getDbConnection().getConnection();
            m_Statement = m_Connection.createStatement();

            m_ResultSet = m_Statement.executeQuery(queryLISTMSISDN);

            while (m_Res ultSet.next()) {

                payment.setReceivedAmt(m_ResultSet.getFloat("RECEIVED_AMOUNT"));
                payment.setDiscount(m_ResultSet.getFloat("DISCOUNT"));
                payment.setCouponDiscount(m_ResultSet.getFloat("COUPON_DISCOU NT"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (m_ResultSet != null) {
                    m_ResultSet.close();
                }
                if (m_Statement != null) {
                    m_Statement.close();
                }
                if (m_Connection != null) {
                    m_Connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return payment;
    }
}
