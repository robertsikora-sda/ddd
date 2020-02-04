package reservix.projection;

import lombok.AllArgsConstructor;
import reservix.meetup.events.MeetupCreatedEvent;

import javax.inject.Singleton;

@Singleton
@AllArgsConstructor
public class MeetupsProjectionEventListener {

    private final MeetupsProjectionInMemoryRepo projectionRepo;

    public MeetupCreatedEvent updateMeetupsProjection(final MeetupCreatedEvent event) {

        projectionRepo.save(

                new MeetupsProjectionDto(
                        String.valueOf(event.getMeetupId().getId()),
                        String.valueOf(event.getOwner().getId()),
                        event.getName().getValue(),
                        event.getTime().getValue(),
                        true
                )
        );

        return event;
    }
}
