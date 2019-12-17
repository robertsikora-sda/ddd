package reservix;

import io.vavr.collection.List;

public abstract class AggregateRoot {

    private List<Event> events = List.empty();

    protected Object emitEvent(final Event event) {
        this.events.append(event);
        return event;
    }


}
