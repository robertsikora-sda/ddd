package reservix.meetup.events;

import lombok.Value;
import reservix.Event;
import reservix.PlaceId;
import reservix.meetup.MeetupProjection;

import java.util.List;

@Value
public class ReservationAcceptedEvent implements Event {

    private final MeetupProjection meetupProjection;
    private final List<PlaceId> reservedPlaces;

}
