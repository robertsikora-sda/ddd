package reservix.tickets;

import reservix.reservation.ReservationEvents;

class TicketIssuer {

    private TicketRepo ticketRepo;
    private Notifier notifier;

    void issueNewTicket(final ReservationEvents.ReservationAcceptedEvent event) {
        ticketRepo.save(new Ticket());
        notifier.sendNotifcation("", "Your ticket for is ready to download");
    }
}
