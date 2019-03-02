package pl.somehost.contactmanager.domain.response;

import org.springframework.data.util.Optionals;
import org.springframework.http.HttpHeaders;

import java.net.URI;
import java.util.Optional;

public class ContactManagerResponseHeader {

    private String headerName;
    private String message;
    private URI resourceLocation;

    public ContactManagerResponseHeader(String headerName, String message, URI resourceLocation) {
        this.headerName = headerName;
        this.message = message;
        this.resourceLocation = resourceLocation;
    }

    public ContactManagerResponseHeader(String headerName, String message) {
        this.headerName = headerName;
        this.message = message;
    }

    public URI getResourceLocation() {
        return resourceLocation;
    }

    public HttpHeaders getResponseHeaders() {
        HttpHeaders responseHeaders = new HttpHeaders();
        Optional<URI> resource = Optional.ofNullable(resourceLocation);

        Optionals.ifPresentOrElse(resource, l -> {
            responseHeaders.setLocation(l);
            responseHeaders.set(headerName, message + " " + l.toString());
        }, () -> responseHeaders.set(headerName, message));

        return responseHeaders;
    }


}
