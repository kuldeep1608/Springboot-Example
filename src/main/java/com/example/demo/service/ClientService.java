package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.ClientTransformException;
import com.example.demo.exceptions.ClientTransformException.ErrorCode;
import com.example.demo.handler.DataHandler;
import com.example.demo.model.Client;

@Service
public class ClientService {

	@Autowired
	private DataHandler dataHandler;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/*
	 * The first 6 digits (YYMMDD) are based on your date of birth. 20 February 1992
	 * is displayed as 920220. 
	 * The next 4 digits (SSSS) are used to define your
	 * gender. Females are assigned numbers in the range 0000-4999 and males from
	 * 5000-9999. 
	 * The next digit (C) shows if you're an SA citizen status with 0
	 * denoting that you were born a SA citizen and 1 denoting that you're a
	 * permanent resident. 
	 * The last digit (Z) is a checksum digit
	 */
	private String SouthAfricanIdValidation = "(?<Year>[0-9][0-9])(?<Month>([0][1-9])|([1][0-2]))(?<Day>([0-2][0-9])|([3][0-1]))(?<Gender>[0-9])(?<Series>[0-9]{3})(?<Citizenship>[0-9])(?<Uniform>[0-9])(?<Control>[0-9])"; 
	
	public Client addClient(Client client) {
		validate(client.getId());
		Client existingClient = dataHandler.checkDuplicacy(client.getId(),client.getMobileNumber());
		Client resp = null ;
		if(existingClient==null ) {
			resp = dataHandler.createClient(client);
		}else {
			logger.error("Either Id or Mobile number is Duplicate ");
			throw new ClientTransformException("Either Id or Mobile number is Duplicate ", ErrorCode.DUPLICATE_ID_OR_MOBILENUMBER);
		}
		return resp;
	}
	
	public Client updateClient(Client client) {
		Client existingClient = dataHandler.checkIfExist(client.getId(),client.getMobileNumber());
		Client resp = null ;
		if(existingClient!=null ) {
			dataHandler.removeClient(existingClient);
			resp = dataHandler.createClient(client);
		}else {
			logger.error("Either Id or Mobile number already exist");
			throw new ClientTransformException("Either Id or Mobile number already exist", ErrorCode.DUPLICATE_ID_OR_MOBILENUMBER);
		}
		return resp;
	}
	
	public List<Client> getClients(String id, String firstName, String mobileNumber){
		Map<String,String> parameters = checkParameters(id,firstName,mobileNumber);
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
		return clients;
	}
	
	public void validate(String id) {
		Pattern p = Pattern.compile(SouthAfricanIdValidation);
		Matcher m = p.matcher(id);
		if(!(m.find() && m.group().equals(id))) {
			logger.error("Invalid id");
			throw new ClientTransformException("Invalid id", ErrorCode.INVALID_ID);
		}
	}
	
	public Map<String, String> checkParameters(String id, String firstName, String mobileNumber) {
		Map<String,String> parameters = new HashMap<>();
		if(id!=null) {
			parameters.put("id", id);
		}else if(mobileNumber!=null) {
			parameters.put("mobileNumber", mobileNumber);
		}else if(firstName!=null) {
			parameters.put("firstName", firstName);
		}
		return parameters;
	}
}
