package reservix.ticket;

public interface NotificationSender {

    void sendNotification(String emailAddress, String content);

}
