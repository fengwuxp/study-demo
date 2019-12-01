package com.study.spring.config;

import com.study.spring.security.StudyUserDetailsService;
import com.study.spring.security.handlers.AuthLimitHandler;
import com.study.spring.security.handlers.LoginSuccessHandler;
import com.study.spring.security.handlers.StudyAuthenticationFailureHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private StudyUserDetailsService studyUserDetailsService;

    /**
     * 设置认证管理器
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(studyUserDetailsService)
                .passwordEncoder(passwordEncoder());
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("user").password(new BCryptPasswordEncoder().encode("123456")).roles("USER")
//                .and()
//                .withUser("admin").password(new BCryptPasswordEncoder().encode("admin")).roles("ADMIN")
//                .and()
//                .withUser("one").password(new BCryptPasswordEncoder().encode("one")).roles("ONE")
//                .and()
//                .withUser("two").password(new BCryptPasswordEncoder().encode("two")).roles("TWO")
//                .and()
//                .withUser("three").password(new BCryptPasswordEncoder().encode("three")).roles("THREE");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .formLogin()
                .loginPage("/login")
//                .loginProcessingUrl("/login")
                .permitAll()
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new StudyAuthenticationFailureHandler())
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(new AuthLimitHandler())
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
