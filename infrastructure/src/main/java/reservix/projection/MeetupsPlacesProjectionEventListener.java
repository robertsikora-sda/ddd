package reservix.projection;

import io.vavr.collection.Set;
import lombok.AllArgsConstructor;
import reservix.meetup.events.MeetupCreatedEvent;
import reservix.reservation.events.PlaceSelectedEvent;
import reservix.reservation.events.PlaceUnselectedEvent;
import reservix.reservation.events.ReservationAcceptedEvent;
import reservix.reservation.events.ReservationRejectedEvent;

import javax.inject.Singleton;

import static reservix.projection.MeetupPlacesProjectionDto.Status.*;

@Singleton
@AllArgsConstructor
public class MeetupsPlacesProjectionEventListener {

    private final MeetupsPlacesProjectionInMemoryRepo projectionRepo;

    public Set<MeetupPlacesProjectionDto> onMeetupCreatedEvent(final MeetupCreatedEvent event) {

        return event.getPlaces().map(placeId ->

            projectionRepo.save(

                    new MeetupPlacesProjectionDto(
                            placeId.getMeetupId().getId().toString(),
                            placeId.getPlaceNumber().getNumber(),
                            FREE)
            )

        );

    }

    public PlaceSelectedEvent onSelectPlace(final PlaceSelectedEvent event) {

        final MeetupPlacesProjectionDto meetupPlace = projectionRepo.findById(event.getPlaceId());
        meetupPlace.setStatus(SELECTED);

        projectionRepo.save(meetupPlace);

        return event;
    }

    public PlaceUnselectedEvent onUnselectPlace(final PlaceUnselectedEvent event) {

        final MeetupPlacesProjectionDto meetupPlace = projectionRepo.findById(event.getPlaceId());
        meetupPlace.setStatus(FREE);

        projectionRepo.save(meetupPlace);

        return event;
    }

    public Set<MeetupPlacesProjectionDto> onAcceptReservation(final ReservationAcceptedEvent event) {

        return event.getReservedPlaces().map(placeId -> {

                final MeetupPlacesProjectionDto meetupPlace = projectionRepo.findById(placeId);
                meetupPlace.setStatus(RESERVED);
                return projectionRepo.save(meetupPlace);
            }
        );
    }

    public Set<MeetupPlacesProjectionDto> onRejectReservation(final ReservationRejectedEvent event) {

        return event.getReservedPlaces().map(placeId -> {

                    final MeetupPlacesProjectionDto meetupPlace = projectionRepo.findById(placeId);
                    meetupPlace.setStatus(FREE);
                    return projectionRepo.save(meetupPlace);
                }
        );
    }
}
