package com.Teamup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Teamup.model.Admin;
import com.Teamup.model.GameAlerts;
import com.Teamup.model.Login;
import com.Teamup.model.TurfDetails;
import com.Teamup.model.User;
import com.Teamup.service.AdminService;
import com.Teamup.service.AlertService;
import com.Teamup.service.LoginService;
import com.Teamup.service.TurfdetailService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("Admin")
public class AdminController 
{
	@Autowired
	private AdminService adservice;
	@Autowired
	private TurfdetailService turfservice;
	@Autowired
	private AlertService alertservice;
	@Autowired
	private LoginService loginservice;
	
	@GetMapping("/adminsignup")
	public String adminsignup()
	{
		return "AdminSignUp";
	}
	
	@PostMapping("/saveadmin")
	public String saveadmin(@ModelAttribute Admin ad)
	{
		String ret="error";
		if (adservice.saveadmin(ad)!=null) 
		{
			ret="redirect:/";
		}
		return ret;
	}
	
	@GetMapping("/adminlogin")
	public String ulogin(HttpSession session,HttpServletResponse response, Model model)
	{
		response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
		int aid=(int) session.getAttribute("id");
		System.out.println("Admin id ---"+aid);
		List<Admin> admininfo=adservice.getadmininfo(aid);
		model.addAttribute("admininfo", admininfo);
		
		 alertservice.updateGameStatusontime();
		long activealertcount=alertservice.activealertcount();
		model.addAttribute("activealertcount",activealertcount);
		
		long turfactivationcount=turfservice.turfActivationPendingcount();
		model.addAttribute("turfactivationcount",turfactivationcount);
		
		long totalTurfactivatedcount=turfservice.turfActivatedcount();
		model.addAttribute("totalTurfactivatedcount",totalTurfactivatedcount);
		
		long useraccountactivationcount=loginservice.userAccountActivationPendingCount();
		model.addAttribute("useraccountactivationcount",useraccountactivationcount);
		
		long useraccountactivatedcount=loginservice.userAccountActivatedCount();
		model.addAttribute("useraccountactivatedcount",useraccountactivatedcount);
		
		long turfowneraccountactivationcount=loginservice.turfownerAccountActivationPendingCount();
		model.addAttribute("turfowneraccountactivationcount",turfowneraccountactivationcount);
		
		List<Login> activateuser=loginservice.activateuser();
		model.addAttribute("useractivation",activateuser);
		
		List<GameAlerts> activeAlerts=alertservice.findAllActiveAlerts();
		model.addAttribute("activeAlerts",activeAlerts);
		
		return "Admin";
	}
	
	@GetMapping("/activateuser")
	public String activateuser(HttpSession session, Model model)
	{
		int aid=(int) session.getAttribute("id");
		System.out.println("Admin id ---"+aid);
		List<Admin> admininfo=adservice.getadmininfo(aid);
		model.addAttribute("admininfo", admininfo);
		
		List<Login> activateuser=loginservice.activateuser();
		model.addAttribute("useractivation",activateuser);
		
		return "ActivateUser";
	}
	
	@GetMapping("/useractivation/{id}")
	public String useractivation(@PathVariable int id, RedirectAttributes redirectAttributes)
	{
		 String ret="error";
			if(loginservice.accountactivation(id)!=null)
			{
				redirectAttributes.addFlashAttribute("successMessage", "User Activated successfully!");
				ret="redirect:/Admin/activateuser";
			}
			return ret;
	}
	
	@GetMapping("/viewallusers")
	public String viewallactivatedusers(HttpSession session, Model model)
	{
		int aid=(int) session.getAttribute("id");
		System.out.println("Admin id ---"+aid);
		List<Admin> admininfo=adservice.getadmininfo(aid);
		model.addAttribute("admininfo", admininfo);
		
		List<Login> activatedusers=loginservice.activatedusers();
		model.addAttribute("activatedusers",activatedusers);
		return "ViewActivatedUsers";
	}
	
	@GetMapping("/userdeactivation/{id}")
	public String userdeactivation(@PathVariable int id, RedirectAttributes redirectAttributes)
	{
		 String ret="error";
			if(loginservice.accountdeactivation(id)!=null)
			{
				redirectAttributes.addFlashAttribute("successMessage", "User deactivated successfully!");
				ret="redirect:/Admin/viewallusers";
			}
			return ret;
	}
	@GetMapping("/activateturfowner")
	public String activateturfowner(HttpSession session, Model model)
	{
		int aid=(int) session.getAttribute("id");
		System.out.println("Admin id ---"+aid);
		List<Admin> admininfo=adservice.getadmininfo(aid);
		model.addAttribute("admininfo", admininfo);
		
		List<Login> activateturfowner=loginservice.activateturfowner();
		model.addAttribute("turfowneractivation",activateturfowner);
		
		return "ActivateTurfowner";
		
	}
	
	@GetMapping("/turfowneractivation/{id}")
	public String turfowneractivation(@PathVariable int id, RedirectAttributes redirectAttributes)
	{
		 String ret="error";
			if(loginservice.accountactivation(id)!=null)
			{
				redirectAttributes.addFlashAttribute("successMessage", "Turf Owner activated successfully!");
				ret="redirect:/Admin/activateturfowner";
			}
			return ret;
	}
	
	@GetMapping("/viewallturfowners")
	public String viewallactivatedturfowners(HttpSession session, Model model)
	{
		int aid=(int) session.getAttribute("id");
		System.out.println("Admin id ---"+aid);
		List<Admin> admininfo=adservice.getadmininfo(aid);
		model.addAttribute("admininfo", admininfo);
		
		List<Login> activatedturfowners=loginservice.activatedturfowners();
		model.addAttribute("activatedturfowners",activatedturfowners);
		return "ViewActivatedTurfowners";
	}
	
	@GetMapping("/turfownerdeactivation/{id}")
	public String turfownerdeactivation(@PathVariable int id, RedirectAttributes redirectAttributes)
	{
		 String ret="error";
			if(loginservice.accountdeactivation(id)!=null)
			{
				redirectAttributes.addFlashAttribute("successMessage", "Turf Owner deactivated successfully!");
				ret="redirect:/Admin/viewallturfowners";
			}
			return ret;
	}
	
	@GetMapping("/activateturf")
	public String activateturf(HttpSession session, Model model)
	{
		int aid=(int) session.getAttribute("id");
		System.out.println("Admin id ---"+aid);
		List<Admin> admininfo=adservice.getadmininfo(aid);
		model.addAttribute("admininfo", admininfo);
		
		List<TurfDetails> turfs=turfservice.viewnotactivatedturf();
		model.addAttribute("turflist",turfs);
		
		return "ActivateTurf";
		
	}
	@GetMapping("/turfactivation/{id}")
	public String turfactivation(@PathVariable int id,RedirectAttributes redirectAttributes)
	{
		 String ret="error";
			if(turfservice.turfactivation(id)!=null)
			{
				redirectAttributes.addFlashAttribute("successMessage", "Turf activated successfully!");
				ret="redirect:/Admin/activateturf";
			}
			return ret;
	}
	@GetMapping("/viewallturf")
	public String viewallturf(HttpSession session, Model model)
	{
		int aid=(int) session.getAttribute("id");
		System.out.println("Admin id ---"+aid);
		List<Admin> admininfo=adservice.getadmininfo(aid);
		model.addAttribute("admininfo", admininfo);
		
		List<TurfDetails> activatedturfs=turfservice.viewactivatedturf();
		model.addAttribute("activatedturflist",activatedturfs);
		
		return "ViewActivatedTurf";
		
	}
	@GetMapping("/turfdeactivation/{id}")
	public String turfdeactivation(@PathVariable int id, RedirectAttributes redirectAttributes)
	{
		 String ret="error";
			if(turfservice.turfdeactivation(id)!=null)
			{
				redirectAttributes.addFlashAttribute("successMessage", "Turf deactivated successfully!");
				ret="redirect:/Admin/viewallturf";
			}
			return ret;
	}
	
	@GetMapping("/viewactivegamealerts")
	public String viewactivegamealerts(HttpSession session, Model model)
	{
		int aid=(int) session.getAttribute("id");
		System.out.println("Admin id ---"+aid);
		List<Admin> admininfo=adservice.getadmininfo(aid);
		model.addAttribute("admininfo", admininfo);
		
		List<GameAlerts> activeAlerts=alertservice.findAllActiveAlerts();
		model.addAttribute("activeAlerts",activeAlerts);
		return "ViewActiveGameAlerts";
	}
	@GetMapping("/viewgamealertshistory")
	public String viewgamealertshistory(HttpSession session, Model model)
	{
		int aid=(int) session.getAttribute("id");
		System.out.println("Admin id ---"+aid);
		List<Admin> admininfo=adservice.getadmininfo(aid);
		model.addAttribute("admininfo", admininfo);
		
		List<GameAlerts> Alerthistory=alertservice.findAlerthistory();
		model.addAttribute("alerthistory",Alerthistory);
		return "ViewGameAlertsHistory";
	}
	@GetMapping("/gamealertdeactivation/{id}")
	public String gamealertdeactivation(@PathVariable int id, RedirectAttributes redirectAttributes)
	{
		 String ret="error";
			if(alertservice.gamealertdeactivation(id)!=null)
			{
				redirectAttributes.addFlashAttribute("successMessage", "Game Alert deactivated successfully!");
				ret="redirect:/Admin/viewactivegamealerts";
			}
			return ret;
	}
	
	@GetMapping("/addnewadmin")
	public String addnewadmin()
	{
		return "AdminSignUp";
	}
	
}
