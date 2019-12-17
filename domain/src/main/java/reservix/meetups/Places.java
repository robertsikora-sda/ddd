package reservix.meetups;

import io.vavr.collection.List;
import reservix.PlaceId;

class Places {

    private List<Place> places;

    Places(List<Place> places) {
        this.places = places;
    }

    Places(final AvailablePlaces availablePlaces, final PlaceNumberAssignPolicy placeNumberAssignPolicy) {
        this(List.fill(availablePlaces.getNumber(), () -> new Place(PlaceId.generate(),
                placeNumberAssignPolicy.next())));
    }

    Place findBy(final PlaceId placeId) {
        return places.filter(t -> placeId.equals(t.getPlaceId())).getOrElseThrow(
                () -> new PlaceNotFoundException(placeId)
        );
    }

    Places allFree() {
        return new Places(places.filter(Place::isFree));
    }

    public List<Place> getPlaces() {
        return places;
    }
}
