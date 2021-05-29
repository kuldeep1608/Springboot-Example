package com.example.demo.handler;

import java.util.*;

import org.springframework.stereotype.Component;

import com.example.demo.model.Client;

@Component
public class DataHandler {
	
	private List<Client> clientList = new ArrayList<>(); 

	public static final String ID = "id";
	public static final String FIRSTNAME ="firstName";
	public static final String MOBILENUMBER = "mobileNumber";
	
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

	
}
