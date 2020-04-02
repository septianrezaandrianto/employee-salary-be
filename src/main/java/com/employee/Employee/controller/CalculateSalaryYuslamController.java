package com.employee.Employee.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.Employee.model.Karyawan;
import com.employee.Employee.model.Parameter;
import com.employee.Employee.model.Pendapatan;
import com.employee.Employee.model.Penempatan;
import com.employee.Employee.model.PresentaseGaji;
import com.employee.Employee.model.TunjanganPegawai;
import com.employee.Employee.repository.KaryawanRepository;
import com.employee.Employee.repository.ParameterRepository;
import com.employee.Employee.repository.PendapatanRepository;
import com.employee.Employee.repository.PenempatanRepository;
import com.employee.Employee.repository.PresentaseGajiRepository;
import com.employee.Employee.repository.TunjanganPegawaiRepository;

@RestController
@RequestMapping("/api")
public class CalculateSalaryYuslamController {
	
	ModelMapper modelMapper = new ModelMapper(); 
	
	@Autowired
	PresentaseGajiRepository presentaseGajiRepository;
	@Autowired
	KaryawanRepository karyawanRepository;
	@Autowired
	PenempatanRepository penempatanRepository; 
	@Autowired
	ParameterRepository parameterRepository;
	@Autowired
	TunjanganPegawaiRepository tunjanganPegawaiRepository;
	@Autowired
	PendapatanRepository pendapatanRepository;
	
	//Mengkalkulasi Gaji Pokok
	@GetMapping("/gaji")
	public double calculateGajiPokok() {	
		double totalGajiPokok = 0;
		
		for(Karyawan karyawan : karyawanRepository.findAll()) {
			double umkPenempatan = 0;
			double rateGajiPokok = 0;
			for(Penempatan penempatan : penempatanRepository.findAll()) {
				if(karyawan.getPenempatan().getIdPenempatan() == penempatan.getIdPenempatan()) {
					umkPenempatan = penempatan.getUmkPenempatan().doubleValue();
				}
			}
			
			for (PresentaseGaji presentaseGaji : presentaseGajiRepository.findAll()) {
				if(karyawan.getPosisi().getIdPosisi() == presentaseGaji.getPosisi().getIdPosisi() &&
						karyawan.getTingkatan().getIdTingkatan() == presentaseGaji.getIdTingkatan()) {
					if(karyawan.getMasaKerja() == presentaseGaji.getMasaKerja()) {
						rateGajiPokok = presentaseGaji.getBesaranGaji().doubleValue();
					}
				}
			}
			totalGajiPokok = umkPenempatan * rateGajiPokok;
		}
				
		return totalGajiPokok;
	}
	
	//Mengkalkulasi Tunjangan Keluarga
	public double calculateFamilyAllowence() {
		double totalAllowence=0;
		for(Karyawan karyawan : karyawanRepository.findAll()) {
			for(Parameter parameter : parameterRepository.findAll()) {
				if(karyawan.getStatusPernikahan() == 1 ) {
					totalAllowence = parameter.getTKeluarga().doubleValue() * calculateGajiPokok();
				}
			}		
		}
		return totalAllowence;
	}
	
	//Mengkalkulasi Tunjangan Transport
	@GetMapping("transport")
	public double calculateTransportAllowence() {
		double totalAllowence=0;
		for(Parameter parameter : parameterRepository.findAll()) {
			for(Penempatan penempatan : penempatanRepository.findAll()) {
				if(penempatan.getIdPenempatan() == 1) {
					totalAllowence = parameter.getTTransport().doubleValue();
				}
			}
		}
		return totalAllowence;
	}
	
	
	//Mengkalkulasi Tunjangan Pegawai
	@GetMapping("/employee")
	public double calculateEmployeeAllowence(){
		double totalAllowence=0;
		
		for(Karyawan karyawan : karyawanRepository.findAll()) {	
			for(TunjanganPegawai tunjanganPegawai : tunjanganPegawaiRepository.findAll()) {
				if(karyawan.getPosisi().getIdPosisi() == tunjanganPegawai.getPosisi().getIdPosisi() 
					&& karyawan.getTingkatan().getIdTingkatan() == tunjanganPegawai.getTingkatan().getIdTingkatan()) {
					totalAllowence = tunjanganPegawai.getBesaranTujnaganPegawai().doubleValue();	
				}
			}
		}
		return totalAllowence;
	}
	

	//Mengakalkulasi Potongan Bpjs
	@GetMapping("/bpjs")
	public double calculatePotonganBpjs() {
		double totalPotongan=0;
		for(Parameter parameter : parameterRepository.findAll()) {
			totalPotongan = parameter.getPBpjs().doubleValue() * calculateGajiPokok();
		}
		return totalPotongan;
	}
	
	//Mengkalkulasi Pph
	public double calculatePph() {
		double totalPph = 0.05 * (calculateGajiPokok() + calculateFamilyAllowence() + calculateEmployeeAllowence() + calculateTransportAllowence());		
		
		return totalPph;
	}
	
	//Mengkalkulasi Gaji Kotor
	public double calculateGrossSalary() {
		
		double totalGrossSalary = calculateGajiPokok() + (calculateFamilyAllowence() + calculateTransportAllowence() + calculateEmployeeAllowence());
		
		return totalGrossSalary;	
	}
	
	//Mengkalkulasi Gaji Bersih
	public double calculateNetSalary() {
		
		double totalNetSalary = calculateGrossSalary() - (calculatePph() + calculatePotonganBpjs());
		
		return totalNetSalary;
	}
	
	//Mengkalkulasi Take Home Pay
	public double calculateTakeHomePay() {
		double totalTakeHomePay = calculateGrossSalary() - 0 - 0;
		 return totalTakeHomePay;
	}
	
	@PostMapping("/insert")
	public HashMap<String, Object> createPendapatan(@RequestParam (value="date") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date){
		HashMap<String, Object> hmPendapatan = new HashMap<String, Object>();		
		for(Pendapatan pendapatan : pendapatanRepository.findAll()) {
			
		}
	}
	
}
