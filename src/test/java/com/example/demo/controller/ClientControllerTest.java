package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.model.Client;
import com.example.demo.service.ClientService;

class ClientControllerTest {

	@InjectMocks
	private ClientController clientController;
	
	@Mock
	private ClientService clientService;
	
	private Client c;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		c = new Client("9301205111082", "kuldeep", "singh", "9876543210", "abc");
	}

	@Test
	void testAddClient() {
		ResponseEntity<Client> result = clientController.addClient(c);
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
	}
	

	@Test
	void testUpdateClient() {
		ResponseEntity<Client> result = clientController.updateClient(c);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	

	@Test
	void testGetClient() {
		ResponseEntity<List<Client>> result = clientController.getClient(null, "kuldeep", null);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	

}
