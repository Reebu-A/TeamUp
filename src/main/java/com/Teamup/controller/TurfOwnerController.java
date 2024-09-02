package com.Teamup.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Teamup.model.TurfBooking;
import com.Teamup.model.TurfDetails;
import com.Teamup.model.TurfOwner;
import com.Teamup.model.User;
import com.Teamup.service.AlertService;
import com.Teamup.service.TurfdetailService;
import com.Teamup.service.TurfownerService;
import com.Teamup.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("Turfowner")
public class TurfOwnerController {

	@Autowired
	private TurfownerService tfservice;
	
	@Autowired
	private TurfdetailService tdservice;
	
	@Autowired
	private UserService userservice;
	
	@PostMapping("/savetf")
	public String turfownersave(@ModelAttribute TurfOwner tf, RedirectAttributes redirectAttributes)
	{
		System.out.println(tf.getPassword());
		System.out.println(tf.getPhone());
		String ret="error";
		if(tfservice.saveturfowner(tf) !=null)
		{
			 redirectAttributes.addFlashAttribute("successMessage", "Turf owner account created successfully!");
			ret="redirect:/";
		}
		return ret;
	}

	@GetMapping("/tflogin")
	public String tflogin(HttpSession session, Model model, HttpServletResponse response)
	{
		response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
		int tfid=(int) session.getAttribute("id");
		System.out.println("TFowner id ---"+tfid);
		List<TurfOwner> tflist=tfservice.gettf(tfid);
		model.addAttribute("tfo", tflist);
		long activationPendingCount =tdservice.turfActivationPendingcountByTfoId(tfid);
		model.addAttribute("activationPendingCount", activationPendingCount);
		long allTurfCountById= tdservice.turfCountByTfoId(tfid);
		model.addAttribute("allTurfCountById", allTurfCountById);
		
		return "TFowner";
	}
	
	@GetMapping("/turfadding")
	public String turfadd(HttpSession session, Model model)
	{
		int tfid=(int) session.getAttribute("id");
		
		List<TurfOwner> tflist=tfservice.gettf(tfid);
		model.addAttribute("tfo", tflist);
		return"Turfadding";
	}
	@GetMapping("/TFowner")
	public String tfowner(HttpSession session, Model model)
	{
		int tfid=(int) session.getAttribute("id");
		System.out.println("TFowner id ---"+tfid);
		List<TurfOwner> tflist=tfservice.gettf(tfid);
		model.addAttribute("tfo", tflist);
		long activationPendingCount =tdservice.turfActivationPendingcountByTfoId(tfid);
		model.addAttribute("activationPendingCount", activationPendingCount);
		long allTurfCountById= tdservice.turfCountByTfoId(tfid);
		model.addAttribute("allTurfCountById", allTurfCountById);
		return "TFowner";
	}
	
	@PostMapping("/saveturf")
	public String saveturf(@ModelAttribute TurfDetails td, @RequestParam(value = "image", required = false) MultipartFile imageFile, RedirectAttributes redirectAttributes)
	{
		String ret="error";
		 if (imageFile != null && !imageFile.isEmpty()) {
		        try {
		            td.setTurfimage(imageFile.getBytes());
		        } catch (IOException e) {
		            e.printStackTrace();
		          
		        }
		    } else {
		       
		        td.setTurfimage(null);
		    }
		if(tdservice.saveturf(td)!=null)
		{
			redirectAttributes.addFlashAttribute("successMessage", "New turf added successfully!");
			ret="redirect:/Turfowner/TFowner";
		}
		return ret;
	}
	
	@GetMapping("/viewturf")
	public String viewturf(HttpSession session, Model model)
	{
		int tfid=(int) session.getAttribute("id");
		List<TurfDetails> tfv=tdservice.viewturf(tfid);
		model.addAttribute("tview", tfv);
		
		List<TurfOwner> tflist=tfservice.gettf(tfid);
		model.addAttribute("tfo", tflist);
		
		return "Turfview" ;
		
	}
	
	@GetMapping("/bookturf")
	public String bookturf( @RequestParam("alertid") int alertId,
            @RequestParam("turfname") String turfName,
            @RequestParam("turfrate") int turfRate,
            @RequestParam("userid") int userId,
            @RequestParam("turfownerid") int turfownerId,
            @RequestParam("gamename") String gamename,
            @RequestParam("gametime") LocalTime gameTime,
            @RequestParam("gamedate") LocalDate gameDate,
            Model model, HttpSession session, RedirectAttributes redirectAttributes)
	{
		int uid=(int) session.getAttribute("id");
		System.out.println("User id ---"+uid);
		
		List<TurfBooking> tbooking=tdservice.getBookedSlotsOfTurf(turfName, gameDate);
		for (TurfBooking booking : tbooking) {
            if (turfName.equals(booking.getTurfname()) &&
                gameTime.equals(booking.getGametime()) &&
                gameDate.equals(booking.getGamedate())) 
            {
            	redirectAttributes.addFlashAttribute("successMessage", "Sorry!! Turf already booked for the time slot!");
            	return "redirect:/User/userlogin";
            }
		}
		List<User> ulist= userservice.getuser(uid);
		model.addAttribute("user",ulist);
		
		
		model.addAttribute("alertid", alertId);
		model.addAttribute("turfname", turfName);
		model.addAttribute("turfrate",turfRate);
		model.addAttribute("userid", userId);
		model.addAttribute("turfownerid", turfownerId);
		model.addAttribute("gamename", gamename);
		model.addAttribute("gametime", gameTime);
		model.addAttribute("gamedate", gameDate);
	
		return "TurfBookingConfirmation.html";
		
	}
	
	@PostMapping("/bookturfconfirm")
	public String bookturfconfirm( @RequestParam("alertid") int alertId,
            @RequestParam("turfname") String turfName,
            @RequestParam("turfrate") int turfRate,
            @RequestParam("userid") int userId,
            @RequestParam("turfownerid") int turfownerId,
            @RequestParam("gamename") String gamename,
            @RequestParam("gametime") LocalTime gameTime,
            @RequestParam("gamedate") LocalDate gameDate,
            Model model)
	{
		model.addAttribute("alertid", alertId);
		model.addAttribute("turfname", turfName);
		model.addAttribute("turfrate",turfRate);
		model.addAttribute("userid", userId);
		model.addAttribute("turfownerid", turfownerId);
		model.addAttribute("gamename", gamename);
		model.addAttribute("gametime", gameTime);
		model.addAttribute("gamedate", gameDate);
		
		return "PaymentGateway";
		
	}
	@PostMapping("/paymentsuccess")
	public String afterpayment( @RequestParam("alertid") int alertId,
            @RequestParam("turfname") String turfName,
            @RequestParam("turfrate") int turfRate,
            @RequestParam("userid") int userId,
            @RequestParam("turfownerid") int turfownerId,
            @RequestParam("gamename") String gamename,
            @RequestParam("gametime") LocalTime gameTime,
            @RequestParam("gamedate") LocalDate gameDate, RedirectAttributes redirectAttributes)
	{
		String ret="error";
		
		if(tdservice.bookturf(alertId, turfName, gamename, gameTime, gameDate, turfRate, userId, turfownerId) !=null)
		{
			redirectAttributes.addFlashAttribute("successMessage", "Turf booked successfully!");
			ret="redirect:/User/userlogin";
		}
		return ret;
		
	}
	
	@GetMapping("/tfprofileupdate")
	public String profileupdate(HttpSession session, Model model)
	{
		int tfid=(int) session.getAttribute("id");
		List<TurfOwner> tflist=tfservice.gettf(tfid);
		model.addAttribute("tfo", tflist);
		return "TurfOwnerProfileUpdate";
	}
	@PostMapping("/updatetfo")
	public String updateprofile(@ModelAttribute TurfOwner tf, RedirectAttributes redirectAttributes)
	{
		String ret="error";
		if(tfservice.updateturfowner(tf) !=null)
		{
			 redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
			ret="redirect:/Turfowner/tflogin";
		}
		return ret;
		
	}
	@GetMapping("/turfupdate/{tid}")
	public String turfupdate(HttpSession session, Model model, @PathVariable int tid)
	{
		int tfid=(int) session.getAttribute("id");
		List<TurfOwner> tflist=tfservice.gettf(tfid);
		model.addAttribute("tfo", tflist);
		
		List<TurfDetails> turfDetails= tdservice.TurfdetailForUpdate(tid);
		model.addAttribute("tdetails", turfDetails);
		return "TurfUpdate";
	}
	
	@PostMapping("/saveturfupdate")
	public String saveturfupdate(@ModelAttribute TurfDetails td, @RequestParam(value = "image", required = false) MultipartFile imageFile, RedirectAttributes redirectAttributes)
	{
		String ret="error";
		 if (imageFile != null && !imageFile.isEmpty()) {
		        try {
		            td.setTurfimage(imageFile.getBytes());
		        } catch (IOException e) {
		            e.printStackTrace();
		          
		        }
		    } 
		if(tdservice.saveturfupdate(td)!=null)
		{
			redirectAttributes.addFlashAttribute("successMessage", "Turf details updated!");
			ret="redirect:/Turfowner/TFowner";
		}
		return ret;
	}
	
	@GetMapping("/Bookingreceived")
	public String bookingsreceived(HttpSession session, Model model)
	{
		int tfid=(int) session.getAttribute("id");
		List<TurfOwner> tflist=tfservice.gettf(tfid);
		model.addAttribute("tfo", tflist);
		
		List<TurfBooking> bookings=tdservice.getBookingsOfTurf(tfid);
		model.addAttribute("bookings", bookings);
		
		return "TurfBookingsReceived";
	}
	
	 @GetMapping("/turfdelete/{id1}")
	    public String deleteTurf(@PathVariable("id1") int turfid,  RedirectAttributes redirectAttributes) 
	 {
	        try {
	            tdservice.deleteTurfById(turfid);
	            redirectAttributes.addFlashAttribute("successMessage", "Turf deleted successfully!");
	            return "redirect:/Turfowner/TFowner";
	        } catch (Exception e) {
	        	redirectAttributes.addFlashAttribute("successMessage", "Error deleting turf: " + e.getMessage());
	            e.printStackTrace();
	            return "redirect:/Turfowner/TFowner";
	        }
	    }
	
}
