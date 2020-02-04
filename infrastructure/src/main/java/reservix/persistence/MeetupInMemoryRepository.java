package reservix.persistence;

import lombok.AllArgsConstructor;
import reservix.meetup.MeetupId;
import reservix.events.EventEmitter;
import reservix.meetup.Meetup;
import reservix.meetup.MeetupRepository;

import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@AllArgsConstructor
class MeetupInMemoryRepository implements MeetupRepository {

    private static final Map<MeetupId, Meetup> INSTANCES = new ConcurrentHashMap<>();

    private final EventEmitter eventEmitter;

    @Override
    public Meetup get(final MeetupId id) {
        return INSTANCES.get(id);
    }

    @Override
    public Meetup save(final Meetup meetup) {
        INSTANCES.putIfAbsent(meetup.getId(), meetup);

        eventEmitter.emit(meetup.getEvents());

        return meetup;
    }
}
