package com.Teamup.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Teamup.model.GameAlerts;
import com.Teamup.model.TurfBooking;
import com.Teamup.model.TurfDetails;
import com.Teamup.model.TurfOwner;
import com.Teamup.model.User;
import com.Teamup.model.WeatherResponse;
import com.Teamup.service.AlertService;
import com.Teamup.service.TurfdetailService;
import com.Teamup.service.UserService;
import com.Teamup.service.Weatherservice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("User")
public class UserController {

	@Autowired
	private UserService service;
	@Autowired
	private Weatherservice wservice;
	@Autowired
	private TurfdetailService turfservice;
	@Autowired
	private AlertService alertservice;
	
	@PostMapping("/saveuser")
	public String usersave(@ModelAttribute User us, RedirectAttributes redirectAttributes)
	{
		String ret="error";
		if(service.saveuser(us)!=null)
		{
			redirectAttributes.addFlashAttribute("successMessage", "User created successfully!");
			ret="redirect:/";
		}
		return ret;
		
	}
	@GetMapping("/userlogin")
	public String ulogin(HttpSession session, Model model, HttpServletResponse response) throws JsonMappingException, JsonProcessingException
	{
		//response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
		int uid=(int) session.getAttribute("id");
		System.out.println("User id ---"+uid);
		List<User> ulist= service.getuser(uid);
		model.addAttribute("user",ulist);
	    WeatherResponse weatherData = wservice.getWeatherData();
	    model.addAttribute("weatherData", weatherData);
	    
	    alertservice.updateGameStatusontime();
	    List<GameAlerts>Galertfilter=alertservice.findacceptedalerts(uid); 
	    long galertcount = Galertfilter.stream().count();
	    System.out.println("new alert count="+galertcount);
	    model.addAttribute("gcount",galertcount);
	    //List<GameAlerts>Galert=alertservice.findalerts(uid);
	    model.addAttribute("galert",Galertfilter);
	    List<TurfDetails> turfDetails = turfservice.viewactivatedturf();
	    model.addAttribute("venues", turfDetails);
	    
		return "inde";
	}
	@GetMapping("/image/{turfId}")
	public ResponseEntity<byte[]> getTurfImage(@PathVariable int turfId) {
	    TurfDetails turf = turfservice.getTurfById(turfId);
	    if (turf != null && turf.getTurfimage() != null) {
	        System.out.println("Image found for turfId: " + turfId + ", size: " + turf.getTurfimage().length + " bytes");
	        return ResponseEntity.ok()
	                .contentType(MediaType.IMAGE_JPEG)
	                .body(turf.getTurfimage());
	    } else {
	        System.out.println("Image not found for turfId: " + turfId);
	        return ResponseEntity.notFound().build();
	    }
	}

	@GetMapping("/creategamealert")
	public String creategamealert(HttpSession session, Model model)
	{
		int uid=(int) session.getAttribute("id");
		List<User> ulist= service.getuser(uid);
		model.addAttribute("user",ulist);
		
		List<TurfDetails> turfs=turfservice.viewactivatedturf();
		model.addAttribute("turflist",turfs);
		return "GameAlert";
	}
	
	@GetMapping("/directturfbooking")
	public String directturfbooking(HttpSession session, Model model)
	{
		int uid=(int) session.getAttribute("id");
		List<User> ulist= service.getuser(uid);
		model.addAttribute("user",ulist);
		
		List<TurfDetails> turfs=turfservice.viewactivatedturf();
		model.addAttribute("turflist",turfs);
		return "DirectTurfBooking";
	}
	
	@GetMapping("/createmoviealert")
	public String createmoviealert(HttpSession session, Model model)
	{
		int uid=(int) session.getAttribute("id");
		System.out.println("User id ---"+uid);
		List<User> ulist= service.getuser(uid);
		model.addAttribute("user",ulist);
		return "MovieAlert";
	}
	
	@GetMapping("/viewgamealerts")
	public String viewgamealert(HttpSession session, Model model)
	{
		int uid=(int) session.getAttribute("id");
		System.out.println("User id ---"+uid);
		List<User> ulist= service.getuser(uid);
		model.addAttribute("user",ulist);
	   
//	    List<GameAlerts>Galert=alertservice.findalerts(uid);
	    
	    alertservice.updateGameStatusontime();
	    List<GameAlerts>Galertfilter=alertservice.findacceptedalerts(uid); 
	    model.addAttribute("galert",Galertfilter);
	   //	    for (GameAlerts alert : Galertfilter) {
//	    	  System.out.println("Alert Details:");
//	    	  System.out.println("  ID: " + alert.getId()); // Assuming `getId` exists in GameAlerts
//	    	  System.out.println("  Name: " + alert.getUserid());
	//    }
		return "ViewGameAlert";
	}
	
	@GetMapping("/mygamealert")
	public String viewmygamealert(HttpSession session, Model model)
	{
		int uid=(int) session.getAttribute("id");
		List<User> ulist= service.getuser(uid);
		model.addAttribute("user",ulist);
		
		List<GameAlerts> myalertlist= alertservice.findmyalerts(uid);
		model.addAttribute("myalerts",myalertlist);
		
		return "ViewMyGameAlert";
	}
	@GetMapping("/mybooking")
	public String viewmyturfbooking(HttpSession session, Model model)
	{
		int uid=(int) session.getAttribute("id");
		List<User> ulist= service.getuser(uid);
		model.addAttribute("user",ulist);
	 List<TurfBooking> bookinghostory=alertservice.bookinghistory(uid);
	 model.addAttribute("booking", bookinghostory);
	 return "ViewMyBookingHistory";
	}
	@GetMapping("/seeallvenues")
	public String viewseeallvenues(HttpSession session, Model model)
	{
		int uid=(int) session.getAttribute("id");
		List<User> ulist= service.getuser(uid);
		model.addAttribute("user",ulist);
		 List<TurfDetails> turfDetails = turfservice.viewactivatedturf();
		 model.addAttribute("venues", turfDetails);
	 
	 return "UserViewAllVenues";
	}
	
	@GetMapping("/userprofile")
	public String profileupdate(HttpSession session, Model model)
	{
		int uid=(int) session.getAttribute("id");
		List<User> ulist= service.getuser(uid);
		model.addAttribute("user",ulist);
		return "UserProfileUpdate";
	}
	@PostMapping("/updateuser")
	public String updateprofile(@ModelAttribute User us, RedirectAttributes redirectAttributes)
	{
		String ret="error";
		if(service.updateUser(us) !=null)
		{
			 redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
			ret="redirect:/User/userlogin";
		}
		return ret;
		
	}
	
	@GetMapping("/deletealert/{id}")
	public String deleteAlert(@PathVariable("id") int id,RedirectAttributes redirectAttributes )
	{
		try {
			alertservice.deleteAlert(id);
			redirectAttributes.addFlashAttribute("successMessage", "Alert Deleted successfully!");
			return "redirect:/User/userlogin";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("successMessage", "Error deleting Alert: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/User/userlogin";
		}
		
	}
}
