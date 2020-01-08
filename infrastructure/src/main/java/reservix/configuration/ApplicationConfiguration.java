package reservix.configuration;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import reservix.meetup.MeetupRepo;
import reservix.application.MeetupService;
import reservix.user.UserId;

import java.util.UUID;
import java.util.function.Supplier;

@Factory
class ApplicationConfiguration {

    private final Supplier<UserId> loggedUserIdSupplier = () -> new UserId(
            UUID.fromString("fb53c6b9-7bda-4a84-9a5d-1467757d7e18")
    );

    @Bean
    public MeetupService meetupService(final MeetupRepo meetupRepo) {

        return new MeetupService(meetupRepo, loggedUserIdSupplier);
    }

}
