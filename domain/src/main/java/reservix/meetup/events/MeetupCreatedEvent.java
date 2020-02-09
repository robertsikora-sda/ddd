package reservix.meetup.events;

import io.vavr.collection.Set;
import lombok.Value;
import reservix.Event;
import reservix.meetup.MeetupId;
import reservix.meetup.PlaceId;
import reservix.user.UserId;

import java.time.LocalDateTime;

@Value
public class MeetupCreatedEvent implements Event {

    private MeetupId meetupId;

    private String meetupName;

    private LocalDateTime meetupTime;

    private UserId owner;

    private Set<PlaceId> places;

}
