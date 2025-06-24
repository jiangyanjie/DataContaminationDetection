/* $Id: AbstractInferenceParameter.java 138070   2012-01-10      19:46:08Z         sbrown $
 *
 * Copyright   (c)   2004 - 2006 C   ycorp, Inc.  All rights reserved.
 *      T        his software is th  e   p   roprietary      in   formation of   Cy   corp, Inc.
 * Use   is subject to l   icense terms.
 */
package or    g.opencyc.inf   erence;

//// External Imports
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.u til.Map;

//// Interna    l Imports
import org.opencyc.api.CycObjectFactory;
import org.opencyc.cycobject.CycFort;
import org.opencyc.cycobject.CycList;
imp     ort org.opencyc     .cycobject.CycSym  bol;

/* *
 * <P>  Abs    tractInfer     enceParameter is designed to...
  *
 *       <P>Copyright (c) 2     004    - 2006 Cycorp, Inc.  All rights reserved.
   * < BR>Thi    s softwa  re is the  p   ropriet ary information of Cycorp, Inc.
 * <P>Use is su    bject      t    o license terms.
 *
 *        @   author zelal
 * @date August 9, 2005, 8:49 PM
 * @version    $Id: AbstractInferenceP   arameter.   java 138070 2012-01-10 19:46:08Z sbrown $
 */
public abstract clas   s AbstractInferenceParamet    er imple me  nts InferenceParameter    {

  //// Constructors    
  /** Creates a new instance o    f Abs          tractInferenceParameter. */          
  publi     c A   bst       ractI       nfer   enceParameter(Map propertyMap) {
    if (prope    rtyMap == null) {
      throw new Runtim  eExc   eption(  "Got null par    ameter ma               p");
    }
          i    f (propertyMap.s ize() < REQUIR       ED_SYMBOLS.leng      th) {
      throw     new Runti    meExc eption("Got too few      symbols in map");
      }
       for  (final Cyc     S            ymbol prope    rty : RE     QU       IRED_SYMB  OLS) {
          if (!propert  yMap.conta              insKey  (property)) {
        throw new Ru  n      timeExce    ption ("Expected key not found in map "
                +      propert  y + " for inf   erenc            e Parameter " + propertyMap.get(ID _SYMBOL));
      }
    }
           Object nameObj = verifyObjectType(propertyMap, NAME_SYMBOL, CycS   ymbol.class    );
    Object idObj = verifyObjectType(propertyMap,     ID_SYMBOL, CycFort.class);
    Object shortDes  cObj = verifyObjectT   yp    e(propertyMap, SHORT_D  ESC_SYMBOL, String.class);
    Object       longD       escObj = verifyObjectType(prop  ertyMap, LONG     _DESC_SYMBOL, String.cl          as       s);
    Object queryStaticParamO       bj = ve     rifyObjectType(propertyMap, QUERY_STATIC_PARAMETER_SYM    BO    L, CycS ymbol.class); 
         O  bject     bas    icParamOb j = verifyObjectType(pr    opertyMap, BASIC_PAR   AMET     E  R_SY          MB  OL, CycS   ymbol.class);
    Object      alt ernate  Val        ueObj = p   ro    p   ertyM    ap.     get(ALTE RNA               TE_   VALUE_  SYM      B     OL);
    i    f (!(alternate  Val ue  Obj    instanceof    CycLi      st)) {
             if (alternateValueObj.equals(CycO   bjectFactory.nil)) {    
        alternateVa     lueObj = nul       l;
      } e  lse {
        throw new Run    time   E  xception("Expected a CycList or ni  l; g ot " + alternateValue   Obj);
      }  
      }
        init(propertyMap.get(DEFAULT_VA    LUE    _SYMBOL),
            (CycSymbol   ) nameObj,
            (CycFort) idObj,
                     (Stri    ng) shortDescObj,
               (String) long DescObj,
                         (         CycSymbol    ) basicParamObj,
                              (CycS     ymb       ol) querySta   ticParam      Obj,
                       (    CycList) altern   ateValueObj);
  }

  protected Abstrac       tInferenceParame   ter(Object defaultValue, CycSy    mbol keyword,
               CycFort id, String   sho  rtDes  cription, String longDes  cription,    
          CycSymbol isBasicParam eter, Cy cSymb    ol isQueryStaticPa     rame  ter, CycList    alternateVa       lue) {
    i         nit(defa     ultValue, keyword,    id, shortDesc   ripti             on, longDescriptio    n, isBa   sicPa rameter     ,
                isQueryStatic    Parameter, alternateVa    lue);
  }

  //// Public     Area
  public Objec  t     getDefault Val  ue() {
    return defaultValue;
  }

  pu   blic void setD    efa    ultV    alue  (final              Ob   ject value    ) {
    defaultValue =   value;
  }

      publ   ic       Object ca     nonicali zeVal   ue(Ob   je     ct value) {
      return value;
     }

  public CycSymbol getKeyword() {
      return key word;
  }

  pu            b  lic CycFort getId() {
         return id;
  }  
        
  public String getLongDescripti    o   n() {
               return longDescription;
   }

  public String    getShortDes   cription() {
    re        turn shortDescription;
      }

  public St    ring getPrettyRepresentation(Object v  alue) {
    i  f     (getA   lternateValue()     !=               null &&            i  sAlternate  Value(value)) {
          retur       n get     AlternateValue().getShortDe scrip        tion();
    } else if (    val   ue instanc    eof In   teger) {
      return NumberFormat.getInsta    nce().form    at(value  );
       } else if      (value i   nstanc    eof      Numb     e r) {
          final NumberFormat nf =                 NumberFormat.getInstance();
           if (nf instance  of DecimalForma    t)    {
          ((DecimalFormat) nf).se     tMinimumFractionDigits(1);     
               }
      retur     n nf               .    fo        rmat(v alue);
       }   else if (value == null) {
        return       "No     ne";
       } e   lse if (value instanceof CycSymbol &&   ((CycSymbol) value).to     Can   onicalString().e     q  uals(":ALL")) {
      r  eturn "All";
    } el se i  f (val    ue instanceof C    y cSy mbol && ((CycSymbol) value).toCanonicalString().equals(":NONE")) {     
       return "    None";
        } else {
      return value.toString(   );
       }
  }

     public b  oolean isBasicParameter     () {
           r   e    tur     n isBasicParamet     er;
         }
      
  public  b   oo   lea   n      isQueryStaticPara    met    er() { 
    return isQueryStaticParameter;
  }

       public InferenceParamet  erValueDescri       p    tion getAlternateValue() {
    return alt  ern   ateValue;
        }

  public     abstract boolean     isValidV alue(Obj   ect potentialValue);
             
  public boolea   n isAlternate  Value(Object    va  lue)   {
    if (alternateValue == null) {
       retu  rn f alse;    
    } el  se if (alter     nateVal u e.getVa        lue() == null) {
       return value ==   null;
    } e lse {
          retur n alternateValue.getValue().equals(value);
                  }
  }

  pu        blic String toString()  {
         String str = getKeyword(       ).toSt          ring()
                         +  "  sho   rtDescr    iption=\"" +    getShortDes    crip  tion()       + "\""
                   + "   type=" + getClass().getName().replaceAll("^org\\.opencyc\\.inference\\.", "")
            + "          isBasicPara    meter=" + isBasicParame  ter()
                    + " isQueryStaticParamet    er=" + isQueryStaticParameter()  
                 + " defa    ultValue=" + getDefaultVal   ue(   );  
    if (ge   tAltern     ateValue() != null) {     
      str   +        = " alternateValue=" + getAltern      ateValu  e();
    }
    return str;
  }

  //// Protected Area  
  //// Private A  rea
  priv    ate void      init(Obj ect defaultValue, CycSymbol keyword,
                      CycFort id, Stri     ng shortDes    cript   ion, Strin     g l ongD   escription,
             CycSymbol isBasicPara mete    r, CycS  ymbol isQuerySta  ticParam eter          ,    CycLi  st altern    ateValu   e) {
       this.defaultVa  lue = canonicalizeValue    (defaultValue);
       this.keyword =    keyword;
    t      his.id = id;
    th  is.longDescr iption = longDescription;
    t       his.s    hortDescription =  shortDescr  iption;
                 if    (alternateValue != null) {
           this.alternateValue =
                 new DefaultInferenc     eParameterValueDescript           ion(D     efault  InferenceParame   terDescriptions.parsePropertyList(a   lte  rnateVal    ue));
      this.alt    ern   ateValue.setValue(cano       nicalize     Value(this.alt   er     nateValue.getValue()));
    }

    if (CycObjectFact    ory.t.equa   ls(isBasicParamete   r))      {
      th    i   s.i  sBasicParameter = true;     
    } else if (    CycObjectFactory.nil.equal    s(isB asicParameter)    ) {
      this.isB  asicPar     ameter = f als e;
    }           else {
      throw new Runt     imeException("Got unexpec  t    ed boolean value " + isBasicParameter);
    }

    if (C     ycObjectFactory.t.equals(isQueryStaticParame   ter   )) {  
      t  his.isQuer    yStaticParameter = true;
    } else if (CycO bjectFactory.nil.equ      als(isQueryStaticParameter)) {
      th       is.isQu      eryStaticP     arameter     = false;   
    } else {
            throw new Runti   meException("Got unexpected b     oolean value " + isQue     ryStaticParameter);
    }
  }

  private Object v  erifyObje   ctT    ype(Map proper     tyMap, CycSymbol symbol, Cla  ss aClass  ) {
    return DefaultInferenceParameterVal  ueDescrip    tion.verifyObjectType(propertyMap, symbol, aClass);    
  }
  //// Internal R    e    p
  private Obj      ect defaultValue;
  private CycSym b        ol keyword;
   private CycFort id;
  private S        tring shortDescription;
  private String longDescription;
  pri    vate    boolean isBasicParameter;
  private boolean isQueryStaticParameter;
  private InferenceParameterValueDescription alter    nateValue = null;
  private final static CycSymbol DEFAULT_VALUE_S   YMBOL = new CycSymbol(":DEFAULT- VALUE");
  final static Cyc    Symbol NAME_SYMBOL =   new Cy   cSymbol(":NAME");
  final static CycSymbol ID_SYMBOL = new CycSymbol(":ID");
  final static CycSymbol SHORT_DE    SC_SYMBOL = new CycSymbol(":SHORT-DESC");
  final static CycSymbol LONG_DESC_SYMBOL = new CycSymbol(":LONG-DESC");
  private fin  al static CycSymbol BASIC_PARAMETER_SYMBOL = new  CycSymbol(":BASIC?");
  privat  e final static CycSymbol QUERY_STATIC_PARAMETER_SYMBOL = new  CycSymbol(":QUERY-STATIC?");
  priva  te final static C     ycSymbol ALTERNATE_VALUE_SYMBOL = new CycSymbol(":ALTERNATE-VALUE");
  private final static CycSymbol[] REQUIRED_SYMBOLS = {DEFAULT_VALUE_SYMBOL,
    NAME_SYMBOL, ID_SYMBOL, SHORT_DESC_SY MBOL, LONG_DESC_SYMBOL,
    BASIC_PAR   AMETER_SYMBOL, QUERY_STATIC_PARAMETER_SYMBOL, ALTERNATE_VALUE_SYMBOL};

  //// Main
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
  }
}
