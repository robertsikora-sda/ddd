package reservix.meetup;

import lombok.AllArgsConstructor;
import reservix.reservation.ReservationEvents;

@AllArgsConstructor
class PlacesPicker {

    private final MeetupRepo meetupRepo;

    public void reservePlace(final ReservationEvents.PlacePickedEvent event) {
        final Meetup meetup = meetupRepo.get(event.getMeetupId());
        meetup.reserveNewPlace(event.getPlaceId());
        meetupRepo.save(meetup);
    }

    public void freePlace(final ReservationEvents.PlaceUnpickedEvent event) {
        final Meetup meetup = meetupRepo.get(event.getMeetupId());
        meetup.makePlaceFree(event.getPlaceId());
        meetupRepo.save(meetup);
    }

}
