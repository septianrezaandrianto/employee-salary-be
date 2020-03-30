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

	
}
