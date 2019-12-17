package reservix.meetups;

import java.time.LocalDateTime;

public class MeetupService {

    private final MeetupRepo meetupRepo;

    public MeetupService(MeetupRepo meetupRepo) {
        this.meetupRepo = meetupRepo;
    }

    public Meetup createNewMeetup(final String name, final LocalDateTime time, final int availablePlaces) {
        final Meetup meetup = Meetup.createNewMeetup(
                new Name(name),
                new Time(time),
                new AvailablePlaces(availablePlaces));

        meetupRepo.save(meetup);
        return meetup;
    }
}
