package reservix.meetups;

import java.time.LocalDateTime;

public class MeetupInfoDto {

    private String name;
    private LocalDateTime time;

    public MeetupInfoDto(final Meetup meetup) {
        this.name = meetup.getName().getName();
        this.time = meetup.getTime().getTime();
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
