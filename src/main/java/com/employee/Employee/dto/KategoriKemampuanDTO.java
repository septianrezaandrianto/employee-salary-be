package com.employee.Employee.dto;

public class KategoriKemampuanDTO {

	private int idKategori;
	private String namaKategori;

	public KategoriKemampuanDTO() {
	}

	public KategoriKemampuanDTO(int idKategori, String namaKategori) {
		this.idKategori = idKategori;
		this.namaKategori = namaKategori;
	}

	public int getIdKategori() {
		return this.idKategori;
	}

	public void setIdKategori(int idKategori) {
		this.idKategori = idKategori;
	}

	public String getNamaKategori() {
		return this.namaKategori;
	}

	public void setNamaKategori(String namaKategori) {
		this.namaKategori = namaKategori;
	}

}
