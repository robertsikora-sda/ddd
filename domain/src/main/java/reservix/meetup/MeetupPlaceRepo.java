package reservix.meetup;

import reservix.PlaceId;

public interface MeetupPlaceRepo {

    MeetupPlace get(PlaceId id);

    MeetupPlace save(MeetupPlace place);

}
