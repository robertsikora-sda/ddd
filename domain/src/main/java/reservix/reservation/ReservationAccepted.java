package reservix.reservation;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ReservationAccepted {

    private ReservationId id;
    private LocalDateTime reservationDate;

}
