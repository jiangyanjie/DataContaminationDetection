/*
MIT      License

Copy   r ight (c) 2016-2023, Openkoda CDX  Sp.   z     o.o. Sp. K. <openkoda.com>

Permissi   on is hereby granted, fr ee of charge, t    o any person obtaining a copy  of this     software and associated
docume   nt   ation files (the "Software")   , to de    al in the So      ft  ware without restriction,  including without li  mitation
the rights to      use, copy, mo      dify, merge, publish,       distribute, sublicense,    and/or sell copies   of the Software,
and t o   permit      pe          rso   ns to      whom the Software is      furn      is    hed to do so,    sub                 ject to t    he following condit    ions:

T   he above copyright notice and th is permission notice
shall b    e included in all  copies or    substantial portions of the Sof tware.

THE SOFTWARE IS    PRO VIDED "AS    IS", WITHOUT WARRA  NTY     O F ANY KIND,   EXPRESS O     R IMPLIED,
INCLUDING B   UT NOT LIMITE  D TO THE WARRANTIES OF        MERC     HANTA   BILITY, FITNESS FOR
 A PAR  TICULAR PURPO SE AND NONINF   RINGEMENT. IN NO EVENT SHALL THE AU    THORS
O R COP  YRIGHT HOLDERS  BE LIABL E FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN A     N ACTION OF CON   TRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF  OR
IN CONNECTION WITH THE  S OFT  WARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.openkoda.core.controller.event;

import com.openkoda.controller.ComponentProvider;
import com.openkoda.core.flow.Flow;
impo  rt com.openkoda.core.flow.PageModelMap;
import com.openkoda.core.security.       HasSecurityRules;
import com.openkoda.form.SchedulerForm;
   import com.openkoda.model.component.Scheduler;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jp  a.dom   ai     n.Specification;
import org.springframework.validation.BindingResult;

/**
 * <p> Cont    rolle   r that provides actual {@link Sc         heduler} related   functionality for differ      ent types of access
     * (eg. A PI,    HTML)</p>
 * <p>Im plementing    classes should tak e     over http binding and forming a result whereas thi s co n          troll  er should     take   care
 * of actual implementation</p>
 * See   a l  so {@link SchedulerControllerHtml}
 *
 * @auth   or Martyna Litkows     ka (mlitkowska@stratoflow.com)
    * @     si     nce   2    019-03-20
 */
public clas  s AbstractSchedulerCont ro ller extends Co     mpon   entProvider imp    lement  s HasSecuri      tyRules {

    /*   *
     *    Re     trieves {@link Schedu     ler} page from the database for the parameters pr      o  vided.
     *
           * @param schedul  e    rSearc   hTerm
     * @param schedu     le        rSp    ecificati   on             {@ link S pecification}   for {@link Sched   uler} retr  iev    al
        * @param  schedulerPagea  ble {@link Pageable} for {@link Schedu        ler} page search
     * @return c                  om.openkoda.core  .flow.PageMo       de        lM  ap
          */
               p   rotected PageModelMap    findSchedu       le              rsFl     ow(
                    String    sc              heduler        S           earch      Ter        m,
                     Spe  cifi       cation<Scheduler> sche   dul e    rSpecification,
                  Pageable sch  edu      lerPageab     le) {
         debug     ("[      findSchedulersFlow] search {}", schedule   rSe arc  hTerm);
        retur n Flow.init(  )
                        .thenSet(schedul     e  rPage, a -> repositories.  secur  e.scheduler .s   ear ch(sc  hedulerSearchTerm, nu ll, sc         hedulerS  pecification, sched   ule rPageable))
                .         execute();
    }

    /*   *
     *    Retrie    ves        th {@link               Schedul        er} en   tity      from the dat      abas  e f      or   t    he pr             ovided ID           a nd pre   pa      res {@l  ink Sc     hedulerF   orm} for   it.
     *
     * @param schedulerI  d
       *           @return com.ope    nkoda.core.flow.Pag   eMod     elMap
     */
    prote cted PageModelMap find(lo    ng schedulerI   d) {
        debug("[find]      schedulerId {}", schedu     lerId);
                return    Flow.init()
                         .thenSet         (sc          hedulerEntity, a    - > reposito ries.s     ec      ure.sc     heduler.findOn  e(    schedu  lerI  d)    )
                      .t   henSet(schedulerForm    , a  -> new SchedulerForm(null, a.re      sult))
                .   execute();
         }


    /**     
            * Val  id    at      es data in the {@link S           c    hedule   rForm} provided and saves a new {@link Schedu  ler} record in the database.
           *      For a successful s       ave, it         re    gister       s the sc  heduler in the run   ni    ng applicat       ion.
          * See       also {@link com.o penkoda.core.service.event.ClusterEv    e ntSenderService}
     *
     * @pa  ram s   chedulerFormD a  ta   {     @link Schedule   rForm}
     * @p a    ram br
     * @return c om.open  koda.core.flow.P  a geModelMap
     */
       protec       ted P  ageMod  el   Map create(Sched   ulerForm   sc hedule    rFo rmData, BindingRe      sult br)        {
           deb     ug("[cre    ateScheduler]");
        r      eturn Fl  ow.init(schedulerForm, schedulerFormData)
                                  .t   hen(a -> services.validation.validat   eAndPopulateToEntity(schedul   erForm     Data, br  ,new Scheduler()))
                    .the  nSe      t  (schedulerE   ntity, a      -> r epositories.unsecure.   scheduler.saveAndFlush (a.resul t  )  )
                       .then(a -> s            ervices.c              ompon   entExport.exportToFileIfRequi   red(a.result        ))
                  .then(     a ->  servi ces.schedul er.loadCluste  rAw         are(a.resu   lt.getId()    )     )
                                                 .thenSet(schedulerFor  m, a -> new SchedulerForm() )
                              .execu   t   e();
    }

        /**
       * Ret   rieves the {@link Sc      heduler} entity    from the databas        e for           the provid    ed ID.
     * I  t then validates   the       c    ontents of {@link Sch    ed  ul         e  rForm} and populat   es d           ata to t he     entity.
     * After updating the database record    succ essfu      lly it tri    ggers reload of the updated schedu     ler running       in the applicat   ion.
     * See      al  so {@lin   k    com.openkoda.core.service.event.ClusterEve ntSenderServic      e}
           *
     * @ param sc  hedulerId
     * @param sched  ule  rFormDat a {@link     Schedul    erForm}
     * @param br
     * @return com.  openkoda.core    .flow.PageModelMap
            */
    p    rotected    PageModelMap   update(long scheduler    I  d, SchedulerForm schedul     erFormData, BindingResult    br) {
            debug("[updateSchedu   le   r          ] schedulerId: {}"   , schedulerId);
         re  turn Flow.init().in  it(schedulerForm, sch edulerFo  rmData)
                .  then(     a -> reposito    ries.unsecure     .schedu     ler.          find   One         (sch   edul    erId))
                     .then(       a    -> services.v  alidation.      validateAndPopula  teToEntity(schedulerFormData, br,a.result))
                          .then(a ->   repositories.unsecure     .scheduler.saveAndFlush(  a .     result))
                .t      hen( a -> servic     es.componentExport.exportToFileIfRequired    (a.result))
                .then(    a -    > services.scheduler.reloadClust      erAware(a.result.getId()))
                .execute();
        }
    
    /**
     * Remov     es {@link Scheduler} w   ith     a pr o      vi     ded ID from the database and removes it from      currently running scheduler     s in the app  lication.
     * See also {@link com.openkoda.core.service.  event.ClusterEven tSenderService}
     *
     * @param schedulerId
     * @  return com.openkoda.core.flow.PageModelMap
     */
    protected P    ageModelMap remove   Scheduler(long schedulerId) {
             debug("[removeScheduler] schedulerId: {  }", schedulerId);
        return Flow.init(schedulerId)
                .then(a -> repositories.s   ecure.scheduler.findOne(schedulerId ))
                        .then(a -> services.componentExport.removeExportedFilesIfRequired(a.result))
                .then(a -> repositories.unsecure.scheduler.deleteOne(schedulerId))
                .then(a -> services.scheduler.removeClusterAware(schedulerId))
                .execute();
    }
}
