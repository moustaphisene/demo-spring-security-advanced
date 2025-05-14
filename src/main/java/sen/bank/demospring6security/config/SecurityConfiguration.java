package sen.bank.demospring6security.config;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import sen.bank.demospring6security.exceptions.CustomAccessDeniedHandler;
import sen.bank.demospring6security.exceptions.CustomBasicAuthenticationEntryPoint;
import sen.bank.demospring6security.filter.CsrfCookieFilter;

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
                .securityContext(contextConfig ->contextConfig.requireExplicitSave(false))
                .sessionManagement(sessionConfig-> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

        //http.sessionManagement(smc-> smc.sessionFixation(sfc->sfc.newSession());
       // http.sessionManagement((session) -> session.sessionFixation((sessionFixation) -> sessionFixation.newSession()));

                .cors(corsConfig ->corsConfig.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200")); //Front Angular UI
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }) )
        // http.sessionManagement(smc-> smc.sessionFixation(sfc->sfc.newSession())
                //.invalidSessionUrl("/sessionInvalid").maximumSessions(3).maxSessionsPreventsLogin(true));
        //Configurations  génération du jeton CSRF pour la toute première fois après l'opération de connexion.
                .csrf(csrfConfig -> csrfConfig
                        .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                        //pour les API publiques nous pouvons ignorer la protection CSRF.
                   .ignoringRequestMatchers( "/contact","/register")
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))

                .addFilterAfter(new CsrfCookieFilter(),BasicAuthenticationFilter.class)
                .requiresChannel(rrc-> rrc.anyRequest().requiresInsecure())  // Only HTTP
        /*http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());*/
        /*http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());*/
                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/yourAccount").hasRole("USER")
//                        .requestMatchers("/yourSold").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers("/yourCredits").hasRole("USER")
//                        .requestMatchers("/yourCard").hasRole("USER")
                .requestMatchers( "/yourAccount").hasAuthority("VIEWACCOUNT")
                .requestMatchers("/yourSold").hasAnyAuthority("VIEWBALANCE","VIEWACCOUNT","VIEWLOANS")
//                .requestMatchers("/yourSold").hasAnyAuthority("VIEWBALANCE", "VIEWACCOUNT")
                .requestMatchers("/yourCredits").hasAnyAuthority("VIEWLOANS","VIEWBALANCE","VIEWACCOUNT")
                .requestMatchers( "/yourCard").hasAnyAuthority("VIEWCARDS","VIEWBALANCE")
                .requestMatchers("/user").authenticated()
//                .requestMatchers("/yourSold", "/yourAccount", "/credits", "/yourCard","/user").authenticated()
                .requestMatchers("/notifications", "/error", "/sessionInvalid","/register","/contact").permitAll());
                //.anyRequest().authenticated());
        /*http.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable());
        http.httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.disable());*/

        http.formLogin(withDefaults());
        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(ehc->ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
        //http.exceptionHandling(ehc->ehc.accessDeniedHandler(new CustomAccessDeniedHandler()).accessDeniedPage("/denied-access")); //Avec Page redirect
        return http.build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(DemoUsernamePwdAuthenticationProvider Provider) {
//        return new ProviderManager(List.of(Provider));
//    }

//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//      UserDetails user =User.withUsername("user").password("{noop}User@12345").authorities("read").build();
//      UserDetails admin = User.withUsername("admin").password("{bcrypt}$2a$12$lpcEhhwcxmRYLccob6tZRuU/1fUuapZhvqb4ojrHq3.Un5usPgoaq").authorities("admin").build();
//      //return new InMemoryUserDetailsManager(user, admin);
//      return new JdbcUserDetailsManager(dataSource);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
        return PasswordEncoderFactories.createDelegatingPasswordEncoder() ;
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
