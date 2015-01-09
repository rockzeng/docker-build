package com.azunitech.rest.repos;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Named;

import com.azunitech.rest.domain.Inventory;
import com.azunitech.rest.domain.Product;

@Named("collection")
public class CollectionAdapter implements IDataAdapter{
	private Map<Long, Inventory> data = new ConcurrentHashMap<Long, Inventory>();

	@Override
	public Inventory getInvetory(long id) throws IOException {
		Inventory result = null;
		if ( data.containsKey(id)){
			result = data.get(id);
		}
		return result;
	}

	@Override
	public Inventory addProductor(long id, Product prod) throws IOException {
		Inventory inventory = getInvetory(id);
		inventory.addProduct(prod);
		return inventory;
	}

	@Override
	public Inventory removeProductor(long id, String name) throws IOException {
		Inventory item = data.get(id);
		if ( item != null){
			item.removeProduct(name);
		}
		return item;
	}

	@Override
	public Inventory updateProductor(long id, Product prod) throws IOException {
		Inventory item = data.get(id);
		item.removeProduct(prod.getName());
		item.addProduct(prod);
		return item;
	}

}
