package reservix.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetupPlaceProjection {

    private String meetupId;
    private String placeNumber;
    private Status status;

    public enum Status {
        FREE,
        SELECTED,
        RESERVED
    }

}
