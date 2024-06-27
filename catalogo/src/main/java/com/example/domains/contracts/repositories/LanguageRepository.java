package com.example.domains.contracts.repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.example.domains.entities.Language;

public interface LanguageRepository extends ListCrudRepository<Language, Integer>{
}
