package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Client;
import com.example.demo.service.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	
	
	@RequestMapping(method =RequestMethod.POST)
	public ResponseEntity<Client> addClient(@RequestBody Client client){
		Client resp = clientService.addClient(client) ;
		return new ResponseEntity<>(resp, HttpStatus.CREATED);
	}
	
	@RequestMapping( method =RequestMethod.PUT)
	public ResponseEntity<Client> updateClient(@RequestBody Client client){
		Client resp = clientService.updateClient(client) ;
		return new ResponseEntity<>(resp, HttpStatus.OK);
		
	}
	
	@RequestMapping(method =RequestMethod.GET)
	public ResponseEntity<List<Client>> getClient(@RequestParam(required=false) String id,
			@RequestParam(required=false) String firstName, 
			@RequestParam(required=false) String mobileNumber){
		List<Client> clients = clientService.getClients(id, firstName, mobileNumber);
		return new ResponseEntity<>(clients, HttpStatus.OK);
		
	}

}
