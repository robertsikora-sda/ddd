package reservix.command;

import lombok.Getter;
import reservix.reservation.ReservationId;

@Getter
public class CreateNewReservationResult {

    private String reservationId;

    public CreateNewReservationResult(final ReservationId reservationId) {
        this.reservationId = String.valueOf(reservationId.toString());
    }
}
