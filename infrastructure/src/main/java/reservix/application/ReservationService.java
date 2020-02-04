package reservix.application;

import lombok.AllArgsConstructor;
import reservix.meetup.MeetupId;
import reservix.reservation.PlaceId;
import reservix.reservation.*;
import reservix.user.LoggedUserSupplier;

import javax.inject.Singleton;

@Singleton
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PlaceAvailabilityValidator placeChecker;

    public ReservationCreated createNewReservation(final MeetupId meetupId) {

        final Reservation reservation = reservationRepository.save(

                new Reservation(meetupId, LoggedUserSupplier.loggedUser())

        );

        return new ReservationCreated(reservation.getId());
    }

    public PlaceSelectionOutcome selectReservationPlace(final ReservationId reservationId, final PlaceId placeId) {
        final Reservation reservation = reservationRepository.getWithAccessCheck(reservationId);

        final PlaceSelectionOutcome outcome = reservation.selectPlace(placeId, placeChecker);

        reservationRepository.save(reservation);

        return outcome;
    }

    public PlaceUnselected unselectReservationMeetupPlace(final ReservationId reservationId, final PlaceId placeId) {
        final Reservation reservation = reservationRepository.getWithAccessCheck(reservationId);

        final PlaceUnselected placeUnselected = reservation.unselectPlace(placeId);

        reservationRepository.save(reservation);

        return placeUnselected;
    }

    public ReservationAccepted acceptReservation(final ReservationId reservationId) {
        final Reservation reservation = reservationRepository.getWithAccessCheck(reservationId);

        final ReservationAccepted reservationAccepted = reservation.acceptReservation();

        reservationRepository.save(reservation);

        return reservationAccepted;
    }

    public ReservationRejected rejectReservation(final ReservationId reservationId) {
        final Reservation reservation = reservationRepository.getWithAccessCheck(reservationId);

        final ReservationRejected reservationRejected = reservation.rejectReservation();

        reservationRepository.save(reservation);

        return reservationRejected;
    }

}
