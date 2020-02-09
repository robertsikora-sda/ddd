package reservix.reservation;

import lombok.EqualsAndHashCode;
import lombok.Value;
import reservix.meetup.PlaceId;

public class PlaceAvailabilityValidator {

    PlaceOccupancy check(final PlaceId placeId) {
        return new PlaceFree(placeId);
    }

}

abstract class PlaceOccupancy {
}

@EqualsAndHashCode(callSuper = true)
@Value
class PlaceFree extends PlaceOccupancy {

    private PlaceId placeId;

}

@EqualsAndHashCode(callSuper = true)
@Value
class PlaceOccupied extends PlaceOccupancy {

    private PlaceId placeId;

}


