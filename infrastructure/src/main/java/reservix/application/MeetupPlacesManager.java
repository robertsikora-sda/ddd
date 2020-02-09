package reservix.application;

import lombok.AllArgsConstructor;
import reservix.meetup.Meetup;
import reservix.meetup.MeetupRepository;
import reservix.reservation.events.PlaceSelectedEvent;
import reservix.reservation.events.PlaceUnselectedEvent;
import reservix.reservation.events.ReservationAcceptedEvent;
import reservix.reservation.events.ReservationRejectedEvent;

import javax.inject.Singleton;

@Singleton
@AllArgsConstructor
public class MeetupPlacesManager {

    private final MeetupRepository meetupRepository;

    public void placeSelected(final PlaceSelectedEvent event) {
        final Meetup meetup = meetupRepository.get(event.getPlaceId().getMeetupId());
        meetup.selectMeetupPlace(event.getPlaceId());

        meetupRepository.save(meetup);
    }

    public void placeUnselected(final PlaceUnselectedEvent event) {
        final Meetup meetup = meetupRepository.get(event.getPlaceId().getMeetupId());
        meetup.unselectMeetupPlace(event.getPlaceId());

        meetupRepository.save(meetup);
    }

    public void reservationAccepted(final ReservationAcceptedEvent event) {
        final Meetup meetup = meetupRepository.get(event.getMeetupId());
        event.getReservedPlaces().forEach(
                t -> meetup.reserveMeetupPlace(t)
        );

        meetupRepository.save(meetup);
    }

    public void reservationRejected(final ReservationRejectedEvent event) {
        final Meetup meetup = meetupRepository.get(event.getMeetupId());
        event.getReservedPlaces().forEach(
                t -> meetup.unselectMeetupPlace(t)
        );

        meetupRepository.save(meetup);
    }

}
