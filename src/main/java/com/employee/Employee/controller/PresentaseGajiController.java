package com.employee.Employee.controller;

import java.util.ArrayList;
import java.util.HashMap;

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

import com.employee.Employee.dto.PresentaseGajiDTO;
import com.employee.Employee.model.PresentaseGaji;
import com.employee.Employee.repository.PresentaseGajiRepository;

@RestController
@RequestMapping("/api")
public class PresentaseGajiController {
	
	@Autowired
	private PresentaseGajiRepository presentaseGajiRepository;
	
	ModelMapper modelMapper = new ModelMapper();
	
//	Convert Entity To DTO
	private PresentaseGajiDTO convertEntityToDTO(PresentaseGaji presentaseGaji) {
		PresentaseGajiDTO presentaseGajiDTO = modelMapper.map(presentaseGaji, PresentaseGajiDTO.class);
	return presentaseGajiDTO;
	}
	
//	Convert DTO To Entity
	private PresentaseGaji convertDTOToEntity (PresentaseGajiDTO presentaseGajiDTO) {
		PresentaseGaji presentaseGaji = modelMapper.map(presentaseGajiDTO, PresentaseGaji.class);
	return presentaseGaji;
	}
	
//	Melihat Seluruh Data Presentase Gaji
	@GetMapping ("/presentasegaji/all")
	public HashMap<String, Object> getAllPresentaseGaji () {
		
		HashMap<String, Object> mapPresentaseGaji = new HashMap<String, Object>();
		ArrayList<PresentaseGajiDTO> listPresentaseGajiDto = new ArrayList<PresentaseGajiDTO>();
		
		for (PresentaseGaji pg : presentaseGajiRepository.findAll()) {
			PresentaseGajiDTO presentaseGajiDto = convertEntityToDTO(pg);
			listPresentaseGajiDto.add(presentaseGajiDto);
		}
		
		mapPresentaseGaji.put("Message" ,"Show All Data");
		mapPresentaseGaji.put("Total" , listPresentaseGajiDto.size());
		mapPresentaseGaji.put("Data" , listPresentaseGajiDto);
	
	return mapPresentaseGaji;	
	}
	
//	Melihat Presentase Gaji berdasarkan Id
	@GetMapping("/presentasegaji/{id}")
	public HashMap<String, Object> getPresentaseGajiById (@PathVariable(value="id") Integer idPresentaseGaji) {

		HashMap<String, Object> mapPresentaseGaji = new HashMap<String, Object>();
		PresentaseGaji presentaseGaji  = presentaseGajiRepository.findById(idPresentaseGaji).orElse(null);
		
		PresentaseGajiDTO presentaseGajiDTO = convertEntityToDTO(presentaseGaji);
		
		mapPresentaseGaji.put("Message", "Data By Id");
		mapPresentaseGaji.put("Data" , presentaseGajiDTO);
		
	return mapPresentaseGaji;
	}
	
//	Menghapus Presentase Gaji
	@DeleteMapping("/presentasegaji/delete/{id}")
	public HashMap<String, Object> deletePresentaseGaji (@PathVariable(value="id") Integer idPresentaseGaji) {
		
		HashMap<String, Object> mapPresentaseGaji = new HashMap<String, Object>();
		
		PresentaseGaji presentaseGaji =  presentaseGajiRepository.findById(idPresentaseGaji).orElse(null);
		
		presentaseGajiRepository.delete(presentaseGaji);
		
		mapPresentaseGaji.put("Message" , "Delete Success");
		mapPresentaseGaji.put("Data" , presentaseGaji);
		
	return mapPresentaseGaji;
	}
	
//	Menambahkan presentase Gaji
	@PostMapping("/presentasegaji/add")
	public HashMap<String, Object> addPresentaseGaji(@Valid @RequestBody PresentaseGajiDTO presentaseGajiDTO) {
		
		HashMap<String, Object> mapPresentaseGaji = new HashMap<String, Object>();
		
		PresentaseGaji presentaseGaji = convertDTOToEntity(presentaseGajiDTO);
		
		mapPresentaseGaji.put("Message" , "Add Success");
		mapPresentaseGaji.put("Data", presentaseGajiRepository.save(presentaseGaji));
		
	return mapPresentaseGaji;
	}
	
//	Update presentase Gaji
	@PutMapping("/presentasegaji/update/{id}")
	public HashMap<String, Object> updatePresentaseGaji(@PathVariable(value="id") Integer idPresentaseGaji, @Valid @RequestBody PresentaseGajiDTO presentaseGajiDTO) {
		
		HashMap<String, Object> mapPresentaseGaji = new HashMap<String, Object>();
		
		PresentaseGaji presentaseGaji = presentaseGajiRepository.findById(idPresentaseGaji).orElse(null);
		
		presentaseGajiDTO.setIdPresentaseGaji(presentaseGaji.getIdPresentaseGaji());
		
		if (presentaseGajiDTO.getPosisi() != null) {
			presentaseGaji.setPosisi(convertDTOToEntity(presentaseGajiDTO).getPosisi());
		}
		
		if (presentaseGajiDTO.getIdTingkatan() != null) {
			presentaseGaji.setIdTingkatan(convertDTOToEntity(presentaseGajiDTO).getIdTingkatan());
		}
		
		if (presentaseGajiDTO.getBesaranGaji() != null) {
			presentaseGaji.setBesaranGaji(convertDTOToEntity(presentaseGajiDTO).getBesaranGaji());
		}
		
		if(presentaseGajiDTO.getMasaKerja() != null) {
			presentaseGaji.setBesaranGaji(convertDTOToEntity(presentaseGajiDTO).getBesaranGaji());
		}
		
		mapPresentaseGaji.put("Message", "Update Success");
		mapPresentaseGaji.put("Data" , presentaseGajiRepository.save(presentaseGaji));
		
	return mapPresentaseGaji;
	}
	
	
}
