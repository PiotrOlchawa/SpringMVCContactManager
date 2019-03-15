package pl.somehost.contactmanager.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(AuthFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        LOGGER.info("Invalid password or username passed for user " + request.getParameter("username"));

        PrintWriter writer = response.getWriter();
        writer.write(exception.getMessage());
        writer.flush();
    }
}