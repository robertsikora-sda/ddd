package reservix.projection;

import io.vavr.collection.Set;
import lombok.AllArgsConstructor;
import reservix.meetup.events.MeetupCreatedEvent;
import reservix.reservation.events.PlaceSelectedEvent;
import reservix.reservation.events.PlaceUnselectedEvent;
import reservix.reservation.events.ReservationAcceptedEvent;
import reservix.reservation.events.ReservationRejectedEvent;

import javax.inject.Singleton;

import static reservix.projection.MeetupPlaceProjection.Status.*;

@Singleton
@AllArgsConstructor
public class MeetupPlaceProjectionEventListener {

    private final MeetupPlaceProjectionInMemoryRepo projectionRepo;

    public Set<MeetupPlaceProjection> onMeetupCreatedEvent(final MeetupCreatedEvent event) {

        return event.getPlaces().map(placeId ->

            projectionRepo.save(

                    new MeetupPlaceProjection(
                            placeId.getMeetupId().getId().toString(),
                            placeId.getPlaceNumber().getNumber(),
                            FREE)
            )

        );

    }

    public PlaceSelectedEvent onSelectPlace(final PlaceSelectedEvent event) {

        final MeetupPlaceProjection meetupPlace = projectionRepo.findById(event.getPlaceId());
        meetupPlace.setStatus(SELECTED);

        projectionRepo.save(meetupPlace);

        return event;
    }

    public PlaceUnselectedEvent onUnselectPlace(final PlaceUnselectedEvent event) {

        final MeetupPlaceProjection meetupPlace = projectionRepo.findById(event.getPlaceId());
        meetupPlace.setStatus(FREE);

        projectionRepo.save(meetupPlace);

        return event;
    }

    public Set<MeetupPlaceProjection> onAcceptReservation(final ReservationAcceptedEvent event) {

        return event.getReservedPlaces().map(placeId -> {

                final MeetupPlaceProjection meetupPlace = projectionRepo.findById(placeId);
                meetupPlace.setStatus(RESERVED);
                return projectionRepo.save(meetupPlace);
            }
        );
    }

    public Set<MeetupPlaceProjection> onRejectReservation(final ReservationRejectedEvent event) {

        return event.getReservedPlaces().map(placeId -> {

                    final MeetupPlaceProjection meetupPlace = projectionRepo.findById(placeId);
                    meetupPlace.setStatus(FREE);
                    return projectionRepo.save(meetupPlace);
                }
        );
    }
}
