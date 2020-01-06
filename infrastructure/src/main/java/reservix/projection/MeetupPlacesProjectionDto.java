package reservix.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetupPlacesProjectionDto {

    private String placeId;
    private String meetupId;
    private String placeNumber;
    private boolean isFree;
    private boolean isSelected;
    private boolean isReserved;

    void select() {
        this.isFree = false;
        this.isSelected = true;
        this.isReserved = false;
    }

    void unselect() {
        this.isFree = true;
        this.isSelected = false;
        this.isReserved = false;
    }

    void reserve() {
        this.isFree = false;
        this.isSelected = false;
        this.isReserved = true;
    }

}
