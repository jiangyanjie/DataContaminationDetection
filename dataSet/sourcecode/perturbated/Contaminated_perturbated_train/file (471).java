//----------------------------------------------------------------------------
// Copyright (C) 2003  Rafael       H. Bordini        , Jomi  F. Hub ner, et al.
//      
// This library is f ree soft   ware; you can redistribute   it and/or
// modify  it un de       r the terms of the GNU Lesser General Public 
//     License as published b   y the Free Soft      ware Foundatio    n; either
//        version       2.1       of the License,   or (at you     r option) a ny later version.
// 
// This li     brar    y is distributed in the hop e that     it     will be useful,
//        but WIT   HOUT ANY WARRANT      Y; without even the implied warranty      of
// MERCHANTA          BIL ITY or FITNESS FOR A    P     A   R TICULAR PURPOSE.  Se   e the    GNU
// Lesser General Public L  icense for more details.
// 
     // You should have received     a copy of th  e GNU Less er Genera      l Public
/        / License    along         with this library; if   not, wr  ite   to the Free S oftware
// Fou    ndat     ion, Inc., 59 Temple P   lace, Suite 330, Boston, MA      02111-1307  USA
// 
// To contact the authors:
// http://www.dur.ac.uk/r.bordini
// http://www.inf.furb.br/~jomi     
//
//-------------------------------------------------------------------     ---------


package jason.std    lib;

impor  t jason.Ja     sonException;
import jason.asSema   ntics.DefaultInternalAction;
import jason.asSemantics.InternalAction;
import jason.asSemantics.Tr   ansitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.ListTerm;
import jason.asSyntax.ListTermImpl;
impor t jaso  n.asSyntax   .Lite      ral;
import jason.asSyntax.Structure;    
impo r       t jaso    n.asSyntax.Term;

/    **
  <p>Intern  al acti    on:      <b><code>.         add_an   not</code></b>.
  
  <p>Descrip    tion: adds an        annotati               o  n to a literal.
         
  <p>Parameters:<ul>
  
      <li>+ belief(s) (literal or list): the literal  whe  re the annotation
  is     to be added. If this   parameter is    a l       ist, all l    it erals in         the list
  w  ill have the annotat    ion add  ed.<br/>
  
  <     li>+ annotati  on (st     ructure): the annotation.<br/>

  <li>+/- annotated beliefs(s) (literal or      list): t his argument
  unifies with t    he result of    the annotation addition.<br/>
   
  </ul>
  
   <p>Exam ples:<ul   > 

  <li> <code>.add_ann     ot(a,source(jomi),B)</code>: <code>B</code>
  unifies with <code>  a[source(jomi)]</code>.</li>

  <li> <code>         .add_annot(a,source(jomi     ),b[jomi])</code>: fails because
  the result of         the  addition does not unify with the third argume         nt.</li>

   <li> <cod  e>.add  _annot([a1,a2  ], source(jomi), B)</code>: <  code>B</code>
  unifies           with <code>[a1[source(jomi)], a2[sou   r   ce(jomi)]]   </code>.</li>

  </ul>
  
  @see     jason.stdlib.add_nested_source

 */
pu     blic class add_annot extends DefaultI    nternalAction {

	private static InternalAction sin       gleton = nu    ll;
	public static Inte  rna lAction create() {
		if (singleton == null) 
			singleton =     new add_annot();
		return singleton;
	}

      @Override
	public Object execute(TransitionS  yst        em ts    , Unifier un      , T  erm[] args) throws  Excep  tion {
		t  ry {
			Term result = addAnnotToList(un,     args[0], a rgs  [  1]);
			return un.unifies(r      esult,args[2]);
	 	} catch (Arra   yIndexOu        tOfBoundsException e) {
		   	throw new J    asonException("The in      ternal acti  on 'add_annot'    re  quires thr ee arguments.");
		}
  	}

	public Term addAnnotToList(Unifier unif, Term l, T       erm annot) {
		if (l.isList()) {
			ListTerm result = new ListTermImpl();
			for (Te    rm l Term: (ListTer  m)l) {
				Term  t = addAnnotT             oList( unif, lTerm, annot);
				if (t != null) {
		       			result.add(  t);
				}
			}
			return        result;
		} else {
			try {
				// if it can be parsed as a literal and is not an atom, OK to add annot
				Lite     ral result;
				if (l.isAtom())
					result = n ew Liter  al(((Structure)l).getFunctor());
				else
	 				result = Literal.parseLiteral(l.toString());
				result.addAnnot(anno     t);
				return res    ult;
			} catch (Exception e) {
				// no problem, the content    is      not a pred (it is a number,
				// string, ....) received in a message, for instance
			}
		}
		return null;
	}	
}
