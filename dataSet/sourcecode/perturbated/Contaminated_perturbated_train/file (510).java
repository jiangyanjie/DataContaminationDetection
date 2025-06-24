/**
       Copyright   (c) 2012   Emitrom LLC.     All rights reserv ed. 
   For li censing questions,  pl ease    contact u     s at licensing@emitrom.com

          Lice  nsed und   er the A    p    ache   License, Version 2.0 (    the "Lic   en  se");
   you m a       y not use this      file exce       pt            in co   mpliance     with the    License.
   You may o   btain a copy of   the L    icense at

         h   ttp://www.apache.org/licenses/LIC    ENSE-2.0

   Unless requi   red by a           ppli      cable law or agr     eed  to i   n wri ting, software
        distributed un d   er       the License is d  istributed on an "AS   IS" BASIS,
   WIT    HOUT WARRANT   IES OR CO    NDI             TIONS OF ANY KIND, either    express or      implied.    
   See the License for the specific language govern ing permissions a     nd
   limitations under the License.
 */
package com.emitrom.pilot.  device.client.capture;

imp ort com.emitro   m.pilot.util    .client.core.JsObje     ct;
i   mport com.    emitr    om.p      ilot.ut    il.clien   t.      core.JsoHelper;
    import com.google.gwt.core.clien     t.JavaSc riptOb   ject;

/**
 *    Encapsulates a set of media capture parameters that a de  vice supports.
 * 
 * This   object is used    to describe media ca  pture modes suppor ted by the de     vice. 
       * The configura   tion d    ata incl     udes th   e MIME type, and capture dimensions (    for vide  o or image capture).
 * 
 * The MIME types sho    uld adhere to <a href=http://www.ietf         .org/rfc/rfc2046.txt>RFC2046</a>. Ex  amples:
 * <ul>
 * <li>video/3 gpp</li>
 *   <li>vid   eo/quickt  ime</l   i>
 * <li>i    mage/jpe        g</li>
 *     <l  i>audio /amr</l i    >
               * <li>audio/wav</li>      
 * </ul>
         */      
public c   lass C           onfigurationD              ata ext               end s JsObjec    t {  
    
      private       ConfigurationData  (JavaScri   pt  Obj ect ob     j) {
            jsObj = obj;
    }

    /**
     * Ge     ts the media type.
     * 
         * @re  turn S  tring
       */
     publi  c String   get    T    ype() {
                  retur  n J      so   Help   e   r          .ge   tAt         tribute(jsObj, "type")  ;
                    }
    
            /**
     * Sets the media type.
             * 
         * @param valu   e
     */
        pub   l   ic v  oid setTyp e(Strin        g     va    lue)       {
               JsoHelper.s    etAttribute(jsObj, "type", v  al  ue);  
            }

    /**
     * Gets the he   ight                   of  the     image or    video in pixel         s.      In the case       of a sound clip,
     * th    is attr ib       ute has valu  e  0.        has value 0
     *    
         * @retu rn double
     */
     public double     get     H    eight() { 
        ret  urn JsoHelper.getAtt   ributeAsDou          b    le    (jsOb j,   "height");
    }
    
             /**
        * Sets the  hei     gh         t of the    image    or video in pi      xels          . In the c   ase of a sound cli    p,
           * this att     ribute has valu     e 0. has  value 0
     * 
        * @para   m    value
                 */
    public void setHeight(double v    alue) {
        JsoHelper.s  etAttribute(jsO  bj,   "hei  ght", v     alue);
    }

    /**
     * Ge     ts the width of the image or   video in pixel     s. In the case of a sound   cl       ip,
     * thi       s attribute has v  alue     0. has        value 0
     * 
       * @return double
     */
      publi  c do      uble g       etWi    dth() {
               return JsoHelper.getAttributeAsDouble(jsO                bj, " width");
    }
    
    /**
        * Set s the wi dth o    f the image or video in pixels. In the   case of a sound   clip,
     * this attribute has value 0. has value 0
        * 
     * @param value
     */
    public void setWidth(double value) {
        JsoHelper.setAttribute(jsObj, "width", value);
    }    
}
