package reservix.meetups;

import io.vavr.collection.Seq;
import lombok.AllArgsConstructor;
import reservix.MeetupId;

@AllArgsConstructor
public class FreePlaceFinder {

    private final MeetupRepo meetupRepo;

    public Seq<FreePlaceDto> getAllFreePlaces(final MeetupId meetupId) {
        final Meetup meetup = meetupRepo.get(meetupId);
        return meetup.freePlaces().getPlaces().map(
                t -> new FreePlaceDto(t.getPlaceId())
        );
    }

}
