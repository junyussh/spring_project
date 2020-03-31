package org.csu.mypetstore.domain.VerificationCode;

import org.csu.mypetstore.domain.VerificationCode.handler.MyAuthenticationFailureHandler;
import org.csu.mypetstore.domain.VerificationCode.handler.MyAuthenticationSuccessHandler;
import org.csu.mypetstore.domain.VerificationCode.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class) //添加验证码效验过滤器
//                .formLogin() // 表单登录
//                .loginPage("/login.html")       // 登录跳转url
////                .loginPage("/authentication/require")
//                .loginProcessingUrl("/login")   // 处理表单登录url
////                .successHandler(authenticationSuccessHandler)
//                .failureHandler(authenticationFailureHandler)
//                .and()
//                .authorizeRequests()            // 授权配置
//                .antMatchers("/login.html", "/css/**", "/authentication/require", "/code/image").permitAll()  // 无需认证
//                .anyRequest()                   // 所有请求
//                .authenticated()                // 都需要认证
//                .and().csrf().disable();

    }
}
