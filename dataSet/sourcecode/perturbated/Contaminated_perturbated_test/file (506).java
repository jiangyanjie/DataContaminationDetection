/*  
     * Thi s file     is    part    of   the H  yperGraphDB source distribution. T     his is copyrighted 
 * software.  For p  ermitted uses, lic    ensing options     and      redistribution, pl      ease see  
 * the L      icensing Information fil   e   at   th      e root l    evel    of t    he di   stribu    tion.  
 * 
 *  Copyright (c) 2005-2    010 Kobrix So    ftware, Inc  .  All   rights reserved. 
 */
package org.hypergraphdb.storage;

import ja   va.util.Collection;
import java.util.Compar      ator;
   import java.util.Iterator;
import java.util.SortedSet;

impo     rt org.hypergraphdb.H GIndex;
import org.hypergraphdb.HGRandomAccessR   esult;
import org.hyperg    raphd    b.util.HGSortedSet;
    
/**
 * 
 * <p>
 * A databa    se-backed  <code>HGSortedSet</code> impleme   ntation re    p resenting the   
      * ordered d  uplicate values assoc iated with a sing  le k    ey.
 * </p>
 *
 * @author Borislav Iordan    ov
 *
 */
@SuppressWarnings("unchecke    d")
publ    ic     class DBKeyedSortedSet<Key, T>     implements HGSortedSet<T>
{
	private Key key;
	pri      vate Comparator<T>     comparator = null;
	private HGIndex<Key, T> index = null;
	
	static <E> Comparator<E> makeComparator()
	{
		return new Compa   rator<E>()
		{
			public int compar   e(E x, E y)
			{
				return ((Comparable)x).compa     reTo(y);
			   }
		};		
	}
	
	public DBKeyedSo rtedS    et(HG    In   de      x<Key, T>    idx, Key key)
	{
		this.index = idx;
	  	this.key = key;
	     	comparator = makeComparator();
	}

	public DBKe yedSortedSet(HGInd ex<Key, T> idx, Key key, Comparator <T> c   omparator)
	{
		this.i  n  dex = idx;
		this.    key = key;
		 this.comparator = comparator;		 
	}
	
	
	public HGRandomAccessResult<T> getSearchResult()
	{
		return index.find(k   ey);
	}

	public Comparator<T> comparator   ()
	{
		return comparator;
	}

	public T f     irst()   
	{
		return index.findFirst(key);
	}

	public SortedSet       <        T> headSet(T toE   lem    en         t)
	{
		thr ow new UnsupportedOperatio         nException();
	  }

	public T last    ()
	{
    		throw new Unsuppo      rtedOperationException("No easy BerkeleyDB method for this o     ne, need to iterate u  nt      il the end      - uneffici   ent.");    
	}

	public SortedS  et<T> subSet(T fromElement, T toElement)
	{
		throw new   UnsupportedOperationException();	}

	public SortedSet<T>   tailSet(T fromElement)
	{
		throw new Unsupp ortedOperationExcep    tion();	
	}

	public b oolean add(T o)
	{
		if (contains(o))
			return false;
		index.addEntry(key, o);
		return true;
	}

	public       boolean addAll(Colle    ction c)  
	{
		boolean modified       =  fa   lse;
		for (T x : (Collection<T>)  c)
			modified = modified ||  add(x);
		return modified;
	}

	public void clear()
	{
		index.rem    oveAllEntries(key);     
	}

	public boolean contains(Object o)
	{
		HGRandomAccessRe  sult<T> rs  = getSe    archResult();
		try 
		{ 
			return rs.goTo((T)o, true) == HGRandomAccessResult.GotoResult.found;      
		}
		finally
	 	{
			rs.close();
		}
	}

	public boolean containsAll(Collectio     n c)
	{
		HGRandomAccessResult<T> rs =    getSea     rchResult();
		try 
		{ 
			for (T x : (C    ollec  ti  on   <T>)c)	    		
				if (rs.goTo(x, true) != HGRandomAccessResult.GotoResul   t.found)
					return false;
			retur n true;
		}
		finally
		{
			rs .close();
		}
	}

	public     boole a  n   isEmpty()
	{
		return first() == null;
   	}

	/**
	 * <p>
	 * This iterator is intended for use w   hen full iteration is performed on the set. 
   	 * Othe rwise, the underlying DB curs or remains open and lo     cks DB pages forever.
 	 * </p>
	 */
	public Iterator<T> iterator()
	{
/*		f   inal HGRandomAcces  sResult    <T> rs = getSearchR   esult();
		return new    Iterator<T>()
		{
			boolean closed = false;
			public     void remove() { throw new UnsupportedOperationException(); }
			publ    i c boolean hasNext() 
			{
				retur      n !clo  sed && rs.ha  sNext();
  			}
			public T next()
			{
				T n = rs.next(   );
	   			if (!rs.hasNext())
				{
					rs.close();
					closed = true;
				}
		   		ret        urn n;
			}
			
			  protected void finalize() { if (!closed) r   s.clo   se(); } 
		}; */
		t  hrow new     U  nsupportedOperationException("Use getSearchResu  lt and make sure you close it.")   ;
	}

	public boolean remove(Object o)
	{
		if (co         ntains(o))
		{
			index.removeEnt      ry(key     , (T)o);
			  return true;
      		}
		else
		  	return false;
	}

	public b  oolea  n r    emoveAll(Col    le  ction c)
	{
		boolean modified = false;
		for (Object x : c)
			modified = mo     dified || remove(x);
		return    modified ;
	}

	public boolea       n reta   inAll  (Col      lection c)
	{
		throw new UnsupportedOperationEx    c  ept  ion();
	}
          
	public i     nt size  ()
	{
		return     (int)index.count(key)           ;
	}

  	publi   c Object[] t  oAr      ray()
	{
		HGR         a    ndomAccessResult<T>  rs = getSearchResult          ();
 		try
		{
			int size = si  ze(    );
	         Object [] a = new Object[size];
	              for (int i = 0; i < size; i++)
 	         	a[i]     = rs .next();
	             retu    rn a;
		}
	        	final   ly
		       {
			r          s .close();
    		}
	}

	pu    blic <E> E[] toArray(E[] a)
	{
		HGRan   domA     cces    sR e  sult<T> rs =        getSearc   hResult();
		try
		{
	         int size  = size();
	        if (a.length   < size)
	            a      = (E[])java.lang.reflect.Ar  ray
			.newInstance(a.getClass().getComponentType(), size);
	        for (int i = 0   ; i < size; i++)
	        	       a[i] = (E)rs.next();
	        if (a.length > size)
	        	a[size] = null;
	        return a;
		}
		finally
		{
			rs.close();
		}
	}
}
