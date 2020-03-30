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

import com.employee.Employee.dto.TingkatanDTO;
import com.employee.Employee.model.Tingkatan;
import com.employee.Employee.repository.TingkatanRepository;

@RestController
@RequestMapping("/api")
public class TingkatanController {
	
	@Autowired
	private TingkatanRepository tingkatanRepository;
	
	ModelMapper modelMapper = new ModelMapper();
	
//	Convert Entity To DTO
	private TingkatanDTO convertEntityToDTO (Tingkatan tingkatan) {
		TingkatanDTO tingkatanDto = modelMapper.map(tingkatan, TingkatanDTO.class);
	return tingkatanDto;
	}
	
//	Convert DTO To Entity
	private Tingkatan convertDTOToEntity (TingkatanDTO tingkatanDTO) {
		Tingkatan tingkatan = modelMapper.map(tingkatanDTO, Tingkatan.class);
	return tingkatan;
	}
	
//	Melihat seluruh data tingkatan
	@GetMapping("/tingkatan/all")
	public HashMap<String, Object> getAllTingkatan() {
		
		HashMap<String, Object> mapTingkatan = new HashMap<String, Object>();
		ArrayList<TingkatanDTO> listTingkatanDto = new ArrayList<TingkatanDTO>();
		
		for (Tingkatan tingkatan : tingkatanRepository.findAll()) {
			TingkatanDTO tingkatanDto = convertEntityToDTO(tingkatan);
			listTingkatanDto.add(tingkatanDto);
		}
		
		mapTingkatan.put("Message" , "Show All Data");
		mapTingkatan.put("Total", listTingkatanDto.size());
		mapTingkatan.put("Data", listTingkatanDto);
		
	return mapTingkatan;
	}
	
//	Melihat tingkatan By Id
	@GetMapping("/tingkatan/{id}")
	public HashMap<String, Object> getTingkatanById (@PathVariable(value="id") Integer idTingkatan) {

		HashMap<String, Object> mapTingkatan = new HashMap<String, Object>();
		Tingkatan tingkatan  = tingkatanRepository.findById(idTingkatan).orElse(null);
		
		TingkatanDTO tingkatanDTO = convertEntityToDTO(tingkatan);
		
		mapTingkatan.put("Message", "Data By Id");
		mapTingkatan.put("Data" , tingkatanDTO);
		
	return mapTingkatan;
	}
	
//	Menghapus Tingkatan
	@DeleteMapping("/tingkatan/delete/{id}")
	public HashMap<String, Object> deleteTingkatan (@PathVariable(value="id") Integer idTingkatan) {
		
		HashMap<String, Object> mapTingkatan = new HashMap<String, Object>();
		
		Tingkatan tingkatan =  tingkatanRepository.findById(idTingkatan).orElse(null);
		
		tingkatanRepository.delete(tingkatan);
		
		mapTingkatan.put("Message" , "Delete Success");
		mapTingkatan.put("Data" , tingkatan);
		
	return mapTingkatan;
	}
	
//	Menambahkan tingkatan
	@PostMapping("/tingkatan/add")
	public HashMap<String, Object> addTingkatan (@Valid @RequestBody TingkatanDTO tingkatanDTO) {
		
		HashMap<String, Object> mapTingkatan = new HashMap<String, Object>();
		
		Tingkatan tingkatan = convertDTOToEntity(tingkatanDTO);
		
		mapTingkatan.put("Message" , "Add Success");
		mapTingkatan.put("Data", tingkatanRepository.save(tingkatan));
		
	return mapTingkatan;
	}
	
//	Update tingkatan
	@PutMapping("/tingkatan/update/{id}")
	public HashMap<String, Object> updateTingkatan(@PathVariable(value="id") Integer idTingkatan, @Valid @RequestBody TingkatanDTO tingkatanDTO) {
		
		HashMap<String, Object> mapTingkatan = new HashMap<String, Object>();
		
		Tingkatan tingkatan = tingkatanRepository.findById(idTingkatan).orElse(null);
		
		tingkatanDTO.setIdTingkatan(tingkatan.getIdTingkatan());
		
		tingkatan.setNamaTingkatan(convertDTOToEntity(tingkatanDTO).getNamaTingkatan());
		
		mapTingkatan.put("Message", "Update Success");
		mapTingkatan.put("Data" , tingkatanRepository.save(tingkatan));
		
	return mapTingkatan;
	}
	
}
