package reservix.meetup;

import lombok.Getter;
import lombok.Value;
import reservix.AggregateRoot;
import reservix.meetup.events.MeetupCreatedEvent;
import reservix.user.UserId;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Meetup extends AggregateRoot {

    private MeetupId id;
    private UserId ownerId;
    private Name name;
    private Time time;
    private AvailablePlaces availablePlaces;
    private PlaceNumberPolicy placeNumberPolicy;

    public Meetup(final UserId ownerId,
                   final Name name,
                   final Time time,
                   final AvailablePlaces availablePlaces,
                   final PlaceNumberPolicy placeNumberPolicy) {

        this.id = new MeetupId(UUID.randomUUID());
        this.ownerId = ownerId;
        this.name = name;
        this.time = time;
        this.availablePlaces = availablePlaces;
        this.placeNumberPolicy = placeNumberPolicy;

        this.emitEvent(new MeetupCreatedEvent(id,
                                              name,
                                              time,
                                              ownerId,
                                              availablePlaces,
                                              placeNumberPolicy));
    }

    @Value
    public static class Name {

        private final static int MAX_LENGTH = 100;

        private String value;

        public Name(final String name) {
            if(name.length() > MAX_LENGTH) {
                throw new IllegalArgumentException("Name length cannot exceed 20 characters !");
            }
            this.value = name;
        }
    }

    @Value
    public static class Time {

        private LocalDateTime value;

        public Time(final LocalDateTime time) {
            if(time.isBefore(LocalDateTime.now().plusDays(7))) {
                throw new IllegalArgumentException("Meetup should be scheduled with 7 days in advance !");
            }
            this.value = time;
        }
    }

    @Value
    public static class AvailablePlaces {

        private int value;

        public AvailablePlaces(final int value) {
            if(value <= 0) {
                throw new IllegalArgumentException("Places number should be positive !");
            }
            this.value = value;
        }
    }
}



