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
            def meetup = createMeetup()
        then:
            meetup
            restClientV1.getAllMeetups().size() == 1
            restClientV1.getAllMeetupsPlaces(meetup.meetupId).size() == 10_000
    }

    def "should select few places for some meetup and accept reservation"() {
      // given:
      //      def meetup = createMeetup()
      //  when:

    }

    def createMeetup() {
        restClientV1.createNewMeetup(new CreateNewMeetupCommand(
                "PGE Narodowy",
                LocalDateTime.now().plusMonths(6),
                10_000))
    }
}
