package com.azunitech.rest.repos;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public class InventoryRepositoryTest {
	private Injector injector;
	private IRepository m_Repo;
	
	@Before
	public void setup(){
		injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
        		bind(IRepository.class).to(InventoryRepository.class).asEagerSingleton();
        		bind(IDataAdapter.class).annotatedWith(Names.named("memcached")).to(InventoryMemcachedAdapter.class).asEagerSingleton();
            }
        });	
		
		m_Repo = (IRepository) injector.getInstance(IRepository.class);
		this.m_Repo.create(100l,  "DISK", 100.99f, "DISK for testing");
	}
	
	@Test
	public void general(){
		this.m_Repo.getInventory(100l);
	}
}
