package pl.somehost.contactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.somehost.contactmanager.domain.message.Message;
import pl.somehost.contactmanager.domain.response.ContactManagerResponseMessage;
import pl.somehost.contactmanager.facade.MessageFacade;

@RestController
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class MailClientController {

    @Autowired //Autowired by name
    MessageFacade mailMessageFacade;
    @Autowired
    ContactManagerResponseMessage contactManagerResponseMessage;

    @PostMapping(value = "/mail/{contactId}")
    public ResponseEntity<ContactManagerResponseMessage> sendMailToContact(@PathVariable Integer contactId, @RequestBody Message message) {
        return mailMessageFacade.sendPersistedMessage(contactId, message);
        //contactManagerResponseMessage.setMessage("Massage was send");
        //return contactManagerResponseMessage;
    }

}
