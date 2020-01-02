package reservix.events;

import reservix.Event;

class DefaultEventListener {

    public Event handle(final Event event) {
        return event;
    }
}
