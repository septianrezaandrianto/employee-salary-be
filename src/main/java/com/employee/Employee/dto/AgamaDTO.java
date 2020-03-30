package com.employee.Employee.dto;

import java.io.Serializable;

public class AgamaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idAgama;
	private String namaAgama;
	
	public AgamaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AgamaDTO(int idAgama, String namaAgama) {
		super();
		this.idAgama = idAgama;
		this.namaAgama = namaAgama;
	}

	public int getIdAgama() {
		return idAgama;
	}

	public String getNamaAgama() {
		return namaAgama;
	}

	public void setIdAgama(int idAgama) {
		this.idAgama = idAgama;
	}

	public void setNamaAgama(String namaAgama) {
		this.namaAgama = namaAgama;
	}
	
}
