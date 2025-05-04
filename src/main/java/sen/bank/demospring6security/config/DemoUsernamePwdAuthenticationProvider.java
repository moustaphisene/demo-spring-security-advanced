package sen.bank.demospring6security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("!Prod")
@RequiredArgsConstructor
public class DemoUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    private final DemoUserDetailsService demoUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        UserDetails userDetails = demoUserDetailsService.loadUserByUsername(username);

//        if (passwordEncoder.matches(pwd, userDetails.getPassword())){
//            //Fetch information detail and perform validation to check by condition
     return new UsernamePasswordAuthenticationToken(username, pwd, authentication.getAuthorities());
//        }else{
//            throw new BadCredentialsException("Invalid Password");
//        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
