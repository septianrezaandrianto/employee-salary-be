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

import com.employee.Employee.dto.PosisiDTO;
import com.employee.Employee.model.Posisi;
import com.employee.Employee.repository.PosisiRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PosisiController {
	 
	 ModelMapper modelMapper = new ModelMapper();
	 
	 @Autowired
	 PosisiRepository posisiRepository;
	 
	 public PosisiDTO convertToDTO(Posisi posisi) {
		 PosisiDTO posisiDto = modelMapper.map(posisi, PosisiDTO.class);
        return posisiDto;
	 }
	    
   private Posisi convertToEntity(PosisiDTO posisiDto) {
   	Posisi posisi = modelMapper.map(posisiDto, Posisi.class);
       return posisi;
   }
		 
	 //Get All User
	 @GetMapping("/Posisi/all")
	 public HashMap<String, Object> getAllUser() {
		HashMap<String, Object> showHashMap = new HashMap<String, Object>();
		List<PosisiDTO> listPosisi = new ArrayList<PosisiDTO>();
		for(Posisi tempPosisi : posisiRepository.findAll()) {
			PosisiDTO posisiDTO = convertToDTO(tempPosisi);
			listPosisi.add(posisiDTO);
		}
		
		String message;
       if(listPosisi.isEmpty()) {
   		message = "Read All Failed!";
   	} else {
   		message = "Read All Success!";
   	}
   	showHashMap.put("Message", message);
   	showHashMap.put("Total", listPosisi.size());
   	showHashMap.put("Data", listPosisi);
		
		return showHashMap;
	 }
	 
	 // Read User By ID
	 @GetMapping("/Posisi/{id}")
	 public HashMap<String, Object> getById(@PathVariable(value = "id") int id){
		HashMap<String, Object> showHashMap = new HashMap<String, Object>();
		Posisi posisi = posisiRepository.findById(id).orElse(null);
		PosisiDTO posisiDTO = convertToDTO(posisi);
		showHashMap.put("Messages", "Read Data Success");
		showHashMap.put("Data", posisiDTO);
		return showHashMap;
	}
	 
	// Create a new User
	@PostMapping("/Posisi/add")
	public HashMap<String, Object> createUser(@Valid @RequestBody PosisiDTO posisiDTO) {
	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
	 	
   	if(checkAlreadyPosisi(posisiDTO)) {
   		showHashMap.put("Message", "Create Succes");
   	   	showHashMap.put("Data", convertToDTO(posisiRepository.save(convertToEntity(posisiDTO))));
   	} else {
   		showHashMap.put("Message : ", "Failed, Nama Posisi is already exist");
   	}
   			
   	return showHashMap;
   }
	
	// Update a User
   @PutMapping("/Posisi/update/{id}")
   public HashMap<String, Object> update(@PathVariable(value = "id") int id, @Valid @RequestBody PosisiDTO posisiDto) {
	HashMap<String, Object> process = new HashMap<String, Object>();
	Posisi tempPosisi = posisiRepository.findById(id).orElse(null);      
	
	posisiDto.setIdPosisi(tempPosisi.getIdPosisi());
    if (posisiDto.getNamaPosisi() == null) {
    	posisiDto.setNamaPosisi(tempPosisi.getNamaPosisi());
    }
    
    tempPosisi = convertToEntity(posisiDto);
    
    posisiRepository.save(tempPosisi);
    process.put("Message", "Success Updated Data");
    process.put("Data", tempPosisi);
    return process;
}
   
   // Delete a User
   @DeleteMapping("/Posisi/delete/{id}")
   public HashMap<String, Object> delete(@PathVariable(value = "id") int id) {
   	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
   	Posisi posisi = posisiRepository.findById(id).orElse(null);
   	PosisiDTO posisiDTO = convertToDTO(posisi);
   	posisiRepository.delete(posisi);

    showHashMap.put("Messages", "Delete Data Success!");
    showHashMap.put("Delete data :", posisiDTO);
   	return showHashMap;
   }
      
   //Validation for Nama Posisi in Table Posisi
   public boolean checkAlreadyPosisi(PosisiDTO posisiDTO) {
	   boolean isAlready=true;
	   for(Posisi posisi : posisiRepository.findAll()) {
		   if(posisiDTO.getNamaPosisi().equalsIgnoreCase(posisi.getNamaPosisi()) ) {
			   isAlready = false;
		   }		   
	   }
	   
	return isAlready;
   }
   
}
	 
