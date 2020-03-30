package com.employee.model;
// Generated Mar 30, 2020 8:42:38 AM by Hibernate Tools 4.0.1.Final

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * PresentaseGaji generated by hbm2java
 */
@Entity
@Table(name = "presentase_gaji", schema = "public")
public class PresentaseGaji implements java.io.Serializable {

	private int idPresentaseGaji;
	private Posisi posisi;
	private Integer idTingkatan;
	private BigDecimal besaranGaji;
	private Integer masaKerja;

	public PresentaseGaji() {
	}

	public PresentaseGaji(int idPresentaseGaji, BigDecimal besaranGaji) {
		this.idPresentaseGaji = idPresentaseGaji;
		this.besaranGaji = besaranGaji;
	}

	public PresentaseGaji(int idPresentaseGaji, Posisi posisi, Integer idTingkatan, BigDecimal besaranGaji,
			Integer masaKerja) {
		this.idPresentaseGaji = idPresentaseGaji;
		this.posisi = posisi;
		this.idTingkatan = idTingkatan;
		this.besaranGaji = besaranGaji;
		this.masaKerja = masaKerja;
	}

	@Id

	@Column(name = "id_presentase_gaji", unique = true, nullable = false)
	public int getIdPresentaseGaji() {
		return this.idPresentaseGaji;
	}

	public void setIdPresentaseGaji(int idPresentaseGaji) {
		this.idPresentaseGaji = idPresentaseGaji;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_posisi")
	public Posisi getPosisi() {
		return this.posisi;
	}

	public void setPosisi(Posisi posisi) {
		this.posisi = posisi;
	}

	@Column(name = "id_tingkatan")
	public Integer getIdTingkatan() {
		return this.idTingkatan;
	}

	public void setIdTingkatan(Integer idTingkatan) {
		this.idTingkatan = idTingkatan;
	}

	@Column(name = "besaran_gaji", nullable = false, precision = 16, scale = 4)
	public BigDecimal getBesaranGaji() {
		return this.besaranGaji;
	}

	public void setBesaranGaji(BigDecimal besaranGaji) {
		this.besaranGaji = besaranGaji;
	}

	@Column(name = "masa_kerja")
	public Integer getMasaKerja() {
		return this.masaKerja;
	}

	public void setMasaKerja(Integer masaKerja) {
		this.masaKerja = masaKerja;
	}

}
