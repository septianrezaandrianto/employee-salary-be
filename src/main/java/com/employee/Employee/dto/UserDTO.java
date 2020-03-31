package com.employee.Employee.dto;

import java.io.Serializable;

public class UserDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private UserIdDTO id;
	private String password;
	private Short status;
	
	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDTO(UserIdDTO id, String password, Short status) {
		super();
		this.id = id;
		this.password = password;
		this.status = status;
	}

	public UserIdDTO getid() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public Short getStatus() {
		return status;
	}

	public void setid(UserIdDTO id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
	
}
