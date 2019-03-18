package pl.somehost.contactmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ComponentScan(basePackages = "pl.somehost.contactmanager")
public class TestingBeanConfig {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Bean
    MockMvc mockMvc(){
        return MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

}