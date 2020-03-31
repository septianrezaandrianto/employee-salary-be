package com.employee.Employee.dto;

import java.math.BigDecimal;

import com.employee.Employee.model.Posisi;

public class PresentaseGajiDTO {

	private int idPresentaseGaji;
	private PosisiDTO posisiDto;
	private Integer idTingkatan;
	private BigDecimal besaranGaji;
	private Integer masaKerja;
	
	
	public PresentaseGajiDTO() {
		super();
	}
	
	public PresentaseGajiDTO(int idPresentaseGaji, PosisiDTO posisiDto, Integer idTingkatan, BigDecimal besaranGaji,
			Integer masaKerja) {
		super();
		this.idPresentaseGaji = idPresentaseGaji;
		this.posisiDto = posisiDto;
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
	public PosisiDTO getPosisiDto() {
		return posisiDto;
	}

	public void setPosisiDto(PosisiDTO posisiDto) {
		this.posisiDto = posisiDto;
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
