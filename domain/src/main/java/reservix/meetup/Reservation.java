package reservix.meetup;

import lombok.Getter;
import reservix.PlaceId;
import reservix.user.UserId;

import java.util.ArrayList;
import java.util.List;

@Getter
class Reservation {

    private Status status;
    private final UserId ownerId;
    private final List<PlaceId> places = new ArrayList<>();

    enum Status {
        NEW,
        OPEN,
        ACCEPTED,
        REJECTED;

        boolean isDone() {
            return this == ACCEPTED || this == REJECTED;
        }
    }

    public Reservation(final UserId ownerId) {
        this.ownerId = ownerId;
        this.status = Status.NEW;
    }

    void selectPlace(final PlaceId placeId) {
        this.places.add(placeId);
        this.status = Status.OPEN;
    }

    void unselectPlace(final PlaceId placeId) {
        this.places.remove(placeId);
    }

    Reservation accept() {
        if(status != Status.OPEN) {
            throw new IllegalStateException("Allow accept only opened reservation");
        }

        status = Status.ACCEPTED;

        return this;
    }

    Reservation reject() {
        if(status != Status.OPEN) {
            throw new IllegalStateException("Allow reject only opened reservation");
        }

        status = Status.REJECTED;

        return this;
    }

}
