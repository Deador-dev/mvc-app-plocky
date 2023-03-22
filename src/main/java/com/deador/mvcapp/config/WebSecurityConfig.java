package com.deador.mvcapp.config;

import com.deador.mvcapp.factory.ObjectFactory;
import com.deador.mvcapp.repository.RoleRepository;
import com.deador.mvcapp.service.userdetail.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
//@EnableWebSecurity
//@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final ObjectFactory objectFactory;
    private final CustomUserDetailService customUserDetailService;
    private final RoleRepository roleRepository;

    @Autowired
    public WebSecurityConfig(ObjectFactory objectFactory,
                             CustomUserDetailService customUserDetailService,
                             RoleRepository roleRepository) {
        this.objectFactory = objectFactory;
        this.customUserDetailService = customUserDetailService;
        this.roleRepository = roleRepository;
    }

    // TODO: 20.03.2023 need to add rememberMe()
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/shop/**", "/register", "/search/**", "/activate/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/shop")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
//                .rememberMe()
//                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                .and()
                .csrf()
                .disable();
        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/images/**", "/productImages/**", "/css/**", "/js/**", "/error");
    }

//    @Bean
//    public PrincipalExtractor principalExtractor(UserRepository userRepository) {
//        return map -> {
//            // FIXME: 21.03.2023 very long id
//            String id = (String) map.get("sub");
//            User user = userRepository.findById(id).orElseGet(() -> {
//                User newUser = (User) objectFactory.createObject(User.class);
//
//                newUser.setId(id);
//                newUser.setFirstName((String) map.get("name"));
//                newUser.setLastName((String) map.get("name"));
//                newUser.setEmail((String) map.get("email"));
//                // FIXME: 21.03.2023 setPassword() ?
////                newUser.setPassword();
//                newUser.setRoles(Collections.singletonList(roleRepository.findById(2L).get()));
//                newUser.setGender((String) map.get("gender"));
//                newUser.setLocale((String) map.get("locale"));
//                newUser.setUserpic((String) map.get("picture"));
//                newUser.setIsActivated(true);
//                newUser.setActivationCode("Confirmed");
//
//                return newUser;
//            });
//            user.setLastVisit(LocalDateTime.now());
//
//            return userRepository.save(user);
//        };
//    }
}
