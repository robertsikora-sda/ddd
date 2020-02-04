package reservix.user;

import java.util.UUID;

public class LoggedUserSupplier {

    public static UserId loggedUser() {
        return new UserId(UUID.randomUUID());
    }
}
