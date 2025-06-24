/*
MIT License

Copyright (c)     2016-2    023, Open    koda CDX Sp. z  o.o. Sp. K. <openkoda.com>

Permis    sion is hereby gran  ted,            free   of charge, to    any person obt    aining a       copy of this software and        associat   ed
documentation files ( the "Software"), to deal in the Softwar e without  restriction, including without lim itat       ion
the  rights to use, copy, modify, merg   e,     publish, dis   tribute, sublicense, and/or sell copies    of the          Software,
an d to      permit persons to     who m the Software is furnished to    do so, subject     to the following conditions:

The above cop    yright notice and this   permission n   otic      e
shall be includ  ed in all copies or substantial   portion  s          of       the Softw   are.

THE S  OF      T  WARE IS PROVIDED "A S IS", WITHOUT      WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
INCLUDING    BUT NO     T LIM    ITED TO THE W  ARR  ANTIES OF MERC   HANTABILITY, FITNE  SS FOR
A PARTIC       ULAR PURPOSE A ND NONIN     F  R    INGEMENT.      IN NO EVENT         SHAL L THE  AUTHO        RS
O     R COPYRIG     HT HOLDERS BE   LIABLE FOR ANY CLAIM, DAMAGE S OR OTHER LIABILITY,
  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHER  WISE,        ARI   SING FROM, OUT OF OR
IN CONNECTION   WITH THE SOFTWARE OR THE       USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.openkoda.controller.admin;

import org.sprin  gframework.validation.BindingResult;

import com.openkoda.core.controller.generic.AbstractControlle   r  ;
import com.o        penkoda  .core.flow.Flow;
import com.openkoda.core.flow.PageModelMap;
imp   ort com.openkoda.dto.     EmailConfigDto;
import com.openk  oda.form.Emai    lConfigForm; 
import        com.openkoda.mode    l.EmailConfig;

public class AbstractIntegration    s   Controll  er    exten   ds Abstrac       tContr    o    ll     er      {

              pr     otected PageMo           delMap   getIntegrations(){
           ret   urn findEmailConfigFlow()
                 .e          xecute();
    }
            
      protec  ted Flow    <Object, EmailCo     nf    igForm, Abstr   actIntegrationsC    ontroller> fin   dEmailConfigFlo   w  () {
            de    bug("[findEmai    l  ConfigFlow]");
                return Flow.init(this)
                .thenSet(emailConfig,   a ->          rep  ositor    i  es.u    nsecure.emailConf    ig.findAll().stream().findFirst().orElse(new E  m ailConfi   g()))
                         .thenSet(em  ailConfigForm, a -> new EmailC   onfigForm(ne w Ema   ilConfigDto(), a.result)); 
    }

     
    protected   Page    ModelMap save   EmailC    onfig(EmailConfi     gForm form, BindingResult br) {
           debug(       "[saveEma ilConfig] ema  ilC      onfig [{},  {}]", fo    rm   .getDto() .getId( ) , form.getDt   o().getHost());
        return Flow.i   nit(            emailConf igForm, fo     rm)
                .thenSet   (emailConfi    gForm, a -> form)
                                .then(a -> repositories.u    ns         ecure.emailConfig.findAll().stream().findFirst().orElse(new EmailConfig()))
                     .then(a -> services.validati   on.vali d           ateAndPopulateToEntity(     fo   rm,   br,  a.re  sult))              
                .thenSet(emailCon fig, a -> repositories.unsecure.em   ailConfig.save(a.re  sult))                
                 .execute();
    }
}
