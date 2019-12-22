package reservix.meetup;

import java.time.LocalDateTime;

public class MeetupInfoDto {

    private final String name;
    private final LocalDateTime time;

    public MeetupInfoDto(final Meetup meetup) {
        this.name = meetup.getMeetupName().getName();
        this.time = meetup.getTime().getTime();
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
