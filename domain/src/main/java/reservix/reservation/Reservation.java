package reservix.reservation;

import io.vavr.collection.List;
import io.vavr.control.Either;
import reservix.AggregateRoot;
import reservix.MeetupId;
import reservix.PlaceId;
import reservix.meetup.FreePlaceDto;
import reservix.meetup.FreePlaceFinder;

import java.util.UUID;

import static reservix.reservation.ReservationEvents.*;

class Reservation extends AggregateRoot {

    private final MeetupId meetupId;
    private final ReservationId reservationId;
    private Status status;
    private final List<PlaceId> places = List.empty();

    enum Status {
        NEW,
        OPEN,
        ACCEPTED,
        REJECTED;

        boolean isDone() {
            return this == ACCEPTED || this == REJECTED;
        }
    }

    private Reservation(final MeetupId meetupId) {
        this.reservationId = new ReservationId(UUID.randomUUID());
        this.status = Status.NEW;
        this.meetupId = meetupId;
    }

    static Reservation createNewReservation(final MeetupId meetupId) {
        final Reservation reservation = new Reservation(meetupId);

        reservation.emitEvent(new ReservationCreatedEvent(reservation.reservationId));

        return reservation;
    }

    Either<?, Reservation> pickupPlace(final PlaceId placeId, final FreePlaceFinder freePlaceFinder) {
        if(status.isDone()) {
            throw new IllegalStateException("Cannot pickup new place for done reservation !");
        }
        status = Status.OPEN;

        if(isPlaceAlreadyOccupied(placeId, freePlaceFinder)) {
            return Either.left("Place is already occupied");
        }

        places.append(placeId);

        emitEvent(new PlacePickedEvent(placeId, meetupId));

        return Either.right(this);
    }

    private boolean isPlaceAlreadyOccupied(PlaceId placeId, FreePlaceFinder freePlaceFinder) {
        return freePlaceFinder.getAllFreePlaces(meetupId).contains(new FreePlaceDto(placeId));
    }

    Reservation unpickupPlace(final PlaceId placeId) {
        if(status.isDone()) {
            throw new IllegalStateException("Cannot un-pickup place for done reservation !");
        }
        status = Status.OPEN;
        places.remove(placeId);

        emitEvent(new PlaceUnpickedEvent(placeId, meetupId));

        return this;
    }

    Reservation accept() {
        if(status != Status.OPEN) {
            throw new IllegalStateException("Allow accept only opened reservation");
        }

        status = Status.ACCEPTED;

        emitEvent(new ReservationAcceptedEvent(reservationId, places, meetupId));

        return this;
    }

    Reservation reject() {
        if(status != Status.OPEN) {
            throw new IllegalStateException("Allow reject only opened reservation");
        }

        places.forEach(
            t -> {
                    emitEvent(new PlaceUnpickedEvent(t, meetupId));
                    places.remove(t);
            }
        );

        status = Status.REJECTED;

        emitEvent(new ReservationRejectedEvent(reservationId));

        return this;
    }

}
