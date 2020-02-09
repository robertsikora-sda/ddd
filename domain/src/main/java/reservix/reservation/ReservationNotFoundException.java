package reservix.reservation;

import lombok.Value;

@Value
public class ReservationNotFoundException extends RuntimeException {

    private ReservationId reservationId;

}
