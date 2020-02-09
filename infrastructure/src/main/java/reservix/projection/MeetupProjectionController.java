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

    private final MeetupsProjectionInMemoryRepo meetupsProjectionInMemoryRepo;
    private final MeetupsPlacesProjectionInMemoryRepo meetupsPlacesProjectionInMemoryRepo;

    @Get("/meetups")
    Set<MeetupsProjectionDto> getAllMeetups() {
        return meetupsProjectionInMemoryRepo.findAll();
    }

    @Get("/meetups/{meetupId}/places")
    Set<MeetupPlacesProjectionDto> getMeetupPlaces(@PathVariable final String meetupId) {
        return meetupsPlacesProjectionInMemoryRepo.findAllPlaces(MeetupId.of(meetupId));
    }

}
