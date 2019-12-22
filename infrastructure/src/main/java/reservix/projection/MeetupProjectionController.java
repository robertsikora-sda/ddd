package reservix.projection;

import io.vavr.collection.Seq;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MeetupProjectionController {

    private final MeetupsProjectionRepo meetupsProjectionRepo;

    public Seq<MeetupsProjectionDto> getAllMeetups() {
        return meetupsProjectionRepo.findAll();
    }

}
