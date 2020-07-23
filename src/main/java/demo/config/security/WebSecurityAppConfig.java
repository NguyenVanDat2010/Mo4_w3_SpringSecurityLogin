package demo.config.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class WebSecurityAppConfig extends WebSecurityConfigurerAdapter {
    /** Phương thức Override configure(AuthenticationManagerBuilder auth)
     * cấu hình xác thực bộ nhớ với thông tin đăng nhập và vai trò của người dùng.*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("123456").roles("USER")
                .and()
                .withUser("admin").password("123456").roles("ADMIN");
    }

    /** Phương thức Override configure(HttpSecurity http) cấu hình bảo mật
     * dựa trên web cho tất cả các yêu cầu HTTP. Theo mặc định, nó sẽ được áp dụng
     * cho tất cả các yêu cầu, nhưng có thể bị hạn chế bằng cách sử dụng requestMatcher()
     * hoặc các phương thức tương tự khác.*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll()
                .and()
                .authorizeRequests().antMatchers("/user**").hasRole("USER")
                .and()
                .authorizeRequests().antMatchers("/admin**").hasRole("ADMIN")
                .and()
                .formLogin()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

                //Nếu sử dụng cách dưới thì phải sử dụng logout ở method = post (xem ở index.html)
//                .logout().logoutSuccessUrl("/").permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/error");
    }
}
