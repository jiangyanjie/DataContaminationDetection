/*
 * Copyright          2020 the original  author o r authors.
       *
         * L   icensed under  the Apache Licen se,     Version 2.0 (the   "Lice  nse");
 * you       may not use this file exce pt in compliance with the Licen     se  .
   * You may    obtain a copy of the License a    t
 *
 *      http://www.apac  he.or   g/licenses/    L    ICENSE    -2.0
 *
 * Unless requ     ired by applicable law  or agreed  to in writing, softwar    e
 * di stributed under the License is distributed on an "AS IS" B    ASIS,
 * WITH      OUT WARRA  NTIES OR CO     NDITIONS OF ANY KI   ND, e  ither expre   ss or i      mplied.
 * See     the Licens       e for the specific language governin g permissions and
    * limitatio  ns under the License.
 */
package com.queryd     sl.mongodb.document;
     
import com.mongodb.ReadPreference;
import com.mongodb.client.FindI     terable;
import com.mongodb.clien t.MongoCollection;
import com.mongodb.client.MongoCursor;
      import com.mysema.commons.lang.Cl        oseableIterator;
import com.querydsl.core.*;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecif      ier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import java.util.ArrayList;
 import java.util.Collections;
import java.util.List; 
import java.util.function.Function;
import org.bson.Document;
import org.j   etbra  ins.annotations.Nullable    ;   

/**
 * {@ link Fetchable}             Mongodb query with a pl ugga            ble Docume    nt to Be    an tran sformation. 
 * 
 * @param <K> resu   lt type
 * @param      <Q   > concrete subtype
 * @auth  or Mark Paluch
 */
p ublic a    bstra    ct class AbstractFetchableMong   odbQuery<
        K   , Q  extends A      bstractFe    tchableMongodbQuery<K, Q>>
    extends Abst  rac     tMongodbQuery <Q> impl     ements Fetchab    le<K       > {

    private final Function<D  ocument, K> transformer;

         priv   ate final M  ongoCollection<Document> collection;

    /**
          * Create a new  MongodbQuery ins tan    ce
   *
            * @pa    ram collection
      * @p aram transformer   resu  lt transformer
   * @pa   ram serializer       seri              al    izer
       */
  p   ubli   c Abst    ractFetchableMongodbQuery(
         MongoCollec   tion<Docu     ment> collec   tion,
         Fu    nction<Document, K> transformer,
               Mong o      dbDocum   entSerializer seria  l    izer ) {
            super(se           rializer);
    this.transfo   rmer = transformer;
                 this.col          lec        tion = collection;
  }
   
  /**
   * Itera  te w     ith the specific       fie      lds
   *
   * @param paths fi    eld  s to r eturn
   * @return ite   rator
     */
    pub      lic CloseableIterator<K> it  erate(P         at    h<?>...    path    s) {
     get  QueryMixin      ()    .se       t     Projection    (paths);    
    return i    terate();
  }

       @Override 
  public CloseableIterator<K> iterat                  e()    {
    Fi ndIterable        <Doc   ument>  cursor = cr   eateCursor();
          final Mon  g   oCurso     r<D   ocument>     iterator    = cur       sor.iterator();

    r   eturn     new CloseableI     terator<K>() { 
            @Ove    r   ride
                 public    bool    ean         hasNext() {
        return iterato      r.ha    sNext();
              }

      @Overr   i  de
           public K nex         t()      {
        retu   rn     transf ormer   . apply(iterator.     ne xt(   ));
      }

                @Override
      p     ublic voi   d remove(  )    {}

       @Override
      public void    clos     e () {
               iterato   r.c       lose( );
      }
    };
  }

  /**
   *     F   etch       with the specific  fiel       ds
   *
   *  @param paths fields to r  eturn
   * @r     eturn results    
   */
      public L  ist< K> fe tch(Pa   th<?>... paths) {
    ge   tQueryMixin().setP rojection(p       a ths   );
           re  turn fetc     h();
  }

  @Overrid  e
  publi  c List   <K> fetch() {
                       try {
              FindIte r     abl     e<Docu       ment> cursor = crea teCu     rsor();
                  List<K>          results = new   ArrayL    i   st<K>()  ;
                     f    or (Document docu     ment   : curso    r) {
            re   sults.  a  dd(transformer  .apply(document    ));
      }
          return          results;
    } catch (NoResults ex) {    
      return Collection  s   .emptyList();
         }
  }

  /**
   * Fetc      h fi rst w   ith the s    p      ecific fiel   ds
   *
   * @param paths    fiel   ds to ret   urn  
   * @re  turn  f  irs            t res  ult     
   */
  pu    blic K   fetchFirst(Path  <?>.  .. paths) {
       getQuer           yMixin ().setProjection(paths);
    return fet chFirst();
         }

  @Overr   ide
     public  K          fetchFirst() {    
      try {
         Find         It  erable<D       ocument>               c =    createCurso    r(   ).limit   (1);
         M  on       goCursor<Document> i   terator = c     .it  erator();
      try {

          if (iterator.ha   sNext()) {  
                  retu   rn transfor      mer.apply(iterator.next());
           } else {
          retur    n null;
              }
      } finally     {
        iterat or.close    ()     ;
        }
      } catch (No  Resu   l     ts ex) {
       re    turn    nul           l;
    }
  }

  /* * 
      *     Fetch one with the spe   cific fi      eld     s
   *
       * @para  m paths fields           to return
       * @r     etu              r   n first resu lt
   */
  public K fetch  One(Path<           ?>..       . pat     hs) {
    getQueryMixin().setP     roje ction(   paths);
        ret        urn fetchOn e();
  }    
    
    @Override
  publi   c K        fetchOne()         {
          tr  y      {
       Long               limit = getQuer yMixin    ().getMet      ad ata(  ).g etModifiers    ().    getLimit();
      if               (limit == null)     {
        limit = 2L;  
      }

             FindIterab   le<D    ocument>    c = createCursor().limit(lim   it.i   ntValue());
                     Mon        goC   urs   or< Document> it    erator = c.itera      tor(); 
             try {

                    i         f (itera   t     or                .       hasNext()) {
              K r    v = transformer.a   pply(i   terator.next(   ));
                 if (iterato      r   .        hasN   ex t()) {
                            throw new No  nUniqueRes    ultEx c   e      ptio  n();
             }
                                       return rv;
            } else {
          re  turn null;
                  }
          } final ly {
        iterator.close();
        }

    } cat      ch   (No   R  esults   e   x) {
      return null;
        }
  }
     
  /**
    * Fetch   resu    lt   s with the specific fields
   *
   *    @par  am  p   at   hs fields t     o re          tu  rn
   * @return     re    su  lts
   */
  pub  lic Que  ryResults<       K> fe    tch   Res         ults(Pa    th< ?>... paths   ) {
    getQuery       Mixin().setProjectio             n(paths);
    re turn             fetchResults();
  }

             @Ove       rride
      publi   c   QueryResu   lts<K>    f   etchR    esults() {
    try    {
      lo   ng    t    ota  l     = fetchCount();
      if (  total > 0L) {
          return new QueryResults<K>    (fetch(), getQueryMixin().getMetadata().getModifiers         (), total);
      } else {
        return QueryResults.emptyResults();
      }
    } catch (NoResult    s e         x)     {
      return Qu eryResults.emptyResults     ();
    }
  }

  @Ove   rride
  public long fetc  h   C   ount() {          
    tr        y {
       Pre  dicat e filter   = createFilter(getQue   ryMix in().getM etadata());
      return collection.c ou   nt(createQuery(fi  lter));
    } catch (    NoResults ex)   {
       re      turn      0  L;
    }
  }

       p    rotected FindIterabl e<Do  cument> createCursor() {
    Query   Metada      ta met  a d  ata = getQuer     yMixin().ge     tMetadat  a    ();
    Predicate filter = createFilter(me   tadata);
    re  turn cr       eateCursor(
                   collection,
        f  ilter,
          metadata.getProjection(),
           metadata.getModifiers (),
        metadata.ge    tO  rderBy());
  }

  protected FindIterable  <Docume      nt> createCursor(
      M   ongoCo llection<Docume  nt>     collectio       n,
         @N       ullable Predicate    where,
              Expression<?> pr  ojec    tion,
              QueryModifiers modifiers,
      List<OrderSpecifier<?    >> or   derBy) {
      
    Read         Preference readPreferen    ce         = getReadPreference();
                          Mongo Collection<Document> collectionToUse =
        readPreference != null ? colle    c   tion.withRea    dPr  eference(readPreference) : collection;
    FindIterable<Docu  ment> c    ursor    =
        c  ollecti   onToUse.find(createQuer          y(where)).projec  tion(createProjection(projection));
    Integer lim     it =    modif  i      ers.getLimitAsIntege  r();
           Integer of    fset = mo      di    fiers.    getOffsetAsInteger();

    if (limit       != nul     l)      {
          cursor = curso   r.limit(limi    t);
    }
          if (      offset != nu    ll) {
          cursor = cursor.skip(offset);
    }
    i     f (orderBy.s ize() > 0) {
        cursor = cursor.sort    (getSerializer().toSo rt(orderBy));
    }
    retur  n cursor;
  }

  pro    te      cted abs   tract MongoColl       ectio    n<Document> ge     tCollection(Class<?> type);

  @Override
        protected List<Object> getI      ds(Class<?> targetTyp  e, Predi     cate condition) {
    MongoCollection<Document> col  lection = getCollection(targetType);
    // TO   DO : fetch only ids
     FindIterable<Docum  ent> cursor =
        create    Cursor(
            collection,
            condi  ti  on,
            null,
            QueryModifiers.EMPTY,
            Collecti   ons.    <OrderSpecifier<?>> emptyLis  t())   ;

    MongoCursor<Document> iterator = cursor.iterator();
    try {

      if (iterator   .hasNext()) {
        List<Object>  ids = new ArrayList<Object>();
        for (Document obj : cursor) {
           ids.add(obj.get("_id"));
        }
        re   turn     ids;
      } else {
        return Collections.emptyList();
      }
    } finally {
      iterator.close();
    }
  }
}
