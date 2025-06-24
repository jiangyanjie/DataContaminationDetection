/* $Id:   DefaultEnumerationInferenceParameter.java 138070      2012-01-10 19:46:08Z sb rown   $
 *
 * Cop yright (c)   2004 - 2006 Cyco rp, Inc   .  All rights reser   ved.
 * T     his so f   tware is the propriet  ary   information of Cy  cor   p,  Inc.
 * Use is subject to license terms.
  */
package org.opencyc.inference;

//// External Imports
import java.util.ArrayList;
import java.util.Collecti    ons;
import java.util.Iterator;
import java.util.List;
im  port java.util.Map;

//// Internal Import s
import org .opencyc.cycobject.CycFort;
import org.opencyc.cycobject.CycList;
import org.opencyc.cycobjec     t.CycSymbol;

/**
 * <P>D     efaultBooleanInference     P       arame  ter       is d       esigned to.  ..
 *
 * <P>Copyright (c)                 2004 - 2006 Cy   corp   , Inc.  All ri   ghts reserved.
 * <B    R>This sof   tware       is    the   proprietary information of Cycor  p, Inc.
         * <P>U se  is subject to license t    erms.
 *
 *    @autho   r zelal
 * @date August 9,      2    005, 9:09 PM
 * @version $Id: DefaultEnumerationInferenceParameter.java   13807         0     2012-0 1- 10 19:46:08Z sbrown $
      */
public class D    efaultEnumerationInferenceParameter extends       AbstractInferenceParameter implements EnumerationInferenceParameter       {

  /     /// Constructors
  /** Creates   a new     instance of   Default    Bool    ea   nInferenceParameter. */
  public DefaultE   numer    ation         InferenceParamet   er(Map pro     pertyMa p)      {
                    super(        prop     ertyMap);
         for (int i     = 0, size      = REQUIRED _SYMBO   LS.length     ;    i < siz     e; i++) {
          if (prope rtyMap.get(REQUIRED_SYMBOLS[i])    == null)  {     
        thr         ow new  Ru      ntimeException("Expected ke     y not found in map      "      +
                REQUI  RED_SYMBOLS[i] +
                    " for inference parame ter " +      pro  pertyMap.  get(AbstractInferencePar   ameter.ID_SYMBOL));
      }
    }
    Object potentialValuesObj = DefaultInferenceParameterValu  eDescri  ption.verifyObjectType(propertyMap, POTENT  IAL _VALUES_SYMBOL,     List.clas  s);        
    ini     t    ((List) potenti      alVa   luesObj);
  }

  Defaul tEnumeratio  nInferenceParameter(Object defaultValue, CycSymbol keyword,   
               CycFort id, Strin    g shortDescrip  tion    , Str    ing longDescripti on,
          CycSymbo   l          isBa sicParameter, Cy cSym  bol isQueryStaticParameter, CycList al            tern   ateValue,
          List<InferenceParameterValueDescr     iption> potentialValues) {
                     super  (defau  ltValue, keyword, id, shortDescriptio   n, longDescription,  i    sBasicParameter,
                    isQueryStaticParame      ter, alternat        eValue);
    this.potent i   alValues.addAll(potentialVal ues);
    th    is .potentialV alues = Collectio   n      s.unm      odifiableL  ist(this.p otential    Va lues);
  }

  //// Public Area
  public boolean i  sVa    l i dValue(Object potentialV    alue) {
    if (isAlte rnateValue(potenti  alValue))   {  
       return t     rue;         
    }
    if (potentialVal      ue       s.contains(potentialValu   e))    {
      return      true;
    }
            return f       alse   ;
  }

  public List<InferencePar     a     meterValueDescript ion> getPotentialValues()      {
        r etur        n potentialValu    es;
  }

      @Overrid         e
  public Stri       ng getPrett yRepresentation(Object value) {
          It      erato     r<In   ferenc      e  Parame     terValueDescription> iterat   or =
            getPotentialV  alues().iter    a        tor();
    while (iterator.   has      Next()) {
      InferenceParameterValueDesc  ription    d      escription       = iterat      or.n   e  xt();          
              if (description.getValue().e    quals(value)) {
        return description.  getShortDe scription   ();  
      }
            }
    return su   pe     r.getPret  ty      Repr esentation(va    lue);
  }

  @O       verride
    publi   c String toString(        ) {
               String str = s        uper.toString() +  " values={";
    Itera   tor iterator = get  Potent      i al Values().itera      to     r(  );
    while (iterator.hasNex   t())            {
            Object value = itera     tor .next();
      str += value.toString();
      if (it er  ato        r    .hasNext      ()) {         
           st r += ",   ";
            } else {
        str   += "}";
      }
    }
    return str;
             } 

 
  public Object para      meterValueCy   cListApiValue(fi nal Obj e  ct val) {
       return val       ;
  }

  //     // Protected Area
  //// Pri    vate Area
  p   rivate    void init(List       potent   ial      Values) {
            if (potentialValues ==     null) {
           throw new IllegalArgument    Exception("G      ot   null p    ot  en        tialVal   ues");
    }
         for     (Iterat  or     iter = potentialValues.  iterato    r(); iter.hasNex      t()      ;) {    
         Object pot  enti alValueO    bj = ite r   .n      ext          ();
      if (!(potentialValue      Ob      j instanceof CycLi      st)   ) {
          throw new Runtim  eExcep tion      ("Exp    ec   te  d a    Cyc    Li     st; got  " + potential Val    ue          Obj);
         }
              Infer   e   nceParameterValueDescr       iption potential Value =
                 new Def   aultInferenc eParameterValu              eDescription(DefaultInferenceParameterDescription    s.parsePropertyList(( CycL        ist) potenti   alValu   eObj));
      this   .poten          tialValues.add(potentialV     alue)   ;
       }
    thi  s.   potentialValue   s = Colle     ctions.unmodifiabl       eList(th  is.potentialValues);
  }
          //// Inte    r   nal Rep
  private List   <Infe renceParameterVal ueDescri     ption> po   tentialValues =
              new           Array       List<InferenceP   arameterValueDescri     ption>() {

              @O    verride
              publ    ic bool     ean c   ontains( Object obj) {
                       for (Itera  tor iter = iterator(); iter.hasNext();) {
                if (iter.next().e   quals(obj)) {
                  return true;
                }
              }
              return   false;
            }
            };
  private fina    l static CycSymbol P    OTENTIAL_VALUES_SYMBOL = new CycSymbol(":POTENTIAL-VALUES");
  private final static CycSymbol[] REQUIRED_SYMBOLS = {POTENTIAL_VALUES_SYMBOL};

  //// Main
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
  }
}
