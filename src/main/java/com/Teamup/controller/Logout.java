package com.Teamup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("Logout")
public class Logout 
{
	
	@GetMapping("/logout")
	public String logout(HttpSession session)
	{
		System.out.println("Logout****++");
		int id= (int) session.getAttribute("id");
		session.removeAttribute("id");
	    session.invalidate();
		return "redirect:/LoginAndSignin/";
	}

}
