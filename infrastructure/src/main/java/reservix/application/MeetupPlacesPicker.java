package reservix.application;

import lombok.AllArgsConstructor;
import reservix.PlaceId;
import reservix.meetup.MeetupPlace;
import reservix.meetup.MeetupPlaceRepo;
import reservix.meetup.events.*;

import javax.inject.Singleton;
import java.util.UUID;
import java.util.stream.IntStream;

@Singleton
@AllArgsConstructor
public class MeetupPlacesPicker {

    private final MeetupPlaceRepo meetupPlaceRepo;

    public void initMeetupPlaces(final MeetupCreatedEvent event) {

        IntStream.range(0, event.getCreatedMeetup().getAvailablePlaces()).forEach(
                i -> meetupPlaceRepo.save(
                        new MeetupPlace(new PlaceId(UUID.randomUUID()),
                                event.getCreatedMeetup().getId(),
                                event.getCreatedMeetup().getPlaceNumberAssignPolicy().next())
                )

        );
    }

    public void placeSelected(final MeetupPlaceSelectedEvent event) {
        final MeetupPlace meetupPlace = meetupPlaceRepo.get(event.getPlaceId());
        meetupPlace.selectPlace();

        meetupPlaceRepo.save(meetupPlace);
    }

    public void placeUnselected(final MeetupPlaceUnselectedEvent event) {
        final MeetupPlace meetupPlace = meetupPlaceRepo.get(event.getPlaceId());
        meetupPlace.unselectPlace();

        meetupPlaceRepo.save(meetupPlace);
    }

    public void placeReserved(final MeetupPlaceReservedEvent event) {
        final MeetupPlace meetupPlace = meetupPlaceRepo.get(event.getPlaceId());
        meetupPlace.reservePlace();

        meetupPlaceRepo.save(meetupPlace);
    }

}
