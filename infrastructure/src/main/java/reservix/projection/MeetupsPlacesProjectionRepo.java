package reservix.projection;

import io.vavr.collection.Set;
import reservix.meetup.MeetupId;
import reservix.meetup.PlaceId;

public interface MeetupsPlacesProjectionRepo {

    MeetupPlacesProjectionDto save(MeetupPlacesProjectionDto projectionDto);

    MeetupPlacesProjectionDto findById(PlaceId placeId);

    Set<MeetupPlacesProjectionDto> findAllPlaces(MeetupId meetupId);
}
