package reservix.reservation;

import io.vavr.collection.Seq;
import lombok.Value;
import reservix.Event;
import reservix.MeetupId;
import reservix.PlaceId;

public interface ReservationEvents {

    void emit(ReservationCreatedEvent created);

    void emit(PlacePickedEvent event);

    void emit(PlaceUnpickedEvent event);

    void emit(ReservationAcceptedEvent event);

    void emit(ReservationRejectedEvent event);

    @Value
    class PlacePickedEvent implements Event {
        private final PlaceId placeId;
        private final MeetupId meetupId;
    }

    @Value
    class PlaceUnpickedEvent implements Event {
        private final PlaceId placeId;
        private final MeetupId meetupId;
    }

    @Value
    class ReservationCreatedEvent implements Event {
        private final ReservationId reservationId;
    }

    @Value
    class ReservationAcceptedEvent implements Event {
        private final ReservationId reservationId;
        private final Seq<PlaceId> places;
        private final MeetupId meetupId;
    }

    @Value
    class ReservationRejectedEvent implements Event {
        private final ReservationId reservationId;
    }

}
