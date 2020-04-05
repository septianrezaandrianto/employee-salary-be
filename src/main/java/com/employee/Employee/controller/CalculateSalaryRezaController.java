package com.employee.Employee.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.employee.Employee.repository.PosisiRepository;
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
	
//	Calculate Rates Lembur
	@GetMapping("/cal/rateslembur/{id}")
	public BigDecimal getCalculateLemburRates(@PathVariable(value = "id") Integer idLemburBonus) {
		
		double temp=0;
		BigDecimal bd = BigDecimal.valueOf(temp);
		
		for (LemburBonus lb :lemburBonusRepository.findAll()) {
			for (Parameter param: parameterRepository.findAll()) {
				
				int lama = lb.getLamaLembur();
				BigDecimal lamaLembur = BigDecimal.valueOf(lama) ;					
				
				if (lb.getIdLemburBonus() == idLemburBonus) {						
					bd = lamaLembur.multiply(param.getLembur());
				}
			} 		
		}
		
	return bd;
	}
	
//	Calculate Uang Lembur
	@GetMapping("/cal/uanglembur/{id}")
	public BigDecimal getCalculateUangLembur(@PathVariable(value = "id") Integer idKaryawan) {
		
		double temp = 0;
		BigDecimal bd = BigDecimal.valueOf(temp);
		
		for (LemburBonus lb : lemburBonusRepository.findAll()) {
			for (Karyawan kar : karyawanRepository.findAll()) {
											 
				if (lb.getIdLemburBonus() == idKaryawan && lb.getIdKaryawan() == kar.getIdKaryawan()) {					
					bd = getCalculateLemburRates(idKaryawan).multiply((getCalculateGajiKotor(kar.getIdKaryawan())));
				}
			}
		}
	return bd;
	}
	
//	Menentukan ketentuan bonus Tester
	@GetMapping("/cal/ketentuanbatasanbonustester/{id}")
	public BigDecimal getKetentuanBatasanBonusTester (@PathVariable(value = "id") Long idLemburBonus) {
		double temp=0;
		BigDecimal bd = BigDecimal.valueOf(temp);
		double result = 0;
		BigDecimal br = BigDecimal.valueOf(result);
	
		for (Karyawan kar : karyawanRepository.findAll()) {
			for (LemburBonus lb : lemburBonusRepository.findAll()) {
				for ( Parameter param : parameterRepository.findAll()) {

				if ( lb.getIdLemburBonus() == idLemburBonus && kar.getIdKaryawan() == lb.getIdKaryawan() && kar.getPosisi().getIdPosisi() == 3) {
						bd = BigDecimal.valueOf(lb.getVariableBonus()/100 * 100);						
							br = bd.multiply(param.getBonusTs().divide(BigDecimal.valueOf(param.getBatasanBonusTs())));					
					}
				}			
			}
		}
	return br;	
	}
	
	
//	Calculate Uang Bonus
	@GetMapping("/cal/uangbonus/{id}")
	public BigDecimal getCalculateUangBonus(@PathVariable(value = "id") Integer idKaryawan) {
		
		double temp = 0;
		BigDecimal bd = BigDecimal.valueOf(temp);
		
		for (LemburBonus lb : lemburBonusRepository.findAll()) {
			for (Parameter param : parameterRepository.findAll()) {
				for (Karyawan kar : karyawanRepository.findAll()) {			
					
					if (idKaryawan == kar.getIdKaryawan()) {
						if (kar.getIdKaryawan() == lb.getIdKaryawan()) {
							if (kar.getPosisi().getIdPosisi() == 1 ) {
								bd = BigDecimal.valueOf(lb.getVariableBonus()).multiply(param.getBonusPg());							
							}
						}							
					}					
					if (idKaryawan == kar.getIdKaryawan()) {
						if (kar.getIdKaryawan() == lb.getIdKaryawan()) {
							if (kar.getPosisi().getIdPosisi() == 3 ) {
								bd = getKetentuanBatasanBonusTester(lb.getIdLemburBonus());							
							}
						}							
					}					
					if (idKaryawan == kar.getIdKaryawan()) {
						if (kar.getIdKaryawan() == lb.getIdKaryawan()) {
							if (kar.getPosisi().getIdPosisi() == 4 ) {
								bd = BigDecimal.valueOf(lb.getVariableBonus()).multiply(param.getBonusTw());					
							}
						}							
					}
					
				//	Menentukan maximal Bonus							
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
	@GetMapping("cal/takehomepay/{id}")
	public BigDecimal getCalculateTakeHomePay(@PathVariable(value="id") Integer idKaryawan) {
		double temp=0;
		BigDecimal bd = BigDecimal.valueOf(temp);
		
		for (Karyawan kar : karyawanRepository.findAll()) {			
				
			if (kar.getIdKaryawan() == idKaryawan) {
				
				bd = getCalculateGajiBersih(idKaryawan).add(getCalculateUangLembur(idKaryawan).add(getCalculateUangBonus(idKaryawan)));
			}			
		}
	return bd;
	}
	
	
	
	
//	Insert data into tabel pendapatan
	@PostMapping("/calculatesalary/add/")
	public HashMap<String, Object> insertIntoPendapatan(@RequestParam(value="date")
														@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Valid LocalDate date) {		
		Date salaryDate = java.sql.Date.valueOf(date);	
		
		HashMap<String, Object> hasMap = new HashMap<String, Object>();

		ArrayList<PendapatanDTO> listPendapatanDto = new ArrayList<PendapatanDTO>();
		
		for (Karyawan kar : karyawanRepository.findAll()) {
				
			BigDecimal gajiPokok = getCalculateGapok(kar.getIdKaryawan());
			BigDecimal tunjanganKeluarga = getCalculateTunjanganKeluarga(kar.getIdKaryawan());
			BigDecimal tunjanganPegawai = (BigDecimal) getCalculateTunjanganPegawai().get(kar.getIdKaryawan());
			BigDecimal tunjanganTransport = getCalculateBonusPenempatan(kar.getIdKaryawan());
			BigDecimal gajiKotor = getCalculateGajiKotor(kar.getIdKaryawan());
			BigDecimal pphPerbulan = getCalculatePPH(kar.getIdKaryawan());
			BigDecimal bpjs = getCalculatePotonganBpjs(kar.getIdKaryawan());
			BigDecimal gajiBersih = getCalculateGajiBersih(kar.getIdKaryawan());
			BigDecimal uangLembur = getCalculateUangLembur(kar.getIdKaryawan());
			BigDecimal uangBonus = getCalculateUangBonus(kar.getIdKaryawan());
			BigDecimal takeHomePay = getCalculateTakeHomePay(kar.getIdKaryawan());
					
//				for (Pendapatan pen : pendapatanRepository.findAll()) {
					for (LemburBonus lb : lemburBonusRepository.findAll()) {
//					if ( pen.getKaryawan().getIdKaryawan() != kar.getIdKaryawan() && salaryDate.getMonth() != pen.getTanggalGaji().getMonth() && salaryDate.getYear() != pen.getTanggalGaji().getYear()) {
						if (lb.getIdKaryawan() == kar.getIdKaryawan()) {		
					Pendapatan pendapatan = new Pendapatan (0, kar , salaryDate, gajiPokok,tunjanganKeluarga,tunjanganPegawai,tunjanganTransport,gajiKotor,pphPerbulan,bpjs,gajiBersih, lb.getLamaLembur(), uangLembur, lb.getVariableBonus(), uangBonus, takeHomePay);
					
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
	
	
}
