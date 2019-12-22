package reservix.ticket;

import lombok.AllArgsConstructor;
import reservix.meetup.MeetupInfoDto;
import reservix.meetup.MeetupInfoProvider;
import reservix.reservation.ReservationEvents;

@AllArgsConstructor
class TicketIssuer {

    private final TicketRepo ticketRepo;
    private final MeetupInfoProvider meetupInfoProvider;
    private final Notifier notifier;

    void issueNewTicket(final ReservationEvents.ReservationAcceptedEvent event) {
        final MeetupInfoDto meetupInfo = meetupInfoProvider.getInfo(event.getMeetupId());
        ticketRepo.save(new Ticket());
        notifier.sendNotifcation("", "Your ticket for is ready to download");
    }
}
