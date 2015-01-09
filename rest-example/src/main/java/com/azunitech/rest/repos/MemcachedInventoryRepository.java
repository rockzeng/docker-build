package com.azunitech.rest.repos;

import java.io.IOException;

import javax.inject.Named;

import org.apache.log4j.Logger;

import com.azunitech.rest.domain.Inventory;
import com.google.inject.Inject;

@Named("memcached")
public class MemcachedInventoryRepository implements IRepository {
	private static final Logger s_Logger = Logger.getLogger(MemcachedInventoryRepository.class);
	
	@Named("collection")
	@Inject
	private IDataAdapter adapter;

	@Override
	public Inventory getInventory(long id) {
		s_Logger.info(id);
		Inventory result = null;
		try {
			result = adapter.getInvetory(id);
		} 
		catch (IOException e) {
		}
		s_Logger.info(result);
		return result;
	}

	@Override
	public void create(long id, String name, float price, String description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(long id, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Inventory updateProduct(long id, String name, float price,
			String description) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
