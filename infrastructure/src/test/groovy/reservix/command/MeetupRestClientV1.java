package reservix.command;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import reservix.projection.MeetupPlaceProjection;
import reservix.projection.MeetupProjection;

import java.util.List;

@Client("/v1")
interface MeetupRestClientV1 {

    @Post("/commands/meetups")
    CreateNewMeetupCommandResult createNewMeetup(@Body CreateNewMeetupCommand command);

    @Post("/commands/meetups/{meetupId}/reservations/selectPlaces")
    void selectReservationPlaces(String meetupId, @Body ChangeReservationPlacesCommand command);

    @Post("/commands/meetups/{meetupId}/reservations/unselectPlaces")
    void unselectReservationPlaces(String meetupId, @Body ChangeReservationPlacesCommand command);

    @Post("/commands/meetups/{meetupId}/reservations/accept")
    void acceptReservation(String meetupId);

    @Get("/queries/meetups")
    List<MeetupProjection> getAllMeetups();

    @Get("/queries/meetups/{meetupId}/places")
    List<MeetupPlaceProjection> getAllMeetupsPlaces(String meetupId);

}
