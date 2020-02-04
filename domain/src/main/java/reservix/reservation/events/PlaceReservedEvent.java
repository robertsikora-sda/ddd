package reservix.reservation.events;

import lombok.Value;
import reservix.Event;
import reservix.meetup.MeetupId;
import reservix.reservation.PlaceId;

@Value
public class PlaceReservedEvent implements Event {

    private MeetupId meetupId;

    private PlaceId placeId;

}
