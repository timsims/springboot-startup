package com.bootstrap.startup.configurations;

import com.bootstrap.startup.http.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * 允许配置对整个Web安全都具有全局影响的事物，例如使用 HttpFirewall 的实现来设置调试模式或启用进一步的防火墙配置，或者仅忽略代码显示的资源。
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * Authentication Manager (下称 AM ) 用于管理和处理用户认证逻辑
     * 通过配置 Authentication Manager Builder , 来建立身份验证机制, 例如基于LDAP的身份验证或基于JDBC的身份验证
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用数据库进行身份验证
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    /**
     * 允许为HTTP请求配置基于Web的安全性。在此级别，您声明身份验证规则。
     * <p>
     * HttpSecurity 可以理解为 WebSecurity 的子集合
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭跨站
        http.csrf().disable()
                // 注册，登陆接口不需要校验
                .authorizeRequests().antMatchers("/login", "/register").permitAll()
                .anyRequest().authenticated()
                .and()
                // 关闭 session , 使用无状态模式
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 使用 jwt filter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
