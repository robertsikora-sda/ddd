package reservix;

import lombok.Value;

import java.util.UUID;

@Value
public class MeetupId {

    private final UUID id;

    public static MeetupId of(String id) {
        return new MeetupId(UUID.fromString(id));
    }

}
