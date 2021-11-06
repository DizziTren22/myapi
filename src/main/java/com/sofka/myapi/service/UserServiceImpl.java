package com.sofka.myapi.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sofka.myapi.interfaces.UserService;
import com.sofka.myapi.model.User;
import com.sofka.myapi.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <b>Descripcion:<b> Clase que determina los servicios relacionados al usuario
 * @author Dizzi Tren
 */
@Service
public class UserServiceImpl implements UserService {

	/**
	 * Atributo que determina el repositorio del usuario 
	 */
	@Autowired
	private UserRepository userRepository;
	
	/** 
	 * @see com.sofka.myapi.interfaces.UserService#getAllUsers()
	 */
	@Override
	public Flux<User> getAllUsers() {
		return userRepository.findAll().map(u -> {
			User user = new User();
			user.setId(u.getId());
			user.setUserName(u.getUserName());
			user.setPassword(u.getPassword());
			user.setToken(u.getToken());
			user.setRol(u.getRol());
            return user;
        });
	}

	/** 
	 * @see com.sofka.myapi.interfaces.UserService#createUser(com.sofka.myapi.model.User)
	 */
	@Override
	public Mono<User> createUser(User user) {
		
		User userCreacion = new User();
		userCreacion.setId(new Random().nextInt());
		userCreacion.setUserName(user.getUserName());
		userCreacion.setPassword(user.getPassword());
		userCreacion.setRol(user.getRol());
		userCreacion.setToken(user.getToken());
		
		return userRepository.insert(userCreacion);
	}

	/** 
	 * @see com.sofka.myapi.interfaces.UserService#findByUserName(java.lang.String)
	 */
	@Override
	public Mono<User> findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}
}
