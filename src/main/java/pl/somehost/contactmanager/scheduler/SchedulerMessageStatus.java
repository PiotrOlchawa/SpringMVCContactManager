package pl.somehost.contactmanager.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.message.Message;
import pl.somehost.contactmanager.domain.message.definitions.MessageStatus;
import pl.somehost.contactmanager.service.MessageService;

@Component
@Scope("prototype")
public class SchedulerMessageStatus {

    private final MessageService messageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerMessageStatus.class);

    @Autowired
    public SchedulerMessageStatus(MessageService messageService) {
        this.messageService = messageService;
    }

    public void setMessageStatus(MessageStatus messageStatus, Message message) {

        if (messageStatus.equals(MessageStatus.SEND)) {
            message.setMessageStatus(MessageStatus.SEND);
            messageService.saveMessage(message);
            LOGGER.info(message.getMessageSendMethod() + " message was successfully send");
        } else {
            message.setSendTrys(message.getSendTrays() - 1);
            messageService.saveMessage(message);
            LOGGER.info(message.getMessageSendMethod() + " message was not send remaining trys " + message.getSendTrays());
        }
    }
}
