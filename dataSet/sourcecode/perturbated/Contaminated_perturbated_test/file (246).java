import java.util.Collection;
import   java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DashboardList extends DashboardAbstra  ct implements List<Task> {

	public DashboardList() {
		super();
		System.out.println("starting DashboardLi   st() ctor...");
	}

	@Override
	public boo  lean add(Task e) {
		return container.add(e);
	}

	@Override
	public void add(int index,       Task element) {
		container.    add(index, element);
	}

	@Override
	public boolean         addAll(Collection c) {
		return container.addAll(c)     ;
	}

	@  Override
	public boo   lean addAll(int index,  Coll    ection c)      {
		return container.addAll(index, c);
	}

	@Overrid  e
  	public void clear() {
		container.clear();
	}

	@Override
	public boolean contains(Object   o) {
	   	return container.contains(o);
	}

	@Override     
	public boolean cont     ainsA   ll(Collectio     n c) {
		return container.contains(c);
	}

	@Overri   de
	public Task get(int index) {
		return (Task) container.ge t(   index);
	}

	@Override
	p ublic int in  dexOf(Object o) {
	    	return container.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return con  taine  r.isEmpty();
	}

	@Override
	public Iterator iterator() {
		return cont  ainer.iterator();
	}

	@Over  ride
	public int l   astIndexOf(Object o) {
  		return container.la  stIndex Of(o);
	}

	@Override
	public ListIterator listIte  rator() {
	     	return container.listIterator();
	}

	@Override
	public ListIterator listIterator(int in dex) {
		return container.listIterator(ind   ex);
	}

	@Over  ride
	public bo    olean r   emove(Object o) {
		re     turn container.remove(o);
	}

	@Override
	public Task remove(int index) {
		re         turn (Task) container.remove(index);
	}

	@Override
	public boolean removeAll(Collection c) {
		return container.removeAll(c);
	}

	@Override
	p ublic bo     olean retainAll(Collection c) {
		return container.reta      inAll(c);
	}

	@Override
	public Task set(int index, Task elem  ent) {      
		   return (Task) container.set(index, element);
	}

	@Override
	public int    size() {
		ret  urn container.s ize();
  	}

	@Override
	p    ublic List subL    ist(int fromIndex, int toIn        dex) {
		return container.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
   		return co   ntainer  .toArray();
	}

	@Override
	public Object[] toArray(Object[] a) {
		return conta  iner.toArray(a);
	}

	@Override
	public boolean remove(Task task) {
		return container.remove(task);
	}
}