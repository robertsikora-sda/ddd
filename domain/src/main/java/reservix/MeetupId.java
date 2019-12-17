package reservix;

import java.util.UUID;

public class MeetupId {

    private final UUID id;

    public MeetupId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

}
