package reservix;

import io.vavr.collection.HashSet;
import io.vavr.collection.Map;
import io.vavr.collection.Set;


public class StateMachine<T extends Enum<T>> {

    private final Map<Enum<T> , Set<Enum<T> >> ALLOWED_TRANSITIONS;

    public StateMachine(Map<Enum<T>, Set<Enum<T>>> ALLOWED_TRANSITIONS) {
        this.ALLOWED_TRANSITIONS = ALLOWED_TRANSITIONS;
    }

    public void changeState(Enum<T> currentState, final Set<Enum<T>> allowedStates, final Enum<T> targetState) {
        checkCurrentState(currentState, allowedStates);
        checkTargetState(currentState, targetState);

        currentState = targetState;
    }

    private void checkCurrentState(Enum<T> currentState, final Set<Enum<T>> allowedStatuses) {
        if(!allowedStatuses.contains(currentState)) {
            throw new IllegalStateException("Wrong current state !");
        }
    }

    private void checkTargetState(Enum<T> currentState, final Enum<T> targetState) {
        if(!ALLOWED_TRANSITIONS.getOrElse(targetState, HashSet.empty()).contains(targetState)) {
            throw new IllegalStateException(String.format("Cannot transit a state from %s to %s", currentState, targetState));
        }
    }
}
