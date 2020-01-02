package reservix.command;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.convert.format.Format;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

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
@AllArgsConstructor
@NoArgsConstructor
class CreateNewMeetupCommandResult {

    private UUID meetupId;

}
