package reservix.ticket;

import lombok.Value;
import reservix.Event;

@Value
public class TicketIssuedEvent implements Event {

    private final Ticket ticket;

}
