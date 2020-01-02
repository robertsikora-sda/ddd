package reservix.meetup.events;

import lombok.Value;
import reservix.Event;
import reservix.meetup.MeetupPlace;

@Value
public class MeetupPlaceCreatedEvent implements Event {

    private MeetupPlace createMeetupPlace;

}
