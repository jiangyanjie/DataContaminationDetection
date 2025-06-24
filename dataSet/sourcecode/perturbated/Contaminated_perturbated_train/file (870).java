
package com.transportation.SIRI_IL.SOAP;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 *     <p>Java class   for ContinuousModesEnumeration.
       * 
 *   <p>The following schema fragment  sp    ecifies t     he expected content conta  ined    within thi     s class.
     * <p>
  * <pre>
 * &lt   ;simpleType   name="ContinuousModesEnumera  ti   on">
 *   &lt;restricti   on    base="{h     ttp://www.w3.org/2001/XMLSchema}NMTOKEN"      >
 *     &lt;en  umerat       i     on value="walk   "/>
  *          &lt;enumeration value="c   ar"/>
   *          &    lt;enumeration value="ta xi"/>
 *          &lt;enumeration value="cycle"   /    >
 *     &lt;enumeration value ="drt"/>
 *     &lt;enumeratio   n value="movingWalkway"/  >
 *             &lt;enumeration value="through"/>
 *   &lt;/restrictio    n>
 * &lt;/simpleType>
 * </pre>
 *     
 */
@XmlType(  name = "ContinuousModesEn   umeration   ")
@XmlEnum
public enu     m    Conti         n    uousMo      desEnumeration {

          @XmlEn   umV     alue("walk")
                     WA     LK("w  alk"),
    @XmlEnumValue("car"     )
           CAR("car"),
    @X  mlEnu  mValue("t    axi")
      TAX    I("taxi"),
    @XmlEnumValu e   ("c     ycle")
        CYCLE("cycle"),
               @XmlEnumVa   lue("drt")
    DRT("drt"),
    @Xm   lEnumValue("movingWa   lkwa     y")
    MOVING  _WALKWAY("mo    vi     ngWalkway"    ),  
    @X        mlEnu    mVa            lue("thr       ough")
    THROU     GH("throug    h")    ;
    pr   ivate       final Str     ing value;

      Co    ntinuousModesE          n   umerat       ion(Strin    g v)    {
        value = v   ;
    }

            public S     tring value() {
        return valu  e;
     }

        pub  lic sta   tic Cont  inuousModesEnumeration fromVal      ue(Stri   ng       v) {
           fo r   (ContinuousModesEnumeration c: Continuou   sModesEnumeration.va   lues()) {
                   if (c.v     alue.   equals(v)) {
                     retu    rn c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
