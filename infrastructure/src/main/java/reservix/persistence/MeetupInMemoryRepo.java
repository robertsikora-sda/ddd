package reservix.persistence;

import reservix.MeetupId;
import reservix.events.EventEmitter;
import reservix.events.SimpleMeetupEventsEmitter;
import reservix.meetup.Meetup;
import reservix.meetup.MeetupRepo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MeetupInMemoryRepo implements MeetupRepo {

    private static final Map<MeetupId, Meetup> INSTANCES = new ConcurrentHashMap<>();
    private EventEmitter emitter = new SimpleMeetupEventsEmitter();

    @Override
    public Meetup get(final MeetupId id) {
        return INSTANCES.get(id);
    }

    @Override
    public Meetup save(final Meetup meetup) {
        final Meetup addedMeetup = INSTANCES.putIfAbsent(meetup.getId(), meetup);

        emitter.emit(meetup.getEvents());

        return addedMeetup;
    }
}
