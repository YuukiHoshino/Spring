package yuki.SpringBootThymeleaf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		// add our users for in memory authentication
		auth.inMemoryAuthentication()
		.withUser("john").password(encoder.encode("test123")).roles("EMPLOYEE")
		.and()
		.withUser("mary").password(encoder.encode("test123")).roles("EMPLOYEE", "MANAGER")
		.and()
		.withUser("yuki").password(encoder.encode("test123")).roles("EMPLOYEE", "ADMIN");

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/employeeController/showAddForm").hasAnyRole("MANAGER", "ADMIN")
			.antMatchers("/employeeController/showUpdateForm").hasAnyRole("MANAGER", "ADMIN")
			.antMatchers("/employeeController/save*").hasAnyRole("MANAGER", "ADMIN")
			.antMatchers("/employeeController/delete").hasRole("ADMIN")
			.antMatchers("/employeeController/**").hasRole("EMPLOYEE")
			.antMatchers("/employeeController/**").permitAll()
			.and()
			.formLogin()
//				.loginPage("/employeeController/login")
//				.permitAll()
//				.defaultSuccessUrl("/employeeController/showEmployees", false)
//				.failureUrl("/employeeController/login-error")
			.loginPage("/employeeController/login")
			.loginProcessingUrl("/authenticateTheUser")
			.permitAll()
			.and()
			.logout().permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/access-denied");
	}
}
