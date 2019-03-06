package pl.somehost.contactmanager.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Optionals;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.client.mail.MailClient;
import pl.somehost.contactmanager.client.sms.SmsClient;
import pl.somehost.contactmanager.domain.message.*;
import pl.somehost.contactmanager.mapper.ContactToMailMapper;
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
    @Autowired
    private MailClient mailClient;
    @Autowired
    private ContactToMailMapper contactToMailMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledMessage.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRateString = "${scheduling.period}")
    public void reportCurrentTime() {
        Optional<List<Message>> optionalSmsMessageList =
                messageService.getStatusAndMessageSendMethodAndSendTraysGreater(MessageStatus.NOT_SEND, MessageSendMethod.MESSAGE_BY_SMS, 0);
        Optionals.ifPresentOrElse(optionalSmsMessageList, smsMessagesList -> {
                    smsMessagesList.forEach(l -> {
                        MessageStatus messageStatus = smsClient.sendMail(new SmsMessage(l, l.getContact().getTelephone()));
                        if (messageStatus.equals(MessageStatus.SEND)) {
                            l.setMessageStatus(MessageStatus.SEND);
                            messageService.saveMessage(l);
                            LOGGER.info("Sms Message was successfully send");
                        } else {
                            l.setSendTrays(l.getSendTrays() - 1);
                            messageService.saveMessage(l);
                            LOGGER.info("Sms Message was not send remaining trys " + l.getSendTrays());
                        }
                    });
                }
                , () -> {
                    LOGGER.info("No any Sms message to send");
                });

        Optional<List<Message>> optionalMailMessageList =
                messageService.getStatusAndMessageSendMethodAndSendTraysGreater(MessageStatus.NOT_SEND, MessageSendMethod.MESSAGE_BY_MAIL, 0);
        Optionals.ifPresentOrElse(optionalMailMessageList, mailMessagesList -> {
                    mailMessagesList.forEach(l -> {
                        MessageStatus messageStatus = mailClient.sendMail(contactToMailMapper.mapContactDtoToMail(l.getContact(), new MailMessage(l)));
                        if (messageStatus.equals(MessageStatus.SEND)) {
                            l.setMessageStatus(MessageStatus.SEND);
                            messageService.saveMessage(l);
                            LOGGER.info("Mail Message was successfully send");
                        } else {
                            l.setSendTrays(l.getSendTrays() - 1);
                            messageService.saveMessage(l);
                            LOGGER.info("Mail Message was not send remaining trys " + l.getSendTrays());
                        }
                    });
                }
                , () -> {
                    LOGGER.info("No any Mail message to send");
                });

        LOGGER.info("Scheduler has finished ", dateFormat.format(new Date()));
    }
}
