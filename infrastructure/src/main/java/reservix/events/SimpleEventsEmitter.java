package reservix.events;

import lombok.AllArgsConstructor;
import reservix.Event;
import reservix.application.MeetupPlacesPicker;
import reservix.meetup.events.*;
import reservix.application.InvoiceIssuer;
import reservix.projection.MeetupsPlacesProjectionEventListener;
import reservix.projection.MeetupsProjectionEventListener;
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

        events.forEach(
                event -> Match(event).of(

                        Case($(instanceOf(MeetupCreatedEvent.class)), e -> {
                            meetupsProjectionEventListener.updateMeetupsProjection(e);
                            pickerProvider.get().initMeetupPlaces(e);
                            return e;
                        }),

                        Case($(instanceOf(MeetupPlaceCreatedEvent.class)), placesProjectionEventListener::initMeetupsPlacesProjection),

                        Case($(instanceOf(MeetupPlaceSelectedEvent.class)), e -> {
                            placesProjectionEventListener.selectPlace(e);
                            pickerProvider.get().placeSelected(e);
                            return e;
                        }),

                        Case($(instanceOf(MeetupPlaceUnselectedEvent.class)), e -> {
                            placesProjectionEventListener.unselectPlace(e);
                            pickerProvider.get().placeUnselected(e);
                            return e;
                        }),

                        Case($(instanceOf(MeetupPlaceReservedEvent.class)), e -> {
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
