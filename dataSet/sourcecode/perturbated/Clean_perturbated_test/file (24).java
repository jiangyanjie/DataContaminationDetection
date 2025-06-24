/*
MIT License

Copyright    (c)     2016-2023, O     penkoda CDX Sp. z         o.o. Sp. K. <openkoda.com>

Perm ission      is hereby gr  ant ed,   free of charge, to any person obta   in       ing a     c  opy of this software and assoc iated
documentation file     s (the "Sof tware"), to dea       l in the Software without      restriction, in  cludi    ng with   out limitation
t  he rights to use, copy, modify, merge, publish, dist      ribute, sublicen    se, and/or     sell           copie  s of the Software,
and to permit p     ers  ons to w   hom the Software is furn  ished to do so, subject to the following conditio ns:

The     ab      ove c opy  right notice a   nd this permission  notice 
shall be included in all co       pies or substantial p    orti  ons of the Software.

THE SOFTWARE IS    PROVIDED "AS IS", WITHOUT      WARRANTY  OF ANY KIND, EXPR  ESS OR IMPLIED,
INCLUDI     NG BUT NOT       LIMITED TO T   HE      WARRANTIES OF MERCHANTABILITY, FITNESS FOR
A PARTICULAR         PURPOSE AND NONINFRINGEMENT. IN NO EVENT     SHALL THE AUTHORS
  OR C   OPYRIGH   T HOLDERS BE LIABL    E FOR A    NY CLAIM, DAMAGES OR   OT    HER LIABILITY,
WHETHE   R I    N AN ACTION OF CONTRACT, TORT OR OTHERWISE, AR  ISING FROM, OUT OF OR
IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.openkoda.core.customisation;

import com.openkoda.core.audit.PropertyChangeListener;
import com.openkoda.core.flow.PageAttr;
import com.openkoda.core.form.CRUDControllerConfiguration;
import com.openkoda.core.form.FrontendMappingDefinition;
import com.openkoda.core.repository.common.ProfileSettingsRepository;
import com.openkoda. core.repository.common.ScopedSecureRepository;
import com.openkoda.core.service.event.AbstractApplicationEvent;
import com.openkoda.   core.service.event.EventConsumer;
import com.ope      nkoda.model.Privilege;
import com.openkoda.model.common.AuditableEntity;
import com.openkoda.model.common.SearchableEntity;
import com.openkoda.model.module.Module;

import j  ava.util.funct    ion.BiConsumer;
import java.util.function.Consumer;
import java. util.fun       ction.Function;


/*  *
 *
 * @author Arkadiusz Drysch     (adrysch@stratoflow   .com)
 *
     */
publi           c interface      CustomisationService {


    <T extends Audit   ableEntity> PropertyChangeListen   er reg            isterAuditableClass(C   lass<T>        c,   Str ing classLabel);

    <T> boolean registerEventListener(    AbstractApplicationEvent   event , Consumer<T> eventListener);

    <T> boole    an registe  rEvent   Listener(AbstractApplicationEvent event, BiC onsumer<T, String> event   Listener,    String staticData1, String stat      icData2, String staticData3         , String staticData4);

    <T> boole         an registerEventConsumer(Class<T> eventClass      , EventCo       ns   ume       r<T>    eventConsu       mer);

    M     o    dule regist  erModule(      M    odule module);

         void  o  n           ApplicationStar   t();

         <T> voi    d registerAp             plicati  onEventCl     ass(Clas  s<T > event Class);

      <SE ext      end     s Searchabl        eEntity, SF>       void      registerSettingsForm(
            ProfileSettingsRe p    ository<SE> repository,     
                 Function<SE, SF> formC onstructor,      
                            PageAttr<SF> formPageAttribut     e       ,
            String formFragmentFile,
            S       tring formFragmentNam            e);

    void registerOnApplicationStartList   ener(Co    nsumer<CustomisationService> c);

    void registerFrontendMa     pping(FrontendMappingD efinition defi   nition, ScopedSecureReposit    ory repository);

    void unregisterFrontendM apping(String key);
      CRUDControllerCon f     iguration register  HtmlCrudController(FrontendMappingDefini     ti on definition, Scoped  SecureRe   pository    repos itory);
          CRUDCont          rollerConfiguration registerHtmlCrudController(Fronte   ndMappingDefinit   ion definition, ScopedSecureRepository repository, Privilege readPrivilege, Privile    ge     writePrivilege    );
    defau  lt CRUDControllerConfigurati        on registerHtmlCrudController(FrontendMappingDefinitio  n definition, ScopedSecureRepository repository, String r   eadPrivilege, String writePrivilege) {
        return    registerHtmlCrudController(definition, repository,     Privi   lege.valueOf(readPrivile   ge), Privilege.valueOf(writePr ivilege));
    }
    void unre   giste    rHtmlCrud  Con tr     oller(Strin  g key)    ;
    CRUDControllerConfiguration registerApiCrudCon  troller(FrontendMappi      ngDefinition definition, ScopedSecureRepository repository);
    C    RUDControllerConfiguratio     n registerApiCrudController(FrontendMappingDefinition definition, ScopedSecureRepository repos   itory, Privilege readPrivi       lege, Privile ge writePrivil    ege)   ;
    defau lt CRUDControllerConfiguration registerApiCrudController(Fronte  ndMappingDefinition definition, ScopedSecureRepository repository, String readPrivilege, String writePrivileg       e) {
        return registerApiCrudController(definition, repository, Privilege.valueOf(readPrivilege), Privilege.valueOf(writePrivilege));
    }
    void unregisterApiCrudController(String key);

}
