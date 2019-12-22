package reservix;

import io.vavr.collection.List;
import lombok.Getter;

public abstract class AggregateRoot {

    @Getter
    private final List<Event> events = List.empty();

    protected Object emitEvent(final Event event) {
        this.events.append(event);
        return event;
    }

}
