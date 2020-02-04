package reservix.reservation;

import lombok.Value;

public class PlaceAvailabilityValidator {

    PlaceOccupancy check(final PlaceId placeId) {
        return new PlaceFree(placeId);
    }

}

abstract class PlaceOccupancy {
}

@Value
class PlaceFree extends PlaceOccupancy {

    private PlaceId placeId;

}

@Value
class PlaceOccupied extends PlaceOccupancy {

    private PlaceId placeId;

}


