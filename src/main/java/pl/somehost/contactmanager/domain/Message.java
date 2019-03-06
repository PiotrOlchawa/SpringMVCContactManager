package pl.somehost.contactmanager.domain;

import javax.persistence.*;

@Entity(name = "Message")
@Table(name = "Message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "message")
    protected String message;
    @Column(name = "messageStatus")
    @Enumerated(EnumType.STRING)
    protected MessageStatus messageStatus;
    @Column(name = "sendTrays")
    protected Integer sendTrays;
    @ManyToOne
    @JoinColumn(name = "CONTACT_ID")
    private Contact contact;

    public Message() {
    }

    public Integer getSendTrays() {
        return sendTrays;
    }

    public void setSendTrays(Integer sendTrays) {
        this.sendTrays = sendTrays;
    }

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }
}
