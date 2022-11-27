package com.ParcialDisenoWeb.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ParcialDisenoWeb.app.entity.Booking;

public interface BookingRepository extends MongoRepository<Booking, String>{

}
