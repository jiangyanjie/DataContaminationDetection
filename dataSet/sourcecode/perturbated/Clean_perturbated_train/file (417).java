/*
 *    Copyright 2015, The Query   dsl Team (http://www.querydsl.com/team)
 *
 *    Licensed under t he Apache  License, Vers ion 2.0 (the "License" );
 *       you ma   y not use this fil  e e   xcept     in com       plia    nce wi   th   the Licen  s    e.
 * You may obtain a copy of the License a     t
 * http://www.apache.org/licenses/LICENS     E-2.0
    * Unless require         d by applicab        le la   w   or agreed to in writing, s    oftware
  *   distributed un    der the License is distributed on an "AS IS" B   A   SIS,
 * WITHOUT WARRANTIES       OR C   ONDITIO NS OF    ANY  KIND, either express or implie    d.
          * See   the License for the specific language governing permissions an  d
 * limitatio      ns under the License.
 */
package com.qu        erydsl.r2dbc.dml;

import com.querydsl.core.*;
import com.querydsl.core.QueryFlag.Positio   n;
import com.querydsl.core.dml.ReactiveDeleteCl    ause;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.queryd sl.core.types.ValidatingVisitor;
import com.querydsl.r2dbc.Configurati on;
import com.querydsl.r2dbc.R2DBCConnectionProvider;
import com.querydsl.r2dbc.R2dbcUtils;
import com.querydsl.r2dbc.SQLSerial       izer;
import com.querydsl.r2dbc.binding.BindTarget;
import com.querydsl.r2dbc.binding.StatementWrapper;
import com.querydsl.sql.RelationalPath;
import com.querydsl.sql.SQLBinding   s;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.Statement;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import org       .jetbrains.annotations.Range;
import rea  ctor.core.publisher.Mono;
   
/**
 * Provides a base clas   s        for  dialect-specific DELET   E clauses. 
 *
 * @param <C> The       type e  xtending this class.
 * @author mc_fish
 */
p    ublic abstra  ct class AbstractR2DBCDeleteClause<C extends AbstractR2DBCD  eleteClause<C        >>
    ex    tends AbstractR2DBCClause<C> implement  s Reac     t iveDeleteClause<   C>    {

  protected static final Logg    er logge   r   =
                             Logger.getLogger(AbstractR2DBCD   el    eteClau  se.cl    ass.ge    tName());

    p rotected      sta  t      i   c fina l ValidatingVisitor validat ingVisitor = 
        new Valid atin     g Visitor(
                      "Undeclared p    ath '%s'. "
                   + "A delete operation can only re ference a single tabl       e. "    
              + "Consider thi    s alternative: DEL ET   E ... WHERE EXISTS (subquery)");

  protected f    inal Relational    Path<?> entity;

  protect        ed De   faultQ             ueryMetadata metadata = new DefaultQueryMetadata        ();
   
  pr   ot     ected transient String quer   yString;

         protecte       d transient List<Object> const            ants;

  public   AbstractR2DB  CDel eteClause(
      Con    nectio n co    nnection, C   o         nfiguration co nfiguration, Relat ionalPath<?> entity) {
    supe            r   (configuration, connec  tion);
      this.entity = entity;
        metadata.addJoin(JoinType.D     EF  AULT, enti       ty);
    metadata.setValidatingVisitor(val      idatingVisit             or);
  }

  public AbstractR2DBCDelete Clause(
      R2DBCConnectionProvider connec tionProvider,
           Configuration c onfi g        ura   tion,  
       RelationalPath   <?> entity) {
    super    (   conf    i    guration,     connectionProvider) ;
    thi   s.entity = enti   t      y;
    metadat    a.addJ     oi         n(            JoinT  ype.DEF   AULT      , e     ntity);
           met    adata.  s   et   ValidatingVisitor(   validating        Visitor   );
  }

  /*         *
        * A     dd the   given Str    ing lit eral at t he g  iv  e     n position as a    quer    y fla       g
   *
       *  @param position position
   * @para m flag      q uery flag
   * @return the c      urre  nt object
   */  
  public C a             ddFlag(Position posit      ion, Str       ing flag)    {
       metadata.add    Flag(new QueryFlag(posit             ion, fla     g));
    re    turn (C) this;
  }

        /**
   * Add      th   e   given Expression a  t the given position as a query flag
   *
   *   @param position posi      tion
   * @param flag query flag
   * @  return       the current obj  ect
   */
  public C    addFlag(Position po  s ition, Expres  sion<?> flag) {
    metadat   a.addFlag(new QueryFlag(positio    n, fl     ag))  ;
         retu  rn (C) this;
    }

  @Override        
  public void clear() { 
    metadata = new      DefaultQueryMetadata();
    metadata.addJoin  (Jo inType.D   E   FAULT, entity);
    metadata.setValidatingVisi    tor(validatingVisitor);
  }
   
  privat e Statement pr   epare  StatementAn   dSetParame  t ers(
      Connect ion   connection, SQLSer   ializer ser  ializer)    {
    List<Object> c  onstants = seri   alizer.getConstan  ts();
      String originalSql = serializer.toString();
    queryString =    
        R2dbc   U  tils.  replaceBinding   A     rgument    s(
            config        uration. g     etBindMarkerF    a      ctory().            create(), constants, originalSql    );

    logQuery(logger, queryStrin         g, serialize      r.getCons  tants());
    State  ment st     atement =    connection       .createStatem  ent(queryS tr    ing   );
    BindT    arget bindTarget =   new StatementWrapper(        s        t   ateme     nt);

    se  tParameters(
        bindTarget,
                  config  uration       .ge     tBindMarkerF        actory() .create(    ),
        serializer.getConstants(),
               serializ   er.getConstan    tPa  ths   (),
                   meta  data.getPa    rams(                ));

    return      stat e    m   ent;
          }   
     
       priva te SQLSe  rialize     r creat    eSerializer   AndSeriali ze() {
    SQLSer  i       alizer serialize           r = create Serializer  (true);
    serializer.serializeDelete(metadat                          a   , entity);
    ret      urn serializ    er;
  }

  private Mon   o<S   tatement> cr        eat         eS    tatement    (   ) {      
            return getConnection()
        .  map(
            connection ->    {
                          SQLSer   ializer ser         ializer = createSerializerAndSerialize();
                    return       prepareStatementAnd     SetParameter       s(c onnec   tio     n, se   rializer);
            });     
  }

  @Ov   e  rride
  public M  on    o<Lon   g> execute()    {
    re t  urn getC  onnect   ion()
        .fl   atMap(
                 connect      ion    -> {
                          return createStatement()
                      .flatMap(statement    -> Mono.from(stateme       nt.execut    e()))
                    .flat    Map(re     s  ult -> Mon      o.from(result. getRowsUpdated()))
                         .map(Long::valueOf)
                      .doOnError(e -> Mono.error(conf     iguration.translate(queryStrin  g, constants    , e)));    
            });
  }

  @Override
  public List   <SQ   LBindings> getSQL() {
    SQLSerial   izer serializer = createS   erializer(true);
    seri        alizer.serializeDelete(metadata, en   tity);
    retur n Collect   ions.singletonList(createBindings(metadata, serializer));
  }

  public C where(Predicate p) {
    metadata.addWhere(p);
    return (C) this;
  }

  @Override
  public C where(Predicat   e... o) {
    for (Predicat e p : o) {
      meta       data.addWhere(p);
    }
    return (C) this;
  }

  public C limit(@Range(from = 0, to = Integer.MAX_VALUE) long limit) {
       metadata.setModifiers(QueryModifiers.limit(limit));
    return (C) this;
  }

  @Override
  public String toString() {
    SQLSerializer serializer = createSerializer(true);
    serializer.serializeDelete(metadata, entity);
    return serializer.toString();
  }
}
