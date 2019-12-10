import io.vavr.collection.List;

public class Reservation extends AggregateRoot {

    private Status status;
    private List<PlaceId> places = List.empty();

    enum Status {
        NEW,
        OPEN,
        ACCEPTED,
        REJECTED;

        boolean isDone() {
            return this == ACCEPTED || this == REJECTED;
        }
    }

    private Reservation(final Status status) {
        this.status = status;
    }

    public static Reservation createNewReservation() {
        return new Reservation(Status.NEW);
    }

    public void pickupPlace(final List<PlaceId> places) {
        if(status.isDone()) {
            throw new IllegalStateException("Cannot pickup new places for done reservation !");
        }
        this.status = Status.OPEN;
        this.places.appendAll(places);

        this.places.forEach(
                p -> emitEvent(new PlacePickedEvent(p.getId()))
        );
    }



    static class PlacePickedEvent {
        private final String placeId;

        PlacePickedEvent(String placeId) {
            this.placeId = placeId;
        }
    }


}
