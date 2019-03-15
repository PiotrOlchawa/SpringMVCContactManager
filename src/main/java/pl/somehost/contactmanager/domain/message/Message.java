package pl.somehost.contactmanager.domain.message;

import pl.somehost.contactmanager.domain.Contact;
import pl.somehost.contactmanager.domain.message.enums.MessageSendMethod;
import pl.somehost.contactmanager.domain.message.enums.MessageStatus;

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
    private MessageStatus messageStatus;
    @Column(name = "messagesendmethod")
    @Enumerated(EnumType.STRING)
    private MessageSendMethod messageSendMethod;
    @Column(name = "sendTrays")
    private Integer sendTrays;
    @ManyToOne
    @JoinColumn(name = "CONTACT_ID")
    private Contact contact;

    public Message() {
    }

    public MessageSendMethod getMessageSendMethod() {
        return messageSendMethod;
    }

    public void setMessageSendMethod(MessageSendMethod messageSendMethod) {
        this.messageSendMethod = messageSendMethod;
    }

    public Integer getSendTrays() {
        return sendTrays;
    }

    public void setSendTrys(Integer sendTrays) {
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
