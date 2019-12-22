package reservix.meetup;

import reservix.PlaceId;

class Place {

    private PlaceId placeId;
    private PlaceNumber placeNumber;
    private State state = State.FREE;

    enum State {
        FREE,
        RESERVED
    }

    Place(PlaceId placeId, PlaceNumber placeNumber) {
        this.placeId = placeId;
        this.placeNumber = placeNumber;
    }

    PlaceId getPlaceId() {
        return placeId;
    }

    void reserve() {
        this.state = State.RESERVED;
    }

    void makeFree() {
        this.state = State.FREE;
    }

    boolean isFree() {
        return state == State.FREE;
    }


}
