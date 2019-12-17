package reservix.meetups;

import io.vavr.collection.Seq;
import reservix.MeetupId;

public class FreePlaceFinder {

    private MeetupRepo meetupRepo;

    public Seq<FreePlaceDto> getAllFreePlaces(final MeetupId meetupId) {
        final Meetup meetup = meetupRepo.get(meetupId);
        return meetup.freePlaces().getPlaces().map(
                t -> new FreePlaceDto(t.getPlaceId())
        );
    }

}
