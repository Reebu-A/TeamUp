package com.Teamup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Teamup.model.Admin;
import com.Teamup.model.Login;
import com.Teamup.model.TurfOwner;
import com.Teamup.model.User;
import com.Teamup.service.LoginService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("LoginAndSignin")
public class LoginOrSignin 
{
	@Autowired
	private LoginService lservice;
	
	@GetMapping("/login")
	public String login(Model model,HttpServletResponse response)
	{
		//response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
		return "Loginpage";
	}
	@PostMapping("/logincheck")
	public String logindetails(@ModelAttribute Login l, HttpSession session, Model model)
	{
		String role="";
		TurfOwner tfologid;
		User userlogid;
		Admin adminlogid;
		System.out.println(l.getEmail());
		List<Login> log= lservice.login(l.getEmail(), l.getPassword());
		if (log.isEmpty()) {
	        
	        model.addAttribute("error", "Invalid credentials");
	        return "Loginpage";
	    }
		Login lo = log.get(0); 
		//for (Login lo:log)
		//{
			if(lo.getStatus()==1)
		{
			 role=lo.getRole();
			 System.out.println(role+"///////////");
			switch (lo.getRole()) 
			{
			case "USER": 
			{
				userlogid=lo.getUser();
				session.setAttribute("id", userlogid.getId());
				System.out.println(role+" and id= "+userlogid.getId());
				return "redirect:/User/userlogin";
				
			}
			case "Turf Owner": 
			{
				tfologid=lo.getTf();
				session.setAttribute("id", tfologid.getId());
				System.out.println(role+" and id= "+tfologid.getId());
				return "redirect:/Turfowner/tflogin";
			}
			case "ADMIN": 
			{
				adminlogid=lo.getAdmin();
				session.setAttribute("id", adminlogid.getId());
				System.out.println(role+" and id= "+adminlogid.getId());
				return "redirect:/Admin/adminlogin";
				
			}
			default:
				model.addAttribute("error", "Invalid user role");
                return "Loginpage";
			}
		}
			else
			{
				 model.addAttribute("showApprovalPopup", true);
				System.out.println("**** Waiting for ADMIN approval *****");
				return "Loginpage";
			}
		//}
		
		
		
	}
	@GetMapping("/userreg")
	public String userregistration()
	{
		return "UserSignUp.html";
	}
	@GetMapping("/tforeg")
	public String turfownerregistration()
	{
		return "TurfOwnerSignUp.html";
	}
}
