package com.employee.Employee.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.employee.Employee.dto.LemburBonusDTO;
import com.employee.Employee.model.LemburBonus;
import com.employee.Employee.repository.LemburBonusRepository;

@RestController
@RequestMapping("/api")
public class LemburBonusYuslamController {

	ModelMapper modelMapper = new ModelMapper(); 
	
	@Autowired
	LemburBonusRepository lemburBonusRepository;
	
	public LemburBonus convertToEntity(LemburBonusDTO lemburBonusDTO) {
		LemburBonus lemburBonus = modelMapper.map(lemburBonusDTO, LemburBonus.class);
		return lemburBonus;
	}
	
	public LemburBonusDTO convertToDto(LemburBonus lemburBonus) {
		LemburBonusDTO lemburBonusDTO = modelMapper.map(lemburBonus, LemburBonusDTO.class);
		return lemburBonusDTO;
	}
	
	//Read All Lembur Bonus
	@GetMapping("/lemburBonus/all")
	public HashMap<String, Object> readLemburBonus(){
		HashMap<String, Object> hmLemburBonus = new HashMap<String, Object>();
		List<LemburBonusDTO> lemburBonusDTOs = new ArrayList<LemburBonusDTO>();
		for(LemburBonus lemburBonus : lemburBonusRepository.findAll()) {
			lemburBonusDTOs.add(convertToDto(lemburBonus));
		}
		hmLemburBonus.put("Message : ", "Read All Lembur Bonus Succes!");
		hmLemburBonus.put("Total :", lemburBonusDTOs.size());
		hmLemburBonus.put("Data : ", lemburBonusDTOs);
		
		return hmLemburBonus;
		
	}
	
	//Read Lembur Bonus By ID
	@GetMapping("/lemburBonus/{id}")
	public HashMap<String, Object> readLemburBonusById(@PathVariable(value = "id") Long id){
		HashMap<String, Object> hmLemburBonus = new HashMap<String, Object>();
		LemburBonus lemburBonus = lemburBonusRepository.findById(id).orElseThrow(null);
		LemburBonusDTO lemburBonusDTO = convertToDto(lemburBonus);
		
		hmLemburBonus.put("Message : ", "Read Lembur Bonus By Id Succes!");
		hmLemburBonus.put("Data : ", lemburBonusDTO);
		
		return hmLemburBonus;
	}
	
	
	
	//Create Lembur Bonus
	@PostMapping("/lemburBonus/add")
	public HashMap<String, Object> createLemburBonus(@Valid @RequestBody LemburBonusDTO lemburBonusDTODetails){
		HashMap<String, Object> hmLemburBonus = new HashMap<String, Object>();
		boolean isAlready = false;
		List<LemburBonusDTO> lemburBonusList = new ArrayList<LemburBonusDTO>();
		
		for(LemburBonusDTO lemburBonusDTO : lemburBonusList) {
			if(lemburBonusDTO.getIdKaryawan() == lemburBonusDTODetails.getIdKaryawan() && lemburBonusDTO.getTanggalLemburBonus().getYear() ==
					lemburBonusDTODetails.getTanggalLemburBonus().getYear() && lemburBonusDTO.getTanggalLemburBonus().getMonth() == lemburBonusDTODetails.getTanggalLemburBonus().getMonth()) {
				hmLemburBonus.put("Message : ", "Lembur Bonus not created, Karyawan and Tanggal Lembur Bonus is already available!");
				isAlready = true;
			}
		}
		if (!isAlready) {
			LemburBonus lemburBonus = modelMapper.map(lemburBonusDTODetails, LemburBonus.class);
			lemburBonusRepository.save(lemburBonus);
			
			hmLemburBonus.put("Message : ", " Succes Create Lembur Bonus!");
			hmLemburBonus.put("Data : ", lemburBonus);
		}
		
		return hmLemburBonus;
	}

	//Update Lembur Bonus
	@PutMapping("/lemburBonus/update{id}")
	public HashMap<String, Object> updateLemburBonus(@PathVariable(value="id") Long id, @Valid @RequestBody LemburBonusDTO lemburBonusDTO){
		HashMap<String, Object> hmLemburBonus = new HashMap<String, Object>();
		LemburBonus lemburBonus = lemburBonusRepository.findById(id).orElseThrow(null);
		
		lemburBonusDTO.setIdLemburBonus(lemburBonus.getLamaLembur());
		
		if(lemburBonusDTO.getIdKaryawan() != null) {
			lemburBonus.setIdKaryawan(lemburBonusDTO.getIdKaryawan());
		}
		if(lemburBonusDTO.getTanggalLemburBonus() != null) {
			lemburBonus.setTanggalLemburBonus(lemburBonusDTO.getTanggalLemburBonus());
		}
		if(lemburBonusDTO.getLamaLembur() != 0) {
			lemburBonus.setLamaLembur(lemburBonusDTO.getLamaLembur());
		}
		if(lemburBonusDTO.getVariableBonus() != 0) {
			lemburBonus.setVariableBonus(lemburBonusDTO.getVariableBonus());
		}
		
		lemburBonusRepository.save(lemburBonus);
		
		hmLemburBonus.put("Message : ", "Update Lembur Bonus Succes!");
		hmLemburBonus.put("Data : ", lemburBonus);
		
		return hmLemburBonus;
	}
	
	//Delete Lembur Bonus
	@DeleteMapping("/lemburBonus/{id}")
	public HashMap<String, Object> deleteLemburBonus(@PathVariable (value="id") Long id){
		HashMap<String, Object> hmLemburBonus = new HashMap<String, Object>();
		LemburBonus lemburBonus = lemburBonusRepository.findById(id).orElseThrow(null);
		
		lemburBonusRepository.delete(lemburBonus);
		
		LemburBonusDTO lemburBonusDTO = convertToDto(lemburBonus);
		
		hmLemburBonus.put("Message : ", "Delete Lembur Bonus Succes!");
		hmLemburBonus.put("Data : ", lemburBonusDTO);
		
		return hmLemburBonus;
	}
	
}
