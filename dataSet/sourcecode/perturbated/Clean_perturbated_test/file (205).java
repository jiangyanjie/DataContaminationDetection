/*
  *   Copyright       (c)    2023 OceanBase.
 *
 * Licensed under     the      Apach     e    License, Ver     sion 2.0 (the "Lice         n  se");
 * you ma y not use          this f    ile    except in      compliance   with the Licen    se.        
 * You may obtain a copy of the License at
 *
 *     http://     www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or    agr   eed to in writing, software
 * distributed under t he License is distribu    ted on  an "A  S IS" BAS   IS,
 * W    ITHOUT WARRANTIES OR C  O  NDITI     ONS OF ANY KIND, e  i t      her express or implied.
 *       See the License for the specific language governing permissions and
 * limitations unde   r the License.
 */
package com   .oceanbase.odc.service.connection;

import java.util.Arra     ys;
import java.util.Date;

import org.junit.After;
import org.junit    .Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.ocean  base.odc.Author          ityTestEnv;
import com.oceanbase.odc.metadb.connection.DatabaseEntity;
import com.oceanbase.odc.metadb.connection.DatabaseRepository;
import com.oceanbase.odc.service.collaboration.environment.EnvironmentService;
import com.oceanbase.odc.service.collaboration.environment.m   odel.Environment;
import com.oceanbase.odc.service.collaboration.project.ProjectService;
import com.oceanbase.odc.service.collaboration.project.model  .Project;
import com.oceanbase.odc.service.collaboration.project.model.QueryProjectParams;
import com.oceanbase.odc.service.common.model.Stats;
import co m.oceanbase.odc.service.common.response.PageAndStats;
import com.oceanbase.odc.service.connection.database.DatabaseSe  rvice;   
import com.oceanbase.odc.service.connection.database.model.Database;
import com.oceanbase.odc.service.connection.database.model.DatabaseSyncStatus;
import com.oceanbase.odc.service.connection.database.model.Query  DatabaseParams;
import com.oceanbase.odc.servi   ce.connection.database.model.TransferDatabasesReq;
import com.oceanbase.odc.service.connection.model.ConnectionCo  nfig;
import com.oceanbase.odc.service.connection.model.Q ueryConnectionParams;
import com.oceanbase.odc  .service.db.schema.model.DBObjectSyncStatus;
impor     t com.oceanb  ase.odc.se rvice.iam.ProjectPermissionValidator;

/**
   * @Au    t    hor: Lebie
 * @Date: 2023/6/5 19:  57
 * @D      escription: []
 */
public class DatabaseServiceTest        extends AuthorityTe  stEnv {
    @     Au   towir        ed
                private Data      baseSer  vice databaseServ  ice;

    @A   utowir  ed
    privat  e  Da   tabaseRepository databaseRepository;
      
    @MockBean
    private ProjectService pro   jectService;

     @Mock  Bean
               private Pro jectPermissionValidator pro    jectPe rmissionVali      dator;

           @Moc   kBea     n
    private    Environm  entService environmentService;

    @Moc    kBean
    pr  ivate Connectio nService connectionServi  ce;

    @Before
    public void setUp()   {
        data       ba   seRepository.del   eteAll();
        Mockito.when(projectService.detail(Mockito.a   nyLong())).thenReturn(getP     roject());
        Mockito.doNothing().when(projectPermissi      onValidat    or).checkProje  c tRole(Mockito.anyLong(), Mockito   .anyList());
        Mockito.when(projectPermissionValidator.hasProjectRole(Mockit   o   .anyLong(), Mockito.anyList())).th  enReturn(true);
             Mockito. when(projectPermissionValidato r.h     asProjectRole(Mocki  to.anyList(), Mockito.an       yList())).thenReturn(true);
            Mocki   to.when(connectionS       ervice   .che ckPermis sion(Mocki  to.anyL ong()   , Mockito.a nyList())).thenRet  urn(tru  e )  ;
        Mockito.when( proje        ct    Service.list(QueryProjectParams.builder().buil  d(), Pageable.unpage        d()))
                             .thenRetur    n(Page.empty());
         Mockito.when(co nnect  ionSe  rvice.getForConnectionSkipPermissionCh      eck(Moc    ki  to.anyLong()))
                .thenRet  urn(getDataSource());
                        Mockito.when(connectionService.list(QueryConnectionP  ar ams.bui          lder    ().build(), Pagea  ble.unpaged())       )  .thenReturn(
                PageAndStats.empty());
               Mockito.when(connectionSe rvice.getWi  thou     tPermis    sio    nChe   ck(Mock       ito.   anyLong())).thenRe   turn(getDa    taSource());
        Mockito.when    (env   ironmentService        .detailSkipPermissionCheck(       Mockito.anyLong())).thenRetur n(get             Env    iron  ment      ());
    }

      @Af     ter
    public void te   arDown() {
          d      atabaseRepository.deleteAll();
    }

    @Test
    public void testListByProject_Succes       s() {
        DatabaseEntity database = databaseRepository. save AndFlush(getEnt    ity());
         Mockit    o.when(proje ctService.list(QueryP r ojectParams.builder().build(), Page          ab le.unpaged()   ))
                .thenReturn(new Pa  geImpl<>(Arrays.asList(get  Project())))      ;
        QueryData  basePa  rams params     = QueryD     at  abaseP  arams.builder().projec        tId(databa  se.getProje          ctId()   ).build();
            Pag    e<Database> dat     abases = databaseService.list(p   arams, Pag     e    able.unpa   ged      ());
                     Assert.asse rt        Equals(      1, databases.getSize());
    }

            @Test
    public void testListByDataSo   urce_S    uccess() {
         DatabaseEntity d     atabase = database   Reposit     ory.saveAndF  lush(g     etEntity())   ;
        Mockito.        when(projectService.list(Query    ProjectPa    rams  .builder().build  (), Pagea   ble.unp   aged()))
                .thenRetu     rn(new PageImpl<>(Arrays.asList(getPr     oje     ct  ())))  ;
           Quer     yDatabaseParams       pa   rams = QueryDatabaseParams.buil der().dataSou  r     ceId(database.getConnectionId()).build();
               Page<Database   > databases = databas    eService.list(params, P  ageable     .unpage  d());
        Assert.assertE     quals   (1, databases      .getSize());
        }

     @T  est
       public void testListByF   uzzyNa meAndDataSource_Return1() {
        Da tabaseEntit    y dat  abase =   databaseRepository.saveAndFlush(get    Entity())   ;
         Mocki   to.when(projectService.list(QueryProje   ctParam   s.bui      lder(  ).build(), Pageable.   unpaged()))
                           .the     nR  eturn(new Pa   geImpl<>  (Arrays.asList(    ge tProject())) ) ;    
               Query         Databas         eParams p arams =
                             QueryData  basePar  a       ms.bui  lder    ().sc   hemaName("fake_").dataSourceId(data         ba     se.g        etConnectio   nId())    .bu  ild();
        Page<Database> databases = da     taba seSe     rvice  .list   (  par   ams, Pageable.unpaged());       
              Assert.assertEquals(1, databases.getSize());
    }     

       @Test
        public void testL   istByFuzzyNameAndDataSource_ReturnEm  pty() {
           Databa  seEntity da   tabase = databaseRepository  .saveAndFlush  (getE   ntity());
        Co   nnectionCo         nfig con   nection      = ne     w ConnectionConfi g();    
              connection.setId(database.ge     tConnectionId());
        Project project = new Projec       t(); 
            project    .setId(database.getProje ctId());
        Mockito.when(connectionServ       ice.lis     t(QueryConnect      ionParams.builder().minPrivilege("update").buil     d(),
                                Pageabl e.unpaged()))
                           .thenRetu     rn(P ageAndStats.of(n  ew PageImpl<>(Arrays.a   sList(connecti  on)), new      St   ats())) ;
           Que      ryDatabaseParams     params =    QueryDa   tabasePara  ms.builder()
                .   schemaName  ("real")   .dataSourceId(1L).bu        ild()   ;
         Page<Database> databa   ses            = database       Service.list(par   ams, Pa  gea      ble.unpaged())     ;
        Assert.assertEquals(0, databas  es.  ge tSize());
    }

              @Test
    public void   testDetail_S  uccess()           {
           Data          baseEntity entity = databaseReposit   ory.saveAndFlush(g  etEnt  ity());
        Database dat  abase = databaseService.detail(entit  y.getId(       ));
        Assert .a  ssertE    quals("fake_db"  , database.getName());
    }

    @Test
    public   void testFindByI     d_Success() {
              DatabaseEnti   ty e   ntity = da    taba seRepository.sa   veA  ndFlush(getE      ntity());
            dat aba     se   Service.detail(entity.getId());
    }

    @Test
            public v oid testT    ransfer_S    ucces s() {
        Mockito   .when    (c onnect  ionService.checkPermission    (Mockito.a           nyList  (), Mockito.anyList())    ).thenRet     urn(true);
           Mockito.when(projectPermi     ssionValidat    or.hasProjectR   ole(Mockito.anyList(), Mockito.a    nyLi st())).thenReturn(true);
            Dat  aba  seEnti   ty saved   =      datab  aseRepos   itory.saveAndFlush(getEntit   y()) ;
          Tr     a   nsferData    b  asesReq re  q = n   ew Tra  nsfe   rDataba  sesR   eq();
                 req.setDatabas   eIds(Arrays     .asList(saved. get  Id()));
        req.setProjectId(2L);
        A  ssert.assertTrue    (data baseService.tran    sfer(req));
                      Asser      t.asse    rt    Equals(2L, data   baseRepositor   y.findById(saved.getId()).get().getProjectId().longVa l            ue());
    }

    pr  ivate     DatabaseEntity  getE    nti      ty()      {
        Datab       a seEntit  y entity = new DatabaseEntity(   );
        entity.setName("fake_db");
        entit   y.setDatabaseId("fake_id");
        ent  ity.set    Existed      (true);
            entity.setPr ojectId(1  L);
            entity.setConnectionI    d(1L);
        e ntity. setEnvironmentId(1L);
        entity.set  T     ableCount(1L);
        e  n   tity.setCollationName("collation_name");
                e      ntity.setChar    setName("chars  et_name"); 
            entity.setLastSyncTime  (   new Date(System .currentTimeMillis()));
        entity.setOrganiz  ation    Id(1L);
        entity.setSyncStatus(DatabaseSyn    cStatus.SUCCEEDED   );
          entity.setObjectSyncStatus(DBObjectSync    Status.IN     ITIALIZED);
        re turn enti  ty;
    }

    private Project getProje  ct() {  
        Project     p    roject = new Project();  
            project.setId(1L);
        project.       setName("fake_project");
        return project;
    }

    private ConnectionConfig getDataSource() {
        ConnectionConfig connectionConfig = new Connecti     onConfi  g();
        connectionConfig.setId(1L);
        connectionConfig.setName("fake_datasource");
        return conn       ectionConfig;
    }

       private Environment getEnvironment() {
          Environment environment = new Environment();
        environment.setId(1L);
        environment.setName("fake_env");
        return environment;
    }

}
