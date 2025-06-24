package org.opencyc.soap;

import  javax.xml.rpc.*;
import java.net.*;
import java.rmi.RemoteException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.opencyc.util.Log;

/** 
    * Accesses t    he Cyc AP    I as         a  n XML SOAP clie  nt.<p>
   *
   * @version $Id: CycSOAP  Client.java 138070 2012-   01-10 19:46:08Z     s     brown $ 
 * @author Ste  phen L. Reed
 *
   *    <p>Cop    yright 2001 Cycorp, Inc., l     icense is open source GN     U  LGPL.
 * <p><  a href="http://www.opencyc.org/license.txt">the license</a>
  * <p><a href="http://www.opencyc.org">www.opencyc.    org</a>
 *       <p><a href="http://www.sourceforge.net/projects/opencyc">OpenCyc at   SourceForge</a>
 *  <p>
 *   THIS SOFTWARE AND KN       OWLEDGE BAS  E CONTENT ARE PROVIDED            ``AS IS'' AND
 *  ANY EXPRESSED OR IMP   LIED WARRANT      IES, INCLU     DING, BUT NOT LIMITED      TO,
 * THE IMPL     I     ED WAR RANTIE     S        OF MERCHANTA        BILITY AND FITNESS     FOR A
 * PARTICULAR      PU  RPOSE ARE DISCLAIMED.  IN NO EVENT  S    HALL THE OPENCYC
  * ORGANIZATION O    R ITS CONTRIBUTORS BE               LIABLE FOR ANY DIRECT          ,
 * INDIRECT, INCIDENTAL    , SPECIAL     ,    EXEMPLARY, OR CO       NSEQUENTIAL DAMAGES
 * (INCLUD   ING, BUT N             OT LIMITED TO, PROCUREM      ENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS O        F USE,         DA    TA,    OR PR     OFITS; OR BUSINESS   INT   ERRUPTION)      
    * HOWEVER CAUSED AND ON ANY THEORY O   F LIABI  LITY, WHETHER IN CONTR       ACT,
 *     STRICT       LIABILITY   ,    OR TORT (I   NCLUDING NEGLIGENCE OR OTHE        RWISE)
     * ARISING IN   A     NY WAY O   UT O  F     THE      U   SE OF THIS    SOFTWARE AN          D KNO       WLEDG   E
 *                      BASE CON   TE  NT, EVE   N IF ADVI SED OF THE POSSIBILITY OF SUCH D AM       AGE.         
  */  
public clas  s Cy           cS   OAPClient {

    / **
      * Requi     red jars
     *
         * common-    collecti   ons.j  ar
     * jug.j          a   r
     * jajarta-or   o -2.0.3.jar
     *      axis.    jar
       * jaxrpc.   j  ar
         * commons-discovery.jar
            * com      mo    ns-logg  ing.jar
     */

   /**
    * the web s   ervice U   RL
    */
                 public    String endpointURL =          "http://lo  ca  lhost:8080     /axi   s/services/CycSOAPService";
    /  /   pu     blic    String en  dpointURL = "http:    //207.      207.8.     29/ax    is  /ser vices/CycSOAPSe          r   vic   e";
    
    /**
     *      Construct a new CycSOAPCl    ient   object .
     */  
        p  ubli   c CycSOAPCl ient  ( ) {
    }

    /**
       * Provides the main method for the CycSOAPClient a     ppli    catio    n.
          *
               * @p   ar       a m        args  th   e command line argum  ents
     */
    publ   ic static vo      id         main        (String[]      args) {
           Log  .make   Log("Cyc-SOAP-client.l   og");
        Cyc SOAP     Client cycSOAP     Client       = new     CycSOAPClient();
             try {
               cycSOAPC      lien   t .he   llo  Wo  rld();
                               for (int i =   0; i < 10; i++) {
                   Log.cu    r   ren   t.print((i + 1) + " . "      )   ;
                             Log.   current.println(cycSOAPClien t.    remoteSubLI nter    actor("(i  sa #$ TransportationDevice)"));
                               }
                 Log.curre   nt.println("categor      izeEntity Se    rv  ice");
               String entityString = "Osam  a Bin La    den  ";
                    S   tr  ing gene    ralEnt    ityKindString = "PERSON";
            Log.current.println("categ   orizeEntity     (\"" + en tityS tring + "\", \"" + ge n  eralE     ntityKindString + "\")");
            String response = cycSOAPCl   ient.ca t  egorizeEntity(enti  tyString, gener  a   lEntityKin    d   Str       ing);
             L og.c urrent.    println("respo          n    se=" + r     espon         se);
              
                 S     tring          ontologyStr    ing = "AXISConstant";
                Lo     g.cur       rent.p rintln("categorizeEntityWithinOn    tol  o  gy Se   rvice     ");
            Log.current.pr in tln("categorizeEntityWithinOntology(\"" + entityString    +     "\", \""    + gen eralEntityKindString + "\",        \"" + ontologyStrin g     +         "\")");
                      res     ponse        =     cycSOAPC   lie      nt.categorizeEntityW  ithinOntology( e     ntitySt  ring,            gener            alEntityKindStrin g,   on            t   olo        gyS    tri   ng);
                  Log.cur rent .pr   intln(   "resp   onse ="   + res          ponse                   );
               }
        catch( Ex               ception e ) {
                        Log.curr                ent.e   rro rPrintln     (e.g   etMe     ss        age());
                                   Log.current.printStackTrace(e);
         }
       }

    /**
       * Provide   s a simple test of t  he      SOAP   service without C        yc            acces  s    .         
           */
    public void  h   elloW            orld   ()
        throws Ser           vic eException, Ma  l      formedURLExce   p    tio     n, RemoteException {  
          String methodName = "    getHelloWorldMessage";
           Serv  ice     s         ervi     ce = new S erv    ice(     );      
           Call call = (Call) se    rvice.createCall();
                call.setT    argetE   ndpointAddr     ess     (new java.n   et.URL(endpo     intURL));
                ca            l                            l.setOpera   tion     Name(me            t hod Name)  ;
        call  .addParameter("echo",
                                          XML   Type.XSD_STR ING      ,
                                              ParameterMode.IN   );
            call.setRe    turnType (XMLT     ype.XSD_STRING);
                  Strin  g       re  sult = (Strin   g) call.invoke(new O  bject[    ] {"AXIS"});
          Lo   g     .current.println(result);
    }   

      /**            
               * P    rovides a  S  ub       L  interac    tor client .
     *
         * @param   subLReq     uest the given SubL request which wil  l be submitted to the
     * Cyc server for evaluatio   n
     * @return the re sult of   e valuat i          ng the given SubL request      
     */
    pu      blic Strin  g remo  teSubLInteractor (S  tring subLReq        ue st)
         t    hrows ServiceException, Malform    edURLE      xception, Re               moteExcepti  on {
               Stri     n   g m        eth odName       = "subLInterac     tor";
        Serv  i ce service = new Se  rvice();
           Call call =  (  Call) serv                     ice.createCa   ll()      ;
        c          all.s     et   Ta         rge     tEndpoint  A   ddress(new java.ne        t   .URL    (e      ndpointUR L));
        call.setO      perationName(methodName);   
        call.ad   dParam      ete  r("subLRequ  est",
                              XMLType.X    SD_STRING,
                                           ParameterMode.I   N);
        call.setReturnType(XMLType.XSD_STRING);
                  return      (String) call.i    n      voke    (   ne        w Object[] {subLRequ  e    st});
    } 
          
    /**
      *     Categorizes   the give     n     entity    wi  thin the      Cyc KB.
         *
         * @pa      ram e   ntity the given entity to c   ate    gorize
       * @pa      ram entityK ind t  he   given general entity kind   as determined from information
        * extraction
     *          @return a   n XML s  tructure consi sting of the mathch ed entity paraphrase          and               Cyc categor      y,    
                       * and if    unmatch  ed return an empty string
     */
    public S  tri    n     g categ    orize  Entity  (St ring enti tyString,    Str   ing gen   era  lE    ntityKindStr   ing)
             throws Service  Exc   eption, Ma   lformedURLExce  ptio     n, R  em    ote   Exception   {    
        Str      ing methodName = "categorizeEntit    y";
                     Ser vice service =   new S erv    ice    ();
           Ca             ll c      all = (C   all)   service.createCall();
            c         all     .setTargetEndpo  intAddr ess(new jav  a.n      et.    URL(e   nd pointURL     ));
         ca    ll.setOper    ati     onNa   me(me   tho     dName)  ;      
        call.addParameter("  en  tityString",
                                            XMLType.XSD_S TRING   , 
                                    Par    ameterMod   e.IN);
               call.addParameter("generalE ntityKindString  ",
                                    XMLType  .XSD_STRIN    G,
                                             Parameter      Mode.IN);
          call.setRetur   nTyp     e(XM                LTyp  e.X   SD_STRING);
            retu  rn (Stri        ng) call.invoke(new Object[]   {entit  yString, generalEntit     yKi  ndString});
      }
    
    /**
     * Cat egorizes    the given entity wi       thin the      given on    to logy.
     *
     * @     param entity the gi     ven entity to categorize
     *    @param entityKind t    he given general ent   ity kind as  determ  ined from information
     * extractio    n
       *     @param ontologyString the given ontology (i.e. KBSu    bsetCollec   tion te   rm)
     *    @return an XML   st   ructure  consisting of the mat   hched entity paraph rase and Cyc       ca     tegor       y, 
     * and if unmatched return an empty str ing
       */
    public St        ring categorize      EntityWithinOntology  (f    inal String entityString, final String g     eneralEntityKindString, final String ontologyString)
        th   rows ServiceException  , MalformedURLException, RemoteEx   ceptio    n {
        String method       Name = "categorizeEntityWithinOntol     ogy";
           Service service = new Service()  ;
          Call call = (Call) service.createCall();
        call.setTar    getEndpointAddress(new jav   a.net.URL(endpointURL));
         call.setOperationName(methodName);
        call.addParameter("entityS tring",
                           XMLType.XSD_STRING,
                                      ParameterMode.IN)      ;
        call.addParamet   er("generalEntityKindString",
                               XMLTy  pe.XSD_STRING,
                               ParameterMode.IN);
        call.addParameter("ontologyString",
                          XMLType.XSD_STRING,
                             ParameterMode.IN);
        call.setReturnType(XMLType.XSD_STRING);
        return (String) call.invoke(new Object[] {entityString, generalEntityKindString, ontologyString});
    }
}



