package reservix.application;

import reservix.ticket.NotificationSender;

import javax.inject.Singleton;

import static io.vavr.API.TODO;

@Singleton
class NotificationSenderService implements NotificationSender {

    @Override
    public void sendNotification(String emailAddress, String content) {
        TODO();
    }

}
