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

import com.employee.Employee.dto.KaryawanDTO;
import com.employee.Employee.model.Karyawan;
import com.employee.Employee.repository.KaryawanRepository;

@RestController
@RequestMapping("/api")
public class KaryawanController {
	 
	 ModelMapper modelMapper = new ModelMapper();
	
	 
	 @Autowired
	 KaryawanRepository karyawanRepository;
	 
	 public KaryawanDTO convertToDTO(List<Karyawan> listKaryawans) {
		 KaryawanDTO karyawanDto = modelMapper.map(listKaryawans, KaryawanDTO.class);
         return karyawanDto;
	 }
	 
	 public KaryawanDTO convertToDTO(Karyawan karyawan) {
	        return modelMapper.map(karyawan, KaryawanDTO.class);
	    }

     public Karyawan convertToEntity(KaryawanDTO karyawanDto) {
        return modelMapper.map(karyawanDto, Karyawan.class);
     }
		 
	 //Get All Karyawan
	 @GetMapping("/karyawan/all")
	 public HashMap<String, Object> getAllKaryawan() {
		HashMap<String, Object> showHashMap = new HashMap<String, Object>();
		List<KaryawanDTO> listKaryawans = new ArrayList<KaryawanDTO>();
		for(Karyawan k : karyawanRepository.findAll()) {
			KaryawanDTO karyawanDto = convertToDTO(k);
			listKaryawans.add(karyawanDto);
		}
		
		String message;
        if(listKaryawans.isEmpty()) {
    		message = "Read All Failed!";
    	} else {
    		message = "Read All Success!";
    	}
    	showHashMap.put("Message", message);
    	showHashMap.put("Total", listKaryawans.size());
    	showHashMap.put("Data", listKaryawans);
		
		return showHashMap;
	 }
	 
	 // Read Karyawan By ID
	 @GetMapping("/karyawan/{id}")
	 public HashMap<String, Object> getById(@PathVariable(value = "id") Integer id){
		HashMap<String, Object> showHashMap = new HashMap<String, Object>();
		Karyawan karyawan = karyawanRepository.findById(id)
				.orElse(null);
		KaryawanDTO karyawanDto = convertToDTO(karyawan);
		showHashMap.put("Messages", "Read Data Success");
		showHashMap.put("Data", karyawanDto);
		return showHashMap;
	}
	 
	// Create a new Karyawan
	@PostMapping("/karyawan/add")
	public HashMap<String, Object> createKaryawan(@Valid @RequestBody ArrayList<KaryawanDTO> karyawanDto) {
    	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
    	@Valid ArrayList<KaryawanDTO> listKaryawans = karyawanDto;
    	String message;
    	
    	for(KaryawanDTO k : listKaryawans) {
    		Karyawan karyawan = convertToEntity(k);
    		karyawanRepository.save(karyawan);
    	}
    
    	if(listKaryawans == null) {
    		message = "Create Failed!";
    	} else {
    		message = "Create Success!";
    	}
    	
    	showHashMap.put("Message", message);
    	showHashMap.put("Total Insert", listKaryawans.size());
    	showHashMap.put("Data", listKaryawans);
    	
    	return showHashMap;
    }
	
	// Update a Karyawan
    @PutMapping("/karyawan/update/{id}")
    public HashMap<String, Object> updateKaryawan(@PathVariable(value = "id") Integer id,
            @Valid @RequestBody KaryawanDTO karyawanDetails) {
    	
    	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
    	String message;
    	
    	Karyawan Karyawan = karyawanRepository.findById(id)
    			 .orElse(null);
    	
    	Karyawan.setAgama(convertToEntity(karyawanDetails).getAgama());
    	Karyawan.setPosisi(convertToEntity(karyawanDetails).getPosisi());
    	Karyawan.setPenempatan(convertToEntity(karyawanDetails).getPenempatan());
    	Karyawan.setTingkatan(convertToEntity(karyawanDetails).getTingkatan());
    	Karyawan.setNama(convertToEntity(karyawanDetails).getNama());
    	Karyawan.setNoKtp(convertToEntity(karyawanDetails).getNoKtp());
    	Karyawan.setAlamat(convertToEntity(karyawanDetails).getAlamat());
    	Karyawan.setTanggalLahir(convertToEntity(karyawanDetails).getTanggalLahir());
    	Karyawan.setMasaKerja(convertToEntity(karyawanDetails).getMasaKerja());
    	Karyawan.setStatusPernikahan(convertToEntity(karyawanDetails).getStatusPernikahan());
    	Karyawan.setKontrakAwal(convertToEntity(karyawanDetails).getKontrakAwal());
    	Karyawan.setKontrakAkhir(convertToEntity(karyawanDetails).getKontrakAkhir());
    	Karyawan.setJenisKelamin(convertToEntity(karyawanDetails).getJenisKelamin());
    	Karyawan.setJumlahAnak(convertToEntity(karyawanDetails).getJumlahAnak());
    	
    	Karyawan updateKaryawan = karyawanRepository.save(Karyawan);
    	
    	List<Karyawan> resultList = new ArrayList<Karyawan>();
    	resultList.add(updateKaryawan);
    	
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
    
    // Delete a Karyawan
    @DeleteMapping("/karyawan/delete/{id}")
    public HashMap<String, Object> delete(@PathVariable(value = "id") Integer id) {
    	HashMap<String, Object> showHashMap = new HashMap<String, Object>();
    	Karyawan karyawan = karyawanRepository.findById(id)
    			.orElse(null);

    	karyawanRepository.delete(karyawan);

        showHashMap.put("Messages", "Delete Data Success!");
        showHashMap.put("Delete data :", karyawan);
    	return showHashMap;
    }
}
	 
