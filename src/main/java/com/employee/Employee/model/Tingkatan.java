package com.employee.Employee.model;
// Generated Mar 30, 2020 8:42:38 AM by Hibernate Tools 4.0.1.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Tingkatan generated by hbm2java
 */
@Entity
@Table(name = "tingkatan", schema = "public")
public class Tingkatan implements java.io.Serializable {

	private int idTingkatan;
	private String namaTingkatan;
	private Set<Karyawan> karyawans = new HashSet<Karyawan>(0);
	private Set<TunjanganPegawai> tunjanganPegawais = new HashSet<TunjanganPegawai>(0);

	public Tingkatan() {
	}

	public Tingkatan(int idTingkatan, String namaTingkatan) {
		this.idTingkatan = idTingkatan;
		this.namaTingkatan = namaTingkatan;
	}

	public Tingkatan(int idTingkatan, String namaTingkatan, Set<Karyawan> karyawans,
			Set<TunjanganPegawai> tunjanganPegawais) {
		this.idTingkatan = idTingkatan;
		this.namaTingkatan = namaTingkatan;
		this.karyawans = karyawans;
		this.tunjanganPegawais = tunjanganPegawais;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_tingkatan_id_tingkatan_seq")
	@SequenceGenerator(name="generator_tingkatan_id_tingkatan_seq", sequenceName = "tingkatan_id_tingkatan_seq", schema = "public", allocationSize = 1)
	@Column(name = "id_tingkatan", unique = true, nullable = false)
	public int getIdTingkatan() {
		return this.idTingkatan;
	}

	public void setIdTingkatan(int idTingkatan) {
		this.idTingkatan = idTingkatan;
	}

	@Column(name = "nama_tingkatan", nullable = false, length = 128)
	public String getNamaTingkatan() {
		return this.namaTingkatan;
	}

	public void setNamaTingkatan(String namaTingkatan) {
		this.namaTingkatan = namaTingkatan;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tingkatan")
	public Set<Karyawan> getKaryawans() {
		return this.karyawans;
	}

	public void setKaryawans(Set<Karyawan> karyawans) {
		this.karyawans = karyawans;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tingkatan")
	public Set<TunjanganPegawai> getTunjanganPegawais() {
		return this.tunjanganPegawais;
	}

	public void setTunjanganPegawais(Set<TunjanganPegawai> tunjanganPegawais) {
		this.tunjanganPegawais = tunjanganPegawais;
	}

}
