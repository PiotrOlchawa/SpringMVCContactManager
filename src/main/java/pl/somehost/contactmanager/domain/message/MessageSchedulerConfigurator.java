package pl.somehost.contactmanager.domain.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.config.scheduler.SchedulerConfiguration;

@Component
public class MessageSchedulerConfigurator {

    @Autowired
    private SchedulerConfiguration schedulerConfiguration;

    public void configureMessage(Message message) {

        if (message.getMessageStatus().equals(MessageStatus.NOT_SEND)) {
            message.setSendTrays(schedulerConfiguration.getMessageTrays());
        }
    }
}
