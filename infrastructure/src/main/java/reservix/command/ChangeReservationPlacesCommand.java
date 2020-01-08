package reservix.command;

import io.micronaut.core.annotation.Introspected;
import lombok.Value;
import reservix.PlaceId;

import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.stream.Collectors;

@Introspected
@Value
public class ChangeReservationPlacesCommand {

    @NotEmpty
    private Set<String> placeIds;

    public Set<PlaceId> toPlaces() {
        return placeIds.stream().map(PlaceId::of).collect(Collectors.toUnmodifiableSet());
    }

}
