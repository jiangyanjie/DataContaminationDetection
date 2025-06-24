/*
      * Copyright         2015, The   Queryd sl Team (http://www.querydsl.com/team)
 *  
 * Lic    ensed under  the Ap         ac           he License, Version  2.0     (the "License");  
   * you ma     y   not use       this file except in compliance with the License.   
 * You may obtain a copy of the Li    cense at
 * http://www.apache.org/li    censes/LICENSE-2.0
 *         Unless required by a  pplicable law       or  agreed to in w  riting, software
 * dist   ributed under th     e Lice nse is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES       OR CONDIT    IONS OF AN   Y KIND    , either express or implied.
 * See the License for the specific language governin   g per   missions and
 * limitations under the License.
 */
p ackage com.querydsl.lucene5;

import com.mysema       .commons.lang.CloseableIterator;
import com.mysema.commons.lang.EmptyCloseableIterator;
import com.mysema.commons.lang.IteratorAdapter;
import com.querydsl.core.DefaultQueryMetadata;
import com.querydsl.core.Fetchable;
import com.querydsl.core.NonUniqueResu    ltException;
import com.querydsl.core.QueryException;
import com.querydsl.core.QueryMeta  data;
import com.querydsl.core.QueryModifiers;
import com.qu   erydsl.core.QueryResults;
impor    t com.querydsl.core.SimpleQuery;
import com.querydsl.core.support.Q  ueryMixin;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.co  re.types.ParamExp     ression;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predi   cate;
import java.io.IOException;
imp  or       t java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
     import java.util.List;
impor    t java.util.Set;
import java.util.function.Func  tion;
import org.apache.lucen   e.document.Document;
import org.apac  he.lucene.sandbox.queries.DuplicateFilter;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.s     earch.Filter;
import org.apache.lucene.search.IndexSearcher;
import o       rg.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene .search.QueryWrapperFi  lter;
import org.apache.lucene.    search.ScoreDoc;
import or  g.apache .lucene.search.Sort;
import org.apache.lu  cene.search.TotalHitCountCollecto r;
import    org.jetbr     a  in s.annotations.Nullable;

/**
 * Abs  tractLuceneQuery is an abstract s     uper class for    Lucene qu   ery   implementations
 *
          * @author ti  we
 * @param     <    T> projection type
       * @param <Q>  concrete sub   type of querydsl
 */
public abstract class AbstractLuceneQuery<T, Q extends AbstractLuce    neQuery<T, Q>>
    implements SimpleQuery<Q>, Fe tchable  <T> {

  private static final   String JAVA_ISO_CONTROL = " [\  \p{Cntrl}&&[^\r\n   \t]]  ";

  privat   e final QueryMixin<Q> queryMixi   n;

  pr   ivate final IndexSearcher searcher;

  private      final LuceneSeria    lizer serializer;

  private final Function     <Docume   nt, T>    transformer ;

  @Nullable priv    ate Se        t<String> f ieldsToLoad;

  pri   v      ate List<Filter> filters = Collecti   ons.emptyList();

  @Nullable private Filter filter;

  @Nullable private Sor                   t q      uerySort;

  @Suppre ssWa  rnings("   unchecked"    )
  public AbstractLuceneQuer y(
      Luce       neSerializer serializ er, Index   Searc    her search     er, Function<Do     cument,  T> transformer) {
    queryMixin   =    new   QueryMixin<  Q>((Q) this, new DefaultQu eryMetad       ata()    );
       this.serial   izer     = ser            ializer;
    this.searc her = searcher;
    this.transfo rmer =  transformer;   
  }

      public  AbstractLuceneQuery(         Index    S    ea    rcher  searcher  , Fu    nction<D   oc         ument, T> transf    ormer) {
      this(      LuceneSerializ er.DEFAULT, search er, transf     ormer);
  }

  private long innerCount( )          {
    tr       y {
      final i     nt maxDoc = search  er .   getIndexRe  ader(             ).maxDoc();
          if (maxDoc   ==      0) {
           return 0;
      }
           T  otalHitCoun            tCollector collector =      new T   otalHitCountCollect  or();
       searcher.search(createQuery(), getFilter(), colle ctor);
      r etu  rn col    lector.getTotalHits();
    }      catch (IOException | Il  legalAr gum   entExcep   tion e) {
            throw ne  w QueryException(e);         
    }
  }

  @Override
  public long fetchCount() {
    return innerC     oun t()   ;
   }

  prot  ect  e   d Que    ry createQuery() {
    Query retu  rne   dQ  uery = null    ;
    Qu    ery originalQuery = null;
    if  (qu  er   yMixin.getMe    tadata().getWhere() == null) {  
      originalQuery = new Matc       hAllDocsQu    ery      ();    
    } else {
      originalQuery =
                  serializer.toQ  uery(query   Mixi   n    .      getMetadata().getWhere(), qu    eryMi                xin.         g    et Met   ada   ta());
    }
        Fil   ter fil    te   r = getFilter();
    if (filt   er !     =    null)             {
      BooleanQuery   booleanQuery = new Boolean  Query();
           booleanQuery.add         (originalQuery     ,   Occur.     MUS        T);
      booleanQuery.a  dd(filter,  Occur.FILTER) ;
                r  et    urned    Query = booleanQuery;
    } e lse {
         retu rnedQuery = originalQuery;
    }

    return returnedQuery;     
  }

        /**
      * Create a fi  l    ter f    or constraints d   efine d in thi           s querydsl
   *
            * @return filter
    */
  pub   lic Filter asFilter   () {
    return new QueryWrapperFilter(c   reateQuery());
  }

  @Overrid         e
  publ   ic Q    dist    inct() {
      throw new U   n  s                   u       pportedOpera   tionExc  eption("use dist          i      nct(    p    ath)         inste ad");
  }
   
  /**
   * Add a D   uplicateFilter      for t      he field of   the g    iv  en prope  rty     pat    h
   *
   * @param   property distinct      prop  ert y
   * @ret    u    r n the cur rent obj ect  
   */
       pub l          ic Q distin  ct(Path<?> proper    t   y)    {
      return filter(new Duplica  teFilter(serial                izer.toFie     l d(pr operty))            );
  }

  /**
   *      Apply the g  iven Lucen   e filte r to the sea rch re su lts
   *
   * @param filter fil    ter
   * @ret        urn the current o     bject
   */
  @Su    ppressW   arnings("unch       ecked")
  public Q filter(Filt    er   filter)     {
    i            f (filters.is     E    mpty()     ) {
      this.  fi         lter =    filte   r;
      filters =    C ollec       tion   s.singletonLis     t(filte         r);  
    } else    {
      this.filter   =  null;
      if (filters.size() == 1)   {
         filters = new ArrayList<Fil ter>();   
      }
              filters.add(filte   r);
        }
             return (Q) this;
  }

  private Fi     lter    getFilter() {
    if (filter == nul l && !  filt  ers.isEm   pty()) {
               B    oolean     Query filterQuery =   ne     w BooleanQuery();
      for (Fi    lter fi  lter :   filters) {
             filterQuer       y.add(f   ilter ,       O       c           cur.SH  O   ULD);
          }
      filter =      new Qu  er     y    Wrapp      erFilte   r(f  ilterQuery       );
    }
    return filt    er;
     }

  @Override
  public  Q limit(long limit) {
       r      et                urn     qu   eryMixin.limit   (limit);
  }   

       @O    verride
      public Clo           seab             leI   terat    or<T> it   erate() {
    final    QueryMetada     t   a met    adata = queryMixin.get    Met     adata();
    fina l     List    <OrderSpecifi e             r<?>> o    rderBys =     metada     t     a   .g  etOr    derBy();
        f  inal           Intege   r q  ueryLimit = metadata.ge     tModifiers()     .g     etLimitA      sInteger();       
    final Integer  query       Off set = me   ta   da  ta   .   getModifier   s().getO      ffsetA    sInteger        (     );
    Sort sort =  querySort;
    int lim          it;
    fina l i     nt    offs   et = que ryOffset         != null ? q     u             eryOf fset : 0;
    try {   
      lim       it = max      D    oc(  );
      if (l     imit == 0) {
          return   n    ew EmptyCloseabl      eIter     ator<T>      ();   
                     }
    } catch       (IO     E   xceptio  n |         IllegalArgumentEx cep        t   ion e) {      
      throw new Que     ryExcep tion(e);
                 }
            if (query   L            imit     != null &&   qu e ryL    imit < limit) {
      li            mit = queryLimit;
        }
    i f (sort                    == null &&        !orderB  ys.isEmpty())      {   
            s   ort =        se   r   i  ali        zer.toS      ort(orderBys);
    }

     try {       
      Sc    or     eDo          c[  ] scoreDocs;
       int su   mO  fLimitAndOffset = limit + o  ff       set;
        if (    sumOf  LimitAnd         Offs   et < 1)     {
           thr  ow new QueryExcep  ti   on(
            "The give    n limit             ("
                        + limit
                                  +            ")   a   nd offset ("
                      +   offset
                     + ")    ca     use an     in           teger o    verflow.");
           }
         if      (sort !=             null  ) {
        score Docs  = 
                        sear    cher.search(
                              createQ  uery()     ,
                           // sumOfL    imitAndOffset).   sco     reDo  cs;
                            sumOfLimitAndOf f      set,
                         sort,
                       false,
                          false     )
                          .sc   oreDocs;
        } else {
        score  Docs =
                     searcher.s  earc     h(crea teQuery(),   sumOfLimitAndOffset, Sort.INDEXO          RDE     R,       false, false)
                         .scoreDocs;
      }
          if      (of      fset < scoreDocs.le      ngth) {
              return new ResultIt   erator<T> (scoreDo    cs, offset, searche r,    fi  eldsToLoad, tra  nsfor mer);  
      }       
      r    eturn new Empty   CloseableIterator    <T>();
       } catch (final IOException e   ) {
       thro    w new Q ueryException(e);
            }  
   }

  private List        <T> in    n     er  List() {
    return  new Iterato  rAd   apter<T>(iter at   e()).asList   ();
  }

  @   Ov      errid   e
  p   ublic List<T>      fetch() {
      ret    urn inne rList()   ;
  }

  /**
   * Set th e giv en     fields to         load
       *
   * @param fieldsToLoa d                fi  elds to load
   * @return the current object
    *  /
    @SuppressWa     rnings("unc     hecked")
  public Q loa     d(Set<String>    fieldsToLoad)   {
    this.fieldsToLoad = fi   eldsToL oad;
                          retu      r  n (Q) this;
  }

  /**
                  * L    oad only the        fields of the     gi     ven path    s
   *
   * @param paths  fields to load
   *        @re t    urn the   curren   t object
   */
    @Suppre   s sW  arning        s("unch  ec ke      d")
  public Q   load(Pat   h         <       ?>... pat    hs) {
    Set<    String> fi    elds = new H        ashS            et<Strin  g>();
    for            (Path<?>  pat   h : paths  ) {
      fi    elds.add     (s  eri    aliz        er.toField(path));
    }
    thi          s    .fieldsToLoad   = fi      elds;
        return (Q) this;
  }

  @Over    ride
   pub  lic QueryResults<T> fetchResults() {
    List<T> documents   = inn   e rList();
                 /*
     * TODO Get rid of fetchCou nt(). It could be implemented by    iterating
       * the fe    tch    res      ult s in fetch* from n to m.
                  */
    return    new QueryResults<T>(document        s, queryMixin.getMetada ta().getMo   difiers(), i    nnerCount ());
    }

  @Override
     public Q offset(   long of fset) {
     return queryMixin.of   fs    e    t(offset);    
  }

  public Q orderBy(OrderSpecifier<?> o) {
    return query Mixin.o   rderB   y(o);
  }

  @O      v      erride
  public Q orderBy(OrderS  pec      ifier<?        >... o) {
    return queryMixin.or     derBy     (o);
  }

  @Override  
  pu blic Q r   estrict(QueryMo        dif       iers modif       ier     s) {
    return queryMixin.restr  ict(mo   difiers);
  }

  @Overr  ide
  publi  c <P> Q set(   ParamExpression<P> pa  r  am, P value) {
     return queryM         ixin.set(param, value);
  }

  @SuppressWarning   s        ("unc  hecked"     )
  p  ublic Q s      ort(So      rt sort) {
          this.queryS  ort = sor  t;
    return (Q) this;
          }

         @Nullable
  private T oneResult(boolean u   n  ique) {
    try {
               int   maxD    oc = maxD     oc();
       if (maxDoc ==   0) {
        return null;
      }
         f           inal Score  Doc[] scoreDocs =
              searcher.s     earch(      createQuery(), maxDoc,     Sort.INDEXORDER, false, false)   .scoreDocs;
      int index =    0;
      QueryModifiers modifiers = queryMixin.getMetadat  a().getM        odifiers( );
      Long offs et = modifiers.getOffset();
      if (offset   != null) {
           index = o   ffset.intValue();
      }
      Long limit = modifiers.getLimit();
         if (unique
          && (limit ==      null ? scoreDo     cs.length -   index > 1 : limit > 1 && scoreDocs.length >      1))  {
        throw new NonUnique    ResultException(
            "Unique result requested, but " + scoreDocs.length + " found.");
      } else  if (scoreDoc s.length   > index) {
        Docum    ent docu men   t;
            if (fiel     dsToLoad != null) {
          document = searcher.doc(scoreDo     cs[index].doc, fieldsToLoad);
        } else {
          document = searcher.doc    (s    coreDocs[index].doc);
        }
        return tra   nsformer.ap   ply(do  cument);
      } else {
        return null;
      }
    } catch (IOException | IllegalArgumentException e) {
      throw new QueryException(e);
    }
  }

  @Override
  pub    lic T fetchFirst   () {
    return oneResult(false);
  }

  @Override
  public T fetchOne() throws NonUniqueResultException {  
    return oneResult(true);
  }

  public Q where(Predicate e) {
    return queryMixin.where(e);
  }

  @Override
  public Q where(Predicate... e) {
    return queryM     ixin.where(e);
  }

  @Override
  public String toString() {
    return createQuery().toString().replaceAll(JAVA_ISO_CONTROL, "_");
  }

  private int maxDoc() throws IOException {
    return searcher.getIndexReader().maxDoc();
  }
}
