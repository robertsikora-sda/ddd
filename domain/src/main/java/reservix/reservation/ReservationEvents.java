package reservix.reservation;

import reservix.Event;
import reservix.MeetupId;
import reservix.PlaceId;

public interface ReservationEvents {

    void emit(ReservationCreatedEvent created);

    void emit(PlacePickedEvent event);

    void emit(PlaceUnpickedEvent event);

    void emit(ReservationAcceptedEvent event);

    void emit(ReservationRejectedEvent event);

    class PlacePickedEvent implements Event {
        private final PlaceId placeId;
        private final MeetupId meetupId;

        public PlacePickedEvent(PlaceId placeId, MeetupId meetupId) {
            this.placeId = placeId;
            this.meetupId = meetupId;
        }

        public PlaceId getPlaceId() {
            return placeId;
        }

        public MeetupId getMeetupId() {
            return meetupId;
        }
    }

    class PlaceUnpickedEvent implements Event {
        private final PlaceId placeId;
        private final MeetupId meetupId;

        public PlaceUnpickedEvent(PlaceId placeId, MeetupId meetupId) {
            this.placeId = placeId;
            this.meetupId = meetupId;
        }

        public PlaceId getPlaceId() {
            return placeId;
        }

        public MeetupId getMeetupId() {
            return meetupId;
        }
    }

    class ReservationCreatedEvent implements Event {
        private final ReservationId reservationId;

        ReservationCreatedEvent(ReservationId reservationId) {
            this.reservationId = reservationId;
        }
    }

    class ReservationAcceptedEvent implements Event {
        private final ReservationId reservationId;

        ReservationAcceptedEvent(ReservationId reservationId) {
            this.reservationId = reservationId;
        }
    }

    class ReservationRejectedEvent implements Event {
        private final ReservationId reservationId;

        ReservationRejectedEvent(ReservationId reservationId) {
            this.reservationId = reservationId;
        }
    }

}
