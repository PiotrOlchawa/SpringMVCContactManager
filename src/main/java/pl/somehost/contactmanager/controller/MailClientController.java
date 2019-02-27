package pl.somehost.contactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.somehost.contactmanager.domain.MailResponse;
import pl.somehost.contactmanager.domain.Message;
import pl.somehost.contactmanager.service.MailContactMessageService;

@RestController
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class MailClientController {

    @Autowired
    MailContactMessageService contactMailService;

    @PostMapping(value = "/mail/{contactId}")
    public MailResponse sendMailToContact(@PathVariable Integer contactId, @RequestBody Message message) {
        contactMailService.sendPersistedMessage(contactId, message);
        return new MailResponse("Massage was send");
    }

}
