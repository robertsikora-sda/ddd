package reservix.configuration;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import reservix.meetup.MeetupRepo;
import reservix.application.MeetupService;
import reservix.user.UserId;

import javax.inject.Singleton;
import java.util.UUID;
import java.util.function.Supplier;

@Factory
class ApplicationConfiguration {

    private final Supplier<UserId> loggedUserIdSupplier = () -> new UserId(UUID.randomUUID());

    @Bean
    @Singleton
    public MeetupService meetupService(final MeetupRepo meetupRepo) {

        return new MeetupService(meetupRepo, loggedUserIdSupplier);
    }

}
