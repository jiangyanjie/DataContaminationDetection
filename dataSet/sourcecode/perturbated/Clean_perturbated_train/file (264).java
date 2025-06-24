/*
         * Copy   right 2015, The   Querydsl       Team (http://www.que rydsl.com/team)
       *
 * Licensed under     the Apache Licen    s e, Versio n 2.0 (the "License");
      * you may n    ot use this fil e except in compliance with t    he Lice   nse.
      * You may obtain a copy    of the Li       cense at
 * http://www.apache.org/lic    enses/LI   CENSE-2 .0
 * Unless      required by applicabl    e l    aw or agreed t      o in writin  g,    software
   * distributed under th   e Li    cense is   distributed on an "AS IS" BASIS,
 * WI    THOUT WARRANTIES OR CONDITI  ONS OF A  NY KIND, either e  xpress or implied.
 * See the Licens     e for the specific language gove  rni     ng permissions and
 * limitations under the License.
 */
package com.que  rydsl.jpa.hibernate.sql;

import com.mysema.commons.lang.C loseableIterator;
import com.querydsl.core.DefaultQueryMetadata;
import com.querydsl.core.NonUniqueResultException;
import com.que    rydsl.core.QueryMetadata;
import com.querydsl.core    .QueryModifiers;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.F   actoryExpression;
import com.querydsl.jpa.AbstractSQLQuery;
import com.querydsl.jpa.FactoryExpressionTransformer;
import com.queryd sl.jpa.NativeSQLSerializer;
import com.querydsl.jpa.ScrollableResultsIterator;
import com.querydsl.jpa.hibernate.DefaultSessionHolder;
import com.querydsl.jpa.hibernate.HibernateUtil;
import com.quer   ydsl.jpa.hibernate.SessionHolder;
import com.querydsl.jpa.hibernate.StatelessSessionHolder;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.SQLSerializer;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import ja va.util.stream.Stream;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import    org. hibernate.query.Query;
import org.jetbrain  s.annot ations.Nullable;

/**
 * {@code AbstractHibernateSQ  LQue      ry} is the ba     se clas   s for Hibernate Native SQL queries
 *
 * @param <T> result type
 * @param <Q> concrete subtype
 * @author tiwe
 */
public abstract class Abs tractHibernateSQLQuery   <T , Q extends  AbstractHib   ernateSQLQuery<T,      Q>>
    ext      ends AbstractSQLQ  uery<T, Q> {

  privat    e sta  tic final Logger logger = Logger.getLogger(AbstractHibernateSQLQu          ery.class.get   Name());

    protected Boolean cacheable, readOnly;

  p     rote cted String cacheRegion;

  protected in t fetchSize     = 0;

  private final SessionHolder session;

  protected int      timeout =       0;

  public AbstractHiberna      teSQLQuery(Session se ssion, Configuration c    onf) {
    th  is(new DefaultSessionHolder(session), conf,   new DefaultQueryMetadat   a());
       }

  public AbstractHibernateSQLQuery(StatelessSession session, Configurat   ion conf) {
    this(new St      atelessSes     sionHolder(session), c   onf, new D    efaultQueryMetadata(         ));
  }
    
  public Abstra       ctHibernateSQLQuery         (
            Sessi   onHol    der session, Configuration conf, Qu  eryMetadat  a       met   adata) {
         super  (metadata, conf  );  
    this  .ses     sion = session;
    }

  public     Query createQuer      y() {
          return createQu     ery(false);
  }

  priva     te Query    c    reateQuery(boolean forCount) {
    NativeSQ  LSerializ  er serializer = (NativeSQLSerializer) serialize(forCo  unt);
    String queryString = serializer.to  String();
     logQuery   (      que      ryString);
    org.hibernate.query.Nativ eQuery que       ry = session.createSQLQuery(queryString);
    /    / set constants
               Hibern     ateUtil.setConstants(      
        quer   y, seri alizer.getCo     nstants(), quer yMixin.g   etMetadata()  .getParams   (  ));

     if (!forCoun    t) {
      Map<Expression<?>  , List<String>> a       li    ases = serializer.getAliases ();
      Set<Stri     ng> u  sed    =     new Has            hSet<>();
                   // set e    ntity paths
      E  x    pression<?>    projec       t     ion  = que   ryMi         xin.getM   etadata().getProjection();
               if (p    r ojection inst             anc   e         of    Factory  Expr  ess  ion   ) {
        for (Express     ion<?> expr :        ((          Facto ryE    xpression<?  >) pr    oj             ectio     n ).getArgs  ()) {
                             i    f (isEn    tity  Expression(exp      r)) {
             qu   ery.ad          dEntity(extractEnt  ityEx   pre ssi                     o    n(expr).t    oSt       r  ing(),             expr.ge  tT     ype   ());
           } el se   if   (alias   es.con   tainsKey(expr))         {
                      for (Str        ing      scalar : ali          ases   .get(expr))               {
                              if (!used.contains      (scal    ar)) {
                     query   .addScalar(sca  lar);
                  used.a     dd(        scalar);
                            break;
              }
               }
          }
            }
         } else if (   isEntityE      xpression(projec    tion))      {
        query.a  ddE   n tity(       e  xtr         actEntityExp   ression              (project  ion).toString(   ), projection.getType());
           } else       if (aliases.co        ntains     Key(project     ion)) {
           for (String scalar :   al iases.g       et(p  r  ojection)) {
              if   (!used.co  n            tai ns          (scalar)) {
                       que    ry.addScalar      (sca      lar  );
                used.a  dd(scalar);
                          break;
            }
               }
          }
   
        // set   re        sult transfo      rmer, if p    roje ction   is a FactoryExpression instance
         if (projection     in   stanceof Fact    or  yExp     ression) { 
        query.set  ResultTr  a      nsfo     rmer(
                      new FactoryExpressionTra   nsformer((Facto             ryE   xp      re      ssi       o  n<?>) proje    ction));
      }
     }

           if (fet   chSize >    0) {
      qu      ery.setFetchSize(fetchSize);
    }
    if (timeout       > 0) {
            query.setT       imeout(time  out);    
    }
    if (cach     eab   le !=       null    ) {
                   query.setCacheable(cac    heable);
           }
    if (cache R   e gion        != null) { 
            query.setCach          eRe           g ion(c  acheRegion);
    }
    if (read   O nly !=  null) {
             query.setReadO   nly    (read O          n ly    );
    }
    ret  u     rn query;   
  }
   
  @Over     ride
  protected SQLSerializer c  r  eate    Seri   ali   zer()    {
              return new      Nativ  eSQLSeri     a  lizer(co     nfiguration           , true);
       }

  @Su    ppressWarnings("unchecked")
  @Override
  pu  bli c         List<T> fetc      h() {
    try {
       retu  rn createQuery().list();
     }   finally {
        reset();
    }
  }

  @Override
  public CloseableIterator<T>        iterate()    {
    try {
      Query query = createQuery();
      r  eturn new     Scro   llableResultsIterator<T>      (   que      ry.getRe     sultList());
         } finally {
        reset();
    }
     }

    @Overrid      e
  p   ubl   ic Stream<T> stream(  ) {
    try {   
      Q  uery query = createQue  r        y();
        return q      uery.getResultStream();
    } fin ally    {
             rese t();
      }
  }

  @Override
  public QueryResults<T> fetchRe        sult     s() {
    // TODO   : handle entity project i    o             ns   a   s     well   
             try {
      Query query = creat     e  Qu  e   r   y(tr  ue);
             long tot     al = ((Nu    mber)  query.uniqueResult()).lo   ng    Val              ue();
        if (t        ot     al >   0) {
        Q    ueryModifiers modifiers = queryMixin.getMetadata().getMo  difie    rs();
        query =     createQuery(false);
          @Suppres    sWarning s("un      checked")
             List<T > li   st = query.     li   st();
        return new Query            Results<T>(list, mod   ifiers, total);
       } else {
        retur  n QueryResults.emptyResul   ts    (   );
      }
    }    finally  {
                      reset()             ;
    }
  }

  p      rotected void logQue    ry   (String qu    eryString) {
    if (logger.isLogga    ble(Level.FIN       E)) {
      S   tring norma  lizedQu  ery = querySt         ring.replace('\n ', ' '         );       
      l ogger.fi  n  e    (normalizedQuer     y);
    }
  }

  p       rotected void r          es  et()      {}

  @SuppressW arning   s("unche   cked")
     @Ov    erride
  public T fetch   One() throws N      onUniqueResultException {
    tr  y {
      Quer y query    = createQuer   y();
       return (T) u    niqueResult (query);
    }  finally {
      reset();
    }
  }

  @Nu      llable
  pri  vate Obj    ect uniqueResult(Query query)   {
          try      {
              return query.   uniqueR  esu       lt();   
    } ca  tch    (org.hibern  ate.  NonUniqueResultExc     eptio   n         e) {
      throw n  ew NonUniqueResultException(e);
    }
  }

  /**
              * Enable cach     i  ng of this query result     set.
   *
        * @param     cacheable Should the query res   ults be cacheable   ?
   */
  @SuppressWarnings("unche            cked   ")
        public Q setCach     e      a  ble(bool ea      n ca    cheable) {    
         thi         s.cacheable = cacheabl  e;
      return (Q ) this;
  }

     /** 
   * Set the n   ame   of the ca       che re    g       ion.
          *
   * @p    aram cacheRegion the n   ame of a q   uery cache region   , or  {@             code nu  ll} for the default query
         *     cache  
   */
      @SuppressWarni  ngs("unchecked")
  pub  lic Q setCacheRegion(String cacheReg    ion) {
    t his   .cacheReg   ion =   cacheRe   gion ;
       retur    n (Q) this;
  }

  /*    *
   * Set a       fetc   hJoin size for             the underlyi  ng J  DBC query.
   *
      * @param fetchSize     the fetc  hJoin size
   */
  @SuppressWarnings("un checked")
  pub  lic Q  setFetchSize(int fetchSize) {
     this.fet       chSize = fetchSize;
    return (Q) this;
  }

  /**
        * Entities retrieved by this query will     be loaded in a read-only mode wh   ere Hibernate will never
   *    dirty-check them or make changes persi   stent.
   */
  @SuppressWarnings("unchecked")
  public Q setReadOnly(boolean   readOnly) {
    this.readOnly = readOnly;
        ret      urn (Q) this;
  }

  /**
   * Set a timeout for the underlying JDBC query.       
   *
   * @param tim  eou t the timeout  in seconds
   */
  @SuppressWarnings(  "unchecked")
  public Q setTi  meout(in   t timeout) {
    th     is.timeout = timeout;
    return (Q) this;
     }

  @O verride
  protect      ed void cl  one(Q query) {
     super.clone(query);
    cacheable = qu  ery.cacheable;
    cacheRegion = query.cacheRegion;
    fetchSize = query.fetchSize;
            readOnly = query.readOnly;
    timeout = query.timeout;
  }

  protected abs    tract Q clone(SessionHolder session);

  public Q clone(Session session) {
    return this.clone(new DefaultSessionHolder(session));
  }

  public Q clone(StatelessSession statelessSession) {
    return this.clone(new StatelessSessionHolder(statelessSession));
  }

  @Override
  public Q clone() {
    return this.clone(this.session);
  }
}
