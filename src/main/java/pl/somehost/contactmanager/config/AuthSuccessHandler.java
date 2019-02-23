package pl.somehost.contactmanager.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pl.somehost.contactmanager.domain.LoggedUserGetter;
import pl.somehost.contactmanager.security.SecurityUser;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthSuccessHandler.class);

    private final ObjectMapper mapper;

    @Autowired
    LoggedUserGetter loggedUserGetter;

    @Autowired
    public AuthSuccessHandler() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        this.mapper = mappingJackson2HttpMessageConverter.getObjectMapper();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        SecurityUser userDetails = (SecurityUser) authentication.getPrincipal();
        LOGGER.info(userDetails.getUsername() + " got is connected ");

/*        PrintWriter writer = response.getWriter();
        mapper.writeValue(writer, authenticatedUserMasterRoleHeader.getRoles());
        writer.flush();*/
        //response.getWriter().flush();

        response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "User-Role");
        response.addHeader("User-Role", loggedUserGetter.getLoggedUserRoles());
        response.addCookie(new Cookie("USER", loggedUserGetter.getLoggedUserName()));
    }
}

