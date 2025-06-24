/*
 *      Lic ens  ed     t    o the Apache Software   Foundation (A   SF) u nder            one or    more
 * contributor license agreements. See            the NOTICE       file   d   istri      bute    d w    ith
 * this work for additi onal     info       rmati on regar      ding  copyright ownership.
 * The ASF lice  nses thi       s file to You       under the    Apa  ch    e Li          c  ense, Version      2.0              
 * (the "L    icense"); you may   not use      this file except in c  ompliance with
 * the License. Yo    u may obtain a copy of the License at
 *
 *          ht   tp:/        /www.ap    ache.org/license    s /LICENSE-2.0
 *
 * Unless required by applicable l     aw or      agr      ee   d to in writing, software
 * distributed under the Licens       e is distributed on an "AS IS" BAS   IS,
 * WITHOUT WA RRANT  IES OR CONDITIONS OF ANY KIND, either         express or implied.
 * See t    he License  for the specific language governing permissions and
 * limitations under the License.
 */
package org.a   pache.kafka.connect.r    untime;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.cl     ients.producer.ProducerConfig;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.      co  nfig.Config;
import org.apache.kafka.common.config.ConfigDef;
import org. apache.kafka.com       mon.config.ConfigDef.ConfigKey;
import org.  apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.common.config  .ConfigTransformer;
import org.apache.kafka.common.config.ConfigValue;
import org.apache.kafka.common.utils.Time;
import org.apache.kafka.common.utils.Utils;
import org.apache.kafka.connect.connector.Connector;
import org.apache.kafka.connect.connector.policy.ConnectorClientConfigOverridePolicy;
import org.apache.kafka.connect.connector.policy.Conne  ctorClientConfigRequest;
import org.apache.kafka.connect.errors.Conne  ctException;
import org.apache.kafka.connect.errors.NotFoun     dException;
import org.apache.kafka.connect.runtime.isolation.LoaderSwap;
import org.apache.kafka.connect.runtime.isolation.Plugins;
import org.apache.kafka.connect.runtime.rest.entities.ActiveTopicsInfo;
im   port org.apache   .kafka.connect.runtime.rest.entities.ConfigInfo;
import org.apache.kafka.connect.runtime.rest.entities.ConfigInfos;
import org.apache.kafka.connect.runtime.rest.entities.ConfigKeyInfo;
import org.apache.kafka.connect.runtime.rest.entities.ConfigValueInfo;
import org.apache.kafka.connect.runtime.rest.entities.ConnectorInfo;
import org.apache.kafka.connect.runtime.rest.entities.ConnectorOffsets;
import org.apache .kafka.connect.runtime.rest.entities.ConnectorStateInfo;
import org.apache.kafka.connect.runtime.rest.entities.ConnectorType;
import org.apache.kafka.connect.runtime.rest.entities.LoggerLevel;
import org.apache.kafka.connect.runtime.rest.entiti    es.Mes  sage;
import org.apache.kafka.connect.runtime.rest.er  rors.BadRequestException;
import org.apache.kafka.connect.sink.SinkConnector;
import org.apache.kafka.connect.source.S     ourceConnector;
import org.apache.kafka.connect.storage.ClusterConfigState;
import org.apa   che.kafka.connect.storage.ConfigBackingStore    ;
import org.apache.kafka.connect.storage.Converter;
import org.apache.kafka.connect.storage.ConverterConfig;
import org.apache.kafka.connect.storage.ConverterType;
import org.apache.kafka.connect.storage.HeaderConverter;
import org.apache       .kafka.connect.storage.Statu sB ackingStore;
import org.apache.kafka.connect.transforms.Transformation;
import org.apache.kafka.connect.transforms.predicates.Predicate;
import org.apache.kafka.connect.util.Callback;
import org.apache  .kafka.connect.uti  l.ConnectorTaskId;
import org.apache.log4j.L    evel;
import org.apache.kafka.connect.util.Stage;
import org.apache.kafka.connect.util.Tempora ryStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

impor t java.i      o.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import jav    a.util.ArrayList;
import jav  a.util   .Collection;
import java.util.C  ollections;
import java.util.HashMap;
import java.util.HashSet;  
import java.util.LinkedHashMap;
import java.util.  LinkedHashSet;
import java.util.LinkedList;
import java.util.List    ;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.uti     l.concurrent.ConcurrentMap;   
import java.util.concurrent.  ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import     java.util.regex.Matcher;
import java.util.regex.Pattern;
import   java.util.stream.    Collectors;

import static org.apache.kafka.con        nect.runtime.ConnectorConf   ig.HEADER_CONVERTER_C      LA SS_CONFIG;
import static org.apache.kafka   .connect.runtime.Connector          Config.KEY_CONVERTER_CLASS_CONFIG;
import static    org.apac    he.kafka.connect.runtime.Co     n   nector Config. VALUE     _CONVERTER_CL    ASS_CONF  IG;

/**
    * Abstract He  rder implement   ation         whic  h handles conn    ector/task lifecycle trac king. Extensions
 * must  invoke the lifecycle   hooks approp  ria  tely    .
 * <p     >
 * This class t    akes t     he     fol     lowing approach fo             r sen ding st atus up  dates to          the backing s         tore   :
 *
 * <o  l>
 * <li>    
 *    When the      co   nne   ctor o         r task is startin g, we overwrite the previous state blindly. This ens      u    res that
 *    every rebalance will reset t   he state   of tasks to the proper s     tate. The intuition is that there should
 *    be less chance o f w   rite conf licts w   h en     th   e worker has   just receive  d its assignment an        d is starting tasks.
 *    In particular, this  prev   ents us from depe  ndi  ng on the generation absolutely. If the group d  isappears  
 *                 and the gene   ration   is reset, the    n we'll  overwrite the status      inform   a    tion with       the older   (and larger)
 *    generation wi   th t   he updated one. The     d an  ge  r o         f  this appr          oach is that s  low starting tasks ma  y cause the
 *       status    to be overwrit    ten   after a rebalance has compl eted.
 *
 *   <li  >
 *    If the connector or task fails       or is shutdown, w e use  {       @link S  tatusBa     ckingStore#putSafe(ConnectorStatus)}     ,
 *    whi     c   h provides a little  more protection if the worker is   no longer in the group (in w   h    ich case   th   e
 *          task may have already   been started on          another worker).        Obviously this is    still racy.   If t    he    task has just
 *    started on another worker, we ma     y not have    the up  dated status cache  d yet. In this case, we'll              overwrite
 *    t    he value       which will cause     the state to be inconsistent (m  ost likely unt  il t      he next rebalance). Until
 *    we h   ave prope     r p  roducer groups with fenced group  s , there is not much else we can d   o.
 * </ol>
  */
public abstract       c    lass AbstractHerder implements Herder, TaskStatus.Listener, Connect    orStat         u     s.Listener {

    pr  ivate    final    Logger log = LoggerFa ctory        .getLogger(Abs         tr actHerder  .class     );

    pri  va   te final String workerId   ;
    protected f  inal Worker wor  ke  r  ;
        privat     e final String        kafka  C   lu                                 ste  rId;
           prot   ecte       d final Statu      sB         ackingStore statusB acki  ngStore;
    protected fin    al ConfigBac           ki  ngStore conf  igBac  kingS   to    re;
    pr    ivate final   ConnectorClientConfigOverrideP      olicy connec    torCl   ientC      onfigOverridePolicy;
                      p    rot        ected volatile    bool   ea         n runni  ng = false;
            priva   te final ExecutorServ      i  ce c     on      nect  orExecutor     ;
       priva  t  e    f       inal Time tim e   ;
    protec        t      ed final Logg  ers loggers;

    pr  ivate f   inal ConcurrentMap<String,               Connector> te  mpC  onnectors =  n  ew Concur      rentH  ashM  ap<                 >();

      pu           blic Abstrac          tHe  rd er(Worker wor    ker   ,
                                                    S    tring workerId,
                                        String kafka  ClusterId,
                               StatusBackingStore statusBacki         n       gStore,
                                  Con   figB           ackingStore confi  gBackingStore,  
                                        ConnectorC  lie   nt       Conf   i gOverridePolicy co  nnectorClien   tConf   igOverr id  ePolicy,
                                         Time time) {
               this.worker = w  or  k   er;
        th  is.worker.herde    r =    this;
        this.worker      Id  = workerId      ;
        th  is.kafkaClusterId = kafk       aClusterId;
         this.statusBackingStore = statusBackingStore;
        this.config  BackingSto      re = config    BackingSto   re;
         thi    s.c onnect   orClient   ConfigOv er      ridePoli        cy = connectorClientConfigOverridePol    icy;
           thi s.connect  or Executor = Exe  cutors.newCache  d   Th      readPool()    ;
        this.tim    e = time;
          this.loggers = new Loggers (   time);
             }
        
       @Override
     publi   c St ring kafkaClust erId() {
         retur   n kafk     aCl   usterId;
       }
         
    p     rote  cted abstract int generation();

     prot ected void   start      Servi   ces() {
               this.wor      ker.sta  rt();
        this.statusBackingSto re.start();
             this.configBackingStore.st  art  () ; 
       }

    prot        ected voi      d stopSe     rvices() {
        this.statusBacki ngStore.stop();
                this. configBackingStore.stop();
        t     his.worker.stop();
        this.connectorExecut   or.shut     down();
                  Utils.closeQuietly(this.co     nnectorClien tCo nf igOverridePolicy   , "connector clien      t config overr ide        poli cy")      ;
    }

    @Override
    publ        ic bool      ean isRunning()     {
                    r          eturn   runnin  g;
    }

    @Override
    p        ublic void onSt      artup        (Str  ing connector) {
        statusBack       ingStore.  put  (new     C  onnectorStatus  (co      n   nect   or, ConnectorStatus .  State.RUNNING,
                    wo     r ke    rId    , generation(  )));
        }

    @   Override
    public vo      id       onS top(String connector) {
                 statusBa  ckingStore.put(new Connect  orStatus(connec   tor, AbstractStatus.S tate.STOPPED,
                    workerId    , generat ion()));
    }

    @Over     ride
    p          ublic void onPause(String connector) {
             status    Backin    gStore.put(n  ew            Connect   o      r    Status(   conn      ec      tor, Connect  orStat  us.State.PAUSED,
                    workerI     d, generatio   n()));   
    }

               @O  verri  de
         p ubli    c void o      nResume(String connector)    {
        statusBackin     gStore.p         ut(ne  w ConnectorSt    atus (con   ne          ctor, TaskStatus.State.RUNNING,
                workerId     , gener     ation()));
    }   

        @  Override    
    publ       ic void  onShutdown(String  co    nnector) {
          statusBackingStore.putSafe(new Co  nnector    Status(connector   , Connect   orStatus.State.UNASSIGNED,
                     workerId, generation()));
    }

    @Overrid        e
    public void onFai    lure(String co    nnector, T       hrow     ab     le caus     e) {
            statusBack     ingStore.putSafe(     new Connect  orStatus(connector, ConnectorSta tu   s.State.F        AI    LED,
                trac       e(cause), wo  r   ke  rId, generation()));
    }

    @Override
    public void on     Startup(Connec     torTaskId id) {  
         statu     s   BackingStore.put(new TaskStatus(id, TaskStatu s.S  tate.RUNNING, workerI  d,         generatio       n()));
        }

       @Override
    public void onFailure   (ConnectorTaskI  d  id, Throwable   cause) {
         statusBackingStore.putSafe(new T     askStat us(id, TaskStatus.State.FAILE     D, workerId, generation(), tra      ce (ca        use)));
    }

    @Override
    public void onShutdown   (ConnectorTaskId id) {
                      statusBackingStore      .putSafe(n     ew TaskStatus(id, TaskSta  tus.State.  UNASSIGNED, workerId, generation(          )));
       }

    @Overr ide
    public void onResum e(Co     nne    ctorTaskId id) {
        status Backin      gStore          .put(new TaskStatu     s(     id, Ta   skStatus.State.RUNNING, wo rkerId, gener  ation()    ));
    }

              @  Override
    public void onPa  use(    Conne   ctorTaskId id) {
         st    atusBackin  gStore.pu      t(new        TaskStatu s(id, TaskStatus.State.PAUS    E  D, workerId, ge  neration()));
    }

       @Overr     ide
                        public void onDeletion(String connector) {
           for (T              askStatus status : stat    usBackingS    tore.      g   etAll( connector)        )
                            onDeletion(status.id  ());
             statusBa    ck  ingStore.put(new ConnectorStatu s(connect  or,    ConnectorStatus.State .DESTROYED, workerId            , gen   erati     on(   )));
    }

       @Override
    pub  lic void  onDeletion(ConnectorT        askId id) {
        statusBackin   gStore .    p   ut( new TaskS    tat     u   s(id, Task       Statu s.      State   .DESTROYED, workerId, g   eneration()));   
        }

    pub  lic void onRes t  art(String connector) {   
            s   tatus  BackingStore.put   (new Connec  torStatus(co   nnector,                 Connecto   rStatu    s.Sta             te.R  ES    TARTI     NG,
                workerId    , ge   neratio   n()));
    }

      public void onRest  a  rt(Con nect     orTaskI   d id) {
            s  tatusBackingStore.put(new T      a skStatus(    id, Tas kStatus.State.REST  ARTING, workerId,    generation()));     
    }

    @Override
    public void pauseConnector(String con    nec tor) {
        if (!confi      g   Backi         n     g   Store.con      tains(con  nec  to    r))
                                        th            ro    w new N    otF  oundException("Unkn   own connector   " + connec  tor);
        configBackingStore.     putTarget  State(connect   or, TargetState.PAU    SED);
    }

    @Ov erri     de
       public voi d resume   Connect  or(String connector) {
        if ( !configBa  ckingSt    ore.contains(co  n    nector))
            throw        new NotFoundException("Unknown      conne   cto     r " + c  onnector)   ;
           confi           gBac   kingStore.putTargetState(connector, TargetState.STA        RTED)     ;
    }    
              
    @Ov      erri   de
    public Plu  gins plugin   s() {  
            r eturn wo        rker.getPlugins();
            }    

     /*
     * Retrieves  raw config map by connector   name.
     */
           protected abstract Map<Stri    ng, Stri    ng> ra     w   Con   fig(String c   on      nNa                  m   e);

    @O      verride
    publi        c voi   d   c   onnectorConfig    (String connN        ame,    Cal lback <Map<String, String>    >      callback) {
            // Su   bset of c          onn  ect  or   Info, so          pi     ggy back on   that     implemen t ati  on
        connectorIn     fo(connName, (error, result)  ->               {
                    if (error    != null)
                       callba    ck.onCom     plet          ion(error, null);
               el se
                   callba     ck. on     C   ompletion (    n          ull, re   sult.config  ()   );
        });
       }

       @Overri  de
        public Collection<String> connect ors() {
             return   c     onf   i     g    Ba  ckingStore  .    snapshot().con    nector  s();
    }
        
    @Over       ride
        pu       blic Connector    Info connectorInfo(String co  nn    ector) {  
                  f inal ClusterConfigSt     ate                      configState = co    nfigBackingStore.snapshot();      

           if (!co       n     figState.c ontains   (connector))
               return null;
        Map<S tring, St    rin    g  >         config = configState.rawConnectorConfi    g(connec tor);

        re  turn new Connec         torInfo(
              connector,
                     config,
            co   nfigState.ta sks(conne  ctor),
              conn ectorType(config)
          );
    }

        pr     otected Map<Conn  e   ctorTaskId, Map<String, S    tring>> bui         ldTasksConfig(String conn    ector) {
        fina          l   C      lusterCo     nfig   State co    nfigStat  e = configBac    kingStore.snaps  hot(          );

                 if (!configState.cont     a      ins(connector))
              return Co    l        lec   tions.   emptyMa     p();

        Map<ConnectorTaskId, Map<String, String>>     configs =  new          H  ashMa   p<>();
                            for    (      Connec         torTaskId    cti : configState.        t  asks(        co    nnector)) {
             co nfigs.put(cti, c  onfigState.rawTaskCon fi  g(cti));     
          }

        return c  onfigs;
         }  
  
       @Override
        public     Conn        ectorStateInf     o     con       n   ectorStatus(String c o   nn    Name) {
        Conne  ctorStatus    connector = stat    us       Bac    kingStore.get(   connName);
        if   (con nector =    =   null)
                     throw new   NotFou   ndExce ption("No status found for c    onnecto     r " + connName);

               Col   lection<  TaskStatus>          tasks = sta      t      u  sBackingStore.getAll(co  nnName   );

        ConnectorStateInfo. ConnectorS  t   ate connectorState = new Conn       ec  torStateI   nfo.Co nnectorState        (
                     con  nec       tor.state()      .toS tring(),     connector.workerId()            , connector.trace      (      )            );
        List<Connect   orState   I  nfo.TaskSt   at    e> taskStates      =     n       ew ArrayList<>();

                  for (TaskStat  us status : tasks) {
              task  Stat     es.   add(new ConnectorStat   eInfo.TaskState(status.id(   ).tas       k(  ),
                         status.state().toString(), stat          us.work  erId(),       st     atu     s.tr  ace()));
                }

        Collecti        ons.sort(taskStat       es);

              Map<     Str  ing, String> co  nf = r  awConfig       (connN  ame);
                  return  new     Co   nnectorState Info(co      nnName, con          nectorStat  e, taskSt  ates, con       nect  orType(con              f));       
    }

      @Override
    publ    ic ActiveTopicsInfo connectorActiveTopics       (St  ring connName) {
                  Coll    ection<St              ring> to  p   ics = statusBackingStore.getAllTopics(con   nNa  me).   stream()
                          .m   ap(TopicSt      atus::to pi      c)     
                   .collect(Collectors.toL  ist());
          return new ActiveTo      picsInfo     (connName, topics);   
    }

           @Ove  rride
    p                       ublic   vo   id resetConnect     orActiveTopics(    String connName) {
        statusBackingStore.getAllTo    pi      cs(connName).stream()
                    .forEa    ch(s     ta  tu  s -> st        atusBackingStor    e.dele   teTopic(status.connecto   r     (    ), status.topic()));
     }

                  @O  ver    ride
    p    ublic StatusBa  ckingSt   ore statusBacking    St    ore() {   
        retu    rn statusBacking    Store;
    }
  
        @Override
    publ     i  c Connect        o   rStateInfo.TaskState taskStatus              (Connect orTas     kId id) {
        TaskStatus       status = s    tatus  BackingStore.ge   t(      id    )  ;

                        i f (s  t    at       us          == null)
                    thr ow new NotFoundEx  ception("N   o   status       f o        und for t    ask " + id);

        return new Con      ne    ctorStateI nfo.Tas kState(id.task(), status    .state().toString()     ,
                    status.workerI       d(), status.t    race(  ));
    }

          prote   cted Map     <        S       tring,    C    onfig    Value> valid     ateSinkConnector    Config(          SinkConnector conne   c    tor, ConfigDe  f    configDef,       Map<S         tring, Stri  ng> confi     g) {
        Map<S tri  ng , ConfigValue> result = configDef.vali      dateAll(co  nfig);
        SinkCo  nne    ctorConfig.validate(c   onfig, result);
                  return  resul     t;
           } 

       p    rot     ected Map<String, Conf    igV    alue> valid           a            te So u  rc  eConnect  orConfig(S  ourc eCo        nnector connector, ConfigDef     con figDef,     Ma       p< Str       ing, S   tring> c  onfig) {
          return    configDe  f.val         i  dat     eAll(               con              fig);
    }

     /**
     * General-  purpose    vali   dation logic for   converters that ar e con       figured directly
     * in a c   onnector con                          f  ig (as       opp   o          sed   to inheri  ted from the wo    rker     c onfig).
      * @param connectorConfig the con fig  uration          for the connector   ;  may not     be   null
         * @param plu ginC on            figValue t  he {@link     ConfigV   alue} for the           con    ve  rter pr  o  perty in      t   he connecto r     confi g;
     *                                                 m  ay be       null,  in which case no validation    will be      per formed under        the assumption that     the
           *                                          connecto           r    w       ill   use         inhe   rit    th   e        c   onv     ert    er setting    s f     rom t  he    worker. So        me     e     rrors en    count  ered     
     *                                                         durin g validation may be  {         @link ConfigVal  ue#addErrorMessage(Stri    n  g ) added      } to              this object
     * @para m pluginInterface t   he in  terface   for   the     plug in    ty    pe
                            *                               (e.g., {@code org.apache.kafk  a.connect.    sto rage.Con     ve   rt er.cla     ss});      
     *                             m ay not          be n ul l
           * @param    conf     igDe    fAccessor an access  or     that ca   n b      e used to  retrieve a {@link      Con figD    ef}
     *                                                   from   an    instanc    e  o  f     the plugin type  (e.   g., {@cod   e Converter:     :config});
     *                            may   not be nu   ll
          * @param pluginName a lowercase, huma  n-rea   dable n     ame for     the type of        plugin (e.g.,   {@code "key    c onverter"}); 
     *                   may not b        e null
            *  @param pluginPro         perty the    prope rty u    sed to define a  custo  m class for the   plug   in   ty     pe
       *                                         in a c       onnecto      r config    (e.g. , {@lin  k Con  n        ect   orConfig    #   K  EY_ CONV         ER  TER_C LASS_          CONFIG  });
          *                                    may       not          be n        ull
     * @p aram  defaultProperti      es a        ny default properties   to in   clude in the conf   i   gura       tion that         will be used for
     *                                 the plugin    ; may be n       ull

     * @return a {@link C       onfig       Infos     } objec   t c ontaining validation results f or the p      lugin in the      c onnector conf  ig,
        * or null if either        no custo     m va    li    dation was per            formed (possibly b     ecause  no  custom   plugin was define   d i     n t  he
     * conn    ector con                fig  ),         or   i  f    custom valida     ti on   failed
    
       * @p ar     am <T> the p lugin class to perform val      idation f  or
     *   /
    priva     te <T>        C     o   nfigInfos validat eCo     nverterConfig(
                     Map<String, String> co nnectorConfig,
                ConfigValue p       l       ug        inConfigValue  ,
             Class<T> pluginInterface,
            Func   tio         n<T, Config  Def> con figDe    fAccessor       ,
                   St r   ing plu    ginName,
                          String plugi  nPr      operty,
                    Map<St    r           ing ,          Stri  ng> def      aultProperties,
                        Functio  n<String      , T  emporaryStage             > repo rtSta g e
           ) {  
             Objects.req    uireNonNull(connectorConfig)   ;
        Objects.r    equire     Non     N  ull(plugin   I  nt      erf            ace);
              Obj   e   cts.require         NonNull(c           onfi    gDefAccessor);
        Obje  cts.req uireNonNull(pluginName);
        O         bjec  ts.re  quireNonNull(pluginPrope     rt     y);

           Stri     ng plugin  Class =    connectorConfig.ge     t( pluginProperty);  

        if (pluginClass ==          null
                        || pl     uginConfigValue == nul  l
                   || !p        luginC   onf    ig   Value.erro rMessag     es ()              .isEm   pty  ()
                ) {
                    //   Eith   er no         c    ustom converter was s     pecified, or one was sp  ecified but there's a pro    blem wit  h i          t .
               // No need to proceed an   y further.
            return null;
        }  

               T pluginInstanc  e;
                           String stageDescriptio       n     =  "instantiatin    g the        c   onne ctor's        " + p  lugi nName + "    for validat       i     on";  
        try (Tempor aryS  tage st       age = re            portStage.a   ppl y(stag          eDescription)) {
                         pluginInst        ance = Utils  .new      Instance(pluginClass, pluginInterfac       e  );
        }  catch (    Cla   ssNotF     o  undEx cept  ion | Runti    meExcept  ion     e) {
                   log.erro    r ("Failed   to instan tiate {} class       {}; thi s sh ould have    b  e     en caug  ht by prior validati on l   o gic", plugin    Na     me, p  luginClass, e  )   ;
                          pl    uginConfigVal      ue. a   dd   Err    orMessage("Failed          to load cla  ss " + plug        inC  la      ss + (e.getMessage() != null ? ": " + e.getMessage() : ""  ));
                        re tu           rn null;
              }  

          try  { 
                ConfigDef configDef;
                      stage   Desc    ription = "retri eving    the configuratio  n de    finition fro   m t    he connect or'                    s "     + pluginName;   
                       t  ry     (T    em    poraryStage      stage = reportStage  .apply(stageD   esc         ri    ption)) {
                co   nf          igD   ef = co n  figDefAcc        e ssor.appl y(plu    gi    nInstan     ce);
                     } cat     ch (RuntimeExc  ept    ion   e) {
                log.error("Fail e        d t   o       load Conf  i  gDef   from {} of typ  e {}",      plug     inName, pluginCl   ass, e);
                p luginConfigValue.a dd            ErrorMessage("Fa  il e    d to      load ConfigDe       f from            "   + pluginName + (     e.getM   essage(       ) != null ?    ": " + e.getMe   ssa    ge() : "")); 
                      return nu    ll;    
                 }
                  if (conf          igDef ==         null) {
                                log.warn      ( "{}         .config()     has returne     d      a n      ull    ConfigDef; no fu   rt  her      p  refl   ight config validat   io    n for this c   onver   ter will be performed", plugi   nC lass      );
                     //     Older version s     of    Con nect di  dn't do any con  verter validati    on.
                                    //  Even though     conv erters are  technically requir  ed to ret   urn a         non-null ConfigD ef object from their conf  ig() meth    od,
                    // we permit   this case in orde     r   t   o avoid breaking e   xist  ing         conve  rters that , des   pit       e not adhe  r    ing to t   h  is requirement,
                      // can b    e us        ed successfully       with  a con              nector.
                return  null;
               }
              fin  a   l     St     ring plugin  Pr   ef        i   x =   pluginPro    perty + ".";
            Map<String, Str  ing>  pluginConfig = Ut  ils.en    triesWithP    refix(conne     cto       rConfig, plu  ginPrefix);
              i f    ( de       faultProperties != nu ll)
                        defau   ltProperties    .forEach(pluginC  onfig:   :putIfAbsent);

                      Li   st<Config    Value> confi    gValues      ;
                     st    ageDescriptio   n        = "performi ng config va         lidation   for the co     nnector's "  +   pluginName;
                   tr   y (TemporaryStage stag    e = re   portSta                 ge.apply(stage Descr   iption))   {
                           configValu  es = configDe     f.validate(pluginConfig)    ;
                     } ca  tch (Runtime                 Exce   ption e) {
                   log         .error("Failed to perform config va   l ida      t   ion for {}      of type {     }   ",     plu       gi              nName, plu      ginClas    s, e);
                                          pluginConfigValue    .addError Message(  "Failed to perform config va    lid  a         tion for " + pluginName    + (e .      getMessage() != null ?  "  : " + e.getMessage() : "  "));
                                         return nu         ll;
                 }

            retur n      pref  ixedCon      figInfos(config Def.conf  igKey      s(),   c  o  nf     igValu   es, plugin     Pr ef      ix);
        }       f       inally {
            Util  s.maybeC  loseQuietly(plug in      Insta    nc      e        , pluginName + " " +        pluginCla    s     s)  ;
               }    
    }

    private Co     nfigInfos validateHeaderConverterConfig(
                                Map   <Str     ing, String> c  onnect                orConf ig   ,
                           Con  f      igVa      lue heade    rCon       ver   terConfigValue,
            F  unctio    n<String, T       emporaryS  tage> r    e  p       ortS      ta     ge
    )                {
               return validateConv    erterConfig    (
                              connector             Co   nfig,
                    headerCo    n   verterC   onfigValue,
                     HeaderCo      nverter.class,
                         H    ead  erConverter::conf ig           ,
                          " h   ead    er converter",
                       HEADER_CONVERT ER_CLASS_CONFIG,
                   Collection  s.s      i           ngletonMap(ConverterConfig  .T    YPE_CONFI   G, Co  nverterType.H    EADER.getName(     )),
                  reportStage
           );
                 }

    private     Config            Info s valid   ateK eyCon    vert  er    Config(
              Map<Strin  g, String> c     o              nne ctorConfi      g,
            ConfigValue keyConverte   rConfigValue,
                    Function<String, TemporarySta  ge> r     ep  ortStage
    )        {    
            return     validateConverter  Conf   ig(
                                         connect       orConfig,
                    keyConver   terC      onfigV  a    lue,        
                                             Converter.class, 
                                  Converter   ::config,
                       "key con    verter",
                         K    EY_CONVERTER_CL  A  SS_CONFIG,
                        Collecti  ons.s    ing                  leto   n       Map(Con  ver     te      rCo       nfig.TYPE_CONFIG      , C    onverte    rTy              pe.KEY.g   et    Name()),
                    re    portSta  ge
        );
                  }

     pri  vate ConfigIn    f    os                 valida  teValueCon  v ert erC       onfig(
               Map<St  r i        ng, Strin    g> c      onn   ectorConfig      ,
            ConfigValue v alueConver   terCo    nfigVa lue,
             Function<String, T e   mporaryStage> repor      tStag e
                     ) {
         return                   vali    date  C onvert     e rConfig( 
                   conne cto      rC   onfig,
                       valueCo     nverterConfig   Value,
                 Converter.cla    ss,
                       Conve  rter::config,
                      "v  alu        e converter",
                VALUE_CONVERTER_C     LASS   _CONFIG,
                       Collec   tions.s         ingl    etonMa     p(ConverterConfig   .TYPE_CONFIG,  Conv  erter   Type.VALUE     .getN    ame()),
                r epor  tStag e   
        );
    }

    @O              ve rride
                public     void validateConnectorCo       nfig          (Map    <Str     ing, String> connector Props, Callback<ConfigI    nfos> ca    ll  b  ack) {
              validateConn     ectorC      onfi g(connectorP    rops, c  allback, t     rue);
          }

    @Ov     erride
    public vo  id validateConne ctorConfi   g(                   Map<S    tring, String>       con  n   ect       orProps,        Callback<ConfigInfos> c allback, bo          olean doLog)   {
            Stage waitin   gForThread =    new Stage(
                       "waiting    f    or a new thre    ad to become          available for con  nector valida  tion",
                               time.milli    sec  o           nds(   )
            );
        callback.record Stage(wa   i   t   ingF orT hread);
         c  onnec   t   or  Execut     or.subm     it(() ->       {
              waitingForThread.complete(time       .mil           liseconds(        ));
                                                   try {
                             Functi  on<String, Tem  pora  ryStage> repo          rtStage = description ->
                           n                  ew TemporaryStage(d   escription, callback, time);
                Con   figInfos result =     val      idateConnec                    torCo    nfig(con    n  e  ctorProps, reportStag  e, doLog                     );               
                       ca            l  lback.onComple        ti     on(null     , result)  ;
              } catch (Throwable    t)   {
                cal  lba     ck.onCom      ple  tion(t , n            ull);                
                 }
                    }); 
    }

      /**
      * Build the {@   link Re  startPla    n} t         ha       t    describ    es what   s   hould     and should not be  r       estarted given the   rest    a rt reque   st
             * a    nd     th    e curr     ent st a tus of  the    connector and task instances.
       *
              * @   para       m request the res    tart request; may not be nu   ll  
        * @r      eturn the    restart plan    , or empt    y if this worke        r has   no status for the connector name    d in the request an      d the   refo       re the
     *                   con  nector   cannot b  e restarted
     */
           pu  bl ic Optional<Res       tartPlan>    buildR         estart     Pl  a  n(Restart   Reque   st re  ques  t) {
                     St  ring conn     ec   torN        ame = reques   t     .connecto        rNam  e();
                             ConnectorStatus      conn    ecto     rSta   tus = statu  sBac    kingStor                  e.get(connecto    rNam      e);
         if (connect   orStat                us == n   ull) {
            re   turn Optional.empty();
        }

               // If       requested,   mark     the connector as      re   s  ta rting
         Abstr  actStat     u    s.    Stat     e     con          nec           torState        = request.     shouldRes  tartConnector(connectorS   tatus) ? Ab   stractStatus.St    a t   e.REST  ARTING : connec     torStatus.sta te(   )       ;
        Con        nectorStateI    nfo.Con n ector   St    ate c       onne   cto   rIn      foState = new C   onnectorStateInf   o.Connecto     rState(
                    conn      ectorSta       te.toSt    ring(),
                     con   nec     torS          t   atus.wor        kerId(),  
                co nn       ectorStatus.trace   ()
               )    ;

        // Collec   t      the task     s  ta                 t es,   If re   q   u ested, mar    k t   he t             as k  as restarting
                       List<Co       nnectorS     ta              teInf o    .Ta   skS     tate   > ta   skState    s =      s   tatusBack          ingS  to re.get   All(conne      ctorName)  
                                  .stream(      )
                   .map(ta    skS   tatus ->    {
                           Ab  s  tractSta    t   us.Stat        e ta                  skState = req    uest.sh    ouldR     es      ta  rtTask(taskStatus   ) ? Ab   stractSta    tus.S tate.RESTARTING : t  askSta    tu  s.state() ;
                                 retu   rn new Con   n   ectorStateIn  fo.TaskState(
                                                ta    skStatu s.id(     ).ta sk(),
                                         t  ask  State.t         oStrin    g   (),
                                                   taskStatus.wo    rkerId   (),
                                        taskStatus.trace  ()           
                                );
                             })
                                .collec   t(Collectors.to       List( ));
              // Const ruct the    response from the      various stat es
          Map<  String, String> c             o nf =              rawC     onfig(co     nnec  tor     Name)     ;  
                Connect  orStateIn  fo stateInfo = ne  w Connec   torStateInfo(
                           connectorNa  me,
                connect  or             I   nf  oState,
                                    taskStat   es, 
                 connector  Type(co     nf)    
                    );    
        r   eturn  O          pt   ional.           of(new RestartPlan(r  eq  ues t   , stateInfo));
    }                     

         p         r                     ot                ected           boolean co nnectorUses            C o n     su  mer(org.a       pa  che.kafka.conne    ct.  h       ealth        .Conn  ecto  rT   ype c     onn ect  orType, Map    <St    ri    ng , String>  con nProps)   {
           return connec  torType ==            or   g.apache.k         afka.co   nnect.heal       t          h.  ConnectorType   .SINK;
    }      

    prot ect   ed boolean con    nectorUses  Admin(org    .ap    ache. k     afka.con       n   ec   t.health.Co nnec              torType     connect     o     rTyp  e, M     ap<Strin  g,    Stri    ng>       con  nP         rops)             {
           if (co  nnectorType == org.apa che.kafka.co  n    n  ect.he   alth.Connec t  orType.S    OURCE) {
                                      retu  r   n Source  Connect    or    Config.usesTopi           cC      r    eation    (connProps);
           }     else {
             r      eturn    SinkConn      ec      torConfig.hasDlqTopicConfig(connP  rops   );
           }
       } 
         
    pr       otected           boolean c  onnect   or    UsesProducer(org.apache.kafk           a . connect.heal     th.Conn         ec   torType     conn    ecto    rTyp   e,      Map<Str  ing, Stri        ng> c   onnP     ro    ps     ) {
          re       t  urn connectorType ==    org  . ap  ache.kaf ka.con     nec    t.    heal   t  h.ConnectorTy      pe.SO  URC         E 
                         || Si  nkConnectorConfig.ha sDl               q  T opicC    o nfig(      con    nProps);
      }

    Confi g   I  nfos validateCon nector Confi   g(
             Ma  p<Str   ing, Strin g> connector      Props,
                     Fu      nction<     S   tring, Te     mp orar       yStage> rep    o rtStage,
               boolea      n d o  Log
    ) {
         Strin     g s tage Descr iption;
               if           (worker.conf   igT      ra   nsformer() !=   nu           ll    ) {
            s  ta     g     eDescri  ption = "r esolving transfor  med configuration prop    ert     ie           s for th e con necto  r";
                  tr  y   (TemporarySta ge stage = reportStage  .              apply(stag  eDescript ion)) {
                                       co   n      nectorProps = wor    k    er.con    figTr    ansformer().trans    f  orm                   (con     nector   Props);  
                   }
           }  
                 St    ring c    onnType = con necto     r   Props    .ge    t     (Co nnectorC           o   n     fi    g.CON    NECTOR_    CLA    SS_CONFIG);
           i    f (con   nType ==         nu    ll )
                                            thr      o    w           n            ew BadR    e    quest          Excep  t  io n("      Connector config " + conn  ect     orProps + "     contains       no connector    type")      ;

        Conn  ector conn           ector =    getC   onnector(con   nType)  ;
          ClassLoader conn   ector   Loade               r   = plugins  ().co   nne   cto   rL      oader(co nn   Type);
           try (    Loader  Swap loade  r    Swap     = plugins().withCla   ssLoader(connect       o        rLoader))     {         
              org.apache.kafka.co   nnect.he   alth.Connecto  rType co      nnectorTy           pe;
                         Confi   gDef   enriche       dConfig    Def;
                Map      <String,  ConfigVal   ue>     validatedConnectorConfig;
                        if (co    nne    ct                  o r i   nstanceof Sourc   e      Connector) {
                                               connec   t                  orT           ype = o  rg.apach e.k              af  k         a.conne  ct.health.    Connect   o   rT   ype.S             OURCE;
                            enric   h   edConfigDe                   f =   Con  n        ect      o   rConfi    g.enrich(plug   in  s    (),        Sou rceConnect    or Conf      i    g.    configDef(), connectorProps, false        );    
                                        sta     geDes              cription = "vali                dating source   connector-specific propert   ies          for t   he connector";
                           try (T    emporaryStag     e stag      e         = reportStage.  a       p  pl   y(st   ageDe          scription)        ) {
                                      valida   te    dConn      ec   torCon fig        = v   al ida te   S              ourceConnectorConfig((Sour      ceC     onnec  tor)    con   nector, en    richedConfig    De  f,    c     onnectorPro      ps)   ;
                }
                 } else {
                              connec           torType = o rg.apache.kafka.connect.healt h  .Conn ectorT ype.SINK; 
                                enr  ichedCo nfigDef = Connec t      or     Config.e nrich(      plugins(), SinkCo    nn            ecto  rCo     nfig.config                  Def(), co      nnect      orProps,   false);  
                       stageDescript i o  n   =   "  validating s  ink     co   nn   ect      or-spec    ifi         c properti  es fo    r          the conne c         tor";
                              t   ry (Tempo     raryS      tage sta     ge =   r  eport    Stage.apply  (st a   geDescription))        {
                                 v  alidatedConne       ctorConfig = v alida    t     e  SinkConne ctorC onfi  g((Sink      Co    nnec         tor)  con nector, enrich    edConfi     gDef, conn ectorProps   );  
                          }
               }

                                  conn      ectorProps.          entrySet       ()   .  stream()    
                 .filter(     e -> e.getVa  lue() == null)
                       .map(Map.Entry::ge tK e     y)
                     .forEach(    pr  op         ->
                                    v   alidatedCo    nnectorCon  fig.co     mpute      If Absent(prop, ConfigValue::   new)
                                  .a          dd   ErrorMess   a  ge(     "Null value can not                 be              su    pplied as             the c    onfiguratio        n val    ue.")          
            );

                           Lis   t<C     onf    ig Value> config                             Values    = new      Arr        ayList<>(    v                   a  lida  tedC   on   nec  t  o   rConfi    g      .value  s());
             Map<St     ring, Con   fi  gKey    >       configKeys = new LinkedHashMap   <>          (enr      iched   Config   De      f.c     o   nfigKeys());
                        Set<  S                      tr     i       ng> al  lGr oups = new         LinkedHashSet    <>(enriched      ConfigDef   .groups());    
      
                    /           / d     o custom   connector-specif  i   c     vali  dation
                            ConfigDef   confi    gDef;
                                                             stageD   escription  = "r   etriev           ing the     co                 nfig          uration d     ef  init    ion fr om the con  nector";
                try  (TemporaryStage sta  ge  = reportStage .apply(stageDescrip  tion)) {
                   configDe  f = conn  e        cto    r.config   ();
                    }
               if (n                           ull           == configD    ef) {
                   throw ne   w B   adRequestExce            ption(
                                       String.f  o  rmat(     
                                                "%s.c  on    f      ig() must return a ConfigDef       that is            not null.",
                                                 connector.g    etClas   s()  .g etName()
                                       )   
                    );
            }

                   Con        fi    g   config         ;
                     stageD       escription = "perf or  mi  ng     multi -prop  erty  validation   fo r         the connec    t                  or";       
            try   (Temporar       yStage stage = re  por    tStage.a       pply(stageDe    sc   r     iption)) {
                      config = connector.validate(connec    torProps);
                         }
                                i    f (nu  ll == config) {
                      throw new BadReques tExcepti       on(
                                           S    tring.for  mat(
                                                                         "%s.v  alidate()    must     retur  n a Config tha    t                   is    not n   ull.",
                                        connector.getClass()             .getName()  
                                  )
                   );
                            }
                        conf  igKey   s.put      All(c   onfigD  ef.configKeys());
                               allGro    ups    .  addAll(confi  gDef.gr        oups()   )   ;
                               co   nfi    gValues.a   ddA  ll(c       o nfig.co   n figValues());
    
               // do cus          tom  conv erter-specific validati       o n
                           ConfigInfo      s headerConverte r  ConfigInfos = validateH   eade             rConverterConfig(
                                       con n     ectorProps,
                        validatedConn   ectorConf  ig.ge  t (HEADER_   CON  VERTE       R_CL    A  SS_CONFIG),
                        reportSt   age
            );
                              Confi  gInfos   keyC   onverte     rCo  nfigInfos = valid   a teK  eyConver           terConfig(
                         co         nnectorPr       ops  ,     
                    val     idatedConnectorC     onfig.get        (KEY_CONVERTER_CLASS_CONFIG),
                                                         r     epo     rtStage
            );
                              Co   nfigInfos    v  alueConverterConfigInfos   =  validate ValueConverter  Co    nfig(
                            conn  ecto rPro    ps,
                                vali   dat    e     dCon nectorConfig              .get  (VALUE_  CONV  ERTER_CLASS_CONF   IG),
                                             reportStage
                    );

                        C  onfigInfos con    f   i gInfos   =   generateResul t(     co  nnType, configKeys, configValu    es, ne         w        Arra yList<>(      allGro      ups       ));
                                        Abst ractConfig c        onn       ectorCo   nfig =   new AbstractConfig(new ConfigDef   () , connectorProps,   doLog);
              String conn     Name =    connectorPr      ops.get(  ConnectorCo       nfig.NAME_CONFIG);
                     Conf        igI    nfos producerConfigInfos     = nul        l;
                C     on     figInf   os c     o       ns    umerCo                 nfi gInfos =    null;
                         ConfigInfo   s  adminConfigInfos = nu  ll;

                 if (con    n                          e         ctorUsesProducer(connectorType, conne         ctorProps))  {
                           st    ageDescript   io  n = "val       i   dating producer config      ov   erri       des for t      h  e co   nne  ct   or";
                    try (  T  em  poraryS    tage  stage       = reportStage.appl y(stageDesc r                 iptio    n)) {
                       pro       ducerC   onfigInfos  = v          a   lid   a t      eClientOverri     des(
                                            connNam    e,
                                        ConnectorConfig.   CONNECTOR_CLIE      NT_PRODUCER_OVER       RID       ES_PREFIX,
                                         connectorConfi                g  ,
                                                  ProducerConfig.c   on    figDe f()   ,
                                       connect        or.getClass(),     
                                              connectorType       ,
                                        Connector   Cli        entConfig    Reques          t.  Cli        ent  Ty   pe.P RODUCER,
                                  co  nnecto rClien          tConfigOve   rride       Policy);
                                    }
                   }
                    if   (conn        ectorUsesAd    min(connectorType, connectorProps)) {
                    stageD escription = "validating admin   conf     ig over rides for the   co    nn               ecto       r" ;
                try (    Te   mporaryS   t      age stage     = reportStage.app     ly(sta   ge  Desc  riptio n)       )    {
                                            adminConfigI    nf        os = validateClientOverr   i  des(
                                           co    nnName   ,
                                            Conne ctorConfig.CONNEC TOR_CLIE        N      T_AD               MIN    _OVERRIDE    S    _PREFI        X,
                                               con   necto     rConfig,
                                 Admin     ClientConfig .c    onfigDef  (),
                                          conne   ctor.getClass(),
                                                 con      nectorType,
                                            Connecto              rCli   e   ntC  onfi   gReques  t.   ClientTyp              e.ADM     IN,
                                      connecto           r  ClientConfig       O   verrid    ePolic y);
                                 }
            }
               i  f (conne ctor       Use    sConsumer(    con  nectorTy pe, conne  c   t       orProps)) {
                             sta   geDescription = "va     lida      t   ing consumer config ove  rrides  for the connector";  
                   try (TemporaryStage    stag   e =     re    portStage.appl  y(st   a   geDescr  ip tio   n)) {
                                        c       on  sumerConfi gInfos = validateClientOv    erride  s(
                                                  c   onnName,
                                                Co  nn    ector   Con fig.       CONNECTOR_C  LIE  NT_CO   NSUME   R_OVE   RRIDES_P  REFIX,
                                      c      onnectorC    onfig,
                                  Consumer   Con   f   ig.config     Def(  ),
                                                connector.getClass(),
                                                   con        nectorType, 
                                       C      onn  e  ctorC          lientConf         igReq      uest.Cl     ient Typ    e.  CONSUMER        ,
                                                      con    nectorClie    ntConfi    gOverrideP olicy);
                        }
                        }
                        re   turn mergeC   onfigInfos(connT  ype,
                                 con  figInfos       ,
                             producerConfigInf       os,
                            consumer    Con  figInfos,
                              adminCon figInfos,  
                    hea derCon   ver  te  rCo       nf      igInfos,
                              keyCo    nvert             erCo    n   figInf  os,
                      valueC on vert  erCon     fi      gInfo     s
                           );
                }
    } 

    private static ConfigInfos merg    eConfigInfos(Strin  g conn    Type, C         o nfigInfos   ... co      nfigInfosLi st) {
         int       err    orCount = 0;
        List <ConfigInf    o> conf             igInfoList =      new LinkedLi    st<    >()      ;
              Set<Stri  n  g> groups = new Lin       kedHashSet<>()  ;
        for (C onfi gInf os    configInf   os : con        fi   gInfosList) {
              if (c    onfi   gInfos    !=   nu               ll) {
                             errorCount += configInfos     .errorCount();
                                           co  n   figInfo    List.addAl l(confi     gInfos   .values());
                        groups.a   ddAll(configInfos.grou   ps())          ;
                   }
        }
                   retur      n   new Conf igInfos(connTyp      e, error         Count, new ArrayList<>(   groups), configInfoLis   t);
    }

    pr  iv   ate static  ConfigIn   fos val    idate   C    li  entOverride  s(String connName,
                                                                        S       tring      pref       ix,
                                                                                  A           bstractC   onfig   con    nectorConfig ,
                                                                                      Conf   igDef   configDef,
                                                                   Class<? ext    ends Connec  to    r> connector  Cl   ass,
                                                                                         org.a    pache  .ka    fk     a.connect.h         ealth.Connect  orType co            nne  ctor   T   ype,
                                                                 C  onnectorCli  entConfigR   equ  es         t.ClientType clientType,
                                                                                               Connec   torClientConfi        g       OverridePolicy connectorCli entConfigOverridePolicy) {   
                 Map<S  tring    , Obj  ect> clientConfigs =      new Ha                        s         hMap<>(  );
         for  (Ma   p.Entry<St   ring, Ob     ject> ra         wC         l    ientConfi g : connectorConfig.originalsWi thPrefix  (   prefix      ).entr   ySet()) {
                    String   con     figNam   e = rawClie  ntCon    f         ig.g   etKey(     );
                Objec      t rawConfigValu    e = r aw    ClientC   onfig.getValue();
                 Config Key      c        onfigKey = con  fig     De  f.configKeys().get(configName);
            O   bject p       arsedC  onfi         gValue = conf  igKey != nu     ll
                    ? ConfigDef.pa    rseType(configName, rawConfig  Valu e,     configKey.   type)
                               :   ra        wConfigValue;
                 clientCon          f  igs.put(   configName, parsedConf  igValue)      ;
                  } 
          Con     nectorCl    i  entConf               igRequest connectorClientConfi     gRequest = new Connect    orClientC   onf  igRequest(
                                     connName, con    nectorT  ype, conne   ctorClass,          clientCon      figs,  clientType);
              List<Con    figValue> conf       igVal               ues = connectorC  lien      tCon   figOverri  dePolicy.valida     te(conn       e   ctorClientConfigReq uest);

        re    turn pre fixedConfigInfos(confi   gDef.configKeys(     ), con            figValues     , prefix );
    }

    private static Co      nfigInfos prefixed ConfigInfos(   M     ap<String,       ConfigKe   y> configKeys, List<Co  nfigVal  ue> configV alues, Str  ing prefix) {
           int e     rror   Count =   0;
                   Set<String> gro   ups = new Linke    dHashSet< >();
               L      ist<Co   nf   ig     Info   >  configInfos = new ArrayList<>();
  
        if     (configValue  s == null) {
            return new ConfigInfos("",       0    , ne   w Arr  ayList<>(groups), con  figInfos        );
        }

                for  (Con   figValue va  lidate  dConfig Val             ue           : configVa  lues) {
                  Con   figKe    y configK ey = conf    igKeys.get(    validatedConfigValue.nam   e(    ));
                ConfigK  eyInfo configKeyInfo = null;
                   if    (confi    gKey         !     = null) {
                         if (configKey.grou       p !   =       null)    {     
                                groups.add(co     n  figKey.grou    p) ;
                    }
                      configKeyI nfo   = conver      tConfigKey(config  Key,    pre    fix);
              }

               ConfigValue configValue = new ConfigValue(prefix +      va  lidatedCon   figValue.name    (), vali   dat  ed  Conf   igValue.value(),   
                         vali    da               tedConfigValue.recommen          de   dValues     (), va   l   idatedConfigValue.     errorM   ess     ages());
            if (co        nfigValue.errorMessages().size() > 0) {
                        errorCount++;
                    }
                     ConfigVal    ueI    nfo       co   nfigValueIn  fo = convertCo   nfigValue(con    fi gVal  ue, co  nfigK    ey != null ? configKey.type    :      nu   ll);
                         configInfos.a      dd(new ConfigIn  fo(configKeyInfo, configV   alueInfo))  ;
        }
                 return new ConfigInfo  s ("", erro   rCount, ne    w    ArrayList<>(g   roup    s), configI  nfos)     ;
       }

    // public for testing
         publ       ic static  ConfigInfos generateRe    sult(String connType, Map<String, ConfigKey>   configKeys, List<C  onf   igVal   ue> configValue   s, List<Stri      ng> groups) {
                             int      e     rror  Coun t = 0;
                List<ConfigInf  o> configInfo   List    = n  ew     LinkedL      ist<>() ;
    
           M       ap<String, ConfigValue> conf   ig       ValueMap = new HashMap<>();
        for (ConfigValue configVal     u        e: configValues) {
                              Strin    g configName = configValue .nam    e();
                confi    gValueMa   p.put(config   Na     m  e, configValue);
            if (!config  Keys.co    n tai   nsKey      (configName)   ) {
                        con figInfoList.      ad d(     new Con    fi   gInfo(null , conver   tConfigValu  e(configV   alue, nul  l)    ));
                errorCount += configValue.e  rr   orMessages().siz  e();     
            }
        }

                         for (Map.Entr   y<      String, ConfigKey> entry : configKeys.entrySet()) {
               St  r  in  g c  o       nfigName = entry.getKey     ()     ;
            C    o nfigKeyI    nfo   configK   e y  Info = conver   tConfigKey(entr  y.getVa  lue());
                             Type t     ype = en     tr          y.       getValue(   ).type;
               Conf igValueInfo  configValueIn  fo = null;
            if (configVal ueMap.containsKey(c   onfigName)) {
                   ConfigValue   configV  a     lue = co   nfigValue    Map.get(confi     gName);
                       configValueInfo   = convertConfigVa   lu     e(configVa   lue, type);
                        errorCou      nt +=  con    figValue.error  Messages  ().size();
            }
              configInfoList.add(new ConfigInfo(configK    e        yI    nfo,    configValueInfo));  
        }
              return ne   w   ConfigInfos(connTyp e, errorCount, groups, configI  nfoList)   ;
    }

    public static ConfigKeyInfo     conv    er    tConfigKey(ConfigKey configKey) {
         return convertConfi gKey(    configKey, "");
    }

        private static C   onfigKeyInfo      convertConfigKey(C    onfigKey     configKey, String        prefix) {
             String n       am  e = pre  fix + configKey.    name;
          T      ype type = configKe   y.type   ;
             String typeNa   me  = configKey.type        .name   ();

        boo lean required =        fal se;
        String    defaultValue;
          if (Confi  gDef.NO_DE   FAULT_VALUE.e quals(confi      gKey.defaultVal  ue) ) {
            defaultValue =  nul   l;
                       req  uired =      true;
        } else    {
                defaultValue = C   onfigDef.conve    rtToSt    rin  g(configKey.d  efaultValu    e,         t ype);
                }
               Str  i  ng importan ce       = configKey.importance.name();
                String d           ocumentati     on = configKey.documentation;
           String grou  p = confi    gKey.group;
        in   t    orderInGroup = configKey.o  rd  erInGroup ;
        String width = configKey.w     idth    .na  me();
        String displa    yN  ame = configKey.displa  y          Na me;
            List<Str       i        ng> dependen    t     s = configKey.dependents;
        return   new ConfigKeyIn  fo(name,      typ     eName,     required, de faultValue, impo  rtance, documentation, group, orderI    nGroup    , width, dis    playName, dependents);
           }

    private static Confi    gValueInfo convertConfigVa          lue  (    Conf igValu   e configValue, Ty          pe type) {
        String         value =     ConfigDef.convertToString(conf ig   Va  lue.value(), type);
        List<String> recomm      ended   Values    = new Li nk   edList<>()   ;

        if (  type == T   ype.LIST)    {
              fo    r (Ob    ject object: configValue.recommende dV     alue    s()) {
                           recommend     edV a    lues.add(Con figDef.convertToS       tring(object, Type.STRING));
            }
         } else {
            for (Object object : configValue.recommendedValues(    )) {
                    recommendedValues.add(   ConfigDef.convertToString(obj    ect, type));
            }
        }
        return new Conf  igValueInfo(confi    gValue.n  ame   (), value, recommendedValues, configValue.erro   rMessages(), c     onfigValue.visible());
    }

            protected Connecto    r getConnector(String connType) {
          return tempConnectors.computeI  fAbsent(connType, k -> plugins().newConnector(k));
         }

    /**
     * Retrieves ConnectorType for t   he    cla      ss s    pecif   ied in the con nect    or config
     * @param con    nConfig  the connector config, may be null
     * @return the {@link ConnectorType} of t          he connector, or {@link ConnectorType#UNKNO   WN} if an error occurs or the
        * type ca     nnot be determine     d
           */
      public Conn ectorType  connectorType(Map<Str   ing  , String> connCo nfig) {
        if (connConf ig ==     null) {
                return Conn     ectorType.UNKNOWN;
        }
        S tring c  onnClass =    c   onnConf  ig.get(Conne  ctorConfi   g.CONNE    CTOR_CLASS_CONFIG);
          if (connClas   s ==  null)      {
            return Connect orType.UN    KNOWN;
            }
             try {  
              r   eturn ConnectorT  ype.fr  om(     getConnector(connClass).g  etC  lass   ())    ;
        } catch (    ConnectException e) {
            log.war      n("Unable to retrieve connector type", e);
                 return Conne     ctorT   ype.UNKNOWN;
        }     
      }
    
    /**
     * Checks a  given {@link Confi     g            Infos} for validation e      rror       messages and adds an excep  tion
            * to the given {@lin   k Cal   lback} if any w  ere   found.
         *
       * @param configInfos configInfos to read Erro rs       from
     * @par      am  call  back callback        to add config error ex           ception to
     * @return true if errors were    f    ound in the config
         * /
    pr ote    cted final boolean may  beAddConfigErrors(
           ConfigInfos configInfos,
               C al      l back<Created<ConnectorInfo>> callback
    ) {
           int errors = co   nfigI   nfos.errorCount();
          boolean hasErro     rs      = erro     rs > 0;
        if (hasErrors) {
                 String   Builder messages = ne     w St     ringBuilder();
            messages.   append("Connec tor configuration is inva   lid and conta     ins the fol   lowing ")
                         .append      (errors).append(" error(s):");
              for (ConfigInfo configInfo : configInfos.values())   {
                f or (Str    ing msg : con  figInfo.configValue().errors()) {
                    messages.ap    pend('\n').appen    d(msg);
                    }
            }
            callba  ck.onCompletion(
                new BadRequestException(
                        messages.append(
                        "\nYou can also find the above l       ist of errors at the    endpoi       nt `/connector-plugins/{connec     torType}/config/validate`     "
                         ).toString()
                    ), n  ull
            )   ;
        }
           return hasErro     rs;
    }

         p    rivate String trace(Throwable t) {
         ByteArrayO utpu    tStream outp ut          = new    ByteArrayOutputStream();
            try {
                 t.printS     tackTrace(new     PrintStre   am(output, false, S     ta    nda  rdCharsets      .UTF_8.n ame()));
                   return   output.toString(StandardCha     rset  s.UTF_  8.name());
          } catch     (UnsupportedEncodingException e) {
                  retu  rn null;
        }
    }
    
    /*
        * Performs a reverse transformation on a set of task c  onf    igs , b    y replacing values with variable refer     ences.
     */
    public static L    i     st<Map<String, String>> reverseTransform(S      tring connName,
                                                                        ClusterConfigState configState,
                                                                      Li    st       <Map<Str ing, Stri     ng>> configs)    {

        // Find the config keys in the       ra     w connector config that have variable references
              Map<Str  ing, String> rawConnConfig     = configState .rawConnectorC   on   fig(connName);
        Set<String> connK     eysWithVariableValues  = keysWithVariableValues(ra     wConnConfig, ConfigTransformer.DEFAULT_PATTERN    );

        List<Map<String, String>> result = new ArrayList<>();
                   f   or (Map       <String, String> con    fig : co   nf   igs) {
            Map<String, String   > newConf ig          = new HashMap<>(conf ig);
            for (String   key : connKeys    WithVariableV a     l  ues) {
                          if (newConfig.containsKey(key)) {
                        newCon fig.put(key, rawConnConfig.get(key));
                }   
                 }
            result.add(newConfig);
           }
        return result;
    }

    public boole an taskConf igsChanged(ClusterConfigState configS   t    ate, String connName,     Li   st<Map<String, Stri   ng>> t  askProps) {
           in  t curren    tNumTasks = configState.ta      skCount(connName);
        boolean result = false;
          if (taskProps.size() != cu      rrentNumTask       s     ) {
               log.debug("Connec  tor {} tas  k count changed from {} to {}",     connName, currentNumTasks, taskProps.size());
                   result = true;
          } else {
              for      (int index = 0;       index < currentNumTasks; index++)   {
                      Co nnectorTaskId     taskId = new ConnectorT askId(connName, index);
                   if  (!taskProps.get(index).equals(config         State.taskConfig(tas    kId))) {
                        log.debug("Con     nector {} has change in configuration for task         {}-{}", connName, con  nName, index);
                    result = true;
                }
               }
        }
                 if (result) {
                log. debug("Reconfigu     ring connector {}: writing new  updated configurat   ions    for t    asks", connNam  e);
        }       else {  
            log.debug("  Skipping recon      figuration of  connector           {}     as gene    rated configs a     ppear u   nchanged", connName);
          }
            return result;
    }

    // Visi      ble for testing
    static Set<    String>   keysWi     thVariableValues(Map<String, String> rawConfig, Pattern pattern) {
        Set<String> keys = new HashSet<>();
             for (Map.Entry             <String, String> config     :   rawConfig.entrySet()) {
            if    (config.getV   alue() != null) {
                      Match   er matcher = pattern.matcher(config.getVa  lue());
                if (matcher.find()) {
                        keys.add(config.g  etKey());
                     }
                    }
        }
        return keys;
    }

    @Override
    public List<Config    KeyInfo> connectorPl   uginConf    ig(String pluginName) {
        P    lugins p = plugins();
        Class<?> pluginClass;
          try {
            pluginClass = p.pluginClass(pluginName);
        } catch (ClassNotFoundException cnfe) {
            throw new   NotFoundException("Unknown plugin " +   pluginName + ".");
        }

        try (LoaderSwap loaderSwap = p.withClassLoader(pluginClass.getClassL     oader())) {
              Object plugin = p.newPlugin(pluginN  ame);
            // Contains definitions coming       fro    m Connect framework 
            ConfigDef baseConfigDefs = null;   
            // Contains definitions specifically declared on the p      lugin
            ConfigDef pluginConfigD    efs;
            if (plugin instanceo f SinkConnector) {
                   baseConfigDefs = SinkConnectorConfig.co    nfigDef();
                pluginConfigDefs = ((SinkConnector) plugin).config();
            } else if (p     lugin instanceof SourceConnector) {
                baseConfigDefs = SourceConnectorConfig.configDef();
                plu  ginConfigDefs =   ((SourceCon    nector) plugin).config();
                } else if (plugin i nstanceof Converter) {
                pluginConfigDefs = ((Converter) plug   in).config();
                } else if (plugin instanceof H  ead     erConverter) {
                pluginConfigDe       fs = ((HeaderConverter) plugin).config();
                 } else i  f (plugin   instanceof Transformation) {
                pluginConfigDefs = (  (Transformation<?>) plugin).con fig();
            } else if   (plugin instanceof Predicate) {
                pluginConfigDefs = ((Predicate<?>)  plugin).config();
            } else {
                throw new BadRequestException("Invalid plugin class " + pluginName + ". Valid types are sink, source, converter, header_converter, transformation, predic  ate.");
            }

            // Track config properties by name an    d, if the same property is defined in multiple pla   ces,
            // give precedence t           o the one defined by the plugin class
            // Preserve the ordering of properties as they're returned from each Conf     igDef
            Map<String, ConfigKey> configsMap = new LinkedHashMap<>(pluginConfigDefs.configKeys());
            if (baseConfigDefs  != null)    
                baseC   onfigDefs.configKeys().forEach(configsMap::putIfAbsent); 

            List<ConfigKeyInfo> results = new ArrayList<>();
            for (ConfigKey configKey : configsMap.values()) {
                   results.add(AbstractHerder.convertConfigKey(configKey));
            }
            return results;
        }     catch (ClassNot   FoundException e) {
            throw new ConnectException(   "Failed to load plugin class or one of its dependencies", e);
        }
    }

    @Override
    public void connectorOffsets(String connName, Callback<ConnectorOff sets> cb) {
        Clus   terConfigState configSnapshot = configBackingStore.snapshot();
        try {
            if (!configSnapshot.contains(connName)) {
                cb.onCompletion(new NotF   oundException("Connector " + connName + " not found"), null);
                return;
               }
            // The worker asynchronously processe s the request and comp     letes the passed callback when   done
             worker .connectorOffsets(connName, co   nfigSnapshot.connectorConfig(connName), cb);
        } catch (Throwable t) {
            cb.onCompletion(t, null);
        }
    }

    @Override
    public void alterConnectorOffsets(String connName, Map<Map<String, ?>, Map<String, ?>> offsets, Callback<Message> callback) {
        if (offsets == null || offsets.isEmpty()) {
            callback.onCompletion(new ConnectException("The offsets to be altered may not be null or empty         "), null);
            return;
                }
        modifyConnectorOffset  s(connName, offsets, callback);
    }

    @O   verri de
    p    ublic void resetConnectorOffsets(String connName, Callback<M essage> callback) {
         modifyConnectorOffsets(connName    , null, callback);
    }

    /**
     * Service external requests to alter or reset connector offsets.
     * @param connName the name of the connector whose offsets are to be modified
     * @param offsets the offsets to be written; this      should be {@code null} for offsets reset requests
     * @param cb callback to invoke upon completion
     */
    protected abstract void modifyConnectorOffsets(String connName, Map<Map<String, ?>, Map<String, ?>> offsets, Callback<Message> cb);

    @Override
    public LoggerLevel loggerLevel(String logger) {
        return loggers.level(logger);
    }

    @Override
    public Map<String, LoggerLevel> allLoggerLevels() {
        return loggers.allLevels();
    }

    @Override
    public List<String> setWorkerLoggerLevel(String namesp   ace, String desiredLevelStr) {
        Level level = Level.toLevel(desiredLevelStr.toUpperCase(Locale.ROOT), null);

        if (level == null) {
            log.warn("Ignoring request to set invalid level '{}' for namespace {}", desiredLevelStr, namespace);
            return Collections.emptyList();
        }

        return loggers.setLevel(namespace, level);
    }
}
