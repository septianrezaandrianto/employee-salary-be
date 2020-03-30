package com.employee.Employee.dto;

import java.io.Serializable;

public class UserIdDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private int idUser;
	private String username;
	 
	public UserIdDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserIdDTO(int idUser, String username) {
		super();
		this.idUser = idUser;
		this.username = username;
	}
	public int getIdUser() {
		return idUser;
	}
	public String getUsername() {
		return username;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
