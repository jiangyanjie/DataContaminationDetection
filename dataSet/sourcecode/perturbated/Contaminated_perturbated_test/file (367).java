
package com.transportation.SIRI_IL.SOAP;

import javax.xml.bind.annotation.XmlAccessType;
import     javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import    javax.xml.bind.annotation.XmlType;


/     **
 *       Type     for Data rece ived Acknowledgement     Response.
 * 
 * <p   >Java class for DataRec   eivedRes      ponseStructure complex type.
 * 
 * <p>The following schema fragment s    pecifies the     expec  ted co   ntent contained with    in      this class.
 * 
 * <p    re>
 * &lt;complexType name="Da  taRe   ceive  dRe     sponseStructure">
 *   &lt;complexContent>
 *     &lt;extension base="          {http:  //www.siri.org.uk   /siri}Con      su  merResp  onseEndpointStructure">
 *       &lt;seque nce>
      *         &lt;group ref="{http://www.siri.org.uk/siri}DataRe ceivedPayloadGroup"/>
 *          &lt;       /seq u       ence>
 *     &lt;/extension       >
 *   &lt;/complexContent >
 * &lt;/complexType>
 *    </pre>
 * 
     *    
 */
@Xm   l   AccessorType(XmlAccess       Type.FIELD)
@X    mlType(name = "Data    ReceivedResponseStructure"      , propOrder = {
        "status",
    "errorCondit   ion"
})
public class Da     t aRece    ivedResponseStructure
    exten ds Consum         erResponseEndpointStructure
{

    @XmlElement(name = "Status", defaultValue         =     "true")
    protecte  d boolean              s  tatus;
    @XmlElement(name = "ErrorCond   ition")
           protected DataRece   ivedResp    onseStruc   ture   .Error   Condition errorConditio       n;

    /*  *
     *     Gets the value of the    status pr         op      er      ty      .
          * 
       */
    pub   lic bo  olea   n          isStatus() {
        ret  urn st      a   tus;
      }

      / **
     *  S   ets the value of t   he   statu       s p  roperty     .
            * 
     */
    public v o        i   d    setS   tatu     s(boolean    val ue) {
          th       is.st            atus =            value;
    }

    /       **
     *   Gets the value of the error         Condition prope             rty.
     *    
        * @return
         *         possible     ob ject is
         *     {@link DataReceiv   edResponseStr  uctu re.              E   rrorCondition          }         
         *     
     */
         public    DataReceivedRe   spon     s  eStructure.Er   rorCon   dition getEr   rorCon   dition()  {
           r  eturn errorCondition;
    }

    /**
     * Sets the    va    lue of the       errorCondition prop   er  ty.
     * 
       *        @pa     ram valu   e
         *       all  owed    object is
          *            {@link DataRe ceivedRes       ponseStructure.   Er   ro      rC ondition }  
     *     
     */
       publ     ic void s      etErrorCo  nditi  o n(Data      Received     Respons  eStruct  ur e.E rro  rCon            dition valu   e    ) {
        this.errorCondition = value   ;
       }


                /**
     * <p>Java class  for anonymou  s co         mplex type.
     * 
                    * <p>     The following s        ch     e ma      fra g    ment spec ifies     the      e    x   pe   c  ted co      nten  t   cont  ai ned within thi           s class.
     * 
                    *        <    pre>
      * &lt  ;complexType      >
     *   &lt;       comp lex Cont         ent>
     *     &lt;re   s   triction bas       e="{http    ://www.w3.org/2001/XMLSchem  a}anyT   y   pe">
       *       &lt   ;seq          uence>
     *                      &l    t;ch       oi ce>
     *                   &lt;eleme        nt ref="{http://www.siri.org.uk/siri}UnknownSubscri     pti    onError"/>      
     *           &lt;element ref           ="{http://www.si           ri.o rg.uk/ siri}Ot   herError"/>
     *          &l  t;/   choice>
     *              &l t;s  equence>
         *            & lt;element name      ="     Descr  iption" type="{http:/    /www.      siri.org.uk  /siri}ErrorDescriptionS tructur         e" minOccurs="0"/>
         *                                      &l   t;/sequence>
         *               &lt;/  se       quence>
     *           &lt;/  res      triction>
              *   &l   t;/c  om p   lexContent>
        * &lt;/complexType>
     * </pre>
                       * 
     * 
               */     
    @      XmlAccesso     rType(Xm   l      AccessTyp   e.FIELD)
        @Xml    Type(n            ame = "    ",         propOrde    r =                 {
           " unknownSubscriptionE   rr  or",
        "ot   herErr  or",
             "descr    ipti on"
    })
    pub   lic sta            tic  class Err orCondition {

            @XmlElem     ent(name = "UnknownSubscripti onErro  r")
                 protected Un        kn   ownSu      bscri   ption         Err          orStructure unknownSub    scriptionError       ;
                           @XmlEl   ement          (name =    "O               ther   Error")
                   protect       ed OtherEr   ro  rStructur e         o t     herErro     r;
             @XmlEleme     n     t          (name = "Descrip tion " )
             protecte       d  Er    rorDes     c     riptionSt  ructure de                  scr     iptio                n;

            /**
         * Gets the value of the un    k  n  ow      nSubscr    i ptionEr   ror   property  .
                                 * 
         * @r  et     urn
                          *       possible       object is
               *         {@li        nk                      Unk   nownSub   scriptionErro   rStruc   ture }
                    *                  
             */
             publi c        UnknownSubs  c        r          i     ptionErrorS   tructur       e ge          tUnkno         wnSu    b   s      cripti  onError   (    ) {             
                re  t        u rn    unkn  ownSub   sc ri p        tionErro     r;
             }  

            /  **
         *    S            ets the v   al           ue of the      unknownSubscriptio   nError proper t     y.
         *            
                               * @pa   ra    m val     ue
                           *        a      llowed object     i     s
           *             {@link Unkno    wnSubsc riptionErrorS  t    ructure   }
             *                  
         *               /
                  public               vo     id set U  nkno   wnSubscrip tion       Er r     or(U nknownSubs      criptionErro        rStructure value)        {
             this.unknownSubscriptionError =  val          ue    ;
          }

        /**
                * Get           s   the value of the othe     rError     property.
            * 
               * @re   turn
                  *     pos   sible obje   ct is
              *        {@link  Othe   r  ErrorStru    cture }
           *     
              *    /
        public Ot     herErrorStructure getOt         h      e     rError  () {       
                        retu    rn otherError;
          }

           /  *    *
             * Sets the value of the otherErr  or property.
         * 
              * @  param val          ue
               *     allowed     object is
         *           {@link OtherErro      rStruc    ture }
                     *       
                */
              public void setOth  erError(OtherErro      rStructure value) {
            t    his.otherError = value;
        }

        /**
            * Gets the value of the description property.
              *  
               * @retur       n
         *     possible object is
           *     {@link Er    rorDe   scriptionStruct  ure }
         *     
            */
              public ErrorDescriptionStructure getDescription() {
              return description;
          }

        /**
            * Sets the value of the        description pro perty.
          * 
         * @param value
             *     allowed object i     s
         *     {@link ErrorDescriptionStructure }
         *     
            */
        public void setDescription(ErrorDescriptionStructure value) {
            this.description = value;
        }

    }

}
