package pl.somehost.contactmanager.domain;

public class SmsMessage {

    private String phoneNumber;
    private String messageText;

    public SmsMessage(Message message) {
        this.messageText=message.getMessage();
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
