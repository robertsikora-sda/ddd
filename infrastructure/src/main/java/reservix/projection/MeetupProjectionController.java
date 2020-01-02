package reservix.projection;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import lombok.AllArgsConstructor;

import java.util.List;

@Controller("/v1/queries")
@AllArgsConstructor
class MeetupProjectionController {

    private final MeetupsProjectionInMemoryRepo meetupsProjectionInMemoryRepo;
    private final MeetupsPlacesProjectionInMemoryRepo meetupsPlacesProjectionInMemoryRepo;

    @Get("/meetups")
    List<MeetupsProjectionDto> getAllMeetups() {
        return meetupsProjectionInMemoryRepo.findAll();
    }

    @Get("/meetups/{meetupId}/places")
    List<MeetupPlacesProjectionDto> getMeetupPlaces(@PathVariable final String meetupId) {
        return meetupsPlacesProjectionInMemoryRepo.findAllPlaces(meetupId);
    }

}
