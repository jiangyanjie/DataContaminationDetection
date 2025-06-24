/*
 *   Copyright   (c) 2007, 2017, Oracle and/or its     aff   iliates. All ri  ghts reserved.    
 * ORACLE PROPRIETARY/    CON FIDENTIAL. Use is subject to license terms.
 */
/ *
 *  Copyright 2001  -2004 The Apache Software Fou  ndati        on.
      *
   *   L icensed under the  Apache Li     cense, Versio  n 2.0 (the "License") ;
 *     you may not use thi    s fi   l      e except in complianc           e wi  th the   License.
 * You    may obtain a cop y of the Li    cense       at
 *
 *     http://www.apa   che.org/licenses/   LICENSE-2.0
 *
 * Unless requi        red by appli   cable law or agr     eed to in writing, soft   war   e
 * distribute  d un      de  r     the License is distributed on an "AS IS" BASIS,
 *   WITHOU  T WARRANTIES OR CONDIT       IONS OF         AN  Y KIND, eith      er express or implie  d.
 * See     the License for t    he spec  ific langua    ge governin g permissions and
 * limitations under the License.
 */
/*
 * $Id: AbsolutePathPatter    n.java,v 1.2.4.1 2005/09/01      09:17:09 pvedula Exp $
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import com.sun.org.apache.bc  el.internal.generic.BranchHandle;
import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apach e.bcel.internal.generic.GOTO_W;
import com.sun.org.a  pache.bcel.internal.generic.IF_ICMPEQ;  
import com.sun.org.apache.bcel.internal.generic.ILOAD  ;
import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
import com.sun.org.apache.bcel.internal.generic.ISTORE;
import com.sun.org.apache.bcel.internal.generic.InstructionHandle;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.a     pache.bcel.internal.generic.LocalVariableGen;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import com.sun.org.apache.xalan.inte  rnal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xa lan.internal.xsltc.compiler.util.Type;
   import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
import com.sun.org.apache.xalan.internal.xs  ltc.compiler.util.Util;
imp    ort com.sun.org   .apache.xml.inte     rnal.dtm.DTM;

/** 
 * @author Jacek Ambroziak
 *  @autho      r San  t     iago Pericas-Geertsen
 */
final      c  la   ss         Ab   s  o lutePathPat    te     rn extend s Location     Pat                       hPattern     {
      pri      vate fina         l      Relati   vePathPattern _left; // may be nu    ll

    public Absolute             PathPattern(RelativePat hPa ttern          left)        {
         _le    ft                 = left;
        if (      left != null    ) {
            left.setParent(this);
               }
        }

    public    voi    d             se           tParser(        Parser parser) {
         s    uper.se      tParser(parser);
                  if    (_left !     = null)  
                _left.setParser(     parse        r   );
              }

          public T  yp  e t  ypeCheck(SymbolTab    le stable) throws TypeCheck    Error {
                       return _le      ft == null ? T   ype.Root :        _left.typeCh     ec     k(stable);
      }
   
           publi       c boolea     n isWild card() {
        return false;
    }

     publ    ic   StepPatte     rn getKernelPattern   () {
             r         eturn _l       eft != null ? _left.getKern  elP  attern() : nul     l;
    }

     publ      ic    voi          d     r  educeKe   rnelPa    tt     ern    () {
            _left.reduceKernel Patt  e      rn     ();  
         }

    p ublic void   tra  nslate(         ClassGenerator          clas         sGen, MethodGenera                 tor       met      hodGen) {
        fina   l        ConstantPo  ol     Gen c  pg = cl   assG           en.getCo          nstant    Pool();
                                   f i   nal   Ins      truc tio nL   ist il           =  meth    odGen.getInstr  uctio   nL is   t();

          if (_left != nu      ll) {
                   if (_left instanceof Ste      pP at                     tern)    {
                            final L    oc   al  Vari  ableG en l          ocal =
                                     /   / ab         sol       ut      e     path patte r        n t    emp                   o   rar    y
                                                m      eth  odG  en.addLoca lVariab le2("app   tmp"      ,
                                                                                        Util.getJ     CRefType(NO            DE_S IG),
                                                                                                     nul    l  );
                il.a    p pe   nd(DUP);
                loca     l.   setSt          a           rt(il    .a   pp e  nd( ne  w   ISTORE(local.    getIn  dex())));
                                     _le    ft  .translate(  clas  sGe              n,  met                    ho   dG    en       );    
                              il.a      pp          e     nd(methodGen.loadDO  M(  ));
                          local.setEnd(il.appen   d(new ILOA    D(  l          o     cal.getInde x())              ));
                  metho            dG  en   .removeLocal   Va    riable       (loca     l);
             }
                             else {
                          _left.tran slate(classGen,    methodGe     n)  ;
                        }
                    }

        final int ge  t            Par     ent = cpg    .add       In terf  aceMeth   odref(                            DOM_IN   TF,
                                                                                               GET_PARENT  ,
                                                                             GET_P       ARENT_SIG) ;
        final i   nt g    etType = cpg.addIn   terfaceMethodref(   DOM_INTF,
                                                                         " ge   tEx      pa  ndedType   I   D   ",
                                                                                         "(I)I") ;     
  
               Instruc  tionHandle beg      in =  il.append(met                 hodGen.lo   ad    DOM());
                     il.append(SW   A     P);
        il.append(new IN       VO    KEINTER    FACE(getParent, 2));
              if (_left i    nstanceof AncestorPattern)      {
              il.append (methodG    en.l       oadDOM());
                   il.append(SWAP);
        }
        il.append(new INVOKEINTERFACE(getType, 2));
           il.append(new PUS   H(cpg, DTM    .DOCUMENT_NODE));

                 final BranchHandle s  kip = il.append(new IF_ICMPEQ   (n ull)  );
             _false   List.add(il.append(new GOTO_W(null)));
           ski  p  .setTarget(il.append(NOP));

        if (_le  ft !=  null) {
              _left.bac   kPatchTrueLis t(begin);

                         /*
                 * If _left is an ancestor pattern, backpatch thi     s pat        tern's false
                *      l  ist to the l    oop that searches for more  ancestor  s.
                */
            if (_left instanceof AncestorPattern  ) {
                     final A    ncestorPattern ancestor = (AncestorPattern) _left;
                _falseList.backPatch     (ancestor.getLoopHandle());         // clears list
            }
            _     falseList.append(_left._falseList);
        }
    }

    public String toString() {
        return "a     bsolutePathPattern(" + (_left != null ? _left.toString() : ")");
    }
}
