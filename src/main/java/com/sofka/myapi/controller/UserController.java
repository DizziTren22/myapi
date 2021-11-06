package com.sofka.myapi.controller;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sofka.myapi.interfaces.UserService;
import com.sofka.myapi.model.User;
import com.sofka.myapi.security.JWTAuthorizationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import reactor.core.publisher.Mono;

/**
 * <b>Descripcion:<b> Clase que determina el controlador del usuario
 * @author Dizzi Tren
 */
@RestController
@RequestMapping("/myapi.com")
public class UserController {
	
	/**
	 * Atributo que determina la interfaz de los servicios relacionados a los usuarios
	 */
	@Autowired
    private UserService userService;
	
	/**
	 * Atributo que determina la interfaz relacionadas al http
	 */
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * Atributo que determina la clase con metodos relacionados a la obtencion del token
	 */
	private JWTAuthorizationFilter jwtAuthorizationFilter = new JWTAuthorizationFilter();
		
    /**
     * Metodo encargado de retornar un saludo con el detalle del usuario del token
     * @author Dizzi Tren
     * 
     * @return resultado esperado, el saludo con el detalle del usuario del token ingresado
     */
    @GetMapping("/greet")
    public String getSaludo() {
    	
    	Mono<User> userMono = userService.findByUserName(jwtAuthorizationFilter.validateToken(request).getSubject());

    	User usuario = userMono.block(Duration.of(1000, ChronoUnit.MILLIS));
    	
        return "Hola " + usuario + "\n" + "Hello " + usuario + "\n" + "Ciao " + usuario;
    }
    
    /**
     * Metodo encargado de obtener los usuarios creados en el aplicativo
     * @author Dizzi Tren
     * 
     * @return resultado esperado del servicio, retornar la lista o el mensaje de que no es administrador el rol
     */
    @GetMapping("/list")
    public String list() {
    		    	
    	Mono<User> userMono = userService.findByUserName(jwtAuthorizationFilter.validateToken(request).getSubject());

    	User usuario = userMono.block(Duration.of(1000, ChronoUnit.MILLIS));
    	
    	if (!"administrador".equals(usuario.getRol())) {
    		return "Para acceder a este servicio debe tener rol de 'administrador'";
    	}
    	
    	List<User> usuarios = userService.getAllUsers().collectList().block(Duration.of(1000, ChronoUnit.MILLIS));
    	
    	return usuarios.toString();
    }
    
    /**
     * Metodo encargado de realizar el login en el aplicativo
     * @author Dizzi Tren
     * 
     * @param user clase que contiene la informacion del usuario
     * @return informacion del usuario creado
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> login(@RequestBody User user) {
    	user.setToken(this.getJWTToken(user));
        return userService.createUser(user);
    } 
    
    /**
     * Metodo encargado de obtener el token en sesion
     * @author Dizzi Tren
     * 
     * @param user clase del usuario que contiene la informacion
     * @return token generado
     */
    private String getJWTToken(User user) {
		String secretKey = "mySecretKey";
		
		List<GrantedAuthority> grantedAuthorities =
				AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRol());
		
		String token = Jwts.builder().setId("softtekJWT").setSubject(user.getUserName())
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}
