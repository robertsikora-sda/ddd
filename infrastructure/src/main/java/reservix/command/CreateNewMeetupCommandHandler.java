package reservix.command;

import lombok.AllArgsConstructor;
import reservix.meetup.MeetupService;
import reservix.user.UserId;

import java.util.UUID;
import java.util.function.Supplier;

@AllArgsConstructor
public class CreateNewMeetupCommandHandler {

    private final MeetupService meetupService;
    private final Supplier<UserId> userSupplier = () -> new UserId(UUID.randomUUID());

    public void handle(final CreateNewMeetupCommand command) {
        meetupService.createNewMeetup(
                command.getMeetupName(),
                command.getMeetupDate(),
                command.getAvailablePlacesNumber(),
                userSupplier.get()
        );
    }

}
