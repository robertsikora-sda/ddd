package reservix.meetup;

import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import lombok.Getter;
import reservix.StateMachine;

import static reservix.meetup.MeetupPlace.PlaceState.FREE;
import static reservix.meetup.MeetupPlace.PlaceState.RESERVED;
import static reservix.meetup.MeetupPlace.PlaceState.SELECTED;


@Getter
public class MeetupPlace {

    public enum PlaceState {
        FREE,
        SELECTED,
        RESERVED
    }

    private final StateMachine<PlaceState> stateMachine = new StateMachine<>(

            HashMap.of(

                    FREE,     HashSet.of(SELECTED),
                    SELECTED, HashSet.of(FREE, RESERVED),
                    RESERVED, HashSet.of(FREE)

            )
    );

    private PlaceId id;
    private PlaceState placeState = FREE;

    public MeetupPlace(final PlaceId placeId) {
        this.id = placeId;
    }

    public MeetupPlace selectPlace() {
        stateMachine.changeState(placeState, HashSet.of(FREE), SELECTED);
        return this;
    }

    public MeetupPlace unselectPlace() {
        stateMachine.changeState(placeState, HashSet.of(SELECTED, RESERVED), FREE);
        return this;
    }

    public MeetupPlace reservePlace() {
        stateMachine.changeState(placeState, HashSet.of(SELECTED), RESERVED);
        return this;
    }

}
