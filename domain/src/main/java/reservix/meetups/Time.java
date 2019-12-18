package reservix.meetups;

import java.time.LocalDateTime;

class Time {

    private LocalDateTime time;

    Time(final LocalDateTime time) {
        if(time.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("");
        }
        this.time = time;
    }

    LocalDateTime getTime() {
        return time;
    }
}
