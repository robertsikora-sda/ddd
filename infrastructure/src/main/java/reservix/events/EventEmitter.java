package reservix.events;

import io.vavr.collection.Seq;
import reservix.Event;

public interface EventEmitter {

    void emit(Seq<Event> events);

}
