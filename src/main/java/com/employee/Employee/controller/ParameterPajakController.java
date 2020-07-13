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

import com.employee.Employee.dto.ParameterPajakDTO;
import com.employee.Employee.model.ParameterPajak;
import com.employee.Employee.repository.ParameterPajakRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ParameterPajakController {
	 
	 ModelMapper modelMapper = new ModelMapper();
	 
	 @Autowired
	 ParameterPajakRepository parameterPajakRepository;
	 
	 public ParameterPajakDTO convertToDTO(ParameterPajak parameterPajak) {
		 ParameterPajakDTO parameterPajakDto = modelMapper.map(parameterPajak, ParameterPajakDTO.class);
        return parameterPajakDto;
	 }
	    
   private ParameterPajak convertToEntity(ParameterPajakDTO parameterPajakDto) {
	   ParameterPajak parameterPajak = modelMapper.map(parameterPajakDto, ParameterPajak.class);
       return parameterPajak;
   }
		 
	 //Get All User
	 @GetMapping("/ParameterPajak/all")
	 public HashMap<String, Object> getAllUser() {
		HashMap<String, Object> showHashMap = new HashMap<String, Object>();
		List<ParameterPajakDTO> listParameterPajak = new ArrayList<ParameterPajakDTO>();
		for(ParameterPajak tempParameterPajak : parameterPajakRepository.findAll()) {
			ParameterPajakDTO posisiDTO = convertToDTO(tempParameterPajak);
			listParameterPajak.add(posisiDTO);
		}
		
		String message;
       if(listParameterPajak.isEmpty()) {
   		message = "Read All Failed!";
   	} else {
   		message = "Read All Success!";
   	}
   	showHashMap.put("Message", message);
   	showHashMap.put("Total", listParameterPajak.size());
   	showHashMap.put("Data", listParameterPajak);
		
		return showHashMap;
	 }
	 
	 // Read User By ID
	 @GetMapping("/ParameterPajak/{id}")
	 public HashMap<String, Object> getById(@PathVariable(value = "id") Integer id){
		HashMap<String, Object> showHashMap = new HashMap<String, Object>();
		ParameterPajak parameterPajak = parameterPajakRepository.findById(id).orElse(null);
		ParameterPajakDTO parameterPajakDTO = convertToDTO(parameterPajak);
		showHashMap.put("Messages", "Read Data Success");
		showHashMap.put("Data", parameterPajakDTO);
		return showHashMap;
	}
	 
	// Create a new User
	@PostMapping("/ParameterPajak/add")
	public HashMap<String, Object> createUser(@Valid @RequestBody ArrayList<ParameterPajakDTO> parameterPajakDTO) {
	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
	@Valid ArrayList<ParameterPajakDTO> listParameterPajak = parameterPajakDTO;
	String message;
   	
   	for(ParameterPajakDTO tempParameterPajak : listParameterPajak) {
   		ParameterPajak parameterPajak = convertToEntity(tempParameterPajak);
   		parameterPajakRepository.save(parameterPajak);
   	}
   
   	if(listParameterPajak == null) {
   		message = "Create Failed!";
   	} else {
   		message = "Create Success!";
   	}
   	
   	showHashMap.put("Message", message);
   	showHashMap.put("Total Insert", listParameterPajak.size());
   	showHashMap.put("Data", listParameterPajak);
   	
   	return showHashMap;
   }
	
	// Update a User
   @PutMapping("/ParameterPajak/update/{id}")
   public HashMap<String, Object> update(@PathVariable(value = "id") Integer id, @Valid @RequestBody ParameterPajakDTO parameterPajakDto) {
	HashMap<String, Object> process = new HashMap<String, Object>();
	ParameterPajak parameterPajak = parameterPajakRepository.findById(id).orElse(null);      
	
	parameterPajakDto.setIdParamPajak(parameterPajak.getIdParamPajak());
    if (parameterPajakDto.getTbParameterPajak() == null) {
    	parameterPajakDto.setTbParameterPajak(parameterPajak.getTbParameterPajak());
    }
    if (parameterPajakDto.getBatasanPphK1() == null) {
    	parameterPajakDto.setBatasanPphK1(parameterPajak.getBatasanPphK1());
    }
    if (parameterPajakDto.getBatasanPphK2() == null) {
    	parameterPajakDto.setBatasanPphK2(parameterPajak.getBatasanPphK2());
    }
    if (parameterPajakDto.getBatasanPphK3() == null) {
    	parameterPajakDto.setBatasanPphK3(parameterPajak.getBatasanPphK3());
    }
    if (parameterPajakDto.getBatasanPphK4() == null) {
    	parameterPajakDto.setBatasanPphK4(parameterPajak.getBatasanPphK4());
    }
    if (parameterPajakDto.getPresentasePphK1() == null) {
    	parameterPajakDto.setPresentasePphK1(parameterPajak.getPresentasePphK1());
    }
    if (parameterPajakDto.getPresentasePphK2() == null) {
    	parameterPajakDto.setPresentasePphK2(parameterPajak.getPresentasePphK2());
    }
    if (parameterPajakDto.getPresentasePphK3() == null) {
    	parameterPajakDto.setPresentasePphK3(parameterPajak.getPresentasePphK3());
    }
    if (parameterPajakDto.getPresentasePphK4() == null) {
    	parameterPajakDto.setPresentasePphK4(parameterPajak.getPresentasePphK4());
    }
    if (parameterPajakDto.getPtkpUtama() == null) {
    	parameterPajakDto.setPtkpUtama(parameterPajak.getPtkpUtama());
    }
    if (parameterPajakDto.getPtkpTambahan() == null) {
    	parameterPajakDto.setPtkpTambahan(parameterPajak.getPtkpTambahan());
    }
    if (parameterPajakDto.getMaxPtkpAnak() == null) {
    	parameterPajakDto.setMaxPtkpAnak(parameterPajak.getMaxPtkpAnak());
    }
    if (parameterPajakDto.getBiayaJabatan() == null) {
    	parameterPajakDto.setBiayaJabatan(parameterPajak.getBiayaJabatan());
    }
    if (parameterPajakDto.getIuranPensiun() == null) {
    	parameterPajakDto.setIuranPensiun(parameterPajak.getIuranPensiun());
    }

    parameterPajak = convertToEntity(parameterPajakDto);
    
    parameterPajakRepository.save(parameterPajak);
    process.put("Message", "Success Updated Data");
    process.put("Data", parameterPajak);
    return process;
}
   
   // Delete a User
   @DeleteMapping("/ParameterPajak/delete/{id}")
   public HashMap<String, Object> delete(@PathVariable(value = "id") Integer id) {
   	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
   	ParameterPajak parameterPajak = parameterPajakRepository.findById(id).orElse(null);
   	ParameterPajakDTO parameterPajakDto = convertToDTO(parameterPajak);
   	
   	parameterPajakRepository.delete(parameterPajak);

    showHashMap.put("Messages", "Delete Data Success!");
    showHashMap.put("Delete data :", parameterPajakDto);
   	return showHashMap;
   }
}
	 
