package kuzmich.hw7.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/home/projects/**").hasAuthority(Role.ADMIN.getName())
                        .requestMatchers("/home/timesheets/**").hasAnyAuthority(Role.ADMIN.getName(), Role.USER.getName())    
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
//                .formLogin(it -> it.loginPage("/my-login.html"))
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
