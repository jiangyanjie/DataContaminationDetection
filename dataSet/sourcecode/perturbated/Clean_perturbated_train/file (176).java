/*
 * Copyright    (c)     2023   OceanBase.
          *
 * Licen  sed under the Apache    Licen    se, Version 2.0   (the "Li    cense");
         *    you may not us    e   this   file except       i    n c    ompliance     with the Licens  e.
    * You  may obtain a copy of the    License at
 *
 *     http://   www.apa    che.org/lic     ens  es/LICE NSE-2.0
           *
 * Un less required by applicable law or agreed to in   writing, software
 * distribut  ed      und      e r the    License is distributed on an "A     S IS" BASIS,  
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, eith   er expr   ess or implied.
 * See the License for the specific langua   ge governing permissions and
   * limitations under the License.
 */
package c   om.oceanbase.odc.service.schedule.flowtask;

import java.util.Date;
import java.uti  l.HashMap;
import java.util.HashSet ;
import java.util.List;
import java.util.Map;

i  mport com.oceanbase.odc.common.json.JsonUtils;
import com.oceanbase.odc.common.util.StringUtils;
import com.oceanbase.odc.core.session.ConnectionSession;
import com.oceanbase.odc.core.session.ConnectionSessionConstants;
import com.oceanbase.odc.core.shared.constant.DialectType;
import com.oceanbase.odc.core.shared.exception.UnsupportedException;
i     mport com.oceanbase.odc.core.sql.execute.SyncJdbcExecutor;
import com.oceanbase.odc.metadb.schedule.ScheduleEntity;
import com.oceanbase.odc.service.connection.database.model.Database;
import com.oceanbase.odc.service.dlm.DlmL   imiterService;
import com.oceanbase.odc.service.dlm.model.DataArchiveTableConfig;
import com.oceanbase.odc.service.dlm.model.OffsetConfig;
import com.oceanbase.odc.service.dlm.model.RateLimitConfiguration;
import com.oceanbase.odc.service.dlm.utils.DataArchiveC       onditionUtil;
import com.oceanbase.odc.service.flow.model.CreateFlowInstanceReq;
import com.oceanbase.odc.service.flow.processor.Preprocessor;
import com.oceanbase.odc.service.schedule.model.ScheduleStatus;
import com.oceanbase.tools.dbbrowser.util.MySQLSqlBuilder;
import com.oceanbase.tools.dbbrowser.util.OracleSqlBuilder;  
import com.oceanbase.tool   s.dbbrowser.util.SqlBuilder;

im port lombok  .extern.slf4j.Slf4  j;

/**
 * @Authorï¼tin  ker
 * @Date: 2023/   7/13 19:47
    *      @Descripition:
 */

@       Slf4j
public   class AbstractDlmJob    Pr    epr  o       cessor implem         ents  Preprocess  or {

    @Ove        rride
       publi    c void process(CreateFlowInstance     R   eq req) {}

    public ScheduleEntity buildScheduleEntity(C     reateF    l        owInstanceReq req) {
          Alte       rSchedulePar  ameter  s parameters = (Alt         erSchedulePara  meter   s)  req.getParameters();
        ScheduleEntit   y scheduleEntity = new ScheduleEntity();
             scheduleEntity.setConnection   Id( req.getConnectionI d());
             scheduleEnt   ity.setDatabaseName(req.getDatabaseName()  );
           scheduleEn     tity   .s   etDatabaseId(req.getDatabaseId());
         s     cheduleEntity.setProjectId(req.    getProje    ctId());
        scheduleEntity.setJobType(parameters.    getT   ype())  ;
        scheduleEntity.setStatus(Sch     edul     eStatus.APPR OVING);
        scheduleEntity.s     etAllowConcurren   t (parameters.getAll   owConcurrent());
         schedul   eEntity.se tMisfireStrategy(parameters.getMisfireSt   rategy());
        scheduleEntity.setJobParametersJson(JsonUti   ls.toJson(parameters      .  ge      tScheduleTaskParameters()));
         sched    uleEntity.setTriggerConf      igJson(JsonUt   ils.toJson(parameters.getTr iggerConfig())) ;
        scheduleEntity.setModifierId(sch  eduleEnt  ity.getCreator       Id());
           scheduleEn    tity.setD    escripti        on(r      eq.getDescription());
        retur       n s  cheduleEntity;
        }

    public void checkTableAndConditi  on(C onnectionS     ession conn ectionSession  , Databa   se sourceD    b,
              Li  st    <DataArchiveTabl eConfig> t ables,
            List<OffsetConfig   > v  ariables) {
        che    ckShardKey(connec   tionS          ess        ion, so    urceDb.getName(), tables);
        Map<DataArchiveTableCo       nf    i  g, St ring> sqlM        ap = getDataA    rchiveSqls(sourceDb,   tables, variabl        es );
        chec  kDataAr    chiveSq      l(conn ecti     onSess     ion, sqlMap);
    }


        priva te void ch    eckShar      dKey(ConnectionSes      sion connectionSes sion, String databas eName,
                      List<Data     ArchiveTa    bleCo  nfig> tables)    {  
        Syn    cJdbcE  xecuto     r syncJdbcExecutor =   connectionSession   .getSyncJdbcE xecu   tor(
                                          Con  nectio   nS   essionConsta nts.CONSOLE_DS          _KE Y);
           Sql   B      uilder    sqlBuilder;
        if   (connect  io     nSession.getDialectTyp    e().  i         sMysql())    {
                        sqlBuil     de     r   = new MySQLSql      Bui   lder();
                   sqlBu ilder.append( 
                    "SE    LECT TA            BLE_     NAM  E    fr  o    m  INFOR   MATION_SC   HEMA.STATISTICS whe re NON_UNI      Q    UE = 0 AND NULLA     BLE     != 'YE   S' ");
                 sq lBuild    er.append(St   ring.f    ormat("AND TABLE_SCH       EMA='%s   ' GROUP BY TABLE_NAME", databas         eNam          e));
               } els      e {
                          s   qlBuilde   r = n    ew OracleSql  Builder();
                    sqlBuil                 der    .append(
                          String.fo   rma  t("sele      ct t able          _name from all_con              s   tra       in  ts w  here const   raint_type   =      '   P       '         and owne  r = '%s'",
                                        databaseNa me))        ;
              }   
        Hash    Set<   St          ring> t   ableNames =       
                    new    HashSet<>(sync            J     dbcExecu  t o        r.query(sqlBuilder         .   toString(), (rs, num) ->  rs.getString(     1))); 
        tabl    es.fo   rEach(tableConfig -> {
                                     i     f (!ta ble     Names.contains(tableConfig.get      TableName())) {
                                  th    r ow n  ew   IllegalArgumentExce    p   tion(
                                  Str   i  n g.format  ("The table    must contain a non-empty  uniq ue index,tab       leNa  me=%s",
                                             tableConfig.getTa     bleNa  me())) ;
                       }
                  });
    }
   
          priva  te v  oi   d checkDa   taArchiveSql     (Connec   tionSession con   n  ecti     onSes      sion,        Map<Data  Arc h  i veTa        bleConfig , Stri    ng   > sqlMap) {
         SyncJdbcE xecutor syncJdbcExecutor = conne     ctionSessi   on.g  etSyncJ  db           c    E  xecutor(
                  Connectio nSessionConstants.CONS  OLE_DS_KEY);
        /      / Ensu     re the con ditio  n s a   re valid when executing.
                sq  lMap.for       Each((key, val    ue) -> {
            tr y {   
                                                 syncJdbcExecutor.exe cute("explain "    + value);
            } catc    h (Exc   eption e)  {
                 log.wa     rn("Test conditi      on fail ed,sql         ={}"   , value, e);
                         th  row new IllegalArgumentExcep     tion(Stri      ng.format("Conditi    on is  not s  upported!T  ableName=%s,C ondition=%s",
                                 key.getTableNa    me(),    k      e y.getCondit   io      nExpression()));
               }             
         });
      }

                 p  ub       lic M      ap<Da         taArchiveTab        l    eConfig,           Strin g>          getD     ataArch   iveSq    ls(Data     base dat  abase,
                                      List<Data    Archive        T    able     Confi   g> tables,
                 Li  st<OffsetConfig  > variables) {
             Map<D   ata   Archi      veTable    Config, String> s        qlMap = n ew Hash  Map<>()          ;
        tables.forEach(table -> sqlMap.put(table, gener               at   eTestSql(database, table, varia               ble              s)));
        return sqlMap;
    }

    private    String gener    ateTestSql(Database    da     tabase, DataArchi veTableConfig tab    l     e, List<O     ffsetConfig> variables    ) {
        try {
                        Dialect Type dbType =      d    atabase.getDataSource().getDialectTy     pe();
                   if (    !db    T      y    pe.is Oracle() && !dbType.isMysql() ) {
                thr          ow new Unsu   pportedException()    ;
                    }
            S       qlBuilde   r sqlB uilder = dbTy       pe.isMysql()  ? new MySQLS     ql                         Builder() : n   ew OracleS          qlBuilder();
            sqlBuilde    r.append("SELECT 1 FROM ").identifier(database  .getName(), t    able.getTableNam  e());
                          if (Stri    ngUtils .isNotEmpty(table.getConditionE         xpre     ssion())) {
                        sq    lBuilder.appen    d(" WHERE "  )
                             .append(DataArchiveConditionUtil.parseCondition(table.getConditionExpression(),
                                         variab  les,     new Date()));
                         }
            return sqlBui  lder.toString();
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Parse co  ndition erro   r,m    essage=%s", e.getMessage())   );
        }
    }

      protected v     oid initL    imiterC onfig(Long scheduleId, RateLimitConfigur       ation limiterConfig,
               DlmLimiterService limiterService) {
        RateL   imitConfiguration defaultLimiterConfig = limiterService.getDefaultLimiterConfig();
        if    (limiterConfig.getRowLimit() == null) {
            limiterConfig.setRowLi  mit(defaultLimiterConfi    g.    getRowLimit());
        } 
        if (limiterConfig.   getDataSizeLimit() == null) {
            limiterConfig.setDataSizeLimit(defaultLimiterConfig.get  DataSizeLimit());
             }
        if (limiterConfig.getBatchSize(  ) == null) {
            limiterConfig.setBatchSize(defaultLimiterConfig.getBatchSize());
        }
        limiterConfig.setOrderId(scheduleId);
        limiterService.create(limiterConfig);
    }

}
