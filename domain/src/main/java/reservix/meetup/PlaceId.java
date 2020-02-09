package reservix.meetup;

import lombok.Value;

@Value
public final class PlaceId {

    private MeetupId meetupId;
    private PlaceNumber placeNumber;

    public static PlaceId of(final String meetupId, final String placeNumber) {
        return new PlaceId(MeetupId.of(meetupId), new PlaceNumber(placeNumber));
    }

}
