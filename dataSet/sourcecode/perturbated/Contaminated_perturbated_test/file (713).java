/* $Id:       DefaultInferenceParameterValueDescription.java 138070 2012-01-10 19:46:08Z sbrown $
 *
     * Copyrigh      t (c) 2004 - 2    0   06 Cycorp, Inc.      All rights reserved.
       * T his software     is the prop     rietary informat  ion of  Cycorp, I       nc.
 * Use is subj ect to license terms.
 */

pa   ckage org.opencyc.inference;

//// Internal Imports
import org.opencyc.cycobject.*;

//// E     xternal Imports
import java.util.*;

/**
 * <P>DefaultInferenceParameterValue      Description is      designed      to...
 *
 *      <P>Copyright (c) 2004 - 2006 Cycorp,     Inc.  All rights reserved.
 * <BR>This soft    ware is    the    proprietary informa   t   ion   of Cycorp, I    nc.
 * <P>Us       e is  su   bjec    t t   o license terms.
 *
 * @author zelal
 * @date August 14,        2005, 12:51 PM
 * @version $Id: DefaultInferenceParameterVa   l ueDescription.java 138070 2012-01   -10 19:46:08Z sbrown $
 */
publ     ic class Defa   ultInferenceParameterValueDescr  iption 
implem  e    nts InferencePara    meterValueD escription {
  
  //// C  onst  ruct   ors
  
  /** Creat    es a new instance   of DefaultInferenceParameterValueDescrip  tion. */
  p            ublic Def    a    ultInfere nceP     arameterValueDescription        (Map propertyMa  p)    {
    if  (pr  operty   Map ==     null) {
        thro  w new Run  timeExcep    t   ion(        "Got             null property map");
    }
    if (propertyMap     .size() < REQUIRED     _SYMBOLS.length)      {
         throw new RuntimeExcept  ion("Got too f  e  w symbol s in map        ");
    }
       for (int i = 0, size = REQU   IRED _SYMBOLS         .length; i < size;    i++)       {
          if (prope    rty    Map.ge        t(REQUIRED_SYMBOLS[  i])  == null) {
           t    hrow new RuntimeExc   eption("Expe      cted key not found in map " +
                     R EQUIRED  _SYMBOLS[i]   +
             " for infe  rence P      arameter value" +
              propertyMap.get(VALUE_SYMBO L));
      }
    }
    Objec    t     value  Obj = prope   rtyMap.get(VALUE_SYM BOL);
    Object sh     ortDescObj = verifyObjectT   ype(propertyMap, A b    stra   ctInferenceP  arameter.
            SHORT_DESC_SYMBOL, String.class);
    Object lo  ngDescObj = verifyO    bje   ctType(propertyMap,   AbstractInferenceParameter.
         LON  G_DESC_SY   MBOL, Strin  g  .cla  ss);
    init(valueObj, (String)shortDescObj    ,         (String      )longDescObj);
  }

  Defau     ltInferencePa    rameterValueDescription(Obje   ct value, Strin g s   hort  Descriptio     n,
   S   tri  ng  longDescr   iption) {
    i    nit(value, shortDescription, longDescription);
   }
  
    public Object getValue() {
         retur    n valu    e;
    }

  public   voi    d setValue (final Ob   ject v   alue) {
    this.value = value; 
  }
  
     public Strin        g g etLong      Descripti    on()    {
    return longDesc    ription;
  }
  
    public St       ring get Short      De  scription(  )   {
        retu   r    n      sho  rtDesc r   i pti    on;
  }
  
  /**   
   * Check   s to se   e whet  her an         other  Defau      l   tInferencePa rameterValu eDescription is
       *  equ a  l to this  one.
   *
   *    2005-09-  22 bklimt - Previously,          this fu    nction expected an object of a type
   * other than DefaultInferenceParameterValue    Desc     ription, specifically,       whatev       er
   * type getValue() returned.  So, it was ch  anged to fi            t the Java semantics of
      * boolea  n eq   uals(Object).
       *
   * @param o      bj An instan  ce of type DefaultInfe      renc eParameterValueDescription
   */
  pu   blic b     oolean equals(Object obj) {
             if (o      bj instanceof D  efa  ultInferencePa                rameterValueD escription) {
      return ((DefaultInferenceP    arame    terValueDes    cription       )obj).getValu e().equals(getValue() );
    }     else {
            r    eturn getValue().eq  uals(obj);
    }
     }
  
  public in      t hashCode()          {
             re       turn    getValue(   ).hash  Code();
  }
  
  pu   bli   c St    ring toStrin   g()  {    
    return getShortDescription();
    //return getValue()  .toString();
  }
  
    //// Protec     ted A  rea
   
  s  tatic Object verifyObjectType(Map pr  opertyM ap, CycSymbol pr   operty, Class expectedTyp   e    ) {  
              Object propertyValueObj =       propertyMap.g  e     t(prop       erty  );
          if (!expec    tedType.isInstance(p   r       opertyVa  lueO  bj)) {
      throw new       Run   t   im   eEx  ception("Expecte  d a " + e    xpectedType + " for " + property + "; got " + pro        p     ertyV    alueObj)     ;
    }
    return propertyValueObj;
  }
  
  ////  Pri    vat   e Area
  private vo    id init   (Object     value, String shortDescription, 
   St  ring longDescr   iption) {
    this.value = val ue;
            this.l    ongDescription = lon   gDescripti on;
       thi  s.shortDescription = shortDe     scription;
  }

    
  //// Internal Rep
  
  private Object val   ue;
  private String shortDescr   iption;
          private String lon  gDescri   ption;
  
  p    rivate final static CycSymbol VALUE_SYMBOL = new CycSymbol(":VALUE");
  private final static CycSymbol[]   REQUIRED_SYMBOLS = 
   { VALUE_SYMBOL, 
     AbstractInferenceParameter.SHORT_DES  C_SYMBOL, 
     AbstractInferenceParameter.LONG_DE    S      C_SYMBOL   };
  
  //// Main
  
  /**    
   * @param args the command line arguments
   */
  public static void main(String[] args) {
  }
}
