package reservix.command;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import reservix.projection.MeetupPlacesProjectionDto;
import reservix.projection.MeetupsProjectionDto;

import java.util.List;
import java.util.UUID;

@Client("/v1")
interface MeetupRestClientV1 {

    @Post("/commands/meetups")
    CreateNewMeetupCommandResult createNewMeetup(@Body CreateNewMeetupCommand command);

    @Post("/meetups/{meetupId}/reservations/selectPlaces")
    void selectReservationPlaces(@Body ChangeReservationPlacesCommand command);

    @Post("/meetups/{meetupId}/reservations/unselectPlaces")
    void unselectReservationPlaces(@Body ChangeReservationPlacesCommand command);

    @Post("/meetups/{meetupId}/reservations/accept")
    void acceptReservation();

    @Get("/queries/meetups")
    List<MeetupsProjectionDto> getAllMeetups();

    @Get("/queries/meetups/{meetupId}/places")
    List<MeetupPlacesProjectionDto> getAllMeetupsPlaces(UUID meetupId);

}
