package reservix.projection;

import lombok.AllArgsConstructor;
import reservix.meetup.MeetupPlace;
import reservix.meetup.events.MeetupPlaceCreatedEvent;
import reservix.reservation.events.PlaceReservedEvent;
import reservix.reservation.events.PlaceSelectedEvent;
import reservix.reservation.events.PlaceUnselectedEvent;

import javax.inject.Singleton;

import static reservix.projection.MeetupPlacesProjectionDto.Status.*;

@Singleton
@AllArgsConstructor
public class MeetupsPlacesProjectionEventListener {

    private final MeetupsPlacesProjectionInMemoryRepo projectionRepo;

    public MeetupPlaceCreatedEvent initMeetupsPlacesProjection(final MeetupPlaceCreatedEvent event) {

        final MeetupPlace meetupPlace = event.getCreateMeetupPlace();

        projectionRepo.save(

                new MeetupPlacesProjectionDto(
                        meetupPlace.getId().getId().toString(),
                        meetupPlace.getMeetupId().getId().toString(),
                        meetupPlace.getPlaceNumber().getNumber(),
                        FREE
                        )
        );

        return event;
    }

    public PlaceSelectedEvent selectPlace(final PlaceSelectedEvent event) {

        final MeetupPlacesProjectionDto meetupPlace = projectionRepo.get(event.getMeetupId(), event.getPlaceId());
        meetupPlace.setStatus(SELECTED);

        projectionRepo.save(meetupPlace);

        return event;
    }

    public PlaceUnselectedEvent unselectPlace(final PlaceUnselectedEvent event) {

        final MeetupPlacesProjectionDto meetupPlace = projectionRepo.get(event.getMeetupId(), event.getPlaceId());
        meetupPlace.setStatus(FREE);

        projectionRepo.save(meetupPlace);

        return event;
    }

    public PlaceReservedEvent reservePlace(final PlaceReservedEvent event) {

        final MeetupPlacesProjectionDto meetupPlace = projectionRepo.get(event.getMeetupId(), event.getPlaceId());
        meetupPlace.setStatus(RESERVED);

        projectionRepo.save(meetupPlace);

        return event;
    }
}
