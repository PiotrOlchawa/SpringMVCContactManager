package pl.somehost.contactmanager.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Optionals;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.message.Message;
import pl.somehost.contactmanager.domain.message.definitions.MessageSendMethod;
import pl.somehost.contactmanager.domain.message.definitions.MessageStatus;
import pl.somehost.contactmanager.mapper.MessageMapper;
import pl.somehost.contactmanager.messageclient.IMessageClient;
import pl.somehost.contactmanager.service.MessageService;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Component
public class ScheduledBySms {

    private final MessageService messageService;
    private final IMessageClient smsGatewayClient;
    private final MessageMapper messageMapper;
    private final SchedulerMessageStatus schedulerMessageStatus;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledBySms.class);

    @Autowired
    public ScheduledBySms(MessageService messageService, IMessageClient smsGatewayClient, MessageMapper messageMapper, SchedulerMessageStatus schedulerMessageStatus) {
        this.messageService = messageService;
        this.smsGatewayClient = smsGatewayClient;
        this.messageMapper = messageMapper;
        this.schedulerMessageStatus = schedulerMessageStatus;
    }

    @Scheduled(fixedRateString = "${scheduling.period}")
    public void reportCurrentTime() {
        Optional<List<Message>> optionalSmsMessageList =
                messageService.getStatusAndMessageSendMethodAndSendTraysGreater(MessageStatus.NOT_SEND, MessageSendMethod.MESSAGE_BY_SMS, 0);

        Optionals.ifPresentOrElse(optionalSmsMessageList, smsMessagesList -> {
                    smsMessagesList.forEach(l -> {
                        MessageStatus messageStatus = smsGatewayClient.sendMessage(messageMapper.mapMessageToSmsMessage(l));
                        schedulerMessageStatus.setMessageStatus(messageStatus, l);
                    });
                }
                , () -> {
                    LOGGER.info("No any Sms message to send");
                });
    }
}

