package reservix.meetup;

import lombok.Getter;
import reservix.PlaceId;
import reservix.user.UserId;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
class Reservation {

    private Status status;
    private final UserId ownerId;
    private final LocalDateTime startDate;
    private LocalDateTime finishDate;
    private final Set<PlaceId> places = new HashSet<>();

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
        this.startDate = LocalDateTime.now();
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
        finishDate = LocalDateTime.now();

        return this;
    }

    Reservation reject() {
        if(status != Status.OPEN) {
            throw new IllegalStateException("Allow reject only opened reservation");
        }

        status = Status.REJECTED;
        finishDate = LocalDateTime.now();

        return this;
    }

}
