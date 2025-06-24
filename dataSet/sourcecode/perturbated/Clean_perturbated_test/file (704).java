
package org.kuali.rice.location.v2_0;

import   java.util.ArrayList;
import java.util.List;
impo        rt javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorTy     pe;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlEleme      nt;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
   *      <p>Ja     va   class for CountryQueryResultsTyp    e complex type.
 * 
 *  <p>T     he f  ollowing schem a fragment specifies the ex pected content contai  ned   within this class.
 * 
 * <pre>
 * &lt;comple    xType name="CountryQueryRes    ultsType">
 *   &lt;complex  Conte  nt>
 *     &lt;restricti    on base="{http://   www  .w3.org/2001/XMLSchema}an      yType">
   *         &lt;sequence>
 *                       &lt    ;element    name="resul    t   s" minOccu   rs="0">
 *                     &lt;co  mplexTyp       e    >
 *                             &l  t    ;c  omplexContent>
      *               &lt;restr ict  ion base="{http://www.w3.org/      2001/XMLSche      ma}anyType   ">
        *                                            &lt;sequen      ce>         
 *                   &lt;element            name=   "Country" typ        e ="{      http://rice.kuali.   or  g/  l        oca      tion/v2_0}Count  ryType" maxOccur      s="unbounded" m    i nOccu  r      s="0"/>
 *                             &l     t;/sequence>
 *                 &lt;/restr    ict ion>
 *                  &lt;/complexCont   ent>
 *           &lt;/  compl      exType>
 *                 &lt;/      element   >
 *         &lt;element name="totalRowCoun     t" type="     {http://www.w3.org/2001/XMLSchema}int" min  Occurs="0"/>
 *         &lt     ;element nam  e="moreResultsAvail     able" type="{http://www.w3.org/       20  01/XML      S   chema}boolean         "/>
  *         &lt;any processContents='skip' namespace='##other' maxOccurs="unbounded" minOccurs=       "0"/>
 *               &lt    ;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAcc essorType(XmlAc    c     essType.FIE  LD)
@XmlType(nam   e = "CountryQueryResults    Type", propOrder      = {
          "resu       lts",
         "t               otalRowCou  nt",
         "moreRes     ultsAv  ailabl e",
       "any"
})
public clas    s Cou        nt   ryQ ueryResultsType   {

               protect      ed CountryQuer    yResultsType.Result    s re   sults;
    p r   o        t             ected I n     teger totalRowC        ount;
    p    r  o     tecte     d b    oolea   n mor                 eResult sAvailable   ;
    @XmlAny   El    eme n    t  
    p      r      otect        ed L   i   st<Element>   any;

              /**
     * Gets        the valu        e of the results    property.   
     * 
     * @r     eturn
              *             po   ss  ib   l    e object is
     *     {@link     Coun   tryQueryR  esul   tsType.Results }  
      *                          
     */    
    public Cou    ntryQue       ryRe    sult       sType.    R    esu             l     ts ge   tR  esul  ts  ()    {
                return           result  s;
        }  

    /**   
     * S   e    ts t h    e v             alue of th         e re            sults propert y.
         * 
            *         @param    value
              *               al   l   owed o    bje     ct is
     *     {            @l   i   nk  Coun    try  QueryResultsT  y  pe.R     es  ult     s }
          *         
           */
     p               ublic v      oi       d setR  esu  lts(    C     ou  ntry  Quer    yResultsT        ype.Resul  t s value) {
                  t    his.results =   va  l   ue;
    }

      /**     
     *        Gets the value of      the tota   lRowCoun            t proper     ty.
             * 
         * @return
                  *     possible     object is    
           *     {@  link In  teg  er }          
     *     
     */
    pub  lic Integer getTotalRow  C     ount() {
               return   totalRowCou   n    t;
          }

    /**
     *   Sets the value of the                   total       RowCou          nt      pr  operty       .
     * 
         *     @param    value
                     *     al  lowed object is
     *              {@link In     teger        }       
         *       
          */
    publ ic void  setTot      alR        o wCou  nt   (Integer value) {
           this.tota   lRowC    ount = val   ue;
          }

            /**
         * Gets t  he value    of the mor     eRes    ults     Av           ailab l    e pro   perty.           
     * 
     */
    p            ublic bo     olean              i         sMo  reRe      sultsAvailable() {
                 re   turn moreResultsAvai lable;        
    }
  
    /**
     * Sets the value of the moreResultsAvailable pr      operty.
     * 
     */
         p   u blic void setMoreR    esu   l     tsAvailabl   e(boolea   n        valu    e) {
              this.m  or  eResultsAv       a  ilable = va  lue;
           }

    /**  
     * Get         s the value of     the any prope rty.
        * 
     * <p>
        * This accesso   r me    thod returns    a reference to the live list,
     * not a    snaps     hot.    Th   erefo  re  any modi  fic a       ti on yo   u make     to       the
       * r   et   urned list will    be presen  t insi               de th  e JA   XB object.
       * This is why        th      ere is not a <CO      DE>set<   /CODE>    method    for the any property.
     *        
            * <p        >
     * For   example, to a    dd   a n      ew item, do as follo  ws:
     * <p        re>
      *    getA ny()    .ad        d(newItem) ;
     * </pre>
        * 
        * 
         * <            p>
        * Obj             ects of    the        following t  ype(s)    are allowed   in the list      
     * {@link Element         }   
     * 
        * 
         */
    public          List <  Elem      e  nt> getAny() {
               if (any ==    null) {  
                               any = new   Arr  ayList<Element>();
          }
                  return this.any;
         }


       /**      
      * <p>    Ja  v   a             clas        s fo  r anonymous complex type.
          * 
     * <p >The f                           ollo wi ng sch    ema fragme  nt    sp  e    ci    fies th e            expected content conta   ined      within t   his    class .
     * 
     * <p   re>
            * &lt;c   omplexType>
     *        &lt  ;complexCo  ntent>
     *     &lt;re       strict ion base="{http://www.w3.org /2  001 /X   MLSchema}anyT       ype">
           *        &lt;se     quence>
     *           &lt;element name=    "   Country" type   ="{http://rice.kual    i.org/lo   cation/         v2_0}CountryTy             pe"       maxOc   curs="unb ounded" m       inOcc        urs="0"/>
     *       &l        t;/seque             nce>
            *     &lt;/restriction>
            *   &lt;/c   omplexConte       nt    >
     * &lt;/comp     le   xTyp  e>      
       * </pre>
           *     
             * 
           */
               @XmlAccessorTyp  e(XmlAcce   ssType.FIELD)
    @XmlType(nam  e = "" , propOrde       r = {
          "country"
          })
    public static        clas      s Results {

                    @XmlElem ent(name = "Country   ")
           protec     ted List     <Cou   ntryType>        country;

        /**
           * Gets the value    of th    e co untry pro   perty.
         * 
         * <p>
           * This accessor   me   thod retur     n      s a reference to       the live list,
            * n    ot a snaps       hot. Therefore any modification you make to the
         * ret  urn    ed li    st will be present   inside the JA  XB   object.
                  * This is why there is not    a <CODE>set</C   ODE> me    thod   for   the  country property.
         * 
         * <p>
             * Fo    r exa mple, to add  a new item, do as follows:
         * <    pre>
         *    getCou   ntry      ().add(newItem);
         * </pre>
         * 
         * 
             * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CountryType  }
         * 
         * 
         */
        public List<CountryType> getCountry() {
            if (country == null) {
                country = new ArrayList<CountryType>();
             }
            return this.country;
        }

    }

}
