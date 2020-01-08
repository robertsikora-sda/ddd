package reservix.application;

import lombok.extern.slf4j.Slf4j;
import reservix.ticket.NotificationSender;

import javax.inject.Singleton;

import static io.vavr.API.TODO;

@Slf4j
@Singleton
class NotificationSenderService implements NotificationSender {

    @Override
    public void sendNotification(String emailAddress, String content) {
        log.info("The notification {} has been sent to recipient {}", content, emailAddress);
    }

}
