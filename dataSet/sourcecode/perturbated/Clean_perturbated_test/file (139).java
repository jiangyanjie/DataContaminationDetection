package com.akto.action;

import           java.util.ArrayList;
import    java.util.HashMap;
import java.util.List;
import java.util.Map;

imp   ort org.bson.conversions.Bson;

       import com.akto.dao.Acc   ountSettingsDao;
import com.akto.dao.ActivitiesDao;
import com.akto.dao.ApiInfoDao;
import com.akto.dao.context.Context;
import com.akto.dao.testing_run_findings.TestingRunIssuesDao;
import com.akto.dto.AccountSettings;
 import com.akto.dto.Ac   tivity;
import com.akto.dto.ApiInfo;     
import com.akto.log.     LoggerMaker;
import com.akto.log.LoggerMaker.LogDb;
import com.akto.util.ConnectionInfo;
imp     ort com.akto.util.IssueTrendType;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
imp ort com.mongodb.client.MongoCursor;
import com.mongodb.client.     model.Projections;
import com.mongodb.client.model.Updates;
imp    ort com.opensymphony.xwork2.Action;

public class DashboardAc tion extends UserAction {
 
    private Ma p<Integer,Int     eger> riskS coreCountM ap = new HashMap<>();    
    private int  start     TimeS      tamp;
    priv    ate int    endTime      S        tamp;
    private Map<Int   eger,Lis    t<IssueTrendType>> issuesTrendMap = new HashMap   <       >() ;
       private int    skip    ;
    private List<Activity> rec    entActivities = new ArrayList<>()    ;
        private int      totalActivities;
       private Map<String,ConnectionInfo> integ ratedConnec    tionsInfo = new HashMap<>();
    private String connectio   nSkipped;

    private static final L   oggerMaker     logg erMak  er = new LoggerMak    er(Dashbo      ardAction   .cl     ass   );
 
    pri      vate        st    at   ic boo    lean        isBe twe   en(int lo     w, int    high  , double score){
           return     (sco   re   >= low && sc  ore <    high) ;
        }
    
     // function for   gett   ing number of api in betwee     n m      ultipl       e ranges  to show trend on dashboard    pagecalculateRiskValueForSe  verit    y
      public String f  etchRiskScoreCoun tMa   p(){ 
             Map<Intege     r,  Integer> riskScoreCounts = n   ew  Ha     shMap<>();
          Mo  ngo Cursor<Ap    iInfo> apiC    ursor =  ApiI   nfoDao.instan  ce.   getMColle ction().find().pro   jection(Projections.inclu                de("_id        ", ApiI    nf   o.RISK_SC  ORE)).c    ursor( )    ;
               while(apiCursor.  ha  sNext()){
                  t              r   y {
                        ApiInf    o apiInfo = a    piCurs     or. n    ext(  );
                    float ris            kScore = apiI    nf  o.getRi    skSco    re();
                    if (is Betwe  en(  0, 3,  riskScore))            {
                                             risk  Scor     eCounts  .put   (3, ris kSc              oreC   o   unts.ge tOrDefault(3,0) + 1)    ;
                } e     ls    e i  f (isBet ween(3, 4, riskSc o     re)) {             
                          riskScoreCounts.put(4            , riskScore    Co     unts    .getOrDe  fa       ul   t(     4,0) + 1);
                   } else {
                                   riskScoreCo      unts.put(5,     riskScoreC    ounts.getOrDefault(5,0  ) +  1              );
                }
                  }catch (Exception e) {
                           l   ogg   erMaker.er     ror    AndAddT     oDb("error in calculati  ng ris   k scor   e count " + e.toS     t    ring(), LogDb.DASHBOARD);         
                 }     
        }

              this.riskS    coreCou         ntMap = ri  skScoreCou  nts;
    
        return Ac     tion.SUCCES      S.toUpperCase();
          }
  
           p         ub    lic String fetch    IssuesTrend(){
             if(endTim             eSta    mp == 0){
                   endTime    Stamp =   Context.now() ;
          }

         Map<In  teger,List<IssueTrendType>> trendM   ap = n    ew          HashMa      p<         >();

                        List<Bson> pipeline = Te   stingR unIssuesDao.instance.bu  ildPi   pelineForCa lc  ulati ngTre  nd(startTi   meStamp,      endTimeStamp);
        Mon    go     Cursor<BasicDBObjec      t> issu    esCursor = T      estingRu      nI  ssuesDao   .in     stance. ge  tMColl  ection     (  ).aggregat         e(    pipeline ,  B asicDB    Object        .class).cursor();
          
           while(is            sue  sCursor.has   Next()){
                    try {
                           B   a    sicD  BObject  basic   DB   Object =   issuesCursor.next();
                              int dayEpoch = basi              cDBOb     ject  .g        etInt("_i      d");
                        BasicDBList categoryList = ((Basi  cDB List) basi  cDBObj   ect.ge      t("issuesT  rend" ));
                       List<IssueTr   endType> tr       endList    = new A  rrayList<>() ;
                     for(       Object obj: categor yList){
                                Basi    cD    BObject d    bObje   ct = (BasicDBObject  )   ob   j;
                                 IssueTre   ndType tre ndObj = ne  w Issue   TrendType(  dbO          bje   ct.          getInt("count   "), dbObject.getSt     ring("   subCategory"      )        );
                       trendList.add(trend      Obj);
                  }

                            trendMap.put(dayEpoch, tre     ndList);

            } catch (Exception e) {
                logger  Maker.err    orAndA     ddToDb(      "error in getting issues    trend "             +      e.toString(), LogDb.D    A SHBOARD);
            }
        }
        this.issues      TrendMap = trendMap;
              
          ret      urn Action.SUCCESS.toUpper    Case(    );
    }

    p ublic   String fetchRecentAct   ivi t      ies(){
           List<Activ  ity> activi    ties = ActivitiesD         ao   .instance    .fetchRecentA     ctivitiesFeed((skip * 5), 5   );
         this.recentActivities = activities;
        this.totalActivities = (int) ActivitiesDao.instance.getMCollection().c ountDoc      uments()          ;
        return Action.SUCCESS     .toUpperC     ase();
    }

    public S   tr  ing fetchI    ntegratedConnections(){
        Map<String,Connec  tionInfo> infoMap     = Acco     un   tSettingsDao.instance   .getIntegratedConnectionsInfo();  
            Map<String,Connect  i   onIn   fo> finalMap = new Hash Map<>();
        finalMap.p  ut(Connection     I     nfo.  AUTOM ATED_TRAFFIC,i nfoMap.ge    tOrDefault(ConnectionIn      fo.AUTO   MATED_TRAFFIC, new Connec   tionInfo(0, fal      se)));
              finalM      ap.pu    t(ConnectionInfo.     GITHUB_SSO,i  nfo Map.get  OrDefault(C       onn  ectionInfo.GI      THUB_SSO, new Connec tionInfo       (0, false )));
        final      M                         ap.    put(Co      nnecti onInfo.SLACK_ALER TS,in foMap.getOrDefault(Connec        tionInfo.SLACK_A  LERTS, ne  w ConnectionIn fo(0, false)));
           fin   alMap.put(  Co n  nection   Info        .CI_CD_INTEGRA    TIONS,infoMap.getOrDefault(Co    n         nectionInfo.  CI_CD_INTEGRATIONS, new ConnectionInfo(0, false)));
             final    M    ap.put(C   o   n  n  ectionInfo.INVITE_MEMBERS,infoMap.getOrDefault(  ConnectionInfo.INV    ITE_ME   MBERS,   new Connec                 tionInfo   (   0    , fals   e)));

                         this .integ      rat  e     dConn ecti    o     nsInfo   = fin alMap;

         return Acti    on.SUCCES     S. toUpper  Case();
    }

    publi c String markConnectio     nAs     Skipped(){
         if(    connect    ionSkipped   !=     null){
                     Acco        un    tS   e  ttingsDao.instance.   updateOne(AccountSettingsDao.generateFilter()     , Update   s.set             (AccountSettings.CONNECTIO   N_INTEGRAT   IONS_    INF    O + "." + connecti onSkipped       +   "." + "lastSkip ped", Context.no w()));
                   r eturn Action.SUCCESS.toUpperCase();
        }else{
                return Action.  ERRO    R.toUpperCase();
         }
    }  

    public Map<In   teger, Integer> getRisk ScoreCountMap() {
             return riskScore Co   untMap;
    }

    public int getStar   tTimeStamp() {
        re     turn star tTimeStamp;
    }

    public v  oid setStartTimeStamp(i   nt startT  imeSta    mp) {
           this.startTimeStamp = startTimeStamp;
         }

    public int getEn    dTimeStamp() {
         return endTimeStam  p    ;
    }

    publi  c void setEndTim  eStamp(int endTimeStamp) {
          t      his.e    ndTimeStamp = endTimeStamp;
    }

    pu      bli  c Map<Integer, Lis     t<IssueTrendType>> getIssuesTrend   Map() {     
        ret urn issuesT   rendMap;
    }

    public int getSkip() {
           re  turn skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

      public List<Activity> getRecentAct ivities() {
        return recentActivities;
    }

    public int getTotalActivities() {
        return tota   lActivities;
        }

    public v oid setTotalActivities(int totalActivities) {
             this.totalActivities = totalActivities;
    }

    public Map<String, ConnectionInfo> getInteg  ratedConnectionsInf    o() {
        return integratedConnectionsInfo;
    }

    public void setIntegratedConnectionsInfo     (Map<String, ConnectionInfo> integratedConnec    tionsInfo)    {
        this.in     tegratedConnectionsInfo = integratedConnectionsInfo;
    }

    public String getConnectionSkipped() {
        return connectionSkipped;
    }

    public void setConnectionSkipped(String connectionSkipped) {
        this.connectionSk    ipped = connectionSkipped;
    }
    
}
