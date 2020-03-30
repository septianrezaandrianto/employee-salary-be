package com.employee.Employee.dto;

import java.math.BigDecimal;

public class PenempatanDTO {

	private int idPenempatan;
	private String kotaPenempatan;
	private BigDecimal umkPenempatan;

	public PenempatanDTO() {
	}

	public PenempatanDTO(int idPenempatan, String kotaPenempatan, BigDecimal umkPenempatan) {
		this.idPenempatan = idPenempatan;
		this.kotaPenempatan = kotaPenempatan;
		this.umkPenempatan = umkPenempatan;
	}

	public int getIdPenempatan() {
		return this.idPenempatan;
	}

	public void setIdPenempatan(int idPenempatan) {
		this.idPenempatan = idPenempatan;
	}
	public String getKotaPenempatan() {
		return this.kotaPenempatan;
	}

	public void setKotaPenempatan(String kotaPenempatan) {
		this.kotaPenempatan = kotaPenempatan;
	}

	public BigDecimal getUmkPenempatan() {
		return this.umkPenempatan;
	}

	public void setUmkPenempatan(BigDecimal umkPenempatan) {
		this.umkPenempatan = umkPenempatan;
	}

}
