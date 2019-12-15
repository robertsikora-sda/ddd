package reservix.reservation;

public interface ReservationRepo {

    Reservation get(ReservationId id);

    Reservation save(Reservation reservation);

}
