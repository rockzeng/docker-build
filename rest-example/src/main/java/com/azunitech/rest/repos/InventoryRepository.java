package com.azunitech.rest.repos;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Named;

import org.apache.log4j.Logger;

import com.azunitech.rest.domain.Inventory;
import com.azunitech.rest.domain.Product;
import com.google.inject.Inject;

public class InventoryRepository implements IRepository{
	private static Logger s_Logger = Logger.getLogger(InventoryRepository.class);
	@Named("memcached")
	@Inject 
	IDataAdapter connectionAdapter;

	@Override
	public Inventory getInventory( long id){
		Inventory result = null;
		try {
			result = connectionAdapter.getInvetory(id);
		} 
		catch (IOException e) {
			s_Logger.warn(e);
		}
		return result;
	}

	@Override
	public void remove(long id, String name) {
		try {
			connectionAdapter.removeProductor(id, name);
		} 
		catch (IOException e) {
		}
	}

	@Override
	public Inventory updateProduct(long id, String name, float price, String description) {
		Inventory result = null;
		try {
			Product p = new Product(name, price);
			p.setDescription(description);
			result = connectionAdapter.updateProductor(id, p);
		} 
		catch (IOException e) {
		}
		return result;
	}

	@Override
	public void create(long id, String name, float price, String description) {
	}
}
