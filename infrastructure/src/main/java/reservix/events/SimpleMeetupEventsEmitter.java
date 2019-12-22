package reservix.events;

import io.vavr.collection.Seq;
import lombok.AllArgsConstructor;
import reservix.Event;
import reservix.projection.MeetupsProjectionEventListener;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

import static reservix.meetup.MeetupEvents.MeetupCreatedEvent;

@AllArgsConstructor
public class SimpleMeetupEventsEmitter implements EventEmitter {

    private final MeetupsProjectionEventListener meetupsProjectionEventListener;

    @Override
    public void emit(final Seq<Event> events) {

        events.forEach(
                event -> Match(event).of(
                        Case($(instanceOf(MeetupCreatedEvent.class)), meetupsProjectionEventListener::updateMeetupsProjection),
                        Case($(), e -> new DefaultEventListener().handle((Event) e)))
        );

    }
}
