package com.azunitech.rest.repos;

import com.azunitech.rest.domain.Inventory;

public interface IRepository {
	Inventory getInventory( long id);
	void create(long id, String name, float price, String description); 
	void remove(long id, String name);
	Inventory updateProduct(long id, String name, float price, String description);
}
