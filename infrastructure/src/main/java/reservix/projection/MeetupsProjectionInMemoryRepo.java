package reservix.projection;

import io.vavr.collection.Set;
import io.vavr.collection.HashSet;
import reservix.meetup.MeetupId;

import javax.inject.Singleton;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Singleton
class MeetupsProjectionInMemoryRepo implements MeetupsProjectionRepo {

    private static final List<MeetupProjectionDto> MEETUPS_PROJECTION = new LinkedList<>();

    @Override
    public MeetupProjectionDto save(final MeetupProjectionDto projectionDto) {
        MEETUPS_PROJECTION.add(projectionDto);
        return projectionDto;
    }

    @Override
    public Set<MeetupProjectionDto> findAll() {
        return HashSet.ofAll(MEETUPS_PROJECTION);
    }

    @Override
    public MeetupProjectionDto findById(final MeetupId meetupId) {
        return MEETUPS_PROJECTION.stream()
                .filter(t -> Objects.equals(MeetupId.of(t.getMeetupId()), meetupId)).findFirst().orElse(new MeetupProjectionDto());
    }

}
