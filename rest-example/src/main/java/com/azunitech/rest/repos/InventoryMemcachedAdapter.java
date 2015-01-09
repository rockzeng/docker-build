package com.azunitech.rest.repos;

import java.io.IOException;
import java.util.Properties;
import javax.inject.Named;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import org.apache.log4j.Logger;
import com.azunitech.rest.domain.Inventory;
import com.azunitech.rest.domain.Product;

@Named("memcached")
public class InventoryMemcachedAdapter implements IDataAdapter{
	private Logger s_Logger = Logger.getLogger(InventoryMemcachedAdapter.class);
	private static final int Hours_24 = 60*60;
	private MemcachedClient m_Client;
	Properties prop = new Properties();
	
	public InventoryMemcachedAdapter(){
		try {
		    prop.load(this.getClass().getClassLoader().getResourceAsStream("memcached.properties"));
		    System.out.println(prop.getProperty("MEMCACHED.SERVER.URL"));

		} 
		catch (IOException ex) {
		    ex.printStackTrace();
		}
	}
	
	@Override
	public Inventory getInvetory(long id) throws IOException {
		s_Logger.info(id);
		Inventory result = null;
		try	{
			String url = prop.getProperty("MEMCACHED.SERVER.URL");
			this.m_Client = new MemcachedClient(AddrUtil.getAddresses(url));
			result = this.getInvetoryHelper(id);
		}
		catch(Exception ex){
			s_Logger.info(ex.getMessage());
		}
		finally{
			this.m_Client.shutdown();
		}
		s_Logger.info(result);
		return result;
	}

	@Override
	public Inventory addProductor(long id, Product prod) throws IOException {
		s_Logger.info( new Object[]{id, prod});
		Inventory result = null;
		try	{
			String url = prop.getProperty("MEMCACHED.SERVER.URL");
			this.m_Client = new MemcachedClient(AddrUtil.getAddresses(url));
			result = this.addProductorHelper(id, prod);
		}
		finally{
			this.m_Client.shutdown();
		}
		s_Logger.info(result);
		return result;
	}

	@Override
	public Inventory removeProductor(long id, String name) throws IOException {
		s_Logger.info( new Object[]{id, name});
		Inventory result = null;
		try	{
			String url = prop.getProperty("MEMCACHED.SERVER.URL");
			this.m_Client = new MemcachedClient(AddrUtil.getAddresses(url));
			result = this.removeProductorHelper(id, name);
		}
		finally{
			this.m_Client.shutdown();
		}
		s_Logger.info(result);
		return result;
	}

	@Override
	public Inventory updateProductor(long id, Product prod) throws IOException {
		s_Logger.info( new Object[]{id, prod});
		Inventory result = null;
		try	{
			String url = prop.getProperty("MEMCACHED.SERVER.URL");
			this.m_Client = new MemcachedClient(AddrUtil.getAddresses(url));
			result = this.updateProductorHelper(id, prod);
		}
		finally{
			this.m_Client.shutdown();
		}
		s_Logger.info(result);
		return result;
	}
	
	private Inventory getInvetoryHelper(long id) throws IOException {
		Inventory result = null;
		String key = String.valueOf(id);
		Object object = this.m_Client.get(key);
		if ( object == null){
			Inventory inventory = new Inventory(id);
			this.m_Client.set(key, Hours_24, inventory);
			object = this.m_Client.get(key);
		}
		result = (Inventory) object;
		return result;
	}
	
	private Inventory addProductorHelper(long id, Product prod) throws IOException {
		String key = String.valueOf(id);
		Inventory inventory = getInvetoryHelper(id);
		inventory.addProduct(prod);
		this.m_Client.set(key, Hours_24, inventory);
		return inventory;
	}

	private Inventory removeProductorHelper(long id, String name) throws IOException {
		String key = String.valueOf(id);
		Inventory inventory = getInvetoryHelper(id);
		inventory.removeProduct(name);
		this.m_Client.set(key, Hours_24, inventory);
		return inventory;
	}

	private Inventory updateProductorHelper(long id, Product prod) throws IOException {
		String key = String.valueOf(id);
		Inventory inventory = getInvetoryHelper(id);
		inventory.removeProduct(prod.getName());
		inventory.addProduct(prod);
		this.m_Client.set(key, Hours_24, inventory);
		return inventory;
	}


	
}
