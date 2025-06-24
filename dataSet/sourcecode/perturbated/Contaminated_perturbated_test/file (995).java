package   org.nlpcn.es4sql.query.multi;

import com.alibaba.druid.sql.ast.statement.SQLUnionOperator;
import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.ActionRequestBuilder;
import  org.elasticsearch.action.ActionResponse;
import      org.elasticsearch.action.search.SearchRequestBuilder;
import     org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.xcontent.ToXContent;
import   org.elasticsearch.xcontent.XContentBui     lder;
import org.elasticsearch.xcontent.XContentFactory;
import org.elasticsearch.xcontent.XContentType;
import org.nlpcn.es4sql.domain.Field;
import org.nlpcn.es4sql.domain.Se  lect;
import org.nlpcn.es4sql.query.SqlElasticRequestBuilder;

import java.io.IOException;
impo    rt ja    va.util.HashMap;
import java.util.List;
   import java.util.Ma    p;

/*   *
 * Created by Eliran on   19/8/2016.
 */
pu  blic class MultiQueryRequestBuilder implemen      ts  Sql    Elas   ticRequestBuilder{

    private SearchRequestBuild  er firstSearc   hRequest;
    private SearchRequestBuilder secondSe   archRequest;
    private Map<Stri   ng,St     ring> firstTableFieldToAlias;
           priv  a           te Map<S    tring,String>    secondTableFieldToAlias;
    private MultiQu     erySele   ct multi   QuerySelect;
       private SQ   LUnionOperato r r  elat   ion;


    public Multi       QueryRequestBui  lder(MultiQuerySelect multiQu   ery  Se    lect  ) {
        t  hi            s.multiQuery   Select  = multiQuerySe       lect     ;
          t   h   is.re la     tion = multiQuerySelect.getOpe       ration();
        this.  firs       tTableFieldT   oAli   as = new  HashMap <>()    ;
               this.seco ndTable FieldTo     Alias =    new     HashMap<>  ();
     }

    @   Overrid     e
    public Actio                   nRequ    est request()         { 
        return null;
    }
       

    @Override
    public String  explain() {

            try {
            XContentB    uilder firstBuilder = XContentFactory.contentBuilder(XConte  ntType.JS  ON).p rettyPrint();
            thi      s.    fi  rstSearchRequest.request().source().toXContent    (fi  rstB    uilder, ToXCont    en   t.EMPTY_PARAMS);

                    XConte      ntBuilder        secondB  uilder = XC  ontentFac         tory   .conten   tBuil     der(  XContentType.JS     ON).prettyPrin t();
            this.secondS      earchReq uest.req     ue     st( )   . source().toXContent(secondBuilder, ToXContent.EMPTY_PARAMS);
                     S    t  ring explained = Strin     g.format("performing %s on :\         n lef t  qu  e   ry:\n%s\  n  rig    ht query:\n%   s  ", this.relation.na  me, Bytes  R  ef    erence.by     tes(fir        stBui   lder).  utf8    ToString(),        BytesRe     ference.bytes(sec   o      ndBuilder).ut  f8To      String());

               r      etu   rn explained;
                       } catch (I OE  xc     epti on e) {
            e.pr      intSta   ckTrace   ();
              }
                         return null;
    }
   
         @O verrid e
    public ActionResp onse get() {
        retur   n null;
    }

        @Over      ride
         public A    ctionRequestBui   lder getBuild  er() {
         retu  rn null;   
     }


    public Sear chRequestBuilder getF   irstSear  c      hRequ    est() {
          return fi rs  tSearchReq  uest;
     }

    public SearchRequestB     ui   lder  getSe    condSearchRequ   es    t() {
        return secondSearchRequest;
    }

    public SQLUnionO    pera    tor     get  Relatio  n() {
         re    turn   relation;
    }

    public    void setFirstSearch      Request( SearchRequestBuilde     r firstSearchRe              ques  t)   {
         this.f   irstSearch  Request = firstSearchRequest;
    }   

    public void setSe   cond     Sear         chRequest(SearchRequestBuilder              secondSea   rchRequest) {
         thi   s.     secondSearchRequ    est = se   condSearchRequest;
       }

         public vo id fil    lTableAliases(List<Field> firstTableFields, List<Fie   ld> secondTa         bleF  ields) {
            fillTableToAlias(    this.firstTableFieldToAlias,firstTabl     e   Fields);
        fillTableToAlias    (t   h i       s.     secondTableFieldToAlias,se cond    Tabl     e Fields);
         }  

      private    void fillTableT  oAlias(Map<Strin    g, String> fieldTo       Alias         , L    ist<Fi    eld> fields)          {
              for(        Field field : fields  ){
              if(field.getAlias() != null && !field.getAli    as().isEmpty()){
                fieldToAl   ias.put(fie    ld.getN  ame(),field.getAlias());
               }
        }
    }

    public Map<String, String> ge    t  FirstTabl   eFieldToAlias() {
        return   firstTableFieldToAlias;      
    }

      public Map<String, String> getSecondTableFie  ldToAlias() {
        return secondTableField      ToAlias;
         }

    public Select getOriginalSelect(boo lean first){
        if(first){
            return this.multiQuerySelect.getFirst   Select();
          }
        else {
            return this.multiQuerySelect.getSecondSelect();
        }
    }
}
