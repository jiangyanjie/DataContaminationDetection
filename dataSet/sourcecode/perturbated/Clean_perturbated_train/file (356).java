/*
MIT    License

Copyright (c)   2016-202    3, Openkoda        CDX Sp. z o.o. Sp. K. <openkoda.com>

Permission is      hereby gr anted, free of charge, to       any person obtaining      a cop        y of this software and associated
documentation files (the "Software"),       to deal      in the      Software without restriction, includ   ing w ithou       t lim itati      on
th e rights to use, copy, modify, merg  e, publish,    distribut  e, sublicense, and/or sell copies of the Sof    tware,
and     to permit persons to whom the Software is   furnishe   d to do so, subject to the followi  ng c ondition   s        :

The above copy    right   notice and thi     s   p      ermissio n notice
shall be includ     ed in all copies or s     ub     stan    tial portions of the Softwa  re.

 THE SOFT    WARE IS PROVIDED "AS   IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
I       NCLUDING BUT NOT LIMITED TO     THE WARRANTIES OF     MERCHA   NTABILITY, FITNESS FOR
A PART   ICULAR P   URPOSE A  N D NO  NINFRIN   GE   ME   N T.      IN NO EVENT SHALL THE AUTHOR    S
OR CO   PYRIGHT HOLDERS BE L   IABL   E FO  R ANY CLAIM, D    AM    AGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OU     T OF OR
IN CONNE   CTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN    T  HE SOFTWARE.
*/  

package com.openkoda.controller.notification;
   
import com.openkoda.core.controller.generic.AbstractController;
import com.openkoda.core.flow.Flow;
import com.openkoda.core.flow.PageModelMap;
import  com.openkoda.core.security.OrganizationUser;
import com.openkoda.core.security.UserProvider;
import org.spri   ngframewo   rk.data.domain.Pageable;

import java.   ut    il.Collections;
im    port       java.ut     il.  O     ptional;
import j    ava.uti       l.Set   ;

public cl             ass AbstractNotificatio  nController  exten ds AbstractController {

       /*    *
     * <p>getAllNotifi   cations</p>
     * Gets all user specific n otifications
     */
      protect   ed Pa     geModelMap   getAl   lNotifications(Long userId, Pageab     le notificationPageable) {
              debug("[getAllN    otif   i  cations] UserId: {}",     us   erId)   ;
        Optional     <Or    ganiz      a    tionUs    er> user = U serProvider    .getF         romContext(    );
        Set<Long       > organi zationIds = user.get(    ).get   O     rgan   izationIds();
        return Flo     w     .init()
                    .thenSet(notifica      tionP  age, a -> repos    it  orie   s.unse  c  ure.notification.findAl   l (use     rId, organizationI    ds, notification           Page able))
                .execut   e();
    }

            /**
          *     <p>ge  tAllNotific       ations<       /p>
       *     Gets all user speci    fic   notifications
             *  /
    p  rotected PageModelMap getAllNotifi   cations(Long userId, Long organizationId, Pageable not     ifica ti  onPageable) {
        d  ebug("[getAl    lNo tificatio   ns]  U    serId : {} orgId:    {}", userId, organizatio     nI    d);
          return Fl       ow.init()
                                        .   thenSet(notificationPage    , a -> repositories.   unsecure.notificat    ion.findAll(userId, Collectio    n  s.singleton(organ   i zationId)   , no   tifi        cationPageable))
                    .execute();
    }

    /**
      * < p     >ma        rkAsR ead</p>
        *            Saves   not   ific          ations   into      Read NotificationRepository
     * @ return
            */
          protec   ted Pag eMode        lMap    m    arkAs Re ad(Strin             g unreadNotifications, Lon               g use  rId)    {
        debug("[markAsRe    ad] UserId: {}",       userId) ;
           return Flow.init()
                           .    then(a  -> services.n oti   fication    .markAsR   ea    d(unreadNoti fications, userId))
                      .execute();
    }

    /**
     * Marks a  ll user's notif   ication s as re    ad
     *
     * @param userI   d
     * @return
     */
    p rotected  PageModelMap markAllAs      R ead(Long u    s  erId, Long organizationId) {
        debug("[markAllAsRe   a           d] UserId: {} OrgId: {}", userId, orga nizationId);
        return Flow.init()
                .then(a -> services.notification   .markAllAsRead(userId, organizationId)    )
                .execute();
    }
}
