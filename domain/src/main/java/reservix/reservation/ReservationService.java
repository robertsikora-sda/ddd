package reservix.reservation;

import lombok.AllArgsConstructor;
import reservix.MeetupId;
import reservix.PlaceId;
import reservix.meetups.FreePlaceFinder;

@AllArgsConstructor
public class ReservationService {

    private final ReservationRepo reservationRepo;
    private final FreePlaceFinder freePlaceFinder;

    public Reservation createNewResevation(final MeetupId meetupId) {
        final Reservation reservation = Reservation.createNewReservation(meetupId);
        reservationRepo.save(reservation);
        return reservation;
    }

    public void pickupPlace(final ReservationId reservationId, final PlaceId placeId) {
        final Reservation reservation = reservationRepo.get(reservationId);
        reservation.pickupPlace(placeId, freePlaceFinder);
        reservationRepo.save(reservation);
    }

    public void unpickupPlace(final ReservationId reservationId, final PlaceId placeId) {
        final Reservation reservation = reservationRepo.get(reservationId);
        reservation.unpickupPlace(placeId);
        reservationRepo.save(reservation);
    }

    public void acceptReservation(final ReservationId reservationId) {
        final Reservation reservation = reservationRepo.get(reservationId);
        reservation.accept();
        reservationRepo.save(reservation);
    }

    public void rejectReservation(final ReservationId reservationId) {
        final Reservation reservation = reservationRepo.get(reservationId);
        reservation.reject();
        reservationRepo.save(reservation);
    }
}
