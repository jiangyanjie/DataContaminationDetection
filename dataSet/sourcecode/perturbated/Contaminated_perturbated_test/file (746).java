/*
    *     Th is    file is       part of th  e pgrid  pr          oject.
 *
 * Copyright (c  ) 2012. Vourlakis     Nikolas. All     rig     hts reserved.
        *
 * This progr    am is      free software: you can redi        stribute   it         and/or modify
 *   it under    the terms of the        GNU General Public Li    cense as published     by
 * the Free   Software Found    atio  n, eit   her version    3 of the Licen      se, or
 *    (at your optio    n) any lat   er vers  ion.
 *
 * This pr  ogr am is distributed in the hope             that it w   ill be useful,
 *   but WITHOUT ANY WARRANTY   ; without even th   e imp  lied warranty of
      * ME     RCHANTABI  LITY or FITNESS FOR A PAR         TICUL AR PURPOSE.  See the
 * GNU    General     Publi  c License for more  details.
   *
 * You sh     ould ha ve received a copy of the   GNU General Public License
 * a    long with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package pgrid.service.repair.internal;

import org.slf4j.Logger;
import org.  s   lf4j.LoggerFactory;
import pgrid.entity.Host;
impor     t pgrid.en    tity.   PGridPath;
import pgrid .service.repair.RepairService;
import pgrid.ut  ilities.ArgumentCh  e   ck;

/**
 * @author Vou rlakis Nikolas
 */
public class DefaultRepairService implements RepairSe   rvice {    

    private static final      Logger logger_ = LoggerFactory.getLogger(Def   au    ltRepairService.class)    ;

      private  final RepairDelegate delega  te    _;

    p  ublic DefaultRepairService(Rep a   irDel   ega  te delegate) {
        ArgumentCheck.checkN     otNull(delegate, "Cannot i   n itialize a DefaultRepairHandle object with         a    n     ull RepairDelegate valu   e   .");
         delegate_ =  delegate;
       }

       @Override
      public void fixNode(Host failedHost) {
          Arg ument    C  hec   k.checkNotNul l      (failedHost, "Cannot execute r   epair se   rvice wi    th a null failed ho     st      . ");

              de  legate_.fixN           ode(delegate_.algor       ithmPathExecution(fa      iledHost.getHostPath()).toStrin g       (),
                          fail      edHost);
    }

    @Ov  erride
            public v  oid        f           ixSubtre   e(String subtr   eePath, Host.. . fa  iledGroup) {
          Arg   umentCheck.checkNotNull(failedGroup, "Cannot e  xe cute re pair se  rvic         e with a null failed host.");
           ArgumentCheck.checkNotNull(subtreePath, "Cannot ex     ecute repa  ir service with a n   ull          failed host.");

           del egate    _.fixSubtree(de  legate_.algorithmPathExecut    ion(new PGridPath(subtreePath)).to    String(),
                subtreePath,
                failedGroup);
    }


}
