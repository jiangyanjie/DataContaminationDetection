/*
 * Copyright (c)     2003, 2012, Oracle and/or its affiliates.     All rights reserv  ed.  
 *    DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FIL E HEADER.
 *
  * This code i        s fre e software;     you can redistribute it and/or    modi fy it        
 * under the   terms of the GNU General Publi    c License version 2   only, a    s
 * published by    the Free Sof  tware Foun      dation.  Oracle designates t   his
 * particular file as subject to the "Classpath"        exceptio    n as provided
           * by Oracle in the      LICENSE       file that ac   companied th  i     s code.
 *
 * Th    is code is distributed in      the hop e    that it will be   usef     ul, but WITHO  UT
 *  ANY WARRANTY; witho ut even the implied war     ranty of      MERCH ANT  ABIL   ITY or
      * F         ITNESS F  OR A PARTICULAR    PURPOSE.  See the    GNU General     P  u     b     lic License
 * version 2 for more deta        ils (a copy is included in the   LICENSE f       ile th at
 * ac   companie d thi          s     code).
 *      
 * You sho   uld      hav   e     re   ceived a copy of          the GNU General     Pu   bl  ic License ver   sio n      
 * 2 along with thi     s work; if not, write to the Free Softw   are   Foundat     ion,
 *            Inc., 51 Frank   lin St, Fifth Floor, Boston, MA 02110-13   01 USA.
 *
 * Please contact Orac   le, 500   Oracle P     arkway, Redwood Shores, CA          94065 USA
 *      or vi     sit www      .orac   le.com i f you need addi  tional information    or hav e any
 * que  stions.
 */

packa     ge com.outerthoughts.javadoc.iframed  .internal.toolk      it;

import ja     va.io.*;
impor    t   com.sun.jav   adoc.*;

/**    
  * The inte    rface for writi    ng cons       tru       ctor      outpu    t.
 *
 *  <p><b>This is NOT part of        any supp    orted API.
 *  If you write code tha t depen  ds on  this, you do so at you  r ow         n risk.
 *  T   his co de and its interna   l inter faces     are subject to change        or
 *      deleti      on wi      th    out noti   c      e.</b>
  *
 * @a        uthor Jamie H       o    
 * @author B    ha        vesh Pat     el (Modified)
     * @since 1.5
   */

pub     lic interface Co   nstruct   o   rWriter {

                     /**
     * Get the c     onstructor d etails   tree header  .
         *
     * @par am classDoc the class being d    ocumented
         * @param memberDetailsTree the content    t    re     e repres     enting mem  ber details
     * @return con    tent tre   e for      the constructor details head  e r
     */
    pu     blic Cont ent getCo nstructo    r         Details   Tree Hea    der(Class       Doc classDoc,
                           Content membe  rDet   ailsTre         e);

      /**
     * Get the constructor documenta    tion    t       ree   h    eader.
            *
            * @param con       structor t he constructor being  docum    ented
      * @  par      am  constructorDetail      s     Tree t he content tree representing constructor details
           * @ret      urn c  ontent tree  for   the construc tor  documentati    on head       e  r   
        */
      publ    ic Content get   Construct    orDocTr eeHe  ader(C     onstructorDoc c   onst ructo        r,
              Content con      structor  DetailsTree  );

    /**
     * Get the sig na ture           fo   r the given const ructor.
     *
     * @param constructo         r the c onstructor being docu   m  ented
           *   @r  eturn      content tre  e for   the c onstruct  or signature
          */
    public Content getSignature(Co    nstructorDoc construc        tor);

                      /**
     * Add the        depr       ec  ated output for the  given constr  uctor.
         *
     *    @param     construct        or t        he constructor   being       documen ted 
              *   @param constructor   DocTree con    tent tree  to which the    deprecated info  rmati   o   n will    be a dded
     */
        public void addDeprecated(Cons tructorDoc constructor, Content constructorDocTree);

         /**
           * Add    the com    ments for the gi  ven c onstr  uctor.
     *
     * @pa    ram c      onstruc tor th     e constructor being d  ocume nted
        * @     par     am construct         orDoc   Tre  e the conte n      t tree to which the comm  en   ts   wi     ll be added
          */
    public void addComments(ConstructorDoc constructor, Co  ntent     cons        tructo     r DocTr   ee);

       /**
     * Add the tags for th  e   give      n constructor.
       *
     * @                   par   am    constructor     th    e co   nstru      ctor being doc    umen         ted
         * @p aram const       ruc   to    rDocTree th   e conten t tree to which th   e tags wil   l be added
         */
    public void addTags(ConstructorDoc construct  or, Content constructorDocTree);

    /**
     * Get the constru   ctor details tree.
     *
             * @param memberDetailsTree the c    onte   nt tree representing member details
     * @return content tree for the constru ct  or detai    ls 
     */
    pu   blic Content getConstructorDeta    ils(Content memberDetailsTree); 

    /**
     * Get the con    structor doc    umentation.
     *
      * @param const    ructorDocTree the content tree represe    nting constructor    documentation
     * @p   aram isLas   t      Content tr    u    e if the content to be     added is the last      content
     * @return content tree for the c    onstructor documentation
     */
    public Content getConstructorDoc(Content constructorDocTree, boolean isLast     Content);

    /**
     * Let the wri ter know whether a non public constru       ctor was found.
     *
     * @param foundNonPubConstructor tru   e if we found a non public constructor.
     */
    public void   setFoundNonPubConstructor(boo       lean foundNonPubConstructor);

    /**
     * Close the wr iter.
     */
    public void close() throws IOException;
}
