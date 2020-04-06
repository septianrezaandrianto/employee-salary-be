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

import com.employee.Employee.dto.AgamaDTO;
import com.employee.Employee.dto.KaryawanDTO;
import com.employee.Employee.dto.LemburBonusDTO;
import com.employee.Employee.dto.PendapatanDTO;
import com.employee.Employee.dto.PosisiDTO;
import com.employee.Employee.model.Agama;
import com.employee.Employee.model.Karyawan;
import com.employee.Employee.model.LemburBonus;
import com.employee.Employee.model.Parameter;
import com.employee.Employee.model.Pendapatan;
import com.employee.Employee.model.Penempatan;
import com.employee.Employee.model.Posisi;
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
public class LemburBonusRenoController {


private static final String DateTimeFormat = null;
//====================================MODEL MAPPER================================================================
	ModelMapper modelMapper = new ModelMapper();
	 
	 public LemburBonusDTO convertToDTO(LemburBonus lemburBonus) {
	        return modelMapper.map(lemburBonus, LemburBonusDTO.class);
	    }

    public LemburBonus convertToEntity(LemburBonusDTO lemburBonusDto) {
       return modelMapper.map(lemburBonusDto, LemburBonus.class);
    }
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
	
	//Get All Data
	@GetMapping("/lemburbonus/reno/all")
	public HashMap<String, Object> getAllLemburBonus() {
		HashMap<String, Object> showHashMap = new HashMap<String, Object>();
		List<LemburBonusDTO> listLemburBonus = new ArrayList<LemburBonusDTO>();
		for(LemburBonus a : lemburBonusRepository.findAll()) {
			LemburBonusDTO lemburBonusDto = convertToDTO(a);
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
	
	@PostMapping("/lemburbonus/reno/add")
	public HashMap<String, Object> addToLemburBonus(@RequestBody LemburBonus inputLemburBonus){
		HashMap<String, Object> process = new HashMap<String, Object>();
		LemburBonusDTO lemburBonusDTO = new LemburBonusDTO();
		List<LemburBonusDTO> listLemburBonusDTO = new ArrayList<LemburBonusDTO>();
		ArrayList<LemburBonus> listLemburBonus = new ArrayList<LemburBonus>();
		for(LemburBonus lb: lemburBonusRepository.findAll()) {
			listLemburBonus.add(lb);
		}
		for(Karyawan k: karyawanRepository.findAll()) {
			LemburBonus lemburBonus = new LemburBonus();
			
			if(listLemburBonus.isEmpty()) {
				lemburBonus = new LemburBonus(0, Long.valueOf(k.getIdKaryawan()), inputLemburBonus.getTanggalLemburBonus(), inputLemburBonus.getLamaLembur(), inputLemburBonus.getVariableBonus());
				lemburBonusRepository.save(lemburBonus);
			}else {
				boolean isKaryawanExist = isKaryawanExistInLemburBonus(inputLemburBonus.getIdKaryawan());
				
				if(!isKaryawanExist && inputLemburBonus.getIdKaryawan() != null) {
					lemburBonus = new LemburBonus(0, inputLemburBonus.getIdKaryawan(), inputLemburBonus.getTanggalLemburBonus(), inputLemburBonus.getLamaLembur(), inputLemburBonus.getVariableBonus());
					lemburBonusRepository.save(lemburBonus);
					
				}else {
					boolean isMonthExist = isMonthExistInLemburBonus(inputLemburBonus.getTanggalLemburBonus(), listLemburBonus);
					if(isMonthExist) {
						for(LemburBonus lb: listLemburBonus) {
							if(lb.getIdKaryawan() == k.getIdKaryawan() && lb.getTanggalLemburBonus().getMonth() == inputLemburBonus.getTanggalLemburBonus().getMonth()) {
								lemburBonus = new LemburBonus(lb.getIdLemburBonus(), lb.getIdKaryawan(), inputLemburBonus.getTanggalLemburBonus(), inputLemburBonus.getLamaLembur(), inputLemburBonus.getVariableBonus());
								lemburBonusRepository.save(lemburBonus);
							}
							
						}
					}else {
						lemburBonus = new LemburBonus(0, Long.valueOf(k.getIdKaryawan()), inputLemburBonus.getTanggalLemburBonus(), inputLemburBonus.getLamaLembur(), inputLemburBonus.getVariableBonus());
						lemburBonusRepository.save(lemburBonus);
					}
						
				}
			}
			lemburBonusDTO = modelMapper.map(lemburBonus, LemburBonusDTO.class);
			listLemburBonusDTO.add(lemburBonusDTO);
		}
		
		
		process.put("Data", listLemburBonusDTO);
		
		return process;
	}
	
	//Validasi Karyawan di Pendapatan
	public boolean isKaryawanExistInLemburBonus(Long idKaryawan) {
		boolean isExists = false;
		for(LemburBonus p: lemburBonusRepository.findAll()) {
			if(p.getIdKaryawan() == idKaryawan) {
				isExists = true;
			}
		}
		return isExists;
	}
	public boolean isMonthExistInLemburBonus(Date date, ArrayList<LemburBonus> listLemburBonus) {
		boolean isExists = false;
		for(LemburBonus p: listLemburBonus) {
			if(date.getMonth() == p.getTanggalLemburBonus().getMonth()) {
				isExists = true;
			}
		}
		return isExists;
	}
		
	@PutMapping("/lemburbonus/reno/update/{id}")
	public HashMap<String, Object> update(@PathVariable(value = "id") int id, @Valid @RequestBody LemburBonusDTO lemburBonusDto) {
		HashMap<String, Object> process = new HashMap<String, Object>();
		LemburBonus tempLemburBonus = lemburBonusRepository.findById(Long.valueOf(id)).orElse(null);      
		
		lemburBonusDto.setIdLemburBonus(tempLemburBonus.getIdLemburBonus());
		lemburBonusDto.setIdKaryawan(tempLemburBonus.getIdKaryawan());
	    if (lemburBonusDto.getTanggalLemburBonus() == null) {
	    	lemburBonusDto.setTanggalLemburBonus(tempLemburBonus.getTanggalLemburBonus());
	    }
	    if (lemburBonusDto.getLamaLembur() == 0) {
	    	lemburBonusDto.setLamaLembur(tempLemburBonus.getLamaLembur());
	    }
	    if (lemburBonusDto.getVariableBonus() == 0) {
	    	lemburBonusDto.setVariableBonus(tempLemburBonus.getVariableBonus());
	    }
	    
	    tempLemburBonus = convertToEntity(lemburBonusDto);
	    
	    lemburBonusRepository.save(tempLemburBonus);
	    process.put("Message", "Success Updated Data");
	    process.put("Data", tempLemburBonus);
	    return process;
	}
	   
	// Delete a User
	@DeleteMapping("/lemburbonus/reno/delete/{id}")
	public HashMap<String, Object> delete(@PathVariable(value = "id") int id) {
	   	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
	   	LemburBonus tempLemburBonus = lemburBonusRepository.findById(Long.valueOf(id)).orElse(null);
	   	LemburBonusDTO lemburBonusDTO = convertToDTO(tempLemburBonus);
	   	lemburBonusRepository.delete(tempLemburBonus);

	    showHashMap.put("Messages", "Delete Data Success!");
	    showHashMap.put("Delete data :", lemburBonusDTO);
	   	return showHashMap;
	   }
}
	 
