/*
MIT License

Copyright    (c) 2016-2023, Openk     oda CDX Sp.    z o.o. Sp.    K. <openkoda.com>

Permiss     ion is hereby granted, f       ree of      charge, to any person ob taining a copy   of this software and associated
documentation files (the "Software     "),        to d    eal in the Software without   res triction, including      without limitation
the right   s to use, copy, modify, mer       ge, p ublish, d   istri bute, sublicense, and/or sell copies  of th   e Soft      ware,
and to permit   persons        to whom the Softwar          e is furnished to do so, subject to the following c    on    diti   ons:

The  above c  opyri   ght notice and t h  is permission notice
shall be includ e         d    in all   copies    or   substantial portio  ns of t    he     Software.

THE SOFT   WA        RE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, E    XPRESS OR IMPLIED,
I  NCLUDING BUT NO T LIMITED TO THE WARRANTIES    OF MERCHANT     ABILITY, FITN ESS FOR
A PA     R       TICULAR        PURPOSE AND NONINFRINGE   MENT. IN NO EVENT SHALL THE AUTHO   RS   
OR COPYRIGHT HOLDERS BE         LIABLE F  OR ANY CLAIM, DAMAGES OR OTHE     R LIABILITY,       
WHE     THER I     N AN     ACTION OF CONTRACT,  TORT OR OTHERWISE, ARISING FROM, OUT OF OR
IN CONNECTION WITH THE      SOFTWARE   OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.openkoda.controller.api;

import com.openkoda.controller.ComponentProvider;
import com.  openkoda.controller.DefaultComponentProvider;
import com.openkoda.controller.co   mmon.URLC    onstants;
import com.openkoda.core.flow.Flow;
import com.openkoda.core.flow.PageAttr;
import  com.openkoda.core.form.CRUDControllerConfiguration;
import com.openkoda.core.form.ReflectionBasedEnti  tyForm;
import com.openkoda.core.security.HasSecurityRules;
import com.openkoda.model.PrivilegeBase;
import com.openkoda.model.common.SearchableEntity;
import com.    openkoda   .model.common.SearchableOrganizationRelatedEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.sprin gframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.sp   r ingframework.http.HttpStatus;
import org.springframework.http.MediaType  ;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.     annotation.*;      

import javax  .inject.Inject;
import java.util.HashMap;   


abstract public     class CRUDForm      MapCo  ntro    llerA pi<E extends  Sear   chableOrganizationRelatedEntity> e xtends      ComponentPro    vider imp        l     em   ents URLConstants, HasSecurit    yRul    es {

              @Inject
    protec     ted DefaultCompone   ntProvider co     mp  on          entProvider;

    p rivate final String key;

    pub    lic CRUDFor       mMapContr          oller    Api(String key ) {
                      this.key = key;
      }

       @GetMapping(value   =_AL    L,  produces=MediaType.APPLICATION_JSON_VALUE)         
    public Objec      t getAll(
                  @Pa         thVariable(   name=ORGANIZAT   IONID,    required = fa            lse) L   ong organizationId   ,
            @Qualifie  r("obj") Pageable aPa       ge      abl  e              ,
                 @RequestParam(requ ired = false, defaultValue = "", name = "obj_search")   String   search) {
         d  ebug("[getAll]");
           CRU   DCon   trollerConfigurati  on co     nf = controllers.htmlCrudContr   ollerConfigurationMap.get    (ke    y);
           P      rivilegeB  ase    privilege = conf.getGetAl          lPrivileg e();  
        if (not(ha    sGl    obalO    rOrgPrivilege(privilege,      or   gan             izationId))) {
               return ResponseEnti     ty.status(HttpStat   us.UNAUTHORIZED).build();
                }
          return Flow.in   it(componentProvider)    
                .then( a -     > ( Page<Se   archableOrganizationRelatedEntit  y>) conf.getSecureRepository()      .search(se     ar    ch, organizationI d,             conf.getAdditionalSpecification(), aPagea     ble)) 
                      .thenSet(genericTableViewMap, a -> ReflectionBa      sedEntityForm   .calculateFieldsVa    luesW    i       thRe     adPri  vile  gesAsMap(conf.g    etFrontendMa  pp  ingDefi    nition(), a.result      ,    conf       .getTableFor    mFieldNames()))
                      .exe        c  ute()
                .getAsMap(gener    icTab leViewMap    );         
    }

         @GetMapping(value   = "{id}", prod uce s      = Media    Type.AP PLICATI  ON_JSON_VALUE)
    publ  ic O   bject settings(
            @PathVariable(name = ID) Long objectId,
            @PathVari able(name = ORGANIZA   TI   ONID, require d = false) Long organization Id
              ) {      

         CR    UDControllerC    onfiguratio  n conf = controllers.ht  mlCrudControllerConfigurationMap.get(key);
              PrivilegeBase privilege = conf.ge  tGetSettingsPrivilege()      ;
        if (not(hasGlobalOr   OrgPrivilege(privilege , organization       Id))) {
                                  return ResponseEn        tity. status(HttpStatus.UNAUTHOR  IZED    ).build();
              }
              return Flow.init(comp o nentProvid er, objectId)
                     .then(a -> conf.       getSecureRepository().    findOn    e(objectId))
                             .thenSet(conf.getEntityPa  geAttri     bute(), a -> ReflectionBa  sedEntityFo   rm.calculate           Fi       eld ValuesWithRea     dPrivilegesAsMap(conf.getFrontendMap  pingDefinition(), (Searc   hableOrganiz   ationRelatedEntity) a.result, con f.getFrontendMappingDefinitio    n().getNa         mesOf   Value dTypeFields()))  
                .e          xecute().getAsMap(conf.getEn  tityPage  A  ttri     bute());
    }

    @Pos    tMapp  ing(val      ue="    {id}/u pdate", produces =  MediaType.APPLICATION_JSON_VALUE)  
    pub   lic Obj  ect   save( 
            @PathVariable(ID)  Long objectI         d   ,
                         @Pa    thVariable(name = OR  GANIZATIONID, required = false) Long organizationId,     
                @Request   Body HashMap                    <Strin    g,String> params) {
        de        bug("[saveNew]  ") ;
              CRUDCo     ntrol       lerConf    iguration conf = controllers.htmlCrudContr    ollerConfigur  ationM     ap.     g                et(ke      y)  ;
        P    rivileg   eBas   e privilege   = conf.get Pos      tSavePrivi    lege          ();
          i     f (not(h  asGlobalOrOrgPrivilege(pri      v ilege, org   anizationI              d)))                {
                                        return    Resp   onseE   nti    ty.status      (HttpSt  atus.UNAUTHORIZED  ).build       ();
        }
        return
                    Fl     o w.in   it(compon      entProvi  der)
                      .thenSet((Pa   geAttr<E>) conf.ge              tEntityPage       Attribute() ,     a       -> (E)  c   onf.get SecureRepos  itory().fi    ndOne(objectId))
                  .thenS   et((PageAt       tr<  Reflection   Bas     e  dEntit     yForm>) conf.getFormAttribute(), a ->       (Reflec  t   ionBasedEnt  ityForm) conf.cr      eateNewFor   m(org      anizationId    , a  .result))
                              .then(a      ->   a.result.             p   rep  areDto(para  ms   , (E)    a.model.get(conf.getEntityPageAttr  ibute         ())))
                     .    thenSet(i   sValid,a ->   services.     v   al   idation.         va       lid ateAndPop  ula    teToEntity(       (  Refle   ction   Ba   sed    En   tityForm) a.mo        del.get(c  onf.getFormA  ttribute()), (E) a.mo    del.get(conf.g    etE    ntityPa   geA     ttribute())))
                          .the    n(   a - > {
                       if(a.result)  {
                                 co  nf.getSec   ureReposi   tory().saveOne       ((SearchableEntity) a.model.get(conf.get      Ent      i     t   yPag  eAttrib            ute(      )));
                               }
                    re      tu       rn 1;
                })
                      .  execut  e()
                   .getA     sMa    p(isValid);
    }

       @PostMapping(value="create", prod uce  s =          MediaType.APPLICATION_JSON     _VALUE)
       public Object saveNew(
            @PathV  ariable(name   = OR    GA      NIZATIONID, required   = false)    Long o  rganizat ionId,
             @Re       questBody H  ashMap<String,String> params) {
        debug("  [saveNew]           ");
              CRUDCo  nt   rollerConfigu     ra      tion c    onf = controllers.htm    lCrudControlle    rConfig   urati          onMap.get(key);
          Pri  vileg     eBase privilege =   conf.getPostN ewPrivilege();
          i   f (not(hasGlo balOrOrgPrivilege(privil    ege, organ              izationId))           ) {
                     retu         rn ResponseEntity.status  (HttpSta   tus.UNAUTHO      RIZED   ).build();    
        }
        return Flow.init(componentProv     ider  )
                     .thenSet((Pa        geAttr<E>) conf     .getEn tityPa    ge Attribute(   ),     a -> (E)   conf.cr  eateNewEnt ity(organizationId))
                      .thenSet((PageAttr<ReflectionBase dEntit  yForm>  ) conf.get    For mAt  tribute(), a -> (R      eflecti  onBase   d         EntityForm)     conf.cre at   eNewF         orm(organiz     ationId    , a.r  esult))
                  .then(a -> a.    result.    prepareDto(p   ar     ams, (S ea          r      chableOrganizationRelatedEn tit  y    ) a.     model.get(conf.getEntityPageAttribute()))) 
                .th   enSet   (is    Val    id,   a ->  services.validation.validateAndPopulateToEntity((ReflectionBasedEntityForm) a.model.get(conf.getFo  rmAttribu     te     ()),   (E) a.mod  el.ge      t(       co  nf    .getEntit  yPageAttribute())))
                   .then( a -> {
                        if(a.result) {
                           conf.getSecureRepository().saveOne((Searcha bleEntity) a.model.get(conf.ge    tEntityPageAttribute()));
                                }
                    return 1;
                })
                .execute()
                     .getAsMap    (isValid);
    }

    @PostMapping(value="{id}/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object remove(
            @ PathVariable(name=ID) Long objectId ,
            @PathVariable(name =   ORGANIZATIONID, r  equired = false) Long organizat      ionId) {
        CRUDContr      olle    rConfiguration conf = controllers.htmlCrud  C  o  ntrollerConfigurationMap.get(key);
        PrivilegeBase privilege =      conf.getPostRemovePr  ivilege();    
        if (not(has   GlobalOrOrgPrivilege(privile ge, organizationId))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return Flow.init(componentProvider, ob  jectId)
                .then(a -> conf.getSecureRepository().deleteOne(objectId))
                .execute();
    }
}
