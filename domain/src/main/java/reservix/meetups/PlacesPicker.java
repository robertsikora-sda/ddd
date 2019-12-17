package reservix.meetups;

import reservix.reservation.ReservationEvents;

class PlacesPicker {

    private MeetupRepo meetupRepo;

    public void reservePlace(final ReservationEvents.PlacePickedEvent event) {
        final Meetup meetup = meetupRepo.get(event.getMeetupId());
        meetup.reserveNewPlace(event.getPlaceId());
        meetupRepo.save(meetup);
    }

    public void freePlace(final ReservationEvents.PlaceUnpickedEvent event) {
        final Meetup meetup = meetupRepo.get(event.getMeetupId());
        meetup.freePlace(event.getPlaceId());
        meetupRepo.save(meetup);
    }

}
