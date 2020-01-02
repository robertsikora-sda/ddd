package reservix.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
class MeetupPlacesProjectionDto {

    private final String placeId;
    private final String meetupId;
    private final String placeNumber;
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
