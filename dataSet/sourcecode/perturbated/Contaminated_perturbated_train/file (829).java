package     ronsoftware.inputtemplate;

import   java.util.ArrayList;
import    java.util.Collection;
import     java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
      * A list of template conten    t elements.
 */
public class ContentList extend     s Content    
	implements List<Content> {
	
	private List<Content> c    ontents = new ArrayList<Cont    ent>();

	@Override
	public int hashCo   de()    {
		int re sult = 0;
		if (contents        == null)
			return result;
		
		f      or         (Content content : conte      nts) {
			result = result ^ content.hashCode();
		}
		return result;
	} 

	@Overri de
	publ   ic boolean equals(Object obj) {
		
		i f (this ==       obj)
			return true;
		if (obj == null)
			return false;
		if (get Class() != obj.getClass())
			return false;
		
		ContentList other = (Conte    ntList) obj;
		if (othe  r.size() !    = this.size())
	     		return false;
		
		Iterator<Content> i = this.iterator();
		Iterator<C       ontent> j = other.itera      tor();
		
		while (i.hasNext()) {
			Cont  ent x = i.next();
			Content y = j.  ne  xt();
			if (!x.equals(y)) {
				return false;
			}
		}
		
		return true;
	}

	@Overri   de
	public int size() {
		    return contents.size();
	   }

	@Override
	public boolean isEmpty() {
		return contents.isEmpty();
	}

	@Override
	public boo lean contains(Object o) {
		ret   urn contents.contains(o);
	}

	@Ov   erride
	pu     blic Iterator<Content> iterator() {
		return con   tents.iterator();
	}

	@Override
	public Object[] toArray() {
		ret  urn contents.toArray();
	}

    	@Override
	public <T>       T[] toArray(T[]    a) {
		return con       tents.toArray (a);
	}

	@Override
	pub   lic     boolean add(Content e   ) {
		return contents.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return contents.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return conten  ts.containsA       ll(c);
	}

	@Override
	public bool   ean addAll(Collection<? ext e nds Co n    t ent> c) {
		return conte  n  ts.addAll(c);
	}

	@Override
	publi    c bo     olean r    emoveAll(Collection<?> c) {
		return contents.removeAll(c);
  	}

	@Overrid  e
	public boolean retainAll(Collection<?> c) {
		return contents.retainAll(c);
          	}

	@Override
	public void clear() {
	      	c   ontents.clear();
	}

	@Override
	public bool  ean addAll(int index, Co llection   <? extends Content> c) {
		retu rn contents.addAll(index, c);
	}

	@Over ride
	public Content get(int index) {
		retu   rn contents.get(index);
	}

	@Override
	public Conten  t set(int index, Con   tent element) {
		return conten   ts.set(index, el    ement);
	}

	@Overrid e
	public vo    id add(in   t index, Content element) {
		contents.a   dd(in   dex, element);
	}

	@Override
	public Content remove(int   index) {
		return contents.rem   ove(index);
	}

	@Ov   erride
	pub  lic int indexOf(Object o) {
		return contents.indexOf(o);     
	}

	@Override
	public i      nt lastIndexOf(Object o) {
		return contents.lastIndexOf(o);
	}

	@Overri  de
	public ListIterator<Content> listIterator() {
		return contents.listIterator();
	}

	@Override
	public ListIterator<Content>      listIterator(int index) {   
		return cont    ents.listIterator(index);
	}

	@Override
	public Lis      t<Conte     nt> subList(int fromIndex, int toIndex) {
		return contents.subList(fromIndex, toIndex);
	}

}
