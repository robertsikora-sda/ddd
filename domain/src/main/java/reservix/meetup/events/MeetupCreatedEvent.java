package reservix.meetup.events;

import lombok.Value;
import reservix.Event;
import reservix.meetup.MeetupId;
import reservix.meetup.Meetup;
import reservix.meetup.PlaceNumberPolicy;
import reservix.user.UserId;

@Value
public class MeetupCreatedEvent implements Event {

    private MeetupId meetupId;

    private Meetup.Name name;

    private Meetup.Time time;

    private UserId owner;

    private Meetup.AvailablePlaces places;

    private PlaceNumberPolicy placeNumberPolicy;

}
