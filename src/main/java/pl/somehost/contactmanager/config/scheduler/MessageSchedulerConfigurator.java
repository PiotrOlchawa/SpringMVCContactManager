package pl.somehost.contactmanager.config.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.message.Message;
import pl.somehost.contactmanager.domain.message.enums.MessageStatus;

@Component
public class MessageSchedulerConfigurator {

    @Autowired
    private SchedulerConfiguration schedulerConfiguration;

    public void configureMessage(Message message) {

        if (message.getMessageStatus().equals(MessageStatus.NOT_SEND)) {
            message.setSendTrys(schedulerConfiguration.getMessageTrys());
        }
    }
}
