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

import com.employee.Employee.dto.AgamaDTO;
import com.employee.Employee.dto.KaryawanDTO;
import com.employee.Employee.dto.KemampuanDTO;
import com.employee.Employee.dto.ListKemampuanDTO;
import com.employee.Employee.dto.PenempatanDTO;
import com.employee.Employee.dto.PosisiDTO;
import com.employee.Employee.dto.TingkatanDTO;
import com.employee.Employee.model.ListKemampuan;
import com.employee.Employee.repository.ListKemampuanRepository;

@RestController
@RequestMapping("/api")
public class ListKemampuanController {
	 
	 ModelMapper modelMapper = new ModelMapper();
	 
	 @Autowired
	 ListKemampuanRepository listKemampuanRepository;
	 
	 public ListKemampuanDTO convertToDTO(ListKemampuan listKemampuan) {
		 ListKemampuanDTO listKemampuanDto = modelMapper.map(listKemampuan, ListKemampuanDTO.class);
		 if(listKemampuan.getKemampuan() != null) {
			 KemampuanDTO kemampuan = modelMapper.map(listKemampuan.getKemampuan(), KemampuanDTO.class);
			 listKemampuanDto.setKemampuan(kemampuan);
		 }
		 if(listKemampuan.getKaryawan() != null) {
			 KaryawanDTO karyawan = modelMapper.map(listKemampuan.getKaryawan(), KaryawanDTO.class);
			 listKemampuanDto.setKaryawan(karyawan);
			 if(listKemampuan.getKaryawan().getPosisi() != null) {
				 PosisiDTO posisi = modelMapper.map(listKemampuan.getKaryawan().getPosisi(), PosisiDTO.class);
				 listKemampuanDto.getKaryawan().setPosisi(posisi);
			 }
			 if(listKemampuan.getKaryawan().getPenempatan() != null) {
				 PenempatanDTO penempatan = modelMapper.map(listKemampuan.getKaryawan().getPenempatan(), PenempatanDTO.class);
				 listKemampuanDto.getKaryawan().setPenempatan(penempatan);
			 }
			 if(listKemampuan.getKaryawan().getTingkatan() != null) {
				 TingkatanDTO tingkatan = modelMapper.map(listKemampuan.getKaryawan().getTingkatan(), TingkatanDTO.class);
				 listKemampuanDto.getKaryawan().setTingkatan(tingkatan);
			 }
			 if(listKemampuan.getKaryawan().getAgama() != null) {
				 AgamaDTO agama = modelMapper.map(listKemampuan.getKaryawan().getAgama(), AgamaDTO.class);
				 listKemampuanDto.getKaryawan().setAgama(agama);
			 }
		 }
		 
		 return listKemampuanDto;
	 }
	    
   private ListKemampuan convertToEntity(ListKemampuanDTO listKemampuanDto) {
	   ListKemampuan listKemampuan = modelMapper.map(listKemampuanDto, ListKemampuan.class);
       return listKemampuan;
   }
		 
	 //Get All User
	 @GetMapping("/ListKemampuan/all")
	 public HashMap<String, Object> getAllUser() {
		HashMap<String, Object> showHashMap = new HashMap<String, Object>();
		List<ListKemampuanDTO> listListKemampuan = new ArrayList<ListKemampuanDTO>();
		for(ListKemampuan tempListKemampuan : listKemampuanRepository.findAll()) {
			ListKemampuanDTO listKemampuanDTO = convertToDTO(tempListKemampuan);
			listListKemampuan.add(listKemampuanDTO);
		}
		
		String message;
       if(listListKemampuan.isEmpty()) {
   		message = "Read All Failed!";
   	} else {
   		message = "Read All Success!";
   	}
   	showHashMap.put("Message", message);
   	showHashMap.put("Total", listListKemampuan.size());
   	showHashMap.put("Data", listListKemampuan);
		
		return showHashMap;
	 }
	 
	 // Read User By ID
	 @GetMapping("/ListKemampuan/{id}")
	 public HashMap<String, Object> getById(@PathVariable(value = "id") Integer id){
		HashMap<String, Object> showHashMap = new HashMap<String, Object>();
		ListKemampuan listKemampuan = listKemampuanRepository.findById(id).orElse(null);
		ListKemampuanDTO listKemampuanDTO = convertToDTO(listKemampuan);
		showHashMap.put("Messages", "Read Data Success");
		showHashMap.put("Data", listKemampuanDTO);
		return showHashMap;
	}
	 
	// Create a new User
	@PostMapping("/ListKemampuan/add")
	public HashMap<String, Object> createUser(@Valid @RequestBody ArrayList<ListKemampuanDTO> listKemampuanDTO) {
	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
	@Valid ArrayList<ListKemampuanDTO> listKemampuan = listKemampuanDTO;
	String message;
   	
   	for(ListKemampuanDTO tempPosisi : listKemampuan) {
   		ListKemampuan posisi = convertToEntity(tempPosisi);
   		listKemampuanRepository.save(posisi);
   	}
   
   	if(listKemampuan == null) {
   		message = "Create Failed!";
   	} else {
   		message = "Create Success!";
   	}
   	
   	showHashMap.put("Message", message);
   	showHashMap.put("Total Insert", listKemampuan.size());
   	showHashMap.put("Data", listKemampuan);
   	
   	return showHashMap;
   }
	
	// Update a User
   @PutMapping("/ListKemampuan/update/{id}")
   public HashMap<String, Object> update(@PathVariable(value = "id") Integer id, @Valid @RequestBody ListKemampuanDTO listKemampuanDto) {
	HashMap<String, Object> process = new HashMap<String, Object>();
	ListKemampuan tempListKemampuan = listKemampuanRepository.findById(id).orElse(null);      
	ListKemampuanDTO tempDTO = convertToDTO(tempListKemampuan);
	listKemampuanDto.setIdListKemampuan(tempDTO.getIdListKemampuan());
    if (listKemampuanDto.getKemampuan() == null) {  	
    	listKemampuanDto.setKemampuan(tempDTO.getKemampuan());
    }
    if (listKemampuanDto.getKaryawan() == null) {
    	listKemampuanDto.setKaryawan(tempDTO.getKaryawan());
    }
    if (listKemampuanDto.getNilaiKemampuan() == null) {
    	listKemampuanDto.setNilaiKemampuan(tempDTO.getNilaiKemampuan());
    }

    tempListKemampuan = convertToEntity(listKemampuanDto);
    
    listKemampuanRepository.save(tempListKemampuan);
    process.put("Message", "Success Updated Data");
    process.put("Data", tempListKemampuan);
    return process;
}
   
   // Delete a User
   @DeleteMapping("/ListKemampuan/delete/{id}")
   public HashMap<String, Object> delete(@PathVariable(value = "id") Integer id) {
   	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
   	ListKemampuan listKemampuan = listKemampuanRepository.findById(id).orElse(null);
   	ListKemampuanDTO listKemampuanDTO = convertToDTO(listKemampuan);
   	listKemampuanRepository.delete(listKemampuan);

    showHashMap.put("Messages", "Delete Data Success!");
    showHashMap.put("Delete data :", listKemampuanDTO);
   	return showHashMap;
   }
}
	 
