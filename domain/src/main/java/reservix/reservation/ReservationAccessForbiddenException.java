package reservix.reservation;

import lombok.Value;

@Value
public class ReservationAccessForbiddenException extends RuntimeException {

    private ReservationId reservationId;

}
