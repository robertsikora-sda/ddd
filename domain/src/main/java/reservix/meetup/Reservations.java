package reservix.meetup;

import reservix.user.UserId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static reservix.meetup.Reservation.Status.OPEN;

class Reservations {

    private final List<Reservation> reservations = new ArrayList<>();

    Reservation getUserReservation(final UserId userId) {
        return reservations.stream()
                .filter(getUserOpenedReservation(userId)).findFirst()
                .orElseGet(() -> {
                            final Reservation reservation = new Reservation(userId);
                            reservations.add(reservation);
                            return reservation;
                        });
    }

    private Predicate<Reservation> getUserOpenedReservation(UserId userId) {
        return t -> Objects.equals(userId, t.getOwnerId()) && Objects.equals(OPEN, t.getStatus());
    }
}
