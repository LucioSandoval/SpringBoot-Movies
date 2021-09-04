package com.proyecto.peliculas;

import com.proyecto.peliculas.model.*;
import com.proyecto.peliculas.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.sendgrid.*;

import java.io.IOException;

@SpringBootApplication
public class PeliculasApplication  extends SpringBootServletInitializer {

	public static void main(String[] args) {SpringApplication.run(PeliculasApplication.class, args);




	}
	// método para crear datos
	@Bean
	public CommandLineRunner iniciarDatos(RepositoryGenero repositoryGenero,
										  RepositoryPelicula repositoryPelicula,
										  RepositoryPersonaje repositoryPersonaje,
										  RepositoryPeliculaPersonaje repositoryPeliculaPersonaje
										  ){
		return (args)->{
			Genero genero= new Genero("Terror", "https://www.adslzone.net/app/uploads-adslzone.net/2019/04/borrar-fondo-imagen.jpg");
			repositoryGenero.save(genero);

			Pelicula pelicula = new Pelicula("imagen1", "Anabelle",3,genero);
			repositoryPelicula.save(pelicula);
			Pelicula pelicula1 = new Pelicula("imagen2", "Anabelle2",3,genero);
			repositoryPelicula.save(pelicula1);

			Personaje personaje1 = new Personaje("imagen1", "lucio",30,12.6 ,"creador de peliculas");
			repositoryPersonaje.save(personaje1);

			Personaje personaje2 = new Personaje("imagen2", "lucia",32,12.6 ,"creador de peliculas");
			repositoryPersonaje.save(personaje2);

			PeliculaPersonaje peliculaPersonaje1 = new PeliculaPersonaje(pelicula,personaje1);
			repositoryPeliculaPersonaje.save(peliculaPersonaje1);
			PeliculaPersonaje peliculaPersonaje2 = new PeliculaPersonaje(pelicula,personaje2);
			repositoryPeliculaPersonaje.save(peliculaPersonaje2);

			PeliculaPersonaje peliculaPersonaje3 = new PeliculaPersonaje(pelicula1,personaje2);
			repositoryPeliculaPersonaje.save(peliculaPersonaje3);




		};
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	RepositoryUser userRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inputName-> {
			Usuario usuario = userRepository.findByUsername(inputName);
			if (usuario != null) {
				return new User(usuario.getUsername(), usuario.getPassword(),
						AuthorityUtils.createAuthorityList("USER"));
			} else {
				throw new UsernameNotFoundException("Usuario no existente" + inputName);
			}
		});
	}
}

@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/characters/**").hasAuthority("USER")
				.antMatchers("/movies/**").hasAuthority("USER")
				.antMatchers("/auth/register").permitAll()
				.antMatchers("/auth/login").permitAll()
				.and().authorizeRequests();


		http.formLogin()
				.loginPage("/auth/login");
		http.logout().logoutUrl("/auth/cerrarsesion");
		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}



