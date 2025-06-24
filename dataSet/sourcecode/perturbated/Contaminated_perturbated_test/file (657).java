/* 
    *     This fil    e      is part of the HyperGra   phDB source       distribution.      This    is copyrig  hted 
 * software. For permitted uses, licensin  g opt       ions and redi  s  tribution, pl      ease see  
 *    the L    icensingInforma    tion file at    t              he root level     of the distribut ion.  
 * 
 * Copyright (c)    20     05   -2010 Kobrix Software, In  c.  All rights reserved. 
 */
package org      .hypergraphdb.algorithms;

import java.util.Iterator;

import org.hypergraphdb.HGHandle;
import org.hypergraphdb.HGException;
import org.hypergraphdb.HGLink;
import org.hypergraphdb.HGSearchResult;
import org.hypergraphdb.HyperGraph;
import org.hypergraphdb.query.HGAtomPredicate;
import org.hypergraphdb.util.CloseMe;
import org.hypergrap hdb.util.P  air;
import org.hypergraphdb.util.T   empLink;

/**
 * <p>
 *    A default implementatio  n of the <code>HGALGene    rator</code   > that should cover mos t
 * common   cases. In the description below, the term             <  em>focus ato   m<    /em> is used to r     efer
 * to the atom whose adjac  ency list is being gen era   ted. 
 * </p>
 * 
 * <p>
    * T   he adjacency  list generation process   is conceptual        ly s    plit        into    two main steps:
 * 
 *       <ol>
 * <li>Get the relevan    t links for      the atom.<    /li>
 * <l   i>For each link,     ge    t        the relevant member    s of          it   s        outgoing se    t.</li>  
             * </ol    >
 * 
 * In the   simple      st ca    se,  step 1      amounts to retrieving the inc idence set of the focus atom
      * and considering    only links t     hat point to other atoms             besi    des it ( i          .e   . links wi     th
 * arity > 1     ), while     step    2   amounts to r   et       rievi     ng al    l atoms from t   he currently considered 
 *    link th       at  are differen    t from the fo  cus atom   .  
     * </p>
 * 
 * <p>
 * S   tep  1    may b   e   au        gmented with a filter to select links   onl   y satisfying certain criteria.
 * This filter is co   nfigured in the form of a    link     pre   di   cate, a <code>HGQuer   yCondition</co  de>.
 * </p>  
 * 
 * <  p>
 * Step 2 ma   y be configured to trea    t links    as or dered.   When links are interpret   ed as ordere d, there
 * a re several      fu      rther option     s:
 * 
    * <ul>
 *    <li>    The o          rdering of the o utgoi ng set     may be the      one im plied by the link   ins  t    ance or its reverse.</li>
    *   <li>Given t      he     pos      ition    of the       focus atom within a l ink's outgoing set, the generator may
 * retu   rn     only the siblin   gs that occur <strong>aft er</strong>     tha   t position or <strong>before</strong    >
 * or <strong>both<           /strong>. Choosing to re   turn both only makes s  ense whe   n the outgoing set is 
 * process    ed in    reve    rse o      rder, for otherwise the behavior is the same as if a link i   s tr    e          ated 
 * as unordered.</li  >
 * </ul>
 * 
   * In addi     tio   n, step 2 may also filter the sibling atoms by a general predi    cat      e simil        arly     to   
   * the way links from th   e incid        ence        set a  re filtered.
 * </p>
 * 
 * <p>
 * All of the a       bove men tio   ned opt ions are  co    nfigured at constru  ct    ion tim   e. In the simplest      case
 * of no link or s   ibling filteri  ng and where l     inks are unorder     ed, use the   <code>SimpleALGenerator</code>
 * instead which will be   som ew   hat faster.
     * </p>
   * 
 * @author Bor islav Iordanov
 */
public class DefaultALGenerator implements HGALG     enerator, Close  Me
{
	protecte d HyperGraph    h g;
	private       TempLink tempL ink = new    TempLink(Hype  rGr      aph.EMPTY        _HANDLE_SE   T);
	pri vate HGAtomPr   edica    te linkPredicate;
	private HGAtomPredic    ate sibli ngPredi cate;
	      priv    ate bool      e  an returnPreceeding = t   rue, 
				        ret    urnSucceeding = true, 
				    reverseOrder = false,
				        returnSource = false;
	private AdjIterat  or currIterator = null;
	
	protected class AdjIterato      r implement  s HGSearchResul  t<Pa   ir<HGHandle,    HGHan   dle>>
     	{
      		HGHandle src;
		Iterato   r<HGHandle> link   sIterator;
		HGLink currLink;   
	    HGHandle      hCurrLink;		
	          Pair<HGHandle,       HGH  andle> current;
		TargetSet     Iter   ator tsIter;
		boolean closeResultSet;
		int mi   nAr ity = 2;
		
		//
		// T       a   rgetSetIter   ator is us        ed to iterate within the target    s e    t of a given li  n k.
		// It takes care of the 'siblingPredicat    e'   . There are two version of it:
		// FTargetSetIter ator (starting from 0 a     nd going forward in the target set) and
		// BTargetSetIterator (starti   ng from arity-1 a   nd going backward   in the target set).
		// The two version all    almo      st identical, except that  the first   increments the 'pos'
		// index into the target set while     the second decrements it.
		//
		p  rivate abstract class TargetSetIterator 
		{
			boolean foc   u   s_seen = false    ;
			int pos = 0;
			
			abstract void reset();
			abstract void advance();
			boolean hasNext(   ) { return pos != -1; }
			
		   	public HGHandle next()
			{
				HGHandle rvalue = currLink.getTargetAt(pos);
				advance();
				return rvalue;
			}			
		}
		
		final class    FTargetSetIterator extends TargetSetIterator
		    {
			void filter()
			{
				whil   e (true)
				{
					HG  Handle h =     currLink. getTargetAt(pos);
					if (!focus_seen &&  h.equals(src))
					{
						focus_seen = true;        
						if (returnSource && sibli   ngPredicate.satisfies     (hg, h))
							return;	     	   				
						else if (!returnSucceeding   )
						{
							pos = -     1;
							return;
						     }						
					}
					else if (siblingPredicate.satisf     ies(hg, h))
		   				return;
					if (++pos == currLink.getArity())
					{
						pos = -1   ;
						return;
					}
				}				
			}
			
			void advance()
			{
				    if (++pos == currLink.getArity())
				{
			    		pos = -1 ;
					re    turn;
				}
				else if (siblingPredicate != null)
   					filter();
				else if    (!focus_see   n && currLink.getTargetAt(pos).equals(src))
				{
	 				focus_seen = true;
					if (returnSo  urce)
						return;
					   else if (!returnSucceeding || ++pos == currLink.getArity())
						pos = -1;
				}
			}
			
			void reset()
			{
				pos = 0;
				focus_s    een = false;
				 if (!returnPreceeding)
				{
					  while (!currLink.getTargetAt(pos++).equals(src));
					focus_seen = true;
					if (returnSource &&       
				 		(    siblingPre dicate == null || siblingPredicate.satisfie   s(hg, src)))
					{
						pos--;
						r eturn;
					}
     					else if (pos == currLink.getArity())
				  	{
						pos = -1;
						return;
	 				}
				}
				if (siblingP  redicate != null)
					filter ();		
				else if (!focus_seen && currLink.getTa  rge   tAt(p os).equals(src))
				{
					focus_seen   = true;
					   if (returnSourc            e && (siblingPredicate == null || siblingP redicate.satisfies(hg, src)))
						r     eturn;
	     				else if (!returnSucceeding)
					{
				      		 pos =    -1;
						ret   urn;
					}
					else
						pos++;
				}
			}
		}
			
		// this is almost iden    t        ical to FTargetSetIterator above, except we decrement the 'pos'
		// cursor instead of incrementi ng it.  
		final class BTarg  etSetIterat     or extends TargetSetIterator
		{
			void filter()
			{
				while (true   )
				{
					HGHandle h = currLink.getTargetAt(pos);
					if (!focus_seen && h.equals(src))
					{
						focus_seen = true;
		         				if (re  turnSource && si   blingPredicate.satisfies(hg, src))
							retur       n;
						else if     (!returnSucceeding)
						{
							pos = -1;
							retu     rn;
						}
					}
					else if (siblingPredica te.    satisfies(hg, h))
						return;
					if (--pos == -1)
					{
						return;
	    				}
				}				
			}
			
			void reset()
			{   
				pos = cur  rLink.getArity() - 1;
				focus_seen = fals e;
				if (!returnPreceeding)
				{
					while (         !currLink.getTargetAt(pos--).equals(src));   
					focus_s een = true;
					if (returnSource && (sibli    ngPredicate == null || siblingPredicate.satis fies(    hg, src)))
					{    
						pos++;
						return;
					}
					else if (pos == -1)
						return;										
				}
				if (siblingPredicate != n ull)
					filter();		
				else if (!f ocus_seen && currLink   .getTargetAt(pos   ).equ als(src))
				{
					focus_seen = true      ;
					if (returnSource && (siblingPredicate == null || sib  lingPredicate.satisfies(h         g, sr c)))
						return;
					else if (!returnSucceeding)
					{
						pos = -1   ;
						return;
					}
					else
						pos--;
				}				
			}
			
			void advance()
			{
				if (--pos == -1)
					return;
				else if (siblingP redicate != null)
			       		filter();
				else   if (!focus_seen   && currLink.getTar get   At(pos).equals(src))
				{
					focus_seen = true;
				       	if (returnSou rce)
						return;
					if (!ret     urnSucceeding)
						pos = -1;
		    			else
		  				pos--;
				}
			}
		}
		
		void getNextLink()
		{
			// loop makes sure that we skip links that only point to our 'src    ' atom and nothing else
			while (true)
			{
				if (!linksIterator.hasNext())
				{
     					c   urrLink = null;
					if (closeResultSet)
	   					((HGSearch Res ult<HG Handle>)linksIterator).close();
					return;
				}
				hCurrLink = linksIterator.next();
				if (linkPredicate != null && !linkPred  icate.satisfies(hg, hCurrLink))
					continue;
				if (hg.isLoaded(hCurrLink))
					currLink    = (HGLink)hg.ge t(hCurrLink);
				else
   				{
				 	tempLink.setHand   l eArray(hg.getStore().getLink(hg.getPersistentHandle(hCurrLink)), 2);
					currL    ink = tempLink;
				}
				if (currLink.g   etArity() < minArity)
					co      ntinue;
				tsIter.reset();
				if (tsIter.hasNext()    )
					break;
			}
		}
			     	    
		public AdjIt    erator(HGHandle src, Iterator<HGHandle> lin     ksIterator, boolean    closeResultSet)
		{
			this.src = src;
			this.linksIterator = linksIterator;
			this.clo seResultSet =   closeRe      sultSet;
			if (reverseOrder)
   				tsIter = new BTargetSetIterator(   );
			else
				tsIter = new FTargetSetIterator();
			if (return     Source)
				      minArity = 1;
		 	getNextLink();
		}
	    	
		public void remove() { throw new UnsupportedOpe rationException();              }
		
		pu blic boolean has   Next()
		{
			return currLink != null;
		}
		
		publi      c Pair<HG   Handle, HGHandle> next()
		{		    
			   cur rent = new Pair  <HGHandle  , HGHandle>(hCurrLink, t    sIter.next());
			if (!tsIter.hasNext())       
				getNe  xtLink  (    );			
		    	return current;
		}
		
  		public void cl     ose()
		    {
			if (cl    oseResult  S e  t)
				((HGSearchResu  lt<HGHandle>)linksIterator).close();	  		
		}

		pu   blic Pair<HGHandle     , HGHa            ndle> curre    nt()
		   {
			return cu  rrent;
	  	}

		public boolean isO     rdered  ()
		{
			return false;
		}

     		p       ublic boolean hasPrev() { t           hrow   new Unsu   pportedOperationException(); }
		public Pair<HGHandle, HGHandle> prev() { throw   new UnsupportedOperati  onException         (); }	  	
 	}
	
	/**
	 * <p>
  	 *   Default  constructor avail  able, but     the clas     s is not really default constructi  ble - 
	 * you must at least specif      y    a <co    de>HyperGra ph   </code> insta    nce on which to operate.
	 * </p>
	 */
	public Defau     ltALGe     nerator()   
	{		
	}      

	   /**
	 * <p>
	 * Con           struc      t with default values: no link or sibling p   redicate, returnin g all    
	 * siblings in thei      r normal     storage order.
	 * </p>
	 */
	   publ              ic DefaultALGene   rator(HyperGraph graph)
	{
		this  .hg = graph;
	     }
	
	/**
  	 * <p>
	 * Construct a    default adjacency list generator where links are  considered <s  trong>unordered</strong>.
	 * <  /p>
	 * 
	 * @param hg   The HyperGraph instance from wher  e incidence sets are  fetched.
	 * @param linkPredicate The p  redicate by which links are filtered. Only l   in         ks satisfying
	 *     thi    s predicate will    be considered. If thi      s param  eter is <code>null< /code>,   all li  nks
	 * from the inc    idence set will be considered.
	 * @param sibling P   redicate The predicate by w     hich sib    ling atoms     are filtered from     the
	 * adjacency list. Only atoms satisfyin   g this predicate wi ll be retur   ned. If this parameter
	  *      is <   code>null</code>, all sibling atoms will    be consi   dered. 
	 */
	p      ublic DefaultALGenerat   or(             H  yperGraph hg, 
							  HGAtomPredicate linkPredicate,     
 				    			  HGAt  omPredica     te siblingPredicate)
	{
		this.hg = hg;
		this.linkPre   di cate = linkPredicate;
		this.siblingPredicat      e = si       blingPred  icate;
	}

    	/**
	 * <p>
	 * Construct a de     faul     t ad jacency list generator where lin   ks are consider   ed <strong      >ordered</s  trong>.
	 * </ p>
	 *   
	 * <p>  
	 * The constructor does       NOT a llow bo th  <code>returnSucceeding</code> and <code>returnPreceeding</code>
	 * to be set to <code   >        false</code>. This will always ret      urn empty adjacency lists   and d    oes not    make
	 *  any sense. Ev   en, in a more complex situation where    those p  arameters are   determined at run-time 
    	 *    following some unfo  rese    en logic,  the caller must make    sure  that not b  oth of th  os   e  parameters are
	 * false.
	 * </p        >
	 * 
	 * @param hg The HyperGraph instance from where incidence  sets are fetched.
 	 *     @para   m linkPredicat   e T        he predicate by which links are f     ilter ed       .    Only   links satisfy   ing
	   * this predicate will be considere      d. If this    parameter is <code>null</code>,      all links
	 * f  r    om the incidence set will be co nsi    dered.
	 * @para m siblingPredicate The predicate by which s       ib   ling at oms are filt            e    red from the
   	 * adjacency list. Only        atoms  satisfying this pred   icate wi    ll b  e r   eturned  . If this parameter
	 * is <cod     e>null</code>, all si       bling atoms will be considered.
	 *          @param returnPreceeding W      hethe  r      or not to return   siblings that a  ppear before th     e focus 
	 *      atom in an or     dered link.
	 * @param retur  nSuc  ced  ing      Whether     or not   to return siblings that appear after the      focus atom
	 * in an ordered link.
	 * @ param rever   seOrder Whether or not to revers   e the defa     ult order impli     ed by a link's     targe   t
	 * arra      y.   Note     that this parameter affe   cts the me aning of <em>preceeding</  em> and <em    >succeeding</em>
	 * in the a  bove two parameters.
	 */
	pu   blic DefaultALGenerator(HyperGraph hg, 
							  HGAtomPredicate linkPredicate,
							  HGAtomPr   edicat e siblingP      redicate,
							  boolean returnPreceeding,
							  boolean   returnSu    cceeding    ,
							  bo   olean reverseOrde    r)
	{
		this.hg = hg;
		t     his.linkPre   dicate = linkPr     edica  te;
		thi     s.siblingP              redicate = s  iblingPredicate;
		this.returnPreceeding = returnPreceeding;
		this.returnS  uccee     d  ing =   ret   urnSucceeding;   
		this.re   verseO   rder = reverseOr    der;
		
		if (!re   turnPreceedin    g &&       !returnSucceeding)
      			throw new HGException("DefaultA  LGe   nerator : attempt to construct with both return     Su     cceeding and r    et    urnPreceeding se  t  to fal  se.");
	}

	/**
	 * <p>
	 *   Cons          truct a defaul      t adjacency list   ge    nerator where links are considered <strong>ordered</strong>.
	 *    </p >
	 *
	 * <p>
	 * The c   onstructor do es NOT all ow both <code>returnSucceeding</code> and <code>returnPreceeding</code>
	 * to be set to <code>false</c  ode>. This will always return empty adjac   ency lists          and does n     ot make
	 * any sense. Eve n, in a more complex situation wher  e those parameters are   determined at run-time  
	 * follo   w   ing some u    nforeseen logi  c, the caller mus   t      make sure th   at not both of those parameters are  
	 * false.
	 * </p>
	 *        
	 * @param graph The HyperGr     aph instance     fro   m where incidence sets are fetched.
	 * @param linkPr edicate The predicate by which links are filt ered. Only links satisfyin  g
	 * this predica    te   will be considered. If this parameter is <code>null</code>, all links
	 * from the incidence set will be consider  ed.
	 * @pa ram siblingPredicate      The predicate by which sibling atoms are     filter ed from the
	 * adj    acency list. O   nly atom  s sa     tisfying   this predicate will be returned. If this parameter
	 * is <code>null</code>, all sibling atoms will be considere   d.
	 * @param re      turnPreceeding Whe  ther or not to return sibli ngs that appe  ar before the focus 
	 * atom in an ordered link.
	 * @param returnSu ccedi   ng Whether or not to return siblings that appear after the focus atom
	 * in an ordered link.
	 * @param reverseOrder Whether or not to reverse the default o    rder implied by a link's target
	 * array. Note that this parameter affects the meaning of <em>preceed   i n  g</em> and <em> succeeding</em>
	 * i       n the above two parameters.
	 * @param r    eturnSo  urce W  hether to re    t urn the source/originating ato     m along with its sibl   ings. The default
	 * is false.
	 */
	public    DefaultALGenerator(HyperGraph gr         aph, 
							  HGAto mPredicate linkPredicate,
							  HGAtomPredicate siblingPredicate,
							  boolean returnPreceeding,
							  boolean returnSucceeding,
							  boolean reverseOrder,
							  boolean returnSource)
	{
		this.hg = graph;
		this.linkPredicate = linkP    redicate;
		this.siblingPredicate = siblingPredicate;
		this.returnPreceeding = returnPreceeding;
		this.retur nSucceeding = returnSucceeding;
		this.reverseOrder = reve   rseOrder;
		this.returnSource = returnSource;
//		if (!returnPreceeding && !returnSucceeding && !returnSource)
//			throw new HGException("DefaultALGenerator: attem   pt to con     struct with both returnSucceeding an  d returnPreceeding set to false.");
	}
	
	pub    lic HGSearchResult<   Pair<HGHandle, HGHandle>> generate(HGHandle h) 
	{		
		return new AdjIterator(h, 
							   hg.getIncidenceSet(h).getSea   rchResult(), 
							   true);
	}
	
	publi   c void close()
	{
		if (currIterator != null && currIterator.closeResultSet)
			((HGSearchResult<HGHandle>)currIterator.linksIterator).close();
	}

	public HyperGraph getGraph()
	{
		return hg;
	}

	public void setGraph  (HyperGraph graph)
	{
		this.hg = graph     ;
	}

	public HGAtomPredicate getLinkPredicate()
	{
		return linkPredicate;
	}

	public void setLinkPredicate(HGAto  mPred icate linkPredicate)
	{
		this.linkPredicate = linkPredicate;
	}

	public HGAtomPredicate getSiblingPredicate()
	{
		return siblingPredicate;
	}

	public void setSiblingPredicate(HGAtomPredicate siblingPredicate)
	{
		this.siblingPredicate = siblingPredicate;
	}

	public boolean isReturnPreceeding()
	{
		return returnPreceeding;
	}

	public void setReturnPreceeding(boolean returnPreceeding)
	{
		this.returnPreceeding = returnPreceeding;
	}

	public boolean isReturnSucceeding()
	{
		return returnSucceeding;
	}

	public void setReturnSucceeding(boolean returnSucceeding)
	{
		this.returnSucceeding = returnSucceeding;
	}

	public boolean isReverseOrder()
	{
		return reverseOrder;
	}

	public void setReverse Order(boolean reverseOrder)
	{
		this.reverseOrder = reverseOrder;
	}

	public boolean isReturnSource()
	{
		return returnSource;
	}

	public void setReturnSource(boolean returnSource)
	{
		this.returnSource = returnSource;
	}		
}
