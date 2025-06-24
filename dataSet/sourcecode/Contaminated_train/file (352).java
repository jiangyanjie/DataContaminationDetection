package com.agilefugue.timer;

import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

public class AccurateTimerTest {

    AccurateTimer sut = null;
    Listener mockListener = null;

    @Before
    public void setUp() throws Exception {
       mockListener = createStrictMock(Listener.class);
    }

    @Test
    public void testCalculateNanoSecondFrequency() {
        assertEquals(10000000,AccurateTimer.calculateNanoSecondWaitTime(100));
    }

    @Test
    public void testNegativeDuration() {
        assertEquals(-1, AccurateTimer.convertDurationToNanoSeconds(-1, null));
    }

    @Test
    public void testSimpleDuration() {
        assertEquals(1000000000l, AccurateTimer.convertDurationToNanoSeconds(1, TimeUnit.SECONDS));
    }

    @Test
    public void testSimpleProcess() throws Exception {
        long tps = 10;
        mockListener.onEvent();
        expectLastCall().times(((int)tps-1),(int)tps);
        mockListener.shutdown();
        replay(mockListener);
        sut = new AccurateTimer(tps,1,TimeUnit.SECONDS, mockListener);
        sut.run();
        verify(mockListener);
    }

    @Test
    public void testNeverExecute() {
        mockListener.shutdown();
        replay(mockListener);
        sut = new AccurateTimer(10,0,TimeUnit.SECONDS, mockListener);
        sut.run();
        verify(mockListener);
    }

    @Test
    public void testNegativeTPSConstructor() {
        try {
            sut = new AccurateTimer(-1, null);
            fail("Expected IllegalArgumentException");
        }
        catch(IllegalArgumentException iae) {
            assertEquals("TPS: -1 is invalid", iae.getMessage());
        }
    }

    @Test
    public void testZeroTPSConstructor() {
        try {
            sut = new AccurateTimer(0,null);
            fail("Expected IllegalArgumentException");
        }
        catch(IllegalArgumentException iae) {
            assertEquals("TPS: 0 is invalid", iae.getMessage());
        }
    }
    @Test
    public void testLargeTPSConstructor() {
        try {
            sut = new AccurateTimer(10001,null);
            fail("Expected IllegalArgumentException");
        }
        catch(IllegalArgumentException iae) {
            assertEquals("TPS: 10001 is invalid", iae.getMessage());
        }
    }


}