package com.example;

public class PersonaService {
	
	//needs a repository
	private PersonaRepository dao;

	public PersonaService(PersonaRepository dao) {
		this.dao = dao;
	}
	
	//para probar esto necesito simular una base de datos
	public void ponMayusculas(int id) {
		var item = dao.getOne(id);
		if(item.isEmpty())
			throw new IllegalArgumentException("ID NOT found");
		var p = item.get();
		p.setNombre(p.getNombre().toUpperCase());
		dao.modify(p);
	}
}
