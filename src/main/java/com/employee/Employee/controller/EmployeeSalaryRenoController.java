package com.employee.Employee.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.Employee.dto.KaryawanDTO;
import com.employee.Employee.dto.LemburBonusDTO;
import com.employee.Employee.dto.PendapatanDTO;
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
import com.fasterxml.jackson.annotation.JsonFormat;

@RestController
@RequestMapping("/api")
public class EmployeeSalaryRenoController {


private static final String DateTimeFormat = null;
//====================================MODEL MAPPER================================================================
	ModelMapper modelMapper = new ModelMapper();
//====================================================================================================    
	@Autowired
	PosisiRepository posisiRepository;
	@Autowired
	TingkatanRepository tingkatanRepository;
	@Autowired
	ParameterRepository parameterRepository;
	@Autowired
	PresentaseGajiRepository presentaseGajiRepository;
	@Autowired
	KaryawanRepository karyawanRepository;
	@Autowired
	PenempatanRepository penempatanRepository;
	@Autowired
	PendapatanRepository pendapatanRepository;
	@Autowired
	TunjanganPegawaiRepository tunjanganPegawaiRepository;
	@Autowired
	LemburBonusRepository lemburBonusRepository;
	
	@GetMapping("/pendapatan/reno/all")
	public HashMap<String, Object> getAllLemburBonus() {
		HashMap<String, Object> showHashMap = new HashMap<String, Object>();
		List<PendapatanDTO> listLemburBonus = new ArrayList<PendapatanDTO>();
		for(Pendapatan a : pendapatanRepository.findAll()) {
			PendapatanDTO lemburBonusDto = modelMapper.map(a, PendapatanDTO.class);
			listLemburBonus.add(lemburBonusDto);
		}
		
		String message;
	    if(listLemburBonus.isEmpty()) {
			message = "Read All Failed!";
		} else {
			message = "Read All Success!";
		}
		showHashMap.put("Message", message);
		showHashMap.put("Total", listLemburBonus.size());
		showHashMap.put("Data", listLemburBonus);
		
		return showHashMap;
	}
	
	//ADD TO PENDAPATAN
	@PostMapping("/pendapatan/reno/run")
	@ResponseBody
	public HashMap<String, Object> addToPendapatan(@RequestParam String date) throws ParseException{
		Date dateGaji = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		HashMap<String, Object> process = new HashMap<String, Object>();
		ArrayList<PendapatanDTO> listPendapatanDTO = new ArrayList<PendapatanDTO>();
//		ArrayList<Pendapatan> listPendapatan = (ArrayList<Pendapatan>) pendapatanRepository.findAll();
		ArrayList<Pendapatan> listPendapatan = new ArrayList<Pendapatan>();
		for(Pendapatan p : pendapatanRepository.findAll()) {
			listPendapatan.add(p);
		}
		for(Karyawan k : karyawanRepository.findAll()) {
				
			KaryawanDTO kDTO = modelMapper.map(k, KaryawanDTO.class);
			BigDecimal gajiPokok = getGajiPokok(kDTO.getIdKaryawan());
			BigDecimal tjPegawai = getTunjanganPegawai(kDTO.getIdKaryawan());
			BigDecimal tjKeluarga = getTunjanganKeluarga(kDTO.getIdKaryawan());
			BigDecimal tjKhusus = getTunjanganKhusus(kDTO.getIdKaryawan());
			BigDecimal gajiKotor = getTotalPenghasilan(kDTO.getIdKaryawan());
			BigDecimal pph = getPPH(kDTO.getIdKaryawan());
			BigDecimal bpjs = getBPJS(kDTO.getIdKaryawan());
			BigDecimal gajiBersih = getGajiBersih(kDTO.getIdKaryawan());
			LemburBonus lemburBonus = getLemburBonus(kDTO.getIdKaryawan());
			BigDecimal uangLembur = getUangLembur(kDTO.getIdKaryawan());
			BigDecimal uangBonus = getUangBonus(kDTO.getIdKaryawan());
			BigDecimal takeHomePay = getTakeHomePay(kDTO.getIdKaryawan());
			Pendapatan pendapatan = new Pendapatan();
			if(!listPendapatan.isEmpty()) {
				boolean isExistKaryawan = isKaryawanExistInPendapatan(k.getIdKaryawan());
				
				if(!isExistKaryawan) {
					pendapatan = new Pendapatan(0, k, dateGaji, gajiPokok, tjKeluarga, tjPegawai, tjKhusus, gajiKotor, pph, bpjs, gajiBersih, lemburBonus.getLamaLembur(), uangLembur, lemburBonus.getVariableBonus(), uangBonus, takeHomePay);
					pendapatanRepository.save(pendapatan);
				}else {
					boolean isExistMonth = isMonthExistInPendapatan(dateGaji,listPendapatan);
					if(isExistMonth) {
						for(Pendapatan p: listPendapatan) {
							if(k.getIdKaryawan() == p.getKaryawan().getIdKaryawan() && p.getTanggalGaji().getMonth() == dateGaji.getMonth()) {
								pendapatan = new Pendapatan(p.getIdPendapatan(), k, dateGaji, gajiPokok, tjKeluarga, tjPegawai, tjKhusus, gajiKotor, pph, bpjs, gajiBersih, lemburBonus.getLamaLembur(), uangLembur, lemburBonus.getVariableBonus(), uangBonus, takeHomePay);
								pendapatanRepository.save(pendapatan);
							}				
						}
					}else {
						pendapatan = new Pendapatan(0, k, dateGaji, gajiPokok, tjKeluarga, tjPegawai, tjKhusus, gajiKotor, pph, bpjs, gajiBersih, lemburBonus.getLamaLembur(), uangLembur, lemburBonus.getVariableBonus(), uangBonus, takeHomePay);
						pendapatanRepository.save(pendapatan);
					}
				}
			}else {
				pendapatan = new Pendapatan(0, k, dateGaji, gajiPokok, tjKeluarga, tjPegawai, tjKhusus, gajiKotor, pph, bpjs, gajiBersih, lemburBonus.getLamaLembur(), uangLembur, lemburBonus.getVariableBonus(), uangBonus, takeHomePay);
				pendapatanRepository.save(pendapatan);
			}
			PendapatanDTO pDTO = modelMapper.map(pendapatan, PendapatanDTO.class);
			listPendapatanDTO.add(pDTO);
			
//			process.put("Id: "+kDTO.getIdKaryawan(), kDTO.getTanggalLahir().getMonth());
		}
//		process.put("Tanggal: ", dateGaji.getMonth());
		process.put("DATA: ", listPendapatanDTO);
		return process;
	}
	
	//Validasi Karyawan di Pendapatan
	public boolean isKaryawanExistInPendapatan(Integer idKaryawan) {
		boolean isExists = false;
		for(Pendapatan p:pendapatanRepository.findAll()) {
			if(p.getKaryawan().getIdKaryawan() == idKaryawan) {
				isExists = true;
			}
		}
		return isExists;
	}
	public boolean isMonthExistInPendapatan(Date date, ArrayList<Pendapatan> listPendapatan) {
		boolean isExists = false;
		for(Pendapatan p: listPendapatan) {
			if(p.getTanggalGaji().getMonth() == date.getMonth()) {
				isExists = true;
			}
		}
		return isExists;
	}
	
	//PresentaseGaji
    public BigDecimal getPresentaseGaji(Integer id) {
    	BigDecimal result = new BigDecimal(0);
    	Karyawan karyawan = karyawanRepository.findById(id).orElse(null);
		KaryawanDTO kDTO = modelMapper.map(karyawan, KaryawanDTO.class);
		for(PresentaseGaji pg: presentaseGajiRepository.findAll()) {
			if(kDTO.getPosisi().getIdPosisi() == pg.getPosisi().getIdPosisi()) {
				if(kDTO.getTingkatan().getIdTingkatan() == pg.getIdTingkatan()) {
					if(kDTO.getMasaKerja() >= pg.getMasaKerja()) {
						result = pg.getBesaranGaji();					
					}
				}			
			}
		}
    	return result;
    }
	
	//Gaji Pokok
    @GetMapping("/gapok/{id}")
    public BigDecimal getGajiPokok(@PathVariable(value = "id") Integer id) {
    	BigDecimal result = new BigDecimal(0);
    	Karyawan karyawan = karyawanRepository.findById(id).orElse(null);
    	KaryawanDTO kDTO = modelMapper.map(karyawan, KaryawanDTO.class);
		BigDecimal presentaseGaji = getPresentaseGaji(kDTO.getIdKaryawan());
		for(Penempatan pen: penempatanRepository.findAll()) {
			if(kDTO.getPenempatan().getIdPenempatan() == pen.getIdPenempatan()) {
				result = presentaseGaji.multiply(pen.getUmkPenempatan());
			}
		}

    	return result;
    }
    
    //Tunjangan Keluarga
    public BigDecimal getTunjanganKeluarga(Integer id) {
    	BigDecimal result = new BigDecimal(0);
		BigDecimal gapok = (BigDecimal)getGajiPokok(id);
		for(Parameter p: parameterRepository.findAll()) {
			result = gapok.multiply(p.getTKeluarga());
		}
    	return result;
    }
    
    //Potongan BPJS
    public BigDecimal getBPJS(Integer id) {
    	BigDecimal result = new BigDecimal(0);
		BigDecimal gapok = (BigDecimal)getGajiPokok(id);
		for(Parameter p: parameterRepository.findAll()) {
			result = gapok.multiply(p.getPBpjs());
		}
    	return result;
    }
    
	//Tunjangan Khusus
    public BigDecimal getTunjanganKhusus(Integer id) {
    	BigDecimal result = new BigDecimal(0);
    	Karyawan karyawan = karyawanRepository.findById(id).orElse(null);
    	KaryawanDTO kDTO = modelMapper.map(karyawan, KaryawanDTO.class);
		for(Parameter p: parameterRepository.findAll()) {
    		if(kDTO.getPenempatan().getIdPenempatan() == 1) {
    			result = p.getTTransport();
    		}else {
    			result = BigDecimal.valueOf(0);
    		}
		}

    	return result;
    }
    
    //TunjanganPegawai
    public BigDecimal getTunjanganPegawai(Integer id) {
    	BigDecimal result = new BigDecimal(0);
    	Karyawan karyawan = karyawanRepository.findById(id).orElse(null);
		KaryawanDTO kDTO = modelMapper.map(karyawan, KaryawanDTO.class);
		for(TunjanganPegawai tp: tunjanganPegawaiRepository.findAll()) {
			if(kDTO.getPosisi().getIdPosisi() == tp.getPosisi().getIdPosisi() && kDTO.getTingkatan().getIdTingkatan() == tp.getTingkatan().getIdTingkatan()) {
				result = tp.getBesaranTujnaganPegawai();
			}
		}   	
    	return result;
    }
    
    //Total
    @GetMapping("/testtot/{id}")
    public BigDecimal getTotalPenghasilan(@PathVariable(value = "id") Integer id) {
    	BigDecimal result = new BigDecimal(0);
		BigDecimal gapok = (BigDecimal)getGajiPokok(id);
		BigDecimal tjPegawai = (BigDecimal)getTunjanganPegawai(id);
		BigDecimal tjKeluarga = (BigDecimal)getTunjanganKeluarga(id);
		BigDecimal tjKhusus = (BigDecimal)getTunjanganKhusus(id);
		result = (gapok.add(tjPegawai)).add(tjKeluarga.add(tjKhusus));
    	
		return result;
    }
    
    //PPH
    @GetMapping("/pph/{id}")
    public BigDecimal getPPH(@PathVariable(value = "id") Integer id) {
    	BigDecimal result = new BigDecimal(0);
		BigDecimal totalPenghasilan = (BigDecimal)getTotalPenghasilan(id);
		result = totalPenghasilan.multiply(BigDecimal.valueOf(0.05));
    	
    	return result;
    }
    
    //Gaji Bersih
    @GetMapping("/gajibersih/{id}")
    public BigDecimal getGajiBersih(@PathVariable(value = "id")Integer id) {
    	BigDecimal result = new BigDecimal(0);
		BigDecimal totalPenghasilan = (BigDecimal)getTotalPenghasilan(id);
		BigDecimal bpjs = (BigDecimal)getBPJS(id);
		BigDecimal pph = (BigDecimal)getPPH(id);
		result = totalPenghasilan.subtract(bpjs).subtract(pph);
    	
    	return result;
    }
    
    //get LemburBonus By ID Karyawan
    public LemburBonus getLemburBonus(Integer idKaryawan) {
    	LemburBonus lemburBonus = new LemburBonus();
    	for(LemburBonus lb: lemburBonusRepository.findAll()) {
    		if(Long.valueOf(idKaryawan) == lb.getIdKaryawan()) {
    			lemburBonus = lb;
    		}
    	}
    	return lemburBonus;
    }
    
    //get Uang Lembur
    @GetMapping("/uanglembur/reno/{id}")
    public BigDecimal getUangLembur(@PathVariable(value = "id")Integer id) {
    	BigDecimal result = new BigDecimal(0);
		BigDecimal totalPenghasilan = (BigDecimal)getTotalPenghasilan(id);
		LemburBonus lemburBonus = getLemburBonus(id);
		for(Parameter p: parameterRepository.findAll()) {
			result = (totalPenghasilan.multiply(p.getLembur())).multiply(BigDecimal.valueOf(lemburBonus.getLamaLembur()));
		}
    	
    	return result;
    }
    
    //get Uang Bonus
    @GetMapping("/uangbonus/reno/{id}")
    public BigDecimal getUangBonus(@PathVariable(value = "id")Integer id) {
    	BigDecimal result = new BigDecimal(0);
		Karyawan karyawan = karyawanRepository.findById(id).orElse(null);
		LemburBonus lemburBonus = getLemburBonus(id);
		BigDecimal variableBonus = BigDecimal.valueOf(lemburBonus.getVariableBonus());
		for(Parameter p: parameterRepository.findAll()) {
			if(karyawan.getPosisi().getIdPosisi() == 1) {
				result = (p.getBonusPg().multiply(variableBonus)).divide(BigDecimal.valueOf(p.getBatasanBonusPg()));
			}
			if(karyawan.getPosisi().getIdPosisi() == 3) {
				result = (p.getBonusTs().multiply(variableBonus)).divide(BigDecimal.valueOf(p.getBatasanBonusTs()));
			}
			if(karyawan.getPosisi().getIdPosisi() == 4) {
				result = (p.getBonusTw().multiply(variableBonus)).divide(BigDecimal.valueOf(p.getBatasanBonusTw()));
			}
		}
    	
    	return result;
    }
    
    //TakeHomePay
    @GetMapping("/takehomepay/reno/{id}")
    public BigDecimal getTakeHomePay(@PathVariable(value = "id")Integer id) {
    	BigDecimal result = new BigDecimal(0);
		BigDecimal gajiBersih = (BigDecimal)getGajiBersih(id);
		BigDecimal uangLembur = (BigDecimal)getUangLembur(id);
		BigDecimal uangBonus = (BigDecimal)getUangBonus(id);
		result = (gajiBersih.add(uangLembur)).add(uangBonus);
    	
    	return result;
    }
}
	 
