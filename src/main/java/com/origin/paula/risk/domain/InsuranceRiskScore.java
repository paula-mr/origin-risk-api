package com.origin.paula.risk.domain;

import java.util.List;
import java.util.stream.Collectors;

import com.origin.paula.risk.enums.InsuranceType;

public class InsuranceRiskScore {
	private int auto;
	private int disability;
	private int home;
	private int life;
	
	public InsuranceRiskScore() {
		this.auto = 0;
		this.disability = 0;
		this.home = 0;
		this.life = 0;
	}
	
	public void calculate(RiskProfile riskProfile) {
		int baseRiskScore = calculateBaseRiskPoints(riskProfile.getRiskQuestions());
		
		this.auto = baseRiskScore;
		this.disability = baseRiskScore;
		this.home = baseRiskScore;
		this.life = baseRiskScore;

		if (riskProfile.isYoungAdult()) {
			this.deductAutoRisk(2);
			this.deductDisabilityRisk(2);
			this.deductHomeRisk(2);
			this.deductLifeRisk(2);
		} else if (riskProfile.isAdult()) {
			this.deductAutoRisk(1);
			this.deductDisabilityRisk(1);
			this.deductHomeRisk(1);
			this.deductLifeRisk(1);
		}

		if (riskProfile.hasHighIncome()) {
			this.deductAutoRisk(1);
			this.deductDisabilityRisk(1);
			this.deductHomeRisk(1);
			this.deductLifeRisk(1);
		}

		if (riskProfile.hasMortgagedHouse()) {
			this.addHomeRisk(1);
			this.addDisabilityRisk(1);
		}

		if (riskProfile.hasDependents()) {
			this.addLifeRisk(1);
			this.addDisabilityRisk(1);
		}

		if (riskProfile.isMarried()) {
			this.addLifeRisk(1);
			this.deductDisabilityRisk(1);
		}

		if (riskProfile.hasNewVehicle()) {
			this.addAutoRisk(1);
		}
	}
	
	private int calculateBaseRiskPoints(List<Boolean> riskQuestions) {
		return riskQuestions.stream().filter(q -> q).collect(Collectors.toList()).size();
	}
	
	private String mapScore(int score) {
		if (score < 1)
			return InsuranceType.ECONOMIC.getValue();

		if (score < 3)
			return InsuranceType.REGULAR.getValue();

		return InsuranceType.RESPONSIBLE.getValue();
	}

	public String getAuto() {
		return mapScore(this.auto);
	}

	public void addAutoRisk(int score) {
		this.auto += score;
	}
	
	public void deductAutoRisk(int score) {
		this.auto -= score;
	}

	public String getDisability() {
		return mapScore(this.disability);
	}

	public void addDisabilityRisk(int score) {
		this.disability += score;
	}
	
	public void deductDisabilityRisk(int score) {
		this.disability -= score;
	}

	public String getHome() {
		return mapScore(this.home);
	}

	public void addHomeRisk(int score) {
		this.home += score;
	}
	
	public void deductHomeRisk(int score) {
		this.home -= score;
	}

	public String getLife() {
		return mapScore(this.life);
	}

	public void addLifeRisk(int score) {
		this.life += score;
	}
	
	public void deductLifeRisk(int score) {
		this.life -= score;
	}
	
}
