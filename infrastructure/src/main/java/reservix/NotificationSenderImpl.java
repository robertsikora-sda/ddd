package reservix;

import reservix.ticket.NotificationSender;

import javax.inject.Singleton;

import static io.vavr.API.TODO;

@Singleton
public class NotificationSenderImpl implements NotificationSender {

    @Override
    public void sendNotification(String emailAddress, String content) {
        TODO();
    }

}
