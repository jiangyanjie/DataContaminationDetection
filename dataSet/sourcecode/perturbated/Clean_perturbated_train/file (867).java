/*
MIT   License

Copyrig   ht (c)    2016-2023,  Openkoda     C    DX S   p. z o.o. Sp. K. <openkoda   .com>

Permiss        io   n  is hereby    granted, free              of    charge, to any person obtaining a copy   of t    his s       oftware an d associated
documentation files (the   "Software"), to deal in the        Software wi   thout rest ricti    on, incl  uding wit   ho    ut limit  ation
the rights to use, copy, mo    dify, merge, publish, distribute, subli    cense, and/or      se      ll co   pies of th     e    Sof  tware,
an    d to permit persons to whom the Software is   furn  ished to do so, subje   ct to the following conditions:

The ab     ove copyright notice and    this permissio n notice
shall          be included    in all cop     ies or substant    ial portio   ns of t he Software.  

THE S        OFTWARE IS PROVIDED "AS IS",         WITHOUT WARR  ANTY OF ANY KIND,     EXPRESS OR I M    PLIED,  
INCLUDING BUT N OT LIMITED TO THE WAR   RANTI   ES OF MERCHANTABILI       TY, FIT  NESS FO    R
A PARTICULAR PURPOSE AND      NONINFRINGEMENT. IN N O EVENT S   HALL THE AUTHO     RS
OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
     WHETHER IN AN ACT   ION    OF CONTRA CT, TORT OR OTHERW    ISE, A  RISING FROM, OUT OF OR
IN CONNECTION    WITH THE SOFTWARE OR THE US  E OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.openkoda.controller.api;

import com.openkoda.controller.ComponentProvider;
import com.openkoda.controller.DefaultComponentProvider;
import com.openkoda.controller.c    ommon.URLConstants;
import com.openkoda.core.flow.Flow;
impor  t com.openkoda.core.flow.PageAttr;
import com.openkoda.core.form.CRUDControllerConfiguration;
import com.openkoda.core.form.ReflectionBased EntityForm;
import com.openkoda.core.security.HasSecurityRules;
import com        .openkoda.model.PrivilegeBase;
import com.openkoda.model.common.SearchableOrganizationRelatedEntity;
import org.springframework.beans       .factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.Ht   tpStatus;
import org.springframework.http.MediaType;
    import org.springframework.http.Resp onseEntity;
import org.      springf     ramework.web.bind.annotation.*;

import j    avax.inject.Inject;
   import java.  util.HashMap;


abstract public class      CRUDApiController<E extends   SearchableOrga nizationRela   tedEnt ity> extends    Com ponent  Provider impleme nts        URLConstants, HasSecur   ityR ules {

    @   Inject
          protected De    faultCo    mponentProvi d   e        r componentProvider;

    private       final Str  ing key;

          pu    blic CRUDApiControll    er(St    ring ke           y) {
         t    his     .key = key;
      }      
      
    @GetMapping(value=_ALL, produ    c   es=MediaTyp      e.APPLIC          ATION_JSON_VALUE)
    public Objec t getAll( 
                        @PathVariable(name=O   RGANIZATIONID   , r   equire   d = fals  e) Long organ      izationId,
                    @Qualifier    (   "obj") Pageable aP  ageable,
                 @Requ       estParam(required = false, defaultValue = "", name    = "obj_     search") String searc h) {
              de    bug("[getAl     l]");
             CRUDControllerConfigura    tion conf =      controlle   rs.apiCrudControllerCon figurationMap.get (key     );
            Pr    ivileg          e Base privilege = conf.getGetAllPrivilege();
        if (not(hasGlo  bal OrOrgPrivile        ge(privilege, organizationId))) {
             r   eturn Res ponseEntity.status(Ht    tpStatus  .UNAUTHORIZED).b   uild();
        }
        return Flow.init(componen   t     Provider    )
                  .then( a -> (Page<Searcha bleOrga    n    izati  onRel ated       En            ti ty>) conf.getSecureReposit  ory().search(searc h, or    gani    zation      Id, conf.getAddit            ionalSpeci    fication(), aPa   ge  able) )
                      .thenS       et(genericTableViewMap, a      -> ReflectionBasedEntityForm.   c   alc            ulateFiel  dsV      alues WithReadPrivilege   sAsM      ap  (con       f.getFrontendMa ppingDefini    tion(  ), a.r    esu   lt, conf.getTableF   or     m    FieldNames()))
                .exe    cut     e()
                  .ge   tAs   Map(ge nericTable   View    Map  );
    }

    @GetMapping(value =      "{id  }", prod       uces =     M                  ediaType.APPLICATION_JSON_VALU E)
    public Object settings(
            @PathVariable(name = ID) Long   objectI    d,
            @       P   athVariabl         e(name = ORGANIZATIONID    , r   equired = false) Long o    rganiza  tionId
                        ) {

          CRU    DControlle   rConfiguratio     n conf = controllers.apiCrudC   ontro  llerConfigurationMap.get(ke      y);
        Pr   ivi  legeBase privilege = conf.getGetSettingsPrivile  ge();
        if (no     t(hasGlobalOrOrgPrivilege(privilege, organiz      ationId))) {
                  return Respo    nseEntity.s   tatus(HttpStatu       s.UNAUTHORI       ZE   D).build();
        }
        re    tur    n Flow.init(co mponentProvider, objec       t      I   d)     
                  .th  en(a -> co  nf.getSecureRepository().findOne(obj          ectId))
                  .thenSet(conf.getE    ntity       PageAttribute(), a    -> ReflectionBase    dEn             tityForm   .calculateFieldValuesWi  t hReadPriv    ile    gesA  sMap(conf.getFrontendMap   pingDefin it  ion(),    (Searchab   leOrganization   Relate    dEntity) a.resul        t, conf.get     F       rontendMa       ppingDefinition().getNamesOfV  aluedTypeFields()))
                       .execute().get  AsMa       p(co  nf.getEntityPage  Attribute());
        }

    @Post Mapping     (value="{id}/        upda        te",      produces =   MediaType.APPLICATION_JSON_VALUE) 
    public Object   save(
            @PathVariable(ID) L   ong o       b    jectId,
              @PathVa  riable  (      name = ORGANIZATIONI       D, required = f  alse) Long organiza   tionId,
            @RequestB    od   y HashMap<Strin    g,S       tring> pa   ram     s) {
        debug(  "[saveNew]");
        C  RUDCont     ro llerConfigurati on conf = controllers.apiCrudControllerConfi     gurationMap.ge t(key);
        Pri   vilege        Ba    se    priv      ilege = conf.getPostSavePrivilege();
           if   (not( hasGloba   lOr    OrgPriv  ileg   e    (privilege, orga  nizati     onId))) { 
            ret urn R    espo  nseEntity.stat      us(Ht  tp Status.UNAUTHORIZED ).build()   ;
                   }       
                     return
                  Flo w.i    nit(compon  entProvider)
                       .thenSet    ((PageAttr<E>) conf.getE     ntityPageAttr    ibute(),       a -> (E        ) co nf.getSec           ur        eRepositor  y()   .findOne(objectId))
                        .thenSet((PageA      ttr   <ReflectionB   asedE   n tity     Form>)     conf   .getFormAtt    r    ib   u    te(     ), a      ->   (Re  f   l  e     ction    BasedEnt  ity Form) con   f.cre  at   eNewFor   m(organizationId, a.res   ult))
                   .the    n(a -> a.r   esult    .pr  epareDto   (pa    r     ams, (E) a.       mo  del .get(conf.getEntityPageAttribute())))
                                   .thenSet(   isValid,a  ->  serv ices.validation.va          li      dateAndPo         pulateTo  Entity((ReflectionBasedEnt ityForm) a.mo  d          el.  get(   co  nf  .getF   ormAttri b    ute()), (E)    a.model.      get(conf.   getEnti tyPageAtt  r  ibute())))
                                .then    Set     (longEntityId, a -> {
                         if(a.result) {
                              ret   ur  n (     (E) con  f.getSecur     eReposito   ry().sa   veOne(a.mo  del.ge t(conf.getEntity  PageAt  tr   ibu    te()))).ge             tId   ();
                     }
                                                  return             null      ;
                         })
                          .exec ute()
                                .getAsMap(isVa  l id, longEntityId);
          }

    @PostMap          ping   (v        alue="cr     eate", p      roduces = MediaType.APPLICATI   ON_JSON_VALUE)
            public Object save   Ne       w(
                 @ Path   Varia     ble(name = ORG        ANIZATIONID   , required = false   ) L  ong        orga n i     zati        onId             ,
                @RequestBody HashMap<    String,String> params)         {
        debug("[sa    veNew]");
         CRUDControllerConfigur  ation conf = contro    llers.apiCru  d  Contro       ll        e rConfigura  t   i   on Map.get( key);
               Pr ivilegeBase     pri     vilege = conf.getPostNewPr   ivilege();
            if (not(hasGlobalO  rOrgPrivilege(privilege, organizationId))) {
                return ResponseEntity.status(HttpStat   us.UNAUT    HOR      IZED).build();
        }
               return Fl ow .init(compone  ntProvider   )
                  .thenSet((PageAtt r<E>) conf.getEntityPageAtt ri  bute(), a -> (E) conf.createNewEnt  ity(or gani      zationId))
                              .thenSet((  PageA   ttr<Refl   ec  tionBasedEntityForm>)       conf.get  FormAttribu te(), a     -> (ReflectionBasedE   ntit yForm) conf.cr          eateNewForm(organiza     tionId, a.result))
                       .t      hen(a -> a.result.prepareDto(params,          (SearchableOrganizationRelatedEntity) a.mode       l.get( conf   .getEntityPage  A    ttribute()   )))
                  .thenSe  t(isValid,   a -> services.vali dation  .validateAndPopulateToE   ntity((Refl         ectionBasedEntityForm) a.model.get(c  on   f.getFormAttribute()     ),     (E) a.mode l.get(conf.getEntit        yPageAtt        ribute(   ))))
                  .thenSe   t(longEntityId, a -> {
                           if(a.result )   {
                               return ((E)    conf.getSe      cur eRep   ositor    y().saveOne(a.model.get(conf.getEntit    yPageAttribute()))).getId();
                    }
                      return null;
                  })
                .execute()
                .getAsMap(isValid, longEntityId);
    }

    @PostMapping(value="{id}/re  m o     ve", produces = MediaType.APPLICATION_JS   ON_VALUE)
    public Object remove(
            @PathVariable(name=ID) Long objectId,
                 @PathVariable(name = ORGANIZATIONID, required = fa  lse) Long organizationId) {
          CRUDControll erConfigur ation conf = controlle rs.apiCrudControllerC       onfigurationMap.get(ke y);
        Privil  egeBase privilege = conf.getPostRemovePrivilege();
        if (not(hasGlobalOrOrgPrivilege(priv      ilege, organizationId))) {
            return ResponseEntity.stat   us(HttpStatus.UNAUTHORIZED).build();
        }
        return Flow.init(componentProvider, objectId)
                .then(a -> conf.getSecureRepository().deleteOne(objectId))
                .execute();
    }
}
