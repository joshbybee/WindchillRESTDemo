package com.hmk.rest;

import com.fasterxml.jackson.annotation.JsonProperty;


public class HMKUpdateResponse {

	private String name;
	private String ID;
	private String message;
	
	public HMKUpdateResponse(String id){
		this.ID = id;
	}
	
	@JsonProperty("ID")
	public String getID() {
		return ID;
	}

	@JsonProperty("Name")
	public String getName() {
		return name;
	}
	
	@JsonProperty("Message")
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public void setName(String Name){
		this.name = Name;
	}
}