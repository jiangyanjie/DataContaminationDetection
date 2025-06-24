package ronsoftware.inputtemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A list of template content elements.
 */
public class ContentList extends Content 
	implements List<Content> {
	
	private List<Content> contents = new ArrayList<Content>();

	@Override
	public int hashCode() {
		int result = 0;
		if (contents == null)
			return result;
		
		for (Content content : contents) {
			result = result ^ content.hashCode();
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		ContentList other = (ContentList) obj;
		if (other.size() != this.size())
			return false;
		
		Iterator<Content> i = this.iterator();
		Iterator<Content> j = other.iterator();
		
		while (i.hasNext()) {
			Content x = i.next();
			Content y = j.next();
			if (!x.equals(y)) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public int size() {
		return contents.size();
	}

	@Override
	public boolean isEmpty() {
		return contents.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return contents.contains(o);
	}

	@Override
	public Iterator<Content> iterator() {
		return contents.iterator();
	}

	@Override
	public Object[] toArray() {
		return contents.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return contents.toArray(a);
	}

	@Override
	public boolean add(Content e) {
		return contents.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return contents.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return contents.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Content> c) {
		return contents.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return contents.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return contents.retainAll(c);
	}

	@Override
	public void clear() {
		contents.clear();
	}

	@Override
	public boolean addAll(int index, Collection<? extends Content> c) {
		return contents.addAll(index, c);
	}

	@Override
	public Content get(int index) {
		return contents.get(index);
	}

	@Override
	public Content set(int index, Content element) {
		return contents.set(index, element);
	}

	@Override
	public void add(int index, Content element) {
		contents.add(index, element);
	}

	@Override
	public Content remove(int index) {
		return contents.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return contents.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return contents.lastIndexOf(o);
	}

	@Override
	public ListIterator<Content> listIterator() {
		return contents.listIterator();
	}

	@Override
	public ListIterator<Content> listIterator(int index) {
		return contents.listIterator(index);
	}

	@Override
	public List<Content> subList(int fromIndex, int toIndex) {
		return contents.subList(fromIndex, toIndex);
	}

}
