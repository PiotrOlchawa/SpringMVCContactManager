package pl.somehost.contactmanager.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.config.scheduler.SchedulerConfig;

@Component
public class MessageSchedulerConfigurator {

    @Autowired
    private SchedulerConfig schedulerConfig;

    public void configureMessage(Message message) {

        message.setSendTrays(schedulerConfig.getMessageTrays());

        if (message.getMessageStatus().equals(MessageStatus.NOT_SEND)) {
            message.setSendTrays(schedulerConfig.getMessageTrays());
        }
    }
}
