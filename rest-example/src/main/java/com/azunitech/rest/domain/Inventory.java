package com.azunitech.rest.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="inventory")
@XmlAccessorType(XmlAccessType.FIELD)
public class Inventory implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private List<Product> items;
	
	public Inventory(){
		items = new ArrayList<Product>();
	}
	
	public Inventory( long id){
		this();
		this.id = id;
	}
	
	public void addProduct( Product prod){
		this.items.add(prod);
	}
	
	public void removeProduct( String name){
		Iterator<Product> it = items.iterator();
		while( it.hasNext()){
			Product prod = it.next();
			if ( prod.getName() == name){
				it.remove();
			}
		}
	}

	public void updateProduct(String name, float price, String description) {
		Iterator<Product> it = items.iterator();
		while( it.hasNext()){
			Product prod = it.next();
			if ( prod.getName() == name){
				prod.setPrice( price);
				prod.setDescription(description);
			}
		}
	}
	
	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("inventory id: ").append(this.id).append(" ");
		for( Product p : this.items){
			buffer.append("   " + p.toString()).append(" ");
		}
		return buffer.toString();
	}
}
