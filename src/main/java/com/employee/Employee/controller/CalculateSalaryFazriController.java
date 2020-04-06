package com.employee.Employee.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
   
    //Mencari LemburBonus Berdasarkan ID karyawan
     @SuppressWarnings("deprecation")
	public LemburBonus getLemburBonus(KaryawanDTO karyawanDto, Date tanggalGaji) {
     	LemburBonus lemburBonus = new LemburBonus();
     	for(LemburBonus lb: lemburBonusRepository.findAll()) {
     		if(tanggalGaji.getYear() == lb.getTanggalLemburBonus().getYear() && tanggalGaji.getMonth() == lb.getTanggalLemburBonus().getMonth() && karyawanDto.getIdKaryawan() == lb.getIdKaryawan()) {
     			lemburBonus = lb;
     		}
     	}
     	return lemburBonus;
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
				 } else {
					 for(Parameter temp : parameterRepository.findAll()) {
						 tunjanganTransportation = temp.getTTransport();
					 }
				 }
			 }
		 }
		 return tunjanganTransportation;
	 }
	 
	 // Mencari Tunjangan Keluarga
	 @SuppressWarnings("deprecation")
	public BigDecimal getTunjanganKeluarga(KaryawanDTO karyawanDto, Date tanggalGaji) {
		 BigDecimal tunjanganKeluarga = new BigDecimal(0);
		 BigDecimal gajiPokok = calculateGajiPokok(karyawanDto);
		 for(Parameter p : parameterRepository.findAll()) {
			 if(p.getTbParameter().getMonth() == tanggalGaji.getMonth() && p.getTbParameter().getYear() == tanggalGaji.getYear()) {
				 if(karyawanDto.getStatusPernikahan() == 1) {
					 tunjanganKeluarga = gajiPokok.multiply(p.getTKeluarga()) ;
				 }
			 } else {
				 for(Parameter temp : parameterRepository.findAll()) {
					 if(karyawanDto.getStatusPernikahan() == 1) {
						 tunjanganKeluarga = gajiPokok.multiply(temp.getTKeluarga()) ;
					 }
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
	public BigDecimal calculatePotonganBPJS(KaryawanDTO karyawanDto, Date tanggalGaji) {
		 BigDecimal bpjs = new BigDecimal(0);
		 BigDecimal gajiPokok = calculateGajiPokok(karyawanDto);
		 for(Parameter p : parameterRepository.findAll()) {
			 if(p.getTbParameter().getMonth() == tanggalGaji.getMonth() && p.getTbParameter().getYear() == tanggalGaji.getYear()) {
				 bpjs = gajiPokok.multiply(p.getPBpjs());
			 } else {
				 for(Parameter temp : parameterRepository.findAll()) {
					 bpjs = gajiPokok.multiply(temp.getPBpjs());
				 }
			 }
		 }
		 return bpjs;
	 }
	 
	 // Menghitung pph perbulan
	 @GetMapping("/pendapatan/pph")
	 public BigDecimal calculatePph(KaryawanDTO karyawanDto, Date tanggalGaji) {
		 BigDecimal pph = new BigDecimal(0);
		 BigDecimal gajiKotor = calculateGajiKotor(karyawanDto, tanggalGaji);
		 BigDecimal persentasePph =new BigDecimal(0.05);;
		 pph = (gajiKotor.multiply(persentasePph));
		 return pph;
	 }
	 
	 // Menghitung Gaji Kotor
	 public BigDecimal calculateGajiKotor(KaryawanDTO karyawanDto, Date tanggalGaji) {
		 BigDecimal gajiKotor = new BigDecimal(0);
		 BigDecimal gajiPokok = calculateGajiPokok(karyawanDto);
		 BigDecimal tunjanganKeluarga = getTunjanganKeluarga(karyawanDto, tanggalGaji);
		 BigDecimal tunjanganTransportasi = getTunjanganTransportation(karyawanDto, tanggalGaji);
		 BigDecimal tunjanganPegawai = getTunjanganPegawai(karyawanDto);
		 gajiKotor = (gajiPokok.add(tunjanganKeluarga).add(tunjanganTransportasi).add(tunjanganPegawai));
		 return gajiKotor;
	 }
	 
	 // Menghitung Gaji Bersih
	 public BigDecimal calculateGajiBersih(KaryawanDTO karyawanDto, Date tanggalGaji) {
		 BigDecimal gajiKotor = calculateGajiKotor(karyawanDto, tanggalGaji);
		 BigDecimal bpjs = calculatePotonganBPJS(karyawanDto, tanggalGaji);
		 BigDecimal pph = calculatePph(karyawanDto, tanggalGaji);
		 BigDecimal gajiBersih = new BigDecimal(0);
		 gajiBersih = (gajiKotor.subtract(bpjs).subtract(pph));
		 return gajiBersih;
	 }
	 
	 // Menghitung Uang Lembur
	 @SuppressWarnings("deprecation")
	 public BigDecimal calculateUangLembur(KaryawanDTO karyawanDto, Date tanggalGaji) {
		 BigDecimal uangLembur = new BigDecimal(0);
		 BigDecimal gajiKotor = calculateGajiKotor(karyawanDto, tanggalGaji);
		 for(LemburBonus lb : lemburBonusRepository.findAll()) {
			 if(karyawanDto.getIdKaryawan() == lb.getIdKaryawan() && tanggalGaji.getMonth() == lb.getTanggalLemburBonus().getMonth() && tanggalGaji.getYear() == lb.getTanggalLemburBonus().getYear()) {
				 for(Parameter p : parameterRepository.findAll()) {
					 if(p.getTbParameter().getMonth() == tanggalGaji.getMonth() && p.getTbParameter().getYear() == tanggalGaji.getYear()) {
						 uangLembur = gajiKotor.multiply(p.getLembur()).multiply(BigDecimal.valueOf(lb.getLamaLembur()));
					 } else {
						 for(Parameter temp : parameterRepository.findAll()) {
							 uangLembur = gajiKotor.multiply(temp.getLembur()).multiply(BigDecimal.valueOf(lb.getLamaLembur()));
						 }
					 }
				 } 
			 }
		 }
		 return uangLembur;
	 }
	 
	 // Menghitung Uang Bonus
	 @SuppressWarnings("deprecation")
 	 public BigDecimal calculateUangBonus(KaryawanDTO karyawanDto, Date tanggalGaji) {
		 BigDecimal uangBonus = new BigDecimal(0);
		 for(LemburBonus lb : lemburBonusRepository.findAll()) {
			 if(karyawanDto.getIdKaryawan() == lb.getIdKaryawan() && tanggalGaji.getMonth() == lb.getTanggalLemburBonus().getMonth() && tanggalGaji.getYear() == lb.getTanggalLemburBonus().getYear()) {
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
					 } else {
						 for(Parameter temp : parameterRepository.findAll()) {
							if(karyawanDto.getPosisi().getNamaPosisi().equalsIgnoreCase("Programmer")) {
								uangBonus = (temp.getBonusPg().multiply(BigDecimal.valueOf(lb.getVariableBonus()))).divide(BigDecimal.valueOf(temp.getBatasanBonusPg()));
							}
							if(karyawanDto.getPosisi().getNamaPosisi().equalsIgnoreCase("Tester")) {
								uangBonus = (temp.getBonusTs().multiply(BigDecimal.valueOf(lb.getVariableBonus()))).divide(BigDecimal.valueOf(temp.getBatasanBonusTs()));
							}
							if(karyawanDto.getPosisi().getNamaPosisi().equalsIgnoreCase("Technical Writter")) {
								uangBonus = (temp.getBonusTw().multiply(BigDecimal.valueOf(lb.getVariableBonus()))).divide(BigDecimal.valueOf(temp.getBatasanBonusTw()));
							}
						 }
					 }
				 }
			 }
			 
			 for(Parameter p : parameterRepository.findAll()) {
				 if(p.getTbParameter().getMonth() == tanggalGaji.getMonth() && p.getTbParameter().getYear() == tanggalGaji.getYear()) {
					if(uangBonus.doubleValue() > p.getMaxBonus().doubleValue()) {
						uangBonus = p.getMaxBonus();
					}
				 } else {
					 for(Parameter temp : parameterRepository.findAll()) {
						 if(uangBonus.doubleValue() > temp.getMaxBonus().doubleValue()) {
							uangBonus = temp.getMaxBonus();
						}
					 }
				 }
			 }
		 }
		 
		 return uangBonus;
	 }
	 
	 // Menghitung takeHomePay
	 public BigDecimal calculateTakeHomePay(KaryawanDTO karyawanDto, Date tanggalGaji) {
		 BigDecimal takeHomePay = new BigDecimal(0);
		 BigDecimal gajiBersih = calculateGajiBersih(karyawanDto, tanggalGaji);
		 BigDecimal uangLembur = calculateUangLembur(karyawanDto, tanggalGaji);
		 BigDecimal uangBonus = calculateUangBonus(karyawanDto, tanggalGaji);
		 takeHomePay = (gajiBersih.add(uangLembur).add(uangBonus));
		 return takeHomePay;
	 }
	 
	// Proses Validasi Karyawan di table Pendapatan
	public boolean isKaryawanExist(KaryawanDTO karyawanDto) {
		boolean isExist = false;
		for(Pendapatan p : pendapatanRepository.findAll()) {
			if(p.getKaryawan().getIdKaryawan() == karyawanDto.getIdKaryawan()) {
				isExist = true;
			}
		}
		return isExist;
	}
	
	// Proses Validasi Tanggal Gaji di table Pendapatan
	@SuppressWarnings("deprecation")
	public boolean isTanggalGajiExist( Date tanggalGaji) {
		boolean isExist = false;
		for(Pendapatan p : pendapatanRepository.findAll()) {
			if(p.getTanggalGaji().getMonth() == tanggalGaji.getMonth() && p.getTanggalGaji().getYear() == tanggalGaji.getYear()) {
				isExist = true;
			}
		}
		return isExist;
	}
		
	// Proses Insert Ke database
	public List<Pendapatan> insertCalculateSalary(Date tanggalGaji) {
		List<Pendapatan> resutlList = new ArrayList<Pendapatan>();
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
    	
    	for(Karyawan k : karyawanRepository.findAll()) {
			KaryawanDTO karyawanDto = convertToDTO(k);
			gajiPokok = calculateGajiPokok(karyawanDto);
			tunjanganKeluarga = getTunjanganKeluarga(karyawanDto, tanggalGaji);
			tunjanganPegawai = getTunjanganPegawai(karyawanDto);
			tunjanganTransportasi = getTunjanganTransportation(karyawanDto, tanggalGaji);
			bpjs = calculatePotonganBPJS(karyawanDto, tanggalGaji);
			pph = calculatePph(karyawanDto, tanggalGaji);
			gajiKotor = calculateGajiKotor(karyawanDto, tanggalGaji);
			gajiBersih = calculateGajiBersih(karyawanDto, tanggalGaji);
			uangLembur = calculateUangLembur(karyawanDto, tanggalGaji);
			uangBonus = calculateUangBonus(karyawanDto, tanggalGaji);
			takeHomePay = calculateTakeHomePay(karyawanDto, tanggalGaji);
			
			LemburBonus lemburBonus = getLemburBonus(karyawanDto, tanggalGaji);
			Pendapatan pendapatan = new Pendapatan();
				
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
			pendapatan.setLamaLembur(lemburBonus.getLamaLembur());
			pendapatan.setVariableBonus(lemburBonus.getVariableBonus());
			pendapatan.setUangBonus(uangBonus);
			pendapatan.setTakeHomePay(takeHomePay);
			pendapatanRepository.save(pendapatan);
			resutlList.add(pendapatan);	
    	}
		return resutlList;
    	
	}
	
	 // Menghitung Gaji semua karyawan
	 @SuppressWarnings("deprecation")
	 @PostMapping("/pendapatan/calculateSalary")
	 public HashMap<String, Object> calculateSalary(@RequestParam(name = "date") String date) throws ParseException {
		HashMap<String, Object> showHashMap = new HashMap<String, Object>();
		List<Pendapatan> listPendapatans = pendapatanRepository.findAll();
		List<Pendapatan> resutlList = new ArrayList<Pendapatan>();
		Date tanggalGaji = new SimpleDateFormat("yyyy-MM-dd").parse(date);
    	String message = "";
    	boolean isInsert = false;
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
    	
    	for(Karyawan k : karyawanRepository.findAll()) {
			KaryawanDTO karyawanDto = convertToDTO(k);
			gajiPokok = calculateGajiPokok(karyawanDto);
			tunjanganKeluarga = getTunjanganKeluarga(karyawanDto, tanggalGaji);
			tunjanganPegawai = getTunjanganPegawai(karyawanDto);
			tunjanganTransportasi = getTunjanganTransportation(karyawanDto, tanggalGaji);
			bpjs = calculatePotonganBPJS(karyawanDto, tanggalGaji);
			pph = calculatePph(karyawanDto, tanggalGaji);
			gajiKotor = calculateGajiKotor(karyawanDto, tanggalGaji);
			gajiBersih = calculateGajiBersih(karyawanDto, tanggalGaji);
			uangLembur = calculateUangLembur(karyawanDto, tanggalGaji);
			uangBonus = calculateUangBonus(karyawanDto, tanggalGaji);
			takeHomePay = calculateTakeHomePay(karyawanDto, tanggalGaji);
			
			LemburBonus lemburBonus = getLemburBonus(karyawanDto, tanggalGaji);
			Pendapatan pendapatan = new Pendapatan();
				
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
			pendapatan.setLamaLembur(lemburBonus.getLamaLembur());
			pendapatan.setVariableBonus(lemburBonus.getVariableBonus());
			pendapatan.setUangBonus(uangBonus);
			pendapatan.setTakeHomePay(takeHomePay);
    	
    	if(listPendapatans.isEmpty()) {
    		pendapatanRepository.save(pendapatan);
			resutlList.add(pendapatan);	
			message = "Create Success!";
    		
    	} else {
    		if(isKaryawanExist(karyawanDto)) {
    			if(isTanggalGajiExist(tanggalGaji)) {
    				for(Pendapatan p : pendapatanRepository.findAll()) {
    					if(karyawanDto.getIdKaryawan() == p.getKaryawan().getIdKaryawan() && p.getTanggalGaji().getMonth() == tanggalGaji.getMonth() && p.getTanggalGaji().getYear() == tanggalGaji.getYear()) {
							p.setKaryawan(pendapatan.getKaryawan());
							p.setTanggalGaji(tanggalGaji);
							p.setGajiPokok(pendapatan.getGajiPokok());
							p.setTunjanganKeluarga(pendapatan.getTunjanganKeluarga());
							p.setTunjanganPegawai(pendapatan.getTunjanganPegawai());
							p.setTunjanganTransport(pendapatan.getTunjanganTransport());
							p.setBpjs(pendapatan.getBpjs());
							p.setPphPerbulan(pendapatan.getPphPerbulan());
							p.setGajiKotor(pendapatan.getGajiKotor());
							p.setGajiBersih(pendapatan.getGajiBersih());
							p.setLamaLembur(pendapatan.getLamaLembur());
							p.setVariableBonus(pendapatan.getVariableBonus());
							p.setUangLembur(pendapatan.getUangLembur());
							p.setUangBonus(pendapatan.getUangBonus());
							p.setTakeHomePay(pendapatan.getTakeHomePay());
			    			pendapatanRepository.save(p);
			    			resutlList.add(p);	
			    			message = "Update Success!";
    					} 
    				}
    			} else {
    				isInsert = true;
    			}
    		} else {
    			isInsert = true;
    			}
    		}
    	}
    	
    	if(isInsert) {
    		resutlList = insertCalculateSalary(tanggalGaji);	
			message = "Create Success!";
    	}
    	showHashMap.put("Message", message);
    	showHashMap.put("Data", resutlList);
    	return showHashMap;
	 }
}
