package com.employee.Employee.dto;

import java.io.Serializable;

public class UserDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int idUser;
	private String username;
	private String password;
	private Short status;
	
	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDTO(int idUser, String username, String password, Short status) {
		super();
		this.idUser = idUser;
		this.username = username;
		this.password = password;
		this.status = status;
	}

	public int getIdUser() {
		return idUser;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Short getStatus() {
		return status;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	
}
