package com.employee.Employee.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.Employee.dto.KaryawanDTO;
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
import com.employee.Employee.repository.PosisiRepository;
import com.employee.Employee.repository.PresentaseGajiRepository;
import com.employee.Employee.repository.TingkatanRepository;
import com.employee.Employee.repository.TunjanganPegawaiRepository;

@RestController
@RequestMapping("/api")
public class CalculateSalaryFazriController {
	 ModelMapper modelMapper = new ModelMapper();
	
	 
	 @Autowired
	 KaryawanRepository karyawanRepository;
	 
	 @Autowired
	 PendapatanRepository pendapatanRepository;
	 
	 @Autowired
	 PresentaseGajiRepository presentaseGajiRepository;
	 
	 @Autowired
	 PenempatanRepository penempatanRepository;
	 
	 @Autowired
	 TingkatanRepository tingkatanRepository;
	 
	 @Autowired
	 PosisiRepository posisiRepository;
	 
	 @Autowired
	 ParameterRepository parameterRepository;
	 
	 @Autowired
	 TunjanganPegawaiRepository tunjanganPegawaiRepository;
	
	 public KaryawanDTO convertToDTO(Karyawan karyawan) {
        return modelMapper.map(karyawan, KaryawanDTO.class);
	 }

     public Karyawan convertToEntity(KaryawanDTO karyawanDto) {
        return modelMapper.map(karyawanDto, Karyawan.class);
     }
     
	 // mencari UMK
	 public double getUmk(KaryawanDTO karyawanDto) {
		 double umk = 0;
		 for(Penempatan p : penempatanRepository.findAll()) {
			 if(karyawanDto.getPenempatan().getIdPenempatan() == p.getIdPenempatan()) {
				 umk = p.getUmkPenempatan().doubleValue();
			 }
		 }
		 
		 return umk;
	 }
	 
	 // Menghitung persentase gaji Programmer
	 public double calculatePersentaseGajiProgrammer(KaryawanDTO karyawanDto) {
		 double persentaseGaji = 0;
		 for(PresentaseGaji pg : presentaseGajiRepository.findAll()) {
			 if(karyawanDto.getPosisi().getIdPosisi() == pg.getPosisi().getIdPosisi() && karyawanDto.getPosisi().getNamaPosisi().contentEquals("Programmer")) {
				 if(karyawanDto.getTingkatan().getIdTingkatan() == pg.getIdTingkatan()) {
					 if(karyawanDto.getTingkatan().getNamaTingkatan().equalsIgnoreCase("Junior")) {
						 if(karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 1) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 3) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() || karyawanDto.getMasaKerja() > 3){
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							}
					 } else if (karyawanDto.getTingkatan().getNamaTingkatan().equalsIgnoreCase("Middle")) {
						 if(karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 2) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 4) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() || karyawanDto.getMasaKerja() > 4){
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							}
					 } else {
						 if(karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 2) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 4) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() || karyawanDto.getMasaKerja() > 4){
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							}
					 } 
				 }
			 }
			
		 }
		 return persentaseGaji;
	 }
	
	 // Menghitung persentase gaji Analist
	 public double calculatePersentaseGajiAnalist(KaryawanDTO karyawanDto) {
		 double persentaseGaji = 0;
		 for(PresentaseGaji pg : presentaseGajiRepository.findAll()) {
			 if(karyawanDto.getPosisi().getIdPosisi() == pg.getPosisi().getIdPosisi() && karyawanDto.getPosisi().getNamaPosisi().contentEquals("Analist")) {
				 if(karyawanDto.getTingkatan().getIdTingkatan() == pg.getIdTingkatan()) {
					 if(karyawanDto.getTingkatan().getNamaTingkatan().equalsIgnoreCase("Junior")) {
						 if(karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 1) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 3) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() || karyawanDto.getMasaKerja() > 3){
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							}
					 } else if (karyawanDto.getTingkatan().getNamaTingkatan().equalsIgnoreCase("Middle")) {
						 if(karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 2) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 4) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() || karyawanDto.getMasaKerja() > 4){
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							}
					 } else {
						 if(karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 2) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 4) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() || karyawanDto.getMasaKerja() > 4){
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							}
					 } 
				 }
			 }
			
		 }
		 return persentaseGaji;
	 }
	 
	 // Menghitung persentase gaji Tester
	 public double calculatePersentaseGajiTester(KaryawanDTO karyawanDto) {
		 double persentaseGaji = 0;
		 for(PresentaseGaji pg : presentaseGajiRepository.findAll()) {
			 if(karyawanDto.getPosisi().getIdPosisi() == pg.getPosisi().getIdPosisi() && karyawanDto.getPosisi().getNamaPosisi().contentEquals("Tester")) {
				 if(karyawanDto.getTingkatan().getIdTingkatan() == pg.getIdTingkatan()) {
					 if(karyawanDto.getTingkatan().getNamaTingkatan().equalsIgnoreCase("Junior")) {
						 if(karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 1) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 3) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() || karyawanDto.getMasaKerja() > 3){
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							}
					 } else if (karyawanDto.getTingkatan().getNamaTingkatan().equalsIgnoreCase("Middle")) {
						 if(karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 2) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 4) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() || karyawanDto.getMasaKerja() > 4){
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							}
					 } else {
						 if(karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 2) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 4) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() || karyawanDto.getMasaKerja() > 4){
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							}
					 } 
				 }
			 }
			
		 }
		 return persentaseGaji;
	 }
	 
	 // Menghitung persentase gaji Technical Writter
	 public double calculatePersentaseGajiTechnicalWriter(KaryawanDTO karyawanDto) {
		 double persentaseGaji = 0;
		 for(PresentaseGaji pg : presentaseGajiRepository.findAll()) {
			 if(karyawanDto.getPosisi().getIdPosisi() == pg.getPosisi().getIdPosisi() && karyawanDto.getPosisi().getNamaPosisi().contentEquals("Technical Writter")) {
				 if(karyawanDto.getTingkatan().getIdTingkatan() == pg.getIdTingkatan()) {
					 if(karyawanDto.getTingkatan().getNamaTingkatan().equalsIgnoreCase("Junior")) {
						 if(karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 1) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 3) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() > 3){
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							}
					 } else if (karyawanDto.getTingkatan().getNamaTingkatan().equalsIgnoreCase("Middle")) {
						 if(karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 2) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 4) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() > 4){
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							}
					 } else {
						 if(karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 2) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() <= 4) {
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							} else if (karyawanDto.getMasaKerja() == pg.getMasaKerja() && karyawanDto.getMasaKerja() > 4){
								persentaseGaji = pg.getBesaranGaji().doubleValue();
							}
					 } 
				 }
			 }
			
		 }
		 return persentaseGaji;
	 }
	 
	 // Menghitung presentasi gaji sesuai dengan posisi dan tingkatan
	 public double calculatePersentaseGaji(KaryawanDTO karyawanDto) {
		 double persentaseGaji = 0;
		 
		 if(karyawanDto.getPosisi().getNamaPosisi().equalsIgnoreCase("Programmer")) {
			 persentaseGaji = calculatePersentaseGajiProgrammer(karyawanDto);
		 } else if (karyawanDto.getPosisi().getNamaPosisi().equalsIgnoreCase("Analist")) {
			 persentaseGaji = calculatePersentaseGajiAnalist(karyawanDto);
		 } else if (karyawanDto.getPosisi().getNamaPosisi().equalsIgnoreCase("Tester")) {
			 persentaseGaji = calculatePersentaseGajiTester(karyawanDto);
		 } else {
			 persentaseGaji = calculatePersentaseGajiTechnicalWriter(karyawanDto);
		 }
		 
		 return persentaseGaji;
	 }
	 
	 // mencari tunjangan pegawai berdasarkan posisi dan tingkatan
	 public double getTunjanganPegawai(KaryawanDTO karyawanDto) {
		 double tunjanganPegawai = 0;
		 for(TunjanganPegawai tp : tunjanganPegawaiRepository.findAll()) {
			 if(tp.getPosisi().getIdPosisi() == karyawanDto.getPosisi().getIdPosisi()) {
				 if(tp.getTingkatan().getIdTingkatan() == karyawanDto.getTingkatan().getIdTingkatan()) {
					 tunjanganPegawai = tp.getBesaranTujnaganPegawai().doubleValue();
				 }
			 }
		 }
		 return tunjanganPegawai;
	 }

	 // mencari tunjangan transportasi
	 @SuppressWarnings("deprecation")
	public double getTunjanganTransportation(KaryawanDTO karyawanDto, Date tanggalGaji) {
		 double tunjanganTransportation = 0;
		 if(karyawanDto.getPenempatan().getKotaPenempatan().equalsIgnoreCase("DKI Jakarta")) {
			 for(Parameter pr : parameterRepository.findAll()) {
				 if(pr.getTbParameter().getMonth() == tanggalGaji.getMonth() && pr.getTbParameter().getYear() == tanggalGaji.getYear()) {
					 tunjanganTransportation = pr.getTTransport().doubleValue();
				 }
			 }
		 }
		 return tunjanganTransportation;
	 }
	 
	 // Mencari Tunjangan Keluarga
	 @SuppressWarnings("deprecation")
	public double getTunjanganKeluarga(KaryawanDTO karyawanDto, double gajiPokok, Date tanggalGaji) {
		 double tunjanganKeluarga = 0;
		 for(Parameter p : parameterRepository.findAll()) {
			 if(p.getTbParameter().getMonth() == tanggalGaji.getMonth() && p.getTbParameter().getYear() == tanggalGaji.getYear()) {
				 if(karyawanDto.getStatusPernikahan() == 1) {
					 tunjanganKeluarga = gajiPokok * p.getTKeluarga().doubleValue();
				 }
			 }
		 }
		 return tunjanganKeluarga;
	 }
	 
	 // Menghitung gaji pokok
	 public double calculateGajiPokok(KaryawanDTO karyawanDto) {
		 double gajiPokok = 0;
		 double umk = getUmk(karyawanDto);
		 gajiPokok = umk * calculatePersentaseGaji(karyawanDto);
		 return gajiPokok;
	 }
	 
	 // Menghitung potongan BPJS
	 @SuppressWarnings("deprecation")
	public double calculatePotonganBPJS(double gajiPokok, Date tanggalGaji) {
		 double bpjs = 0;
		 for(Parameter p : parameterRepository.findAll()) {
			 if(p.getTbParameter().getMonth() == tanggalGaji.getMonth() && p.getTbParameter().getYear() == tanggalGaji.getYear()) {
				 bpjs = gajiPokok * p.getPBpjs().doubleValue();
			 }
		 }
		 return bpjs;
	 }
	 
	 // Menghitung pph perbulan
	 public double calculatePph(double gajiKotor) {
		 double pph = 0;
		 double persentasePph = 0.05;
		 pph = (gajiKotor * persentasePph);
		 return pph;
	 }
	 
	 // Menghitung Gaji Kotor
	 public double calculateGajiKotor(double gajiPokok, double tunjanganKeluarga, double tunjanganTransportasi, double tunjanganPegawai) {
		 double gajiKotor = 0;
		 gajiKotor = gajiPokok + tunjanganKeluarga + tunjanganTransportasi + tunjanganPegawai;
		 return gajiKotor;
	 }
	 
	 // Menghitung Gaji Bersih
	 public double calculateGajiBersih(double gajiKotor, double bpjs, double pph) {
		 double gajiBersih = 0;
		 gajiBersih = (gajiKotor - bpjs - pph);
		 return gajiBersih;
	 }
	 
	 // Menghitung Uang Lembur
	 public double calculateUangLembur(double gajiPokok) {
		 double uangLembur = 0;
		 
		 return uangLembur;
	 }
	 
	 // Menghitung Uang Bonus
	 public double calculateUangBonus(double gajiPokok) {
		 double uangBonus = 0;
		 
		 return uangBonus;
	 }
	 
	 // Menghitung takeHomePay
	 public double calculateTakeHomePay(double gajiBersih, double uangLembur, double uangBonus) {
		 double takeHomePay = 0;
		 takeHomePay = gajiBersih + uangLembur + uangBonus;
		 return takeHomePay;
	 }
	 
	 //Proses untuk inisialisasi object pendapatan
	 public List<Pendapatan> initializationPendapatan(Date tanggalGaji) {
		List<Pendapatan> listPendapatan = new ArrayList<Pendapatan>();
		double gajiPokok = 0;
    	double tunjanganKeluarga = 0;
    	double tunjanganPegawai = 0;
    	double tunjanganTransportasi = 0;
    	double bpjs = 0;
    	double pph = 0;
    	double gajiKotor = 0;
    	double gajiBersih = 0;
    	double uangLembur = 0;
    	double uangBonus = 0;
    	double takeHomePay = 0;
	    	
    	for(Karyawan k : karyawanRepository.findAll()) {
			KaryawanDTO karyawanDto = convertToDTO(k);
			
			gajiPokok = calculateGajiPokok(karyawanDto);
			tunjanganKeluarga = getTunjanganKeluarga(karyawanDto, gajiPokok, tanggalGaji);
			tunjanganPegawai = getTunjanganPegawai(karyawanDto);
			tunjanganTransportasi = getTunjanganTransportation(karyawanDto, tanggalGaji);
			bpjs = calculatePotonganBPJS(gajiPokok, tanggalGaji);
			pph = calculatePph((gajiPokok + tunjanganKeluarga + tunjanganTransportasi + tunjanganPegawai));
			gajiKotor = calculateGajiKotor(gajiPokok, tunjanganKeluarga, tunjanganTransportasi, tunjanganPegawai);
			gajiBersih = calculateGajiBersih(gajiKotor, bpjs, pph);
			uangLembur = calculateUangLembur(gajiPokok);
			uangBonus = calculateUangBonus(gajiPokok);
			takeHomePay = calculateTakeHomePay(gajiBersih, uangLembur, uangBonus);
			
			Pendapatan pendapatan = new Pendapatan();
			pendapatan.setKaryawan(convertToEntity(karyawanDto));
			pendapatan.setTanggalGaji(tanggalGaji);
			pendapatan.setGajiPokok(BigDecimal.valueOf(gajiPokok));
			pendapatan.setTunjanganKeluarga(BigDecimal.valueOf(tunjanganKeluarga));
			pendapatan.setTunjanganPegawai(BigDecimal.valueOf(tunjanganPegawai));
			pendapatan.setTunjanganTransport(BigDecimal.valueOf(tunjanganTransportasi));
			pendapatan.setBpjs(BigDecimal.valueOf(bpjs));
			pendapatan.setPphPerbulan(BigDecimal.valueOf(pph));
			pendapatan.setGajiKotor(BigDecimal.valueOf(gajiKotor));
			pendapatan.setGajiBersih(BigDecimal.valueOf(gajiBersih));
			pendapatan.setUangLembur(BigDecimal.valueOf(uangLembur));
			pendapatan.setUangBonus(BigDecimal.valueOf(uangBonus));
			pendapatan.setTakeHomePay(BigDecimal.valueOf(takeHomePay));
			
			listPendapatan.add(pendapatan);
    	}
    	Collections.sort(listPendapatan, Comparator.comparing(Pendapatan::getIdPendapatan));
		return listPendapatan;
	 }
	 
	 //Menghitung Gaji semua karyawan
	 @SuppressWarnings("deprecation")
	 @PostMapping("/pendapatan/calculateSalary")
	 public HashMap<String, Object> calculateSalary(@RequestParam(name = "date") String date) throws ParseException {
		HashMap<String, Object> showHashMap = new HashMap<String, Object>();
		List<Pendapatan> listPendapatans = pendapatanRepository.findAll();
		List<Pendapatan> resutlList = new ArrayList<Pendapatan>();
		Date tanggalGaji = new SimpleDateFormat("yyyy-mm-dd").parse(date);
    	String message = "";
    	int index = 0;
    	
    	if(listPendapatans.isEmpty()) {
    		for(Pendapatan p : initializationPendapatan(tanggalGaji)) {
    			pendapatanRepository.save(p);
    			resutlList.add(p);	
    			message = "Create Success!";
    		}
    	} else {
    		for(Pendapatan p : listPendapatans) {
    			if(p.getTanggalGaji().getMonth() == tanggalGaji.getMonth() && p.getTanggalGaji().getYear() == tanggalGaji.getYear()) {
					p.setKaryawan(initializationPendapatan(tanggalGaji).get(index).getKaryawan());
					p.setTanggalGaji(tanggalGaji);
					p.setGajiPokok(initializationPendapatan(tanggalGaji).get(index).getGajiPokok());
					p.setTunjanganKeluarga(initializationPendapatan(tanggalGaji).get(index).getTunjanganKeluarga());
					p.setTunjanganPegawai(initializationPendapatan(tanggalGaji).get(index).getTunjanganPegawai());
					p.setTunjanganTransport(initializationPendapatan(tanggalGaji).get(index).getTunjanganTransport());
					p.setBpjs(initializationPendapatan(tanggalGaji).get(index).getBpjs());
					p.setPphPerbulan(initializationPendapatan(tanggalGaji).get(index).getPphPerbulan());
					p.setGajiKotor(initializationPendapatan(tanggalGaji).get(index).getGajiKotor());
					p.setGajiBersih(initializationPendapatan(tanggalGaji).get(index).getGajiBersih());
					p.setUangLembur(initializationPendapatan(tanggalGaji).get(index).getUangLembur());
					p.setUangBonus(initializationPendapatan(tanggalGaji).get(index).getUangBonus());
					p.setTakeHomePay(initializationPendapatan(tanggalGaji).get(index).getTakeHomePay());
	    			pendapatanRepository.save(p);
	    			resutlList.add(p);	
	    			message = "Update Success!";
	    			index++;
    			} else {
    				for(Pendapatan temp : initializationPendapatan(tanggalGaji)) {
    	    			pendapatanRepository.save(temp);
    	    			resutlList.add(temp);	
    	    			message = "Create Success!";
    	    		}
    			}
    		}	
    	}
    	showHashMap.put("Message", message);
    	showHashMap.put("Data", resutlList);
    	return showHashMap;
	 }
}
