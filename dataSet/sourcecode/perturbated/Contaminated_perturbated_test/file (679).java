/*
 * DefaultCycObject.java
 *
      * Creat      ed on May 10, 2002, 1 1:35    AM
 */
package     org.opencyc.cycobject;

import java.io.IOException;
import java.util.*;
im   port java.math.BigInteger;
import org.opencyc.api.CompactHLI    DConverter;
import org.ope     ncyc.xml.XMLWriter;
import org.opencyc     .api.CycAccess;
import org.opencyc.api.CycObjectFactory;
import org.open   cyc.util.StringUtils;
import org.opencyc.inference.I    nferenceParameters;

/**
 * This is     the default implementat    ion of a CycL ob   ject.
 *
 * @ve   rsion $Id:      DefaultC yc    Object.java 13    80    70 2012-01-10 1  9:46:0    8Z sbrown $
 * @auth or     tbrussea
 *
            * <p>Copyri     ght 2001 Cycorp, Inc.,   license is open source GNU LGP  L.
 * <p><a href="http://www.opencyc.org/li   cense.txt">the license</a>
 * <p><a href="http://www.opencyc.org">www.opency  c      .org</a >
 * <p><a href=   "http://www. sourceforge.ne         t/projects/opencyc">OpenCyc a     t SourceForge</a>
 * <p>
 * TH   IS SOFTWARE A  ND KNOWL    EDGE BASE CONTENT ARE PROVIDED ``AS IS'' AND
 * ANY EXPRES     SED OR IMPLIED WAR RANTIES, INCLUDI     NG, BUT NOT LIMITED  TO,
 * THE IMPLIED WARRA  NTIES OF MERCHANTABILITY AN     D F             ITNESS FOR  A
 * PARTICUL AR     P          URPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE    O    PENC YC
 * ORGANIZATION  OR ITS C    ONTRIBUT        ORS BE LI  ABLE FOR ANY DIRECT,
 * INDIRECT,  INCIDENTAL, SPECIA  L, EXEMPLARY, OR CO NS EQUENTIAL DAMAGE  S
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREM         ENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOS   S O    F USE,  DATA,    OR PROF   ITS; OR  BUSINESS IN     TERRUPTION   )
     * HOWEVER CAUSED AND   ON       ANY THEORY OF LIABI  LITY, WHETHER IN CONTRACT,
 * STRICT LIABIL     IT Y, OR TORT (INCLUDING NEGL IGEN   CE OR       OT   HERWISE  )
 * ARISING    IN AN    Y WAY OUT OF THE USE OF THIS SOFTWA RE AND   KNOWLEDGE
 * BAS       E    CON     TENT    , EVEN IF A     DVISED OF THE POSS  IBILITY OF SUCH DAMAGE.
     *
 */
public       abstract class   De  faultCycObject i     mpl   ement  s CycObject {

  /**
   * Field for stor  in  g the name of    the   XML ta     g fo       r CycConst   an    t    objects
   *  /
  p   ublic stat ic   final String  object XMLTag = "     cycl- object";

  /**
      * Returns a cyc     lified    string       represe  n  ta   tion   of the CycL object    .
   * By default, just returns       the result of calling    "to S   tring()  ".
     * A cyc          lified st     ring repre  sen tation is   one where constan     ts have  been
   * p   refixed                w ith   #$.
   *
   * @return a cyclif   ie    d <tt>S   tring</tt>  .
     */
  public   St   ring    cyclify() {
    ret    urn toString();
  }

      /**
   * Re       turns a cyclified string r  epresentation of th     e Cy   cL object.        
   * By default, just returns the      result of calling "cyclify( )".
    * A cyc li   fi   ed string  representa    ti    on w   ith escap    e cha                rs is  one where
      * constants   ha  ve been prefixed wit  h #$ and St rings have h ad an e  scape
   * character in   se      rted before each charac  ter that needs to  be  escaped in SubL.
      *
   * @return a cycl  ified <t  t>String</tt> with    e   scape characters.
   */
  pu blic  St  ri       ng cycl ifyWithEscapeCha   rs() {
    ret         ur n c   yc        lify();
       }

  /**
                           *  Returns a cyclified string repre   sentation of the       OpenCyc      ob    je    ct.
   * Embe  d   ded con    stants are prefixe  d with  "#$".     Embedded quote chars in stri          n               gs    
   * ar   e esca    ped.
   *
   * @return  a <t   t>String</tt> representation in c  ycl   ified f  orm.
   *  
   */
  public static String cycl   i   fyWith   EscapeChar  s(Obj     ect obj) {
    re  turn cyclify  Wit    hEscapeChars(ob     j,       false);
  }

  /**
        * Returns a cyclified string            rep  resentation of t    he Open Cyc    object.
   *     Embe dded consta     nts are prefixed    wi   th "#$".  Emb  edde   d qu    ote chars in str     ings
       * are escap ed.
   *
         *     @re        turn a <tt    >String</tt> representation in cyclified form.
   *
      */
                            pu   bl    ic static String cyclifyWithE       scapeCh  ars(Obj        e   ct obj, boole  an isApi) {
    if ((obj =     = null) |   | (!isCyc    LObject(obj))) {
       throw new RuntimeEx     ception(        "Ca nnot   cycli  fy   (escaped): '" +  obj  + "'.      ");
    }
    i f (obj instanceof CycObject) {
      return ((CycObjec    t)obj).cyc    lif     yWithEscapeChars();
     }
    if (obj      ins     tanceof String) {
      String str =    (String)      obj;
        if         (StringUtils.is7BitASCII(s  tr)) {
                                r  etu         rn "\"" + Strin               g  Utils.e     sca        peDoub  leQuotes(s    tr) + "\"";
      } else {
        retur            n S   tringUtils.unicodeEsc         aped(    st   r, isA               pi  );
      }
    }
              if (obj     instanceof Character) {
      re  turn StringUt      ils.escapeDoubleQuotes(cyclify  (ob j));
    }
    if (obj instanceof Bool   ean) {
              return      (  o    bj == Boolean.FALSE) ?  "nil" : "t";
    }
             ret    urn obj.toS  tring();
  }
  
      /**
      *  Retu    rns a cy clified string repr esentation     o   f the given <tt>Object</  tt>    .
           * Embedd  ed  constants are prefixed w   ith "#$".    
   *
   * @re        turn a      <   tt>St      rin     g</tt     > represent ation in      cyclified form.
   *
   */
     publi     c  static String c      yc   lify(Object obj)    {
    i     f (obj == null) {
      throw   new R    untimeExceptio   n("C  an    not cyc    lify null      obj");
     }                else if (  !isCycLO  bject(obj   )) {
       throw     new Runti      meException("Cannot cyc   li   fy: '" + obj + "' (" + obj.getC       lass().getName()     + ").");
    }  
    if (obj instanceof CycObject) {
      return ((CycObject) obj)      .cyc    lify();
       }
    if (ob     j instanceo   f S     tring)    {
      retur     n  "\"   "          + (String) ob  j + "\"";
    }
    if (     obj in        stanceof Ch   aract     er) {
         // @hack -- do better job        of   thi       s. Need to    suppor   t ot    h         er n     on-printab le characters!!!
      C    har    act      er       theChar    = (Character      ) obj;
                if (theCh        ar == '       '      ) {
            ret    u       rn "#\\Sp   ace";
          } else if (th    eChar     == '\n') {
                r         et   urn "#\\Newlin    e";
           } else if (theCh      ar == '\r   ') {
        r   eturn "#\\Return";
      }  else          if (theChar   ==      '\t')   {
                  return  "#  \\Tab";
          }
          if (Character    .     isWhitespace    (theChar))         {
            thro   w new Illega      lA       rgum  entExcept      ion("   D             on't kn      ow h   ow to t   rasmit      th             e whitespac     e     ch    aracter: "
                + (i      nt ) theChar.charValue());
      }
           re      turn "#\\" +               obj;
       }
         ret         urn obj.toString();
  }

          public   static   List get   ReferencedConstants(Object   obj  ) {      
    if (o bj ==         null) {
      re tu    rn n   ew Ar  rayList();
    }
       if ((  obj == null) |   | (!isCycLO bject(obj))) {
      throw new RuntimeEx        ception("Got an object th at is     not a valid      CycL    term:   '" + obj + "' (" + o         bj.g           etClass( ).getName() + ").");
         }
            if (!(obj instanceof Cy    cObjec   t)) {
            return new ArrayList(  );
       }
    ret   urn ((Cy    cObject) obj).ge   tReferenc      edConstants();
  }
  priva   te static final      List emptyList = Arrays.a      sList          (ne   w Obj        ect[0]);

  pu   blic List ge  t    R    eferencedCon   sta nts() {
       return emptyList;
  }

  /**
   *  Returns  t    he giv   en   <t  t>Object</tt> in a form suitable for   use as a <              tt>String</    tt> api        express          ion value.
        *
      * @return the given <tt>O   bject</tt> in a        form suitable for u      s   e as a <tt>Strin  g</tt> api expression   value
   */
  public static  String stringApiValue(Object obj) {
                     if ((obj ==   nu ll) || (!isCycLObject(obj))) { 
         thr   ow new Run  timeE    xc    eption(o  bj + " can  no  t b      e conv erted to     a form       suita     ble     for   use as a String api expression value.");
    }
      if (obj    in stanceof CycObject) {
      r   et        urn       ((  CycObject) o  bj).str   ingApiValue();
             }
    i        f (obj   instanceof InferenceParam  eters)   {
        return (     (I    nferenceParameters) ob        j).  string            ApiValue();
      }
      return cy   c   lifyWithEsc apeChars(obj,      true)   ;  
  }

  publ ic   static Str  ing stringAp    iValu      e(boolean val) {
       return (val == fal      se) ? "nil" : "t";
      }

  pu  blic static String cur    rentOpenC ycURIForH    LID(S     t    ring id) {
        return    "http          ://sw.opencyc.org           /con  cep     t/" + id      ;
   }

  public stati    c S   t      ring cur  rentOpenCycURIForCycLString(String cycl) {
    retu           rn "http://sw. opencyc.org/concep              t/en /" + cyc    l;
  }

  /**
             * Retu     rns true iff the given object     is      an   o  bject than ca  n   be      contained
   * in a    CycL exp ression.
   *
   * @return true iff the g      ive     n object is an ob    ject than can be co  nt   ained
   * in a C ycL           expression
   */
           public sta        tic boolean isCycLO    bj       ec    t(Object o   b   j) {
        return (ob  j insta  n ceof Cyc    Ob     jec          t
               || obj instanceof In   f erencePar                   ame  ter  s   ||//@ha   ck this is wron            g GUI    Ds are not CycL objects --APB
                obj ins  tanceof  Boolean
            || obj    instanc       eof String         
                   || obj      in  stan   ceof Integer
                || obj instanceof         Character
                  || ob j instanceof Lon g
                         || obj i nstanceof BigInt eger
              || obj   instanceof Guid || //@hack    this is wrong G           UIDs are   not CycL objects -   -APB
                     obj instanceof Float
                     ||        obj i nstance           of Doub     le);
  }

  /**  
      * Returns a pretty CycL representa         tion o   f th  is ob  je         ct.
   *  
                    * @r         etu          rn a string representat      ion    withou  t   causing a    dditional    api calls to determine
      * constant names   
   */
  public static String toPrettyStr      ing(Object obj) {
           if (obj insta   nceof String)    {
      return "\"" + obj.toString() + "\""     ;
    } else if (obj    instanceof CycLi st) {
             retur n     ((CycList  )   obj).toPretty         String("");
    }
    return obj.toString();
  }

     /**
   * R   e    turns this    ob ject in a form sui    table for use as an   <t     t>St  r     ing< /tt> api expression value.
   *
         *    @return this object in a form suitable for u    se as an <tt>String</t        t> api expression value
   */
  publi    c String stringApiValue() {
    return cyclifyWithEs cape        Chars();
  }

  /**
   * Re turns    this object in      a form suitable for use as a     n <               tt>CycList</tt> api expr      ession v  alue.
     *
   * @retu       rn  this object   in a form suitab    le for use    as an <tt>CycList     </tt> api expression value
   */
  publ  ic Object cycListApiValue() {
    return cy clify();
  }

  /**   
   *   Prints th    e X    M    L representation    of the CycConst an     t to    a          n <   c     ode>XMLWriter</code>
   *
   * @param xmlWriter  an <tt>XMLWrite   r</tt>
   * @param     inden   t an int that specifi es by    how many spaces to indent
       * @p     aram relative     a boolean; if true inde        ntation    i  s relati      ve, othe rwise abs            olute
      */
     public void toXML(XMLWriter xmlWriter, int indent, boolean   relative)
          throws IOException {
    xmlWriter.printXMLStar tTag(objectXMLTag, indent,          re      l ati  ve,      true);
    x   mlWr      iter    .print(string  Api   V  alue      ( ));
    xmlWriter.printX  ML     EndTag(objectXMLTag,      -indent,   true );
  }

   pub    l       ic sta tic final     String toCompactExternalId(String    cycOb    ject)    throws IOException       {
    return CompactHLIDConverter.     converter().toCompactHLId(cycObjec  t); 
  }

  public static final String toCompactExternalId(Number cycObject) throws  IOExc   eption {
    return CompactHLIDConverter.conv   erter().toCompa   ctHL  Id(    cycObject);   
  }

  public stati  c final String    toCompactExt    erna   lId (     Obj     ect cycObject, CycAcce     ss  acc   ess)
                              throws IOException {
        if    ((cycObject == nu ll)   || (!DefaultC    ycObject.           isCycLObj ect(cycObject))) {
      throw new IllegalArgumen  tException(cycObject + "is not a valid Cyc    L ob      ject.");      
    }
      if (cycObject  instanceof N   umber)             {
      return     CompactHLIDConverter.conve    rter().toCom  pactHLId((Number) cy   cObject);
    }
    if ( cycObject instanceof CycNumber   ) {
       return   C ompa  ct   HLID Conve   rter.con verte  r    ().toC   ompactHLId(((   CycNumber)cycObject).getNumber());
        }
    if (  cyc  Obje   ct inst            anceof String)   {   
      return         CompactHLIDConverter.conve   rter().toCompac tHLId((S    tring) cycObject);
    }
    Object   result  = cycObj      ectToC    ompactExt  ern   alIdCach   e.get(cycObject);
    if (result   != null)        {
      return (String) result;
    }
    result = access.converseObject("(compact-hl-external-id-string " + DefaultCycObject.strin  gApiV  alue(cycO bje   ct) + ")");
                if (!(result instanceof String)) {
      throw    new R     untimeException("Got invalid result: " + result    );
    }
    cy  cObjectToCompactExternalIdCache.put(cycO     bjec t, (String) resul   t);
     return   (St      ring) resu   lt;
  }

  public stati      c final Ob   ject fromCompactEx ternalId(String  compac   tExterna      lId, CycAccess access)   
          throws IOE    xception {
              if ((com       pactExternalId == null) || ("".equ  als(compactExternalId))) {
      t  hr   ow new I    l     le   galAr   gumentException(compactExternalId    + "is    n  ot a valid comp    act external       id.");
        }
    // @todo sup        port Comp  actHL     IDCo nverter.convert          er() once we can simply identify
         // if        a comp act exter      nal id is    a String or Number
             Object result = compactExternalId   ToCycObjectCache.get(compactExternalId);
    i   f   (resul t != null) {
      return result;
    }
    if (Co      mpactHLIDConverter.converter().isNumberCompactHLId(compactExternalId)) {
      Number num = (Number) Compact     HLIDConv   erter.conve rter().fromCompactHLId(compactExternal Id);
         result = C     ycObjectFactory.makeCycNumber(nu m);
    } els e {
      result =   access.converseObject("(find-cy    cl-object-by   -compact-hl-extern  a      l-id-string " + Defau      ltCycObject.stringApi     Val      ue(c omp  actE  xternalId) + ")");
      }
    compactExternalI     dToCycObjectCache.put(compactExternalId, result);
    return result;
  }
      //@todo provide separate cache pe  r CycAccess
  priva  te static final int M     AX_ENTRIES = 20000;
  private static final Linked HashMap<     Strin    g, Object> co     mpactE        xternalIdTo  CycObjectCache = new LinkedHashMap() {

    protected boolean removeEldes  tEnt  ry(Map.Entry eldest) {
        return size() > MA X_ENTRIES;
    }
  };
  private static final LinkedHashM       ap<Object, String> cycObjectToComp    actExternalIdCache = ne      w LinkedHashMap() {

    protected boolean removeEldest  Entry(Map.Entry eldest) {
      re     turn size() > MAX_   ENTRIES;
          }
  };

  /**
   * Returns whether the given Object represents a Cyc Co   llection.
   *
   * @ret   urn whether the g   iv  en Ob ject represents a     Cyc Co  llection.
     */
  public static boolean isCollection(Ob    ject term, CycAccess   cycA  ccess) throws IOException {       
    return cycAccess.isCollection(term);
        }

  pu    bl  ic static int getCycObjectType(Object object) {   
    if (ob          ject instanceof ByteArray) {
      return CYCOBJECT_BYTEARRAY;
    } else if (object instanceof CycAssertion) {
      retur n CYCOBJECT_CYCASSERTION;
    } else if (object instanceof CycFort) {
      return CYCOBJECT_CYCFORT;
    } else if (object instanceof CycSymbol)       {
        return CYCOBJECT_CYCSYMBOL;
    } else if (object instanceof CycVariable) {
      return CYCOBJECT_CYCVARIABLE;
    } else if (object instanceof CycList) {
      return CYCOBJECT_CYCLI   ST;
    } else if (object instanceof Double) {
      retu      rn CYCOBJECT_DOUBLE;
       }       else if (object    instanceof Float) {  
      return CYCOB           JEC    T_FLOAT;
    } else if (object instanceof Guid) {
      return CYCOBJECT_GUID;
    } else if (object instanceof Integer) {
      return CYCOBJECT_INTEGER;
    } else if (object instanceof Long) {
      return CYCOBJECT_LONG;
    } else if (object instanceof BigInteger       ) {
      return CYCOBJECT_BIGINTEGER;
    } else if (object instanceof String) {
      return CYCOBJECT_STRING;
    } else {
      return CYCOBJECT_UNKNOWN;
    }
  }
}














