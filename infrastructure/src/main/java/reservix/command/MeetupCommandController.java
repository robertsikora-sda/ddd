package reservix.command;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import lombok.AllArgsConstructor;
import reservix.meetup.MeetupId;
import reservix.meetup.PlaceId;
import reservix.application.MeetupService;
import reservix.application.ReservationService;
import reservix.reservation.ReservationId;

import javax.validation.Valid;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;

@Validated
@Controller("/v1/commands")
@AllArgsConstructor
class MeetupCommandController {

    private final MeetupService meetupService;
    private final ReservationService reservationService;

    @Post("/meetups/create")
    CreateNewMeetupCommandResult createNewMeetup(@Body @Valid final CreateNewMeetupCommand command) {

        return new CreateNewMeetupCommandResult(

                meetupService.createNewMeetup(command.getMeetupName(),
                command.getMeetupDate(),
                command.getAvailablePlacesNumber()).getMeetupId()

        );
    }

    @Post("/meetups/{meetupId}/reservations/create")
    CreateNewReservationResult createNewReservation(@PathVariable final String meetupId) {

        return new CreateNewReservationResult(

                reservationService.createNewReservation(MeetupId.of(meetupId)).getId()

        );
    }

    @Post("/reservations/{reservationId}/selectPlace")
    HttpResponse selectReservationPlace(@PathVariable final String reservationId, @Body @Valid final ChangeReservationPlacesCommand command) {

        return Match(reservationService.selectReservationPlace(ReservationId.of(reservationId), PlaceId.of(command.getMeetupId(), command.getPlaceNumber()))).of(

                Case($Right($()), HttpResponse.ok()),

                Case($Left($()), HttpResponse.badRequest("Place already selected. Please choose another one")));

    }

    @Post("/reservations/{reservationId}/unselectPlace")
    void unselectReservationPlaces(@PathVariable final String reservationId, @Body @Valid final ChangeReservationPlacesCommand command) {

        reservationService.unselectReservationPlace(ReservationId.of(reservationId), PlaceId.of(command.getMeetupId(), command.getPlaceNumber()));

    }

    @Post("/reservations/{reservationId}/accept")
    void acceptReservation(@PathVariable final String reservationId) {

        reservationService.acceptReservation(ReservationId.of(reservationId));

    }

    @Post("/reservations/{reservationId}/reject")
    void rejectReservation(@PathVariable final String reservationId) {

        reservationService.rejectReservation(ReservationId.of(reservationId));

    }
}
