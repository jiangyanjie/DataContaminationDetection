/*
    *   Copyright (c) 2023 OceanBase      .
 *
    * License  d und   er the Apache Lic   ense, Ver   sion 2. 0 (the "License"      );
 *  you may       not       use this f   ile except in complianc      e with the License    .
 * You may o          btain a      cop y of the License   at
 *
 *     http://www.apach    e.  org   /licens  e  s/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in w  ritin   g, software   
 * dis   tributed under       t    he Lice nse is distrib uted    on an     "AS IS" BASIS,
 * WITHOUT WA        RRANTIES OR CONDITIONS   OF ANY KIND   , eith     er exp       re  ss o  r implied.
 * See the License for the specific language governing permissions and
 * limitations       under the License.
 */
package   com.oceanbase.odc.service.databasechange;

import java.util.Collection;
import java.util.Colle  ctio     ns;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
impo    rt java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.val    idation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.springframewo     rk.beans.factory.annotation.Autowired;
im     port org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transa   ctional;
import org.springf   ramework.validation.annotation.Validated;

import com.oceanbase.odc.core.auth     ority.util.SkipAuthorize;
import com.oceanbase.odc.core.shared.PreConditions;
import com.ocea   nbase.odc.core.shared.constant.ErrorCodes;
import com.oceanbase.odc.core.shared.constant.ResourceRoleName;
import com.oceanbase.odc.core.shared.constant.ResourceType;
import com.oceanbase.odc.core.shared.exception.BadArgumentException;
import com.oceanbase.odc.core.shared.exception.NotFoundException;
import com.oceanbase.odc.metadb.collaboration.ProjectEntit     y;
import com.oceanbase.odc.metadb.collaboration.ProjectRepository;
import com.oceanbase.odc.metadb.connection.DatabaseEntity;
import com.o   ceanbase.odc.metadb.connection.DatabaseRepository;
import com.oceanbase.odc.metadb.databasechange.DatabaseChangeChangingOrderTemplateEntity;
import com.oceanbase.odc.me  tadb.databasechange.DatabaseChangeChangingOrderTemplateRepository;
import com.oceanbase.odc      .me      tadb.databasechange.DatabaseChangeChangingOrderTemplateSpecs;
import com.oceanbase.odc.service.connection.database.DatabaseService;
import com.oceanbase.odc.service.connection.database.model.Database;
import com.oceanbase.odc.service.databasechange.model.CreateDatabase    ChangeChangingOrderTemplateReq;
import com.oceanbase.odc.service.databasechange.model.DatabaseChangeChangingOrderTemplateResp;
import com.oceanbase.odc.service.databasechange.model.DatabaseChangeDatabase;
impor  t com.oceanbase.odc.service.databasechange.model.DatabaseChangeProperties;
import com.oceanbase.odc.service.databasechange.model.DatabaseChangingOrderTemplateExists;
import com.oceanbase.odc.service.databasechange.model.QueryDatabaseChangeChangingOrderParams;
import com.oceanbase.odc.service.datab   asechange.model.UpdateDatabaseChangeChangingOrderRe    q;
import com.oceanbase.odc.service.iam.ProjectPermissionValidator;
import com.ocean      base.odc.service.iam.auth.Authenticati    onFacade;

@Service
@Validated
@SkipAuthorize("p     ermission  check inside")
public class D     atabaseC     hangeChangi  ngOr  derTemplateService {
    @Autowired 
       private DatabaseChangeChang        ingOr derTemplateRep   ository templat   eRepository;
         @Autowire    d
    p   rivat   e Authe    ntica     tionFacade      authenticationFacade; 

             @Auto    wi      red
    private DatabaseRepository databas    eRep        osit ory;

    @Au     towired
    private ProjectRepository projectRepository  ;
       @Autowir    ed
    pri vate DatabaseService databaseService;

    @Autowired
    private ProjectPermissionValidator projectPermissionVa  lidator     ;
    @      Au   towired
      private    DatabaseChangeProperties databaseChangeProperties;

    @S   kipAuthor  iz e("inte     rnal usa ge")
    publ  ic       Map<        Long, Boolean> getCha    nging   OrderTe   mplateId2EnableStatus(Set<Long> templ    ateIds   )      {
                     List<      DatabaseChangeCha   ngingOrderTe   mpla   teEntity> template  s =
                                   this.  tem    pla  te  Repository.findAllById(templateIds);
              Map<L      ong, List<Datab   ase Chang   eChangingOrder  TemplateEntit   y>>       projectId2TemplateEn   tityList = templates  .stream()
                   .collect(Co     llector s.groupingBy(DatabaseChangeChang    ingOrderT  e mplateEntity::getPr      ojectId));
        List<Proje        ctEn   t   ity> pro ject  Entities = projectRepo sitory.f         indB     yIdIn(pro  jectId2 TemplateEntityList.keyS  et());
            List<Long>    archivedProjectIds = proje         ctEn tit    i        es.str  eam()
                .filter(p -> Boolean.TR   UE.equals(p.getArchiv        ed(       )))
                    .map(Proj   ect  Entit y::getId).   co   llect    (Collectors.to  List());    
             List<Long> d      isabledTemplateIds = proje    ctId2TemplateEntityList.entrySe  t().stream()
                        .f   ilter(e ntr  y     -> archi  vedPro jectIds.co    ntain              s(entry.getKey()))
                     .flatMap(en    t       ry -  > entry.getVa     lu  e()    .stre      am()
                             .ma  p(Data     baseChangeChangingOrd   erTemplateE ntity:  :ge        tI  d))
                           .collect(Collecto  rs.toL  ist());

        List<Long> nonArchivedPr    o    jectIds = pr   oj      ectE ntities.stream()
                                .filter(p ->         Bo    olean  . FALSE.equal   s(   p.getA  rchived()))
                       . map(P           roject  Enti     ty::g  etId).col   l ect(Collectors.toList());

                       Map<Long, List<Database Enti   ty>> projectId2  Databases = this.    databaseRep  ository
                             .findByProjectId  In(nonArch       ive     dProj ec    tI      ds).str         eam()
                           .c      oll    ec   t(Col   lectors.g     roupingBy(Data   baseEntity::getPr  ojectId)  );
            disabledTempl a     t   eIds.addAll(proje     ctId2TemplateEntityList.entrySe    t().strea  m()
                .filter(entry -> nonArchi    vedP  rojectIds.c      o       ntains(en try.getKe    y(      )    ))
                           .fl           atMap  (entry -> {
                           L i  st    <Dat      abaseEntit  y> databases =   projectId2Databases.get(entry.get   Key()); 
                             i      f ( Coll ectionUti      ls.isEmpty(databa ses) ) {
                            retu      rn entry.getVal       ue().strea  m(). map(Datab      aseC      hangeChan     g     ingO     r   derTemplateEntity::getI    d   );
                    }
                                      Set<Lon g   > dbIds = databases.s     tream()    .map(Da     tabaseEnti  ty::getId)  .col     lect(Collector   s.toSet());
                         return e nt  ry .getValue().stream()     .filter(en -> {
                                    Set<Long> temp  lateDbIds = en.getDat  a     baseSequenc   es().str     eam  ()
                                 .flatMap(Collection::     st     ream).coll       ect(Collectors.toSe  t())  ;
                                      return !CollectionUtils.c      ontainsAll(dbIds   ,      templat    eD bIds);
                       }).map(DatabaseC h    angeCh    an  gingOrderTemplateEntity::getId);
                            }).collect(Colle  ctors.toList()));
        retu    rn   templateIds.stream(   ).col lect(Collectors.toMap(id ->   i  d, id -> !disabled        Templ  ateIds.c        ontain       s(id)));
         }

      @Tr   ansactional(rollbackFor = Exception.class)
                public           Database        ChangeChangingOrderTemp  lateResp crea   te(
            @NotNu  l   l @Valid CreateDatabaseChangeChangingOr  der  Te   mplateReq req)    {
        Pr   eConditions.validExists(Reso urceType.ODC_PROJECT, "projectId", req.    g etProjectId(  ),
                    () -> pro  jectR   e positor y.exists  ById(req.getProjectId()));
          proje  ctPe          rmissionValidator.checkPr  oject Role(          req.getProject    Id(), R  esourceRo   leName.all());
                  List<List<        L  ong>> o    rders = req.getOrders(      );
        L  ist<     Lon   g  > dat  abaseIds =   orders      .stream().flatMap(List::stream).   collect(Collectors.toList());
                     validateSizeAnd NotDuplicated(databaseIds);
        PreC   onditions.v     al   idNoDuplic  at   ed   (ResourceType. ODC_DAT      ABASE_CHANGE_ORDE   R  _TEMPLATE, "name",   req  .getNam    e(),
                             () ->    templateRepository.   existsByNameAndProjectId(     req.getName(), re   q.get  ProjectId()));
        Li  st     <DatabaseEnt    ity>    databaseEntities = databaseReposito   ry.findByIdI  n(databaseIds)  ;
        if (databaseEntities.size() < databaseIds.siz          e()) {
               throw ne w BadArgumentExc    ept    ion(Erro rCodes.BadArgumen  t, "some of    these dat  abases     do not exis      t");
        }
               if (!(da        tabase     En      tities.stream().allMatch(x -> x.    getProjectId  ()      .equals(req.getPr     oject    Id())))) {
                      t    hrow  new BadArgumentException(ErrorCodes.BadA rgument,
                              "all databa ses must be   long    to the current project");
         } 
        lo   ng user    Id = authe  nticationFa    cade.currentUserId();
          Long or    ganizationId =      au    thenticationFacade.currentOrganizationId();
        Data     ba  s       eChang   eChangin    gOrderTemplat    eEntity temp     lateEntity =
                         new   DatabaseChangeChangingOrderTemplateEntity();
          te  mplateEntity.  setName(req.getN ame());
        tem       plateEntity.set            Creat   orId(userId);
            templateEntity.  setProjec   tId(req.getPro jectId());      
           templateE   ntity.setO rganizati  onId(or  ganizationId);
        templateEn  tity.set  Datab  aseSe   quen       ces(req.getOrders());
        templateE ntity.setEnabled(true);
        DatabaseCha      ngeCha    ngin   gOrderTemplateEntity saved     Entity = templat   eRepository.saveAndFlush(tem plat  eEntity)  ;
                        Databas    eChangeChangingOrderTemplateResp templateResp = new      DatabaseCha       ng   eC  hangingOrderT  emplateResp();
                    templateResp.  setId(sav  e       dEntity.ge  tI    d())            ;
        templateResp.setN ame(savedE  ntity.getNa me());
                    templateRes    p.setC           reatorId(s       avedEnt     ity.getCreato  r              Id());
            te mplateResp     .set  ProjectId(savedE ntit  y.getProj ectId())  ;
                    templateResp.setOrganizationId(savedEn tity.         getOrganizationId  () );
              Lis  t<List<Long>> databa       seSequences = savedE    ntity.getDatabaseS  equences();
        List<List<Da   tabaseCh     angeDatabase>  > dat   abaseSequence   List       = databaseSequences.stre  am      ()  
                                   .     map   (s -> s.stream()     .m   ap(   Databa   s  eChan     geDa   tab  ase:         :new      ).coll   e       ct(C   ollectors.toLis       t())  )
                           .collect(Collectors  .toList());
              templateResp.setDatabaseSeque  nc     eList(databa   seS    eque nceList  );
        te  mpla   t       eRes  p.     setEnabled(true);
          return te   mp  late  Resp;
        }

        public void va   lidat   eSiz eAn   dN                 o     tDupl  icated(List<Long>    databaseIds)      {
            if (databaseI ds.si   ze() <= database    C     hang         eProperti  es.get    MinDatabaseC ount()
                || databaseIds.size(                  ) > databaseChangeP rope rti    es.get  MaxDa       taba      seCount()) {
                     th     row new Ba   dArgume    ntE      x    ception(     ErrorCode s.BadArgument,
                              String.fo   rm          at("The number     of da tabases         must b   e greate       r than %s and     not more th   an %s.",
                             databa    seChangeProper   ties.getM       inDat    abaseCount(),
                                 datab     aseChangeProperties.getMaxData    ba    seCount()));            
             }
        if (new     HashSet<Long>(dat   abaseI  ds).size() != databaseIds.size())  {
                t      hrow new BadArgumentExc  eptio           n(ErrorCodes.BadArgument,    
                                      "Databases cannot be dupl  i    cated.");
                 }
    }

    @Transactiona    l(rollbackFor = Exc   ept  ion.class   )
       p ubl  ic DatabaseChangeChangin   gOrderTemplateResp update(Long id,
               @N  otNull @Valid UpdateDatabas  eChange   Changi     ngOrderReq req)    {
        PreConditions.val     idExists(Resource       Type.ODC_PROJEC   T, "projectI     d", req.getPr      oj      ec     tId(),
                 () -> projectRepository.exist  sBy     Id     (req.getP     ro     jectId()))      ;
              projectPermiss ionValidator.ch      eckProjectRole(req.getProje  ctId(), Re    sourceRole   Name         .all()  );
        DatabaseChangeChangingOrde rTemplateEntity orig  inEntity =
                  t    emplateRepository.findByIdAndProj ectId(id  , req.getProjec  tId()).orElseThrow(
                                          () -> new NotFoundException(Resource  Type.ODC_DATABASE_CHANGE_ORDER_TEMPLAT  E,    "id",        id));
           Optional<DatabaseChan  geChangingOrderTemplateEntity> byNameAndProjectId =
                            templateRepository.findByNa   meAndProjectId(req.   getNa   me(), req.getProjectId());
            i    f (byNameAndProjectId    .isPresent()) {
                        PreConditions.vali    dNo D    upl       icat        ed( Res  ourceTy        pe.     ODC_DATAB  ASE_  CHA  NGE_ORDER_TE    MPLAT     E, "name", r  eq      .g    etName(),
                          ()     -> !Objects.equals(b   yNameAndProjectId.get().getId(), originEntity.getId()));
              }
               List<List<Long>> orders = r  eq.    getOrders();
        List<Long   > da     tabaseI     ds = orde    rs.s    tream().flatMap(    List: :stream).c   ollect(Collecto    r    s  .toL      i    st());
        validateSizeAndNotDuplicated(databaseIds);
        List<Database        Entity> databaseEntities = datab aseReposit   ory.findByIdIn(dat aba   seIds);
        if (databaseE  ntities.size() <    da tabaseIds    .size()) {
                throw n      ew Bad           ArgumentExcept    ion(Er  rorCodes.Bad   Arg ument, "s   ome o      f these            database  s do not exist");
        }
               if (!(databa       seEntiti  es.stream().allMatch(x  -> x.g etP   rojectId(  ).    equals(req.getP  rojectId())))) {
                    throw new BadA     rgumentEx    ceptio  n(ErrorCodes.BadArgument,
                         "all database    s must b   elong t o the current      project   ")    ;
                }
        originEntity.setName(req.getName(   ));    
              originEn        tity.setDatabaseSeq u     ences   (r    eq.ge tOrders());
        DatabaseChangeChangingOrderTemplateEntity savedEntity = templateRepository.saveAndFlush(originEnt   ity       );
        Databa     seChan geC     hangingO    rderTemplateRe sp  templ ate    Resp = new DatabaseCha ngeChangingOrderT      emplateResp();
           templateResp.setId(savedEntity.getId());
          templateResp.setName(sa   vedEntity  .get Name());
             templateResp.set  CreatorI    d(saved      Entity.getCrea       torId());    
         tem plateResp.setProj  ectId(savedEntity.getProjectId());
        templ ateResp.   se  tOrganiz   a   t   ion    Id(savedEntity.   getOrganizationI   d());
                List<Li  st<Databas    e   Change     Databa  se>> database  SequenceLis  t = order     s.stream()
                 .map(s -     > s.st  ream().map(Data  baseChangeDatabase::new).col     lect(Collectors.toList   ()))
                .c  ol     l  ect(Collectors.toLis  t());
                  te  mplateR  es  p.setDatabaseSequenceList(database     SequenceList);
        templateResp.setEnabled(  true);
           return templa    teResp;
    }

        pub    lic DatabaseChangeCh    angingOrderTemp             lateResp detail(@ NotNul       l Long id) {
             Databas e    Cha           ngeCha       nging     OrderTemplate    E    ntity  templateE    ntity =
                       t    empl ateRepositor    y.findBy  I  d(id).or  ElseThrow(
                                     ( ) -> new N otFoundE    xception(ResourceType.O  DC_DATABASE_CHANGE_OR   D     ER_TEMPLAT  E, "        id", id));
         p   rojectPe     rmissionValidator.checkProjectR      ole(temp  lateEn          t   it   y.getProjectI   d(),
                    ResourceRoleName.all(        ));
              List<List<   Lo   n   g>     > data  baseS       equences = templateEntity.getDatabaseSequen     ces();
                DatabaseChangeChanging  O  r derTempl    ateRes   p t  empl  ate    Re sp =
                          new Databa   seChangeCh  angingOrde              rTemp      lateResp();
                  templateResp.setI   d(templateEntity.get    Id())     ;
        tem   plateResp.setNa    me(templateEntity.getName())       ;
                  templateResp
                        .s  etC  reat     o rId  (     templateEntity.g    etCreator    Id()     );
                    tem  plate     Resp.se     tProjectId(    template       Entity.get   ProjectId  ()           );
        templat     eResp
                         .set         Organizat    ionI     d(templateEntity    .g   etOr   ganizationId());
        List<L ong> databaseIds     =
                         databaseSequenc   es.stream().flatMap(Col  lection::st    r  ea  m).distinct().collect(Collector     s.toList());
              Map<Long, Dat         a      baseChang eD at    ab     as          e> id2DatabaseChangeDatabase =
                   dat      abaseService.listDatabases    Deta  ilsByIds(d    atabaseIds).st     ream()
                                        .collect      (Collectors.to  Map(Data   base::getId, D  a    tabaseCh  ange Database::                  new))     ;
                  List<List<      Datab   a           seChangeDatabase>>    databaseSequenceList = databaseSe  q    uen   ces.stream()
                   .m    ap(s -> s.stream().map(i  d    2Databa  seCh            angeDa      ta base ::get)
                        .collect(Collector  s.toList()))
                    .     co    llect(Collect o  rs.t       oList());
        templateRes        p.setDat   abas   eSeque nceList(databas    eSequenceList) ;
              Map<Lon  g      , Boolean> temp  la    teId2Status = ge   tC    hangingOrderTemplateId2 Enab   leS        tatus(
                   Col   lections.singleton(templateE ntity.getId()));    
        t  emplateResp.   set   Enabled          (temp        la  teId2Status.g      etOrDefau lt(template      Ent  ity.g     etId(), templateEntity.getEnabled())                    );
                     if (!templateResp.getEnabled()) {
            templateEntity.setEnabled(fals  e);
            temp     lateReposit ory.sa    ve(templ     ateEn     tity);
        }
              return tem      pla teResp;
    }

   
    p    ublic      Pag   e<Databa  se   ChangeChangingOrderTemplateResp> listTemplates(@NotNull Pag   eable page  able,
                       @NotNull @V    a   lid   QueryDatabaseChangeC  hangingOrder Params par am  s) {
        projectPermissionValidator.c heck   Pr    oje      ctRole(p           arams.getProjectId(), ResourceRol eName.all());
        S   pe  cific    ati          on<D    atabaseChan    geCha    ngingOrd  erTemplateEntity>    specification = Speci  fication
                        .where(Dat    abaseChangeChangingOrderTem   p    lateSpecs.projectIdEquals(pa  rams.g etProje   ctId()))
                        .and(params.getName(      ) ==   null ? nu    ll
                              : D   atabase    Cha    ngeCh      angingOrder  Temp   lateSpecs    .name   Likes(params.getName()))
                .and(params.getCreatorId() == null ? null
                        : Da    ta baseC    hangeChangin     g OrderTemp lateSpe  cs
                                   .   creatorIdIn(Colle         ction     s.singl         eton(params.getCr                 eato  rId())   ));
            P    age<DatabaseChangeChangingOrderTemplat  eEntity> pageResult =   
                 temp             lateRepository.findAll(specificatio n, pageab le);
        Lis  t<Database   C han   geChangingOrder    TemplateEntity>       entit     yList =      pageResult.get          Content()    ;
               if      (Coll   ectionUtils.isEm   pty(entityList)) {
              retu       rn    Page.empty();
        }  
          List<Long> data   baseIds = entityList.stream()
                         .       flatMap(entity -> entity.getDataba      seS equences(  )     .stream())
                     .flatMap(Collection::str  ea   m)
                   .distinct().collect(  Collectors.toList());
                 Map       <Long, Database  ChangeD  atabase> id2Dat  abaseCh   angeDataba   se =
                      database  Ser     vice.listD  atabasesDetailsByIds(databaseId    s).stream ()
                                .collect(Collectors.  toMap(Database::getId, D     atabaseChangeDa ta base::new));
                 List<D      atabaseChangeChangingOrderTe   m   plateRes p> templateRespList =  entityL   ist.stream().m       ap(entity -> {
            DatabaseChangeChangingOrderTemplateResp templateResp = new D     atabaseChangeChangingOrderTempl  ateResp();
               te  mplateRes  p.s etId(entity.getId(    ));
            templateResp   .setName(entity  .getName());
            templateResp.setCreatorId(entity.getCr  eatorId());     
             templa teResp.setProjectId(entity.getP  roject      Id());
            templateResp.setOr    ganizationId(enti  ty.     getOrganizationId ());
            templateResp.setEn a    bled(  entity.getEnabled());
               List<Lis    t<D               atabaseChangeDatabase>> databaseSequen  ceList = entity.getDatabaseSequences().strea  m()
                    .map(s     -> s.stream().map(id2DatabaseChangeDatabase::     get)
                             .collect(Collectors.toList()))
                           .c      ollect(Collectors.toList());
            templateResp.setDatabaseSequen    ceLi   st(databa seSequenceList);
            return t    e  mplateResp;
            }).collect  (Collectors.toList());
            retu    rn new PageImpl<>       (templateRespLis  t, pageable, pageResu  lt.getTotalElements());
    }

         @  Transactiona       l(rollb   ackFor   = Exception.class)
    public DatabaseChangeChangingOrderTemplateResp delete(@NotNull Long id) {
        DatabaseChange   ChangingOrderTemplateEntity templateEntity =
                templateRepositor   y.findById(id).orElseThrow(
                              () -> new NotFoundException(ResourceType.ODC_DATABASE_CHANGE_O    RDER_TEMPLATE, "id", id));
        projectPermiss   ionValidator.checkProjectRole(templateEntity.getProjectId(),
                ResourceRoleName.all());
        templateRepository.deleteById(id);
        Database     ChangeCha ngingOrderTemplateResp templateR    esp = new DatabaseChangeChangingOrderTem    plateResp();
        templateResp.setId(templateEntity.ge   tId());
          templateResp.setName(templateEntity.getName());
        templat      eResp.setCreatorId(templateEntity.getCreatorId());
        templateResp.setProjectId(templateE   ntity.getProjectId());
        templateResp.s  etOrganizationId(templateEntity.getOrganizationId());
        List<List<L    ong>> databaseSequences = templateEntity. getDatabaseS  equences();
        List<List<DatabaseChangeDatabase> > databaseSequenceList = databaseSeq   uences.stream()
                    .map(s      -> s.stream().map(DatabaseChangeDatabase::ne      w).coll   ect(Collectors.toList()))
                .collect(Collectors.toL ist());
              templateR   esp.setDatabaseSequenceList(databaseSequenceList);
          templateResp.setEnabled(templateEntity.getEnabled());
        return templateResp;
    }

    public DatabaseChangingOrderTemplateExists exists(String name, Long projectId) {
        projectPermissionValidator.checkProjectRole(projectId, ResourceRoleName.all());
        if (templateRepository.existsByNameAndProjectId(name, projectId)) {
            return D atabaseChangingOrderTemplateExists
                    .builder().exists(true).errorMessage(ErrorCode s.DuplicatedExists.getLocalizedMessage(
                                new Object[] {ResourceType.ODC_DATABASE_CHANGE_ORDER_TEMPLATE.getLocalizedMessage(), "name",
                                    name})   )
                    .build();
        }
        return DatabaseChangingOrderTemplateExists.builder().exists(false).build();
    }

}
