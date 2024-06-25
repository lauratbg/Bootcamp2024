package com.example.domains.entities.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class ActorOtroDTO implements Serializable{
	private int id;
	private String nombre;
	private String apellidos;
	
	public ActorOtroDTO(int id, String nombre, String apellidos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
	}
	

}
