package reservix.projection;

import io.vavr.collection.Set;
import io.vavr.collection.HashSet;
import reservix.meetup.MeetupId;

import javax.inject.Singleton;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Singleton
class MeetupProjectionInMemoryRepo implements MeetupProjectionRepo {

    private static final List<MeetupProjection> MEETUPS_PROJECTION = new LinkedList<>();

    @Override
    public MeetupProjection save(final MeetupProjection projectionDto) {
        MEETUPS_PROJECTION.add(projectionDto);
        return projectionDto;
    }

    @Override
    public Set<MeetupProjection> findAll() {
        return HashSet.ofAll(MEETUPS_PROJECTION);
    }

    @Override
    public MeetupProjection findById(final MeetupId meetupId) {
        return MEETUPS_PROJECTION.stream()
                .filter(t -> Objects.equals(MeetupId.of(t.getMeetupId()), meetupId)).findFirst().orElse(new MeetupProjection());
    }

}
