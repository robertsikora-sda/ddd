package reservix.command;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class CreateNewMeetupCommand {

    private final String meetupName;
    private final LocalDateTime meetupDate;
    private final int availablePlacesNumber;

}
