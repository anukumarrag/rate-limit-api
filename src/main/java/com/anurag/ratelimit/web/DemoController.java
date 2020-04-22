package com.anurag.ratelimit.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

 
@RestController
public class DemoController
{
    
    @GetMapping("/developer")
    public ResponseEntity<String> getAllEmployeesName() {
 
        return new ResponseEntity<String>("Success Response From /developer", new HttpHeaders(), HttpStatus.OK);
    }
 
    @GetMapping("/organization")
    public ResponseEntity<String> getAllEmployees() {
    	 return new ResponseEntity<String>("Success Response From /organization", new HttpHeaders(), HttpStatus.OK);
    }
 
    
}