/*
       * Copyright   2  015, The Querydsl Team (http://www.querydsl.com      /team)
 *
 * Li censed    under the Apache License, Versi    on 2.0 (the "License") ;
       *   you may not us  e            this file except in c   o mpl iance with the    License.
  *   You may obtain a c  opy of the License at
 * http://www.apac   he.org/l     icenses/LICENSE-2.0  
      * Unless requir  ed by applicable      law or agreed to in writing, softw are
 * distributed under th e   License is distributed o     n an  "AS   IS" BASIS,
 * WIT    HOUT WAR    R   AN       TIES OR CONDITIONS OF ANY KIND, either exp ress or implied.
 * See   the Lice  nse for    the specific language governing permissions  and
 * limitations under the License.
 */
package com.querydsl.sql;  

import com.mysema.commons.lang.CloseableIterator;
import com.querydsl.core.DefaultQueryMetadata;
i  mport com.querydsl.core.QueryException;
import com.querydsl.core.QueryFlag;
import com.querydsl.core.QueryMetadata;
import com.queryd   sl.core.QueryModifiers;
import com.querydsl.cor e.QueryResults;
import com.querydsl.core.support.QueryMixin;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.FactoryExpression;
import com.querydsl.core.types.ParamExpression;
import com.querydsl.core.types.ParamNotSetException;
import com.querydsl.core    .types.Path;
import com.qu        erydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimpleExpression;
impor   t com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.core.util.Result   SetAda pter;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java   .sql.PreparedStatement;
import java  .sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import jav  a.util.function.Suppli   er;
import java.util.logging.Level; 
im   port java.util.logging.Logger;
import org.jetbrain  s.annotations.   Nullable;

/**
   * {@code         Abst      ractSQLQuery} is the base t      ype      for SQL     query imp    le         menta   tion   s        
 *
 * @pa     ram <T> re       sult type
 *     @para   m <Q>     concrete subtype
         * @author tiwe
     */
public abstract class AbstractSQLQuery<T, Q extends      AbstractSQLQuery<T, Q>>
    extends Projectab    leSQLQue   r y<T, Q> {

    protected static  final String PAR ENT_CONTEXT =
      AbstractSQ       LQuery.class.getName(     ) + "#PARENT_C ONTEXT";

  private st     ati c final Logger    logger        = Logger.getLogger(Abs  tractSQLQ uery.class.getName());

   private static  final QueryFlag rowCountFlag =
                new Query Flag(QueryFlag.Position.AFTER_PROJECTION, ", count(*) over() ");

   @Nullable private S     upplier<Co nnection> connProvi         der;

  @Nullable private Connection    conn;
 
  protected SQLListeners listeners;

     protected bo           olean useLiterals   ;

  private boolean getLast  Cell;
  
  pri         vate Object lastCell ;

  private SQLListene    rContext parentContext;

  priva  te Sta     tementOptions   statementOption  s;

  pu   blic   Abstr actSQL   Query(@Nullable Connection conn, Configuration configuration) {
    this(conn, configurat    i  on, new DefaultQu     eryMetadata());
  }     

  public AbstractSQLQuery(
      @Nullable Connec  ti  on conn, Configuration configuration, QueryMetadata metadata) {  
       super(new Qu   eryMixi       n<   Q>(me tadata,    false), configurat   i on);
    this.conn = conn;
    this.listeners = new SQLListeners(configuration.getL  isteners());
              this.useLiterals =   configuration.getUseLit     erals();
    this.statementOptions = configuration.getStat     ementOptions();
  }

  public AbstractSQLQuery(Supplier<Connection> connProvider  , C     onfiguration configuration              ) {
        this(connProvid er, configu  r   ation, new       DefaultQueryMetadat          a());
  }

  public AbstractSQLQuery(
      Supplier<Conn                 ecti on> connProv   ider,      Configur      ation            configuration, QueryMetadata metada   ta) {
    super(ne      w    QueryMix    in<Q>(metad   ata, false),       c  onfiguration);
    this.connProvider =      con       nProvi der  ;
         this.listeners = new SQL    Listeners(configurat   ion. g   etList   eners());
    this.u       seLiterals = configurat   ion.get       UseLiteral        s();
       this.s   t   atemen   tOptio    n  s     = configu          rati     o   n.ge  t   Statem    entOptions    ();
  }

            /**
   * Create an alias f    or the ex     p   res   sion
   *
   * @param   alias alias
   *       @retur    n this as         alia        s
     *   /
   p   ublic SimpleExpression<T> as(String alias) {
    return Expr         essions  .as(this, alias);
  }

  /**
   * Create an alias for t  he e   xpression
      *
   * @param ali    as      ali   as
   * @return th     is as alias
   */
  @SuppressWarn     in  gs(   "unchecked")
  public Simpl  eExpression<     T> as(   Path      <?> alias) {
    r  etu   rn Exp   res    sions.as(th   i    s, (Path) alias)        ;
  }

  /**
   * Ad     d a          listener
         *
   * @param listener listener to add
   *     /
        pu   blic    void   addLi stener(SQL     Listener listener) {
      liste     ners.add(listene  r);
  }

  @Override
  p   ublic l        on      g fetchCo  unt() {    
       try {
       return unsafeCount();
           } c  atch (SQLExce   p   t  ion e) {
      Str  ing error = "Caught " + e.getCla      ss().getName();
      log ge   r. log(Le  vel.SEVER  E    ,   error,     e);
      throw  configura   tion.translate(e);
    }
  }

  /**
   * If you us       e forUp    dat e(   ) wi        th a backend that uses page or row locks, rows examined by the query
   * are    write-locked until the         end of th    e   current transac      tion.
   *
   * <p>       Not suppo    rted for SQLite     and C    UBRID
   *
       * @return the    current ob jec  t
   */   
  public Q forUpdate() {
    Query   Fl     ag forUp       dateFlag    = configurat  ion    .getTemp        lates().getForUpdateFlag();
     ret  urn addFlag(forUpdateF  lag);
  }

  /**
        *  FOR S   HA   RE causes the rows re       trieved by     the SELECT sta tement to be locked as though f or update.
   *
   *        <p>Sup p    orted by MySQL, P  os   tgreSQL, SQLServer.
   *
   * @return the c           urrent  object
   * @throws QueryEx  cept  ion if     th   e FOR SHARE     is not supporte   d.
      */
  pub lic Q forSha      re() {
          ret     urn forShare(fals  e)     ;
       }

               /**
   *     FOR S   H         ARE causes the rows retrieved by      th   e      SELECT          statement   to be locked as t  hough fo     r update.
           *
   * <p>S   u     pported by  MySQL, P  ostgre      SQL, SQLServer    .
   *  
   * @param fallbackToForUpdate   if the FOR S      HARE is not    sup     ported and this parameter is <code>true
   *        </co  de>  , th e {@link #   forUp    da  te()} functionality     will be u       sed.
   * @return the current      object
   * @ throws       QueryExcep tion if t    he FOR SHARE i  s not supported and    <i>fallbackToForUpdate</i> is   set   
   *        t   o <code>false</code>.     
   */
               public   Q forShare(boolean fallbackToForUpdate)   {
    SQLTe    mplates sqlTemplat    es = configur          ation.ge  tTemplates()   ;

     if     (sqlTemplates.isForShareSupp  orte d()) {
      Query   Flag forSha     re     Flag = sql Te     mplates.getForSh areF          lag();
        return     addFlag(forShareFlag);
    }

    if (f     all b      ac  kToForUpdate) {
      return forUp date(                  )  ;
    }

    throw new QueryException(   "Using forS   ha    re()    is not supported");
  }
  
  @Override
  protected SQL    Serializer     createSer       ializer()  {
    SQLSerialize r serializer      = new SQLSerializer(con    fi   guration);
    serializer    .setUseLiterals(u  seLiterals);
        ret urn  s  e  rializer  ;
  }

    @Nu     lla     ble
    private <U>   U get(ResultSet rs , Expression<?> expr, int i, Class<U> type) thro   ws SQLException {
    re        turn con   figura ti on.g et(rs,     ex       pr instanceof Path ? (Path<?>)    expr : null, i, type);
  }

  p   r  ivate void s       et(Prep aredStatement stm    t, Pat  h<?>  path, int i, Object value)   throws SQLException {
     con figurat   ion.set(stmt, p ath, i  , value)    ;
  }
      
  /**
   * Called t   o create an  d sta     rt a new SQL Listener co  ntext
   *
   * @  p   aram connectio   n     the    database connection
        * @par  am metadata the met   a data   for that context
   * @return the n  ewly started context
                   */
     prote    cted SQLListenerContextI  mpl sta      rtContext(Co   nnection conn      ection, QueryMetadat  a     metadata) {
    SQLListener C    ontextI  mpl    context          = n  ew SQLL    istenerContextIm pl(met    adata, connection);
    if (pa  rentConte   xt != null) {
        context .setData(PARENT_CONTEXT, pa re   nt     Context);
    }
    listeners.start(c   ontext);
    retu               rn context;       
  }

  /**
   * Called to make t    he call b  ack to l    isteners when an exception happens
   *
                  * @p      aram cont ext    the current context in play
        * @param e the exception
   */
  protected v o   i   d onExce    ption( SQLListenerContextImpl contex   t, Exception e)  {
    c  ontext.setExce     ption(e);
     listeners.exception(context);
  }

  / **
   * Ca  lled to e         nd a SQL listener context
   *
   * @param context the listener   co ntex   t to end
   */
        protected void  endCo  ntext(SQL     ListenerContext con    te         xt) {
    li   stene   r        s.  end(contex t);
  }

  /**
   * Get    the res     ults a      s a JDBC ResultSet
   *
   * @par am exprs   the express   ion arguments   to        retr   ieve
         * @return    results as            R    esultSe   t
   * @depreca ted Use @{code select(..)} to de  f  ine     the projection and {@    code      ge tResults()} to obtain  
       *     t    he   result set
      */
  @De  precated
  public ResultSet       getResults(Exp  r ession< ?>... exp    rs) {
    if (exprs.length > 0) {
        qu                eryMixin .se   t     Projection(exprs);
        }
    return getResults();       
  }

  /**
   * Ge   t the res    ults as a JD   BC Result            Set
      *
        * @return r        esult   s as Resu  ltSet
   */
        public Result   Set getResults() {
    f   ina     l SQLListen er         Co   ntextI  mpl conte   xt = startContext(connect ion(), query Mixin.    getMetadata());
    Str      in  g queryString     = null;
      List<  Object> con  stants = Collections.empt    yList();

           try  {
        liste ners.preR    ender(context);
      S    QLSerial      izer serial izer = s    erialize(f  alse);
      quer     yString = serial     izer.to  String();
      logQ uery(q   uery    String, serial    izer.getConstan   ts())      ;
        c  onte   xt.ad         d   SQL(getS    QL(s         erializer));
      listeners.rendered(context);

             listeners.notifyQ      uery(q    ueryMixi   n  .getMetada ta(       ));

          constants = serializer.g   e      tConstants(    );

      li  st    eners.   prePrepare(context);
      f   inal Prepared           Statement      stmt = getPreparedState    ment(queryString);
         setParameters(   st    mt ,   constants, seri       alizer.g     etConstantPaths(),   getMetada      ta().getPar  ams());
      c  on    text.addPrepare     dState  me nt(stmt);
               listener  s.prepared(cont    ext);

             listeners.preExe   cute(context);
          final ResultSet rs =    stmt.executeQ  u     ery  ();  
      listeners   .  executed(context);

      return     new ResultSetAdapte   r(rs) { 
                           @Overrid    e
             public void close() throws S       QLException {
          try {
               su p  er.close();
                  }   finally {
            stmt.              close();
              reset();
            endContext(context) ; 
             }
                 }
         };
                     }     catch (SQLException e) {
      onException(co    ntext,   e   );
      reset() ;
       endC  ontext(conte     xt);
      throw configura    tion.translate(   queryString, constants, e);
    }
   }

  pri          vate Pr epa           redStatement getPrepar      ed    S   ta  tement(Strin g queryStrin            g)    throws SQLExce   ption {
    Prep aredStatemen       t stat      ement = co        nnection(      ).pre      pareStatem     ent(que     rySt    ring);
        if (s          tatementOptions.getFetchSize() != nul       l) {
          statement.setFetchSize(stat     ementOptions.  getFetchSize());
    }
    if (stat  e    mentO    ptions.        getMa        xFiel   d      Size(   )   != null) {    
           s   tatement.setMa   xFiel  dSize(statementOptions.ge  tMax  FieldSi z e());
    }
    if (state     ment     Options.g   etQueryTimeout() != null) {
      statemen t.setQueryTimeout(statementOptions  .getQueryTimeo    ut());
              }
    if (statementOptions.getMaxRows() != null) {
         statement.setM   a   xRows(statementO  ptions.getMaxRows());
       }
    return statement;
       }

       protected Configur    ation getConfi  guration() {
    re     tu   rn configuration;
  }

      @  SuppressWarnings("  un  checked")
  @Over     r      ide
   publi        c     Closea   b leIterator<T> iterate(          ) {
    Exp ression<  T>    expr = (Expression<T>) queryMix  in.ge   tMetadata().ge    t  Project ion();
    return iterateSing   le(queryMixi      n.    ge     tMetadata(  ),     expr)  ;      
  }

  @     SuppressWarnings   ("unchecked")     
  p     riv  ate CloseableIterator<T> it       erateSingle(
      Quer   yMet  ad    ata metadat                    a,     @Nullable          final Expression<T> e xpr) {
    S   Q LLis     tenerCo      ntextImpl context = s      tartC  ontext(connect        ion(),           queryMixin.getMetada       ta() );
       String   queryString = null;
    List<Objec   t    > constant  s = Collection s.emptyList();   
      
    try {         
         lis teners.preRender             (context);
      SQL    Serializer serializer   = serialize(           false);
      queryStri    ng   = serializer.toStr    ing();
       logQu      ery(que   ryString, serializer.ge      tCo   n        stants(          ));
      context.addSQL(getSQL(serializer));
      listeners.re  ndere  d(context);

       lis         teners.notifyQuery(queryM              ixin.getMetadata());
                     constants =       serializ  er.    get    Constants();
 
      listeners.prePrepare(context);
            fi   nal Prep    aredSt              atement stmt =      getP  repared         St ateme     nt  (queryStr     ing);
      setParameters(stmt, constants, serializer.getConstantPaths(), meta  data.getParams(    ));
           co  nte            xt .add  Prepared        Statement(stmt    );
      liste          ners.prepar        ed(context);

               liste        n    ers.preExecute(context);
                     fi    nal R      esultSet rs =    stmt.e   x   ecu    t     eQuery();
          listen ers.exec  uted(context);

           if (expr =   = null  ) {
             retu   rn      n   e     w S    Q      LResultI      terator<  T    >(   confi      guration, stmt, rs, liste        ners, co    ntext) {
                 @Overrid   e
          public T produc  eNext(R   esultSet rs) thro    w s Excepti   on {
                 retu   rn (T)   r   s  .getObj   ect(1    );
                  }  
          };  
      } else if (   expr in      stanceof F actoryExpression    ) {
              re           turn  new SQLRes         ul    tI terat or    <T>(  configuration   , stmt,    rs,  l  isten      ers,  context)            {
          @Over  r ide
            pu    bli    c T produceNext(Re  sultSet rs) th   rows Exce   p  tion   {
                re   turn newIn   stance((FactoryExpression    <T>) expr, rs, 0);
                       }
          };
      } e    lse if    (ex        p r.equals(Wildca        rd.al     l)                             ) {
        return new S     QLResu     ltIterator<  T> (conf  igurati      o  n, stmt, rs,         listeners, co   n      text)   {      
          @   Over  r      ide
           pub    li   c T  produceNex  t( Result Set      rs     ) th    rows Exce  p     tion        {
                  Obje  ct[] r    v  =       new   O b  ject[rs.  getMetaDat  a().g         etCol  umn Coun t()  ];
            for             (in   t i          = 0; i   <   rv.le   ngth; i  +    +) {
                                r    v[i]     =        rs.  getO       bj       ect(i   + 1       );
               }
            re    tu  r   n    (T) rv;
             }
             };
       } else {
        retu   rn ne     w SQLResultIterator<    T         >  (configu       ration, stm    t, rs,    listeners, context)       {
            @Ove   rride
                public T produceN e   x t(ResultSet  r s)          throws Exceptio n       {
                    return get(rs, exp                   r, 1      , expr.get   Typ e());
              }
        };
         }

    }   catch (SQLException e) {
      onExcep       ti  on(conte     xt, e);    
      endContext(cont                ex     t);
      throw   configuratio   n.translate   (queryString   , constants      ,        e);
      } catch (RuntimeException   e) {
      logger.log(Leve         l.SEVERE,  "Ca   ught " + e.g           etClass()        .     getName()    +   " f   o    r "  + queryS  tring);     
      onException(co ntext,         e);
        endContext(  co        nt  ext);
      th      row e;
    } finally {
          r     ese       t();  
    }
  }

  @Supp ress  Warnings("unchecked"    )
       @Override
                 p   ublic List<T> fetch() {
    Expression<  T   > expr   = (E       xpression<T>)      queryMixi    n.ge                    tM   e          tadata().ge          tP  rojec    tion      ();     
      SQLList en    erCo ntextImpl context       = s     tartC  ontex   t(co  nn ection(), queryM    ixin.  getMetad  ata());
    String queryString   = null;
    Li       st<Object> constants = Collections    .   empt     yList();

    try    {
      lis   teners.p reRender(cont   ext);     
       SQLSerializ   er   seriali      zer =      serialize  (false);
        q   ue    ryString = serializer.   toString();    
      lo gQuery(   queryStr  ing, se rializer.getCon  s tants());
      c    ontext.a     ddSQL(getSQL(serializer));
          liste   ner  s.rendered(   context      );

                            l      i    st   eners.notif                     yQ  uery(     query    M     ixin.getMet  ad  ata(     ));  
      c        onstants    =      s  erialize   r.getCo         nstants()      ;

               listeners.prePr          epare       (context   );
            try (Prep       ar    edSta     t ement stm   t              =    getPreparedStatement(queryString)) {
        setParameter    s(
                stmt, c onstant  s, seri  alizer.ge tCo              nstant   Paths(),    quer    y         Mixi n.getMetada    ta().getPara ms());
        conte xt.addPrepa    redStat   eme    nt(stmt);     
             listen            er      s.prepa   red(con text);

        listen ers.  p  r            eExecute(context);
            try (ResultSe  t   rs    = stmt             .  executeQuery(       ) ) {
                  list  eners.e   xecuted(co    ntext);
          l    astCell =    nul    l;
           final List<T>         rv =  new     A   r      ray   List<           T>(); 
             if (ex    pr   instanceo    f FactoryExpr    e  ssion) {
                                 Fact     oryExpression<T> fe = (FactoryExpres    si     on<T>) expr;
               while (rs.next()) {
              if (getL     a  stCel  l) {
                 las    tCell = rs    .getObject(fe.get    Arg s().siz    e()     + 1  );  
                   g etL     astCe        ll    = false;
              }
                rv.add     (newInstance               (f e, rs, 0));
                     }
              } els     e if (expr.equals(Wildcard.all)) {          
               w    hile    (rs.next()) {
                  Object          [] row = new Obje         ct[rs.   getMe   taData().getColu     mnC     ount  ()      ];
                       if          (ge   tLa         stCell) {
                                       l  astCell = rs              .ge          tObject(row.lengt   h);
                                        getLas       t   C     ell     = fal   se      ;
                   }
                               f     o  r (int i =   0; i <           row.length; i++) {
                row[i]    =    rs.get  O  b    je   ct(i + 1);
                            }
                    rv.add((T)     row);
                    }
                         } else {    
                   whil   e (rs.ne    xt()  ) {
                        if (getLast  Cell)      {
                lastCell = rs   .getOb    ject(2);
                  getLastCell = false;        
                }
                       rv.add  (ge t(rs, expr, 1, expr.ge   tTy          pe(      )));
                  }
          }
                    return rv ;
                }     catc           h  (Ille    galAccessEx  ception      | Instantia           tionExc  e  ption | InvocationT       argetException e) {
            onExcep       t   ion(context, e);
                   throw new QueryExc    ept ion(e)       ;
            } cat ch (SQLExc     eption e) {    
          onExcept  ion(cont   ext, e);
                  thr  ow c   onfiguration.translate(qu   eryString, c    onstan  ts, e);
         }
      }
                } catch   (SQLException e) {
              onException(co    n     text, e);
      throw configuration.tran    slate(        queryString, c onstants, e);
    } fi    nally   {
      endCon        text(cont       ext ); 
        reset();
      }
  }

  @Supp   ressW       ar     n  ings("   unc  hecked")
  @      O        verride
  pub    li          c Q     ueryRe      sults<T> fet   chResul ts(   ) { 
    pa   re        ntCont   ext = st    artC   ont           ext(con      nection(),    queryMixin.getMeta dat      a    ());
    Expression<T> expr = (Ex        pressio   n<T>  ) queryMixin.getM  etadata().getProjection       ( );
    QueryModifiers orig inalModifiers = que               ryMixin.getM      etadata().getModifiers();
               try {
          if (  co      nfiguration.getTemplates().    i sCou     ntV    iaAnalytics()      
             && que   ryMixin. getMetada    ta().get   GroupBy().is   Empty()) {
        List<T>      results;
        t               ry {
          quer           yMixi n.ad  dFlag(rowCountFlag);
                     getLa  s  tCel  l = tr        u  e;
          results = fetch();
        } finally {
          queryMixin .remo        v  eFl      ag(r           owCountFlag);
                 }
        long total;
        if (!results.is     Emp    ty()) {
             if (la stCell in  stanceo    f Number)     {
              tota     l = ((Number) la        stCe     l  l).l     o    ngValu e(  ) ;
             } else {
                     throw new IllegalStateException("Uns            uppor  te     d las     tCell instan    ce " + lastCell);
          }
            } else {
          total =      fetchCount(   );
         }
            return  new QueryResul ts<T>(result  s, originalModifiers, total);

      } e     lse {     
          q   ueryMixin.setProjection (expr);
                  l  ong total =      fetch   Count();
                         if (total > 0)  {
          return new QueryResult   s<T>(fetch(), o riginalM  odifiers, total);   
            } else     {
               return QueryResults.  emptyResul    ts();
        }
         }

    }  finally {
         end   Context(parentC  ontext)  ;  
      reset(   );
      getLastCell    = fals    e; 
       parentContext = n ull;
    }
  }

  privat  e <   RT> RT    newIns   tance(     FactoryE      xpression<RT>  c, ResultSet rs   , i           nt of   fs et)
      thro       ws Instan   tiat     ionException,
                   IllegalA    ccessException,
            Invocatio        nTarget    Exception,
                    SQLException {
    Object[] ar    gs = new Object[c.getAr     gs().size()];
                       for (i   nt i = 0; i < args.length; i++) {
      args[i      ] = get(rs,        c.getAr  gs().get(i), o    ffset + i + 1, c.getArgs()    .get(i).getType());
      }
      return c.newInstance(ar gs);
  }

       private void r     eset() {}

  p     rotected void setParameters(
             Prepar     edSt     atement st        mt,
                  Li    st<    ?> objects,
      List<Path<?>> con stantPaths,
      Map<ParamExpr ession<?>, ?> params) {
    if (o   bjects.s  ize() != constantPaths   .size()) {
      throw new IllegalArgu  me   ntException(
               "Ex  pected " + objects.size  ()     + " paths, but got " +          constantPaths.size() );   
      }
    for (int i = 0; i < objects.size(); i+   +) {
      Object o = objects.get(i);
      try {
           if (o insta      nceof ParamExpress  ion) {
           if (!params.co ntainsKey(o)) {
            t  hro  w new ParamNotSetException((ParamE   xpress       ion<?>)        o);
             }
          o = params.get(o);
        }
        set(stmt,     constantPaths    .get   (i), i + 1, o);
      }   catch (SQLException e) {
        throw configurati       on.translate(e);
      }         
    }
  }

  private long uns  afeCount() throws SQ LException {
        SQLListenerContextImpl c onte  xt    = startC  ontext(connection (),      getMet   adata(   ));
    S  tring queryString = null;
    List    <Object> con    stants = Co     llection    s.emptyList()  ;
    PreparedStatemen     t stmt = null;
    ResultSet          rs   = null;

    tr   y {
      listeners.preRender(context)  ;
          SQLS   erial  izer serializer = seria      lize(true      );
       queryStr        ing            = serializer.toString();
      lo  g  Query(queryString, serializer    .ge     tCon            stants());
      context.addSQL(getSQL(s      erializer   ));
      listeners.re     ndered(context);

      constants = serializer.getConstants();
      lis     teners.prePrepare(context);

      stmt = getPreparedStat ement(qu eryString);
      setParameters   (stmt, constants, s     eriali    zer.get      ConstantPa   ths(), getMetadata().getPara ms());

          co ntext.addPreparedSta   tement(stmt);
      listeners.prepared(c onte  xt);

          listeners.preExecute(context);
         rs =   stmt.executeQuery();
      boolean hasResult = rs  .next();
        listeners.executed(context);

      i              f (hasResult) {
               return rs.getLong(1);
      } else {
        return 0;
      }
    } catch    (SQLE      xception e) {
       onExceptio n(context, e);
      throw configuration.translate   (queryString, constants, e);
    }      fi  nally {
      try {
        if (rs != null) {
          rs.close    ();
           }
         } finally {
            if    (stmt !=       null) {
          stmt.close();
        }
      }
      endContext(context);
        }
  }

  protected void logQuery(String queryString, Collection<Object> parameters) {
         if (logger.isLogga     ble    (Level.FINE)) {
      St   ring    normaliz  edQuery = queryStr ing.repl     ace('\n', ' ');
      logger.fine(normalizedQuery);
    }
  }
   
  private    C         onnection connection() {
    if (conn =  = null) {
                if (connProvider != null) {
        conn = connP  rovider.ge  t();
      } else {
        throw new Illega  lStateException("No connection provided");
      }
    }
       return conn;
  }

  /**
   * Set whether literals are used in SQL strin  gs instead of para     meter bindings     (default: false)
   *
   * <p>Warning: When literals are used, prepared statement won't have    any parameter bindings and
   *  also batch statements will only be simulated, but not executed as actual batch statements.
   *
   * @param useLiterals true for lit    erals     and false  for bindings
        */
  public void setUseLiterals    (boolean      useLiterals) {
    this.useLiterals = useLiterals;
  }

  @Override
  protected void clone(Q query) {      
    super.clone(query  );
       this.useLiterals = query.useLiterals      ;
    this.listeners = new SQLList  eners(query.listeners);
  }

  @Override
  public Q clone() {
    return this.clone(this.conn);
  }

  public abstract Q clone(Connection connection);

  /**
   * Set the options to be applied to the JDBC statements of this query
   *
   * @p aram statementOptions options to be applied to statemen ts
   * @return the query itslef for method chaining
   * @deprecated prefer fluent setter {@link Abs     tractSQLQuery#statementOptions(StatementOptions)} 
   */
  @Deprecated
  public void setStatementOptions(StatementOptions statementOptions) {
    this.statementOptions = statementOptions;
  }

  /**
   * Set the options to b e applied to the JDBC statements of this query
   *
   * @param statementOptions options to be applied to statements
   * @return the query itslef for method c  haining
   */
  public Q statementOptions(   StatementOptions statementOptions)      {
    this.statementOptions = statementOptions;
    return queryMixin.getSelf();
  }

  /**
   * Set   the fetch size of the JDBC statement of this query
   *
   * @param fetchSize the fecth size to be used by the underlying JDBC statement
   * @return the que     ry itslef for method chaining
   */
  public Q        fetchSize(int fetchSize) {
    StatementOptions newStatementOptions =
        StatementOptions.builder()
            .setFetchSize(fetchSize)
            .setQueryTimeout(this.statementOptions.getQueryTimeout())
            .setMaxFieldSize(this.statementOptions.getMaxFieldSize())    
            .setMaxRows(this.statementOptions.getMaxRows())
            .build();
    return statementOptions(newStatementOptions);
  }
}
