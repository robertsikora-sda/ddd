package reservix.application;

import lombok.AllArgsConstructor;
import reservix.meetup.Meetup;
import reservix.meetup.MeetupRepository;
import reservix.reservation.events.ReservationAcceptedEvent;
import reservix.ticket.Ticket;
import reservix.ticket.TicketRepo;

import javax.inject.Singleton;
import java.util.stream.Collectors;

@Singleton
@AllArgsConstructor
public class TicketIssuer {

    private final TicketRepo ticketRepo;
    private final MeetupRepository meetupRepository;

    public Ticket issueNewTicket(final ReservationAcceptedEvent event) {

        final Meetup meetup = meetupRepository.get(event.getMeetupId());

        return ticketRepo.save(Ticket.builder()
                .ownerFullName("TO-DO")
                .meetupName(meetup.getMeetupName().getValue())
                .meetupTime(meetup.getMeetupTime().getValue())
                .places(event.getReservedPlaces().map(t -> t.getPlaceNumber().getNumber()).collect(Collectors.toUnmodifiableList()))
                .build()

        );
    }
}
