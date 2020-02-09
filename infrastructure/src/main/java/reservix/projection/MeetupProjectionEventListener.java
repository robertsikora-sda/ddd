package reservix.projection;

import lombok.AllArgsConstructor;
import reservix.meetup.events.MeetupCreatedEvent;

import javax.inject.Singleton;

@Singleton
@AllArgsConstructor
public class MeetupProjectionEventListener {

    private final MeetupProjectionInMemoryRepo projectionRepo;

    public MeetupProjection onMeetupCreatedEvent(final MeetupCreatedEvent event) {

        return projectionRepo.save(

                new MeetupProjection(
                        String.valueOf(event.getMeetupId().getId()),
                        String.valueOf(event.getOwner().getId()),
                        event.getMeetupName(),
                        event.getMeetupTime(),
                        true
                )

        );
    }
}
