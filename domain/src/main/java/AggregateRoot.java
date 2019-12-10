import io.vavr.collection.List;

abstract class AggregateRoot {

    private List<Object> events = List.empty();

    protected Object emitEvent(final Object event) {
        this.events.append(event);
        return event;
    }
}
