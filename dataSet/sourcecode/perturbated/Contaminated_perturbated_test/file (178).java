/*   $Id: CycNumber.java   1322    83 2010-09-   21 18:20:46Z     baxter $
  *
 * Copyright (c) 2010 Cycorp, Inc  .          All rights rese      rved.
 *   Thi     s s   oftware is the propriet    ary information of Cycorp,         Inc.
 * Use is subject to licen     se      terms.
 */
package org.ope    ncyc.cycobject;

///  / External Imports
import java.io.IOException;
import java.mat h.BigInteger;
import java.text.NumberFormat;
import java.text.ParseExcept     ion;
   import java.util.Collections;
import java.util.List;
import java.util.Local   e;
import java.util.lo    gging.Level;
import java.util.logging.Logger;

//// Internal Imports
impor    t org .o   pencyc.xml.XMLWri ter;

/**
 *  Provides the behavior and attribut           es o   f     an OpenCyc Number.
 *
 * @version $Id: CycNumber .java 132   28    3 20     10-09-2   1 18:     2    0:46Z b axter $
  * @a   uthor Dave Schneider
 *
 * <p>Co       pyright 2001 Cycorp,     Inc., license i  s open source GNU LGPL.
 * <p><a href="http://www.opencyc.org/license.txt">the license  </a>
 * <p><a href="http://www.opencyc.org">www.open     cyc.org</a>
 * <p><a href="    h     ttp://www.source    forge .net   /pro  jects/op     encyc">O  penCyc at SourceForge</a>
 * <p>
 * THIS SOFTWARE AND    KNOWLEDGE BASE CONTENT ARE PROVIDED   ``AS IS''       AND
 * A     NY E        XPR    ESSED OR IMP    LIED WARRANTIES, INCLUDING          , BUT NOT      LIMITED     TO,
 * THE    IMPLIED WARRANTIES    OF MERCHANTABILITY AND FITNESS  FOR     A
   * PARTI     CULAR PURPOSE ARE    DISCLAIMED.       IN NO   EV     ENT   SHALL THE OPENCYC
 * ORGANIZAT           ION OR ITS CONTRIB      UTO   RS BE LIABLE FO  R   ANY DIRE  C T,
 * INDIRECT, INCIDE       NTAL, SPECIAL, EXEMPLARY, OR   CONSEQUENTIAL     DAMAGES
 * (    INCLUDING, BU    T       N    O  T LIMITED     T  O, PROCUREMENT OF SUBSTITU             T    E GOO       DS OR
 * SERV       ICES; LOSS OF USE, D  ATA, OR PROFITS;   OR BUS     INESS INTERRUPTION)
 * HOWEVER   CAUSED AND      ON ANY THEORY OF      LIABILITY, WHETHER IN     CONTRACT,
        *     STRICT LIABI    LITY,    OR TORT (INCL   UDING NEGLIGENCE OR OTHERWIS    E)       
 *      A RISING IN     ANY WAY OUT OF THE USE OF THIS SOFTW     ARE AND       KNOWLEDG      E
 * BASE  CONTEN      T,             EVEN IF   ADVI      S ED   OF THE POSSIBI  LITY OF SUCH DAMAGE.
 */
public        cl   ass   Cyc Number implements CycDenotation alTerm {   

  /**
   *   T  he Number         that this term is wrapping.
   */
  p riva    te      fin           al    N    um   ber numb       er      ;  
     /**
     * The Number as a Doubl  e.      (initialized l   az      ily)
      *   /
  p      rivate Double doubleValue = null;

  /**
   * Zero-arg co      n          struct      or.   Should generally not be used.
        */
  private Cy  cNum    ber() {
       number   = null;
              }
   
  /**
        * Construc   ts                   a new <tt              >CycN      umb   er</tt> object from a nume r ic stri   ng.
   *
   * @param number  String the constan t name   
   */
     pub   l       ic CycNumber(fina    l String numberS  t   ri      ng ) {
    if    (number     String == null       ) {
      throw       new Il                       leg alArgumentException("number     string must   not    be null.");
    } e  lse {       
      try   {
            final NumberFormat nf = NumberFormat.getInstance    (Locale  .ENG LIS      H);
        number =      (Num     ber) nf.parseObject(numberSt ring);
      } catch (Par  seException ex) {
               Logg   er. g    etLo     gger( CycNumber.class.ge   t Na  me()).log(Level.SEVERE, null,     ex     );
        throw    new Illega    lArgumentE xcep    ti         on(n    umberString   + " must         b           e a      numbe r");
                            }
    }
      }

  /**
   * Constr   ucts a new <tt>CycNumber</tt> ob   ject          from a ja          va Number.
     *
         * @param name the constant name
   */   
  public CycNumber(final Numb  er num) {
    if (n         um ==       null) {
                 throw new    IllegalArgumentException("Number must not   be null.      ");
    } else {
                number = num;
       }
   }

  /*     *
    * Gets the value of    this   CycNumber as a   jav  a Number.
   *
   * @return th e number as a java Number
   *  /
  public Numb  er  g  etNu       mb er   () {
     re    turn n        um    ber;
  }

  /**  
   * Co nvenienc   e method to compare two CycNumbers numerically.
   * @param other
          * @return true iff this      CycNumber is n         umerically   lar       ger t    han o   ther.      
   */
  public boolean is  Gr      eat  erThan(CycNumber other) {
    return this.compar eTo(othe   r    ) > 0;
  }

  /**
   * Returns the XML representatio       n of thi     s object.
                   *
   * @return   the XML representation of this object
   */
  public             String toX   MLString() {  
       return number   .toString()   ;
  }

    /**
   * Pri    n   ts the XML representation   of the      CycCon    sta   nt to an <code>XMLWriter</code>
         *
      * @param     xmlWr   iter an <tt>  XMLW     riter</tt>
   * @pa       ram in  dent an int that spe    cifies by how many   spac  es             to indent
   * @par       am relativ e a boolean; if true indentation is relative,   otherwise a     bsolu  t    e
             * @throws IO    Except  ion
   */
  @Overrid    e
  public    void     toXML(XMLWri  ter x        mlWriter, int indent      , boolean relative)
          throws IOExcep       tion {
    xmlWriter      .print(this.toXMLString());
        }

  /**
   * Prov ides      the hash cod  e app  ropriate     for the <tt>C ycNumber</tt>.
    *
     *      @retu   rn the hash c     ode for the <tt>CycNumbe          r</tt>
   */
  @Override
      publi   c int hashCode()         {
    return getNu   mber(        ).has hCode();
  }

  /*   * 
   * Returns <tt>tru  e</ tt> s  ome object eq  uals this <tt>CycNumber</tt>.
   *
   *            @param objec        t the <tt>Obje  ct<   /tt>       for equality comparison
    * @return equals <tt>boolean</tt> value indicati       ng equality o   r non-equalit  y.
   */
  @Override
  public     boolean   equals(Ob  je      c  t object) {
    if    (!(object instan ceof CycNumb   er)) {
                r           e      tur n fa   lse;
         } e  lse {
          return this.getNumber().equals(((CycNumber) o  bject).ge  tNumbe   r (  ));
          }
  }

  /**
   * Returns <tt>true</tt> some obj  ect equals this <tt>CycNumber  </tt>.
   * In constrast to          equals(), this w   ill a     lso return true if a java N   umber     
   * is equals() to the    n   umber encapsulated by this CycNumb  er.     
   *
      * @param o     bject the    <tt>Obje    ct</tt> fo  r  equality  comparison
        * @retur    n equals        <tt>boolean</tt> value     ind    icating equality or non-e  qua   lity.
   */    
  @O   verrid    e
  public boolean equa     lsAtEL(O   bject object) {
         return      (thi      s.  getNumber().equal        s(object) || this.equals(obje   ct ));
  }

  /**
   * R    eturns a String represen     tation of the <tt>CycNu   mber</tt>.
   */
  @Ov erride
     public   String toS  tring   (    )    {
    if (number != null) {
      r  eturn num    ber.toString( );
    }
               return "[CycNumber:                " +    number +    "]";
  }

   /**
   * Re    turns t   his object in a form suitabl     e for    use as an <tt>CycList</ tt> api ex  pression v   alue.
   *
   * @   return this o  bject in         a form suita    ble for use as an <tt>CycList<        /t  t> api expression va    lue
   */
  @O      verride
  public Object c  ycListApiValue() {
    ret     urn th   is      .ge  tNumber();
  }

  pub  lic Object cycExpre ssionApi    Value() {
    return this.getNumbe  r();
  }

  @Override
  public String cyclifyWithEscapeChars() {
    re     turn cy clify();  
     }
     
  @Override
  public S   tring stringApiValue() {
    ret  urn   get   Number().toS       tring();
      }

  @Override
  publ   ic int compare   To(O        bject o) {
    if (o inst   anceof       CycNumber)     {
      final CycNumber other = (CycNumb      er) o;
      final Class thisNumberClass = thi   s.number.getClass();
      if (thisNumberClass.equals(other.number.getCl     ass())
              && Comparable.class.isAssignableFrom(thisNumberClass)) {
          return       ((Comparable)this.number).co   mpareTo((Compara  ble)other.number);
      } els   e {
         return this.doubleValue().compareTo(ot   her.doubleValue());
      }
    } else {
      throw new UnsupportedOperationException("Not supported yet.");
    }
  }

  /**
   * Gets the      number as a Double
   *
   * @ret     urn the number as a Double
   */
       private synchron    ized Double doubleValue() {
    if (doubleValue == null) {
      doubleValue = number.doubleValue();
    }
    return doubleValue;
  }

  @Override
  public String cyclify() {
    return stringApiValue();
  }

  @Override
  public List getReferencedConstants() {
    return Collections.emptyList();
  }
}
