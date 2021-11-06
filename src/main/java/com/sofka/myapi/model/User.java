package com.sofka.myapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <b>Descripcion:<b> Clase que determina la entidad del usuario
 * @author Dizzi Tren
 */
@Document
@Data
@NoArgsConstructor
public class User {

	/**
	 * Atributo que determina el id del usuario
	 */
	@Id
	private Integer id;
	
	/**
	 * Atributo que determina el username del usuario
	 */
	private String userName;
	
	/**
	 * Atributo que determina la contrasena del usuario
	 */
	private String password;
	
	/**
	 * Atributo que determina el token del usuario
	 */
	private String token;
	
	/**
	 * Atributo que determina el rol del usuario
	 */
	private String rol;

	/**
	 * Constructor de la clase.
	 * @param userName
	 * @param password
	 */
	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
}
