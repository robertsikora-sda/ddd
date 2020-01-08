package reservix.command;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.convert.format.Format;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;
import reservix.MeetupId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Introspected
@Value
class CreateNewMeetupCommand {

    @NotBlank
    private final String meetupName;

    @NotNull
    @Format("yyyy-mm-dd hh:mm")
    private final LocalDateTime meetupDate;

    private final int availablePlacesNumber;

}

@Getter
@NoArgsConstructor
class CreateNewMeetupCommandResult {

    private String meetupId;

    public CreateNewMeetupCommandResult(MeetupId meetupId) {
        this.meetupId = String.valueOf(meetupId.getId());
    }
}
