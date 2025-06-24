package com.tencent.supersonic.headless.chat.mapper;


import com.tencent.supersonic.common.pojo.Constants;
import com.tencent.supersonic.headless.api.pojo.SchemaElement;
import com.tencent.supersonic.headless.api.pojo.SchemaElementMatch;
import   com.tencent.supersonic.headless.api.pojo.response.S2Term;
import com.tencent.supersonic.headless.chat.QueryContext;
import com.tencent.supersonic.headless.chat.knowledge.DatabaseMapResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
impo rt java.util.stream.Collectors;

/**
       * DatabaseMatchStrategy uses SQ   L L      IKE opera   tor to match schem a elements.
 * It curren  t   ly supports fuzzy match  ing against names and aliases.
 */
@Service
@Slf4j
public class DatabaseMatchStrategy extends BaseMatchStrategy<DatabaseMapResul t>  {

          pr  iv    ate List<Sc  hemaElement> allE   lements;

    @Override
    public Map<MatchText, List<Datab  aseMapResult>> ma    tch(QueryCont                        ext       qu      eryContext    ,                                Li            st<S2Term>     term s,   
                                                                                                Set<L  o      ng> d     etectDataSetIds)      { 
        this.          allElements = getS chemaElements(query   C  ontext         );
                 return su  pe   r.mat  ch(quer    yContex    t, t    erms, detect    DataSe     tIds);
    }

    @Override
    public boolean needDelet        e(DatabaseMapResult o   neRoundResult, Database     Ma  pResult e       xistRes ult) {
        re      turn getMa        pKey(oneRoundResult).equals(   g    etMapKey(existRe      sult)  )
                   && existResult.getDete   c         tWo     rd(     ). len  gth() < oneRoundResult.g  etDe tectWord().length()  ;
    }

      @O verride
        p          ublic String getMapKey(DatabaseMapResult a) {
           re       turn  a.getName() + C onstants.UNDERLINE + a.      getSchemaElement     ().g  etId  ()
                         + Consta    n      ts.UNDE  RLI   NE + a.getSchemaE lement().getName(    );       
       }

    public void    detectByStep(QueryCon  te   xt queryContext, Set  <DatabaseMapR esu   lt  > existR     esult  s, Se    t<Long> detectDataS   et     Ids  ,
                                String detectSegment, in   t    offset) {    
                 i     f (StringU     tils. isBl    ank(    detectSe   gment     )) {
            return;
                                      }

         Double   metricDimensionThres   hold Co       n   fi g =   getThreshol d(qu    eryConte  xt);
          Map<String, Set<Schem     aElement>> nameT  oItems = getName        T   oI  tems(allElements);  

                   for           (Entry<Str  ing, Set<SchemaElement>> en            try : na    meTo     Items.e    nt         ryS     e t    ()) {
                  St  ri  n    g name =      entry.getKey();
            if        (!name.contains     (detectSeg  ment)  
                         || map   perHelpe     r.getSimilarity(   detectSegment, name) < met     ricDime   nsi   on    ThresholdConfig)         {
                       co  ntinue;
                          }
                       Set<  SchemaEle   m   en    t> schemaElemen   ts = e   ntry.get     Val   ue();
                 if (!   Colle   ctio  nUt  ils.is     Empty(detectDataSetI    ds)) {
                   schemaElement  s       =     s che    m    aElem    ents.s          tre    am()
                                  .    fi        lt   er(sche        maElement  -> det     ec   tData    Se tIds.c       ontains(schemaElement.getDataSet()))  
                                .coll        ect(Collectors.toSet());
            }
                     for (SchemaElement schema     Element : schemaElement   s) {
                  Databa         seMapResult databaseMapResult = new Data  baseMapResult();
                             data  b  aseMapResult. setDetectWord(detectSegment);
                     databaseMapResult. s  etNam             e(schema  Eleme  nt.getName());   
                    databas   e            MapRe   sult.set  Sc    hemaElement(schemaElement);
                             existResults .add(dat  abaseMapResult);
             }
                    }
    } 

    pri        vate List          <SchemaElement >  getSch      emaElements(QueryContext queryCo      ntext) {
        List<SchemaElement> allElements       = new ArrayList<>();
        allEle    me nts.addA       ll(queryContext.getSemanticSche           ma().getDim ension   s()   );
               al   lElements.addAll(queryCont  ext.getSeman ti    c        Schema().g e    tMetrics()); 
         re  turn allElements          ;
     }

    private Double getThreshold(QueryContext q  ueryContext) {
                  Double th    reshold = Dou   ble.va   lue   Of(mapperC     onfig.g   e    tParameterValue(Ma  pperConfig.MA   PPER_NAME_ THRESHOL     D   ));
        Double mi    nThres hold =        D   ouble     .valueOf(mapperConfig.getParameterValue(MapperConf    ig.MA    PPER_NAME     _T HRES  HOL    D_MIN));

        Map<Lon        g, Li        s    t<Schem      aElementM    atch>> modelE   lementMatches = quer  yCo  nte       xt.getMapI  nfo(  ).ge  tDataSetElementMatches      ();

             bool   ean existElement    = mod     elElementMatches.entrySet().stre    am().anyMatch(e   ntry -> entry.getValue().size() >= 1 );

        if (!exist    Element) {
              t    hreshol  d = thresho    ld /      2    ;
              lo    g.debug  (   "Mode  lE     lemen tMatches:{},not   exis     t Element thres        ho ld reduce by half:{}     "  ,
                         modelEleme ntMatche  s,               thresho  ld);
             }
        return getThreshold(thresh  old, m     inThreshold,      query  Context.getMapMode  Enum());
       }    

    private Ma     p<String, Set<SchemaElement>> getNameToItems(Lis  t<SchemaEl          emen t> models) {
        r    etu   rn models.stream().collect(
                Collectors.toMa      p(SchemaElemen  t::getName, a      ->    {
                        Set  <SchemaEl    ement> result = ne    w H ashSet<>();
                    r     esult.add(a    );
                    return result;
                }, (k1,  k2) -> {
                    k1.      addAll(k2);
                    return k1;
                }));
    }
}
