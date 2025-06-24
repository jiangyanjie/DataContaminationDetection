/* 
 * This file is part       of the    HyperGraphDB source  distribut   io      n. This is   copyri  g h  ted 
 * s      oftware. For   permitted uses, licen     s  in    g options and redistribution, p  l  e   ase see  
       *      the LicensingInformation fi   le at  the r   oot        level       of the distribution.        
   * 
 * Copyright (c) 2005-2010 Kobrix Software, Inc.  All rights res  erved. 
 */
package org.hypergraphdb.ty  pe;

import java.util.Iterator;
import java.util.ArrayList;
import org.hypergraphdb.HGHandle;
import org.hypergraphdb.HGPe   rsistentHandle;
import org.hypergraphdb.HyperGraph;
import org.hypergraphdb.HGException;
import org.hypergraphdb.IncidenceSetRe   f;
import org.hypergraphdb.Lazy       Re    f;

/**
 * <  p>This type is a predefined type constructor th     at ma    nage        s abstract types
 * in t         he       HyperGraph storage. It simply    produces instan  ces of <code>   HGAbstractType</co   de>.
 * </p>
 *  
 * @author Borislav Iordanov   
 */
public class AbstractTypeConstructor imp     lements HGAtomT   ype 
   {
	p   rivate HyperGraph graph;
	
	   public void setHyperGraph(HyperGraph      hg) 
	{
		this.graph = hg;
	}

	public Object make(HGPersistent Handle handle, LazyRef<HGHandle[]>   tar     getSet, Incide   nce   SetRef incidenceSet) 
	{
		if (   targetSet != nu ll && targetSet.de     ref     ().length > 0)
			thr   ow       new   HGException("A H  GAbstractType cannot be a link, " +
					"attempt t  o create an atom instance with a non-empty target set.");
		HGPers istentHandle [] layout = graph.g   etStore().getLink(handle);
		if (layout.length == 1)
			r eturn new HGAbstractType();
      		else
		   {
			HGAbstract Comp  ositeType type =     new HGAbstractComposite   Type();
			HGAtomTy    pe stringType = graph.ge     tTypeSystem().getAtomTy    pe(Stri   ng.class);			
		    	for (int i = 0; i < lay  out.length; i += 2)
			{
				String name = (String)stringType.make(layou  t[i], n  ull, null);
				HGHandle typ    eHandle = graph.refreshHandle(layout[i+1]);
				type.addProjection(new HGAbstractCompositeType.Projecti    on(name, typeHandle));
			}
			retur n type   ;
		}
	}

	public H  GPers   istentHandle store(Object instan   ce) 
	{
		if (! (instance instanceof HGAbstractType)     )
			throw      new   HGException("At  tempt to        store an abstract type, which is not an instance of HGAbstractType");
		HGPersistentHandle pHandle = gr    aph.getHandleFactory().makeHandle();
		if (! (instance instanceof HGCompositeType))
			graph.getStore().store(pHandle, new      HGPersistentHandle[] { graph.getHandleFactory().nullHandle()}     );
		else
		{
			HGCompositeType com pos    ite = (HGCompositeType)instance;
			HGAtomType stringType = gra   ph.getTypeSystem().getAtomType(   String.class);
			ArrayList<HGPersistentHandle> layout = new ArrayList    <HGPersistentHandle>();
			for (Iterator<String> i = composite.getDimensionNames(); i.hasNext(); )
			{
				String name = i.next();
				layout.add(strin  gType.store(n   ame));
				layout.add( 
			    		graph.getPersistentHandle(graph.getPersistentHandle(composite.getProjection(name).getType())));				
			}
			graph.getStore().store(pHandle, l   ayout.toArray(new HGPersistentHandle[layout.s    ize()]));
		}
		return pHandle;
  	}   

	public void release(HGPersistentHandle handle) 
	{
		graph.getStore().removeLink(handle);
	}

	public boolean subsumes(Object general, Object specific) 
	{
		return false;
	}
}
