package com.ParcialDisenoWeb.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ParcialDisenoWeb.app.entity.Peet;
import com.ParcialDisenoWeb.app.exception.NotFoundException;
import com.ParcialDisenoWeb.app.repository.PeetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping(value = "/api/mascotas")
public class PeetController {
	@Autowired
	private PeetRepository peetRepository;
	
	 @GetMapping("/")
    public List<Peet> getAllMascotas() {
        return peetRepository.findAll();
    }

    @GetMapping("/{id}")
    public Peet getMascotaById(@PathVariable String id) {
        return peetRepository.findById(id).orElseThrow(() -> new NotFoundException("Mascota no encontrada"));
    }

    @PostMapping("/")
    public Peet saveMascota(@RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Peet peet = mapper.convertValue(body, Peet.class);
        return peetRepository.save(peet);
    }

    @PutMapping("/{id}")
    public Peet updateMascota(@PathVariable String id, @RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Peet peet = mapper.convertValue(body, Peet.class);
        peet.setId(id);
        return peetRepository.save(peet);
    }

    @DeleteMapping("/{id}")
    public Peet deleteMascota(@PathVariable String id) {
    	Peet peet = peetRepository.findById(id).orElseThrow(() -> new NotFoundException("Equipo no encontrado"));
        peetRepository.deleteById(id);
        return peet;
    }
}
