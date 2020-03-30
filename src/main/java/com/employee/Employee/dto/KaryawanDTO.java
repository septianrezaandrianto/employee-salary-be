package com.employee.Employee.dto;

import java.io.Serializable;
import java.util.Date;

import com.employee.Employee.model.Agama;
import com.employee.Employee.model.Penempatan;
import com.employee.Employee.model.Posisi;
import com.employee.Employee.model.Tingkatan;

public class KaryawanDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int idKaryawan;
	private PosisiDTO posisiDto;
	private PenempatanDTO penempatanDto;
	private TingkatanDTO tingkatanDto;
	private AgamaDTO agamaDto;
	private String nama;
	private String noKtp;
	private String alamat;
	private Date tanggalLahir;
	private Integer masaKerja;
	private Short statusPernikahan;
	private Date kontrakAwal;
	private Date kontrakAkhir;
	private String jenisKelamin;
	private Integer jumlahAnak;
	
	public KaryawanDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public KaryawanDTO(int idKaryawan, PosisiDTO posisiDto, PenempatanDTO penempatanDto, TingkatanDTO tingkatanDto,
			AgamaDTO agamaDto, String nama, String noKtp, String alamat, Date tanggalLahir, Integer masaKerja,
			Short statusPernikahan, Date kontrakAwal, Date kontrakAkhir, String jenisKelamin, Integer jumlahAnak) {
		super();
		this.idKaryawan = idKaryawan;
		this.posisiDto = posisiDto;
		this.penempatanDto = penempatanDto;
		this.tingkatanDto = tingkatanDto;
		this.agamaDto = agamaDto;
		this.nama = nama;
		this.noKtp = noKtp;
		this.alamat = alamat;
		this.tanggalLahir = tanggalLahir;
		this.masaKerja = masaKerja;
		this.statusPernikahan = statusPernikahan;
		this.kontrakAwal = kontrakAwal;
		this.kontrakAkhir = kontrakAkhir;
		this.jenisKelamin = jenisKelamin;
		this.jumlahAnak = jumlahAnak;
	}

	public int getIdKaryawan() {
		return idKaryawan;
	}

	public PosisiDTO getPosisiDto() {
		return posisiDto;
	}

	public PenempatanDTO getPenempatanDto() {
		return penempatanDto;
	}

	public TingkatanDTO getTingkatanDto() {
		return tingkatanDto;
	}

	public AgamaDTO getAgamaDto() {
		return agamaDto;
	}

	public String getNama() {
		return nama;
	}

	public String getNoKtp() {
		return noKtp;
	}

	public String getAlamat() {
		return alamat;
	}

	public Date getTanggalLahir() {
		return tanggalLahir;
	}

	public Integer getMasaKerja() {
		return masaKerja;
	}

	public Short getStatusPernikahan() {
		return statusPernikahan;
	}

	public Date getKontrakAwal() {
		return kontrakAwal;
	}

	public Date getKontrakAkhir() {
		return kontrakAkhir;
	}

	public String getJenisKelamin() {
		return jenisKelamin;
	}

	public Integer getJumlahAnak() {
		return jumlahAnak;
	}

	public void setIdKaryawan(int idKaryawan) {
		this.idKaryawan = idKaryawan;
	}

	public void setPosisiDto(PosisiDTO posisiDto) {
		this.posisiDto = posisiDto;
	}

	public void setPenempatanDto(PenempatanDTO penempatanDto) {
		this.penempatanDto = penempatanDto;
	}

	public void setTingkatanDto(TingkatanDTO tingkatanDto) {
		this.tingkatanDto = tingkatanDto;
	}

	public void setAgamaDto(AgamaDTO agamaDto) {
		this.agamaDto = agamaDto;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public void setNoKtp(String noKtp) {
		this.noKtp = noKtp;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public void setTanggalLahir(Date tanggalLahir) {
		this.tanggalLahir = tanggalLahir;
	}

	public void setMasaKerja(Integer masaKerja) {
		this.masaKerja = masaKerja;
	}

	public void setStatusPernikahan(Short statusPernikahan) {
		this.statusPernikahan = statusPernikahan;
	}

	public void setKontrakAwal(Date kontrakAwal) {
		this.kontrakAwal = kontrakAwal;
	}

	public void setKontrakAkhir(Date kontrakAkhir) {
		this.kontrakAkhir = kontrakAkhir;
	}

	public void setJenisKelamin(String jenisKelamin) {
		this.jenisKelamin = jenisKelamin;
	}

	public void setJumlahAnak(Integer jumlahAnak) {
		this.jumlahAnak = jumlahAnak;
	}
	
}
