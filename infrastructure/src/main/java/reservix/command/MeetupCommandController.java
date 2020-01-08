package reservix.command;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import lombok.AllArgsConstructor;
import reservix.MeetupId;
import reservix.application.MeetupService;

import javax.validation.Valid;

@Validated
@Controller("/v1/commands")
@AllArgsConstructor
class MeetupCommandController {

    private final MeetupService meetupService;

    @Post("/meetups")
    CreateNewMeetupCommandResult createNewMeetup(@Body @Valid final CreateNewMeetupCommand command) {

        final MeetupId meetupId = meetupService.createNewMeetup(command.getMeetupName(),
                                      command.getMeetupDate(),
                                      command.getAvailablePlacesNumber());

        return new CreateNewMeetupCommandResult(meetupId);
    }

    @Post("/meetups/{meetupId}/reservations/selectPlaces")
    void selectReservationPlaces(@PathVariable final String meetupId, @Body @Valid final ChangeReservationPlacesCommand command) {

        meetupService.selectReservationPlaces(MeetupId.of(meetupId), command.toPlaces());

    }

    @Post("/meetups/{meetupId}/reservations/unselectPlaces")
    void unselectReservationPlaces(@PathVariable final String meetupId, @Body @Valid final ChangeReservationPlacesCommand command) {

        meetupService.unselectReservationMeetupPlaces(MeetupId.of(meetupId), command.toPlaces());

    }

    @Post("/meetups/{meetupId}/reservations/accept")
    void acceptReservation(@PathVariable final String meetupId) {

        meetupService.acceptReservation(MeetupId.of(meetupId));

    }
}
