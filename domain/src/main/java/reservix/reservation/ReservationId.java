package reservix.reservation;

import java.util.UUID;

public class ReservationId {

    private final UUID id;

    public ReservationId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
