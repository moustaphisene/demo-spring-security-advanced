package sen.bank.demospring6security.config;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import sen.bank.demospring6security.exceptions.CustomAccessDeniedHandler;
import sen.bank.demospring6security.exceptions.CustomBasicAuthenticationEntryPoint;
import sen.bank.demospring6security.filter.*;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("!prod")
@RequiredArgsConstructor
public class SecurityConfiguration {

    //private final AuthenticationManager authenticationManager;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {


        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();

        http
                .sessionManagement(sessionConfig-> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .cors(corsConfig ->corsConfig.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200")); //Front Angular UI
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        //JWT for expose headers
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }) )
                .csrf(csrfConfig -> csrfConfig
                        .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                        //pour les API publiques nous pouvons ignorer la protection CSRF.
                   .ignoringRequestMatchers( "/contact","/register","/apiLogin")
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(),BasicAuthenticationFilter.class)
                .requiresChannel(rrc-> rrc.anyRequest().requiresInsecure())  // Only HTTP
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/yourAccount").hasRole("USER")
                        .requestMatchers("/yourSold").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/yourCredits").authenticated()
                        .requestMatchers("/yourCard").hasRole("USER")
                .requestMatchers("/user").authenticated()

                .requestMatchers("/notifications", "/error", "/sessionInvalid","/register","/contact","/apiLogin").permitAll());

        http.formLogin(withDefaults());
        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(ehc->ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));

        return http.build();
    }





}
