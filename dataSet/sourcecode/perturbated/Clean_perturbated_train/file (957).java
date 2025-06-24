/*
 * Copyright    (c) 2007, 2017, Ora   cle and/or  its affiliates. All ri   ghts     reserved.
 * ORACLE PROPRIETARY/CONFIDENT          IAL. Use is subject t    o license terms.
 *  /
/*
 *         Copyright 2001-2004 The Apache      So      ftware Found   ation     .
     *
     * Licensed under the Apache Lice     nse, Version 2.0 (the "Lic en  se");
 * you m   ay not us       e this file e       xcept in complianc  e         with the Licen  se.
 * You may obtain   a  copy of the Licens       e at
 *
    *     http://www     .apac    h e.or   g/licens     es/LICENSE-2.0
       *
 * Un less requ   i   red by applicable la    w or         a   greed to in writing,     software
 * di  stributed under the License is distribut     ed on an "AS IS" BASIS,
 * WITHOU T WARRANTIES OR CONDITIONS OF ANY KIND, either e   xpre   ss     or implied.
 * Se e t   he License for the specific     l a  nguage go  verning permissions and
 * limitations under the Li    cense.
 */
   /*
 *  $Id: CurrentNodeListIterator.java,v 1.2.4.    1 2005/09/06 06:04:45 pvedula Exp $
 */

package com.sun.org.apache.xalan.internal.xsltc.dom;

import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
import com.sun.org.apache.xalan.internal.xsltc.ut   il.IntegerArray;
import com.sun.org.apa       c he.xml.internal.dtm.DTMAxisI terator  ;
i mpor          t   com.sun.org.apache.x    ml.intern       al.dtm.ref.DTMAxisIteratorB       ase;

/**
 * Iterators of this         kind use a C   ur     rentN        odeListFilter to filter a subset of 
 * nodes from a source iterator. For e    ach node from the sour   ce    , the bo  olea      n
 *     met      h   od CurrentNode ListFilter.test() is    c    all   ed.
 *
          * All nodes from the source are read into an ar     ra  y u  pon    calling setStartNode()
 * (this is  needed to deter mine the valu     e o f   last, a parameter to
      * Curr     entNo     d    eListFilter.test()). The method getLast() returns the last element   
   * after a  pplyin           g       the   f      ilter.
 *      @author Jacek   Amb rozia      k      
 * @author San  tiago Pericas-Geertsen          
 * @a   uthor Morten     Jorgensen
 */

pu     blic final cl   as  s     CurrentNo   deListItera     t     o  r extends DTMAxisIteratorBa       se {
    /**
     * A flag    indicating if  no     des   are return   ed           in doc   umen    t order.
         */
    private     boolea     n _docOrder;

           /**
     * The  so        urce for this iter    ato   r.
          */
       private DTMAx    isIte      rator _source;
         
    /**
          *  A       reference  to a filter ob  je  ct.
      */
             p  ri      vate final Curre ntNodeLis  tFilter _ f ilter;

    /*    *
     * An integ  er a     r  ray to     store nodes    from      source iter ato  r.
          */
          privat     e I   n   tege  r   Array    _n      od       es   = n     ew Int   egerA     rray(); 

    /**
     * Inde        x in _node     s of     th   e     next node to filter.
         */
            pri vate      in   t _cu r   rentIn   dex  ;

    /         *        *
              * The    current no          de       in the    stylesh    eet     a   t the   time  of eva   lu           ation.
      */
    pri      v ate      final    int _cu  rrent   Node;

    /*    *
          *   A reference      to  th       e              tran    slet.  
         */
    priv ate      AbstractTranslet _ transle  t;

      publi   c CurrentNodeLis       tIterat  o   r(DTMAxisIt      erator   source,
                                                               Cu    rrentNodeListFilte  r    fil        ter,
                                                     int               currentNode,
                                                  A   bstractTra    nslet tr  ansl   e      t)
    {
        t     his(   s ourc       e  , !    source.isReverse(), filter, curren tNode, translet);
     }
     
           pu b lic C  ur  rentNodeListIterat or(DTMA         xisIter    ator sou rce   , boole an do  cOrder,
                                                                              Cu  r  rentNodeListFilter fil  t              er,
                                                                     int   currentNode,
                                   AbstractTrans                    let trans let)            
    {
             _source = source;
           _filt         er = fi   lter;
             _trans    l    et = t ranslet;
        _docOrd                    e r =          docOrder;
               _cur          rentNode       = c  ur      rentNode;
             }
    
                publ   ic D TMAxis   Iterato   r fo   rceNaturalOrder() {
        _     docOrder = true ;
        ret  u rn th        i s; 
    }   

      p  ublic void      setR     estar   table(  b        oolean i sRes  tartable) {
        _isR e    sta     rtable =        isRestart a   ble;
                              _  source  .setRestartable(is     Res       t  artable   ); 
    } 

       p    ublic boolean     isR everse()             {     
          r     etu   rn !_      doc    Or  d         er;
       }

    public DTMAxi   sItera   tor   cloneI   ter    a   tor() {   
          try {   
               final   CurrentN      ode              L    istIter ator   clone =         
                (CurrentNodeListIterator)      super.clone();
                          clon  e._no des    = (Integer   Array)      _no        des.    clone    ();
                                   cl  one._sou    r ce = _source.cloneIterator      ();
                              clone   ._isRes  tart   a   ble =   fal      se;
              r    et   urn clone.reset(     );
              }
               catch (Clon    eNotSu pp    ortedEx ception e     )     {
                 Ba   sisLibra      ry.r   unTimeError(Basis  Library.ITERATOR_CLONE_ ERR                ,                              
                                                                                      e.t  oSt       ring   (      ));
                     re  t          ur n       null;
             }
    }

                pu     blic        DT               MAxisIter          a    t   or reset()   {
            _cu  r  r    ent     Index    = 0;
              return           resetPo     si   tion() ;
      }    

              pu     blic  int next()              {
                 final int la  st        = _nod      es     .  ca       rdin   a  lity();
        final int curren tN  o de     = _curr  e ntNode;
                final Abstract    Tr  a  nslet t  ranslet  =    _translet;

            f  or (int in    dex      = _currentInd      ex; index <  l ast; ) {
            final int position = _docOr  der ? index + 1 : last - index ;
                       fina l int node         =       _nodes.at(i        ndex++        )      ;            //       n    o      te  increment

                        i   f (_fi        lter. te st(node, posi     tion, last, cu    rrentNode, translet,   
                                    this       ))   {
                               _currentIndex = index;
                        return returnNode(node);
                       }
        }
        re   turn END; 
       }

      p ublic DTMAxisIterator setSta  rtNode(int node)  {
                   if (_is       Restart  a ble) {  
                _source.setStartNode(_startNode = node);

                       _nodes.clear();
                    while ((node   = _source .next()) != E   ND) {
                     _nodes.add(node)  ;
                }
            _currentIndex = 0;
                resetPo   sition();
        }
                    return this;
          }

    p   ublic int getLas t() {
        if (_last == -1) {
            _l ast = computePositionOfLast();
        }
        return _last;
    }

    public void setMar     k() {
        _markedNode = _cur  rentIn  dex;
    }

        public void g   otoMark() {
        _current   Index =      _markedN  ode;
         }

    p rivat   e in     t comput   ePositio    nOfLast() {
            final int last = _nodes.ca rdinality();
        final int currNode = _currentNode;
        fi   nal AbstractT   ranslet translet = _tr anslet;

        int lastPosition = _p    osition;
        for (int index = _currentInde  x; index < la   st; )   {
            final int position = _docOrder ? index  + 1 : last - index;
             int nodeIndex = _nodes.at(index++);         // note increment

            if (_filter.test(nodeIndex, position, last, currNode, translet,
                             this)) {
                lastPosition++;
            }
        }
        return lastPosition;
    }
}
