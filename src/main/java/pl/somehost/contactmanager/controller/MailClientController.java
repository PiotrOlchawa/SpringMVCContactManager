package pl.somehost.contactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.somehost.contactmanager.domain.Message;
import pl.somehost.contactmanager.service.SimpleMailContactMessageService;

@RestController
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class MailClientController {

    @Autowired
    SimpleMailContactMessageService contactMailService;

    @PostMapping(value = "/mail/{contactId}")
    public void sendMailToContact(@PathVariable Integer contactId, @RequestBody Message message) {
        contactMailService.send(contactId, message);
    }

}
