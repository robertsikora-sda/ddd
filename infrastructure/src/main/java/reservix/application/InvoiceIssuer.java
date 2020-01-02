package reservix.application;

import lombok.AllArgsConstructor;
import reservix.ticket.TicketIssuedEvent;

import javax.inject.Singleton;

@Singleton
@AllArgsConstructor
public class InvoiceIssuer {

    public TicketIssuedEvent issueNewInvoice(final TicketIssuedEvent event) {

        return event;
    }

}
