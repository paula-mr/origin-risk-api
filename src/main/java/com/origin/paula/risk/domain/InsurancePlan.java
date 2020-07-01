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

	public void calculate(RiskProfile riskProfile) {
		RiskScore riskScore = new RiskScore();
		riskScore.calculate(riskProfile);
		
		checkForIneligibility(riskProfile);
		updatePlan(riskScore);
	}
	
	private void checkForIneligibility(RiskProfile riskProfile) {
		if (!riskProfile.hasIncome())
			this.setDisability(InsuranceType.INELIGIBLE.getValue());

		if (!riskProfile.ownsHouse())
			this.setHome(InsuranceType.INELIGIBLE.getValue());

		if (!riskProfile.ownsVehicle())
			this.setAuto(InsuranceType.INELIGIBLE.getValue());

		if (riskProfile.isElderly()) {
			this.setDisability(InsuranceType.INELIGIBLE.getValue());
			this.setLife(InsuranceType.INELIGIBLE.getValue());
		}
	}

	private void updatePlan(RiskScore riskScore) {
		if (!InsuranceType.INELIGIBLE.getValue().equals(this.getAuto()))
			this.setAuto(mapScore(riskScore.getAutoRisk()));

		if (!InsuranceType.INELIGIBLE.getValue().equals(this.getHome()))
			this.setHome(mapScore(riskScore.getHomeRisk()));

		if (!InsuranceType.INELIGIBLE.getValue().equals(this.getDisability()))
			this.setDisability(mapScore(riskScore.getDisabilityRisk()));

		if (!InsuranceType.INELIGIBLE.getValue().equals(this.getLife()))
			this.setLife(mapScore(riskScore.getLifeRisk()));
	}
	
	private String mapScore(int score) {
		if (score < 1)
			return InsuranceType.ECONOMIC.getValue();

		if (score < 3)
			return InsuranceType.REGULAR.getValue();

		return InsuranceType.RESPONSIBLE.getValue();
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
