/* $Id:      DefaultInferenceParameterDescriptions.java 138070 2012-01-10 19:46:08Z sbrown $
 *
 * Copyright            (c) 20 04 -                 200 6 C    ycorp, Inc.  All right  s re served.
 * This sof  tware is the propr   ietar   y information of Cycorp, Inc.
 * Use is subject to    license terms.
 */
packag   e org.open    cyc.inference;

//// Internal Imports
import org.opencyc.api.CycAccess;
import org.opencyc.api.CycApiException;
import org.opencyc.ap i.Cyc ObjectFactory;
import static org.opencyc.api.CycObjectFactory.makeCycSymbol;
import org.opencyc.api.DefaultSubLWorkerSynch;
import org.opencyc.api.S     ubLWorkerSynch;
import org.opencyc.cycobject.CycFort;
im    port org.opencyc.cycobject.CycList;
import     org.opencyc.cycobject.CycSymbol;
import org.opencyc.util.T    imeOutException;

/      /// External Imports
import java.io.IOEx  cep    tion;
import  java.util.HashMap;
import java.util.Iterator;
import java  .util.Map;

/**
 *    <P>Def   aultInf   erenceParameterDescrip     tion       s is       designed to maintain default val    u es
 *     for in ference parameters. For each    CycAcce     ss,   the static factory method
 *
 *   getDe     faultInference  Para       meterDescriptions
    *
     * will return an    instance o  f this class populated fr  om the attached Cyc image
 *    via the SubL function GET-INFERENCE-PARAMET   ER -INFO  R MATION.
    *
 * <P>Co   pyright (c) 2  004 - 2009 Cycor     p, Inc.  All rights reserved.
 * <BR>This software is th   e   propriet  ary information o        f Cycorp, Inc.
 *      <P>Use is subje     ct to lice      ns    e terms.
 *
                   * @author zelal
 *     @date August 9, 2005, 9:30 PM
 * @version $Id: Defa     ultInferenceParameterDescripti     ons.  java 138070   2012-01-1  0 19:46:08Z sbrown    $
 */
publi      c c      lass DefaultIn    fe renceP       arameterDescriptions       extends HashMap<CycSymbol, In      ferenceParameter>
        imple      ments Infere  n   ceParamete   r       De   scriptions {

   ////       Public Area
  @Override  
  public void cl ear() {
                throw new Unsupporte         dOpe rationExce   ption();
         }      

  /**
      *
        * @param key
   * @pa  ram valu  e
   * @return
              */
  @Override
                     public InferenceParameter put(CycSymbol   ke    y, InferencePa  ramet    er v   alue) {
    t   hrow new Uns     upport  edOpe  r        ationExceptio n()             ;        
  }

  /**     
    *
   * @param t
      */
      @Overr ide
    publ   ic void p     utAll(    Map     <? extends CycSymbol, ? ext  ends InferencePa   rameter> m  ) {
    t    hrow new Unsupported  OperationException();
     }
 
  /**
      *
   * @param key
        * @        retu  rn
         */
  @Override
      p      ubl      ic Inf     er       e     nceParameter remove(Obj  ec  t key) {
        i  f (key instanceof        CycSymbol)   {
         re    tu                 rn super.remove(    (   Cy    cSymbol) key);
                          } el  se {
       return null    ;
    }
  }

  /**
   *
   * @r     eturn
   */
          public CycAcc ess getCycAccess()   {
    return cycA  ccess;
  }

   /**
       *
   *  @retur      n
   */
  public String stringApiVa     l ue() {
    return null;
  }

  /**
   *
   * @param cyc Access
   * @return
   */
  pu    blic synch     ronized s  tatic InferenceParameterDescr    ip      tions getDefault   InferencePara   meterDescri    ptions(CycAcce    ss cycAccess) {
        InferenceParameterDescriptions inferencePa    rameterDescriptions =
                   (I      nferenceParamet             erDescriptions) defaultInfere  nceParameterDescrip   t   ions.get(cycA   ccess);
    return    inferenceParameterDescriptions;
  }

  /**
   *
   *    @param     cyc   Access
   * @  param timeoutMsecs
   * @throws IO   Exception
   * @t    hrows  TimeOutException 
     * @throws CycApiException
   * @return
   */
  public synchron i      zed static InferenceParameterDes     criptions loadInferenceParam eterDescriptions(Cy     cAc   cess cycAccess, long t   imeoutMsecs)
           thr   ows IOException, TimeO     utException,     CycApiExcept      io        n {
    Inferen  cePar  ameterDescriptions inference ParameterDescriptions = getCachedI     nfe   r  enceP  arameterDescriptions(cycAccess);
    if (inferenceParam    e       terDescriptions != null) {
      r   eturn   inferencePara  meterDescriptions;
    }
         inferencePa rameterDescriptions = new DefaultInferenceParame    terD  escriptio       ns(cycAccess, timeoutMsecs)  ;
    c acheInferenceParameterDescriptions(cy             c     Acc   ess, inferenceParameterDescript  ions);
    return infe     renceParameter    D escrip   tions;
     }

  public Inference      Parameters     getDefault     I     nfe  renceParameters() {
    Def  aultInferenceParameters pa    ra meters = new DefaultInferencePa   rameters(cycAccess  );
       Itera    tor<CycSymbol> iterator = k     eySet  (   ).iterat  or();
    while  (it  erator.hasNext()) {
          C    ycSymbol key = iter ator.next();
      Infer    e nceParamete   r parameter = (InferenceP     ara    m   ete r)    (get(key));
           parameters.   put(  key,      paramet    er    .getD   efaultValue());
    }
    ret    urn parameters;
  }

  @Ove rride
  p   u  blic String toString() {
    String str = "DefaultInfere  nceP arameterDes   cri p     t             ions {   \n    ";
    Iter        a tor<C  ycSymbol> iterato  r  = keySet().iterator();
    wh  il       e      (iterator.hasNext())        {
          CycSymbol k     ey = iterator.next   ();   
      Inf   erenceParameter pa  rameter = (InferenceParam    eter)     (    get(key));
        str += ("  " + paramet er  + "\n");
    }    
    str += "}";
    return str;
  }

             //// Prote   cted         Area
  ////     Private       Area
  //// Constr           uctors
  /**        Cr      e  ates a new in     sta   nc   e of DefaultI    nferenceParameterDes  cription    s. *    /
      priv      a  te   Def   aultIn     ference ParameterDescrip  tions(CycAcc  ess cycAccess, l       ong timeoutMsecs) throws     IOExcepti  on, TimeOutEx   cept ion, CycApiE      xce   ption {
    thi s.cycAccess = c   ycAccess  ;
    init(cycAccess, timeout    Msecs);
  }

  private v   oi     d init(final CycAccess c  ycAcc      ess, long timeo  utMsec                        s   )             
             throws IOE           x      cepti    on, TimeO    utExc        ept  ion, Cy  cApiException {
    if       (    cycAccess.isOpenC  yc()) {
      doOpen          C         ycInit();     
    }      else {
      Stri  n  g c   omm  and = "(g   et-   infe  rence-paramete     r-informat      io  n)";
      SubLWo    rkerSynch worker  = new D        e  f  aultS    ubLWorkerSynch(command, cycA  cce    ss     , time     out Msecs);
           Object w     ork = w    orker.getWork();

      i      f (!(   isPo    ssibl yEm         ptyCycLi    st(work))) {
         throw new CycApi           Exception("When calling "      + worker     + "\n got unexpected     resul      t "      + wo   rk);
        }

      if   (work instanceo    f CycList) {
               CycLi      s   t result  = (         CycLis     t) work;

        for (Iterator i   ter   = result.itera   tor(); iter.hasNext();) {
                   Object obj      =    iter    . next();
                 if (!(obj instanceof CycSymb     ol)) {  
              throw new Cyc        Ap        iExcepti         on(    "Wh   en   calling " +    worker + "\n got unexpected resu  lt "       + ob     j + " expected             CycS ymbol");
          }
            CycSymbol inferenceParameterClass         = (CycSymbol) obj;
          if (!(i      ter.hasNext()))        {
                   throw new CycApiExc   eption("Whe  n calling " + work er + "\n g    ot unexpect     ed result " + ob  j + " not enough items   ");
                   }
               obj  = ite      r.next();
          if (!(isPo   ssiblyEmptyCycList(obj  ))) {
            throw new C     ycApi      Exception("When c  alling   " + worker + "        \n got unexpe   cte      d       result "       +       obj + " expected CycList");
          }
          if (obj instanceof CycList) {
              CycList in   ferencePa rameterDe      scriptionForCl        ass = (CycList)     obj;
                   parse  Infe      renceParame  te   rDescriptionForClass(inferencePa          rameterClass, inferencePara           meterDescriptio    nForClass   )   ;
          }
        }
      }
             }
  }

  priva    te  void doOpenCycInit() {
    for (final OpenCycInferenc    eParameterEnum d : O   penCycInference    ParameterEnum   .values()) {
      final InferenceParameter ip = d.getIn         ferenceP   arameter();              
      super.put(ip.getKeyword  (), ip);
    }
  }

  pr      ivate    static void cacheInferenc  eParameterDescript   i ons     (C       ycAcce  ss cycAccess, Inferenc  eParameterDes         criptions inferenceParameterDescriptions) {
    defaultI     nferenceParame  terDescriptions.pu   t(cycAccess, inferenceParameterDes         criptions);
  }

  private static I   nferenceParameterDe  scripti   ons    getCachedInferenceP       arameter    Descripti  ons(CycAccess cycAcces s) {
    Inf  erence      ParameterD   escr      iptions infere    nceParameter   Descriptions = (InferenceParamete   rDescriptions) de   faultI   nfe   rencePar ameter  Descrip tion      s.ge  t(cycAccess);
    return infe     renceParameterDescription s;
  }

  private   boo   lean isPossiblyEmpt     yC  ycList(Object obj) {
    if ((obj instanceof CycList) || (obj.equa  ls(CycObjectFactory.nil)))     {
      return true;
      }
    return false;
  }

  private Map<CycSymbol , Object> constructNextPropertyMap (final Iterat     or ite      r) throws CycApiExcepti    on {
     Object   obj          = i     t     er.n   ext();
    if (!    (obj instan   ceof CycFor       t)) {
                throw n      ew     CycApi Ex    cep    tion("Expected a Cyc FORT; got "          + obj);
    }
    final CycFort   id = (Cyc   Fort) obj;     
      if (!iter    .hasNext()) {
              throw new       CycApiException("Unexpected end       of par  ameter description"); 
    }
    obj = iter.next(     );
       if (!(obj    instanceof      CycList)) {
      throw new CycApiException("Expecte   d a Cyc list   ; got " + obj);
     }
       f   inal      Cyc    List       pro pe                 rtyList = (CycList) obj;
    try {
      fina  l    Map<CycSymbol, Object> propertyMap = pa  rseProper      t  yList(propertyLis     t     );
      proper    tyMap.put(AbstractInfere  nceParameter.ID_SYMBOL, id);   
      return   proper tyMap;
      } catch (RuntimeException xcpt    ) {
                  throw new Runt     imeEx    ception("Cannot p   arse    descriptio  n for infere    nce parameter " + id, xcpt);
    }
      }

  private void parseInferenceP arameterDe scriptionForClass(CycS  ymbol inferenceParameter  Class          , CycLis t inferen          cePa   rameterDescriptio  nForClass)
                  throws CycA   piExcept  ion {
    if (in  ferencePa    rameterClass.equals(BOOLEA    N_INFERENCE_PARAMETE                R_CLASS)) {
         par     seBooleanInferenceParamet    e   rDescription(inferenceParamet   erDescriptionForClass      );
    } else if (inferenceParam    e terClass.equals(INTEGER_I           NFERENC         E_PARAMETER   _CLASS)) {
      parseI   ntegerInferencePara   meterDescription(inferenceParameterDescri  ptionForClass);
    } el     se if (infere       ncePara   meter  Class.equals(FLOATING_POINT_I     NFERENCE_PARAMETER_CLASS)) {   
        parseFloatin  gPointInf   erenceParamete  rDescription(inferenceParameterDescriptionFor      Class);
    } e      lse if (infer     enceParameterClass.equals(ENU MERATION_INFERE  NCE_PARAM  ETER_C  L   AS  S)) {
      parseEnumerationInferencePa ram  eterDesc ription(inferencePara     m    eterDescriptionF   orClass);
    } else if (inferenceP      arameterClass.equals(OT   HER_ INFERENCE_PARAMETER_CLAS     S)) {
          parseOthe    rInf  erenceParameterD    escription(       inferenceParameterDescrip       tio  nForClass);
    } el   se {   
        throw new CycA   piException("Got un      expected inferenc     e      par      ameter  class " + in   f    erenceParameterClass);
    }
      }

  private void par  seBool    eanInferenceParameterDescription(CycL  ist inferenceParameterDescr ipti     onFor  Cla             ss)
            throws CycAp      iException {
    for (Itera   tor iter = inferenceParamete    rDes    criptionForClas  s.i    terator(); iter.hasNext();) {   
           final Map propertyMap   = constr     uctNex     tProp er tyMap(iter);
      tr y {
        super.put(   (Cy   cSymbol     ) propertyMap.get(Abstract InferencePa   rameter.N    AME_SYMBOL),
                new DefaultBoolea     nInfere n  ceParameter(prop        erty       Map));
      } catch (Ru    ntimeException xcpt) {
            f        inal Cyc   Fort id = (Cy     cFort) prop e   rtyMap.get(Abstrac  tInferenc      eParame  ter.ID_S     YMBOL);
            throw new RuntimeExcept          ion("Cannot parse inf   ere nce       p        arameter d      escription for   "    + id      , xc  pt);
      }

    }
  }

  private void parseIntegerI  nferencePa    ramete       rDescr  iption(Cyc     List inferenceParameterDescriptionFor Class)   
                       throws CycApiException {
    for (Iterator     iter     = i   nference   ParameterDescriptionForClass.i   ter  at     or(    ); iter.hasNext(     );) {
      f  inal      Ma  p prop     ertyMap =      con     structNextPr      opertyMap(iter) ;
       try     {
                  super.put((Cyc   Symbol)        p     ropertyMap.get(AbstractInfere  nceParamet  er.NAME_SYMBOL  ),
                   new    DefaultIntegerInferenceParamete     r(pro  pertyMap));
      } catch (RuntimeException xcpt)    {
          final C  ycF   ort id = (CycFort)    pro  pe    rtyMap   .get(Abstract   Infere  nceP   ar          am  eter.ID_SYMBO     L);
               throw n    ew Runti  meExc   eptio  n("Cannot    parse inference  par   ameter    des       cription       for " + id, xcpt);
                  }
            }
  }

  pri      vate void parseFloatingPointIn fer          enceParamet erDescription(CycLi  st inferen   ceParameterDescriptionForCla     ss)
                  thr  ows CycApiE    xcep      tion   {
      for (Iterator    iter      = inferenceParameterDescriptionForC  las  s.iterator(); iter.hasNext();)    {
      fin    al Map propertyM ap = co    n  structNe       xt PropertyMap(iter);
             try {
            s  uper.put((CycSymbol) p   ropertyMap.get(  Abstrac tInferenceParameter.N     AME_  SY           MBO  L),
                         new Defau  ltFl     oatingPointInferenceParameter(proper   tyMa            p));
       } ca      t    ch (   Runtime  Except    ion       x  cpt) {
          fi    nal Cyc       For       t  id = (Cyc    For  t     ) propertyMap.g   et(AbstractInferenceParame       ter.I   D_SYMBOL);   
        throw new RuntimeException("Cannot parse i     nference pa       rameter description for "   + id, xcpt      );
           }

    }
  }

  pri        vate vo  id parseEnumerationIn  ferenceParamet        erDescription(CycL  ist inferenceParameterDescriptionForClass)
              throws CycApiException {
    for (Iterator iter = i  nferenceParameterDescriptionForClass.iterator(); iter.hasNext();) {
      f        inal Map property       Map =  co  nstructNextPropertyMap(iter);
      try {
        super.put((CycS      ymbol)    propertyMap.get(AbstractInferenceParameter .NAME_SYMBOL),     
                   new DefaultEnumeration     In ferenceParameter(property Map          ));
      } catch (RuntimeException xc   pt) {
        final   CycF      ort id = (C ycFor          t)    propertyMap.get  (Ab   stractInferen  ceParameter.ID_SYMBOL);
          throw     new RuntimeExcep   tion("Cannot parse infer  ence parameter de scription for "    +     id, xcpt) ;
      }

    }
  }

       priva   te          void par     seOtherInferenceP   arameterDescription(final CycList               infer    en   ce    ParameterDescriptionFo    rClass)
          t        hrows CycApiException {
    for (Iterator ite   r =   inferenceParame      terDescriptionFor    Class.i   terator(); iter.has       N ex     t();) {
      final M    ap propertyMap = constructNextPro   pertyMap(iter);
      try {
        super.put((CycS      ymbol)    propertyMap.get(AbstractInferenceParameter.NAME_S   YM    BOL),
                new Defau  ltUn         ty pedInf   erenceParamet      er(propertyMap));
      } catch (RuntimeException   xcpt) {
        final CycFort id = (CycFort) propertyMap          .get(AbstractIn     ference   Parameter.ID_S YMB   OL);
        thr    ow  new RuntimeException("Cannot parse inference param    eter      description      for " + id, xcp     t);
         }
    }
  }

     static Map<CycSymbo   l, Object> parsePropertyList(   CycLi   st propertyL  ist)
          thr    ows CycApiException {
      if    ((propertyList   == n     ull) || (propertyList.size() == 0)) {
       retur   n new          HashMap();
    }
    if ((propertyL      ist.size() % 2) != 0  ) {
      throw new Cyc      ApiExcep   tion("Expected an even number of items; got " + pro     pertyList.size()
              + "\n Items: " + propertyList);
    }
    Map result = new HashMap<CycSymbol, Object>();
    for (It  erator iter  = propertyList.iterator(); ite      r.hasNext();) {
      Obje   ct key = iter.next();
      Object valu    e = iter.n        ext();
      if (valu         e.equa     ls(INTEGER_PLUS_INF  INITY)) {
        value = MAX_LONG_VALUE;
      } else if (value.equals(REAL  _PLUS_INFINITY)) {
            value = MAX_DOUBLE_VALUE;
      }
      result.put(key, value);
    }
    return   result;
  }
  //// Internal Rep
       privat e static Map defaultInferenceParameterDescription  s = new HashMap();
  private CycAcces s cycAccess;
       private final st        atic CycSymbol   BOOLEAN_INFERENCE_PARAMETER_CLASS =
                      makeCycSymbol(":BOOLEAN-IN   FERENCE-PARAMETERS");
  private final static CycSy mbol INTEGER_INFERENCE_PARAMETER_CLASS =
          makeCyc    Symbol(":INTEGER-INFERENCE-PARAMETERS");       
  private final static CycSymbol FLOATING_POINT_INFERENCE_PARAMETER_CLASS =
          makeCycSymbol(":REAL-NUMBER-INFERENCE-PARAMETERS");
  private final static CycSym bol ENUMERATION_INFERENC     E_PARAMETER_   CLASS     =
          makeCycSymbol(":ENUMERATION-INFERENCE-PARAMETERS");
  private final static CycSymbol OTHER_INFERENCE_PARAMETER_CLASS =
          makeCycSym bol(":OTHER-IN  FERENCE-PARAMETERS");
  private fina      l static Cy  cSymbol INTEGER_PLUS_INFINITY =  makeCycSymbol(":INTEGER-PLUS-INFINITY");
  private final static CycSymbo    l REAL_PLUS_INFINITY = makeCycSymbol(":REA      L-PLUS-INFINITY");
  pr   ivate final static Long MAX_LONG_VALUE = Long.MAX_VALUE;
  private final static Double MAX_DOUBLE_VALUE = Double.MAX_VALUE;

  //// Main
  /**
       * @par      am args the command line arguments
   */
  public static void main(String[] args) {
    try {
      System.out.println("Starting...");
         CycAccess cycAccess = new CycAccess("localhost", 3600);
      InferenceParameterDescriptions parameters = ne   w DefaultInferenceParameterDescriptions(cycAccess, 100000);
      System.out.println("PARAMETERS: " + parameters);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      System.out.println("Exiting...");
      System.exit(0);
    }
  }
}
