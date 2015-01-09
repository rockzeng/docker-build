package com.azunitech.rest;

import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.azunitech.rest.domain.Inventory;
import com.azunitech.rest.repos.IRepository;
import com.azunitech.rest.repos.InventoryRepository;
import com.google.inject.Inject;

@Path("/api/inventory")
public class InventoryService {
	private static Logger s_Logger = Logger.getLogger(InventoryService.class);
	private static AtomicInteger ai = new AtomicInteger();
	
	@Inject
	private IRepository repo;
	
	@GET 
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Inventory getInvetory( @PathParam(value = "id") long id){
		s_Logger.info(id);
		int i = ai.get();
		s_Logger.info(i);
		return repo.getInventory(id);
	}
	 
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String createInvetory( @FormParam(value = "id") long id,
		@FormParam(value = "name") String name,
		@FormParam(value = "price") String price,
		@FormParam(value = "description") String description){
		s_Logger.info(id);
		repo.create( id, name, Float.parseFloat(price), description);
		ai.set(1);
		return "OK";
	}
 
	@DELETE
	@Path("/{id}/name/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String removeInventory( @PathParam(value = "id") long id,
		@PathParam(value = "name") String name){
		repo.remove(id, name);
		return "OK";
	}
 
	@PUT
	@Path("/{id}/name/{name}/price/{price}/description/{desc}")
	public Inventory AddProduct( @PathParam(value = "id") long id,
		@PathParam(value = "name") String name,
		@PathParam(value = "price") String price,
		@PathParam(value = "desc") String desc){
	return repo.updateProduct(id, name, Float.parseFloat(price), desc);
	}	
}
