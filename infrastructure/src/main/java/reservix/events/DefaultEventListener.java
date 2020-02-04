package reservix.events;

import lombok.extern.slf4j.Slf4j;
import reservix.Event;

@Slf4j
class DefaultEventListener {

    public Event handle(final Event event) {
        log.warn("Event {} appeared", event);
        return event;
    }
}
