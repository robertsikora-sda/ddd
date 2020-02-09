package reservix.projection;

import io.vavr.collection.Set;
import reservix.meetup.MeetupId;
import reservix.meetup.PlaceId;

public interface MeetupPlacesProjectionRepo {

    MeetupPlaceProjection save(MeetupPlaceProjection projectionDto);

    MeetupPlaceProjection findById(PlaceId placeId);

    Set<MeetupPlaceProjection> findAllPlaces(MeetupId meetupId);
}
