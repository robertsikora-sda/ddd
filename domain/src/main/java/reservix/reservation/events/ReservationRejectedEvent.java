package reservix.reservation.events;

import io.vavr.collection.Set;
import lombok.Value;
import reservix.Event;
import reservix.meetup.MeetupId;
import reservix.meetup.PlaceId;

@Value
public class ReservationRejectedEvent implements Event {

    private MeetupId meetupId;

    private Set<PlaceId> reservedPlaces;

}
