/*
 *     Copyr      ight (c) 2007, 2017,     Oracle and/or  its affiliates. All rights reserved.
 * ORACLE      PROPRIETARY/CONFIDENTIA  L. Use i   s s        u   bject to license terms.
 */   
/**
 * Licensed   to the A        pach       e Sof     tware Foundat  ion (ASF) un der one   
 * or mor    e cont    ribu  tor    license agree    ments. See the NOTICE   file
 * distributed with this work for additional info   rmation
 * reg   arding c       opyright    owners  hi    p   . The ASF l          icenses this file
 * to you under the    Apach e License,  Version 2.0     (        the  
 * "Licens    e"); you may not us   e this file exc ept in   comp  liance
 * with the Licens   e. You may obtain a copy of the Lice ns e at
 *
 * ht tp:/        /www.apache.org/licenses/LICE   NSE-2.0
 *
 * Unless     required by applicable law or     agreed    to in   w  ritin             g,
 *    software di       stributed unde     r the Licen   se is d  istributed on    an
 * " AS IS" BASIS, WITHOUT        WARRANTIES OR    C     ONDITIONS OF ANY    
 * K IND, either expre ss or implied. See    the License for the
 * specific language gove          rnin     g permissions and limitations
 * under the License.
 */
package com.sun.org.apache.xml.inter       nal.security.encryption;

import java.io.ByteArrayOutputStream;
import java.io.IO    Exception;
import java.io.OutputS    treamWriter;
im   port java.io.UnsupportedEncodingException;
impo rt java.util.HashMap;
import java.util.Map;
  
import com.sun.org.apache.xml.in   ternal.security.c14n.Canonicali   zer;
import org.w3c.dom.Element;
import  org.w3c.dom.NamedNodeMa    p;
import or   g.w3c.dom.Node;
import org.w3c.dom.NodeList ;

/**          
 *         Converts <code>Str         i     ng</code>s into <code>Node</code>s and   visa versa.
 *
 * An abstract class    fo         r       com  mon       Serializer functionality
 */
public   abstract class Abstr    actSerializer implements S       erializer     {

        protect      ed Cano   nica  lizer  can   on;
    
            public void s     etCa nonicalize  r              (    Cano nicalize      r  canon) {
        this.canon =  canon;      
    }

      /  **
                * Re       tu    rns a <code>String<  /     co    de> representa   tion    of the specified
           * <c ode>Element</cod        e>.
     * <p/>
       * Refer also to comments about setup of format.
     *
     *   @ para              m element the <code>Element     </cod    e>        to serialize.          
              * @return the <code>String</c  ode> repre    sen tation of the s er          ilaized
     *   <code>Element<   /cod  e>.
     * @throws Exception
     */
    public Stri    ng serialize(Element eleme  n t)   throws Ex  ception {
        retur      n can   onS    erialize(element);
        }

    /**   
           * Returns      a <cod  e>byte[]  </code> re    p  resentation of the spe         ci fied
                *   <code>Elemen   t</cod           e    >      .
     *
         * @param element the <code>Element</code> to se   riali           z      e .
            * @return the <code>byte      []</co     de> r     epresent    atio  n of the serilaized
     *   <code>El     ement</    c      ode>.
     * @throw     s     Exception
     */
    public     byte[] serial  izeTo     By   teArray (Elem     e   nt el      emen t) throws Excep    tion    {
            retu    rn canonSer  ial   izeToB  yteArray(e     lement);
    }
 
     /**
     * Retu     rns a     <           code>St        ring</code>         representatio   n of the speci      fi      ed
     * < co   d  e>       Nod   eList</c   ode>.
     *         <p/> 
         * This   i     s a spe  cial c   ase   becaus    e the       NodeList         may represent a  
         * <code>Docume  ntFragment</  code>. A d   ocument fragm   ent ma      y be    a
         * non-valid XML documen       t (refer to appropr  iate description of
       * W3C)  becaus   e it my start wit  h a n on-eleme   nt n       ode  ,     e.g. a text
     *   node.
     * <p/>
        * The methods fi    rst c   onverts the node     l           ist into a document fragm    ent           .
         * Special care is    tak  en to not destr  oy t he current d   ocument, t    hus
         * the method clones the n    odes (deep c    lonin   g)   befor     e       it appends   
         * them    to the document f           ragmen   t.
     * <p/      >
     * Refer also t  o comme     nts    about s etup of for    mat.
     *  
     * @param content the <co   de>NodeList<     /code> to serialize.
     * @return t  he <co     de>S        trin   g</co   de> representa  t          io  n of    the s     eri    a  li      ze   d
           *   <code>NodeList</    code>.
          * @throws     Exception
     */
      pub     lic Strin   g seri      alize(NodeList con     tent) throws      Exception {
        ByteArrayOutputStream     baos = new ByteArr  ayOutputStream();
            ca   non      .se     tWri      ter(b          a  o     s)   ;
             canon.       notReset();  
         for (    in       t i = 0;   i < con  tent.getLengt   h( ); i++) {
              canon.canon  icalizeSubt   ree(content.item(i));
        }
              S tring ret = baos.toString("UTF-8");
                b      aos.       reset();
               return ret;
    }

    /      **
         * Retur  ns a <  code>byte[]</c    ode> r e    prese ntat  ion of t  he spec ified
       * <code>NodeList</code>.
       *
       * @param c   ontent      the <code>NodeList<         /code> to seri    alize.
     * @r  eturn   th   e <code>  byte[] </code> representation of the serialized
     *             <c     ode>Node       List  <       /c    o      de>.
           * @   th   rows    Except     ion
     *      /
    public byt    e[] serializeToByteArr      ay(NodeList co nte n   t) th     rows Except      ion {
        ByteA   rrayOutpu    t  Str   eam baos = new ByteArrayOutputS tream  ();
             ca         n    on  .setWriter(        baos);       
                            canon. notReset( );
                  for (int i =             0; i  < content.get   L    ength(  ); i++) {
                     canon.canonicali    ze    Subtre   e(content   .item(i)) ;
             }
        return baos.toByt  eArray();
    }

       /*    *
     * Use the      Canonicalizer to seria    li    ze the node
          * @param node
         *      @return the canoni  calization of   th   e nod      e
      * @throws Excep tion
            */
    public String      canonSerialize(Node no    de) throws Exception          {    
              B     yteArr   ayOutput    Stream baos    =   n   ew       ByteArr  ayOutputStream();
              canon.setWriter(baos);
              canon   .no  t Re    set();
            canon.  canonicalizeSubtre e(node  );
        String        r et = b         aos.t oString  ("UTF-8");
        b      aos.reset()   ;                     
                 return ret;
    }

    /**
        * Use the    Canon    icaliz er to  serialize the node
     * @p  aram n  od   e
         *      @return the ( byte[]            ) canonica     lization of t    he node
        * @throws Exce  pti    on
     */
    public by   te[] canonSerialize T      oByteA  rray(Nod   e node  ) t   hrows Except     ion {
                  ByteA      r ra    yOut    put    Str  eam   b    aos   = new B    yteArrayOutputStream();
          canon.s   etWriter(baos);
        canon.notReset();
        can   o n.cano ni    cal    izeSubtree(              n      o            de      );
        return b    aos.toByteA     rray();  
    }

      /**
            * @p   aram             so     urce
     * @p   a   r      am ctx
        *   @r      eturn the  Node r  e sulting fro m the p    arse   of the          s         ource
     *       @thr  ows XMLEncr yptionExcep   tion    
       */
              public abstrac    t Node             deserialize(String s     ourc    e   ,     Node   ct           x) th   row s XML        E  ncryp   t  ion   Ex   ception    ;
      
     /**
        * @param s   ource
          *        @para   m ctx
              * @  return the Node resul         tin   g from th e pa rse of the sour  ce             
                  * @throws            XMLEncryptionException
     */
    publ  ic abstrac t       Node            deser ializ  e(byte[]      so      ur   ce, Node ctx) thro   ws X  M  LEn   c   ryptionExcep   t        ion;  

    prote    cted st   a    t      ic byt e  [] c reateContext(byte[]            so  urc        e, Node c   t       x) th  ro  ws XMLEncryptio     nExc       eptio  n {
              // Cr          e  ate the context to pa rse the document a gainst
                 ByteArra  yOu     tput    S     trea m byte       ArrayOutputS    tr           eam = new         ByteArrayOutpu  tStream();
        try {
                     O   u    tputStreamWrite            r  outputSt    reamWriter = new     Out    put    StreamWriter(byteA        rrayO         ut  putStr eam, "UTF-8");
                                outputStr  eamWriter.w  r    ite("<?xml v      ersion= \"1.0\" encoding=\"UTF-8\    "?   ><dummy");

                          / / Run throu    gh each node   up to the do     cu     me  nt        no    de and fi nd                any xmlns: nodes
                     M                          ap<Str    ing   ,   String  > storedNames  p   aces     = new HashMap<String, St                  ri            ng>();
              No     de    wk = ct     x   ;
               w     hile (  wk != null) {
                       Nam   edNodeMap atts =   wk.getAttributes()  ;
                                           if (att                         s !=     nu ll) {    
                             for (int    i = 0   ; i    < atts.g  etLength(); ++i)       {
                                      Node att = att   s .item(i);
                                            String  node   Name    = att.get              NodeName()  ;
                           if ((nodeName.eq     uals("xm                         lns") || nodeName.s   ta    rts With("xmlns:")          )    
                                              && !storedN amespac      es.cont     ainsK   ey(     att.getNodeName())   )     {
                                                          outputStreamWriter.  write(" ");
                                                   outputStrea    mWrit   er.  write(no deName)    ;    
                                                              outputStreamW   ri ter.write("     =\"");
                                          outputS     treamWriter.write(att.get           N odeVal    ue ());
                                            outp utSt   reamWriter.write("\"");    
                                                   s    toredNamespa ces.pu t(       node    Na   me,     att.getNodeVa    l       ue());
                                                           }
                                    }
                         }
                  wk = wk.getParent  Nod e();
                  }
            outputStrea mWri   ter.         write(">");
            outputStreamWri     ter.f  lush();
                     byteArrayOutputStream.write(sou    rce);

            outputStreamWriter.write       ("</dummy>");
                 outputStream  Writer.c    lose();  
  
            return byteAr           rayOut  putStream.toByteArray();
        } catch (UnsupportedEnco     d   ingException e) {
            throw new XMLEncryp    tionException        ("empty  ", e);
             } catch   (IOException e) {
               thro   w new XMLEncryptionException("empty", e);
           }
    }

    protecte       d st  atic String createContext(   St    ring source, Node ct      x) {
        // Creat  e the context to parse the document against
          StringBuilder sb = new StringBuilder();
           sb.append("<?xml versi         on=\"1.0\" encoding=\"UTF-8\"?><dummy");

        // Run thro   ugh each node     up to the document n      ode and find any xmlns: nodes
        Map<String, String> sto     redNamesp     aces = new HashMap<String   , String>();
            Node wk = ctx;
        while       (wk != null) {
              NamedNo  deMap atts = wk.getAttributes();
            if     (at     ts != null) {      
                for (int i = 0; i < att s.getLength(); ++i) {
                         Node att = atts.item(i);
                    String nodeName = att.getNodeName(); 
                    if ((nodeName.equals("xmlns") || nodeName.startsWith("xmlns:"))
                        && !storedNamespaces.containsKey(att.getNodeName())) {
                        sb  .append(     " " + nodeName + "=\"" + att.getNodeValue() + "\"");
                        storedNamespaces.put(nodeName, att.getNodeValue());
                    }
                }
            }
            wk = wk.getParentNode();
        }
        sb.append(">" + source + "</dummy>");
        return sb.toString();
    }

}
