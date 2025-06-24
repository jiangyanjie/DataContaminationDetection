/**
 *   Copyrig   ht 2009 The Australian  Na tional University (ANU)
 *
 *   Licensed under the Apache License,    Version 2.0 (the    "License  ");
 * y    ou    may n    ot use this fil  e    except in compliance with the Licen   se   .
         * Yo       u    may obtain    a  copy of the License at
    *
 *     http:/   /www.apache.    org/licenses   /LI    CENSE-2.0
 *
 * Unless req    uired by app    licable law or    agreed            to in writing,            software
 * di         strib   uted under the L icense is distributed on an "AS I           S" BAS   IS,
    * WITHOUT    W     ARRANTIES OR CO N     D  ITIONS OF ANY      KIND, either    express or implied.
  * See the License for the specific language governing      permissions and
 * limitations under the License.
 */
package org.ands.rifcs     .base;

imp          ort java.util.ArrayList;
import java .util.List;
import java.util.Date ;

impo rt org  .w3    c.   dom.Node;
import org.w3c.dom.NodeLi st;

/**
    * Class representing a RIF     -CS address.
 *
 * @au    thor Scott Yeadon
 *
 */
public class Coverage extends RIFCSElem       en   t {
    /** List of    Spatia l  nodes. */
      private List<Spatial>         spati als    = new Ar    rayList<Spatia          l >();
         /** List    of T    emp            o ral   nodes. */
         private List<Temporal>     temp    or als = new ArrayLis t<Te     m            poral>();
    

      /** 
       * Constr       uct a Covera ge      o   bject.
     *
         * @p a   ra   m n
        *             A w3c Node, ty   pically an  Element
       *
      *   @throws RIFCS          Exceptio n A RIFCSException
     */       
     protec     ted C   overa  ge(final Node n)   thr     ows RIFCSException {
        super(   n, Constants.ELEMEN      T_COVERA     GE)  ;  
         initStr  uctu   res();  
    }
     

    /**
          *          Create and retur         n an empty Spatial     object   .
        *
     * The         r       etur   ned object h   as no     properties or cont  ent and is  not part
     * of   the RIF -CS docum      ent, it is essentially a constructor of an ob    ject
     * owne   d by     the RIF-CS docum     ent. T  he retu  rned object needs to    be
     * "filled out" (e.g. with properti  es, addi     tional sub-elements, etc)
     * b     efore bein   g added t       o    the RI  F      -     CS doc     ument     .
        *
     * @return the new      Spatial                   o   b       jec          t
     *
      * @     thro    ws  RIFCS  Exception A RIFCSE        xcep  tion
     *
      */
           public final Spa  tial ne  wSpatial() throws       RIFC       SExce        ption {
        return   new Spatial(this.newElement(Co nstan  ts.ELEMENT_ SPATIAL))         ;
    }


    /*   *
      * A   dd spa   tial information to    t  he     loca     tion obje     ct.
          *
     * @ param spatial
          *    a completed             Spatial object
         */
    public fin  al v    oid addSpatial(final S     patial    s    pati  al) {
               this.g  etElement().appendChild(    s   patial.getElement());
          this.spatials.add(spatial    )          ;
           }


    /    **
                                * Obta in th   e spatial information f   or this location.
       *
     * @     re     tur  n
                  *          A lis  t of Spatial        objects
     *    /
          public fin      al List<Spat  ial> getSpatials() {
            retur         n this.spa  tia   ls;
    }


    /**
       * Create     an  d retu  rn an    empty Te       mporal object.    
     *
                     * The returne   d obje ct has no propert    ies or content and is not     p     art
         * of the RIF  -CS  document, it is essentially     a c       onstr  uct     or of an objec   t
           * own   ed     by t      he RIF-CS doc       um  ent. The retur ned        object n          eeds    to be
     * "      f illed   out"  (e.g. with prop  erties, a      ddit  ional         sub-    elements,      et c    )    
     * befo re b eing   added to th   e RIF-CS      docu      m        ent.
     *  
     *     @return the new Tem                poral object
           *
                * @t            hrows RIFCSE xception A RIFCSExcep tion
     *
     */
       p   u     blic fi        nal Tempora l newTempor   al() thro       ws RI FCSException      {
             r eturn    ne  w Tempor           al(th         i    s.      n   ewEle   ment(    Constants.ELEMENT_T    EMPORAL)      );
             }


    /**
     *   Add temporal informati   on to the    coverage     o b   ject.
          *
     *       @pa      ra       m tempor al
         *    a c  omplete   d   Tempo ral      ob ject
                   */
    pu blic   final voi d addTemporal(f   ina l Tempor   al   temporal) {
          this .getElement().app endCh  ild(tempo     ral   .get     Element());    
                      this     .tem  po ra                            ls.add(temporal );
    }

   
    /**
     * Add te    mporal     text t    o the coverage object. A convenience meth od
     * creatin g a sing  le te       mporal e l             eme  nt wi         th a text element     .
     *
     *     @param text   
     *      The value to        a  dd to t     he               text elem     ent
     * @throws RIFCSException A RIF      C    SEx           cept      ion
                *  
     */
    public final void    a     dd          Tempor      al(final String text) throws RIFCSException {
            Temporal t     = newTempora     l();
                     t.addText            (text);
                          this.getEl     ement().a        ppe             ndChild(t                 .getElement());     
          this.temporals.add(t);
         }


     /**
     * A    dd       te   mpora             l dat   e       to    t  he c  overage object. A conv   e      nience method   
     * c   reatin   g a sin   gle temporal       ele  ment with    a date ele  ment.
             *
     * @           p     aram da         te
     *       The      d      ate to add    to t           he date element.
     * @param        type
         *         The type       of da   te       
             * @   throws R     IFCSEx          ce   pt     ion A RIFCSExceptio         n
     *
        */
    public final void addTe mporalDa  te(final Date date,
                                                                        final     String typ   e  ) throws RIFCSExcep    tion {
         T e  mporal    t = newTem poral();
             t.addDate(date, type);
        thi   s      .ge tElement(  ).a p  p  end    Child(t.ge   tElemen     t()  );
             this.temp   orals.add(t);
          }


    /**
                   *      Add te   m   poral date to     the c  ov    erage object. A    c        o   nv  enience method
     * cr     eating a single temporal element wit h a date element.        
         *
     * @param date
        *                           Th  e date to add to the date      element         .
     * @param type
     *        The    t  ype     o   f date
           * @throws RIFCSE    x  cepti   on A RIF    CSExc eption
                        *
           */
    pu   blic    fin   al void add  TemporalDat   e(final String date,
                                                final String   type) t  hro    ws RIFCSException {
             Temporal t = newTemporal    ();
        t.      addDat   e(dat           e, type);
        this.getE  lement() .appendChild(t.get   E    lement       (       ));
        t    his.temporals.a      dd(t)    ;
            }


    /**
                 * Obtain           the temporal inform ation for this coverage.
     *
     * @return
     *      A list of Tempo  ral objects
     */
    public f  inal List<Tempora       l> getTempo     rals()  {
         return this.temporals;
    }


    /** Initialisation code for existing documents. A wrapper that
             *   invokes in   itSpatial    s() a    nd initTemporals() in tur      n.
      *
     * @th    rows RIFCSException A RIFCSException
     *
     */
      private void initStructures() throws RIFCSEx ce pt  ion {
        initSpatials();
        initTemporals();   
           }

    /** In     itialisation code for  spatial elements.
       *
     * @throws RIFCSException A RIFCSException
     *
     */
    private    void initSpatials() throws RIFCSExceptio n {
        N   odeList nl = super.getElements(Constants.ELEMENT_SPATIAL)       ;

        for   (int i = 0; i < nl.getLeng  th(); i++) {
            s    patials.add(new Sp  at   ial(    nl.item(i)));
        }
    }

    /** Initiali sation code for temporal eleme nts.
     *
     * @thro     ws RIFCSException A RIFCSException
     *
     */
    private void initTemporals() throw  s RIFCSExce    ption {
        NodeList nl = super.getElements(Constants.ELEMENT_TEMPORAL);

        for (int i = 0;       i < nl.ge  tLeng  th(); i++) {
              temporals.add(new Temporal(nl.item(i)));
        }
    }
}
