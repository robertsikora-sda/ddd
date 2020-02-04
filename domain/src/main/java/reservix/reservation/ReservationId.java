package reservix.reservation;

import lombok.Value;

import java.util.UUID;

@Value
public class ReservationId {

    private UUID id;

    public static ReservationId newId() {
        return new ReservationId(UUID.randomUUID());
    }

    public static ReservationId of(final String id) {
        return new ReservationId(UUID.fromString(id));
    }
}
