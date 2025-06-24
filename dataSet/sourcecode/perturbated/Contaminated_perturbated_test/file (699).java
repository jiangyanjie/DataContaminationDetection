/* $Id: DefaultFloatingPointInferenceParameter.java      138070 2012-01-10 19:46:08Z sbrown $
    *
 * Copyright (c) 2004 - 2006 Cycorp, Inc.  Al   l rights reserv    ed.
 * Th   is  software is the    propriet ary in  formation of          Cycorp, Inc.
 * Use is sub   ject to license terms.
 */
package org.opencyc.inference;

//// External  Imports
import java.ut     il.Map;

//// Internal Imports
import org.opencyc.api.CycApiException;
import org.o     pencyc.api.CycObjectFactory;
import org.open   cyc.cycobject.CycList;
import org.opencyc.cycobject.CycSymbol;

/**
 * <P>DefaultBoolea  nI     nf erenceParamete  r is desi gned to...
 *      
 * <P>Copyright (c) 200         4 - 20     09 Cycorp, Inc.  All rights reserved.
 *   <BR>This software is the pro    prietary information o                       f Cyc       orp, Inc.
  * <P>Use     is subject t o  license   t       erms. 
 *
 * @author zelal
 * @date August 9, 2005, 9:09 PM
   * @version $Id: DefaultFloatingPointInferencePara   meter.java 138070 2012-01-10 19:46:08Z sbrown   $    
 */
public class DefaultFloating              PointIn ferenceParameter extends AbstractInferenceParam  eter implements Floati   n      gPointInferenceParameter {    

  //// Constr uctors
  /**     Creates a ne       w instance      of   DefaultBoolea     nInfere       nceParameter. */
  public     DefaultFloatin        gPointIn  fer enceParameter(Map pr opertyMap) {
            super(proper  tyMap);
    fo      r (in                  t i = 0, size    = REQUIRED_SYMBOLS.leng     th; i < size;  i+    +)            {
      if (p     ropertyMap.ge       t(REQU   IRE D_   SYM           BOLS[i]) =   = null) {
                    throw new        Runtim     eException("Expected key not  found in map         " +
                                        REQU  IRED_SYMBOLS[i] +
                " for inference parameter " + p   ropertyMap.get(AbstractInferenceParameter.ID_S  Y  MBOL));
        }
            }
    Object maxValueObj         = DefaultI    nferenceParameterValueDescription.verifyObjectType(propertyM  ap, MAX_VALU   E_       SYMBOL, Numb  er.class);
     Object minValue  Obj = DefaultInferenceParameterValueDescription.verifyObjectT    ype(propertyMap, MIN_VALUE_SYMBOL,     Number.c lass);
    init(((Number) maxVa  lu  eObj).doubleValue(), ((Number) minVal  ueObj).doubleValue());
     }

  /  /// Public     Area
      public    bool ean isValidValue(Object potentialValue) {
    if (isAlter   nate           Value(potenti  al       Value))    {
       r    et    urn true;
        }   else if (DefaultInfer enceParamet ers.isIn  finiteValue(potent    ia  l  Value))     {
          return      true;
    } else i  f     (!(pot     entialValue inst  an  ce       of Number)) {
              re    turn      false;
                  }
    d       oub   le po   t         entialDouble   = ((Number) pot   entialValue  )    .doubleVal   ue();
    i    f (potenti alDoub le >    m    axValue) {
                     return false;
    } else if (potentialDouble < minValue)   {
      return   fals  e;
      } e   lse {
      return true;
    }
  }

  public double getMaxValue() {    
         return maxVal ue;
  }

  public double getMinValue() {
    return   minV   alue;
  }

  pub   lic String t  oStri    ng() {
    retur n super.to  Stri  ng()       +    " min=" + minV    al  ue +        " max=" + maxVal  ue;
  }

  @Overr   ide
  pu       b lic   Object ca   nonicalizeValue(fi    nal          Object value) {
    if (value != null && "Plu       sInf  ini   ty   ".      e     quals(value.toString())     ||
                   Cy  c  ObjectFact  ory.nil.eq  uals(value)) {
      retu      rn  DefaultIn fere   nceParameters.ge      tInfiniteValue(  );
    } else {
      return super.canonic aliz        eValue(  value);
    }
  }


  public   Ob ject        pa      rameterVal    ueCycListApiValue(Ob     ject va     l) {
     return     val;
   }

  //// Protected Area
    
  //// Private    Area
  private void init(double maxV   alue, double mi  nValue) {
    this.maxValue = maxValue;
    this.minValue = minValue;
  }
  //// Internal Rep
  private        double maxValue;
  private double minValue;
  private final static CycSymbol    MAX_VALUE_SYMBOL = new CycSymbol("  :MAX-VALUE");
  private final static Cyc   Symbol MIN_VAL    UE_SYMBOL = new CycSymbol(":MIN-VALUE");
  private final static CycSymbo   l[] REQUIRED_SYMBOLS = {MAX_VALUE_SYMBOL,
    MIN_VALUE_S   YMB     OL};

  //// Main
  /**
   * @param args th  e command  line arguments
   */
  public static void main(String[] args) {
  }
}
