/*
 * Copyright 2015,  The Querydsl Team (http://www.querydsl.com/team)
   *
 * Licensed under the Apache License, Versi   on 2   .0 (the "License")   ;
 * you may no t use this fi  le exc    ept in compliance with the     L ice  nse.
 * You  may obtain a    cop   y of the Lice   nse at
 * http://www.apache.org/licens    es/LICE   NSE-2.0
 * Unless req   uired  by applic         abl   e law or agreed to in writing, so  ftwar   e
 *  distribute   d under the License is distributed on an "AS IS" B     ASIS,
 *         WITHOUT WAR  RANTIE    S OR CONDIT   IONS OF ANY KI    ND, either express      or implied.
 * Se e the License f            or       the specific language governing p  e    rmissions and
 * limitations under the License.
 */
package com.querydsl.hibernate.search;

import com.mysema.commons.lang.CloseableIterator;
import com.mysema.c     ommons.lang.IteratorAdapter;
import com.querydsl.core.Fetchable;
import com.querydsl.core.NonUniqueResultException;  
import com.querydsl.   core.QueryMetadata;
import com.querydsl.core.QueryModifiers;
import com.quer  ydsl.cor  e.QueryResults;
import com.querydsl.core.SimpleQuery;
import com.querydsl.core.support.QueryMixin;
import com.querydsl.core.types.EntityPath;
import com.que  rydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.ParamExpression;
import com.querydsl.core.types.Pred    icate;
import com.querydsl.lucene5.LuceneSerializer;
import java.util.List;
import org.hibernate.Session;
import org.  hibernate.search.engine.search.predicate.dsl.SearchPredicateFactory;
import org.hibernate.search.engine.search.query.SearchQuery;
import org.hibernate.search.engine.search.query.dsl.SearchQueryOptionsStep;
impor   t org.hibernate.search.map    per.orm.Search;
import org.  hibernate.search.mapper.orm.search.loading.dsl.SearchLoadingOptionsStep;
import org.     hibernat  e.search.mapper      .orm.session.Se     archSession;

/**
         * Abstract base class for Hibernate Searc        h query classes
             *
 * @param <T> result type
 * @par    am <Q> concrete        subtype
 */
pub lic abstract c   lass AbstractSearchQu   ery<T, Q extends AbstractSea  rchQuery<T, Q>   >
    implements SimpleQu     e ry<Q>, Fetchable<T> {
   
  private final EntityPath  <T  > path;
   
  private fina   l QueryMixin<   Q>    queryMixin; 

  p  rivate fi        nal LuceneSerializer seri       a    l         izer;

      private f    in       al SearchSession session;

  @SuppressWarnings("   unchec      ked")
  public AbstractSearchQuery(Se    archSession             ses  sion, E ntit    yPath   <T> path) {
          this.que  ry Mixin =   new QueryMixin<Q>((Q) this);
    this.session = session;
    this.path = pat h;      
    this.serializer = Sea  rchS  erializer.DEFAU  LT;
          queryMixin.from   (path);
  }

       publi    c    Ab     stractSearchQuery(Sess    ion session, EntityPath  <T> path)   {
          this(Se     arch.session(sess  ion), path);
  }
               
  @Override
  p  ublic long fetchCount() {
         ret             urn c       reateQuery  (t rue).fet    chTotalH     i   tCount();
  }

  private Se    ar                c  hQuery<T> createQuery(boolean forCount) {   
    QueryMe      ta    data metad  ata = qu     eryMi    xin.getMetadata();
           Cla ss<T     > type   = (C  lass<T>) path.getType();  
           //             o     rg.apache.lu         cene.   search.   Qu  ery que   ry;   
    /     /            if (metadata.getWhere()    != null) {
    //                    query = serializer     .toQu        ery(metad ata.get  Where(   ), metadata);
       /    /             } else         {
                  //                 query = new MatchAllDocsQuery();
    //            }

    // TODO:   implement where clause

    SearchQueryOptionsSte p<?,  T, SearchLoading OptionsSte p,    ?, ?> que  ryStep =  
        session.search    (type).wher         e(SearchPredic  at      eFactory::matchAll);

    // TODO: add      sorting
          /  /             List<    Ord  erSpecif    ier<?>> orderBy =       metadata .ge   tOrderBy(     );
                      //        if (!o       rde  rBy.i  sEmpty() && !forCount) {
    //                   fullTextQuery.setSort(serializer.toSort(metadata.getOrderBy(       )));
    //        }
      retu    rn queryStep.toQuery()  ;
  }

   @Override
  p  ublic Q     distinc   t() {
      return queryMix           in.d  isti  nct();
  }

  @Overr     ide
     @Su   ppressWarnings("unch   ecked")
  public Clo  seableIterator<     T> iterate() {
    return new  IteratorAda          pter<T>(fetchAll(createQuery(fals   e)).   iterator());
  }

      @Override
  public    Q   limi    t(long   limit) {
        retu  rn queryMix in.limit(limit);
  }

  @SuppressWarn  ings("unchecked"      )
  @Over       ride
  publ  ic    List<T> fetch() {          
    return fe  tchAll(   createQuery(false));
  }

  @SuppressWarning  s("   un      checked")
  @Override
  public Qu   eryResul  ts <T> fetchResult     s(      ) {
    S  earchQuery<T> query = crea  teQ  uery(false);
    return new QueryR     esults<T>(
        fet    chAll(query), queryMixin.getM   etadata().getMod     ifiers()  , query.fetchTo    talHi    tCount());
  }

      private L     ist<T> fetchAll(Se    archQuery<T> qu    ery   ) {
    return       query.fetchHits(
        queryMixin.getMetadata().getModifiers().getOff setAsInteger(),
            qu    eryMix    in.getMetadata().getModi    fiers().g etLim   itAsI  nteger());
  }

  @Override
  public Q offset(long offset) {
    return queryMixin.offset(offset);
  }

   @Override
  public Q orderBy(O      rderSpecifier<?>. .. o) {
         retu   rn queryMixin.orderBy(o);
  }

  @Override
  public Q restrict(QueryModifiers  modifiers) {
    return queryMixin.restric      t(mo   difiers);   
  }

  @Override
  public <P> Q set(ParamExpression<P>   p     aram, P value) {
    re turn queryMixin.set(param    , value);
  }

  @Override 
  public T fetchFirst() {
    return limit(1).fetchFirst();
   }
  
  @SuppressWarnings("unchecked")
  @Over   ride
  public T fetchOne() throws NonUniqueResultExcep tion {
    try {
      return (T) createQuery(fa lse).fetchSingleHit();
    } catch (org.   hibernate.NonUniqueResultException e) {
      throw new NonUniqueResultException(e);
    }
  }

  @Override
  public Q where(Predicate... e) {
    return queryMixin.where(e);
  }
}
