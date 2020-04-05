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
import com.employee.Employee.model.LemburBonus;
import com.employee.Employee.model.Parameter;
import com.employee.Employee.model.Pendapatan;
import com.employee.Employee.model.Penempatan;
import com.employee.Employee.model.PresentaseGaji;
import com.employee.Employee.model.TunjanganPegawai;
import com.employee.Employee.repository.KaryawanRepository;
import com.employee.Employee.repository.LemburBonusRepository;
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
	 
	 @Autowired
	 LemburBonusRepository lemburBonusRepository;
	
	 public KaryawanDTO convertToDTO(Karyawan karyawan) {
        return modelMapper.map(karyawan, KaryawanDTO.class);
	 }

     public Karyawan convertToEntity(KaryawanDTO karyawanDto) {
        return modelMapper.map(karyawanDto, Karyawan.class);
     }
     
	 // mencari UMK
	 public BigDecimal getUmk(KaryawanDTO karyawanDto) {
		 BigDecimal umk = new BigDecimal(0);
		 for(Penempatan p : penempatanRepository.findAll()) {
			 if(karyawanDto.getPenempatan().getIdPenempatan() == p.getIdPenempatan()) {
				 umk = p.getUmkPenempatan();
			 }
		 }
		 
		 return umk;
	 }
	 
	 // Menghitung presentasi gaji sesuai dengan posisi dan tingkatan
	 public BigDecimal calculatePersentaseGaji(KaryawanDTO karyawanDto) {
		 BigDecimal persentaseGaji = new BigDecimal(0);
		 for(PresentaseGaji pg: presentaseGajiRepository.findAll()) {
				if(karyawanDto.getPosisi().getIdPosisi() == pg.getPosisi().getIdPosisi()) {
					if(karyawanDto.getTingkatan().getIdTingkatan() == pg.getIdTingkatan()) {
						if(karyawanDto.getMasaKerja() >= pg.getMasaKerja()) {
							persentaseGaji = pg.getBesaranGaji();					
						}
					}			
				}
			}
		 
		 return persentaseGaji;
	 }
	 
	 // mencari tunjangan pegawai berdasarkan posisi dan tingkatan
	 public BigDecimal getTunjanganPegawai(KaryawanDTO karyawanDto) {
		 BigDecimal tunjanganPegawai = new BigDecimal(0);
		 for(TunjanganPegawai tp : tunjanganPegawaiRepository.findAll()) {
			 if(tp.getPosisi().getIdPosisi() == karyawanDto.getPosisi().getIdPosisi()) {
				 if(tp.getTingkatan().getIdTingkatan() == karyawanDto.getTingkatan().getIdTingkatan()) {
					 tunjanganPegawai = tp.getBesaranTujnaganPegawai();
				 }
			 }
		 }
		 return tunjanganPegawai;
	 }

	 // mencari tunjangan transportasi
	 @SuppressWarnings("deprecation")
	public BigDecimal getTunjanganTransportation(KaryawanDTO karyawanDto, Date tanggalGaji) {
		 BigDecimal tunjanganTransportation = new BigDecimal(0);
		 if(karyawanDto.getPenempatan().getKotaPenempatan().equalsIgnoreCase("DKI Jakarta")) {
			 for(Parameter pr : parameterRepository.findAll()) {
				 if(pr.getTbParameter().getMonth() == tanggalGaji.getMonth() && pr.getTbParameter().getYear() == tanggalGaji.getYear()) {
					 tunjanganTransportation = pr.getTTransport();
				 }
			 }
		 }
		 return tunjanganTransportation;
	 }
	 
	 // Mencari Tunjangan Keluarga
	 @SuppressWarnings("deprecation")
	public BigDecimal getTunjanganKeluarga(KaryawanDTO karyawanDto, BigDecimal gajiPokok, Date tanggalGaji) {
		 BigDecimal tunjanganKeluarga = new BigDecimal(0);
		 for(Parameter p : parameterRepository.findAll()) {
			 if(p.getTbParameter().getMonth() == tanggalGaji.getMonth() && p.getTbParameter().getYear() == tanggalGaji.getYear()) {
				 if(karyawanDto.getStatusPernikahan() == 1) {
					 tunjanganKeluarga = gajiPokok.multiply(p.getTKeluarga()) ;
				 }
			 }
		 }
		 return tunjanganKeluarga;
	 }
	 
	 // Menghitung gaji pokok
	 public BigDecimal calculateGajiPokok(KaryawanDTO karyawanDto) {
		 BigDecimal gajiPokok = new BigDecimal(0);
		 BigDecimal umk = getUmk(karyawanDto);
		 gajiPokok = umk.multiply(calculatePersentaseGaji(karyawanDto));
		 return gajiPokok;
	 }
	 
	 // Menghitung potongan BPJS
	 @SuppressWarnings("deprecation")
	public BigDecimal calculatePotonganBPJS(BigDecimal gajiPokok, Date tanggalGaji) {
		 BigDecimal bpjs = new BigDecimal(0);
		 for(Parameter p : parameterRepository.findAll()) {
			 if(p.getTbParameter().getMonth() == tanggalGaji.getMonth() && p.getTbParameter().getYear() == tanggalGaji.getYear()) {
				 bpjs = gajiPokok.multiply(p.getPBpjs());
			 }
		 }
		 return bpjs;
	 }
	 
	 // Menghitung pph perbulan
	 public BigDecimal calculatePph(BigDecimal gajiKotor) {
		 BigDecimal pph = new BigDecimal(0);
		 BigDecimal persentasePph =new BigDecimal(0.05);
		 pph = (gajiKotor.multiply(persentasePph));
		 return pph;
	 }
	 
	 // Menghitung Gaji Kotor
	 public BigDecimal calculateGajiKotor(BigDecimal gajiPokok, BigDecimal tunjanganKeluarga, BigDecimal tunjanganTransportasi, BigDecimal tunjanganPegawai) {
		 BigDecimal gajiKotor = new BigDecimal(0);
		 gajiKotor = (gajiPokok.add(tunjanganKeluarga).add(tunjanganTransportasi).add(tunjanganPegawai));
		 return gajiKotor;
	 }
	 
	 // Menghitung Gaji Bersih
	 public BigDecimal calculateGajiBersih(BigDecimal gajiKotor, BigDecimal bpjs, BigDecimal pph) {
		 BigDecimal gajiBersih = new BigDecimal(0);
		 gajiBersih = (gajiKotor.subtract(bpjs).subtract(pph));
		 return gajiBersih;
	 }
	 
	 // Menghitung Uang Lembur
	 @SuppressWarnings("deprecation")
	 public BigDecimal calculateUangLembur(KaryawanDTO karyawanDto, BigDecimal gajiKotor, Date tanggalGaji) {
		 BigDecimal uangLembur = new BigDecimal(0);
		 for(LemburBonus lb : lemburBonusRepository.findAll()) {
			 if(karyawanDto.getIdKaryawan() == lb.getIdKaryawan()) {
				 for(Parameter p : parameterRepository.findAll()) {
					 if(p.getTbParameter().getMonth() == tanggalGaji.getMonth() && p.getTbParameter().getYear() == tanggalGaji.getYear()) {
						 uangLembur = gajiKotor.multiply(p.getLembur()).multiply(BigDecimal.valueOf(lb.getLamaLembur()));
					 }
				 } 
			 }
		 }
		 return uangLembur;
	 }
	 
	 // Menghitung Uang Bonus
	 @SuppressWarnings("deprecation")
 	 public BigDecimal calculateUangBonus(KaryawanDTO karyawanDto, BigDecimal gajiKotor, Date tanggalGaji) {
		 BigDecimal uangBonus = new BigDecimal(0);
		 for(LemburBonus lb : lemburBonusRepository.findAll()) {
			 if(karyawanDto.getIdKaryawan() == lb.getIdKaryawan()) {
				 for(Parameter p : parameterRepository.findAll()) {
					 if(p.getTbParameter().getMonth() == tanggalGaji.getMonth() && p.getTbParameter().getYear() == tanggalGaji.getYear()) {
						 if(karyawanDto.getPosisi().getNamaPosisi().equalsIgnoreCase("Programmer")) {
							 uangBonus = (p.getBonusPg().multiply(BigDecimal.valueOf(lb.getVariableBonus()))).divide(BigDecimal.valueOf(p.getBatasanBonusPg()));
							}
							if(karyawanDto.getPosisi().getNamaPosisi().equalsIgnoreCase("Tester")) {
								uangBonus = (p.getBonusTs().multiply(BigDecimal.valueOf(lb.getVariableBonus()))).divide(BigDecimal.valueOf(p.getBatasanBonusTs()));
							}
							if(karyawanDto.getPosisi().getNamaPosisi().equalsIgnoreCase("Technical Writter")) {
								uangBonus = (p.getBonusTw().multiply(BigDecimal.valueOf(lb.getVariableBonus()))).divide(BigDecimal.valueOf(p.getBatasanBonusTw()));
							}
					 }
				 } 
			 }
			 
			 for(Parameter p : parameterRepository.findAll()) {
				 if(p.getTbParameter().getMonth() == tanggalGaji.getMonth() && p.getTbParameter().getYear() == tanggalGaji.getYear()) {
					if(uangBonus.doubleValue() > p.getMaxBonus().doubleValue()) {
						uangBonus = p.getMaxBonus();
					}
				 }
			 }
		 }
		 
		 
		 return uangBonus;
	 }
	 
	 // Menghitung takeHomePay
	 public BigDecimal calculateTakeHomePay(BigDecimal gajiBersih, BigDecimal uangLembur, BigDecimal uangBonus) {
		 BigDecimal takeHomePay = new BigDecimal(0);
		 takeHomePay = (gajiBersih.add(uangLembur).add(uangBonus));
		 return takeHomePay;
	 }
	 
	 //Proses untuk inisialisasi object pendapatan
	 public List<Pendapatan> initializationPendapatan(Date tanggalGaji) {
		List<Pendapatan> listPendapatan = new ArrayList<Pendapatan>();
		BigDecimal gajiPokok = new BigDecimal(0);
    	BigDecimal tunjanganKeluarga = new BigDecimal(0);
    	BigDecimal tunjanganPegawai = new BigDecimal(0);
    	BigDecimal tunjanganTransportasi = new BigDecimal(0);
    	BigDecimal bpjs = new BigDecimal(0);
    	BigDecimal pph = new BigDecimal(0);
    	BigDecimal gajiKotor = new BigDecimal(0);
    	BigDecimal gajiBersih = new BigDecimal(0);
    	BigDecimal uangLembur = new BigDecimal(0);
    	BigDecimal uangBonus = new BigDecimal(0);
    	BigDecimal takeHomePay = new BigDecimal(0);
	    LemburBonus lemburBonus = new LemburBonus();
    	for(Karyawan k : karyawanRepository.findAll()) {
			KaryawanDTO karyawanDto = convertToDTO(k);
			gajiPokok = calculateGajiPokok(karyawanDto);
			tunjanganKeluarga = getTunjanganKeluarga(karyawanDto, gajiPokok, tanggalGaji);
			tunjanganPegawai = getTunjanganPegawai(karyawanDto);
			tunjanganTransportasi = getTunjanganTransportation(karyawanDto, tanggalGaji);
			bpjs = calculatePotonganBPJS(gajiPokok, tanggalGaji);
			pph = calculatePph(gajiKotor);
			gajiKotor = calculateGajiKotor(gajiPokok, tunjanganKeluarga, tunjanganTransportasi, tunjanganPegawai);
			gajiBersih = calculateGajiBersih(gajiKotor, bpjs, pph);
			uangLembur = calculateUangLembur(karyawanDto, gajiKotor, tanggalGaji);
			uangBonus = calculateUangBonus(karyawanDto, gajiKotor, tanggalGaji);
			takeHomePay = calculateTakeHomePay(gajiBersih, uangLembur, uangBonus);
			
			Pendapatan pendapatan = new Pendapatan();
			if(lemburBonus.getIdKaryawan() == k.getIdKaryawan()) {
				pendapatan.setLamaLembur(lemburBonus.getLamaLembur());
				pendapatan.setVariableBonus(lemburBonus.getVariableBonus());
			}
			pendapatan.setKaryawan(convertToEntity(karyawanDto));
			pendapatan.setTanggalGaji(tanggalGaji);
			pendapatan.setGajiPokok(gajiPokok);
			pendapatan.setTunjanganKeluarga(tunjanganKeluarga);
			pendapatan.setTunjanganPegawai(tunjanganPegawai);
			pendapatan.setTunjanganTransport(tunjanganTransportasi);
			pendapatan.setBpjs(bpjs);
			pendapatan.setPphPerbulan(pph);
			pendapatan.setGajiKotor(gajiKotor);
			pendapatan.setGajiBersih(gajiBersih);
			pendapatan.setUangLembur(uangLembur);
			
			pendapatan.setUangBonus(uangBonus);
			pendapatan.setTakeHomePay(takeHomePay);
			
			listPendapatan.add(pendapatan);
    	}
    	Collections.sort(listPendapatan, Comparator.comparing(Pendapatan::getIdPendapatan));
		return listPendapatan;
	 }
	 
	 // Proses Insert 
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
//    			pendapatanRepository.save(p);
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
//	    			pendapatanRepository.save(p);
	    			resutlList.add(p);	
	    			message = "Update Success!";
	    			index++;
    			} else {
    				for(Pendapatan temp : initializationPendapatan(tanggalGaji)) {
//    	    			pendapatanRepository.save(temp);
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
