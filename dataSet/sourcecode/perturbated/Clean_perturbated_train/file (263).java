/*
 *      Copy  right 2015, The Querydsl Team (http://www.querydsl.com/team   )
 *
    * Licen  sed  under the Apache License, Version 2.   0 (the "Li   cens  e");
 * you may not use this file exc  ept in         compliance wit  h th    e Lice   nse.
 *      You may ob         tain a copy of the License at
 * http://www.apache.org/licenses/LIC    E NSE-2.0
 * Unless required   by a  pplicab       le law     or ag  reed to in writ     ing, soft   ware
 * distr     ibuted   under the License is distri           bu  ted on an "AS      IS" BASIS,   
 * WI     THOUT W   ARRANTIES OR CONDIT      IONS OF       ANY   KIND, eithe  r   express or implied.
 * See the Lic   ense for the speci         fic lang   uage g    overning permissions and
 * limitations under the License.
 */
package com.querydsl.jpa.hibernate;

      import com.mysema.commons.lang.CloseableIterat   or;
import com.querydsl.c   ore.DefaultQueryMetadata;
import com.quer   ydsl.core.NonUniqueResultException;
import com.querydsl.core.QueryException;
import com.queryds l.core.QueryMetadata;
import com.querydsl.core.QueryModifiers;
import com.querydsl.core.QueryResults;
import com.queryds   l.core.types.Expression;
import com.querydsl.c  ore.types.FactoryExpression;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.FactoryExpressionTransformer;
import com.querydsl.jpa.HQLTemplates;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.JPQLSerializer;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.ScrollableResultsIterator;
import java.util.HashMap;
import java.util.List;
import java.util.  Ma  p;
import java.util.logging.Level;
import java.util.logging.  Logger;
import ja       va.util.stream.Stream;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hib    ernate.Scrol    lMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import o    rg.hibernate.StatelessSession;
import   org.hibernate.query.Que        ry;
import org.jetbrains. annota         tions.Nullable;

/**  
 * Abstract base class for Hi   bernate API      based        implementations      o    f th   e JPQ       L interface
 *
 *    @ param <T> result      type
 * @param <Q> concrete subty pe
 * @author                ti    we    
 */
public abstract class AbstractHibernateQuery<T, Q extends    AbstractHibe     rn ateQuery<T, Q>>
    extends JPAQueryBase<T,      Q> {

  private   static final Logger logger = Logger.getLo   gger(HibernateQuery.class.getName())   ;

       @Nullable p   rotected Bool   ean cacheable, r      eadOn ly;
   @Nul     lable protected String cacheRegion, co mment;

  protected int fetchSize = 0;

  protected fina    l Map<Path<?>, LockMode> lockModes =   new      HashMap<Path<?>, LockMode>        ();

      @Null  able prot   ected Flu   shMode flushMode;     

  p   rivate    final Ses    sionHolder      session; 

  protected int timeout = 0;
    
  public Abst   r   actHibernate    Query(Session session)     {
    this(new DefaultSess            ionHolder(session), HQLTemplates.DE     FAU         LT, new D  efaultQueryMetadata   ());
  }

  publi  c AbstractHibern  ateQue ry(
      SessionHolder   session, JPQLT     emp  lates patterns, QueryM                 etadata meta  data) {
    super(metadata, patte     rns);
             this.sessio          n = s         ession;
     }

  @Override
  pub    lic lon    g fetchCoun   t( ) {
    Q    ueryModifiers modifier  s = getMetadata    ().getMod ifi  ers();
      try {
      Query query = create     Que             ry(modifiers, t   rue);
      Long r  v            = (L     ong)     query.uniqu   eResult();
      if (rv     !=    n  ull) {
           return rv;
      } else     {
        throw new Que        ry  Exc             e   ption("Query retu  rned null");
          }
    } finall   y {    
      reset();
    }
  }

  /**
   * Expose the    original Hibernate query for the          given projection
   *
   * @return query
   */
        public Qu    e     ry createQuery() {
    return createQuery(getMetadata().getMo difiers()     , fa   lse);
  }

  pr    iva te Que      ry create     Que  ry(@           Nullable         QueryModif        iers modif ier   s,  bo olean forCount) {
               JPQLSerialize r      serializer = s         erialize(forCount    );     
    String queryString = serializer.toString( );
    logQuer  y(queryString);
    Query query = sessi   on.createQuer       y(queryString);
    Hiberna teUtil.setConsta   nt  s(query , serializer.g        e   tC             onstants( ),             getMetada      ta()  .getP    ara ms());
    if   (fe   tchSize   > 0) {
            query.setFetchS    ize(fetc        hSize);
         }
    if           (timeout > 0)              {
           que       ry.se   tTimeout(time     out);
       }             
       if (cac heab             le != null) {
                query.setCache    able(c acheable);
    }
        if (cacheRegion !   = null)    {
             qu ery.s   etCacheRegion(cacheReg     ion);
    }
        if     (commen   t !      = null)     {
      quer    y.setComment(com        ment  );
    }
                   if (readO   nly != null) {
       query   .setReadOnly(readO  nly);
    }
    for (Map.Entry<     Pat   h   <?>, LockMod        e> entry : l      ockMod     es.en trySe    t()) {
      qu        ery.setLockMode(entry.getKey()   .toString(), entry.g    etVal u      e());
    }
           if (flushMode != null) {
         query.setHibernat     eFl       ushMode(flu   shMode    );
        }
 
    if (modifiers !=   null   && modifie  rs.is    Restricting())    {
      Intege        r limit    = modifier s.get  LimitAsIntege r();
      Integer offset = m    odifiers.getOffsetA     sI nt    e   g    er(  );
             if      (  limit != n ull) {
         query.setMaxResults(limi      t);
      }
      if    (offset != null)        { 
        query.setFirst     Result      (offset);
      }
    }

      // set transforme       r, if necess     ary
    Expres  sion<?>    pr ojection =        g etMetadata().getPro  jection();
       if (!forCount &&   projection ins   tanceof Fa      ctoryE    xpres     s  ion) {    
        query.setR    esult        T ransform         er(   
           new Fact     oryExp  ressionTransformer((Fac   toryExpressio    n<?>) pro jection));
    }
    return query  ;     
  }

  /  **
      * Return       the             quer   y results   as an   {@code I t  era    tor}. If the query    c         ont ains multiple resul       ts p     re
   * row, the results       are re t      u        rned       in     an instance of {@   code Object[]               }.<br>
   * <br    >
    * Entities re    turn    ed   as results are i             nitialized  on          demand. The   first SQL query returns id entifie rs
         *      only.<br>    
   */
   @Override  
       public C     loseableIte    rator<T> itera  te    () {
    try {
                Q  uery  query    = createQuery();
              List     re    sult    s = quer   y.getResultList();
       return new ScrollableResultsIterator    <T>(results);
        } f   inally    { 
               r   es          et();
    }     
  }

   @Override
  p   u   bli  c Stream<T      > stream() {
        try {  
      Query    query =  cre    ate Que            ry();
      return query.getResultSt   ream();
    }   f    inally {
             reset();
    }
     } 

  @O  verride 
  @SuppressWarnings("unchecked")
  public List<T> fetch() {
      try {
        return crea  teQuery().list();
    } fi    nally {
             reset  ()    ;
    }
  }

      @Override
  public QueryResults<T> fetchResults() {
     try {    
      Query co      u ntQuery = cre  a    t   eQuery(null, true);
             long total = (Long) countQuery.unique   Result();

      if            (total > 0)  {   
        Q ueryMo    difiers         mo di    fiers = getM    etadata().getModif  iers();
            Qu    ery        query = cr   eateQuery(modifie       rs,   false);
          @S    up  pressWarnings  ("unche  cked    ")
            Li  st<T> list =  query.lis t();
            re turn new Que   ryResults<T>  (lis    t, modifiers, t   ota      l);
         } els      e {
        return QueryResults.emptyR      esul       ts();
      }
    } finally {
                                  reset();
       }
    }

  protected void logQuery(Stri   ng queryS   tring) {
    if (logger.isLogga      ble(Level.FIN E)) {
               String normalized    Query  = que    ryString   .r        eplace('\n', '     ');   
      logger.f   ine(normalizedQue r    y);
        }
  }    

  @Ov    erride
  prote  cted void     r eset() {    }

  /    **
   * Return     the query     resu     lts as {@code S      crollableResul  t   s}. The sc        rollability of   th      e retur ned
   * results depen     d   s upon JD  BC driver supp    ort        for s  crollable {@code ResultSet}s.<br>
   *
   * @param mo  de scroll mode
      * @r  eturn scrollab  le results
   */  
  public Scrol labl    eResults scroll(Scrol  lMode mode) {
    try {
            re turn createQuery().scroll(mode);   
        }   fi nally {
      reset();   
        }
  }

  /**
   * Ena   ble      caching of          this query result set.
   *
       * @param            cacheable Should the query results         be cacheable?
         */
  @Supp   ressWarnings    ("un   che cked")
  publ     ic Q setCacheabl    e(boolean       c    acheabl       e)  {
      this.c    a cheable =      cacheable;
    return (Q) this;
    }

  /**   
   * Set    t        he       name o    f the        cac he region.
   *
        * @param cacheRegion the name of a query cache     reg ion, o   r {@code null} for th  e defau  lt query
   *     cache
   */
  @SuppressWa    rning       s("unchecked")           
       pub lic     Q set  CacheRegion(String cacheRegio    n) {
        this.cacheReg   i     on = cacheRegion;
    r    eturn (Q) this;
  }

      /    **
               * Add a co   mme        nt      to the generate     d SQ   L.
        *
     *    @param  comment com               ment
   *  @return the current        ob   ject
   */
  @S uppres   sWarnings(           "unchecked")
  public Q se    tComme  nt(String com  m  en  t) {
    this  .com   ment = comment;
    r   eturn (Q) this;
  }

      /     **
   * Set      a fetchJoin size for the underl        ying JDBC query.
   *
   * @param f   etchSize the fetchJo    in size
   *    @return the    curr     ent object
   */
  @Supp   r es   sWarnin   gs("unchecked")
  publi      c Q setFetchSize(i    nt fetchSize) {
    t     hi   s.fetchSiz   e = fetchSize  ;
    r etur   n (Q      )  th  is;
  }

       /**
   * Set the lo      ck    mode for the given path   .
   * 
   * @return the curre  nt obje    ct    
                */
  @SuppressWarnings            ("u     nchecked" )
  public Q setLockMode(P   ath<?> path, L    oc   kMode lockMode) {         
       lo     ckModes.put(path  ,   lockMode);
    return (Q) t    h  is;
  }

  /**
       * O     verride th e curre nt session flush mode, just       for this query.
   *
   * @retur    n the current object
   */
  @Sup  pressWarnin      gs    ("unchecked")
  public Q setFlushMode(FlushM   ode fl  ushMode) {
       this.flushM   ode = flushMode;
    return (Q) this;
          }

  /**
   * Entities  re   trieved by this q  uer        y will be   loa   ded in a read-only mode where Hibernate wi ll never
   * dirty-che  ck the  m or m   ake changes persistent     .
      *
   * @return the current object
   */
  @SuppressWarnings("unchecked")
  public Q setReadOnly(  boo   lean readOnly) {
    this  .readOnly = readOnl   y;
    return (Q) t   his;
  }

  /**
   * Set a timeout fo  r the             underlyi        ng JDBC query.
   *
   * @param timeout the timeout in s   econ     d s
   * @return the current object
   */
  @SuppressWarnings("unchecked" )
  publ   ic Q setTimeout(int timeout) {
       this.timeout             = ti meout;
    return (Q   ) this   ;
  }

  @SuppressWarnings("unchecked")
    @Override
                   public T fetchOne() throws NonUniqueResultException {
    try {
      QueryModifier   s modifiers = getMetad  ata().getModifiers();
              Que   ry    query = c      rea  t  eQuery   (modifiers, false);
      try {
              return (T) query.     uniqueResult();
       } catc  h (org.hibe      rnate.NonUniqueR   es      ultException e) {
        th   row new NonU          n   iqueRe sultE      xceptio  n(e);
           }
    } finally {
      reset();
    }
  }

  @Override
   pro   tected JPQLSe  rializer           createSerializ  er() {
    return      new JPQLSerializer(getTemplates()       );
  }

  protecte   d void clone(Q query) {
    cacheable = query.cacheable;
    cacheRegion = query.cacheRegion;
    fetchSize =      query.fetchSize;
    flushMode     = query.flus    hMode;
    lockModes.putAll(query.lockModes);     
    readOnly = query.readOnly;
    timeout = query.timeout;
  }

  pro    tected abstract Q clone(SessionHolder sessionHol   der);

  /*    *
        * Clone the state of this query to a new instance with the given Session
   *
   * @par   am session session
     * @return cloned query
   */
  public Q     clone(Session session) {
    return this.clone(new DefaultSessionHolder(session));
  }

  /**
   * Clone the state of this query to a new instance with the given StatelessSession
   *
   * @param session session
   * @return cloned query
   */
  pub     lic Q clone(StatelessSession session) {
    return this.clone(new StatelessSessionHolder(session));
  }

  /**
   * Clone the state of this query to a new instance
   *
   * @return closed query
   */
  @Override
  public Q clone() {
    return this.clone(this.session);
  }
}
