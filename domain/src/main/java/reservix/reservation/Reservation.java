package reservix.reservation;

import io.vavr.collection.List;
import reservix.AggregateRoot;

public class Reservation extends AggregateRoot {

    private ReservationId reservationId;
    private Status status;
    private List<PlaceId> places = List.empty();

    enum Status {
        NEW,
        OPEN,
        ACCEPTED,
        REJECTED;

        boolean isDone() {
            return this == ACCEPTED || this == REJECTED;
        }
    }

    private Reservation(final Status status) {
        this.status = status;
    }

    public static Reservation createNewReservation() {
        return new Reservation(Status.NEW);
    }

    public Reservation pickupPlace(final PlaceId placeId) {
        if(status.isDone()) {
            throw new IllegalStateException("Cannot pickup new place for done reservix.reservation !");
        }
        status = Status.OPEN;
        places.append(placeId);

        emitEvent(new ReservationEvents.PlacePickedEvent(placeId));

        return this;
    }

    public Reservation unpickupPlace(final PlaceId placeId) {
        if(status.isDone()) {
            throw new IllegalStateException("Cannot un-pickup place for done reservix.reservation !");
        }
        status = Status.OPEN;
        places.remove(placeId);

        emitEvent(new ReservationEvents.PlaceUnpickedEvent(placeId));

        return this;
    }

    public Reservation accept() {
        if(status != Status.OPEN) {
            throw new IllegalStateException("Allow accept only opened reservix.reservation");
        }

        status = Status.ACCEPTED;

        return this;
    }

    public Reservation reject() {
        if(status != Status.OPEN) {
            throw new IllegalStateException("Allow reject only opened reservix.reservation");
        }

        status = Status.REJECTED;

        return this;
    }


}
