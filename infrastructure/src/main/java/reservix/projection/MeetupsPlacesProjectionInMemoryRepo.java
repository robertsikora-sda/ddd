package reservix.projection;

import io.vavr.collection.Set;
import io.vavr.collection.HashSet;
import reservix.meetup.MeetupId;
import reservix.meetup.PlaceId;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Singleton
class MeetupsPlacesProjectionInMemoryRepo {

    private static final List<MeetupPlacesProjectionDto> MEETUPS_PLACES_PROJECTION = new ArrayList<>();

    MeetupPlacesProjectionDto save(final MeetupPlacesProjectionDto projectionDto) {
        return MEETUPS_PLACES_PROJECTION.stream()
                .filter(findMeetupPlace(projectionDto))
                .findFirst()
                .map(updateExistingPlace(projectionDto))
                .orElseGet(createNewPlace(projectionDto));
    }

    private Predicate<MeetupPlacesProjectionDto> findMeetupPlace(MeetupPlacesProjectionDto projectionDto) {
        return t -> Objects.equals(PlaceId.of(t.getMeetupId(), t.getPlaceNumber()),
                PlaceId.of(projectionDto.getMeetupId(), projectionDto.getPlaceNumber()));
    }

    private Supplier<MeetupPlacesProjectionDto> createNewPlace(MeetupPlacesProjectionDto projectionDto) {
        return () -> {
                MEETUPS_PLACES_PROJECTION.add(projectionDto);
                return projectionDto;
            };
    }

    private Function<MeetupPlacesProjectionDto, MeetupPlacesProjectionDto> updateExistingPlace(MeetupPlacesProjectionDto projectionDto) {
        return dto -> {
                    dto.setStatus(projectionDto.getStatus());
                    return dto;
                };
    }

    MeetupPlacesProjectionDto get(final PlaceId placeId) {
        return MEETUPS_PLACES_PROJECTION.stream().filter(t -> Objects.equals(placeId, PlaceId.of(t.getMeetupId(), t.getPlaceNumber()))).findFirst()
                .orElse(new MeetupPlacesProjectionDto());
    }

    Set<MeetupPlacesProjectionDto> findAllPlaces(final MeetupId meetupId) {
        return HashSet.ofAll(MEETUPS_PLACES_PROJECTION.stream()
                .filter(t -> Objects.equals(String.valueOf(meetupId.getId()), t.getMeetupId())).collect(Collectors.toSet()));
    }
}
