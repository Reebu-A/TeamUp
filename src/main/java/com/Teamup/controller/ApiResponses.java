package com.Teamup.controller;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Teamup.service.TurfdetailService;

@RestController
@RequestMapping("apicontroller")
public class ApiResponses 
{
	@Autowired
	private TurfdetailService tdservice;

	
	
	@GetMapping("/api/booked-slots")
    public List<LocalTime> getBookedSlots(@RequestParam String turfName, @RequestParam String date) {
 
		System.out.println("api called****///////");
		
        return tdservice.getBookedSlots(turfName, date);
    }
}
