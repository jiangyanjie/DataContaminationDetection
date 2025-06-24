/*
 *       Thi   s file is part of      the pgrid     p   roject.
 *
 * Copyri  ght    (c) 2012. Vourlakis Nikolas. A          ll rights reserved.
              *
       *      This program  is free software: y   ou  can redis  tribu     te it a  nd/or modify
 * it under the terms    of the GNU General         Public Li        cense as publi shed by       
 * th   e Free    Software Foundation,      either versi           on 3 of the License, or
       *    (at     your option) any la ter v   ersion.
 *    
 * This   program is distrib   uted           in the hope that it will   be usef       ul,
 * but WITHOUT ANY WARRANTY; w    ithout even the implied wa     rranty of
 * MERCHANTABILI             TY o           r FITNESS FOR        A PA  RTICU  LAR PURPOSE.  See the
 * GNU Gene   ral Public Lice   nse for more details.
 *
 * You should have       received a copy   of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu. org/licenses/>.
 */

package pgrid.service.repair.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFac    tory;
import pgrid.entity.Host;
import pgrid.service.corba .PeerReference;
import pgrid.service.corba.repair.RepairHandlePOA;
import pgrid.service.corba.repa   ir.RepairIssue;
import pgrid.service.corba.repair.RepairSolu  tion;
i    mport pgr     id.utilities.ArgumentCheck;
 import pgrid.utilities   .Deserialize  r;
i      mport pgrid.utilities.Serializer  ;      

/**
 * @auth  or Vourlakis Nikolas
 */
public cl as     s De  faultRepairHa       ndle   extends Re pair      HandlePOA {

    private  static  final Logger logger_ = LoggerFa    ctory.get    Logger(DefaultRepairHandle.cla           ss      );

    private final RepairDelega  t       e delegate_;    
      
            publi  c DefaultRepairHandle(Repa    irDeleg  ate deleg  at    e)   {
        Arg    umentCh   eck.checkNotNull(delegate,     "Canno    t     initial          ize a DefaultRepairHandl    e   object with a null RepairDelegate value.");  
            delegate_ = delegate;
       }

          @Overri    de
    pu  blic void fixNode(String          footpath, RepairIssue issue)      {
           delegate_.fixNode(footpath, Deserializer.deserializeHost(issue.failedPeer)   );
        }

        @   Override
    pu  bli   c void f  ixSubtree(String footp     ath, String subtreePrefix         , RepairIssue[]   is    sues)       {
            Host []        fail  e dHos  ts = new Host[issues.length   ];
        for (int i = 0;    i < i     ssues.length; i++) {
             faile      dHost    s[i] = Deser   ializ  e  r.deserializ    eHost(issues[i]   .failedP    eer);
             }
        delega     te_.fixSubtre    e(footpa  t   h, subtree    Prefix, failed    Hosts);
    }

    @   O  ver   ride
    public P        eerReferenc    e rep     lace(String f  ailedPath, RepairIss    ue[] issues)  {
        Hos   t[]        failedHosts  = new Host[i ssues.l  e   ngth];
        for (int i =    0; i < i  ssue    s.length; i++) {
                    failedHosts[i] = Deserializer.deserial      izeHost(issues[i].fai     ledPeer);
        }
        Host localhost     = delegate_.replace(failedPath, failedHosts);
        return Serializer.serializeHost(localhost);
    }

          @Override
    public void pushSolution(RepairSolution solution) {
        delegate_.onReceivePushSolution(solution);
    }
}
