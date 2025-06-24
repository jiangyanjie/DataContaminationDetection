/*
 * To change this   license heade    r, choose      License Headers in   Project Properties .
   *    To change this template file, choose Tools  | Templat       es
 * and ope   n the  template in the editor.
 */

package airlines;

impor     t java.sql.*     ;
import java.util.    logging.Level;
import java.util.logging.Logger;
    import javax.    swing.t    able     .Defa    ult  TableModel;

/**
 *
 * @autho   r sanket
 */
public c      lass DBC     onnect {
       p          rivate Connecti   on conn ect;    
      pr     iv        ate Statem        en   t st   atement;
               
          publ  i      c DBConne        ct() thr       o      ws       SQLException{
        
             tr  y{
                          Clas s.forName   ("com.mysql.jdbc.Dr       iver");             
             conne    ct = DriverManage        r.g  etConne   ction(           "     jd   bc    :   mysql://loc    alhost:3306   /airlineDB   ","  ro     ot","");
                  sta    tement = connect. createS               ta  tement();
                        
          } catch(ClassNo tF                 o undException e){
                                  Sy  stem     .out    .pr    intl     n("E    rr   or: "+            e)  ;
                                   }
                
    }
    
    public Re    sultSe            t get             Data(Stri   ng tabl e){
                         
             try          {            
            String q    uery = "selec   t * from "+table;  
              Result    Set       returnT  abl     e       =        state    me     nt.exe  cuteQ      uery(query);    
                   return returnT able;                
                   } catch (SQLException ex) {
                Logger.getLogger(DBConnect.cla    ss.getNa  me(      )).log(Level.SEVE   RE, nu ll, ex);
                              }           
          retu        rn null;
    }
      
    publi         c boolean checkCustomer(String ID)throw   s       S    QLException{
          //<            editor-fold  defaul  ts            tate="collapsed" desc="true    if  a customer with th   is          ID e     xists    in the datab   ase">
        String     searchQue      ry = "SELECT    * from custDB WH    ER  E use  rname = \"" + ID +      "       \"";
          
        Re  sultSet rs =     statement    .executeQuery(searchQuery); // exec          ute the query, and get a java resultset
            return rs.abso    lute(1);
//<   /editor-fold>
           }
       
         pub    lic boolean chec   k    AR(String ID)throw s SQLExceptio      n {        
          //<editor-fold d     efaultstate=                  "    collapsed" desc="tru       e if an AR wi th this ID e xists in the dat  abas     e"> 
        String searchQ       uery = "SELEC   T      *   from ARDB WHERE username= \"" +    ID +"    \"";
        
           Resu   ltSet rs   = sta     tem      e           nt.executeQuery(searchQue      ry);          // execu   te th e query,   and get a jav   a resultset
        r  eturn rs.abs      olute(1);
  /   /</edit    or -fo ld  >             
    }
 
    
    public boolean          cr     edentialC     heck( C ustomer     cust)  th     ro           ws SQLExcepti    on{ 
        //<editor-fold defaultstate="collapsed"      desc="tr u         e   if a customer with corre  ct credentials exists  in data    bas    e">
        S   tring user name = cust.ID  ;
             String password     = cust.getPassword();
        
                         String searchQuery = "SELEC  T * f    rom custDB WHE  RE username= \""    + usernam        e +"\"   AND pass    word= \"   " + passwo    rd + "     \"";
              ResultSet rs = stat         ement   .executeQuery(searchQuery);
        re     turn rs.ab    s      olute(       1);
//</editor -    fold>
        }
    
        pub     lic boolean credentialARCheck(      Customer cust)       throw  s SQLException{    
          /  /< editor-fold defaultstate="col laps  ed" desc=   "true     if an AR    wi   th corr     ect cre           dentials     exist  s in data   base">
                  String userna     me = cust.ID;
                              Stri  ng password = cu   st.g etPasswo r   d();
            
         S   t  ring searchQuery = "SELECT * from ARDB WHERE usernam e= \"" + username +"\" AND passw   ord= \"" + password + "\"";   
        ResultSet rs = statement.execu       teQuery(searc   hQuery);     
        return rs.absolute(1);           
//< /e      di   tor-fold>
    }
    
    public       b        oolean checkFlight   Owner(Str           ing      fl    ightNo, Stri    n  g ARID)  throws SQL  Exc          eption{ 
          //<editor-fold    defaults  tate="collapse      d" desc   ="  true   if an A  R         with corre  ct credent ials     exist s in     da      tabase         ">
              
             Strin   g searchQuery = "SELECT * from   transDB      WHERE custID=   \"" + A   RID +"\" AND      type=       \"A  DD\    " AND flightN  o= \"" + flightNo + "\"";
        Re sul    tSet rs      =     statem ent.executeQu er  y(s     earch    Query  );
         return rs.abs  ol       ute(  1);
// </editor-fold  >
             }
    
    p     ublic  boo    lea   n login  C heck  (  Strin  g custI  D)  t  hr ows SQLEx   ception{    
            //  <editor-fold def        aultstate="collapsed    "    desc=   "tr         ue if     this    cus      tomer is logg  e d in the      system">
        String se   arc     h   Query =   "SELECT * from custDB WHERE username  =         \ "" +      custID     +"\";";
           ResultSet rs = statement   .exe   cuteQuery      (searchQuery);
           if(rs.absolute   (1)     ){       
            ret   urn      1==rs.getInt("login");
                     }
        els    e
                    return false;
   //</editor-fold>
               }
      
    public boolean login     ARC  heck(S     tring AR   ID)  t        hrows SQ  LExcepti      on{  
        //<editor-fo  ld    defaultstate=         "collaps   ed" desc="true if    this        custo   me   r is logged in the    sy    stem   ">
          Strin  g searchQu e   ry = "SELECT *       from AR     DB W      HER E u    serna    me= \"     " + ARID +"\";";
              Res          ultS            et rs =   statement.ex    ecuteQ   uery(searchQue    r y);
        if(rs.a     bso    lute(1)){
                      return 1==rs.getInt("login");
                   }
              else
            return false;
//</edit     or-fold>
    }
    
    
           
        public boo   lean checkFlight( Stri ng f         l     ightNo)t  hr    ows SQL    Exception{ 
        //returns true i          f t   his flightNo ex ists i    n the datab   as     e                   
                        Strin   g     searchQuer   y = "SELE    CT                       *        from fligh tDB WHERE flightNo=  \    " " + flightNo +"\""; 
        
               Resul tSet r    s = statement.ex e   cuteQuery(sea        rchQuery);   // execu te the query, and ge    t a    j     ava resultset
                  ret  urn r s.absolute(1);
         }
         
         public vo  id delet    eF    lig ht(String flightNo)  throws      SQLException{  
            //    returns true if this f      lightNo exists in the dat  abase
        String   deleteQuery = "DELETE FROM f  lightDB WHERE flightNo= \"" + flightNo +"\""     ;
            
              statement.executeUpdate(deleteQuery); /   / execute the  query, and get a ja   v   a resu      lts  et  
                     
      }
    
    publ      ic  void insertCu  stomer(Customer c  ust) t   h  rows SQLExce ption{
             Stri   ng  use     rname =     cust.ID;
        String pa  ssword = cust.getPassword();
        Stri    n   g fi   r     stNa me    = cust.    custFirs   tName;
        S tring lastName = cust.custLastName;
           int login = cust.getLogin()? 1:0       ;
        String sql;
        s        ql = St            ring.forma        t( "    I   NSERT INTO cus     tD  B        VALUES (\"      "+u s  ername+"\",     \""+passwor      d+"\",  \""+fir    stNam         e+"         \", \""+la  st  Na  m  e+"\       ", "+lo   gin+")");
          sta   teme    nt.executeUpdate(sql);
       }
    
            public void insertFlight(Fli      ght fl  ight) t    hrow        s SQLException   {
           Strin      g flightNo = flight    .get Flight  No()  ;
           String source = flight.getOr      igin()          ;
               Stri   ng dest = f   light.getDest();
                       int numS   e     ats =    flight.ge     t Seats();
          int numA   vail = flight.getAvail()   ;
                 int       price     = f light.getPrice();
           String   s ql       ;
        sql = String.format("    I       NSERT INTO fl  ightDB V  ALUE S (\""+flightNo  +"\", \""+sou   rce+"\"   , \""+dest+  "\", "+numSeats+", "+numAv ail+", "+price+")");  
          statement.execute  Update     (sql);     
      }
            
            p ubl  i     c void ins      ertTransaction(T   ran  saction trans) throws SQLEx    cept  ion{
               Str    ing t           ransID = tra  ns.getTran sID();
        String custID               =   trans.getC    ustID( );
                      String       flightNo   = trans       .getFl          ight               No();
             int  cost   = tran    s.getCost();
          int numSe ats     = trans.getSea      ts();
                    String       ty    pe = tran s.g etType();
        
        String sql;  
                   sql = Stri  ng.format("    INSERT INTO tra    nsDB     (ID, c ustID, flightNo, type, numSeats, cost) VALUES (\ ""+transID+"\"  , \""+c      us  tID+"\", \""+flight    No+    "      \",        \""+t  yp e+"\", "+numSeats+"    , "+co   st+   ")");
                  s            tatement.exec  u    teU     pdate(sql);
       }
     
         public void updateFlight(F    li   ght             flight) t          h  rows SQLExcept  ion{
        String flightNo    =    fligh        t   .getFlightNo();   
        String source = fligh  t.getOrigin();
         String d    est = flight.getDe st ();
        int numSeat     s = flight.getSeat        s(  );
               int numA      v  ail = flight.getAvai     l();
                 int              p   rice = flig    ht   .   getPrice()  ;
                  
          Stri  ng u pdate = "UPDATE         `airli   neD    B`.   `fli   ghtDB` SET `source` = \' " +  source    +"\'   , `dest` = \'"+dest+   "\', `n  umSeats` = \'"+numSeats+"\',     `numAvail` = \'"+   numA  va  il+"\', `price` =   \'"+  price+"\'     WHERE `flig            htD   B`.`flightNo  `        = \'"  +f  li        gh    tNo+"       \';";     
              statement.exec ute     Updat    e(update   );
              
    }    
    
            public void updateCustom             er(Customer         cust   ) throws SQL   Exception{
           S   tring username = cust.ID;
        String pass  w      ord = c ust   .getPas sword();
        String firstNa       me =   cust.custF   irstName;
                     Stri     ng lastNa           me = cust.custLastN   ame;
                      int login = cust.getLogin(     )? 1     :0;
        
        Stri        ng update = "  UPDATE   `air  line D       B`.`c ustDB` SET `p     asswo rd` = \'" + pas  sword +"\',   ` firstNam  e` = \   '"+f   irstName+"\', `     lo  gi n` = \'"+lo   gin+"\' WH   E   RE `custDB`.`u serna me`   = \'"     +u    sername+ "\';";
           statement   .executeUpdat  e(update);
    }
         
    public      v   oid l     o goutAll() thr         ows SQLExceptio         n{
                       
        Stri             ng update1 = "U      PDATE `airline    D     B`.`c  ustDB`  SET `l og in` = \' "+   0+"\'      ;";
               Stri   ng u       pdate2 = "       U              PDATE `airli         n   eDB`.`ARDB` SET   `lo  gin` =  \'"+0+"\';"    ;
                        statement.executeUp   date(u   pdate1);
               stat  ement.execut eUpd     ate(update  2);
        }
    
    public       void custLo gin(S tring user    name)  throws SQLExc     e    pt   ion {
                  int             login = 1;
                        St      ring update        = "UPDA     TE `airline   DB`.        ` custDB` SET `l   o     gin`  = \'"+log    in+"\ ' WHE                    RE `custD   B`.`usernam   e                   `               = \'            "+username+"\';"   ;
              statement.exe             cuteUpdate(up  da t  e);
         }
    
    pub l  ic void loginAR(S      tring        ID) throws SQLExcep   ti     on {
           int log   in = 1  ;
        Str ing update = "        UPDA   TE `ai    rline DB`.`ARDB` SET `logi  n`   = \'"+logi    n  +   "    \' WHERE   `ARDB`.`usernam       e`          = \'   "+ID+"\';"; 
        statement.executeUpdate(update);
      }
         
          p ublic void custLo                    gout(String u  sername   , boolean isA    R)   throws S   Q  LExc epti   on {
            in   t   login = 0;
        
                      if (!isA R){
             String   upd ate = "UPDAT E `airlineDB`.`cus     tDB`           SET `login` = \'"+login+     "    \' WHE RE     `cust     DB`.`username` = \'"+user  name+"\  ';";
                            statement.executeUpdate(upda     te);
              }
                else                   {
                         String u   p    date = "UP  DATE `airlineD  B`.`ARDB` SET `  login   ` =          \'"+logi  n+"\' WH      E          RE `ARDB`.`usernam  e`  = \'"+username+"\';";
                  statement     .executeUpdate(update);                      
          }
            

    }
      
           publ    ic Flight retrieveFlight(String flightNo) throws   SQLE    x ception   {
        String           searchQuery = "SELECT  *   from flightD      B WHE   RE flightNo=    \"   "   +  flightNo +"\"    "; 
        ResultSet rs       = statement.exec  uteQ      uer y(search   Que    r   y);
            
          if(r      s.abs       olute(1)){
                String        source = rs.getString("source   ");
                   String dest = rs.getString("dest");
               int numSeats = rs.getInt("numSe  ats");
               i nt num   Av ail = r   s  .getInt("   numAvail    ");
              int pric  e = rs.getInt(   "price");
                 Fli ght f = new Flight              (flightNo ,   source, d    est, nu    mSe     ats, n      umAv ail, price);
                 return f;
          }
        else   {
                           Flight e            rror = new Flight(  "Error", "Error", "Erro   r",     0, 0);
            return    error;
        }
     }

    public Transact   ion retri     eveTrans             action(String transID) throws SQL  Exception{
        String searchQuery =   "SEL     EC T   * from transDB WHERE ID= \"" + transID +"\"";
        ResultSet rs = statement.executeQuery       ( searchQuery);
                  
            if(rs.absolute(1)){
            String custID = rs.getString("custID");
                  Stri  ng flightN      o = rs.getString(   "flightNo ");
            in  t num   Seats =   rs.getInt("numSeats");
              int cost = rs.getI      nt    ("cost"   );
            S   tring typ    e =   rs.getStri  ng("type"      );
                 Transac tion t = new    Transaction(tra    nsID        , custID, flig       htNo, type, nu   mSeats);
                    t    .setCost(co    st);
                            retu rn     t;
        }
        else {
            Transaction error = new T ransaction("Error", "Error", "Error", 0  );
              return      error;
             }
    }
           
    pub   lic i    nt custFlightSeat   s(St         rin    g custID, String flightNo) throws SQLException{
        Str  ing se  archQuery =  "SELECT SUM(numSeats) AS answer f      rom   transDB WHERE cust      ID= \""   + cu   stID    +"\  " AND flightNo= \""     + flightNo + "\";";
        ResultSet seatsHistory = stat    ement.e xecuteQuery(searchQuery    );
        i      nt     answer = 0     ;
        w  hile(seatsHistory.next()){
            answer = seat    sHistory.g    etInt(1);
        }
        
        return answer;
    }   
    
    publ    ic  DefaultTableM  odel custFlightHistor     y(String    custID, String flig  ht   No)        throws SQLException{
        String searchQuery =          "SELECT *   fr om tran sDB WHERE custID= \"" + custID +"\" AND flightNo         = \""       + flightNo + "\"";
        ResultSet history = statement.e x    ecuteQuery(searchQuery);
                  ret       urn resultSetToTableModel(history);
       }
    
    public DefaultTableModel viewHistory   (String custID) throws SQLException{
        String searchQuery = "SELECT * from transDB WHERE cust      ID = \"" + custID + "\"";
        
           ResultSet history = statement.ex ecuteQuery  (sear chQuery);
        return resultSetToTableModel(history);
    }
    
    pu    blic DefaultTabl eModel getFlights(String source, St  ring d     est) throws SQLExceptio      n {
        String searchQuery = "SELECT * from flightDB WHERE source= \""   +     source +"\" AND dest= \"" +    dest + "\"";
          ResultSet flights = statement.executeQuery(searchQuery);
           return resultSetToTableModel(flights);
    }
    
    public Defau     ltTa bleModel resultSet  ToTableModel(R  esultSet row) throws SQLException {
        
        ResultSetMetaData meta = row.getMetaData();
        DefaultTableModel model = new Def    aultTableModel();
           
        Stri    ng cols[] = new String[meta.getColumnCount()];
        for (int i = 0; i < cols.leng th; ++i) {
            cols[i] = meta.getColumnLabel(i + 1);
        }

        model.setColumnIdentifiers(cols);

        while (row.next()) {
            Object data[] = new Object[cols.length];
            for (int i = 0; i < data.length; ++i) {
                data[i] = row.getObject(i + 1);
            }
            model.addRow(data);
        }
        return model;
    }
    
}



