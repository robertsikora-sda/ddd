package reservix.command;

import io.micronaut.core.annotation.Introspected;
import lombok.Value;
import javax.validation.constraints.NotEmpty;


@Introspected
@Value
public class ChangeReservationPlacesCommand {

    @NotEmpty
    private String meetupId;
    @NotEmpty
    private String placeNumber;

}
