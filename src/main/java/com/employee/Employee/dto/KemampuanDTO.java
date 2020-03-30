package com.employee.Employee.dto;

public class KemampuanDTO {

	private int idKemampuan;
	private KategoriKemampuanDTO kategoriKemampuanDTO;
	private String namaKemampuan;

	public KemampuanDTO() {
	}

	public KemampuanDTO(int idKemampuan, String namaKemampuan) {
		this.idKemampuan = idKemampuan;
		this.namaKemampuan = namaKemampuan;
	}

	public KemampuanDTO(int idKemampuan, String namaKemampuan, KategoriKemampuanDTO kategoriKemampuanDTO) {
		this.idKemampuan = idKemampuan;
		this.kategoriKemampuanDTO = kategoriKemampuanDTO;
		this.namaKemampuan = namaKemampuan;
	}

	public int getIdKemampuan() {
		return this.idKemampuan;
	}

	public void setIdKemampuan(int idKemampuan) {
		this.idKemampuan = idKemampuan;
	}

	public KategoriKemampuanDTO getKategoriKemampuan() {
		return this.kategoriKemampuanDTO;
	}

	public void setKategoriKemampuan(KategoriKemampuanDTO kategoriKemampuanDTO) {
		this.kategoriKemampuanDTO = kategoriKemampuanDTO;
	}

	public String getNamaKemampuan() {
		return this.namaKemampuan;
	}

	public void setNamaKemampuan(String namaKemampuan) {
		this.namaKemampuan = namaKemampuan;
	}

}