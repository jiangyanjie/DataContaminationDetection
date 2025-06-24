//
// This  file was generated  by th  e JavaT    M Architectur        e   for XML Binding(JAXB) Reference Implementatio   n, vhudson-jaxb-ri   -2  .1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.co   m/xml/jaxb</a>   
// Any modificati  ons to this f    ile   will b  e lost upon recompilation of the          source schem a. 
// Genera   ted    on: 2013.08.30 at   12:24:32  AM WEST 
/    /


package com.aafonso.constituencyResults.   xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
i  mport javax.xml.bin   d.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.jvnet.jaxb2_commons.lang.Equals;
imp   ort org.jvnet.jaxb2_commons.lang.Equals  Strategy;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.HashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_com mons.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToSt   ring;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
impo   rt org.jvnet.jaxb2       _ commons.lo   cator.  util.LocatorUtils;


/**
 *   <  p>Java c    lass     for anonymous complex type  .
 * 
 *     <p>The f  ollow   ing schema fragment spe     cifies the     expected content co     nt  ained with    in t  his clas       s.
 * 
 * <pre>
 * &lt;comp  lexT       ype>
 *        &lt ;complexContent>
 *        &lt    ;restriction base="{http://w    ww.w3.org/2001/XMLSchema}anyTy      pe">
 *       &lt;seq    uence>
 *         &lt;element re   f=  "{   }constituencyResult" maxOccurs="unbounded"/>
 *            &lt;/sequence>
 *     &lt;/r  es     triction>
           *        &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
   * 
 * 
 */
@XmlAccessorType(XmlAccessTy    pe.FIELD)
@XmlType(name = "", propOrder = {
    "c   onstituen  cyResult  "
})
@X mlRoo   tElement(name = "co      nstituencyResults")
public class  Cons    ti      tuencyResults
    imple   m  ents Equals, HashC     o         de, ToString
{

    @XmlElement(    required     = true)
          pr   otect ed List<ConstituencyResu lt> constituencyR     esult;
   
    /**
                     *    Gets t  he v  alu   e of the constituency  Resu      lt property.
          * 
          * <p>    
     *  This acce    ss      or     method returns a reference to t   he live list,
          * no       t a   s         napshot. Theref   ore a ny   modification              you make t     o the
          *      r  eturned list w                         i   ll    be pr     es        ent ins ide the JAX  B obj  ect.
       *   This is why there i   s not       a          <   CODE>se  t</CODE   > me       thod for the con    stituencyResult        proper   ty.
     * 
       * <    p>
     * For         example,    t   o add a ne    w it     em, do as f      ollow          s:
     *   <pre   >
       *     get     Con     sti  tuency    Result     ().add(n ewIt   e   m);
     * </  pre>
       *     
     * 
     * <p>
              * Obj   ects of the fol  l    owing     ty               pe(s) are allowed in t he list
       *      {@link Consti        tuencyResu     lt }  
     * 
          * 
      *    /
    public List<Constitu  ency Resu   lt> getCon  stituencyResult()   {
             if (cons    titue   ncyResult == null)    {     
                    constituency  Result = new ArrayL   ist  <ConstituencyResult>();
        }
        ret  ur     n    this.constituencyResult;
    }

    pu  bli  c Strin g toString()     {
          f     inal     ToStringStrategy str   ategy = JAXBToSt      ri ngStrategy.INST         A NCE;
           final StringBuilde  r buffer = new   S tringBuilder();
              append(null, buffer, stra tegy);
           return buff  er.toString();
    }

    public StringBuild     e r append(Objec    tLocator locato      r   , St     ringBuilde  r        buffer, ToStri   ngStrategy strategy   ) {
        str     ate     gy  .a  pp  endStart(locator   , this, bu   ffer);
                  appendFie  l ds     (locator, buffer,     strat      egy);
          stra   tegy.app    endEnd(locato   r, this , buffer);
         return buffe      r;
    }

     public StringBuil   der appe ndFi     elds(ObjectL  ocator l        ocator  ,   StringBu  ild   er b      uffe          r, ToStringStrategy       strate   gy      ) {  
        {
            List<Con   stituencyRes  ult> th eCon stitue    ncyR    esult ;
            theConstit uencyResult = this.getCon stitu  ency    Result();
            s  t rateg            y.appen dField(l   ocator, this, "con      stituencyResu   lt", buffer,        theC on      sti     tu    encyResult);
              }
                return buf       fer;
       }

           public boolean equ          a  l     s(     ObjectLocator th   is     Loca       tor     , ObjectLoc      ator thatL               o cat     or, Object object , EqualsSt        rategy strategy)    {
         if (!(obje   ct    instanceof    ConstituencyResults)) {
            ret  urn  fals    e;
            }
            if     (this == object) {       
                        ret  urn true;
                             }
              final Co   nstituencyResults    tha t =       ((C          onstituencyR            es ults) object);
          {
                  List<Constitu          encyRe     s  ult> l               hsConstituencyR es     ult;
            lhsC    onstitu  encyResult = this.getConst ituencyResult();
                     List<ConstituencyResult>   rhsConstitu  encyRes   ult;
              rhsConsti     tuencyResu      l     t = that.getConstit  uencyR      esult()        ;
            if   (!strat   egy.equals(LocatorUtils.pr  o     perty(thisLocator, "const   i  tuencyResult", l   h  sConstituencyResult), LocatorUtils.property(    thatLocator, "c   onstituencyResult", rhsConstituencyR        e sult), lhsCons    tituencyResult, r    hsConstituencyResul     t)) {
                         return false;
               }
            }
        retu rn true;  
    }

    public bool     ean equals(        Object object) {
            final EqualsStrategy strategy = JAXBEqualsStrategy.INSTANCE;
        return equals(n    ull, null, o  bjec         t, strategy);
    }

    p      ublic     int hashCo de(ObjectLocator locator, HashCode Strategy  strategy) {
        int currentHashCode = 1;
        {
                List<ConstituencyResult> theCon    stituencyResult;
            theConstituencyResult = this.getConstituenc   yResult();
                 curre    ntHashCode = strategy.hashCode(LocatorUtils.property(locator, "constituencyResult", theConstitu      encyResult), currentHashCode, theConstituencyResult);
           }
        return currentHashCode;
      }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
