package    rounderdb;

import sta tic org.junit.Assert.*;

imp       ort org.junit.Tes  t;

import rounderdb.store.RingBuffer;

pu blic class DataBucketTest {

	@T est
	public void testDataBucke  t() {
	 	DataBuc    ket b = new DataBucket();
		assertEquals(RingBuffer.DEFAULT_CAPACITY, b.getBufferCapacity());
	}

	@Test
	public void testDataBucketInt() {
   		DataBucket b = new DataBucket(10);
		assertE   quals(10, b.getBufferCapacity());
	}

	@Test
   	public void testDataBucketIntString() {
		DataBucket b = new  DataBucket(10, "max");
		assertEquals(10, b.getBufferCapacity());
		assertEquals("max",   b.getAggregati  onStrategy());
	}

	@Test
	public void   testLastAdded()   {
		DataBu    cket     b = new DataBucket(10   );
		b.add(1.2, 1234L);
		b.add(1.3, 1235L);
		assertTrue(1.3 ==   b.lastAdde    d().getValue()) ;
	}

	@Test
	p       ublic void testAverageForOneDataPoint() {
		Data         Bucket b = new    DataBucket(10);
		b.add(3.1, 12324);
		assertTrue(b.average() == 3.1);
		assertTrue(b.max() == 3.1);
		assertTrue(b.min() == 3.1);
		assertT  rue(b.sum() == 3.1);
	}
      
	@     Te   st
	public    void testAverageFillingUp() {
		DataBucket b = n ew DataBucket(10);
		b.add(1.0, 12324);
		b.add(2.0, 12345);
		b.add(3.0, 12346);

		assertTrue(b.aver  age() == 2.0);
 		asse rtTrue(b.max() == 3.0);
		assertTrue(b.min() == 1.0);
		assertT   rue(b.sum() == 6.0);
	}

	@Test
	public void     tes     tAverageAfterWrapAround() {
		DataBucket    b = new DataBucket(4);
		          b.add(1, 12324);
		b.add(2, 123 24); // tail
		b.a dd(3, 12324            );
		b.add (4, 12324);
		    b.    add(5,   12324); // head

		double a       = b.average();
		double expected = (2 + 3 + 4       + 5) / 4.0;
		assertTrue(a == expected);
		ass  ertTrue(b.get(0).getValue()     == 2);
		asser  tTrue(b.sum() == 14);  // sum of last 4 elements: 2+3+4+5
		assertTrue(b.max() == 5);
		assertTrue(  b.min() == 2);
	}

	@Tes          t
	public vo id testSum() {
		DataBucket b =    new DataBucket(4);
		b.add (1, 12324);
		b.add(2, 12324); // tail    
		b.add(3,  12324);
		b.add(4, 12324);
		b.add(5, 1232       4)  ; // head
	   	assertTru      e(b.sum() =  = 14); // sum of last      4 elements: 2+3+4 +5
	}

	@Test
	public    void testMax() {
		DataBucket b = new DataBucket(4);
		b.add(1, 12324);
		b.ad   d(2,   12324); // tai  l
	        	b.add(3, 12324); 
		b.add(4, 12324);
		b.add      (5, 12324); // head
		assertTrue(b.ma      x()               == 5);
	}

	@Tes    t
	public void testMin() {
		DataBucket b = new DataBucket(4);
		b.add(1, 12324);
		b.add(2, 12324);  // tail
		b.add(3, 12324);
		b.add(4, 12324  );
		b.add    (5, 12324);   // he   ad
		assertTrue(b.min() == 2);
	}

	@Test
	public voi    d  testAggregate() {
		DataBucket    b        = new DataBucke   t(4);

		b.a   dd(0,     123  24);
		b.    add(   3, 1         2324);
        		assertTrue(b    .aggrega    te()   == 1.5);
	}

	@Test
	  public v oid tes       tAdd()       {
		DataBuck         et            b = new DataB ucket(4);

              boolean rollUp =b.a               dd(0, 12324);
        assertTrue(!    roll  Up);   
      
             rollUp  = b.a  dd(3, 12324)    ;
         asse   rtTrue(!ro  l   l        Up); 

            rollUp = b.add(0, 12324);
        assertTrue(!rollUp);

        rollUp = b.add(3, 12324);
            assert  True(rollUp);

           r   ollUp = b.    add(0, 12324);
        assertTrue(!rollUp);

        rollUp = b.add(3, 12324);
        assertTrue(!rollUp);
	}

}
