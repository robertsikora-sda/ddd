package reservix.meetup;

import reservix.MeetupId;

public interface MeetupRepo {

    Meetup get(MeetupId id);

    Meetup save(Meetup meetup);

}
