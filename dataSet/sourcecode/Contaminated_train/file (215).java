package calculator.stateMachine;

import java.util.Set;

abstract public class AbstractStateMachine<
        State extends Enum,
        Context extends FiniteMachineContext<State, Result>,
        Matrix extends TransitionMatrix<State>,
        Recognizer extends StateRecognizer<State, Result, StateMachineError, Context>,
        StateMachineError extends Exception,
        Result> {

    public Result run(Context context) throws StateMachineError {

        final Matrix matrix = getTransitionMatrix();
        final Recognizer recognizer = getStateRecognizer();

        if (!recognizer.accept(context, matrix.getStartState())) {
            deadlock(context);
        }

        context.setState(matrix.getStartState());

        while (context.getState() != matrix.getFinishState()) {
            if (!moveForward(context)) {
                deadlock(context);
            }
        }

        return context.getResult();
    }

    private boolean moveForward(Context context) throws StateMachineError {
        final Matrix matrix = getTransitionMatrix();
        final Recognizer recognizer = getStateRecognizer();

        final Set<State> possibleTransitions =
                matrix.getPossibleTransitions(context.getState());

        for (State possibleState : possibleTransitions) {
            if (recognizer.accept(context, possibleState)) {
                context.setState(possibleState);
                return true;
            }
        }

        return false;
    }


    abstract protected Matrix getTransitionMatrix();

    abstract protected Recognizer getStateRecognizer();

    abstract protected void deadlock(Context context)
            throws StateMachineError;

}
