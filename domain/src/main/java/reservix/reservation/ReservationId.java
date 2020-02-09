package reservix.reservation;

import java.util.UUID;

public final class ReservationId {

    private final UUID id;

    private ReservationId(UUID id) {
        this.id = id;
    }

    public static ReservationId id() {
        return new ReservationId(UUID.randomUUID());
    }

    public static ReservationId of(final String id) {
        return new ReservationId(UUID.fromString(id));
    }
}
