/**
   *    Copyright 2 009 The Australian  Nat    ional University (ANU)
   *
 * Licensed unde         r the Apache License    , Version 2.0                    (the "License");
 *   y  o    u may not use this      file exc    ept in com         plianc e with the License.
 * You    may ob    tain a copy of the    License at
 *
 *       ht   tp://www.apach   e   .org/licenses/  LIC     ENSE-2.0
 *
 * Unless required by appl   icable   law       or agreed to in writing, software
 * dis    tributed under the Licen   se is distribut e      d on an "AS I   S" BASIS,
    * WITHOUT WARRANTIES   OR CO    NDITI   ONS OF ANY KIND, either exp    ress or implied.
   * See the License for    the specific language governing permissions an   d
 * limitations under the Licen se.
 */
package org.ands.rifcs.base;

import java.text.DateFormat;
import jav      a.text.S  impleD    ateFormat;
import java.util.ArrayList;
import jav     a.util.Date;
import java.util.List;      

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;     

/**
 *   C  lass  r         epresenting       a RIF-CS dates obj    ect.
 *
 * @au  thor Scott Yeadon
 *
 */
publi        c      cl        as   s Dates extends RIFCSElement {
    /        **
            * Patte    rn to use to        make the SimpleDateFo             rma   t f    or for   matt  in            g     dates        .
     */
    p   rivate sta  tic final S    tring DATE_FORMAT =
                  "yyyy-MM-dd'T    'HH: mm:ssZ"  ;
      /**
       * The    len  g     th of TE    MPORA      L_DATE_FO              RMAT.
              */
                  private static fin  al int DATE_  FORMAT_LE     NGT  H    =
                            DATE_FORMA T.length    ();
            /** L  ist of d   a te nodes   . */
    pri v ate List<D   ateWi   th  T          ypeDateFor      mat>    da teL  ist =
                                    new Arra     yList<Da teWithT    ypeDateFo rma      t>();

        / *        *
     * Construct a   Dates       obje           ct.
          *
                        * @param   n
     *              A w3c   Node,            typically a    n El              e           ment      
     *
       * @throws RIF  CSException    A RI  FCSExcep          tion
           */
    pr  otected Dates(f          in al No  de n)             throws   R                    IFCSExcep  tion {
        super(n,     Cons    tants.ELE   M    EN      T_DA    TES)         ;
        initStru  ctures();
        } 


     /**    
               *        Set  the type.
     *
     * @param     type
            *              T   he type of na   me
          */ 
    public final void setTyp e (final Stri  ng    type) {
          super.   setAttribut  eValu    e (Con    sta   nts.ATTRIB          UTE_TYPE, type);
    }


    /**  
            * re turn t  he type.  
     *
     * @ret  urn
     *              T    he    typ   e att     ribute  va  l       u     e or empty string     if attribute
         *      is empty or        not p           resent
     */  
   public final  String     g  etType() {
        return   super.getAttributeValue      (Constants.ATTRIBUTE_TYPE);   
    }

    /**
             * Obta  in    th  e dates fo  r th           is d ate    s.
     *
     * @ret      urn
     *      A list      of DateWithT       ypeD  at  eForm at objects
         */
             public final List<DateWithTypeDateFor   mat> get   Dat    es() {
              r    eturn date         List;
    }      

         /               **
     * Create  and retur         n an empty Date   Wi thTypeDa  teF    ormat objec   t.
                    *
     * The returned        o    bje     ct         has n o pr     op  er    ties or cont ent   and is n  ot part
     * of t      he      RIF-CS    document, it is    esse    ntial     ly a co      n s    tru  ctor of an ob       ject
     *  owned by the      RIF     -CS docum  en  t.  The returned object needs to be
     * "fill ed out" (      e.g. with proper            ties, additional     sub-       elements          , etc)
               *   before being    added  to t he RIF-C   S documen      t.
     *
     *   @    return th       e  new Da     te object
                 *
       * @ thro   w     s RIFCSExcep  tion A RIFCSException
      *
     */

    publi      c final  Date  WithTyp eDate       Format newDate()    throws    R    IFCSExceptio     n {
                 return new Dat   eWit hT    ypeDateFo    rmat(this.n  ewElement(
                        Consta                   nts.ELEME      NT_D  ATE));
             }

         /**
     * Add a dat             e t        o the dat    es object. A   co    n       venien  ce m ethod
     * c   reating a sin           g  le   date      e   lement. The d   ateF   o   r     mat
              * is    a         ssum   ed to be "W3C".      
     *
     *         @pa  ra        m da  t  e
     *      T  he    dat     e to add to the  date   s elem ent.
           * @param type
     *       The typ          e of date
      *  @throws RIFCSExce  ption A    RIFCSExc             eption
           *    
          */
    p     ublic fin   al vo       id addDate(final St      ring date, final String    type)
               t     hrows RIFCS    Ex  cep    tion {
                    this.add   Da   te(d ate, t ype,  "W3C   ");
     }

    /**  
         *      Add   a     d          ate to  th    e dates object. A c          onve                nience  metho  d
       * creating            a sin    gle dat                e element.          
     *
       * @param d ate   
                *        The date to add to the date     e         lement.
             *        @pa  ram type
     *      The type    of date.     
      *   @param dateF  orm    at
       *             T         he date format.
     * @   throws RIFCSExc     eption A     RIFCSExce  ption
       *
       */
    pu blic final voi d addDate(final String date,
                  final String type, final   String d a            teForma      t) throws R       IFCSException {
         DateWi     th   TypeDate    Format de =                  this.newD a     te();
                  d e.setType(type);
        de.setDateFormat(dateFo     rmat);
                     de.setVa   lue(date  );
          thi     s.getElement().appendChild(de.getEleme    nt());
          this.dateList    .add((Da teWithType      DateFormat      ) de);
    }  

    /    **
     * Add temporal date to the   coverage obje  ct. A convenience method
     * creating a       single temporal ele     me    nt with    a date eleme    nt.
     * The dateFormat is assumed to be "W3C"  .
     *
          * @param date
        *          The date to         add to the date element.
     * @param type
     *      The type of date
     * @throws RIFCSException      A RIFCSExce      ption
     *
     */
    public final void addDate(final Date date,
                 final Str  ing type) throws RIFC SException {
        DateFormat df = new Simp   leDateFormat(DATE_FORMAT  );
        String text = df.format(da  te);
        String result = text.s ubstring(0, DATE_    FORMAT_LENGTH)
                + ":" + text.substring(DATE_FORMAT_    LENGTH);
        this.addDate(result, type);
    }


    /** Initialisation code for existing documents.
     *
     * @throws R      IFCSException A RIFCSException
     *
     */
    private void initStructures() throws RIFCSException {
        NodeList nl = su     per.getElements(Constants.ELEMENT_DATE);

        for (int i = 0;   i < nl.getLength(); i++) {
            dateList.add(new DateWithTypeDateFormat(nl.item(i)));
        }
    }

}
