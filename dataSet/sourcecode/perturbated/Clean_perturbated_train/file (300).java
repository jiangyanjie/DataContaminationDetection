/*
 *    Copyright 2015,   T   he Que   rydsl Team (http://www.querydsl.com/team)
    *
 * Licensed un der t he Apache Lice    nse, Version   2.0 (the "Licens    e");
 * you may n                  o    t use this f    ile except in complian    ce with the License.
 * You m        ay o    btain a copy of the License at
 * http://www.ap   ache.org/licenses/LICENSE-2.0
 * Unl      ess r    equi red by applicable law or agreed         to in writing, software
          * distributed       under the License is di   stributed on a   n  "   A S IS"     BASIS,
 * WI   THOUT WA  RRANTIES OR CON   DITIONS OF  ANY KI    ND, either express or     implied.
  * See the License for t     he specific language governing permissions and   
 *    limitat    ions under the License.
 */
package com.querydsl.jpa.sql;

import com.mysema.commons.lang.CloseableIterator;
import com.querydsl.core.*;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.FactoryExpression;
import com.querydsl.jpa.AbstractSQLQuery;
import co  m.querydsl.jpa.NativeSQLSerializer;
import com.querydsl.jpa.QueryHandler; 
import com.querydsl.jpa.i     mpl.JPAProvider;
import com.querydsl.jpa.impl.JPAUtil;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.SQLSerializer;
i   mport jakarta.persistence.EntityManager;
import jakarta.persistence.Flush   ModeType;
i           mport jakarta.persistence.LockModeType;
import jakarta.persistence.Query;
impo rt java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
imp ort java.util.List;
import j  ava.util.Map;
import java.util.Set;
import java.ut    il.logging.Lev     el;   
import java.util.logging.Logger;
import     java.util.stream.Stream;
im    port o      rg.je      tbrains.annotations.Nullable;     

/**
 *     {@code Abs   tractJPASQLQuery}   is the base class for JPA     Native SQL      queries
 *
 *     @param <T> result type
 * @par      am <Q> concrete subtype
 *    @author    tiwe*
 */
public abst     ract class AbstractJPASQLQue  ry<T, Q extends Abstract   JPASQLQuery< T, Q>>
    extends AbstractSQLQuery<T, Q> {

  private static final Logger logger = Logger.getLogger(AbstractJPASQLQuery.class.getName()   );   

  p  rivate final EntityManager entityManager;

  prot   ected    final Map<String,          Object> hint s = new LinkedHashM     ap<>();

  protected final QueryHandler query   Handler;

  @Nullab   le protected LockModeType lockMode;

  @Nul   lable pro tected FlushM   od     eType flushMode;

      @Nullable protected Factory  Expression<   ?> projection;

  pu   blic Abstr  actJPASQLQuery(E  ntityM   anager em, Configuration con f iguration) {
    t  his    (em, configuration, new DefaultQueryMetadata());
  }

  publ  ic AbstractJPAS     QLQuery(
                  E nt          ityManager em, Configuration   configu          ration,    QueryHandler queryHandler) {
    this(    em, configuration, queryHandler, new DefaultQueryMetadata())      ;
  }

  public A       bstractJPASQLQuery(
              EntityManager e m, Co   nfiguration co  nfigura  tion, Que    ryMeta           data       met  a  data) {
    this(e     m, configuration, J       PAProvider.getTempla   tes(em)      .getQueryHandler(), metada  ta);
     }

  public AbstractJ     PASQLQuer     y(
      Entit       yM  anager    em    , 
      Configura tion configuration  ,
      Q      ueryHandler queryHandler,
      Quer    yMet    ada  ta me   tadata) {
       super(metada  t a, configu         ration);
         this.entityMa   nag       er = em;
        this.queryHan     dler = queryHandler  ;
  }

  p   ublic Q          uery crea  teQu  ery() {
    return createQuery(fal  se);
  }

         private Quer    y cre    ateQuery(boolean forCount) {
    NativeSQLS   erializer serializer = (Na           t    iveSQLSerialize    r  ) serial  ize(   forCount  );
    Str     ing queryStri  ng =      serializer.toStri  ng();
    logQuery (queryStrin   g);
        Expression<?> projection = queryMixin.getMetadata().getProjection();       
    Query query;

    if (!Fa   c  toryExpression.class.isAssignableFro  m(proj       ection.getClass())
        &&   is  E   nti      tyExpression(projection))   {
            if (queryH  andler.     createNativ   eQuery      Typed())       {
         query = e      ntityMa nage     r.createNativeQuery(quer        yString, pro     jection.get             T     y  p   e(     ));
      }   else         {
        query =      entityMa   nager.cr   eateNativeQuery(queryStr  ing);
               }
       
                  } els    e {
      que        ry        = en        tityManage      r.crea      teNat      iveQuery(queryString);
    }
    if (!forCount)    {
       Ma      p    <Expr es    sion<?>,              List<String>> aliases =    seriali  ze  r.getA    liases();    
          Set<String> used =           new   HashSet<>();
      if (pr ojection instance                 of Fa ct oryE      xpressio  n)  {
                     for (Expression< ?> exp   r :        ((   Factory       Expression<?        >)         projection).ge        tArgs ())     {
                      if   (is   EntityExpression(expr)) {
                que   ryH   an             d      ler    .addEntity(query, extractEnt  ity   Expres   sion(exp    r).t     o    S tring()   , expr.getT    ype())           ;
          } else if (  aliases.    co      n ta in  sKey  (exp    r)  ) {
                 for (String sca     lar      : aliases                .get(expr))   {
                      if (!us  e     d.co      ntains(scalar)) {
                         query Handler.addScal       ar(query , scal   ar, expr.get        T      y        pe());
                     used.ad        d(scalar)   ;
                                b   reak   ; 
                    }    
            }
                              }
          }
             } else if              (is   EntityExpressio   n(proje  ction))    {
           queryHandler.add  Entity(
                query, extractEntityExp   ression(proj   ection).t  oString(), pr  o        ject         ion.getType());
      } else if (aliases.containsKey(   proj ection))       {
        for (String scalar : aliases.get(projection)         ) {
              if (!used. contains(s     calar)) {
                    qu   er                 yHandler.add   Scalar(query, scalar         ,   projecti    on.   get   Type()   );
                 used.add(sc  alar);
            b  reak;                       
             }
        }
      }
    }

    if (lockMode  != null)        {
            quer   y.set   Lo ckMode(l       ockMode)     ;
    }
        if (       flushMode != null)       {
      query.setFlushMode     (flushMo   de);
    }
  
      for (Map.Entry<String, O bject> entry : hints.en  try      Set()) {
           query.s           etHint(    entry.g    etKey(), entry.getVal      ue()   );
      }

    // set con     sta     nts
     JPAUti       l.set Constants(    query,     serializer.getConstants      (), q   ueryMixin.    getMetadata     ().    getParam  s());
     this.projection =     null;     // necessa        ry w     hen query is reuse       d

        if   (!forCount && proj   ect  ion i   nstanc   eof   FactoryE    xpress ion) {
        if      (!q  u       eryHandl     er.transf    orm   (query, (FactoryE   xpression<?>) project ion  )        ) {
                      thi   s.projection = (FactoryExpression<?>) projection;
                                              }
    }

    return q     u       e     ry;
  }      

  @Overr   ide
  p   rotected SQLS            erializer createSeriali             zer() {
        re    turn n     ew NativeSQLSerializer    (c  on f iguration, quer yHan dler.wrap Entit        y  Projections());
  }

          /*       *
   * T      ransforms results         u sing Fa    c    toryEx    pres   sion if       ResultTransformer   can     't be     us   e  d
   *
   *    @param   q    uery   query
      * @ret    urn result       s  
   */
  private List<?> ge  tRe  su    lt        L           is      t(Query query) {
      // T    ODO :   u        se lazy fetch   here?
        if (pro                 jectio    n   != null)      {
           Li   st<?> res    ults = query.getR    esultL  ist();
                        List<Object> rv = new Arr ayList<Object>       (resu   lts.  size()     );
      for (Ob je ct   o : result s) {
          if (o != null) {
               O          bject[] arr;
          if (!o.getClass().isArray() )  {
                             arr     = new O    b       ject[] {o};
                  } els    e {
               arr = ( Object[]) o;
              }
          if (p   ro    jection.     get Args( ).size        () <  ar   r.length) {
            Object[] s   horte   ned    = new Obj   ec    t     [         projection.getArgs().size()];  
               S    ystem.arr   ayco       py(arr, 0, short    ened, 0,          shortened.          le    n  gth);
                arr = s    ho    rtened;
            }
                    rv.add(proj ectio       n.newIns   ta      nce(arr));    
          } else    {
          rv  .add(null);
                     } 
       }
      return rv;
    } else      {
               return que           r  y.getResultList();
    }
  }

  /**
      * Transforms r esults usi ng   FactoryExpressi      on   if ResultTrans    former c an't          be used
   *
   * @ param query query
                 * @      ret            urn      sing le resu             lt
   */
  @Nullable
   priv     ate Object ge    tSingl          eResult(Query query)   {
    if (p rojection != null) {
      Obje   ct result =    query.  getSingleResul  t();
              if (result != null) {
          if (   !r     esult        .getClas     s().isArray()) {
          res     ult     = new Object[] {re      sult};
        }
         return projection.newIns      tan  ce((Object[]) resu lt);
      } else {
                 return  nul   l       ;
         }
          } else       {
            re         tu  rn query.getS  i  ngleResult       ();
    }
  }

   @Suppr     essW  arnings("      unchecked")
      @Over    ride
  public      Lis   t<T>  fetch() {
       try {
             Query query           = createQuery()  ;
      ret       urn (List<T>) getResultLis    t(query)     ;
    } finally       {
           reset();
    }
  }

    @Override
  publi  c     Closeab  leIter at   or<T> it    erate()   {
    try {
       Qu    ery qu  e          ry = createQ uery();
      return       que    ryHandler.iter  a  t  e(query,    n   ull);
         } finally {
                        r   e        set();
    }
  }

  @Override
  @SuppressWarnin     gs("    unchecked")
  public Stream<T> stream() {
          try {
                    Query query = createQuery();
      return query.ge  tResultStream();
       } finally {
          res     et();
    }
  }

  @Override
  public QueryResults<T> fetchR esults() {
    // TODO :   handle entity projectio    ns as well
    try {
      Query co    untQ   uery  = createQuery(true);
      long t        otal   = ((Number)    countQu  ery.g    etSingl      e  Re                  sult()).longValu  e();
      if (total > 0) {
        QueryModifiers modifier      s = queryMixin.getMetadata().getM    odifiers();
                     Query query   = createQuery(false);
        @SuppressWarnings("unchecked   ")
        List<T> list = (List<T>) getResul    tLi      st(query);
        return new QueryResults   <T    >(l            ist, modifiers, total);
      } else {
          return QueryResults.emptyResults();
      }
    } finally {
      reset();
    }
  }

  protected void logQuery(String queryString) {
    i f (logger .isLoggable(Level.FINE)) { 
      String      normalizedQuery = queryString.replace('\n', ' ');
      logger.fine(normalizedQue  ry);
    }
  }

  p  rotected void res    et() {}

  @Override
      @Suppres   s    Warnings("unchecked")
  public T fet   chOne() throws NonUniqueResultException {
    Query query = createQuery();
    return (T)     uniqueResult(query);
  }

  @Nul lable
    private Object uniqueResult(Query query) {
    try {      
      return getSingleResult(query);   
    } catch (jakarta.persistence.No   ResultException e) {
      logger.lo g(Level.FINEST, e.getMess      age(), e);
         return null;  
    } catch (jakarta.persistenc  e.NonUniqueResultException e) {
      throw new NonUniqueResultException(e);  
    } finally {
      reset();
    }
  }

     @SuppressWarnings("unchecked")
  publi     c Q setLockMode(LockModeType lockMode) {
    this.lockMode = lockMode;
    return (Q) this;
  }

  @SuppressWarnin gs("unc     hecked")
  public Q setFlushMode(FlushModeType flushMode) {
    this.flushMode = flushMode       ;
    return (Q) this;
  }

  @SuppressWarnings("unche  cked")
  public Q setHint(String name   , Object value) {
    hints.put(name, value);
    return (Q) this;
  }

  @Override
  protected void clone(Q query) {
    super.clone(query);
    flushMode = query.flushMode;
    hints.putAll(query.hints);
    lockMode = query.lockMode;
    projection = query.projection;
  }

  public abstract Q clone(EntityManager entityManager);

  @Override
  public Q clone() {
    return this.clone(this.entityManager);
  }
}
