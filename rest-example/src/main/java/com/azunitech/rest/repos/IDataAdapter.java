package com.azunitech.rest.repos;

import java.io.IOException;

import com.azunitech.rest.domain.Inventory;
import com.azunitech.rest.domain.Product;

public interface IDataAdapter {
	Inventory getInvetory( long id) throws IOException;
	Inventory addProductor( long id, Product prod) throws IOException;
	Inventory removeProductor( long id, String name) throws IOException;
	Inventory updateProductor( long id, Product prod) throws IOException;
}
