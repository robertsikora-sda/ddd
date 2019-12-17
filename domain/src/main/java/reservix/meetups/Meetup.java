package reservix.meetups;

import reservix.AggregateRoot;
import reservix.PlaceId;

class Meetup extends AggregateRoot {

    private Name name;
    private Time time;
    private AvailablePlaces availablePlaces;
    private Places places;
    //private User owner;

    private Meetup(Name name, Time time, Places places) {
        this.name = name;
        this.time = time;
        this.places = places;
    }

    static Meetup createNewMeetup(final Name name,
                                  final Time time,
                                  final AvailablePlaces availablePlaces) {
        return new Meetup(name, time, new Places(availablePlaces, new NumericPlaceNumberPolicy()));
    }

    Meetup reserveNewPlace(final PlaceId placeId) {
        places.findBy(placeId).reserve();
        return this;
    }

    Meetup freePlace(final PlaceId placeId) {
        places.findBy(placeId).makeFree();
        return this;
    }

    Places freePlaces() {
        return places.allFree();
    }
}
