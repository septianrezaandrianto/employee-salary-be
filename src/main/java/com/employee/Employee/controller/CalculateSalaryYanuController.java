package com.employee.Employee.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.employee.Employee.dto.PendapatanDTO;
import com.employee.Employee.model.Karyawan;
import com.employee.Employee.model.LemburBonus;
import com.employee.Employee.model.Parameter;
import com.employee.Employee.model.Pendapatan;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    
    @GetMapping("/calculate-salary-yanu")
    public HashMap<Integer, Object> calculateTakeHomePay(@RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date requestDate) {
        SimpleDateFormat simDatMonth = new SimpleDateFormat("MM");
        SimpleDateFormat simDatYear = new SimpleDateFormat("yyyy");
        HashMap<Integer, Object> result = new HashMap<Integer, Object>();

        if (newMonthAndYearValidation(requestDate)) {
            for (Karyawan tempKar : karyawanRepository.findAll()) {
                Pendapatan pendapatan = new Pendapatan();

                double gajiPokok = calculateGajiPokok(tempKar);
                double tunjangan = calculateAllTunjangan(requestDate, gajiPokok, tempKar);
                double gajiKotor = gajiPokok + tunjangan;
                
                double potongan = calculateAllPotongan(gajiPokok, gajiKotor, requestDate);
                double gajiBersih = gajiKotor - potongan;

                double bonus = calculateAllBonus(requestDate, gajiPokok, tempKar);
                double takeHomePay = gajiBersih + bonus;

                pendapatan.setKaryawan(tempKar);
                pendapatan.setTanggalGaji(requestDate);
                pendapatan.setGajiPokok(BigDecimal.valueOf(gajiPokok));
                pendapatan.setTunjanganKeluarga(BigDecimal.valueOf(calculateTunjanganPernikahan(tempKar, requestDate)));
                pendapatan.setTunjanganPegawai(BigDecimal.valueOf(chooseTunjanganPegawai(tempKar)));
                pendapatan.setTunjanganTransport(BigDecimal.valueOf(calculateTunjanganTransport(tempKar, requestDate)));
                pendapatan.setGajiKotor(BigDecimal.valueOf(gajiKotor));
                pendapatan.setPphPerbulan(BigDecimal.valueOf(calculatePPH(gajiKotor)));
                pendapatan.setBpjs(BigDecimal.valueOf(calculateBPJS(requestDate, calculateGajiPokok(tempKar))));
                pendapatan.setGajiBersih(BigDecimal.valueOf(gajiBersih));

                for (LemburBonus tempLem : lemburBonusRepository.findAll()) {
                    if (simDatMonth.format(tempLem.getTanggalLemburBonus()).equals(simDatMonth.format(requestDate)) && simDatYear.format(tempLem.getTanggalLemburBonus()).equals(simDatYear.format(requestDate)) && tempKar.getIdKaryawan() == tempLem.getIdKaryawan()) {
                        pendapatan.setLamaLembur(tempLem.getLamaLembur());
                        pendapatan.setVariableBonus(tempLem.getVariableBonus());
                        break;
                    } else {
                        pendapatan.setLamaLembur(0);
                        pendapatan.setVariableBonus(0);
                    }
                }

                pendapatan.setUangLembur(BigDecimal.valueOf(calculateLembur(gajiKotor, tempKar, requestDate)));
                pendapatan.setUangBonus(BigDecimal.valueOf(calculateBonus(tempKar, requestDate)));
                pendapatan.setTakeHomePay(BigDecimal.valueOf(takeHomePay));

                pendapatanRepository.save(pendapatan);

                result.put(tempKar.getIdKaryawan(), takeHomePay);
            }
        } else {
            for (Pendapatan tempPen : pendapatanRepository.findAll() ) {
                if (simDatMonth.format(tempPen.getTanggalGaji()).equals(simDatMonth.format(requestDate)) && simDatYear.format(tempPen.getTanggalGaji()).equals(simDatYear.format(requestDate))) {
                    for (Karyawan tempKar : karyawanRepository.findAll()) {
                        if (tempKar.getIdKaryawan() == tempPen.getKaryawan().getIdKaryawan()){
                            PendapatanDTO updatedPendapatan = convertToDTO(pendapatanRepository.findById(tempPen.getIdPendapatan()).get());

                            double gajiPokok = calculateGajiPokok(tempKar);
                            double tunjangan = calculateAllTunjangan(requestDate, gajiPokok, tempKar);
                            double gajiKotor = gajiPokok + tunjangan;
                            
                            double potongan = calculateAllPotongan(gajiPokok, gajiKotor, requestDate);
                            double gajiBersih = gajiKotor - potongan;
            
                            double bonus = calculateAllBonus(requestDate, gajiPokok, tempKar);
                            double takeHomePay = gajiBersih + bonus;

                            updatedPendapatan.setTanggalGaji(requestDate);
                            updatedPendapatan.setGajiPokok(BigDecimal.valueOf(gajiPokok));
                            updatedPendapatan.setTunjanganKeluarga(BigDecimal.valueOf(calculateTunjanganPernikahan(tempKar, requestDate)));
                            updatedPendapatan.setTunjanganPegawai(BigDecimal.valueOf(chooseTunjanganPegawai(tempKar)));
                            updatedPendapatan.setTunjanganTransport(BigDecimal.valueOf(calculateTunjanganTransport(tempKar, requestDate)));
                            updatedPendapatan.setGajiKotor(BigDecimal.valueOf(gajiKotor));
                            updatedPendapatan.setPphPerbulan(BigDecimal.valueOf(calculatePPH(gajiKotor)));
                            updatedPendapatan.setBpjs(BigDecimal.valueOf(calculateBPJS(requestDate, calculateGajiPokok(tempKar))));
                            updatedPendapatan.setGajiBersih(BigDecimal.valueOf(gajiBersih));

                            for (LemburBonus tempLem : lemburBonusRepository.findAll()) {
                                if (simDatMonth.format(tempLem.getTanggalLemburBonus()).equals(simDatMonth.format(requestDate)) && simDatYear.format(tempLem.getTanggalLemburBonus()).equals(simDatYear.format(requestDate)) && tempKar.getIdKaryawan() == tempLem.getIdKaryawan()) {
                                    updatedPendapatan.setLamaLembur(tempLem.getLamaLembur());
                                    updatedPendapatan.setVariableBonus(tempLem.getVariableBonus());
                                    break;
                                } else {
                                    updatedPendapatan.setLamaLembur(0);
                                    updatedPendapatan.setVariableBonus(0);
                                }
                            }

                            updatedPendapatan.setUangLembur(BigDecimal.valueOf(calculateLembur(gajiKotor, tempKar, requestDate)));
                            updatedPendapatan.setUangBonus(BigDecimal.valueOf(calculateBonus(tempKar, requestDate)));
                            updatedPendapatan.setTakeHomePay(BigDecimal.valueOf(takeHomePay));

                            pendapatanRepository.save(convertToEntity(updatedPendapatan));

                            result.put(tempKar.getIdKaryawan(), takeHomePay);
                        }
                    }
                }
            }
        }

        return result;
    }

    public boolean newMonthAndYearValidation(Date requestDate) {
        SimpleDateFormat simDatMonth = new SimpleDateFormat("MM");
        SimpleDateFormat simDatYear = new SimpleDateFormat("yyyy");
        boolean isNew = true;

        for (Pendapatan tempPen : pendapatanRepository.findAll()) {
            if (simDatMonth.format(tempPen.getTanggalGaji()).equals(simDatMonth.format(requestDate)) && simDatYear.format(tempPen.getTanggalGaji()).equals(simDatYear.format(requestDate))) {
                isNew = false;
                break;
            }
        }

        return isNew;
    }

    public double calculateAllTunjangan(Date requestDate, double gajiPokok, Karyawan karyawan) {
        double result = chooseTunjanganPegawai(karyawan);

        result += calculateTunjanganPernikahan(karyawan, requestDate);
        result += calculateTunjanganTransport(karyawan, requestDate);

        return result;
    }

    public double calculateAllBonus(Date requestDate, double gajiPokok, Karyawan karyawan) {
        double result = 0;

        result += calculateLembur(gajiPokok, karyawan, requestDate);
        result += calculateBonus(karyawan, requestDate);

        return result;
    }

    public double calculateLembur(double gajiKotor, Karyawan karyawan, Date requestDate) {
        SimpleDateFormat simDatMonth = new SimpleDateFormat("MM");
        SimpleDateFormat simDatYear = new SimpleDateFormat("yyyy");
        double result = 0;

        for (Parameter tempPar : parameterRepository.findAll()) {
            if (simDatMonth.format(tempPar.getTbParameter()).equals(simDatMonth.format(requestDate)) && simDatYear.format(tempPar.getTbParameter()).equals(simDatYear.format(requestDate))) {
                for (LemburBonus tempLem : lemburBonusRepository.findAll()) {
                    if (simDatMonth.format(tempLem.getTanggalLemburBonus()).equals(simDatMonth.format(tempPar.getTbParameter())) && simDatYear.format(tempLem.getTanggalLemburBonus()).equals(simDatYear.format(tempPar.getTbParameter())) && karyawan.getIdKaryawan() == tempLem.getIdKaryawan()) {
                        result = (gajiKotor * tempLem.getLamaLembur()) * tempPar.getLembur().doubleValue();
                        break;
                    }
                }
                break;
            }
        }

        return result;
    }

    public double calculateBonus(Karyawan karyawan, Date requestDate) {
        SimpleDateFormat simDatMonth = new SimpleDateFormat("MM");
        SimpleDateFormat simDatYear = new SimpleDateFormat("yyyy");
        double result = 0;

        for (Parameter tempPar : parameterRepository.findAll()) {
            if (simDatMonth.format(tempPar.getTbParameter()).equals(simDatMonth.format(requestDate)) && simDatYear.format(tempPar.getTbParameter()).equals(simDatYear.format(requestDate))) {      
                for (LemburBonus tempLem : lemburBonusRepository.findAll()) {
                    if (simDatMonth.format(tempLem.getTanggalLemburBonus()).equals(simDatMonth.format(tempPar.getTbParameter())) && simDatYear.format(tempLem.getTanggalLemburBonus()).equals(simDatYear.format(tempPar.getTbParameter())) && karyawan.getIdKaryawan() == tempLem.getIdKaryawan() && karyawan.getPosisi().getIdPosisi() != 2) {
                        result = (tempLem.getVariableBonus() / chooseBatasanBonus(karyawan.getIdKaryawan(), tempPar)) * chooseBonus(karyawan.getIdKaryawan(), tempPar);

                        if (result > tempPar.getMaxBonus().doubleValue()) {
                            result = tempPar.getMaxBonus().doubleValue();
                        }
                        break;
                    }
                }
                break;
            }
        }

        return result;
    }

    public double chooseBonus(Integer karyawanId, Parameter parameter) {
        double bonus = 0;
        Karyawan karyawan = karyawanRepository.findById(karyawanId).get();

        if (karyawan.getPosisi().getNamaPosisi().equalsIgnoreCase("Programmer")) {
            bonus = parameter.getBonusPg().doubleValue();
        } else if (karyawan.getPosisi().getNamaPosisi().equalsIgnoreCase("Tester")) {
            bonus = parameter.getBonusTs().doubleValue();
        } else if (karyawan.getPosisi().getNamaPosisi().equalsIgnoreCase("Technical Writter")) {
            bonus = parameter.getBonusTw().doubleValue();
        }
        
        return bonus;
    }

    public double chooseBatasanBonus(Integer karyawanId, Parameter parameter) {
        double batasanBonus = 0;
        Karyawan karyawan = karyawanRepository.findById(karyawanId).get();

        if (karyawan.getPosisi().getNamaPosisi().equalsIgnoreCase("Programmer")) {
            batasanBonus = parameter.getBatasanBonusPg();
        } else if (karyawan.getPosisi().getNamaPosisi().equalsIgnoreCase("Tester")) {
            batasanBonus = parameter.getBatasanBonusTs();
        } else if (karyawan.getPosisi().getNamaPosisi().equalsIgnoreCase("Technical Writter")) {
            batasanBonus = parameter.getBatasanBonusTw();
        }
        
        return batasanBonus;
    }

    public double calculateTunjanganPernikahan(Karyawan karyawan, Date requestDate) {
        SimpleDateFormat simDatMonth = new SimpleDateFormat("MM");
        SimpleDateFormat simDatYear = new SimpleDateFormat("yyyy");
        double tunjanganPernikahan = 0;

        for (Parameter tempPar : parameterRepository.findAll()) {
            if (simDatMonth.format(tempPar.getTbParameter()).equals(simDatMonth.format(requestDate)) && simDatYear.format(tempPar.getTbParameter()).equals(simDatYear.format(requestDate)) && karyawan.getStatusPernikahan() == 1) {
                tunjanganPernikahan = calculateGajiPokok(karyawan) * tempPar.getTKeluarga().doubleValue();
            }
        }

        return tunjanganPernikahan;
    }

    public double calculateTunjanganTransport(Karyawan karyawan, Date requestDate) {
        SimpleDateFormat simDatMonth = new SimpleDateFormat("MM");
        SimpleDateFormat simDatYear = new SimpleDateFormat("yyyy");
        double tunjanganTransport = 0;

        for (Parameter tempPar : parameterRepository.findAll()) {
            if (simDatMonth.format(tempPar.getTbParameter()).equals(simDatMonth.format(requestDate)) && simDatYear.format(tempPar.getTbParameter()).equals(simDatYear.format(requestDate)) && karyawan.getPenempatan().getKotaPenempatan().equals("DKI Jakarta")) {
                tunjanganTransport = tempPar.getTTransport().doubleValue();
            }
        }
        
        return tunjanganTransport;
    }

    public double calculateAllPotongan(double gajiPokok, double gajiKotor, Date requestDate) {
        double result = 0;
        
        result = calculateBPJS(requestDate, gajiPokok);
        result += calculatePPH(gajiKotor);

        return result;
    }

    public double calculatePPH(double gajiKotor) {
        return gajiKotor * 0.05;
    }

    public double calculateBPJS(Date requestDate, double gajiPokok) {
        SimpleDateFormat simDat = new SimpleDateFormat("MM");
        SimpleDateFormat simDatYear = new SimpleDateFormat("yyyy");
        double result = 0;

        for (Parameter tempPar : parameterRepository.findAll()) {
            if (simDat.format(tempPar.getTbParameter()).equals(simDat.format(requestDate)) && simDatYear.format(tempPar.getTbParameter()).equals(simDatYear.format(requestDate))) {
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
                break;
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
                break;
            }
        }

        return tunjanganPegawai;
    }

    public PendapatanDTO convertToDTO(Pendapatan pendapatan) {
        return modelMapper.map(pendapatan, PendapatanDTO.class);
    }

    public Pendapatan convertToEntity(PendapatanDTO pendapatanDTO) {
        return modelMapper.map(pendapatanDTO, Pendapatan.class);
    }

}