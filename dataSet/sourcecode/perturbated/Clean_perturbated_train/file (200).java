/*
MIT License

Copyright     (c)      2016-2023, Openkod  a CDX      Sp. z o.o. Sp.      K. <openkoda.com>

Permission is hereby granted, f   ree of charge     , to any per     son ob   taining     a copy of this software and associated
documentation   files    (the "Software"), to deal in the Software      with    out restriction,    includin  g without limitation
the rights        to use, copy, modify,  merg   e, publish,   distribute, sublicense, and/o   r sell cop  ies     of the      Softw            are,
and to permit persons to whom the Software is furnish ed to do so,   subject  to the followi   ng conditions:

T he ab  ove cop    yright    notice and this      permission notice
shall be inc    luded in all co pies or substanti   al portions of    the Softwa   re.

THE      SOFTW   ARE IS PROV   IDED "AS IS", WITHOUT WARRANTY O     F ANY KI    ND, EXPRESS OR IMP      LIED,
INCLUDING BUT NOT L   IMITED TO THE  WARRANTIES   OF MERC      HANTABILITY, FITNESS FOR
A PARTICULAR PURPOSE AND NONINFRINGEM    EN  T. IN            NO EVENT SHALL THE AUTHORS
OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER  IN      AN ACTION OF C    ONTRACT, T   ORT OR OTHERWISE, ARISING FROM, OUT O      F OR
IN CONNECTION WITH THE    SOFT      WARE OR THE USE OR OTHER     DEALINGS IN THE SOFTWARE.
*/

package com.openkoda.core.controller.event;

import com.openkoda.controller.  ComponentProvider;
import co    m.openkoda.core.flow.Flow;
im    port com.openkoda.core.flow.PageModelMa   p;
import com.openkoda.core.helper.JsonHelper;
import com.openkoda.core.security.HasSecurityRules;
import com.openkoda.core.service  .event    .AbstractApplicationEvent;
import com.openkoda.dto.NotificationDto;
import com.openkoda.dto.OrganizationDto;
import com.openkoda.dto.payment.InvoiceDto;
import com.openkoda.dto.payment.PaymentDto;
import com.openkoda.dto.payment.PlanDto;
import com.openkoda.dto.payment.SubscriptionDto;
import com.openkoda.dto.system.FrontendResourceDto;
import com.openkoda.dto.system.Schedule     dSchedulerDto;
import com.openkoda.dto.user.BasicUser;
import com.openkoda.dto.user.UserRoleDto;
import com.openkoda.form.EventListenerForm;
import com.openkoda.form.FrontendMappingDefinitions;
import com.openkoda.form.SendEve ntForm;
imp   ort com.openkoda.model.component.event.Event;
import com.openkoda.model.component       .event.EventListenerEntry;
import org.apache.commons.lang3.StringUtils;
import or     g.springframework.data.domain.Pageable;
import org.springfram    ework.data.jpa.domain  .Specifi    cation;
import org.springframework.validation.Bi  ndingResult;

import java.io.IOException;
import j   a va.util.Map;
impo  rt java.util.stream   .Collectors;

/**
 *    <p     >Controll    er            that provides   actual {@link      c  om.openkoda.core.serv  ice.event.EventList  ener} related functionality
 * for       different types of access (eg.      API, HTML    )</p>
 * <p>Implementing classes s    hould take over ht          tp     binding  and forming a result whereas this controll er       should t   ake care
 * of actual impl emen   tat      io   n</p>
 *
 * <p>See also {@link EventListenerControllerHtml},      {@link EventList       enerEntry  }</p>
 *
 * @author  Ma   rtyna Litkowska (ml   itkowska@st                ratofl   ow.co m)
 * @since 2019-03-1    1
        */
public class AbstractEventListenerController ex       tends Compone       n    tPr  ovider imp lement   s         HasSecurityR    ules    {

        /**
           * Retrieves {@link Eve     n     tListen       erEntry } p age from the da  tabase for the parameter    s provided
     *
           * @param e     ventListenerSearchTer m
     *    @param eventListenerSpe   cification {@link Spec    ification} for {@li         n   k Even   tListe      nerEnt   ry} retrieval
       * @param e v     en  tLis   tenerPage  able {@link Pageable} for {@link Ev     entLi  stenerE     nt           ry    } page     search
     * @return com       .openkoda.core.flow.PageMo           delMap 
     */
    protected PageModelMap      findListenersFlow(
            Stri     ng ev  entListenerSea  rchTerm,
            Spec    ification<Eve n     tListenerEntry >     eventListener  Specificatio       n,
            Pagea  ble eventL      istenerPageab     le       ) {
         d          eb        ug("[findListenersFlow]");
            return Flow. init()
                        .thenSet(e  ventL   istenerPage, a -> repositor  ies.     s   ec              u    re.eventListener.s      earch(eve        ntListenerSearchTe  r    m  , null, event   Li        stenerS  pecificat    ion      , ev        entListene     rP            ageable))   
                               .e  xecute();
      }

    /**
       *    Prepares {@link   E       ventListenerForm} fo  r the {@lin  k EventListenerEnt    ry} retri    eved from the dat  abas e.
           *
     * @para    m organizationId
         *    @param eLi    stenerId
        * @return c        om.openkoda.core.flo  w.P       ageModelMap
     */
    protected    PageModelMap find(Lo  ng o         rga         nizatio    nId, long eListene  rId) {
         debu        g("[find] ListenerId: {}"     ,  eListenerI    d);
               return       Flow.init()
                        .thenSet(eventLis tenerEntity,  a -> rep             osit        ories.unsecur      e.ev e   ntListen      er.findOne(eListener    Id))
                .th      enSet(eventListenerForm, a -> new EventListene rFo     rm(organizationId, a.result))
                                 .e     xecute();   
    }     


        /**
           * V      alidates {@link  EventListenerForm} data,  populates to {@link EventListenerEntry} and      saves n   ew re     co  rd in the database.
     *    After succe  ssful save the {@link Ev  entListen   erEntry} is being   r  egi    stered as a liste    ner.
       * S    ee also {@link com.open        koda.co      re.        service.event.   EventListenerSer   vice}
          *
     *   @para   m       eListenerForm
     * @param br
          *  @          return    com.openkod  a.core.flow.P        ageM  ode lMap
     */
    prot    ected PageMo        delMap create(EventListene   rForm eListener For     m, BindingResult br) {
        debug("[create]");
           return Flow.init(eventListene     rForm   ,    eList   e   ner   Form)
                .then(a -> se     rvices.validation.val   id     ate   AndPopulateToEntity(eListenerF or   m, br,new   Event ListenerE   ntry()      ))
                  .then   Set(eve        ntListenerEntity   ,    a -> repos  i   to     ries.unsec   ure.ev  entListener. save(     a.result))
                .then(a         -> services.componentEx   port.exportToFileIfRequired(a.result))
                           .then(a ->      serv          ices .e  v        e   ntListener.regist   erLis  tenerClusterA     wa     re((EventListenerEntry) a.res     ult))
                  .thenS           et(event    ListenerForm, a - > new E    ventListen         erFor  m())
                        .ex e   c       ute(  );
      }

    /**     
     * Retrieves {@link EventLis tenerEntry} fr   om the databa se for the gi  ven ID.
       * Then validates {  @link EventListener   Form} d   ata, updates the ex  is      ting {@link Eve       nt    Lis  ten  erEntry}       entity and               updates th    e dat abase.
     *                For a   successf      ul update it up  dates the ac tive list     ener.
     * See als o {    @link c      om  .openkoda.core.s     ervi    ce.event.EventListe    nerService     }
     *
     *  @pa    ram eventList                enerId
                   *       @para m eListenerForm
     * @para   m br
      * @return com.openkoda.          c  ore.flow.PageModelMap
     */
    protected     PageMo       d  elMap update(long eventListenerId, EventLis     tenerForm eListenerForm, B  indingResult br) {
          de    bug("[update] L is      t    enerId: {}   ", eventListe  nerId);
                return Flo     w.init(even  tListenerFo    rm, eL    istenerForm          )
                                          .then(a -> r        epositori e      s.unsecure   .eventLi  s tener.findOne(     eventListen erId     ))
                .then(a   ->       se   rvices.validatio n.val                idateAndPopulateToE ntity(eL  isten         erForm, br ,   a.result  )  )
                  .then(a ->   repositories.unsecure.eventList ener.saveAndFlus   h(a.resul       t))
                       .then(  a -> services.componentExport.    exportToFileIfRequired(a.result))
                 .then(a     -> services.ev   entListener.up  dateEventListen    erClusterAware((Ev  entLis   tener    En  try) a.res  ult))
                    .exe    cute();
    }

    /     **
          * Remov     es {@lin          k E    ventListen   erEntry} fro              m the database     f        o  r the       g   iven ID   .
     * A fter s   uccessful removal the active li   stener is       be      i ng u nre  gister        ed fr om the a    pp.
               * Se    e also {@link com.openk   o  da.core.service.even   t.EventListenerService }, {@l   ink Event       L        isten            erForm}
     *
        * @param eventLi   stenerId
     *  @   retur            n com.openkoda.core.flow.PageMod     elMap
                 *     /
    protecte   d P   ageModelMap remove(long    eventListenerId) {
             deb  ug(" [rem       ove] Listener  Id : {}", even        tListenerId);
         return      Flow.init (e v ent List       enerId)
                        .thenSet(        eventListenerEnti tyToUnregister, a    -> rep ositories.uns ecur       e.event    Listen er.findOne(e      ventListenerId))
                          .then(a -> services.componentExpor        t   .remove    ExportedFiles  IfRequired(a.res  ult ))
                            .then(a -  > repositories.   unse     cure.eventListener.deleteO             ne( a  .resu lt.ge     tId() ) )
                       .then(a -> services.eventListen   er.u   nregisterEventListenerClusterAw     are(a.model.get(eventListener   Entity                   ToUnregi        ster)))
                            .execute()  ;
    }


       /**
     * Prepa res the {@ l  ink S  endEven      tForm}   for the manual e    vent sending (available only i       n Admin pa n   el)
     *
         * @return com.o       penko      da     .core.flow.Page    ModelMap  
           */           
    pr  otect    ed        PageMode     lM     ap choos    eEvent() {
                       debug(           "[    cho     oseE   ve      nt]"); 
        return Flow.init()
                                      .thenSet(sen  dEventForm, a -> new SendEventForm())
                   .execute();
    }

    /**
        * Pr     epares the {@link Send  E       ventForm} fo     r    a particul    ar {@link    Eve        nt} cl  ass.
           * Mean    ing t   hat      the form retur    ned   by the method     contains   fie    lds for th  e      dto obje              ct of   the    selected eve   nt type.
      *
        * @pa      ram eventForm          {@link SendEventForm}
     *  @param   br
     * @r         eturn com .     openkoda.co   re.flow.PageMod           elMap
     */
    protected P  ageModelMap prepareEv       ent(Send  Eve       ntForm e    ve     ntForm, BindingR    esul t br  )    {    
                    debu    g("[pr  epareEvent]");    
        return Flow.init(sendEve       ntForm, even     tFo  rm)
                  .th      en(a ->     services   .v   alidation    .v  alida  t   e(eve   ntF        orm, br))
                .t  hen(a ->   new Even      t   (a.resu     l  t.getEvent   ()))
                        .thenSet(sendEve   ntForm,          a -> ge   tEventFor      mForClass(a.result     ))
                .execute();
         }   

            /**
     *       Em its event triggered manually from the Admin panel.
      * Du   e to gene      ric app  roach to this functionality, the e  vent da    ta is in the  form of {@l      ink M   ap} object.
        *   See als  o {@lin  k      c om.op   enkoda.core.service.event.      ApplicationEven   tService}
     *
     * @pa     ram even     t   Data map of even  t   object para  meters
     *     @return com.openkoda.core    .f      low.PageModelMa        p
       * @throws IOException
     */
    protected PageMo   delMap e   m itEvent(Map<  String, Str   ing> eventData) throws IOException {
             debu       g("    [emi     tEven     t]");  
        Event event = new Event(event   Data.remove(  "event"    ));
        Map<String,             String>     objectDat  a = eventData.entrySet().stream()
                  .filte      r(e -  >       e.getKey().startsWith("d        to.") && StringUtils.isNotBlank(e.g     etVal       ue() ))
                .collect(C   o   llectors  .toMap(Map.Entry::getKey,    M   ap.Entry::getValue));
        String jso n  = JsonHelper.formMapToJson(objectData);
        json  = "{"     + "\"object@" + event.getE    vent    ObjectType() + "\":" +   json + "}   ";
            Object object        = JsonHelper.f  ro mDebugJson   (jso  n).      get("objec      t   ");
              AbstractApplicationEve   nt appEv     ent   =  AbstractAppl         ication     Eve   nt.ge       tEvent(event.getEve         ntName());
        return Flow.init(sendEventForm, new SendEventForm(eve  nt.getEventString  ()))
                   .then(a -> services.applicationEvent.emitEven   t(appEven                t, object))
                .ex   ecute ( );
    }

      /** 
           * Prep   ares {@link  SendEventFo   rm} on the basis of {@link Event} class p   r    ovided.
     * T here is a different set       of fields for each eventObje ctType      of the {@link Event}.
     * Thi     s is for the manual   trigger of events in t he Admin panel.
        *
      * @p  aram event {@link Event}
     * @retur      n com.openkod    a.for  m.    Se   ndEventForm
     */
      priv ate SendE  ventForm getEventFor            mForC  lass(Eve  nt event) {
        switch (event.   getEventObjectType()){
                   case "com.openko   da.dto.payment.InvoiceDto":
                return new SendE   ventFo    rm<>(new InvoiceDto(), Fr    onte  ndMap     pingDefinitions.sendEventInvoiceDto, event.getEve    nt String());
              case "com.openkoda.d  to.paym   e    nt.PaymentD  to":
                 return new SendEventForm<>(new P   aymentDto(), F            rontendMappingDefinitions.sendEventPaymentDto  , event.getEventString());
            case "com.o       penkoda.dto.payment.PlanDt  o":
                    re turn new SendEventForm<>(new Plan    Dto(), FrontendMappingDefinitions.sendEventPlanDto, event.getEventString  ());
            case   "com.openkoda.dto.payment.Subsc    ription   Dto"  :
                retu         rn new   SendEventForm<>(new Su  bscriptionDto(   ), FrontendMappingDefinitions.sendEventSubscriptionDto, event.getEventString());
            case "com.openkoda.dto.syst      em.CmsDto":
                return new SendEventForm<>(new FrontendReso     urceDto(), Front     endMappingDefinitions.sendEventFrontendRe  sourceDto, event.getEventString( ));
            c    ase "com.openkoda.   dto.sys   tem.ScheduledSchedulerDto":
                 return new SendEventForm<>(new ScheduledSchedulerDto(), FrontendMappingDefinitions.sendEventScheduledSchedulerDto, event.getEventString());
            case "com.openkoda.dto.user.BasicUser":
                ret   urn new SendEventForm<>(new BasicUser(), FrontendMappi ngDefinitions.sen dEventBasicUser, event.getEventStrin    g());
            cas e "com.o     penkoda.dto.user.UserRoleDto":
                return new SendEventForm<>(new UserRoleDto        (), FrontendMappingDefinitions.sendEventUserRoleDt   o, event.getEventString());
            c       ase "com.openkoda.  dto.OrganizationDto":
                return new SendEventForm<>(new OrganizationDto(), FrontendMappingDefinitions.sendEventOrganizationDto, event.getEventStri  ng());
            case "com.openkoda.dto.NotificationDto":
                return new SendEventForm<>(new NotificationDto(), FrontendMappingDefinitions.sendEventNotificationDto, eve      nt.getEventString());
            default:
                return new SendE   ventForm();
        }
    }
}
