package com.origin.paula.risk.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.origin.paula.risk.enums.InsuranceType;

public class InsurancePlan {
	@JsonProperty("auto")
	private String auto;
	
	@JsonProperty("disability")
	private String disability;
	
	@JsonProperty("home")
	private String home;
	
	@JsonProperty("life")
	private String life;

	public InsurancePlan() {}
	
	public InsurancePlan(String auto, String disability, String home, String life) {
		super();
		this.auto = auto;
		this.disability = disability;
		this.home = home;
		this.life = life;
	}
	
	public void checkForInegibility(RiskProfile riskProfile) {
		if (riskProfile.getIncome() == 0)
			this.setDisability(InsuranceType.INELEGIBLE.getValue());

		if (riskProfile.getHouseOwned() == null)
			this.setHome(InsuranceType.INELEGIBLE.getValue());

		if (riskProfile.getVehicleOwned() == null)
			this.setAuto(InsuranceType.INELEGIBLE.getValue());

		if (riskProfile.getAge() > 60) {
			this.setDisability(InsuranceType.INELEGIBLE.getValue());
			this.setLife(InsuranceType.INELEGIBLE.getValue());
		}
	}

	public void updatePlans(InsuranceRiskScore riskScore) {
		if (!InsuranceType.INELEGIBLE.getValue().equals(this.getAuto()))
			this.setAuto(riskScore.getAuto());

		if (!InsuranceType.INELEGIBLE.getValue().equals(this.getHome()))
			this.setHome(riskScore.getHome());

		if (!InsuranceType.INELEGIBLE.getValue().equals(this.getDisability()))
			this.setDisability(riskScore.getDisability());

		if (!InsuranceType.INELEGIBLE.getValue().equals(this.getLife()))
			this.setLife(riskScore.getLife());
	}

	public String getAuto() {
		return auto;
	}

	public void setAuto(String auto) {
		this.auto = auto;
	}

	public String getDisability() {
		return disability;
	}

	public void setDisability(String disability) {
		this.disability = disability;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getLife() {
		return life;
	}

	public void setLife(String life) {
		this.life = life;
	}
	
}
