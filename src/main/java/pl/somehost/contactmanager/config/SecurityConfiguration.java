package pl.somehost.contactmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.somehost.contactmanager.service.UserDetailsServiceImpl;

@EnableWebSecurity(debug = false)
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private HttpAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AuthSuccessHandler authSuccessHandler;
    @Autowired
    private AuthFailureHandler authFailureHandler;
    @Autowired
    private HttpLogoutSuccessHandler logoutSuccessHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //Enable H2 console
        http.authorizeRequests().antMatchers("/").permitAll()
                .and()
                .authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and()
                .headers().frameOptions().disable()
                .and()
                .csrf().ignoringAntMatchers("/h2-console/**");
        //Enable H2 console

        http.cors();

        http.csrf().disable()
                .authorizeRequests()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/front-admin/users/**").authenticated()
                .antMatchers("/front-user/contact/**").authenticated()
                .and()
                .formLogin()
                .successHandler(authSuccessHandler)
                .failureHandler(authFailureHandler)
                .and()
                .httpBasic()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessHandler(logoutSuccessHandler);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
