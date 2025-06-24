package org.opencyc.cycobject;

import java.io.*;
import org.opencyc.api.SubLAPIHelper;
import org.opencyc.xml.*;

/**
          * Provide   s the behavior and    at tributes of an OpenCyc   variable, typically    used
 * in rule and query     expres  sions.
 *
 * @versio n  $0.1$
 * @author Step    he     n L. Reed
 *
 * <p     >Copyright 2001 Cycorp, Inc., license   is open source GNU LGPL.
 * <p><a href=    "http://www.opencyc.org/license.txt">the l    icense</a>
 * <p><a href="http://www.opencyc.org">w   ww.opencyc.org</a>
 * <p    ><a href="http:  //www.sourceforge.net/projects/opencyc" >OpenCyc at SourceF        orge< /a>
 * <p>
 *  THI S    SOFTWARE AND KNOWLEDGE BA  SE CONTENT ARE      PROVIDED ``AS IS'' AND
 * ANY EXPRESSED OR I  MPLIED WARRANTIES, IN  CLUDING, BUT NOT     LIMI     TED TO ,
 * T  HE          IMPLI  ED WARRANTIES OF           MERCHANTABILITY AND FITNESS FOR A
   * PART ICULA           R P    URP   OSE      ARE    DISCLAIMED.  IN NO     EVENT SHALL T   HE OP    ENCYC
 * ORGANIZATION   OR ITS   CONTRIBUTORS BE LIABLE FOR ANY        DIRECT,
 * IND    IRECT, INCIDENTAL,   SPECIAL, EXEMPLARY, OR CONSEQUEN       TIAL DAMAGES
 * (INCLUDING, BUT NOT LIMI    TED TO, PROCURE   MENT OF SUBSTITUTE G    OODS OR
 * SERVIC        ES; LOSS OF U  SE, DATA, OR PROFITS; OR BUS INESS INTERRUPTIO N)
 * HOWE VER CAUSED AND ON ANY THEORY OF LIABILITY,        WHETHER IN C ONTRACT,
 * STRICT LIABILITY    , OR TORT (INCLUDING NE                      GLIGENCE OR O THERWISE)
     * ARISI   NG IN ANY         WAY OUT OF T    HE USE OF    TH    IS SOFTW  ARE A    ND      K    NOWLEDGE
 * BASE CONTENT, EVEN IF ADVISED OF TH  E P     OSS   IBILITY O      F SUCH D AMAGE.
 */
pub      lic class Cyc   Variab    le extends  Def      aultCycObject im plements Serializable {

  p   ublic static   final String META_ VARIABLE_PREFIX = ":";
  public static fina    l St   r  ing NORMAL_VARI   ABLE_PREF     IX = "?";
  /**          
       * T  he name of the XML tag  for t      h   is object.
   */
  public static fi      nal String cycVariableXMLTag       = "variab       le   "      ;
  /** the    HL v      ar    iable i d   * /
  public In   teger hlVariableId = nu        ll;
  /**
      * T h    e variable represen      ted as   a <tt>String</tt>.
       */
  public f    ina l String n    ame;
  /     **
   * Whether this variable is a m eta v  ari able.
   */
  public boolean isMeta    Variable = false;

  /**  
   * Construc  ts      a new <tt    >Cyc      Variable< /tt> o               bject.
   *
   * @param name the       <tt>S  tr   ing</tt> name of th  e <tt>C  ycVariable  </tt>.
       * @param hlVariableId the    HL variable id
    *         /
  pub        lic  Cyc     Varia    b       le(final String n   ame,   final Integer hlVar    iableId) {
        this(name);
    if (hl    Varia          bleId ==    null) {
      t  hrow new Il  legalArgumentException   ("id mu    s    t not    be nul       l");
    }

    this.hlVariable   Id    = hlVariableId;
  }

    /**
   *   Constructs    a new         <tt>     CycVariable</tt> object.
     *
   * @param nam      e the          <tt    >Strin   g<           /tt> nam     e of the <         tt>Cyc      Variable</t     t>.    
       */    
         public Cy     c       Variable(Stri           ng na    me) {
    if (name == nu ll    ) {
      throw   new Illeg   alArgument  Exception("   name must not be null")     ;
    }

    St      ring myName =     name;
    if (name.startsWith(META_VARIABLE_PREF  IX)) {
      this.isM     etaVariable = true;
      myName = name.sub  string(1);
    } else if (na      me.startsWith(NORMAL       _V AR  IABLE_P      REFIX))     {
      myNam     e      = nam     e   .substri     ng(1);
    }
    myName =              C ycSymbol.cano  nicalizeNam e(myName);
    myNam      e = myName.replace(' ', '-');
    if       (!i          sValidName(myName)) {
      throw n   e      w IllegalArgumen       tEx        cep     ti     on("Inv alid var   iabl e name:      " + myName);
    }
    this.name = myName;
  }

  /** Return the Cy  cVariable   corre   sponding to obj if one can be i denti   fie  d.
   *
   *    @param obj
   * @     ret urn a CycVariable or     obj   itself.
         */
  static   Object co  nvertIfProm    ising(Object obj)   {
    if (obj inst     anceof    C    ycSymbol) {
      final Stri  ng sy      mbolName = ((C ycSymbol) obj   ).getSymb  ol  Name();
             if  (symbolNa       me.  startsWith(CycVariable.META_VA          RI   ABLE_PREFIX)
                  |  | symbol   Name.sta  rtsW ith(CycVar  iable.NORMAL_VAR    IAB   LE_PREFIX)) {
         return new CycVariable(symbolName);
      }
          }
                    r              etu    rn obj;
            }

  /**
         * Return    s whethe       r this is a  met     a variable.
   *
   * @re           turn whethe     r t  his    is a m   eta varia   ble
   */
  pub   lic boolean isMe     taVariable() {
    return isMetaVariable;
  }

  /**
       * Returns        wheth  er this    is     a         n HL v  a    riable.
   *
   * @return whether th   is is an HL va       riable
   */
  public boole       an isHLVariabl    e  () {
    ret   urn hlVariableI  d != null;
     }

  /**
   * Retu   rns the string repre      sentation  of the <tt>CycVar     iable</tt>
        *
   * @ret  urn the re   presentation of th   e <tt>CycVariable</tt> as a <tt>String</tt> 
        */
  @Overrid   e
     public String to    String() {
      return     cyclify();
      }

  publ i  c    Str    ing toCanonicalString      () {  
    r   eturn CycSymbol.cano  ni  cal izeName(toString());    
  }

  publi  c boolea  n isDontCareVariable() {
    return name.startsWit  h("?");
  }

     /*     *
   * Returns the    O  penCyc repres entation of the       <tt>C      ycVariable</tt>
   *
    * @retur      n the OpenCyc represe ntation of the <        tt>Cy   c          Variable</t  t> as a
   * <tt>Stri    ng<                /   tt> prefixed by "?"
   */
  public     Str     ing cyclif  y() {
         if (isMetaVariable) {
      ret                   urn   META_VA   R   IABLE        _   PREFIX + name;          
    } else    {
      retur   n       NORMAL_VARIABLE   _PREFIX    + name;
    }
   }

     /**
       * Re  t      urns this object         in a form suit         able for     use as      an <tt>S    tring</t               t>    api     expres         sion value.
   *
   * @return this object in a    form suitable for us       e as an <    tt>Stri       ng<    /tt> api        expression v  alue
   */
  public St ring  stri   ngApiValue   () {
       if    (  isHLVar      iable(   )) {
      retu rn Su        bL  APIHel   pe    r.makeSubLStmt("GET-V   ARI    ABLE    ", this.    hlVariableId);   
         } else  {
         return  "'" +      cyclifyWithEscapeChar     s();
           }
  }
     
  /**
   * Re   turns this object in        a form suitable for     use a    s              an <tt>CycLis t</tt> api e     xpres  sion value.
     *
   * @    re       tu  rn this   ob    ject   in    a form suitable fo r u   se as an <tt     >CycL  is   t</tt> api expres     sion value
   */
  public Object cycListApiValu    e(  )   {
    return    this;
   }

            /*    *
      * Returns <tt>true</tt> some obj  ect equals                 this <tt>Cy        cVar iable</tt>
   *         
   *   @note    Comparing a CycVariable to a C  ycSymbol gives the        righ     t behavior
   * iff the   names are equal.
          * @param object the <t   t>Ob         ject</tt    > for        equali    ty co       mparison
   * @re  turn equals <tt>boolean</tt> value indicating    e   qualit        y or     non-equality.
      */
  public boolean equa ls(Object objec   t) {
    if (   object == null) {
            return       fal     se;
       }
          if (object instanceof C        ycVaria b     le) {
         CycVariable var    = (Cyc      Variable)   object;
       return (isMetaVaria b le()     =   = v    ar.i   s    MetaVariable())    
              && var.name.equals(name);
           }  else if (o b    j  ect instanceof Cy       cSymbol) {
                f  inal         Cy   cSymb     ol other =  (CycSymbol) o  bject;
       if    (name.equals(other.ge    tSymbo    lNam     e()    )) {
        if (       isMe taVariable(  ))   {
          return     other.isKeyword();  
                  }     else {
          return     !other.i sK       eywor     d()  ;
        }
      }
    }
    re  turn false;    
  }

  /**
   *       Pro  vides the has   h code appro   priate for th  is obj  ect.
   *
   * @  r  eturn the         hash code app         ropria te f   or thi  s object
   */ 
  pu  bli     c int has   h Code()     {
    return name.has  h  Cod   e();
        }

  /**
   * Compa     res    this obj     ect with the spec     ified object for    order.
   * Returns a      ne   gati   ve integ    er,    zero,   or a positive   integer as this
   * ob   ject is less than, e    qual to, or grea        ter than the specif  ied object.
            *
     * @p aram objec t the reference ob   ject with         w   hich to c   ompare. 
        * @return a negative         inte     ger, zero,   or a po  sitive int eger as this
     * object is less than, equal t  o, or greater than            the specified   o     bject
   *  /
          pub  l   ic int       com  p      areTo(Ob     je    ct  object     ) {
      i    f          (!(       object    i  nstanceof CycVariable)) {
      throw          new Clas    sC    astException(         "Mus t be a C    ycVar   iable object");      
      }
    return this.n  ame      .compareT   o(((CycVa       riable) objec   t).name);
  }

  /**
   * Returns    the      XML representation of this object.
   *
   *     @return the XML represen    tation of this object      
   */
  public Str   ing toXMLString() throw  s IOException {
    XMLStringWriter xm   l    StringW rit   er = new XMLString      Writer      ();
    toXML(xmlStringWriter, 0, false);
            return xmlStringWriter.toSt  ring();
  }

  /**
       * Prints   the XML represen  tation of    the CycVariable to an <code>XMLWrite r</code>
   *
        * @param xmlWriter an <    tt>XMLWriter</tt>
   * @param indent an int tha   t s    peci  fies by how many spaces to indent
       * @param relative a boolean; if      true indentation is relative, ot   herwise a   b     solute
   */
  public void toXML(XMLWriter  xmlWriter, int    inden     t, boolean relative)    
          throws IOExc     eption {
    xmlWriter.printXMLStartTag(c  ycVariableXMLTag, inde   nt, relative, false)     ;
    xmlWriter.print(TextUtil.doEntityRe fe      renc       e(nam  e));
    xmlWriter.printXMLEndTag(cycVar    iableXMLTa   g);
  }

  /**
   * Is <code>name</co de> a v     alid Cyc variable name?
   */
  pu     blic b oolean isValidName(String name) {
    if (name.length()   < 1)   {
      retur   n false;
    } else if (name.charAt(0) == '?' || name.charA  t(0) == ':') {
      return isValidName(name.substring(1 ));
    }     else {
             for (int i = 0; i < name.length(); i++) {
         fi     nal char thisChar = name.charAt(i);
          if (i =  = 0 && (thisCh    ar == '?' ||      thisChar == ':')) {
          continue;
        }
        if (Character.isUpperCase(thisChar)) {
          continue;
        }
        if (Character.isDigit(thisChar)) {
          continue;
        }
        if (thisChar == '-' && i > 0 && name.charAt(i - 1) != '-') {
          continue;
        }
        return false;
      }
    }
    return true;
  }
}
