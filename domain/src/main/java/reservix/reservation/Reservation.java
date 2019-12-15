package reservix.reservation;

import io.vavr.collection.List;
import reservix.AggregateRoot;

import java.util.UUID;
import static reservix.reservation.ReservationEvents.*;


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

    private Reservation() {
        this.reservationId = new ReservationId(UUID.randomUUID());
        this.status = Status.NEW;
    }

    public static Reservation createNewReservation() {
        final Reservation reservation = new Reservation();

        reservation.emitEvent(new ReservationCreated(reservation.reservationId));

        return reservation;
    }

    public Reservation pickupPlace(final PlaceId placeId) {
        if(status.isDone()) {
            throw new IllegalStateException("Cannot pickup new place for done reservix.reservation !");
        }
        status = Status.OPEN;
        places.append(placeId);

        emitEvent(new PlacePickedEvent(placeId));

        return this;
    }

    public Reservation unpickupPlace(final PlaceId placeId) {
        if(status.isDone()) {
            throw new IllegalStateException("Cannot un-pickup place for done reservix.reservation !");
        }
        status = Status.OPEN;
        places.remove(placeId);

        emitEvent(new PlaceUnpickedEvent(placeId));

        return this;
    }

    public Reservation accept() {
        if(status != Status.OPEN) {
            throw new IllegalStateException("Allow accept only opened reservix.reservation");
        }

        status = Status.ACCEPTED;

        emitEvent(new ReservationAccepted(reservationId));

        return this;
    }

    public Reservation reject() {
        if(status != Status.OPEN) {
            throw new IllegalStateException("Allow reject only opened reservix.reservation");
        }

        status = Status.REJECTED;

        emitEvent(new ReservationRejected(reservationId));

        return this;
    }


}
