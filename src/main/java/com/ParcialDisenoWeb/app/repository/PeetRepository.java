package com.ParcialDisenoWeb.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.ParcialDisenoWeb.app.entity.Peet;

public interface PeetRepository extends MongoRepository<Peet, String>{}
