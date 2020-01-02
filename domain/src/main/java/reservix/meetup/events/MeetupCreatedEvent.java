package reservix.meetup.events;

import lombok.Value;
import reservix.Event;
import reservix.meetup.MeetupProjection;

@Value
public class MeetupCreatedEvent implements Event {

    private MeetupProjection createdMeetup;

}
