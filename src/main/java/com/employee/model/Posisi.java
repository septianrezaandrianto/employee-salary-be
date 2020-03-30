package com.employee.model;
// Generated Mar 30, 2020 8:42:38 AM by Hibernate Tools 4.0.1.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Posisi generated by hbm2java
 */
@Entity
@Table(name = "posisi", schema = "public")
public class Posisi implements java.io.Serializable {

	private int idPosisi;
	private String namaPosisi;
	private Set<TunjanganPegawai> tunjanganPegawais = new HashSet<TunjanganPegawai>(0);
	private Set<Karyawan> karyawans = new HashSet<Karyawan>(0);
	private Set<PresentaseGaji> presentaseGajis = new HashSet<PresentaseGaji>(0);

	public Posisi() {
	}

	public Posisi(int idPosisi, String namaPosisi) {
		this.idPosisi = idPosisi;
		this.namaPosisi = namaPosisi;
	}

	public Posisi(int idPosisi, String namaPosisi, Set<TunjanganPegawai> tunjanganPegawais, Set<Karyawan> karyawans,
			Set<PresentaseGaji> presentaseGajis) {
		this.idPosisi = idPosisi;
		this.namaPosisi = namaPosisi;
		this.tunjanganPegawais = tunjanganPegawais;
		this.karyawans = karyawans;
		this.presentaseGajis = presentaseGajis;
	}

	@Id

	@Column(name = "id_posisi", unique = true, nullable = false)
	public int getIdPosisi() {
		return this.idPosisi;
	}

	public void setIdPosisi(int idPosisi) {
		this.idPosisi = idPosisi;
	}

	@Column(name = "nama_posisi", nullable = false, length = 128)
	public String getNamaPosisi() {
		return this.namaPosisi;
	}

	public void setNamaPosisi(String namaPosisi) {
		this.namaPosisi = namaPosisi;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "posisi")
	public Set<TunjanganPegawai> getTunjanganPegawais() {
		return this.tunjanganPegawais;
	}

	public void setTunjanganPegawais(Set<TunjanganPegawai> tunjanganPegawais) {
		this.tunjanganPegawais = tunjanganPegawais;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "posisi")
	public Set<Karyawan> getKaryawans() {
		return this.karyawans;
	}

	public void setKaryawans(Set<Karyawan> karyawans) {
		this.karyawans = karyawans;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "posisi")
	public Set<PresentaseGaji> getPresentaseGajis() {
		return this.presentaseGajis;
	}

	public void setPresentaseGajis(Set<PresentaseGaji> presentaseGajis) {
		this.presentaseGajis = presentaseGajis;
	}

}
