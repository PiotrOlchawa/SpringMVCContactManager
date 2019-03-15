package pl.somehost.contactmanager.domain.message;

public class MailMessage {

    private String mailTo;
    private String toCC;
    private String subject;
    private String message;

    public MailMessage(Message message) {
        this.message = message.getMessage();
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getToCC() {
        return toCC;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToCC(String toCC) {
        this.toCC = toCC;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
