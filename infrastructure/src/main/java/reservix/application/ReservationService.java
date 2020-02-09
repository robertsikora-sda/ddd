package reservix.application;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import reservix.meetup.MeetupId;
import reservix.reservation.PlaceAvailabilityValidator;
import reservix.meetup.PlaceId;
import reservix.reservation.PlaceUnselected;
import reservix.reservation.Reservation;
import reservix.reservation.ReservationAccepted;
import reservix.reservation.ReservationCreated;
import reservix.reservation.ReservationId;
import reservix.reservation.ReservationRejected;
import reservix.reservation.ReservationRepository;
import reservix.user.LoggedUserSupplier;

import javax.inject.Singleton;

import static reservix.reservation.PlaceSelectionOutcome.PlaceOccupiedError;
import static reservix.reservation.PlaceSelectionOutcome.PlaceSelected;

@Singleton
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PlaceAvailabilityValidator placeChecker;

    public ReservationCreated createNewReservation(final MeetupId meetupId) {

        final Reservation createdReservation = reservationRepository.save(

                Reservation.of(meetupId, LoggedUserSupplier.loggedUser())

        );

        return new ReservationCreated(createdReservation.getId());
    }

    public Either<PlaceOccupiedError, PlaceSelected> selectReservationPlace(final ReservationId reservationId, final PlaceId placeId) {
        final Reservation reservation = reservationRepository.getWithAccessCheck(reservationId);

        return reservation.selectPlace(placeId, placeChecker)
                .peek(t -> reservationRepository.save(reservation));
    }

    public PlaceUnselected unselectReservationPlace(final ReservationId reservationId, final PlaceId placeId) {
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
