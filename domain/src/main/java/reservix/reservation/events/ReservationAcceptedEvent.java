package reservix.reservation.events;

import lombok.Value;
import reservix.Event;
import reservix.reservation.PlaceId;
import reservix.meetup.Meetup;

import java.util.List;

@Value
public class ReservationAcceptedEvent implements Event {

    private Meetup.Name name;

    private Meetup.Time time;

    private List<PlaceId> reservedPlaces;

}
