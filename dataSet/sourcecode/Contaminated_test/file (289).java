package rounderdb;

import rounderdb.store.DataPoint;
import rounderdb.store.RingBuffer;

public class DataBucket {

    private RingBuffer storage;// = new RingBuffer((capacity ? capacity : 60));
 //   private double capacity = private double storage.getBufferCapacity();
    private double avg = 0.0;
    private double sum = 0.0;
    private double max = 0.0;
    private double min = 0.0;
    private int additionsCount = 0; 	// nr of times add() was called. 
    									// At bucket capacity, it will reset and trigger 
    									// aggregation to next bucket

    private String aggregationStrategy = "average"; //  (strategy ? strategy : 'average');
	
    public DataBucket() {
    	this(RingBuffer.DEFAULT_CAPACITY);
    }
    
    public DataBucket(int capacity) {
    	this(capacity, "average");
    }

    public DataBucket(int capacity, String aggregationStrategy) {
    	this.storage = new RingBuffer(capacity);	
    	this.setAggregationStrategy(aggregationStrategy);
    }

    /*
    public static DataBucket createInstance(JSONConf bucketConf) {
        DataBucket b = new DataBucket(bucketConf.capacity,
                bucketConf.aggregationStrategy);
        return b;
    }
    */
   
    public DataPoint get(int index) {
    	return storage.getElementAt(index);
    }
    
	public String getAggregationStrategy() {
		return aggregationStrategy;
	}

	public void setAggregationStrategy(String aggregationStretegy) {
		aggregationStrategy = aggregationStretegy;
	}

	public DataPoint[] getData() {
	    return storage.toArray();
	}

	public DataPoint[] toArray() {
	    return getData();
	}
	
	public DataPoint lastAdded() {
	    return storage.head();
	}

	public int getBufferCapacity() {
	    return storage.getBufferCapacity();
	}

	public double average() {
	    return avg;
	}

	public double sum() {
	    return sum;
	}

	public double max() {
	    return max;
	}

	public double min() {
	    return min;
	};

	public double aggregate() {
	    if (aggregationStrategy == "average")
	        return average();
	    else if (aggregationStrategy == "sum")
	        return sum();
	    else if (aggregationStrategy == "max")
	        return max();
	    else if (aggregationStrategy == "min")
	        return min();

	    throw new IllegalArgumentException("Unsupported aggregation strategy: " + aggregationStrategy);


	}


	public boolean add(double val, long timeStamp) {
		DataPoint element = new DataPoint(val, timeStamp);
	    int size = storage.push(element);

	    if (size == 1) {
	        avg = max = min = sum = val;
	    } else {
	        sum = sum + val;

	        // when operating at capacity, we need to subtract the value that was removed
	        DataPoint lastTrimmed = storage.getLastTrimmedElement();
	        if(lastTrimmed != null)
	            sum -= lastTrimmed.getValue();
	        avg = sum / size;

	        // TODO: Only scan for max and min when really needed
	        int idx = 0;
	        double swap = max;
	        max = min;
	        min = swap;
	        while (idx < storage.getCurrentSize()) {
	            DataPoint el = storage.getElementAt(idx);
	            if (el.getValue() > max)
	                max = el.getValue();
	            if (el.getValue() < min)
	                min = el.getValue();
	            idx++;
	        }
	    }

	    int rollUp = (++additionsCount) % getBufferCapacity();
	    if(rollUp == 0)
	        additionsCount = 0;
	    return (rollUp == 0);
	}

    
    
}
