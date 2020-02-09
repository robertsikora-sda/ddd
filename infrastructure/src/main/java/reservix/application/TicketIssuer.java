package reservix.application;

import lombok.AllArgsConstructor;
import reservix.projection.MeetupProjectionDto;
import reservix.projection.MeetupsProjectionRepo;
import reservix.reservation.events.ReservationAcceptedEvent;
import reservix.ticket.Ticket;
import reservix.ticket.TicketRepo;

import javax.inject.Singleton;
import java.util.stream.Collectors;

@Singleton
@AllArgsConstructor
public class TicketIssuer {

    private final TicketRepo ticketRepo;
    private final MeetupsProjectionRepo meetupsProjectionRepo;

    public Ticket issueNewTicket(final ReservationAcceptedEvent event) {

        final MeetupProjectionDto meetup = meetupsProjectionRepo.findById(event.getMeetupId());

        return ticketRepo.save(Ticket.builder()
                .ownerFullName(meetup.getOwnerId())
                .meetupName(meetup.getMeetupName())
                .meetupTime(meetup.getMeetupDateTime())
                .places(event.getReservedPlaces().map(t -> t.getPlaceNumber().getNumber()).collect(Collectors.toUnmodifiableList()))
                .build()

        );
    }
}
