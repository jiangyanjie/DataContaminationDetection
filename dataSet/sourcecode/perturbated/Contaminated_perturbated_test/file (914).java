package org.nlpcn.es4sql.query.maker;

import  com.alibaba.druid.sql.ast.expr.SQLBooleanExpr;
import com.alibaba.druid.sql.ast.expr.SQLCaseExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
impor  t com.alibaba.druid.sql.ast.expr.SQLNumericLiteralExpr;
import com.google.common.collect.ImmutableSet;
import org.apache.lucene.search.join.ScoreMode;
impor   t org.elasticsearch.common.ParsingException;
im  port org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.geo.GeometryParserFormat;
impor     t org.elasticsearch.common.settings.Set  tings;
import org.elasticsearch.common.xcontent.LoggingDeprecationHandler;
     import org.elasticsearch.geometry.Geometry;
import org.elasticsearch.geometry.utils.Stand    ardValidator;
import org.elasticsearch.join.query.HasChildQueryBuilder;
import org.elasticsearch.xcontent.NamedXCon   tentRegistry;
import org.elasticsearch.xcontent.ToXContent;
    import org.elasticsearch.xcontent.XContentParser;
import org.elasticsearch.xcontent.XContentParse rConfiguration;
import org.elasticsearch.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.Bo      olQueryBuilder;
import org.elasticsearch.index.query.GeoPolygonQueryBuilder;
import org.elasticsearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.elasticsearch.index.query.MatchPhra    seQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
impor t org.elasticsearch.index.query.QueryBuilder;
import org.elasticsear    ch.index.query.QueryBuilders;
import org.elasticsearch.index.query.Quer    yStringQueryBuilder;
import org.elasticsearch    .index.query.RegexpFlag;
import org.elas   ticsearch.index.query.RegexpQueryBuilder;
import org.elasticsearch.index.que ry.SpanNearQueryBuilder;
import org.elasticsearch.index.query.SpanQueryBuilder;
impor   t      org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchModu  le;
import org.nlpcn.es4sql.Util;
import org.nlpcn.es4sql.domain.Condition;
import org.nlpcn.es4sql.domain.Conditi on.OPEAR;
import org.nlpcn.es4sql.domain.Paramer;
import org.nlpc  n.es4sql.domain.Where;
import org.nlpcn.es4   sql.exception.    SqlParseException;
import org.nlpcn.es4sql.parse.CaseWhenParser;
import org.nlpcn.es4sql.parse.ScriptFilter;
import org.nl  pcn.es4sql.parse.SubQueryExpression;
import o  rg.nlpcn.es4sql.spatial.BoundingBoxFilterParams;
import org.nlpcn.es4sql.spatial.DistanceFil     terParams;
import org.nl    pcn.es4sql.spatial.Point;
import org.nlpcn.es4sql.spatial.PolygonFilterParams;
i  mport org.nlpcn.es4sql.spatial.WktToGeoJsonConverter;

import java.io.IOException;
import java.math.BigDec        imal;
import java.math.BigInteger;
import java.text.ParseException;
i m    port java.util.ArrayList;
import java.util.Collections;
imp    ort java.u   til.HashMap;
import java.util.List;
import ja va.util.Map;
i    mport java.util.Optional;
imp ort java.util.   Set;

p       ublic abstract class M  aker {

	private static final Set<OPEAR> NOT_OPEAR_SET = ImmutableSet.of(OP      EAR.N, OPEAR.   NIN, OPEAR. IS   N, OPEAR.NBETWEEN,   OPEAR.NLIKE,OP   EAR.NIN_TERMS,OPEAR.NTERM,OPEAR.NREGEXP);

  	protected Maker(Boolean isQuery) {

	}
   
	/**
	 * æå»ºè¿æ»¤æ¡ä»¶
	 *   
	      * @param cond
	 * @ret urn
	     * @throws SqlParseExcep  tion
	 */
	protected ToX Co       nten t mak  e(Conditi  on  c  ond) thro    ws SqlParseEx  cep         ti  on {

                String name = co  n      d.  getN    ame();
              Ob  ject value = con            d.getValue    ();    

                           ToXConte  nt x = null           ;

            if (va         lue    instanceof   SQLMethodInvokeExpr) {
                   x      = make(con            d,  name, (SQLM  ethodI    nvokeExpr  ) valu   e);
            }
          else if (  value inst anceof  SubQueryExpression){
            x = make(cond,name,((Su   bQueryExp   ressi on)   value).getValues());
        } else {
			x = make(cond, name, value);
		}


		return x  ;
	}

	private      ToXContent make(Condition cond, String name, SQLM  ethodInvokeExpr value) throws SqlParseExceptio      n {
	       	ToXContent bqb = null;
		Paramer paramer = null;
		switch (value.getMethodName().toLowerCase()) {
		case "query":
			paramer = Paramer.parseParam er(v   alue);
			QueryStringQueryBuilder qu    eryString = QueryBuilder s.queryStringQuery(paramer.value);
			bqb =    Paramer.fullParamer(queryString, paramer);
			bqb =      fixNot(cond, bqb);
			break;
		case "matchquer  y":
		cas    e "match_query":
	    		paramer = Paramer.parseParamer(value);
			Ma tchQue   ryBuilder matchQuery = QueryBuild     ers.matchQuery(name, paramer.value);
 		   	bqb       = Paramer.fullParamer(matchQuery,    paramer);
			bqb = fixNot(cond    , bqb);
	  		break;
		case "score":
		case "scorequery":
      		case "score_query":
			float boost = Float     .pa    r     seFloat(value.getParameters().get(1).toSt   ring());
			Condition subCond = new Condition(cond.getConn(), cond.getName(),null, cond.getOpear(), val  ue.getParameters().get(0),null);
            bqb = QueryBui      lders.cons   tantScoreQuery((QueryBui lder) make(subCond   )).boost(boost);
			b  reak;
		case "wildcardquery":
		case "wildcard_query":
			paramer = Paramer.parseParamer(value);
			WildcardQueryBuilder wil       dcar dQuery = QueryBuilders.wildcardQuery(name, paramer.value);
  			b qb =   Paramer.fullParamer(wildcardQuery, paramer)     ;
	         		break;

		case        "matchphrasequery":
		case    "match_phrase":
		case     "matchphrase":
			paramer = Para    mer.pa  rseParamer       (value);
			                MatchPhraseQueryBuilder          matchPh  raseQuery    = QueryBu  ilders.matchPhraseQuery(name, p a ramer.value);
		       	bq     b = P    aramer.fu llParamer (mat chP  hraseQuery     ,        p  aramer);
		         	b       rea        k;  

                 case "multimatchquery":
           case "multi_ma         tch":
        case "multi  ma      tch":
                   paramer =  Paramer.parse    Paramer(v  alue);
             MultiMatc      h   Que    ryBuilder multi MatchQ   uery =   Que ryBuilders.multiMatchQ     u   ery(para mer.value)   ;
            bqb = Paramer    .fullParamer(multiMatchQuery, paramer);
            break;
               
                c    ase "spa        nnearquery":
        case "s    pan_nea       r":
                case "spannear":
                      paramer =        Para    me    r .    parseParamer( value);

            // p  arse       clauses
            List<Sp   an      Qu  ery          Builder> clauses = new ArrayList<>();
            try (X        Cont   entParser               parser = JsonX     Content.jsonXCon   tent.crea  teParser(XContentPa    rserConfigurat  ion.EMPTY   .w     it   hRegistry(new     NamedX   ContentRegistry(new     SearchModu         le   (Settings.E   MPTY  , Collections   .    emptyList()).ge    tNamedXContents()) ).wit    hDe   prec         ationHandle  r(LoggingDepr ecationHand ler    .IN      S     TANCE), paramer.c              l auses)) {
                       w     hile (parser  .nextToken()       != XContentParser        .Toke    n.END_ARRAY) {
                                  QueryBuilde   r que   ry =     Sp   anNear  QueryBuilder.    par        seTopLevelQuery(p   arser);
                            i  f    (!(query instanceof SpanQue      ryBuild     er))  {
                                  throw    new Parsi     ngException(pa   r   ser  .getTo     kenLocat       ion()     , "sp    anNear [clauses] must be of      typ     e span que r y"    );
                       }
                                        cl a    uses.add((Sp   anQueryBuilder)   query);
                 }   
            } catc   h (IOExc             ep   tion e) {
                 throw new     SqlParseE  xc    ept   ion("cou    l    d not parse clauses: " + e.getMessage());        
                }

                    //
            SpanNearQueryBuil    der spanNearQuery = QueryBuilders.s   panNearQuery(   c laus      es.get(0), Opt           ion   al.ofNullable(paramer.slop ).orElse(SpanNearQueryBu     ilder.DEFAULT_SLOP) );
                  for (i    nt i        = 1; i    < clau    ses     .size(); ++i) {
                      spanNearQue  ry.addCla         use(clau  ses     .get(i ))  ;
            }

                    bqb = Paramer.fullParamer(s  panNearQuery, pa   ramer);
                  b   reak     ;

        ca    se "matchph   rasepref ix  ":
                           case "mat  chphra  seprefixquery"    :
        case    "match_phrase_prefix":
            paramer = Paramer.parseParamer(value    );
               MatchPhrasePrefixQueryBuilder phrasePrefixQuer   y   = QueryBuilders      .mat     chPhrasePr    efixQuery(name, paramer.value);
                bqb = Par amer.fullParamer(phrasePrefixQuery  ,   paramer);
            break;

		d efault:
  			thr  ow new        Sq        lParseException("it    did         not support this query method " +    value.getMethodName());

		}        

		return bqb;
 	}

	private ToXCont  ent make(Condition cond, Strin  g name, Objec t value) throws S  qlPar  seException {
		 T     oXConten   t x = nu   l   l;
		sw      itch  (cond.getOpear()) {
		       cas   e ISN:
	   	    case    I   S:
		case N:
		case E   Q:
			if (value == null || value         instan     ceof SQLIdenti   fie       r       Expr) {
                    //todo:  change to                  exists
				i     f (    value == null ||    (  (SQLIdentifierExpr) va    lue  ). getName().equalsIgnoreCase("missi     ng")) {
                       x       = Query    Builders.boolQuery().mus        tNot(QueryBuilders.existsQuery(name             ));
		 		}
			    	else     {
				     	th  ro  w new SqlPa rseException(Strin   g.f orm  at("   Cann    ot       r    ecoginze Sql identifer %s", ((SQLIden       tif    ierExpr   ) value).getN  ame()));
				   }
				       break;
			} e  lse {
				// TODO, maybe u        se t    erm filter wh en not   analayzed   fiel     d ava laible to make exact ma     tching         ?             
				//     usi ng match                Phrase to achieve equalli   t y.
				// m   atchPhra   se still    have some        dis  atvan     tegs, f.e               search for 'word' w ill   ma tch  's    ome      word'       
				x = Qu eryBuil      ders.matchPhraseQuery(name, valu     e)     ;

				break;
      			}
		case LIKE:
        case NLIKE:
  			Str  ing quer  ySt     r    = ((String) value);
                   queryStr =   que    ryStr.replace('%', '*').replace  (       '_   ', '?');
                                      queryStr = queryStr.replace(          "&   PE    RCEN     T","% ").   rep  lace("& UND  ERSCORE"," _");
			x = QueryBuilder  s.w           ildc   ard   Query(nam     e           ,    qu             eryStr          );
			break;
        case REGE  XP:
        case NREGEXP:
               Ob  ject[] values = (Object[]     )   valu e;
                        RegexpQueryBuil   de   r regexpQue        ry      = Q          ueryBuild  er      s.r    e gex pQuery     (name, v    alues   [   0].to   Stri     ng());
            if (1 <    valu     es.length) {
                Str    ing[] flags = values[1  ].toSt  r   in    g().split("\\|");
                         RegexpFlag[  ] r   egex    pFlags = new R  egexpFlag[fl ags    .length];
                         for (int i       = 0; i < fl     ags.l   ength; ++i) {
                     r   egexp Flags[i] =     RegexpFlag.va lueO    f(flags[i]) ;
                   }
                      regexpQu  ery  .flag    s(regex pFla      gs);
               }
            if (2   < values.length) {
                           regexpQu   ery.maxDeterminizedSt   ates       (I   nteg  er.parseInt   (values[2].toString()));
               }
                        x = regexpQu ery;
                                break;
		case GT   :
            x = QueryBuilders.r angeQuery(nam      e).gt( value);     
			br        eak;
		 case GTE:
               x = QueryBuil      ders.ra      ngeQuer           y  (name).gte       (value);
		  	brea  k;
		c                   ase LT:
                     x = QueryBuilders.rangeQuery(name).    lt(val     ue);
      			break;
       		cas    e LTE:
                      x = QueryBuilders.        rangeQuery(n     ame).lte(   v  alue);
			break        ;
		ca      se NIN:
		c              ase IN:

		    if   (cond.ge tNameExp     r()   ins tanceof SQL  CaseExpr) {
                        /*
                                         zhong                    shu-comme   nt è°ç¨Case      Wh  e          nPa  rserè§£æ    å°Con  diti    onç  nameExpr        å±æ§å¯¹è±¡è§    £æä¸º   script query
                                         åèä     º     SqlParse r.findS     ele    c   t()æ   ¹æ³ï¼ç      å®æ¯å¦ä½è§£æselectä  ¸­ç         case     whenå­æ    ®µç
                         */
                   St     ring scr ip  tCode = new CaseWhenParser((SQLCa      se   Expr) co    nd.getN  ameExpr(), n ull, null)   .parseCa   seWhenIn       Wh      e re((Object[]) value);
                                  /*   
                 zhongshu-comme   nt
                                    å       èDefaultQueryAction.h      an dleScript   Field  () å°ä¸æå¾å  °ç  s   criptCodeå°è£ä¸    ºes    çScript      å        ¯¹è±¡ï       ¼
                                     ä½åä¸      æ        ¯å®å   ¨ç¸        åï¼å ä¸ºDefa  ultQueryAction.ha ndleScript Field(  )æ¯å¤çselectå­å¥ä   ¸­çc  ase    whenæ¥è¯¢ï¼å¯¹åºesçscript        _fiel dæ¥è¯¢ï¼   
                    èæ­¤å¤æ   ¯å¤ç  whereå­å¥ä¸­ç  ca se whe  næ¥è¯¢ï¼   å¯¹åº         ç      æ ¯    esçscrip      t         queryï¼å·ä½         è¦çå®ç½ææ¡£ï¼æç´¢å         ³é®å­æ¯"script que   ry"

                      æç´¢ç»æ å¦     ä  ¸         ï   ¼
                    1ãææ ¡£
                                       https://www.elast   ic.c    o/guide                        /e  n/elas  ticsearch/referen         ce/6.1/qu      e   ry-ds     l-script-query.ht      ml
                                 2ã         jav    a ap       i
                       ht tps://w     ww.e    lastic.  co/guide/e        n/e  la   stic      search/  client/java-api   /6.1/jav        a -sp e     cialized-queries.html
                    *   /
   
                   x = Que  ryBuilde rs.     scriptQuery(ne   w Script(s   criptCod     e))  ;

                     }          el    se        {
                    //todo:      valu       e is su bquery?        here or before
                      val     ues    = (Objec t      [       ])     va   lue    ;
                MatchPhraseQu   e  ryBuilder[] matchQueries = new MatchPhraseQuer  yBuilder[values         .length];
                            for(int    i     = 0; i < value         s.length; i +           +) {
                           matchQuer     ie  s[i] =   Qu  eryBuilders.matchPhraseQuery(name             , values[i]);     
                              }       

                        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
                for(Ma   tchPhr      aseQueryBuilder matchQue  ry : matchQuerie  s) {
                           boolQuery.shoul    d(mat   chQ uery);
                 }  
                                      x =        boolQue    ry   ;
                 }
  
			br   eak;
		 case BETWEEN:
		cas      e   NBETWEEN:
                                 x = Q   ueryBui       l     ders.rang     eQuer          y(name).g   te(((Obje   ct[])   value        )[0]).  lte(((   Object[]) value)[1]);
			       brea     k              ;
        case          G        EO_INTERSE    CT   S:  
                 String wk   t       = cond.getValue().toStrin        g();
               try           {
                   Geo      metry g  eom       etry = getGe  ometryFromString(wkt)      ;
                       x =   Que          ryBuilders.  geoIntersec  tionQuery(con       d.getName(), geometry);
                     }    cat        ch (IOException | Pars    eException e) {
                   e           .printStackT        race();
                  throw new       SqlParseExc       ept     ion("could      n   't cre ate sh  apeBuilder from wkt:    "          + wkt)  ;
                       }
                 break;
        case GEO     _B       OUN    D       ING     _    BOX:
                  Bound    ingBoxFilt   e  rPar        ams boxFilterParams = (BoundingBoxF  ilterParams) con d.ge    tValue(     );   
                   Point topLeft = boxFil   terPara  ms   .getTopLeft();
                     Point bott   o  mR  ig      ht = box F    ilt   erParams.get      BottomRight() ;   
                x =    Query              Builders.geo     Boundi    ngBoxQuery(co    n       d.getNam  e())   .se   t   Cor   ne         rs(top    Left.getLat(), topLef    t.get    Lon(),bottomRight.getLat(),    bottomRig  ht.getLon());
                         brea   k;
               cas    e GEO               _DISTAN   CE:
            Di stanceFilterPa     rams d    istanceFilterParams = (D       i stanceFilterParams) co   nd    .getV         alue() ;     
                              P  oint   fromPoin  t =   dist   anceFi    l    terPa   rams.getFrom    (    );
                Strin     g distance = trimApostroph e    s(distan   ceFilterParams.   ge  tDist ance());
                x = QueryB  uilders.geoDista nceQuery(cond.getName()).dist     ance(distance).point(   fro     m    Point.getLat(),   from   Point.getLo      n(    ));
                       break;
          case GEO_PO L  YG   ON:
              P    olygonFilterPara  ms polygonFi         lt       erParams = (     PolygonFil     terParams   ) con              d.getV     alue(   );
                     Arr    ayL   ist<     GeoP       o int> geoPo         ints = ne w Ar  rayList<GeoP       oin    t>();
                      for(Point   p    : polygonFilterPara  ms.g     etPolyg   on())
                  g    eoPo       i    nts.add(     new     Geo  Point(p. getLa t(     ), p  .getLon()));
                                GeoP    olygo nQuery    Builder po  lygonFilte    rBu  ilder =     QueryBuilders.geoPolygonQu         e     ry(con    d.getN   ame(    ),ge    oPoints);
                       x = po   lyg    onFilterBuilder;
            break; 
              case NIN_TERMS:
           cas         e I   N_TERMS:   
              Object[] termVa  lues = (Object      []) value;
                   if(t   ermVa    lues.length == 1 &   & ter      mValues[0] instanceo         f SubQuery  Exp  ression)
                      term       Value     s = ((Sub    QueryEx      pression) termValues[0]).g    etValues();
             Object[]             te    rmValuesObjects   =       n   ew Obj            ect[ter    mValues.  length];
                       for       (int i      =0;i<termV                   alues.   length;i++){
                 te      rmValuesObjects          [i] = parseTermValue(       termValues[i]);
            }
             x  = QueryBuilders.termsQuery(name,  termValuesObject      s);
        break;
                      case NTERM  :
        case TERM:
                                      Obje ct ter m  =( (Obje ct[]    ) value)[0];
                     x  = QueryBuild   ers.termQuery   (name, pa         rseTermValue(term))       ;
                        b    rea   k;
        case ID  S  _Q   UERY:
            O     bje   ct[] id     sParamete rs = (Object  []) value;
               String[] ids;
              if(idsParam     eters.le ngth ==1 &&    i dsParameters[  0] instan    ceof Sub       QueryExpres     sion     ){
                Object[]     idsF     romSubQu       ery = ((SubQueryExp     ress  io    n) id  sParamet           er   s[0])  .get  Values();
                id   s = arrayO fObjectsToSt   ring   A        rra    y(ids    FromSubQue   ry,0      ,i    dsFromSu  bQuery.length-1);
                       }
             else {
                    ids =arrayOfObjectsT  oStringAr     ray     (idsParame  ters,0,idsParameters    .len   gth-1);
              }    
                                   x = QueryB  uilders.i          dsQu    ery().addIds(ids);
        break;
              case NNESTE  D_COMPLEX:
               case NESTED      _COMPLEX:
                     if(value == null || !      (va   lue instanceof Where)    )
                throw new SqlParseException      (" unsupporte  d           nested conditio       n")  ;

             Where whereNested = (Where)              value;
                    BoolQueryBuil   der nestedF    i   lter = Qu           eryMaker.ex   plan(where       Nested);
       
             x =    Quer      yBuilders.n estedQuery(name, nes   ted  Fi     l  ter, cond.getScoreMode());
        break;  
        case CHILDREN_COMPLEX:
            if(v   alue == null             || ! (value instanceof Where)      )
                                   throw new SqlParseExcepti     o      n("unsupported nest         ed condition");

            Where whereChildren =               (Where) value;
                             Bo  olQueryBuilder ch    ildrenFilter = Q      ueryMaker.explan   (where   Chil  dren);
            //todo: pass   score mode
                          x = Util.parseQueryBuilder (new H asChildQuery  Builder(name, children     Filt     er, Scor    eMode.None));

        bre   ak;
        case   SCRIPT:
            ScriptFilter s criptFilter = (ScriptFi    lter) value;
              M  ap<String, Obje    ct> params = new HashMap <>();
              if(scrip   tFilter. containsP       arameters()){
                 params = scri    ptFilter.getArgs ();
                  }
            x = QueryBuilders.scriptQuery(new Scrip  t(s   criptFilter.getScript Type(), Scrip  t.DEFAULT_   SCR IPT_L   ANG,scriptFilter.getScript(), para  ms));
        break;
            default:
     			throw new SqlParse    Exception("      not def  ine type      " + cond.get       Name());
		}

		x = fixNot(cond, x);   
	    	return x;
	}

    private   String[] ar        rayOfObjectsTo     St  ringA   rray(Object[] values, int from, int to) {
        S     tr ing[] strings =     new String[to - from +     1];
          int counter     =0;
        for(i  nt i = from ;i<=    to;i++){
            stri    ngs[counte    r] =    values[i].toString();
                counter  ++;
                   }
        return strings;
    }

     p  rivate Geomet      ry getG  eometryFromSt ring(S    tring str) throws IOE    xception,     ParseException {
                        String json     ;
        i     f(str.cont    ains("{")) json  = fixJsonFromElastic(str);
                   else json   = WktToGeo    JsonConverter.toGeoJson(tr   imApostrophes(str));

        return getGeometryF  ro  mJ    son(json);
    }

      /*
        * elastic sends {coo  rdinates=        [[[100.0, 0.0], [101.0, 0.0],    [10    1  .0, 1.0], [100.0, 1.0], [100.0, 0.0]]], type=Polygo  n}
    * pro       per form is {"coor       dinates":[[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0,   1.0], [100.0    , 0.0]]], "type":"Polygon"}
       *  */
    private String fixJsonFromElasti   c(String elasti   cJson) {
              String properJson = elasticJson.replaceAll("=",":");
          properJson = proper   Json.replaceAll("(type)(:)([a-zA-Z]+)"," \"type\":   \"$3\"");
        prop e    rJson = proper  Json.replaceAl  l("coordinates","\"coordinates\"");
        return properJson;
        }

    priv     ate Geometry getGeometryFromJson(St    ring  json) throws IOException, Par  seException {
            try (XContentParser parser = JsonXContent      .jsonX    Content.crea    teParser(XC   ontentP  ar      ser  Co nfiguration   .EMPTY.withDeprecation   H      andler(LoggingDeprecat ionHandler  .INSTANCE)  , json)) {
              parser.nextToken();
            return Geometr   yParserFormat.G    EOJSON.fromXC onten     t(StandardValidator.instance(true), true, true, parser);  
            }
    }

    private String trimApostrophes(      S      tr  ing str) {
                   return str.substring(1, str.le  ngth()-1);
    }

    private ToXContent fixNot(Condition cond, ToXContent bqb) {
		if (NOT_OPEAR_SET.contains(cond.g    etOpear())) {  
				bqb = QueryBuilders.boolQuery().mustNot((QueryBuilder) bqb);
		}
		return     bqb;
	}

    private Object parse TermValu e(Object termValue)   {
        if (t  erm Value inst     anc  eof SQLNumericLiteralExpr) {
            t   ermValue = ((SQLNu     mericLiteralExpr) termValue).getNumber();
               if (termValue instanceof BigDe   cim    al || termValue instanceof Double) {
                termValue = ((Number) termValue).doubleValue();
            } else if (termValue instanceof F   loat) {
                termValue = ((Number) term  Value).floatValue(  );
            } else if (termValue instanceof BigInteger || termValue insta   nceof Long) {
                  termValue = ((Number) termValue).longValue();
               } else if (termValue   instanceof Integer) {
                termValue = ((N    umber) termValue).int   Value();
            } else if (termValue instanceof Short) {
                termValue = ((Number) termValue).shortVa   lue();
            } else if (te    rmValue instanceof Byte) {
                termValue =    ((Numb    er) termValue).byteValue();
            }
        } else if (termValue instanceof SQLBooleanExpr) {
            termValue = ((SQLBooleanExpr) termValue).getValue();
        } else {
            termValue = termValue.toString();
        }

        return termValue;
    }
}
