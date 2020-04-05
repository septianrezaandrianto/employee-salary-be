package com.employee.Employee.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.Employee.dto.LemburBonusDTO;
import com.employee.Employee.model.Karyawan;
import com.employee.Employee.model.LemburBonus;
import com.employee.Employee.repository.KaryawanRepository;
import com.employee.Employee.repository.LemburBonusRepository;


@RestController
@RequestMapping("/api")
public class LemburBonusRezaController {

	@Autowired
	private LemburBonusRepository lemburBonusRepository;
	@Autowired
	private KaryawanRepository karyawanRepository;
	
	ModelMapper modelMapper = new ModelMapper();
	
//	Convert Entity To DTO
	private LemburBonusDTO convertEntityToDTO(LemburBonus lemburBonus) {
		LemburBonusDTO lemburBonusDto = modelMapper.map(lemburBonus, LemburBonusDTO.class);		
	return lemburBonusDto;
		
	}
	
//	Convert DTO To Entity
	private LemburBonus convertDTOToEntity(LemburBonusDTO lemburBonusDTO) {
		LemburBonus lemburBonus = modelMapper.map(lemburBonusDTO, LemburBonus.class);
	return lemburBonus;
	}
	
//	Melihat seluruh Data Lembur Bonus
	@GetMapping("/lemburbonus/all")
	public HashMap<String, Object> getAllLemburBonus() {
		
		HashMap<String, Object> hasMap = new HashMap<String, Object>();
		ArrayList<LemburBonusDTO> listLemburBonusDTO = new ArrayList<LemburBonusDTO>();
		
		for (LemburBonus lemburBonus: lemburBonusRepository.findAll()) {
			LemburBonusDTO lemburBonusDto = convertEntityToDTO(lemburBonus);
			listLemburBonusDTO.add(lemburBonusDto);
		}
		
		hasMap.put("Message", "Show All Data");
		hasMap.put("Total", listLemburBonusDTO.size());
		hasMap.put("Data", listLemburBonusDTO);
		
	return hasMap;
	}
	
//	Melihat data lembur bonus by id
	@GetMapping("/lemburbonus/{id}")
	public HashMap<String, Object> getLemburBonusById(@PathVariable(value = "id") Long idLemburBonus) {
		
		HashMap<String, Object> hasMap = new HashMap<String, Object>();
		LemburBonus lemburBonus = lemburBonusRepository.findById(idLemburBonus).orElse(null);
		
		LemburBonusDTO lemburBonusDTO = convertEntityToDTO(lemburBonus);
		
		hasMap.put("Message " , "Data By Id");
		hasMap.put("Data " , lemburBonusDTO);
		
	return hasMap;
	}
	
	
//	Menghapus Lembur Bonus
	@DeleteMapping("/lemburbonus/delete/{id}")
	public HashMap<String, Object> deleteLemburBonus(@PathVariable(value = "id") Long idLemburBonus) {
		
		HashMap<String, Object> hasMap = new HashMap<String, Object>();
		
		LemburBonus lemburBonus = lemburBonusRepository.findById(idLemburBonus).orElse(null);
		
		lemburBonusRepository.delete(lemburBonus);
		
		hasMap.put("Message", "Delete Success");
		hasMap.put("Data", lemburBonus);
		
	return hasMap;
	}
	
	
//	Menambah data lembur bonus
	@PostMapping("/lemburbonus/add")
	public HashMap<String, Object> addLemburBonus(@RequestParam(value="date")
												  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Valid LocalDate date) {
		
		Date lemburBonusDate = java.sql.Date.valueOf(date);	
		
		HashMap<String, Object> hasMap = new HashMap<String, Object>();
		ArrayList<LemburBonusDTO> listLemburBonusDto = new ArrayList<LemburBonusDTO>();
		
		for (LemburBonus lb : lemburBonusRepository.findAll()) {
			for (Karyawan kar: karyawanRepository.findAll()) {
						
			//	if (lb.getIdKaryawan() != kar.getIdKaryawan() && lemburBonusDate.getDate() != lb.getTanggalLemburBonus().getDate()) {
					LemburBonus lemburBonus = new LemburBonus(lb.getIdLemburBonus(), lb.getIdKaryawan(), lemburBonusDate, lb.getLamaLembur(), lb.getVariableBonus());
					lemburBonusRepository.save(lemburBonus);
					
					LemburBonusDTO lemburBonusDto = convertEntityToDTO(lemburBonus);
					listLemburBonusDto.add(lemburBonusDto);
			//	}			
			}
		}
		hasMap.put("Total", listLemburBonusDto.size());
		hasMap.put("Data", listLemburBonusDto);
		
	return hasMap;	
	}
		
	
//	Mengupdate Lembur Bonus
	@PutMapping("/lemburbonus/update/{id}")
	public HashMap<String, Object> updateLemburBonus(@PathVariable(value="id") Long idLemburBonus, @Valid @RequestBody LemburBonusDTO lemburBonusDTO) {
		
		HashMap<String, Object> hasMap = new HashMap<String, Object>();
		
		LemburBonus lemburBonus = lemburBonusRepository.findById(idLemburBonus).orElse(null);
		
		lemburBonusDTO.setIdLemburBonus(lemburBonus.getIdLemburBonus());
		
		if (lemburBonus.getTanggalLemburBonus() == null) {
			lemburBonus.setTanggalLemburBonus(convertDTOToEntity(lemburBonusDTO).getTanggalLemburBonus());
		}
		if (lemburBonus.getLamaLembur() == 0) {
			lemburBonus.setLamaLembur(convertDTOToEntity(lemburBonusDTO).getLamaLembur());
		}
		if (lemburBonus.getVariableBonus() == 0) {
			lemburBonus.setVariableBonus(convertDTOToEntity(lemburBonusDTO).getVariableBonus());
		}
		
		hasMap.put("Message " , "Update Success");
		hasMap.put("Data ", lemburBonusRepository.save(lemburBonus));
	
	return hasMap;
	}
	
}
