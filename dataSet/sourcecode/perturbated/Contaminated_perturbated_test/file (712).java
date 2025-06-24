/*  $Id:    DefaultInferenceParameters.java 140336 2012-06-08  19:55:09Z dave s    $
 *
 *     Copyright (c) 2004 - 2006 Cyc   orp,   Inc.  All rights reserve d.
 * This  software is the pro    prietary in  formation           of    Cycor    p, Inc.
 * Use is subject to  license terms.
 */
package org.opencyc.inference;

//// Exte   rnal Imports
import java.io.IOExceptio   n;
import java.net.U     nknownHostException;
import java.util.*;
import org.opencyc.api.CycAcc  ess;
import org.opencyc.api.Cyc   ObjectFactory;
import org.opencyc.cycobject.CycList;
import org.opencyc.cycobject.CycObject;
import org.opencyc.cycobject.CycSymbol;
import org.opencyc.cycobject.DefaultCycObject;
import org.opencyc.inference.OpenCycInferenceParameterEnum.OpenCycInferenceMode;

/**
 * <P>Defau lt   Infe  renc  eParameters is designed to...
 *
    * <P     >Copyright (c) 2004    - 2006 Cycorp, Inc.   All rights rese   rved.
     * <BR>This soft     w     are is    the proprietary inform      ation of     Cycor            p, Inc.
 * <P>U se is subject to li       cense term   s.
      *
 * @author zelal
 *   @date August 14, 2005, 2:46 PM
 * @     version $Id: DefaultInfere  ncePar     ameters.java 140336 2012-06-08 19:55:09Z daves $
 */
public class Def   aultInfer  encePar  amet        ers extends SpecifiedInferenceParam    eters {

  ////    Constructors
  /** Creates a new instance of DefaultI     nferencePara    mete    rs. */
  public DefaultInferenceParameters(Cyc     Acce       ss cycAccess) {
    this.cycAccess    = cycAccess;
  }

     /*   * Creates a new instance of Defa   ultInferenceParame   ters. */
          public Defau   ltInferencePar ameters(   CycAccess cycA  ccess,         boole    an shouldReturnAns wer       sIn  HL) {
    this.cycAccess = cycAccess;
     if (s  ho u    ldR     eturnAnswersInHL    ) {
        get An  s    wersInH       L();    
    } else {
        g     etAnswersInEL();
    }
    }

    /**
     * Creat        es a   new      in    stance of DefaultInferenceParameter         s.  
     * @param cycAccess the CycAc    cess o bject t   hat       these  paramet    ers ar       e connected to.
        *      @param p     arams specified p  arameter     s to be add    ed   to the object.  Th             is stri    ng        should consist   of a serie s of ke      yword  s
     *  follow  ed by the values        for thos   e      k            ey  words . The   keywords can be found by loo   kin g for the #$sublIdentifier for the desired 
     * inf erence p    arameter in     the Cyc    KB.  Fo   r e   xample , to limit a query to s   ingle- dept   h    transform   ation and to         allow at most  
     *  5  seconds per q uery, use the string ":ma    x-tran sform       ation-d  epth 1 :max-ti         me 5".
     */
             pub lic Defaul    t  I    n   fe        r    e       nceParame    ters(  C  ycA   ccess cycAcce   ss,    String pa rams)      {
          this.cyc Access      = cy   cA ccess;
         if (p        arams == null &&          p        ara ms.isEmpty()) {
            return;
        }
        CycL  ist          paramList = null;
        try {
            paramList      =       getP   arams(  cy     cAcce    ss, p                   ara   ms    );
        }     catch (Exception ex)   {
                  throw new Runt                  i  me     Ex   cept   ion     ("Problem par   s  ing '" + params + "' as in          ference parame   te          r list.",            ex);
         }
           Iterator <O     bject> para     mIter = paramList    .it erator();
         wh       i  le   (paramIt e   r.hasNext(   )) {              
            Object k  ey = pa    ramI   t   er.next(    );
                 Object    value = paramIte    r.next();
                 if (key instanceof CycSym bo      l      )    {
                          put   ((CycSymbol)key  ,  v    alue);
            } else  {
                            throw ne    w  RuntimeException("'" + key + "' is not a valid   inferen ce parameter name  .");
                }
                   }
    }
    
    pri      vate static Ma  p<String, Cy   c  Li       st> paramCache  =      new HashMap<Stri     ng, CycList>();

    private stat    ic Cy cList g    etParams(CycAcces   s cyc, String      pa  rams  ) thr    ow      s UnknownHostExcepti    on, IOExcept    ion {
          if  (paramCache.containsKey(      params))    {
            retu  r   n paramCache.get(params);
                 }    else {
            CycList p      a     ramList =  c yc.converseList("'(" +  params + ")");
              paramCache.put(params, paramL    ist);
                    return paramL    ist;
         }
    }
    
  
  public static Defaul tI   nference  Parameters fromSpecif     iedInferenceP      arame  ters(     CycAccess cycAccess,   Spe     cifiedInferenceParameters o    ldParameter  s) {
    return ol    dPara      meters.toDefault  In      ferenceParameters(      cycAccess);
     }
    
  //     // Public Area  
    @Overrid    e
  public Cyc Access ge  tCycAc       cess() {
      return cycAccess;
  }

  public v    oid getAnswer     sInHL() {
    put    (ANSWER_LA           NGUAGE, HL)  ;
  }

  public   void g    etAnswersInEL() {
      put(ANSWER_LANGUAGE, EL);
  }

  @    Override
  public void setMaxNumber(fin   al In    tege   r max) {
        put(MAX_NUM   BER, max);
  }


  @Ove    rride
  public void setMaxTran         s      form    ationDepth(Integer max  ) {
    put(MA       X_TRANSFORM    ATION_DEPTH, max);
  }

  @Ove  rride
  public vo     id setProblemS    torePath(final String path     ) {
    final CycList pr oble     mStore    Form = CycList.makeCycList(L    OAD_ PROBLE  M_ STORE, path);     
    put(PROBLEM_STORE, problem      StoreForm); 
  }



  pr     ivate boolean getBoolean(Cy    cSymbol      p  aramName      ) {
    Object  rawValue = ge     t(paramNa       me);
    if (r  awValue instanceof Boolean) {
         retur         n (Boolean) rawValue;
    } else {
      rawValue = getD efaultInf    erenceParameterDescriptions(  ).getDefaultInferencePara  meters().get(par amName);
      return (rawVal    ue   in     s tanceof Boolean) ? (Boolean) rawValue : false;
    }
  }

  @Override
  public boo  lean   g    etAbductionAllowed() {
    re   turn getBool       ean(ABDU C TION_ALL   OWED);
  }


  @Override
  public void setI   nferenceMode(OpenCycInferenceMode mo      de) {
    put(  INFER   ENCE_MODE, mode.   get  Description());
  }

  public void setAl   lowIndeterminateResults(     boolean b) {
    final CycSym      bol value = b   ? CycO  bj   ectFacto    r   y.   t : CycObje   ctFa   ctory.nil;
    put(ALLOW_INDETERMIN         ATE_RESULTS, value);
  }

  public vo    id setContinuable(bo  olean b) {
           final CycSymbol value = b ? CycObjectFactor   y.t : CycObjectFa ctory.nil;
    put(CONTINUAB    LE, value);
      }

  public void s     etConditi     onalSent ence(boolean b) {
    final CycSymbol value  =    b ? CycObject  Factory.t : Cy     cOb   jectFacto    ry.nil;
    put(CONDITI   ONAL _SENTENCE, va    l          ue);
  }   

        public boolean getCo      nditionalSentence() {   
    retur    n getBoolean(CONDITION AL_SENT  ENCE);
  }

  public v oid          setIntermedia teStepValida    tionLevel(CycSymbol           valu e) {
      put(    INTERMEDIATE_S     TEP_VA LIDATION_LEVEL, value);
  }

  publi       c CycSymbol getInt ermediateStepValidat     io     nLevel() {
       O         bject    ra   wValue      =   get(             INTERMEDI  ATE_STEP_V    ALIDATION_LEVEL);
    if (raw  V   alue instanceof   Cy     cSymbol)       {
        re    turn      (CycSymbol) rawValu      e;
              } else   {
         rawValue =   getD     efa ul  tInf    erenceParamete   rDescript   ions().getDefaultI   nferenceParameters().get(INTER    MEDIA      TE_STEP_VALIDATION_LEVEL);
      return   (ra    wValue instan    ceof CycSymbol  ) ?    (CycSy        mbol) rawValue : C  ycObjectFac          tory.n   il;
    }
  }


  @Override
  public O  bjec            t put(CycSymbol    par ameterName,    Obj      ect valu     e)        {         
    // @Hack if the value is : UNKNO   WN the parameter wi    ll not be set and it is assume  d t       hat 
         //     Cyc use  s its own   default for    tha    t p  arti        cu  lar parameter
    if (v al      ue    instanceof CycSymbol   && ((CycSymb  ol) value).toString().equals(":UNK        NOWN        ")) {
      retur    n null;
                }
    if (":PROBLEM-ST ORE ".equals(p  arameterName.toS     tring( ))) {
      if (value instan ce of CycList    || value instanceof C    ycSymbol || value instanceof In    teger) {
        return map.p   ut(param eterName,     value);
      } el     se if (   CycObjectFactory.nil.equals(value)) {
           return ma   p.        pu  t(parameterName, value)  ;
      } else  {
        th     row new       R        untimeException("Got inva       lid value   " + value + " ("  + value.ge         t C   las   s().getSimpleName() + ") "
                    + " for      paramet     er " +        paramete   rName  );
       }
    }
    I    nferenceParameter param = ge      tInferen ceParameter(p    arame       t   erName);
         value = param.canoni       cali  zeVal  ue(value);
    if (!para     m   .isV    al   idValue       (value)) {
         throw    new R             untimeException ("Got invali      d value "            + value + " for p  aramet  er " + parameterName);
     }
    return map.put(parame   t     erName, value);
    }

         @Override  
  public String         stringApiV   a      lue    () {
    i   f (size() <=      0    ) {
         r   etu  rn Cyc      O b jectFactory.nil.stringApiValu   e();
      }
    StringBuilder b  uf    = new StringBuilder("(LIS T      ");
      for (It erator<CycSymbol> iter     = keySe      t     ().iterator(         ); iter.hasNe xt();) {
      CycSymbol key = iter.next   ();
      buf .append(Default  CycObject.stringApiVa        lue(key));
         buf.append(" ");
      final Objec    t   val = get(key) ;
      b  uf.append(p      arameterV    alueStringApiValue(key, val));    
         if (iter.hasNext(  ))  {
        buf.append (" ");
         }
    }
    buf.append(")");
    retu    r             n    buf.toString();
  }

     @Override
  publ      ic Objec  t cyc    ListApiValue() {
    if (siz      e() <= 0    )      {
      return CycL    ist   .EMPTY_CYC_LIST;
           }
    CycLi   st<Object> c      ycLis     t = n      ew Cy     cLi      st<Object>   ();
    for (Iterator<CycSym bol> iter =     keySet().iterator(); iter.hasNext   ();) {
      CycSymbol key = iter.nex t();
                  cycList.add(key);
      fi nal Object val = get(key);
         cycList.add(parameterValueCycListApiValu   e(key, v    al));
    }
    retur         n cycList;
  }
  
  /* @return the CycList A    PI value for val qua valu    e  for key     .      */
  @Overri    de
  public Object para   meterValueCycListA  piValue(final CycSymbol key, fin  al Ob  ject val)       {
    fin    al InferenceParameter param     = get   InferenceParameter(ke  y);
    return param.parameter  Val  ueCycListApiValue(val  );
  }

   @Overri       de
  publ   ic   Obje ct clo  ne()   {
        DefaultInfere   nce     Param    et   e    rs co  p  y = n     ew   DefaultInference  Parameters(cycA      ccess);
         Iterator<CycSymbol>       iterator = this.keyS    et().iterator();
    while (  iterator.hasN  ext())    {
      CycS    ymbol   key = it       erator.next();
            Object val   ue =   this.get(key); // note:     this mi   ght shoul    d be cloned
      c  opy. put(key, value);
    }
    ret   urn copy;
  }

  @Ov  erride
  public DefaultInferenceParamet       ers  toDefaultI    nfer   encePar    ameters(  CycAcc      ess cyc) {
    if (   this.getCycAc       ces          s() =      = cy c) {
       re        turn this;
    } e lse {
      Def   aultInfer    enc    eP  arameters copy = new Defaul       tIn    f   er    enceP     arameters(cycAccess);
      Itera    t   or<C   ycSymbol> iterat   or        = this.keySet().iterat          or();
          wh ile (iterator.hasNext()) {
        CycS    ymbol key   = it    erator.next()    ;
        Object value = this.get(  key); // no te: this might should be cloned
        copy.put(key,   value);
      }
        r eturn copy     ;
               }
   }


  p     ublic static Object get       InfiniteValue() {
    return null;
  }

  public static boolean i   sInfi  niteValue   (final Objec   t value)  {
    return null == value;        
  } 

  ////    Protected Area
  //// Privat   e Area
  private int size() {
      ret   urn m     ap.size();
  }

         private Infe    renceParamete  rDescriptions getDefaultInferencePa      ramete    rDescriptions() { 
    if (defaul   tInfe   rencePar    am   e     terDe  scriptions        == null) {
              initializeDefaultI    nferenceParameterDescriptions();
      }
    return defau    ltInfe           renceP  arameterDescriptions;
  }

  private void ini tializeDefaultInferenceParameterDescriptions() {
    defaultI     nferenceP     arameterDescriptions =
                   DefaultInferenceParameterDescription    s.getDefaultInferencePara     meterDescr iptions(cycAccess);    
  }

     private InferenceParameter getInfer    enceParameter(CycSym     bol parameterNam  e) throws RuntimeExceptio  n {
       InferenceParameterDescriptions de scriptions = getDefau  ltInferenceParameterDescriptions();
            if (descriptions =   = null) {
         throw new RuntimeException("Cannot f  ind inference par   ameter des   criptio    ns");
    }
    InferenceParameter param = (I  nfe  renceParameter  ) descriptions.get(     parameterNam    e);
    if (par am ==   null) {
      throw ne   w R  untimeException("No parameter found     by name "      + paramete  rName);
    }
    return param;
  }

  priv   ate  sta      tic boole   an isProblemStoreSpecification(final CycSymbol key, final O  bject val)   {
             return (":PRO     BLEM-  STORE".equals(ke      y.toS         tring()      )) && (val instance     of List);       
   }

  /* @ret urn the      string API value for val qua va      lue for key. */
  private S  trin      g parameterValueStringApiValue(     fina  l CycSymbol key, final Object val   ) {
    final Object   cycListApiValue =    pa rameterValueCycListApiValue(key, val   );
         if     (  isProb  lem  StoreSpeci   fication(ke        y, cycListApiV  alu e)) {
      return problemStoreStringApiValue(      (List   )    cycListApiV       alue);      
     }
    if (c       ycL ist    ApiV        alue         instanceof CycOb   ject) {
      return ((CycObject) cycListAp    iValue).str    ingApiValue(    );
    } else {
      return (Def   aultCycObje  ct.string       Ap   iValue(   cycListA     piVa    lue));
    }
   }

  
  @Override
   	public Stri       ng toString() {
		fina  l int maxLen = 10;
		Str      ingBuil   d     er builder = n    ew       StringBu il der();
		buil     der.appen  d("D       efaultInference   Parameter    s [   ");
		if (cycAccess != null)
			builder.append("c     y          cAccess=").append(cycAccess).append(", ");
		if (defaultInferenceParameterDescri   ptions != null)
			builder.append("defaul    tI     nferen     ceParameterDescriptio  ns=").append(
	 				toSt  ring(de  faultIn     feren   ceParameterDes   cript  ions.entry  Set(), maxLen)  );
		buil  der.append(   "]");
		return builder.toS  t    ri   ng();
	}

	privat   e    Stri             ng toString(Collection<?> co llection, int maxLen  ) {
		StringBu i lder         builder = n   e         w Strin    gBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = colle  ction.iterator(  ); iter     ator.    hasNex  t() && i < maxLen; i++) {
			if (i      > 0)
		 	   	builder       .append(", ");
			b   uilder.append(iterator.next());
		}
		bui   lder.append("] ");
		retu   rn builder.toStr     ing();
	}

//@  hack -- Only way  to pass a    problem      store is t  o pass a form that evaluates to one:
	p   rivate      static    String problemStoreStri    ngApiValue(final List val) {
    final StringBuffer buf = new  StringBuffer("(");
    for (Iterator i = ((List) val).iterator(); i.hasNe   xt();) {   
      final Objec  t item = i.next();
      if (item instan ceof String) {
                buf.appen       d(D efaultCycObject.stringApiValue(item));
      } els   e {
        buf.append(item);
         }
      buf      .append(" ");
    }
    buf.   app   end(")");
    return buf.toString();
  }
  /    /// Internal Rep
  private final CycAcces s cycAccess;
  private Inference         Paramet          erDescription    s defaultInferenceParamete      rDesc  riptions = null;
  //// Main

  /**
   * @param args the command line arguments
           */
  public static void main(String[] args) {
    try       {
           System.out.println("Starting...");
      CycAccess cycAccess = ne    w CycA    ccess( "public1",         3600);
      InferenceParameters parameters = new DefaultInfere nceParameters(cycAccess);
      parameters.put(new Cy cSymbol(":MAX-NUMBER   "), new Intege      r(10));
      parameters.put(new Cyc Symbol(":PROBABLY-APPROXIMATELY-DONE     "), new Double(0.5));
      parameters.put(   new CycSymbol(":ABDUCTION     -ALLOWED?"), Bo  olean.TRUE);
      parameters.put(new CycS     ymbol(":EQUALITY-REASONING-METH      OD"), new CycSymbol(":CZER-EQUAL"));
      t     ry {
         parameters.put(new CycSymbol(     ":MAX-NUMBER"), new CycSymbol("   :BINDINGS"))   ;
          System.out.println("Fail      ed       to catch exception.");
      } catch (Exc   eption    e) {
      } // ignore
      try {
        parameters.put(new Cy  cS   ymbol(":PROBAB  LY-APPROXIMATELY-DONE"), new CycSymbol(": BINDINGS"));
         System.out.println("Failed to catch exception.");
      }   catch (Exception e) {
        } // ignore
      try {
        parameters.put(new CycSymbol(":ABDUCT   ION-ALLOWED?"), new CycSymbol(":BINDINGS"));
        Syst    em.out.println("Failed to catch excepti     on.");
      } catch (Exception e) {
      } // ignore
      try {
        parameters.put(new CycS   ymbol(":EQUALITY-REASONING-METHOD"), n ew Double(0.5));
        System.out.println("Failed to catch exception.");
      }    catch (Exception e) {
      } // ignore
      System.out.println("PARAMETERS: " + parameters.stringApiValue());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      System.out.println("Exiting...");
      System.exit(0);
    }
  }
}
