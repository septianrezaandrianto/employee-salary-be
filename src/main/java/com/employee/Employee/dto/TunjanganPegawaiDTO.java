package com.employee.Employee.dto;

import java.math.BigDecimal;

public class TunjanganPegawaiDTO {
		
	private int idTunjanganPegawai;
	private PosisiDTO posisi;
	private TingkatanDTO tingkatan;
	private BigDecimal besaranTujnaganPegawai;
	

	public TunjanganPegawaiDTO() {
		
	}

	public TunjanganPegawaiDTO(int idTunjanganPegawai, PosisiDTO posisi, TingkatanDTO tingkatan,
			BigDecimal besaranTujnaganPegawai) {
		super();
		this.idTunjanganPegawai = idTunjanganPegawai;
		this.posisi= posisi;
		this.tingkatan = tingkatan;
		this.besaranTujnaganPegawai = besaranTujnaganPegawai;
	}


	public int getIdTunjanganPegawai() {
		return idTunjanganPegawai;
	}


	public void setIdTunjanganPegawai(int idTunjanganPegawai) {
		this.idTunjanganPegawai = idTunjanganPegawai;
	}


	public PosisiDTO getPosisi() {
		return posisi;
	}


	public void setPosisi(PosisiDTO posisi) {
		this.posisi = posisi;
	}


	public TingkatanDTO getTingkatan() {
		return tingkatan;
	}


	public void setTingkatan(TingkatanDTO tingkatan) {
		this.tingkatan = tingkatan;
	}


	public BigDecimal getBesaranTujnaganPegawai() {
		return besaranTujnaganPegawai;
	}


	public void setBesaranTujnaganPegawai(BigDecimal besaranTujnaganPegawai) {
		this.besaranTujnaganPegawai = besaranTujnaganPegawai;
	}
	
}
