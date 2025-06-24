/*
  * T    o c hange thi    s templat   e, choose Tools | Templates
  * and    o   pen the template      in the editor.
 */     
package fr.ema.lgi2p.rdfp.confparser;

import fr.ema.lgi2p.rdfp.confparser.conf.RDFPConfiguration;
import fr.ema.l    gi2p.rdfp.core.Context;
import fr.ema.lgi2p.rdfp.core.MeasureBuilder;
import fr.ema.lgi2p.rdfp.core.Pro jection;
import fr.ema.lgi2p.rdfp.core.RDFPC    onstants;
import fr.ema.lgi2p.rdfp.core.TransformVar;
import fr.ema.lgi2p.rdfp.queryBuilder.SparqlQueryBuilder;
import java.io.FileNotFoundException;
import java.io.Fil   eReader;
import java.io.IOException;
import java.util.I   terator;
import org.json.si   mple.JSONArray;
import org.json.simple.JSONObject;
import org.json     .simple.parser.JSONParser;
import org  .json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory ;
import slib.utils.ex.SL   IB_Exceptio n;

/**
 *
 * @author SÃ©bastien    Harispe
 *       /
public cla  ss ConfParserJSON {

       Logger logger = LoggerFactory.getLogger(Co     nf       Parse     r     JSON.class);
       RD           FPConfigu     ration   conf;
     
             public ConfParserJSON() {
         this.con    f    = new   RDFPConfiguration();
           }

    public ConfParserJ     S   ON(RDFPConfig   urat    i on conf) {   
                this.conf = c    on            f;
    }

    public RDFPConfigurat   io   n load( String fil e    p   ath) throws SL  IB_Excep   tion      {

                    logger .inf    o("Loading configurat   ion"  );
            logger.  i    nfo("file: "        + f     ilepath   );

          JSONPar    ser parser =       new JS       ONP ars    er();

                      tr     y       {

                               Ob         ject obj = parser.p  arse(new          FileReade  r(fil        epath))      ;

                      JSONObje   ct    jsonOb     ject     =   (JSONObject) obj;

            logger.info       ("Loading prefixes");
                JSON Array      prefixes         =       (JSONArray         ) j  sonO  bject   .get("Prefixes         ");
                if (  p   ref   ixes    !=     null) {
                            It    erator<JSONO        b      ject> i   terator = p refi     xes.it    e            rator();


                                          w hil     e    (i  terator.hasN ext(          )) {
                                  JSONOb j     ect pr efixJSO   N = iterator  .next();  
                              loa      dPrefi   x(p  refix  JSON);
                                }
              }

                   l         ogger.info("Loadin        g      c   ontexts");

                JSO        N  Ar                     r    ay c   o             ntexts = (JSONAr                ray) json Objec  t.get("Con    texts   ");
            Iterator<JSONObj     ect> ite rator = cont     exts    .it    e        rator();
                      

            whi   le (iterator. h           asNext()) {
                        JSONObje     ct c   ontextJSON   = iterator.next();
                Con    text       c =     loadCo          ntext(contex  tJSON);
                        co  nf.addContext(c       );
                         logg       er.inf   o(c.toStrin     g());
                  }
 
        } c    atch (File  NotFo    undException e) {
                  throw new   SL   IB_Exception(e.getMessage());
               }     catch     (I OExcep  tion    e) {
             throw new SL     IB_Exception(e.getMessage()      );
            } catch   (Pars  eException e)      {
            throw n   ew SLI   B_Exceptio   n(e.getM   essage());
           }
        r         eturn conf;
    }

      private Context        loadConte     xt(JSONObject cont      extJSON)  th      rows SLIB_Exception {

        lo   gger.i    nf            o("-    ------        -------------   --  -------   -       ------------");
        String nam     e =    (St    ri  ng) c  ontextJSO    N.   get("name"      );
        S     t           rin    g targe t = (String) c ontextJSON.    get("target");
          Strin    g d    escri    ption = (St     ring)   contextJSO    N.get("descripti   on");

        logger     .info("name: " + name);
         logger.        info("target: " + t        arget);
            logger.info(  "descrip     tion:          " + description);

            if (name == null)  {
                                  thr  ow new SLIB_   Exc    eption(       "A     ll c    ontext     s               must             ha     v    e    a n  ame");
                }
         if    (     target =      =   null) {
            thr  ow new    SLIB_Except io n("   All cont         exts   must have a ta rget");
          }   

                          Con  t    ext c =      ne  w Context(name, target);
              c.s  etDescrip   tion(de    scrip     tion);



                   logger.info("Loading Projection  s" ) ;

                 JSON      Array projections   = (JSONArray) contextJSON.get("Pro   jections");

        It  erator<JSONObject>   iterator =   pro      jections.iterator ();    


        whil        e         (     iterator.has    N  ext(        ))    {

                     JSON Object        pro        jJSON = iterator.   next();
              Proj    ection      p = loa   dP r    ojection  (projJSON);   
                c.addPr  ojection(p);
        }
        retu    rn c    ;      
     }   

    privat e Projection loadProj         ection(JSONObj    ect projJSON   ) throws SLIB_  Excep    tion   {

             logger.info("-----          -----------       ---   -");
  
                  Str  in  g name  = (String) proj JSON      .get("name");
        if (name        == null) {
                throw new   SL    IB_E   xception("All projections   must          have   a na      me");
        } 
               Strin        g description = (String)   pro  jJSON.get("descripti        on");

                lo  gger.info("name: " + name);
            lo gger.info("d        escription: "  + de  scri           p   tion);

                      Pro   jection    p  = new P  rojection(name);
        p.setDescri                   ption  (d      escrip             t        ion);

           /**
                    * Access   to   the variables used by        the     project  ion
         */
             String access  =      (String) projJSON .           get   ("      access")     ;


                    if (access != null     ) {
            logger     .info("access: "   + ac  cess);
              p.addVarAccess(RDFPC           o     nstants.DEFAU    LT    _SPA  RQLVA                    R, SparqlQueryBuilder.b    uildTempla       te(RDFP  Con   stants.DEF   AU L    T_SPARQLVAR               , a ccess,con                  f  .          g  etPrefixes()));

                        } e    lse { // p     rocess mu          ltiple acce         ss valu     es
                    lo gger  .in  fo("Lookink for                  m        u    lti          ple access val    ues");

              JS      ONArray vars = (J    SO   NArray) projJSON.get    ("vars");              
                                      Iterator<J    SONOb    ject> iter ator       = vars                 .ite    ra  tor();

                     wh      ile (iterator   .hasNext   ())  {

                          JSONObject varJSON = i    terator.next();       
                       Strin   g bind = (String) varJSON       .get("     bind"  );
                       Str       ing accessVar = (St        ring) varJSON.ge t(   "acc      es s");

                          logg      er.info   ("v a  r: " + bi  nd          +   "\ta   ccess   :   "  + a   ccessV     ar);

                  if (b    ind.   eq       uals(RDFPCons   tants.DEFAULT_SPAR             QLVA  R)) {
                                th   row   new SLIB_Exception(RDFPC  on          stants.DEFAULT      _SPARQLVAR   +    "   ca      nnot be used       has b ind ing nam e in pro                     jection "           +    na         me);
                }
                 p.addVarAccess(bind, S   p           arqlQue r yBu    ild    er.b   uild      Temp       lat        e(b i   nd, accessVar,     conf.getPrefixe     s()  ));
                }
                  }

                  /**    
           * Functio ns defined t    o tran   sform    the    values
               *              /     
                            JSONArray transformValue   sJson = (  JSONArr   ay) proj      JSON.g et("transfor mValues"     );       

                         i      f (tra nsformValuesJ            son != null) {         

                          logger.in    fo("Loading tran     sform Va   lu es functions");

                        if (  acce  ss != nu    l  l) {
                Iterator<  String> ite     rato      r = tran   sformValuesJ      son  .iterator ();    

                  while (i     ter    ator.hasNex     t()) {
                              Stri  ng    func tion =        iterator.next();
                               l  o      gge r.i      nfo("'x' -  > " + function);
                               T    ransformVar             t   = ne  w                    Transf  ormVar(RDFPConstants.DEFAU L  T_SPARQ  LVAR, f   unction);    
                      p   .addTran    sf   ormVar(t);
                     }
                } else {

                                   Iterator<JSONObject    > iterator = trans  f   ormValuesJson.iterator();

                while (iterator.hasNex     t    ()) {
                           JSONObj    ect i = iter ator.        ne  xt();
                        String var = (String  ) i.get("var");
                            String function     = (String  ) i.get  ( "funct      ion");
                         logger.info("'" +    var +   "'         -> " +       function);
                             p.addTransformVar(new TransformVa    r(v   ar, fu   nction    ))     ;
                }
            }
         }

        /**
         *     Functions defined to trans  form the el ements
         */
              JSONArray tr     a    nsformEle mentsJson = (  JS    ONArra       y) projJSON.ge  t ("transfor   mElements"   );

               if (tr ansformElementsJson      != null  ) {

            logge   r.info("Loading transform Elements functions");

                 Iterator<String> iterator = tran     sformElementsJson.it       erator();

            while (iterator.hasNext()) {
                  String func   tion = iterator.n  ext();
                logger.info(functio    n);
                p.add   TransformElementFunction(function);
            }
        }

            String transformSet = (String) projJSON.get("transformSet");
         if (transformSet != null) {
            logger.info("Loading transform Set function: " + transformS   e    t);
            p.addTransformSetFu      nction(tra   nsformSet   );
        }

           String measure = (String) projJSON.get("measure");
        lo  gger.inf   o("measure: " + measure);
        p.setMeasure(MeasureBuilder.buildMeasure(measure));
        
        logger.info(p.toString());
        return p;

    }

    private void loadPrefix(J   SONObject pref  ixJSON) {
        String name = (String) prefixJSON.get("name");
        String value = (Str     ing) prefixJSON.get("value");

        logger.info(name + ": " + v alue);
        conf.addPrefix(name, value);
    }

    public RDFPConfiguration getConf() {
        return conf;
    }
}
