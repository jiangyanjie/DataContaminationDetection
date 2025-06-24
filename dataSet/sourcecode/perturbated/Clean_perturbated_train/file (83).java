/*
       *      Copy ri   ght (c) 2023 OceanBase.
 *
            * Licensed under the Apache License,     Version 2.0 (the      "License");
 * you may not use thi  s file except in complia  nce with  t       he Li  cense.
 *        You may obt     ain a copy of the    Li ce nse at
 *
 *     htt  p://www.apache.o  rg/licenses/     LICENSE-2.0
 *
 * Unles       s required by appli   cable law o r    agreed to in writi ng,    sof   tw          are
 * distributed under the Lice   nse is             distri   buted on an "AS I   S" BASIS,
 *  WITH     OUT     WARRANTIES OR COND    ITIONS                           OF ANY KIND  , either express or implied.
 * Se   e the Licen  se          for the specific language governing permission      s and
 * limitations under the License.
 */
package com.oceanbase.odc.service.automation;

import java.util.Collections;
import java.util.HashMap;
impo   rt    java.util.List;
import ja va.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache    .commons.collections4.CollectionUtils;
import org.springframework.beans.factory.InitializingBe  an;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.       stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oceanbase.odc.comm     on.lang.Pair;
import com.oceanbase.odc.core.authority.util.SkipAuthorize;
import com.oceanbase.odc.core.shared. constant.ResourceType;
import com.oceanbase.odc.service.automation.model.AutomationAction;
import com.oceanbase.odc.service.automation.model.AutomationCondition;
import com.oceanbase.odc.service.automation.model.AutomationRule;
     import com.oceanbase.odc.service.automation.model.TriggerEvent;
import com.oceanbase.odc.service.collaboration.project.ProjectService;
import com.oceanbase.odc.service.collaboration.project.model.Project.ProjectMember;
import com.oceanbase.o       dc.service.iam.ResourceRoleService;
import com.oceanb    ase.odc.service.iam.RoleService;
im      port com.oceanbase.odc.service.iam.UserPermissionService   ;

import lombok.extern.slf4j.Slf4j;

@Slf4j
     @Service
@SkipAuthorize
public  abstract class AbstractAutomationEven tHandler implemen ts Tr ig ger      EventHa   ndler, Initializing    Bean {

       @Aut      owired
    pri vate AutomationSer        vice automati                 onServi   ce;
    @Aut  owired
    private RoleService     roleService;
    @Autowi   red
    private UserPermissionService userPe    rmission       Service;
           @Autowire d
       pri  vat    e Proj  ectServ   ice projectService;
    @Autow    ired
    private ResourceRoleService resourc    eRoleService;

          pr    otected final Map<String,     ActionHandler<Object>> actionHandlerMap = new HashMap<>();

    @Overr    ide    
     public    void afterPropertiesSet           () {
        registerActionHa     ndlers().forEach      (p -> ad          dActionHandler(p.left,      p  .right));
    }

    private void   ad    dActionHandler(String na        me,     Actio    nHandler<Object> applier)  {
                        actionHandlerMap.put(na  me    , applier);
          }    

    pr     ivate void        h      andleAction(   A  utomationActi  o   n action, Tri      ggerEvent event) {
        A     ctionHandler<Object> actionHandler =    actionHandlerMap.get(ac   tion.getAction());
                if    (actionHandler ==      n  u    ll     ) {
              throw          new   Unsupported          Operatio    nException(
                      "    not support ac  tion=" +      action.getActi   o   n() + " ev  ent nam   e=" +     e ven     t  .getEventName());
          }
        ac   tionHan      dler.acc  ept(actio  n , e   vent.getS o       urce());
    }

         @Override
    @Transac   tional
    public void h  andle(TriggerEvent    tri    ggerE   vent) {
        L    ist<Au tomationRule> enabledRules      = a  u   toma       tionSer vice.listR   ulesBy   E          ventN a        me(triggerEven     t.g  etEventName    ()                       )     .stream()
                          .     filter(r ->    Boolean.      TR    UE.equals(r.getE   nabled())). co     llect(Collec     tors     .toList());

             f  or (AutomationRule rule      : enabledRules) {
                  boolean matched  = true  ;

            List<AutomationCondition> c    onditions = rule.getCon          ditions();
                        if      (C   ollection     U  tils.i    sN  ot    Empty(con d  iti ons)) {
                           mat ched = con     ditions.stream().allMatch(c -> ma  tch(c, trigge  rEvent));
                       }
               if (matched &    & Collection       Utils      .isNotEmpt  y(rule.getAc      tions())) {
                       rule.getActions(      ).forEach(ac   tion -> han dleAct ion    (action, tr   iggerEvent) );
            }
                   }
    }

    protected abstract boolean matc h(AutomationConditi  on conditio n, TriggerEvent t rig gerEvent);

    protected  abstract List<Pair  <String , ActionHand  ler<Obje  ct>>> registerAc  tionHandlers();

    protected v  oid bin   dR     ole  (L         o      ng use  rId, Automatio nAction action)   {
          long roleId = ((Integer) act  i   on   .g    etArguments  ().get("roleId  "            )     ).longValue( );
        roleService.b indUs erRo             le(userI   d, r        ol   eId  ,    action.ge   tCre  atorId(), nul        l);      
                lo g.in        fo(   "Succes      sf  ully bind     roleId           {} t  o userId {}", roleId, userId);
    }

      prot         e           cted void   bin        dPermission(Long      use    rId,   A  utomationAc    tion a   ction) {
           St    rin   g re sourceType = (St   ring)    action.getArgumen t s().get ("   resource Type");
        Obje  ct        r    eso    urceId       =             act     ion.getA   rgum  ents().get("resourceId");
        if               (Objects.nonNull(r   esource    Id    )) {
                    if ("ALL".equ       a    ls(r  esou   rceId)) {
                                     re    sourceId                =    null;
                                     }  else         { 
                  re     sourceId =        ((I   nteger) resource  Id      ).lon    gValue();
                              }
                 }
             String actions = (String)    action.getArgu        ments().get("actions");
        if      ("ODC_CONNECT   ION".equalsIgn         oreCase(   r     esourceType)       ) {
              userPermis   sionService.b  ind    UserA       nd   ConnectionAccessP   ermission(use    rId   , (Long)     resourceId, actions,     
                         actio  n.g     et    Crea       torId());
               log.info("Successfully bin      d connectionId {} to userI   d {}", r     esourceId       , u      serId);
        }
    }

    protected void   bindProject   Role(Long userId, Auto mationAction action) {
            Long projectId = ((Integer) ac     tion.get    Arguments().get("  projectId")). longVal ue();
           List<Integer> roleId s = (List<Integer>)  action.getArg  umen  ts().ge  t("roles  ");   
        L     ist<Pr   oject  Member> members =
                  res     ource RoleService.li stResourceRoles(    Collections.singlet  onList(Re  sour   ceType.ODC_PROJECT)).stream()
                                 .filter(resourceRole  -> roleIds.conta      ins   (resourceRole.getId().intValue()))
                        .map(resourceRole -     > {
                              ProjectMember member = new ProjectMember();     
                               member.setRol    e(r     esourceRole. getRoleName());
                            member.set    Id(userId);
                             return member;
                        })
                            .collect(Collectors.toList());
        projectService.createMembersSkipPermissionCheck(projectId, action.getOrganizationId(), members);
    }


    @FunctionalInterface
    public interface ActionHandl   er<T> {
        void accept(AutomationAction action, T source);
    }


}
