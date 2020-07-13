package com.employee.Employee.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.validation.Valid;

import com.employee.Employee.dto.KategoriKemampuanDTO;
import com.employee.Employee.model.KategoriKemampuan;
import com.employee.Employee.repository.KategoriKemampuanRepository;

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
public class KategoriKemampuanController {

    @Autowired
    KategoriKemampuanRepository kategoriKemampuanRepository;

    ModelMapper modelMapper = new ModelMapper();

    // Get all kategori kemampuan
    @GetMapping("/kategori-kemampuan/all")
    public HashMap<String, Object> getAllKategoriKemampuan() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        ArrayList<KategoriKemampuanDTO> kategoriKemampuanList = new ArrayList<KategoriKemampuanDTO>();
        
        for (KategoriKemampuan tempKat : kategoriKemampuanRepository.findAll()) {
            kategoriKemampuanList.add(convertToDTO(tempKat));
        }

        result.put("Message", "Success");
        result.put("Total", kategoriKemampuanList.size());
        result.put("Data", kategoriKemampuanList);

        return result;
    }

    // Get kategori kemampuan by id
    @GetMapping("/kategori-kemampuan/{id}")
    public HashMap<String, Object> getKategoriKemampuanById(@PathVariable(value = "id") Integer kategoriKemampuanId) {
        HashMap<String, Object> result = new HashMap<String, Object>();
    
        result.put("Message", "Success");
        result.put("Data", convertToDTO(kategoriKemampuanRepository.findById(kategoriKemampuanId).get()));

        return result;
    }

    // Create new kategori kemampuan
    @PostMapping("/kategori-kemampuan/add")
    public HashMap<String, Object> createKategoriKemampuan(@Valid @RequestBody KategoriKemampuanDTO kategoriKemampuanDTO) {
        HashMap<String, Object> result = new HashMap<String, Object>();
    
        result.put("Message", "Success");
        result.put("Data", convertToDTO(kategoriKemampuanRepository.save(convertToEntity(kategoriKemampuanDTO))));

        return result;
    }

    // Update kategori kemampuan
    @PutMapping("/kategori-kemampuan/update/{id}")
    public HashMap<String, Object> updateKategoriKemampuan(@PathVariable(value = "id") Integer kategoriKemampuanId, @Valid @RequestBody KategoriKemampuanDTO kategoriKemampuanDTO) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        KategoriKemampuanDTO updatedKategoriKemampuanDTO = convertToDTO(kategoriKemampuanRepository.findById(kategoriKemampuanId).get());

        updatedKategoriKemampuanDTO.setNamaKategori(kategoriKemampuanDTO.getNamaKategori());

        result.put("Message", "Success");
        result.put("Data", convertToDTO(kategoriKemampuanRepository.save(convertToEntity(updatedKategoriKemampuanDTO))));

        return result;
    }

    // Delete kategori Kemampuan by id
    @DeleteMapping("/kategori-kemampuan/delete/{id}")
    public HashMap<String, Object> deleteKategoriKemampuan(@PathVariable(value = "id") Integer kategoriKemampuanId) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        kategoriKemampuanRepository.delete(kategoriKemampuanRepository.findById(kategoriKemampuanId).get());

        result.put("Message", "Success");

        return result;
    }

    public KategoriKemampuanDTO convertToDTO(KategoriKemampuan kategoriKemampuan) {
        return modelMapper.map(kategoriKemampuan, KategoriKemampuanDTO.class);
    }

    public KategoriKemampuan convertToEntity(KategoriKemampuanDTO kategoriKemampuanDTO) {
        return modelMapper.map(kategoriKemampuanDTO, KategoriKemampuan.class);
    }

}