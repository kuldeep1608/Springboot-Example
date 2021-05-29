package com.example.demo.service;

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

import com.example.demo.exceptions.ClientTransformException;
import com.example.demo.handler.DataHandler;
import com.example.demo.model.Client;

class ClientServiceTest {

	@InjectMocks
	private ClientService clientService;
	
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
		when(dataHandler.createClient(c)).thenReturn(c);
		Client result = clientService.addClient(c);
		assertNotNull(result);
	}

	@Test
	void testAddClient_exception() {
		assertThrows(ClientTransformException.class,() -> {
			when(dataHandler.checkDuplicacy("9301205111082", "9876543210")).thenReturn(c);
			clientService.addClient(c);
		});
	}

	@Test
	void testUpdateClient() {
		when(dataHandler.checkIfExist("9301205111082", "9876543210")).thenReturn(c);
		when(dataHandler.createClient(c)).thenReturn(c);
		Client result = clientService.updateClient(c);
		assertNotNull(result);
	}
	
	@Test
	void testUpdateClient_exception() {
		assertThrows(ClientTransformException.class,() -> {
			when(dataHandler.checkIfExist(Matchers.anyString(), Matchers.anyString())).thenReturn(null);
			clientService.updateClient(c);
		});
		
	}

	@Test
	void testGetClient() {
		Map<String,String> parameters = new HashMap<>();
		parameters.put(DataHandler.FIRSTNAME, "kuldeep");
		when(dataHandler.getClients(parameters)).thenReturn(Arrays.asList(c));
		List<Client> result = clientService.getClients(null, "kuldeep", null);
		assertNotEquals(0, result.size());
	}
	
	@Test
	void testGetClient_exception1() {
		assertThrows(ClientTransformException.class,() -> {
			clientService.getClients(null, null, null);
		});
	}
	
	@Test
	void testGetClient_exception2() {
		assertThrows(ClientTransformException.class,() -> {
			Map<String,String> parameters = new HashMap<>();
			parameters.put(DataHandler.FIRSTNAME, "abc");
			when(dataHandler.getClients(parameters)).thenReturn(Collections.emptyList());
			clientService.getClients(null, "abc", null);
		});
	}


	@Test
	void testValidate() {
		clientService.validate("9301205111082");
	}
	
	@Test
	void testValidate_exception() {
		assertThrows(ClientTransformException.class,() ->clientService.validate("1"));
	}

	@Test
	void testCheckParameters() {
		Map<String, String> result = clientService.checkParameters("9301205111082", "kuldeep", null);
		assertEquals("9301205111082", result.get(DataHandler.ID));
	}

	@Test
	void testCheckParameters_case2() {
		Map<String, String> result = clientService.checkParameters(null, null, null);
		assertEquals(0, result.size());
	}

}
