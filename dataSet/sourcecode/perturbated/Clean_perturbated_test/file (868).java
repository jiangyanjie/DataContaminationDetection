package   ru.vladambulance.crew;

import ru.vladambulance.sql.AmbulanceSQL;
impo rt ru.vladambulance.workers.Worker;

import java.sql.*;
impo  rt java.util.ArrayList;
im     port java.util.Date;

    /**
 *        Created with IntelliJ IDEA           .
 * User:    serge    i
 * Date: 2/1 2 /13  
 * Time: 1:10 PM
 * To change this template u   se   File | S     ettin          gs | File Templates.
 */
public class C         rew   {
    private static Connec  tion connect     ion = AmbulanceSQL.getIn    s  tance().getConnectio         n();
    pri vate Inte  g   er  id;
    private Date date;
    priva te ArrayList<Work    e        r>   workers = new A                 rrayList<Worker>();
          private String name;

      pub          li   c   Crew(Integer         id){
                                      i                   f(id   ! =     n    ull){
            this    .id = id   ;
            thi  s   .r  etrieve();    
        }el          se{
            //todo                                   create ne    w crew
                this.se tDate(new Date(          ));
                    }
            }

           p  u blic    sta  tic    Connection getCon  nectio n() {
          return con   nection     ;
              }

    public static void se    tCon  n   ect         ion(Con       n              ect     ion   con  nect      i       on)     {
                     Cr   ew.conne     c tion = connec            t ion;
    }

          private void retr i   eve(){
            Str   ing sql        = "  S     EL  ECT cr ew.id," +      
                                   "     crew.name as nam  e," +
                       "crew.     date  as     date ,   "       +
                                                                                "w  ork   ers.      i d a  s work   er "         +
                                        "F  ROM crew "     +
                             "LE   F        T JOIN c rew_  log ON crew_log.crew      =   crew     .  id " +
                "LEFT JOIN w             orkers ON  workers. id = crew_log.worker "   +
                                  "WHERE crew.id = ?";
         tr      y {
                                 Pr          epar  edStatement pr     eparedState          me    nt = Crew   .getConnectio  n(     ).pre                 pareStatement             (    sql     );
              p r     epared    State       men      t  .setInt(1,      t      his.          g     etI      d( ))   ;
              Result     Set re     sult    Set    = p   repar   edS   tatemen t.exec    uteQu ery();
                    i    f  (r    esultSet.next()){
                                 this.setName(r   e                s  ultSe  t.ge    tStrin   g("na  me    ")             );
                                   thi   s.setDat         e (    ne w Date(
                                                          re     s        ul      t   Set.g          etTimestamp("  d   at         e").g  etTime()
                                           ))      ; 
                          worke    r  s.ad     d(   
                                 new W  orker(
                                                           res u       ltSet.get   Int("wo  r                k       er  "   )
                           )
                                                 )  ;
                                  }  
                  while(r  esu      ltSe             t.next()){
                                wo  r   kers.ad  d     (
                                                          new        Wor   ker(
                                                             re   sultSet.getInt("             w   orker"           )  
                                    )
                            );    
            }
        } c a         tch (     SQL Excep  tion     e) {  
            e      .printS  ta  ckTrace   ();                 //To change b ody of catch stat    ement use File  | Setting      s   | F    il  e     Te           mpla          tes .
              }        
                     }        

    pub     li c void   save(){
            if(th         is.get                Id ( ) != null){
                       String sql        =    "D   ELE    TE                 FROM      cr  ew_     log "  +
                                         "WH     ER   E c      rew_log.crew     =     ?";
                 t  ry { 
                                                     Pr eparedS     ta    tement prepar     e    dSta     te     m   ent  =        C  re                  w.getConnection(              ).pre  pareStatement(sql);  
                                   preparedStatem  ent.se tInt(1  , this.getId());
                                                p  r    e    paredS  ta   te   m      e     nt.        e      xecu       t eU    pdate()    ;
                   } catch (SQLException   e) { 
                e    .pr          intStackTrace();          //To change    bod             y     o f catch statement use File  | Setting s    | File Tem    plates.
                         }
                 }else{
                                                 String s  ql =                       " INS     E          R          T INT      O     crew (" +
                           "   nam    e," +
                                    "da   te"    +
                                        ")" +                              
                        "   VALUES ("   +    
                    "?,?" +
                      ")    "      ;    
                try {
                                                    PreparedStatement     preparedStatement = Crew.ge                 tCo   nnection().p      r e   p   areState  me nt(
                                  s   ql      , 
                                      Stat emen    t.RET URN_GENERAT   ED_  KEYS
                    );
                                    p   repa redStatement.      setStrin  g  (1, thi s.g etName(             ));
                                 preparedState   ment.set Timestamp(  2,
                                                            n e           w Timestamp(
                                                    th     is.getDate().getTime    ()
                                           ))       ;
                     pr                      eparedSt    atement.  execute  U   pdate      ();
                                            Res  ultSet resultSet = pre     paredState ment.getGenerate      d   Ke ys()   ;
                 if(resultS        et.nex     t()){           
                                  this.setId    (resultSet.   g     etInt(    1));
                      }
                                re     s   u    l tSet    .clo       se();
                  }  c       atc     h    (         SQLE   xcepti   on ex){
                                         e   x.printS      tac             kTra   ce();
               }
              }
              try{
                 String    sql = "I     NSERT INTO cr ew_log (crew, worker) " +
                     "V   ALU  ES    (?,?)";
                  P   reparedS        tatemen   t prep   aredStatement = Crew.getConnection().prep ar   eSt    at   ement  (sql);
                               f or(int i = 0; i  < worke    rs.size     ()  ;        i  ++){
                        preparedStatement.set    Int(1, thi s.getId());
                        prepa redStatement.se  t      I  nt(2     , workers.get(i).g   e  t     Id());
                pr eparedStatement.add Batch()    ;    
              }
                        preparedStateme       nt.executeBatch();
             preparedStatem    ent.clos           e();
            }     catch (SQL     Exce   ption e) {
            e.printS      ta              ck     Trace()   ;  //To c hang   e body of c         a                t  ch st     at   ement use    File   | Settings    |      File Templates.
        }
               }

    public vo    id delete    (){
         String sql = "DELETE     FRO   M crew_log " +
                   "WHE RE crew_log.cre   w = ?"  ;
             try {
                 PreparedSt     atement prepa    redS    tatement = Crew.ge     tConnecti            o    n().prep areSt at            em  ent(sql  ) ;
             prep aredS     tatement.set  Int(1, this.getId());
            preparedStatement.execu     teUpdat  e();
  
                    sql = "DELETE FROM crew " +
                                       "WHERE crew.id    = ?";
               pre paredStatement =       Crew.getConnection()      .pre    pareStatement(sql);
            preparedStatement.setInt(1, this.get   Id());
                         preparedStatement.exec   u    teUpda te();
        } catc h (SQL  Exceptio   n e) {
                    e.pr intStackTrace();  //To  change body of catch statement   use File | Settings | File Templates.
            }
          }    

    public stat   ic Array    List<Cre     w> getCrews(Date startDate, Date   endDate){
        ArrayList<  Crew>    crews = new Arr   ayList< Crew>(         );
             String sql = "SE  LECT            id FROM crew " +
                   "WHERE date     >    =    ? AND " +
                     "dat     e <= ? " +
                "ORDER BY date";
              try {
              Pr    eparedStatement pre         paredStatement = C rew.getConnection()    .prepareStatement(sql);
                 pr   eparedStat em      ent.setDate(1, new java.sql.Date(startDate.getTime()));
             p   reparedStatemen     t.s    etDate(2, new java.sql.Date(endD        at     e.getTime  (     )));
              Res    ultSet  resultSet = preparedState     ment.execut      eQuery();
              while (resultSe  t.next()){
                    crews.add(new Crew(resultSet.getInt("id")));
             }
        } catch (SQLException e) {
            e.pri    ntStackTrace();  //To change body of catch statement use File   | Settings | File Templates.
        }
        ret  urn crews;
    }

    public String getContents    (){
        StringBuilder stringBuild  er = ne  w StringBuilder();
         for(Worker worker: this.getWorkers()){
            stringBuilder.append(worker.toString()  + ", ");
        }
        return stri        ngBuild      e   r.toString();
    }

    public String toString(){
        return thi    s.getName();
    }

    public void addWorker(Worker w){
        this.getWorkers().   add(w);
    }

    public Inte      ger getId(  ) {
        return id;
    }

    public void setId(Inte  ger id) {
        this.id = id;
    }

    public Date getDate() {
           return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(ArrayList<Worker> workers) {
        this.workers = workers;
       }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
