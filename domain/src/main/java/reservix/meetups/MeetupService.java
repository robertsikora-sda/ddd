package reservix.meetups;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class MeetupService {

    private final MeetupRepo meetupRepo;

    public Meetup createNewMeetup(final String name, final LocalDateTime time, final int availablePlaces) {
        final Meetup meetup = Meetup.createNewMeetup(
                new Name(name),
                new Time(time),
                new AvailablePlaces(availablePlaces));

        meetupRepo.save(meetup);
        return meetup;
    }
}
