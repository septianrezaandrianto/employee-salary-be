package com.employee.Employee.dto;

import java.io.Serializable;

public class UserDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private UserIdDTO idDto;
	private String password;
	private Short status;
	
	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserIdDTO getIdDto() {
		return idDto;
	}

	public String getPassword() {
		return password;
	}

	public Short getStatus() {
		return status;
	}

	public void setIdDto(UserIdDTO idDto) {
		this.idDto = idDto;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
	
}
