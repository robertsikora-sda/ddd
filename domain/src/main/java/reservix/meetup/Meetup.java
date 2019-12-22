package reservix.meetup;

import lombok.Getter;
import reservix.AggregateRoot;
import reservix.MeetupId;
import reservix.PlaceId;
import reservix.user.UserId;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Meetup extends AggregateRoot {

    private MeetupId id;
    private UserId ownerId;
    private MeetupName meetupName;
    private MeetupTime time;
    private Places places;

    private Meetup(MeetupId id, UserId ownerId, MeetupName meetupName, MeetupTime time, Places places) {
        this.id = id;
        this.ownerId = ownerId;
        this.meetupName = meetupName;
        this.time = time;
        this.places = places;
    }

    static Meetup createNewMeetup(final MeetupName meetupName,
                                  final MeetupTime time,
                                  final int availablePlaces,
                                  final UserId ownerId) {
        return new Meetup(new MeetupId(UUID.randomUUID()), ownerId, meetupName, time, new Places(availablePlaces, new NumericPlaceNumberPolicy()));
    }

    Meetup reserveNewPlace(final PlaceId placeId) {
        places.findBy(placeId).reserve();
        return this;
    }

    Meetup makePlaceFree(final PlaceId placeId) {
        places.findBy(placeId).makeFree();
        return this;
    }

    Places getFreePlaces() {
        return places.allFree();
    }

    public boolean areFreePlaces() {
        return getFreePlaces().getPlaces().size() > 0;
    }

    public static class MeetupName {

        @Getter
        private String name;

        MeetupName(final String name) {
            if(name.length() > 20) {
                throw new IllegalArgumentException("");
            }
            this.name = name;
        }
    }

    public static class MeetupTime {

        @Getter
        private LocalDateTime time;

        MeetupTime(final LocalDateTime time) {
            if(time.isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("");
            }
            this.time = time;
        }
    }
}



