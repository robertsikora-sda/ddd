package reservix.reservation.events;

import lombok.Value;
import reservix.Event;
import reservix.meetup.MeetupId;
import reservix.meetup.PlaceId;

@Value
public class PlaceUnselectedEvent implements Event {

    private MeetupId meetupId;

    private PlaceId placeId;

}
