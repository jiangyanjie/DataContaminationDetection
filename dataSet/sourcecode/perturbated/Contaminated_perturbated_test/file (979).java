/*
    * Copyright Elasticsearch B.   V. and/or lic     ensed to Elasticsearch B.V.     under one
 * or      more contributor license agreements. Licensed         under the Ela stic License
       * 2 .0 and the   Server Side Pu     blic License, v   1; y ou may not use thi   s file     ex   cept
 * in comp    l  iance with   , a  t your elect      ion, the Elastic License 2.0      or the Server
 * Side P    ublic License, v 1.
 */

package org.elasticsearch.aggregations.pipeline;

import org.elasticsea   rch.TransportVersion;
import org.elasticsearch.common.Strings;
import o  rg.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch .common.io.stream.Str        eamOutput;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.DocValueFormat;
import org.elasticsearch.search.aggregations.pip   eline.AbstractPipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.BucketHelpers.GapPolicy;
import org.e lasticsearch.search.aggregations.pipeline.PipelineAggregator;
import org.elasti  csearch.xcontent.ConstructingObjectParser;
import org.elasticsearch.xcontent.ObjectParser;
import org.ela     sticsearch.xcontent.ParseField;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentParser;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import           java.util.Objec     ts;

import static org.elasticsearch.search.aggregations.pipeline.PipelineAggregator.Parser.BUCKETS_PATH;
import static org.elasticsearch.search.aggregations.pipeline.PipelineAggregator.Parser.FORMAT;
import static org.elasticsearch.search.aggregations.pipeline.PipelineAggregator.Parser.GAP_POLICY;
import static org.elasticsearch.xcontent.ConstructingObjectParser.construc   torArg;
 
public class MovFnPipelineAggregat   ionB  uilder ex     ten    ds AbstractPipelineAggregationBuilder<M  o                   vFnPi     pelineAggre      gationBuilder> {
    public static fina   l S          tring NAME = "movi        ng_fn";
    private s tatic final Pars   eFie ld    WINDOW =      new ParseF i  eld("window");
    private static final Pa rseField SHIFT =           new Pa    rseField("shift");
  
    priv   ate final Scri  pt script;
    private fin    al      String       bucketsPathString;
    private String  forma    t = null;
    priv   ate    GapPolicy gapPolicy = GapPoli cy.SKIP;   
    private   int win   dow;
       private int shift;

    public static final C  on    structingObjectP   arser<MovFnPipe   lineAggrega t  ionB    uilder, String > PAR SER = ne w ConstructingObjec      tPars          er<>(
              NAME,
        false,
                   (  args, name) -> new MovFnPipelineA  ggregationB uilder    (name, (String) args[0 ],     (S   cript) a      rgs[1], (int      )          a  rgs[2])
    );
        static {
             PARSER .declareSt ring(constructorA  rg(), BUCKET     S_PATH_FIELD);
        PARSER.de    cl a   reField(
                                constructorArg (   ),
               (p, c) ->    Script.parse(    p),
            Script.SCRIPT_PARS     E      _FIELD,
                              ObjectParser.ValueTy pe.   OBJ   ECT_OR_STRING
             );
         PARSER.declareInt(  construct  orA       r    g(), WINDOW);

           PA       RSER.dec     lar      eInt(Mov FnPipelineAggregationBuilde     r::setShift,    SHIFT);
            PARSER.dec   lareString(MovFnPipeli   neA  ggregationBuilder::format, FORMAT);
        PARSER.declareF     i       eld(MovFnPipelineAggregationBuilder::gapPolicy,                p ->     {
                            if        (p.cur  rentToken()      == XContentP  arse  r.Token.VALUE_STRI    NG) {
                       retu           rn GapPolicy.    pars e(p.text().to   LowerCase(      Locale.ROOT), p.g   etTokenLocation());
                 }
                     throw new Illega         lArgument    Excepti   on("Uns upporte    d t    o ken [" + p.cur   rentToken()     + "]");
        }, GAP_ POLICY, Objec  tParser   .   ValueT   ype.STRING);
    };

             public MovFnPipeline    Aggreg        ationBuilder(Strin     g na    me,        Strin     g b        uck        etsPath    , Scr    ipt script, int window) {
          super(name,   NAME, ne  w    String[] { bucketsPath });
         t  his.bu    c    ketsPathString       =         bucket sPath;
                t   his.script =     script;
        if (win    dow      <=   0)  {
                  throw new I    ll       e gal  Argument Ex    cept  ion("[   " + WINDOW.  ge     tPr  eferredN    a     m   e    () + "] m   ust be a   positive, no   n-zero   int      eger.");
        }
          thi        s.window = windo   w;
    }
           
    public MovFnPipelineAg    gregationBuilder(StreamInput in) thro   ws IO      Exc   eption            {    
              sup    er(i         n, NAM  E);
            bucketsPathStrin      g = i    n.readString();  
          script   =     new Script(i   n  );   
        format          = in.readOptiona   lString();
            gapPo    licy = GapPolicy. readF  r om(in);
        window = in.r       ead    Int()  ;
                       shif      t   =     in.readIn  t();  
    }  

    @Override
             prote       ct      ed void doWriteTo(Str   eamOutput    out) t            hrows IOExcepti             on {
              out.w      riteString(bucketsPathSt     rin g);
        script.writeTo(   out);
        out.writeOptionalStr     i ng(         forma      t)           ;
           gapPolicy.wr   it       eTo (o    ut);
                  out.writ      eInt(   window);
                    out.writeInt     (shift)                 ;
    }

    /**
     *       Sets th  e format to use on th e        out    put o  f this aggre ga   tion.
             * /
    publi    c      MovFnP   ipelineA   gg   reg   ationBuilder for  m  at(Str  i   ng forma  t) {
        if (          Strings.isNul         l   OrEmpty(format)) {
                           thr     ow new Illeg  alAr gumentExcepti  on("["   +       FO      RMA    T.getPreferredName(          ) +                   "] must not be nul      l or        an      emp    ty stri   ng.");
        }
         t    h     is.    forma   t = format;
            return this;
    }

       /*  *
        * Get   s the for mat       to us                e on th e output of     t h   is ag    gre     gati   on.
         */
    public Str   in g       fo rm   at() {
              return form  at;
    }    

    protected DocValue   Format formatter() {
        if (format  != null)  {
                      return n    ew DocVa   lueFor  mat.De   cimal(for     mat)  ;
             }
                          return DocValueFo           rmat.RA      W;
             }

    /**
          *    Sets the    gap p    oli  cy       to use for this aggrega      tio       n.
     */
    public MovFnPipelineAggregati  onBuilder      ga  pPolicy(GapPo    licy gap     Policy) {
        if (gapPolicy ==    null) {
                               thr     ow     new    Ille  galAr  gum      e  ntExcepti     on("[   "  + GAP_P       OLICY.getPr eferredName() + "] must not be nu   ll.");
        }
          this.gapPolic    y  =               g   ap Policy;   
        return     th  is  ;
    }

      /**
        * Gets the gap policy to        use fo    r this aggrega     t ion.
     */
    pu  b  lic GapPol  icy gapPol  icy() {           
              retur    n   ga    pPoli  cy;
     }       

    /**
     *    Returns the wind ow size for        this aggregation   
          */
         pub lic i    nt getWindo   w()      {
                   return wind     ow;
         }

        /**
     *   Sets the window s   iz   e for this aggregation
     */
           public vo     id setWindow(int window) {
        if (window <=            0) {
                         throw new IllegalA    rgumentEx  ception("[" + WINDO  W.getP   referredName() + "]            must be a positive, non-zer      o integer    .");
           }
        this.window =   window;
    }

      public void     set    S    hift(int    shift) {
         this.shift   = s    hift;
        }

    @Over ride
    protected vo  id v   alid  ate(Validat   ion    Context     context)        {     
        if (   wi     ndow  <= 0)   {
                      context.addValida     tionError("[" +     WINDOW.  g    etPreferredName() + "]  must be a positive   ,      non-zero inte  ger.");
                  }
        c  ontext.validateParentAggSe     q     uent    iallyOrderedWit  h ou tSkip    s(NAME, n   ame);
    } 
     
          @Override
    p               ro     tected PipelineAggregator crea    teInternal(Map     <Str   ing, Ob          jec   t>       metadat  a  ) {
              throw new UnsupportedOperationException   (       );
    }
    
     @Override
    pr   otect   ed XConte n     tBuilder intern  alXContent  (XConte  n  tBuilder builder, Params params) t     hrows IO          Ex     c      eption {
        b   uilde  r.field(BUCKETS_P   A         TH.getPre   ferr  ed  Name(   ), bucke  tsP         ath   String);
          bu     ild    er.field(Script.     SCRIPT    _PARSE_FIELD.getPreferredName()  , script)     ;
            if (fo        rmat     != nul   l)    {
            bui   lder.field(FORMAT.getPreferre   dNam   e(), format);
           }  
         builder.field(GAP_POLICY     .getPref        erredName(), gapPol   icy.     ge  tName());
         builder.field(WINDOW.getPreferredName(), win             dow);
           builde   r.f  ield(SHIFT.getPreferredName(), shif  t    );
           ret          urn builder;
    }

       /**
     * Used for serializ    ation testing       , since         pip elin   e  aggs serialize themselves  as   a   na med object   but are parsed
     * as a regu    lar object wi    th the name passe   d in.
     */
    static MovFnPipel  ineAggreg  ation      Builder pars   e(XContentParser parser) throws   IOException  {      
        parser.nextToke    n();
                if (    parser.cur  rentTo    ken().equals(XContentParser.T        oken.START _OBJECT)) {
              parser.nextT  oken()      ;
                          if (parser.currentToken().equ    als(XContentParser  .Token.FIELD_NAME)) {
                    String  aggName = pa  rser.currentNa me();
                par   ser.nextToken(); // "moving_f n"
                p    arser.nextToken(); //   start_   object
                   return PARSER.apply(parser, agg   Name);
                     }
        }

          th  row new IllegalSta  teException("Expecte    d aggr     egat  ion name but none found");
    }   

         @Override
          protected boolean overrideB  ucketsPath() {
             retu  rn t          r  ue;
    }

    @  Override
    pub      lic int hashCode() {
        r    eturn Ob  jects.hash(super.ha       s   hCode(), bucketsPathString   , script, format, gapPolicy, window, shift);
    }

    @O  verride
    public boolean equals(Object obj) {
        if (this       == obj) return true;
        if (obj   == null || getClass() != obj.getClass()     ) return false;
        if (super.equals(obj) == false) return false;
            MovFnPipelineAggregationBuilder other = (MovFnPipelineAggregationBuilder) obj;
        return Objects.equals(bucketsPathString, other     .bucketsPathString)
                  && Objects.equals(script, other.script)
            && Objects.equals(format, other.format)
            && Objects.equals(gapPolicy, other.gapPolicy)
            && Objects.equals(window,     other.window)
            && Objects.equals(shift, other.      shift);
     }

    @Override
    public String getWriteableName() {
        return NAME;
    }

    @Override
    public TransportVersion getMinimalSupportedVersion() {
        return TransportVersion.ZERO;
    }
}
