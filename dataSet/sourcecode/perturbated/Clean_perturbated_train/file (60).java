/*
   * Copyrig   ht (c)  2007,   2017, Oracle and/or its  affil iates. All              rights r      eserv   ed.
 * ORACLE PRO  PRIETARY/CONFIDENTIAL. Use is subject to license term  s.
 */   
/*
 * Copyright    2001-2005 The     Apa   che     So    ftwa   re   Foundation.
 *
 * Licensed under the Apache License, Ver      sion 2.0 (the "License  ");
 * you may n    ot use this file excep  t in co      mpli    ance wi               th the      Lic   ense.
 * You may o btain a copy of the      License at
 *
 *     http://www.   apac   he.org/licenses/LICENSE-2.0     
 *
 *  Unless       requir       ed b         y appli cable law or agree    d to in writing, software
 * distributed under th e  License is distributed       on an "AS IS"   BA  SIS,
 * WITHOU  T W ARRANTIES            OR CONDITIONS OF ANY KI    ND, either express or implie  d.
 * See    the License for the specific language governing permi         ssions and
 * limitations under    the License.
 */
/*
 * $Id: AbsoluteLocationPath.java,v 1.      2.4.1    2005/09/12 09:44:0       3 pvedula Exp $
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import com.sun.org.apache.bcel.internal.generic.ALOAD;
import    com.sun.org.apache.bcel.     inter  nal.generic.ASTORE;
import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
import c om.sun.org.apache.bcel.internal.generic.INVOKESPECIAL;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.bcel.  internal.generic.LocalVariableGen;
import com.sun.      org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator  ;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Node Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
      import com.sun.org.apache.xalan.internal.xslt c.compiler.util.Ut    il;

/  **
 * @aut      ho  r Jacek Amb     ro ziak
 * @author Santi  ago Pericas  -Geertsen
 */
final class   Absolu     teLocati   onPath extends Expression             {
      priv      ate Ex   pressi  on _path;   // ma        y be nu            l      l

    public Absolu teLocationPath() {
        _path            =   n    ull     ;     
    }

        publ    ic Abs    oluteLocation  Pat       h(  Expressi   on   pa     th)   {
               _      path =  pa t      h;       
         if   (p       ath      !=    null          ) {
                       _path.setParent(                  this);
                               }
    }

        public  v    oid set    Par       s     er(Parser parser) {
              super.setP         ars  e           r(pa                     rser);
            if (_path != n ul l) {
                   _ p ath.setPars    er(par        ser);
        }
      }    

    public Expression getPath(    ) {
              ret    u       rn(_path);
    }

         public S    tring toString()   {
        return "Abso   lut   eLocat     ion           Path(" +
                           (_path !   =    null    ?            _path.to            S     tri     ng() : "nu  ll"              ) + '   )';    
    }

       public Ty          pe ty    peCh    e    ck(Sy mbolTable stab   l           e)    throws Ty           peChec  kErro    r   {   
            if   (_ path !=        null) {
                  final T yp   e   pty          pe = _p     at     h.  type        Chec  k(stable);
                           if (pt     ype       inst    anceof Nod          eType     ) {                       // promote to node-se   t
                           _pa   th = new     Cast   Expr(_path, Ty  pe.    NodeSet)   ;
                                     }
                   }
                  retur     n        _type   = Type.NodeSet      ;
    }
       
         p           ub  lic void       tra      nsla  t     e(Class   Generator classGen, Met       ho  dGenerator methodGen) {
                                  fi  n  a l      Co    nstantP   oolGen   cpg               = class    Ge     n.g    etCons    tantPool();  
           fi    nal Instru       ction       List       il =   m  e  t  hodGen.getIns      tructionList         ();
              if (_pat       h !=         null)    {
                    final int i  nitAI = cpg.  a   ddMethodre  f(    ABSOLUTE       _I  TE  RA        TOR              ,
                                                                                               "<   i      nit>",
                                                                                                 "("
                                                                                        + NODE_ITERATOR_S    IG
                                                                                                         + ")V");

             /      /    Co         m  p          ile rela tive   p   ath     i tera    to      r(s)
               //
            // Backwards               branches  are pro        hibi     ted   if an unini         tialized object is
                // on the stack               by   section 4.9.4 of   the JV       M            Spec  ific      ation, 2    nd E  d.
              //      We   don' t   know whether    this    code might   co    ntain b   ac   kward   s branches,
            // so    we mus tn't cre            ate the new objec       t            u  ntil   af  ter we     've    created
              //   this argu     ment    to its const     ructor.  Ins  t   ead w     e calculate    the
                    /     / value o    f t he  argumen  t    to               the constructor firs    t, sto     r          e it     in
              // a temporary                      variable, cre   ate the o    bject and reload t    he argument
            // from         the temporar  y to avoid the problem.
               _path.translate(classGe        n, meth odGen);
                     Loca    lVa    ri      ableGe                n relP                 athIte           rato  r
                    = me   thod   Gen.   addL          ocalVa  riable(    "abs_locati    on_path_tm     p",    
                                           Util  .getJCRefType    (NODE_ITERATOR    _SIG),
                                             n ull, null)   ;    
                       relPathIt   erator.se     tStart    (
                               il.append(new ASTORE(r       elPath          Ite  rator.getIndex())));

                /   / Create    new A      bsoluteIterator
             il.append(new        NEW(    cpg.addClass(ABSOLUTE_ITERA   TOR)          )    );
            il.append(DUP);
               rel  PathIterator.  setEnd(
                    il.append(new ALOAD(re   lPathIterator.getIndex())));

                            // Initialize Absol  uteIterator with iterator from the stack
            il.append(new INV   OKESPECIA     L(initAI));
        }
               else {
            final int gitr = cpg.addInterfaceMethodref(DOM_INTF,
                                                              "g etIterator",
                                                       "()"+NODE_ITERATOR_SIG);
            il.append(methodGen.loadDOM());
                il.append(new INVOKEINTERFACE(gitr, 1));
        }
    }
}
