package kuzmich.hw7.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/home/**").authenticated()
                        .anyRequest().permitAll())
                .formLogin(Customizer.withDefaults())
//                .formLogin(it -> it.loginPage("/my-login.html"))
                .build();
    }
}
