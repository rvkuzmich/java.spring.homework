package kuzmich.hw9.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("MY_ROLE_PREFIX_");
    }

    @Bean
    SecurityFilterChain noSecurity(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(it -> it.anyRequest().permitAll()).build();
    }

//    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/home/projects/**").hasAuthority("admin")
//                        .requestMatchers.("/home/projects/**").hasRole("admin") //MY_ROLE_PREFIX_admin
                        .requestMatchers("/home/timesheets/**").hasAnyAuthority("admin", "user")
                        .anyRequest().hasAuthority("rest")
                )
                .formLogin(Customizer.withDefaults())
//                .formLogin(it -> it.loginPage("/my-login.html"))
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
