package reservix.meetup;

public interface MeetupRepository {

    Meetup get(MeetupId id);

    Meetup save(Meetup meetup);

}
