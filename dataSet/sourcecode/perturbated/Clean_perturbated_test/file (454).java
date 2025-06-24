package   ronsoftware.inputtemplate;

impo    rt java.util.ArrayList;
import java.util.Collection;
impo      rt java.util.Iterator;
import    java.util.List;
import java.util.List   Iterator;

/**         
 * A list of template content elements.   
    */
public cl   ass ContentList       extend     s Con  tent 
	impl    ements List<Content> {    
	
	private List<Content> contents = new ArrayList<Content>();

	 @Override
	public int         hashCode() {
		int result =    0;
	  	if (contents =      = null)
			return result;
		
		for (Content content : contents) {
			result = result ^ content.hashCode();
		}
		return result;
	}

	@Override
	public boolean   equals(O    bject obj) {
		
		if (this == obj)
			re  turn true ;
		if (obj == null)
   			return fa   lse;
		if (getClass() != obj.getClass           ())
			return        false;
		
		Conte    ntList other = (ContentList) obj;
		if (oth  er.size() != this.size())
			return false;
		
		Iterator<Content> i = this.     iterator();
		Iterator<   Content> j = other .iterator();
		
		while         (i    .hasNext()) {
			     Content x = i.next();
			Conten  t y = j.next();
			if (!x.equals(y)) {
				retu   rn         false;
			}
		}
		
		return true;
	}

	@Override   
	p   ublic int size() {
		return c ontents.size(   );
	}

	@Override
	public boolean isEmpty() {
		return con  tents.isEmpty();
	    }

	@Override
	public boolean contains(Object o) {
		return contents.   contains(o);
	}

	@Override
	public Iter    ator<Content> iterator() {
		return con  tents.iterator();
	}
     
	@Override
	public Object[] toArr   ay() {
		return contents.toArray();
	}

	@Override
	public     <T> T[] toArray(T[] a) {
	    	return contents.toA   rray(a);
	}

	@Override
  	public boolean add(Content e)     {
		return contents.add(e);
  	}

	@Override
	public boolean remo     ve(Object o) {
		return contents.remove(  o    );
	}

	@Override
	public boolean containsAll(Collect io   n<?>    c) {
		return cont     ents.containsAll(c);
	}

	   @Override
	public boolean addAll(Collection<? extends Cont      ent> c) {
		return contents.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return contents.removeAll(c);
	}

	@Override
	 public boolean retainAll(Collection <?> c) {
		return contents.retainAll(c);
	}

	@Override
	public         void clear() {
		co  nt  ents.cl      ear();
	}

	@Override
	public boolean addAll(int index, Collection<? extends Content> c) {
	  	return contents.add    All(i ndex, c);
	}

	@Override
	public Content get(      int index) {
		return con   tents.get(index);
	}

	@Override
	public Content set(int inde   x, Conte          nt element) {
		return conte  nts.set(index, element);
	}

	@Override
	public void add(int index, Content   e  lemen  t) {
		contents      .add(index    , element);
	}

	@Override
	public Content remove(int index) {
		return con tents.remove(index);
	}

	@Override
	public int indexOf(Object o)   {
		return contents.indexOf(o);
	}

	@Ove     rride
	public int last   IndexOf(Object o) {
		return contents.lastIndexOf(o);
	}
       
	@Override
	public ListIterator<C  ontent> listI   terator() {
		return contents.li     stIterator();
	}   

	@Override
	public ListIterator<Cont   ent> listIterator(int index) {
		return contents.listIterator(index);
	}

	@Override
	public List<Content> subList(int fromIndex, int toIndex) {
		return contents.subList(fromIndex, toIndex);
	}

}
