package reservix.reservation;

import lombok.Value;

public abstract class PlaceSelectionOutcome {

    @Value
    public static class PlaceSelected extends PlaceSelectionOutcome {

        private final PlaceId placeId;

    }

    @Value
    public static class PlaceOccupiedError extends PlaceSelectionOutcome {

        private final PlaceId placeId;

    }

}
