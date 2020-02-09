package reservix.meetup;

import java.util.UUID;

public final class MeetupId {

    private final UUID id;

    private MeetupId(UUID id) {
        this.id = id;
    }

    public static MeetupId id() {
        return new MeetupId(UUID.randomUUID());
    }


    public static MeetupId of(String id) {
        return new MeetupId(UUID.fromString(id));
    }

    public UUID getId() {
        return id;
    }
}
