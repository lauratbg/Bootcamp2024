package com.example.domains.entities.models;

import org.springframework.beans.factory.annotation.Value;

public interface ActorShort {
	@Value("#{target.ActorId}")
	int getActorId();
	
	@Value("#{target.firstName + ' ' + target.lastName}")
	String getNombre();
}
