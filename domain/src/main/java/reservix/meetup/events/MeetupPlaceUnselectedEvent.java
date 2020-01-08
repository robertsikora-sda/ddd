package reservix.meetup.events;

import lombok.Value;
import reservix.Event;
import reservix.MeetupId;
import reservix.PlaceId;

@Value
public class MeetupPlaceUnselectedEvent implements Event {

    private MeetupId meetupId;
    private PlaceId placeId;

}
