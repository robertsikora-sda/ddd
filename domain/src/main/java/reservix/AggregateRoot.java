package reservix;

import java.util.Collections;
import java.util.List;

public abstract class AggregateRoot {

    private final List<Event> events = Collections.emptyList();

    protected Event emitEvent(final Event event) {
        this.events.add(event);
        return event;
    }

    public io.vavr.collection.List<Event> getEvents() {
        return io.vavr.collection.List.ofAll(events);
    }

}
