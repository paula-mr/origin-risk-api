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

	public void calculate(RiskProfile riskProfile) {
		RiskScore riskScore = new RiskScore();
		riskScore.calculate(riskProfile);
		
		checkForInegibility(riskProfile);
		updatePlans(riskScore);
	}
	
	private void checkForInegibility(RiskProfile riskProfile) {
		if (!riskProfile.hasIncome())
			this.setDisability(InsuranceType.INELEGIBLE.getValue());

		if (!riskProfile.ownsHouse())
			this.setHome(InsuranceType.INELEGIBLE.getValue());

		if (!riskProfile.ownsVehicle())
			this.setAuto(InsuranceType.INELEGIBLE.getValue());

		if (riskProfile.isElderly()) {
			this.setDisability(InsuranceType.INELEGIBLE.getValue());
			this.setLife(InsuranceType.INELEGIBLE.getValue());
		}
	}

	private void updatePlans(RiskScore riskScore) {
		if (!InsuranceType.INELEGIBLE.getValue().equals(this.getAuto()))
			this.setAuto(mapScore(riskScore.getAutoRisk()));

		if (!InsuranceType.INELEGIBLE.getValue().equals(this.getHome()))
			this.setHome(mapScore(riskScore.getHomeRisk()));

		if (!InsuranceType.INELEGIBLE.getValue().equals(this.getDisability()))
			this.setDisability(mapScore(riskScore.getDisabilityRisk()));

		if (!InsuranceType.INELEGIBLE.getValue().equals(this.getLife()))
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
