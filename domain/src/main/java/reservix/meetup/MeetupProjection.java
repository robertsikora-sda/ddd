package reservix.meetup;

import lombok.Value;
import reservix.MeetupId;
import reservix.user.UserId;

import java.time.LocalDateTime;

@Value
public class MeetupProjection {

    private final MeetupId id;
    private final UserId ownerId;
    private final String name;
    private final LocalDateTime time;
    private final int availablePlaces;
    private final PlaceNumberAssignPolicy placeNumberAssignPolicy;
    private final boolean freePlaces;

    public MeetupProjection(final Meetup meetup) {
        this.id = meetup.getId();
        this.ownerId = meetup.getOwnerId();
        this.name = meetup.getName().getName();
        this.time = meetup.getTime().getTime();
        this.availablePlaces = meetup.getAvailablePlaces();
        this.placeNumberAssignPolicy = meetup.getPlaceNumberAssignPolicy();
        this.freePlaces = false;
    }

}



