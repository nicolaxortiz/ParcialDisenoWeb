package com.ParcialDisenoWeb.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ParcialDisenoWeb.app.entity.Booking;
import com.ParcialDisenoWeb.app.entity.Peet;
import com.ParcialDisenoWeb.app.entity.Usuarios;
import com.ParcialDisenoWeb.app.repository.BookingRepository;
import com.ParcialDisenoWeb.app.repository.PeetRepository;
import com.ParcialDisenoWeb.app.repository.UsersRepository;

@Controller
public class HomeController {
	
	@Autowired
	private PeetRepository IPeets;
	
	@Autowired
	private BookingRepository IBooking;
	
	@Autowired
	private UsersRepository IUser;
	
	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}
	
	@GetMapping("/login")
	public String login(Model model, @ModelAttribute Usuarios usuarios) {
		model.addAttribute("user", usuarios);
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute Usuarios usuarios) {
		
		for(Usuarios item :IUser.findAll()) {
			if(item.getUsername().equals(usuarios.getUsername())) {
				if(item.getPassword().equals(usuarios.getPassword())) {
					return "redirect:/home";
				}
			}
		}
		return "redirect:/login";
	}
	
	@GetMapping("/formUser")
	public String createuser(Model model, @ModelAttribute Usuarios usuarios) {
		model.addAttribute("user", usuarios);
		return "formUser";
	}
	
	@PostMapping("/formUser")
	public String createuser(@ModelAttribute Usuarios usuarios) {
		
		IUser.save(usuarios);
		
		return "redirect:/login";
	}

	@GetMapping("/home")
	public String home(Model model) {
		return "home";
	}
	
	@GetMapping("/peet")
	public String peetHome(Model model) {
		model.addAttribute("peet", IPeets.findAll());
		return "Peet";
	}
	
	@GetMapping("/save")
	public String guardar(@ModelAttribute Peet peet, Model model) {
		
		model.addAttribute("peet", peet);
		return "formPeet";
	}
	
	@PostMapping("/save")
	public String guardar(@ModelAttribute Peet peet, Model model, @RequestParam("file") MultipartFile imagen) {
		Path directorioImagenes = Paths.get("src//main//resources//static/images");
		String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
		
		try {
			byte[] bytesImg = imagen.getBytes();
			Path rutaCompleta = Paths.get(rutaAbsoluta + "//"+ imagen.getOriginalFilename());
			Files.write(rutaCompleta, bytesImg);
			
			peet.setFoto(imagen.getOriginalFilename());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		IPeets.save(peet);
		
		return "redirect:/peet";
	}
	
	@GetMapping("/search/{id}")
	public String ver(@PathVariable(value="id") String id, @ModelAttribute Peet peet, Model model) {
		
		List<Booking> booking = new ArrayList<Booking>();
		
		peet = IPeets.findById(id).get();
		
		for(Booking item :IBooking.findAll()) {
			if(item.getIdPaciente().equals(id)) {
				booking.add(item);
			}
		}
		
		model.addAttribute("searchPeet", peet);
		model.addAttribute("listBooking", booking);
		
		return "hojadevida";
	}
	
	@GetMapping("/update/{id}")
	public String editar(@PathVariable(value="id") String id, @ModelAttribute Peet peet, Model model) {
		
		peet = IPeets.findById(id).get();
		model.addAttribute("searchPeet", peet);
		
		return "updatePeet";
	}
	
	@PostMapping("/update/{id}")
	public String editar(@PathVariable(value="id") String id, @ModelAttribute Peet peet, Model model, @RequestParam("file") MultipartFile imagen) {
		
		Path directorioImagenes = Paths.get("src//main//resources//static/images");
		String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
		
		try {
			byte[] bytesImg = imagen.getBytes();
			Path rutaCompleta = Paths.get(rutaAbsoluta + "//"+ imagen.getOriginalFilename());
			Files.write(rutaCompleta, bytesImg);
			
			peet.setFoto(imagen.getOriginalFilename());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		peet.setId(id);
		
		IPeets.save(peet);
		return "redirect:/peet";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable(value="id") String id) {
		IPeets.deleteById(id);
		return "redirect:/peet";
	}
	
	@GetMapping("/booking")
	public String bookingHome(Model model) {
		model.addAttribute("booking", IBooking.findAll());
		return "Booking";
	}
	
	@GetMapping("/saveBooking")
	public String guardarBooking(@ModelAttribute Booking booking, Model model) {
		
		model.addAttribute("booking", booking);
		model.addAttribute("allPeet", IPeets.findAll());
		return "formBooking";
	}
	
	@PostMapping("/saveBooking")
	public String guardarBooking(@ModelAttribute Booking booking) {
		Peet bookingPeet;
		
		bookingPeet = IPeets.findById(booking.getIdPaciente()).get();
		
		booking.setPaciente(bookingPeet.getNombre());
		
		IBooking.save(booking);
		
		return "redirect:/booking";
	}
	
	@GetMapping("/updateBooking/{id}")
	public String editarBooking(@PathVariable(value="id") String id, @ModelAttribute Booking booking, Model model) {
		
		booking = IBooking.findById(id).get();
		model.addAttribute("searchBooking", booking);
		
		return "updateBooking";
	}
	
	@PostMapping("/updateBooking/{id}")
	public String editarBooking(@PathVariable(value="id") String id, @ModelAttribute Booking booking) {
		
		booking.setId(id);
		
		IBooking.save(booking);
		return "redirect:/booking";
	}
	
	@GetMapping("/deleteBooking/{id}")
	public String eliminarBooking(@PathVariable(value="id") String id) {
		IBooking.deleteById(id);
		return "redirect:/booking";
	}
}
