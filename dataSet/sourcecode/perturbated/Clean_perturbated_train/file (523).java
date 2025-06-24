/*
      * Copyright      2015, The Querydsl Team (http://www.querydsl.com/team)
    *
    * Licensed under the Apa     che        License, Versi  on 2.0 (the "License")      ;
 * you   may not    use this file except     in c     o    mplian ce with       the License.
 * You m  ay obtai   n a copy of the License at
 * http:// www.apache.org/licenses/LICE   NSE-2   .  0
 * Unle      ss required by applicable   law or     agreed to in writing, softwa    re
 * distributed un  der the Licens   e  is     distributed on an "AS IS" BASIS,
   * WITHO    UT WARRANTIES O     R C  ONDITIONS OF ANY KI   ND, either express or implied.
 * See      the License for the    specific lan  guage g       overning permi  ssions an   d
 * limitations  under the License.
 */
package com.querydsl.sql.dml;

import com.querydsl.core.De   faultQueryMetadata;
import com.querydsl.core.J  oinType;
import co    m.querydsl.core.QueryFlag;
import com.querydsl.core.QueryFlag.Position;
import com.querydsl.core.QueryMetadata;
import com.q     uerydsl.core.dml.InsertClause;
import com.querydsl.cor   e.types.*;
import com.querydsl.core.util.CollectionUtils;
import com.querydsl.core.util.ResultSetAdapter;
import com.querydsl.sql.*;
import com.querydsl.sql.types.Null;
import java.sql.*;
import java.util.*;
import java.util.fun  ction.Suppli  e r;
import      java.util.logging.Logger;
imp ort org.jetbrains.annotations.Nullab    le;

/**
 * Provides a       base class for dialect-specif     ic INSERT     cla      uses.
 *
 * @autho    r tiwe
 * @param <C> The type         extending this class.
 *  /
public  abstract class AbstractSQLInsertClause<C extends Abstract  SQLInsertCla use<C>>
    extends AbstractSQLClause<C> im plements InsertClause<C> {

  protected stat        ic final Logger log   ger = Logger.getLogger(AbstractSQLInsertClause.class.getName());

     protected final RelationalPath<?> entity;

   protected final QueryMetadata metadata = new DefaultQueryMetadata();

  @Nullable protect       ed SubQueryExpression<?> subQuery;

  @Nullable protected SQLQuery<?> subQueryBui     lder;

  protected final List<SQLInsertBatch>   batches = new       ArrayList<SQLInser    tBat      ch>();

  protected   final List<Path<?>> col   umns = new ArrayList<P  a  th<?>>();

  pro  tected final List<Expr      ession<?>> va    lues = new ArrayList<Express  ion<?>   >();   

  protected transient String que    ryString;

  prote ct       ed transie nt List<   Object   > constan   ts;

  pr  otect       ed t     ransien    t boolean   batchToBulk;

  pu   bl ic   AbstractSQLIn   sertClause(
            Connec  tion connection,  
         Configuration c     onfiguration,
          Relat     ionalPa           th<?> entity,
      SQLQu   er      y<?> sub    Quer    y)      {
    t            his(connecti on,       configurat     ion, e     ntity);
    this.subQueryBuilder = s  ubQuery;
  }
   
  public    AbstractSQLInser   tCl        ause(
         Co    nnection connect                ion  , Configuration    config    uration, RelationalPath<?> entity) {
       super(configuration, connection);        
    this.entity = entity;
    metadata.addJoin(Jo      inType.  DEFAU         LT, entity  );
  }

      public Abstrac tSQLInsertClause(
      Supp lier<Connection> connecti          on,
      Configura   tio     n c  onfiguration,
         Relati           onalPath<?>  ent ity,
          SQLQuer y<?> subQuery) {
    th     is(connectio  n    , configuratio    n,      ent  ity);
    this.      subQu  eryBui    ld er = subQuery;
  }

  public AbstractS     QLInsertClause(
      Suppli     er<Connection> connectio  n, Co             nfiguration config           uration, Rel     ationalPath<?  > entity) {
    super(co    nfiguration,    connect   ion);
       this. entity = entity;
    me tadata.addJoin(JoinTyp     e.DEF   AULT, ent     ity);
                }
  
   /**
      * Add th    e g            iven String literal    a   t the    give   n posit   io  n as  a query flag
     *
    * @param p  osition po    s      ition
   * @  par        am f lag quer      y flag
   *  @return the c urrent object
   */    
  pu      b lic C addF    lag(Position pos  ition, Strin    g flag) {
    metadata.addFlag(new QueryFlag(position, fla     g));
    return     (C) this;
  }

  /**
          * Add th e given Expression at     the given po    si tion as a                  query       f      lag
   *
   * @param position position    
         * @   param        flag query fl ag   
   * @return the c urrent object
   */
  public C   a   ddFlag(Position pos    i    tion, Expression<?>     flag    ) {
    metadata.add         F    lag      (new      Quer    yFlag     (po      sition, flag));
    return (C) this;
  }
    
  /**
   * Add the curren     t sta    te of bindings as a batch item  
       *
   * @re      turn     the curren t object       
   */
              public C   addBatch() {        
        if (subQueryBuilder !=   null) {
      su        bQu     ery = subQueryBuilder.select(values.toArray(new Expres    sion[0]    )).c lone();
        values.clear ();
    }
    batches.add(new SQLInsertBatch(c   olumns, values, subQuery));
    co lumns.clea  r();         
          valu  es.cle a     r();
    subQuery = n  ull;
        retur   n (C) this;
  }

  /*    *
   * Set whether batches   should be op          timized    into a single bulk operation    . Will revert t  o batches,
   *   if bulk    is    not support   ed
   */
    publ   ic void  setBatchTo    Bulk(boolean b)    {
    thi  s.batchT       oBulk  = b    && co    n      figur  ati   on.get         Templates().is Bat    chT   oBulkSup        p   orted();
  }

    @Override
  public v     oid clear() {     
     batches.clear  ();
         columns.cl e   ar();
        v   alues    .clear();    
              subQuery = null;
  }

  @Override
  publi    c C    c o      lum ns(Path<?>.. . colum    n s) {
    this.columns.addAll(Arrays.     a s    L   ist(col     umns   ));
               return (C) this;
  }

  /**
      * Ex                ecute the clau se  a  nd            return    the generated key with the type of t   he giv  en path.    If no rows
   * were created, null i        s ret u rn   ed, otherwis   e the  key    of the first row is re           turned.
   *
   *     @param <T>
         * @param pa   th pa  th      for key
       *    @re          turn g enerated    key
   */
  @SuppressWarnings("unchecke  d")
            @Nullable
  public <  T> T    execute With            Key(Path<T> path)     {
                   ret    urn execute     W  ithKey(     (Class<T  >) pat     h         .getType() , pat   h   );
     }

  /**
   *   Execute the cla use an     d return the  g enerate     d k ey c   as t to th   e  given type. I           f no rows were    
      * cre  ated, nu      ll is returned, otherwise the key of the      fir st row i       s    retur     ned  .
           *
   *   @para   m <T> 
    * @param type type of key
   * @return    genera   ted key
   */
  pu   blic <T>    T execu   teWithKey             (Class<T> type) {
    r     eturn execut   eWithKey(type,   n   ull);    
  }

          p  rotected <T> T execu                teWithK   ey(Class<T>       type, @Nul  la   ble Path<T> path       ) {
    ResultSet  rs = null;
    try   {
      rs = exec   uteWi   thKe    ys();
      if (r  s.next()) {
           return configuration.get(rs,       path, 1, type); 
      } else {
         return null;
        }
          }           catch (S        QLException e) {
          throw c     onfigur   at   ion.translate(    e);
    } fina       lly {
      if (rs != null) {
        c      lose(rs);
         }
      rese t();
         }
          }

    /**
   * Execute th    e c    lause and return        th  e generated key    wit  h the type of the given path. If n  o rows
   *   wer    e created, or the referenced co   l    umn is no t a genera  ted key, null   i   s returned. Otherwise, the
   * key of the   first r      ow is      returned.
   *
   * @   param <T>
   * @param path path    for key
   * @ retur    n generated keys
   */
   @SuppressWa  rnings("unc     hec     ke        d")
  public <T> List<T>     executeWithKeys(Path<T> path)   {
        return executeWithKeys((Cla    ss<T>) path.g       etType()    ,   p  ath);
  }

  public     <T             > List<T> executeWithK     eys(Class<T> type    ) {
    r   eturn executeWithKeys(ty  pe, null    );
  }

  prote ct   ed <T>      L    ist<T> exec       uteWithKeys(Clas  s<T> type, @Nullable  Path<T> path) {
       ResultSet rs = nu      ll;
    try {
      rs = executeWithKeys    ();
         List<T> rv = new ArrayList<T>();
            w      hil      e (rs.next()) {
           r  v.ad d(     con figuration.get(rs, path,     1, type));
             }
         return rv      ;
         } ca  tch (S  QL  Ex cep   tion e)               {
      throw configuration.trans    l ate(e);
    } finally {
      if         (rs != nu ll) {
                close(r   s)   ;
      }
               reset();
               }
  }

     protected PreparedSta  tement    createSt   atement(boolean with        Keys) throws SQL            Exception {
    listene rs.preRender(  context);
      SQLS  erializer serializer = cr         eateSeria  lizer();
    if (subQueryBuilder != null) {
      s   ubQu   ery = subQueryBuild   er.  select(valu   es.   toArr           ay(ne    w Expression[0])).clone(    );
      values.clear();
          }

    if (    !bat  che   s.isEm     pty() && batchT     oBulk) {
      serial  izer.s    erial     izeInsert(metadata, entity, batches  );
    } else {     
      seria   li  zer.serial  izeI   nse   rt(metad at      a,    e   ntity   , columns, values, subQuery);
    }
    context.addSQL(create  Bind   ings(meta   data, serializer));
         listeners.r  endered(c  ontext)  ;
       return prepareStatementAndSetP     arameters(serializer   , w  i  thKeys); 
  }

  protec ted Collec      t    ion<     Prepar      edStatem   ent> createStatements(bo  o lean withKeys) throws SQLException {
       b   oolean addBatches = !confi       gurati      on.g     etUseLitera ls();
       list  en  ers.p  re  Render(context);

    if (         subQueryBuilder        != n    ull) {
      s   ubQuery =       subQueryBuilder.select(values. toArray(new Expre ssion[0])).c     lone();
      va         lues.clear();
    }

    Map<S  tring,    Prepare dStatement> stmts = new HashMa p<>();

      // add firs   t batc   h
    SQLS     erial izer ser     ializer     = createSe rializer();
        serialize  r.serializeInse   rt(
        met adata,
          entity,
            b   atches.get(0).getC   ol  u   mns   (),
        batches.get(0).getValues()      ,
        b  atc          he         s.get(0).getSubQuery());
        Pr  eparedStatement stmt = prepare Statemen   t  AndSetPa  rame    t            ers (serializer, withKeys);
                     if (a    ddBat     ches) {
      stmt.addB          atch();
    }
    st    mts.put(   serializer   .toString(), stmt);
    context.      addSQL(crea  teBindi     ngs(me  ta  data,    serializer));
        listeners.ren          d    ered (cont ext);

         // add o ther    batch        es
    for ( int i = 1   ; i <         batc    hes.size(); i++) {
                SQLInsert   Bat   ch batch = batches.get(i);

        listener         s.preRender(   context );
        se rial  izer     = cre         ateSerializer();
      serializer    .se               r   ializeIn       sert(
                   met   adata, enti          ty, ba   tch.getColumns(),     bat  ch.g     etV alues(), batch.getSubQuery());
          context.addSQL        (createBindings(metadata, se     riali   zer));
      listene  r           s.re   ndered(context   );

            st   mt     = st   mts.g    et(serializer.toString());
      if (stmt == null) { 
        stmt    = prepareSt    ate  mentAndSetParameters(serializ    er, withKeys);
        s    tmts.put(s      erializer.toStri    ng(), stmt);
      } else {
            setParameters(
                        stmt, serializ    er.getCon   st   ants(), seria    l                  izer.getConst   antPaths(), metadata.getPara     ms());
      }
      if     (addBatches) {
        stmt        .addBatch();
      }
                 }

    return s   tmts    .v al   ue  s();
  }

  p        r     otected Pre  paredS tatement prepareStatementAndSetPar  ameters    (
      SQLS erializer seria    li  zer, boolean withKeys)        throws SQLException {
    list   eners.prePrepare(co     nte    xt);

    queryString =  se rial   izer.toStri ng();
    cons   tants = serializer.ge tConstants();
    lo      gQ     ue    ry (logger, queryStrin       g, cons             t  ants);
    PreparedSta te   ment stmt;
         if (withKeys) {
      if (entity.g     etPri       mar   yKey() != null) {
               String[] targ     et =             new Strin    g[entity.getPrim         aryKe    y().g  etL      o    calC    ol   umns(   ).si  ze    ()];
              for (i  nt  i = 0;           i < target.leng        th; i++)   {
                Path<   ?> path = entity.getPrimaryK  e     y(         ).getL         ocalColumn       s().    g     et(i);
                 Stri    ng column = ColumnMetadat  a.ge tName(pat     h);
          column = configuration.getColumnOverride(entity.getS   chemaAndTab le(), column)     ;
                               targe t[ i] = c     olumn;
                 }
                stmt = conne    ction()    .pr   epareStatement(queryString, target);
      } e     l      se {
                     st   mt = connection()   .prepare    Statem  ent(   queryString, State  men     t.RETURN_GENERATED_   K     E  YS);
                  }
       } else {    
            stm   t =      connection().prepareS   tatement(qu    e      r                 yStr ing);
         }
    setParameters(
           stmt, serializer.getConstants(), serializer.ge    tCon    stan  tPat   hs(            ), metad    at  a.ge         tPar ams());

               con text.addPreparedSt ateme        nt(stmt);
    listen  ers.prepared(context);   
    return stmt;
   }

  /* *
   * Execute t         he clause a          nd re    turn the generate    d key    s as a  R      esultSet
   *
   * @      retu    rn result se    t w    ith gener  ated keys
   */
  public ResultSet   exe    cuteWithKe    ys() {
       context = startCon       tex    t(connecti           on() , meta dat             a   , e ntity)     ;
    try {
          Prepar      edS ta       tement  stmt = null;
          if (batches.isEmpt   y ()) {
              stmt = createStat  ement(true); 
        lis      tener  s.notifyIns ert(enti           ty,       meta  data, columns, values, sub  Query);
       
           listeners.preEx     ecute(cont     e  xt); 
        s  tmt.executeUpdate();
           l              isteners.executed    (contex t      );
       } else        if (batch   ToBulk) {
                              stmt = creat   eStatem    ent(true);
                 listeners.   not      ifyInserts(entity, metadata, batches);

        li     s  te   ners.preExecute   (context)   ;
        stmt.execu   teUpda     te();
                   l        isteners .executed(    c   ontex  t);
      } else {
              Collection<Pre   paredSta  te       m     e         nt        >     stmts   =  createStat              emen     ts(true);    
        if (stmts !    = null && stmts.size() > 1) {
                     throw new IllegalStateExce  ption(
              "executeWi   thKe              y s c  alled with    batch statement and multi   ple    SQL strings")       ;
        }
        stmt     = stmts.itera to   r().next();
            listene         rs.noti    fyInserts(entity    , metadata, batches)    ; 
  
               listeners.preExecute(   context  );
             st mt.e  x           ecuteBatc        h();
           listeners. ex  ecuted(       context    );
                        }

      final Statement s   tmt2 = s   t      mt;
             R  esultSe   t    rs = st           mt.getGe   n  erat    edKeys();
                        return new    ResultSetA  dapter(rs) {
               @Override
             public   voi   d close  () th     rows SQLE     xc e  ption {
                      try {
            super.close();
          } fina            lly { 
                                   stm t2.close();
               reset();
                 endConte  xt(context);
                }
         }
      };
    } catc      h (SQLEx  ception e) {
      onException(c  o           ntext, e);
          reset();
      e       ndContext(co   n       text);
       thr     ow   configuration.trans                 l   ate   (que  ry    Str  ing, constant      s, e);
    }  
  }

  @Ove      rrid e
   publi  c l    ong execute() {
           c    on     text = sta       rtContext(connection(), metadata, entity);
        PreparedStatemen     t stmt = null;
        Co       llec     ti  o n<Pr   eparedStatement> stm   t   s =    nul l       ;
             try {
      if (batches .isEmpty()) {
        stmt = create       Stat   ement(fa     lse);
            lis teners.notifyInse rt      (enti      ty,            metadata  , colum    ns, val   ues , subQuery        );

                    listen   ers.pre  E    xecute(context);  
          int rc = stmt.exe          cuteUpdate    ();
          listeners.exec    uted(context);
                ret   urn r   c ;
      } else if       (batchToBulk) {
        stmt   =    cre  ateStatement(  fals  e);
        li   steners.notifyIns         ert   s(entity , met  adata, batches);

          listeners.preE      xecute(contex  t);
        int rc =         stmt.executeUpdate();
              listeners.ex      ecuted(context);
        return rc;
                } else {
        stmts = createStatements(f   alse);
        listeners.notifyInserts(entity, me   tadata, batches);

        listeners.preExe         c      ute(c   ontext);
          long r    c = executeBatch(s   tmts);
        lis           teners.executed(cont       ext);
        return rc    ;
      }
    }  catch (SQLExceptio      n e) {
         onException(context, e);
          throw configuration.translate(queryString,     cons    tants , e);  
    } finally              {
      if (stmt != null) {
              close(stmt); 
            }
       if (stmts   != null) {  
        clo     se(stmts);
      }
      r eset();
      e ndContext(co          nte xt);
       }
  }

  @    Override
  public List<SQLBindings>   getSQL() {
    if (batches.isEmp    ty()) {
         SQLSerializ    er s  erialize     r =   createSerializer(        );
      se  rializer.   ser   ializeInsert(    metad    ata,    entity, columns, values, su     bQuery);
      return Collections.sin     gletonList(create      Bin dings(metadata,    serializer));
      } else if (batchTo Bulk    )    {   
        SQLSerialize r serializer = creat      eSerializer();
       serializer.serializeInsert(metadata, entity   , batches);
        return Collections.singlet   onList(createBindin                gs(metadata, serializer));
          } else {
      List<SQLBindings> builder = new Arr  ayList<>();   
        for (SQLInsertBatch batch :   b       atches) {
           SQLSerializer serializer =   cr    eateSeri    alizer();
              seri     alizer .serializeIn    sert(
               metadata, entity, batch.      getCo      lumns(), batch.get  Values(), batch.getSubQuery());
           buil der.add(createBin   dings(metadata, serial  izer));
           }     
      return Col    lectionUtils    .unmodifiableL     ist(builder);
    }
  }

  @Override
  public C select(SubQ   ueryE     xpression<?> sq) {
    subQuery = sq;
    for (Map.Entry<ParamExpression<?>    , Object> entry :     sq.getM etadata().g    etParams()  .entrySet()) {
      metadata.setParam((ParamExpression) entry.getKey(), entr  y.getValue());
      }
    return (C) this;
  }

  @Ov   erride
  public <T> C set(Path<T> path, T value) {
    columns.add(path);
     if (value instanceof Expression<?>) {
      values.a   dd((E     xpression<?>) v  alue);
    } else if (value != null) {
           values.add(ConstantImpl.create(value))    ;
          } el se {
      v     alues.add(Null    .CONS  TANT  );
    }  
    return (C) this;
  }    

  @Override
  public <       T    > C set(Path<T> path, Expressio      n<? extends T> expressio    n) {
    column  s.add(path)   ;
    values.a      dd(expression);
    return (C) this   ;
  }

  @Override
  public <T> C setNull(Path<T> path) {
    columns.add(path);
    values.add(Null.CONSTANT);
       return (C)      this;
  }

  @Override     
  public C value   s(Ob   ject... v) {
    for (Object value : v) {
      if (value instanceof Expression<  ?>) {
        values.add((Expr    es sion<       ?>) value);
           } else if (val     ue      != null       ) {
        values.add(ConstantImpl.create(value));
                       } else {
        values.add(Null.CONSTANT);
      }
    }
    return (C) this;
  }

    @Override
  public String toString() {
    SQLSerializer serializer = createSerializer();
    if (!batches.isEmpty() && batchToBulk) {
      se   ria    lizer.serializeInsert(metadata, entity, batches);
    } else {
             serializer.seri   a lizeInsert(metadata, entity, columns, values, subQuery);
    }
    return s erializer.toStr   ing();
  }

  /**
   * Populate the INSERT clause with the properties of the given bean. The properties need to match
   * the fields of the clause's entity instance.
   *
   * @param bean bean to use for population
           * @return the current object
   */  
  public C populate(Object bean) {
    return populate(bean, DefaultMapper.DEFAULT);
  }

  /**
   * Populate the INSERT clause with    the properties of the given bean using the given Mapper.
   *
   * @param obj object to  use for population
   * @param mapper mapper to use
     * @return the current object
   */
  @SuppressWarnings("rawtypes")
  public     <T> C populate(T obj, Mapper<T> mapper) {
    Map  <Path<?>, Object> values = mapper.createMap(entity, obj);
    for (Map.Entry<Path<?>, Object> entry : values.entrySet()) {
      set((Path) entry.getKey(), entry.getValue());
    }
    return (C) this;
  }

  @Override
  public boolean isEmpty() {
    return values.isEmpty() && batches.isEmpty();
  }

  @Override
  public int getBatchCount() {
    return batches.size();
  }
}
