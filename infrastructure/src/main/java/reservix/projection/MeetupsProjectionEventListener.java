package reservix.projection;

import lombok.AllArgsConstructor;
import reservix.meetup.Meetup;

import static reservix.meetup.MeetupEvents.MeetupCreatedEvent;

@AllArgsConstructor
public class MeetupsProjectionEventListener {

    private final MeetupsProjectionRepo projectionRepo;

    public MeetupCreatedEvent updateMeetupsProjection(final MeetupCreatedEvent event) {

        final Meetup createdMeetup = event.getCreatedMeetup();

        projectionRepo.save(

                new MeetupsProjectionDto(
                        createdMeetup.getId().getId().toString(),
                        createdMeetup.getOwnerId().getId().toString(),
                        createdMeetup.getMeetupName().getName(),
                        createdMeetup.getTime().getTime(),
                        createdMeetup.areFreePlaces()
                )
        );

        return event;
    }
}
