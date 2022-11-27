package com.ParcialDisenoWeb.app.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ParcialDisenoWeb.app.entity.Usuarios;

public interface UsersRepository extends MongoRepository<Usuarios, String>{
	public Optional<Usuarios> findByUsername(String username);
}
