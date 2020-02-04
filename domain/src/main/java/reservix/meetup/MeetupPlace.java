package reservix.meetup;

import lombok.Getter;
import reservix.AggregateRoot;
import reservix.reservation.PlaceId;
import reservix.meetup.events.MeetupPlaceCreatedEvent;

@Getter
public class MeetupPlace extends AggregateRoot {

    private PlaceId id;
    private MeetupId meetupId;
    private PlaceNumber placeNumber;
    private State state = State.FREE;

    public enum State {
        FREE,
        SELECTED,
        RESERVED
    }

    public MeetupPlace(PlaceId placeId, MeetupId meetupId, PlaceNumber placeNumber) {
        this.id = placeId;
        this.meetupId = meetupId;
        this.placeNumber = placeNumber;

        emitEvent(new MeetupPlaceCreatedEvent(this));
    }

    public void selectPlace() {
        if(state != State.FREE) {
            throw new IllegalStateException("Only free place can be selected!");
        }
        this.state = State.SELECTED;
    }

    public void unselectPlace() {
        this.state = State.FREE;
    }

    public void reservePlace() {
        if(state != State.SELECTED) {
            throw new IllegalStateException("Can reserve only place selected before");
        }
        this.state = State.RESERVED;
    }

}
