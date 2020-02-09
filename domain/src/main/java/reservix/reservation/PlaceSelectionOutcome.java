package reservix.reservation;

import lombok.Value;
import reservix.meetup.PlaceId;

public interface PlaceSelectionOutcome {

    @Value
    class PlaceSelected {

        private final PlaceId placeId;

    }

    @Value
    class PlaceOccupiedError {

        private final PlaceId placeId;

    }

}
