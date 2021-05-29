package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.ClientTransformException;
import com.example.demo.exceptions.ClientTransformException.ErrorCode;
import com.example.demo.handler.DataHandler;
import com.example.demo.model.Client;

@RestController
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	private DataHandler dataHandler;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(method =RequestMethod.POST)
	public ResponseEntity<Client> addClient(@RequestBody Client client){
		dataHandler.validate(client.getId());
		Client existingClient = dataHandler.checkDuplicacy(client.getId(),client.getMobileNumber());
		Client resp = null ;
		if(existingClient==null ) {
			resp = dataHandler.createClient(client);
		}else {
			logger.error("Either Id or Mobile number is Duplicate ");
			throw new ClientTransformException("Either Id or Mobile number is Duplicate ", ErrorCode.DUPLICATE_ID_OR_MOBILENUMBER);
		}
		return new ResponseEntity<>(resp, HttpStatus.CREATED);
	}
	
	@RequestMapping( method =RequestMethod.PUT)
	public ResponseEntity<Client> updateClient(@RequestBody Client client){
		Client existingClient = dataHandler.checkIfExist(client.getId(),client.getMobileNumber());
		Client resp = null ;
		if(existingClient!=null ) {
			dataHandler.removeClient(existingClient);
			resp = dataHandler.createClient(client);
		}else {
			logger.error("Either Id or Mobile number already exist");
			throw new ClientTransformException("Either Id or Mobile number already exist", ErrorCode.DUPLICATE_ID_OR_MOBILENUMBER);
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
		
	}
	
	@RequestMapping(method =RequestMethod.GET)
	public ResponseEntity<List<Client>> getClient(@RequestParam(required=false) String id,
			@RequestParam(required=false) String firstName, 
			@RequestParam(required=false) String mobileNumber){
		Map<String,String> parameters = dataHandler.checkParameters(id,firstName,mobileNumber);
		List<Client> clients = new ArrayList<>();
		if(parameters.isEmpty()) {
			throw new ClientTransformException("All parameters are null", ErrorCode.MISSING_REQUEST_PARAMETER);
		}else {
			clients = dataHandler.getClients(parameters);
			if(clients.isEmpty()) {
				logger.error("No data found");
				throw new ClientTransformException("No data found", ErrorCode.NO_OBJECT_FOUND);
			}
		}
		return new ResponseEntity<>(clients, HttpStatus.OK);
		
	}

}
