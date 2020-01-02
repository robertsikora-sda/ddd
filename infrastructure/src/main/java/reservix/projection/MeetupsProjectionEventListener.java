package reservix.projection;

import lombok.AllArgsConstructor;
import reservix.meetup.MeetupProjection;
import reservix.meetup.events.MeetupCreatedEvent;

import javax.inject.Singleton;

@Singleton
@AllArgsConstructor
public class MeetupsProjectionEventListener {

    private final MeetupsProjectionInMemoryRepo projectionRepo;

    public MeetupCreatedEvent updateMeetupsProjection(final MeetupCreatedEvent event) {

        final MeetupProjection createdMeetup = event.getCreatedMeetup();

        projectionRepo.save(

                new MeetupsProjectionDto(
                        createdMeetup.getId().getId().toString(),
                        createdMeetup.getOwnerId().getId().toString(),
                        createdMeetup.getName(),
                        createdMeetup.getTime(),
                        createdMeetup.isFreePlaces()
                )
        );

        return event;
    }
}
