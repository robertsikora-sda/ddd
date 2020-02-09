package reservix.projection;

import io.vavr.collection.Set;
import reservix.meetup.MeetupId;

public interface MeetupsProjectionRepo {

    MeetupProjectionDto save(MeetupProjectionDto projectionDto);

    Set<MeetupProjectionDto> findAll();

    MeetupProjectionDto findById(MeetupId meetupId);
}
