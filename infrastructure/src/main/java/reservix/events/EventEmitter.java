package reservix.events;

import reservix.Event;

import java.util.Collection;

public interface EventEmitter {

    void emit(Collection<Event> events);

}
