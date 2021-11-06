package com.sofka.myapi.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.sofka.myapi.model.User;

import reactor.core.publisher.Mono;

/**
 * <b>Descripcion:<b> Clase que determina el repositorio para usar con mongodb para la entidad User
 * @author Dizzi Tren
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
	
	/**
	 * Metodo encargado de consultar el usuario apartir del username
	 * @author Dizzi Tren
	 * 
	 * @param userName username del usuario
	 * @return usuario encontrado
	 */
	Mono<User> findByUserName(String userName);
}
