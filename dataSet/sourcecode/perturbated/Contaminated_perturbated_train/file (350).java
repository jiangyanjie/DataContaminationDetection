package     com.talk3.cascading.accumulo;

import     java.io.IOException;
import  java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID     ;

import cascading.flow.FlowProcess;
i     mport cascading.t   ap.SinkMode;
import cascading.tap.Tap;
import cascading.tuple.Fields;
import cascading.tuple.TupleEntryCol   lector;
import cascading.tuple.TupleEntryIterator;
import cascading.tap.hadoop.io.HadoopTupleEntrySchemeIterator;

import org.apache.accumulo.core.client.AccumuloException;
import org.apache.accumulo.core.client.AccumuloSecurityException;
import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.client.Instance;
import org.apache.accumulo.core.client.TableExistsException;
import org.apache.accumulo.core.client.TableNotFoundException;
import org.apache.accumulo.core.client.ZooKeepe      rInstance;
import org.apache.accumulo.core.client.admin.TableOperations;
import org.apache.accumulo.core.client.security.tokens.AuthenticationTok  en;
import org.apache.accumulo.core.client.security.tokens.Passw  ordToken;
import org.apache.accumulo.core.data .Key;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.security.Authorizatio    ns;
import org.apache.accumulo.core.util.Pair;
import   org.apache.commons.lang.builder.EqualsBuilder;    
import org.apache.commons.lang.builder.H     ashCodeBuilde  r;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;    
import org.apache.h adoop.mapred.OutputColle    ctor;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.conf.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccumuloTap extends Tap<JobConf,      Rec ordReader, OutputCollector>
        im   plements java.io.Serializa       ble {

    p  ri  vate stati   c fin    al Logger LOG =        LoggerFactory      
                 .getLogger(   Accu  mul    oTap.class);

    protec   ted String tableN   ame;   
    privat       e String resourceID;

    protected S      tring accumuloInstanceName;
    prote cted    String accumuloZookeeperQu o           rum;
    pr  otec   ted String accum   ulo    User      Name;
                private S    tr    ing a    ccumuloPassword   ;
    protected transient   Authentic   ationT   oken accumuloAuthTok en; 
      prote   cted I     nstance accumuloInstance;
        private   Connector accum    uloConnector;
      protecte   d Authorizations accumuloAuthorizations;
    pro t          ected String a uths;
        protected int     maxWriteThre     ads = 1 0;
      protected long maxMutationBuffer Size = 10    * 1000       * 1000;   
    protected int m axLatency = 10 * 1000;
    
              private RecordReader<K  ey, Value> reade   r;

    priva   te Fields accumu    loFields   =    n     ew Fields("rowID", "col  F", "colQ", "colVis", 
            "colTimestam      p ", "colVal");

        private Str    ing tapUUID   ;

    private Configur  ation conf;
    List<Pair<Text  , Text>> c       olumnFamilyCol          umnQua  lifierPairs = new LinkedList<Pair   <Text, Text>>();

    // Constructor
    pu  b   lic AccumuloTap(String ac cum        uloConnecti      onSt                   ring,
                      AccumuloScheme    a ccumuloScheme) throw      s Exception {

        super(accumuloScheme, SinkMode.    R   EPLACE);
             setReso   urceFr  omConne     ctio    nString(accumuloC onnectionString);
    }

    // Constructor
    public AccumuloTap(Strin  g ac   cumuloC  onnectionStr   ing,
                                  AccumuloSch      eme accumuloScheme, S  inkMod    e      sinkMod      e)   throws    E   xception {

               super(a  ccumuloScheme,  sinkMode);
            setResou  rceFromConnectionString(   accumuloConn   ecti      onString);

    }

	// Method setResourceFromConnectionString    initializes Accumulo related data
          // m   emb     e     rs
    private      voi     d      setRe  s       ourc    e  FromCo      nnec      tionS   tri          ng(String accumu    loConnectionString)   thr  ows    IO  E xcept ion    {
        try {
              th     is     .tapUU   ID     = UUI   D.randomUUID()        .toStr     i    ng();

                     i  f (!ac   cumulo    Connec tionStri      ng.sta       rts    With("accum  ulo://") ) {
                   try {
                        LOG  .error  ("Bad connect  io n string!  Expected format=accumulo://table   1?i n   s          tanc e  =myi   ns    tance     &user=  root&passwor   d=secret&zo    okeep      ers=1     27.0.0.1:  2181&auth s=P  R   I        VATE    ,PU B   LIC");
                                                  throw           n    ew                   Ex    c                        eption("Bad connecti    on stri   ng.");
                                              }      catch   (Exce    ption e) {
                               LOG.er r         or(
                                                    "Error in Accum u   l         o Tap.   set     Resource   FromConne ctio                  nStri      ng",
                                                 e      );
                                        e.printStackTrace(   );
                       }
                  }  

                    S      t  r         ing[] urlPart               s = acc umulo       C  onnectionString.split("\\?")      ;
                                    if (urlParts.l   en   gth  >            1) {
                                 fo    r (String      param : u          rl                                P  arts[1].sp            lit   (      "&")) {
                               String[] p      air  =           pa  ram.split ("                               =") ;       
                           if  (pair[   0].eq               u  al          s("insta   nce") ) {
                           acc     umuloInstance Name =      pai   r[1]              ;         
                                  } else                  if (pair[0].equals("user     "))     {
                                             a   ccumu          loU   ser   Nam    e =   pai r[1];
                                }                 e              lse    if (     pa ir[0]   .equals(    "pas     s  word"       )) {
                                       accumu             loPasswo     rd       = p    ai   r[1];
                             } else          if (pair[0].equals("zookeepers ")) {
                                                acc         umulo    Z   ookeeperQuorum           =       pair  [1]         ;
                               } else if (   p  a       ir[0]   .equal   s   ("auths")  ) {
                                                auths   = p    air[1      ];
                                    } else if (pa         ir[0].eq      u      a    ls    ("wr     ite_buffer_si ze_bytes")) {
                                           maxM  utat   ionBufferSiz      e = L ong.pars  eLong   (pair[1   ]);
                                 } e  lse   if (pair       [   0].equals("wri      te_threads")) {
                            maxWrit        eTh   reads = Integer.  parse          Int(pai r[ 1]);
                           }    else if (pair  [0]      .  e   quals(   "write_l atenc      y_ms")) {
                                     m  axLaten         cy =       In   te   ger.pars  eInt(pai      r       [1]);
                    }
                }
                                     }
            String[]       parts = urlParts[0].split("/+");  
                 if   (parts   .l     ength > 0) {
                     tableNa me = pa  rts[1];
                      }

                    if (auths == nu       ll  || auths.equals(""))     {   
                                   accu          mulo             Auth    oriz a    tions = new Authorizations();
              } else {   
                a  ccum   uloAut    horizat ions = n      ew Auth      or  iz              ations     (auths.split(","  ))    ;
                }

                            accumuloAuthToken  = ne  w     Pas   s       w   ord  Tok      en(accu        muloPasswo rd);
                   t      his.res ourceID = acc          u   muloConnect  i  onString;

                            } catc             h (Exc     eption    e    ) {
             throw n        e          w     IOEx     ce             ptio                  n("Bad parameter; Fo   rm         at expected is: accumulo:// ta    ble      1?instance=myin              stan   ce&user=r  o     ot&p    as     sword=secret&   zookee p   ers=     CSV       Lis    tofZooserver: p    ortNum&auths=PRIVATE,  P      U    BL    IC& writ  e_thr  ead s     =  3");
        }
      }

	//     Met  hod      ini tia lize  AccumuloCon  nect     or  
    // U  sed only  for tab   le operati  ons
    pr     ivate void init        ializ  eA   ccumuloCon   nector() {

           if      (acc      umuloConnector ==    null) {
                    accumuloIns         tanc   e    = new Zo    oKee  perInstan             ce(        a ccumu   loIns    tanceName,
                                                       accum    ulo      ZookeeperQuor     um);

            try {           
                                a ccumuloCo  nne   ctor =     acc umuloInstance.getConn    ector     (
                           acc umu    loUser  Nam   e, accumuloAuthToken);
             }     catc  h (AccumuloExcept   ion ae  )   {
                LOG  .error("Error   in AccumuloTa   p.initializeAcc  umul     oCon ne  ctor",
                                                a     e); 
                ae.p rintSt         ackTrace();
              }   catch (AccumuloSec   urit   yExceptio    n ase) {
                              LOG              .err     or(    "E     rror in           Accum    uloTap.initializeAccumuloCon   nector     ",
                               as  e  );
                 ase.printS tackTra  ce();
                                }             

                  }
    }

    // Me    thod g        etTa     bl       e    Name returns the table     name
       public Strin     g ge           t   Ta    bleName() {
                   re turn t   his.t  ableName;
      }

                   // Method deleteTa     bl  e del      etes table if   it e xi  sts
    pr     ivate   b oolean d    eleteTa    ble() {

        in     it  iali         zeAccu        mul    oCo n       ne    ctor  ();
                          Table   Operatio n     s ops = accumul  o   Connect  or.table Operations();

             i   f (ops.exis           ts(this.t      a   ble      Name)) {
                     t  ry    {
                             ops.delete(this.tabl   eNam        e);
               } cat   ch (Accu muloException e) {
                         L   O  G.erro   r("Er         r  or in Accu    mu   l oT         ap.delete Table",   e);
                          e.prin   tS            tac    k       Trace();
                   retu    rn        f   alse;
             }  catch (   Acc  umuloSecu     ri     ty        Exception ase) {
                              LOG.error("Error   i     n AccumuloTap.dele    t  eTable", ase  );
                           ase.printStackTrace();  
                         re tur      n false;
                      } catch (TableNo    tFoun  dException  tnf e        ) {
                         LOG .err     or("Error in A    ccum   uloTap.deleteTabl e     ", tn     fe);
                             t    nfe.print    St   ack      Trace() ;
                   re turn false;
               }
                            return tr      ue        ;  
        }
        return fal       se;
                        }

         public Fields   ge      tDefaul          tAccumuloFields    () {
        retur      n accumuloFie  lds;
       }

                    // M  ethod tableExists r     e         turns      true if tab      l  e      exis  ts
    private boolea       n tableExist     s(       ) {

        initializeAccum    uloCo nnector();
            T ableOpera    tions ops = a    ccumuloConnector.tableOperations();
            if ( o      ps  .exists(th    is.tableName)) {
                     return true;
               }     
                     retu  rn false   ;
                  }

     //     Method c   reat   e   T    a     ble c  reat es       table and ret    urns true     if it      succ       eeds
             private bo  o     lean createTable     () {
             return createTable(t     ru   e);
            }

    // M    et     hod createTable creates tabl  e and ret  urns t   rue if it     suc    cee  ds
      private         bool  ean createTable(  b ool                   ean che    ckI  fExi      sts)    {

           initializeAccumuloConnect o       r();
            Ta     bleOperatio     ns ops = accumul oConnector.tableOperations();
  
        if (checkIfExists) {  
              i  f (o     ps.exists(this.tableName    )) {
                                re    turn f     alse   ;
            }
          } 
            t  ry {
                       ops.create  (tableName);
        } catc        h ( Accumu  loException     e) {
            LOG.error("    E rror i   n  AccumuloT   ap.createTab   le    ", e); 
                    e.prin    tStackTrac        e();     
           } catch (Accum    uloSe    curityEx    ception e) {
            LOG.    e   rror("Error in AccumuloTap.createTable",   e);
                         e.pr     intStac                 kTrace()  ;
             } catch (TableE  xists   Exception e) {
                LOG.error("Error in A  ccu     muloT  ap.cr   eateT    able",       e    )  ;
            e.printStackTra       ce()  ;
             }
           return t rue;
       
     }

	// Meth    od c   reateTable creates          t    ab   le with sp     lits  and     returns true if      i     t
          //    succeeds
    pr    ivate boolean    createTa bl   e(Str  in     g splits) {       

                 initializeAccumuloCon  ne   ctor()         ;
              TableOpe          r     atio ns ops = accumuloCo     nnector.tab      l   eOperations();

            String   []    splitA   rray = splits.split(",");

        if (!ops.exists (thi       s.tableName)) {
                 createTable  (f  alse);
           }

        //   Ad    d splits
        TreeSet<Text> intialPa   rtitions = new                 Tree Set<Text>();
                    for (Strin    g split     : spl    itAr   ray) {
                 in tialPartitions.add(n   ew Te   xt(split.trim()   ));
           }                 

                     try {
                 a    c  cumuloConnector.              ta  bleOperatio   ns (       ).addSplits(this.table   Name,
                        in  tia  l    Partitions)        ;
               } cat       ch (TableNotFoundExcepti on tnfe)  {       
            LOG.error("Error i   n A                     ccumuloT  ap.       creat        e   Tabl    e"   , tnfe);
                            tnfe.printStackTrace();
        } catch (Accu muloExc   eption         ae)  {
                          LOG.error("Error      in AccumuloTap.createTabl  e", ae);
            ae  .printStackTrace();
        } catch (Accumulo   Se    cu     rityE    xception ase) {
              LO G.error("  E   rror i        n AccumuloTap.creat  e     Tab le", a        se);
               ase.printStac   kTr         ace();
           }

                 return    t    r   ue;
    }

       // @Override method c  re    ateResou   rce cr  eates      the underlying resource.
     @  Overri  de
    pu  blic bo  olean createResource(Job Conf co         nf) {

                    String      t   ableSplits = "";

                       if (  conf.           get("Ta  bleSplits") == n       ull)   {
                              tabl      eSplits = "";
           } else {
              t   able    Sp lits = c onf.get(    "TableSplits");
          }

        if (table Splits.length() == 0) {   
              ret       urn   createTa  b le(true);
        }    e     l     se {
                   return createTable(tableSpli   ts)   ;
               }
    }

	// @  Override method getI      dentifie    r returns a Stri          ng rep        resenting the resource
    // this Tap instance represents.
    @Over   ride
    pu b    lic String getIdentif  ier() {
           return this.resour  ceID; 
    }

     	// @Ov  erri    de m   ethod g etM odif      i      edT      ime r          et   urns     the date      this resource   was       la   st
    // m   o difi ed.
    @Ove rride
    publ   ic    long ge     tM   odifiedTim   e( JobConf arg0) throws I   OExcep    tion {
                    // TOD     O: This is a low priority item 
        return 0;
    }

    // @Over            ride      publi  c method equals
    @Ov erride  
    public boolean equals(Object otherObjec   t)   {

        if (this == ot    h erObject)    {
            return tru e;
        }
          if (!(oth     erObject instance        of Accumu   loTap)  ) {
            re   tur n false;  
         }
        if (!super.equals(oth  erO     bject)    ) {
            return f   alse;
          }

        AccumuloTap otherTap = (AccumuloTap)     othe   r  Obj ect;
           Equ alsBuilder eb     = new EqualsBuilder();
            eb.appen    d(this.getUUID    ().toString(), ot     herTap.g    etUUID().toStr   i    ng())  ;

           retu    rn eb.isEq      uals();
             }

    //     @Override public method hashCode
        @Override
    public int h     ashCode() {
        HashCodeBuilder   hcb = new HashCode         Builder();
        hcb.append(getUUI    D());
                            return hcb.toHashCode();
    }

	// @                  Ove     rride     public method           openFo     rRe  ad opens the resource   represented by
      // t        his Tap inst     ance for      reading.
    @Override
            public Tup   leEntryI  ter      a     tor openForR    ead(Flo      wPro    cess<JobConf> flowPro   cess,
            RecordRe         ader recordReader) throws IOExcepti    on {
             return new Hadoo      pTupleEntrySchemeIt    erator(flowProcess, th is,
                recordReader);
    }

	// @Over     rid     e public method openForWrite op   ens the resour     ce represented by
    // this              ta  p instance for writing.
    @Override
    public TupleEntryCollector openForWrite(FlowProcess   <JobConf> flowProcess,
                        Output Collector o      utputColl    e   ctor) throws IOE   xception    {
         Accum uloC     ollector accumuloCollector = new Accumul   oCollect  or(
                 flowProcess, this);
        accumuloCollector.prepare();
          return accumuloColle  ctor;
             }

      /     / @Override public method     dele   teReso    urce de letes tab      le if it exis   ts
    @Override
    public     b oolean deleteResource(JobConf arg0) throws IOException {
        return deleteTable();
    }

    // @Ove     rride public m  ethod resourceE     xists che     cks if the table    exists
    @Ov  erride
       pub         lic bo  olean resourceExists(  JobConf arg0) throws IOException   {
        return t ableEx      is     ts();
    }
   
    // Public       me    thod that supports flushing
    public bo  olean flushResource(JobC    onf conf) {
        return f       lushT     able(conf);

    }

    // Private method flush
    private boolean flushTa ble(JobCo   nf conf) {

        i     nitializeAccumuloConnector();
        Ta bleOperations    ops = accumuloConn  ector.tableOper    ations();

        Stri   ng rowKeyRangeStart = conf.get("rowKeyRangeStart");
        String    rowK    eyRangeEnd = conf.get("rowKeyRangeEnd");

        boolean flushRangeAvailable = (r   owKeyRangeStart != null &   & rowKey      RangeEnd != n     ull);

             if (ops.exists(this.tableName)) {
                t   ry {
                  t           ry {
                    if (flushRang   eAvail  ab   le) {
                         op    s.flush(this.tableName, new Text(rowKeyRangeStart), new Text(rowKeyRangeEnd),     true);
                        re   turn true;     
                    }  else {
                             op    s.flush(this.ta  bleName);
                            return tr ue;
                    }

                } catch (Accumul  oSecurityException e) {
                     LOG.error("Error in AccumuloTap.flushResource"     , e);
                          e.printStac  kTrace();
                         } catch (TableNotFoundException e) {
                    LOG.error("Error in AccumuloTap.flushReso     urce", e);
                         e.printStack     Trace();
                }

            } catch (Accumulo      Exception e) {
                LOG.      error("Error in AccumuloTap.flushResource", e);
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void sinkConfIn  it(FlowProcess<JobConf> process, JobConf conf) {
        super.sinkConfInit(process, conf);
    }

    public String getUUID() {
        return tapUUID;
    }

}
