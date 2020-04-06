package com.employee.Employee.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.Employee.dto.LemburBonusDTO;
import com.employee.Employee.model.LemburBonus;
import com.employee.Employee.repository.KaryawanRepository;
import com.employee.Employee.repository.LemburBonusRepository;

@RestController
@RequestMapping("/api")
public class LemburBonusFazriController {
	 
	 ModelMapper modelMapper = new ModelMapper();
	
	 
	 @Autowired
	 LemburBonusRepository lemburBonusRepository;
	 
	 @Autowired
	 KaryawanRepository karyawanRepository;
	 
	 public LemburBonusDTO convertToDTO(List<LemburBonus> listLemburBonuss) {
		 LemburBonusDTO lemburBonusDto = modelMapper.map(listLemburBonuss, LemburBonusDTO.class);
         return lemburBonusDto;
	 }
	 
	 public LemburBonusDTO convertToDTO(LemburBonus lemburBonus) {
		 LemburBonusDTO LemburBonusDto = modelMapper.map(lemburBonus, LemburBonusDTO.class);
         return LemburBonusDto;
	 }
	    
    private LemburBonus convertToEntity(LemburBonusDTO lemburBonusDto) {
    	LemburBonus lemburBonus = modelMapper.map(lemburBonusDto, LemburBonus.class);
        return lemburBonus;
    }
		 
	 //Get All LemburBonus
	 @GetMapping("/LemburBonus/all")
	 public HashMap<String, Object> getAllLemburBonus() {
		HashMap<String, Object> showHashMap = new HashMap<String, Object>();
		List<LemburBonusDTO> listLemburBonuss = new ArrayList<LemburBonusDTO>();
		for(LemburBonus a : lemburBonusRepository.findAll()) {
			LemburBonusDTO lemburBonusDto = convertToDTO(a);
			listLemburBonuss.add(lemburBonusDto);
		}
		
		String message;
        if(listLemburBonuss.isEmpty()) {
    		message = "Read All Failed!";
    	} else {
    		message = "Read All Success!";
    	}
    	showHashMap.put("Message", message);
    	showHashMap.put("Total", listLemburBonuss.size());
    	showHashMap.put("Data", listLemburBonuss);
		
		return showHashMap;
	 }
	 
	 // Read LemburBonus By ID
	 @GetMapping("/LemburBonus/{id}")
	 public HashMap<String, Object> getById(@PathVariable(value = "id") Long id){
		HashMap<String, Object> showHashMap = new HashMap<String, Object>();
		LemburBonus lemburBonus = lemburBonusRepository.findById(id)
				.orElse(null);
		LemburBonusDTO lemburBonusDto = convertToDTO(lemburBonus);
		showHashMap.put("Messages", "Read Data Success");
		showHashMap.put("Data", lemburBonusDto);
		return showHashMap;
	}
	 
	// Create a new LemburBonus
	@SuppressWarnings("deprecation")
	@PostMapping("/LemburBonus/add")
	public HashMap<String, Object> createLemburBonus(@RequestParam(name = "date") String date, 
			@Valid @RequestBody LemburBonusDTO lemburBonusDto) throws ParseException {
    	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
    	String message = "";
    	boolean isInsert = false;
    	boolean isUpdate = false;
    	LemburBonus lemburBonus = convertToEntity(lemburBonusDto);
    	List<LemburBonus> listLemburBonuss = new ArrayList<LemburBonus>();
    	Date tanggalGaji = new SimpleDateFormat("yyyy-MM-dd").parse(date);
    	
    	if(lemburBonusRepository.findAll().isEmpty()) {
    		isInsert = true;
		}else {
			for(LemburBonus lb: lemburBonusRepository.findAll()) {
				if(lb.getIdKaryawan() == lemburBonus.getIdKaryawan() && (lb.getTanggalLemburBonus().getMonth() == tanggalGaji.getMonth() && lb.getTanggalLemburBonus().getYear() == tanggalGaji.getYear())) {
					lb.setTanggalLemburBonus(lemburBonus.getTanggalLemburBonus());
					lb.setLamaLembur(lemburBonus.getLamaLembur());
					lb.setVariableBonus(lemburBonus.getVariableBonus());
					lemburBonusRepository.save(lemburBonus);
					isUpdate = true;
				} else {
					isInsert = true;
				}
			}
		}
    	
    	if(isInsert) {
    		lemburBonusRepository.save(lemburBonus);
			listLemburBonuss.add(lemburBonus);	
			message = "Create Success!";
    	} 
    	if(isUpdate) {
    		message = "Update Success!";
    	}
    
    	showHashMap.put("Message", message);
    	showHashMap.put("Total Insert", listLemburBonuss.size());
    	showHashMap.put("Data", listLemburBonuss);
    	
    	return showHashMap;
    }
	
	// Update a LemburBonus
    @PutMapping("/LemburBonus/update/{id}")
    public HashMap<String, Object> updateLemburBonus(@PathVariable(value = "id") Long id,
            @Valid @RequestBody LemburBonusDTO lemburBonusDetails) {
    	
    	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
    	String message;
    	
    	LemburBonus lemburBonus = lemburBonusRepository.findById(id)
    			 .orElse(null);
    	
    	if (lemburBonusDetails.getTanggalLemburBonus() == null) {
    		lemburBonusDetails.setTanggalLemburBonus(lemburBonus.getTanggalLemburBonus());
	    }
	    if (lemburBonusDetails.getLamaLembur() == 0) {
	    	lemburBonusDetails.setLamaLembur(lemburBonus.getLamaLembur());
	    }
	    if (lemburBonusDetails.getVariableBonus() == 0) {
	    	lemburBonusDetails.setVariableBonus(lemburBonus.getVariableBonus());
	    }
	    
    	LemburBonus updateLemburBonus = lemburBonusRepository.save(lemburBonus);
    	
    	List<LemburBonus> listLemburBonuss = new ArrayList<LemburBonus>();
    	listLemburBonuss.add(updateLemburBonus);
    	
    	if(listLemburBonuss.isEmpty()) {
    		message = "Update Failed!";
    	} else {
    		message = "Update Success!";
    	}
    	
    	showHashMap.put("Message", message);
    	showHashMap.put("Total Update", listLemburBonuss.size());
    	showHashMap.put("Data", listLemburBonuss);
    	
    	return showHashMap;
    }
    
    // Delete a LemburBonus
    @DeleteMapping("/LemburBonus/delete/{id}")
    public HashMap<String, Object> delete(@PathVariable(value = "id") Long id) {
    	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
    	LemburBonus lemburBonus = lemburBonusRepository.findById(id)
    			.orElse(null);

    	lemburBonusRepository.delete(lemburBonus);

        showHashMap.put("Messages", "Delete Data Success!");
        showHashMap.put("Delete data :", lemburBonus);
    	return showHashMap;
    }
}
	 
