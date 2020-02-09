package reservix.application;

import lombok.AllArgsConstructor;
import reservix.meetup.*;
import reservix.user.LoggedUserSupplier;

import javax.inject.Singleton;
import java.time.LocalDateTime;

import static reservix.meetup.Meetup.AvailablePlaces;
import static reservix.meetup.Meetup.MeetupName;
import static reservix.meetup.Meetup.MeetupTime;

@Singleton
@AllArgsConstructor
public class MeetupService {

    private final MeetupRepository meetupRepository;

    public MeetupCreated createNewMeetup(final String name,
                                         final LocalDateTime time,
                                         final int availablePlaces) {

        final Meetup meetup = meetupRepository.save(

               new Meetup(LoggedUserSupplier.loggedUser(),
                        new MeetupName(name),
                        new MeetupTime(time),
                        new AvailablePlaces(availablePlaces),
                        place -> new PlaceNumber("Place" + place))

        );

        return new MeetupCreated(meetup.getId());
    }
}
