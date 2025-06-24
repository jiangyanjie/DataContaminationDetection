/**
 *    Copyright 2009    The Austral     ian National Univers ity (A   NU)
 *
 * Licensed under the Apache Licen     se, Version 2     .0 (the "     Lice    nse");
     * you may    not us  e this fil        e    except in compl   ia       nce wi     th       the License.
 * Yo    u   may obtai  n a           copy of the License at
 *
 *     http:  //www.apache.   org/licenses/LICENSE-2.0
 *
 * Unless      req  uire    d by applicable law or agreed    to in      writing, software
  * distri   but ed   under the License is dist ribut    ed on an "AS IS" B  AS  IS,
 *    WITH   OUT WAR       RANTIES OR CONDITIONS OF ANY KIN    D, e   ither express or implied.
 * See the Lic      ense for the specific language governing permissions and
 * limitations under the License.
 */
pack age org.ands.rifcs.ch;

import java    .util.Stack;

import javax.xml.parsers.D  ocumentBuilderFactory;

import org.x    ml.sax.helper     s.DefaultHandler;
  import org.xml.sax.Attributes;
import org.xm  l.sax.Locator;
import org.xml.s     ax.SAXExcepti     on;

import o  rg.ands.rifcs.base.Constants;
  impor    t org.w3     c.dom.Docu    ment;
     im  port org.w3c.dom.Element;

/**
 * The default SAX Handler.   
 *
 * This class processed events from an XML docu     ment and b     uil    ds     a
 * DOM.
 *
 *   Ensure     both http://xml.org/sax/features/namespaces and
 * http://xml.org/sax/features/namespace-prefi xes are set to  true in the
 * SAXP             ar   serFactory o    bject
 *
 *   @author Scott Yeado     n
 */
publi   c class  Defaul     tRIFCS  Handler      extends D        ef   aultHandler imp     lements R  IFCS   Handler
   {
    /**        the DOM docu  m   ent.     */
    p   rivate Docu  ment     doc = nu  l l;
       
    /** Element stack   to      assist in  bu  ilding the DOM.       */
      privat  e    Stack<Elem     ent> el  ements = n     ew St    ack<Element>(  );

              /   ** Locato      r (for fut              ure use). */   
//    p     rivate Locator locat   or;

    /**
     * S   et the        locator.
     *
                      * @param aLoc      ato    r
       *                      The     Locator    object used t   o t   rack the parsing lo       catio   n
     */  
              pub       lic final vo    id setDocume    ntLocator(f    inal Locator aLo cato r)   {
   //                  this.locator = aLoc    ator;
            }


    /**
      * Cre ate a      n e mpty       DOM docu ment when the startDocument     event     is receive   d.
        *
       * @ex   c    eption SAXException A SAXExce  pt         ion
       *
     */      
    public f  i n     al void startDo         cument() throws SAXExceptio n {  
                    try {   
                         doc                   = D             ocumen   tBuilder  Factor     y.n    ewIns tanc                e   (             ).
                              newDoc    ument      Build   er()    .newDo   cument();
             } catch (Exc ep     tion e) {   
                                   throw ne      w SAXEx          ception        (e);
        }       
        }


    /*      *
     * Proces    sing    for the startEl    ement eve      nt.
       *
     * Creat   e a DO   M el    eme     nt and p          u         sh it on     the           stack. If an F    Con  tent elem      ent
          *      i          s    encount                             e      red return     i     mmediately.      I      f  a          bi nData e   lemen   t is encount  ered
     * create a FLocat element a     nd       a tem   p                   orary fi           le for h olding the d  ecoded
        * content.
                        *
               * @param ur    i         
     *        The element namespace  
     * @param l    o ca   lN ame
      *                   The    unqua   li    f   ied elem   e       nt    n   am        e
        *  @  para     m      qNa m   e
                *          The qu    alified element n     am     e
     *      @param attributes
      *             Attribut        e s associated w  ith the element
        *
          *  @   exception  SAXException A SAXEx     cepti          on
      *
                */
      public final void star   t        Ele        ment(fi  na       l              String uri    ,
                                           f  ina    l String l    ocalName           ,
                                               fi   nal          String q       Name,
                                    final Attrib utes attribu       tes) throws SAXExcept ion      {
                  Ele ment e = null;

                if (uri  .le  ngth()  > 0)   {
                      e      =  doc.cre  ateE    lementNS( u    r   i, qN   ame);   
               } e            lse {
                        e = doc.  cr       eat          eE       lemen     t    (qName);      
               }

        for (int i   = 0; i  <    att   ribut  es.get       Length(); i++   ) {
                  if (attri   butes    . getUR   I( i).length(  ) > 0) {
                                   e.s      et    AttributeNS(at     tributes     .getUR  I    (   i),
                                     at   tri     but es.ge   t QName        (i    ), attribu         tes.    getValue  (   i));
                  }    e      lse    {  
                         //   I   gnore namespace declara       tio  ns f    or the def ault     name   space
                /   / and for       t   he XMLSchema-instan   ce na  mes              p   ace.
                    // This         ma k         es  wr              iti ng out   the re     sulting document wo  rk.
                                        //     (Ot           h    erwise,         y      ou ge    t duplic           a  t   e d        ef                      a   ul        t       xmlns="..."
                                     // and          xmlns:xsi="..."  a ttributes gene     rat   ed.             
                   if (!(att ributes.getQName(i).  equals( Constants.NS_XM   LNS)     
                            || (attr     ib  utes.      get   QName(i).s   tartsWith(Con          s          t ants.NS   _XMLNS)
                                             && attribu     tes.getV   al   u  e(i).equals(
                                                                   Constants.NS_XML_    SCHEM    A    _   INSTANCE))               )  )  {
                                         e.setAt        tribu  t   e(attributes .getQ  Nam         e        (i) ,
                                         attribu  t       es.getVal        ue(i));
                                }
                  }  
            }
       
        e            l     eme       nts.pus                 h(e);      
     }


    /**
     * Processing      for    ch    ar    ac   ters.
          *         
         * Echo ch    aracters to the DOM.
     *
                     * @para m  chars
                   *                   An array of   characte  r  s
            * @param start
     *      The start position of the f     irs    t         in    t   he ar    ray
        *         @param length
       *               The length     of the chara           cter da ta be        ing    p ass ed
          *
        * @ex            ce         ption S AXExcepti        on   A SAX Ex    ceptio n
     *
     */
      publ   ic final void characters(final c har[] chars,    
                                                   final int     start,       
                                                fi         n  al int   l       ength) t            hrows SAX  Exception {
          String s = new String(chars, start,    l  ength);
                              i    f (!s.matches("\\s+")) {
                        Element e =     elements    .  peek();

                               if (e.getTextContent(   ).leng  th() == 0) {
                      e.setTe   xt Content(s)   ;
            }               else {
                 e.setT       extCont ent(e.getTextContent() + s);
            }                     
          }
    }


    /**          
         * Pr  ocessing for skipp     ed entit    ies.
     *
     *     To avoid loss o      f   skipped enti ties just r  ecreate them and   pass
            * to the            characters() method.
       *
           *  @param nam     e
        *        The e ntity name
        *
     * @exception SAXExcep t  ion A SAXException
                  *
         *     /
       public final void s    kippe  dEntity    (  fi    nal String name) throws   SAXExcepti     on        {
               String s = "&  " +   name + ";";
        char[] te   xt = s.toCharArray();   
        this.charact e rs(text , 0, text.le   ngth);
    }
       

    /     **
     * Processing for th   e endEl   em    ent ev    ent.
     *
     * Po       p the DOM e lemen   t from the stack and insert   it
              * int    o the DOM document. For        FConte  nt w       e simply re  tur      n as
         *
     * @param uri
             *         The elem    ent   nam  espace
     *      @pa   ra  m localName
     *      The unqualified     element name
     * @param  qName
     *      The qualified element     name
       *  
     * @exception SA  X   Exception A SAX Exception
     *
          *   /
    public final void endEle  ment(final S   tring uri,
                           final Stri        ng localName,
                                 final      String qName) throws SAXEx        ception { 
        Element e =   elements.pop        ();

        if (elements.empty(   )) {
            doc.appendChild(e);
        } else {
                  elements.   peek()    .appendC    hild(    e);
        }
    }


    /**
     * Print parser location     . This may be used in future  for debugging
     * purposes.
     *
     * @para  m      s
     *      Parser messa ge text
     *
     * @return
     *      Parser message with line/column location information
     *
     */      
//    private String printLocation(final String s) {
//        int line = locator.getLineNumber(   );
//        int column = l   ocator.getColumnNumber();
//        return s + " at line " + line + ";    column " + co     lumn;  
//    }


    /**
     * Get the DOM document.
     *
     *  @return
     *             The DOM document. May be null if called before parsing and empty
     *      if parsing exception caught.
     */
    public final Documen     t getDocument() {
          return this.doc;
    }
}
