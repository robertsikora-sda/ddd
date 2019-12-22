package reservix.meetup;

import lombok.AllArgsConstructor;
import reservix.MeetupId;

@AllArgsConstructor
public class MeetupInfoProvider {

    private final MeetupRepo meetupRepo;

    public MeetupInfoDto getInfo(final MeetupId meetupId) {
        return new MeetupInfoDto(meetupRepo.get(meetupId));
    }
}
