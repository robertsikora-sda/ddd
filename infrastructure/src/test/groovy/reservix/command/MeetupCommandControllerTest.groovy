package reservix.command

import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import reservix.projection.MeetupPlaceProjection
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
            getAllPlaces(meetup.meetupId).size() == 12
    }

    def "should select few places for some meetup and accept reservation"() {
       given:
            def meetup = createMeetup()
            def randomPlaces = randomPlaces(meetup.meetupId, 10)
       when:
            selectPlaces(meetup.meetupId, randomPlaces)
            unselectPlaces(meetup.meetupId, randomPlaces.dropRight(3) as Set<String>)
            acceptReservation(meetup.meetupId)
       then:
            def allPlaces = getAllPlaces(meetup.meetupId)
            allPlaces.size() == 12
            allPlaces.stream().filter{t -> t.status == MeetupPlaceProjection.Status.RESERVED}.count() == 3
    }

    def createMeetup() {
        restClientV1.createNewMeetup(new CreateNewMeetupCommand(
                "PGE Narodowy",
                LocalDateTime.now().plusMonths(6),
                12))
    }

    def randomPlaces(String meetupId, int placesNumber) {
        RandomPlacesSelector.randomSelector(restClientV1.getAllMeetupsPlaces(meetupId), placesNumber).get()
    }

    def selectPlaces(String meetupId, Set<String> places) {
        restClientV1.selectReservationPlaces(meetupId, new ChangeReservationPlacesCommand(places))
    }

    def unselectPlaces(String meetupId, Set<String> places) {
        restClientV1.unselectReservationPlaces(meetupId, new ChangeReservationPlacesCommand(places))
    }

    def acceptReservation(String meetupId) {
        restClientV1.acceptReservation(meetupId)
    }

    def getAllPlaces(String meetupId) {
        restClientV1.getAllMeetupsPlaces(meetupId)
    }
}
