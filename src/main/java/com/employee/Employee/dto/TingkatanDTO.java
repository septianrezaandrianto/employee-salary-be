package com.employee.Employee.dto;

public class TingkatanDTO {

	private int idTingkatan;
	private String namaTingkatan;
	
	
	public TingkatanDTO() {
		super();
	}
	
	
	public TingkatanDTO(int idTingkatan, String namaTingkatan) {
		super();
		this.idTingkatan = idTingkatan;
		this.namaTingkatan = namaTingkatan;
	}
	
	
	public int getIdTingkatan() {
		return idTingkatan;
	}
	public void setIdTingkatan(int idTingkatan) {
		this.idTingkatan = idTingkatan;
	}
	public String getNamaTingkatan() {
		return namaTingkatan;
	}
	public void setNamaTingkatan(String namaTingkatan) {
		this.namaTingkatan = namaTingkatan;
	}
	
	
}
