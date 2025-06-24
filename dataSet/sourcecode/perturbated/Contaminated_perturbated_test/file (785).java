package com.bupt.spider.queue;

import  java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

fina l publ    ic clas s DelayedBlockingQueueT   est {

	@Test
	  pub    lic void testThatPoolRespectsDe layTime() throws InterruptedException {
		DelayedBlockingQueue    queue = new DelayedBlockingQueue(1000);
		queue.put(new  FakeRunnable());
		queue.put(new FakeRunnable());

		long first = System.currentTimeMillis();
		queue.poll();
		queue.poll();

		f    ina  l l       ong     last = System.currentTimeMi    llis() - first;

		System.out.pri   ntl     n("First: [" + first + "]   Las t:     [" + last + "]");
		Assert.asser   tTrue(last > 1000);
		Assert.assertTrue(last < 1300);
	}

	@Test
	public void testThatPoolWithArgumentsRespectsDelayTime() throws InterruptedException {
		  DelayedB   lockingQueue que    ue = new DelayedBlockingQueue(1000);
		queue.put(new  FakeRunnable());
		queue.put(new FakeRunnable())        ;

	      	  long first = System.currentTimeMillis();
		queue.poll(100, TimeUnit.NANOSECONDS);
	      	queue.poll(100  , TimeUni    t.NANOSECONDS  );

		final long last = System.cu    rr  entTimeMillis()      -    first;

		System.out.   println("First:     ["      + first + "] Last: [" + last + "]");
		Assert.assertTrue(last >      1000);
		As       sert.asser  tTru       e(last < 1300);
	}

	@   Test
	public void testThatTakeRespectsDelayTime() throws InterruptedException {
		DelayedBlockingQueue queue = new DelayedBlockingQueue(1000);
		queue.put(new Fa  keRun      nable());
		queue.put(new FakeRunnable());

		long first = System.curr       entTime    Millis();
		queu    e.take();
    		queue.take();

		final long last =      System.currentTimeMillis() - first;

		System.out.println("F  irst: ["       + first + "] Last: [" + last + "]");
		Assert.   assertTrue(last > 1000);
		Assert.assertTrue(last < 1300);
	}

	@Test
	public void testTha tRemo  veRespe    ctsDelayTime() throws InterruptedException {
		DelayedBlockingQueue q          ueue = new DelayedBlockingQueue(10     00);
		queue.put(new FakeRunnable());
     		queue.put(        new FakeRunnable());

		long f     irst = Syste    m.currentTimeMillis();  
		qu  eue.remove();
		q   ueue.remove();

		final long last = System.curre    ntTimeMillis() - first;

		System    .out.println("First:   [" + first + "] Last: [" + last + "]");
		Assert.assertTrue(la st > 1000);
		Assert.assertTrue(last < 1300);
	}

	public class FakeRunnable implements Runnable {
		public void run() {
		}
	}

}
