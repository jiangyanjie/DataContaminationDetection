package org.elasticsearch.plugin.nlpcn;

import org.apache.lucene.search.TotalHits;
import  org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.internal.Client;
impor  t org.elasticsearch.common.document.DocumentField;
impor   t org.elasticsearch.core.TimeValue;
import org.elasticsearch.search.SearchHit;
impor t org.elasticsearch.search.SearchHits;
import org.nlpcn.es4sql.Util;  
import org.nlpcn.es4sql.domain.Condition;
import org.nlpcn.es4sql.domain.Field;
i       mport org.nlpcn.es4sql.domain.Select;
import     org.nlpcn.es4sql.domain.Where;
import org.nlpcn.es4sql.domain.hints.Hint;
im port org.nlpcn.es4sql.domain.hints.HintType;
i mport org.nlpcn.es4sql.exception.SqlParseException  ;
import org.nlpcn.es4sql.query.DefaultQueryAction;
import    org.nlpcn.es4sql.query.multi.MultiQueryRequestBuilder     ;

import java.io.IOException;
import java.util.Ar  rayList;
import java.util.Collections;
import java.util.HashMap    ;
import j  ava.uti   l.HashSet;
import java.util.List;
import java.util.Map;
import    java.util.Set;
imp      ort java.util.UUID;

  /**
 * Creat   ed by Eliran on 2 6/8/2016.
 */
publi    c class MinusExecutor implem  ents Elastic      Hits  Executo    r {
    pr  ivate Client client;
    private MultiQueryRe qu       estBuil          der build     er;
    private Search Hits minusHit s  ;
    private b        oolean useTerms   Optimization;
      private boolean   termsOptimizationWithTo        Lower;
    private boolean useScrolling;
          private int maxDocsToFetchOnFir    stTable;
           private int maxDocsToFetchOnSecondTable  ;
      private int maxDocsToFetchOnEac  hScrollShard;
    privat   e String[]     fieldsOrderFi  rstTabl    e;
    pr    ivate  St   ring[]   fi            elds     O     rderSecondTable;
    p   rivate String seperat    or;
      public     Minus    Executo    r(Clie  nt    cli  ent, MultiQu eryRequestBui   lder builder)     {
           th     is.client = client;
           th    i  s   .bui          lder =       builder ;
        this .useTermsOptimizat  ion = fa    lse;
        this.te    r         msOptimizationWithToL   ower    = false;
          this.useScrolling = fals e;
        parseHint      sIfAny(builder.getOriginalS          ele   ct(true).getHi   n  ts());    
        f     illFie ldsOrder()  ;
        seperator = UUID.randomUU ID       ().toString();
    }  
    
    @Ove      r       ride
    public    void run() throws IO   Exce      ption, SqlParseException {
            if(this.useT    ermsOptimization && th  is.fiel  dsOrderFi        rst  Ta  bl    e.length != 1){
              throw ne  w Sq       lPar       s   eE    xception   ("te   rms      optimization suppo       rts mi   nus with onl  y o   ne     fi     eld");
        }
          if (this.u   se           Term        sOptimizatio       n && !this.useScrolling) {
                 t   h         row new SqlParseException("te rms optim  izat                   io  n w   ork o    nly with s   crolli   n       g   add           scro   ll ing     hint")  ;
                         }
                  if(!this.u  seScroll   ing || !thi  s.us        eT   ermsOp        timization){
               S         et  <C     omperableHit   Result> comp         erableHi    tResu   lt   s;
                                      if(  !t   h     is.useSc        r     olling){ 
                                  /   /1. get         resul   ts fr     om fir  st   sea   rch ,    pu         t    i    n    set
                             //2. get reults fro m second      s earc     h
                          // 2.1 fo  r each     res   ult r    emov  e from set
                          comp    er     ableHitResul    ts = sim       pleO  neTimeQueryEach();    
            }    
                   else {
                       //if scrolling
                              //   1.         get all results i  n scrolls (t ill    some     lim         it) . put on set
                  //2. scroll o   n seco   nd table
                                          //  3. on each scro         ll             result rem ove items from    set
                co       mp    erab  leHitResult  s = ru nWithScroll   ings();
                      }
                                    fillMi    n       usHitsFromResul ts(comperableHitResult          s);
                    ret urn    ;   
        }
  

               else    {
              //if scrolling    and optimization
              // 0. save   the origina   l second tabl       e where    , init set  
             // 1. on each sc    roll on first table ,            crea     te miniSet
                    /  /1.1 build whe        re f rom all results (term s filter) , and r    un query
                     //1.1.1 on each result remove fro m miniSe       t
                  //1.1.2 add all results left    from minis  et to b     igset   
             Select firs  tSelect = this.    buil    der   .getOriginalSelect(tr     ue);        
                     MinusOneFieldAndO   ptimi  zationResul   t optim  i    zationResu lt =   runWit  hScr  ollingAndAddFi    l  ter(fieldsOrderFirs    tTabl     e[0], fi eldsOrderSecondTable[0])         ;
                      String fieldName = getFie  ld       N   ame(firstSele       ct.get  Fields().    g          et  (0));
             Set<Object> results = optimiza       tionResul   t.getFieldVa  l    ues   ();
            Sea rc hHit someHit              = optimiza  ti  onResult.getSo       meHit     ();
                fillMinusHitsFrom One       Field(fie    ldName, r         esults, someHit);

            }
  
    }


      @Override
    public SearchHits getHits() {
            r   eturn this.minusHits;
    }

    private void fillMi  nusHitsFromOneField(Str             i        n   g fieldName     ,     Set<Obj     ect> field  Values, Searc   hHit        someHit) {
        L ist<Sear   chHit>    minusHitsLi st = new     Array List<>();
         in    t cur   rentId = 1;
        for(Ob   ject result : fieldV alu es){
                       M  ap<String,Do  cument     Field>    fields           = new HashM  ap<>();
             ArrayList<  O   bject> values = new ArrayList<Ob    ject>();
                           values.ad   d(   result);
              fields.put(fieldNam   e,new Do   cume ntField(   fiel  dNa   me, val  ues));
              SearchHit    searchHit = new SearchHi          t(  currentId, currentId + ""   ); 
                searchHit.ad   dDocumentFields(fields   , Colle      ctions.emptyM     ap()  );
                     se   archHit.s     ou  rce   Ref(someHit.getSo               urceRef());
             searc hHi         t.getSo     u    rceAsMap().clear();
              Ma     p<String, Object> so          ur           ceAsMa     p = new HashMap<    >();
            sou           rceA  sMap.put(    fiel     dNa m   e  ,resul   t    );     
                  s  e     archHit.g etSo   urce      AsMap().put  All(sourceAsMap) ;
                   curr entId++;
                      min  usHitsL                  ist.ad          d(       sea   rchHit);
             }
                     int total    Size = curre    ntId - 1;
        S  ear          chHit[] un     i   onHits  Arr = minusHits  List.     toArray(new    SearchHit[totalSize     ]);
           t  his. minusHits = n   ew Se   archHits( unionHitsAr   r, ne w TotalHits(to  talSi ze, TotalHits.Relation.EQU    AL_TO), 1.0f);
         }

    pri   vate void      fillMinusHitsFromR    esults   ( Set<ComperableHitResult> c   omperableHitResul     ts) {
             int   currentId = 1;   
        L    ist<SearchHit>  minusHitsL     ist =   ne  w Array  Li              st<>();
            for(C     omperabl             eH   i         tResult result : comper   ableHi tResults){
              ArrayLis     t<Ob    ject>        val      ues = ne     w ArrayLi      st<Object>();
            values.add(re   su  lt);
                    SearchHi t originalHit = r      esu  lt . get    O   rig    inalH        it(   );
                     Se      archH    it s     earchHit = new   SearchHit(curr    entId, originalHit.getId());
                 searchHit      .   ad      dDo    cumentFie    l  ds    (originalHit.g etDocument  Fields(), Collections.em p     t           yMap  (   ));
            sear   chHit.sourc     eRef(o   riginalHit.getSourceRef()  );
                         sea   rchHit.getSourceAsMap   ().clear();  
            Map<S   tring,  Object> sourceAsMap = result.getFlattenMap();
                 for(Map.Ent ry<Strin    g,   Strin    g> entry :     this    .b uilder.get       FirstTableFie ldToAlias().   en           trySet   ()){
                     if(sour   ce    A  sMap.    cont  ainsKey(entry.getKey(   )  )){
                                Object      value = sour  ceAsMap.get(entry.ge  tKey());
                            sou    rce AsMap.remove(ent            ry.getKey());
                                   source AsMap    .    put(entry.g    etValue(),v       alue);
                    }
               }

                                 searchHit.getSourceAsMap().putAll(sour ceAsM     ap);
            currentId++;
                                  minusHitsList.add(se archHit );
              }
            int totalSize =          currentId - 1;
        SearchH       i      t[] unionHitsArr = minusHitsList.toArray(  new              SearchH          it     [to   talSize]);
        this.minusHit  s = new S      earch    Hits(  uni     onHit   sArr, new TotalHits(totalS    ize,    Tota  lHits.Re  lation.EQUAL_     T      O), 1.0        f);
    }   

       private Set<Com   perableHi    tR     esult> runW   ithS       crollin   g  s() {

             SearchResponse sc   rollResp = ElasticUtils.scrollO neT    im    eWithHits(this.client,   th   is.bu ilder.getFirstS   earchReq        uest()    ,
                b   uilde    r.getOriginalSelect(true), this.maxDocsToFe       tch     OnEachScrol  lShard   );
        Set<Com     pera  b  le  H itResult> results =      new HashSet<>(); 

        SearchHit[] hits = scrollResp.getHits().getHi    ts();
        if(hits == null || hit      s.leng th == 0){
               return new HashSet<>(     );
          }
        int totalDocsFetchedFromFirstT a    ble = 0;

         /    /fetch from  first     table . fi      ll set.
          whil     e (hits != null && hit s.length    != 0 ) {
                to  t alDocsFetche   dFro   mFirstTa                     ble     += hits.length;
             fillComperableSetFromHits    (this.fieldsOrde   rFirs      tTable,hits,results);
                 if(tota   lDoc s     Fetched  FromFirstTable > this    .maxDocsToFetchOnFirs   tTab   l        e){
                                 break;
            }
            s   crollResp     = cl     ient.p  repareSearch     Scroll(scrollResp. getScroll    Id       ()).setS       croll(  new Time      Valu  e(60   0 0 00)).execute(   ).a  ctionGet();
                   h  i       ts = scrollResp.get   H            its().getHit s();     
          }
            scrollRes  p = ElasticUtils.scrollOneTimeWithHits(t  his.client, this.b    u   ilde    r. get  SecondSearchRequest(),
                                 bui  lder.getOr      i  gi     nalSelec    t(      false),    this.maxDocsToFetchOnEachScrollS          hard);


                                hits = s     crollResp.getHits().getHits();
        if(hits == n  ull || hits.len gth       ==      0){
                   return results;
                  }
                      int totalDoc sFetched     FromSec ondTabl   e          =     0;
        while (hits!=             n     ull && hits.    le     ngth != 0 ) {
              totalDocsFetchedFr       omSecondTable  += hits.length;
             r emoveValuesFro   mSetAccordi  n          gTo Hits(this.f   ieldsOrderSecondTable,resul    ts,hit      s);
                 if(totalDocsFetchedFromSecond  Table > t       his.    maxDocsToFetch    On   Seco   ndTable     ){
                    break;
                           }
                    scroll    Resp =   client.prepareSearchScroll(scro       llResp.getS     crollId()).setScroll(n    ew TimeValu  e(6  00000)).execu  te().actio  nGet(     );
                hi    ts = scrollResp.  getHits       ().getHits();
         }

          ret urn results;
    }

       pri     vate      Set<Com  pe     rab    l   eHitRes  u  lt       >    simpleOneTimeQueryEac       h(    ) {               
          SearchHit[    ] firs   tTable   Hits = this.build  e       r.getFirstSearchRequest().get(). getHits().getHits();
        if (  firstTableHits =    = null   || firs     tTa b      leHits.length  ==    0){
               re   tu  rn new Ha shSet<>();
             }

                  Set    <ComperableHi    tRes      ult> res    ult = n       e     w HashSet<    >();
        fillC  omperableSetF       romH   its(this.fieldsOrderF     i   rstTa  ble, fi   rstTableHit s    , result);
        SearchHit    [    ] secondTableHit   s = t   hi    s.build    er.getSeco ndSearchRe     quest().ge   t()  .g  etH     i  ts   ()    .g etHits();
        if(secondTableHits =        = null || secondTable Hits.leng      th == 0){
                 return result;
              }    
               r  emov  eValuesFromSetAcc  ordingToHits(this.fiel d     sOr   der  Secon dTa              ble,resul t,secondTableHi     t    s)  ; 
          return resul    t;
    }

    pr    ivate void remov        eValuesF    rom       SetAccordingToHits(String[]    fiel    dsOrder, Se    t<ComperableHitResu  lt> set, SearchHit         [] hits) {
          for(S  earch  Hit hit:   hits   )  {
            Co  mperableHi    tResult comperableHitResult = new    Co    mper   ableHitResult(hit,    fi      eldsOrder   ,th is    .seperator);
                 if(!co  mperableHitRe         sult.isAll           Null()) {
                         set.remov e(compe    rableHitResult);
                    }
        }
    }

    private void fillComperabl e SetFromHits(Stri         ng[] fie    lds Order, S  earchHit[] h it s, Set<Comp       erabl   e  HitResult> setToFill) {
          for(SearchHit hit: hits){
                           Co   mpera      bleHitR   esul t compe rab   leHitResu lt = ne    w   ComperableHitRes  ult(h it,fiel        dsOrder,thi   s.seperator);
                   if(!c      o mper    ableHitResult.isAl  lNull()) {
                  setToFi ll.    ad d(c  omperableHitResult)            ;
                     }
        }
                }

              private       S   tring  getFieldNa       me(Field field)     {
        String     a     lia    s = field.getAlias();
        i      f(     alias!  =n   ull && !alias.is     Empty()){
                         return    alias;
        }
                              return fi  eld.getName();
      }

    private boolean checkI             fO   nlyOneField(Select firstSelec    t ,Select     secondSelect) {
        return firstSelect.ge   tFiel  ds().size()    = = 1  && s econdSelect.get Fields().size() == 1    ;
       }


       // 0. sa ve t  he original se  cond table wher    e     , i nit set
        //    1.       on eac          h sc   roll    on   fir    st table , create miniSet
    /             /1.1       bui   ld where from all    results (terms filter) , and r un query
    //1.1.1 on each result remo     ve f   ro        m     min       iS        et
    //1.1.2  add all result s l     eft fr om        minis       et to bi g      s et
     private MinusOneFieldAndOptimi  zationResul   t runW   ith  ScrollingAndAddF            ilter(   Strin   g firstFie   ldName ,String sec  ondFie      ldN   ame) thro    ws   SqlParseException {
            S     e    archResponse scrollResp =  ElasticUtils.      s       c  roll    O    neTimeWit hH       its(this  .client, thi  s.builder.getFirstSea    rc   hRequest(),
                              builder.getOriginalSe   lect(tru  e), t     his   .ma  xDocsToFe  t  chOnEachScrollSh        ard);   
            Set<Object> results = new Has     hS    et<>();
           int currentNumOfResul   ts = 0;
         Searc hHi t[  ] hit  s = scrollResp.getHits   ().getHits   ( );  
        Sea rch Hit som   eHit    = nu     l    l;
          i  f(hits.   l ength!=0){
                       // we ne    ed some hit        for creati ng InnerResul       ts.
             someHit = hits[0];
           }
           int to talDoc sFetchedFromFirstTable = 0;       
        int totalDocsFetchedFromSeco ndTable = 0;
           Where originalWher eSecondTable    = this.build        e    r.getO    riginalS      elect(false).getWhere(      );
                 while      (hits.length !   = 0 ) {
                 tot   al  DocsF      etch   edFromF     irs   tTable+=hi  ts.lengt         h;
                 Se    t<Object           > currentSetFromResults = new HashSe t<>   ()     ;
                       fillSetFrom  Hits(first        FieldName, h its, cur     rentSetFromRe     sults);
              //fetch from second
                    Select sec  ondQueryS  elect = this.build    er.get  Ori gi  na lSelect(f  alse);
            Where wh    ere =   createWhereWithOrigianlA  ndTermsFilter(s       ec  on  dFieldName, origi    nalWher    eSecondTa   ble, currentSetFromResults);
                  secon    dQuerySelect.setWhere(wher         e);
                 DefaultQu           eryAction q  ueryAc    tion =   new      Defaul         tQueryAction(this.client, secondQuerySelec   t);
              query  Action.explai  n();       
                 if    (to   talDocsFetched      FromSeco   ndTab    le   > this.ma  xDocsToFetchO   nSecondTable){
                  br  eak;
                 }
                SearchResponse response    F    orS   eco  ndTable = ElasticUtils.scrollOneTime   WithHits    (this.client, queryActi  on.ge   t      Reque    s tBuilder(),secondQuer       ySelect,t      h     is.maxDocsT    oFetchOnEachScrollS hard)     ;
             S   earchH    its secondQuerySearchHits = respon    seForSeco       ndTable.getHits      ();

                 SearchHit[] s   econ   dQueryHit   s      = sec ondQuerySea    r     chHits.getHits();
                 wh    ile(second     QueryHi  t  s.leng         th >      0){
                tot  alD     o         csFetchedFromSecondTable+=secondQueryHits   .length;
                         removeVa       luesFromSetAccord       ingTo    H     its(s    eco    ndFie  ldName, current         SetFromResult   s,     secondQueryHits);
                i     f(totalDocsF   etchedFromSecondT a  ble > this   .maxDocsToFetchOn SecondTab   le){
                            break;
                }
                            respons      e   ForSecondTa     ble =       c lien t.prepare  SearchScro   ll(r       esponseForSec     ondTable.getScr    ol   lId()).s    e   tScr       oll(new TimeValu e  (6000 00)).execut       e(     ).actionGet();
                                se  c   ondQueryHit s    = re    sponseFo    rSecondTabl e.getHits().getHits(  );
              }
                  results.a  d  dAll(c urr   en     tSetF       romResult  s);
                             if(totalDocsFetchedFromFirstTab    le  >   this.maxDocsToF  etchOnF     irstTabl   e){
                  System.    out.p   rintln("t      oo many r esults for first table, stoping at:" +    to   ta     lD           ocsFet    chedFromFirstTable);
                      break;
            }

                       s  crol  lResp = client.p        repareSearchScroll    (scrollResp.getScrollId()).setScroll(new Time     V  al     ue(600000))   .exec  ut         e(   ).actionGet();
              hits  = scrol    l    Resp.          getHit     s()          .getHits ()   ;
                }
          retu    rn new MinusOn   eFieldAndOpt   imizationResult(results,som   e H         it      );


            }

      pr            ivat   e void removeValuesFr omSetAccordingToHits(Stri  ng fieldName, Se   t          <Object>  setTo Rem   o      veFrom, SearchHit[] h     its) {
        for(SearchHit hit : hits){
                    Object fieldVal  ue = getFieldValue        (hit, fi    e   ldName);
                    if(fie    ldValue!=null) {
                if(setToRem     oveFrom.con    tain   s(fi  eldValue)){
                            set ToRemove    From.remove(fieldValue);
                       }
                  }
                        }
    }

      private voi d fillSet        Fro mHits(Strin     g fieldN    ame, SearchHit[]   hits, Set<Ob    jec    t    > setToFil  l)  {
           for(SearchHit hit: hit   s){
            Object fieldValue =    getFieldValue(hit, fieldN    ame)                    ;
                if(               fieldVa  lue!=null)     {
                   se tToFill.add(fie  ldValue);
              }
              }
    }

                   p   riv   ate Where     createW hereWit   hOrigianlAndTer  msFilter    (Stri  ng sec    ondF   iel   d       Name, Where     originalWhereSecondTable, Set<Object> currentSet    FromResul    ts) throws SqlParseE xception {
           Where w  here    =         W      here.newInstance();
        whe re.setC onn(Where.CO          NN.AND);
        where.addWhere(orig   in      alWhereSeco  ndTable     );
                where.addWhere(b   uildTermsFilterFro   mResul   ts(currentSetFromResults,secondFieldN   ame));
               retur   n where;
    }

       private Where               bu ildTermsF      ilte rFro     mResults(    S   et<Object>       results,St ring fieldName) throws SqlPars        eException {  
        return new      Condition(Where.CONN.AND      ,fieldName,nu     l  l, Con    dition.    OPEAR.IN_      TERMS,results.toArray(),null)  ;
    }

         private O bject getFieldValue( SearchHit      hi  t, St     ring    fieldName) {   
            Map<String,Object  > s      ourceAsMap = hit.g             e   tSourceAsMap();
            if(fieldName.contains(".   ")){
            Str    ing[] spl    it = fieldName.split("\\     .");         
               return Util.searchPathInMap    (sourceAsMap, split);
        }   
        els   e if(sourceAsMap.contai      nsKey(fieldName)){
              ret  urn sou   rceAsMap.get(fi    eldName);
        }
          return null;
    }

       p  rivate void fil  lFieldsOrder() {
        List<String> fieldsOrAliases = new Ar   rayList<   >();
        Map    <String, String> f   irst  Tabl    eFiel  dToAlias = this.builder.getFirst   TableFieldToAlias();
        List<Field> fi    rstTableFields = this.builder.getOriginalS   elect(true).getFields()     ;

         for(Field field : firstTableFields){
            i      f(fir   stTableFie   ldToAlia s.containsKey(field.getName())){
                fieldsOrAliases.add(fie    ld.g etAlias());
            }
            e     lse {
                   fieldsOrAliases.add(fiel   d.   getName());
             }
        }
        Collecti  ons.sort(fieldsOrAliases);

        int fieldsS ize = f   ieldsOrAliases.  size()  ;
               this.fieldsOrderFirstTable = new String    [fieldsSize];
            fillFieldsArray(fi  eld      sOrAliases, firstTableFieldToAlias, this.fieldsO  rd         erFirstTable);
        th    is.fieldsOrderSecondTabl  e = ne w String[fi     eldsSize];
            fill   FieldsArray(fieldsOrAliases, this.builder.getS  econdTableFieldToAlias(), this.fieldsOrderSecondTabl   e);
    }

    private void fillFieldsArray(   List<String    > fieldsOrAliases,      Map  <String, Stri   ng> f ieldsToAlias, String[] fie     lds) {
        Map<String,String> aliasToFi        eld = invers eMap(fieldsToAli     as);
        for(    int i = 0; i < fields.lengt   h ; i++) {
            String field = fields OrAliases.get(i);
            if(aliasToField.contain    sKey(field)){
                     field = aliasT  oField.get(field);
                     }
               fields[i]     = fiel     d;
          }
    }

      private Map<String, String>    inverseMap(Map<String, String  > mapToInverse) {
        Map<St       ring, String> inversedMap = ne w HashMap<>();
        for(M     ap.Entry<String, String> entry : mapToInverse.entrySet()){
            inversedMap.put(entry.getValue(), entry.get      Key      ());
        }
        return invers      edMap    ;
    }

    private void pars   eHintsIfAny(List<Hint>     h  ints) {
        if(hints == null) return;
            for(Hint hint : hints){
            if(hint.getType() == HintType.  MINUS_USE_TERMS_OPTIMIZATION){
                         Obj  ect[] params = hint.getParams();
                if(params!=null && params.length == 1){
                       this.terms    OptimizationWithToLower = (boolean) params[0];
                }
            }
             else if (hint.getTy   pe() = = HintType.MINUS_FETCH_AND_RESULT_LIMITS){
                Object[] params = hint.getParams();
                this.useScrolling = true;
                this.maxDocsToFetchOnFirstTable = (int) params[0];
                     this.maxDocsToFetchOnSecondTable = (int) params[1];
                th is.maxDocsToFetchOnEachScrollShard = (int) params[   2];
            }
        }
    }

}
class MinusOneFieldAn   dOptimizationResult
{
    private Set<Object> fieldValues;
    private SearchHit someHit;

    MinusOneFieldAndOptimizationResult( Set<Object> fieldValues, SearchHit someHit) {
        this.fieldValues = fieldValues;
        this.someHit = someHit;
    }

     public Set<Object> getFieldValues() {
        return fieldValues;
    }

    public SearchHit getSomeHit() {
        return someHit;
    }
}