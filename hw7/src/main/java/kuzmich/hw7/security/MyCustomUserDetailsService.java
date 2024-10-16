package kuzmich.hw7.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Component
@RequiredArgsConstructor
public class MyCustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<String> userRoles = userRoleRepository.findByUserId(user.getId());
        return new org.springframework.security.core.userdetails.User(
            user.getLogin(),
            user.getPassword(),
            userRoles.stream().map(SimpleGrantedAuthority::new).toList()
        );
    }
}
