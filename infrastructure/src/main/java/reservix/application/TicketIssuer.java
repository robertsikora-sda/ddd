package reservix.application;

import lombok.AllArgsConstructor;
import reservix.meetup.MeetupProjection;
import reservix.meetup.events.ReservationAcceptedEvent;
import reservix.ticket.NotificationSender;
import reservix.ticket.Ticket;
import reservix.ticket.TicketRepo;

import javax.inject.Singleton;
import java.util.stream.Collectors;

@Singleton
@AllArgsConstructor
public class TicketIssuer {

    private final TicketRepo ticketRepo;
    private final NotificationSender notificationSender;

    public ReservationAcceptedEvent issueNewTicket(final ReservationAcceptedEvent event) {
        final MeetupProjection meetupProjection = event.getMeetupProjection();

        ticketRepo.save(Ticket.builder()
                .ownerFullName("TO-DO")
                .meetupName(meetupProjection.getName())
                .meetupTime(meetupProjection.getTime())
                .places(event.getReservedPlaces().stream()
                        .map(t -> t.getId().toString()).collect(Collectors.toUnmodifiableList()))
                .build()

        );

        notificationSender.sendNotification("recipient@test.com", "Your ticket for is ready to download");

        return event;
    }
}
