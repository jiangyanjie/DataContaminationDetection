//----------------------------------------------------------------------------
// Copyright   (C)     2003       Rafael H. Bordini,       Jomi   F.   Hubner, et al.
// 
// T  h  is library is free  software; you can       redistribute it and/or
// m     odify it  unde     r the term  s of the GNU Lesser General Publ  ic
/          /     License as published by th    e Free  Software  Fo  undation; eithe   r
     // version    2.1 of th e License, or (     at your option      ) any later version.
// 
//   This library is distributed    in the hope t  hat it will be useful,
// but WITHOUT    ANY WARRANTY; withou  t even      the implied warrant y of
// MERCHANT  ABILITY or FITNESS FOR A PAR TICU    LAR   PURPOSE.  See the  GNU
//     Lesser General Public License f       or more   details     .
    // 
// You should have received a copy of the    GNU Le  sser General Public
// Licen    se a  long with this library; i      f not, write to the Free Sof  tware
// Fou        ndation, Inc.,  59 T emple Place, Suite 330, Boston, MA  02111-1307  USA
// 
// To contact the authors:
// http://www.dur.ac.uk/r.bordini
// http://www.inf.furb.br/~jo mi
//
//-----------------------------------------------------------------    ----------  -

package jason.stdlib;

import jason.JasonException;
import jason.asSemantics.DefaultInternalActi      on;
import jason.asSemantics.Transit   ionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Atom;
import jason.asSyntax.DefaultTerm;
import jason.asSyntax.ListTerm;
impo         rt jason.asSyntax.StringTerm;
import jason.asSyntax.Structure      ;
import j   ason.asSyntax.T  erm;


/**
      <p>In   ternal action: <b><code>     .add_plan   </code></b>.
  
  <p>Description: adds plan(s) to       t     he agent's plan lib     r         ary .
  
  <       p>Param  eters         :<ul>
  
  <li>+ plan(s) (str    in             g o    r list      ): the     strin  g representing the plan to be
  added      . If it is       a         list, each string       in  the lis    t  will    be par  s        ed into an
  AgentSpea    k plan and added to the plan l       ibrary. The  syntax of    the c   ode w  ithin
  the string i     s    the same as ordinary   Age n t    Speak co     de.<br/>
  
       <li> <i>+ so      urce            </i> (atom [op  tional]): the sour                  ce    of the
  p   lan(s). The def  ault val u   e for the sour  c  e is <code>self</code>.<br/>
  
  </ul>
  
  <p>Examples:      <ul>   

        <li> <code>.    add_plan("+b : true &lt;- .p  rint(b). ")</code>    : a dds t    he pla     n          
  <co     de>+b    : true &lt;- .print(b).</code> to the agent's plan library
        wi     th a plan label annotated w ith <     code>source (  self)</code>.<        /li>

  <li> <code>.add _p  lan("+b : true &lt;- .print(b).",        rafa)<  /code>: same as
        the previous example, bu   t the source of the plan is a      gent
           "ra  fa".</li>         

  <li> <cod        e>   .add_plan(["+b : true &l        t;- .print      (b)."      ,        "+b : bel &l    t;-
  .print(bel)." ]    ,     raf a)</code>:    adds both plans w  ith "rafa "   as       their
                  source      s.</li>

         </u l>

  @see jas               on.stdl i    b.p        lan_label
      @   see jason  .stdli   b.   rele          va    nt   _plans
        @see   jas         on.st  dlib     .r   emove_plan

 */
p  ubl     ic     class    a     d   d_pla n exten ds Defau      ltI   ntern  a  lAction {

    @Ov  e     rride
    pub lic       Object ex    ec  ute(Tra             nsi          tionSystem ts,       Unifier    un, Te r  m       [] args) thro    ws     Exception {
              try {  
                       Ter    m plans          = De         fault   Te        rm.parse(    arg  s [0].     toString());

                      St   ruc     ture   source       = new    Atom("self");
            if (args.length > 1) {
                source  =    (Structur      e) ar      gs[1]   ;
                        }

            if (plans.i  sList()) { //  arg      [0] is     a      l     ist of strings
                   for (Term t: (             ListTe    rm)      plans) {
                    t     s.      get  Ag(      ).getPL     ()   .add((StringTerm) t, source);
                }
            } else { // args[    0] is a plan
                        ts.getAg().getPL().add((String  Term) pl     ans, source);
            }
            return      true;
        } catch (Arra  yIndexOutOfBoundsException e) {
             throw new JasonEx    ception("The int  ern      al action 'add_plan' has not received two arguments (a plan as a string and the source).");
        } catch (Exception e) {
            throw new JasonException("Error in internal action 'add_plan': " + e, e);
        }
    }
}
