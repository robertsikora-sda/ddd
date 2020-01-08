package reservix.command

import reservix.projection.MeetupPlacesProjectionDto
import spock.lang.Specification

class RandomPlacesSelectorTest extends Specification {

    def "Name"() {
        when:
        def s = RandomPlacesSelector.
                randomSelector(List.of(new MeetupPlacesProjectionDto(), new MeetupPlacesProjectionDto()), 2)
        then:
           s.get()
    }
}
