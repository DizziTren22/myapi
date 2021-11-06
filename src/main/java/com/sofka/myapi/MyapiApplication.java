package com.sofka.myapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sofka.myapi.security.JWTAuthorizationFilter;

/**
 * <b>Descripcion:<b> Clase que determina la clase principal en la cual se inicia la aplicacion
 * @author Dizzi Tren
 */
@SpringBootApplication
public class MyapiApplication {

	/**
	 * Metodo encargado de ejecutar la aplicacion de spring boot
	 * @author Dizzi Tren
	 * 
	 * @param args argumentos
	 */
	public static void main(String[] args) {
		SpringApplication.run(MyapiApplication.class, args);
	}

	/**
	 * <b>Descripcion:<b> Clase que determina 
	 * @author Dizzi Tren
	 */
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		/** 
		 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests().antMatchers(HttpMethod.POST, "/myapi.com/login").permitAll().anyRequest()
					.authenticated();
		}
	}
}
