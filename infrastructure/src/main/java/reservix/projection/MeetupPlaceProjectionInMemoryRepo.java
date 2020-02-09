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
class MeetupPlaceProjectionInMemoryRepo implements MeetupsPlacesProjectionRepo {

    private static final List<MeetupPlaceProjection> MEETUPS_PLACES_PROJECTION = new ArrayList<>();

    @Override
    public MeetupPlaceProjection save(final MeetupPlaceProjection projectionDto) {
        return MEETUPS_PLACES_PROJECTION.stream()
                .filter(findMeetupPlace(projectionDto))
                .findFirst()
                .map(updateExistingPlace(projectionDto))
                .orElseGet(createNewPlace(projectionDto));
    }

    private Predicate<MeetupPlaceProjection> findMeetupPlace(MeetupPlaceProjection projectionDto) {
        return t -> Objects.equals(PlaceId.of(t.getMeetupId(), t.getPlaceNumber()),
                PlaceId.of(projectionDto.getMeetupId(), projectionDto.getPlaceNumber()));
    }

    private Supplier<MeetupPlaceProjection> createNewPlace(MeetupPlaceProjection projectionDto) {
        return () -> {
                MEETUPS_PLACES_PROJECTION.add(projectionDto);
                return projectionDto;
            };
    }

    private Function<MeetupPlaceProjection, MeetupPlaceProjection> updateExistingPlace(MeetupPlaceProjection projectionDto) {
        return dto -> {
                    dto.setStatus(projectionDto.getStatus());
                    return dto;
                };
    }

    @Override
    public MeetupPlaceProjection findById(final PlaceId placeId) {
        return MEETUPS_PLACES_PROJECTION.stream().filter(t -> Objects.equals(placeId, PlaceId.of(t.getMeetupId(), t.getPlaceNumber()))).findFirst()
                .orElse(new MeetupPlaceProjection());
    }

    @Override
    public Set<MeetupPlaceProjection> findAllPlaces(final MeetupId meetupId) {
        return HashSet.ofAll(MEETUPS_PLACES_PROJECTION.stream()
                .filter(t -> Objects.equals(String.valueOf(meetupId.getId()), t.getMeetupId())).collect(Collectors.toSet()));
    }
}
