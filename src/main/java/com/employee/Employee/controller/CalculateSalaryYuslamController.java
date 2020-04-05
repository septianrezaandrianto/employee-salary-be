package com.employee.Employee.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.Employee.dto.KaryawanDTO;
import com.employee.Employee.dto.PosisiDTO;
import com.employee.Employee.model.Karyawan;
import com.employee.Employee.model.LemburBonus;
import com.employee.Employee.model.Parameter;
import com.employee.Employee.model.Penempatan;
import com.employee.Employee.model.PresentaseGaji;
import com.employee.Employee.model.TunjanganPegawai;
import com.employee.Employee.repository.KaryawanRepository;
import com.employee.Employee.repository.LemburBonusRepository;
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
	@Autowired
	LemburBonusRepository lemburBonusRepository;
	
	
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
	public double calculateTakeHomePay(KaryawanDTO karyawanDTO, Date tanggalGaji) {
		double totalTakeHomePay = calculateGrossSalary() + calculateLemburSalary(karyawanDTO, tanggalGaji)  - calculateBonusSalary(karyawanDTO, tanggalGaji);
		 return totalTakeHomePay;
	}
	
	//Mengkalkulasi Lembur
	public double calculateLemburSalary(KaryawanDTO karyawanDTO, Date dateTanggalGaji) {
		double totalLemburSalary=0;
		
		for(LemburBonus lemburBonus : lemburBonusRepository.findAll()) {
			if(karyawanDTO.getIdKaryawan() == lemburBonus.getIdKaryawan()) {
				for(Parameter parameter : parameterRepository.findAll()) {
					if(parameter.getTbParameter().getYear() == dateTanggalGaji.getYear() && 
							parameter.getTbParameter().getMonth() == dateTanggalGaji.getMonth()) {
						totalLemburSalary = parameter.getLembur().doubleValue() * lemburBonus.getLamaLembur() * calculateGrossSalary();
					}
				}
			}
		}
		return totalLemburSalary;
	}
	
	//Mengkalkulasi Bonus
	public double calculateBonusSalary(KaryawanDTO karyawanDTO, Date dateTanggalGaji) {
		List<PosisiDTO> posisiDTOs = new ArrayList<PosisiDTO>();
		double multipleBonus = 0;
		double totalBonusSalary=0;
		double maxBonus=0;
		int batasanBonus=0;
		
			for(Parameter parameter : parameterRepository.findAll()) {
			if(karyawanDTO.getPosisi().getNamaPosisi().equalsIgnoreCase(posisiDTOs.get(1).getNamaPosisi())) {
				multipleBonus = parameter.getBonusPg().doubleValue();
				batasanBonus = parameter.getBatasanBonusPg();
			}
			else if(karyawanDTO.getPosisi().getNamaPosisi().equalsIgnoreCase(posisiDTOs.get(3).getNamaPosisi())) {
				multipleBonus = parameter.getBonusTs().doubleValue();
				batasanBonus = parameter.getBatasanBonusTs();
			}
			else if(karyawanDTO.getPosisi().getNamaPosisi().equalsIgnoreCase(posisiDTOs.get(4).getNamaPosisi())) {
				multipleBonus = parameter.getBonusTw().doubleValue();
				batasanBonus = parameter.getBatasanBonusTw();
			}
			maxBonus = parameter.getMaxBonus().doubleValue();
			}
			
			for(LemburBonus lemburBonus : lemburBonusRepository.findAll()) {
				if(lemburBonus.getIdKaryawan() == karyawanDTO.getIdKaryawan() && dateTanggalGaji.getYear() == 
						lemburBonus.getTanggalLemburBonus().getYear() && dateTanggalGaji.getMonth() == 
						lemburBonus.getTanggalLemburBonus().getMonth()) {
					double variableBonus = lemburBonus.getVariableBonus();
					double besaranBonus = variableBonus / batasanBonus;
					int besaranBonusTemp = (int) besaranBonus;
					totalBonusSalary = multipleBonus * besaranBonusTemp;
					if(totalBonusSalary > 0) {
						totalBonusSalary = maxBonus;
					}
				}
			}
		
			
		return totalBonusSalary;
	}
	
	
	
//	@PutMapping("/insert/{date}")
//	public HashMap<String, Object> createPendapatan(@PathVariable (value="date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date){
//		HashMap<String, Object> hmPendapatan = new HashMap<String, Object>();		
//		List<KaryawanDTO> karyawanDTOs = new ArrayList<KaryawanDTO>();
//		List<PendapatanDTO> pendapatanDTOs = new ArrayList<PendapatanDTO>();
//		int lamaLembur, variableBonus;
//		double gajiPokok, tunjanganKeluarga, tunjanganTransport, tunjanganPegawai, gajiKotor, bpjs, gajiBersih,pphPerbulan, uangBonus,uangLembur, takeHomePay;
//		Calendar calendarTanggalGaji = Calendar.getInstance();
//		boolean isUpdate = false;
//		
//		int yearRequest = date.getYear()+1900;
//		int monthRequest = date.getMonth()+1;
//		
//		for(Karyawan karyawan : karyawanRepository.findAll()) {
//			gajiPokok = calculateGajiPokok();
//			tunjanganKeluarga = calculateFamilyAllowence();
//			tunjanganPegawai = calculateEmployeeAllowence();
//			tunjanganTransport = calculateTransportAllowence();
//			gajiKotor = calculateGrossSalary();
//			pphPerbulan = calculatePph();
//			bpjs = calculatePotonganBpjs();
//			gajiBersih = calculateNetSalary();
//			lamaLembur = 0;
//			uangLembur = calculateLemburSalary(karyawanDTO, dateTanggalGaji);
//			variableBonus =0;
//			
//		}
//		
//	}
	
}
