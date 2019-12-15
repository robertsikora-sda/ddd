package reservix.reservation;

import reservix.Event;

class ReservationEvents {

    static class PlacePickedEvent implements Event {
        private final PlaceId placeId;

        PlacePickedEvent(PlaceId placeId) {
            this.placeId = placeId;
        }
    }

    static class PlaceUnpickedEvent implements Event {
        private final PlaceId placeId;

        PlaceUnpickedEvent(PlaceId placeId) {
            this.placeId = placeId;
        }
    }

    static class ReservationCreated implements Event {
        private final ReservationId reservationId;

        ReservationCreated(ReservationId reservationId) {
            this.reservationId = reservationId;
        }
    }

    static class ReservationAccepted implements Event {
        private final ReservationId reservationId;

        ReservationAccepted(ReservationId reservationId) {
            this.reservationId = reservationId;
        }
    }

    static class ReservationRejected implements Event {
        private final ReservationId reservationId;

        ReservationRejected(ReservationId reservationId) {
            this.reservationId = reservationId;
        }
    }

}
