package com.azunitech.rest.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="product")
@XmlAccessorType(XmlAccessType.FIELD)
public class Product implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private float price;
	private String description;
	
	public Product(){}
	
	public Product( String name, float price){
		this.name = name;
		this.price = price;
	}
	
	public String getName(){
		return this.name;
	}
	
	public float getPrice(){
		return this.price;
	}
	
	public void setDescription( String desc){
		this.description = desc;
	}
	
	public String getDescription(){
		return this.description;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("name :" ).append(this.name).append(" ");
		buffer.append("price :" ).append(this.price).append(" ");
		buffer.append("description :" ).append(this.description).append(" ");
		return buffer.toString();
	}
}
