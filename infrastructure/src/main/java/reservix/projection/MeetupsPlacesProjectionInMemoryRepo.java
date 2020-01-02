package reservix.projection;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import reservix.PlaceId;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;

@Singleton
class MeetupsPlacesProjectionInMemoryRepo {

    private static final MultiMap<String, MeetupPlacesProjectionDto> MEETUPS_PLACES_PROJECTION = MultiValueMap.multiValueMap(new HashMap<>());

    MeetupPlacesProjectionDto save(final MeetupPlacesProjectionDto projectionDto) {
        MEETUPS_PLACES_PROJECTION.put(projectionDto.getMeetupId(), projectionDto);
        return projectionDto;
    }

    MeetupPlacesProjectionDto get(final PlaceId placeId) {
        //return MEETUPS_PLACES_PROJECTION.get(placeId);
        return null;
    }

    List<MeetupPlacesProjectionDto> findAllPlaces(final String meetupId) {
        return (List<MeetupPlacesProjectionDto>) MEETUPS_PLACES_PROJECTION.get(meetupId);
    }
}
