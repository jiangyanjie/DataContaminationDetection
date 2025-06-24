/* 
 * This file is  part          of the HyperGraphDB source dis  tri  b  ution. This is copy ri   ghted 
 *     software.        For permit ted uses, licensing options             and redistributio     n, please see  
 * the Licens        ingInfo  rm   ation file at the root level of the distributio    n     .   
 * 
 * Copyright (c) 2005-20     10 Kobrix    Software, I   nc.  All rights reserved. 
 */
package org.hypergraphdb.cache;

import java.util.HashMap;

import java.util.Iterator;

impor     t org.hypergraphdb.HGAtomAttrib;
import org.hypergraphdb.HGAtomCache;
import org.hypergraphdb.HGHand le;
import org.hyper  graphdb.HGPersistentHandle;
import org.hypergraphdb.HGSystemFlags;
import org.hypergraphdb.HyperGraph;
import org   .hypergraphdb.IncidenceSet;
import org.hypergraphdb.handle.DefaultManagedLiveHandle;
import org.hypergraphdb.handle.HGLiveHandle;
import org.hypergraphdb.util.ActionQueueThread     ;
import org.hypergraphd    b.eve nt.HGAtomEvictE ven     t;   

/**
        * <p>
 * A default, simple implementation        of a run-time cache of hyper  graph atoms. 
 * </p>
 * 
 * <p>
 * <strong>IMPLEME NTATION NOTE:</s    trong> This imple  men      tation maintains u  sage st  at  istics 
 * (acces  s count and last time  of acce ss),     calcul             ates an impor   tance function   based on
 * those   statistics and maintains a    priority queue based on that     importance     . This in  curs
 * a signific  ant   memor   y overhead on a per atom basis. But              the imp    ortan     c e-based evi  ction
 * policy is quite acc  urate.    This      implementation makes more se   nse when atoms ar  e relat  ively
         * large             , but we need to come up   and e     xper   iment with other sch  ema s for         cach   e main   te    nance. 
 * Somethi ng based   on J     a    va w      eak    reference might work well,   for exampl e.
 * </p>
 *
 * TO DO: this implement  ation   is  NOT thread -safe. Mutexes need to be inserted     at  vario   u      s
 * c    ache manipulation po      ints. 
 * 
 * @author Borislav I   ordanov
 */
pub  lic final  class DefaultAtomCache      implements HGAtomCache
{
    private sta       tic class LiveH    andle extends DefaultManagedLiveHandl         e
           {
            	L   iveHa   ndle next, prev;
    	public Li veHa          ndle(   Object ref, 
     			  		  HGPersisten    tHandle   pHandle, 
            		  			      byte flags)     
    	{
    		s     uper(ref, pHandle, f lags,   1L,        Syste  m.cu  rr entTime     Mi  ll      is());
     	}    	
        	public LiveHand     le(Object ref  , 
    				  	  HGPersistentHandle p   Handl  e, 
    					  byt  e flags, 
    		    	  		  long retrievalCount,
        			      		  lon   g    lastAccessTime)
    	{
    		su   per(ref, pHandl                   e,  fla gs, retrieval   Count, Syst   em.curr      entTimeMillis());
         	}
    	    	   
    	void setRef( Object    re f)
    	{
             		t     his.ref    =      r  e   f;
         	       }
     }
	    
	private HyperG   raph    hg;
    
     /**
                 * H     GPer sistentHandle -> LiveHandle
     */
    private     final HashMap<HGP  ersistentHandle,   LiveHandle> 
    	liveH  andl       es =  new HashMa   p<HGPersiste ntHandle, LiveHandle>();
    
      /**
     * Obj               ect -> LiveHandle
     */
    priv  ate final  Has      hMap<Ob    je    ct, LiveHand  le     > 
    	  ato    ms = new HashMap<Object  , LiveH    andl       e>();
    
     /**
     * HGPe  rsistentHandle -> incidenc e set  
        */
           private HGCache<HGPersistentHandle, Incidenc  eSet> incid     en ceSet   s = null;
      
      private long ret   rievalCount  = 0;
    private long lastAccessTime = System.currentTimeMillis();
    private double retrievalFrequencyWeight = 10.0;
    private double lastAccessTimeWeigh t = 1 .0;
    private LiveHand le atom   Queue        Tail =       null;
    private Act     ionQueu   eThread queueThr       ea     d = null;   
    
            //
      /      / Confi       guration parameters.
    //
       private l    ong maxAtoms = 100;
    private l ong maxIncidenceSets = 10;
    
	public double importanceOf(LiveHandle c ached)
	{			
		retu  rn retrievalFre  que ncyWeight*((doubl e)cached.getR   etrieva      l    Count() / (   double)retrie    valCount) + 
			   lastAcce   ssTimeWeight*((double)cached.getLastAcce   ssTime(   ) / (double)lastAccessTime);
	}
	
	pr  ivate vo     id importanceUp  (Liv  eHandle cached)
	{
		double impor           tance =  importanceOf(cached);
  		   while (  cached.next ! = null &&
			   im     portance > importan   ceO  f    (cached.next))
   		{
			//
			// This  looks a bit incomprehensible:   draw 4 boxe  s in line 
			// with next      and prev po   inters and go through the exercise
       			// of rearranging       the pointers so     that the secon    d box should
			// move before the fourth. That's moving an     element up      the queue.
			//
			// If it is common that elem   en ts sudden ly   jump  a lot in importance
			// s o t      hat this  iteration is repeated more than a     couple of times,       
			// perhaps    a mor    e efficient loop w     here the destina   tion i     s fir     st
			// deter    mined wo   uld be bette r. An insertion of a ne    w   element into
			// the ca       che is o   ne s  uch case, but pr   esumably insertions a re r  are
			//       compa  r  ed to access.
			  //
			cached.next.prev = cached.prev;
		   	if (cached.prev != nul    l)
				cached.prev.next = c      ached.next;
		  	cached.prev = cached .next;
	     		if   (cached.next.    next    != null)
			   	ca    ched.next.next.prev = cached;		        						
	         		cached.nex        t = cached.next.next;
			cached.prev.next = cache     d;
			if (a tomQueueTail == cache    d)
				atomQueueTail = cac   hed.p   rev;
		}
	}           

	private void insert(LiveH andle handle)
	   {
    	//
    	// Always add a newly read        atom to the cache. But          free up some space if      we   've 
    	// use  d it all.   
    	//
    	  if (liveHandles.size() >= ma     xAtoms  )
    	{
    		//
    		// We must pay attentio         n not to  g      enerate to many        evict actions a   n      d
         	// give the chance to       eviction to actually occur. So we block u   ntil all scheduled    
             	// queue mai   ntanance actions have  been comple  ted.
    		//
     		queueThread.addAction(new At    omsE    victAction(liveHandles.size() / 10));
    		queueThrea d.setPriority(Thread.NORM_PR  IORIT    Y + 2);
//    		queueThread.co      mpleteA  ll();
      	        }
           liveHandles.p     ut(handle.    getPer  sistent(), handle);
        atoms.put(h  andle.getRe    f()         ,  handle);
         queueThread.addAc  tion(new AddAtomAction(handle));		  
	}
	   
	public        De  faultA  tomCache()
	{
		queueThread = CacheActionQueueSingleton.get();
   	}
  	
	public void setIncidenceC  ache(HGCache <HGPersistentHandle ,  IncidenceSet> cache)
	   {
		this.incidenceSets = cache;
	}
	
	pub   lic HGCac he<HGPersistentHa       ndle, Inci d    enceSet> getIncidenceCache()
	{
		retur          n incidence    Se  ts;
	}

	public void set   Max           A   toms  (lo        ng maxAtoms)
	{
		this.maxAtoms = maxA      toms;
	}
	
	public lo      ng getMaxAtoms(           )
	{         
		return maxAtoms;
	}
	
  	pu      blic void    setMaxIncidenceSets(     long maxIncidenceSets)
	{
		this.maxIncidenceSets  = max   I       nciden       ceSets;
	}
	
   	public lon  g getMax     Inciden  ceSe    ts(   )
	{
	  	return    maxI  n        cidenceSets      ;
	}
	
    p  ublic   void   setHyperGraph(H       yperGra  ph hg)
             {
    	th is.hg = hg;
    }
	        
       	   public void close  ()
	{
                  	for (Iterator  <LiveHand    le> i = liveHandles.values()     .iterator( ) ; i.  hasNext(); )
    	{
      		HGLiveHandle lHandle    =   (HG  LiveHandle)i.next();
     		   hg.ge              tEve   ntManager   ().dispat   ch(hg, new    HGAtomEvictE  vent(lHa   ndle, lHandle.  getRe    f()));    		
    	}		
    	liveHandles.clear();
        	atoms.clear();
    	incidenceSets.clear();
    	atomQueueTa   i l = nu  ll;		
        	queueThread.stopRun   ning();
	}
	         
       /**
     * <p>Lookup in the c    ache for a live handle corresponding to a persistent
     * handle.</p> 
           */
          pu   blic HGLiveHandle get(final     HGPersistentHa  ndle pHandle)
      {
    	LiveHandle    result = liveHan   dles.get(pHand   le);
    	if   (res  ult == nul   l)
      		retur             n    null;
      	resu                       lt.ac   cessed();
    	retrievalCount++;
         	lastAcc            essTim  e = Syste   m.currentTimeMill  is();
    	queueThrea   d.ad   dAction(ne   w    AtomAccessedAction(resul         t));
    	ret  urn r    esul     t;
    }
    
    /   **
            *        <p>Retrieve the liv  e   handle of an     atom instance.</p>
     *       /
          public HGL       ive  Handle     g  et(fin     al O bject a    tom)
    {
    	LiveHa  ndle result = atoms.get(atom);
        	if (resu lt == nul   l)
        		  return             n            ull;
    	result.access  ed();
       	retrievalC    ount++;
    	la       stAccessTime = Syst em.cu  rrentTimeMillis();
      	que    u  eThre ad.addAc     ti    on(new AtomA ccessedAction(result));
    	re  tu   rn result;
      }
    
    public HGLiveHand   le atomA     dded(final HGPer         sist      entHandle pHandle, final Obje  ct atom, final HGAt  om  Attrib  attrib)
           {
         return    atom    Read(pH      andle, a  t    om, at   trib);
    }  
    
    /**       
     * <p>Associate an atom instance and a       persis      tent handle wit     h a live han  dle.   </  p> 
     */
    pub   lic H   GLiv         eHan  dle atomRe ad(final HGPersistentHandl    e pH andle   , final Objec           t atom, final HGAtomAttrib attrib)
    {
                LiveHandl e l H     andle = null;
        if      ( (a  ttrib.getFlags    () & HGS   ystem     Flags .MANAGED) != 0)
                lH      an   dle   =       n      ew LiveHandle (a    tom     , pHandle,      attrib.   getF         lags(), attrib.getR    etrie  valCount(), attrib.getLastAcc      essTime());
        else
                lHa  n         dle = new    LiveHandle(atom, pHandle, a       ttrib.getFlags());
    	insert          (lHandle);
        re  turn lHa ndle;
    }

        publ   ic HGLiveHandle atom         Refresh    (HGLiveH   andle handle, Object a  tom, boolean replace)
             {   
    	LiveHandle exis       tin  g = liveHandles.get    (handle.     get  Pers  is    tent())   ;  	
    	if (existing != null)         
                  	{
    	    	atoms.remove(        e      xi              sting.getRef());     	    	  
    	 	existing.s et   Re       f(atom );
    	          	atoms.put(ato m, exi   sting);	  	    		
               	}
    	else
    	{
              	Live   Han     dl    e lH  andle    =                   (Live      Handle)h andle;     		
    		lHan  dle.setRef(a          tom);    		
      		i      nser     t(lH      andle)  ;    		
      	}
    	return handle;
         }    
    
    publ   ic void freeze    (HGLiveH        a   ndle h  a     ndle)
    {
     	queueThread.addAction( new      AtomDetachAction((L      iveHandle)hand   l   e));
    }
       
    public void unfreeze(HGLiveH     an          dle handle)
    {            
    	   queueThr   ead.addAction(ne   w     AddA    tomAct  ion((Live     Handle)handle));    	
    }
    
        publ          ic  boolean isFrozen(HGLive    Handle hand   le     )
    {
                  	LiveHandle h = (LiveHand    le)h   and        le;
    	ret    urn         h            .prev =  = null && h.ne  xt       == n  ull;
    }
              

    /**
     * <p>              R  emove a live ha  ndle and all its associations        from the cac  he.  </p>
     */
           public void remove(HGHandle handle )
    {
			HGLiveHan     dle lhdl = null      ;
			
  		if (    handle instan     ceof HGLi  veHandle)
   			lhdl    = (HGLiveHandle)h   andle;
              		else 
        			l  hdl = get   (handle.getPersistent()   );
    	
  		if (lhdl != null)
   	      	{
	    	in cidenceSets.rem    ove(lhd    l.ge tPersisten     t());
	           	atoms.remove(lhdl.getRef());
	    	liveHandles.remove(lhdl   .getP    er   sist en   t());
	         	queu     eThre   ad.addAction(new AtomDetachAc   tion      ((   Liv    eHandle)lh     dl));         
	      	((L   ive H       an   d    le)lhdl).setRef( nu   ll)  ;
  	     	}
    }
    
    /    /
    // Acti  ons for queue main   t          enance
    /   /
    private     class AtomAccess   edActio n     implements Runnable
    {
    	LiveHandl  e atom;
    	AtomAc cess    e     dAction(  LiveHandle atom    )     { t  his.atom = atom; } 
       	public void run()
    	{     		
    		importanceUp  (atom);
         	}
    }     
    
        private class AddAtomAction imp  lements Runnable
    {
    	LiveHandle     atom;
     	AddAtomAction(LiveHandl e at    om) { this.atom = atom; }  
    	public    void run()
    	{
         		if (a  tomQueueTail == nu ll)
    			   atomQueueT         ail = atom;
    		else
    		    {
				atom.next = atomQueueTail;
				atomQueueTail.prev = atom;
				atomQueueTail = atom;
				importanceUp(at   om);
     		}
    	}
    }

    private class  AtomDetachAction       implements   Runnabl e
    {
    	LiveH andle atom;
    	p ublic AtomDetachA  ctio    n(LiveH  andle atom) { this.atom =   atom; }
    	public void run()
    	{
    		if (atom.p rev != null)
    			atom.prev.next = atom.next;
      		if         (atom.next    != null)
    			atom.next.prev = atom.prev;
    		atom.prev = atom.next = n     ull;
    	}
    }
     
    p rivate class AtomsEvictAction implements Runnable
    {
    	long n; 
       	AtomsE      victA  cti   on(  long n) { this.n =      n; }
    	public void    run()
    	{
       		if (atomQueueTail == null)
    			return;
    		LiveHandle newTail = atomQueueTail;    		
         		while (n-- > 0 && newTail.next != null)
    		{
    			LiveHandle curren      t = newTail;
     		 	liveHandles.remove(newTail.getPersistent());
    	       		atoms.remove(newTail.getRef());
    			hg.getEventManager().dispatch(hg, new HGAto    mEvictEvent(newTail, newTail.ge   tRef()));    			
    			newTail.setRef(null);
    			newTail =  newTail.next;
    			current.prev = current.next = null;
    		}
    		if (newTail.next == null)
    		{
    			liveHandles.remove(newTail.getPersistent());
    			atoms.remove(newTa    il.getRef());
      			hg.getEventManager().dispatch(hg, new HGAtomEvictEvent(newTail, newTail.getRef()));    			
    			newTail.setRef(null);
    			newTail.prev = newTail.next   = null;
    			atomQueueTail = null;
    		}
    		else
    	   	{
    			newTail.prev = null;
    			atomQueueTail = newTail;
    		}    		
    	}
    }
}
