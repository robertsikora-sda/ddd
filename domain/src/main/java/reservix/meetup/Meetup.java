package reservix.meetup;

import io.vavr.collection.HashSet;
import lombok.Getter;
import lombok.Value;
import reservix.AggregateRoot;
import reservix.meetup.events.MeetupCreatedEvent;
import reservix.user.UserId;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class Meetup extends AggregateRoot {

    private MeetupId id;
    private UserId ownerId;
    private MeetupName meetupName;
    private MeetupTime meetupTime;
    private AvailablePlaces availablePlaces;
    private PlaceNumberPolicy placeNumberPolicy;

    private Set<MeetupPlace> places = Collections.emptySet();

    public Meetup(final UserId ownerId,
                  final MeetupName meetupName,
                  final MeetupTime meetupTime,
                  final AvailablePlaces availablePlaces,
                  final PlaceNumberPolicy placeNumberPolicy) {

        this.id = MeetupId.id();
        this.ownerId = ownerId;
        this.meetupName = meetupName;
        this.meetupTime = meetupTime;
        this.availablePlaces = availablePlaces;
        this.placeNumberPolicy = placeNumberPolicy;
        this.places.addAll(generateMeetupPlaces(availablePlaces.value));

        this.emitEvent(new MeetupCreatedEvent(id, meetupName.getValue(), meetupTime.getValue(),
                ownerId, HashSet.ofAll(places).map(MeetupPlace::getId)));
    }

    private Set<MeetupPlace> generateMeetupPlaces(final int placesNumber) {
        return IntStream
                .range(0, placesNumber)
                .mapToObj(i -> new MeetupPlace(new PlaceId(id, placeNumberPolicy.generate(i)))).collect(Collectors.toSet());
    }

    public MeetupPlace selectMeetupPlace(final PlaceId placeId) {
        return findPlaceById(placeId).selectPlace();
    }

    public MeetupPlace unselectMeetupPlace(final PlaceId placeId) {
        return findPlaceById(placeId).unselectPlace();
    }

    public MeetupPlace reserveMeetupPlace(final PlaceId placeId) {
        return findPlaceById(placeId).reservePlace();
    }

    private MeetupPlace findPlaceById(final PlaceId placeId) {
        return places.stream().filter(place -> Objects.equals(placeId, place.getId())).findFirst().orElseThrow(
                () -> new IllegalStateException("Cannot find place !")
        );
    };

    @Value
    public static class MeetupName {

        private final static int MAX_LENGTH = 100;

        private String value;

        public MeetupName(final String name) {
            if(name.length() > MAX_LENGTH) {
                throw new IllegalArgumentException("MeetupName length cannot exceed 20 characters !");
            }
            this.value = name;
        }
    }

    @Value
    public static class MeetupTime {

        private LocalDateTime value;

        public MeetupTime(final LocalDateTime time) {
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



