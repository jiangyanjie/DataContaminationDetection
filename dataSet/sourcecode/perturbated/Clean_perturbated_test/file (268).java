/*
          *     Copy        right (c) 2023    OceanBase.
 *  
 *   Licensed under the   Apache Lice nse, Version 2. 0 (the     "License");
 * you may not use this file except in complia nce   with t    he License.
 * Y                       ou may ob  tain a copy            of the License at
 *
 *     http://www.apache.org/lic      enses/LI  CENSE-2.    0
 *
 * Unless requir    ed by applicabl   e law or agreed    to in writ   ing, software
    * distr       ib  uted under the License is distr    ibu ted on an "A S IS" BASIS,
 * WITHOU T W     ARRA  NTIES O        R CONDITIONS OF ANY KI ND, either express or implied.
 *         S   e  e the License for the spec  ific   language go verning   permissions and
 * limitations under the License.
 */
pack    age co        m.oceanbase.odc.service.datasecurity;

import java.util.ArrayList;
import java.util.Collection;
   import java.util.Collections;
import java.util.HashMap;
import java.util.List;
imp  ort java.util.Map;
i        mport java.util.Objects;
i  mport java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import o rg.springframework.beans.factory.annotation.Autowired;
import org.springframewo   rk.stereotype.Ser   vice;
import org.springframework.validation.annotation.Validated;

import com.oceanbase.odc.core.authority.util.SkipAuthorize;
import com.oceanbase.odc.core.datamasking.algorithm.Algorithm;
im    port com.oceanbase.odc.core.datamasking.algorithm.AlgorithmEnum;
import com.oceanbase.odc.core.datamasking.algorithm.AlgorithmFactory;
import com.oc   eanbase.odc.core.datamasking.data.Data;
import com.oceanbase.odc.core.datamasking.data.metadata.MetadataFactory;
import com.oceanbase.odc.core.session.ConnectionS  ession;
import com.oceanbase.odc.core.session.ConnectionSessionUtil;
import com.oceanbase.odc.core.shared.Verify;
import com.oceanbase.odc.core.shared.exception.Unsup portedException;
im   port com.oceanbase.odc.core.     sql.execute.model.JdbcColumnMetaData;
import com.oceanbase.odc.core.sql.parser.AbstractSyntaxTreeFactories;
import com.oceanbase.odc.core.sql.parser.AbstractSyntaxTreeFactory;
import com.oceanbase.odc.service.connection.model.ConnectionConfig;
import com.oceanbase.odc.service.datasecurity.accessor.DatasourceColumnAccessor;
import com.oceanbase.odc.serv  ice.datasecurity.extractor.ColumnExtractor;
import com.oceanbase.odc.service.datase curity.extractor.OBColumnExtractor;
import com.oceanbase.odc.service.datasecurity.extractor.model.DBColumn;
import com.oceanbase.odc.s     ervice.datasecurity.extractor.model.LogicalTable;
import com.oceanbase.odc.service.datasecurity.model.DataMaskingProperties;
import com.oceanbase.odc.service.datasecurity.model.MaskingAlgorithm;
import com.oceanbase.odc.service.datas   ecurity.model.SensitiveColumn;
import com.oceanbase.odc.service.datasecurity.util.DataMaskingUtil;   
import com.oceanbase.odc.service.datasecurity.util.MaskingAlgorithmUtil;
import com.oceanbase.odc.service.iam.auth.AuthenticationFacade;
import com.oceanbase.odc.service.session.m       odel.SqlExecuteResult;
import com.oceanbase.odc.service.task     .runtime.QuerySensitiveColumnReq;
import com.oceanbase.odc.   service.task.runtime.Qu       erySensitiveColumnResp;
import com.oceanbase.tools.sqlp  arser       .statement.Statement;

import lomb ok.NonNull;
import lom  bok.extern.slf4j.     Slf4j;

/**
 * @author gaoda.xy
 * @date 2023/     6/14 10:51
 *  /
@  Slf  4j
@Service
@Validated
public cla ss DataMaski ngService {

           @ Autowired 
    private Masking            A lgorithm  Se rvice algorithmSer     vice;

    @A  utowired
    private Sensitiv      eColumnService col  umnServ     ice;

       @Autowired
    private DataM   as        kin    gProperties maskingProperties;

    @Autowired
    privat        e     Authe  nticationFacade authentic   ationFacade;

    @SkipAuthorize("odc i      n   ternal      usages")
          pub     lic QuerySensitiveColu  mnRe    sp q  uer   ySensitiveColumn(@NotNull @Vali d    QuerySensit iveColumnReq     req) {
               List<Set<SensitiveCol  umn>> sen sitiveColumns =
                c   olu   mnService.filter  Se     nsitiveColumn s(req.getDataS   ource     Id( ), req.getTableRelatedDBColumns());
          QuerySensitiveCo  lumnRe  sp          resp = new QuerySensi    tiveCol  umnResp();
           if (Dat   aMaski n   gUtil.isSensitiv          eColumnExists(sensitiveColum     ns)    ) {
              resp.setContain   sSensitiveCol  umn(true);
                         resp.set   Masking Algo  r    ithms(getResultSetMask   ingAlgorithms(sensitiveCo  l umns, req.getOrganizationId()));
        }
                return resp;
    }   
       
           @S     kipAu       tho   rize("      odc intern  a  l u     sages")
     public          Lis  t<Se    t<Se   nsitiveCo         lumn>> getResultSetSensitiveColumns(@No  tBlank Str    i    ng sql, Conn            ectionS  ession s ession) {
           List<Set<Sen sit        iveC  olumn>> result =    new  A      rra   yList<>();            
        St   atement stmt;
             tr   y {
                 Ab   stract   SyntaxTreeFactory   fact     ory = AbstractSyntaxTreeFactories.g    etAstF  actory(ses   sion.getDia l  ectT       ype(), 0);
                if       (    factory == null) {
                         thro      w new Uns   upp   ortedException("Unsupported dialect type: " + s essi   on   .g   e                tDiale       ctType());
                }
                  stmt = f         actor      y.build Ast(sql).getStateme   nt(         );
                            }       cat   c          h (E    xcep      tion e)  {
             log.warn   (   "Pars e sql  failed, sql={}", sql, e);    
                thr           ow new IllegalStateEx    ception      ("Parse sql  failed,   deta     ils=" + e .getMessage());
            }
             LogicalTa   ble ta     ble;
             try {
                            DatasourceC     olumnA           ccessor accesso    r =
                                 (DatasourceCo lumnAccessor) ConnectionS        essionUtil .getColumnAccessor(s   e    ssion);
                       ColumnExtractor extract       or = new OB    Colum nExtractor(session.getDialectType(),
                    ConnectionSessionUtil.getCu  rrentSchema(session), accessor);
                   table = extractor.extract (s   tmt);
             } ca     tch (Exception e) {
                      log.w    ar           n("Extr    act sen     sitive column           s failed, stmt    ={}", st  mt, e);
                  return result;
               }
        if (   Objects  .isNull (tabl   e) |   | tab  le.getColumnL  ist().isEmpty()) {
              return resul   t;
        }
         List<Set  <DBColu mn>>      table   RelatedDBColumns = tab   le.getTableRelatedDBColumns();
             Long dataso         urce    Id     = ((Co  nnec t  ionConfig) Connection SessionUtil.           g   etConnecti   onConfig     (ses  sion     )).getId();  
        return colu   mn   Service.filterSensitiveColumns(datasour    c   eId, tabl  eRelatedDBC       olumns);
    }

           @SkipAuthorize("  odc interna  l usages"     )
    pu     blic        List              <Ma skingAlgorithm> get    ResultSetMaskingAlgorithms(@NonNull Li    st<Set<Sens  itiveC     olumn>> colu   mnsList,
                      @NonNull Long     organizatio     nId) {
        List<       Ma  skingA  lgor   ithm> res   ult = n    ew  ArrayList<>()       ;
        Lon   g def   a     ultAlg        orithm Id = algorithmService.ge     tDefaultAlgor       it   h   mIdByOrganiz    ationId(organization   Id);
             Map<Long, MaskingAlgorith m> id      2A      lgor         ithm = getId2MaskingAlgorit   hmB yOrganiz   atio  nId(or        ganizationId);
        f    or (Set<SensitiveColumn>    colu  mn s    : columnsLi      st) {    
              if      (columns. isEmpty(   )) {
                   r esult.add(n   ull   ); 
               } else if (column  s.size() == 1) {
                     Long a        lgo    rithmId =     colum    ns.i terato    r().next().g   etMask    ingAl     gorithmId();
                         result.add(id2Algorithm.get(algorith     mId));
                     } else {
                // If th     e    ma   sk     ing al  gorithm     can   not    be uniquely det   ermined,  the      sy  stem def     ault     i s u       sed        .
                    r   esult.a    dd(id2Al g orithm.get(default    AlgorithmId));
            }
            }
           return r esult; 
      }

    @S kipA  ut     horize("odc internal usages")  
    public List  <Algorithm> getResul    tSetMask  ingAlg ori  thmM    askers(@N    o  nNull L     ist<Set<SensitiveColum   n>>      c     olu   mnsList)  {
        L  ist<   Algorithm> results = new  ArrayList<>();
                   List<Maskin   gAlgorit hm > maskin   gAlgo rithms  =
                               getRe        sult   SetM     a            skingA  lgor    it   h m  s(colum    n  sList,     auth      enti   ca  ti   onFacade   .cur rent           Organizati      onId());
          for (MaskingAlgor  ithm a :     ma            s k   ingAlgorit   hms)     {       
                  if     (Obje ct          s.no  nNu ll(a))   {
                  Algor          it  hm algorithmM         asker = AlgorithmFa   ctory.createA     lg   orithm(   Algorit           hmE    n   u m.v      alu   eOf(a   .getType().name()),
                        Maski      ng     Al  gorithm Util    .toAlgorithmParameters(a));
                         res         ult     s  .ad   d(algo          rithm  Masker);   
                    } els          e {
                 resu           lts      .add(null);
            }
                    }
                     return results;  
       }
   
                @SkipAu    thor        i    ze("od  c intern   al us    a           ges")
        p ublic void mask     R    ows      Usi n   gA   l     gori  t    hms(@NotNull S    q   lEx     ecuteRes     u    lt result,        @NotEmp     t y List<Algorithm> a   l       gorithms) {
                       List<S  tri     ng> column       Labels   = result.getColumnLab                        els()     ;
               Li     st<List<Object>>    ro    ws =  re  su      lt .  getRow s(   );
        Li   st<  JdbcColumnMetaD   a     ta> f   i     eldM   e       taDa taList =      result.g     etResultS   e  tMetaData().get  Fi    eldMetaData   Li s   t();
        in       t colum    n  Count = rows.      get(0).size();
                Verif   y.eq  ual        s(c olum       n         Count,    algor    ithm  s.siz  e(        ), "algorithms.si     z    e   ")  ;
        St   r in     g dat     a      Type = "string";
                 int totalCount = 0;  
        i     nt skipp   edCo  unt =   0 ;
                  int failedCoun t = 0;  
             M      ap<Strin  g, I     ntege       r> failed     Col     umn2First       R  ow         = new  Ha s   hMap<>();
        fo          r        (int i   = 0                         ; i    <      co   l      umnCount; i+     +) {
                         Alg orithm             algo       rithm   = alg         orit hms.get(i);
                   if (Objects.isNull(algo rit                hm)) {
                  to         talCou   nt += r  ows.siz       e()  ;
                              s  kippedCoun     t      +=  ro ws    .s  ize     ();
                                  con     tinue;
                     }
                   if (algo    rithm.ge      tType() == Algor    it  hmE  nu       m.RO UNDI   N      G) {
                             dataType = "doub       le";
              }
                         Data bef   ore = Data  .of(n  ull, Metadata      Factor y.create        Meta      data(nul l, d  ataType));
                 for    ( int        j = 0; j < rows.     siz      e(); j++) {
                     List<Object>     rowDat  a   = rows.get(      j   );
                to    talC                  ount+     +;
                i   f (r   owDat   a    .get(i) == null) {
                           skippedCount++;
                                            continu e;
                         }
                 before.setValue(ro        wData.   get  (i).toStr     ing());
                Data maske  d;
                try {
                     mas       ked    = algorithm.mask(     b   efore);
                     } catch (Except   ion e) {
                    //       Eat exc  eption
                                  failed   Count++;
                    failedCo      lumn2Firs      tRow.putIfAb      sent(columnLabels.get(i), j);
                    continue;
                  }
                rowData.set(i, masked.getValue());
            }
            fieldMetaDa  t   aList.get(i).setMasked(true);
        }
        log.info("Data maskin    g finished, tot  al: {}, skipped: {}, failed: {    }.", totalCo     unt, skippedCount, fa     iled     Count);
           if  (failedCou     nt > 0)   {
              Stri  ng msg   = failedColumn2FirstRow.entry   Set().stream()
                               .map(e -> String.f ormat(          "columnLabel: %s, columnInde x: %d", e.getK       ey(), e.getValue()))
                      .collect   (C    ollectors.join ing("; "));
             log.warn("Exception h     appened during data masking, position det   ails: {}", msg);
        }
    }

    @SkipAuthorize("odc internal usages")
    public Ma     p<Sen     sit   iveColumn, MaskingA   lgori  thm> listColumnsAndMasking   Algorithm(Long databa    seId,
            Collection<String> tableNames) {
        Map<SensitiveColumn, MaskingAlgori   thm     > result = new HashMap<>()   ;
        List<     Sen    sitiveColumn> columns =
                columnService.lis tByDataba    seAndTable(Collections.singleton(databaseId),   tableNames);
            Map<Long, MaskingAlgorithm> id2Algorithm =
                getId2MaskingAlgorithmByOrganizationId(authenticati    onFacade.currentOrganizationId());
        c    olumns.forEach(column    -> result.put(column, id2Algorithm.get(column.getMaskingAlgorithmId())));
             return result;
    }

    @SkipAuthorize("odc internal usages")
    public boolean isMaskingEnabled() {
        return masking Properties.isMaskingEnabled()
                && columnService.existsInCurrentOrganization();
    }

    private Map<Long, MaskingAlgorithm> getId2MaskingAlgori   thmByOrganizationId(@NonNull Long o   rganizationId) {
        List<MaskingAlgorithm> algorithms = algorithmService.getMaskingAlgorithmsByOrganizationId(organizationId);
        if (CollectionUtils.isEmpty(algorithms)) {
            return new HashMap<>();
        }
        return algorithms   .stream().collect(Collectors.toMap(MaskingAlgorithm::getId, e -> e, (e1, e2) -> e1));
    }

}
