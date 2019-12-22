package reservix.meetup;

import lombok.Value;
import reservix.Event;

public class MeetupEvents {

    @Value
    public static class MeetupCreatedEvent implements Event {

        private final Meetup createdMeetup;

    }

}
