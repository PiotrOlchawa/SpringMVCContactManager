package pl.somehost.contactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.somehost.contactmanager.domain.Message;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseMessage;
import pl.somehost.contactmanager.service.MailContactMessageService;

@RestController
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class MailClientController {

    @Autowired
    MailContactMessageService contactMailService;
    @Autowired
    ContactManagerResponseMessage contactManagerResponseMessage;

    @PostMapping(value = "/mail/{contactId}")
    public ContactManagerResponseMessage sendMailToContact(@PathVariable Integer contactId, @RequestBody Message message) {
        contactMailService.sendPersistedMessage(contactId, message);
        contactManagerResponseMessage.setMessage("Massage was send");
        return contactManagerResponseMessage;
    }

}
