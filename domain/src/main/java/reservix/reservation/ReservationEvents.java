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


}
