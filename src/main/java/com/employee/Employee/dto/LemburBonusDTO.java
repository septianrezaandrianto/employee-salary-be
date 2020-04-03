package com.employee.Employee.dto;

import java.util.Date;

public class LemburBonusDTO implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private long idLemburBonus;
	private Long idKaryawan;
	private Date tanggalLemburBonus;
	private int lamaLembur;
	private int variableBonus;
	
	public LemburBonusDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LemburBonusDTO(long idLemburBonus, Long idKaryawan, Date tanggalLemburBonus, int lamaLembur,
			int variableBonus) {
		super();
		this.idLemburBonus = idLemburBonus;
		this.idKaryawan = idKaryawan;
		this.tanggalLemburBonus = tanggalLemburBonus;
		this.lamaLembur = lamaLembur;
		this.variableBonus = variableBonus;
	}

	public long getIdLemburBonus() {
		return idLemburBonus;
	}

	public Long getIdKaryawan() {
		return idKaryawan;
	}

	public Date getTanggalLemburBonus() {
		return tanggalLemburBonus;
	}

	public int getLamaLembur() {
		return lamaLembur;
	}

	public int getVariableBonus() {
		return variableBonus;
	}

	public void setIdLemburBonus(long idLemburBonus) {
		this.idLemburBonus = idLemburBonus;
	}

	public void setIdKaryawan(Long idKaryawan) {
		this.idKaryawan = idKaryawan;
	}

	public void setTanggalLemburBonus(Date tanggalLemburBonus) {
		this.tanggalLemburBonus = tanggalLemburBonus;
	}

	public void setLamaLembur(int lamaLembur) {
		this.lamaLembur = lamaLembur;
	}

	public void setVariableBonus(int variableBonus) {
		this.variableBonus = variableBonus;
	}
	
}
