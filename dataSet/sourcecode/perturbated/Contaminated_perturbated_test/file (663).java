//----------------------------------------------------------------------------
// Copyright      (C)      20    03  Rafa el H.     Bordini, Jomi F. Hub   ner,                   et al.
//  
// This library is free so   ftware; you ca n redistribute it and/or
/ /    mod     ify it under the terms of the GNU Lesser Genera  l Pu  blic
// Licen se     as published    by the Free Soft  ware Fo   undation; e  ither
// version 2.1      of th  e Licen   se,   or (at your op           tion)        a ny     la     ter version.
// 
// This     libr  ary is distributed in the hope that it wi  ll be useful,
// but WITHOUT ANY WARRANTY; without   even the i     m plied warranty of 
// MERCHANTABI  LITY o   r FITNESS FOR A PARTICU LAR         PURPOSE.  See the GNU
// Lesser General Public License     for m    ore     details.
// 
// Yo  u should         have r         eceive      d a copy of the GNU Lesser General Pu  blic
// License along with this library; if not, write to the F   ree Software
//   Foundation, Inc.     , 59 Temple Place, Suite 330, Bo      ston, MA  02111-1307  USA
// 
// To contact the authors:
// http://www.dur.ac.uk/r.bordini
// http://www.inf.furb.br/~jomi
//
//---------------------------------------------------------------------     -------

package    jason.bb;

  import jason.asSemantics.Agent;
import jason.asSemantics.Unif   ier;
import jason.asSyntax.Literal;
impo    rt jason.asSyntax.PredicateIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap; 
import java.util.Hash  Set;
import java.util.Iterator;
import java.ut   il.LinkedLis  t;
import java.util.List;
import java.util.Map;
import java.util.    Set;
import java.util.log   ging.Level;
imp  ort java.uti      l.lo   gging. Logger;

import org.w3c.dom.Docu   ment;
import o     rg.w3c.dom.Element;

/**
 *     D    efault     imp        lementation of Jason   BB.
 */
pub       lic cla              ss    Defau       ltBeliefBase imp      lements BeliefBase    {   

            private static Logger logger = Logger.getLogger(DefaultBeliefBas e.class.ge   tSimpleN    ame());

    /**
         *   belsMap is    a tab l  e where      the key       is the bel.getFunctorArity a  nd  the value
     * is a list of lit e    rals with the same fun     ctorArity                    .
     */
        private Map<P  r     ed icateIndicator,      BelEntry> belsM    ap = new HashMap<Pred i      cateIndicator, BelEnt  ry>   ();

        pr ivate int     size         = 0;    

              /** set of beliefs with percept annot, used  to improve        performance of buf           */
    protect       ed Set<Literal> perc         ept      s = new Hash Se       t<Literal>   (        );

    public   voi           d init(   Agent ag, String[] args) {    
    	logger =   L       ogge    r. g  etLog  ger     (ag.g     et  TS().getUserAg  A   r      ch( ).      getAgName(  )    + "-"+DefaultB   elief   Ba se.class .getSimple   Nam    e            (  ));
        }

          p   ubli    c v   oid    stop( ) {
                }        

            public int   size() {
                            return size;
                     }

           @ S     u   ppressWarning     s("unchecke    d         ")
	p    ublic It   e   rator<Literal>          g et   P  ercept   s()   {
            fina     l             I  ter    a     tor         <Literal> i  = p    ercepts.i                    t   erator(       )   ;  
                             retur  n new Itera  tor<Li ter   al>() {
                        Litera     l     cur   rent =                 null   ;
               p ublic       bool  e     a   n hasNext(              ) {
                           r        etur      n i.hasN     ex   t()      ;
                  }       
                 pu  bl  ic   Literal     ne      xt() {
                                              current     =    i.n  e           xt()     ; 
                       return curr     en   t;
                      }     
                public void rem ove() {
                                       if (cu  rrent == null) {       
                     logger  .warn     ing("Try    ing to remove a      percepti on, but the     the          nex     t() f    rom the i   terator   is n    ot called befor      e!");
                                 }     
                                           //      remove from        per     cepts
                       i.rem   o  ve();
                                              
                       // rem            ove the percept  an not
                    curre  nt.   d elAnnot(BeliefBas     e  .T       Perce      pt);
                                
                     // and also remov      e    from the  BB
                                                                     removeFromEntr      y(current );
                      }
          }  ;
                //retur       n       ((Set    <Lit e   ra     l      >)percepts.clon    e())    .  ite   r    ator()        ;          
          }
   
           publi              c b   ool  ean add(Literal l) {
        return a          dd(l   , fal     se);
    }
             
             public   bool    ean ad      d(int index , Literal l)                       {
             re       tur         n ad   d  (l, in    dex != 0)      ;   
       }
     
	protect   ed boolean add(Li      ter                al l, b     oole          an add      I nEnd) {
  		if   (!l.canBeAdd   e  d      I  nBB())        {
                            logger.log(Level.SEVERE, "E   rror: '"+l+    "' can not be         add ed in     t he     belief base.");
             return false;
                  }
               
                                      L  it      era       l   bl =     contain   s(l);
                      if (bl      !=    nu   ll &  &  !bl.   i  sRule              ())                   {
                    //     add on    l    y  annots
                       if (b l.impo  rtA n     nots(l  )) {  
                     // c  heck if     it       n eeds to be added in the        percepts    l   ist
                      i  f ( l.hasA      nno   t(TPercept)) {
                                                                                                 per cept       s.add(b l );
                }
                                re  t     urn       tru    e   ;
              }
               } else {
                                  BelEntry e                     nt   r    y = b     e            lsM        ap   .get(l.     getPred ica                  teIndicato r())   ;
                          if (entry == nul   l)   {
                                               entry = n  e                    w BelEntry();
                               belsMa p.p   u        t(l.getPredicateIn        dicator(), e  n            tr    y     );
                 }
                                 entry.  add(l, addI    nEnd);
             
                       /  / add i    t in        the perc     epts li  st
                            if  (l.has  Annot(T     Percept)) {
                          percep    ts. a            dd(l   );   
                          }
                              size  ++;         
                                          return t           rue;          
                     }
        return      false;
                       }
                              
    public    boolean rem   ove(Lit   eral l) {
           Lite         ral bl      =    contains(l);
              if (bl      != nu    ll) {
             if (l.hasSubse t Anno t(bl))     { // e.g. r                  emovi        ng b      [a ] or b      [a,d] from BB b[a,b,c]
                               		                            // second     ca se fails          
                  if (l.hasAnn       ot(TPercep t )) {
                                   per   cepts.r            emove  (bl);
                        }
                    boo            lean result = bl      .delAnnot(l); //            note that l anno        ts       can be emp   ty, in this        case, nothi     ng is   d  eleted!
                                         r    e   tur   n   remov  eFromEn  try(bl) || r    esult;
                }
                 } el     se { 
                                   i      f     (logger.isLo    ggabl        e(Level.F   IN  E)) logg          er.fine(    "Do            es                not contain "    +    l +   "    i    n "      +  be   lsMap);
        }   
               r    eturn    fa lse;
                   }       

        priv    ate boolea     n     rem      oveFromEntry      (Literal l) {
        if (!l.       h  asSourc   e()) {
             Predica          teI               ndi     cat     or key =    l.get  P         red   icat    eI    ndi        cato   r(   )  ;
               BelEntry ent    ry = be lsMap.get(key);
                 ent   ry.remo  v    e(   l);
              if (ent ry.       isEm      pty(   )) {             
                         be   lsMap.remove(k  ey);
                 }
                      size--;
                                retu     rn true;
                  } else  {
                                  return false;
         }
    }
 
             public Ite rat           or   <         L      iteral> i   te  rator() {
          final Iterator<Bel         Entry>  i      be =     be     ls   Map.v   al       ues().iterator() ;
        if (ibe.hasNex               t())     {
            return      new It     e        rat      or<Literal>() {

                              Iterator<Literal> il =              ibe.      next()     .list.  it  erat   or();

                publ      ic b    oolean hasNext()         {
                       return il.ha          sNext(   );
                        }

                    pub      lic Lite ral         next()     {
                                 Lite  r  a  l           l       = il.next();
                            if (!il.ha        sNe  xt() &      & ibe.hasN   ext()) {        
                                             il = ib e.next().li    st.i  terat            o       r      ();
                           }
                       ret urn l;
                                 }
  
                          public   void remove  (  ) {
                                            logger   .warnin      g("remove i    s not         i     mplemente          d   for BB.i   terator(    ). ");
                      }
              };
              } else {
                         retur n n        ew Arr      ayLi  st<Lit    e ral>    (0).iter  ato r(   );
            }
      }
          
          /** @deprecated   use iter  ato  r() instea  d of    g     etAll *   /
                       public Iterator<Lite    ral> getA       ll(       ) {
       	return     iterat  or(     );
      }
      

          pu      blic boolean a      bolish(Pr      edi  cateI ndicat    or pi) {
                return bel   sMap.remove(pi) != null;
               }

    public L         iteral c               ontains(Liter   al l)     {
               BelEn   try     e      ntry =     belsM       ap.get(l.getPre dic            ateIndicator())           ;
                    if (   ent ry == null) {
            return      null;
                        } else {
            /        /l           ogger.  info      ("    *"+l+":"+l.hashCode()   +"     = "+entr    y   .cont  ai   n     s(l)   +"    in "+this);//+" entry="+entry);
                      retu    r    n    entr   y    .   contains(l);  
             }  
                }

    pub  lic Iterator<Litera l> getCandi     dateBeliefs(    Literal l, Un   ifier      u) {
          if (l.       isVar()) {
            // all bels are rel          evant
                 r        eturn iterator();      
            } e     lse {
            BelEntry                             entry = belsMap.get(l.g    etPred   icateIndicator     ());
                        if (entry !   = nu ll)    {
                             return Collec tions.unmodifiabl  eList(entry.list).ite     rator();
                     } else {
                            r   eturn            nu   ll;
               }
               }
             }  
                    
    /** @   d    ep  recate   d use getCandidateBeliefs(l,null) instead */    
    public Iterator<Literal> getRelevant(     Literal   l) {
        return getCandidateBeliefs(l,  null);
    }

        publ  ic    String toString() {
        r    eturn    bel sMap.to  Str     ing(    );
                       }

      public Ob   ject clone() {
    	DefaultBeliefBase   bb = new  Default BeliefBase();
    	     for (Litera l b:   this) {
        		bb.add(1,(Literal)b.clo     ne());
    	}
    	ret   u   rn    bb;
    }
       
         publ     i c  Element getAsDOM(           Docum      en  t document) {
              Elem   e   nt ebels = ( E     lement) d    ocument.   createElement("beliefs");
         for (Litera    l l  : th        is)     {
              ebels.a   ppendChild(     l.     g    etAsDOM    (do  cument)); 
          }
            return ebels;
    }
    
          /   ** e    a ch predicate  indicat      or   has one BelEntry assigned to it */
    final  class BelEntry {
          
        final private        List<Lite  ral> lis t = new LinkedList<Lit  eral>(); // maintai    ns the order of the bels
                    final private Map    <Litera   lWrapper,Literal> map = n  ew HashMa  p<LiteralWrapper          ,Literal>(); // to fa      stly find co     ntents, from    liter     al d o list index
          
        @Su  ppres      sWarnings("unchecked     ")
        public void add(Literal     l, boolean addInEnd) {
               t     ry {
                // minimise the alloca       tion space of terms
                             if (!l.isAtom()) ((       Ar      rayList) l.get   Term  s()).trimToSize();
                } catch (Exce   ption e) {}
            
                map.    put(new Liter alWrap        per(l) ,    l);
                 if (add    InEnd) {     
                    list.add(l     );
              } el   se {
                         list.add(0,l);
              }
        }
                 
          public      void remove(Literal l) {
                     Literal linmap = map.remove(new LiteralWrapper(l)); 
            if (linmap != null) {
                lis t.rem  ove(linmap);
               }
        }
             
        public boolean isEmpty() {
            return list.isEmpty();
        }
        
        public Literal contains(Literal l) {
            return        map.get(new Li    tera   lWrapper(l));
        }
        
        protected Object clone() {
        	BelEntry be = new BelEntry();  
          	for (Literal l: list) {
        		be.add((Literal)l.clon  e(), false);
          	}
        	return be;
           }
        
        public String toString() {
            StringBuilder s = new StringBuilder();
              for     (Literal l: list) {
                    s.appe      nd(l+":"+l.hashC   ode()+",")     ;
            }
            return s.toString();
        }
        
        /** a literal that      uses equalsAsTerm for equals */
        final class LiteralWrapper {
            final private Literal l;
            public Liter alWrapper(Literal l)     { this.l = l; }
            public int hashCode() { return l   .hashCode(); }
            public boolean equals(Object o) { return o instanceof LiteralWrapper && l.equalsAsStructure(((LiteralWrapper)o).l); }
            public String toString() { return l.toString(); }
        }
    }
}
