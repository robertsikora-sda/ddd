package reservix.command;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

@Client("/v1")
interface MeetupRestClientV1 {

    @Post("/commands/meetups")
    CreateNewMeetupCommandResult createNewMeetup(@Body CreateNewMeetupCommand name);

}
