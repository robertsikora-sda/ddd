package reservix.persistence;

import lombok.AllArgsConstructor;
import reservix.events.EventEmitter;
import reservix.ticket.Ticket;
import reservix.ticket.TicketRepo;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
@AllArgsConstructor
class TicketInMemoryRepo implements TicketRepo {

    private static final List<Ticket> INSTANCES = Collections.synchronizedList(new ArrayList<>());

    private final EventEmitter eventEmitter;

    @Override
    public Ticket save(final Ticket ticket) {

        INSTANCES.add(ticket);

        eventEmitter.emit(ticket.getEvents());

        return ticket;
    }
}
