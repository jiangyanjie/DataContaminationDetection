/*
         * T      his      file is          part of the       pg    rid project.
 *
 * Co        pyright (c) 2012. V ou   rlakis Nikolas. All        rights   rese   rved.
 *
    * This          program is free software: y     ou   can red  istribute it and/o  r modify
 *        i     t under the terms of the GNU Gener al Public L  icense as pub  lished by
 * th  e F  ree Softwar      e Fou       ndatio   n, either   version 3 of the L icense, or
 * (    at y       ou    r opti  on) any later version.
 *
 * Thi     s progr      am is distr   ibuted in the hope t  hat it will be use f   ul,
 * but WITHOUT ANY WARRANTY; wi    thout even t  he implied warranty o f
 *     MERCHANTABILI    TY or FITN ES       S FOR      A     PARTIC ULAR PURPOSE.        See th  e
  * GNU General Public Lice  nse for more details.
 *
 * You should    have received a copy of the GNU Gener   al Public License
 * along with  this pr ogra   m.  If not, see <http://www.gnu.org/licenses/>.
 */

package pgrid.service.exchange.inter     nal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pgrid.entity.routingtable.RoutingTable;
import pgrid.service.corba.Corba Routin gTable;
import pgrid.service.corba.exchange.ExchangeHandlePOA;
import pgrid.service.exchan  ge.spi.ExchangeAlgorithm;
import pgrid.service.exc     hange.spi.ExchangeContex     t;
import      pgrid.utilities.ArgumentCheck;
import pgrid.utilities.Deserial    izer;
impor  t pgrid.utiliti       es.S  erializer;
               
/**
 * @author Vourlakis Niko          las
 */
pub   lic class DefaultExchangeHan   dle extends ExchangeHandlePOA {

    pri vate static final Logger logger_ = Logg   erFactory.ge  tLogger(DefaultExc         hangeHandle.c lass);

    priv     at   e f     inal Routing  Table  local   RoutingTable_;
    private ExchangeAlgorithm algo _    ;

    private  final int REF_MAX;
    private fin    al int  MAX_RECURS     IONS;

    public Default    ExchangeHandle(Rou  tingTa     ble localRoutingTable, Exchang  eAlgorithm algo, int    max  Ref,    int max    Recur) {
                    A   r    gumen  tCheck.checkNotNull(localRoutingTable    , "Cannot initialize a De  faultExc   hang  eHandle object wi  th    a null RoutingTabl    e value.");
                   Argume   ntCheck.che      ckNotNull(algo, "     Cannot initia   lize a     DefaultExchangeHa     nd      le object with a n   ull Excha  ngeAlgorit hm   value.");

        localRou    tingTable_ =    localRo                utingTa               ble;
           algo_ =        a lg   o;
         REF_MAX = m    axRef;
            MAX_RECU  RSIONS = maxRecur;
          }

    @Overri     de
    publ   ic CorbaRouti                 ngTable routingTable() {
          // a remote pe er wants the local to send its ro     uting table
           return S   erializer.s   erializeRou  tin gTable(loca  l   RoutingTable_);    
       }
  
       @ O         verride
    publ   ic void exchan    ge(Corb     aRoutingTable routin gTable) {
                      if (routingTable ==      nul  l) {
            logger_.warn("Received        an exchan    ge request       bu        t  w    as pr       ov ided w     ith a null Co   rbaRout   ingTable object.");
            return;
        }   
        // a remote peer wants the loca    l to execute the exchange algor   ithm
        RoutingTable remoteRT = Des  erializer.deserializeRoutingTable(routingTable);
        ExchangeContext context = new Excha ngeContext(l       ocalRouting       Table_,     true, R  EF_M   AX     );
        context.setRemoteInfo    (remoteRT);
              algo_.execute(context);
        // TODO: Implement the reaction to the recursion case of the exchange algorithm.
    }
}
