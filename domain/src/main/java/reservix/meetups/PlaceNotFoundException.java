package reservix.meetups;

import reservix.PlaceId;

public class PlaceNotFoundException extends RuntimeException {

    public PlaceNotFoundException(final PlaceId placeId) {
        super(String.format("The place %s is not found", placeId.getId()));
    }

}
