package reservix.meetup;

import reservix.reservation.PlaceId;

public interface MeetupPlaceRepository {

    MeetupPlace get(PlaceId id);

    MeetupPlace save(MeetupPlace place);

}
