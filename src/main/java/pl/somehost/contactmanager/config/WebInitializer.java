package pl.somehost.contactmanager.config;

import org.h2.server.web.WebServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;


public class WebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {

        AnnotationConfigWebApplicationContext applicationContext =
                new AnnotationConfigWebApplicationContext();
        applicationContext.register(SpringContextInitializer.class);

        ServletRegistration.Dynamic dynamic =
                servletContext.addServlet("front-controller", new DispatcherServlet(applicationContext));
        dynamic.setLoadOnStartup(1);
        dynamic.addMapping("/");

        ServletRegistration.Dynamic h2Servlet = servletContext
                .addServlet("h2-console", new WebServlet());
        h2Servlet.setLoadOnStartup(2);
        h2Servlet.addMapping("/h2-console/*");
    }
}