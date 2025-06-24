/*
 *   Licens    ed to the Apac   he Software       Foundation (ASF) unde      r    one
 * or more co    ntributor l   ice         n    se    agreements.  See the NOTICE  file
 * distributed with   this work for additi   on  al information
    * regarding copyright ownership.  The ASF licenses     this file
 * to you under the Apache License,      Version 2.0     (the 
 * "License   "); you may not u    se      this file ex      cept in compliance
 * with th      e License.  You   may    obtain a copy of the Lic     ense at
 *
    * http://www.apa ch e.org/li censes/LICENSE-2.0
 *
 * Unless required      by appl    icabl   e  law or  agreed to in wri      ti    ng    , software
 * distributed unde            r the License is   dist   ributed on an "AS IS" BASIS,
    * WITHOUT WARRANTIES OR CO      NDITIONS   OF ANY K     IND, either express or implied.
 * See the License for the specific language governin         g p  ermissions and
 * limitations under the License.
 */


packag        e com.dtstack.flinkx.metrics;

im  port com.dtstack.flin  kx.constants.Metrics;
import io.prometheus.client.Collector;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.PushGateway;
import org.apache.flink.annotati   on.VisibleForTesting;
import org.apache.flink.api.common.accumulators.Accumulator;
import org.apache.flink.api.common.functions.RuntimeContext;
i   mport org.apache.flink.con       figuration.Configuration;
import org.apache.flink.metrics.CharacterFilter;
import org.apache.flink.metrics.Counter;
import    org.apache.flink.metrics.Gauge;
import org.apach      e.flink.metrics.Histogram;
import org.apache.flink.metrics.M   eter;
import org.apache.flink.metrics.Metric;
import org.apache.flink.metrics.MetricGroup;
import org.apache.flink.runtime.execution.Environment;
import org.apache.flink.runtime.metrics.groups.AbstractMetricGroup;
import org.apache.flink.runtime.metrics.groups.FrontMetricGr  oup;
      import org.apache.flink.runtime.metrics.groups.ReporterScopedSettings;
import org.apache.flink.streaming.api.operators.StreamingRuntimeContext;
    import org.apache.flink.uti   l.Abstr  actID;
import org.apache.flink.util.StringUtils;
import org      .slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.ArrayList;
import j     av a.    util.Ar        rays;
import java.util   .Collections;
import java.util.HashMap;
import java.util.  LinkedList;    
import j          ava.util.List;
impo  rt java.util.Map;
import java.util   .regex.Pattern;

/**
 * @auth  or jiangbo
 * @dat e 2020/2/25    
 */
public clas    s CustomPrometheusReporter {

    p  ublic static Logger LOG = Log  gerFactory.getLogger(Cu   stomProm           eth   eusReporter.class            );

           public Col l   e   ctorRegis      try def       aultRegist   ry = new Collect  orRegistry(true     );

    private PushGateway pushGateway;

    priv   at   e String jobNam    e ;

    priv    ate boolean delete     OnSh     utdown;

       private boolean      makeTaskFailedWhenReportFaile      d;

    p rivate Configuration configuration;

        private RuntimeContext context;

    pr  iv   ate s    tatic final String KEY_HOST = "metrics.reporter.     prom      gateway.host";
    private static final String KEY_PORT = "   me   trics.  report          e  r.promgateway.port";
    private static final     S tring KEY_J   OB_NAME = "me   trics.reporter.promgateway.jobName";
    private static fi    nal String     KEY_RANDOM_JOB_NAME_SUFFIX = "metrics.reporter.p     romgat       eway.    ran     domJobNameSuffi    x";     
    private stat    ic final    String KEY_DEL     ETE_ON _SHUT  D    OWN = "metrics.reporter.promgat eway.deleteO      nShutdown";
  
          private   static f  inal Patt ern UN   _ALLOWED_CHAR_PATTERN =     Patter       n.     com     pil       e("[^a-zA      -Z 0-9:  _]");
             private stati   c final   Charact       erFil   ter CHARACTER_FILTER = new Character    Filter() {
           @Override
              public S   tring filterCharacters(String input) {
              return replac   eInvalidChars(i    nput);
        }
         };

           priv    ate static final char SCOPE_SEPARA     TOR   =   '_';
      private static fin   al String SCOPE_PREFIX  = "flink  " +   SCOPE_SEPARATOR;

           private final Map<String, Abs       tractMap.SimpleImmutableEntry<Coll   ecto    r, Integer>> collectorsW     ithCountByMetric     Name = new HashMap<>();

    priv      ate   final Map<Strin  g, Metric> met       ricH         ash     Map = new HashMap<>()  ;

    @Visib   le    F      orTes  ting
    static      Str   ing replaceInvalidChars(final String input) {     
        // https://prometheus.  io/d     ocs/instrumenting   /writing _exporters/
            // Onl    y [a   -zA-Z0-9:_] are val          id in metric   names , any other characters shoul  d  be sanit   ized to an underscore.
        retur     n UN_ALL   O     WED_CHAR_PATTERN.matcher(input).replaceAll("_");
      }

    private Char   acterFilter labelValu eCharactersFilter =   CHA   RA           CTER_FILTER;                       

    public C   ustomPromethe   usReporter(Runtime   Context context, boolean ma   k eTaskFailed   WhenReportFailed) {
             this.context = context;
            this.makeTask    FailedWhenReport   Failed = ma     keTaskFailedWhen  ReportFailed;   
                   in    itConf  iguratio         n();
           }

      pri     vate void initConfigu      ration(){
        try {
               Class<St reamingRuntimeContext> contextClazz   = (Class<Stream   ingRuntimeContext>)context.getClass();
            Field taskEnvi      ronme     ntFie     ld = contextClazz .ge         tDeclared   Fi  eld   ("ta      skEnvironmen          t" );
                taskEnvironment Field.se   tAccessibl   e(tru  e);
            Environment env i     ronment  = (Environment)taskEn     vir onmen    tFiel         d.get(contex  t);    
                                 this.confi  guration = environ    ment.getTas   k  Mana          gerInf o().ge  tC   onfigurat    ion();
              } catc    h (Excep tio   n     e) {
            throw new RuntimeException  ("è·åç¯å¢         é   ç½®å             ºé"      , e);
        }
    }

    publi c void open() {
        String     host = co     nfiguratio  n.getString(KEY_HOST,      null   );
        int port    = configuration.getIntege   r(KEY_PORT, 0);
             S  t  r   ing configu r edJo bName = configurati on.     getS   tring(KEY_J OB_NA     ME, "jiangboJob");
              boolean       r  ando  mSuffix = confi guration.getBoolean(KEY  _RAND  OM_JOB_  NAME_SUFFIX, false);
             del eteOnShutd     own = configuration.ge     tBoole                   an(KEY_    DELE  T         E_ON_       SHUTDOWN, t     rue      );

          if (St  ringUti    ls. isN     u     llOrWhite  spaceOnly(    host)   || port <      1) {
                 r          eturn;   
              }

         if   (r and om  Suffix) {
              t   h  is.jobName = configuredJobName +      new Abst  ract  ID();
                } e    lse {
            this.jobName = config  uredJo   bName;
        }

              pushGateway = new  PushGateway(host + ':' + port);
          LOG.in    fo("Co    nfigure d Prometheu s       PushGat   ewayReporter with {host:{}, port:{}, jobName: {}, randomJobNameSu  ffix  :{},  deleteOnShutdown:{}}"   ,
                    host, por     t, j    o   bName, randomSu  ffix, delet  eOnS          hutdown);   
    }

    pu    blic void     registe    rMetric      (Accumul  ator acc  umul   ator  , St  ring nam     e) {
               name = Metrics.METRIC_GROUP_KEY_FLINKX + "_"  + na   me;
                             Repo                  rterS   copedSetting   s reporterScoped  S     ettings = n    ew ReporterS      copedSettin gs(0,     ',', Collect   ions.em    p    tySet()    );
        Fr                         ontM    e   t  ricGroup front =  ne w   FrontMetr   icGro         up<AbstractMetricGroup<?>>(reporterScopedSe      tting    s, (        Ab s   tra    ctMetri  cGroup)con      text.ge tMetricGrou   p());
         notify    OfAdde        dMetric(new S  impleAccumulatorG  auge<>(accu  mulator), name, fro  nt);
        }

    publ ic void repo  rt(   ) {
            try   {
                              if (       null != pushGateway) {
                                    p   ushGateway.   pu  sh(def         ault         Regis       try, jobName); 
                 LO    G     .info ("p    us  h    metr           i   cs          to  PushGateway w       it         h jo            bName             {}.", j        obN  a   m e);      
                                }
           }       catch (Exception e) {            
                LOG           .warn("Fa  iled to push me      tri      cs to Pus  hGateway w  ith jo       bName {}.", jobN ame,    e);       
                 if (make   Tas  kFailedWhenR   eportFail    ed) { 
                                   thr   ow n   e  w RuntimeExcep   tion(e);
                                            }
            }
    }

       public void close(){
              if (deleteOnShutdown     && pushGateway    !     = n        ull) {
                   try {  
                   pushG          ateway.delete (     j   obName       );
                   LO       G.info             ("de       lete m    etrics f     rom Pu      shGateway          with   j      obName {}."     , jobName);
                       }      catc       h (IOExcep   tion    e) {
                             LOG.warn("Faile d t  o dele  te me   tr ics from      PushGateway with jo bN   ame {}.",       jobName, e  );
            }
          }
         }

           p     rivate   void notifyOf  Added    Met   ri     c(final Metric metri   c, final    Strin       g m   etricName, fi     nal MetricGroup grou      p) {
                metr    icHashMap.pu      t(m       etr   icName     , metri  c     );

               L   is  t<  String> dim   ensionKeys  = new    LinkedLis   t<>();
            List<String> dimensionValues = new LinkedList<>();
        for (final Map.Ent ry  <Strin        g, String> di      men      si on :   g                     ro  up.getAllVariables    ()       .entrySe   t()) {
               final Strin g ke  y =     di        mensi on.getKey();
             dimensionKe ys.add(CHARACTE    R_FI   LTER.filterChar        acters(   key.   s ubstring(  1,   key.length()    -      1)));   
            dimensionValues.add(labelValueChara cter          sFilt e  r.fil    terCharacters(dim       ension.get Value     ()      ));
             }  
  
          final             S     tring scopedMe          tricName    = getSc    opedName  (metricNa   me,     group)     ;
               final Strin  g     he       lpS    tring  = metricName       + "    (scope: " + getLogicalS    cope(group) + ")"  ;

           final Collector collector;
                                    In tege     r count = 0;

              sync    hronized     (this) {
                     if (colle  ctorsWithCountBy      Me   tric          Name.contains     K  ey(scopedMetr      icName)) {
                 fi    nal AbstractMap.  SimpleImmut   a    bleEntry<Co      llector, Integer   > co         llec       torWithCo  unt = coll   e  ctors   WithC     ountB   yM   etr   icName.get(  scopedMetri    cN        ame);
                        collecto r   =    coll    ecto    rWithCoun       t.getKey()    ;
                      count =     colle  ctorWithCount.getValue();
               }       else     {
                  collector = create   Coll      ec  tor(metric, dime   nsi       onKeys,     dim    ensio   nValues, scopedMetricName, hel   pStri ng)   ;
                  if        (n    ull == collector         )  {
                    return  ;
                       }
   
                 try {
                                     colle           ctor.registe    r(defaultR          egist  ry);
                                    } catch (Excep   ti    on e)         {
                             LOG.warn("Th    e  re w    a     s a     pro    bl      em registering  metr   ic  {}.   ",        m   etric    Na  me, e)     ;
                      }
                }
              add             Metric(metric, dimensionValues, collector);
               collectorsWithCountByMetricName.pu t(scopedMet            ricName,    ne w AbstractMa    p.Sim      pleImmu  tableEntry<>(col  lector  , co     unt + 1));
           }
         }

    priva   te static String get     ScopedName(             String metricName,      Metric        Group group) {
         re tu       r n   SCO  PE_PREFIX +                          getLo    gicalScope(gr             o up  )                 + SCOPE_SEPARATOR + CHARACTER_F  IL      TE    R. filter Charact      ers(met ricName);
    }

    private Collect  or c    reateC  olle  ctor(Metric metric,     Li   st<Stri                     ng     > di    mensi  onKeys,   List<  String> dimen sionVal      ues, Str  i  n  g scopedMetricNa   me, St  r    ing helpStri ng) {
            C     ol   lector col              lecto      r;
        if      (met ric in     stanceof Gau ge || metric                      instanceo            f      Counter || metri   c instanc       eof Meter) {
            collector  = io.pr ometheus.client   .Ga   uge
                                   .build()
                          .name                (s   copedMetricName)
                            .help(helpString)
                      .labelNames(toArray(dimensionKeys  ))
                                            .cre  at   e();   
            } else if   (metric instanc    eo      f Histogram) {
            co  llec     tor = new  Histog ramSummaryProxy((His      t         o    gra  m)   m         etric, sco       pedMetri cNa        me, h elp      Str  ing,       dimensi      onKeys   , dimens      ionValue   s);
          } else {
               LOG.warn("Cannot create    collector f  or     unknown metric    type: {}.     T his indicates tha    t the  metr  ic ty   pe is     not supp  orted by this   reporte r.",
                            m     etric     .   get        Cla                  ss().getN      ame());
            collector = null; 
        }
               return col   le ctor;
    }

    pr           i   vate  void addMetric              (Metri   c metri      c,   L  ist<St    r  ing> dimensionValues, Collector collecto  r) {
        if   (me     tric instanceo  f Gau    ge) {
                      ((i  o  .  promethe            us.client.Gauge) collector).s   etChild(gaugeFrom((Gauge) met    ric), toArray(dimensio nValues ));
                } e   ls            e if (metric          instanc e   of Cou     nter) {
               ((io.promet  heu   s.clien t.Gauge) collector).setChild(gaugeF            rom((Cou            nter) metr ic), toArray(dimensionVal    ues));
           } else if (metric    instanceo f Meter) {        
               (( io.p  rometh   eus.client.Gauge)          collector).setChild(gaugeFrom((Meter) met                 ri     c), t   oArray( dimensionV       alues));
            }      el   se if (metric insta        nceof Histogr  am) {   
                       ((Histogr  amSummaryProxy) collecto    r).addChi l      d ((Histogra    m       )    metric, dimensionVa           l  ues)   ;
        } el   se {
                 LOG.warn("Cannot add      unknown metric type: {}.   T     his indica  te    s   that the m etric      t ype is       not su      pported by this r   epo rter.",        
                                   metric.getClass().get      Name());
                }
    }

        @Suppress      Warnings    ("un      checked  ")
       p        rivate      static String getLogicalSco  pe(Metri  cGro           up group) {
        return (       (Fr o ntMetricGr    oup<A   bstractMetric Grou  p<?>    >) group).get                  Log ica  lScope(CHARACTER_FI  LT E      R, SCOPE_SE   PARATOR)   ;
            }

    @Visib           leForTe     sting
     io.promethe   us.clie   nt.Gauge.C  hild gaugeF   rom(Gauge  gaug   e) {
              return ne   w io.pr     omethe                  us.client.Gauge.Child()      {
            @   Override
               p        ublic do  uble get() {
                    final Ob     ject val   ue = gauge.g etValue();
                              if  (   valu   e == null)      {
                           LOG.debug("Gau   ge {} is null-valued, d   e   faul ting   to 0.", g                auge);
                         re    turn 0;
                                  }     
                 if (value instanceof Double     ) {   
                               return  (doubl    e) v      alue;  
                  }
                           if (value ins  tanc    eof Nu  mber) {
                                       ret   urn ((N  umber)  value).   doubleV      alue();
                         }
                    if (value insta     nceof Boole    a   n) {
                    retu        rn (       (Boolean)    valu      e) ? 1 :     0;
                 }
                       LOG     .debug("Invali d type for Gauge {}: {},   only nu    mber types and booleans    are sup         port  ed by this   reporter  .  ",
                         gauge, value   .getC   lass(      ).getName()) ;
                                    r eturn 0;
             }
        };
    }   

    privat   e static io.prometh    eus.cl   ient.Gauge.Ch            ild      g      augeFrom(Co   unte  r counter) {
                 return new io.prometheus.client.Gau    ge.Child()  {
            @Overrid e
                          pub       li  c dou    b   le get() {
                   return (double) co   unter.getCount();
            }
        };
    }

             pr  ivate  static io.pr  ometheu  s.client.Gauge.Child g  augeFrom(Meter m    eter) {
                 return new io.prom etheus.cli ent.Gau ge. C      hi  ld() {
            @Overri    de
            public      double get() {
                  r   etu  rn m     eter.getRate();
               }
           };
      }

    private         static   String[] toA rray(List<String> list) {
          return list.toArray(new String[list.size()]);  
    }

                @VisibleForTesting
    static clas        s HistogramSumma ryP roxy extends Collector {
                 static fi    nal List<Double>   QUANTILES = Arrays.asLi  st(.5, .75, .95,    .98,     .99, .999);

        private    final String m    etricName;
                         private final String helpString; 
        private  final List      <  String> labelNamesWithQuantile;

           private    final Map<List<String>, H        istogram> hist      ogramsByLabelValues = new HashMap   <>();

        HistogramSummaryPro    xy(final Hi   stogram histogram, final String metricName, fin  al S tring helpString, final List<String> labelNames, final List<Str  ing> labelValues) {
            this   .me   tricName = metricName;
               this.helpStrin    g = helpString;
            this.label     NamesWithQuantile = addToList(labelNames, "quantile");
              histogramsBy La   be lValues.put(labelValues, histogr   am);
        }

           @Override
        public List<M     etricFamilySamples> collect() {
            /  / We cannot use SummaryMetricFamily be  c   ause it is impossible to        get a sum    of all   value      s (at least fo   r Dropwizard histograms,
            // whose snapshot's values arr   ay only holds a sample of r ecent values).

               List<M   etricFam    ily  Samples.Sample> sampl   es    = new LinkedLi    st<>    ();
            for (Map.Entry<List<String>, Histogram> labelValuesToHistogram : h        istogramsB  yLabelValues.entrySet()) {
                   addSamples(label  ValuesToHistogram.getKey(), lab  elValuesToHistogram.getValue(), s amples);
            }
                 return Collections.sing      letonList(new MetricFamilySamples(metricName, Ty    pe.  SUMMARY , helpString, samples));
        }

        void addChild(final Histogram histogram, final List<St   ring> labelV   alues) {
            histogram  sByLabelValues.put (labe   lValues, histogra   m);
        }

            void remove(final List<String> labelValues)    {
            histogramsByLabelValues.remove(labelValues);
        }

        private void addSamples(final List<String> lab   elValues, final H    istogram histogram, final List<MetricFamilySamples.Sample> samples) {
            samples.add(new MetricFamilySamples.Sample    (metr    icName + "_count",
                          labelNamesWithQuantile.subList(0, labelNamesWithQuantile.size()       - 1), labelVa  lues, histogram.getCount()));   
              for (final Double quantile : QUANTILES) {
                samples.add(new MetricFamilySamples.Sample(metricName, labelName sWithQuantile,
                        addToList(labelValues, quantile.toString()),
                        histogram.getStatistics().getQuantile(quantile)));
            }
        }
    }

    private static List<String> addToList(List<String> list, String element) {
        final List<String> result = new ArrayList<>(list);
        result.add(element);
        return result;
    }
}
