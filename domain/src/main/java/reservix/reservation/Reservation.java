package reservix.reservation;

import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import lombok.Getter;
import reservix.AggregateRoot;
import reservix.meetup.MeetupId;
import reservix.reservation.events.PlaceReservedEvent;
import reservix.reservation.events.PlaceSelectedEvent;
import reservix.reservation.events.PlaceUnselectedEvent;
import reservix.user.UserId;

import java.time.LocalDateTime;
import java.util.Collections;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;
import static reservix.reservation.Reservation.State.*;

import static reservix.reservation.PlaceSelectionOutcome.PlaceSelected;
import static reservix.reservation.PlaceSelectionOutcome.PlaceOccupiedError;

public class Reservation extends AggregateRoot {

    enum State {
        NEW,
        OPEN,
        ACCEPTED,
        REJECTED
    }

    private static final Map<State, Set<State>> ALLOWED_TRANSITIONS = HashMap.of(

            NEW, HashSet.of(OPEN),
            OPEN, HashSet.of(ACCEPTED, REJECTED),
            ACCEPTED, HashSet.of(REJECTED)

    );

    @Getter private ReservationId id;
    @Getter private UserId ownerId;
    private MeetupId meetupId;
    private State state;
    private LocalDateTime startDate;
    private LocalDateTime finishDate;

    private final java.util.Set<PlaceId> places = Collections.emptySet();

    public Reservation(final MeetupId meetupId, final UserId ownerId) {
        this.id = ReservationId.newId();
        this.meetupId = meetupId;
        this.ownerId = ownerId;
        this.state = NEW;
        this.startDate = LocalDateTime.now();
    }

    public PlaceSelectionOutcome selectPlace(final PlaceId placeId, final PlaceAvailabilityValidator placeAvailabilityValidator) {
        changeState(HashSet.of(NEW, OPEN), OPEN);

        return Match(placeAvailabilityValidator.check(placeId)).of(

                Case($(instanceOf(PlaceFree.class)), t -> {

                    this.places.add(t.getPlaceId());

                    emitEvent(new PlaceSelectedEvent(meetupId, placeId));

                    return new PlaceSelected(t.getPlaceId());

                }),
                Case($(instanceOf(PlaceOccupied.class)), t -> new PlaceOccupiedError(t.getPlaceId())));

    }

    public PlaceUnselected unselectPlace(final PlaceId placeId) {
        changeState(HashSet.of(OPEN), OPEN);

        this.places.remove(placeId);

        emitEvent(new PlaceUnselectedEvent(meetupId, placeId));

        return new PlaceUnselected(placeId);
    }

    public ReservationAccepted acceptReservation() {
        changeState(HashSet.of(OPEN), ACCEPTED);

        finishDate = LocalDateTime.now();

        places.forEach(place -> emitEvent(new PlaceReservedEvent(meetupId, place)));

        return new ReservationAccepted(id, finishDate);
    }

    public ReservationRejected rejectReservation() {
        changeState(HashSet.of(OPEN, ACCEPTED), REJECTED);

        finishDate = LocalDateTime.now();

        places.forEach(place -> emitEvent(new PlaceUnselectedEvent(meetupId, place)));

        return new ReservationRejected(id, finishDate);
    }

    private void changeState(final Set<State> allowedStates, final State targetState) {
        checkCurrentState(allowedStates);
        checkTargetState(targetState);

        this.state = targetState;
    }

    private void checkCurrentState(final Set<State> allowedStatuses) {
        if(!allowedStatuses.contains(state)) {
            throw new IllegalStateException("Reservation has wrong state !");
        }
    }

    private void checkTargetState(final State targetState) {
        if(!ALLOWED_TRANSITIONS.getOrElse(targetState, HashSet.empty()).contains(targetState)) {
            throw new IllegalStateException(String.format("Cannot transit reservation state from %s to %s", state, targetState));
        }
    }

}
