package de.yannickmortier.productservice.configuration;

import de.yannickmortier.productservice.jwtsecurity.StatelessAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final StatelessAuthenticationFilter statelessAuthenticationFilter;

    @Autowired
    public WebSecurityConfig(StatelessAuthenticationFilter statelessAuthenticationFilter) {
        super(true);
        this.statelessAuthenticationFilter = statelessAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().and()
                .anonymous().and()
                .servletApi().and().cors().and()
                .headers().and()
                .authorizeRequests()
                    .antMatchers("/products",
                            "/images/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .addFilterBefore(statelessAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);
    }

}
