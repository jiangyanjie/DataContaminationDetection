/*
   * Copyright 201    5, T he Querydsl T   eam (http://www.querydsl.com/team)
      *
 * Licensed under the Apache Licen  se, Version 2.0 (the "License");
  *    you may   no    t    use thi    s file exc  ept in    com   plianc e with t h  e Li  cense.
             * You  may obtain a copy of the License at
 * http://www.apac  he.org/licenses/LICENSE-2.0
 *         Unless req    uired by app  licable law o    r a greed to in    writing, software
 *      di        stributed under the    License is d   istribute          d        on an "AS I    S" BASIS,
 * WITH   OUT WARRANTIES OR CONDITIONS OF ANY KIND, e  ither express or implied.
  * See the License for the specific language governing perm issi     ons and
 * limitations under the License.
 */
package com.querydsl.r2dbc.dml;
      
import com.querydsl.core.DefaultQ    ueryMetadata;
import com.querydsl.core.JoinType;
import com.querydsl.core.QueryFlag;
import com.querydsl.core.QueryFlag.Position;
import com.querydsl.core.QueryMetadata;
import com.querydsl.core.dml.ReactiveInsertClause;
import com.querydsl.core.types.*;
import com.quer       ydsl.r2dbc.*;
import com.   querydsl.r2dbc.binding.BindTarget;
import c    om.querydsl.r2dbc.binding.StatementWrappe r;
import com.querydsl.r2dbc.types.Null;
import com.querydsl.sql.ColumnMetadata;
import com.querydsl.sql.RelationalPath;
import com.querydsl.sql.S   QLBindings;
import com.querydsl.sql.dml.DefaultMa    pper;
import com.querydsl.sql.dml.Mapp  er;
import io.r2dbc.spi.*;
import java.util.*;
import java.util.logging.Logger;
import org.jetbr    ains.annotations.NotNull;
import org.jetbrains.annotat   ions.Nullable;
i mport reactor.cor   e.pub lisher.    Flux;
import reactor.core.publisher.Mono;

/**
 * Pro  vides a base class        for dialect-specifi   c I    NSERT cl    auses.
 *
 * @param <C> The type extending this class.
 * @author mc_fis      h
 */
public   abst     ract class AbstractR         2DBCInse    rtCl ause<C extends Abstra  ctR2DB   CInsertClause<C>>
    extends Abstr  actR2DBCClause<C> implements Reac  t        iveI       nsertClause<  C  >    {

  protected static fina    l Logger logger =
      Logger.getLogger(AbstractR2DBCInsertClause.class.getName());

     prot  ected final RelationalPa  th<?> entity;

  protected final   QueryMetadata metadata =  new DefaultQ    u    eryMetadata();

  @N   ullable protected SubQ ueryExpression<?> subQue   ry;

  @Nullable protected R2DBCQuery<?> su  bQue   ryBuild   er;

  protected final List<Path<?>> columns = new ArrayList<Path<?>>();

  protecte d final List< Expres           sion<?>> values =    n      ew ArrayList<Expressio   n<?>>();

  prot  ect   ed transient   String queryString;

    protected transient List<Object> const   ants;

  public AbstractR2DBCIn  sertClause(
       Connection connection,
                   Configurati        on configurati   on,
      RelationalPath<?> en  t     ity,
      R2DBCQuery<?> subQu   ery) {
     this(connection,   configuration, entity)  ;
    this    .     subQue    ryBuilder      = subQuery    ;
  }
   
  public AbstractR2DB CInser  t       Clause(
           Co   nnection conne   ction        , Configuratio  n con  fi     guration, RelationalPath<?> e          nt     ity) {
     super(c     onfiguration, co nnection);
    this.entity = entity;
    metadata.addJoin(J  oinType.DEFAULT, entity);
  }        

  pu        blic AbstractR2DBCIn  sertClause(
      R2DBCConnectionProvider connection,
       Configurat ion conf   iguration,
      RelationalPath<?> entity,
        R2DBCQuery<?> subQ  uery) {
    this(connection, configurat     ion, entity);
             t   h   is.subQuer     yBuild e  r = subQuery;
  }

  public Abstract     R2DBCIn sertClause(
             R2DBCConne c  tionProvider conne  ct ion, Configuration    configura   tion, RelationalPath<? > entity) {
    super(configuration  , co   nnec     t ion);
                               this.entity  = entity;
    metadata.addJoi      n(JoinTy    pe.DE     F    AUL         T                , ent  ity);
  }  

     /**
   *  Add the given       Strin       g literal at the given pos        ition   as a quer   y       flag
   *
   * @param posit   ion posit  ion
   * @param flag query flag
   * @  r eturn the current object
      */
      public  C a  ddFlag(Posi tio     n position,   String flag) {
          metadata.addFlag(new QueryFlag(position, flag));
    retur    n (      C) this;
    }

  /**
             * Add    the given Exp   ression at the give    n position a   s a      query fl  ag
   *
   * @pa  ram      position posit     ion
      *      @param fl   ag query flag     
   * @return the curr ent o     bject
   *  /  
  pu   blic C    addFlag(Position      positio        n, Expressi    on<?>    flag)   {      
      meta   d    at   a    .addFlag(new QueryFlag(pos  ition, flag));
            return (C) this;
  }

  @O verride
   publ    ic void  clear() {
    columns    .clea r();
       values.clear();
           subQuery = nul  l;
         }

  @Override
        public          C columns(      Path <?>... columns) {
    this.colum   ns.addAll(Arrays.asList(col      umns));
         return (C) this;
  }

  /**
   * Execute the claus  e and return the  generated k  ey wit  h the type of t   he given path. If no     rows
    * were created, null is r           eturn   ed, o   therw  is  e the key of th        e first row is       returne  d  .
   *
     * @p  aram      <T>
   *     @p  aram pa   th         path for key
    *    @retu   rn ge    nerated key     
   */
  @Sup   pre                         ssWarnings("u nchecked   ")
  @Nullable
  public <T> M o   no<T>     executeWithKey(Path<T> path  ) {
    return  executeWithKe  y((Class<T>) path.getType(), path);
  }
  
        /**
   * Exe    cute the cl    au       se        and r    et    urn the ge    nerated    key cast to the given   ty  pe. If no rows were    
   * created, null is re  tu    rned, otherwi   se the key of     the first row is returned.
     *     
       *   @pa    ram <T>
   *       @par  am typ e   type of     key 
   * @retur     n g     en erated key
         */
  publ  ic <T> Mono<T> ex    e    cuteWithKe   y(Class<T>      type) {
    retur  n       executeWit    hKey(type, null);
  }
    
  protected <T> Mono<T> executeWithKe  y(Class<T> type, @Nu  llable    Path<T>          path) {     
    return getConnection    ()  
                .map(connecti on -> createStatement(connection,       true))
        .flatMap(  c onnection -> execute   StatementWi     thKey(connection, t     ype    , path));
  }

  /**
   * Execute      the clause and return the g    enera ted    k ey with                the type of    the given path. If no r  ows
   * were    created,  or the referenced co  lumn is not a        generated key, null is returned. O     therwise, the
   *  key o      f the first row   is r     eturned.
   *
   *  @param <T        >
   * @param path path for      key
       * @return generated keys
   *    /
  @Supp   re  ssWarnings("unch     ecked      ")
  public <T> Flux<T> executeWithKeys        (Path<T> path) {
    retur   n executeWithKe    ys((Class<T>) path.getType(), path);
    }

    publ  ic <  T> Flux<T> executeWithKeys(Class<T>  type)      {
    return  executeWithKeys(type, null);
        }
       
  public <T> Flux<T> executeWithKe   ys(Class<T> type,   @Nullable Path   <T> pat    h) {
    return getConnection( )
        .map(c   onnection -> createSta     tement(connection, true))
                     .flatMapM an  y(   conne   ction -> executeStatementWithKeys(connection,      type,    path));   
  }

  protected  Sta  tement createSta  tement(C  onnection connec      tion,  boolean withKeys) {
    if (subQueryB  uilder != nu     ll) {
      subQuery =        subQueryBuilder.select(values.toArray(new Expression[v   alues.size()])).clone();
      values.clear();
    }

    SQL   Serializer se  rializer = createSerializerAndSeriali   ze()  ;
    return prepareStatemen  tA    ndSetParameters(connection,   serializer, withKeys);
  }

    prote    ct ed Statement createStatements(Co nnect  io  n con    nection,  boo        lean withKey     s) {
             if     (subQueryBuilder !   = null)   {
          su    b  Que ry = subQueryBuilder.select(values.t          oArray(new Expression[values.siz     e()]  )).clone();
      va   lues.clear();
          }

        SQLSerializer serialize    r  = creat   eSerializ  erAndS  e  rialize();   
    Statem  ent statement   = prepareStatementAndSetParameters(conn       ection   , serializer, withKe     ys);

    r      eturn st      atement;
      }

  prot  ected Statement prepareStatementAnd   SetParam          eters(
        Connection connectio  n,         SQLSerial   izer serializer, boole  an withKe ys)   {
        L        ist<Object> constan     t   s = serializer.ge  t  Constant   s();    
    String orig      ina lSql = serializer.to      String();
    qu     e ryStr  in    g =
        R2dbcUtils.repla    ceBindin    gArgument    s   (
            configuration.getBindMarkerFactory().c  re   ate(           ),       const   ants,   origi     nalSql  );

    log   Query(logger, queryStri       ng, co   nsta  nts);

      Stateme          nt   stateme  n    t = c  onnection.creat         eStatement   (queryString);
    B     indTarg    e   t    bindTarget = new  Stat      ementWrapper(statement);

    if (withKeys) {
          if (    ent    ity.getPrimar   yKey  ()  !=    null) {
        Strin  g    [] t     a       rget = new Strin   g[entity   .getPrim    ary       Key().ge   tLocalColum  ns(   ).s  iz e()];
               for (int i = 0; i < targe    t.length; i++) {
               Path<?> path = entity.getPrim  aryKey().getLocalColumns().get(i);
          String colum    n = ColumnMet   a    data.getName(path);
             target      [i] = colu     mn;
        }
          state  me      nt.retur    nGeneratedValues(target);
      }
    }

    setParamet   ers(
             bin   dT    a   rge     t,
          configuration.getBi ndMarkerFacto          ry().c   reate()      ,
        constants,
        serializer.getConstantPat  h            s(), 
         me        tadata.getPar     ams());

    return statement;
   }

  /**
   * Execu   te the   cla  use and ret     urn the genera ted k  eys as a Resu   lt
   *
   * @return result set with gener  ated keys  
   */
  protected <T> Mon   o<T     > exe   cuteStatemen tWithKey(
      State    ment stmt, Class<T> typ    e, @Nullable     Path<T>    path) {
      RowMapper<T> mapper =
             (r  ow, metadata) -> O      bjects.    re   quireNonNull(ro w.get    (0, type),   "Null k     e y resu    lt      ");
        return Mono.from(stmt.execute()).flatMa     p(r    esult -> Mo   no.from(result.m   ap(mapper::map)));
  }

      protected <T> Flux<    T> ex  ecute   StatementWithKeys(
      S  tatement stmt,      Clas    s<T    > type, @Nul lable Path<T> path) {
          Row   Mapper  <  T> mapp      er =
        (row, metadata) -> Objects.requireN  on     Null(row.get(0, type), "Null key result");
    return     Flu      x.from(stmt.execu  te(  )).flatMap(result ->        result.map( mapper::map));
       }

  priva       t   e Mono          <Long>  executeStatement(Statem  ent stmt) {
    return Mono.from(stmt.execu  te())
          .       flatMap(result -> Mono.from(result .ge    tRowsUpdated()))
          .map(L     ong::valueOf);
  }

  private Mono<L    ong> executeState men  ts(S     tatement stmt) {
    return Flu     x.from(stmt.e    xecute          ())
        .flatMap(result -> Mono.       from(resul t.getRo    wsUpdated(    )))
        .reduce(0L, Long::su     m);
  }

  private Mono< Long   > executeBatches(Batch batch) {
           return   F    lux.from(batch.execute())
        .fl    atMap(result -> Mono.from(result.getRowsU    pdated()))
                .reduce(0L, Long::sum);
  }
     
  @O      verride
       publ    ic Mono<    Long> ex       ecute()    {
      retur n get    C   on nection()
                            .       map(connection -> createState                ments(connection, fal     se))
             .fl atMap(this::exec     uteS tateme   nts  );
   }

  @Override
    public List<SQLBindings> getSQL()      {
      SQLSerializer serializer = createSer  ialize      r(tr   ue);
    serializer.serializeInsert(metadata, en     tity, columns, values, subQuery)  ;
    return Colle    ctions.singletonList(cr   eateBin   d      ings(me tadata, ser     ializer));
  }
        
  @Override
  pub lic C selec   t(SubQueryExpr     ession<?> sq) {
         subQuery = s     q;
        for (Ma   p.Entry<Pa   ramEx   pression     <?>,     Ob  ject> entry : sq.getMetadata(   ).getPar           ams().e    ntr       ySet   ()) {
             metadata.setParam( (Para mE          xpression)        entry.getKey(), entry.getValu   e())    ;
    }
         re    tu   rn (C) this;
  }

    @Override
  publ   ic <T> C set   (    Path<  T> path, T value  ) {
    colum  ns.add(pat   h);
             if (v     alue instanceof Expres      sion<?>) {
       value     s.a  dd( (Expre       ssion<?>) va l   ue);
                } else if (    value != nul l) {
      values   .     add(ConstantImpl.creat  e(val              ue));
       }        el  s   e { 
      values           .a dd  (Nul   l.CO   NSTANT);
       }
        return (C)    this     ;
  }

  @Override
  public               <T> C   s  et(Path<T> pat  h, Expression<   ? extends T> expressi   on) {
    column  s.add(path);
    values.add(expres   sio    n)       ;
              re  tur  n (C) t   his;
  } 

  @Override
  publ  ic  <T> C se   t   Null(Path<T    >       path) {
       columns.add(path)    ;
    values.a       dd(Null.CONSTANT  );
        ret   urn (C) t   his;
  }

  @O    verride    
   pub  lic C values(Object... v) {
         for (Ob     ject value : v) {
      if (val  ue instanceof Expression<?>) {
        values.ad d((Expression<?>     ) value);
      } else if (value != null) {
        values.add(ConstantImpl.c   r     eate(value))    ;
       } else {
           values.add(Nul   l.CONSTANT);
      }
     }
    return (C) th is;
  }

  @Overr  ide
  public           String toString() {
    SQL   Serializer serializer = createSerializer(tru   e);
    serial  izer.serialize    Inser      t(metadata, entity, columns, values, subQuery);
    retur     n serializer.toString();
  }

  private SQLSerializer create  SerializerAndSerialize() {
           SQLSerializer       seria     lizer = cre     ateSeriali  zer(true);
      seri     alizer.s  e    ria         lizeInsert(metadata, entity, co    lumns, values, subQuery);
    ret   urn serializer;
  }    

  /**
   * Populate the INSERT cl     a     use wi          th the propertie        s of the  g  iven          bean. The properties nee   d to ma         tch
   * the fields o   f the cla use's enti      ty instance.
        *
   * @param bean bean to   use for population
   * @return the current object
   */
  public C populate(Object bean) {
    return populate(bean, DefaultMapper.DEFAULT);
  }      

  /**
   * Populate t   h    e INSERT clause with the properties of the given bean using the given Mapper.
   *
   * @param obj ob  ject to use for populati     on
   * @param mapper mapper to use
   * @return the current object
   */
  @Suppre ssWarnings("rawtypes")
  publ    ic     <T> C pop    ulate(T obj, Mapper<T> mapper) {
    Map<Path<?>, Object> values = mapper.createMa   p(entity, obj);
    for (Map.Entry<Path<?>, Object> entr  y : values.entrySet()) {
      s et((Path) entry.getKey(), entry.getValue());
    }
             return (C) this;
  }

  @Override
  public       boolean isEmpty() {
    return  values.isEmpty();
  }

  //    pr     ivate <T> vo id setBatchParameters(Statement stmt, R2DBCInsertBatch batch, int offset) {
  //        List<Object> constants = batch
         //                .getValues()
  //                 .stream()
  //                .map       (c -> ((Constant<T>) c).getConstant()) // TODO: support expressions
  //                .collect(Collectors.toList());
  //        setParameters(stmt, co   nstants, batch.getColumns(), metadata.getParams());
  //    }

  @FunctionalInterface
  private interface RowMapper<T> {
    @NotNull
    T map(Row row, RowMetadata metadata);
  }
}
