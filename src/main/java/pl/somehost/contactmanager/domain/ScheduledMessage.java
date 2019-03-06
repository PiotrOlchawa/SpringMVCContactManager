package pl.somehost.contactmanager.domain;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Optionals;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.client.sms.SmsClient;
import pl.somehost.contactmanager.service.MessageService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class ScheduledMessage {

    @Autowired
    private MessageService messageService;
    @Autowired
    private SmsClient smsClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledMessage.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRateString = "${scheduling.period}")
    public void reportCurrentTime() {
        Optional<List<Message>> optionalMessageList = Optional
                .ofNullable(messageService.getMessageWithStatusAndNumberOfTrys(MessageStatus.NOT_SEND,0));

        Optionals.ifPresentOrElse(optionalMessageList, messagesList -> {
                    messagesList.stream().forEach(l -> {
                        MessageStatus messageStatus = smsClient.sendMessage(new SmsMessage(l, l.getContact().getTelephone()));
                        if (messageStatus.equals(MessageStatus.SEND)) {
                            l.setMessageStatus(MessageStatus.SEND);
                            messageService.saveMessage(l);
                        LOGGER.info("Message was sucessfuly send");
                        } else {
                            l.setSendTrays(l.getSendTrays() - 1);
                            LOGGER.info("Message was not send remaining trys " + l.getSendTrays());
                        }
                    });
                }
                , () -> {
                    LOGGER.info("No any message to send");
                });
        LOGGER.info("The time is now {}", dateFormat.format(new Date()));
    }
}
