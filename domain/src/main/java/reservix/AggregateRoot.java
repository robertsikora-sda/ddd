package reservix;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {

    @Getter
    private final List<Event> events = new ArrayList<>();

    protected Object emitEvent(final Event event) {
        this.events.add(event);
        return event;
    }

}
