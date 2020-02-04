package reservix.reservation;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ReservationRejected {

    private ReservationId id;
    private LocalDateTime rejectionDate;

}
