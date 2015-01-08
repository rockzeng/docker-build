package com.azunitech.rest.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="result")
@XmlAccessorType(XmlAccessType.FIELD)
public class Result {
	@XmlElement(required=true, name="action") 
	private ActionType action;

	@XmlElement(required=true, name="key") 
	private String key;
	
	@XmlElement(required=true, name="value") 
	private String url;
	
	public void setUrl( String url ){
		this.url = url;
	}
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("url: ").append(this.url);
		return buffer.toString();
	}
}
