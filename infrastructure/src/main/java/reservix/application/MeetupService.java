package reservix.application;

import lombok.AllArgsConstructor;
import reservix.MeetupId;
import reservix.PlaceId;
import reservix.meetup.Meetup;
import reservix.meetup.MeetupRepo;
import reservix.user.UserId;

import java.time.LocalDateTime;
import java.util.function.Supplier;
import java.util.stream.Stream;

@AllArgsConstructor
public class MeetupService {

    private final MeetupRepo meetupRepo;
    private final Supplier<UserId> loggedUserIdSupplier;

    public MeetupId createNewMeetup(final String name,
                                    final LocalDateTime time,
                                    final int availablePlaces) {
        return meetupRepo.save(

               new Meetup(loggedUserIdSupplier.get(),
                        name,
                        time,
                        availablePlaces)

        ).getId();
    }

    public void selectMeetupPlaces(final MeetupId meetupId, final PlaceId ... placeIds) {
        final Meetup meetup = meetupRepo.get(meetupId);
        Stream.of(placeIds).forEach(
                placeId -> {
                    meetup.selectNewPlaces(loggedUserIdSupplier.get(), placeId);
                    meetupRepo.save(meetup);
                }
        );
    }

    public void unselectMeetupPlaces(final MeetupId meetupId, final PlaceId ... placeIds) {
        final Meetup meetup = meetupRepo.get(meetupId);
        Stream.of(placeIds).forEach(
                placeId -> {
                    meetup.unselectPlaces(loggedUserIdSupplier.get(), placeId);
                    meetupRepo.save(meetup);
                }
        );
    }

    public void acceptReservation(final MeetupId meetupId) {
        final Meetup meetup = meetupRepo.get(meetupId);
        meetup.acceptReservation(loggedUserIdSupplier.get());

        meetupRepo.save(meetup);
    }

    public void rejectReservation(final MeetupId meetupId) {
        final Meetup meetup = meetupRepo.get(meetupId);
        meetup.rejectReservation(loggedUserIdSupplier.get());

        meetupRepo.save(meetup);
    }

}
