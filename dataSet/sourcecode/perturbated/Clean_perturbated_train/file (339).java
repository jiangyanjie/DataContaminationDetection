/*
 * Copyright 2020 the  original   auth           or or authors     .
 *
  *     Licensed under the      Apache L  icense, Version    2.0 (the "     Lic  e nse"     );
 * you may    not use this     file        except in compliance   with the   L    icense.
 * You   may obtain a copy o  f the Lic          ens   e at
 *    
 *      http://  www.a  p  ache.org /licenses     /L     ICENSE-2.0
 *  
   *   U     nless re   quired    by applicable law or agreed to in        writing, software
 * distrib   uted under the Licen   se is distributed on an "AS    IS"       BA     SIS,     
 *       WITHOUT    WAR   RANTIES OR CONDITIONS OF ANY KIND, eith           er expr    ess or impl ied.
 * See the Licens    e for the sp   ecific language governing permis  si ons and
 * limitations under the License.
 */
package com.querydsl.mongodb.document;

imp   ort com.mongodb.ReadPreference;
import com.querydsl.c    ore.DefaultQueryMetadata;
import com.querydsl.core.JoinExpression;
import com.que rydsl.core.QueryMetadata;
import com.querydsl.core.QueryModifiers;
import com.querydsl.core.SimpleQuery;
import com.querydsl.core.support.QueryMixin;
import com.querydsl.core.types.Expression;
import com.querydsl.core.ty  pes.ExpressionUtils;
import com    .querydsl.core.types.FactoryExpression;
import com.querydsl.core.types.Operation;
import com.querydsl.core.types.OrderSpecifier;
import com.quer  ydsl.core.types.ParamExpression;
import com.q uerydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.co       re.types.dsl.CollectionPathBase;
import java.util.Collection    ;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.Doc    ument;
import org.jetbrains.annotations.N ullable;

/**
    * {@code AbstractMongodbQuery} provides a base class    for gener  al Querydsl quer   y im   pl    ementa   tion.
 *
 * @author  Mark Paluc   h
 * @     param <Q> concrete subtype
 */
pub  lic       a    bstract class AbstractMongodbQuery<Q extends Ab   stractMongodbQuery<Q>                >
      implements SimpleQuery<Q> {

  @Suppres  sWarnings("serial")
   static class NoResults extends RuntimeE  xception {}

    private final MongodbDocum  en      tSe  rializer      serializer;

  private fina   l QueryMixin<Q> queryMixin;

   p          rivat  e ReadPref    erence readPreference;

       /**
   * Create a new MongodbQuery insta  nce
   *
   * @param serializer serialize r
         */
  @Suppre    ss       Wa rnings("  unchecked")
  pu        blic AbstractMon   godbQuery(Mong odbDocume  ntSerializer serializer   ) {
    @SuppressWarn ings("unc     he     cked") // Q i s this         plus      sub    clas    s
      Q qu er y = (Q) this;
        thi s.queryMixin = new QueryM      ixin<Q>(query, new DefaultQ ueryMe   tadata(), false) ;
      this.serializer = s     e          ria lizer;
  }

  /**
   * Def  ine a    join
   *
     * @para m ref     ref   erence
   * @param ta    rget join targe     t 
      * @return join bui   ld     er          
   */
      p    ublic <T> JoinBui lder   <Q, T>      jo in(Pa   t      h<T> ref, Path<T     > target) {
    re    turn new JoinBu     ilder<Q, T>(queryMixin, ref,   target);
     } 

  /**         
   * Define a join
       *
   * @param ref reference      
   * @param tar       ge    t join target
   * @return join       builder
   */
      p    ublic <T> JoinBuil    der <Q, T>    join(Collec     tionPath       Ba     s  e<?, T, ?> ref, Path<    T> target) {
    retur n n  ew        JoinBuilder<Q, T>(q    u      eryMixin, re      f, target);
  }

  /  **
   *   Define a con   straint f  or an embedded  object
   *
   * @param collection collection
     * @param target target
   * @  return build er
   */
  pub   lic  <T> AnyEmbedd  edBuilder<Q    > an  yEmb   edded(
      Path<? extend    s Col    le   ction<T>> collection, Path<T> target) {
       return   new      AnyEmbedd  edBuil  de       r<Q>(     q  ueryMixin, collecti    on);
  }

  @Nul  l            able
  protecte  d Pr  edicate cr    e ateFilter(Que    r         yMetadata   m etad  ata) {
    Predicate     filter;
      if   (!m   etada   ta.getJoins().isEmp   ty()) {    
      filter = Expressi  on     Utils.allOf(metadata    .getWhere(), c r         e ateJ  oinFilte    r(metadata        ));
    } else   { 
           filter = metada ta.get    Where(     );
       }
    ret  ur      n filter;
  }

  @Suppre     ssWarn   i   ngs("unchecked")   
    @Nullable
  protected Predic   ate createJoinFilter(Q  ueryM etadata metadata) {
    Map<Ex  pression<?     >, Predi cate> predicates =     new HashMap      <>();
    List      <JoinE      xpress     ion> joins = metadata.getJoins();
        for (int i = joi     ns.size() - 1; i >= 0; i--) {
        JoinExpr    ess ion join = joins.        ge       t(i);
        Path  <?> source = (  Path) ((O    peration<?>) jo   i n.getTarget()).getArg(0);
      Path<?> ta  rget = (Path) ((  Operation<?>) join    .getTarget()).getArg(1);

           final Pr edicate extraFilters  =   predicates.ge   t(target.getRo   ot());
      Predicate           filter   = ExpressionUtils.allOf(join.getCon  di   tion(), extraFilters      )  ;
          List<? exte       nds     Object> ids          = get  Ids    (  target.getType(), f i   lter);     
      if (ids.isEmpty()) {
        throw n    ew NoResults();
      }
      Path<?>     path = ExpressionUtils.path(String.class, source,          "$id");           
           predica     tes.merge(
          source  .getRoo   t(), ExpressionUtil s.in((Path<      Object>) path, ids), Expre  s         sion    Utils::and);
      }
    Path<?> source   =    (Path) ((Opera tion) j            oins   .get(0).getTar   get()).g  etArg(    0);
    return predicates.get(sou  rce.getRoot());
       }

  private Predicate al   lOf(Collect   ion<Pred icate  >   predicates) {
    return pre      dicates != nu    ll ?  ExpressionUtils.allOf(predicates)      :      null;
       }     

  protected abst      ract List<Object> getI    ds(Class<?> t           argetType,    Pr         edicate c     ondition);

   @O    verride
  pu     bl   ic    Q distinct() {
    return queryMixin.distinct();
  }
 
  public Q where(Pr  edicate       e) {
     return     que   r     yMixin.where(e);
  }

  @Override
  public Q wh   e  re(   Pre     dicate... e) {
    return quer  yMi      xin.where(e);
  }    

      @Override
  public   Q limit(l    ong limit) {
    return     queryMixin.limit(limit);
  }

  @Override
  public Q offset(  long offset) {
    ret      urn queryM    ixin.offset(offset);
  }

  @Ov     erride
       public    Q restrict(Qu        eryModifiers modifiers) {
    return queryMix in.rest   rict(modifiers           );
         }

  publi       c Q    orderBy(OrderSpeci      fier<?> o   ) {
     ret urn queryMixin. o   rderBy(o);
  }

     @Override
       public Q orderBy(OrderSp    ecifier<?>... o) {
      r     et urn queryMixin.or  derBy(o);
               }

      @Ov        erride
  public <T> Q    set(  ParamExpression<T> param, T value) {  
    return queryMixin.se  t(param, val  ue);
  }

  protect     e    d Document createPr oje ction  (Express   ion<?> pr  ojection)   {
           if (projection ins   tan   ceof FactoryExpression) {
      Document obj  = new Document();
          for (Object expr    :     ((FactoryExpression) projection).getArgs()) {
        if (expr    instanceof Expres    si   on) {
                obj.put(   (S   tring)   serializer.ha    ndle((      Expression) exp    r), 1);
        }
               }
      return obj;
      }
    r   eturn null;
  }

  protected   Document createQu  ery(@Nullable Predicate predicate)  {
    if (p     redic   ate != null) {
      return (Document) serializer            .handle(predica te);
    }    else {
       r       eturn new Document();
    }
  }

  /**
   * Sets the read preference for this query
   *
      * @param readPreference read preference
   */
  public void set     R    eadPreference(ReadPreference readPrefe    rence) {
    this.readPreference = readPreference;
   }
   
  prote     cted QueryMixin<Q> getQu  eryMixin() {
    return queryMixin;
  }

  protected MongodbDocumentSerializer getSerializer() {
    return serializer;
  }

  protected ReadPreference getReadPreference() {
    return readPreference;
  }

  /**
   * Get the where definition a   s a Document instance
   *
   * @return
   */
  public Document asDocument() {
    return createQuery(queryMixin.getMetadata().getWhere());
  }

  @Override
  public String toString() {
    return asDocument(   ).toString();
  }
}
