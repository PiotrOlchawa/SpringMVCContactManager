package pl.somehost.contactmanager.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Optionals;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.message.MailMessage;
import pl.somehost.contactmanager.domain.message.Message;
import pl.somehost.contactmanager.domain.message.enums.MessageSendMethod;
import pl.somehost.contactmanager.domain.message.enums.MessageStatus;
import pl.somehost.contactmanager.mapper.ContactMapper;
import pl.somehost.contactmanager.messageclient.IMessageClient;
import pl.somehost.contactmanager.service.MessageService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class ScheduledByMail {

    @Autowired
    private MessageService messageService;
    @Autowired
    private IMessageClient mailClient;
    @Autowired
    private ContactMapper contactMapper;
    @Autowired
    private SchedulerMessageStatus schedulerMessageStatus;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledByMail.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRateString = "${scheduling.period}")
    public void reportCurrentTime() {

        Optional<List<Message>> optionalMailMessageList =
                messageService.getStatusAndMessageSendMethodAndSendTraysGreater(MessageStatus.NOT_SEND, MessageSendMethod.MESSAGE_BY_MAIL, 0);
        Optionals.ifPresentOrElse(optionalMailMessageList, mailMessagesList -> {
                    mailMessagesList.forEach(l -> {
                        MessageStatus messageStatus = mailClient.sendMessage(contactMapper.mapContactToMail(l.getContact(), new MailMessage(l)));
                        schedulerMessageStatus.setMessageStatus(messageStatus, l);
                    });
                }
                , () -> {
                    LOGGER.info("No any Mail message to send");
                });

        LOGGER.info("Scheduler has finished ", dateFormat.format(new Date()));
    }
}
