package reservix.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetupsProjectionDto {

    private String meetupId;
    private String ownerId;
    private String meetupName;
    private LocalDateTime meetupDateTime;
    private boolean areFreePlaces;

}
