/*
 * Copyright 2015,    The Querydsl   Team (http://www.querydsl.com/team )
 *
    * Licen  sed under  the Apache License       , Version 2.0 (the "Lice   nse "  );
 * you    may not use this file ex   cept         in complian   ce wi  th the  Licens   e.
         * You   may obtain     a copy of   the License at
 * http://w   ww.apache.org/licenses/LICE  NSE-2 .0
 * Unless required by ap   plicable                    law  or agreed to in writing, sof   tware
 * distr  ibuted under    the License is distr     ibuted on an "   AS IS" BASIS,
         *         WITH   OUT WA     RRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the    specific language govern     ing permissions and
 * limi    tations under the License.
 */
package com.querydsl.sql.dml;

import com.que  rydsl.core.*;
import com.querydsl.core.QueryFlag.Position;
import com.querydsl.core.dml.DeleteClause;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Valid   atingVisitor;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.Relat    ionalPath;
import com.querydsl.sql.SQLBin dings;
import c  om.querydsl.sql.SQLSerializer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.uti   l .*;
import java.util.function.Supplier;
import java.util.log   ging.Logger;
im  port     org.  jetbrains  .annotations.Range;

/**
 * Provides a b      ase class    f or dialect       -specific DELETE cla  us   es.
 *
 *    @author         tiwe
 * @param      <C> The type extending this class.
 */    
public abstract class AbstractSQLDeleteClause<C extends Abstract     SQLDelete Cla   use<C>>
    e        xtends Abs  tract  SQLClause<C> implements Dele  teClaus    e<C> {

  protected static f i    nal Logger logger = Logger.get  Log    ger(Abs     t  r   actSQLDeleteClause.cl      ass.getName( ))     ;

  protec  ted static        fin      a     l      ValidatingVisitor val   idatin   g       Visitor =
         new V   alidatingVisitor(
              "Und  eclared p   ath       '%     s'. "
                 + "A    delete op         eration can only refe rence a single table. "         
                    +  "Consider this alternative: DELETE ... WHE RE EXISTS (subquery)");

  protec   ted final RelationalPath<?> entity;

  pro   tect     ed final Lis t<Quer yM etada  ta> batches    =  new ArrayList<QueryMet         adata>()    ;

  protected DefaultQueryMetadat     a meta      data = new DefaultQue    ryMetadata();

  protected transient String queryString;

  prote cted tran       sient List<Object> constants;

       public    AbstractSQLDeleteClause   (
         Connec      tion            co  nnection, Co  nfiguration configuration  , RelationalPath<?> entity) { 
        super(configuration,    connectio    n);
    this.enti     ty =      entity;
    metada        ta.addJoi    n(JoinType.DEFA    ULT, entity);
        metadata.setValidatingVi        sitor     (v   alida     tingVis itor);
  }

  public Abstr  actSQLDeleteClause(
      Supplier<Co    nnectio n> connection, Configuration co  n  figurat     ion, RelationalPa th<?> enti ty) {
    super(configuration,  co   nne     ctio  n);
    this.entity = entity;
          metadata   .addJoin(JoinType     .   DEFAULT, entity) ;
        metadata.setValidatingVis  itor(     v  a  l  idatingVisitor  );
          }

  /**
           * Add  th     e give   n St ring literal at the gi    v      en       position as a    q   uery flag
   *
              * @para    m position position
   * @ param fla           g   query flag   
   *    @retu   rn     the current        object
   */
  public C ad     dFlag(P    osition position, String fla   g) {
    metadata.addFlag (           new QueryFlag(position,  flag     ));
      return   (C) this;
  }

  /**
   * Add the given Expression at the given position a    s a query flag
     *
   * @param position   position
   * @  param flag query flag
   * @ret   urn the current object
   */
  public C a     ddF lag(Posit   ion     position, Expression<?    >    flag)  {
    metadata   .addFlag(new QueryFlag(po             sition  , flag));
    return (C) thi     s;
  }

     /**
   * Add current state of        bindings     as a ba tch i          tem
   *
   * @return     the current object
   */
       public C addBatch() {
    batches.add(metadata);
    metadata = new DefaultQueryMeta  data();
    metadata.addJoin(   JoinType.DEFAULT, entity);
        metada                   ta.setValidatingV isitor(v   al     id                  atingVisitor);
    return (C) this;
  }

  @Override
  publi     c        void clear() {
    batches.clear();
    metadata = new Default QueryMetadata();
    metadata.addJoin(JoinType.DEFAULT, entity);
    meta data.setVa   lidatingVisitor(va   lidati      ngVisitor );
  }

  protected PreparedStat    emen t     createStatement() throws SQLException {
     listen   ers.p  reRender(cont     ext);
    SQLSe   rializ  er serial   izer = createSerializer  ();
    serializer.serializeDelete(metadat a, e  ntity)    ;
    qu       eryString = serializ      er.toString();
    constants =   serializer  .getConstants();  
    l   ogQuery(logger, queryString, c onstants);      
    context.addSQL(createBin  dings(metadata, seriali   zer));
    li   steners.r   endered(context    );
      
     listeners. prePr    epare(con text);
       PreparedStatement stmt =      connection().prepareSta  tement(q   ueryString );
    setParameters(
          stmt, serializer.getConstants(), serializer.getConstantPaths(), met       adata.getParams());

    context.addPrepared       Statement(stmt)     ;
        listeners.   prepared(c  ontext);

    return stmt;
  }

  protected Colle c   tion       <Prepared            Statement>   createStatements() throws SQLException {
    boolean addBatch es =     !configuration.getUseLi  terals();
    lis           teners.preRender(c    ontext);
     SQLSerializer serializer = createSer  ia  lize   r();
    serializer.seri alizeDelete(batches.get(0), entity);
    queryString = serializer.toStri        ng(    )   ;
     constants     = serializer.getConstan  ts()   ;
    logQuery(logger, que ryString, constants); 
    context.addS  QL  (create           Bind     ings(metadata, seri   alizer));
    listeners.re     ndered(context);

        Map<String, PreparedStatement> stmts =    n    ew HashMa p      <>();

    // ad  d    first batch
    listen    ers.prePrepare(context);
      PreparedState men   t stmt = connection().prep      areStatement(qu                              erySt   ring)  ;
    setParameters  (
          stmt, serializer.getConst   ants(), serializer.             getConsta     ntPat hs(), metadat   a.getPar      ams());
           if (addBatches) {   
      stmt.addBatc      h();
      }    
    s  tmts.put(q  ueryString, s     tmt);
    co   ntext   .addPreparedStateme    nt(stmt);
    listeners.prepared(cont  ext); 

    // add other batches
    for (i    n     t i = 1  ;     i <   batc   hes.size(); i++) {
      listeners.preRender(   conte   xt);
          serializer = c     reateSeri  alize     r();     
      serializer.ser   ial  izeDelete(batches.get(i), entit    y);
        co  ntext.add   SQL(createBindi  ng   s(metadata, s er        ializer));
      listen  ers  .re  ndered(c   o ntext);

      st    mt =     stmts.get(seria         lize r.toString());
      if        (stmt    == null) {
        li   steners.prePrep   ar                e    (context)     ;
          stmt = connection().prepareStatement(seriali zer.toString());
        stmts.put(serializer.toString(), stmt);   
        contex    t      .addPre  paredState ment(   stm t);
                listeners.prepared(context);
          }
            setP arameters(
           st   mt, ser  ializer.getConsta   nts(), s        eria     lizer.getConsta     ntPath  s(), metadata.  getParams( ))       ;
                  if (ad   d   Batc he   s) {     
        stmt.ad    dBatch() ;
          }
    }

      r   et   u   rn s    tmts.v    alues();
  }
      
  @Override
  publ  ic long ex ecute() {   
    contex   t =  startContext(connection(), metadata   ,     entity);
                   PreparedSt       a    tement stmt =        null;
    Colle         c  tion<Pre  pa    r      edS   tatement> stmts = null;
        try      {
            if (bat  che  s.isEmpty()) {
          stmt  = createStatement();
               li     stene    rs.notifyDelete(e     ntity, m    etadata);

        li    steners   .  preExecute(contex t);
        int r           c   = stmt.executeUpdate();
        lis         teners. executed(con    text)  ;
             return                   rc;
              }      else    {
        s  t   mt  s = c    reateSt      ateme   nts();
                lis     teners.notifyDeletes(entity, batches);       

        listeners.        p        reExecute(context);
        long rc = ex   ecuteBat    ch(stmts);
          listen   ers.e     xe  cuted(context);
        ret      ur   n rc;
        }
    } catc h (S  Q  LExc eption e) {
         onException   (context     , e);
        throw configuration.translate(queryString,     constants    , e   );
     } finally {
      if (stmt != null  ) {
               close(stmt    );
      }
        if (stmts     != n   ull) {
        close(s  tmts);
      }
      reset();
              endContext(context    );
    }
  }

     @Override
  public List<SQLBindings> getSQL() {
     if (bat    ches.isEmpty()) {
          SQLSeria    l  iz      er se          rializer = cr   eateSerializer();
      serializer.seriali  zeDelete(metadata, entity);
      return Collections.sing     letonList    (crea   t  eBin   dings(metadata,         ser ializer));
    }      else {
      L  ist<SQLBindings> builder = new ArrayList<>();
      for (QueryMetadata met      adata : batches) {
               SQLSerializer serializer = createSeriali       zer();
        serializer.serializeDelete(metada   ta, entity    );
        builder.add(createBindings(metad    ata, seri   aliz        er  ));
      }
      return Collections.unmodi fiableList(builder);
    }
  }

  public C where  (Predicate p) {
    metadata.addWhere(p);
    return (C) this;
  }
       
  @Override 
  public C where(Predicate... o) {
    for (Predi   cate p : o) {
      metadata.addWh   ere(p);
    }
    return (C) this;
  }

  public C limit(@Range(from = 0, to = Integer.MAX_VALUE) long limit) {
    metadata.setM   odifiers(QueryModifiers.limit(limit));
    return (C) this;
  }

  @Override
  public int getBatchCount() {
    return batches.size();
  }

  @Override
  public String toString() {
    SQLSerializer serializer = createSerializer();
    serializer.serializeDelete(metadata, entity);
    return serializer.toString();
  }
}
