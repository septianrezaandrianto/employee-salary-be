package com.employee.Employee.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.validation.Valid;

import com.employee.Employee.dto.KemampuanDTO;
import com.employee.Employee.model.Kemampuan;
import com.employee.Employee.repository.KemampuanRepository;

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

@RestController
@RequestMapping("/api")
public class KemampuanController {

    @Autowired
    KemampuanRepository kemampuanRepository;

    ModelMapper modelMapper = new ModelMapper();

    // Get all kemampuan
    @GetMapping("/kemampuan/all")
    public HashMap<String, Object> getAllKemampuan() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        ArrayList<KemampuanDTO> kemampuanList = new ArrayList<KemampuanDTO>();
        
        for (Kemampuan tempKem : kemampuanRepository.findAll()) {
            kemampuanList.add(convertToDTO(tempKem));
        }

        result.put("Message", "Success");
        result.put("Total", kemampuanList.size());
        result.put("Data", kemampuanList);

        return result;
    }

    // Get kemampuan by id
    @GetMapping("/kemampuan/{id}")
    public HashMap<String, Object> getKemampuanById(@PathVariable(value = "id") Integer kemampuanId) {
        HashMap<String, Object> result = new HashMap<String, Object>();
    
        result.put("Message", "Success");
        result.put("Data", convertToDTO(kemampuanRepository.findById(kemampuanId).get()));

        return result;
    }
    
    // Create new kemampuan
    @PostMapping("/kemampuan/add")
    public HashMap<String, Object> createKemampuan(@Valid @RequestBody KemampuanDTO kemampuanDTO) {
        HashMap<String, Object> result = new HashMap<String, Object>();
    
        result.put("Message", "Success");
        result.put("Data", convertToDTO(kemampuanRepository.save(convertToEntity(kemampuanDTO))));

        return result;
    }

    // Update kemampuan
    @PutMapping("/kemampuan/update/{id}")
    public HashMap<String, Object> updateKemampuan(@PathVariable(value = "id") Integer kemampuanId, @Valid @RequestBody KemampuanDTO kemampuanDTO) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        KemampuanDTO updatedKemampuanDTO = convertToDTO(kemampuanRepository.findById(kemampuanId).get());

        if (kemampuanDTO.getNamaKemampuan() != null) {
            updatedKemampuanDTO.setNamaKemampuan(kemampuanDTO.getNamaKemampuan());
        }

        if (kemampuanDTO.getKategoriKemampuan() != null) {
            updatedKemampuanDTO.setKategoriKemampuan(kemampuanDTO.getKategoriKemampuan());
        }

        result.put("Message", "Success");
        result.put("Data", convertToDTO(kemampuanRepository.save(convertToEntity(updatedKemampuanDTO))));

        return result;
    }

    // Delete Kemampuan by id
    @DeleteMapping("/kemampuan/delete/{id}")
    public HashMap<String, Object> deleteKemampuan(@PathVariable(value = "id") Integer kemampuanId) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        kemampuanRepository.delete(kemampuanRepository.findById(kemampuanId).get());

        result.put("Message", "Success");

        return result;
    }

    public KemampuanDTO convertToDTO(Kemampuan kemampuan) {
        return modelMapper.map(kemampuan, KemampuanDTO.class);
    }

    public Kemampuan convertToEntity(KemampuanDTO kemampuanDTO) {
        return modelMapper.map(kemampuanDTO, Kemampuan.class);
    }

}