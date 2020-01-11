package reservix.projection;

import lombok.AllArgsConstructor;
import reservix.meetup.MeetupPlace;
import reservix.meetup.events.MeetupPlaceCreatedEvent;
import reservix.meetup.events.MeetupPlaceReservedEvent;
import reservix.meetup.events.MeetupPlaceSelectedEvent;
import reservix.meetup.events.MeetupPlaceUnselectedEvent;

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

    public MeetupPlaceSelectedEvent selectPlace(final MeetupPlaceSelectedEvent event) {

        final MeetupPlacesProjectionDto meetupPlace = projectionRepo.get(event.getMeetupId(), event.getPlaceId());
        meetupPlace.setStatus(SELECTED);

        projectionRepo.save(meetupPlace);

        return event;
    }

    public MeetupPlaceUnselectedEvent unselectPlace(final MeetupPlaceUnselectedEvent event) {

        final MeetupPlacesProjectionDto meetupPlace = projectionRepo.get(event.getMeetupId(), event.getPlaceId());
        meetupPlace.setStatus(FREE);

        projectionRepo.save(meetupPlace);

        return event;
    }

    public MeetupPlaceReservedEvent reservePlace(final MeetupPlaceReservedEvent event) {

        final MeetupPlacesProjectionDto meetupPlace = projectionRepo.get(event.getMeetupId(), event.getPlaceId());
        meetupPlace.setStatus(RESERVED);

        projectionRepo.save(meetupPlace);

        return event;
    }
}
