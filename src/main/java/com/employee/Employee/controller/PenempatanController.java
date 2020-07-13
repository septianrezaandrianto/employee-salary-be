package com.employee.Employee.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.validation.Valid;

import com.employee.Employee.dto.PenempatanDTO;
import com.employee.Employee.model.Penempatan;
import com.employee.Employee.repository.PenempatanRepository;

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

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PenempatanController {

    @Autowired
    PenempatanRepository penempatanRepository;

    ModelMapper modelMapper = new ModelMapper();

    // Get all penempatan
    @GetMapping("/penempatan/all")
    public HashMap<String, Object> getAllPenempatan() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        ArrayList<PenempatanDTO> penempatanList = new ArrayList<PenempatanDTO>();
        
        for (Penempatan tempPen : penempatanRepository.findAll()) {
            penempatanList.add(convertToDTO(tempPen));
        }

        result.put("Message", "Success");
        result.put("Total", penempatanList.size());
        result.put("Data", penempatanList);

        return result;
    }

    // Get penempatan by id
    @GetMapping("/penempatan/{id}")
    public HashMap<String, Object> getPenempatanById(@PathVariable(value = "id") Integer penempatanId) {
        HashMap<String, Object> result = new HashMap<String, Object>();
    
        result.put("Message", "Success");
        result.put("Data", convertToDTO(penempatanRepository.findById(penempatanId).get()));

        return result;
    }

    // Create new penempatan
    @PostMapping("/penempatan/add")
    public HashMap<String, Object> createPenempatan(@Valid @RequestBody PenempatanDTO penempatanDTO) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        
        if (validateCity(penempatanDTO.getKotaPenempatan())) {
            result.put("Message", "Success");
            result.put("Data", convertToDTO(penempatanRepository.save(convertToEntity(penempatanDTO))));
        } else {
            result.put("Message", "Failed, city is already existed.");
        }
        
        return result;
    }

    // Update penempatan
    @PutMapping("/penempatan/update/{id}")
    public HashMap<String, Object> updatePenempatan(@PathVariable(value = "id") Integer penempatanId, @Valid @RequestBody PenempatanDTO penempatanDTO) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        PenempatanDTO updatedPenempatanDTO = convertToDTO(penempatanRepository.findById(penempatanId).get());
        boolean isValid = true;

        if (penempatanDTO.getKotaPenempatan() != null) {
            if (validateCity(penempatanDTO.getKotaPenempatan())) {
                updatedPenempatanDTO.setKotaPenempatan(penempatanDTO.getKotaPenempatan());
            } else {
                isValid = false;
            }
        }

        if (penempatanDTO.getUmkPenempatan() != null) {
            updatedPenempatanDTO.setUmkPenempatan(penempatanDTO.getUmkPenempatan());
        }

        if (isValid) {
            result.put("Message", "Success");
            result.put("Data", convertToDTO(penempatanRepository.save(convertToEntity(updatedPenempatanDTO))));
        } else {
            result.put("Message", "Failed, city is already existed.");
        }
            
        return result;
    }

    // Delete penempatan by id
    @DeleteMapping("/penempatan/delete/{id}")
    public HashMap<String, Object> deletePenempatan(@PathVariable(value = "id") Integer penempatanId) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        penempatanRepository.delete(penempatanRepository.findById(penempatanId).get());

        result.put("Message", "Success");

        return result;
    }

    // City validate method 
    public boolean validateCity(String cityInput) {
        boolean isValid = true;

        for (Penempatan tempPen : penempatanRepository.findAll()) {
            if (cityInput.replaceAll("\\s+", "").equalsIgnoreCase(tempPen.getKotaPenempatan().replaceAll("\\s+", ""))) {
                isValid = false;
            }
        }

        return isValid;
    }

    public PenempatanDTO convertToDTO(Penempatan penempatan) {
        return modelMapper.map(penempatan, PenempatanDTO.class);
    }

    public Penempatan convertToEntity(PenempatanDTO penempatanDTO) {
        return modelMapper.map(penempatanDTO, Penempatan.class);
    }

}