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
import com.employee.Employee.dto.TunjanganPegawaiDTO;
import com.employee.Employee.model.TunjanganPegawai;
import com.employee.Employee.repository.TunjanganPegawaiRepository;

@RestController
@RequestMapping("/api")
public class TunjanganPegawaiController {
	
	ModelMapper modelMapper = new ModelMapper();
		
	@Autowired
	TunjanganPegawaiRepository tunjanganPegawaiRepository;
	
	public TunjanganPegawai convertToEntity(TunjanganPegawaiDTO tunjanganPegawaiDTO) {
		TunjanganPegawai tunjanganPegawai = modelMapper.map(tunjanganPegawaiDTO, TunjanganPegawai.class);
		return tunjanganPegawai;		
	}
	
	public TunjanganPegawaiDTO convertToDTO(TunjanganPegawai tunjanganPegawai) {
		TunjanganPegawaiDTO tunjanganPegawaiDTO = modelMapper.map(tunjanganPegawai, TunjanganPegawaiDTO.class);
		return tunjanganPegawaiDTO;
	}
	
	//Read All Tunjangan Pegawai
	@GetMapping("/tunjanganPegawai/add")
	public HashMap<String, Object>readAllParameter(){
		HashMap<String, Object> hmTunjanganPegawai = new HashMap<String, Object>();
		List<TunjanganPegawaiDTO> tunjanganPegawaiDTOs = new ArrayList<TunjanganPegawaiDTO>();
		for (TunjanganPegawai tunjanganPegawai : tunjanganPegawaiRepository.findAll()) {
			tunjanganPegawaiDTOs.add(convertToDTO(tunjanganPegawai));
		}
			
		hmTunjanganPegawai.put("Message : ", "Read All Tunjangan Pegawai Succes!");
		hmTunjanganPegawai.put("Total : ", tunjanganPegawaiDTOs.size());
		hmTunjanganPegawai.put("Data : ", tunjanganPegawaiDTOs);
			
		return hmTunjanganPegawai;
	}
		
	
	//Read Tunjangan Pegawai By ID
	@GetMapping("/tunjanganPegawai/{id}")
	public HashMap<String, Object> readTunjanganPegawaiById(@PathVariable(name="id") Integer id){
		HashMap<String, Object> hmTunjanganPegawai = new HashMap<String, Object>();
		TunjanganPegawai tunjanganPegawai = tunjanganPegawaiRepository.findById(id).orElseThrow(null);
		TunjanganPegawaiDTO tunjanganPegawaiDTO = convertToDTO(tunjanganPegawai);
		
		hmTunjanganPegawai.put("Message : ", "Read Tunjangan Pegawai By ID succes!");
		hmTunjanganPegawai.put("Data : ", tunjanganPegawaiDTO);
		
		return hmTunjanganPegawai;
	}
	
	
	//Create Tunjangan Pegawai
	@PostMapping("/tunjanganPegawai/add")
	public HashMap<String, Object> createTunjanganPegawai(@Valid @RequestBody TunjanganPegawaiDTO tunjanganPegawaiDTODetails){
		HashMap<String, Object> hmTunjanganPegawai = new HashMap<String, Object>();
		TunjanganPegawai tunjanganPegawai = convertToEntity(tunjanganPegawaiDTODetails);
		tunjanganPegawaiRepository.save(tunjanganPegawai);
		
		hmTunjanganPegawai.put("Message : ", "Create Tunjangan Pegawai Succes!");
		hmTunjanganPegawai.put("Data : ", tunjanganPegawai);
		
		return hmTunjanganPegawai;		
		
	}
	
	//Update Tunjangan Pegawai
	@PutMapping("tunjanganPegawai/update/{id}")
	public HashMap<String, Object> updateTunjanganPegawai(@PathVariable(value="id") Integer id, @Valid @RequestBody TunjanganPegawaiDTO tunjanganPegawaiDTODetails){
		HashMap<String, Object> hmTunjanganPegawai = new HashMap<String, Object>();
		TunjanganPegawai tunjanganPegawai = tunjanganPegawaiRepository.findById(id).orElseThrow(null);
		
		tunjanganPegawaiDTODetails.setIdTunjanganPegawai(tunjanganPegawai.getIdTunjanganPegawai());
		
		if(tunjanganPegawaiDTODetails.getPosisiDTO() != null) {
			tunjanganPegawai.setPosisi(convertToEntity(tunjanganPegawaiDTODetails).getPosisi());
		}
		if(tunjanganPegawaiDTODetails.getTingkatanDTO() != null) {
			tunjanganPegawai.setTingkatan(convertToEntity(tunjanganPegawaiDTODetails).getTingkatan());
		}
		
		if(tunjanganPegawaiDTODetails.getBesaranTujnaganPegawai() != null) {
			tunjanganPegawai.setBesaranTujnaganPegawai(convertToEntity(tunjanganPegawaiDTODetails).getBesaranTujnaganPegawai());
		}
		
		tunjanganPegawaiRepository.save(tunjanganPegawai);
		
		hmTunjanganPegawai.put("Message : ", "Update Tunjangan Pegawai Succes!");
		hmTunjanganPegawai.put("Data : ", tunjanganPegawai);
		
		return hmTunjanganPegawai;
	}
	
	//Delete Tunjangan Pegawai By ID
	@DeleteMapping("tunjanganPegawai/delete/{id}")
	public HashMap<String, Object> deleteTunjanganPegawai(@PathVariable (name="id") Integer id){
		HashMap<String, Object> hmTunjanganPegawai = new HashMap<String, Object>();
		TunjanganPegawai tunjanganPegawai = tunjanganPegawaiRepository.findById(id).orElseThrow(null);
			
		tunjanganPegawaiRepository.delete(tunjanganPegawai);
			
		hmTunjanganPegawai.put("Message : ", "Delete Tunjangan Pegawai Succes!");
		hmTunjanganPegawai.put("Data : ", tunjanganPegawai);
			
		return hmTunjanganPegawai;
	}
	
	
}
