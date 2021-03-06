package com.employee.Employee.model;
// Generated Apr 3, 2020 10:34:04 AM by Hibernate Tools 4.3.5.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * LemburBonus generated by hbm2java
 */
@Entity
@Table(name = "lembur_bonus", schema = "public")
public class LemburBonus implements java.io.Serializable {

	private long idLemburBonus;
	private Long idKaryawan;
	private Date tanggalLemburBonus;
	private int lamaLembur;
	private int variableBonus;

	public LemburBonus() {
	}

	public LemburBonus(long idLemburBonus, Date tanggalLemburBonus, int lamaLembur, int variableBonus) {
		this.idLemburBonus = idLemburBonus;
		this.tanggalLemburBonus = tanggalLemburBonus;
		this.lamaLembur = lamaLembur;
		this.variableBonus = variableBonus;
	}

	public LemburBonus(long idLemburBonus, Long idKaryawan, Date tanggalLemburBonus, int lamaLembur,
			int variableBonus) {
		this.idLemburBonus = idLemburBonus;
		this.idKaryawan = idKaryawan;
		this.tanggalLemburBonus = tanggalLemburBonus;
		this.lamaLembur = lamaLembur;
		this.variableBonus = variableBonus;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_lembur_bonus_seq")
	@SequenceGenerator(name="generator_lembur_bonus_seq", sequenceName= "lembur_bonus_seq", schema = "public", allocationSize=1)
	@Column(name = "id_lembur_bonus", unique = true, nullable = false)
	public long getIdLemburBonus() {
		return this.idLemburBonus;
	}

	public void setIdLemburBonus(long idLemburBonus) {
		this.idLemburBonus = idLemburBonus;
	}

	@Column(name = "id_karyawan")
	public Long getIdKaryawan() {
		return this.idKaryawan;
	}

	public void setIdKaryawan(Long idKaryawan) {
		this.idKaryawan = idKaryawan;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "tanggal_lembur_bonus", nullable = false, length = 13)
	public Date getTanggalLemburBonus() {
		return this.tanggalLemburBonus;
	}

	public void setTanggalLemburBonus(Date tanggalLemburBonus) {
		this.tanggalLemburBonus = tanggalLemburBonus;
	}

	@Column(name = "lama_lembur", nullable = false)
	public int getLamaLembur() {
		return this.lamaLembur;
	}

	public void setLamaLembur(int lamaLembur) {
		this.lamaLembur = lamaLembur;
	}

	@Column(name = "variable_bonus", nullable = false)
	public int getVariableBonus() {
		return this.variableBonus;
	}

	public void setVariableBonus(int variableBonus) {
		this.variableBonus = variableBonus;
	}

}
