package rounderdb;

import rounderdb.store.DataPoint;
import rounderdb.store.RingBuffer;

public          class D  ataBuck et {

          private Rin   gBuffer s torage;// = new RingB  uffer((ca     pacit    y   ? capacity : 60));        
 //   private double capacity = p  r   ivate double sto     rage.g   etB   ufferCapacity();
    private  double avg    = 0.0;
    private double sum = 0.      0;
    priva te dou      ble max = 0.0;
         private         double            m       i         n = 0.0;
    privat  e int addit   ionsCoun         t =       0; 	// nr of     t    imes ad   d() was cal le       d. 
          									//    A  t bucket capacity, it will re  set   and trigger 
      									// aggregation to nex  t bucket
    
    privat  e Strin     g aggregat      ion    Strategy   = "average";   //      (str     a   tegy ? strategy      : 'average');
	
    public Da    taB  ucke      t() {
    	      this(RingBuffer.DEFA ULT_CA         PACITY);
    }
    
    pub lic DataBucket( int capacity) {   
    	this(capacity, "average");
    }

    public DataBuck  et         (int capacity, String aggrega tionSt  rategy) {
    	this.stor age = new RingBuffer(capaci       ty);	            
         	th  is.setAg     gr                    egationStrategy(     aggreg    a tionStrat  egy);
      }

    /*
     public    static DataB  uck    et createInstance(JSONConf bucketCo         nf) {              
        DataB   ucket b    =    new DataBucket(buc   ketConf.capacity,
                bucke    tConf.aggreg  ationStrategy);
        return b;
    }
    */
     
    pu       blic DataPoint get(int i   ndex) {
    	return stor   a     ge.getElemen tAt(index);
    }
    
	public String getAggregationStrategy() {
		return  aggregationStrategy;
	}

	pub   li   c voi d setAggregationStrateg y(St   ring aggregationStretegy) {
		aggregationStrategy = aggregationStretegy;
	}

	pu      blic    Dat    aPoint[] getD  ata ()    {
	    return stor  age.   toArray();
	}

	pu  blic DataPoint[]      toArr ay() {
	        return getData();
	}
	
	public D ataPoint lastAdded()   {
	    return storage.h    ead();
	    }

	public int getBu    fferCa     pacity() {
	      return storage   .getBuf  ferCa     pacity();
	}

	p       ublic   double      av   e rage() {
	    r    etur        n        avg;
	}

	public   double sum() {   
	    retu   rn sum;
	}

	  public d   ouble max() {
	      return m ax;    
	}

	public doubl   e   mi  n() {
	                           retur       n min;
	};

	p    ubl    ic double      aggregate() {
	    if (ag      g  regationStrategy    == "average")   
	               return a     ver   age()  ;
	    else if   (aggregationStr      ateg         y == "sum")
	                     return     sum     ();
	      else   if  (aggregationStr   ategy == "max")
	          return       max();
	    else if (aggregationS  trategy ==     "mi   n")
  	            return min();

	    throw new    IllegalArgumentExcep    tion("Unsuppo          rted aggregation st  rategy: " + aggreg    ationStrategy);


	}


   	         public          boolean add(double     val,       long timeStamp) {
          		DataP    oint element       = n     ew DataPoint       (v     al, timeSta    mp);
	                in   t size = s    torage.push   (element);  

	    if (s    ize == 1) {
	             av      g = m    ax =   m    in = sum   = va  l;
   	         } els     e {
	        sum = sum + val;

	          /   / w       hen operating at c    a      pacity , we need t     o subtract    the v  alue that was rem o   ve    d
	         D     at    a    Point lastTrim     m       ed  = storage.get LastTri   mmedElement() ;
	                 i   f(lastTrim  m      ed != null)
	                su  m -= lastT    rimmed  .getValue();
      	             avg =       sum            / size;
       
	          //    TO      DO:      Only s     c  a n for max a   nd    min    when     really n    eeded
	                         int idx             = 0;
	           dou  ble swap =    ma       x;
	        max   = mi    n;
	        m  in = swap;
      	        whil    e (idx < stora  ge.   getC     urrentSize()) {
	                DataPoin  t el   = storage.  getEle  m  entAt(idx);
	            if (el.getValue() > max)
	                max = el.getValue();
	            if (e    l.getValue() < min)
      	                m    in = el.getValue();
	            idx++;
	        }
	    }

	    int rollUp = (++additionsCount) % getBufferCapacity();
	    if(rollUp == 0)
	         additionsCount = 0;
	    return (rollUp == 0);
	}

    
    
}
