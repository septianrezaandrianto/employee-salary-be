package com.employee.Employee.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.Employee.dto.AgamaDTO;
import com.employee.Employee.model.Agama;
import com.employee.Employee.repository.AgamaRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AgamaController {
	 
	 ModelMapper modelMapper = new ModelMapper();
	
	 
	 @Autowired
	 AgamaRepository agamaRepository;
	 
	 public AgamaDTO convertToDTO(List<Agama> listAgamas) {
		 AgamaDTO agamaDto = modelMapper.map(listAgamas, AgamaDTO.class);
         return agamaDto;
	 }
	 
	 public AgamaDTO convertToDTO(Agama agama) {
		 AgamaDTO agamaDto = modelMapper.map(agama, AgamaDTO.class);
         return agamaDto;
	 }
	    
    private Agama convertToEntity(AgamaDTO agamaDto) {
    	Agama agama = modelMapper.map(agamaDto, Agama.class);
        return agama;
    }
		 
	 //Get All Agama
	 @GetMapping("/agama/all")
	 public HashMap<String, Object> getAllAgama() {
		HashMap<String, Object> showHashMap = new HashMap<String, Object>();
		List<AgamaDTO> listAgamas = new ArrayList<AgamaDTO>();
		for(Agama a : agamaRepository.findAll()) {
			AgamaDTO agamaDto = convertToDTO(a);
			listAgamas.add(agamaDto);
		}
		
		String message;
        if(listAgamas.isEmpty()) {
    		message = "Read All Failed!";
    	} else {
    		message = "Read All Success!";
    	}
    	showHashMap.put("Message", message);
    	showHashMap.put("Total", listAgamas.size());
    	showHashMap.put("Data", listAgamas);
		
		return showHashMap;
	 }
	 
	 // Read Agama By ID
	 @GetMapping("/agama/{id}")
	 public HashMap<String, Object> getById(@PathVariable(value = "id") Integer id){
		HashMap<String, Object> showHashMap = new HashMap<String, Object>();
		Agama agama = agamaRepository.findById(id)
				.orElse(null);
		AgamaDTO agamaDto = convertToDTO(agama);
		showHashMap.put("Messages", "Read Data Success");
		showHashMap.put("Data", agamaDto);
		return showHashMap;
	}
	 
	// Create a new Agama
	@PostMapping("/agama/add")
	public HashMap<String, Object> createAgama(@Valid @RequestBody ArrayList<AgamaDTO> agamaDto) {
    	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
    	@Valid ArrayList<AgamaDTO> listAgamas = agamaDto;
    	String message;
    	
    	for(AgamaDTO a : listAgamas) {
    		Agama agama = convertToEntity(a);
    		agamaRepository.save(agama);
    	}
    
    	if(listAgamas == null) {
    		message = "Create Failed!";
    	} else {
    		message = "Create Success!";
    	}
    	
    	showHashMap.put("Message", message);
    	showHashMap.put("Total Insert", listAgamas.size());
    	showHashMap.put("Data", listAgamas);
    	
    	return showHashMap;
    }
	
	// Update a Agama
    @PutMapping("/agama/update/{id}")
    public HashMap<String, Object> updateAgama(@PathVariable(value = "id") Integer id,
            @Valid @RequestBody AgamaDTO agamaDetails) {
    	
    	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
    	String message;
    	
    	List<Agama> listAgamas = agamaRepository.findAll();
    	
    	for(Agama a : listAgamas) {
    		if(a.getIdAgama() == id) {
    			if(agamaDetails.getNamaAgama() == null) {
    				agamaDetails.setNamaAgama(listAgamas.get(id).getNamaAgama());
    	    	}
    		}
    	}	
    	
    	Agama agama = agamaRepository.findById(id)
    			 .orElse(null);

    	agama.setNamaAgama(convertToEntity(agamaDetails).getNamaAgama());
    	
    	Agama updateAgama = agamaRepository.save(agama);
    	
    	List<Agama> resultList = new ArrayList<Agama>();
    	resultList.add(updateAgama);
    	
    	if(resultList.isEmpty()) {
    		message = "Update Failed!";
    	} else {
    		message = "Update Success!";
    	}
    	
    	showHashMap.put("Message", message);
    	showHashMap.put("Total Update", resultList.size());
    	showHashMap.put("Data", resultList);
    	
    	return showHashMap;
    }
    
    // Delete a Agama
    @DeleteMapping("/agama/delete/{id}")
    public HashMap<String, Object> delete(@PathVariable(value = "id") Integer id) {
    	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
    	Agama agama = agamaRepository.findById(id)
    			.orElse(null);

    	agamaRepository.delete(agama);

        showHashMap.put("Messages", "Delete Data Success!");
        showHashMap.put("Delete data :", agama);
    	return showHashMap;
    }
}
	 
