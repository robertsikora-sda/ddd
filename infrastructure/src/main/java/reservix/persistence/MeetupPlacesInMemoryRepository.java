package reservix.persistence;

import lombok.AllArgsConstructor;
import reservix.reservation.PlaceId;
import reservix.events.EventEmitter;
import reservix.meetup.MeetupPlace;
import reservix.meetup.MeetupPlaceRepository;

import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@AllArgsConstructor
class MeetupPlacesInMemoryRepository implements MeetupPlaceRepository {

    private static final Map<PlaceId, MeetupPlace> INSTANCES = new ConcurrentHashMap<>();

    private final EventEmitter eventEmitter;

    @Override
    public MeetupPlace get(PlaceId id) {
        return INSTANCES.get(id);
    }

    @Override
    public MeetupPlace save(MeetupPlace place) {
        INSTANCES.putIfAbsent(place.getId(), place);

        eventEmitter.emit(place.getEvents());

        return place;
    }
}
