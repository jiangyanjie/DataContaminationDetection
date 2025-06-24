/*
 * Copyright 2015,    The Querydsl  Team (http://www.querydsl.com/team)  
 *
    * Licensed   un  der the Apache Licen    se, Version    2.0  (th e        "L  icense");
 * you may      not use this file            except in compliance  w   ith the L  icense.
   * You may obtain a co   py o  f the Licen se   at
 * http://www.apache.org/licenses/L  ICENSE-2.0
 * Unle   ss       required by applicable law or agreed to in writing  ,     software
 * distrib      uted under the License is d     istrib     uted on an   "AS IS        " BASIS   ,
 * WITHOUT WARRANT IES OR CONDITIO  NS OF ANY KIND, either express or implied.
 * Se       e the Lic       ense   for the specif  ic language governi  ng permissions and
 * limi    tations  under the License.
 */
package com.queryds   l.sql.dml;

import com.quer  ydsl     .core.*;
import com.querydsl.core.QueryFlag.Position;
import com.querydsl.core.dml.UpdateClause;
import com.queryd sl.core.types.ConstantImpl;
import com.que   rydsl.core.types.Expression;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.RelationalPath;
import com.querydsl.sql.SQLBindings;
import com.   querydsl.sql.SQL   Serializer;
import com.querydsl.sql.types.Null;
import java.sql.Connect    ion;
import java.sql.PreparedStatement;
import java.sql.SQLExcep       ti    on;
import java.util.*;
import java.util.function.Supplier;
imp  ort jav   a        .uti   l.loggi   ng.Logger;
import org.jetbrains   .annotations.Range;

/**
 * Provides a base class   for dia   lec   t-specific UPDATE    clauses.
 *
 * @auth    or tiwe
 * @param <C> The type    e xtendin   g    this class.
 */
public abstract class AbstractSQLUpdateClause<C exten    ds A      bstractSQLUp     dateClause<C>>
    extends    AbstractSQLClause<C> implements Updat        eClause<C> {

    protect            ed static final Logger logger = Logger.getLog   ger(AbstractSQLUpda   teClause.class.getN     ame());

  protec   t      ed final RelationalPath<?> ent   i  ty;

  protected final List<SQLUpdate      Batch> batches    = new ArrayList<SQLUpdateBatch>();

  protecte   d Map<Path<?>, Expr   ession<?>> updates = new LinkedH   ashMap< > ();

  protected Qu     er   y    Metadata metadata = new   DefaultQueryMetadata();

  protected transi   ent String         queryString;

    protec      ted transient List<Object> c  onstants;

          p ublic Ab  stractS   QLUpdate  Clause(
      Connec    tion connection, Configuration    configuration, Relationa    lPa th<?> entity) {
    su  per(configurati  on, conne      ction)    ;
    this.entity  = entity;
    met adata.   addJoin(JoinType.DEFAUL        T, entity);
  }

  public  AbstractS  QLUpdateClause(
      Su   pplier<Connection> conne   ction, C  onfi   guration configu       r     ation, RelationalP  ath<?    > entity) {
      super(configurat ion        , connection)    ;
        this.entity = entity;
                      metadata.   addJo  in(J   oinTyp    e.DEFAULT, entity);
  }
    
  /**
   * Add the given String liter  al at the given po  sition as a query flag     
   *
   * @param position position
   * @p  aram      fl    ag query  fla    g   
   * @return the curre     nt object
   */
  public C addFla    g(Position position, Str       i    ng fl   a   g) {                 
    metadata   .add  Flag(ne         w Quer    yFlag(position, flag       ));
    return   (C) this;
  }
          
  /**
   * Add t  he give  n Expr   ession at the given positi  on as a   query f     lag
   *
      * @param position posi       tion
   * @param fl     ag query flag
        * @return the  current object
     */
  public C add   Flag  (Position    position,  Expression<  ?> fla           g) {
      metadata.addF lag(n      ew Que      ryFlag(   position,   fl          ag)     );
    return (C) this;
  }     

  /*  *
       *     Add         the current state of bindings as a batch item
        *
   * @re turn t     he current object
   */
     pu   blic    C addBa  t   ch()       {
    batches.add(new SQLUpdateBatch(me  ta    data, upda   tes));    
    updates = new LinkedHashMap<>();
      metad      ata =   new DefaultQueryMetada    ta();
    metadata.addJoi  n(JoinType.DEFAULT, entity);
     return (C) this;
  }

  @Override
  public void clear() {
    batches.clear();
       updates = new LinkedHashMap<>();
      metadata = new Default     QueryMetadata();
    metad     ata.addJoin(Join    Type.DEFAULT, entity);
  }

  prot  ected   PreparedStatement createStatement() throws SQLExcep        tion {
    listeners.preRender(context);   
    SQLSerializer serializer = crea      teS    erializer();   
          serializer.se rializeUp   date(met        adata, entity, updates);
    que ryString =           s erializer .toString();
    const  ants = serializer.getConstants();
           logQuery(logger, queryString, constants);
                     c  ontex   t.   addSQL(createBindings(metada  ta    ,     serializer));
    listeners.prepared(context);

    listeners.prePr             epare(co      ntext);
        PreparedStatement stmt =   connection().prepareStatement(queryString);
       setParame   ters(
        stmt, serializer.getConstants(), serializer.ge tCons  tantPaths( ), metadata.g   e   t     Params()  );
    context.a   ddPrepa     redStatement(s   tmt);
    listeners.prepared(context);

    return stm    t;
  }
   
       pr   otected   Collection  <PreparedStatement> createStatements() throws SQLEx cept ion {
    boolean addBatches = !configur      ation.   getUseLiterals();
    listener s.preRender(context);
           SQLSerializ           er serializer = c   reateSerializer();
    serializer.serializeUpdate(batches.get(0).getMetada     ta(), entity, b atches.get(   0).  get  Updat   es());
    queryString = ser   ializer.toString(    );
    c  onst       a   nts = serializer.getConstants();
       l      ogQuery(logger, que   ryString, cons  tants);
    cont    ext.addSQL(creat   eBindings(met  ada       ta,    serializ   er));
    li   steners.r          endered(context);

    Map<Strin g, Pr  ep    ar      edS      tate      ment> stmts = new HashMap<>();
   
     // a      dd f   irst b   atch
    listeners.prePrepare   (context);
    PreparedStatement  s  tm  t   = con        nectio    n().prepareS      tatemen      t(que     ryString);
    setPa   rameters    (
           stmt, serializer.   getCon       stants(), seriali  zer.g        etConstantPaths(), metadata.g   etParams());
       if (addBatches) { 
      stmt.addBatch();
    }
         stmts.put(serializer.toString(), stm      t);
        c ont     ext.addP reparedS t atement(stmt);
    listeners.prep         ared(conte      xt  );

            // add o          t    her batches
       for     (int i = 1   ; i        < batches.size(); i+  +) {
                    l isteners.    preRender(co      ntext);
            serializer = createSeri   aliz       er();
      serializer.se    rializeUpdate(batches.get(i).ge     tMetadat    a(), entity,      batches.get(i).getUpdates(    ));
      co    ntext.addSQL(cre    a  teB   indings(metadata, se      rializ     er))  ;
        l isteners.rendered(context);  

      stmt = stmts.get(serializer.toString()   );
           if (stmt == n  ull) {
                        list en        ers.pr    e              Prepare(contex             t);
          stm    t = connectio n().prepareSta    tement(serializer.to   String()      );
           stmts. put     (seria      lize r   .toString(), s      tmt);
              context.addPreparedStatement(st       mt)  ;
                   listeners.prep    ared(context);
      }
      set    Paramet                 ers(     
          stmt, s  erializer.getConstants(), ser   ializer.getConstantPaths(), metad   a        ta.get   Params());
              if (addBatches) {
                   stmt.addBa   tch() ;
         }
    }

    re         turn st   m  ts.valu     es();
  }
  
  @Override
       public l    ong e    xecute() {
         co    nte     xt =      star  tConte         xt(connection() , metadata    , en  tity);

    Prepare  dStatement st    mt = n    ull;
      Collectio   n<Prep   aredSta     teme     n            t> st  mt    s    = nul l;
        try {
        if (b         atche s.is     Empty()) {
            st     mt = createStat   emen  t( );
         listeners.n  ot  ifyUpdate(entit   y, metadata, updates);

        listene               rs.preExecute(c          ontext);
            int rc = stmt.executeU   pdate();
             listen  e       rs.exec  u   ted        (contex   t);
        r  eturn   rc  ;
      } else {
             s      tm    ts = createStatements();
          li         steners.notifyUpdates(entity, batches);

          list    eners.preE         xecute  (co  ntex t);
        long rc = executeBat ch    (stmts)  ;
             listeners.execu   ted(context);
        return         rc;
      }
    } catch (S    QLE  xception     e) {
      onExc   eption(cont          ext, e);
      throw configur   ation.transla       te   (queryString, cons        tant     s , e);   
    } finally {
      if (stmt != null) {
              close(stmt);      
       }
      i  f (    stm   ts != null)    {
        close(stmts);
          }
              rese    t();
      endC  ontext(context    );
    }
  }

  @O          verride   
    public   List<SQ       LB        indin     gs> g    etSQL() {
     if ( b    a    tches.i    s Empty()) {
           SQLSerializer serializer = createSerializer(       );
        s eria    li    zer.serializeUp     date(metadata, e  ntity, u         pdates);
      return          Col  le   ctions.singletonList(creat   eBindings   (metadata, ser        ializer)           );     
       } else {
      L    ist<SQLBindings> bui   lder = new     A   rrayList<>();
            for (SQ    LUpd  at  eBatch batch      : batches)   {
        SQLSerializer seria    lizer    = cre        ateS  e  rializer();
        se    rializer.serializeUpdate(bat     ch.getMeta  da ta(), entity, batch.getUpdates   ())    ;
        builder.add(createBindings(metada   ta, serializer));
         }
         return          Collection   s.   unmodifi    ab  le        List(      builder);
        }
  }

  @Override
  public <T> C se        t(Pat h<T> path, T value) {
    if (v        alue instan          ceof Expression<?       >) {
                  u                pdates                   .p ut(path, (Expres     sion<?>) val   ue);
       }         el   se if (value != null) {
      updates.put(pa     th, ConstantImpl.create(value));    
    } else {  
               setNull      (path);
    }
          return (C) this;
  }     
    
  @Ov     errid        e
     p  ubl      ic <T>        C set(    Path<T> path, Ex  pression<? extends T> expressi         on) {
          i   f (expression !=  null)    {
      updates.put(path, exp  ression);       
      } els     e {
      setN           ull(path);
    }
         return (C) this;
       }

  @Overr      ide
    p    ublic <T> C set N ull(Path<T> path)     {
    upda   tes     .put  (path, Null.CONSTANT);
         return (C) this;
  }

  @O  verride
  pu blic C set(List<? extends     Path   <?>> p  aths, List<?> values) {
    f or (int i = 0; i < paths.siz    e(); i++) {
      if (values.get(i) instanceof Expres  s  ion) {
        upd ates.p   ut(paths.get(i), (Expres     sion<?  >   ) values.get(i));
      } else if (  v        alues.get(i) != null)    {
        update s.put(paths.get(i), ConstantImpl.create(value  s.get(i)));
      } else {
                  updates.put    (pa       t      hs.get          (i), Null.CONSTANT)    ;
      }
    }             
    return (  C     ) this;
  }

  public C where(Predicate p) {
    metadata.addW  h ere     (p);
           return (C        ) th  is;
  }

  @Override
  public C whe    re(Predicate... o) {
    for (Pre      dicate p : o) {
        m   etadata.addWhere(p);
      }
    r    eturn (C) this;        
       }

      public C limit(@Range(from = 0, to = Integer.MAX_VAL     U   E) long limit) {
          metadata.setM     odifiers(QueryModifiers.lim it(limit));
    r  eturn (C     ) thi     s;
  }

  @Override
  public String     toString() {
    SQLSerializer s  erializer = createSerializer();
         serializer.serializeUpdate(metadata, entity, updates);
    re    turn serializer.toStr   ing();
     }

  /**
   * Populate the UPDATE clause with the proper ties of the g    iven       bean. The properties need to match
      * the fi   elds of the clause's entity instance. Primary key columns are skipped in t    he population.
   *
   * @param bean      bean to use for population
   * @return the current object
   */
  @Suppress       Warnings("unchecked")
  public C populate(O  bject bean) {
    r  eturn populate(bean, Def  aultMapper.DEFAULT);
  }

  /**
       * Populate the UPDATE cl ause with the        properties o  f the given bean using    the given Mapper.
   *
   * @param    o      bj  object  to use for population
       * @param mapper mapper to use
   * @re   turn the current object
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public <T> C populate(T obj, Mapper<T> mapper) {
    Collection<? extends Path<?>> primaryKeyColu      mns =
        entity.getPrimar yKey() != null
            ? entity.getPrimaryKey().getLoc  alColumns() 
            : Co   llections.<Path<?>>emptyList();
       Map<Path<?>, Object> values = mapper.createMap(entity, obj);
    for (Map.Entry<Path<?>, Object> entry : values.entrySet()) {
           if (!primaryKeyColumns.contains(entry.getKey())) {
         set((Path) entry.getKey(), entry.getValue());
        }
    }
    return (C) thi   s;
  }

  @Override
  public boolean isEmpt  y() {
    return updates.isEmpty() && batches.isEmpty();
  }

  @Override
  public int getBatchCount() {
    return batches.size();
  }
}
