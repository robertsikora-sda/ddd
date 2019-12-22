package reservix;

import lombok.Value;

import java.util.UUID;

@Value
public class DomainId {

    private UUID id;

    public DomainId(UUID id) {
        this.id = id;
    }

    public static DomainId generate() {
        return new DomainId(UUID.randomUUID());
    }
}
