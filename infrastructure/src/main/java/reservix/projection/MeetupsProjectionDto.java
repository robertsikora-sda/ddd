package reservix.projection;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class MeetupsProjectionDto {

    private final String meetupId;
    private final String ownerId;
    private final String meetupName;
    private final LocalDateTime meetupDateTime;
    private final boolean areFreePlaces;

}
