package pl.somehost.contactmanager.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class ResourceLocationService {

    public URI getLinkedResourceLocation(String path) {
        return ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path(path)
                .buildAndExpand()
                .toUri();
    }
}
