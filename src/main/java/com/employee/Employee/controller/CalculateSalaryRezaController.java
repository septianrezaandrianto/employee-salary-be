package com.employee.Employee.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.Employee.dto.ParameterDTO;
import com.employee.Employee.dto.PendapatanDTO;
import com.employee.Employee.dto.PenempatanDTO;
import com.employee.Employee.dto.PresentaseGajiDTO;
import com.employee.Employee.dto.TunjanganPegawaiDTO;
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
	@Autowired
	private LemburBonusRepository lemburBonusRepository;

		
	ModelMapper modelMapper = new ModelMapper();

//	Convert Entity To DTO
	public PendapatanDTO convertPendapatanEntityToDTO (Pendapatan pendapatan) {
		PendapatanDTO pendapatanDTO = modelMapper.map(pendapatan, PendapatanDTO.class);
	return pendapatanDTO;
	}
	
//	Convert DTO to Entity
	public Pendapatan convertPendapatanDTOToEntity (PendapatanDTO pendapatanDTO) {
		Pendapatan pendapatan = modelMapper.map(pendapatanDTO, Pendapatan.class);
	return pendapatan;
	}
	

	
//	Calculate rates gaji
	public BigDecimal getCalculateRates(Integer idKaryawan) {
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
	
//	Calculate Gaji Pokok
	public BigDecimal getCalculateGapok(Integer idKaryawan) {
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
	
	
//	Calculate bonus penempatan untuk di jakarta
	public BigDecimal getCalculateBonusPenempatan(Integer idKaryawan ) {
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
	public BigDecimal getCalculateTunjanganKeluarga(Integer idKaryawan) {
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
	public BigDecimal getCalculatePotonganBpjs(Integer idKaryawan) {
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
	public BigDecimal getCalculateGajiKotor(Integer idKaryawan) {
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
	public BigDecimal getCalculatePPH(Integer idKaryawan) {
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
	public BigDecimal getCalculateGajiBersih(Integer idKaryawan) {
		double temp=0;
		BigDecimal bd = BigDecimal.valueOf(temp);
		
		for (Karyawan kar : karyawanRepository.findAll()) {
			
			if (kar.getIdKaryawan() == idKaryawan) {
				bd = getCalculateGajiKotor(idKaryawan).subtract(getCalculatePPH(idKaryawan)).subtract(getCalculatePotonganBpjs(idKaryawan));
			}
		}
	return bd;
	}
	
	
//	Untuk mengambil lama lembur jika id, bulan dan tahun matching 
	public Integer getLamaLembur (Integer idKaryawan, LocalDate date) {
		
		ZoneId defaultZone = ZoneId.systemDefault();
		Date lamaLemburDate = Date.from(date.atStartOfDay(defaultZone).toInstant());
		
		int lamaLembur=0;
		
		for (LemburBonus lb : lemburBonusRepository.findAll()) {					
		
			if (lb.getIdKaryawan() == Long.valueOf(idKaryawan) && lamaLemburDate.getMonth() == lb.getTanggalLemburBonus().getMonth() && lamaLemburDate.getYear() == lb.getTanggalLemburBonus().getYear()) {
					lamaLembur = lb.getLamaLembur();
			}
		}
	return lamaLembur;
	}
	

//	Calculate Rates Lembur with date
	public BigDecimal getCalculateLemburRates(Integer idKaryawan, LocalDate date) {
		
		ZoneId defaultZone = ZoneId.systemDefault();
		Date lamaLemburDate = Date.from(date.atStartOfDay(defaultZone).toInstant());
		
		double temp=0;
		BigDecimal bd = BigDecimal.valueOf(temp);
		
		for (LemburBonus lb :lemburBonusRepository.findAll()) {
			for (Parameter param: parameterRepository.findAll()) {
					
			int lama = getLamaLembur(idKaryawan, date);
			BigDecimal lamaLembur = BigDecimal.valueOf(lama) ;					

				if (lb.getIdKaryawan() == Long.valueOf(idKaryawan) && lb.getTanggalLemburBonus().getMonth() == lamaLemburDate.getMonth() && lb.getTanggalLemburBonus().getYear() == lamaLemburDate.getYear()) {						
					bd = lamaLembur.multiply(param.getLembur());
				}
				else if (lb.getIdKaryawan() != Long.valueOf(idKaryawan) && lb.getTanggalLemburBonus().getMonth() != lamaLemburDate.getMonth() && lb.getTanggalLemburBonus().getYear() != lamaLemburDate.getYear()){
					bd = BigDecimal.valueOf(0);
				}
			}
		} 		
		
		
	return bd;
	}
	
	
//	Calculate Uang Lembur with Date
	public BigDecimal getCalculateUangLembur(Integer idKaryawan, LocalDate date) {
		
		ZoneId defaultZone = ZoneId.systemDefault();
		Date lamaLemburDate = Date.from(date.atStartOfDay(defaultZone).toInstant());
		
		double temp = 0;
		BigDecimal bd = BigDecimal.valueOf(temp);
		
		for (LemburBonus lb : lemburBonusRepository.findAll()) {
				
			if (lb.getIdKaryawan() == Long.valueOf(idKaryawan) && lb.getTanggalLemburBonus().getMonth() == lamaLemburDate.getMonth() && lb.getTanggalLemburBonus().getYear() == lamaLemburDate.getYear()) {					
						bd = getCalculateLemburRates(idKaryawan, date).multiply(getCalculateGajiKotor(idKaryawan));
					}
					else if (lb.getIdKaryawan() != Long.valueOf(idKaryawan) && lb.getTanggalLemburBonus().getMonth() != lamaLemburDate.getMonth() && lb.getTanggalLemburBonus().getYear() != lamaLemburDate.getYear()){
						bd = BigDecimal.valueOf(0);
					}
		}
			
		
	return bd;
	}
	
	
	
//	Untuk mengambil variabel bonus jika Id, bulan dan Tahun matching
	public Integer getVariabelBonus(Integer idKaryawan, LocalDate date) {
		
		ZoneId defaultZone = ZoneId.systemDefault();
		Date lamaLemburDate = Date.from(date.atStartOfDay(defaultZone).toInstant());
		
		int variabelBonus = 0;
		for (LemburBonus lb : lemburBonusRepository.findAll()) {
			
			if (lb.getIdKaryawan() == Long.valueOf(idKaryawan) && lb.getTanggalLemburBonus().getMonth() == lamaLemburDate.getMonth() && lb.getTanggalLemburBonus().getYear() == lamaLemburDate.getYear()) {
				variabelBonus = lb.getVariableBonus();
			}
		}
	return variabelBonus;
	}
	
//	Menentukan ketentuan bonus Tester
	public BigDecimal getKetentuanBatasanBonusTester (Integer idKaryawan, LocalDate date) {
		
		ZoneId defaultZone = ZoneId.systemDefault();
		Date bonusDate = Date.from(date.atStartOfDay(defaultZone).toInstant());
		
		double temp=0;
		BigDecimal bd = BigDecimal.valueOf(temp);
		double result = 0;
		BigDecimal br = BigDecimal.valueOf(result);
	
		for (LemburBonus lb : lemburBonusRepository.findAll()) {
			for (Karyawan kar :karyawanRepository.findAll()) {
				for ( Parameter param : parameterRepository.findAll()) {

					if ( lb.getIdKaryawan() == Long.valueOf(idKaryawan) && kar.getPosisi().getIdPosisi() == 3 && lb.getTanggalLemburBonus().getMonth() == bonusDate.getMonth() && lb.getTanggalLemburBonus().getYear() == bonusDate.getYear()) {
						
						bd = BigDecimal.valueOf(getVariabelBonus(idKaryawan, date)/100 * 100);						
						br = bd.multiply(param.getBonusTs().divide(BigDecimal.valueOf(param.getBatasanBonusTs())));					
					}
				}			
			}
		}
		
	return br;	
	}
	
	
//	Calculate Uang Bonus
	public BigDecimal getCalculateUangBonus(Integer idKaryawan,LocalDate date) {
				
		ZoneId defaultZone = ZoneId.systemDefault();
		Date bonusDate = Date.from(date.atStartOfDay(defaultZone).toInstant());
		
		double temp = 0;
		BigDecimal bd = BigDecimal.valueOf(temp);
		
		for (Karyawan kar : karyawanRepository.findAll()) {
			for (LemburBonus lb : lemburBonusRepository.findAll()) {
				for (Parameter param : parameterRepository.findAll()) {					
					if  (kar.getIdKaryawan() == Long.valueOf(idKaryawan)) {
						
							if (kar.getPosisi().getIdPosisi() == 1) {
								bd = BigDecimal.valueOf(getVariabelBonus(idKaryawan, date)).multiply(param.getBonusPg());													
							}
							else if (kar.getPosisi().getIdPosisi() == 3 ) {
								bd = getKetentuanBatasanBonusTester(idKaryawan, date);							
							}
							else if (kar.getPosisi().getIdPosisi() == 4) {
								bd = BigDecimal.valueOf(getVariabelBonus(idKaryawan, date)).multiply(param.getBonusTw());
							}
						}
//					Menentukan maximal Bonus							
					BigDecimal maxBonus = param.getMaxBonus();
					if (bd.compareTo(maxBonus) == 1) {
						bd = maxBonus;
					}
				}
			}
		
		}
	return bd;
	}

	
//	Calculate take home pay
	public BigDecimal getCalculateTakeHomePay(Integer idKaryawan,  LocalDate date) {
			
		ZoneId defaultZone = ZoneId.systemDefault();
		Date lamaLemburDate = Date.from(date.atStartOfDay(defaultZone).toInstant());
		
		double temp=0;
		BigDecimal bd = BigDecimal.valueOf(temp);
				
		bd = getCalculateGajiBersih(idKaryawan).add(getCalculateUangLembur(idKaryawan, date)).add(getCalculateUangBonus(idKaryawan, date));

	return bd;
	}
	
	
	
//Insert data into tabel pendapatan
@PostMapping("/calculatesalary/add")
public HashMap<String, Object> insertIntoPendapatan(@RequestParam(value="date")
													@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Valid LocalDate date) {		
	ZoneId defaultZone = ZoneId.systemDefault();
	Date salaryDate = Date.from(date.atStartOfDay(defaultZone).toInstant());	
	
	HashMap<String, Object> hasMap = new HashMap<String, Object>();

	ArrayList<PendapatanDTO> listPendapatanDto = new ArrayList<PendapatanDTO>();
	
	for (Karyawan kar : karyawanRepository.findAll()) {
		for (LemburBonus lb : lemburBonusRepository.findAll()) {	
			BigDecimal gajiPokok = getCalculateGapok(kar.getIdKaryawan());
			BigDecimal tunjanganKeluarga = getCalculateTunjanganKeluarga(kar.getIdKaryawan());
			BigDecimal tunjanganPegawai = (BigDecimal) getCalculateTunjanganPegawai().get(kar.getIdKaryawan());
			BigDecimal tunjanganTransport = getCalculateBonusPenempatan(kar.getIdKaryawan());
			BigDecimal gajiKotor = getCalculateGajiKotor(kar.getIdKaryawan());
			BigDecimal pphPerbulan = getCalculatePPH(kar.getIdKaryawan());
			BigDecimal bpjs = getCalculatePotonganBpjs(kar.getIdKaryawan());
			BigDecimal gajiBersih = getCalculateGajiBersih(kar.getIdKaryawan());
			BigDecimal uangLembur = getCalculateUangLembur(kar.getIdKaryawan(), date);
			BigDecimal uangBonus = getCalculateUangBonus(kar.getIdKaryawan(), date);
			BigDecimal takeHomePay = getCalculateTakeHomePay(kar.getIdKaryawan(), date);
				
//			for (Pendapatan pen : pendapatanRepository.findAll()) {
				
//				if ( pen.getKaryawan().getIdKaryawan() != kar.getIdKaryawan() && salaryDate.getMonth() != pen.getTanggalGaji().getMonth() && salaryDate.getYear() != pen.getTanggalGaji().getYear()) {
				
			if (lb.getIdKaryawan() == kar.getIdKaryawan()) {
						
				Pendapatan pendapatan = new Pendapatan (kar.getIdKaryawan(), kar , salaryDate, gajiPokok,tunjanganKeluarga,tunjanganPegawai,tunjanganTransport,gajiKotor,pphPerbulan,bpjs,gajiBersih, getLamaLembur(kar.getIdKaryawan(), date), uangLembur, getVariabelBonus(kar.getIdKaryawan(), date), uangBonus, takeHomePay);
				
				pendapatanRepository.save(pendapatan);
				
				PendapatanDTO pendapatanDto = convertPendapatanEntityToDTO(pendapatan);
				
				listPendapatanDto.add(pendapatanDto);	
					
				hasMap.put("Total", listPendapatanDto.size());
				hasMap.put("Data", listPendapatanDto);
			}		
		}
	}	
return hasMap;
}

	
////	Insert data into tabel pendapatan
//	@PostMapping("/calculatesalary/add")
//	public HashMap<String, Object> insertIntoPendapatan(@RequestParam(value="date")
//														@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Valid LocalDate date) {		
//		ZoneId defaultZone = ZoneId.systemDefault();
//		Date salaryDate = Date.from(date.atStartOfDay(defaultZone).toInstant());	
//		
//		HashMap<String, Object> hasMap = new HashMap<String, Object>();
//
//		ArrayList<PendapatanDTO> listPendapatanDto = new ArrayList<PendapatanDTO>();
//		
//		for (Karyawan kar : karyawanRepository.findAll()) {
//			for (LemburBonus lb : lemburBonusRepository.findAll()) {	
//				BigDecimal gajiPokok = getCalculateGapok(kar.getIdKaryawan());
//				BigDecimal tunjanganKeluarga = getCalculateTunjanganKeluarga(kar.getIdKaryawan());
//				BigDecimal tunjanganPegawai = (BigDecimal) getCalculateTunjanganPegawai().get(kar.getIdKaryawan());
//				BigDecimal tunjanganTransport = getCalculateBonusPenempatan(kar.getIdKaryawan());
//				BigDecimal gajiKotor = getCalculateGajiKotor(kar.getIdKaryawan());
//				BigDecimal pphPerbulan = getCalculatePPH(kar.getIdKaryawan());
//				BigDecimal bpjs = getCalculatePotonganBpjs(kar.getIdKaryawan());
//				BigDecimal gajiBersih = getCalculateGajiBersih(kar.getIdKaryawan());
//				BigDecimal uangLembur = getCalculateUangLembur(kar.getIdKaryawan(), date);
//				BigDecimal uangBonus = getCalculateUangBonus(kar.getIdKaryawan(), date);
//				BigDecimal takeHomePay = getCalculateTakeHomePay(kar.getIdKaryawan(), date);
//					
////				for (Pendapatan pen : pendapatanRepository.findAll()) {
//					
////					if ( pen.getKaryawan().getIdKaryawan() != kar.getIdKaryawan() && salaryDate.getMonth() != pen.getTanggalGaji().getMonth() && salaryDate.getYear() != pen.getTanggalGaji().getYear()) {
//						if (lb.getIdKaryawan() == kar.getIdKaryawan()) {		
//					Pendapatan pendapatan = new Pendapatan (0, kar , salaryDate, gajiPokok,tunjanganKeluarga,tunjanganPegawai,tunjanganTransport,gajiKotor,pphPerbulan,bpjs,gajiBersih, getLamaLembur(kar.getIdKaryawan(), date), uangLembur, getVariabelBonus(kar.getIdKaryawan(), date), uangBonus, takeHomePay);
//					
//					pendapatanRepository.save(pendapatan);
//					
//					PendapatanDTO pendapatanDto = convertPendapatanEntityToDTO(pendapatan);
//					listPendapatanDto.add(pendapatanDto);	
//						
//					hasMap.put("Total", listPendapatanDto.size());
//					hasMap.put("Data", listPendapatanDto);
//				}		
//			}
//		}	
//	return hasMap;
//	}
	
	
}
