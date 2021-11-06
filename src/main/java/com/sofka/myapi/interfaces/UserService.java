package com.sofka.myapi.interfaces;

import com.sofka.myapi.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <b>Descripcion:<b> Clase que determina la interfaz para los servicios
 * @author Dizzi Tren
 */
public interface UserService {

	/**
	 * Metodo encargado de obtener todos los usuarios registrados
	 * @author Dizzi Tren
	 * 
	 * @return lista de los usuarios
	 */
	Flux<User> getAllUsers();
	
	/**
	 * Metodo encargado de crear el usuario
	 * @author Dizzi Tren
	 * 
	 * @param user usuario a crear
	 * @return el usuario creado
	 */
	Mono<User> createUser(User user);
	
	/**
	 * Metodo encargado de buscar al usuario a partir del nombre
	 * @author Dizzi Tren
	 * 
	 * @param userName username del usuario
	 * @return usuario encontrado
	 */
	Mono<User> findByUserName(String userName);
}
