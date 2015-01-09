package com.azunitech.rest;

import javax.inject.Named;

import com.azunitech.rest.repos.CollectionAdapter;
import com.azunitech.rest.repos.IDataAdapter;
import com.azunitech.rest.repos.IRepository;
import com.azunitech.rest.repos.InventoryMemcachedAdapter;
import com.azunitech.rest.repos.InventoryRepository;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class RestfulModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(IRepository.class).to(InventoryRepository.class).asEagerSingleton();
		bind(IDataAdapter.class).annotatedWith(Names.named("memcached")).to(InventoryMemcachedAdapter.class).asEagerSingleton();
		bind(IDataAdapter.class).annotatedWith(Names.named("collection")).to(CollectionAdapter.class).asEagerSingleton();
	}
}
