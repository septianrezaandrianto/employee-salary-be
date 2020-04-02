package com.employee.Employee.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Formatter;
import java.util.HashMap;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.Employee.dto.KaryawanDTO;
import com.employee.Employee.dto.ParameterDTO;
import com.employee.Employee.dto.PenempatanDTO;
import com.employee.Employee.dto.PresentaseGajiDTO;
import com.employee.Employee.dto.TunjanganPegawaiDTO;
import com.employee.Employee.model.Karyawan;
import com.employee.Employee.model.Parameter;
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
public class CalculateSalaryRezaController {
	
	@Autowired
	private PendapatanRepository pendapatanRepository;
	@Autowired
	private PresentaseGajiRepository presentaseGajiRepository;
	@Autowired
	private ParameterRepository parameterRepository;
	@Autowired
	private PenempatanRepository penempatanRepository;
	@Autowired
	private KaryawanRepository karyawanRepository;
	@Autowired
	private TunjanganPegawaiRepository tunjanganPegawaiRepository;
		
	ModelMapper modelMapper = new ModelMapper();

	
	@GetMapping("/cal/presentasegaji/{id}")
	public BigDecimal getCalculateRates(@PathVariable(value="id") Integer idKaryawan) {
		double temp=0;
		BigDecimal bd = BigDecimal.valueOf(temp);
		
		for (Karyawan kar : karyawanRepository.findAll()) {
			for (PresentaseGaji preGaji : presentaseGajiRepository.findAll()) {
				
				if (kar.getIdKaryawan() == idKaryawan) {
					if (kar.getPosisi().getIdPosisi() == preGaji.getPosisi().getIdPosisi()) {
						if (kar.getTingkatan().getIdTingkatan() == preGaji.getIdTingkatan()) {
							if (kar.getMasaKerja() >= preGaji.getMasaKerja()) {
								bd = modelMapper.map(preGaji, PresentaseGajiDTO.class).getBesaranGaji();
							}
						}
					}
				}
			}
		}
	return bd;	
	}
	
	//Calculate Gaji Pokok
	@GetMapping("/cal/gapok/{id}")
	public BigDecimal getCalculateGapok(@PathVariable(value="id") Integer idKaryawan) {
		double temp=0;
		BigDecimal bd = BigDecimal.valueOf(temp);
		
		for (Karyawan kar : karyawanRepository.findAll()) {
			for (Penempatan pen : penempatanRepository.findAll()) {
				
				if (kar.getIdKaryawan() == idKaryawan) {			
					if(kar.getPenempatan().getIdPenempatan() == pen.getIdPenempatan()) {
						bd = modelMapper.map(pen, PenempatanDTO.class).getUmkPenempatan().multiply(getCalculateRates(idKaryawan));
					}
				}
			}		
		}
	return bd;
	}
	
	
	//Calculate bonus penempatan untuk di jakarta
	@GetMapping("cal/tunjanganpenempatan/{id}")
	public BigDecimal getCalculateBonusPenempatan(@PathVariable(value="id") Integer idKaryawan ) {
		double temp=0;
		BigDecimal bd = BigDecimal.valueOf(temp);
		
		for (Karyawan kar : karyawanRepository.findAll()) {
			for (Parameter param : parameterRepository.findAll()) {
				
				if (kar.getIdKaryawan() == idKaryawan ) {
					if (kar.getPenempatan().getIdPenempatan() == 1) {					
						bd =  modelMapper.map(param, ParameterDTO.class).getTTransport();										
					}
				}
			}
		}
	return bd;
	}	

	
//	Calculate tunjangan jika sudah berkeluarga
	@GetMapping("cal/tunjangankeluarga/{id}")
	public BigDecimal getCalculateTunjanganKeluarga(@PathVariable(value="id") Integer idKaryawan) {
		double temp =0;
		BigDecimal bd = BigDecimal.valueOf(temp);
		
		for (Karyawan kar : karyawanRepository.findAll()) {
			for (Parameter param : parameterRepository.findAll()) {
				
				if (kar.getIdKaryawan() == idKaryawan) {
					if(kar.getStatusPernikahan() == 1) {
						bd = modelMapper.map(param, ParameterDTO.class).getTKeluarga().multiply(getCalculateGapok(idKaryawan));
					}
				}
			}
		}	
	return bd;
	}
	

	
//	Calculate potongan bpjs
	@GetMapping("/cal/potonganbpjs/{id}")
	public BigDecimal getCalculatePotonganBpjs(@PathVariable(value="id") Integer idKaryawan) {
		double temp=0;
		BigDecimal bd = BigDecimal.valueOf(temp);
		
		for(Karyawan kar: karyawanRepository.findAll()) {
			for (Parameter param: parameterRepository.findAll()) {
				
				if (kar.getIdKaryawan() == idKaryawan) {
					bd = modelMapper.map(param, ParameterDTO.class).getPBpjs().multiply(getCalculateGapok(idKaryawan));				
				}
			}
		}
	return bd;
	}
	
//	Calculate tunjangan pegawai
	@GetMapping("cal/tunjanganpegawai/all")
	public HashMap<Integer, Object> getCalculateTunjanganPegawai() {
		
		HashMap<Integer, Object> hasMap = new HashMap<Integer, Object>();
		
		for (Karyawan kar : karyawanRepository.findAll()) {
			for (TunjanganPegawai tp : tunjanganPegawaiRepository.findAll()) {
				
				if (kar.getPosisi().getIdPosisi() == tp.getPosisi().getIdPosisi()) {
					if (kar.getTingkatan().getIdTingkatan() == tp.getTingkatan().getIdTingkatan()) {
						hasMap.put(kar.getIdKaryawan(), modelMapper.map(tp, TunjanganPegawaiDTO.class).getBesaranTujnaganPegawai());
					}
				}
			}
		}
	return hasMap;
	}
	
//	Calculate gaji Kotor
	@GetMapping("cal/gajikotor/{id}")
	public BigDecimal getCalculateGajiKotor(@PathVariable(value="id") Integer idKaryawan) {
		double temp=0;
		BigDecimal bd = BigDecimal.valueOf(temp);
		
		for (Karyawan kar: karyawanRepository.findAll()) {
			
			if (kar.getIdKaryawan() ==  idKaryawan) {
				BigDecimal tunjanganPegawai = (BigDecimal) getCalculateTunjanganPegawai().get(kar.getIdKaryawan());
				bd = (getCalculateGapok(idKaryawan).add(getCalculateBonusPenempatan(idKaryawan)).add(getCalculateTunjanganKeluarga(idKaryawan).add(tunjanganPegawai)));
			}
		}
	return bd;
	}
	
//	Calculate potongan pph 5%
	@GetMapping("/cal/pph/{id}")
	public BigDecimal getCalculatePPH(@PathVariable(value = "id") Integer idKaryawan) {
		double temp=0;
		BigDecimal bd = BigDecimal.valueOf(temp);
		double pphrate = 0.05;
		BigDecimal pph = BigDecimal.valueOf(pphrate);
				
		for (Karyawan kar : karyawanRepository.findAll()) {								
			
			if (kar.getIdKaryawan() == idKaryawan) {					
				bd = pph.multiply(getCalculateGajiKotor(idKaryawan));
			}
		}	
	return bd;
	}
	
//	Calculate Gaji Bersih
	@GetMapping("/cal/gajibersih/{id}")
	public BigDecimal getCalculateGajiBersih(@PathVariable(value="id") Integer idKaryawan) {
		double temp=0;
		BigDecimal bd = BigDecimal.valueOf(temp);
		
		for (Karyawan kar : karyawanRepository.findAll()) {
			
			if (kar.getIdKaryawan() == idKaryawan) {
				bd = getCalculateGajiKotor(idKaryawan).subtract(getCalculatePPH(idKaryawan)).subtract(getCalculatePotonganBpjs(idKaryawan));
			}
		}
	return bd;
	}
	
//	Calculate take home pay
	@GetMapping("cal/takehomepay/{id}")
	public BigDecimal getCalculateTakeHomePay(@PathVariable(value="id") Integer idKaryawan) {
		double temp=0;
		BigDecimal bd = BigDecimal.valueOf(temp);
		double lmbr=0;
		BigDecimal lembur = BigDecimal.valueOf(lmbr);
		double bns=0;
		BigDecimal bonus = BigDecimal.valueOf(bns);
		
		for (Karyawan kar : karyawanRepository.findAll()) {
			
			if (kar.getIdKaryawan() == idKaryawan) {
				bd = getCalculateGajiBersih(idKaryawan).add(lembur).add(bonus);
			}
		}
	return bd;
	}
	
	
//	Insert data into tabel pendapatan
//	@GetMapping("/cal/add/")
//	public HashMap<String, Object> insertIntoPendapatan(@RequestParam(value="date")
//														@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate Date) {
//		
//
//		HashMap<String, Object> hasMap = new HashMap<String, Object>();
//		
//		for (Karyawan kar : karyawanRepository.findAll()) {
//			
//		}
//		
//	return hasMap;
//	}
	
	
}
