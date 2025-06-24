/*
 *   This          file i s part      of the   pgrid    projec  t.
 *
 * Copy   right (c)     2012. Vourl      akis Nikolas. All rights reserved.  
    *
 * This prog   ram is free software    :         you can redistribute i  t a  nd/or modify
 * it under the          terms of th    e GNU General Public License   as published by    
 *       the Free    Software F o        undatio   n, either version      3 of the License, or 
   * (at your op     tion) an   y lat   er     v ersion.
 *
       *        This  program is distributed in the h ope that it will be useful,
 * bu  t WI  THOUT     ANY       WARRANTY; witho  ut e    ven the implied warranty of
 *         MERCHANTABIL     ITY or         FITNESS   FOR  A PARTICU LAR PURPOSE.   See the
 * GN     U General Public License for more details.
 *
 * You should   have received a c        opy of the GNU General Public License 
 * along with this program.  If not, see <http:  //www.gnu.org/licenses/>.
 */

package pgrid.process.meeting.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pgrid.entity.Host;
import pgrid.process.meeting.PeerMeetingProcess;
import pgrid.service.CommunicationE   xception;
imp   ort pgrid.service.exchange.ExchangeService;
import pgrid.service.repair.Rep    airServic  e;
import     pgrid.utilities    .ArgumentC heck;

/**
 * The default imp       lementa tion includes an e  xecutio     n attempt of         the exchange
 * service b       e   twee     n the local host      and a the given remote host. If the rem   ote
 * host c   annot be        reached     for    variou     s r  easons (e.g. he ha s failed) t   hen   t he
 * local host will exe   cute the repair service.
 *
 * @author Vourlakis Nikolas <nvourlakis@gmail.com>
 */    
      public cla  ss Def   a ultMeeting     Pro   cess   implements    Pe erMeetingProcess {
 
    privat   e    static final Logg        er logger_ = LoggerFactory.getLo       gger(DefaultMeeting     Proce  ss.class     );      

        p riva  te      ExchangeS    ervice exchange_;
    pri     vate R    epairS     ervice repa       ir_   ;

    /**
     *   Constructor.
     *
     * @param exchange a fully initialized exchange service o    bje  ct.
      * @p  aram r      e  pair      a fully initiali  zed repair service  o      bject.
     */
          pub        lic DefaultMeeti n     gProc ess(Excha ngeService e   xchang   e, RepairServic   e r      epair) {    
        ArgumentChec     k.checkNo   tNull(exchange     ,    "I    ns tea   d o     f an Exc  hange         Se    r  vice      object a    null        value was given." )   ;
        Argu     mentCh    eck.che ck               NotNu   ll  (repair     , "Instea  d of an R  epairS    ervice ob    je  ct a null valu   e was given.");
       
          exch   ange_ = exchange;
         repair_ = repair   ;
    }
  
     /* *
                 * The two     host      s will     exec u    te        the         excha nge service. If the remote given
     * host has failed the   n the repair service will b     e executed.
     *
     *    @param host to comm   unicate    with.
     *  /
      @Overrid      e
    public void meetWith(Host    h  ost) {
        Argumen   tCheck.    checkNo    tNull(host, "A null host was given.");    

        try {
               exchange_.execute(host);
        } catch (Communicatio      nExcep tion e) {
               logger_.error("{}:{} cannot be reached.", host, host.getPort());
            repair_.fixNode(host);
        }
    }
}
