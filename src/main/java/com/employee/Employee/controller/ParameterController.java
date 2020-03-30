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

import com.employee.Employee.dto.ParameterDTO;
import com.employee.Employee.model.Parameter;
import com.employee.Employee.repository.ParameterRepository;

@RestController
@RequestMapping("/api")
public class ParameterController {
	
	ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	ParameterRepository parameterRepository;
	
	public Parameter convertToEntity(ParameterDTO parameterDTO) {
		Parameter parameter = modelMapper.map(parameterDTO, Parameter.class);
		return parameter;		
	}
	
	public ParameterDTO convertToDTO(Parameter parameter) {
		ParameterDTO parameterDTO = modelMapper.map(parameter, ParameterDTO.class);
		return parameterDTO;
	}
	
	//Read All Parameter
	@GetMapping("/parameter/all")
	public HashMap<String, Object>readAllParameter(){
		HashMap<String, Object> hmParameter = new HashMap<String, Object>();
		List<ParameterDTO> parameterDTOs = new ArrayList<ParameterDTO>();
		for (Parameter parameter : parameterRepository.findAll()) {
			parameterDTOs.add(convertToDTO(parameter));
		}
		
		hmParameter.put("Message : ", "Read All Parameter Succes!");
		hmParameter.put("Total : ", parameterDTOs.size());
		hmParameter.put("Data : ", parameterDTOs);
		
		return hmParameter;
	}
	
	//Read Parameter By ID
	@GetMapping("/parameter/{id}")
	public HashMap<String, Object> readParameterById(@PathVariable(name="id") Integer id){
		HashMap<String, Object> hmParameter = new HashMap<String, Object>();
		Parameter parameter = parameterRepository.findById(id).orElseThrow(null);
		ParameterDTO parameterDTO = convertToDTO(parameter);
		
		hmParameter.put("Message : ", "Read Parameter By ID succes!");
		hmParameter.put("Data : ", parameterDTO);
		
		return hmParameter;
	}
	
	//Create Parameter
	@PostMapping("/parameter/add")
	public HashMap<String, Object> createParameter(@Valid @RequestBody ParameterDTO parameterDTODetails){
		HashMap<String, Object> hmParameter = new HashMap<String, Object>();
		Parameter parameter = convertToEntity(parameterDTODetails);
		parameterRepository.save(parameter);
		
		hmParameter.put("Message : ", "Create Parameter Succes!");
		hmParameter.put("Data : ", parameter);
		
		return hmParameter;		
		
	}
	
	//Update Parameter 
	@PutMapping("parameter/update/{id}")
	public HashMap<String, Object> updateParameter(@PathVariable(value="id") Integer id, @Valid @RequestBody ParameterDTO parameterDTODetails){
		HashMap<String, Object> hmParameter = new HashMap<String, Object>();
		Parameter parameter = parameterRepository.findById(id).orElseThrow(null);
			
		parameterDTODetails.setIdParam(parameter.getIdParam());
		
		if(parameterDTODetails.getTbParameter() != null) {
			parameter.setTbParameter(convertToEntity(parameterDTODetails).getTbParameter());
		}
		
		if(parameterDTODetails.getTKeluarga() != null) {
			parameter.setTKeluarga(convertToEntity(parameterDTODetails).getTKeluarga());
		}
		
		if(parameterDTODetails.getTTransport() != null) {
			parameter.setTTransport(convertToEntity(parameterDTODetails).getTTransport());
		}
		
		if(parameterDTODetails.getPBpjs() != null){
			parameter.setPBpjs(convertToEntity(parameterDTODetails).getPBpjs());
		}
		
		if(parameterDTODetails.getLembur() != null) {
			parameter.setLembur(convertToEntity(parameterDTODetails).getLembur());
		}
		
		if(parameterDTODetails.getBonusPg() != null) {
			parameter.setBonusPg(convertToEntity(parameterDTODetails).getBonusPg());
		}
		
		if(parameterDTODetails.getBonusTs() != null) {
			parameter.setBonusTs(convertToEntity(parameterDTODetails).getBonusTs());
		}
		if(parameterDTODetails.getBonusTw() != null) {
			parameter.setBonusTw(convertToEntity(parameterDTODetails).getBonusTw());
		}
		
		if(parameterDTODetails.getBatasanBonusPg() != null) {
			parameter.setBatasanBonusPg(convertToEntity(parameterDTODetails).getBatasanBonusPg());
		}
		
		if(parameterDTODetails.getBatasanBonusTs() != null) {
			parameter.setBatasanBonusTs(convertToEntity(parameterDTODetails).getBatasanBonusTs());
		}
		
		if(parameterDTODetails.getBatasanBonusTw() != null) {
			parameter.setBatasanBonusTw(convertToEntity(parameterDTODetails).getBatasanBonusTw());
		}
		
		if(parameterDTODetails.getMaxBonus() != null) {
			parameter.setMaxBonus(convertToEntity(parameterDTODetails).getMaxBonus());
		}
			
		parameterRepository.save(parameter);
		
		hmParameter.put("Message : ", "Update Parameter Succes!");
		hmParameter.put("Data : ", parameter);
		
		return hmParameter;
	}
	
	//Delete Parameter By ID
	@DeleteMapping("parameter/delete/{id}")
	public HashMap<String, Object> deleteParameter(@PathVariable (name="id") Integer id){
		HashMap<String, Object> hmParameter = new HashMap<String, Object>();
		Parameter parameter = parameterRepository.findById(id).orElseThrow(null);
		
		parameterRepository.delete(parameter);
		
		hmParameter.put("Message : ", "Delete Parameter Succes!");
		hmParameter.put("Data : ", parameter);
		
		return hmParameter;
	}
	
	
	
}
