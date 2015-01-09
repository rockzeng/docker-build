package com.azunitech.rest.repos;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import com.azunitech.rest.domain.Inventory;

public class InventoryMemcachedAdapterTest {
	private IDataAdapter adapter;
	
	@Before
	public void setup(){
		adapter = new InventoryMemcachedAdapter();
	}
	
	@Test
	public void generalTest() throws IOException{
		Inventory inventoiry = adapter.getInvetory(100l);
		assertNotNull(inventoiry);
	}
}
