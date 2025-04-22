package sen.bank.demospring6security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sen.bank.demospring6security.entity.Mandate;
import sen.bank.demospring6security.repository.MandateRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DemoUserDetailsService  implements UserDetailsService {

    private final MandateRepository mandateRepository;
    /**
     * @param username the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Mandate mandate = mandateRepository.findByEmail(username).orElseThrow(() -> new
                UsernameNotFoundException("User not found: " + username));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(mandate.getRole()));

        return new User(mandate.getEmail(), mandate.getPwd(), authorities);
    }
}
