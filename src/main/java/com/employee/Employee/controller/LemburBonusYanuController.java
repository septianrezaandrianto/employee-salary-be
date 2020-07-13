package com.employee.Employee.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.validation.Valid;

import com.employee.Employee.dto.LemburBonusDTO;
import com.employee.Employee.model.LemburBonus;
import com.employee.Employee.repository.LemburBonusRepository;

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
public class LemburBonusYanuController {

    @Autowired
    LemburBonusRepository lemburBonusRepository;

    ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/lembur-bonus/get/all")
    public HashMap<String, Object> getAllLemburBonus() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        ArrayList<LemburBonusDTO> lemburBonusList = new ArrayList<LemburBonusDTO>();

        for (LemburBonus tempLem : lemburBonusRepository.findAll()) {
            lemburBonusList.add(convertToDTO(tempLem));
        }

        result.put("Message", "Success");
        result.put("Total", lemburBonusList.size());
        result.put("Data", lemburBonusList);

        return result;
    }

    @GetMapping("/lembur-bonus/get/{id}")
    public HashMap<String, Object> getLemburBonusById(@PathVariable(name = "id") Long id) {
        HashMap<String, Object> result = new HashMap<String, Object>();

        result.put("Message", "Success");
        result.put("Data", convertToDTO(lemburBonusRepository.findById(id).get()));

        return result;
    }

    @PostMapping("/lembur-bonus/add")
    public HashMap<String, Object> createLemburBonus(@Valid @RequestBody LemburBonusDTO lemburBonusDTO) {
        HashMap<String, Object> result = new HashMap<String, Object>();

        if (newLemburBonusValidation(lemburBonusDTO)) {
            result.put("Message", "Success");
            result.put("Data", convertToDTO(lemburBonusRepository.save(convertToEntity(lemburBonusDTO))));
        } else {
            result = updateLemburBonusById(getLemburBonusId(lemburBonusDTO), lemburBonusDTO);
        }

        return result;
    }
    
    @PutMapping("/lembur-bonus/update/{id}")
    public HashMap<String, Object> updateLemburBonusById(@PathVariable(name = "id") Long id, @Valid @RequestBody LemburBonusDTO lemburBonusDTO) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        LemburBonusDTO updatedLemburBonusDTO = convertToDTO(lemburBonusRepository.findById(id).get());

        if (lemburBonusDTO.getIdKaryawan() != null) {
            updatedLemburBonusDTO.setIdKaryawan(lemburBonusDTO.getIdKaryawan());
        }

        if (lemburBonusDTO.getTanggalLemburBonus() != null) {
            updatedLemburBonusDTO.setTanggalLemburBonus(lemburBonusDTO.getTanggalLemburBonus());
        }

        if (lemburBonusDTO.getLamaLembur() > 0) {
            updatedLemburBonusDTO.setLamaLembur(lemburBonusDTO.getLamaLembur());
        } else {
            updatedLemburBonusDTO.setLamaLembur(updatedLemburBonusDTO.getLamaLembur());
        }
        
        if (lemburBonusDTO.getVariableBonus() > 0) {
            updatedLemburBonusDTO.setVariableBonus(lemburBonusDTO.getVariableBonus());
        } else {
            updatedLemburBonusDTO.setVariableBonus(updatedLemburBonusDTO.getVariableBonus());
        }

        result.put("Message", "Success");
        result.put("Data", convertToDTO(lemburBonusRepository.save(convertToEntity(updatedLemburBonusDTO))));

        return result;
    }

    @DeleteMapping("/lembur-bonus/delete/{id}")
    public HashMap<String, Object> deleteLemburBonusById(@PathVariable(name = "id") Long id) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        LemburBonusDTO deletedLemburBonusDTO = convertToDTO(lemburBonusRepository.findById(id).get());

        lemburBonusRepository.deleteById(id);

        result.put("Message", "Success");
        result.put("Data", deletedLemburBonusDTO);

        return result;
    }

    public Long getLemburBonusId(LemburBonusDTO lemburBonusDTO) {
        SimpleDateFormat simDatMonth = new SimpleDateFormat("MM");
        SimpleDateFormat simDatYear = new SimpleDateFormat("yyyy");
        Long id = null;

        for (LemburBonus tempLem : lemburBonusRepository.findAll()) {
            String month = simDatMonth.format(lemburBonusDTO.getTanggalLemburBonus());
            String year = simDatYear.format(lemburBonusDTO.getTanggalLemburBonus());

            String temMo = simDatMonth.format(tempLem.getTanggalLemburBonus());
            String temYe = simDatYear.format(tempLem.getTanggalLemburBonus());

            if (lemburBonusDTO.getIdKaryawan() == convertToDTO(tempLem).getIdKaryawan() && month.equalsIgnoreCase(temMo) && year.equalsIgnoreCase(temYe)) {
                id = tempLem.getIdLemburBonus();
                break;
            }
        }

        return id;
    }

    public boolean newLemburBonusValidation(LemburBonusDTO lemburBonusDTO) {
        SimpleDateFormat simDatMonth = new SimpleDateFormat("MM");
        SimpleDateFormat simDatYear = new SimpleDateFormat("yyyy");
        boolean isNew = true;

        for (LemburBonus tempLem : lemburBonusRepository.findAll()) {
            String month = simDatMonth.format(lemburBonusDTO.getTanggalLemburBonus());
            String year = simDatYear.format(lemburBonusDTO.getTanggalLemburBonus());

            String temMo = simDatMonth.format(tempLem.getTanggalLemburBonus());
            String temYe = simDatYear.format(tempLem.getTanggalLemburBonus());

            if (lemburBonusDTO.getIdKaryawan() == convertToDTO(tempLem).getIdKaryawan() && month.equalsIgnoreCase(temMo) && year.equalsIgnoreCase(temYe)) {
                isNew = false;
                break;
            }
        }

        return isNew;
    }

    public LemburBonus convertToEntity(LemburBonusDTO lemburBonusDTO) {
        return modelMapper.map(lemburBonusDTO, LemburBonus.class);
    }

    public LemburBonusDTO convertToDTO(LemburBonus lemburBonus) {
        return modelMapper.map(lemburBonus, LemburBonusDTO.class);
    }

}