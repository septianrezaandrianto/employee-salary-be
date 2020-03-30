package com.employee.Employee.dto;

import java.math.BigDecimal;

import com.employee.Employee.model.Posisi;

public class PresentaseGajiDTO {

	private int idPresentaseGaji;
	private Posisi posisi;
	private Integer idTingkatan;
	private BigDecimal besaranGaji;
	private Integer masaKerja;
	
	
	public PresentaseGajiDTO() {
		super();
	}
	
	
	public PresentaseGajiDTO(int idPresentaseGaji, Posisi posisi, Integer idTingkatan, BigDecimal besaranGaji,
			Integer masaKerja) {
		super();
		this.idPresentaseGaji = idPresentaseGaji;
		this.posisi = posisi;
		this.idTingkatan = idTingkatan;
		this.besaranGaji = besaranGaji;
		this.masaKerja = masaKerja;
	}
	
	
	public int getIdPresentaseGaji() {
		return idPresentaseGaji;
	}
	public void setIdPresentaseGaji(int idPresentaseGaji) {
		this.idPresentaseGaji = idPresentaseGaji;
	}
	public Posisi getPosisi() {
		return posisi;
	}
	public void setPosisi(Posisi posisi) {
		this.posisi = posisi;
	}
	public Integer getIdTingkatan() {
		return idTingkatan;
	}
	public void setIdTingkatan(Integer idTingkatan) {
		this.idTingkatan = idTingkatan;
	}
	public BigDecimal getBesaranGaji() {
		return besaranGaji;
	}
	public void setBesaranGaji(BigDecimal besaranGaji) {
		this.besaranGaji = besaranGaji;
	}
	public Integer getMasaKerja() {
		return masaKerja;
	}
	public void setMasaKerja(Integer masaKerja) {
		this.masaKerja = masaKerja;
	}
	
}
