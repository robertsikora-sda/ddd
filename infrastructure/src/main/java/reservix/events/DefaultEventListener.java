package reservix.events;

import lombok.extern.slf4j.Slf4j;
import reservix.Event;

@Slf4j
public class DefaultEventListener {

    public Event handle(final Event event) {

        return event;
    }
}
