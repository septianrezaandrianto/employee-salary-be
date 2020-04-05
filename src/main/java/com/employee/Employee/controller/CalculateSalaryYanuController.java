package com.employee.Employee.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.employee.Employee.model.Karyawan;
import com.employee.Employee.model.LemburBonus;
import com.employee.Employee.model.Parameter;
import com.employee.Employee.model.Penempatan;
import com.employee.Employee.model.PresentaseGaji;
import com.employee.Employee.model.TunjanganPegawai;
import com.employee.Employee.repository.KaryawanRepository;
import com.employee.Employee.repository.LemburBonusRepository;
import com.employee.Employee.repository.ParameterRepository;
import com.employee.Employee.repository.PendapatanRepository;
import com.employee.Employee.repository.PenempatanRepository;
import com.employee.Employee.repository.PresentaseGajiRepository;
import com.employee.Employee.repository.TunjanganPegawaiRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CalculateSalaryYanuController {

    @Autowired
    PendapatanRepository pendapatanRepository;

    @Autowired
    KaryawanRepository karyawanRepository;

    @Autowired
    PenempatanRepository penempatanRepository;

    @Autowired
    PresentaseGajiRepository presentaseGajiRepository;

    @Autowired
    TunjanganPegawaiRepository tunjanganPegawaiRepository;

    @Autowired
    ParameterRepository parameterRepository;

    @Autowired
    LemburBonusRepository lemburBonusRepository;

    ModelMapper modelMapper = new ModelMapper();

    // public HashMap<String, Object> getAllPendapatan(@RequestParam(name = "date") Date date) {
    //     HashMap<String, Object> result = new HashMap<String, Object>();



    //     return result;
    // }
    
    @GetMapping("/test/yanu")
    public HashMap<Integer, Object> calculateTakeHomePay(@RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date requestDate) {
        HashMap<Integer, Object> a = new HashMap<Integer, Object>();

        // if (newMonthValidation(requestDate)) {
            for (Karyawan tempKar : karyawanRepository.findAll()) {
                double gajiKotor = calculateGajiPokok(tempKar) + calculateTunjangan(requestDate, calculateGajiPokok(tempKar), tempKar);
                double bpjs = calculateBPJS(requestDate, calculateGajiPokok(tempKar));
                double pph = calculatePPH(gajiKotor);

                a.put(tempKar.getIdKaryawan(), pph);
            }
        // }

        return a;
    }

    public double calculateLembur(LemburBonus lemburBonus, Parameter parameter) {
        return Double.parseDouble(String.valueOf(lemburBonus.getLamaLembur())) * parameter.getLembur().doubleValue();
    }

    public boolean newMonthValidation(Date requestDate) {
        SimpleDateFormat simDat = new SimpleDateFormat("MM");
        boolean isNew = true;

        for (Parameter tempPar : parameterRepository.findAll()) {
            if (simDat.format(tempPar.getTbParameter()).equals(simDat.format(requestDate))) {
                isNew = false;
                break;
            }
        }

        return isNew;
    }

    public double calculateTunjangan(Date requestDate, double gajiPokok, Karyawan karyawan) {
        SimpleDateFormat simDatM = new SimpleDateFormat("MM");
        SimpleDateFormat simDatY = new SimpleDateFormat("yyyy");
        double result = chooseTunjanganPegawai(karyawan);

        for (Parameter tempPar : parameterRepository.findAll()) {
            if (simDatM.format(tempPar.getTbParameter()).equals(simDatM.format(requestDate)) && simDatY.format(tempPar.getTbParameter()).equals(simDatY.format(requestDate))) {
                if (karyawan.getStatusPernikahan() == 1) {
                    result += calculateTunjanganPernikahan(karyawan, tempPar);
                }

                if (karyawan.getPenempatan().getKotaPenempatan().equals("DKI Jakarta")) {
                    result += calculateTunjanganTransport(tempPar);
                }
                
                break;
            }
        }

        return result;
    }

    public double calculateTunjanganPernikahan(Karyawan karyawan, Parameter parameter) {
        return calculateGajiPokok(karyawan) * parameter.getTKeluarga().doubleValue();
    }

    public double calculateTunjanganTransport(Parameter parameter) {
        return parameter.getTTransport().doubleValue();
    }

    public double calculatePPH(double gajiKotor) {
        return gajiKotor * 0.05;
    }

    public double calculateBPJS(Date requestDate, double gajiPokok) {
        SimpleDateFormat simDat = new SimpleDateFormat("MM");
        double result = 0;

        for (Parameter tempPar : parameterRepository.findAll()) {
            if (simDat.format(tempPar.getTbParameter()).equals(simDat.format(requestDate))) {
                result += gajiPokok * tempPar.getPBpjs().doubleValue();
                break;
            }
        }

        return result;
    }

    public double calculateGajiPokok(Karyawan karyawan) {
        return chooseUMK(karyawan) * choosePokokRate(karyawan);
    }

    public double chooseUMK(Karyawan karyawan) {
        double umk = 0;

        for (Penempatan tempPen : penempatanRepository.findAll()) {
            if (karyawan.getPenempatan().getIdPenempatan() == tempPen.getIdPenempatan()) {
                umk = tempPen.getUmkPenempatan().doubleValue();
            }
        }

        return umk;
    }

    public double choosePokokRate(Karyawan karyawan) {
        double gajiPokokRate = 0;

        for (PresentaseGaji tempPre : presentaseGajiRepository.findAll()) {
            if (karyawan.getPosisi().getIdPosisi() == tempPre.getPosisi().getIdPosisi() && karyawan.getTingkatan().getIdTingkatan() == tempPre.getIdTingkatan()) {
                if (karyawan.getMasaKerja() == tempPre.getMasaKerja()) {
                    gajiPokokRate = tempPre.getBesaranGaji().doubleValue();
                    break;
                } else {
                    gajiPokokRate = tempPre.getBesaranGaji().doubleValue();
                }
            }
        }

        return gajiPokokRate;
    }

    public double chooseTunjanganPegawai(Karyawan karyawan) {
        double tunjanganPegawai = 0;

        for (TunjanganPegawai tempTun : tunjanganPegawaiRepository.findAll()) {
            if (karyawan.getPosisi().getIdPosisi() == tempTun.getPosisi().getIdPosisi() && karyawan.getTingkatan().getIdTingkatan() == tempTun.getTingkatan().getIdTingkatan()) {
                tunjanganPegawai = tempTun.getBesaranTujnaganPegawai().doubleValue();
            }
        }

        return tunjanganPegawai;
    }

}