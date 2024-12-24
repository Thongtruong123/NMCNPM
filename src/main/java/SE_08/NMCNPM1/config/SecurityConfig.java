package SE_08.NMCNPM1.config;

import SE_08.NMCNPM1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().and()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/index", "/css/**", "/js/**", "/image/**").permitAll()
                        .requestMatchers("/quan-ly-tai-khoan").hasRole("ADMIN")
                        .requestMatchers("/create", "/edit", "/delete", "/thong-tin-nhan-khau/create", "/thong-tin-nhan-khau/edit", "/thong-tin-nhan-khau/delete").hasAnyRole("ADMIN", "HIGH_MANAGER")
                        .requestMatchers("/forgot-password").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/api/family/invoice").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/index")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/homepage", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/index")
                        .permitAll()
                )
                .exceptionHandling().accessDeniedPage("/access-denied");

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authenticationProvider);
    }


}

