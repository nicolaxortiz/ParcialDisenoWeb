package com.ParcialDisenoWeb.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ParcialDisenoWeb.app.entity.Booking;
import com.ParcialDisenoWeb.app.exception.NotFoundException;
import com.ParcialDisenoWeb.app.repository.BookingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/api/citas")
public class BookingController {
	@Autowired
	private BookingRepository bookingRepository;
	
	 @GetMapping("/")
    public List<Booking> getAllCitas() {
        return bookingRepository.findAll();
    }

    @GetMapping("/{id}")
    public Booking getCitasById(@PathVariable String id) {
        return bookingRepository.findById(id).orElseThrow(() -> new NotFoundException("Cita no encontrada"));
    }

    @PostMapping("/")
    public Booking saveCitas(@RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Booking booking = mapper.convertValue(body, Booking.class);
        return bookingRepository.save(booking);
    }

    @PutMapping("/{id}")
    public Booking updateCitas(@PathVariable String id, @RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Booking booking = mapper.convertValue(body, Booking.class);
        booking.setId(id);
        return bookingRepository.save(booking);
    }

    @DeleteMapping("/{id}")
    public Booking deleteCitas(@PathVariable String id) {
    	Booking booking = bookingRepository.findById(id).orElseThrow(() -> new NotFoundException("Equipo no encontrado"));
    	bookingRepository.deleteById(id);
        return booking;
    }
}
