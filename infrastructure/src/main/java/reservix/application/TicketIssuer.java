package reservix.application;

import lombok.AllArgsConstructor;
import reservix.projection.MeetupProjection;
import reservix.projection.MeetupProjectionRepo;
import reservix.reservation.events.ReservationAcceptedEvent;
import reservix.ticket.Ticket;
import reservix.ticket.TicketRepo;

import javax.inject.Singleton;
import java.util.stream.Collectors;

@Singleton
@AllArgsConstructor
public class TicketIssuer {

    private final TicketRepo ticketRepo;
    private final MeetupProjectionRepo meetupProjectionRepo;

    public Ticket issueNewTicket(final ReservationAcceptedEvent event) {

        final MeetupProjection meetup = meetupProjectionRepo.findById(event.getMeetupId());

        return ticketRepo.save(Ticket.builder()
                .ownerFullName(meetup.getOwnerId())
                .meetupName(meetup.getMeetupName())
                .meetupTime(meetup.getMeetupDateTime())
                .places(event.getReservedPlaces().map(t -> t.getPlaceNumber().getNumber()).collect(Collectors.toUnmodifiableList()))
                .build()

        );
    }
}
