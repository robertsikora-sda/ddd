package reservix.events;

import io.vavr.collection.Seq;
import lombok.AllArgsConstructor;
import reservix.Event;
import reservix.application.MeetupPlacesManager;
import reservix.meetup.events.*;
import reservix.application.InvoiceIssuer;
import reservix.projection.MeetupPlaceProjectionEventListener;
import reservix.projection.MeetupProjectionEventListener;
import reservix.reservation.events.PlaceSelectedEvent;
import reservix.reservation.events.PlaceUnselectedEvent;
import reservix.reservation.events.ReservationAcceptedEvent;
import reservix.reservation.events.ReservationRejectedEvent;
import reservix.ticket.TicketIssuedEvent;
import reservix.application.TicketIssuer;

import javax.inject.Provider;
import javax.inject.Singleton;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Singleton
@AllArgsConstructor
class SimpleEventsEmitter implements EventEmitter {

    private final Provider<TicketIssuer> ticketIssuer;
    private final Provider<InvoiceIssuer> invoiceIssuer;
    private final Provider<MeetupPlacesManager> meetupPlacesManager;

    private final MeetupProjectionEventListener meetupProjectionEventListener;
    private final MeetupPlaceProjectionEventListener placesProjectionEventListener;

    @Override
    public void emit(final Seq<Event> events) {
        try {
            routeEvents(events);
        } finally {
            //events.r
        }
    }

    private void routeEvents(final Seq<Event> events) {
        events.forEach(event -> Match(event).of(

                        Case($(instanceOf(MeetupCreatedEvent.class)), e -> {
                            meetupProjectionEventListener.onMeetupCreatedEvent(e);
                            return e;
                        }),

                        Case($(instanceOf(PlaceSelectedEvent.class)), e -> {
                            meetupPlacesManager.get().placeSelected(e);

                            placesProjectionEventListener.onSelectPlace(e);

                            return e;
                        }),

                        Case($(instanceOf(PlaceUnselectedEvent.class)), e -> {
                            meetupPlacesManager.get().placeUnselected(e);

                            placesProjectionEventListener.onUnselectPlace(e);

                            return e;
                        }),

                        Case($(instanceOf(ReservationAcceptedEvent.class)), e -> {
                            meetupPlacesManager.get().reservationAccepted(e);

                            placesProjectionEventListener.onAcceptReservation(e);

                            return e;
                        }),

                        Case($(instanceOf(ReservationRejectedEvent.class)), e -> {
                            meetupPlacesManager.get().reservationRejected(e);

                            placesProjectionEventListener.onRejectReservation(e);

                            return e;
                        }),

                        Case($(instanceOf(ReservationAcceptedEvent.class)), ticketIssuer.get()::issueNewTicket),

                        Case($(instanceOf(TicketIssuedEvent.class)), invoiceIssuer.get()::issueNewInvoice),

                        Case($(), e -> new DefaultEventListener().handle((Event) e)))
        );
    }
}
