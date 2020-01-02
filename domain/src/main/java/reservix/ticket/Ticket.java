package reservix.ticket;

import lombok.Builder;
import reservix.AggregateRoot;

import java.time.LocalDateTime;
import java.util.List;

public class Ticket extends AggregateRoot {

    private String ownerFullName;
    private String meetupName;
    private LocalDateTime meetupTime;
    private List<String> places;

    @Builder
    public Ticket(String ownerFullName, String meetupName, LocalDateTime meetupTime, List<String> places) {
        this.ownerFullName = ownerFullName;
        this.meetupName = meetupName;
        this.meetupTime = meetupTime;
        this.places = places;

        emitEvent(new TicketIssuedEvent(this));
    }
}
