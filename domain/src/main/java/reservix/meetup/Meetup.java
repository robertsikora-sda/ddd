package reservix.meetup;

import lombok.Getter;
import lombok.Value;
import reservix.AggregateRoot;
import reservix.MeetupId;
import reservix.PlaceId;
import reservix.meetup.events.*;
import reservix.user.UserId;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
public class Meetup extends AggregateRoot {

    private MeetupId id;
    private UserId ownerId;
    private MeetupName name;
    private MeetupTime time;
    private int availablePlaces;
    private PlaceNumberAssignPolicy placeNumberAssignPolicy = new NumericPlaceNumberPolicy();
    private final Reservations reservations = new Reservations();

    public Meetup(UserId ownerId, String name, LocalDateTime time, int availablePlaces) {
        this.id = new MeetupId(UUID.randomUUID());
        this.ownerId = ownerId;
        this.name = new MeetupName(name);
        this.time = new MeetupTime(time);
        this.availablePlaces = availablePlaces;

        this.emitEvent(new MeetupCreatedEvent(new MeetupProjection(this)));
    }

    public void selectNewPlaces(final UserId userId, final Collection<PlaceId> placeIds) {
        placeIds.forEach(t -> {
            reservations.getUserReservation(userId).selectPlace(t);

            emitEvent(new MeetupPlaceSelectedEvent(t));
        });
    }

    public void unselectPlaces(final UserId userId, final Collection<PlaceId> placeIds) {
        placeIds.forEach(t -> {
            reservations.getUserReservation(userId).unselectPlace(t);

            emitEvent(new MeetupPlaceUnselectedEvent(t));
        });
    }

    public Reservation acceptReservation(final UserId userId) {
        final Reservation reservation = reservations.getUserReservation(userId).accept();

        emitEvent(new ReservationAcceptedEvent(new MeetupProjection(this), List.copyOf(reservation.getPlaces())));

        reservation.getPlaces().forEach(t -> emitEvent(new MeetupPlaceReservedEvent(t)));

        return reservation;
    }

    public Reservation rejectReservation(final UserId userId) {
        final Reservation reservation = reservations.getUserReservation(userId).reject();

        emitEvent(new ReservationRejectedEvent());

        reservation.getPlaces().forEach(t -> emitEvent(new MeetupPlaceUnselectedEvent(t)));

        return reservation;
    }

    public boolean areFreePlaces() {
        return false;
    }

    @Value
    static class MeetupName {

        private String name;

        MeetupName(final String name) {
            if(name.length() > 20) {
                throw new IllegalArgumentException("");
            }
            this.name = name;
        }
    }

    @Value
    static class MeetupTime {

        private LocalDateTime time;

        MeetupTime(final LocalDateTime time) {
            if(time.isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("");
            }
            this.time = time;
        }
    }
}



