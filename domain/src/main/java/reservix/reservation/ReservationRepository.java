package reservix.reservation;

import io.vavr.control.Option;
import reservix.user.LoggedUserSupplier;

import java.util.Objects;

public interface ReservationRepository {

    Reservation get(ReservationId id);

    Reservation save(Reservation meetup);

    default Reservation getWithAccessCheck(final ReservationId id) {
        final Reservation reservation = Option.of(get(id))
                .getOrElseThrow(() -> new ReservationNotFoundException());

        if(!Objects.equals(reservation.getOwnerId(), LoggedUserSupplier.loggedUser())) {
            throw new ReservationAccessForbiddenException();
        }

        return reservation;
    }

}
