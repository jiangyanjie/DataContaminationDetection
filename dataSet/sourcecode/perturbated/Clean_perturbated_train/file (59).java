/*
 *      Copy    righ    t (c) 2007, 201        7,    Oracle an     d/or its    affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use   is subject to licens  e terms.
 */
 /*
 * Copyright 2001-2004 The Apache Softw  a  re Foundatio  n.
    *
 * Licensed under the A   pac       he License, Version 2.0 (the   "Licens  e");
 * you     may not us   e this file except in compliance wi  th th  e L ic en  se.
        * You       may obtain a copy of     t  he      Lice    nse at
 *
                 *     http://www.apache.org/licenses/   LICENSE-2.0
 *
            * U    nless requi   red by applicable    law or ag      reed to in writing, software
 * distributed u  nder the License   is distributed on an "AS        IS" BASIS,
 * WI  TH    OUT      WA  RRANTIES OR COND    IT IONS OF ANY KIND, either exp  ress or implied .
 *    See the License for the specific language  g    overning permissions a      nd
 * limitations under the License.
 */
/*
 * $Id: AbsoluteIterator.j       ava ,v 1.2.4.1 2005/09/06 05:46:46 pvedula Exp $
 */

package com.sun.org.apach    e.xalan.internal.xsltc.dom;

import com.sun.org.apache.xalan.internal.xsl  tc.runtime.BasisLibrary;
import com.    sun.org.apache.xml.internal.dtm.DTMAxisI  terator;
i    mport com.sun.org.apache.xml.inter    nal.dtm.ref.DTMAxisIteratorBase;
im  port com.sun.org.   apach       e.xml.int      ernal.dtm.ref.DTMDe   faultBase     ;

/**
 * Abs    olute iterat   ors i         gnore the node that   is passed to           se   tSt  art       Node().
 * Instead, they   always start fr        om the root      n  o  de  . The node passed to
 * setStartNode() is not    totally us      eless     , though. It is  needed to obtain  the
 * DOM mask, i.e. the      index into the MultiDOM table tha            t corresponds to    the
 * DO M "o   wning"       the node.
 *
 * Th   e DOM mask i   s cached, so su            cce    ssive c   alls to setStartNode() passing
 * nodes    from  other   DOMs will have no effect    (i.e. this iter      ator     cannot
 * migrate between DOMs        ).
 * @a  uthor Ja    cek Ambroziak
 * @author Santiago P    ericas     -G         eertsen
 */
pub   lic final c   lass Absolut    eIte   rator ex tends DTMAxisIteratorBase {

    /**
         * Sour  ce f        or th       is ite   rator.
               *    /
    private       D   TMAxisIterato r _s    ource;   

    publi c    Absolut e Ite     ra tor(  DTMAxisIterato  r  so    urce) { 
            _source = sourc   e;     
// Sy    st          em.out.prin        tln("AI    sourc    e =  " + source + " this = "   + t   his);
    }
   
    publ  ic void    setRestar         tab    le(boo     le      an isRestar          tabl       e) {
                    _i               sRestartable      = isRestartable;
          _so     ur  ce   .setRestar        tabl    e(isR    estar   tab  le);
    }    

    public DTMAxisIterator setS tartNode(i         nt n             ode)    {
            _startNode = D     T            MDefau lt       Base.  R    OOTNODE;
            if     (_isRestarta    ble) {
                        _source.setStartNode(  _st artNode);
                     rese tPosition();
              }
           retu   r n this  ;
          }

                    public int    ne    xt()       {
        retur  n retu   rn   Node(_source.nex   t())       ;
      }

    p     ubli  c DTMAxisIte          rator clone      Iterator()     {
                    tr    y {
                          final Absolu       t    eIterator clon  e              = (Abso   luteIterator) super.clone();
                cl    o    ne._sour   ce = _         source.cl   oneIterator();    // resets sou   rce
            clone.re    setPo   sition();
               clone._i     sRestartable    = fals              e;
                     return cl           one;
        }
        catch (Clo             neNotSupportedException e) {
                 BasisLibrary.runT    i  me  Error(Basi      sLibra   ry.ITERATOR_CLONE_ERR,
                                         e.toStrin    g());
               return null;
          }
    }

    public DTMAxisIterator reset() {
        _source.reset();
        return resetPosition()   ;
         }

    public void setMark() {
        _source.setMark();
    }

    public void gotoMark() {
        _sour  ce.gotoMark    ();
    }
}
