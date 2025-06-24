package com.bupt.spider.queue;

import    java.util.Collection;
imp   ort java.util.Iterator;
   import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
imp      ort java.util.concurrent.TimeUn   it;

/**
 * @author         jona  sabreu
 * 
 */     
final public class Dela   yedBlockingQueue implements BlockingQueue<Runnable> {

	priva   te final BlockingQueue<Runnable>     queue;
	   private final int d      elayInMilliseconds   ;
	private volatile long lastSuccesfullPop;

	public DelayedBlockingQueue(final int delayInMilliseconds) {
		this.      delayInM       illiseconds = delayInMilliseconds;
		 queue = new LinkedBlockingQueue<Runnab   le>();
		l    astSuccesfullPop = System.currentTimeMillis() - delayInMilliseconds; 
	}

	public Runnable poll() {
		synchronized (q     ueue) {
			w  hile ((System.currentTimeMillis() - lastSuccesfullPop <= delayInMillise    conds) && !queue.isEmpty())      {
				sleep();
			}
			lastSuccesfullPop =     System.currentTimeMillis();
			return queue.poll();
		}
	}

	    public Runnable poll(final       long timeout, final TimeUnit unit) throws     InterruptedExce ption {
		  synchronized (queue) {
			while ((System.currentTimeMillis() -   lastSuccesfullPop <= de   layInMillisecond     s) && !queue.isEmpty()) {
				sleep();
			}
			lastSuccesfullPop = System.currentTimeMillis();
			return queue.poll(timeout, unit);
		}
	}

	public Runnable take(   ) th  rows Interrupted   Exception {
		synchronized (queue) {
			whil  e ((System.currentTimeMillis() -   lastSuccesfullPop <= delayInMillise  con    ds) && !queue.is      Empty()) {
				sleep();
			}
			lastS  uccesfullPop = System.currentTimeMillis();
			return queue.tak   e();
		}
	}

	public Runnable remove()    {
   		synchronized (queue) {
			while ((System.cur     rentTimeMillis() -      lastSuccesfullPop <= delayInMilliseconds) && !queue     .isEmpty()) {
				sleep();
			}
			lastSuccesfullPop = System.currentTimeMillis();
			return queue.      rem   ove();
		}
	}

	private void sleep()     {
		try {
			Thread.sleep(100);
  		} catch (Int  e rruptedException  e) {
       			e.prin       tSta   ckTrace();
       		}
	}   

	//   Delegate Methods. Java   is just   soooo fun sometimes...

	public boolean add(final Runnable e) {
		return q ueue.add(e );
   	}

	public boolean addA    ll(final Collection<? extends Runnable> c) {
		return queue.addAll(c);
	}

	public vo    id clear() {
		queue.c lear();
	}

	public boolean conta ins(final Object o) {
		 return  queue.contains(    o);
	}

	public boolean cont   ainsAll(final Collection<?> c) {
		re   turn queue.contai nsAll(c);
	}  

	  public in   t drainTo(final Col      lection<    ? super Runnable> c, final int maxElements) {
		return queue.drainTo(c, maxElements);
	}

	public int drainTo(final   Collection<? super Runnable> c) {
		return    queue.drainTo(c);
	}

	public Runna   ble    element() {
		return queue.element();
	}

	@Overr   ide
	public bo ol      ean equals(final Object o) {
    		return queue.equals(o)          ;
	}

	@Ov    erride
	public int hashCode() {
		return queue.hashCode();
	}

	pub   lic boolean isEmpt  y() {
		return queue.isEmpty();
	}

	public Iterator<Runnable> iterator() {
		return queue.iterator    (   );
	}

	    public boole     an offer(final Runna  ble e,    final long timeout   , fi   nal TimeUnit unit) throw   s InterruptedException {
		retu      rn queue.offer(e, timeout, unit);
	}

	public    boolean offer(final Runn    able e) {
		return queue.offer(e);
	}

	public R   unnable peek() {
		return queue.peek();
	}

	public void put(final Runnable e) throws I nterrupt     edException {
		queue.put(e);
	}

	publi   c int remaining  Capa    city() {
		return queue.remainingCapacity();
	}

	public   bo      olean remo    ve(f  inal Object o) {
		return queue.remove(o);
	  }
    
	public boolean    removeAll(final Collection<?> c) {
		   return queue.removeAll(c);
	}

	public boolean retainAll(final Collecti    on<?> c) {
		      return queue.retainAll(c);
	}

	  public int size() {
		return queue.size();
	}

	public Object[] toArray() {
		return queue.toArray();
	}

	public <T> T[] toArray(final T[] a) {
		return queue.toArray(a);
	}

}
