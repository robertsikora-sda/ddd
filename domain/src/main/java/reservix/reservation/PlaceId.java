package reservix.reservation;

import lombok.Value;

import java.util.UUID;

@Value
public class PlaceId {

    private final UUID id;

    public static PlaceId of(String id) {
        return new PlaceId(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
