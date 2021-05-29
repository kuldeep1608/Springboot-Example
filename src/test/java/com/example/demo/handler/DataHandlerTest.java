package com.example.demo.handler;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.exceptions.ClientTransformException;
import com.example.demo.model.Client;

class DataHandlerTest {

	private DataHandler dataHandler;
	
	private Client c;
	
	@BeforeEach
	void setUp() throws Exception {
		dataHandler = new DataHandler();
		c = new Client("9301205111082", "kuldeep", "singh", "9876543210", "abc");
		dataHandler.createClient(c);
	}

	@Test
	void testCreateClient() {
		Client c = new Client("9308165112083", "kuldeep", "yadav", "9876543211", "abc");
		Client result = dataHandler.createClient(c);
		assertEquals("9308165112083", result.getId());
	}

	@Test
	void testCheckDuplicacy() {
		Client result = dataHandler.checkDuplicacy("9301205111081", "1234567890");
		assertNull(result);
	}

	@Test
	void testCheckDuplicacy_case2() {
		Client result = dataHandler.checkDuplicacy("9301205111082", "1234567890");
		assertNotNull(result);
	}
	
	@Test
	void testCheckIfExist() {
		Client result = dataHandler.checkIfExist("9301205111082", "9876543210");
		assertNotNull(result);
	}

	@Test
	void testRemoveClient() {
		dataHandler.removeClient(c);
	}

	@Test
	void testCheckParameters() {
		Map<String, String> result = dataHandler.checkParameters("9301205111082", "kuldeep", null);
		assertEquals("9301205111082", result.get(DataHandler.ID));
	}

	@Test
	void testCheckParameters_case2() {
		Map<String, String> result = dataHandler.checkParameters(null, null, null);
		assertEquals(0, result.size());
	}
	
	@Test
	void testGetClients() {
		Map<String, String> ma = new HashMap<>();
		ma.put(DataHandler.ID, "9301205111082");
		ma.put(DataHandler.FIRSTNAME, "kuldeep");
		ma.put(DataHandler.MOBILENUMBER, "9876543210");
		List<Client> result = dataHandler.getClients(ma);
		assertNotEquals(0, result.size());
	}
	
	@Test
	void testGetClients_case2() {
		Map<String, String> ma = new HashMap<>();
		ma.put(DataHandler.FIRSTNAME, "abc");
		List<Client> result = dataHandler.getClients(ma);
		assertEquals(0, result.size());
	}

	@Test
	void testValidate() {
		dataHandler.validate("9301205111082");
	}
	
	@Test
	void testValidate_exception() {
		assertThrows(ClientTransformException.class,() ->dataHandler.validate("1"));
	}

}
