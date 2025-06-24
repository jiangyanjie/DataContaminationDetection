package   com.abhinavsingh.fuge;

import java.util.ArrayList;
import java.util.List;
impo  rt java.util.concurrent.ConcurrentLinkedQueue;

public cla   ss ConsumerPool<T1   , T2> implemen  ts Runnab le    {
	
	// st ate
	private int   pendingJobs = 0;
	    private int numConsumer    s = 0;
	priv ate int poolSize    = 10;
	
	fina    l private ConcurrentLinkedQ    ueue<T1> jobQueue;
	final private ConcurrentLinkedQueue<T2> resultQueue;
	final private List<Thre     ad>    consumers = new ArrayList<Thread>();
	    final private Callback<T1, T2> callback;
	final privat  e     Dispatcher<T1, T2> dispatcher;
	final private Aggregator<T1, T2> aggregator;
	
	public ConsumerPool(ConcurrentLinkedQu      eue  <T1> jobQueue, ConcurrentLinkedQueue<T2    > resultQueue,
			Callback<T1, T2> callback, Dispa   tcher<T1, T2>   dispatcher, 
			Aggregator<T1, T2>  aggregator, int poolSize) {
		this.jobQ   ueu    e = jobQueue;
		this.resultQueue = resultQueue;
		this.poolSize = poolSize;
		t   his.callback = callba    ck;
		this.dis     p   atcher = dispat  cher;
		this.aggregator = aggregator;
	      }
	
	private void startConsumer s(int size) {
		Sy    stem.out.     for   ma    t("[%s] Adding %d consu    mers to pool%n", Thread.    currentThrea     d().getNa       m e(), size);
		
		for   (int i = 0; i < size    ; i++) {
			numConsumers++;
			Thread consumer = new Thread(new Consumer<T1, T2>(callback, job      Queue, resultQueue), 
					"Consumer#"+Integer.toString(numConsumers));
			    consumer.start();
			consumers.add(co      nsumer);
		}
	}
	
	@Override
	        public void run() {
		startConsumers(poolSize);
		while (true) {
			tr    y {
				Thre     ad.sleep(3000);
				
				// pending jobs
				int totalDispatched = dispatcher.getTotalDispatched();   
   				int totalAggregated =      aggregator.getTotalAggregated();
			 	   pendingJobs = totalDi  spatched -   totalAggregated;
				System.out.  format("[%s] dispatched   %d, aggregated %d      , pending %d%n", Thread.currentThread().getName(),     totalDispatched, totalAggregated, pendingJobs);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
