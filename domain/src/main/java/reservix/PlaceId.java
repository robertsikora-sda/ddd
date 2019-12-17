package reservix;

import java.util.UUID;

public class PlaceId {

    private final UUID id;

    public PlaceId(UUID id) {
        this.id = id;
    }

    public static PlaceId generate() {
        return new PlaceId(UUID.randomUUID());
    }

    public UUID getId() {
        return id;
    }
}
