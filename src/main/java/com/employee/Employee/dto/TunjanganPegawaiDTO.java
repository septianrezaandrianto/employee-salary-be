package com.employee.Employee.dto;

import java.math.BigDecimal;

public class TunjanganPegawaiDTO {
		
	private int idTunjanganPegawai;
	private PosisiDTO posisiDTO;
	private TingkatanDTO tingkatanDTO;
	private BigDecimal besaranTujnaganPegawai;
	
	
	public TunjanganPegawaiDTO(int idTunjanganPegawai, BigDecimal besaranTujnaganPegawai) {
		super();
		this.idTunjanganPegawai = idTunjanganPegawai;
		this.besaranTujnaganPegawai = besaranTujnaganPegawai;
	}


	public TunjanganPegawaiDTO(int idTunjanganPegawai, PosisiDTO posisiDTO, TingkatanDTO tingkatanDTO,
			BigDecimal besaranTujnaganPegawai) {
		super();
		this.idTunjanganPegawai = idTunjanganPegawai;
		this.posisiDTO = posisiDTO;
		this.tingkatanDTO = tingkatanDTO;
		this.besaranTujnaganPegawai = besaranTujnaganPegawai;
	}


	public int getIdTunjanganPegawai() {
		return idTunjanganPegawai;
	}


	public void setIdTunjanganPegawai(int idTunjanganPegawai) {
		this.idTunjanganPegawai = idTunjanganPegawai;
	}


	public PosisiDTO getPosisiDTO() {
		return posisiDTO;
	}


	public void setPosisiDTO(PosisiDTO posisiDTO) {
		this.posisiDTO = posisiDTO;
	}


	public TingkatanDTO getTingkatanDTO() {
		return tingkatanDTO;
	}


	public void setTingkatanDTO(TingkatanDTO tingkatanDTO) {
		this.tingkatanDTO = tingkatanDTO;
	}


	public BigDecimal getBesaranTujnaganPegawai() {
		return besaranTujnaganPegawai;
	}


	public void setBesaranTujnaganPegawai(BigDecimal besaranTujnaganPegawai) {
		this.besaranTujnaganPegawai = besaranTujnaganPegawai;
	}
	
}
