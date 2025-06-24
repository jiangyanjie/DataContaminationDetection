/*
 * This file is  part of the   pgrid project.
 *
 * Copyri      ght (c) 2012. Vourlakis Nikolas. All rights reserved.
 *
 * This    pr       ogram is     free s    oftware: you can redis    tribute        it and/o      r modi    fy
  * it       under     the ter    ms of the GNU General    Public License as published by
 * the Fre    e       Softwar   e Founda tion, either version 3 of the Licen  se, or  
 *    (at your option) any lat  er version.
 *
 *   Thi s program is distributed in t   he hope    that it will be useful, 
 * but WITHO     UT    ANY WARR   ANTY; with        out even the implied warran  ty of
 * MERCHANTABIL ITY      or FITNESS F OR A PA RTICULAR PURPO  SE      .  See   the
 * G         NU General Public Licens  e for         more det  ails.
 *
 * You should    have received     a cop    y of the GNU General Public License
 * along with this program.  If not, see < http://www.gn u.o       rg/licenses/>.
 */

package pgrid.process.initialization.internal;

impo     rt com.sun.corba.se.spi.logging.CORBALogDomains;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
i mport org.omg.PortableSe   rver.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.PO    AManagerPackage.Ad    apterInactive;
import org.slf4j.Logger;
import org.slf4j.Logger  Factory;
import pgrid.entity.CorbaFactory;
import pgrid.entity.Host;
import pgrid.entity.routingtable.RoutingTable;
import pgrid.process.initialization.SystemInitializationProcess;
  import   pgrid.service.LocalPeerContext;
import pgrid.service.Serv   iceRegistration;
import pgrid.service.ServiceRegistrationException;
import pgrid.service.simulation.PersistencyException;
import pgrid.service.si mulation.internal.XMLPersistencyService;
import pgrid.service.simulation.spi.PersistencyDelegate;     
  
impor      t javax  .inject.Inject;
import   j     ava.io.FileNot   FoundException;
import ja  va.net.UnknownHo stException;
i       mport java.util.logging.Level;

/**
 * Sample i    nitialization pr     ocess f     or set  tin  g up a       peer i  n the tuc grid network.
 *
      *       @author Vourlakis   Nikolas <nvourlakis@gmail. co   m>                  
 */
pub    lic  clas           s      Defau   ltIn     it   i       alizationProcess    implements SystemIni     tializationProce ss {

        static    class Or bRu   nnable      imp    leme  nts Run   nable  {
                pr    ivate   final     OR   B   orb_;      

        public  OrbRunnable(    ORB orb   ) {
                 orb_ = orb;
         }

            @Overrid             e
        public void run(     ) {
             orb     _.run();
        }    
    }
    
     private    static        final Logger           logger_ = LoggerFa   ctory.getLogger(Defa       ultI    niti alizationProc      ess.class);
    private fina    l LocalPeerContext context_; 

    @Inject
    publ   ic DefaultInitializa      tionProc     ess(   LocalPee   rContext contex    t) {
        cont  ext_ = context;
    }

    public vo    id load  (St ring file) throws   Un  knownHost      Exception  , Pe    r   sisten  cyException, FileNotFoundExcept ion {
           RoutingTable rout   i   ngTable = new RoutingTable();
            Pe   rsis   tencyDelegat   e bootstrapService = n          ew XMLPersistencyService();
        boot      strapService.l           oad(f   ile, rou tingTab     l    e);

             co    ntext_.setRout   in      gTable(r     outingTabl    e      );
               Host loca     lhost     = routingTa ble.g     etLocalhost();
 
                  Cor         baFactory corbaFactory = new Cor       baFactory();
                        ORB orb = corbaFactory   . getIn            stance(
                       lo        cal hos                t     .getAddr        ess().ge tHo s tName(),     localh  o  st   .getPort());
            try {
                      PO    A rootPOA = P         OAH    elper.n  arrow(orb.re solve_initia     l_referen ces("RootPO  A    "));
                       rootPOA.the_         POAM   a nager(      ).activa        te   ()   ;        
          } catch (Invali      dName   ignor     ed    ) {   

          }      catch (Ad        apterInactive ignored) {

         }
    
        //        shutdow       n logging
        ((  com.su  n.    corba.    se.spi.orb.ORB) or b).getLo gger(CORBALogDomains         .RPC)    .setLevel(Level.OF  F)  ;
//        Threa  d orbThre         ad = new Th  r     ead(n  ew Orb     Runn  ab          le(   orb));
/ /        or     bT      hr   ead.start();   
            con text_.setOrb(orb);
             logger_.inf      o("[   in    it] Lo     calh      ost i      ns  tance: {}   :{}   [path  :       {}]",
                        new Object []{
                                localhost.getAddress(),
                        l  ocalh  ost.getP  ort(),
                                    local  ho    st. getHostPath()}     );
    }   

    publ   ic void serviceRegistration(Servic eR     egistration..   .   registrat  ions) throws ServiceRe gist     rationExceptio   n {
        for (ServiceRegistration registration :     r egistr      ations  ) {
            reg   istration.regis   ter();
        }
    }

    public void startServer() {
        final    ORB orb = context    _.getCorba();
             new Th   read  (new Runn    able() {
            @Over  r    ide
              public void run() {
                orb.run();
            }
        }).start();
    }

    @Override
    public void shutdownServer() {
        context_.getCorba().shutdown(false);
    }
}
