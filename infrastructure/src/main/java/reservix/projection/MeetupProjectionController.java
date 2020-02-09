package reservix.projection;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.vavr.collection.Set;
import lombok.AllArgsConstructor;
import reservix.meetup.MeetupId;

@Controller("/v1/queries")
@AllArgsConstructor
class MeetupProjectionController {

    private final MeetupProjectionInMemoryRepo meetupProjectionInMemoryRepo;
    private final MeetupPlaceProjectionInMemoryRepo meetupPlaceProjectionInMemoryRepo;

    @Get("/meetups")
    Set<MeetupProjection> getAllMeetups() {
        return meetupProjectionInMemoryRepo.findAll();
    }

    @Get("/meetups/{meetupId}/places")
    Set<MeetupPlaceProjection> getMeetupPlaces(@PathVariable final String meetupId) {
        return meetupPlaceProjectionInMemoryRepo.findAllPlaces(MeetupId.of(meetupId));
    }

}
