package reservix.events;

import lombok.AllArgsConstructor;
import reservix.Event;
import reservix.application.MeetupPlacesPicker;
import reservix.meetup.events.*;
import reservix.application.InvoiceIssuer;
import reservix.projection.MeetupsPlacesProjectionEventListener;
import reservix.projection.MeetupsProjectionEventListener;
import reservix.reservation.events.PlaceReservedEvent;
import reservix.reservation.events.PlaceSelectedEvent;
import reservix.reservation.events.PlaceUnselectedEvent;
import reservix.reservation.events.ReservationAcceptedEvent;
import reservix.ticket.TicketIssuedEvent;
import reservix.application.TicketIssuer;

import javax.inject.Provider;
import javax.inject.Singleton;

import java.util.Collection;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Singleton
@AllArgsConstructor
class SimpleEventsEmitter implements EventEmitter {

    private final MeetupsProjectionEventListener meetupsProjectionEventListener;
    private final MeetupsPlacesProjectionEventListener placesProjectionEventListener;

    private final Provider<TicketIssuer> ticketIssuer;
    private final Provider<InvoiceIssuer> invoiceIssuer;
    private final Provider<MeetupPlacesPicker> pickerProvider;

    @Override
    public void emit(final Collection<Event> events) {
        try {
            routeEvents(events);
        } finally {
            events.clear();
        }
    }

    private void routeEvents(final Collection<Event> events) {
        events.forEach(
                event -> Match(event).of(

                        Case($(instanceOf(MeetupCreatedEvent.class)), e -> {
                            meetupsProjectionEventListener.updateMeetupsProjection(e);
                            pickerProvider.get().initMeetupPlaces(e);
                            return e;
                        }),

                        Case($(instanceOf(MeetupPlaceCreatedEvent.class)), placesProjectionEventListener::initMeetupsPlacesProjection),

                        Case($(instanceOf(PlaceSelectedEvent.class)), e -> {
                            placesProjectionEventListener.selectPlace(e);
                            pickerProvider.get().placeSelected(e);
                            return e;
                        }),

                        Case($(instanceOf(PlaceUnselectedEvent.class)), e -> {
                            placesProjectionEventListener.unselectPlace(e);
                            pickerProvider.get().placeUnselected(e);
                            return e;
                        }),

                        Case($(instanceOf(PlaceReservedEvent.class)), e -> {
                            placesProjectionEventListener.reservePlace(e);
                            pickerProvider.get().placeReserved(e);
                            return e;
                        }),

                        Case($(instanceOf(ReservationAcceptedEvent.class)), ticketIssuer.get()::issueNewTicket),

                        Case($(instanceOf(TicketIssuedEvent.class)), invoiceIssuer.get()::issueNewInvoice),

                        Case($(), e -> new DefaultEventListener().handle((Event) e)))
        );
    }
}
