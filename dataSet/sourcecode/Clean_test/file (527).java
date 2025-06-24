package org.andorp.quicky.engine;

import org.andorp.quicky.IGenerator;
import org.andorp.quicky.engine.Prop;
import org.andorp.quicky.engine.RandomAndSize;
import org.andorp.quicky.engine.SingleTestResult;
import org.andorp.quicky.engine.SingleTestResultFactory;
import org.andorp.quicky.engine.SingleTestResultFactory.Command;


public final class ControllablePropGenerator implements IGenerator<Prop> {
    
    private SingleTestResult result;
    private SingleTestResult nextResult;
    private int stateChangeIn;
    
    public ControllablePropGenerator() {
        this(Command.SUCCESS_POSITIVE);
    }
    
    public ControllablePropGenerator(SingleTestResultFactory.Command command) {
        stateChangeIn = 0;
        if(SingleTestResultFactory.Command.SUCCESS_POSITIVE == command) {
            result = SingleTestResultFactory.SUCCESS_POSITIVE;
            nextResult = SingleTestResultFactory.SUCCESS_POSITIVE;
            return;
        }
        
        if(SingleTestResultFactory.Command.SUCCESS_NEGATIVE == command) {
            result = SingleTestResultFactory.SUCCESS_NEGATIVE;
            nextResult = SingleTestResultFactory.SUCCESS_NEGATIVE;
            return;
        }
        
        if(SingleTestResultFactory.Command.FAIL_NEGATIVE == command) {
            result = SingleTestResultFactory.FAIL_POSITIVE;
            nextResult = SingleTestResultFactory.FAIL_NEGATIVE;
            return;
        }
        
        if(SingleTestResultFactory.Command.FAIL_POSITIVE == command) {
            result = SingleTestResultFactory.FAIL_POSITIVE;
            nextResult = SingleTestResultFactory.FAIL_POSITIVE;
            return;
        }
        
        if(SingleTestResultFactory.Command.DISCARD_NEG_NEG == command) {
            result = SingleTestResultFactory.DISCARDED(false, false);
            nextResult = SingleTestResultFactory.DISCARDED(false, false);
            return;
        }
        
        if(SingleTestResultFactory.Command.DISCARD_NEG_POS == command) {
            result = SingleTestResultFactory.DISCARDED(false, true);
            nextResult = SingleTestResultFactory.DISCARDED(false, true);
            return;
        }
        
        if(SingleTestResultFactory.Command.DISCARD_POS_NEG == command) {
            result = SingleTestResultFactory.DISCARDED(true, false);
            nextResult = SingleTestResultFactory.DISCARDED(true, false);
            return;
        }
        
        if(SingleTestResultFactory.Command.DISCARD_POS_POS == command) {
            result = SingleTestResultFactory.DISCARDED(true, true);
            nextResult = SingleTestResultFactory.DISCARDED(true, true);
            return;
        }
        
        if(SingleTestResultFactory.Command.ABORTED == command) {
            result = SingleTestResultFactory.ABORTED(true, true, true);
            nextResult = SingleTestResultFactory.ABORTED(true, true, true);
            return;
        }
        
        throw new RuntimeException("Invalid path");
    }
    
    
    public void willFailIn(int steps) {
        willFailIn(steps, true);
    }
    
    public void willFailIn(int steps, boolean positive) {
        stateChangeIn = steps;
        if(positive) {
            nextResult = SingleTestResultFactory.FAIL_POSITIVE;
        } else {
            nextResult = SingleTestResultFactory.FAIL_NEGATIVE;
        }
    }
    
    public void willSuccessIn(int steps) {
        willSuccessIn(steps, true);
    }
    
    public void willSuccessIn(int steps, boolean positive) {
        stateChangeIn = steps;
        if(positive) {
            nextResult = SingleTestResultFactory.SUCCESS_POSITIVE;
        } else {
            nextResult = SingleTestResultFactory.SUCCESS_NEGATIVE;
        }
    }
    
    public void willDiscardedIn(int steps) {
        willDiscardedIn(steps, true, true);
    }
    
    public void willDiscardedIn(int steps, boolean expected, boolean ok) {
        stateChangeIn = steps;
        nextResult = new SingleTestResult(
                true, expected, ok, "Discarded:" + expected + " " + ok, false
                );
    }
    
    
    public void nextWillFail(boolean positive) {
        willFailIn(1, positive);
    }
    
    public void nextWillSuccess(boolean positive) {
        willSuccessIn(1, positive);
    }
    
    public void nextWillDiscarded(boolean expected, boolean ok) {
        willDiscardedIn(1, expected, ok);
    }
    
    public void willAbortIn(int steps) {
        stateChangeIn = steps;
        nextResult = SingleTestResultFactory.ABORTED(true, true, true);
    }
    
    public void willAbortIn(int steps, boolean x, boolean y, boolean z) {
        stateChangeIn = steps;
        nextResult = SingleTestResultFactory.ABORTED(x, y, z);
    }
    
    // @Override
    public Prop next(RandomAndSize random) {
        if(stateChangeIn < 1) {
            return new Prop((SingleTestResult)result.clone(), new Object[0]);
        }
        
        if(stateChangeIn > 1) {
            stateChangeIn--;
            return new Prop((SingleTestResult)result.clone(), new Object[0]);
        }
        
        result = nextResult;
        return new Prop((SingleTestResult)result.clone(), new Object[0]);
    }
    
}
