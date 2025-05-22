package sen.bank.demospring6security.config;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
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
import sen.bank.demospring6security.filter.*;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("prod")
public class SecurityProdConfiguration {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();

        http
                .sessionManagement(sessionConfig-> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsConfig ->corsConfig.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("https://localhost:4200")); //Front Angular UI
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        //JWT for expose headers
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }));
        http.csrf(csrfConfig -> csrfConfig
                .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                .ignoringRequestMatchers("/contact", "/register","/apiLogin")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(),BasicAuthenticationFilter.class)
                .addFilterBefore(new RequestValidationBeforeFilter(),BasicAuthenticationFilter.class)
                //.addFilterAfter(new RequestLoggingFilter(),BasicAuthenticationFilter.class).addFilterBefore(new RequestValidationBeforeFilter(),BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthoritiesLoggingAfterFilter(),BasicAuthenticationFilter.class)
                .addFilterAt(new AuthoritiesLoggingAtFilter(),BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(),BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(),BasicAuthenticationFilter.class);

                //.sessionManagement(smc-> smc.invalidSessionUrl("/sessionInvalid").maximumSessions(1).maxSessionsPreventsLogin(true));
        http.requiresChannel(rcc-> rcc.anyRequest().requiresSecure()); //HTTPS
        //http.csrf(csrfConfig -> csrfConfig.disable());
        /*http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());*/
        /*http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());*/
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/yourAccount").hasRole("USER")
                .requestMatchers("/yourSold").hasAnyRole("USER", "ADMIN")
                //.requestMatchers("/yourCredits").hasRole("USER")
                .requestMatchers("/yourCredits").authenticated()
                .requestMatchers("/yourCard").hasRole("USER")
//                .requestMatchers( "/yourAccount").hasAuthority("VIEWACCOUNT")
//                .requestMatchers("/yourSold").hasAnyAuthority("VIEWBALANCE","VIEWACCOUNT","VIEWLOANS")
//                .requestMatchers("/yourSold").hasAnyAuthority("VIEWBALANCE", "VIEWACCOUNT")
//                .requestMatchers("/yourCredits").hasAnyAuthority("VIEWLOANS","VIEWBALANCE","VIEWACCOUNT")
//                .requestMatchers( "/yourCard").hasAnyAuthority("VIEWCARDS","VIEWBALANCE")
                .requestMatchers("/user").authenticated()
                //.requestMatchers("/user").authenticated()
                //.requestMatchers("/yourSold", "/yourAccount", "/credits", "/yourCard","/user").authenticated()
                .requestMatchers("/notifications", "/error","/sessionInvalid","/apiLogin").permitAll()
                .anyRequest().authenticated());
        /*http.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable());
        http.httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.disable());*/
        http.formLogin(withDefaults());
        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(ehc->ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
       // http.exceptionHandling(ehc->ehc.accessDeniedHandler(new CustomAccessDeniedHandler()).accessDeniedPage("/denied-access"));

        //http.exceptionHandling(ehc -> ehc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));// config global
        return http.build();
    }

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

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder, DemoUserDetailsService demoUserDetailsService) {
        DemoProdUsernamePwdAuthenticationProvider authenticationProvider =
                new DemoProdUsernamePwdAuthenticationProvider(demoUserDetailsService,passwordEncoder);
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;

    }
}
