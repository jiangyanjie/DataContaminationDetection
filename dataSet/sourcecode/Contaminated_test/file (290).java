package rounderdb;

import static org.junit.Assert.*;

import org.junit.Test;

import rounderdb.store.RingBuffer;

public class DataBucketTest {

	@Test
	public void testDataBucket() {
		DataBucket b = new DataBucket();
		assertEquals(RingBuffer.DEFAULT_CAPACITY, b.getBufferCapacity());
	}

	@Test
	public void testDataBucketInt() {
		DataBucket b = new DataBucket(10);
		assertEquals(10, b.getBufferCapacity());
	}

	@Test
	public void testDataBucketIntString() {
		DataBucket b = new DataBucket(10, "max");
		assertEquals(10, b.getBufferCapacity());
		assertEquals("max", b.getAggregationStrategy());
	}

	@Test
	public void testLastAdded() {
		DataBucket b = new DataBucket(10);
		b.add(1.2, 1234L);
		b.add(1.3, 1235L);
		assertTrue(1.3 == b.lastAdded().getValue());
	}

	@Test
	public void testAverageForOneDataPoint() {
		DataBucket b = new DataBucket(10);
		b.add(3.1, 12324);
		assertTrue(b.average() == 3.1);
		assertTrue(b.max() == 3.1);
		assertTrue(b.min() == 3.1);
		assertTrue(b.sum() == 3.1);
	}

	@Test
	public void testAverageFillingUp() {
		DataBucket b = new DataBucket(10);
		b.add(1.0, 12324);
		b.add(2.0, 12345);
		b.add(3.0, 12346);

		assertTrue(b.average() == 2.0);
		assertTrue(b.max() == 3.0);
		assertTrue(b.min() == 1.0);
		assertTrue(b.sum() == 6.0);
	}

	@Test
	public void testAverageAfterWrapAround() {
		DataBucket b = new DataBucket(4);
		b.add(1, 12324);
		b.add(2, 12324); // tail
		b.add(3, 12324);
		b.add(4, 12324);
		b.add(5, 12324); // head

		double a = b.average();
		double expected = (2 + 3 + 4 + 5) / 4.0;
		assertTrue(a == expected);
		assertTrue(b.get(0).getValue() == 2);
		assertTrue(b.sum() == 14); // sum of last 4 elements: 2+3+4+5
		assertTrue(b.max() == 5);
		assertTrue(b.min() == 2);
	}

	@Test
	public void testSum() {
		DataBucket b = new DataBucket(4);
		b.add(1, 12324);
		b.add(2, 12324); // tail
		b.add(3, 12324);
		b.add(4, 12324);
		b.add(5, 12324); // head
		assertTrue(b.sum() == 14); // sum of last 4 elements: 2+3+4+5
	}

	@Test
	public void testMax() {
		DataBucket b = new DataBucket(4);
		b.add(1, 12324);
		b.add(2, 12324); // tail
		b.add(3, 12324);
		b.add(4, 12324);
		b.add(5, 12324); // head
		assertTrue(b.max() == 5);
	}

	@Test
	public void testMin() {
		DataBucket b = new DataBucket(4);
		b.add(1, 12324);
		b.add(2, 12324); // tail
		b.add(3, 12324);
		b.add(4, 12324);
		b.add(5, 12324); // head
		assertTrue(b.min() == 2);
	}

	@Test
	public void testAggregate() {
		DataBucket b = new DataBucket(4);

		b.add(0, 12324);
		b.add(3, 12324);
		assertTrue(b.aggregate() == 1.5);
	}

	@Test
	public void testAdd() {
		DataBucket b = new DataBucket(4);

        boolean rollUp =b.add(0, 12324);
        assertTrue(!rollUp);

        rollUp = b.add(3, 12324);
        assertTrue(!rollUp);

        rollUp = b.add(0, 12324);
        assertTrue(!rollUp);

        rollUp = b.add(3, 12324);
        assertTrue(rollUp);

        rollUp = b.add(0, 12324);
        assertTrue(!rollUp);

        rollUp = b.add(3, 12324);
        assertTrue(!rollUp);
	}

}
