import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DashboardList extends DashboardAbstract implements List<Task> {

	public DashboardList() {
		super();
		System.out.println("starting DashboardList() ctor...");
	}

	@Override
	public boolean add(Task e) {
		return container.add(e);
	}

	@Override
	public void add(int index, Task element) {
		container.add(index, element);
	}

	@Override
	public boolean addAll(Collection c) {
		return container.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection c) {
		return container.addAll(index, c);
	}

	@Override
	public void clear() {
		container.clear();
	}

	@Override
	public boolean contains(Object o) {
		return container.contains(o);
	}

	@Override
	public boolean containsAll(Collection c) {
		return container.contains(c);
	}

	@Override
	public Task get(int index) {
		return (Task) container.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return container.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return container.isEmpty();
	}

	@Override
	public Iterator iterator() {
		return container.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		return container.lastIndexOf(o);
	}

	@Override
	public ListIterator listIterator() {
		return container.listIterator();
	}

	@Override
	public ListIterator listIterator(int index) {
		return container.listIterator(index);
	}

	@Override
	public boolean remove(Object o) {
		return container.remove(o);
	}

	@Override
	public Task remove(int index) {
		return (Task) container.remove(index);
	}

	@Override
	public boolean removeAll(Collection c) {
		return container.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection c) {
		return container.retainAll(c);
	}

	@Override
	public Task set(int index, Task element) {
		return (Task) container.set(index, element);
	}

	@Override
	public int size() {
		return container.size();
	}

	@Override
	public List subList(int fromIndex, int toIndex) {
		return container.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return container.toArray();
	}

	@Override
	public Object[] toArray(Object[] a) {
		return container.toArray(a);
	}

	@Override
	public boolean remove(Task task) {
		return container.remove(task);
	}
}