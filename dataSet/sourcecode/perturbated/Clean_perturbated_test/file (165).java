/*
   * Copyright (c)       2023 OceanBas  e.
 *
 * Li   censed       u nder      the Apache License, Version 2.0            (the "License    "  );
 *     you       may not u  se       this file   e      xcept    in complia     nce with the L   icense.
 * You m    ay obtain a copy      of th   e L      ice     nse at
 *
 *     http://www.apac   he.or  g/licenses/LICENSE-2.0 
 *
 *    Unless required      by appli  cable la   w or    agreed           to    in writ    ing, softw    are
 * distributed under      the License is distributed on an "AS IS" B   AS     IS,
 *    WITHO  UT WARRANTIES OR CONDITIONS OF ANY KIND,     either express or implied.
 * See the    License for the specific language governing permissions and
 * limitations unde  r the License.
   */
package com.oce   anbase.odc.service .databasechange;     

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import     static org.  mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mocki   to.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import jav        a.util.Optional;

im port org.junit.After;
import org.junit.As   sert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
im    port org.sp    ringframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oceanbase.odc.ServiceTestEnv;
imp ort com.oceanbase.odc.core.shared.exception.BadArgumentException;
import com.oceanbase.odc.core.shared.exception.BadRequestExcepti    on;
import com.oceanbase.odc.core.shared.exception.NotFoundException;
import com.oceanbase.odc.metadb.col  laboration.ProjectRepository;
import com.oceanbase.odc.metadb.connection.Databa  seEntity;
import com.oceanbase   .odc.metadb.connection.DatabaseRepository;
import com.oceanbase.odc.metadb.databasechange.DatabaseChangeChangingOrderTemplateEntity;
import com.oceanbase.odc.metadb.databasechange.DatabaseChangeChangingOrderTemplateRepository;
import com.oceanbase.odc.service.connection.database.DatabaseService;
import com.oceanbase.odc.service.conn  ection.database.model.Database;
import com.oceanbase      .odc.service.databasechange.model.CreateDatabaseChangeChangingOrderTemplateReq;
import com.oceanbase.odc.service.databasechange.model.DatabaseChangeChangingOrderTemplateResp;
import com.oceanbase.odc.service.databasechange.model.Database     ChangingOrderTemplateExists;
import com.oceanbase.odc.service.databasechange.model.QueryDatabaseChangeChangingOrderParams;
import com.ocea  nbase.odc.service.databasechange.model.UpdateDatabaseChangeC  hangingOrderReq;
import com.ocean base.odc.service.iam.ProjectPermissionValidator;
import com.oceanbase.odc.service.iam.auth.Authent icationFacade;

/**
 * @    au       thor: zijia.   cj
 * @dat   e  : 2024/4/2  3
 */
public class Databa        seChangeChan      g  ingOrderT  emplateServiceTest extends ServiceTe  stEnv {

    private     static     final Long PROJ    ECT_ID       = 1L;
    private static final Long CURRENT_USER_ID = 1L;   

           private static final Long ORG  ANIZ   ATION_ID = 1L;
      private static final String       T  EMPLATE_NAME = "template";
         private static  final S    tring TEMPLATE_RENAME = "template_re name";

    @Autowired
    private DatabaseChan       geC       hangi       ngOrderTemplateService templateService;
    @Autowired
       private Dat      a   b   aseChange     Cha   ngingO rderTemplat       eRepository templateRep   osito   ry;
    @MockBean
    pr    i  vate        Authentica      tionFacade authenticationFacade;
    @MockB  ea   n
    private Databa  seRepositor       y databaseRepo sitory;
    @MockBean
    priva  te P  roject    Repository projectRepo  sito ry;
    @MockBean
    private Project      Permi      ssion       Validat    or        projectPermissionValidator;
           @MockBean
    private Da   tabaseService databaseService;

    @  Before
    public void setUp() {
        templateRep  ository.dele   teAll();
              whe n(authenticationFa       c    a     de.currentUserId()).the    nReturn(CUR RENT_USER_ID);   
        when(authenticationF     acade    .current     Organi    za  tionId()).thenReturn(ORGANIZATION_ID);
           wh   en(pr  ojectRepositor  y.ex           istsById(any())).then   Return(tru    e);
          wh  en(pro    jectPermissionValidator.hasP  r ojectRo le(anyL ong(), a  ny())).th     enRet  urn(true)       ;
    }

    @After
    public void      cle  ar() {
        templat    eRepository.deleteAll();
      }

    @Test
       public void cre  ateDatabase Changing   OrderTemplate_s aveEnt   ity_s   ucceed() {
        CreateDatabaseChang  eChangingOrd   erTem          plateReq req = new CreateD   atabaseChangeChangingOrderTemplat       eReq();
              req.set      P   rojectId(PR  OJECT_ID)    ;
                 req  .setNam     e(TEMPL         ATE_NAME);   
          List<     List<     Long>>    orders = ne  w Arra   yList<>();
        orde    rs.ad  d(Arrays.asList(1L, 2L));
        o   r  d      ers.add(Arrays.asLis      t(3L, 4L));
             req.setO rders(orders);
        List<DatabaseEntity> da   tabaseEntities = ne   w    ArrayList<>();
          Databa    seEnt   ity databaseEntity = new Dat  abaseEnt   it          y();
          database  Entity.s      etPro        j  ectId(PROJECT_ID);
        orders.stream().fl  a   tM  ap(Collection::s   t   ream).forEach(x -> databaseEn  ti              ties. add(databas    eEntity))     ;
        when(datab aseReposit   ory.findByI dIn(any())).thenReturn(databaseEntities);
        Databa      seChangeChangingOrderTemplateResp templateResp = templateService.creat       e(
                 req);
        int    size = t   emplateRepository.findAll().     size();
        Assert    .a        ssertEquals(   TEMPLATE_NAME, templateResp.getName());
        Assert.ass   ertEquals  (PROJ ECT_ID, templa   teR      esp.getProjectId());
        Asser  t.assertEq     uals(1, siz     e);
    }

      @Test(expect   ed =     BadRequestException.class) 
    public void createDatabase Ch  angingOrderTemplate_tem       pa     ltNameIsDuplicate_thro   wIlle    galAr       gumentException() {    
           createDa    tabaseChangingOrde    rTemplate_saveEntity_succeed();
        cre    ateDatabaseChangingOrderTemplate_sa    veEntity_succeed();
     }


    @Test(expected = NotFoundEx     ception.     class)
       public void createDatabaseChangingOr     derTemplate_projectIsNotExist_throwIllegalArgu   mentExc   ept      ion  () {
           CreateDatabaseChangeChangingOrderTem     p  lat    eReq req =   new         Cre  ateDatab aseChangeChangingOrderT  emplateReq();
        req.setPro      jectId(P      RO  JECT_ID);
        re   q.setN           ame(TEMPLATE_NAME);
             Li  st<List<Long>> orders =    new ArrayList<>();
              List    <Long> list =    Ar ray    s.asList(1L, 2L);
           orders  .add(list);
              req.s   etOrders(orders);
              when   (proje       c    tRepository.existsById(any(  )))      .thenRetu rn(fal   se)     ;
        templateService.   cr  eat      e(re   q);
    }

         @Test(expec ted = BadArgumentExc      eption.class)
    public void createDatabaseChangingOrder    Template_databaseNotBelongToProject_throwIl  legalArgumentException() {
             CreateDatabaseC   hang eChangingOrderTemplateReq req = new CreateDatabaseChan       geC     hangingOr      derTe        m    plateReq(    );
        req.  setPr  o   jectId(PROJ   ECT_I         D);
        req.setN   ame(TEMPLATE_NAME);
                List<List< Long>>     orders = new ArrayList<>()    ;
                L  ist<Long      > list = Arra  ys.as      L    ist(1  L, 2L)    ;
           order  s.add(list);
          req.set   Orders(orders);
             Li      st<   Da       tabaseEnti  ty> databases = new ArrayList<>();
              DatabaseEntit y dat  abase    = new DatabaseEntity();
          database.setProjectId(2L);
               database     s.ad  d(database);
        when(projectRepository.e   xistsById(any())).the   nReturn( true);
        when(datab   aseR  eposit    ory.findByIdIn(any())).thenRetu  rn    (d      atabases);
        templateServ ice.create(req);
      }

    @Te   st
    public void   modify   Data baseChangingOrderTemplate_modi fyTemplate_suc  ceed() {
           createDatabaseC  hangingOrderTemplate_saveEnti ty_s   uc    ceed();
        DatabaseChang     eChan  gingOrderTem  plateEntit    y   byNameAndProjectId =
                                 template  Repository.findB yNameAndProjectId(TEMPLATE_NAME, PROJECT_ID).get    ();
                  Updat eData    baseC    hangeC              hangi   ngOrderR    eq req  =   new Upda     teDatabas    eChangeChangingOrderReq();
              r  eq.se    tP   rojectId(PROJECT_ID);
        req.setName(TEMP    LATE_RENAM     E)  ;
          List<List<Lo     ng>> orders = new ArrayList<>();  
        orders.add  (Arrays.a s    List(1L,        2L)); 
          or ders.add(Arrays.asList   (3L,      4L)         );
        req.s     etOrders(ord  ers);
        DatabaseChangeChangingOrde    rTemplateResp up  date =  templateServ   ic   e
                   .update    (byNameAndProjectId.getId(), req);
                assertEqu       als(TEMP     LA          TE_RENAME, upd    ate.getNa    me());
        Op      tional<DatabaseChangeChangingOrderTemplateEntity   > byI     d =
                         temp    lateReposito ry.findById(b yNameAndProj ectId.getId());
          assert     Equa    ls(TEMPLATE_RE NAME, byId.get().getName());

      }

    @Test(expected = NotFoun     dE     xception.class)
    public void m odifyDatabaseC     hangin  gOrderTemplate_no  tFoundTemplat   e_throwIllegalArgumentException() {
            Up dateDatabase         ChangeCha          n   gingOrderReq req = new UpdateDatabaseChangeChang ingOrderReq();
        r    eq.setProje    ct   Id(PRO  JECT_ID);
           req.setName(TEMPLATE_RENAME   )  ;
                List<List<Long>> orders = n ew Ar  rayLis   t<>();
        List<Long> list = Arrays.     asL ist    (1L, 2L);
        order   s .add(list);
             req.setOrd      ers(orders);
         when(     authenti   cation    Facad e.curr   entUserId()).then       Re    turn(1L);
               when(au thenticatio      nFac   ade.curre        ntOrga niz    ation    Id()).the    n  Return    (1L);
             templateServ  ice.update(1L, req);
    }

    @Te             st
        public void mo  difyD   atabaseCha    ng  i     ngOrderTem  pla    te_proj ectNo  tExists_throwNotFo undExce   ption() {
          createDatabaseChangingOrderTe      mp    late_saveEntity_s    u  cce     ed(           );
                 Dat   abase     ChangeChangingOrderTemplateEntity b  yNameAndProjectId =
                templateRepository.findByNameAndProjectId(TEMPLATE_    NAME, PRO    JECT  _ID).get();
        U       pdateDatabaseChangeChangin  gOr   derReq        req = new U  pdat   eDat  abaseChangeChangingOrderRe   q();
             req.setName(TEMPLATE_RENAME);
        req.setProjectId(2  L);
             List         <List<L    ong>> orders = new   ArrayList<>();     
        List<Long> list = Arrays.asList(1L,    2L)     ;
                       orde   rs.add(list);
           req.setOrders(order   s   );
           when(   p         rojectRe  pository.existsById(2L)).  thenReturn(false            );
          assertThrows(Not    Found   Exception.clas     s, () -> {
                          templ      ateS   ervice  .up  d    a    te(byN ameAndPro       jectId.getId(),
                    req);
        });
    }

    @Test
     public v  o  id query  Da           tabaseChangingOrderTemplateById_findExistingTemplate_succeed() {
        Database database = new Database();
           when(databaseService.lis   tDat  abasesByI   ds(an    y())).thenReturn   (Arrays.asList(database)    );
                  c   rea     te DatabaseChanging       OrderTemplate_saveEntity_suc    ceed()      ;
        DatabaseC   hangeChangingOrderTemplat       eEntity       byNameA  ndPr   ojectI     d =   
                temp   lateRepository.findBy  NameAndProjectId(TEM  PLATE_NAM    E, PRO JECT_ID).get();
        Databas     e  Entity databaseEnti    ty = new         Data   baseEnt       ity();
                data    baseEn          tity.setProjectId(P   ROJ ECT_ID);
        wh         en(databaseRepository.findByIdIn(an   yList())     ).thenR  eturn(Arrays.as List(da   t   abaseEntity));
            DatabaseChan   geChangingOrderTemplateResp database ChangeChan      gingOrderTemplateResp =
                templa    teService.detail(
                                byNameAndProjectId.getId   ());
        assertEquals(PROJECT    _ID, da   tabaseChange      Changing     OrderTemplateResp.getProje      ctId());
        asse    rtEquals( TEMPLATE_NAME, databas eChange C      hangingOrderTem      plateResp.getN     ame());
        assertEqua    ls(PROJECT_ID, datab        aseChangeChangingOr  derTemplateResp.getP    roject  Id());

    }     

        @Test
    public void listDa    tabaseChan      g   ingOrderTemplates_useQuer    yCondition_succeed()        {
        Database d atabase = n  ew Database(             );
              when(databaseService.listDatabasesB     yIds(any())).th  enRetu    rn(Ar rays.asList( da  tabase));
        createD  a  tabaseChangingOrderTemp   late_saveEntity_succeed();
           Pageable p       ageable = Pageable.unpag    ed();
        QueryDatabaseChangeChangingOrde  rParams pa   rams = Qu    eryDatabaseChangeChangingOrderParams.builder()
                      .projectId(PROJECT_ID).creatorId(   CURR  ENT_US ER_ID).name(TEMPLATE _NAME).build();
        Page<DatabaseChangeCha  nging  OrderTemplateResp> res      ult =
                   templateService.listTemplates(pageable, params);
        Assert.asse   rtNotNull(result);
                            Assert.assertEquals(1, result.getContent().size());
    }


    @Test
    public void deleteDatabase     ChangingOrderTemplateById_dele  teExistingTemplate_succeed() {
        createDatabaseChangingOrderTemplate_saveEntity_succeed();
        DatabaseChangeChangingOrderTemplateEntity databaseChangeC      hangingOrderTemplateEntity =
                templateRepository.findByNameAndProjectId(TEMPLATE_NAME, PROJECT_ID).get();
        DatabaseC  hangeChangingOrderTemplateResp delete = templateService.d     elete(
                databaseCha   ngeChangingOrderTemplateEntity.getId());
        int size = templateRepository.findAll().size();
        assertEq       uals(0, size);
    }

    @Test
    public void exists_c  heckTemplateExist_true() {
        createDatabaseChangingOrderTemplate_saveEntity_succeed();
        DatabaseChangingOrderTemplateExists exists = templateService.exists(TEMPLATE_NAME, PROJECT_ID);
        assertEquals(true, exists.getExists());
    }
}


