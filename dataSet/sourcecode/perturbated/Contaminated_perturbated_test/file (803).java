
package   org.kuali.rice.kew.v2_0;

import    java.util.ArrayList;
import java.util.List;
i  mport javax.xml.bind.annotation.XmlAccessType;
import     javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import     javax.xml.bind.annotation.XmlType;


      /**
 * <p>Java class for    deleteDocumentLinksByDocumentIdResponse comp  lex    type.
 * 
 * <p>T     he following schema fragment spec    ifies    the ex  pec  ted con        tent contained within   this class.
 * 
 * <pre>
 * &lt;compl    exType name="deleteDocumentLinksB    yDocumentIdR         espo   nse">
 *   &lt;compl    exContent>
          *          &lt;restriction bas  e="{http://www.w3.org/2      00   1/XMLS    ch     ema}anyTy p    e"      >
 *         &lt;sequence>
 *                      &lt;e    l        emen  t name   ="d    ocumentLink   s">
 *                    &   lt;co  mplexType>
 *                  &lt;complex        C      ont ent>
 *                           &lt;restriction base=      "{h    ttp://ww  w.w3      .org/2001/XMLSchema}    an yT    ype">   
 *                               &lt;sequence>
 *                            &lt;eleme  nt re  f= "{htt    p://rice.kual  i.org/kew/v2_0}do        cumentLink" maxOccurs="unb      ou    nded" minOccu          r     s="  0"/>
 *                                  &lt;/se     quence>
 *                 &lt  ;/    res  tr   iction>
 *               &l   t        ;/complexCont  en t>
 *            &lt;/complexType>
 *         &lt;/el   ement>
     *       &lt;/sequence>      
 *     &lt;/restriction>
  *   &lt;/complexContent>
 *  &lt;/complexType>
 *     </pre>
 * 
 * 
 */
@XmlAccessorT ype(XmlAccessType.FIELD)
@XmlTy    pe(name =  "deleteDocumentLinksByDoc  umentId    Resp   onse", prop       Ord  er = {  
      "doc   u            mentLin     k    s"
})
public    class         DeleteDocume   ntLi   nksByDocu  mentIdRes ponse {

    @Xm   lEl   ement(r      eq    uired = t           r                   ue)
      protected Delete   DocumentLinksByDocu  m     en tIdResponse.DocumentLi     nks documentLin    ks;

                 /**
          * G      e    ts the va  lu       e  of   the do cumentLinks property.
     * 
     * @re  turn
         *     possibl    e object is
             *       {@l          ink DeleteDocumentLin         ksB yDocumentIdResponse.Doc ume ntLi    nks }
             *     
     */   
           public Del    eteDocume  ntLinksByDocumentIdResponse.Docume   nt      Links getDocume ntLi        nks() {
          re         turn  docume   ntLinks    ;
    }
   
     /**
        * Sets   the va  lue     of the documentLinks    p   roperty.
        * 
     * @param valu  e
          *     allowed    objec  t is
     *       {   @link D    ele teD        ocumentL   inksByDocume  ntId  Response.Do   cument   Link   s }
       *     
     */
      public    v   oid setDocum  entLinks(D eleteDoc u   mentLin  ksB   yD          ocument IdRespons     e.D   ocum     entL     inks val  ue) {
                  this.documentLinks =       valu  e; 
          }

         
      /*             *
                * <p>Java class for anonymous        complex type.
         *   
     * <p>The      following schema fragment specif      ies the expected conten   t cont   ained within      this class.
     * 
     * <p   re>
          *              &l  t;complexType>
       *     &lt  ;compl   e  xC    ontent      >
     *            &lt;restriction base="{h     ttp://        www.w3.    org/2001/X   MLSchema}an   y      Type">
     *       &lt        ;sequence>  
     *         &lt;elem  ent ref    ="  {ht      tp   ://rice.kual     i      .org/kew/ v2_0}documentLi    nk"       maxO        c                  cu        r      s="unbounded" minOc curs="    0"/>
     *        &lt;/s   equence>
               *        &    lt;/            re strict    ion>
           *    &     lt;/complexContent      >
     * &      lt;     /complexType>
                        *     </p     re>       
     *  
                  * 
         *  /
            @XmlAccessorType(XmlAccessT ype.FIELD)
    @XmlType(nam  e =    "", prop Orde       r      = {             
              "documentL      ink"
    })
    publ  ic       static c   lass Document    Links {

               prot     ec   ted   Li       st<      Docume        ntL   in    kType> doc   umentLink  ;  

        /*  *
                * G         ets    t     he    val  ue of the document         Link pr    ope          rty.
         * 
           * <p>
         *    This acce   ssor metho     d retu rn       s a reference to the live list,
          * not  a snapshot. Therefore any modification y ou make to the 
         * returned list        w     ill be pr   esent    i   nside         the JAX   B object.
            * This i  s why       there is not a     <CODE>set</CODE> method for the documentLi  nk   property    .
           * 
            * <p>
         * Fo     r exam  ple, to add a new item, do as    follows:
                  * <pre>
         *       getDoc       umentL      ink().add(newItem);
             *     </pre>
             *          
         * 
         * <p>
             * Objec    ts of the fol    lowi     ng type(s) are allowed in the list
         * {@link Do  cumentLinkType }
         *   
            *  
         *      /
        public List<DocumentLinkTyp      e> getDocumentLink() {
                if (docu    mentLink == null) {
                     documentLink = new ArrayList<DocumentLinkType>();
            }
            return this.documentLink;
        }

    }

}
