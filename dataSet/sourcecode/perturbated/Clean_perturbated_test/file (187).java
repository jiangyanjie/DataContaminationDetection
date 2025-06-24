package     com.ghostchu.peerbanhelper.database;

import  com.ghostchu.peerbanhelper.PeerBanHelperServer;
impor  t com.ghostchu.peerbanhelper.module.IPBanRuleUpdateType;
imp    ort com.ghostchu.peerbanhelper.text.Lang;
import lombok.Cleanup;
impor   t lombok.extern.slf4j.Slf4j;
im   port org.jetbrains.annotations.NotNull ;

import java.sql.Da    te;
import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
pu  blic class DatabaseHelpe            r {
     private final Da  t    abaseMan    ager m    anager;
    private final Peer BanHelperS erver server;

    publ     ic DatabaseHelper(Pe     erBanHelperServer server, Data   bas  eManager       ma   nage   r) throw   s            SQLExc   ep   tio           n {
           this   .se     rv    e  r = server;
                this.   manager =                man         ager   ;
                   try {
               cre  ateTa    bles();
            perf            or    mUpgrade();       
            int clean   e   d =   cleanOutd    ate   dBan L  o  gs();   
                       if      (cl eaned > 0) {    
                        log.info(Lang.DATABASE          _OUTDATED_LO       GS_C LEANE   D_U   P, cl  eaned);      
                       }
                   } c        a    tch (SQ   LException e)         {
                 lo    g  .warn   (La  ng.DAT           ABASE_SETUP  _    FA  ILED, e);
                  thr   o     w e;       
        }
            }
  
        private v oid    perf    orm     Upgrade()            th    rows S          QLE xc  eptio      n {
           try (Con  ne  ction co          nnection = manager  .getConn     ection ()) {
                             int v = 0;
              Stri ng v  er    sion = getMetadata("ver      sio n");
               i    f   ( versio         n     !  = nu ll) {
                  v     =   Inte      ger.  p    a rseI        nt(   v           ersion)     ;              
            }
                           if (v ==     0)   {
                    try {   
                                               //    åçº§ p ee r_   id   / peer_client  n   ame æ®          µå¯ç©º
                       connection.prepareState  ment(     "A     LTER T       ABLE    b      an_l  ogs       RE NAME TO ban_          logs_v1_old_backup").ex             ecute();
                             connection.prepareState     me  nt("DR  OP IN  DEX   ba     n_lo            g     s_id    x").execute(         )       ;
                      c    reateTables()  ;
                     } catch (Ex         ception         ignored) {
                  }
                               v++;
              }
                         set    Metadata("version"          , Str ing.valueOf              (v));
                 }

    }
       
    public int setM   e     tadata(       S  trin       g k   ey,     S       t  ri ng    value)     throws SQLExc    ep tion   {
        try    (Con        nection conn     ect     ion = man        ager.    getCon   nect    ion()) {
                 @Cleanup
                        PreparedSta      tement   ps =    c  on nect  ion.prep a reStatement("RE  PLACE INTO m etadat a (key, v  alue) V   ALUE  S (?,?)");
                              ps.     setStri  ng(1, key);
                       ps.setString(2, value);
                     retur   n p      s.  exec   u      teUpdate             ()      ;
                       }
    }

    p   ubli    c String getMetadata(String       key)   thr    o  ws     SQLExcepti   on     {     
               try (C   onnect       ion con n  ect   ion = mana    ger .getConne        ction(     )) {
                    @Cleanup
            Prepare     dStatemen  t ps = connection.p repar   eStatement("SEL ECT * FROM metadata WHERE `k  ey` = ? LIM  IT   1"); 
               @                     C     lea                  nup
                                 Resu    ltS et set     = ps.executeQuer   y(    );
                       if (set.next()    ) {
                             return  s   et.getS   tri     ng("k        ey");
                           } els  e {
                       r   etur n null;
                }             
                }
       }     

    pu   blic long q   u   eryBanLogsC    o             u  nt() throw      s SQLException {        
          t   ry (Co      nnection     co nnection        =          manag  er.getConnect ion())            {
                  @Cleanup    
                      ResultSe            t set    =      connection.cr          ea   teState          m        ent().ex     e          cuteQ  uery("S         EL           ECT    COU        NT(*) AS     count FROM ba  n_logs");
              re turn  s   et.ge  t       Long                   ("cou  nt");
        }
            }

      pu   bl    ic       List<Ba    nL    og> qu eryBa  nLo    gs(D         ate f  rom,     D             ate to      , int   pageIn  dex,              int   pageSize      )   t hrows                           SQLExc eption {
             try (Conne    ction connection =    manager.get         Connec   tion()) {
                      Prep a redSta    tem ent                 ps;
               i           f           (from       ==      nul  l &&    to            ==      null) {  
                       ps =    c                o   n    necti   o      n.prepa reSt     ate    ment(   "    SELECT    * FROM ban_  logs  ORDER  BY id            DESC  LIMIT    "          + (pageInd      ex  *     p  ag     eSize) + ", "      + pageSi  ze)     ;
                   }     else          {
                                          i             f (from    ==    nu  ll |  | to      =  = n   ull) {
                                     th    row        new IllegalArgumentE         xce   ption("fr    om   o  r n      ull canno          t  be n  ul       l if a     ny pro   v     ided"             );
                        } el            se {
                               ps = conn  ection.prepareS   t                        a     t     em    ent("SELE  CT * FROM  ban_logs WHE        RE b  an_         at   >=   ?        AND ban_ at      <       = ? ORDER BY i   d DESC LIMI     T "  +    (          p     ag     eIndex   * page      Size) + "    ,        "    +     pa  geSi         z  e);
                                              }
                                                                   }
                      try    (ps) {
                           if (from != nul  l                    )        {
                                     ps.s              etDate(1, from);
                         ps     .s  etD       ate(2, to);   
                                          }  
                          t  r y (ResultSet  set =                 ps     . exec  u       te  Q      uery     ()) {
                                 List<BanLog>       logs = new LinkedList<>     ();       // å                   °½å¯ è½  èçº¦åå­  ï¼        ä¸     ä  ½¿ç¨ ArrayList
                               while (se                     t.next   (   )) {
                              Ban            Log   banLog =   ne  w   Ban            Log(
                                               set.getL      ong    ("b  an_at"),   
                                                                  set.ge  tLong("u nban   _at"),         
                                                      set.ge   t   Stri    ng("pee  r_ip     " )      ,
                                            set.getInt  (    "       peer    _po               rt          ")   ,
                                                    set.ge    tStr         i  ng( "peer_i          d"),
                                                                                 se     t.ge  tSt             rin        g(  "p     ee  r_clientname"),
                                              set       .   getLong("         pee   r_uploaded"),
                                                                      set.getLong("pee      r_down    loaded")  ,
                                                            se     t.getDouble(   "        peer_p    rogr     ess"),
                                                    s        et.getString(   "to           rrent_  infoh            a    sh"),
                                                                  set.g   etStri      ng("torrent_name"),
                                                                      set.getLong("torre    nt_size"),
                                                    set.getS       tring     ("mod  ule"),
                                                s           et.getStrin g("d    escription")
                                      );
                                    logs.ad    d(     banLog);
                                 }
                                                                 re   turn logs     ;
                      }
               }  
             }
    }

    pub        lic       M   ap<     String, Long> fin dMaxBans(        int n      ) throws SQLException    {
           try (Connection connect  io    n = man           ager.g  etConnection()) {
                  @Cleanup     
                  P      rep       a      red   Stateme    nt    ps = co     nn  e   ction   .            pre p  areStat  em          ent("SELECT      pe           er_ip, COUNT(*       ) AS co    unt "    +
                                 "FROM ban_log   s WHERE      ba    n_at     >= ?" +
                                     "GROUP BY pee    r_ip " +
                                           "OR       DER B      Y count D                 ESC LIMIT "   +   n);
                                ps.set           T  imes tamp(1, new Times          tam    p(Ins     tant.now().m   inus(14    ,      ChronoUnit.DAYS).to    EpochMil       li(         ))); 
                 @C             leanu       p
                     Re    su   ltSet s  et = ps.ex ec uteQuery();    
              Map<Str ing, Lon    g> map = new LinkedHashM    ap<>();
             while          (   s                et.next   ()) {
                             m         ap .put(  set             . getString      ("peer_ip"), set.getLong("                     cou nt"   )    );
                          }
                        retur    n m   ap;
             }
       }

    public    int cle   anOu                    tdatedBanLogs() {
            int days     = server.getMa   inConfig().    getI nt("pers    i     st.ban-logs-    keep-       days",  30);
                      try (Conne    ction connec  ti  on = ma        na   g                 e       r.getC  onn    ec    ti   on  ())             { 
                                              @Cle anu      p
                    Pre   p  a    red            St at     ement ps = c     onne  ct          ion.prepareState    ment   ("DELETE                 F        ROM b an_logs w        here u            n    ban  _a              t < ?");
                        I     nstant     instan      t    =         I     n      st       ant            .no     w().mi        nus(days, Chro  noUnit.D A             YS);
                   ps.s   etTim      estamp(                            1, new Times     tamp  (i     nst   ant.      toEp                 ochMilli()))          ;
                 retu     rn ps.ex   e                c      uteUpdate();
        }             catch   (SQLExce           ptio   n e) {
                     throw       n  ew    Run   ti    meException(e);
          }     
    }

       publ    ic in    t        insertBanLogs(   Ba        nLog ba     nLog)                     throws    SQLEx  ception {
           try (Connection    conn   e    ction = manage  r.  getConn             e  c          ti  o n())  {
            @Cle   an           up
                         Pr     e       p           ared Statement p s = c   o      nnection.prepareState  m   e    nt("I   N SERT INTO     ban_l ogs    (ban_at, unban_a      t, pee          r_ip, p     eer    _port, pe       er       _id, p  e er_cli  entnam      e,      " +
                                                           " pee   r  _downlo aded,     peer      _up   l           oaded     , p                      e  er_progr    es     s, to rrent_info    h ash, to     rr     ent_name,     torr               ent_s  ize, m      odule,     desc       ription       )" +
                                                                         "VALUE    S (?,?,?,?,?,                   ?,?,?,?,?,            ? ,?    ,       ?,?)")  ;
                          ps  .s   etL   o n  g(1,  banLog.         banAt(             )) ;
                          p        s                       .se         tLon   g(2       ,  b   anLog.unbanAt());
                   ps.set    String   (3                   , ba   nLog.    peerIp()); 
                  p s.se                       tInt  (4,      b  anLog.pee  rPor       t()      );
                                  S      tr        ing peer       Id  = b anLog  .peerId()       ;
                    ps      .set           Stri                                       ng    (5, peer   Id)    ;
                       String clie  n   t   Name        =      banLo    g.pee   rClientN    ame()   ;
                             ps.     s    etS  tring(6     , cli    ent N                ame);
                                                  p       s.se  tLon   g(7,    ban        Log.peer                Do   wn   loa         ded  ()      );
                 ps.setLo ng(8, b    a n        Log.pe     er     Uploaded(          ))     ;
                            ps.setDouble(9,          b       anLog.peer  P      rog  ress());
                        ps .    setStr    ing(10,   ban     Lo                  g.torre       ntInfoHash()  );
               ps.setString(11   ,              banL      og .torre  ntNa     me());
                        ps     .setLong(1    2,          ban                     Log                       .     t   orrentS            ize   ()    ); 
                                         p               s.s  etString(13, banLog.mod    ule( ))   ;
                   p         s.          setString(1   4,          banLo    g.des    crip   tion());
                                            r    etur                    n  ps  .e  xecuteUpdat     e();    
                    }
       }   

         pub        li c               v    oid createTables(        ) t hro    ws S             Q      LExcepti      on      {
                           t     ry         (Conne        ction   c  onnect       io    n =      manager.getCon     nec   t      i      on()) {
                                         if             (!has   Table(  "     ba n_logs") )    {
                                    @Cleanu p
                                                     PreparedSta           t ement                     ps   = co   n nection.p  repareStatement       ("""
                                                                                                CR       EA TE                 TABLE ban_logs       (
                                                                                                                                     "id " INT                EG      ER NOT N     UL  L P RIMARY     KEY     A       U TOINCREMEN  T   ,
                                                                                    "ba   n             _at" integ er NOT NU      LL,  
                                                                          "u  nba         n _a t" integer                    N     OT NUL L,
                                                                                                        "peer_ip" TEXT      N             O   T N    ULL    ,
                                                                                                              "p  e   e   r_por  t"   integ   er NOT N     UL    L,
                                                                                                                           "  peer_id" TE                     XT                NU       L   L,
                                                                                   "     pee    r_clie          nt    name        "       TEX  T NUL         L  ,  
                                                                                                             "peer         _downlo    aded" i  nteger NOT    NU            LL,
                                                                                      "p   ee  r_upl   oa     de      d" i  n  te    ger  NOT N ULL,
                                                                           "          p  eer        _p           rogre      ss"     r   eal      N   OT NUL    L,
                                                                "tor r     ent_i   nfohas   h" TE   XT NOT NULL,    
                                                                                                                "torrent_na        me" TEX    T       NOT NULL,
                                                                                                 "to          r   r       ent_siz                 e" i n    t eger NOT NULL,
                                                                               "modu  le"    TE       XT,
                                                                                                                "de   s     cription"    T                      E     XT              NOT NULL
                                                                 );
                                                   "" ")   ;
                      ps.exec  u   teUpda       te()     ;
                                                ps = connection.p r   ep  areStatem ent("""
                                                                                                CREATE INDEX       b     an _l    o    gs_   i   dx
                                                                                ON ban_   logs (    
                                                                                 "i       d     "   ,
                                                                                   "ban_at",
                                                                              "pe  er_ip",
                                                                                                 "torren     t_infohash",
                                                                        "module"
                                                                                            );
                                                        """);
                 ps.executeUpdate();
                             }
               if (!  hasTable("metada     t        a")) {  
                     @ Cleanup
                              PreparedStatement ps = conne            ction     .            pre         pare    Statement("""
                                                                               CREATE TAB  LE     metadata (
                                                                                    "key   " TE         XT NO T   NULL PR       I                   M             A      RY K E  Y,
                                                                          "val  ue     "  TEX     T
                                                                           );
                                   """)      ;
                                 ps  .executeUpdate();
              }
                if        (!has  T              ab      le("rule_s     ub     _lo  gs")) {
                   @Clean     up
                  PreparedStatement    ps = c      onnection.prepareState                me   nt   ("     ""
                                                                                                                       cr   ea  te               ta  ble rule_  sub_logs
                                                                                                (       
                                                                                                                       id         i        nteg     er not         null
                                                                                constr   aint     rule_s ub  _logs_pk
                                                                                     primary key autoincrement,
                                                                                                                rule_id    integ      er   not nul  l,
                                                                                                      updat     e_time integer,
                                                                                                           e   nt_count  integ  er,
                                                                                                           upd  ate  _type  TEXT
                                                    );
                           """);
                        ps      .  executeU    pdate();
                   }
             if (h asTable("rule_sub_logs"  )  ) {
                                @Cleanup
                Pr ep       ared   Statement ps   1 =      co      n necti   on.prepareState men  t  ("""
                                upd           ate rule_sub_log       s set update_type =   'AUTO' where update_type = 'è  ªå¨æ´æ°';
                               ""  ");
                              ps1.execut       eUpdate();
                        @Cleanup
                            Pr      e          paredState   ment ps2 =    connection.p  repar    eStatement     ("""
                                    update rule_sub_logs set up   d    at              e_type = 'MA    NUAL' where update_ty  pe = 'æå¨æ´æ °    ';
                            "        "");
                ps2.executeUpdate();
            }
              }
          }


    /**
     * Retu         rns    t    rue i  f      the    gi ven   table ha s the given column
     *
        *     @p   aram table  The table
     * @param column The column
         * @return Tru   e if the g       ive      n table has the          given    column
     * @throws      S     QLExcept    ion I    f the database isn't connected 
             */
            public boolean     h    asC  olumn(@NotNull Strin     g table,    @NotNull String column) throws SQLException {
        if (!hasTable(table)) {    
                  ret urn false;
                         }
             String query = "SELECT * FR OM " + table + " LIMIT 1";
        boolean match = fal    se;  
        try (Connection connection = manag     e      r.getConnection(); PreparedStatement    ps = connecti     on.prepareStatement(     query); ResultSet rs  = p      s.exec  uteQuery()) {
                      ResultSe     tMetaDat      a metaData     =    rs.getMetaData();
            for (int i   = 1; i    <= metaDat a.getC    olumnCo    unt(); i++) {
                     if     (metaData.ge   tColumnLabe    l(  i).   equals(col   umn)) {
                       match =       true;
                         break;
                     }
                }
        } catch (S  QL  Excepti    on e) {
            re   turn m   atch;
        }  
             return match     ; // Uh, wtf.
    }


    /**
      * Returns true if the table exists
     *
     * @pa  ram table The table to ch eck for
     *      @re turn Tru  e if th e     table is found
     * @throws SQLException Throw exception when failed execute somethins o  n SQL
         */
    publi  c boole an hasTa   ble(@NotNull String     table) throws SQLException {
               Connec  tion connec tion = manager.getCo  nne     ction();
           boolean match = false;
          try (ResultSet rs = connection.getMet   aData().getTables(    null, null, "%", null)) {
            w  hile (rs.next()) {
                  if (table.equalsIgno r   eCase(rs.getString("TABLE_NAME")))   {
                                 match = true;
                                brea k;
                        }
            }
            } f ina  lly {
                  connecti                 on.close();
           }
        return match;
    }

    /**  
     * æ¥è¯¢è®¢éè§     åæ  ´æ°      æ¥å¿æ°é
     *
     * @param ruleI      d è®¢     éè§åID
     * @return æ   ¥  å¿æ°é
     * @throw     s SQL     Exception SQLå  ¼  å¸¸
     */
    public int co    untRuleSubLogs(String ruleId) throws SQLE    xception {
        try (Co    nnection connection = manager.getConnection()) {
              Prepare dState me   nt ps;
            b  oolean id  NotEmpty =      null !  = rul   eId && !ruleId.isEmpty();
                 String sql = "SELECT    c  ount( 1) as      count FROM     rule_sub_logs " +
                    (idN otEmpty ? "WHERE rule_id =    ? "    : ""   );
            ps =     connection.prepareStat ement(sql);
            if (idNotEmpty) {
                ps.setStrin   g(1, ruleId);
             }
                  tr   y (ps     ) {
                   try (ResultSet  set = ps.exec        uteQuery())    {
                    int count = 0;
                    w    hile     (set.next()) {
                              count = set.getI nt("count");
                    }
                    retur   n  count;
                }
                          }
        }
    }

     /**
     * æ¥è¯¢è®¢éè§åæ´æ°  æ¥å¿
       *
     * @param ruleId    è®¢éè§åID
     * @param pageIndex     é¡µç 
     * @param pageSize  æ¯é¡µ      æ°é
           * @return æ  ¥å¿åè¡¨
     * @throws SQLException SQLå¼å¸¸
     */  
    public List<RuleSubLo g> query      RuleSubLogs(String ru    leId, int pageIndex,  int pageSize) throws SQLE  x   c    eption {        
        try (Connec     tion connection =    manager.getConnection()  ) { 
              PreparedStatement p     s;
            boolean idNo    tEmpty = null      != ruleId &    & !ru leId.isEmpty();
            String sql = "SELECT * FROM rule_sub_logs  " +
                    (idNotEmpty ?     "WHERE rule_id = ? " : "") +
                    "ORDER BY id DESC LIMI   T " + pageIndex *     pageSize + ", " + pageSize;
            ps = connection.prepareStatement(sql);
            if   (idNotE   mpty) {           
                ps.setString(1, ruleId);
            }
            try ( ps) {
                tr  y (ResultSet set = ps.executeQuery()) {
                    List<RuleSubLog> infos = new ArrayList<>();
                    while (set.next()) {
                          infos.add(new RuleSubLog(
                                set.getString("rule_id"),
                                              set.getLong("update_time"),
                                set.getInt("ent_count"),
                                            IPBanRuleUpdateType.valueOf(set.getString("update_type"))
                        ));
                    }
                    return infos;
                  }
            }
        }
    }

    /**
     * æ   å¥è®¢éè§åæ´æ°æ¥å¿
         *
     * @  param ruleId     è®¢é è§åID
     * @param count      æ´æ°æ°é
     * @param updateType æ´æ°ç±»å
     * @throws SQLException SQLå¼å¸¸
     */
    public void insertRuleSubLog(String ruleId, int count, IPBanRuleUpdateType updateType) throws SQLException {
        try (Connection connection = manager.getConnection()) {
            PreparedStatement ps;
            ps = connection.prepareStatement("INSERT INTO rule_sub_logs (rule_id, update_time, ent_count, update_type) VALUES (?,?,?,?)");
            ps.setString(1, ruleId);
            ps.setLong(2, System.currentTimeMillis());
            ps.setInt(3, count);
            ps.setString(4, updateType.toString());
            ps.executeUpdate();
        }
    }

}
