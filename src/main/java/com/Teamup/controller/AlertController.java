package com.Teamup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Teamup.model.GameAlerts;
import com.Teamup.model.User;
import com.Teamup.service.AlertService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("Alert")
public class AlertController 
{
	@Autowired
	public AlertService alertservice;
	
	@PostMapping("/savegamealert")
	public String savegamealert(@ModelAttribute GameAlerts galert, RedirectAttributes redirectAttributes) 
	{
	    if (alertservice.savegamealert(galert) != null) {
	        redirectAttributes.addFlashAttribute("successMessage", "Alert created successfully!");
	        return "redirect:/User/userlogin";
	    }
	    return "error";
	}
	
	@GetMapping("/gamealertaccept/{aid}")
	public String gamealertaccept(@PathVariable int aid, HttpSession session,RedirectAttributes redirectAttributes)
	{
		System.out.println(aid+"**********");
		int uid=(int) session.getAttribute("id");
		 User user = new User(); 
	     user.setId(uid);
	     
	     String ret="error";
	     if (alertservice.acceptgamealert(aid, user) != null) {
	         redirectAttributes.addFlashAttribute("successMessage", "You have successfully accepted the alert!");
	         ret = "redirect:/User/userlogin";
	     }
			return ret;
	}

	
}
