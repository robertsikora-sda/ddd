package reservix.application;

import lombok.AllArgsConstructor;
import reservix.meetup.*;
import reservix.user.LoggedUserSupplier;

import javax.inject.Singleton;
import java.time.LocalDateTime;

import static reservix.meetup.Meetup.AvailablePlaces;
import static reservix.meetup.Meetup.Name;
import static reservix.meetup.Meetup.Time;

@Singleton
@AllArgsConstructor
public class MeetupService {

    private final MeetupRepository meetupRepository;

    public MeetupCreated createNewMeetup(final String name,
                                         final LocalDateTime time,
                                         final int availablePlaces) {

        final Meetup meetup = meetupRepository.save(

               new Meetup(LoggedUserSupplier.loggedUser(),
                        new Name(name),
                        new Time(time),
                        new AvailablePlaces(availablePlaces),
                        () -> {

                            int number = 1;
                            return new PlaceNumber(String.valueOf(number++));

                        })

        );

        return new MeetupCreated(meetup.getId());
    }
}
