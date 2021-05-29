package com.example.demo.handler;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.example.demo.exceptions.ClientExceptionHandler;
import com.example.demo.exceptions.ClientTransformException;
import com.example.demo.exceptions.ClientTransformException.ErrorCode;
import com.example.demo.model.Client;

@Component
public class DataHandler {
	
	private List<Client> clientList = new ArrayList<>(); 

	public static final String ID = "id";
	public static final String FIRSTNAME ="firstName";
	public static final String MOBILENUMBER = "mobileNumber";
	
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
	
	public Client createClient(Client client) {
		clientList.add(client);
		return clientList.get(clientList.size()-1);
		
	}

	public Client checkDuplicacy(String id, String mobileNumber) {
		Optional<Client> clientObj = clientList.stream().filter(c -> c.getId().equals(id) || c.getMobileNumber().equals(mobileNumber)).findFirst();
		return clientObj.orElse(null);
	}

	public Client checkIfExist(String id, String mobileNumber) {
		Optional<Client> clientObj = clientList.stream().filter(c -> c.getId().equals(id) && c.getMobileNumber().equals(mobileNumber) ).findFirst();
		return clientObj.orElse(null);
	}
	
	public void removeClient(Client existingClient) {
		clientList.remove(existingClient);
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

	public List<Client> getClients(Map<String, String> parameters) {
		List<Client> clients = new ArrayList<>();
		for(Client client : clientList) {
			for(Map.Entry<String, String> params : parameters.entrySet()) {
				switch (params.getKey()) {
				case ID:{
					if(params.getValue().equals(client.getId())){
							clients.add(client);
					}
					break;
				}
				case FIRSTNAME:{//firstname can be same for multiple clients.so returning a list
					if(params.getValue().equals(client.getFirstName())){
							clients.add(client);
					}
					break;
				}
				case MOBILENUMBER:{
					if(params.getValue().equals(client.getMobileNumber())){
							clients.add(client);
					}
					break;
				}
				default:
					break;
				}
			}
		}
		return clients;
	}

	public void validate(String id) {
		Pattern p = Pattern.compile(SouthAfricanIdValidation);
		Matcher m = p.matcher(id);
		if(!(m.find() && m.group().equals(id))) {
			throw new ClientTransformException("Invalid id", ErrorCode.INVALID_ID);
		}
	}
}
