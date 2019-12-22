package reservix.meetup;

import lombok.AllArgsConstructor;
import reservix.user.UserId;

import java.time.LocalDateTime;

@AllArgsConstructor
public class MeetupService {

    private final MeetupRepo meetupRepo;

    public Meetup createNewMeetup(final String name,
                                  final LocalDateTime time,
                                  final int availablePlaces,
                                  final UserId ownerId) {

        final Meetup meetup = Meetup.createNewMeetup(
                new Meetup.MeetupName(name),
                new Meetup.MeetupTime(time),
                availablePlaces,
                ownerId);

        meetupRepo.save(meetup);
        return meetup;
    }
}
