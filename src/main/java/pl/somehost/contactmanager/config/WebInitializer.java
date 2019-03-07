package pl.somehost.contactmanager.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.h2.server.web.WebServlet;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;


public class WebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {

        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties("contactmanager.properties");
            String log4jConfigFile = properties.getProperty("log4j.configFile");

            LoggerContext context = (LoggerContext) LogManager.getContext(false);
            context.setConfigLocation(URI.create("classpath:"+log4jConfigFile));
            context.reconfigure();

        } catch (IOException e) {
            e.printStackTrace();
        }

        AnnotationConfigWebApplicationContext applicationContext =
                new AnnotationConfigWebApplicationContext();
        applicationContext.register(SpringContextInitializer.class);

        ServletRegistration.Dynamic dispatcher =
                servletContext.addServlet("front-controller", new DispatcherServlet(applicationContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        ServletRegistration.Dynamic h2Servlet = servletContext
                .addServlet("h2-console", new WebServlet());
        h2Servlet.setLoadOnStartup(2);
        h2Servlet.addMapping("/h2-console/*");
    }
}