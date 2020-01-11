package reservix.projection;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import reservix.MeetupId;
import reservix.PlaceId;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Singleton
class MeetupsPlacesProjectionInMemoryRepo {

    private static final MultiMap<String, MeetupPlacesProjectionDto> MEETUPS_PLACES_PROJECTION = MultiValueMap.multiValueMap(new HashMap<>());

    MeetupPlacesProjectionDto save(final MeetupPlacesProjectionDto projectionDto) {
        return ((List<MeetupPlacesProjectionDto>)MEETUPS_PLACES_PROJECTION
            .getOrDefault(projectionDto.getMeetupId(), Collections.emptyList()))
            .stream().filter(t -> Objects.equals(t.getPlaceId(), projectionDto.getPlaceId()))
                .findFirst()
            .map(t -> {
                        t.setStatus(projectionDto.getStatus());
                        return t;
                    }
            ).orElseGet(() -> {
                        MEETUPS_PLACES_PROJECTION.put(projectionDto.getMeetupId(), projectionDto);
                        return projectionDto;
            });
    }

    MeetupPlacesProjectionDto get(final MeetupId meetupId, final PlaceId placeId) {
        return ((List<MeetupPlacesProjectionDto>)MEETUPS_PLACES_PROJECTION.get(meetupId.toString())).stream().filter(
                t -> Objects.equals(t.getPlaceId(), placeId.toString())).findFirst().orElseThrow(() -> new IllegalStateException("Cannot find place for given id")
        );
    }

    List<MeetupPlacesProjectionDto> findAllPlaces(final String meetupId) {
        return (List<MeetupPlacesProjectionDto>) MEETUPS_PLACES_PROJECTION.get(meetupId);
    }
}
