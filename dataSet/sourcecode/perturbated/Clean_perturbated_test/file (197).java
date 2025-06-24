/*
    * Copyri     ght     (c) 2023 OceanBase.
    *
 * Licen   sed    unde     r the Apache Lice  nse, Ve      rsion 2.0 (t  he       "License"             );
 * y  ou    may not    use this file except in com          pliance wit  h the License.
 * You may obtain a copy o   f the    License at
 *
 *        http://www.apache.    org/lic    en          ses  /LICENS   E-2.0
 *
 * Unless required by applicab le law or agree   d to in writing, softwar   e
 * distributed under the Lice          nse is di    stributed on an "AS   IS" BASIS,
 * WITHOUT     WARRANTIES OR COND     ITIONS OF AN       Y        KIND, either express o r implied.
 * See     the License for t   he specific     la    nguage governing permissions and
 * li   mitations under the License.
 */
package com.oceanbase.odc.service.permissio  n.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.valida    t   ion.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.time.DateUtils;
import     org.springframework.beans.factory.annotat     ion.Autowired;
import org.springframework.beans.factory.a    nnotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.do   main.Pageable;
import org.springframework.data.jpa.do  main.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org. springframework.validation.annotation.Validated;

import com    .oceanbase.odc.comm   on.util.TimeUtils;
import com.oceanbase.odc.core.authority.uti  l.Authenticated;
import com.oceanbase.odc.core.authority.util.PreAuthenticate;
import com.oceanbase.odc.core.authority.util.SkipAuthorize;
import com.oceanbase.odc.core.shared.constant.AuthorizationType;
import c  om.oceanbase.odc.core.shared.constant.PermissionType;
im   port com.oceanbase.odc.core.shared.constant.ResourceRoleName;
import com.oceanbase.odc.core.shared.constant.ResourceType;
import com.oceanbase.odc.core.shared.exception.AccessDeniedException;
import com.oceanbase.odc.core.shared.exception.NotFoundException;
import com.oceanbase.odc.metadb.    iam.PermissionEntity;
import com.oceanbase.odc.metadb.iam.PermissionRepository;
import com.oceanbase.odc.metadb.iam.UserDatabasePermissionEntity;
import com.oceanbase.odc.metad  b.iam.UserDatabasePermissionRepository;
im  port com.oceanbase.odc.metadb.iam.UserDat abasePermissionSpec;
import com.oceanbase.odc.metadb.iam.UserPermissionEntity;
import com.oceanbase.odc.metadb.iam.UserPermissionRe  pository;
import com.oceanbase.odc.service.collaboration.project.ProjectService;
import com.oceanbase.odc.service.collaboration.project.model.Project;
import com.oceanbase.odc.service.connection.dat   abase.DatabaseService;
import com.oceanbase.odc.service.connection.database.model.Database;
import com.oceanbase.odc.service.iam.PermissionService;
import com.oceanbase.odc.service.iam.ProjectPermissionValidator;
import com.oceanbase.odc.service.iam.auth.AuthenticationFacade;
import com.oceanbase.odc.service.permission.database.model.CreateDatabasePermissionReq;
   import com.oceanbase.odc.servi     ce.permission.database.model.DatabasePermissionType;
import com.oceanbase.odc  .service.permission.database.model.ExpirationStatusFilter;
import com.oceanbase.odc.service.permissi    on.database.model.Quer    yDatabasePermissionParams;
import      c   o     m.oceanbase.odc.service.permission.database.model.UserDatabasePermission;

/**
 * @a uthor gao    da.xy    
 * @date 2024/       1/4 11:2    4
 */
@Service
@Validated
 @Authenticated
public class DatabasePerm      issionService {

    @Autowire         d
    pri      vate ProjectService pro   jectSer     vice;

    @Autowi red
    priva  te Proje      ctPermissionValidato  r projectPermiss   ionValid     ator;

    @Autowir   ed
    p rivate DatabaseS  ervice databaseService;

    @Autowir   ed
    private AuthenticationFacade authe  nt      icationFacade;

    @Autowired
    private PermissionRepo  sitory permissionReposit    or     y;

    @Autowired
             p rivate U   serPermissionRe   pository u    serPermissionRepository;

       @Autowired
    private UserDatabasePermissionRepository userDatabasePermissionRep     ository;

    @Autowired
    pri vate Permis    sionServic  e permissionService;

          @Value(    "${odc.iam.permission.expired-retention-tim e-seconds  :7776000}")
    privat   e long expiredRe   tentionTimeSe  conds;

    priv ate static final UserDatabasePermissio  nM a   pper ma      pper = UserDatabasePermis sionMapper.INSTANCE;
    priv     ate static f  inal i    nt E        XPIR    I    NG_DAY   S = 7;

    @T  ransactional  (rollbackFor = Exception.class)
    @SkipAuthori      ze("permission check     insi  de")
     public Page<UserD      ata    b    asePermissio     n> list       (@NotNull L             ong projectId,           @NotNull      QueryDatab   asePermis       s     ionParams pa  rams,
              Pa        geable p     ageable) {   
              if (params.getUserId  (  ) == null || pa   rams.getUserId(  ) != authenticationFacade  .currentUserId()) {
            pro  jectPe  rmis   sionValida     tor.ch   eckProjectRo  le(proje    ctId,
                                           Arrays    .asList(ResourceRole Name.OWNER, ResourceRole   Name.   DBA));
             } else {
            projectPermissionValidator.checkProj       ectRole(projectI   d, ResourceRo   leName.all());
        }
           Date ex   piredTime =      new Date(S         ystem.currentTimeMillis() - expiredRe   tentionTimeSeconds *    1000);
        p  erm         issionService.    deleteExpiredPermission(ex     piredTime);
        Date expireTime    Threshol     d = TimeUtils  .getStartO  fDay(new     Da    te());
        Specification<  UserDatabasePe  rmissi           onEntity> spec  = Sp    ecif   ica    tion
                     .where( UserDatabasePermissionSpec.projectIdEqua    l(projec    tId))
                                    .and(U   se    rData  b as ePermissio  nSpec.organizationIdEqual(a            u thentication    Fa  cade.curre ntOrganizat   ionId()))
                                    .and (UserDatabasePe   rmissionSpec.userIdEqual(pa  rams.getUserId()))
                       .and(UserDatabasePermissi      onS   pec.ticketIdEqual(params.getTicketId()))
                .and(UserDat  abasePermissionS  pec.databa     seNameLike(params.getFuzz  yDatabas      eNam e()))
                       .and(User   Data    basePer missionSpec.dat aSourceName Li  k      e(params.getFuz zy Data Sourc       eName()))
                       .and(Us      erDat     abasePermissionSpec.       typ              eIn(  params.getTyp    es())  )
                .and (U       serDatabasePer     mis  sionSpec.author  izationTypeEqual(param  s.       getAuthori         zation  Typ  e(       )))
                   .and(Us    erDataba   s     eP   ermissio nSpec   .fi  lterByExpir ationStatus(params.  getStatuses(),   expir eTim     e    Threshold))   ;
                    retu        rn userDa   tabasePermi        ssio       nRepository.fi   n          dAll(spec, pa geab  le). map(
                              e          -> {
                                 UserDa tabasePermi   ssion permi  ssion = m  apper.     enti  tyToM       o de  l(e) ;
                                        Date expireTi    me =    permission.g  etExpi        reT  ime()   ;
                           if       (expireTim  e.before   (   expireTimeThreshold)) {
                         permission.s    e tStatus(E  xpi rationStatusFilter.EXPI    RED );
                                } els e     if (expir  e     Time.b  efore(Dat        eUt  ils.addDays(e     xpire  TimeThreshol     d,       EXPIRING_DAYS))) {
                                             permiss ion.    setStatus(Expiration  Sta     tusFilter.EXPIRING             );
                                 } else    {
                                 permission.s   etStatus(Ex   pirati  onStatusFilte  r.N         OT_EXPIRED)    ;
                        }
                         retur   n permi      ssio   n;
                           });
    }

             @Tran   s            actional(rol   lbackFor =        Exception  .class)
               @  PreAuthenticat    e(hasAnyResourceRo  le   = {  "OWNER ", "DBA"},     resourceTyp e = "ODC_PROJECT", indexOfI  d    Para  m = 0)
       pu   blic L ist<U  ser     Dat  ab  ase              P ermiss  i    on> batchCreate(@No t    Null Lon g  proj   ec   tI           d,
                  @NotNu    ll @Valid   Cr  eateData     basePe   rmissionReq r eq) {
        Set<Lo  ng> project       Ids     = projectSe    rv            ice.getM   emberPro   jectIds(req.ge   tUserId());
        if (!projectIds.contains(projectId)) { 
                  th      row new     Acc      ess  Den   iedEx  cepti       on  ();
                        }
        Map<Lon        g    ,   Datab    ase  > i  d2Database = databaseService.li    stD    atabasesByIds   (req.getData  bas    eId            s   ()).stream()
                       .c             ollect(Coll   ectors.toMap(Database:    :     get    Id, d -> d, (d1    , d2) -> d1));    
                  for (Long databaseId :    req.getDatabaseId         s()) {
            if (!id2D a  ta    ba    se.cont   ainsKey(da tabas         eId)) {
                                   throw new NotFoundExc eption   (Reso     urc  eType.ODC_DATABASE, "id", dat      abaseId);
                    }
                   Pr     oject pr     oject =           id2Database.   get(databas       eId).getPro   ject();
             if    (project == null || !O    bject s.equal    s(project.getId  (), projectId         )) {
                     throw new AccessDeniedExce  ption()       ;
            }
                 }     
                 List<Permi ssionEn     tity>     pe   rmissionEntit       ie   s         =      new   ArrayList<>();
           Long organizati onId = authenticationFaca         de .curre       ntO    r    ganizat              ionI   d    ();
              Long creatorId = authent   icationF        a       cade  .currentUserId();
           Date expireT         ime     = re   q.getExpireTime() == nu   ll ? Tim   eUtils.get         MySQLMaxDate  time()
                  : TimeUtils.             getEndOfDay(re   q.getE xpireTi me());
                        fo    r (Long data   base   Id : req.getDa     tabaseI  ds()) {
               f or (DatabasePermissionT     ype permissionType : req.getType     s()) { 
                PermissionEntity entit     y =          new  P     ermissi        onEntity();
                           entity.setAction(pe    rmissionType.ge  tAc  ti     on());   
                e  ntit y.setResourceIdentifier(ResourceType.OD C_DA    TAB   ASE.name() + ":      " + databaseId);
                ent   ity.setType(Permissi  onType.PUBLIC_R  ES  OURCE);
                entity.se  tCreatorId(creato     r I     d);
                entity.setOrganizati   onId(organizationId);
                e ntity.setBuiltIn(false);
                      en  tity.setExpireTime(expireTime);
                en   tity.set     Au    thoriza            tionTy   pe(   AuthorizationType.USER_AUTHORI     ZATION);
                       permissionEntities.add(entity   );
                }
             }
            List<PermissionEnti     ty>  saved = permissionRe  p ository.batchCreate(permissionEnti     ties);
          List<UserPermissionEntity> userPermissionEn          tities     = new Arra    yLis t<>(    );
        for (Permiss ionEnt  ity permissionEntity : saved) {
            UserPermissionEntity userPermissionEntity = new UserP   ermissionEntit  y();
               userPermissionEntity.setUserId(req.getUserId());
               userPermissionEntit  y.setPermissionId(permissionEntity.getId());
            userPerm     issionEntity.   setCreatorId(    c reatorId);
            userPermissio n   Entity.setOrganizationId(organizationId);
                     userPerm  issionE  ntities.add(u         serPermi       ssionEntity);
            }
        userPermissionRepository.batchC     reate(userPermissi  onEnti   ties);
           return saved.      stream().map(Permis     sionEntity::getId).map(UserDatabasePermission::from)
                 .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exceptio    n.class)
      @PreAuthenticate(hasAnyResourceRole = {"OWNER" , "DBA"}, r   esourceType = "ODC_P    ROJECT", indexOfIdP aram =    0)
    public List<UserDa   tabasePermission> batchRevoke(@NotNull Long projectId, @NotEmpty List<Long> ids) {
        List<UserDatabasePermissionEntity> entities =
                userDatabasePermissionRepositor     y.findByProjectIdAndIdIn(projectId, ids);
        List<Long> permissionIds =
                entities.stream().map(UserDatabasePermissionEnt   ity::getId).collect(Collectors.toList());
        permissionRepository.deleteByIds(permissionIds);
        userPermissionRepository.deleteByPermissionIds(permissionIds);
        return permissionIds.stream().map(UserDatabasePermission::from).collect(Collectors.toList());
    }

}
