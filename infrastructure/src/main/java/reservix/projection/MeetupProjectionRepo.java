package reservix.projection;

import io.vavr.collection.Set;
import reservix.meetup.MeetupId;

public interface MeetupProjectionRepo {

    MeetupProjection save(MeetupProjection projectionDto);

    Set<MeetupProjection> findAll();

    MeetupProjection findById(MeetupId meetupId);
}
