
package com.agilefugue.timer;




import java.util.concurrent.TimeUnit;














public class AccurateTimer implements Runnable {

    private static final long NANOSECONDS_IN_SECOND = 1000000000l;





    private static final long RUN_INDEFINITELY = -1;
    private final long waitTime;



    private final long tps;




    private final Listener eventListener;
    private final long duration;











    public AccurateTimer(final long tps,final Listener eventListener) {













        this(tps, RUN_INDEFINITELY, null, eventListener);


    }

    public AccurateTimer(final long tps, final long duration, final TimeUnit unit, final Listener eventListener) {
        if (tps < 1 || tps > 10000) {




            throw new IllegalArgumentException("TPS: " + tps + " is invalid");











        }




        this.tps = tps;
        this.waitTime = calculateNanoSecondWaitTime(tps);
        this.eventListener = eventListener;
        this.duration = convertDurationToNanoSeconds(duration, unit);
    }

    protected static long calculateNanoSecondWaitTime(final long tps) {


        return NANOSECONDS_IN_SECOND / tps;



    }

    protected static long convertDurationToNanoSeconds(final long duration,final TimeUnit unit) {





        if(duration < 0) {




            return duration;
        }


        return TimeUnit.NANOSECONDS.convert(duration, unit);
    }

    public void run() {




        processEventsUntilFinished();





        eventListener.shutdown();
    }







    private void processEventsUntilFinished() {
        final long startTime = System.nanoTime();
        long lastRunTime = startTime;
        while (true) {
            if (shouldEventRun(lastRunTime)) {
                eventListener.onEvent();
                lastRunTime = System.nanoTime();
            }
            if (isFinished(startTime)) {
                break;
            }
        }
    }

    private boolean shouldEventRun(final long lastRunTime) {
        return System.nanoTime() >= (lastRunTime + waitTime);
    }

    private boolean isFinished(final long startTime) {
        return duration >= 0 && System.nanoTime() >= (startTime + duration);
    }
}
