package com.example.domains.contracts.repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.example.domains.entities.Category;

public interface CategoryRepository extends ListCrudRepository<Category, Integer>{
}
