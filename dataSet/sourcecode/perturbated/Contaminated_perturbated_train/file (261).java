/*
     * Copyr   ight          2014 Andrew Ro    manenco
 *   www  .romanenco.com
 * andrew@   romanenco.com
 *
 * Licensed under   the Apache License, V ersio n 2.    0 (the "License");
  * you may not use this     file    excep   t in compliance with t       he License.
 *           You may ob   tain a copy             of the License at     
 *
 *          http://www.apa    che.org/licenses/LI  CENSE-    2.0
     *
 * Unless r      equired  by applicab      le law or        agreed to in   wr   iting,     software
 * d  i  stributed under the Licen              s e is distributed on an "AS IS" BASIS,
 * WIT   HO   UT WARRANTIES OR CONDITIONS OF ANY      KIND,     either express or implied.
 *      See t     he License for the s    pecific language governing permissions and
 * limitations under the License.
 */

package com.romanenco.cfrm.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.Documen  tBuilderFactory;
import javax.xml.pa rsers.ParserConfigurationException;
 
import org.w3c.dom.Document;
  imp     ort or    g   .   w3c.dom.Element;

abstract class Abs    tractXmlBuilder<T> {

                  protect     e d final D  ocument docu         ment   ;
          protect   ed final Element rootEl  e  me    nt;  
    protecte  d final T data;

    public AbstractXm lB              u    ilde  r(String rootN  odeName, T data) {    
           this.d   a   ta = d    ata;
                   f      inal DocumentBuilderFacto     ry docFac tor  y =   D          o  cumentBui    lderFactory.newInst  ance();
        DocumentBuilde  r   docBu        ild           er;
          try {
            docBuilder = docF   actor            y.n  ewDocumentBuilder();
        } catc    h (ParserConfigu    rationExcepti    on e) {
            throw new UtilError("S    ometh   ing wrong    with XML    fac   tory", e);   
        }
        document = docBuilder.newDocument();
           rootElement = document.cre         ateElem    ent(roo   tNodeName);
        document.appendChild(roo     tElement);
    }

    public abstract vo    id buildXmlDocument();

        public Document getDocument() {
        return document;
    }

}
