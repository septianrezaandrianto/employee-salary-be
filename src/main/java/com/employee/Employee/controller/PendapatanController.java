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

import com.employee.Employee.dto.AgamaDTO;
import com.employee.Employee.dto.KaryawanDTO;
import com.employee.Employee.dto.PendapatanDTO;
import com.employee.Employee.dto.PenempatanDTO;
import com.employee.Employee.dto.PosisiDTO;
import com.employee.Employee.dto.TingkatanDTO;
import com.employee.Employee.model.Karyawan;
import com.employee.Employee.model.Pendapatan;
import com.employee.Employee.repository.PendapatanRepository;

@RestController
@RequestMapping("/api")
public class PendapatanController {

	@Autowired
	private PendapatanRepository pendapatanRepository;
	
	ModelMapper modelMapper = new ModelMapper();
	
//	Convert Entity To DTO
	private PendapatanDTO convertEntityToDTO (Pendapatan pendapatan) {
		PendapatanDTO pendapatanDto = modelMapper.map(pendapatan, PendapatanDTO.class);
		
		if (pendapatan.getKaryawan() != null) {
			KaryawanDTO karyawanDto = modelMapper.map(pendapatan.getKaryawan(), KaryawanDTO.class);
			pendapatanDto.setKaryawanDto(karyawanDto);
		}
		
		if(pendapatan.getKaryawan().getPosisi() != null) {
			PosisiDTO posisiDto = modelMapper.map(pendapatan.getKaryawan().getPosisi(), PosisiDTO.class);
			pendapatanDto.getKaryawanDto().setPosisiDto(posisiDto);
		}
		
		if(pendapatan.getKaryawan().getPenempatan() != null) {
			PenempatanDTO penempatanDto = modelMapper.map(pendapatan.getKaryawan().getPenempatan(), PenempatanDTO.class);
			pendapatanDto.getKaryawanDto().setPenempatanDto(penempatanDto);
		}
		
		if (pendapatan.getKaryawan().getTingkatan() != null) {
			TingkatanDTO tingkatanDto = modelMapper.map(pendapatan.getKaryawan().getTingkatan(), TingkatanDTO.class);
			pendapatanDto.getKaryawanDto().setTingkatanDto(tingkatanDto);
		}
		
		if (pendapatan.getKaryawan().getAgama() != null) {
			AgamaDTO agamaDto = modelMapper.map(pendapatan.getKaryawan().getAgama(), AgamaDTO.class);
			pendapatanDto.getKaryawanDto().setAgamaDto(agamaDto);
		}
	return pendapatanDto;
	}
	
//	Convert DTO To Entity
	private Pendapatan convertDTOToEntity (PendapatanDTO pendapatanDTO) {
		Pendapatan pendapatan = modelMapper.map(pendapatanDTO, Pendapatan.class);
	return pendapatan;
	}
	
	
//	Melihat seluruh Data Pendapatan
	@GetMapping("/pendapatan/all")
	public HashMap<String, Object> getAllPendapatan () {
		HashMap<String, Object> mapPendapatan = new HashMap<String, Object>();
		ArrayList<PendapatanDTO> listPendapatanDto = new ArrayList<PendapatanDTO>();
		
		for (Pendapatan pendapatan : pendapatanRepository.findAll()) {
			PendapatanDTO pendapatanDto = convertEntityToDTO(pendapatan);
			listPendapatanDto.add(pendapatanDto);
		}
		
		mapPendapatan.put("Message" , "Show All Data");
		mapPendapatan.put("Total" , listPendapatanDto.size());
		mapPendapatan.put("Data", listPendapatanDto);
		
	return mapPendapatan;
	}
	
//	Melihat pendapatan by Id
	@GetMapping("/pendapatan/{id}")
	public HashMap<String, Object> getPendapatanById (@PathVariable(value = "id") Integer idPendapatan) {
		
		HashMap<String, Object> mapPendapatan = new HashMap<String, Object>();
		
		Pendapatan pendapatan = pendapatanRepository.findById(idPendapatan).orElse(null);
		
		PendapatanDTO pendapatanDto = convertEntityToDTO(pendapatan);
		
		mapPendapatan.put("Message" , "Data By Id");
		mapPendapatan.put("Data" , pendapatanDto);
		
	return mapPendapatan;
	}
	
//	Menghapus pendapatan
	@DeleteMapping("/pendapatan/delete/{id}")
	public HashMap<String, Object> deletePendapatan (@PathVariable(value = "id") Integer idPendapatan) {
		HashMap<String, Object> mapPendapatan = new HashMap<String, Object>();
		
		Pendapatan pendapatan = pendapatanRepository.findById(idPendapatan).orElse(null);
		
		pendapatanRepository.delete(pendapatan);
		
		mapPendapatan.put("Message", "Delete Success");
		mapPendapatan.put("Data" , pendapatan);
		
	return mapPendapatan;
	}
	
//	Menambahkan pendapatan
	@PostMapping("/pendapatan/add")
	public HashMap<String, Object> addPendapatan (@Valid @RequestBody PendapatanDTO pendapatanDTO) {
		
		HashMap<String, Object> mapPendapatan = new HashMap<String, Object>();
		
		Pendapatan pendapatan = convertDTOToEntity(pendapatanDTO);
		
		mapPendapatan.put("Message" , "Add Success");
		mapPendapatan.put("Data" , pendapatanRepository.save(pendapatan));
		
	return mapPendapatan;
	}
	
//	Update pendapatan
	@PutMapping("/pendapatan/update/{id}")
	public HashMap<String, Object> updatePendapatan(@PathVariable(value = "id") Integer idPendapatan, @Valid @RequestBody PendapatanDTO pendapatanDTO) {
		
		HashMap<String, Object> mapPendapatan = new HashMap<String, Object>();
		
		Pendapatan pendapatan = pendapatanRepository.findById(idPendapatan).orElse(null);
		
		pendapatanDTO.setIdPendapatan(pendapatan.getIdPendapatan());
		
		if (pendapatanDTO.getKaryawanDto() != null) {
			pendapatan.setKaryawan(convertDTOToEntity(pendapatanDTO).getKaryawan());
		}
		
		if (pendapatanDTO.getTanggalGaji() != null) {
			pendapatan.setTanggalGaji(convertDTOToEntity(pendapatanDTO).getTanggalGaji());
		}
		
		if (pendapatanDTO.getGajiPokok() != null) {
			pendapatan.setGajiPokok(convertDTOToEntity(pendapatanDTO).getGajiPokok());
		}
		
		if (pendapatanDTO.getTunjanganKeluarga() != null) {
			pendapatan.setTunjanganKeluarga(convertDTOToEntity(pendapatanDTO).getTunjanganKeluarga());
		}
		
		if (pendapatanDTO.getTunjanganPegawai() != null) {
			pendapatan.setTunjanganPegawai(convertDTOToEntity(pendapatanDTO).getTunjanganPegawai());
		}
		
		if (pendapatanDTO.getTunjanganTransport() != null) {
			pendapatan.setTunjanganTransport(convertDTOToEntity(pendapatanDTO).getTunjanganTransport());
		}
		
		if (pendapatanDTO.getGajiKotor() != null) {
			pendapatan.setGajiKotor(convertDTOToEntity(pendapatanDTO).getGajiKotor());
		}
		
		if (pendapatanDTO.getPphPerbulan() != null) {
			pendapatan.setPphPerbulan(convertDTOToEntity(pendapatanDTO).getPphPerbulan());
		}
		
		if (pendapatanDTO.getBpjs() != null) {
			pendapatan.setBpjs(convertDTOToEntity(pendapatanDTO).getBpjs());
		}
		
		if (pendapatanDTO.getLamaLembur() != null) {
			pendapatan.setLamaLembur(convertDTOToEntity(pendapatanDTO).getLamaLembur());
		}
		
		if (pendapatanDTO.getUangLembur() != null) {
			pendapatan.setUangLembur(convertDTOToEntity(pendapatanDTO).getUangLembur());
		}
		
		if (pendapatanDTO.getVariableBonus() != null) {
			pendapatan.setVariableBonus(convertDTOToEntity(pendapatanDTO).getVariableBonus());
		}
		
		if (pendapatanDTO.getUangBonus() != null) {
			pendapatan.setUangBonus(convertDTOToEntity(pendapatanDTO).getUangBonus());
		}
		 
		if (pendapatanDTO.getTakeHomePay() != null) {
			pendapatan.setTakeHomePay(convertDTOToEntity(pendapatanDTO).getTakeHomePay());
		}
		
		mapPendapatan.put("Message" , "Update Success");
		mapPendapatan.put("Data" , pendapatanRepository.save(pendapatan));
		
	return mapPendapatan;
	}
}
