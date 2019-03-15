package pl.somehost.contactmanager.domain.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.service.ResourceLocationService;

import java.net.URI;

@Component
@Scope("prototype")
public class CMResponseEntityPreparator {

    @Autowired
    private ContactManagerResponseMessage contactManagerResponseMessage;
    @Autowired
    private ResourceLocationService resourceLocationService;

    public ResponseEntity<ContactManagerResponseMessage> getResponseEntity(String responseMessage, String resourceLocationPath, HttpStatus httpStatus) {

        contactManagerResponseMessage.setMessage(responseMessage);
        URI resourceLocation = resourceLocationService.getLinkedResourceLocation(resourceLocationPath);
        ContactManagerResponseHeader contactManagerResponseHeader =
                new ContactManagerResponseHeader("CMResponceHeader", "Link to updated resource", resourceLocation);
        return new ResponseEntity<>(contactManagerResponseMessage, contactManagerResponseHeader.getResponseHeaders(), httpStatus);

    }

    public ResponseEntity<ContactManagerResponseMessage> getResponseEntity(String responseMessage, HttpStatus httpStatus) {
        contactManagerResponseMessage.setMessage(responseMessage);
        return new ResponseEntity<>(contactManagerResponseMessage, httpStatus);
    }
}
