package com.employee.Employee.dto;
// Generated Mar 30, 2020 8:42:38 AM by Hibernate Tools 4.0.1.Final

/**
 * Posisi generated by hbm2java
 */
public class PosisiDTO{

	private int idPosisi;
	private String namaPosisi;

	public PosisiDTO() {
	}

	public PosisiDTO(int idPosisi, String namaPosisi) {
		this.idPosisi = idPosisi;
		this.namaPosisi = namaPosisi;
	}


	public int getIdPosisi() {
		return this.idPosisi;
	}

	public void setIdPosisi(int idPosisi) {
		this.idPosisi = idPosisi;
	}


	public String getNamaPosisi() {
		return this.namaPosisi;
	}

	public void setNamaPosisi(String namaPosisi) {
		this.namaPosisi = namaPosisi;
	}


}
