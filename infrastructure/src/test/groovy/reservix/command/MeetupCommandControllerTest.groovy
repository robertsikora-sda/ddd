package reservix.command

import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDateTime

class MeetupCommandControllerTest extends Specification {

    @Shared
    @AutoCleanup
    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)

    @Shared
    MeetupRestClientV1 restClientV1 = embeddedServer
            .applicationContext
            .getBean(MeetupRestClientV1)


    def "should create new meetup and initialize all places"() {
        when:
        def result = restClientV1.createNewMeetup(new CreateNewMeetupCommand(
                "PGE Narodowy",
                LocalDateTime.now().plusMonths(6),
                10_000
        ))
        then:
        result
        result.meetupId

    }
}
