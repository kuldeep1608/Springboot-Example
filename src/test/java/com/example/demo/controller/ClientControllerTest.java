package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.exceptions.ClientTransformException;
import com.example.demo.handler.DataHandler;
import com.example.demo.model.Client;

class ClientControllerTest {

	@InjectMocks
	private ClientController clientController;
	
	@Mock
	private DataHandler dataHandler;
	
	private Client c;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		c = new Client("9301205111082", "kuldeep", "singh", "9876543210", "abc");
	}

	@Test
	void testAddClient() {
		when(dataHandler.checkDuplicacy(Matchers.anyString(), Matchers.anyString())).thenReturn(null);
		ResponseEntity<Client> result = clientController.addClient(c);
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
	}
	
	@Test
	void testAddClient_exception() {
		assertThrows(ClientTransformException.class,() -> {
			when(dataHandler.checkDuplicacy("9301205111082", "9876543210")).thenReturn(c);
			clientController.addClient(c);
		});
	}

	@Test
	void testUpdateClient() {
		when(dataHandler.checkIfExist("9301205111082", "9876543210")).thenReturn(c);
		ResponseEntity<Client> result = clientController.updateClient(c);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	void testUpdateClient_exception() {
		assertThrows(ClientTransformException.class,() -> {
			when(dataHandler.checkIfExist(Matchers.anyString(), Matchers.anyString())).thenReturn(null);
			clientController.updateClient(c);
		});
		
	}

	@Test
	void testGetClient() {
		Map<String,String> parameters = new HashMap<>();
		parameters.put(DataHandler.FIRSTNAME, "kuldeep");
		when(dataHandler.checkParameters(null, "kuldeep", null)).thenReturn(parameters);
		when(dataHandler.getClients(parameters)).thenReturn(Arrays.asList(c));
		ResponseEntity<List<Client>> result = clientController.getClient(null, "kuldeep", null);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	void testGetClient_exception1() {
		assertThrows(ClientTransformException.class,() -> {
			Map<String,String> parameters = new HashMap<>();
			when(dataHandler.checkParameters(null, null, null)).thenReturn(parameters);
			clientController.getClient(null, null, null);
		});
	}
	
	@Test
	void testGetClient_exception2() {
		assertThrows(ClientTransformException.class,() -> {
			Map<String,String> parameters = new HashMap<>();
			parameters.put(DataHandler.FIRSTNAME, "abc");
			when(dataHandler.checkParameters(null, "abc", null)).thenReturn(parameters);
			when(dataHandler.getClients(parameters)).thenReturn(Collections.EMPTY_LIST);
			clientController.getClient(null, "abc", null);
		});
	}

}
