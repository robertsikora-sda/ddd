package reservix.meetup.events;

import lombok.Value;
import reservix.Event;
import reservix.PlaceId;

@Value
public class MeetupPlaceSelectedEvent implements Event {

    private PlaceId placeId;

}
