package reservix.reservation;

public class ReservationService {

    private final ReservationRepo reservationRepo;

    public ReservationService(ReservationRepo reservationRepo) {
        this.reservationRepo = reservationRepo;
    }

    public Reservation createNewResevation() {
        final Reservation reservation = Reservation.createNewReservation();
        reservationRepo.save(reservation);
        return reservation;
    }

    public void pickupPlace(final ReservationId reservationId, final PlaceId placeId) {
        final Reservation reservation = reservationRepo.get(reservationId);
        reservation.pickupPlace(placeId);
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
