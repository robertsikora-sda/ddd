package reservix.reservation;

import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.control.Either;
import lombok.Getter;
import reservix.AggregateRoot;
import reservix.StateMachine;
import reservix.meetup.MeetupId;
import reservix.meetup.PlaceId;
import reservix.reservation.events.PlaceSelectedEvent;
import reservix.reservation.events.PlaceUnselectedEvent;
import reservix.reservation.events.ReservationAcceptedEvent;
import reservix.reservation.events.ReservationRejectedEvent;
import reservix.user.UserId;

import java.time.LocalDateTime;
import java.util.Collections;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static reservix.reservation.Reservation.ReservationState.*;

import static reservix.reservation.PlaceSelectionOutcome.PlaceSelected;
import static reservix.reservation.PlaceSelectionOutcome.PlaceOccupiedError;


public class Reservation extends AggregateRoot {

    enum ReservationState {
        NEW,
        OPEN,
        ACCEPTED,
        REJECTED
    }

    private final StateMachine<ReservationState> stateMachine = new StateMachine<>(

            HashMap.of(

                    NEW, HashSet.of(OPEN),
                    OPEN, HashSet.of(ACCEPTED, REJECTED),
                    ACCEPTED, HashSet.of(REJECTED)

            )
    );

    @Getter private ReservationId id;
    @Getter private UserId ownerId;
    private MeetupId meetupId;
    private ReservationState state;
    private LocalDateTime startDate;
    private LocalDateTime finishDate;

    private final java.util.Set<PlaceId> places = Collections.emptySet();

    Set getPlaces() {
        return HashSet.ofAll(places);
    }

    private Reservation(final MeetupId meetupId, final UserId ownerId) {
        this.id = ReservationId.id();
        this.meetupId = meetupId;
        this.ownerId = ownerId;
        this.state = NEW;
        this.startDate = LocalDateTime.now();
    }

    public static Reservation of(final MeetupId meetupId, final UserId ownerId) {
        return new Reservation(meetupId, ownerId);
    }

    public Either<PlaceOccupiedError, PlaceSelected> selectPlace(final PlaceId placeId, final PlaceAvailabilityValidator placeAvailabilityValidator) {
        stateMachine.changeState(state, HashSet.of(NEW, OPEN), OPEN);

        return Match(placeAvailabilityValidator.check(placeId)).of(

                Case($(instanceOf(PlaceFree.class)), t -> {

                    this.places.add(t.getPlaceId());

                    emitEvent(new PlaceSelectedEvent(meetupId, placeId));

                    return Either.right(new PlaceSelected(t.getPlaceId()));

                }),

                Case($(instanceOf(PlaceOccupied.class)), t -> Either.left(new PlaceOccupiedError(t.getPlaceId()))));

    }

    public PlaceUnselected unselectPlace(final PlaceId placeId) {
        stateMachine.changeState(state, HashSet.of(OPEN), OPEN);

        this.places.remove(placeId);

        emitEvent(new PlaceUnselectedEvent(meetupId, placeId));

        return new PlaceUnselected(placeId);
    }

    public ReservationAccepted acceptReservation() {
        stateMachine.changeState(state, HashSet.of(OPEN), ACCEPTED);

        finishDate = LocalDateTime.now();

        emitEvent(new ReservationAcceptedEvent(meetupId, getPlaces()));

        return new ReservationAccepted(id, finishDate);
    }

    public ReservationRejected rejectReservation() {
        stateMachine.changeState(state, HashSet.of(OPEN, ACCEPTED), REJECTED);

        finishDate = LocalDateTime.now();

        emitEvent(new ReservationRejectedEvent(meetupId, getPlaces()));

        return new ReservationRejected(id, finishDate);
    }

}
