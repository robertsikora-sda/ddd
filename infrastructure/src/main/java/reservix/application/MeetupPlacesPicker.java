package reservix.application;

import lombok.AllArgsConstructor;
import reservix.reservation.PlaceId;
import reservix.meetup.MeetupPlace;
import reservix.meetup.MeetupPlaceRepository;
import reservix.meetup.events.*;
import reservix.reservation.events.PlaceReservedEvent;
import reservix.reservation.events.PlaceSelectedEvent;
import reservix.reservation.events.PlaceUnselectedEvent;

import javax.inject.Singleton;
import java.util.UUID;
import java.util.stream.IntStream;

@Singleton
@AllArgsConstructor
public class MeetupPlacesPicker {

    private final MeetupPlaceRepository meetupPlaceRepository;

    public void initMeetupPlaces(final MeetupCreatedEvent event) {

        IntStream.range(0, event.getPlaces().getValue()).forEach(
                i -> meetupPlaceRepository.save(
                        new MeetupPlace(new PlaceId(UUID.randomUUID()),
                                event.getMeetupId(),
                                event.getPlaceNumberPolicy().generate())
                )

        );
    }

    public void placeSelected(final PlaceSelectedEvent event) {
        final MeetupPlace meetupPlace = meetupPlaceRepository.get(event.getPlaceId());
        meetupPlace.selectPlace();

        meetupPlaceRepository.save(meetupPlace);
    }

    public void placeUnselected(final PlaceUnselectedEvent event) {
        final MeetupPlace meetupPlace = meetupPlaceRepository.get(event.getPlaceId());
        meetupPlace.unselectPlace();

        meetupPlaceRepository.save(meetupPlace);
    }

    public void placeReserved(final PlaceReservedEvent event) {
        final MeetupPlace meetupPlace = meetupPlaceRepository.get(event.getPlaceId());
        meetupPlace.reservePlace();

        meetupPlaceRepository.save(meetupPlace);
    }

}
