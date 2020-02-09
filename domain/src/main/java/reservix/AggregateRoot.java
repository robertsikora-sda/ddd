package reservix;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {

    private final List<Event> events = new ArrayList<>();

    protected Event emitEvent(final Event event) {
        this.events.add(event);
        return event;
    }

    public io.vavr.collection.List<Event> getEvents() {
        return io.vavr.collection.List.ofAll(events);
    }

}
