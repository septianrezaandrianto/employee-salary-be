package com.employee.Employee.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ParameterDTO {
	
	private int idParam;
	private Date tbParameter;
	private BigDecimal TKeluarga;
	private BigDecimal TTransport;
	private BigDecimal PBpjs;
	private BigDecimal lembur;
	private BigDecimal bonusPg;
	private BigDecimal bonusTs;
	private BigDecimal bonusTw;
	private Integer batasanBonusPg;
	private Integer batasanBonusTs;
	private Integer batasanBonusTw;
	private BigDecimal maxBonus;
	
	public ParameterDTO () {
		
	}

	public ParameterDTO(int idParam, Date tbParameter, BigDecimal tKeluarga, BigDecimal tTransport, BigDecimal pBpjs,
			BigDecimal lembur, BigDecimal bonusPg, BigDecimal bonusTs, BigDecimal bonusTw, Integer batasanBonusPg,
			Integer batasanBonusTs, Integer batasanBonusTw, BigDecimal maxBonus) {
		super();
		this.idParam = idParam;
		this.tbParameter = tbParameter;
		TKeluarga = tKeluarga;
		TTransport = tTransport;
		PBpjs = pBpjs;
		this.lembur = lembur;
		this.bonusPg = bonusPg;
		this.bonusTs = bonusTs;
		this.bonusTw = bonusTw;
		this.batasanBonusPg = batasanBonusPg;
		this.batasanBonusTs = batasanBonusTs;
		this.batasanBonusTw = batasanBonusTw;
		this.maxBonus = maxBonus;
	}

	public int getIdParam() {
		return idParam;
	}

	public void setIdParam(int idParam) {
		this.idParam = idParam;
	}

	public Date getTbParameter() {
		return tbParameter;
	}

	public void setTbParameter(Date tbParameter) {
		this.tbParameter = tbParameter;
	}

	public BigDecimal getTKeluarga() {
		return TKeluarga;
	}

	public void setTKeluarga(BigDecimal tKeluarga) {
		TKeluarga = tKeluarga;
	}

	public BigDecimal getTTransport() {
		return TTransport;
	}

	public void setTTransport(BigDecimal tTransport) {
		TTransport = tTransport;
	}

	public BigDecimal getPBpjs() {
		return PBpjs;
	}

	public void setPBpjs(BigDecimal pBpjs) {
		PBpjs = pBpjs;
	}

	public BigDecimal getLembur() {
		return lembur;
	}

	public void setLembur(BigDecimal lembur) {
		this.lembur = lembur;
	}

	public BigDecimal getBonusPg() {
		return bonusPg;
	}

	public void setBonusPg(BigDecimal bonusPg) {
		this.bonusPg = bonusPg;
	}

	public BigDecimal getBonusTs() {
		return bonusTs;
	}

	public void setBonusTs(BigDecimal bonusTs) {
		this.bonusTs = bonusTs;
	}

	public BigDecimal getBonusTw() {
		return bonusTw;
	}

	public void setBonusTw(BigDecimal bonusTw) {
		this.bonusTw = bonusTw;
	}

	public Integer getBatasanBonusPg() {
		return batasanBonusPg;
	}

	public void setBatasanBonusPg(Integer batasanBonusPg) {
		this.batasanBonusPg = batasanBonusPg;
	}

	public Integer getBatasanBonusTs() {
		return batasanBonusTs;
	}

	public void setBatasanBonusTs(Integer batasanBonusTs) {
		this.batasanBonusTs = batasanBonusTs;
	}

	public Integer getBatasanBonusTw() {
		return batasanBonusTw;
	}

	public void setBatasanBonusTw(Integer batasanBonusTw) {
		this.batasanBonusTw = batasanBonusTw;
	}

	public BigDecimal getMaxBonus() {
		return maxBonus;
	}

	public void setMaxBonus(BigDecimal maxBonus) {
		this.maxBonus = maxBonus;
	}
	
	
	
}
