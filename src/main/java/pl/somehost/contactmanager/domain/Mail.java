package pl.somehost.contactmanager.domain;

public class Mail extends Message  {

        private String mailTo;
        private String toCC;
        private String subject;

    public Mail(Message message) {
        super(message.getMessage());
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
